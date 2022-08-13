function listSelectCB() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'cb_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.cb_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectZXB() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'zxb_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.zxb_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectST() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'st_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.st_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectST2() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'st2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.st2_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectJSPX() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'jiaoshi_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.jiaoshi_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectOther() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'other_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.other_all.checked;
				}
			}
		}
		return true;
	}
	function clickCB()
	{
		var cbObj;
		if(document.getElementById("CBS"))
		{
			cbObj = document.getElementById("CBS");
		}
		if(cbObj.checked){
			document.getElementById("cb_button").style.display="block";
		}else{
			document.getElementById("cb_button").style.display="none";
			document.getElementById('cnt1').style.display = 'none';
		}
	}
	function clickZXB()
	{
		var zxbObj;
		if(document.getElementById("ZXB"))
		{
			zxbObj = document.getElementById("ZXB");
		}
		if(zxbObj.checked){
			document.getElementById("zxb_button").style.display="block";
		}else{
			document.getElementById("zxb_button").style.display="none";
			document.getElementById('zxbDiv').style.display = 'none';
		}
	}
	
	function clickST()
	{
		var stObj;
		if(document.getElementById("ZXSTS"))
		{
			stObj = document.getElementById("ZXSTS");
		}
		if(stObj.checked){
			document.getElementById("st_button").style.display="block";
		}else{
			document.getElementById("st_button").style.display="none";
			document.getElementById('cnt2').style.display = 'none';
		}
	}
	function clickST2()
	{
		var qtstObj;
		if(document.getElementById("QTSTS"))
		{
			qtstObj = document.getElementById("QTSTS");
		}
		if(qtstObj.checked){
			document.getElementById("st2_button").style.display="block";
		}else{
			document.getElementById("st2_button").style.display="none";
			document.getElementById('cnt3').style.display = 'none';
		}
	}
	function clickJSPX()
	{
		var qtstObj;
		if(document.getElementById("JSPX"))
		{
			qtstObj = document.getElementById("JSPX");
		}
		if(qtstObj.checked){
			document.getElementById("jiaoshi_button").style.display="block";
		}else{
			document.getElementById("jiaoshi_button").style.display="none";
			document.getElementById('jsdiv').style.display = 'none';
		}
	}
	function clickOther()
	{
		var otherObj;
		if(document.getElementById("OTHER"))
		{
			otherObj = document.getElementById("OTHER");
		}
		if(otherObj.checked){
			document.getElementById("other_button").style.display="block";
		}else{
			document.getElementById("other_button").style.display="none";
			document.getElementById('otherDiv').style.display = 'none';
		}
		
	}
	function showDiv(objID){
		var tempObj;
		if(document.getElementById(objID)) {
			tempObj = document.getElementById(objID);
		}
		if(tempObj.style.display == 'none') {
			tempObj.style.display = 'block';
			checkdivs(objID);
		}
		else {
			tempObj.style.display = 'none';
		}
	}
	function checkdivs(objdiv){
		var a=new Array('cnt1', 'zxbDiv', 'cnt2', 'cnt3','otherDiv','jsdiv'); 
		
		for(var i=0;i<a.length;i++){
		var tempObj = document.getElementById(a[i]);
			if(a[i]!=objdiv){
				//alert(a[i]);
				tempObj.style.display = 'none';
			}
		}
	}
		function showDiv1(objID){
		var tempObj;
		if(document.getElementById(objID)) {
			tempObj = document.getElementById(objID);
		}
		if(tempObj.style.display == 'none') {
			tempObj.style.display = 'block';
			checkdivs1(objID);
		}
		else {
			tempObj.style.display = 'none';
		}
	}
	function checkdivs1(objdiv){
		var a=new Array('cnt1', 'zxbDiv', 'cnt2', 'cnt3','otherDiv','cbDiv2', 'zxbDiv2', 'zxstDiv2', 'qtstDiv2','otherDiv2','jsdiv','jsdiv2'); 
		
		for(var i=0;i<a.length;i++){
		var tempObj = document.getElementById(a[i]);
			if(a[i]!=objdiv){
				
				tempObj.style.display = 'none';
			}
		}
	}
	function theCheckedBoxes(){
		var a=new Array('cb_id', 'zxb_id', 'st_id', 'st2_id','other_id','jiaoshi_id'); 
		var person =document.getElementsByName("person");
		var n=0;
		for(var i=0;i<a.length;i++){
			if(person[i].checked){
				var plen =document.getElementsByName(a[i]);
				for(k=0;k<plen.length;k++){
					if(plen[k].checked){
					 n++;
					}
				}
			}
		}
		return n;
	}
	function theCheckedBoxes2(){
		var a=new Array('cb2_id', 'zxb2_id', 'zxst2_id', 'qtst2_id','other2_id','jiaoshi2_id'); 
		var person =document.getElementsByName("person2");
		var n=0;
		for(var i=0;i<a.length;i++){
			if(person[i].checked){
				var plen =document.getElementsByName(a[i]);
				for(k=0;k<plen.length;k++){
					if(plen[k].checked){
					 n++;
					}
				}
			}
		}
		return n;
	}
	function onloadObject(str){
		var person =document.getElementsByName("person");
		for(var n=0;n<person.length;n++ ){
			if(str.indexOf(person[n].value)>=0)
			person[n].checked = true;
		}		
		clickCB();
		clickZXB();
		clickST();
		clickST2();
		clickOther();
		clickJSPX();
		var cbs = window.document.getElementsByName("cb_id");
		for(var i=0;i<cbs.length;i++){
			if(str.indexOf(cbs[i].value)>=0){
				cbs[i].checked = true;
				}
		}
		var zxb = window.document.getElementsByName("zxb_id");
		for(var i=0;i<zxb.length;i++){
			if(str.indexOf(zxb[i].value)>=0){
				zxb[i].checked = true;
				}
		}
		var st = window.document.getElementsByName("st_id");
		for(var i=0;i<st.length;i++){
			if(str.indexOf(st[i].value)>=0){
				st[i].checked = true;
				}
		}
		var st2 = window.document.getElementsByName("st2_id");
		for(var i=0;i<st2.length;i++){
			if(str.indexOf(st2[i].value)>=0){
				st2[i].checked = true;
				}
		}
		var other = window.document.getElementsByName("other_id");
		for(var i=0;i<other.length;i++){
			if(str.indexOf(other[i].value)>=0){
				other[i].checked = true;
				}
		}
		var jspx = window.document.getElementsByName("jiaoshi_id");
		for(var i=0;i<jspx.length;i++){
			if(str.indexOf(jspx[i].value)>=0){
				jspx[i].checked = true;
				}
		}
	}
	
	function clickZXB2()
	{
		var zxbObj2;
		if(document.getElementById("ZXB2"))
		{
			zxbObj2 = document.getElementById("ZXB2");
		}
		if(zxbObj2.checked){
			document.getElementById("zxb2_button").style.display="block";
		}else{
			document.getElementById("zxb2_button").style.display="none";
			document.getElementById('zxbDiv2').style.display = 'none';
		}
	}
	function clickCB2()
	{
		var cbObj;
		if(document.getElementById("CBS2"))
		{
			cbObj = document.getElementById("CBS2");
		}
		if(cbObj.checked){
			document.getElementById("cb2_button").style.display="block";
		}else{
			document.getElementById("cb2_button").style.display="none";
			document.getElementById('cbDiv2').style.display = 'none';
		}
	}
	function clickZXST2()
	{
		var stObj;
		if(document.getElementById("ZXSTS2"))
		{
			stObj = document.getElementById("ZXSTS2");
		}
		if(stObj.checked){
			document.getElementById("zxst2_button").style.display="block";
		}else{
			document.getElementById("zxst2_button").style.display="none";
			document.getElementById('zxstDiv2').style.display = 'none';
		}
	}
	function clickQTST2()
	{
		var stObj;
		if(document.getElementById("QTSTS2"))
		{
			stObj = document.getElementById("QTSTS2");
		}
		if(stObj.checked){
			document.getElementById("qtst2_button").style.display="block";
		}else{
			document.getElementById("qtst2_button").style.display="none";
			document.getElementById('qtstDiv2').style.display = 'none';
		}
	}
	function clickOther2()
	{
		var otherObj;
		if(document.getElementById("OTHER2"))
		{
			otherObj = document.getElementById("OTHER2");
		}
		if(otherObj.checked){
			document.getElementById("other2_button").style.display="block";
		}else{
			document.getElementById("other2_button").style.display="none";
			document.getElementById('otherDiv2').style.display = 'none';
		}
		
	}
	function clickJSPX2()
	{
		var qtstObj;
		if(document.getElementById("JSPX2"))
		{
			qtstObj = document.getElementById("JSPX2");
		}
		if(qtstObj.checked){
			document.getElementById("jiaoshi2_button").style.display="block";
		}else{
			document.getElementById("jiaoshi2_button").style.display="none";
			document.getElementById('jsdiv2').style.display = 'none';
		}
	}
	function listSelectCB2() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'cb2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.cb2_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectZXB2() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'zxb2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.zxb2_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectZXST2() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'zxst2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.zxst2_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectQTST2() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'qtst2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.qtst2_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectOther2() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'other2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.other2_all.checked;
				}
			}
		}
		return true;
	}
	function listSelectJSPX2() {
		var form = document.forms['information'];
		for (var i = 0 ; i < form.elements.length; i++) {
			if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'jiaoshi2_id')) {
				if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
					form.elements[i].checked = form.jiaoshi2_all.checked;
				}
			}
		}
		return true;
	}
	function onloadObject2(str2){
		var person2 =document.getElementsByName("person2");
			for(var m=0;m<person2.length;m++ ){
				if(str2.indexOf(person2[m].value)>=0)
				person2[m].checked = true;
			}		
			clickCB2();
			clickZXB2();
			clickZXST2();
			clickQTST2();
			clickJSPX2();
			var cbs2 = window.document.getElementsByName("cb2_id");
			for(var i=0;i<cbs2.length;i++){
				if(str2.indexOf(cbs2[i].value)>=0){
					cbs2[i].checked = true;
					}
			}
			var zxb2 = window.document.getElementsByName("zxb2_id");
			for(var i=0;i<zxb2.length;i++){
				if(str2.indexOf(zxb2[i].value)>=0){
					zxb2[i].checked = true;
					}
			}
			var zxst2 = window.document.getElementsByName("zxst2_id");
			for(var i=0;i<zxst2.length;i++){
				if(str2.indexOf(zxst2[i].value)>=0){
					zxst2[i].checked = true;
					}
			}
			var qtst2 = window.document.getElementsByName("qtst2_id");
			for(var i=0;i<qtst2.length;i++){
				if(str2.indexOf(qtst2[i].value)>=0){
					qtst2[i].checked = true;
					}
			}	
			var jspx2 = window.document.getElementsByName("jiaoshi2_id");
			for(var i=0;i<jspx2.length;i++){
				if(str2.indexOf(jspx2[i].value)>=0){
					jspx2[i].checked = true;
					}
			}		
		}
	