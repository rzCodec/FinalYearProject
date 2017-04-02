var express = require('express');
var router = express.Router();
var request = require('request');
var async = require('async');
var app = express();
var bodyParser = require('body-parser');
app.set('view engine', 'ejs');
router.use(bodyParser.urlencoded({
    extended: true
}));
/* GET home page. */

module.exports = function () {
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
    router.get('/dashboard', function (req, res) {
        res.render('pages/UserDashboard/DashBoard.ejs',  function (err, html) {
            res.send(html);
        });
    });
    router.get('/settings', function (req, res) {
        res.render('pages/UserDashboard/Settings.ejs',  function (err, html) {
            res.send(html);
        });
    });
    router.get('/soundcloud', function (req, res) {
        res.render('pages/temporary/Soundcloud.ejs',  function (err, html) {
            res.send(html);
        });
    });
    return router;
};

app.use('/', router);