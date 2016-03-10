var exec = require('cordova/exec');

module.exports = {
  show: function(url, options) {
    options = options || {};

    var rect = options.rect || null;
    var callback = options.callback || null;
    exec(callback, null, "VideoPlayerPlugin", "show", [url, rect]);
  },
  pause: function(url) {
    exec(null, null, "VideoPlayerPlugin", "pause", [url]);
  },
  resume: function(url) {
    exec(null, null, "VideoPlayerPlugin", "resume", [url]);
  },
  destory: function(url) {
    exec(null, null, "VideoPlayerPlugin", "destory", [url]);
  },
};