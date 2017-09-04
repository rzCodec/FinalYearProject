var LocalStrategy = require('passport-local').Strategy
var mysql = require('mysql')
var bcrypt = require('bcrypt-nodejs')
var dbconfig = require('./database')
var connection = mysql.createConnection(dbconfig.connection)
var async = require('async')
connection.query('USE ' + dbconfig.database)

var identicon = require('identicon')
var fs = require('fs')

module.exports = function (passport) {
  passport.serializeUser(function (user, done) {
    done(null, user.id)
  })
  passport.deserializeUser(function (id, done) {
    connection.query('SELECT * FROM users WHERE id = ? AND users.is_banned != 1', [id],
      function (err, rows) {
        done(err, rows[0])
      })
  })

  passport.use(
    'local-signup',
    new LocalStrategy({
        usernameField: 'username',
        passwordField: 'password',
        passReqToCallback: true,
      },
      function (req, username, password, done) {
        connection.query('SELECT * FROM users WHERE username = ? AND users.is_banned != 1', [username],
          function (err, rows) {
            if (err)
              return done(err)
            if (rows.length) {
              return done(null, false,
                req.flash('signupMessage', 'That username is already taken.'))
            } else {
              var newUserMysql = {
                username: req.param('realUserName'),
                password: bcrypt.hashSync(password, null, null),
                email: username,
                firstname: req.param('firstname'),
                surname: req.param('surname'),
                genre_id: req.param('genre_id'),
                distance_id: req.param('distance_id'),
                description: req.param('description'),
                skill_ids: req.param('skills'),
              }

              var insertQuery = 'INSERT INTO users ( username, password, email, firstname, surname, genre_id, distance_id, description,join_timestamp,last_login_timestamp ) values (?,?,?,?,?,?,?,?,ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))'

              connection.query(insertQuery, [
                newUserMysql.username,
                newUserMysql.password,
                newUserMysql.email,
                newUserMysql.firstname,
                newUserMysql.surname,
                newUserMysql.genre_id,
                newUserMysql.distance_id,
                newUserMysql.description,
              ], function (err, rows) {
                if (err) {
                  return done(err)
                }
                newUserMysql.id = rows.insertId

                identicon.generate({id: rows.insertId.toString(), size: 150},
                  function (err, buffer) {
                    if (err) throw err

                    fs.writeFileSync(__dirname +
                      '/../public/images/profilePics/' +
                      rows.insertId + '.png', buffer)
                  })

                async.map(newUserMysql.skill_ids, function (id, cb) {
                  connection.query(
                    'INSERT INTO `user_skills` (`id`, `user_id`, `skill_id`) VALUES (NULL, ?, ?)',
                    [newUserMysql.id, id], function (error) {
                      if (error) {
                        cb(error)
                      } else {
                        console.log(error)
                        cb()
                      }
                    })
                }, function (err) {
                  if (err) {
                    console.log(err)
                    return done(err)
                  } else {
                    return done(null, newUserMysql)
                  }
                })
              })
            }
          })
      })
  )

  passport.use(
    'local-login',
    new LocalStrategy({
        usernameField: 'username',
        passwordField: 'password',
        passReqToCallback: true,
      },
      function (req, username, password, done) {
        connection.query('SELECT * FROM informatics.users WHERE email= ? AND users.is_banned != 1',
          [username], function (err, rows) {
            if (err)
              return done(err)
            if (!rows.length) {
              return done(null, false,
                req.flash('loginMessage', 'No user found.'))
            }

            if (!bcrypt.compareSync(password, rows[0].password))
              return done(null, false,
                req.flash('loginMessage', 'Oops! Wrong password.'))
            return done(null, rows[0])
          })
      })
  )
}