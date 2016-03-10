var exec = require('cordova/exec');

module.exports = {
  show: function(url) {
    exec(null, null, "VideoPlayerPlugin", "show", [url]);
  },
  pause: function(url) {
    exec(null, null, "VideoPlayerPlugin", "pause", [url]);
  },
  resume: function(url) {
    exec(null, null, "VideoPlayerPlugin", "resume", [url]);
  },
};