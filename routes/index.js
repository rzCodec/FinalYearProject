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



module.exports = function (pool) {
    router.get('/', function (req, res) {
        res.render('pages/HomePage/Landing.ejs',  function (err, html) {
            res.send(html);
        });
    });

    router.get('/SoundAuthen', function (req, res) {
        res.render('Pages/temporary/callback.ejs', { name: "sad" }, function(err, html) {
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
        res.render('pages/temporary/userInfo.ejs',  function (err, html) {
            res.send(html);
        });
    });
    router.get('/addStudio', function (req, res) {
        res.render('pages/AdminDashboard/AddStudio.ejs',  function (err, html) {
            res.send(html);
        });
    });

    router.get('/getUserInfo/:userID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/setUserLocation/:userID/:latitude/:longitude', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/clearUserLocation/:userID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/setUserGenre/:userID/:genreName', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/setSong/:userID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//SongUrl in body
    router.get('/setSearchDistance/:userID/:latitude/:longitude', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/flagUser/:ActiveUser/:UserToFlagID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/followUser/:userID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });

    router.get('/getStatus/:userID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });
    router.get('/setStatus/:userID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//req.query.Status And req.query.ExtraInfo
    router.get('/flagStatus/:statusID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });
    router.get('/likeStatus/:statusID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });


    router.get('/CreateChat/:UserID1/:UserID2', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });
    router.get('/sendMessage/:userID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//And UserID to send to
    router.get('/getMessages/:chatID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/getChats/:userID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID='+ req.params.userID, function (error, results) {
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