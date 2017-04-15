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

var sc = require("soundclouder");
//require('request-debug')(request);
var mysql = require('mysql');
var app = express();
app.use(compression());

var pool;


//Production();
Testing();

function Production() {
    pool  = mysql.createPool({
        connectionLimit : 10,
        host            : 'localhost',
        user            : 'root',
        password        : 'innes381!need4speed',
        database        : 'informatics',
        debug: false
    });
}
function Testing() {
    pool  = mysql.createPool({
        connectionLimit : 10,
        host            : 'localhost',
        user            : 'root',
        password        : 'innes381!need4speed',
        database        : 'informatics',
        debug: false
    });
}

var routes = require('./routes/index')(pool);
app.use('/', routes);

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
app.use(express.static(path.join(__dirname, 'public'), options));

app.use('/users', users);

module.exports = app;