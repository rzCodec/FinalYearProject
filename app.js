var express = require('express')
var session = require('express-session')
var cookieParser = require('cookie-parser')
var morgan = require('morgan')
var path = require('path')
var favicon = require('serve-favicon')
var logger = require('morgan')
var bodyParser = require('body-parser')
var async = require('async')
var request = require('request')
var http = require('http')
var compression = require('compression')
var passport = require('passport')
var flash = require('connect-flash')
var fs = require('fs');

require('./config/passport')(passport)
//require('request-debug')(request);
var mysql = require('mysql')
var app = express()

app.use(compression())

const swaggerJSDoc = require('swagger-jsdoc')
var swaggerDefinition = {
  info: {
    title: 'Node Swagger API',
    version: '1.0.0',
    description: 'Demonstrating how to describe a RESTful API with Swagger',
  },
  host: 'eternalvibes.me',
  basePath: '/',
  schemes: 'https',
}
var options = {
  swaggerDefinition: swaggerDefinition,
  apis: ['./routes/index.js'],
}
var swaggerSpec = swaggerJSDoc(options)

var options2 = {
  dotfiles: 'ignore',
  etag: false,
  extensions: ['htm', 'html'],
  index: false,
  maxAge: '2w',
  redirect: false,
  setHeaders: function (res, path, stat) {
    res.set('x-timestamp', Date.now())
  },
}

app.use(morgan('dev'))
app.use(favicon(path.join(__dirname, 'public', 'favicon/favicon.ico')))
app.use(cookieParser())
app.use(bodyParser.urlencoded({
  extended: true,
}))
app.use(express.static(path.join(__dirname, 'public'), options2))
app.use(bodyParser.json())
app.set('view engine', 'ejs')
app.set('views', path.join(__dirname, 'views'))
app.use(session({
  secret: 'vidyapathaisalwaysrunning',
  resave: true,
  saveUninitialized: true,
}))
app.use(passport.initialize())
app.use(passport.session())
app.use(flash())

require('./routes/index.js')(app, passport, swaggerSpec)
var identicon = require('identicon')
var fs = require('fs')
identicon.generate({id: "test", size: 150},
  function (err, buffer) {
    if (err) throw err

    fs.writeFileSync(__dirname +
      '/public/images/profilePics/test.png', buffer)
  })

module.exports = app