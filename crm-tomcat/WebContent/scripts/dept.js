var submitFlg = false;
function submitDept() {
	if (document.all.dept != null) {
		var i = document.all.dept.selectedIndex;
		if (i >= 0) {
			document.all.deptID.value = document.all.dept.options[i].id;
		} else {
			document.all.deptID.value = "";
		}
		if (!submitFlg) {
			document.all.actForm.submit();
			submitFlg = true;
		}
	}
}

function saveDept(linkValue) {
	if (document.all.dept != null) {
		var i = document.all.dept.selectedIndex;
		if (i >= 0) {
			document.all.deptID.value = document.all.dept.options[i].id;
		} else {
			document.all.deptID.value = "";
		}
	}
	submitButton(linkValue);
}

function saveAll() {
	saveDept("SAVE");
}

function submitDeptID(deptElement, deptArrayElement, linkValue) {
	var dept = document.all(deptElement);
	var deptAry = document.all(deptArrayElement);
	if (dept == null || deptAry == null) return false;
	var i = deptAry.selectedIndex;
	if (i >= 0) dept.value = deptAry.options[i].id;
	submitButton(linkValue);
}

