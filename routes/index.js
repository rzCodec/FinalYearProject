var mysql = require('mysql');
var parallel = require('async/parallel');
var dbconfig = require('../config/database');
var connection = mysql.createConnection(dbconfig.connection);
var request = require("request");
connection.query('USE informatics');
module.exports = function (app, passport) {
    app.get('/', function (req, res) {
        res.render('Pages/HomePage/Landing.ejs');
    });
    app.get('/login', function (req, res) {
        res.render('Pages/UserAuth/login.ejs', {message: req.flash('loginMessage')});
    });
    app.post('/login', passport.authenticate('local-login', {
            successRedirect: '/dashBoard',
            failureRedirect: '/login',
            failureFlash: true
        }),
        function (req, res) {
            if (req.body.remember) {
                req.session.cookie.maxAge = 1000 * 60 * 3;
            } else {
                req.session.cookie.expires = false;
            }
            res.redirect('/');
        });
    app.get('/signup', function (req, res) {
        connection.query('USE informatics');
        connection.query("SELECT * FROM `genres` ", function (err, genres) {
            connection.query("SELECT * FROM `distances` ORDER By distance ASC", function (err, distances) {
                res.render('Pages/UserAuth/signup.ejs', {
                    genres: genres,
                    distances: distances,
                    message: req.flash('signupMessage')
                });
            });
        });
    });
    app.post('/signup', passport.authenticate('local-signup', {
        successRedirect: '/profile',
        failureRedirect: '/signup',
        failureFlash: true
    }));
    app.get('/profile', isLoggedIn, function (req, res) {
        res.render('Pages/UserAuth/profile.ejs', {
            user: req.user
        });
    });
    app.get('/dashBoard', isLoggedIn, function (req, res) {
        var UserInfo={
            info:req.user
        };
        parallel([
                function (callback) {
                    request({
                        url: "https://www.eternalvibes.me/" + req.user.id,
                        json: true
                    }, function (error, response, body) {
                        if(error){
                            console.log(error);
                        }
                        UserInfo.Status=body;
                        callback(null);
                    });
                },
                function (callback) {
                    request({
                        url: "https://www.eternalvibes.me/getTopGenres",
                        json: true
                    }, function (error, response, body) {
                        if(error){
                            console.log(error);
                        }
                        UserInfo.TopGenres=body;
                        callback(null);
                    });
                },
                function (callback) {
                    request({
                        url: "https://www.eternalvibes.me/getTopArtists",
                        json: true
                    }, function (error, response, body) {
                        if(error){
                            console.log(error);
                        }
                        UserInfo.TopArtists=body;
                        callback(null);
                    });
                }
            ],
            function (err, results) {
                res.render('Pages/UserDashboard/DashBoard.ejs', {
                    UserInfo: UserInfo
                });
            });

    });
    app.get('/AdminProfile', isLoggedIn, function (req, res) {
        res.render('Pages/UserAuth/profile.ejs', {
            user: req.user
        });
    });
    app.get('/logout', isLoggedIn, function (req, res) {
        req.logout();
        res.redirect('/');
    });
    app.get('/appinfo', function (req, res) {
        res.render('Pages/HomePage/AppInfo.ejs', function (err, html) {
            res.send(html);
        });
    });
    app.get('/broadcast', isLoggedIn, function (req, res) {
        res.render('Pages/AdminDashboard/Broadcast.ejs', function (err, html) {
            res.send(html);
        });
    });
    app.get('/flaggedposts', isLoggedIn, function (req, res) {
        res.render('Pages/AdminDashboard/FlaggedPosts.ejs', function (err, html) {
            res.send(html);
        });
    });
    app.get('/flaggedusers', isLoggedIn, function (req, res) {
        res.render('Pages/AdminDashboard/FlaggedUsers.ejs', function (err, html) {
            res.send(html);
        });
    });
    app.get('/settings', isLoggedIn, function (req, res) {
        res.render('Pages/UserDashboard/Settings.ejs', function (err, html) {
            res.send(html);
        });
    });
    app.get('/addStudio', isLoggedIn, function (req, res) {
        res.render('Pages/AdminDashboard/AddStudio.ejs', function (err, html) {
            res.send(html);
        });
    });


    app.get('/MobileLogin', function (req, res) {
        if (req.param("status") === "false") {
            res.status(500).send('BadCredentials!')
        } else if (req.param("status") === "true") {
            res.status(200).send('Worked')
        } else {
            res.status(400).send('Something broke!')
        }
    });
    app.post('/MobileLogin', passport.authenticate('local-login', {
            successRedirect: '/MobileLogin?status=true',
            failureRedirect: '/MobileLogin?status=false',
            failureFlash: true
        }),
        function (req, res) {
            if (req.body.remember) {
                req.session.cookie.maxAge = 1000 * 60 * 3;
            } else {
                req.session.cookie.expires = false;
            }
            res.redirect('/');
        });
    app.get('/MobileSignup', function (req, res) {
        if (req.param("status") === "false") {
            res.status(500).send('BadCredentials!')
        } else if (req.param("status") === "true") {
            res.status(200).send('Worked')
        } else {
            res.status(400).send('Something broke!')
        }
    });
    app.post('/MobileSignup', passport.authenticate('local-signup', {
        successRedirect: '/MobileSignup?status=fail',
        failureRedirect: '/MobileSignup?status=fail',
        failureFlash: true
    }));


    app.get('/getTopGenres', function (req, res) {
        connection.query('SELECT genres.name, COUNT(users.genre_id) AS count FROM users INNER JOIN genres on genres.id = users.genre_id GROUP BY genre_id ORDER BY count DESC', function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//
    app.get('/getTopArtists', function (req, res) {
        connection.query('SELECT users.username, COUNT(status_list.id) AS count FROM users INNER JOIN status_list on status_list.user_id = users.id GROUP BY status_list.user_id ORDER BY count DESC', function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//
    app.get('/getUserInfo/:userID', function (req, res) {
        connection.query('SELECT * FROM `users` WHERE id=' + req.params.userID, function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//
    app.post('/updateUserInfo/:userID', function (req, res) {
        connection.query('UPDATE users SET email=?,song_link=?,distance_id=?,last_login_timestamp=?,profilepic_url=?,description=?  WHERE id=?',
            [req.body.email, req.body.song_link, req.body.distance_id, req.body.last_login_timestamp, req.body.profilepic_url, req.body.description, req.params.userID], function (error, results) {
                if (error) {
                    console.log(error)
                } else {
                    res.send(results);
                }
            });
    });//SongUrl in body
    app.get('/setUserLocation/:userID/:latitude/:longitude', function (req, res) {
        connection.query('UPDATE users SET longitude=?, latitude=? WHERE id=?', [req.params.longitude, req.params.latitude, req.params.userID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//
    app.get('/clearUserLocation/:userID', function (req, res) {
        connection.query('UPDATE users SET longitude=NULL, latitude=NULL  WHERE id=?', [req.params.userID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//
    app.get('/getGenres', function (req, res) {
        connection.query('SELECT * FROM `genres`', function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//
    app.get('/getDistances', function (req, res) {
        connection.query('SELECT * FROM `distances`', function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//
    app.get('/setUserGenre/:userID/:genreID', function (req, res) {
        connection.query('UPDATE users SET genre_id=?  WHERE id=?', [req.params.genreID, req.params.userID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//
    app.get('/setSearchDistance/:userID/:distance', function (req, res) {
        connection.query('UPDATE users SET search_distance=?  WHERE id=?', [req.params.distance, req.params.userID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//reports
    app.get('/flagUser/:userToFlagID', function (req, res) {
        connection.query('UPDATE users SET reports=(reports+1)  WHERE id=?', [req.params.userID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//
    app.get('/followUser/:activeuserID/:userIDToFollow', function (req, res) {
        connection.query('INSERT INTO `followers` (`user_id`, `liked_id`, `timestamp`) VALUES (?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))', [req.params.activeuserID, req.params.userIDToFollow], function (error) {
                if (error) {
                    console.log(error)
                } else {
                    connection.query('SELECT * FROM `followers` WHERE  user_id=? AND liked_id=?', [req.params.userIDToFollow, req.params.activeuserID], function (error2, results) {
                            if (error2) {
                                console.log(error2)
                            } else {
                                if (results.length != 0) {
                                    connection.query('INSERT INTO `friends` (`user1_id`, `user2_id`, `timestamp`) VALUES (?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))', [req.params.activeuserID, req.params.userIDToFollow], function (error) {
                                        res.send(results);
                                    });
                                }
                                else {
                                    res.send(results);
                                }
                            }
                        }
                    );
                }
            }
        )
        ;
    });
    app.get('/getStatuses/:userID', function (req, res) {
        var IDsToFetch = [];
        parallel([
                function (callback) {
                    connection.query('SELECT liked_id AS id FROM followers WHERE user_id = ?', [req.params.userID], function (error, results) {
                        if (error) {
                            console.log(error)
                        } else {
                            results.forEach(function (item) {
                                IDsToFetch.push(item.id);
                            });
                            callback(null);
                        }
                    });
                },
                function (callback) {
                    connection.query('SELECT user2_id AS id FROM friends WHERE user1_id = ?', [req.params.userID], function (error, results) {
                        if (error) {
                            console.log(error)
                        } else {
                            results.forEach(function (item) {
                                IDsToFetch.push(item.id);
                            });
                            callback(null);
                        }
                    });
                },
                function (callback) {
                    connection.query('SELECT user1_id AS id FROM friends WHERE user2_id = ?', [req.params.userID], function (error, results) {
                        if (error) {
                            console.log(error)
                        } else {
                            results.forEach(function (item) {
                                IDsToFetch.push(item.id);
                            });
                            callback(null);
                        }
                    });
                }
            ],
            function (err, results) {
                var where = "";
                IDsToFetch.forEach(function (ID) {
                    where = where + " OR status_list.user_id=" + ID + " ";
                });
                connection.query('SELECT status_list.*, users.firstname,users.surname,users.email,users.username,users.profilepic_url FROM `status_list` INNER JOIN users ON status_list.user_id = users.id WHERE status_list.user_id=' + req.params.userID + ' ' + where, function (error, results) {
                    if (error) {
                        console.log(error)
                    } else {
                        res.send(results);
                    }
                });

            });

    });
    app.get('/getFollowing/:userID', function (req, res) {
        connection.query('SELECT followers.user_id, users.username FROM `followers` INNER JOIN users on users.id=followers.liked_id WHERE followers.user_id = ?', [req.params.userID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });
    app.get('/getLocation/:userID', function (req, res) {
        connection.query('SELECT followers.liked_id,followers.user_id,followers.timestamp,users.username FROM `followers` INNER JOIN users on users.id=followers.liked_id WHERE followers.user_id = ?', [req.params.userID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });
    app.get('/stopFollowing/:currentUserID/:stopUserID', function (req, res) {
        connection.query('DELETE FROM followers WHERE user_id=? AND liked_id=?', [req.params.currentUserID, req.params.stopUserID], function (error) {
            if (error) {
                console.log(error)
            } else {
                res.send("Done");
            }
        });
    });
    app.get('/getFriends/:userID', function (req, res) {
        var FriendList = [];
        connection.query('SELECT friends.*,  users.firstname,users.surname,users.email,users.username,users.avatar_url FROM `friends` INNER JOIN users ON friends.user2_id = users.id WHERE user1_id = ?', [req.params.userID], function (error, result) {
            if (error) {
                console.log(error)
            } else {
                res.send(result);
            }
        });
    });
    app.post('/setStatus/:userID', function (req, res) {
        connection.query('INSERT INTO `status_list` (`user_id`, `timestamp`, `status`, `extra_info`) VALUES (?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000), ?, ?)', [req.params.userID, req.body.status, req.body.extraInfo], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });
    app.get('/flagStatus/:statusID', function (req, res) {
        connection.query('UPDATE `status_list` SET flagged=(flagged+1)  WHERE id=?', [req.params.statusID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });
    app.get('/likeStatus/:statusID', function (req, res) {
        connection.query('UPDATE `status_list` SET liked=(liked+1)  WHERE id=?', [req.params.statusID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });
    app.get('/CreateChat/:UserID1/:UserID2', function (req, res) {
        connection.query('INSERT INTO `chats` (`user1_id`, `user2_id`, `start_timestamp`) VALUES (?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))', [req.params.UserID1, req.params.UserID2], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });
    app.post('/sendMessage/:chatID/:userID', function (req, res) {
        connection.query('INSERT INTO `message_list` (`message`, `timestamp`, `is_read`, `sender_id`, `chat_id`) VALUES (?,  ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000), 0, ?, ?)', [req.body.message, req.params.userID, req.params.chatID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });
    app.get('/getMessages/:chatID', function (req, res) {
        connection.query('SELECT * FROM `message_list` WHERE 	chat_id=? ORDER BY timestamp ASC', [req.params.chatID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });//
    app.get('/getChats/:userID', function (req, res) {
        connection.query('SELECT * FROM `chats` WHERE user1_id=? OR user2_id=? ORDER BY start_timestamp ASC', [req.params.userID, req.params.userID], function (error, results) {
            if (error) {
                console.log(error)
            } else {
                res.send(results);
            }
        });
    });
    app.get('/getNearbyStrangers/:userID', function (req, res) {
        var DistanceUpperBound = 10;
        var Longitude = 0;
        var Latitude = 0;
        var Users = [];
        /*var Following = [];
        var Followed = [];
        var Friend1 = [];
        var Friend2 = [];*/
        parallel([
                function (callback) {
                    connection.query('SELECT users.search_distance,users.longitude,users.latitude FROM users WHERE users.id = ?', [req.params.userID], function (error, results) {
                        if (error) {
                            console.log(error)
                        } else {
                            if (results.length = 0) {
                                DistanceUpperBound = results[0].search_distance;
                                Longitude = results[0].longitude;
                                Latitude = results[0].latitude;
                            }
                            callback(null, 'one');
                        }
                    });
                },
                function (callback) {
                    connection.query('SELECT users.id, users.longitude,users.latitude FROM users WHERE users.id != ?  AND users.longitude IS NOT NULL', [req.params.userID], function (error, results) {
                        if (error) {
                            console.log(error)
                        } else {
                            Users = results;
                            callback(null, 'one');
                        }
                    });
                }/*,
                function (callback) {
                    connection.query('SELECT liked_id AS id FROM followers WHERE user_id = ?', [req.params.userID], function (error, results) {
                        if (error) {
                            console.log(error)
                        } else {
                            Following = results;
                            callback(null, 'two');
                        }
                    });
                },
                function (callback) {
                    connection.query('SELECT user_id AS id FROM followers WHERE liked_id = ?', [req.params.userID], function (error, results) {
                        if (error) {
                            console.log(error)
                        } else {
                            Followed = results;
                            callback(null, 'two');
                        }
                    });
                },
                function (callback) {
                    connection.query('SELECT user2_id AS id FROM friends WHERE user1_id = ?', [req.params.userID], function (error, results) {
                        if (error) {
                            console.log(error)
                        } else {
                            Friend1 = results;
                            callback(null, 'three');
                        }
                    });
                },
                function (callback) {
                    connection.query('SELECT user1_id AS id FROM friends WHERE user2_id = ?', [req.params.userID], function (error, results) {
                        if (error) {
                            console.log(error)
                        } else {
                            Friend2 = results;
                            callback(null, 'three');
                        }
                    });
                }*/
            ],
            function (err, results) {
                /* Following.forEach(function (t) {
                     Users = Users.filter(function (obj) {
                         return obj.id !== t.id;
                     });
                 });
                 Friend1.forEach(function (t) {
                     Users = Users.filter(function (obj) {
                         return obj.id !== t.id;
                     });
                 });
                 Friend2.forEach(function (t) {
                     Users = Users.filter(function (obj) {
                         return obj.id !== t.id;
                     });
                 });*/
                res.send(filterDistance(Users, DistanceUpperBound, Longitude, Latitude));
            });
    });
};

function isLoggedIn(req, res, next) {
    if (req.isAuthenticated())
        return next();
    res.redirect('/');
}

function filterDistance(array, distance, longitude, latitude) {
    var EndUsers = [];
    array.forEach(function (user) {
        var km = calcCrow(user.latitude, user.longitude, longitude, latitude);

        if (km <= distance) {
            EndUsers.push(user);
        }
    });
    return EndUsers;
}

function calcCrow(lat1, lon1, lat2, lon2) {
    var R = 6371; // km
    var dLat = toRad(lat2 - lat1);
    var dLon = toRad(lon2 - lon1);
    var lat1 = toRad(lat1);
    var lat2 = toRad(lat2);

    var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c;
    return d;
}

function toRad(Value) {
    return Value * Math.PI / 180;
}