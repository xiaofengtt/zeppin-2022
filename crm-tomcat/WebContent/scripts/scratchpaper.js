var sid = "";
function savechpaper(){   
	if(sid === ""){
		alert("无法修改！"); 
	    return false;
	}
	if($("contents").value === ""){
		alert("请填写便签内容！"); 
		$("contents").focus();
	    return false;
	}
			scratchpaperManager.saveData($("contents").value,sid,{
	callback: function(data) {
			alert("便签修改成功！")
			hideBox();
			$("contents").value = "";
		},
		errorHandler : function(message) {
			
		},
		async : false
	});
};
function editor(id){
	scratchpaperManager.readData(id,{
	callback: function(data) {
			$("contents").value = data;
			sid = id;
		},
		errorHandler : function(message) {
			
		},
		async : false
	});
	showBox();
};