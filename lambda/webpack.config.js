const { ESBuildMinifyPlugin } = require('esbuild-loader')
const config = require('./scalajs.webpack.config');

config.mode = "production";
config.output.libraryTarget = 'commonjs2';
config.target = 'node';
config.module = {
  rules: [{
    test: /\.js$/,
    loader: 'esbuild-loader',
    options: {
      target: 'es2020'
    }
  }]
};
config.optimization = {
  minimizer: [
    new ESBuildMinifyPlugin({
      target: 'es2020'
    })
  ]
};

module.exports = config;