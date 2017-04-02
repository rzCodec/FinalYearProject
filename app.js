var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var bodyParser = require('body-parser');
var users = require('./routes/users');
var async = require('async');
var request = require('request');
var http = require('http');
var compression = require('compression');
var monk = require('monk');
//require('request-debug')(request);
var app = express();
app.use(compression());

var DB;


//Production();
//Testing();

function Production() {
    MongoDB = monk('TeamOverClock:password@10.129.24.135:23456/Users', function (err) {
        if (!err) {
            console.log('Connected correctly to server');
        } else {
            console.log(err)
        }
    });
}
function Testing() {
    MongoDB = monk('TeamOverClock:password@localhost/Users', function (err) {
        if (!err) {
            console.log('Connected correctly to server');
        } else {
            console.log(err)
        }
    });
}

var routes = require('./routes/index')();
app.use('/', routes);

//<editor-fold desc="Description">
// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
app.use(favicon(path.join(__dirname, 'public', 'favicon/favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
var options = {
    dotfiles: 'ignore',
    etag: false,
    extensions: ['htm', 'html'],
    index: false,
    maxAge: '2w',
    redirect: false,
    setHeaders: function (res, path, stat) {
        res.set('x-timestamp', Date.now())
    }
};
app.use(express.static(path.join(__dirname, 'public'),options));

app.use('/users', users);

module.exports = app;