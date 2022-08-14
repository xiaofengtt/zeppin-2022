
function MoveSingleItem(sel_source, sel_dest) {
	if (sel_source.selectedIndex == -1) {
		return;
	}
	var SelectedText = sel_source.options[sel_source.selectedIndex].text;
	var SelectedVaule = sel_source.options[sel_source.selectedIndex].value;
	sel_dest.options.add(new Option(SelectedText,SelectedVaule));
	sel_source.options.remove(sel_source.selectedIndex);
}
function MoveAllItems(sel_source, sel_dest) {
	var sel_source_len = sel_source.length;
	for (var j = 0; j < sel_source_len; j++) {
		var SelectedText = sel_source.options[j].text;
		var SelectedValue = sel_source.options[j].value;
		sel_dest.options.add(new Option(SelectedText,SelectedValue));
	}
	while ((k = sel_source.length - 1) >= 0) {
		sel_source.options.remove(k);
	}
}

