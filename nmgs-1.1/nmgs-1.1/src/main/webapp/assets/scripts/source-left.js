var province;
$(function() {
	province = (url('?province') != null) ? url('?province') : '';
	if(province==''){
		window.location.href = "../views/sourceMain.html";
	}
})

function gotoSourceUrl(url){
	window.location.href=url + "?province="+province;
}