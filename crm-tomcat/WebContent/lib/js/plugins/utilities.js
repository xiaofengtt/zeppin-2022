//jquery Cookie
(function($, document) {
    var pluses = /\+/g;
    function raw(s) {return s;}
    function decoded(s) {return decodeURIComponent(s.replace(pluses, ' '));}
    $.cookie = function(key, value, options) {
        // key and at least value given, set cookie...
        if (arguments.length > 1 && (!/Object/.test(Object.prototype.toString.call(value)) || value == null)) {
            options = $.extend({}, $.cookie.defaults, options);
            if (value == null) {options.expires = -1;}
            if (typeof options.expires === 'number') {var days = options.expires, t = options.expires = new Date();
                t.setDate(t.getDate() + days);}
            value = String(value);
            return (document.cookie = [
                encodeURIComponent(key), '=', options.raw ? value : encodeURIComponent(value),
                options.expires ? '; expires=' + options.expires.toUTCString() : '', // use expires attribute, max-age is not supported by IE
                options.path    ? '; path=' + options.path : '',
                options.domain  ? '; domain=' + options.domain : '',
                options.secure  ? '; secure' : ''
            ].join(''));
        }
        options = value || $.cookie.defaults || {};
        var decode = options.raw ? raw : decoded;
        var cookies = document.cookie.split('; ');
        for (var i = 0, parts; (parts = cookies[i] && cookies[i].split('=')); i++) {
            if (decode(parts.shift()) === key) {return decode(parts.join('='));}}
        return null;};
    $.cookie.defaults = {};
})(jQuery,document);

/*
(function($,document){
   $.timer = {
    data:   {}
,   set:    function (s, fn, ms) { this.clear(s); this.data[s] = setTimeout(fn, ms); }
,   clear:  function (s) { if (this.data[s]) {clearTimeout(this.data[s]); delete this.data[s];} }
}
})(jQuery,document);
*/

(function($){
    $.ms=function(ms){
        if($("html").hasClass("IE6")||$("html").hasClass("IE7")||$("html").hasClass("IE8"))
        {return 0;}else{return ms;}
    }
})(jQuery);
