global.console ?= {}
for method in ['log', 'debug', 'info', 'warn', 'error']
	global.console[method] ?= ->
		
require 'pace/themes/orange/pace-theme-minimal.css'
require('pace/pace.coffee').start()

require './app/app.coffee'