var path = require('path');
var webpack = require('webpack');

module.exports = {
	entry: [
		"webpack-dev-server/client?http://0.0.0.0:8000",
		'webpack/hot/only-dev-server',
		'./launcher.coffee'
	],
	devtool: "source-map",
	debug: true,
	output: {
		publicPath: 'http://0.0.0.0:8000/',
		path: path.join(__dirname, "public"),
		filename: '[name].bundle.js'
	},
	resolveLoader: {
		modulesDirectories: ['node_modules']
	},
	plugins: [
		new webpack.DefinePlugin({
			// This has effect on the react lib size.
			"process.env": {
				NODE_ENV: JSON.stringify("development")
			}
		}),
		new webpack.HotModuleReplacementPlugin(),
		new webpack.NoErrorsPlugin(),
		new webpack.IgnorePlugin(/vertx/) // https://github.com/webpack/webpack/issues/353
	],
	node: {
		__filename: true
	},
	resolve: {
		extensions: ['', '.js', '.coffee', '.cjsx']
	},
	module: {
		loaders: [{
			test: /\.css$/,
			loaders: ['style', 'css']
		}, {
			test: /\.styl$/,
			loaders: ['style', 'css', 'stylus?import=' + require.resolve('nib/index.styl')]
		}, {
			test: /\.woff(\?v=[0-9]\.[0-9]\.[0-9])?$/,
			loader: "url-loader?limit=10000&minetype=application/font-woff"
		}, {
			test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/,
			loader: "file-loader"
		}, {
			test: /\.jade$/,
			loader: "jade-react-compiler-loader"
		}, {
			test: /\.jpg$/,
			loader: "url?limit=1000000&minetype=image/jpg"
		}, {
			test: /\.png$/,
			loader: "url?limit=1000000&minetype=image/png"
		}, {
			test: /\.jsx?$/,
			loaders: ['react-hot', 'jsx?harmony']
		}, {
			test: /\.cjsx$/,
			loaders: ['react-hot', 'jsx', 'iced-coffee-loader', 'cjsx']
		}, {
			test: /\.coffee$/,
			loaders: ['react-hot', 'iced-coffee-loader']
		}, {
			test: /\.csv$/,
			loader: 'dsv-loader'
		}, {
			test: /\.json$/,
			loader: 'json-loader'
		}, {
			test: /snapsvg/,
			loader: 'imports-loader?this=>window,fix=>module.exports=0'
		}]
	}
};