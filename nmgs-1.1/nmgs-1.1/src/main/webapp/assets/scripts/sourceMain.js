$(function() {
	$.get('../front/admin/province!execute?uid=l0001&status=normal',function(r){
		if(r.status == 'success') {
			var provinceHtml='';
			$.each(r.data,function(i,v){
				provinceHtml+="<option value='"+v.id+"'>"+v.name+"</option>";
			})
			$('#provinceSelect').html(provinceHtml);
		}else {
			alert(r.message);
		}
	})
});

function submitProvince(){
	var province = $('#provinceSelect').val();
	window.location.href="../views/categoryList.html?province="+province;
}
