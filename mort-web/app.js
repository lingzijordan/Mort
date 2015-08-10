// so that we can require *.coffee
require('iced-coffee-script').register();

process.env.PORT = process.env.PORT || 80;

// start server
module.exports = require('./server')