
function getCookieVal(offset) {
	var endstr = document.cookie.indexOf(";", offset);
	if (endstr == -1) {
		endstr = document.cookie.length;
	}
	return unescape(document.cookie.substring(offset, endstr));
}
function getCookie(name) {
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	while (i < clen) {
		var j = i + alen;
		if (document.cookie.substring(i, j) == arg) {
			return getCookieVal(j);
		}
		i = document.cookie.indexOf(" ", i) + 1;
		if (i == 0) {
			break;
		}
	}
	return 0;
}
function setCookie(name, value) {
	var argv = setCookie.arguments;
	var argc = setCookie.arguments.length;
	var expires = (2 < argc) ? argv[2] : null;
	var path = (3 < argc) ? argv[3] : null;
	var domain = (4 < argc) ? argv[4] : null;
	var secure = (5 < argc) ? argv[5] : null;
	document.cookie = name + "=" + escape(value) + ((expires == null) ? " " : (";expires =" + expires.toGMTString())) + ((path == null) ? "  " : (";path = " + path)) + ((domain == null) ? " " : (";domain =" + domain)) + ((secure == true) ? ";secure" : " ");
}

