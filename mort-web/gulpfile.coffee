gulp = require 'gulp'
gutil = require 'gulp-util'
webpack = require "webpack"
WebpackDevServer = require("webpack-dev-server")


map = require 'map-stream'
touch = require 'touch'
_ = require 'underscore'

# Load plugins
#$ = require('gulp-load-plugins')()

gulp.task "webpack:build", (callback) ->
	webpackProductionConfig = require("./webpack.production.config.js")
	webpack webpackProductionConfig, (err, stats) ->
		throw new gutil.PluginError("webpack:build", err)  if err
		gutil.log "[webpack:build]", stats.toString(colors: true)
		callback()

# gulp.task "server", ->
# 	process.env.PORT = 3456
# 	$.express.run 'app'

# Create a single instance of the compiler to allow caching.

gulp.task "webpack:build-dev", (callback) ->
	devCompiler = webpack(webpackConfig)
	devCompiler.run (err, stats) ->
		throw new gutil.PluginError("webpack:build-dev", err)  if err
		gutil.log "[webpack:build-dev]", stats.toString(colors: true)
		callback()

gulp.task "webpack-dev-server", (callback) ->
	webpackConfig = require("./webpack.config.js")
	devServer = new WebpackDevServer(webpack(webpackConfig),
		contentBase: './public/'
		hot: true
		watchOptions:
			aggregateTimeout: 100
		noInfo: true
	)
	devServer.listen 8000, "0.0.0.0", (err) ->
		throw new gutil.PluginError("webpack-dev-server", err) if err
		gutil.log "[webpack-dev-server]", "http://localhost:8000"
		callback()

gulp.task 'default', ->
	gulp.start 'build'

gulp.task 'build', ['webpack:build']

gulp.task 'watch', ['webpack-dev-server']
