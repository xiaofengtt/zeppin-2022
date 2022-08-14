var number = (url('?number') != null) ? url('?number') : '';
var type = (url('?types') != null) ? url('?types') : '';
var paraTypes;
var pageSize = 20;
var pageNum = 1;
$(function(){
	type = type.split(',');
	getList(number,type);
});
//列表
function getList(number,type){
	if(number=='1'){
		paraTypes = type;
	}else if(number=='2'){
		paraTypes = types;
	}
	if(paraTypes==""){
		layer.msg("请选择要检验的数据");
	}else{
		var index = layer.load(1, {shade: false});
		$(".layui-layer").append("<span style='line-height:40px;'>正在校验，请稍后</span>");
		$.ajax({
		    url: '../rest/backadmin/cross/check',
		    type: 'get',
            traditional: "true",
		    data: {
		    	"types":paraTypes,
	            "dataproduct":$("select.form-product").val()
		    }
		}).done(function(r) {
			layer.closeAll();
			if (r.status == "SUCCESS") {
				var tableHtml="";
				if (r.totalResultCount != 0) {				
					for(var i=0;i<r.data.length;i++){
						var value;
						if(r.data[i].value){
							value = r.data[i].value;
						}else{
							value = "";
						}
						tableHtml+="<tr><td>"+Number(pageSize*(pageNum-1)+Number(i+1))+
						"</td><td>"+getUpper(r.data[i].table)+
						"</td><td>"+r.data[i].tableName+"</td><td>"+r.data[i].field+
						"</td><td>"+r.data[i].fieldType+"</td><td>"+value+"</td><td>"+r.data[i].crossType+
						"</td><td><a href='"+r.data[i].table+"Form.jsp?id="+r.data[i].id+"'>修改</a></td></tr>";					
					}
					$("#queboxCnt").html(tableHtml);				
					
				}else {
	                $("#queboxCnt").html("<tr style='text-align:center;'><td colspan='7'>没有数据</td></tr>");
	               
	            }
	        }else{
	        	layer.msg(r.message);
	        }
		}).fail(function(r){
			layer.closeAll();
			layer.msg(r.message);
		});
	}
}
$('.form-product').change(function(){
	getList();	
});
$("#formGoBackBtn").click(function(){
	window.history.go(-1);
});

