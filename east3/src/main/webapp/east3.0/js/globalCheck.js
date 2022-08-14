$(document).ready(function() {
    var id = url("?id"),
        modalType = "",
        type = $("#formType").val();

    function get() {
        $.ajax({
                url: '../rest/backadmin/' + type + '/checkGet',
                type: 'get',
                data: {
                    "id": id
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    var html = "";
                    html =  $.templates("#boxTpl").render(r.data.newdata);
                    $("#queboxCnt").html(html);
                    autocomplete();
                    for(var key in r.data.newdata){ 
                		if(key == 'xtzzkjkmlx' || key == 'gyzzkjkmlx' || key == 'gsywdl'){
                			$("input[name="+key+"]").val(getXtzzkjkmlx(r.data.newdata[key]));
                		}else if(key == 'bszq' || key == 'qxlx'){
                			$("input[name="+key+"]").val(getBszq(r.data.newdata[key]));
                		}else if(key == 'gsywzl'){
                			var values = r.data.newdata[key].split(";");
                			var valueStr='';
                        	for(index in values){
                        		valueStr+=getGsywzl(values[index])+';'                       		
                        	}
                        	$("input[name="+key+"]").val(valueStr);
                		}else if(key=='bz'||key=='dbbz'||key=='hsbz'||key=='jybz'||key=='jsbz'){
                			$("input[name="+key+"]").val(getBz(r.data.newdata[key]));
                		}else if(key=='ssdq'||key=='jzdq'||key=='zjtxdq'||key=='khqy'||key=='txdq'||key=='zcdq'||key=='ztjd'||key=='htqdd'){
                			if(r.data.newdata[key].length>3){
                				getScode(key,r.data.newdata[key]);
                			}else{
                				$("input[name="+key+"]").val(getJwgi(r.data.newdata[key]));
                			}
                		}else if(key=='ssgb'){
                			$("input[name="+key+"]").val(getJwgi(r.data.newdata[key]));
                		}
                	} 
                    
                    if (r.data.type == "add") {
                        $("#eType").html("添加");
                    } else {
                        $("#eType").html("修改")
                    }
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });

    }

    get();

    $(".checkBtn").click(function() {
        modalType = "check";
        $("#btnSubmit").attr("data-status", $(this).attr("data-status"));
    });

    //审核
    function check(_this) {
    	var checkStatus = $(_this).attr("data-status");
        $.ajax({
                url: '../rest/backadmin/' + type + '/checkCheck',
                type: 'get',
                data: {
                	"status": checkStatus,
                    "id": id
                }
            })
            .done(function(r) {
                if (r.status == "SUCCESS") {
                    layer.msg(r.message, {
                        time: 2000
                    }, function() {
                        location.href = document.referrer;
                    });
                } else {
                    layer.msg(r.message, {
                        time: 2000
                    });
                }
            })
            .fail(function() {
                layer.msg("error", {
                    time: 2000
                });
            });
    }

    $("#btnSubmit").click(function() {
        if (flagSubmit == false) {
            return false;
        }
        flagSubmit = false;
        setTimeout(function() {
            flagSubmit = true;
        }, 3000);
        check($(this));
    });

    $("#btnCancel").click(function() {
        location.href = document.referrer;
    });
  
});