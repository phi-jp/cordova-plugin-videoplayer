var exec = require('cordova/exec');

module.exports = {
  show: function(url, rect) {
    rect = rect || null;
    exec(null, null, "VideoPlayerPlugin", "show", [url, rect]);
  },
  pause: function(url) {
    exec(null, null, "VideoPlayerPlugin", "pause", [url]);
  },
  resume: function(url) {
    exec(null, null, "VideoPlayerPlugin", "resume", [url]);
  },
};