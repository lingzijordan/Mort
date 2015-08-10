express = require 'express'
bodyParser = require 'body-parser'
cookieParser = require 'cookie-parser'
session = require 'express-session'
compress = require 'compression'
path = require 'path'
morgan = require 'morgan'
fs = require 'fs'
dsv = require 'dsv'

app = module.exports.app = express()

app.use bodyParser.json extended:true
app.use bodyParser.urlencoded extended:true
app.use cookieParser()
app.use session
	httpOnly: false
	secret: 'bigeye'
	resave: true
	saveUninitialized: true

app.set 'views', path.join(__dirname, 'views')
app.set 'view engine', 'jade'
app.use compress()

app.use(morgan(':remote-addr - :remote-user [:date[clf]] ":method :url HTTP/:http-version" :status :res[content-length]'))
app.use('/public', express.static(__dirname + '/public'))
app.listen process.env.PORT
console.log "server listening on port:#{process.env.PORT}"

app.get '/', (req, res)->
	res.render 'index'