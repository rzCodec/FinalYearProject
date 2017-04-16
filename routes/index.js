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
        pool.query('UPDATE users SET longitude=?, latitude=? WHERE userID=?',[req.params.longitude,req.params.latitude,req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/clearUserLocation/:userID', function (req,res) {
        pool.query('UPDATE users SET longitude=NULL, latitude=NULL  WHERE userID=?',[req.params.userID], function (error, results) {
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
        pool.query('UPDATE users SET genreID=?  WHERE userID=?',[req.params.genreID,req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.post('/setSong/:userID', function (req,res) {
        pool.query('UPDATE users SET songLink=?  WHERE userID=?',[req.body.songLink,req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//SongUrl in body
    router.get('/setSearchDistance/:userID/:distance', function (req,res) {
        pool.query('UPDATE users SET searchDistance=?  WHERE userID=?',[req.params.distance,req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//reports
    router.get('/flagUser/:userToFlagID', function (req,res) {
        pool.query('UPDATE users SET reports=(reports+1)  WHERE userID=?',[req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/followUser/:activeuserID/:userIDToFollow', function (req,res) {
        pool.query('INSERT INTO `followers` (`userID`, `likedID`, `timeStamp`) VALUES (?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))',[req.params.activeuserID,req.params.userIDToFollow], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });

    router.get('/getStatuses/:userID', function (req,res) {
        pool.query('SELECT * FROM `status list` WHERE userID=?',[req.params.userID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });
    router.post('/setStatus/:userID', function (req,res) {
        pool.query('INSERT INTO `status list` (`userID`, `timestamp`, `status`, `extraInfo`) VALUES (?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000), ?, ?)',[req.params.userID,req.body.status,req.body.extraInfo], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//req.query.Status And req.query.ExtraInfo
    router.get('/flagStatus/:statusID', function (req,res) {
        pool.query('UPDATE `status list` SET flagged=(flagged+1)  WHERE statusID=?',[req.params.statusID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });
    router.get('/likeStatus/:statusID', function (req,res) {
        pool.query('UPDATE `status list` SET liked=(liked+1)  WHERE statusID=?',[req.params.statusID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });

    router.get('/CreateChat/:UserID1/:UserID2', function (req,res) {
        pool.query('INSERT INTO `chats` (`user1ID`, `user2ID`, `startTimeStamp`) VALUES (?, ?, ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000))',[req.params.UserID1,req.params.UserID2], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });
    router.post('/sendMessage/:chatID/:userID', function (req,res) {
        pool.query('INSERT INTO `message list` (`message`, `timeStamp`, `isRead`, `senderID`, `chatID`) VALUES (?,  ROUND(UNIX_TIMESTAMP(CURTIME(4)) * 1000), 0, ?, ?)',[req.body.message,req.params.userID,req.params.chatID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//And UserID to send to
    router.get('/getMessages/:chatID', function (req,res) {
        pool.query('SELECT * FROM `message list` WHERE chatID=? ORDER BY timeStamp ASC',[req.params.chatID], function (error, results) {
            if(error){
                console.log(error)
            }else{
                res.send(results);
            }
        });
    });//
    router.get('/getChats/:userID', function (req,res) {
        pool.query('SELECT * FROM `chats` WHERE user1ID=? OR user2ID=? ORDER BY startTimeStamp ASC',[req.params.userID,req.params.userID], function (error, results) {
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