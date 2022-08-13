function changeIcon(e) {
	if (e.attr('class') == 'o') {
		e.attr('class', 'c');
	} else {
		e.attr('class', 'o');
	}
}