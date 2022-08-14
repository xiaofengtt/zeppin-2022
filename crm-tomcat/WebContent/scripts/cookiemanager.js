// cookiemanager.js
var CookieManager = function () {
	return {
		setCookie:function (key, value) {
			var days = 30;
			var exp = new Date();
			exp.setTime(exp.getTime() + days * 24 * 60 * 60 * 1000);
			document.cookie = key + "=" + escape(value) + ";expires=" + exp.toGMTString();
		}
		, getCookie:function (key) {
			var arr = document.cookie.match(new RegExp("(^| )" + key + "=([^;]*)(;|$)"));
			if (arr !== null) {
				return unescape(arr[2]);
			}
			return null;
		}
		, delCookie:function (key) {
			var exp = new Date();
			exp.setTime(exp.getTime() - 1);
			var cval = this.getCookie(key);
			if (cval !== null) {
				document.cookie = key + "=" + cval + ";expires=" + exp.toGMTString();
			}
		}
	};
}();