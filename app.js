var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var users = require('./routes/users');

var async = require('async');
var mysql = require('mysql');
var request = require('request');
var http = require('http');
var fs = require('fs');

var app = express();

var pool;

//Production();
Testing();

function Production() {
    db = monk('Djinnes:need4speed@10.129.24.135:23456/TimeLineJSON');
    pool = mysql.createPool({
        connectionLimit: 100, //important
        host: '10.129.24.135',
        user: 'david',
        password: 'Innes381!need4speed',
        database: 'proplayers',
        debug: false
    });
    poolError = mysql.createPool({
        connectionLimit: 100, //important
        host: '10.129.24.135',
        user: 'david',
        password: 'Innes381!need4speed',
        database: 'errors',
        debug: false
    });
}
function Testing() {

    pool = mysql.createPool({
        connectionLimit: 1, //important
        host: 'localhost',
        user: 'root',
        password: 'innes381!need4speed',
        database: 'informatics',
        debug: false
    });

}
var routes = require('./routes/index')(pool);
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));
app.use(cookieParser());
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
app.use('/', routes);
app.use('/users', users);

module.exports = app;

