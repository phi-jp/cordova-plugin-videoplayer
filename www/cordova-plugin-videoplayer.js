var exec = require('cordova/exec');

module.exports = {
  show: function(url) {
    exec(null, null, "VideoPlayerPlugin", "show", [url]);
  },
};