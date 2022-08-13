$(function(){
	$("#layerDiv").css({"width":$(document).width(),"height":$(document).height()});
	getCounty();
})
function getCounty(){
	var timestamp = (new Date()).valueOf();
	$("#layerDiv").css("display","block");
	$.get('../mw_gpjh/trainee_headsearch4county.action?time='+timestamp,function(r){
		$("#layerDiv").css("display","none");
		var str = '';
		for ( var i = 1, l = r.preInitData.county.length; i < l; i++ ) {						
			str +='<option value="'+r.preInitData.county[i].id+'" search="'+r.preInitData.county[i].search+'">'
			+r.preInitData.county[i].provinceName+'-'+r.preInitData.county[i].cityName+'-'+r.preInitData.county[i].name
			+'</option>';						
		}
		$('#county').html(str);
	}).done(function(){
		$('#county').select2();
	})
}

function stopPropagation(e) {
	if (e.stopPropagation)
	e.stopPropagation();
	else
	e.cancelBubble = true;
} 

function initGrid() {	
	$("#layerDiv").css("display","block");
	var county = $('#county').val();
	$.post("trainee_adminTraineeNumForAllPKX.action",{county:county}, function(r) {
		$("#layerDiv").css("display","none");
		var trs = "<tr class='tr1'><th>地区</th><th>示范性项目培训人数</th><th>中西部项目培训人数</th></tr>";
		if(r.status == 'OK'){
			trs+="<tr><td>"+r.nameStr+"</td><td>"+r.sfcount+"</td><td>"+r.mwcount+"</td></tr>";
			$('table').html(trs);
		} else {
			alert(r.message);
		}
		return false;
	});
	return false;
}
