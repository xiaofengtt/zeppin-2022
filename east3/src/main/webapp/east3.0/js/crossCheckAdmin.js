var pageSize = 20;
var pageNum = 1;
var types = [];
var paraTypes;
var typeUrl="";
var datas;
$(function(){
	$(".tableNameUpper").each(function(){
    	$(this).html(getUpper($(this).html()));
    });
    $(".tableNameCN").each(function(){
    	$(this).html(getdataType($(this).html()));
    });
    $(".serialNumber").each(function(index,value){
    	$(this).html(index+1);
    });
	//选中
    $(".check").unbind("change").change(function() {
        var checkFlag = true,
            _this = "";
        types = [];
        typeUrl=""
        $(".check").each(function(index, el) {
            _this = $(".check").eq(index);
            if (_this.prop("checked") == true) {
            	types.push($(this).next().val());
            	typeUrl = typeUrl+"&types="+$(this).next().val();
            } else {
                checkFlag = false;
            }
        });
        if (checkFlag) {
            $("#allCheck").prop("checked", true);
        } else {
            $("#allCheck").prop("checked", false);
        }
    	
    });
	//全选
    $("#allCheck").on("change", function() {
        if ($(this).is(":checked") == true) {
            types = [];
            typeUrl=""
            $(".check").prop("checked", true);
            $(".check").each(function(index, el) {
            	types.push($(this).next().val());
            	typeUrl = typeUrl+"&types="+$(this).next().val();
            });
        } else {
            $(".check").prop("checked", false);
            types = [];
            typeUrl = "";
        }
    });
});
$(".lastTd a").click(function(){
	getList('1',$(this).parent().parent().find("input[name='types']").val());
});
$(".testbtn").click(function(){
	$(".check").each(function(index, el) {
        _this = $(".check").eq(index);
        if (_this.prop("checked") == true) {
        	types.push($(this).next().val());
        }
    });
	getList('2');
});

//$(".exportResult").click(function(){
//	if(typeUrl!=""){
//		$(".exportResult").attr("href","../rest/backadmin/cross/output?dataproduct="+$("select.form-product").val()+typeUrl);
//	}else{
//		$(".exportResult").attr("href","javascript:");
//		layer.msg("请选择要导出的数据");
//	}
//});
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
		    url: '../rest/backadmin/cross/adminCheck',
		    type: 'get',
            traditional: "true",
		    data: {
		    	"types":paraTypes,
	            "dataproduct":$("select.form-product").val()
		    }
		}).done(function(r) {
			layer.closeAll();
			if (r.status == "SUCCESS") {
				datas = JSON.stringify(r.data);
				$(".firstDiv").hide();
				$(".secondDiv").show();
				var tableHtml="";
				if (r.totalResultCount != 0) {				
					for(var i=0;i<r.data.length;i++){
						var value;
						if(r.data[i].value){
							value = r.data[i].value;
						}else{
							value = "";
						}
						tableHtml+="<tr><td>"+r.data[i].row+
						"</td><td>"+r.data[i].tableName+
						"</td><td>"+r.data[i].field+"</td><td>"+r.data[i].crossType+
						"</td><td>"+r.data[i].product+"</td><td>"+r.data[i].productName+"</td><td>"+r.data[i].manager+
						"</td><td>"+r.data[i].department+"</td></tr>";					
					}
					$("#queboxCnt").html(tableHtml);				
					$(".exportResult").show();
				}else {
	                $("#queboxCnt").html("<tr style='text-align:center;'><td colspan='8'>没有数据</td></tr>");
	                $(".exportResult").hide();
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
	if($(".firstDiv").css("display")=="none"){
		getList();
	}	
});
$("#formGoBackBtn").click(function(){
	$(".firstDiv").show();
	$(".secondDiv").hide();
});
$(".exportResult").click(function(){
	var url = '../rest/backadmin/cross/adminOutput';
	var xhr = new XMLHttpRequest();
	xhr.open('POST',url,true);
	xhr.responseType = 'blob';
	xhr.onload = function(){
		if(this.status == 200){
			var blob = this.response;
			var reader = new FileReader();
			reader.readAsDataURL(blob);
			reader.onload = function(e){
				var a = document.createElement('a');
				a.download = '勾稽校验.xlsx';
				a.href = e.target.result;
				$('body').append(a);
				a.click();
				$(a).remove();
			}
		}
	}
	xhr.send();
});
$(".exporbtn").click(function(){
	if(typeUrl==''){
		layer.msg("请选择要导出的数据");
	}else{
		var index = layer.load(1, {shade: false});
		$(".layui-layer").append("<span style='line-height:40px;'>正在导出，请稍后</span>");
		var url = '../rest/backadmin/cross/adminDepartmentOutput?dataproduct='+$("select.form-product").val()+typeUrl;
		var xhr = new XMLHttpRequest();
		xhr.open('GET',url,true);
		xhr.responseType = 'blob';
		xhr.onload = function(){
			if(this.status == 200){
				var blob = this.response;
				var reader = new FileReader();
				reader.readAsDataURL(blob);
				reader.onload = function(e){
					var a = document.createElement('a');
					a.download = '部门统计结果.xlsx';
					a.href = e.target.result;
					$('body').append(a);
					a.click();
					$(a).remove();
					layer.closeAll();
				}
			}else{
				layer.closeAll();
			}
		}
		xhr.send();
	}
	
});
