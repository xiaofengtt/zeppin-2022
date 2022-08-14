var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var pageNum = 1,
	pageSize = 50;
var imgagesListArr = [],imgagesDetailArr=[];
var list = 0,detail = 0;
var listFlag = false,detailFlag = false;
var listSort = {},detailSort = {};//存放上传图片序号
var listSortData = [],detailSortData = [];//存放上传uuid
var listSortNew = {},detailSortNew = {};//存放上传图片序号--排序后
var imgBaseUrl = ''; // 图片上传后地址
$(function(){	
	$(".goPrePage").click(function(){
		window.location.href='goodsList.html?pagesize='+pagesize+'&pagenum='+pagenum
	});
	getList();
	$(".form-next").click(function(){
		$(".layui-body").scrollTop = 0;
		$('.layui-body').animate({
            scrollTop: 0
        }, 100);
		$(".layui-tab-title li:eq(1)").click();
	});
})
//获取商品分类
function getList(){
	$.ajax({
        url: '../back/goodsType/list',
        type: 'get',
        async:false,
        data:{
        	'pageSize':pageSize,
        	'pageNum':pageNum
        }
    }).done(function(r) {
    		if (r.status == "SUCCESS") {
    			var html = '';
    			for(var i = 0;i<r.data.length;i++){
    				html += '<option value="'+r.data[i].code+'">'+r.data[i].name+'</option>';
    			}
    			$("select[name='type']").html(html);
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
        
    }); 
}

layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,$ = layui.jquery
  ,upload = layui.upload
  ,element = layui.element;

  //自定义验证规则
  form.verify({
    
  });
  
  //日期
  laydate.render({
    elem: '#test1'
    ,type: 'datetime'
	,theme: '#3D99FF'
  });
  
  //初始赋值
  if(uuid!=""){
		$.ajax({
	        url: '../back/goods/get',
	        type: 'get',
	        async:false,
	        data:{
	        	'uuid':uuid
	        }
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			 form.val('first',r.data);
	    			 //把图片分组
	    			var imgDetail = [],imgList = [];
	    			for(var i=0;i<r.data.imageList.length;i++){
	    				if(r.data.imageList[i].type=="type_detail"){
	    					imgDetail.push(r.data.imageList[i]);
	    				}else if(r.data.imageList[i].type=="type_list"){
	    					imgList.push(r.data.imageList[i]);
	    				}
	    			}
	    			var objectArraySort = function (keyName) {
	    				 return function (objectN, objectM) {
		    				  var valueN = objectN[keyName]
		    				  var valueM = objectM[keyName]
		    				  if (valueN > valueM) return 1
		    				  else if (valueN < valueM) return -1
		    				  else return 0
	    				 }
	    			}
	    			imgDetail.sort(objectArraySort('sort'));
	    			imgList.sort(objectArraySort('sort'));
	    			for(var i=0;i<imgDetail.length;i++){
	    				$('#right-defaults').append('<div class="img-box" attr-uuid="'+imgDetail[i].image+'"><img src="../'+ imgDetail[i].imageUrl +'" alt="" class="layui-upload-img">'+
//	    	      	    		'</div>');
	    				'<a class="delete-img" onclick="deleteImgDetail(this)"></a></div>');
	    				// imgagesDetailArr.push('type_detail@@'+imgDetail[i].image+'@@'+detail);
	    		    	detail = detail + 1;
	    		    	// $("input[name='images']").val(imgagesDetailArr.concat(imgagesListArr));
	    			}
	    			for(var i=0;i<imgList.length;i++){
	    				$('#left-defaults').append('<div class="img-box" attr-uuid="'+imgList[i].image+'"><img src="../'+ imgList[i].imageUrl +'" alt="" class="layui-upload-img">'+
//	  	    		    	  '</div>');
	    				'<a class="delete-img" onclick="deleteImg(this)"></a></div>');
	    				// imgagesListArr.push('type_list@@'+imgList[i].image+'@@'+list);
	    			  	list = list + 1;
	    			  	// $("input[name='images']").val(imgagesDetailArr.concat(imgagesListArr));
	    			}
	    			listFlag = true,detailFlag = true;
	    			$('#goodsVideoImg').html('<video src="..'+ r.data.videoUrl +'" alt="" class="layui-upload-img">');
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
	        
	    }); 
	}
  upload.render({
    elem: '#goodsList'
    ,url: '../back/resource/add' //改成您自己的上传接口
    ,multiple: true
    ,before: function(obj){
      //预读本地文件示例，不支持ie8
	    //   obj.preview(function(index, file, result){
			  // imgBaseUrl = result
	    // 	  if(uuid!=""){
	    // 		  if(listFlag){
		   //  		  listFlag = false;
		   //  		  list = 0;
		   //  		  listSort = {};
		   //  	  }else{
		   //  		  list = list + 1;
		   //  	  }
		   //  	  listSort[index] = {sort:file.name.substring(file.name.indexOf(".")-2,file.name.indexOf(".")),img:result};
	    // 	  }else{
		   //  	  listSort[index] = {sort:file.name.substring(file.name.indexOf(".")-2,file.name.indexOf(".")),img:result};
		   //  	  list = list + 1;
	    // 	  }
	    	  
	    //   });
    }
    ,done: function(res,index,result){
      //上传完毕
      // if(list==0){
    	 //  imgagesListArr = [];
    	 //  listSortNew = {};
   	  // }
      // listSortNew[index]=res.data.uuid;
      // var l = 0
      // Object.keys(listSort).forEach(function(key){
    	 //  listSortData[l] = {sort:listSort[key].sort,uuid:listSortNew[key],img:listSort[key].img};
    	 //  l++;
      // });
      // listSortData.sort(function (a, b) {
    	 //  if (a["sort"] < b["sort"]) {
    	 //  return -1
    	 //  }
    	 //  if (a["sort"] > b["sort"]) {
    	 //  return 1
    	 //  }
    	 //  return 0;
      // });
      // imgagesListArr = [];
      var imgHtml = '';
	  imgHtml='<div class="img-box" attr-uuid="'+res.data.uuid+'"><img src="../'+ res.data.url +'" class="layui-upload-img">'+
	  //    	  '</div>'
	      	  '<a class="delete-img" onclick="deleteImg(this)"></a></div>'
//       for(var i=0;i<listSortData.length;i++){
//     	  imgagesListArr.push('type_list@@'+listSortData[i].uuid+'@@'+i);
//     	  imgHtml+='<div class="img-box"><img src="'+ listSortData[i].img +'" class="layui-upload-img">'+
// //    	  '</div>'
//     	  '<a class="delete-img" onclick="deleteImg(this)" attr-data="type_list@@'+listSortData[i].uuid+'@@'+i+'"></a></div>'
//       }
      $('#left-defaults').append(imgHtml);
  	  // $("input[name='images']").val(imgagesDetailArr.concat(imgagesListArr));
    }
  });
  upload.render({
    elem: '#goodsDetail'
    ,url: '../back/resource/add' //改成您自己的上传接口
    ,multiple: true
    ,before: function(obj){
      //预读本地文件示例，不支持ie8
    //   obj.preview(function(index, file, result){
		  // imgBaseUrl = result
    // 	  if(uuid!=""){
    // 		  if(detailFlag){
    //     		  detailFlag = false;
    //     		  detail = 0;
    //     		  detailSort = {};
    //     	  }else{
    //     		  detail = detail + 1;
    //     	  }
    // 		  detailSort[index] = {sort:file.name.substring(file.name.indexOf(".")-2,file.name.indexOf(".")),img:result};
    // 	  }else{
    // 		  detailSort[index] = {sort:file.name.substring(file.name.indexOf(".")-2,file.name.indexOf(".")),img:result};
    // 		  detail = detail + 1;
    // 	  }
    //   });
    }
    ,done: function(res,index){
    	// if(detail==0){
     //  	  imgagesDetailArr = [];
     // 	}
    	// detailSortNew[index]=res.data.uuid;
     //    var k = 0
     //    Object.keys(detailSort).forEach(function(key){
     //  	  detailSortData[k] = {sort:detailSort[key].sort,uuid:detailSortNew[key],img:detailSort[key].img};
     //  	  k++
     //    })
    	// detailSortData.sort(function (a, b) {
     //  	  if (a["sort"] < b["sort"]) {
     //  	  return -1
     //  	  }
     //  	  if (a["sort"] > b["sort"]) {
     //  	  return 1
     //  	  }
     //  	  return 0;
     //    });
    	// imgagesDetailArr = [];
        var imgHtml = '';
		imgHtml+='<div class="img-box" attr-uuid="'+res.data.uuid+'"><img src="../'+ res.data.url +'" class="layui-upload-img">'+
		//      	    		'</div>'
		      	    		'<a class="delete-img" onclick="deleteImgDetail(this)"></a></div>'
//         for(var i=0;i<detailSortData.length;i++){
//         	imgagesDetailArr.push('type_detail@@'+detailSortData[i].uuid+'@@'+i);
//       	    imgHtml+='<div class="img-box"><img src="'+ detailSortData[i].img +'" class="layui-upload-img">'+
// //      	    		'</div>'
//       	    		'<a class="delete-img" onclick="deleteImgDetail(this)" attr-data="type_detail@@'+detailSortData[i].uuid+'@@'+i+'"></a></div>'
//         }
        $('#right-defaults').append(imgHtml);
    	// $("input[name='images']").val(imgagesDetailArr.concat(imgagesListArr));
    }
  });
  upload.render({
    elem: '#goodsVideo'
    ,url: '../back/resource/add' //改成您自己的上传接口
    ,accept: 'video' //视频
    ,done: function(res){
      layer.msg('上传成功');
      $('input[name="video"]').val(res.data.uuid);
      $('#goodsVideoImg').html('<video src="..'+ res.data.url +'" alt="" class="layui-upload-img">');
    }
  });
  
});
$(".lay-submit").click(function(){
	if($("input[name='name']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写商品名称");
		return false;
	}
	if($("input[name='shortname']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写商品简称");
		return false;
	}
	if($("textarea[name='description']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写商品描述");
		return false;
	}
	if($("input[name='costs']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写商品成本价");
		return false;
	}
	if($("input[name='price']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写商品价格");
		return false;
	}
	if($("input[name='code']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写商品编号");
		return false;
	}
	var data = {
		'name':$("input[name='name']").val().replace(/(^\s*)|(\s*$)/g, ""),
    	'shortname':$("input[name='shortname']").val().replace(/(^\s*)|(\s*$)/g, ""),
    	'type':$("select[name='type']").val().replace(/(^\s*)|(\s*$)/g, ""),
    	'description':$("textarea[name='description']").val().replace(/(^\s*)|(\s*$)/g, ""),
    	'costs':$("input[name='costs']").val().replace(/(^\s*)|(\s*$)/g, ""),
    	'price':$("input[name='price']").val().replace(/(^\s*)|(\s*$)/g, ""),
    	'code':$("input[name='code']").val().replace(/(^\s*)|(\s*$)/g, ""),
    	'status':$("input[name='status']:checked").val().replace(/(^\s*)|(\s*$)/g, ""),
	}
	if($("select[name='source']").val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		data['source'] = $("select[name='source']").val().replace(/(^\s*)|(\s*$)/g, "");
	}
	if($("input[name='sourceUrl']").val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		data['sourceUrl'] = $("input[name='sourceUrl']").val().replace(/(^\s*)|(\s*$)/g, "");
	}
	if($("input[name='video']").val().replace(/(^\s*)|(\s*$)/g, "")!=""){
		data['video'] = $("input[name='video']").val().replace(/(^\s*)|(\s*$)/g, "");
	}
	// 列表图
	imgagesDetailArr = [],imgagesListArr=[]
	$("#left-defaults .img-box").each(function(index,i){
		imgagesListArr.push('type_list@@'+$(this).attr('attr-uuid')+'@@'+index)
	})
	$("#right-defaults .img-box").each(function(index,i){
		imgagesDetailArr.push('type_detail@@'+$(this).attr('attr-uuid')+'@@'+index)
	})
	if(imgagesDetailArr.concat(imgagesListArr).length!=0){
		data['images'] = imgagesDetailArr.concat(imgagesListArr);
	}
	if(uuid!=""){
		data['uuid'] = uuid;
		$.ajax({
	        url: '../back/goods/edit',
	        type: 'post',
	        traditional: true,
	        async:false,
	        data: data
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('编辑成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href = 'goodsList.html?pagesize='+pagesize+'&pagenum='+pagenum;
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
	}else{	
		$.ajax({
	        url: '../back/goods/add',
	        type: 'post',
	        async:false,
	        traditional: true,
	        data: data
	    }).done(function(r) {
	    		if (r.status == "SUCCESS") {
	    			layer.msg('添加成功', {
	    				time: 1000 
	    			}, function(){
	    				window.location.href = 'goodsList.html?pagesize='+pagesize+'&pagenum='+pagenum;
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
	return false;
})
function deleteImg(obj){
	// imgagesListArr.some(function(item, i){
 //      if (item == $(obj).attr("attr-data")) {
 //    	  imgagesListArr.splice(i, 1);
 //    	  return true
 //      }
 //    })
    $(obj).parent().remove()
}
function deleteImgDetail(obj){
	// imgagesDetailArr.some(function(item, i){
 //      if (item == $(obj).attr("attr-data")) {
 //    	  imgagesDetailArr.splice(i, 1);
 //    	  return true
 //      }
 //    })
    $(obj).parent().remove()
}