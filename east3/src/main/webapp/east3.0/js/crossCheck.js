var pageSize = 20;
var pageNum = 1;
var types = [];
var paraTypes;
var typeUrl="";
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
	window.location.href = "crossCheckResult.jsp?number=1&types="+$(this).parent().parent().find("input[name='types']").val();
});
$(".testbtn").click(function(){
	$(".check").each(function(index, el) {
        _this = $(".check").eq(index);
        if (_this.prop("checked") == true) {
        	types.push($(this).next().val());
        }
    });
	window.location.href = "crossCheckResult.jsp?number=1&types="+types;
});


$(".exportResult").click(function(){
	if(typeUrl!=""){
		$(".exportResult").attr("href","../rest/backadmin/cross/output?dataproduct="+$("select.form-product").val()+typeUrl);
	}else{
		$(".exportResult").attr("href","javascript:");
		layer.msg("请选择要导出的数据");
	}
});
