var uuid = (url('?uuid') != null) ? url('?uuid') : '';
var pagesize = (url('?pagesize') != null) ? url('?pagesize') : 50;
var pagenum = (url('?pagenum') != null) ? url('?pagenum') : 1;
var pageNum = 1,
	pageSize = 50;
var imgagesArr = [];
var list = 0,detail = 0;
var exchangeRate = 1;//汇率
var goodsId;
$(function(){
	$(".goPrePage").click(function(){
		window.location.href='teamPkGoodsList.html?pagesize='+pagesize+'&pagenum='+pagenum
	});
	if(uuid == ""){
		$("input[name='goodsId']").val(window.localStorage.getItem("goodsTitle"));
	}
	if(window.localStorage.getItem("goodsImg")){
		$(".goodsImg img").attr("src",".."+window.localStorage.getItem("goodsImg")).show();
	}else{
		$(".goodsImg img").hide();
	}	
	if(window.localStorage.getItem("code")){
		$(".goodsCode").html(window.localStorage.getItem("code")).parent().show();
	}
	$("input.goodsPrice").val(window.localStorage.getItem("costs"));
	$.ajax({
        url: '../back/param/get?paramKey=gold_exchange_rate',
        type: 'get',
        async:false
    }).done(function(r) {
  		if (r.status == "SUCCESS") {
  			exchangeRate = r.data.paramValue
			$("input[name='dPrice']").val(Number(window.localStorage.getItem("price"))*r.data.paramValue);
		}
	})
	$("input[name='betPerShare']").blur(function(){
		$("input[name='shares']").val(Math.ceil(Number($(".dTotal").val())/Number($("input[name='betPerShare']").val())))
	});
	if(uuid == ""){
		$(".demoFlag-btn").click(function(){
			layer.open({
				type: 2, 
				title:false, 
				area: ['80%', '80%'],
				content: ['luckyGoodsChoose.html?demoFlag='+$(this).attr("data-flag")] 
			});
			return false;
		});
	}
})

layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate
  ,$ = layui.jquery
  ,upload = layui.upload
  ,element = layui.element;
  form.on('select(quiz)',function(data){
	$(".dTotal").val(Math.ceil(new BigNumber(data.value).multipliedBy($("input[name='dPrice']").val())))
	$("input[name='shares']").val(Math.ceil(Number($(".dTotal").val())/Number($("input[name='betPerShare']").val())))
  })
  
  form.on('checkbox(totalIssue)',function(data){
	  if(data.elem.checked){
		  $("input[name='totalIssueNum']").parent().hide();
	  }else{
		  $("input[name='totalIssueNum']").parent().show();
	  }
  })
  //初始赋值
  if(uuid!=""){
	  $.ajax({
	        url: '../back/param/get?paramKey=gold_exchange_rate',
	        type: 'get',
	        async:false
	    }).done(function(r) {
	  		if (r.status == "SUCCESS") {
	  			exchangeRate = r.data.paramValue
			$.ajax({
		        url: '../back/luckygameGoods/get',
		        type: 'get',
		        async:false,
		        data:{
		        	'uuid':uuid
		        }
		    }).done(function(r) {
		    		if (r.status == "SUCCESS") {
		    			 form.val('first',r.data);
		    			 $(".currentIssueNum").html(r.data.endIssueNum);
	    		  		 $(".oldgoosprice").html('商品最新价值有变动，原值'+Math.ceil(r.data.dPrice/exchangeRate));
		    			 $(".oldgoosname").html('奖品最新名称有变动，原值：'+r.data.shortTitle);
	    		  		 $(".oldgoosdPrice").html('奖品最新价值金币有变动，原值'+r.data.dPrice);
	    		  		 $(".oldgoosdTotal").html('总金币最新值有所变动，原值'+Math.ceil(r.data.profitRate*r.data.dPrice));
	    		  		 $(".oldgoostotalDemand").html('总需人次最新值有所变动，原值'+r.data.shares);
	    		  		 goodsId = r.data.goodsId
		    			 if(r.data.totalIssueNum=="-1"){
		    				 $("input[name='totalIssueNum']").val('');
		    				 $("input[name='totalIssue']").next('div').click();
		    			 }
		    			 $.ajax({
		    			        url: '../back/goods/get',
		    			        type: 'get',
		    			        async:false,
		    			        data:{
		    			        	'uuid':r.data.goodsId
		    			        }
	    			    }).done(function(r) {
				    		if (r.status == "SUCCESS") {
				    			for(var m=0;m<r.data.imageList.length;m++){
									if(r.data.imageList[m].type=="type_list"&&r.data.imageList[m].sort=='0'){
										$(".goodsImg img").attr("src","http://backadmin.happyxmall.com/"+r.data.imageList[m].imageUrl).show();
									}
								}
				    			 $("input[name='goodsId']").val(r.data.shortname);
				    			 $(".currentIssueNum").html(r.data.endIssueNum);
			    		  		 $(".goodsPrice").val(r.data.price);
			    		  		 $("input[name='dPrice']").val(r.data.price/exchangeRate)
				    			 $(".dTotal").val(Math.ceil(new BigNumber($("select[name='profitRate']").val()).multipliedBy(r.data.price/exchangeRate)))
				    			 $("input[name='shares']").val(Math.ceil(Number($(".dTotal").val())/Number($("input[name='betPerShare']").val())))
				    			 $(".goodsCode").html(r.data.code).parent().show();
				    			 if('奖品最新名称有变动，原值：'+r.data.shortname!=$(".oldgoosname").html()){
				    				 $(".oldgoosname").show()
				    			 }
			    		  		 if('商品最新价值有变动，原值'+r.data.price!=$(".oldgoosprice").html()){
				    				 $(".oldgoosprice").show()
				    			 }
			    		  		 if('奖品最新价值金币有变动，原值'+$("input[name='dPrice']").val()!=$(".oldgoosdPrice").html()){
				    				 $(".oldgoosdPrice").show()
				    			 }
			    		  		 if('总金币最新值有所变动，原值'+$(".dTotal").val()!=$(".oldgoosdTotal").html()){
				    				 $(".oldgoosdTotal").show()
				    			 }
			    		  		 if('总需人次最新值有所变动，原值'+$("input[name='shares']").val()!=$(".oldgoostotalDemand").html()){
				    				 $(".oldgoostotalDemand").show()
				    			 }
				    		}
	    			    })
	    			   
				    		
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
	    })
	}
  
});
$(".lay-submit").click(function(){
	if($("select[name='profitRate']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请选择盈利比例");
		return false;
	}
	if($("input[name='betPerShare']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
		layer.msg("请填写单人次金币");
		return false;
	}
	if($("input[name='shares']").val()%2!=0){
		layer.msg("总需人次需设置为偶数");
		return false;
	}
	if($("input[name='totalIssueNum']").parent().css("display")=="block"){
		if($("input[name='totalIssueNum']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写发布总期数");
			return false;
		}else if(Number($("input[name='totalIssueNum']").val().replace(/(^\s*)|(\s*$)/g, ""))<=0){
			layer.msg("发布总期数不能小于0");
			return false;
		}
	}
	if(uuid!=""){
		var data = {
	        	'uuid':uuid,
	        	'goodsId':goodsId,
	        	'dPrice':$("input[name='dPrice']").val(),
	        	'profitRate':$("select[name='profitRate']").val(),
	        	'betPerShare':$("input[name='betPerShare']").val(),
	        	'shares':$("input[name='shares']").val()
	        }
		if($("input[name='totalIssueNum']").parent().css("display")=="block"){
			data['totalIssueNum'] = $("input[name='totalIssueNum']").val();
		}else{
			data['totalIssueNum'] = '-1';
		}
		$.ajax({
	        url: '../back/luckygameGoods/edit',
	        type: 'post',
	        traditional: true,
	        async:false,
	        data: data
	    }).done(function(r) {
	    	window.localStorage.removeItem("goodsId");
			window.localStorage.removeItem("goodsTitle");
			window.localStorage.removeItem("goodsImg");
			window.localStorage.removeItem("costs");
			window.localStorage.removeItem("price");
			window.localStorage.removeItem("code");
    		if (r.status == "SUCCESS") {
    			layer.msg('编辑成功', {
    				time: 1000 
    			}, function(){
    				window.location.href = 'teamPkGoodsList.html?pagesize='+pagesize+'&pagenum='+pagenum;
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
		var data = {
				'goodsId':window.localStorage.getItem("goodsId"),
				'dPrice':$("input[name='dPrice']").val(),
	        	'profitRate':$("select[name='profitRate']").val(),
	        	'betPerShare':$("input[name='betPerShare']").val(),
	        	'shares':$("input[name='shares']").val(),
	        	'gameType':'group2'
	        }
		if($("input[name='totalIssueNum']").parent().css("display")=="block"){
			data['totalIssueNum'] = $("input[name='totalIssueNum']").val();
		}else{
			data['totalIssueNum'] = '-1';
		}
		$.ajax({
	        url: '../back/luckygameGoods/add',
	        type: 'post',
	        async:false,
	        traditional: true,
	        data: data
	    }).done(function(r) {
	    	window.localStorage.removeItem("goodsId");
			window.localStorage.removeItem("goodsTitle");
			window.localStorage.removeItem("goodsImg");
			window.localStorage.removeItem("costs");
			window.localStorage.removeItem("price");
			window.localStorage.removeItem("code");
    		if (r.status == "SUCCESS") {
    			layer.msg('添加成功', {
    				time: 1000 
    			}, function(){
    				window.location.href = 'teamPkGoodsList.html?pagesize='+pagesize+'&pagenum='+pagenum;
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
