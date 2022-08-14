var idArr = [];
var types = "";
var errorInfo=[];
$(function(){
	//选中
    $(".check").unbind("change").change(function() {
        var checkFlag = true,
            _this = "";
        idArr = [];
        types="";
        $(".check").each(function(index, el) {
            _this = $(".check").eq(index);
            if (_this.prop("checked") == true) {
                idArr.push(_this.attr("data-id"));
                types =types+"&types="+$(this).next().val(); 
            } else {
                if (idArr.indexOf(_this.attr("data-id")) != -1) {
                    idArr.splice(idArr.indexOf(_this.attr("data-id")), 1);
                }
                checkFlag = false;
            }
        });
//        $(".batchExport").attr("href","../rest/backadmin/submit/submit?time="+$("input[name='time']").val()+types+"&starttime="+$("input[name='starttime']").val()+"&endtime="+$("input[name='endtime']").val());
        if (checkFlag) {
            $("#allCheck").prop("checked", true);
        } else {
            $("#allCheck").prop("checked", false);
        }
    });
	//全选
    $("#allCheck").on("change", function() {
        if ($(this).is(":checked") == true) {
            idArr = [];
            types="";
            $(".check").prop("checked", true);
            $(".check").each(function(index, el) {
                idArr.push($(".check").eq(index).attr("data-id"));
                types =types+"&types="+$(this).next().val(); 
            });
        } else {
            $(".check").prop("checked", false);
            idArr = [];
            types="";
        }
//        $(".batchExport").attr("href","../rest/backadmin/submit/submit?time="+$("input[name='time']").val()+types+"&starttime="+$("input[name='starttime']").val()+"&endtime="+$("input[name='endtime']").val());
    });
});
//数据列表
function getList(){
	if(types==""){
		layer.msg("请选择要采集的数据");
	}else{
		var index = layer.load(1, {shade: false});
		$(".layui-layer").append("<span style='line-height:40px;'>正在采集，请稍后</span>");
		$.get("../rest/backadmin/input/middleSync?"+types.substring(1,types.length),function(r){
			layer.closeAll();
	    	if(r.status=="SUCCESS"){
	    		$(".check").each(function(){
					if ($(this).is(":checked") == true) {
			            $(this).parent().parent().find(".lastTd").html("采集成功");
			        } 
				});					
				if(JSON.stringify(r.data)!= "{}"){
					for(var item in r.data){
						$("."+item+"Td").html("采集失败");
						errorInfo.push(r.data[item]);
					}
					layer.open({
					  type: 1,
					  shade: false,
					  title: false, 
					  area:['750px','600px'],
					  content: '<div style="padding:10px;">'+errorInfo[0]+'</div>', 
					  cancel: function(){
					    
					  }
					});
				}						
			}else{
				$(".check").each(function(){
					if ($(this).is(":checked") == true) {
			            $(this).parent().parent().find(".lastTd").html("采集失败");
			        } 
				});	
	    		layer.msg(r.message);
	    	}
		}).fail(function(){
			layer.closeAll();
		});
	}	
}
$(".editSql").click(function(){
	var datatype = $(this).parent().parent().find("input[name='types']").val()
	$.ajax({
	    url: '../rest/backadmin/input/getMidSql',
	    type: 'get',
	    data: {
	    	"datatype":datatype,
            "dataproduct":$("select.form-product").val()
	    }
	}).done(function(r) {
		if (r.status == "SUCCESS") {
			var index = layer.open({
				title:"修改sql语句",
				area:['600px','400px'],
				content: '<textarea rows="6" id="editSql">'+r.data.value+'</textarea>'
				,btn: ['确认', '取消']
				,yes: function(index, layero){
					var data = {
				    	"datatype":datatype,
			            "value":$("#editSql").val()
				    }
					$.post('../rest/backadmin/input/editMidSql',data,function(r){
						if (r.status == "SUCCESS") {
							layer.close(index);
							layer.msg("修改成功");
						}else{
							layer.msg(r.message);
						}
					}).fail(function(){
						layer.msg("error");
					});					
				}
				,btn2: function(index, layero){
				   
				}
				,cancel: function(){ 
				    
				}
			});
		}else{
			layer.msg(r.message);
		}
	}).fail(function(r){
		layer.msg('error');
	});	
});
