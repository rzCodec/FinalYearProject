var LocalStrategy = require('passport-local').Strategy;
var mysql = require('mysql');
var bcrypt = require('bcrypt-nodejs');
var dbconfig = require('./database');
var connection = mysql.createConnection(dbconfig.connection);

connection.query('USE ' + dbconfig.database);
module.exports = function (passport) {
  passport.serializeUser(function (user, done) {
    done(null, user.id);
  });
  passport.deserializeUser(function (id, done) {
    connection.query("SELECT * FROM users WHERE id = ? ", [id], function (err, rows) {
      done(err, rows[0]);
    });
  });

  passport.use(
    'local-signup',
    new LocalStrategy({
        usernameField: 'username',
        passwordField: 'password',
        passReqToCallback: true
      },
      function (req, username, password, done) {
        connection.query("SELECT * FROM users WHERE username = ?", [username], function (err, rows) {
          if (err)
            return done(err);
          if (rows.length) {
            return done(null, false, req.flash('signupMessage', 'That username is already taken.'));
          } else {
            var newUserMysql = {
              username: req.param('realUserName'),
              password: bcrypt.hashSync(password, null, null),
              email: username,
              firstname: req.param('firstname'),
              surname: req.param('surname'),
              genre_id: req.param('genre'),
              distance_id: req.param('distance'),
              description: req.param('description')
            };

            var insertQuery = "INSERT INTO users ( username, password, email, firstname, surname, genre_id, distance_id, description,join_timestamp,last_login_timestamp ) values (?,?,?,?,?,?,?,?,ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000),ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))";

            connection.query(insertQuery, [newUserMysql.username, newUserMysql.password, newUserMysql.email, newUserMysql.firstname, newUserMysql.surname, newUserMysql.genre_id, newUserMysql.distance_id, newUserMysql.description], function (err, rows) {
              console.log(err)
              newUserMysql.id = rows.insertId;
              return done(null, newUserMysql);
            });
          }
        });
      })
  );

  passport.use(
    'local-login',
    new LocalStrategy({
        usernameField: 'username',
        passwordField: 'password',
        passReqToCallback: true
      },
      function (req, username, password, done) {
        connection.query("SELECT * FROM informatics.users WHERE email= ? ", [username], function (err, rows) {
          if (err)
            return done(err);
          if (!rows.length) {
            return done(null, false, req.flash('loginMessage', 'No user found.'));
          }

          if (!bcrypt.compareSync(password, rows[0].password))
            return done(null, false, req.flash('loginMessage', 'Oops! Wrong password.'));
          return done(null, rows[0]);
        });
      })
  );
};