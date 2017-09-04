var mysql = require('mysql')
var waterfall = require('async/waterfall')
var async = require('async')
var parallel = require('async/parallel')
var dbconfig = require('../config/database')
var connection = mysql.createConnection(dbconfig.connection)
var request = require('request')
var website = process.env.website || 'http://localhost:8080'
connection.query('USE informatics')
module.exports = function (app, passport, swaggerSpec) {
  /**
   * @swagger
   * definition:
   *   skill:
   *     properties:
   *       id:
   *         type: integer
   *       name:
   *         type: string
   *   topGenre:
   *     properties:
   *       id:
   *         type: integer
   *       name:
   *         type: string
   *       count:
   *         type: integer
   *   topUsers:
   *     properties:
   *       id:
   *         type: integer
   *       username:
   *         type: string
   *       count:
   *         type: integer
   *   user:
   *     properties:
   *       id:
   *         type: integer
   *       firstname:
   *         type: string
   *       surname:
   *         type: string
   *       email:
   *         type: string
   *       username:
   *         type: string
   *       genre_id:
   *         type: integer
   *       song_link:
   *         type: string
   *       latitude:
   *         type: integer
   *       longitude:
   *         type: integer
   *       distance_id:
   *         type: integer
   *       profilepic_url:
   *         type: string
   *       description:
   *         type: string
   *       skillset:
   *         type: array
   *   genre:
   *     properties:
   *       id:
   *         type: integer
   *       name:
   *         type: string
   *       created_timestamp:
   *         type: integer
   *       updated_timestamp:
   *         type: integer
   *   distance:
   *     properties:
   *       id:
   *         type: integer
   *       distance:
   *         type: string
   *   status:
   *     properties:
   *       id:
   *         type: integer
   *       user_id:
   *         type: integer
   *       timestamp:
   *         type: integer
   *       status:
   *         type: string
   *       extra_info:
   *         type: string
   *       liked:
   *         type: integer
   *       firstname:
   *         type: integer
   *       surname:
   *         type: integer
   *       email:
   *         type: string
   *       username:
   *         type: string
   *       profilepic_url:
   *         type: integer
   *   followers:
   *     properties:
   *       liked_id:
   *         type: integer
   *       username:
   *         type: string
   *   friends:
   *     properties:
   *       id:
   *         type: integer
   *       user1_id:
   *         type: integer
   *       user2_id:
   *         type: integer
   *       timestamp:
   *         type: integer
   *       firstname:
   *         type: string
   *       surname:
   *         type: string
   *       email:
   *         type: string
   *       username:
   *         type: string
   *       profilepic_url:
   *         type: string
   *   message:
   *     properties:
   *       id:
   *         type: integer
   *       message:
   *         type: string
   *       timestamp:
   *         type: integer
   *       is_read:
   *         type: integer
   *       sender_id:
   *         type: integer
   *       chat_id:
   *         type: integer
   *   chat:
   *     properties:
   *       id:
   *         type: integer
   *       user1_id:
   *         type: string
   *       user2_id:
   *         type: integer
   *       start_timestamp:
   *         type: integer
   *   eventSkill:
   *     properties:
   *       id:
   *         type: integer
   *       event_id:
   *         type: string
   *       skill_id:
   *         type: integer
   *       status:
   *         type: boolean
   *   event:
   *     properties:
   *       id:
   *         type: integer
   *       title:
   *         type: string
   *       date:
   *         type: integer
   *       description:
   *         type: string
   *       duration:
   *         type: integer
   *       user_id:
   *         type: integer
   *       createdTimestamp:
   *         type: integer
   *       message:
   *         type: string
   *       invite_id:
   *         type: integer
   *       sender_user_id:
   *         type: integer
   *   responses:
   *     properties:
   *       id:
   *         type: integer
   *       response:
   *         type: string
   *   inviteResponse:
   *     properties:
   *       response_id:
   *         type: integer
   *       response:
   *         type: string
   *       user_id:
   *         type: integer
   *       username:
   *         type: string
   *   event_request:
   *     properties:
   *       id:
   *         type: integer
   *       event_id:
   *         type: integer
   *       sender_user_id:
   *         type: integer
   *       reason:
   *         type: string
   *       timestamp:
   *         type: string
   *       responses_id:
   *         type: integer
   */
  app.get('/', function (req, res) {
    res.render('Pages/HomePage/Landing.ejs')
  })
  app.get('/login', function (req, res) {
    res.render('Pages/UserAuth/login.ejs',
      {message: req.flash('loginMessage')})
  })
  app.post('/login', passport.authenticate('local-login', {
      successRedirect: '/dashBoard',
      failureRedirect: '/login',
      failureFlash: true,
    }),
    function (req, res) {
      if (req.body.remember) {
        req.session.cookie.maxAge = 1000 * 60 * 3
      } else {
        req.session.cookie.expires = false
      }
      res.redirect('/')
    })
  app.get('/signup', function (req, res) {
    connection.query('USE informatics')
    connection.query('SELECT * FROM `genres` ', function (err, genres) {
      connection.query('SELECT * FROM `distances` ORDER By distance ASC',
        function (err, distances) {
          connection.query('SELECT * FROM `skills`', function (err, skills) {
            console.log(skills)
            res.render('Pages/UserAuth/signup.ejs', {
              genres: genres,
              distances: distances,
              skills: skills,
              message: req.flash('signupMessage'),
            })
          })
        })
    })
  })
  app.post('/signup', passport.authenticate('local-signup', {
    successRedirect: '/dashBoard',
    failureRedirect: '/signup',
    failureFlash: true,
  }))
  app.get('/profile', isLoggedIn, function (req, res) {
    var userInfo
    var UserFinishedEvents
    var UserUpcomingEvents
    var UserPosts
    parallel([
        function (callback) {
          request({
            url: website + '/getFinishedEvents/' + req.user.id,
            json: true,
          }, function (error, response, body) {
            console.log(body)
            if (error) {
              console.log(error)
              callback(error)
            }
            UserFinishedEvents = body
            callback(null)
          })
        },
        function (callback) {
          request({
            url: website + '/getEvents/' + req.user.id,
            json: true,
          }, function (error, response, body) {
            if (error) {
              console.log(error)
              callback(error)
            }
            UserUpcomingEvents = body
            callback(null)
          })
        },
        function (callback) {
          request({
            url: website + '/getUserStatuses/' + req.user.id + '/0/10',
            json: true,
          }, function (error, response, body) {
            if (error) {
              console.log(error)
              callback(error)
            }
            UserPosts = body
            callback(null)
          })
        },
        function (callback) {
          connection.query(
            'SELECT users.*, genres.name AS genre,distances.distance AS distance FROM users INNER JOIN distances ON users.distance_id = distances.id INNER JOIN genres on users.genre_id = genres.id WHERE users.id=? AND users.is_banned != 1',
            [req.user.id], function (error, results) {
              if (error) {
                callback(error)
              } else {
                userInfo = results
                callback(null)
              }
            })
        },
      ],
      function (err, results) {
        res.render('Pages/UserDashboard/profile.ejs', {
          user: userInfo,
          UserFinishedEvents: UserFinishedEvents,
          UserPosts: UserPosts,
          UserUpcomingEvents: UserUpcomingEvents,
        })
      })
  })
  app.get('/dashBoard', isLoggedIn, function (req, res) {
    var defaultt = 0
    if (req.user.admin.toString() === defaultt.toString()) {
      var UserInfo = {
        info: req.user,
      }
      parallel([
          function (callback) {
            console.log(website)
            request({
              url: website + '/getStatuses/' + req.user.id + '/0/10',
              json: true,
            }, function (error, response, body) {
              console.log(body)
              if (error) {
                console.log(error)
              }
              UserInfo.Status = body
              callback(null)
            })
          },
          function (callback) {
            request({
              url: website + '/getTopGenres',
              json: true,
            }, function (error, response, body) {
              if (error) {
                console.log(error)
              }
              UserInfo.TopGenres = body
              callback(null)
            })
          },
          function (callback) {
            request({
              url: website + '/getTopUsers',
              json: true,
            }, function (error, response, body) {
              if (error) {
                console.log(error)
              }
              UserInfo.TopArtists = body
              callback(null)
            })
          },
        ],
        function (err, results) {
          res.render('Pages/UserDashboard/DashBoard.ejs', {
            UserInfo: UserInfo,
          })
        })
    } else {
      res.redirect('/flaggedposts')
    }

  })

  app.get('/logout', isLoggedIn, function (req, res) {
    req.logout()
    res.redirect('/')
  })
  app.get('/appinfo', function (req, res) {
    res.render('Pages/HomePage/AppInfo.ejs', function (err, html) {
      res.send(html)
    })
  })

  app.get('/flaggedposts', isLoggedIn, function (req, res) {
    connection.query(
      'SELECT status_reports.*, status_list.user_id,status_list.timestamp,status_list.status,status_list.extra_info,status_list.liked, users.username FROM `status_reports` INNER JOIN status_list ON status_list.id = status_reports.status_id INNER JOIN users ON users.id = status_list.user_id WHERE users.is_banned != 1',
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.render('Pages/AdminDashboard/FlaggedPosts.ejs',
            {info: req.user, flaggedPosts: results},
            function (err, html) {
              res.send(html)
            })
        }
      })

  })
  app.get('/flaggedusers', isLoggedIn, function (req, res) {
    connection.query(
      'SELECT users.username,user_reports.userThatReported,user_reports.userThatGotReported,user_reports.reason FROM `user_reports` INNER JOIN users ON users.id = user_reports.userThatGotReported WHERE users.is_banned != 1',
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.render('Pages/AdminDashboard/FlaggedUsers.ejs',
            {info: req.user, flaggedUsers: results},
            function (err, html) {
              console.log(err)
              res.send(html)
            })
        }
      })
  })
  app.get('/settings', isLoggedIn, function (req, res) {
    res.render('Pages/UserDashboard/Settings.ejs', function (err, html) {
      res.send(html)
    })
  })
  app.get('/addGenres', isLoggedIn, function (req, res) {
    request({
      url: website + '/getGenres',
      json: true,
    }, function (error, response, body) {
      if (error) {
        console.log(error)
        res.sendStatus(500)
      }
      res.render('Pages/AdminDashboard/AddGenres.ejs',
        {info: req.user, genres: body}, function (err, html) {
          res.send(html)
        })
    })
  })
  app.get('/addSkills', isLoggedIn, function (req, res) {
    request({
      url: website + '/getSkills',
      json: true,
    }, function (error, response, body) {
      if (error) {
        console.log(error)
        res.sendStatus(500)
      }
      res.render('Pages/AdminDashboard/AddSkills.ejs',
        {info: req.user, skills: body}, function (err, html) {
          res.send(html)
        })
    })
  })
  app.get('/reports', isLoggedIn, function (req, res) {
    var Reports = {
      users24: null,
      events24: null,
      genres: null,
      usersLogin24: null,
      skills: null,
      usersWeek1: null,
      usersWeek2: null,
      usersWeek3: null,
      usersWeek4: null,
      cities: null,
      eventsWeek1: null,
      eventsWeek2: null,
      eventsWeek3: null,
      eventsWeek4: null,
      postsWeek1: null,
      postsWeek2: null,
      postsWeek3: null,
      postsWeek4: null,
    }
    parallel([
        function (callback) {
          connection.query(
            'SELECT COUNT(*) AS count FROM `users` WHERE join_timestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-86400000)',
            function (error, results) {
              if (error) {
                console.log(error)
              } else {
                Reports.users24 = results[0]
                callback()
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT skills.id,skills.name, COUNT(user_skills.skill_id) as count FROM user_skills INNER JOIN skills ON skills.id = user_skills.skill_id GROUP BY user_skills.skill_id',
            function (error, results) {
              if (error) {
                console.log(error)
              } else {
                Reports.skills = results
                callback()
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT COUNT(*) AS count FROM `users` WHERE join_timestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-604800000)',
            function (error1, results1) {
              Reports.usersWeek1 = results1[0].count
              connection.query(
                'SELECT COUNT(*) AS count FROM `users` WHERE join_timestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-1209600000)',
                function (error2, results2) {
                  Reports.usersWeek2 = results2[0].count - Reports.usersWeek1
                  connection.query(
                    'SELECT COUNT(*) AS count FROM `users` WHERE join_timestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-1814400000)',
                    function (error3, results3) {
                      Reports.usersWeek3 = results3[0].count -
                        Reports.usersWeek2 - Reports.usersWeek1
                      connection.query(
                        'SELECT COUNT(*) AS count FROM `users` WHERE join_timestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-2419200000)',
                        function (error4, results4) {
                          if (error4) {
                            console.log(error4)
                          } else {
                            Reports.usersWeek4 = results4[0].count -
                              Reports.usersWeek3 - Reports.usersWeek2 -
                              Reports.usersWeek1
                            callback()
                          }
                        })
                    })
                })
            })
        },
        function (callback) {
          connection.query(
            'SELECT COUNT(*) AS count FROM `events` WHERE events.createdTimestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-86400000)',
            function (error, results) {
              if (error) {
                console.log(error)
              } else {
                Reports.events24 = results[0]
                callback()
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT COUNT(*) AS count FROM `users` WHERE users.last_login_timestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-86400000)',
            function (error, results) {
              if (error) {
                console.log(error)
              } else {
                Reports.usersLogin24 = results[0]
                callback()
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT genres.id,genres.name, COUNT(users.genre_id) AS count FROM users INNER JOIN genres on genres.id = users.genre_id GROUP BY genre_id ORDER BY count DESC',
            function (error, results) {
              if (error) {
                console.log(error)
              } else {
                Reports.genres = results
                callback()
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT users.city AS name, COUNT(*) AS count FROM users GROUP BY users.city ORDER BY users.city',
            function (error, results) {
              if (error) {
                console.log(error)
              } else {
                Reports.cities = results
                callback()
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT COUNT(*) AS count FROM status_list WHERE status_list.timestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-604800000)',
            function (error1, results1) {
              Reports.postsWeek1 = results1[0].count
              connection.query(
                'SELECT COUNT(*) AS count FROM status_list WHERE status_list.timestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-1209600000)',
                function (error2, results2) {
                  Reports.postsWeek2 = results2[0].count - Reports.postsWeek1
                  connection.query(
                    'SELECT COUNT(*) AS count FROM status_list WHERE status_list.timestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-1814400000)',
                    function (error3, results3) {
                      Reports.postsWeek3 = results3[0].count -
                        Reports.postsWeek2 - Reports.postsWeek1
                      connection.query(
                        'SELECT COUNT(*) AS count FROM status_list WHERE status_list.timestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-2419200000)',
                        function (error4, results4) {
                          if (error4) {
                            console.log(error4)
                          } else {
                            Reports.postsWeek4 = results4[0].count -
                              Reports.postsWeek3 - Reports.postsWeek2 -
                              Reports.postsWeek1
                            callback()
                          }
                        })
                    })
                })
            })
        },
        function (callback) {
          connection.query(
            'SELECT COUNT(*) AS count FROM events WHERE events.createdTimestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-604800000)',
            function (error1, results1) {
              Reports.eventsWeek1 = results1[0].count
              connection.query(
                'SELECT COUNT(*) AS count FROM events WHERE events.createdTimestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-1209600000)',
                function (error2, results2) {
                  Reports.eventsWeek2 = results2[0].count - Reports.eventsWeek1
                  connection.query(
                    'SELECT COUNT(*) AS count FROM events WHERE events.createdTimestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-1814400000)',
                    function (error3, results3) {
                      Reports.eventsWeek3 = results3[0].count -
                        Reports.eventsWeek2 - Reports.eventsWeek1
                      connection.query(
                        'SELECT COUNT(*) AS count FROM events WHERE events.createdTimestamp > (ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000)-2419200000)',
                        function (error4, results4) {
                          if (error4) {
                            console.log(error4)
                          } else {
                            Reports.eventsWeek4 = results4[0].count -
                              Reports.eventsWeek3 - Reports.eventsWeek2 -
                              Reports.eventsWeek1
                            callback()
                          }
                        })
                    })
                })
            })
        },
      ],
      function (err, results) {
        res.render('Pages/AdminDashboard/reports.ejs', {
          Reports: Reports,
          info: req.user,
        }, function (err, html) {
          res.send(html)
        })
      })

  })

  /**
   * @swagger
   * /MobileLogin:
   *   post:
   *     tags:
   *       - UserAuthentication
   *     description: Allows a mobile user to login
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: username
   *         description: The email
   *         in: body
   *         required: true
   *         type: string
   *       - name: password
   *         description: The Password
   *         in: body
   *         required: true
   *         type: string
   *     responses:
   *       200:
   *         description: Successful Login
   *       500:
   *         description: Failed to Login
   */
  app.get('/mobileLogin', function (req, res) {
    if (req.param('status') === 'false') {
      res.status(500).send('BadCredentials!')
    } else if (req.param('status') === 'true') {
      res.status(200).send(req.user)
    } else {
      res.status(400).send('Something broke!')
    }
  })
  app.post('/mobileLogin', passport.authenticate('local-login', {
      successRedirect: '/MobileLogin?status=true',
      failureRedirect: '/MobileLogin?status=false',
      failureFlash: true,
    }),
    function (req, res) {
      if (req.body.remember) {
        req.session.cookie.maxAge = 1000 * 60 * 3
      } else {
        req.session.cookie.expires = false
      }
    })

  /**
   * @swagger
   * /MobileSignup:
   *   post:
   *     tags:
   *       - UserAuthentication
   *     description: Allows a mobile user to signup
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: realUserName
   *         description: The username
   *         in: body
   *         required: true
   *         type: string
   *       - name: password
   *         description: The Password
   *         in: body
   *         required: true
   *         type: string
   *       - name: username
   *         description: The email of the user
   *         in: body
   *         required: true
   *         type: string
   *       - name: firstname
   *         description: The first name
   *         in: body
   *         required: true
   *         type: string
   *       - name: surname
   *         description: The surname
   *         in: body
   *         required: true
   *         type: string
   *       - name: genre_id
   *         description: The genre_id
   *         in: body
   *         required: true
   *         type: integer
   *       - name: distance_id
   *         description: The distance_id
   *         in: body
   *         required: true
   *         type: integer
   *       - name: description
   *         description: The description
   *         in: body
   *         required: true
   *         type: string
   *       - name: skills
   *         description: Array of skills id's
   *         in: body
   *         required: true
   *         type: array
   *     responses:
   *       200:
   *         description: Successfull Registration
   *       500:
   *         description: Failed to Register
   */
  app.get('/mobileSignup', function (req, res) {
    if (req.param('status') === 'false') {
      res.status(500).send('BadCredentials!')
    } else if (req.param('status') === 'true') {
      res.status(200).send(req.user)
    } else {
      res.status(400).send('Something broke!')
    }
  })
  app.post('/mobileSignup', passport.authenticate('local-signup', {
    successRedirect: '/MobileSignup?status=true',
    failureRedirect: '/MobileSignup?status=fail',
    failureFlash: true,
  }))

  /**
   * @swagger
   * /getGenres:
   *   get:
   *     tags:
   *       - genres
   *     description: Returns all genres
   *     produces:
   *       - application/json
   *     responses:
   *       200:
   *         description: An array of genre objects
   *         schema:
   *           $ref: '#/definitions/genre'
   *       500:
   *         description: An error occurred
   */
  app.get('/getGenres', function (req, res) {
    connection.query(
      'SELECT id, name, created_timestamp, updated_timestamp FROM genres',
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  app.post('/addGenre', function (req, res) {
    connection.query(
      'INSERT INTO `genres` (`id`, `name`, `created_timestamp`, `updated_timestamp`) VALUES (NULL, ?,ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000), ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))',
      [req.body.genreName],
      function (error) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })
  app.post('/addSkill', function (req, res) {
    connection.query(
      'INSERT INTO `skills` (`id`, `name`) VALUES (NULL, ?)',
      [req.body.skillName],
      function (error) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })
  /**
   * @swagger
   * /getTopGenres:
   *   get:
   *     tags:
   *       - genres
   *     description: Gets the top genres
   *     produces:
   *       - application/json
   *     responses:
   *       200:
   *         description: Array of skill objects
   *         schema:
   *           $ref: '#/definitions/topGenre'
   *       500:
   *         description: Server error
   */
  app.get('/getTopGenres', function (req, res) {
    connection.query(
      'SELECT genres.id, genres.name, COUNT(users.genre_id) AS count FROM users INNER JOIN genres on genres.id = users.genre_id GROUP BY genre_id ORDER BY count DESC',
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.status(200).send(results)
        }
      })
  })

  /**
   * @swagger
   * /getTopUsers:
   *   get:
   *     tags:
   *       - users
   *     description: Gets the top users
   *     produces:
   *       - application/json
   *     responses:
   *       200:
   *         description: Array of user objects
   *         schema:
   *           $ref: '#/definitions/topUsers'
   *       500:
   *         description: Server error
   */
  app.get('/getTopUsers', function (req, res) {
    connection.query(
      'SELECT users.id,users.username, COUNT(status_list.id) AS count FROM users INNER JOIN status_list on status_list.user_id = users.id WHERE users.is_banned != 1 GROUP BY status_list.user_id ORDER BY count DESC',
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /getUserInfoEmail/{email}:
   *   get:
   *     tags:
   *       - users
   *     description: Returns a single user referenced by their email
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: email
   *         description: Users email
   *         in: path
   *         required: true
   *         type: string
   *     responses:
   *       200:
   *         description: A single user
   *         schema:
   *           $ref: '#/definitions/user'
   *       500:
   *         description: error
   */
  app.get('/getUserInfoEmail/:email', function (req, res) {
    connection.query(
      'SELECT id, firstname, surname, email, username, genre_id, song_link, latitude, longitude, distance_id, profilepic_url, description, genre_id FROM `users` WHERE email=? AND users.is_banned != 1',
      [req.params.email], function (error, userResults) {
        if (error) {
          res.status(500).send(error)
        } else {
          userResults = userResults[0]
          connection.query(
            'SELECT skills.* FROM `user_skills` INNER JOIN skills ON user_skills.skill_id = skills.id WHERE user_skills.user_id = ?',
            [userResults.id], function (error, results) {
              if (error) {
                res.status(500).send(error)
              } else {
                userResults.skillset = results
                res.status(200).send(userResults)
              }
            })
        }
      })
  })
  /**
   * @swagger
   * /getUserInfo/{userID}:
   *   get:
   *     tags:
   *       - users
   *     description: Returns a single user referenced by their id
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: Users id
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: A single user
   *         schema:
   *           $ref: '#/definitions/user'
   *       500:
   *         description: error
   */
  app.get('/getUserInfo/:userID', function (req, res) {
    connection.query('SELECT id, firstname, surname, email, username, genre_id, song_link, latitude, longitude, distance_id, profilepic_url, description, genre_id FROM `users` WHERE id=? AND users.is_banned != 1',
      [req.params.userID], function (error, userResults) {
      if (error) {
        res.status(500).send(error)
      } else {
        userResults = userResults[0]
        connection.query(
          'SELECT skills.* FROM `user_skills` INNER JOIN skills ON user_skills.skill_id = skills.id WHERE user_skills.user_id = ?',
          [userResults.id], function (error, results) {
            if (error) {
              res.status(500).send(error)
            } else {
              userResults.skillset = results
              res.status(200).send(userResults)
            }
          })
      }
    })
  })
  /**
   * @swagger
   * /updateUserInfo:
   *   post:
   *     tags:
   *       - users
   *     description: Updates a users information
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The users id who you want to update
   *         in: body
   *         required: true
   *         type: integer
   *       - name: genre_id
   *         description: The users genre_id
   *         in: body
   *         type: integer
   *       - name: distance_id
   *         description: The users distance_id
   *         in: body
   *         type: integer
   *       - name: last_login_timestamp
   *         description: The users last_login_timestamp
   *         in: body
   *         type: integer
   *       - name: profilepic_url
   *         description: The users profilepic_url
   *         in: body
   *         type: string
   *       - name: description
   *         description: The users description
   *         in: body
   *         type: string
   *     responses:
   *       200:
   *         description: Successfully updated
   *       500:
   *         description: Failed to update
   */
  app.post('/updateUserInfo', function (req, res) {
    var body = req.body
    waterfall([
        function (callback) {
          if (body.hasOwnProperty('genre_id')) {
            connection.query('UPDATE users SET genre_id=?  WHERE id=?',
              [req.body.genre_id, req.body.userID], function (error) {
                if (error) {
                  callback(error)
                } else {
                  callback()
                }
              })
          } else {
            callback()
          }
        },
        function (callback) {
          if (body.hasOwnProperty('distance_id')) {
            connection.query('UPDATE users SET distance_id=? WHERE id=?',
              [req.body.distance_id, req.body.userID], function (error) {
                if (error) {
                  callback(error)
                } else {
                  callback()
                }
              })
          } else {
            callback()
          }
        },
        function (callback) {
          if (body.hasOwnProperty('last_login_timestamp')) {
            connection.query(
              'UPDATE users SET last_login_timestamp=?  WHERE id=?',
              [req.body.last_login_timestamp, req.body.userID], function (error) {
                if (error) {
                  callback(error)
                } else {
                  callback()
                }
              })
          } else {
            callback()
          }
        },
        function (callback) {
          if (body.hasOwnProperty('profilepic_url')) {
            connection.query('UPDATE users SET profilepic_url=? WHERE id=?',
              [req.body.profilepic_url, req.body.userID], function (error) {
                if (error) {
                  callback(error)
                } else {
                  callback()
                }
              })
          } else {
            callback()
          }
        },
        function (callback) {
          if (body.hasOwnProperty('description')) {
            connection.query('UPDATE users SET description=? WHERE id=?',
              [req.body.description, req.body.userID], function (error) {
                if (error) {
                  callback(error)
                } else {
                  callback()
                }
              })
          } else {
            callback()
          }
        },
      ],
      function (err) {
        if (err) {
          console.log(err)
          res.status(500).send(err)
        }
        res.sendStatus(200)
      })

  })
  /**
   * @swagger
   * /updateUserSkills:
   *   post:
   *     tags:
   *       - users
   *     description: Updates the users skills
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: id of user being updated
   *         in: body
   *         required: true
   *         type: integer
   *       - name: skillsIDs
   *         description: array of ids of skills
   *         in: body
   *         required: true
   *         type: array
   *     responses:
   *       200:
   *         description: Successfully updated skills
   *       500:
   *         description: Failed to update skills
   */
  app.post('/updateUserSkills', function (req, res) {
    async.map(req.body.skillsIDs, function (id, cb) {
      connection.query(
        'DELETE FROM user_skills WHERE user_skills.id = ? AND user_skills.skill_id = ?',
        [req.body.userID, id], function (error) {
          if (error) {
            cb(error)
          } else {
            connection.query(
              'INSERT INTO `user_skills` (`id`, `user_id`, `skill_id`) VALUES (NULL, ?, ?)',
              [req.body.userID, id], function (error) {
                if (error) {
                  cb(error)
                } else {
                  cb()
                }
              })
          }
        })
    }, function (err) {
      if (err) {
        res.status(500).send(err)
      } else {
        res.sendStatus(200)
      }
    })
  })
  /**
   * @swagger
   * /reportUser:
   *   post:
   *     tags:
   *       - users
   *     description: Logs a report of a user
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userThatReported
   *         description: UserID that is reporting
   *         in: body
   *         required: true
   *         type: integer
   *       - name: userThatGotReported
   *         description: UserID that got reported
   *         in: body
   *         required: true
   *         type: integer
   *       - name: reason
   *         description: The reason for the reporting
   *         in: body
   *         required: true
   *         type: string
   *     responses:
   *       200:
   *         description: Successfully created
   *       500:
   *         description: Failed to report
   */
  app.post('/reportUser', function (req, res) {
    connection.query(
      'INSERT INTO `user_reports` (`id`, `userThatReported`, `userThatGotReported`, `reason`) VALUES (NULL, ?, ?, ?)',
      [
        req.body.userThatReported,
        req.body.userThatGotReported,
        req.body.reason], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /followUser/{activeuserID}/{userIDToFollow}:
   *   get:
   *     tags:
   *       - users
   *     description: Follows a user
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: activeuserID
   *         description: user_id
   *         in: path
   *         required: true
   *         type: integer
   *       - name: userIDToFollow
   *         description: liked_id
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Followed the user
   *       500:
   *         description: Failed to follow the user
   */
  app.get('/followUser/:activeuserID/:userIDToFollow', function (req, res) {
    connection.query(
      'INSERT INTO `followers` (`user_id`, `liked_id`, `timestamp`) VALUES (?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))',
      [req.params.activeuserID, req.params.userIDToFollow], function (error) {
        if (error) {
          console.log(error)
        } else {
          connection.query(
            'SELECT * FROM `followers` WHERE  user_id=? AND liked_id=?',
            [req.params.userIDToFollow, req.params.activeuserID],
            function (error2, results) {
              if (error2) {
                res.status(500).send(error2)
              } else {
                if (results.length != 0) {
                  connection.query(
                    'INSERT INTO `friends` (`user1_id`, `user2_id`, `timestamp`) VALUES (?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))',
                    [req.params.activeuserID, req.params.userIDToFollow],
                    function (error) {
                      if (error) {
                        res.status(500).send(error)
                      }
                      res.sendStatus(200)
                    })
                }
                else {
                  res.sendStatus(200)
                }
              }
            }
          )
        }
      }
    )

  })
  /**
   * @swagger
   * /getFollowing/{userID}:
   *   get:
   *     tags:
   *       - users
   *     description: Gets a users followers
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The user id of the user who's followers you want
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Got the statuses
   *         schema:
   *           $ref: '#/definitions/followers'
   *       500:
   *         description: Failed to get the followers
   */
  app.get('/getFollowing/:userID', function (req, res) {
    connection.query(
      'SELECT followers.liked_id, users.username FROM `followers` INNER JOIN users on users.id=followers.liked_id WHERE followers.user_id = ? AND users.is_banned != 1',
      [req.params.userID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /stopFollowing/{currentUserID}/{stopUserID}:
   *   get:
   *     tags:
   *       - users
   *     description: Stops following a user
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: currentUserID
   *         description: The user id of user requesting the stop following action
   *         in: path
   *         required: true
   *         type: integer
   *       - name: stopUserID
   *         description: The user id of the user who's will not be follow anymore
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Stopped following the user
   *       500:
   *         description: Failed to stop following the user
   */
  app.get('/stopFollowing/:currentUserID/:stopUserID', function (req, res) {
    connection.query('DELETE FROM followers WHERE user_id=? AND liked_id=?',
      [req.params.currentUserID, req.params.stopUserID], function (error) {
        if (error) {
          res.status(500).send(error)
        } else {
          connection.query(
            'DELETE from friends where (user1_id = ? AND user2_id = ?) OR (user1_id = ? AND user2_id = ?)',
            [
              req.params.activeuserID,
              req.params.userIDToFollow,
              req.params.userIDToFollow,
              req.params.activeuserID], function (error) {
              if (error) {
                res.status(500).send(error)
              }
              res.sendStatus(200)
            })
        }
      })
  })
  /**
   * @swagger
   * /getFriends/{userID}:
   *   get:
   *     tags:
   *       - users
   *     description: Gets a users friends
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The user id of user who's friends need to be fetched
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Array of friend objects
   *         schema:
   *           $ref: '#/definitions/friends'
   *       500:
   *         description: Failed to get the users friends
   */
  app.get('/getFriends/:userID', function (req, res) {
    var FriendList = []
    waterfall([
        function (callback) {
          connection.query(
            'SELECT friends.*,  users.firstname,users.surname,users.email,users.username,users.profilepic_url FROM `friends` INNER JOIN users ON friends.user2_id = users.id WHERE user1_id = ? AND users.is_banned != 1',
            [req.params.userID], function (error, result) {
              if (error) {
                callback(error)
              } else {
                FriendList.push(result)
                callback()
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT friends.*,  users.firstname,users.surname,users.email,users.username,users.profilepic_url FROM `friends` INNER JOIN users ON friends.user1_id = users.id WHERE user2_id = ? AND users.is_banned != 1',
            [req.params.userID], function (error, result) {
              if (error) {
                callback(error)
              } else {
                FriendList.push(result)
                callback()
              }
            })
        },
      ],
      function (err) {
        if (err) {
          res.status(500).send(err)
        }
        res.send(FriendList)
      })
  })
  /**
   * @swagger
   * /setCity:
   *   post:
   *     tags:
   *       - users
   *     description: Updates a users city
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The event ID
   *         in: body
   *         required: true
   *         type: integer
   *       - name: city
   *         description: The Response ID
   *         in: body
   *         required: true
   *         type: string
   *     responses:
   *       200:
   *         description: Successfully set the city
   *       500:
   *         description: Failed to set the city
   */
  app.post('/setCity', function (req, res) {
    connection.query('UPDATE users SET users.city=? WHERE users.id=?',
      [req.body.city, req.body.userID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })

  /**
   * @swagger
   * /getSkills:
   *   get:
   *     tags:
   *       - skills
   *     description: Gets all the skills
   *     produces:
   *       - application/json
   *     responses:
   *       200:
   *         description: Array of skill objects
   *         schema:
   *           $ref: '#/definitions/skill'
   */
  app.get('/getSkills', function (req, res) {
    connection.query('SELECT * FROM `skills`', function (error, results) {
      if (error) {
        res.status(500).send(error)
      } else {
        res.send(results)
      }
    })
  })

  /**
   * @swagger
   * /getStatuses/{userID}/{offset}/{limit}:
   *   get:
   *     tags:
   *       - statuses
   *     description: Gets a users statuses
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The user id of the user whos statuses you want
   *         in: path
   *         required: true
   *         type: integer
   *       - name: offset
   *         description: The starting row to return
   *         in: path
   *         required: true
   *         type: integer
   *       - name: limit
   *         description: How far from the starting row to return
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Got the statuses
   *         schema:
   *           $ref: '#/definitions/status'
   *       500:
   *         description: Failed to get the statuses
   */
  app.get('/getStatuses/:userID/:offset/:limit', function (req, res) {
    var IDsToFetch = []
    parallel([
        function (callback) {
          connection.query(
            'SELECT liked_id AS id FROM followers WHERE user_id = ?',
            [req.params.userID], function (error, results) {
              if (error) {
                console.log(error)
              } else {
                results.forEach(function (item) {
                  IDsToFetch.push(item.id)
                })
                callback(null)
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT user2_id AS id FROM friends WHERE user1_id = ?',
            [req.params.userID], function (error, results) {
              if (error) {
                console.log(error)
              } else {
                results.forEach(function (item) {
                  IDsToFetch.push(item.id)
                })
                callback(null)
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT user1_id AS id FROM friends WHERE user2_id = ?',
            [req.params.userID], function (error, results) {
              if (error) {
                console.log(error)
              } else {
                results.forEach(function (item) {
                  IDsToFetch.push(item.id)
                })
                callback(null)
              }
            })
        },
      ],
      function (err, results) {
        var where = ''
        IDsToFetch.forEach(function (ID) {
          where = where + ' OR status_list.user_id=' + ID + ' '
        })
        connection.query('SELECT status_list.*, users.firstname,users.surname,users.email,users.username,users.profilepic_url FROM `status_list` INNER JOIN users ON status_list.user_id = users.id WHERE status_list.user_id=' +
          req.params.userID + ' ' + where +
          ' AND users.is_banned != 1 ORDER BY status_list.timestamp DESC LIMIT ' + req.params.limit +
          ' OFFSET ' + req.params.offset, function (error, results) {
          if (error) {
            res.status(500).send(error)
          } else {
            res.send(results)
          }
        })
      })
  })
  app.get('/getUserStatuses/:userID/:offset/:limit', function (req, res) {
    connection.query(
      'SELECT * FROM `status_list` WHERE user_id=? ORDER BY timestamp DESC LIMIT ? OFFSET ?',
      [req.params.userID, req.params.limit, req.params.offset],
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.status(200).send(results)
        }
      })
  })
  /**
   * @swagger
   * /setStatus:
   *   post:
   *     tags:
   *       - users
   *     description: Creates a new status
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The user id of the user setting the status
   *         in: body
   *         required: true
   *       - name: status
   *         description: The details of the status
   *         in: body
   *         required: true
   *       - name: extraInfo
   *         description: Any extra details for the status
   *         in: body
   *         required: true
   *     responses:
   *       200:
   *         description: Successfully created
   *       500:
   *         description: Failed to create status
   */
  app.post('/setStatus', function (req, res) {
    connection.query(
      'INSERT INTO `status_list` (`user_id`, `timestamp`, `status`, `extra_info`) VALUES (?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000), ?, ?)',
      [req.body.userID, req.body.status, req.body.extraInfo],
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })
  /**
   * @swagger
   * /flagStatus:
   *   post:
   *     tags:
   *       - statuses
   *     description: Flags a status
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: status_id
   *         description: The id of the status being reported
   *         in: body
   *         required: true
   *       - name: reporterUser_id
   *         description: The reporters user id
   *         in: body
   *         required: true
   *       - name: reason
   *         description: The reason for reporting
   *         in: body
   *         required: true
   *     responses:
   *       200:
   *         description: Successfully reported
   *       500:
   *         description: Failed to report
   */
  app.post('/flagStatus', function (req, res) {
    connection.query(
      'INSERT INTO `status_reports` (`id`, `status_id`, `reporterUser_id`, `reason`) VALUES (NULL, ?, ?, ?)',
      [req.body.status_id, req.body.reporterUser_id, req.body.reason],
      function (error) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })

  /**
   * @swagger
   * /likeStatus/{userID}/{statusID}:
   *   get:
   *     tags:
   *       - statuses
   *     description: Likes a status
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The user id of the user liking the status
   *         in: path
   *         required: true
   *         type: integer
   *       - name: statusID
   *         description: The status id that is being liked
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Successfully liked status
   *       404:
   *         description: Already liked
   *       500:
   *         description: Failed to like
   */
  app.get('/likeStatus/:userID/:statusID', function (req, res) {
    connection.query(
      'INSERT INTO `status_likes` (`id`, `status_id`, `likerUser_id`) VALUES (NULL, ?, ?)',
      [req.params.statusID, req.params.userID], function (error) {
        if (error) {
          res.status(404).send(error)
        } else {
          connection.query(
            'UPDATE `status_list` SET flagged=(flagged+1)  WHERE id=?',
            [req.params.statusID], function (error) {
              if (error) {
                res.status(500).send(error)
              } else {
                res.sendStatus(200)
              }
            })
        }
      })
  })

  /**
   * @swagger
   * /createChat/{UserID1}/{UserID2}:
   *   get:
   *     tags:
   *       - chat
   *     description: Create a chat
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: UserID1
   *         description: The user creating the chat
   *         in: path
   *         required: true
   *         type: integer
   *       - name: UserID2
   *         description: The user creating the chat with
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Successfully create a chat
   *       500:
   *         description: Failed to create a chat
   */
  app.get('/createChat/:UserID1/:UserID2', function (req, res) {
    connection.query(
      'INSERT INTO `chats` (`user1_id`, `user2_id`, `start_timestamp`) VALUES (?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))',
      [req.params.UserID1, req.params.UserID2], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          connection.query('SELECT * FROM `chats` WHERE `id`= ?',
            [results.insertId], function (error, result) {
              if (error) res.status(500).send(error)
              res.status(200).send(result)
            })
        }
      })
  })
  /**
   * @swagger
   * /sendMessage:
   *   post:
   *     tags:
   *       - chat
   *     description: Sends a message in a chat
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The user id of the user sending the message
   *         in: body
   *         required: true
   *       - name: message
   *         description: The message that is being sent
   *         in: body
   *         required: true
   *       - name: chatID
   *         description: The chat ID that it's being sent in
   *         in: body
   *         required: true
   *     responses:
   *       200:
   *         description: Returns the sent message
   *         schema:
   *           $ref: '#/definitions/message'
   *       500:
   *         description: Failed to send message
   */

  app.post('/sendMessage', function (req, res) {
    connection.query(
      'INSERT INTO `message_list` (`message`, `timestamp`, `is_read`, `sender_id`, `chat_id`) VALUES (?,  ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000), 0, ?, ?)',
      [req.body.message, req.body.userID, req.body.chatID],
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          connection.query('SELECT * FROM `message_list` WHERE `id`= ?',
            [results.insertId], function (error, result) {
              if (error) res.status(500).send(error)

              res.status(200).send(result)
            })
        }
      })
  })
  /**
   * @swagger
   * /getMessages/{chatID}:
   *   get:
   *     tags:
   *       - chat
   *     description: Get the messages from a chat
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: chatID
   *         description: The id of the chat being requested
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: An array of message objects
   *         schema:
   *           $ref: '#/definitions/message'
   *       500:
   *         description: Failed to get messages
   */
  app.get('/getMessages/:chatID', function (req, res) {
    connection.query(
      'SELECT * FROM `message_list` WHERE 	chat_id=? ORDER BY timestamp ASC',
      [req.params.chatID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /getChats/{userID}:
   *   get:
   *     tags:
   *       - chat
   *     description: Gets the chats that a user is involved in
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: chatID
   *         description: The id of the users who's chats are being requested
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: An array of chat objects
   *         schema:
   *           $ref: '#/definitions/chat'
   *       500:
   *         description: Failed to get chats
   */
  app.get('/getChats/:userID', function (req, res) {
    connection.query(
      'SELECT * FROM `chats` WHERE user1_id=? OR user2_id=? ORDER BY start_timestamp ASC',
      [req.params.userID, req.params.userID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /messageIsRead:
   *   post:
   *     tags:
   *       - chat
   *     description: Marks the array of message id's as read
   *       - application/json
   *     parameters:
   *       - name: messageIds
   *         description: array of message id's that have been read
   *         in: body
   *         required: true
   *         type: array
   *     responses:
   *       200:
   *         description: The messages have been marked as read
   *       500:
   *         description: Failed to mark the messages as read
   */
  app.post('/messagesAreRead', function (req, res) {
    var messageIds = req.body.messageIds
    async.map(messageIds, function (id, cb) {
      connection.query(
        'UPDATE message_list SET message_list.is_read = true WHERE id=?', [id],
        function (error) {
          if (error) {
            cb(error)
          } else {
            cb()
          }
        })
    }, function (err) {
      if (err) {
        res.status(500).send(err)
      } else {
        res.sendStatus(200)
      }
    })
  })
  /**
   * @swagger
   * /deleteMessages:
   *   post:
   *     tags:
   *       - chat
   *     description: Deletes the array of message id's
   *       - application/json
   *     parameters:
   *       - name: messageIds
   *         description: array of message id's that will be deleted
   *         in: body
   *         required: true
   *         type: array
   *     responses:
   *       200:
   *         description: The messages have been deleted
   *       500:
   *         description: Failed to delete the messages as read
   */
  app.post('/deleteMessages', function (req, res) {
    var messageIds = req.body.messageIds
    async.map(messageIds, function (id, cb) {
      connection.query('DELETE FROM message_list WHERE message_list.id=?', [id],
        function (error) {
          if (error) {
            cb(error)
          } else {
            cb()
          }
        })
    }, function (err) {
      if (err) {
        res.status(500).send(err)
      } else {
        res.sendStatus(200)
      }
    })
  })

  /**
   * @swagger
   * /getNearbyStrangers/{userID}:
   *   get:
   *     tags:
   *       - radar
   *     description: Get the strangers that are nearby a user
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The id of the user who is requesting to find strangers
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: An array of strangers objects TODO a scheme
   *       500:
   *         description: Failed to find strangers
   */
  app.get('/getNearbyStrangers/:userID', function (req, res) {
    var DistanceUpperBound = 10
    var Longitude = null
    var Latitude = null
    var Users = []
    /*var Following = [];
    var Followed = [];
    var Friend1 = [];
    var Friend2 = [];*/
    parallel([
        function (callback) {
          connection.query(
            'SELECT distances.distance AS search_distance,users.city,users.longitude,users.latitude FROM users INNER JOIN distances on distance_id=distances.id WHERE users.id = ? AND users.is_banned != 1',
            [req.params.userID], function (error, results) {
              if (error) {
                callback(error)
              } else {
                if (results.length != 0) {
                  DistanceUpperBound = results[0].search_distance
                  Longitude = results[0].longitude
                  Latitude = results[0].latitude
                }
                callback(null, 'one')
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT users.id, users.city, users.longitude,users.latitude FROM users WHERE users.id != ?  AND users.longitude IS NOT NULL AND users.is_banned != 1',
            [req.params.userID], function (error, results) {
              if (error) {
                callback(error)
              } else {
                Users = results
                callback(null, 'one')
              }
            })
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
        if (err) {
          res.status(500).send(err)
        }
        res.send(
          filterDistance(Users, DistanceUpperBound, Longitude, Latitude))
      })
  })
  /**
   * @swagger
   * /setUserLocation/{userID}/{latitude}/{longitude}:
   *   get:
   *     tags:
   *       - radar
   *     description: Sets the user location
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: Users id
   *         in: path
   *         required: true
   *         type: integer
   *       - name: latitude
   *         description: Users id
   *         in: path
   *         required: true
   *         type: integer
   *       - name: longitude
   *         description: Users id
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Set successfully
   *       500:
   *         description: Failed to set
   */
  app.get('/setUserLocation/:userID/:latitude/:longitude', function (req, res) {
    connection.query('UPDATE users SET longitude=?, latitude=? WHERE id=?',
      [req.params.longitude, req.params.latitude, req.params.userID],
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })
  /**
   * @swagger
   * /clearUserLocation/{userID}:
   *   get:
   *     tags:
   *       - radar
   *     description: Clears the users location
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: Users id
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Cleared the users location successfully
   *       500:
   *         description: Failed to clear the users location
   */
  app.get('/clearUserLocation/:userID', function (req, res) {
    connection.query(
      'UPDATE users SET longitude=NULL, latitude=NULL  WHERE id=?',
      [req.params.userID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /getDistances:
   *   get:
   *     tags:
   *       - radar
   *     description: Returns all distances
   *     produces:
   *       - application/json
   *     responses:
   *       200:
   *         description: An array of distance objects
   *         schema:
   *           $ref: '#/definitions/distance'
   *       500:
   *         description: An error occurred
   */
  app.get('/getDistances', function (req, res) {
    connection.query('SELECT id, distance FROM `distances`',
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })

  /**
   * @swagger
   * /createEvent:
   *   post:
   *     tags:
   *       - events
   *     description: Creates an event
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: title
   *         description: The title of the event
   *         in: body
   *         type: string
   *       - name: date
   *         description: The timestamp of the event start
   *         in: body
   *         type: integer
   *       - name: description
   *         description: The description of the event
   *         in: body
   *         type: string
   *       - name: duration
   *         description: The duration of the event in seconds
   *         in: body
   *         type: integer
   *       - name: user_id
   *         description: The id of the user who is hosting the event
   *         in: body
   *         type: integer
   *     responses:
   *       200:
   *         description: Successfully created the event
   *       500:
   *         description: Failed to create the event
   */
  app.post('/createEvent', function (req, res) {
    connection.query(
      'INSERT INTO `events` (`id`, `title`, `date`, `description`, `duration`, `user_id`,`createdTimestamp`) VALUES (NULL, ?, ?, ?, ?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))',
      [
        req.body.title,
        req.body.date,
        req.body.description,
        req.body.duration,
        req.body.user_id], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })
  /**
   * @swagger
   * /cancelEvent/{eventID}:
   *   get:
   *     tags:
   *       - events
   *     description: Deletes a event from the database and all invites associated with it
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: eventID
   *         description: The event that is going to be cancelled
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Cleared the users location successfully
   *       500:
   *         description: Failed to clear the users location
   */
  app.get('/cancelEvent/:eventID', function (req, res) {
    connection.query('delete from events where events.id = ?',
      [req.params.eventID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          connection.query('delete from invites where invites.event_id = ?',
            [req.params.eventID], function (error, results) {
              if (error) {
                res.status(500).send(error)
              } else {
                res.send(results)
              }
            })
        }
      })
  })
  /**
   * @swagger
   * /getEvents/{userID}:
   *   get:
   *     tags:
   *       - events
   *     description: Gets the events associated to a user
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The id of the user who's events need to be fetched
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Array of event objects
   *         schema:
   *           $ref: '#/definitions/event'
   *       500:
   *         description: Failed to get a users events
   */
  app.get('/getEvents/:userID', function (req, res) {
    connection.query('SELECT events.*, invites.message,invites.id AS invite_id,invites.sender_user_id FROM invites \n' +
      'INNER JOIN events on events.id = invites.event_id \n' +
      'INNER JOIN responses ON responses.id = invites.response_id \n' +
      'WHERE invites.receiver_user_id = ? AND response_id !=3 AND events.date>=(UNIX_TIMESTAMP(CURTIME(4)) * 1000)',
      [req.params.userID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /getUsersEvents/{userID}:
   *   get:
   *     tags:
   *       - events
   *     description: Gets the events a user has created
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The id of the user who's created events need to be fetched
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Array of event objects
   *         schema:
   *           $ref: '#/definitions/event'
   *       500:
   *         description: Failed to get a users events
   */
  app.get('/getUsersEvents/:userID', function (req, res) {
    connection.query(
      'SELECT events.* FROM events WHERE events.user_id=? AND events.date>=(UNIX_TIMESTAMP(CURTIME(4)) * 1000)',
      [req.params.userID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /getFinishedUsersEvents/{userID}:
   *   get:
   *     tags:
   *       - events
   *     description: Gets the finished events a user has created
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The id of the user who's finished created events need to be fetched
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Array of event objects
   *         schema:
   *           $ref: '#/definitions/event'
   *       500:
   *         description: Failed to get a users finished events
   */
  app.get('/getFinishedUsersEvents/:userID', function (req, res) {
    connection.query(
      'SELECT events.* FROM events WHERE events.user_id=? AND events.date<=(UNIX_TIMESTAMP(CURTIME(4)) * 1000)',
      [req.params.userID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /getFinishedEvents/{userID}:
   *   get:
   *     tags:
   *       - events
   *     description: Gets the finished events that a user was invited to
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: userID
   *         description: The id of the user who's finished events need to be fetched
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Array of event objects
   *         schema:
   *           $ref: '#/definitions/event'
   *       500:
   *         description: Failed to get a users events
   */
  app.get('/getFinishedEvents/:userID', function (req, res) {
    connection.query('SELECT events.*, invites.message,invites.id AS invite_id,invites.sender_user_id FROM invites \n' +
      'INNER JOIN events on events.id = invites.id \n' +
      'INNER JOIN responses ON responses.id = invites.response_id \n' +
      'WHERE invites.receiver_user_id = ? AND response_id !=3 AND events.date<=(UNIX_TIMESTAMP(CURTIME(4)) * 1000)',
      [req.params.userID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /sendInvite:
   *   post:
   *     tags:
   *       - events
   *     description: Sends an invite to an event
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: sender_user_id
   *         description: The inviter user id
   *         in: body
   *         type: integer
   *       - name: receiver_user_id
   *         description: The invitee user id
   *         in: body
   *         type: integer
   *       - name: event_id
   *         description: The id of the event that is being invited to
   *         in: body
   *         type: integer
   *       - name: message
   *         description: Invite message
   *         in: body
   *         type: string
   *     responses:
   *       200:
   *         description: Successfully invited
   *       500:
   *         description: Failed to invited
   */
  app.post('/sendInvite', function (req, res) {
    connection.query(
      'INSERT INTO `invites` (`id`, `sender_user_id`, `receiver_user_id`, `response_id`, `event_id`, `message`) VALUES (NULL, ?, ?, 2, ?, ?)',
      [
        req.body.sender_user_id,
        req.body.receiver_user_id,
        req.body.event_id,
        req.body.message], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })
  /**
   * @swagger
   * /respondToInvite:
   *   post:
   *     tags:
   *       - events
   *     description: Responds to an invite
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: id
   *         description: The id of the event to respond to
   *         in: body
   *         type: integer
   *       - name: response_id
   *         description: The id of the response that was selected
   *         in: body
   *         type: integer
   *     responses:
   *       200:
   *         description: Successfully responded
   *       500:
   *         description: Failed to respond
   */
  app.post('/respondToInvite', function (req, res) {
    connection.query(
      'UPDATE `invites` SET `response_id` = ? WHERE `invites`.`id` = ?;',
      [req.body.response_id, req.body.id], function (error) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })
  /**
   * @swagger
   * /getResponses:
   *   get:
   *     tags:
   *       - events
   *     description: Gets the responses that can be responded to an event
   *     produces:
   *       - application/json
   *     responses:
   *       200:
   *         description: Array of response objects
   *         schema:
   *           $ref: '#/definitions/responses'
   *       500:
   *         description: Failed to get responses
   */
  app.get('/getResponses', function (req, res) {
    connection.query('SELECT * FROM `responses`', function (error, results) {
      if (error) {
        res.status(500).send(error)
      } else {
        res.status(200).send(results)
      }
    })
  })
  /**
   * @swagger
   * /getEventResponses/{eventID}:
   *   get:
   *     tags:
   *       - events
   *     description: Gets the responses of a events invites and includes the user information
   *     produces:
   *       - application/json
   *     responses:
   *       200:
   *         description: Array of inviteResponse objects
   *         schema:
   *           $ref: '#/definitions/inviteResponse'
   *       500:
   *         description: Failed to get responses
   */
  app.get('/getEventResponses/:eventID', function (req, res) {
    connection.query(
      'SELECT invites.id AS response_id, responses.response, users.id AS user_id, users.username  FROM invites INNER JOIN users ON users.id = receiver_user_id  INNER JOIN responses ON responses.id = invites.response_id WHERE invites.event_id =? AND users.is_banned != 1',
      [req.params.eventID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.status(200).send(results)
        }
      })
  })

  /**
   * @swagger
   * /getEventSkills/{eventID}:
   *   get:
   *     tags:
   *       - eventSkills
   *     description: Gets the skills associated with an event
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: eventID
   *         description: The id of th event
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: An Array of skills associated with an event
   *         schema:
   *           $ref: '#/definitions/eventSkill'
   *       500:
   *         description: Failed to clear the users location
   */
  app.get('/getEventSkill/:eventID', function (req, res) {
    connection.query(
      'SELECT events_skills.* from events_skills INNER JOIN events on events.id = events_skills.event_id WHERE events_skills.event_id=?',
      [req.params.eventID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.status(200).send(results)
        }
      })
  })
  /**
   * @swagger
   * /confirmEventSkill/{event_skillID}:
   *   get:
   *     tags:
   *       - eventSkills
   *     description: Confirm an event skill
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: event_skillID
   *         description: The id of th event skill
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: The skill was confirmed
   *       500:
   *         description: The skill failed to confirm
   */
  app.get('/confirmEventSkill/:event_skillID', function (req, res) {
    connection.query('UPDATE events_skills SET status = TRUE WHERE id=?',
      [req.params.event_skillID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })
  /**
   * @swagger
   * /unconfirmEventSkill/{event_skillID}:
   *   get:
   *     tags:
   *       - eventSkills
   *     description: Unconfirm an event skill
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: event_skillID
   *         description: The id of th event skill
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: The skill was unconfirmed
   *       500:
   *         description: The skill failed to unconfirm
   */
  app.get('/unconfirmEventSkill/:event_skillID', function (req, res) {
    connection.query('UPDATE events_skills SET status = FALSE WHERE id=?',
      [req.params.event_skillID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })
  /**
   * @swagger
   * /addEventSkill/{event_id}/{skill_id}:
   *   get:
   *     tags:
   *       - eventSkills
   *     description: Add a skill needed to an event
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: event_id
   *         description: The id of the event
   *         in: path
   *         required: true
   *         type: integer
   *       - name: skill_id
   *         description: The id of the skill
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: The skill was added to the event successfully
   *       500:
   *         description: The skill failed at being added to the event
   */
  app.get('/addEventSkill/:event_id/:skill_id', function (req, res) {
    connection.query(
      'INSERT INTO `events_skills` (`id`, `event_id`, `skill_id`, `status`) VALUES (NULL, ?, ?, FALSE)',
      [req.params.event_id, req.params.skill_id], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })

  /**
   * @swagger
   * /sendRequestToJoinEvent:
   *   post:
   *     tags:
   *       - eventRequest
   *     description: Creates a request to join an event
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: event_id
   *         description: event id
   *         in: body
   *         required: true
   *         type: integer
   *       - name: sender_user_id
   *         description: Usender_user_id
   *         in: body
   *         required: true
   *         type: integer
   *       - name: reason
   *         description: Treason
   *         in: body
   *         required: true
   *         type: string
   *     responses:
   *       200:
   *         description: Successfully created
   *       500:
   *         description: Failed to report
   */
  app.post('/sendRequestToJoinEvent', function (req, res) {
    connection.query(
      'INSERT INTO `event_request` (`id`, `event_id`, `sender_user_id`, `reason`, `timestamp`, `responses_id`) VALUES (NULL, ?, ?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000), 2)',
      [req.body.event_id, req.body.sender_user_id, req.body.reason],
      function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.sendStatus(200)
        }
      })
  })
  /**
   * @swagger
   * /getRequestedInvites/{eventID}:
   *   get:
   *     tags:
   *       - eventRequest
   *     description: getRequestedInvites
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: eventID
   *         description: event id
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Array of event request objects
   *         schema:
   *           $ref: '#/definitions/event_request'
   *       500:
   *         description: Failed to get
   */
  app.get('/getRequestedInvites/:eventID', function (req, res) {
    connection.query(
      'SELECT event_request.*, users.username FROM `event_request` INNER JOIN users ON users.id= event_request.sender_user_id WHERE event_request.event_id=? AND event_request.responses_id!=3 AND users.is_banned != 1',
      [req.params.eventID], function (error, results) {
        if (error) {
          res.status(500).send(error)
        } else {
          res.send(results)
        }
      })
  })
  /**
   * @swagger
   * /updateRequestResponse/{responseID}/{responseID}:
   *   get:
   *     tags:
   *       - eventRequest
   *     description: Updates the request to join an events reponse
   *     produces:
   *       - application/json
   *     parameters:
   *       - name: eventRequestID
   *         description: The event request ID
   *         in: path
   *         required: true
   *         type: integer
   *       - name: responseID
   *         description: The Response ID
   *         in: path
   *         required: true
   *         type: integer
   *     responses:
   *       200:
   *         description: Successfully updated
   *       500:
   *         description: Failed to update
   */
  app.get('/updateRequestResponse/:eventRequestID/:responseID',
    function (req, res) {
      connection.query(
        'UPDATE event_request SET responses_id=? WHERE event_request.id=?\n',
        [req.params.responseID, req.params.eventRequestID],
        function (error, results) {
          if (error) {
            res.status(500).send(error)
          } else {
            res.send(results)
          }
        })
    })

  app.post('/deleteStatus', function (req, res) {
    waterfall([
        function (callback) {
          connection.query('SELECT * FROM `status_list` WHERE id = ?',
            [req.body.statusId], function (error, results) {
              if (error) {
                callback(error)
              } else {
                callback(null, results[0])
              }
            })
        },
        function (flaggedPost, callback) {
          connection.query('INSERT INTO `removed_status` (`post_id`, `user_id`, `timestamp`, `status`, `extra_info`, `liked`) VALUES (' +
            flaggedPost.id + ', ' + flaggedPost.user_id + ', ' +
            flaggedPost.timestamp + ', ' + flaggedPost.status + ',  ' +
            flaggedPost.extra_info + ', ' + flaggedPost.liked + ')',
            function (error) {
              if (error) {
                callback(error)
              } else {
                callback(null)
              }
            })
        },
        function (callback) {
          connection.query('DELETE FROM status_list WHERE status_list.id=?',
            [req.body.statusId], function (error) {
              if (error) {
                callback(error)
              } else {
                callback(null)
              }
            })
        },
        function (callback) {
          connection.query(
            'SELECT * FROM `status_reports` WHERE status_reports.status_id=?',
            [req.body.statusId], function (error) {
              if (error) {
                callback(error)
              } else {
                callback(null)
              }
            })
        },
      ],
      function (err) {
        if (err) {
          console.log(err)
          res.status(500).send(err)
        }
        res.sendStatus(200)
      })
  })
  app.post('/pardonStatus', function (req, res) {
    waterfall([
        function (callback) {
          connection.query(
            'SELECT * FROM `status_reports` WHERE status_reports.status_id=?',
            [req.body.statusId], function (error) {
              if (error) {
                callback(error)
              } else {
                callback(null)
              }
            })
        },
      ],
      function (err) {
        if (err) {
          console.log(err)
          res.status(500).send(err)
        }
        res.sendStatus(200)
      })
  })
  app.post('/banUser', function (req, res) {
    waterfall([
        function (callback) {
          connection.query(
            'UPDATE users set users.is_banned=1 WHERE users.id=?',
            [req.body.userId], function (error) {
              if (error) {
                callback(error)
              } else {
                callback(null)
              }
            })
        },
      ],
      function (err) {
        if (err) {
          console.log(err)
          res.status(500).send(err)
        }
        res.sendStatus(200)
      })
  })//TODO
  app.post('/pardonUser', function (req, res) {
    waterfall([
        function (callback) {
          connection.query(
            'SELECT * FROM `user_reports` WHERE user_reports.userThatGotReported=?',
            [req.body.userId], function (error) {
              if (error) {
                callback(error)
              } else {
                callback(null)
              }
            })
        },
      ],
      function (err) {
        if (err) {
          console.log(err)
          res.status(500).send(err)
        }
        res.sendStatus(200)
      })
  })

  app.get('/swagger.json', function (req, res) {
    res.setHeader('Content-Type', 'application/json')
    res.send(swaggerSpec)
  })
}

function isLoggedIn (req, res, next) {
  if (req.isAuthenticated())
    return next()
  res.redirect('/')
}

function filterDistance (array, distance, longitude, latitude) {
  var EndUsers = []
  array.forEach(function (user) {
    var km = calcCrow(user.latitude, user.longitude, latitude, longitude)
    if (km <= distance) {
      EndUsers.push({user: user, km: km, city: user.city})
    }
  })
  return EndUsers
}

function calcCrow (lat1, lon1, lat2, lon2) {
  var R = 6371 // km
  var dLat = toRad(lat2 - lat1)
  var dLon = toRad(lon2 - lon1)
  var lat1 = toRad(lat1)
  var lat2 = toRad(lat2)

  var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2)
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
  var d = R * c
  return d
}

function toRad (Value) {
  return Value * Math.PI / 180
}