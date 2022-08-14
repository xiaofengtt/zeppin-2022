function $() {
	var elements = new Array();
	for (var i = 0; i < arguments.length; i = i + 1) {
		var element = arguments[i];
		if (typeof element == "string") {
			element = document.getElementById(element);
		}
		if (arguments.length == 1) {
			return element;
		}
		elements.push(element);
	}
	return elements;
}
var ZebraTable = {
	bgcolor : "",
	classname : "",
	render:function (el) {
		var elements = $(el);
		if (!elements) {
			return;
		}
		var rows = elements.getElementsByTagName("tr");
		for (var i = 1, len = rows.length; i < len; i = i + 1) {
			if (i % 2 === 0) {
				rows[i].className = "alt";
			}
		}
	}
};