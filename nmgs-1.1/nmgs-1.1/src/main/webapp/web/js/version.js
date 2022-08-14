$(function(){
	getList();
	
})
function getList(){
	$.get('../front/web/webInterface!execute?uid=i0010&province=01181b6c-39a2-4fed-bc3b-048f31ae5650&component=634ce983-ac42-46cd-9984-b927c6231a07',function(r){
		if(r.status=="success"){
			var str ="";
			for ( var i = 0, l = r.data.length; i < l; i++ ) {
				var versionName=r.data[i].versionna;
				var appexplain=r.data[i].appexplain;
				var createtime=r.data[i].createtime;
				createtime = createtime.substring(0,createtime.lastIndexOf("."));
				str += "<div class='list'><h5>"+versionName+"</h5><p class='data'>"+createtime+"</p><p class='intro'>"+appexplain+"</p></div>";
			}
			$(".versionList").html(str);
		}else{
			alert(r.message);
		}
	});
}
