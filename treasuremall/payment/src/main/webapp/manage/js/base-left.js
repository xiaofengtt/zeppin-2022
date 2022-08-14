/**
 * 左侧菜单
 */
$(function(){
	getLeft();	
});
function getLeft(){
	$.ajax({
        url: '../system/menu/left',
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
    						className = "layui-this";
    						classparentName = "layui-nav-itemed";
    					}else{
    						className = "";
    					}
    					leftChild += '<dd data-uuid="'+r.data[i].children[j].uuid+
    					'" class="'+className+'"><a href="'+r.data[i].children[j].url+'">'+r.data[i].children[j].name+'</a></dd>';
    				}
    				baseLeft +='<li data-uuid="'+r.data[i].uuid+'" class="'+classparentName+' layui-nav-item"><a href="javascript:" class="parent-item">'+r.data[i].name+
    				'<span class="layui-nav-more"></span></a><dl class="content-left-list-child layui-nav-child">'+leftChild+'</dl></li>';
    			}
    			$(".content-left-list").html(baseLeft);
    			layui.use('element', function(){
  				  var element = layui.element;				  
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