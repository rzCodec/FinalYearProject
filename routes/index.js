var express = require('express');
var router = express.Router();
var request = require('request');
var async = require('async');
var app = express();
var bodyParser = require('body-parser');
app.set('view engine', 'ejs');
router.use(bodyParser.json());
router.use(bodyParser.urlencoded({
    extended: true
}));

module.exports = function (pool) {
    router.get('/', function (req, res) {
        res.render('pages/HomePage/Landing.ejs',  function (err, html) {
            res.send(html);
        });
    });

    router.get('/appinfo', function (req, res) {
        res.render('pages/HomePage/AppInfo.ejs',  function (err, html) {
            res.send(html);
        });
    });
    router.get('/broadcast', function (req, res) {
        res.render('pages/AdminDashboard/Broadcast.ejs',  function (err, html) {
            res.send(html);
        });
    });
    router.get('/flaggedposts', function (req, res) {
        res.render('pages/AdminDashboard/FlaggedPosts.ejs',  function (err, html) {
            res.send(html);
        });
    });
    router.get('/flaggedusers', function (req, res) {
        res.render('pages/AdminDashboard/FlaggedUsers.ejs',  function (err, html) {
            res.send(html);
        });
    });
    router.post('/dashboard', function (req, res) {
        var id=req.body.id;
        var avatar_url=req.body.avatar_url;
        var username=req.body.username;
        var first_name=req.body.first_name;
        var surname=req.body.surname;
        var email=req.body.email;
        var alias=req.body.alias;
        var genre_id=req.body.genre_id;
        var permalink_url=req.body.permalink_url;
        var permalink=req.body.permalink;

        var user_info;
        var status_info;
        var genre_info;
        var top_info;


        insert_user();
        function insert_user(){
            pool.query('INSERT IGNORE INTO `users` (`id`, `firstname`, `surname`, `email`, `alias`, `genre_id`, `token`, `song_link`, `latitude`, `longitude`, `pardons`, `search_distance`, `join_timestamp`, `is_banned`, `last_login_timestamp`, `avatar_url`, `permalink_url`, `permalink`) VALUES ('+id+', '+first_name+', '+surname+', '+email+', '+alias+', '+genre_id+', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '+permalink_url+', '+permalink+')', function (error, results) {
                if(error){
                    console.log(error)
                }else{
                    render_page();
                }
            });
        }
        function render_page(){
            async.parallel([
            function(done){
                pool.query('SELECT * FROM `users` WHERE id='+ id, function (error, results) {
                    if(error){
                        console.log(error)
                    }else{
                        user_info=results;
                        done(null);
                    }
                });
            },//user info
            function(done){
                pool.query('SELECT users.alias,users.avatar_url,status_list.* FROM `status_list` INNER Join users On users.id=status_list.user_id WHERE status_list.user_id='+id+' ORDER BY status_list.timestamp', function (error, results) {
                    if(error){
                        console.log(error)
                    }else{
                        status_info=results;
                        done(null);
                    }
                });
            },//status list
            function(done){
                pool.query('SELECT genres.name,COUNT(*) FROM `users` INNER JOIN genres on genres.id=users.genre_id GROUP by users.genre_id', function (error, results) {
                    if(error){
                        console.log(error)
                    }else{
                        genre_info=results;
                        done(null);
                    }
                });
            },//genre count
            function(done){
                pool.query('SELECT users.id,COUNT(*) as followerss from users INNER JOin followers on followers.liked_id= users.id GROUP BY users.id ORDER by followerss DESC LIMIT 3', function (error, results) {
                    if(error){
                        console.log(error)
                    }else{
                        top_info=results;
                        done(null);
                    }
                });
            }//top artists
        ], function(err){
            if(err){
                console.log(err);
            }else{
                res.render('pages/UserDashboard/DashBoard.ejs',{id:id,avatar_url:avatar_url,username:username,user_info:user_info,status_info:status_info,genre_info:genre_info,top_info:top_info},  function (err, html) {
                    res.send(html);
                });
            }
        });}
    });
    router.get('/settings', function (req, res) {
        res.render('pages/UserDashboard/Settings.ejs',  function (err, html) {
            res.send(html);
        });
    });

    router.get('/addStudio', function (req, res) {
        res.render('pages/AdminDashboard/AddStudio.ejs',  function (err, html) {
            res.send(html);
        });
    });

    router.get('/getUserInfo/:userID', function (req,res) {
        pool.query('SELECT * FROM `users` WHERE id='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/setUserLocation/:userID/:latitude/:longitude', function (req,res) {
        pool.query('UPDATE users SET longitude=?, latitude=? WHERE id=?',[req.params.longitude,req.params.latitude,req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/clearUserLocation/:userID', function (req,res) {
        pool.query('UPDATE users SET longitude=NULL, latitude=NULL  WHERE id=?',[req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/getGenres', function (req,res) {
        pool.query('SELECT * FROM `genres`', function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/setUserGenre/:userID/:genreID', function (req,res) {
        pool.query('UPDATE users SET genre_id=?  WHERE id=?',[req.params.genreID,req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.post('/setSong/:userID', function (req,res) {
        pool.query('UPDATE users SET song_link=?  WHERE id=?',[req.body.songLink,req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//SongUrl in body
    router.get('/setSearchDistance/:userID/:distance', function (req,res) {
        pool.query('UPDATE users SET search_distance=?  WHERE id=?',[req.params.distance,req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//reports
    router.get('/flagUser/:userToFlagID', function (req,res) {
        pool.query('UPDATE users SET reports=(reports+1)  WHERE id=?',[req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/followUser/:activeuserID/:userIDToFollow', function (req,res) {
        pool.query('INSERT INTO `followers` (`user_id`, `liked_id`, `timestamp`) VALUES (?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))',[req.params.activeuserID,req.params.userIDToFollow], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });

    router.get('/getStatuses/:userID', function (req,res) {
        pool.query('SELECT * FROM `status_list` WHERE user_id=?',[req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });
    router.post('/setStatus/:userID', function (req,res) {
        pool.query('INSERT INTO `status_list` (`user_id`, `timestamp`, `status`, `extra_info`) VALUES (?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000), ?, ?)',[req.params.userID,req.body.status,req.body.extraInfo], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//req.query.Status And req.query.ExtraInfo
    router.get('/flagStatus/:statusID', function (req,res) {
        pool.query('UPDATE `status_list` SET flagged=(flagged+1)  WHERE id=?',[req.params.statusID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });
    router.get('/likeStatus/:statusID', function (req,res) {
        pool.query('UPDATE `status_list` SET liked=(liked+1)  WHERE id=?',[req.params.statusID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });

    router.get('/CreateChat/:UserID1/:UserID2', function (req,res) {
        pool.query('INSERT INTO `chats` (`user1_id`, `user2_id`, `start_timestamp`) VALUES (?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))',[req.params.UserID1,req.params.UserID2], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });
    router.post('/sendMessage/:chatID/:userID', function (req,res) {
        pool.query('INSERT INTO `message_list` (`message`, `timestamp`, `is_read`, `sender_id`, `chat_id`) VALUES (?,  ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000), 0, ?, ?)',[req.body.message,req.params.userID,req.params.chatID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//And UserID to send to
    router.get('/getMessages/:chatID', function (req,res) {
        pool.query('SELECT * FROM `message_list` WHERE 	chat_id=? ORDER BY timestamp ASC',[req.params.chatID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/getChats/:userID', function (req,res) {
        pool.query('SELECT * FROM `chats` WHERE user1_id=? OR user2_id=? ORDER BY start_timestamp ASC',[req.params.userID,req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    return router;
};

app.use('/', router);