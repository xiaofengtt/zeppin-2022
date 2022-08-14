var size = (url('?pagesize') != null) ? url('?pagesize') : '';
var num = (url('?pagenum') != null) ? url('?pagenum') : '';
var pageNum = 1,
pageSize = 50;
var flagSubmit = true;
var selectDate;
$(function(){
	getList();
})
layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,$ = layui.jquery
  ,upload = layui.upload
  ,element = layui.element;
  
  //日期
  laydate.render({
    elem: '.worktimeBegin'
    ,type: 'time'
 	,range: true
	,theme: '#3D99FF'
 	,done: function(value, date){
 		selectDate = value;
    }
  });
  form.on('switch(c_boolean)', function (data) {
      var item = $(".check");
      item.each(function () {
    	  $(this).prop("checked", false);
//          if ($(this).prop("checked")) {
//              $(this).prop("checked", false);
//          } else {
//              $(this).prop("checked", true);
//          }
      })
      $("#c_all").prop("checked", false);
      form.render('checkbox');
  })

  //有一个未选中 取消全选
  form.on('checkbox(c_one)', function (data) {
      var item = $(".check");
      for (var i = 0; i < item.length; i++) {
          if (item[i].checked == false) {
              $("#c_all").prop("checked", false);
              form.render('checkbox');
              break;
          }
      }
      //如果都勾选了  勾上全选
      var  all=item.length;
      for (var i = 0; i < item.length; i++) {
          if (item[i].checked == true) {
        	  $("#c_boolean").prop("checked", true);
        	  form.render('checkbox');
              all--;
          }  
      }
      if(all==item.length){
    	  $("#c_boolean").prop("checked", false);
    	  form.render('checkbox');
      }
      if(all==0){
          $("#c_all").prop("checked", true);
          $("#c_boolean").prop("checked", true);
          form.render('checkbox');
      }
  });

  //清空
  form.on('switch(switchTest)', function(data){
      if(this.checked){
          var a = data.elem.checked;
          if (a == true) {
              $(".check").prop("checked", true);
              $("#c_boolean").prop("checked", true);
              form.render('checkbox');
          } else {
              $(".check").prop("checked", false);
              form.render('checkbox');
          }
      }else{
          var item = $(".check");
          item.each(function () {
              if ($(this).prop("checked")) {
                  $("#c_boolean").prop("checked", false);
                  $(this).prop("checked", false);
              } else {
                  $(this).prop("checked", true);
              }
          })
          form.render('checkbox');
      }

  });
  //监听select
  form.on('select(gameType)',function(data){
	  getList();
	  $("#c_all").prop("checked", false);
      $("#c_boolean").prop("checked", false);
	  form.render('checkbox');
  })
});
$(".lay-submit").click(function(){
	var chk_value =[];
    $('input[class="check"]:checked').each(function(){
    	chk_value.push($(this).val());
    });
//	if($("input[name='title']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
//		layer.msg("请填写渠道名称");
//		return false;
//	}
//	if($("input[name='channelId']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
//		layer.msg("请填写渠道ID");
//		return false;
//	}
	var data = {
		'gameType':$("select[name='gameType']").val().replace(/(^\s*)|(\s*$)/g, ""),
		'minPay':$("input[name='minPay']").val().replace(/(^\s*)|(\s*$)/g, ""),
		'maxPay':$("input[name='maxPay']").val().replace(/(^\s*)|(\s*$)/g, ""),
		'periodMin':$("input[name='periodMin']").val().replace(/(^\s*)|(\s*$)/g, ""),
		'periodRandom':$("input[name='periodRandom']").val().replace(/(^\s*)|(\s*$)/g, ""),
		'worktimeBegin':selectDate.substring(0,selectDate.indexOf('-')).replace(/(^\s*)|(\s*$)/g, ""),
		'worktimeEnd':selectDate.substring(selectDate.indexOf('-')+1).replace(/(^\s*)|(\s*$)/g, ""),
		'goodsPriceMin':$("input[name='goodsPriceMin']").val().replace(/(^\s*)|(\s*$)/g, ""),
		'goodsPriceMax':$("input[name='goodsPriceMax']").val().replace(/(^\s*)|(\s*$)/g, ""),
	}
	data['uuids'] = JSON.parse(window.localStorage.getItem("uuid")).split(",");
	data['goods'] = chk_value;
		$.ajax({
	        url: '../back/robot/settingadd',
	        type: 'post',
	        async:false,
	        traditional: true,
	        data: data
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('添加成功', {
	    				time: 1000 
	    			}, function(){
	    				var index = parent.layer.getFrameIndex(window.name);
	    				window.parent.getList(size,num);
//	    				parent.location.reload();
	    				parent.layer.close(index);
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
	return false;
})
//获取商品列表
function getList(){
	$.ajax({
        url: '../back/luckygameGoods/list',
        type: 'get',
        async:false,
        data: {
        	'pageSize':pageSize,
        	'pageNum':pageNum,
        	'gameType':$("select[name=gameType]").val()
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var tableHtml = '';
				for(var i=0;i<r.data.length;i++){
					tableHtml += '<div class="luckyGoodsItem"><input lay-filter="c_one" class="check" type="checkbox" name="goods" value="'+r.data[i].uuid+
    				'"><img src="..'+r.data[i].coverImg+'"/><p>名称：'+r.data[i].shortTitle
    				+'</p><p>价格：'+r.data[i].dPrice+'</p><p>人次：'+r.data[i].shares+'</p></div>' 
				}

				$(".goosList").html(tableHtml+'<div class="clear"></div>');

				
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
