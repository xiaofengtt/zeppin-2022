/**
 * 左侧菜单
 */
$(function(){
	getLeft();	
});
function getLeft(){
	$.ajax({
        url: '../back/menu/left',
        type: 'get',
        async:false,
        data: {
            
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var baseLeft = "";
    			for(var i=0;i<r.data.length;i++){
    				var leftChild = "";
    				var className = "";
    				var classparentName = "";
    				for(var j=0;j<r.data[i].children.length;j++){
    					if($("#uuidInput").val()==r.data[i].children[j].uuid){
    						className = "light";
    						classparentName = "light";
    					}else{
    						className = "";
    					}
    					leftChild += '<li data-uuid="'+r.data[i].children[j].uuid+
    					'" class="'+className+'"><a href="'+r.data[i].children[j].url+'">'+r.data[i].children[j].name+'</a></li>';
    				}
    				baseLeft +='<li data-uuid="'+r.data[i].uuid+'" class="'+classparentName+'"><a href="javascript:" class="parent-item">'+r.data[i].name+
    				'</a><ul class="content-left-list-child">'+leftChild+'</ul></li>';
    			}
    			$(".content-left-list").html(baseLeft);
    			$(".parent-item").click(function(){
    				if($(this).parent().hasClass("light")){
    					$(this).parent().removeClass("light").find("ul").hide();
    				}else{
    					$(this).parent().addClass("light").find("ul").show();
    				}
    				
    			});
    		} else {
    			if(r.errorCode=="302"){
    				layer.msg(r.message, {
    		            time: 2000
    		        },function(){
    		        	window.location.href="login.html";
    		        });
    			}else{
    				layer.msg(r.message);
    			}
    		}
    }).fail(function(r) {
        layer.msg("error", {
            time: 2000
        },function(){
        	window.location.href=window.location.href;
        });
    });   	
}