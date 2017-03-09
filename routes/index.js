var express = require('express');
var async = require('async');
var router = express.Router();
var request = require('request');
/* GET home page. */
var app = express();
var bodyParser = require('body-parser');
app.set('view engine', 'ejs');

router.use(bodyParser.urlencoded({
    extended: true
}));

module.exports = function (pool) {
    router.get('/', function (req, res) {
        res.render('pages/Login/login', {}, function (err, html) {
            if (!err) {
                res.send(html);
            } else {
                console.log(err)
                res.render('pages/Error/error', {error: err});
            }
        });
    });
    router.get('/LoginAuthenticate', function (req, res) {
        var Username = req.query['Username'];
        var Password = req.query['Password'];
        var queryString = "Select ID WHERE Email = ? AND Password = ?";
        pool.query(queryString, [Username, Password], function (err, rows) {
            if (err) {
                console.log(err)
                res.send('{"error" : "Failed Update", "status" : 500}');
            } else {
                if (rows.length > 0) {
                    res.send('{"success" : "Updated Successfully", "status" : 200}');
                }
                else {
                    res.send('{"error" : "Failed Update", "status" : 500}');
                }
            }
        });
    });
    router.get('/Register', function (req, res) {
        var Genres = [];

        var matchString = 'SELECT Name FROM genre';

        pool.query(matchString, function (err, rows) {
            if (err) {
                console.log("Error connecting to matchhistory");
                throw err;
            }
            if (!err) {
                console.log(rows)
                Genres = rows;
                res.render('pages/Register/register', {
                    Genres: Genres
                }, function (err, html) {
                    console.log(err)
                    res.send(html);
                });
            }
        });
    });
    router.get('/DashBoard', function (req, res) {
        res.render('pages/DashBoard/dashboard', {}, function (err, html) {
            if (!err) {
                res.send(html);
            } else {
                console.log(err)
                res.render('pages/Error/error', {error: err});
            }
        });
    });
    router.get('*', function (req, res, next) {
        var err = new Error();
        err.status = 404;
        next(err);
    });

    router.use(function (err, req, res, next) {

    });

    return router;
};

app.use('/', router);



