function onLoad() {
	init();
	menu();
	loadIFrameContent();
}

function submitButton(linkType) {
	if (document.all.actForm.functionName != null) {
		document.all.actForm.functionName.value = linkType;
	}
	saveFrame();
	document.all.actForm.submit();
}

function saveFrame() {
	if (document.HTMLEditor != null) save();
	if (document.all("TANGER_OCX") != null) savedoc();
}

function checkAll() {
	var count = document.all.checkBox.length;
	if (count > 0) {
		if (document.all.selectAll.checked) {
			for (var i = 0; i < count; i++) {
				document.all.checkBox[i].checked = true;
			}
		} else {
			for (var i = 0; i < count; i++) {
				document.all.checkBox[i].checked = false;
			}
		}
	}
}

lastclickid = 0;
function content_onclick(obj)
{
        if (lastclickid %2 == 0) {
	        eval("d" +lastclickid).className = "TableDetail2";
        } else {
	        eval("d" +lastclickid).className = "TableDetail1";
        }
        num = parseInt(lastclickid) + 1;
        document.actForm.checkBox[num].checked = false;
        obj.className = "selectLine";
	currentIndex = obj.id.substring(obj.id.indexOf("d")+1,obj.id.length);
	num = parseInt(currentIndex) + 1;
        document.actForm.checkBox[num].checked = true;
        lastclickid=currentIndex;
}

function loadIFrameContent() {
	if (document.all.frameSection == null) return;
	if (document.all.frmValue == null) return;
	var obj = document.frames.frameSection.document;
	obj.open();
	obj.write(document.all.frmValue.value);
}
