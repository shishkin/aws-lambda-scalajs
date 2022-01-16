const config = require('./scalajs.webpack.config');
config.mode = "production";
config.output.libraryTarget = 'commonjs2';
config.target = 'node';
config.module = {
  rules: [{
    test: /\.js$/,
    loader: 'esbuild-loader',
    options: {
      loader: 'jsx',  // Remove this if you're not using JSX
      target: 'es2015'  // Syntax to compile to (see options below for possible values)
    }
  }]
};
module.exports = config;