/**
 * 活动营销--0元购
 */
var tabActive = (url('?tab') != null) ? url('?tab') : 0;
var uploadClass = ''
var flagSubmit = true;
var capitalAccountHtml='';//充值渠道数据
var buyfreeFlag = true,checkinFlag = true,scoreFlag = true,firstchargeFlag = true,rechargeFlag = true,recommendFlag = true;
var activityInfo = '';//name值
var selectDate;
var index;//item序号
var goodsList = [];//存储最开始设置的数组

var checkInactivityInfo = '';//签到活动name值
var checkInselectDate;
var checkInindex;//item序号
var checkIngoodsList = [];//存储最开始设置的数组

var scorelotteryactivityInfo = '';//签到活动name值
var scorelotteryselectDate;
var scorelotteryindex;//item序号
var scorelotterygoodsList = [];//存储最开始设置的数组

var firstchargeactivityInfo = '';//签到活动name值
var firstchargeselectDate;
var firstchargeindex;//item序号
var firstchargegoodsList = [];//存储最开始设置的数组

var rechargeactivityInfo = '';//签到活动name值
var rechargeselectDate;
var rechargeindex;//item序号
var rechargegoodsList = [];//存储最开始设置的数组

var recommendactivityInfo = '';//签到活动name值
var recommendselectDate;
var recommendindex;//item序号
var recommendgoodsList = [];//存储最开始设置的数组

var statusObj = {
	'normal':'已开启',
	'disable':'已关闭'
}
$(function(){
	$(".buyfree .lay-activityList").click(function(){
		window.location.href="activityFreeDetail.html"
	})
	$(".checkIn .lay-activityList").click(function(){
		window.location.href="activityCheckInDetail.html"
	})
	$(".scorelottery .lay-activityList").click(function(){
		window.location.href="activityScoreDetail.html"
	})
	$(".recommend .lay-activityList").click(function(){
		window.location.href="recommendDetail.html"
	})
});

layui.use(['form', 'layedit', 'laydate','upload','element'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate
	  ,$ = layui.jquery
	  ,upload = layui.upload
	  ,element = layui.element;	
	  function activityList(){
		   $.ajax({
		        url: '../back/activity/list',
		        type: 'get',
		        async:false,
		        data: {}
		    }).done(function(r) {
				if (r.status == "SUCCESS") {
					var li = '';
					for(var i=0;i<r.data.length;i++){
						li += '<li>'+r.data[i].title+'(<span class="'+(r.data[i].status=="normal"?"color-orange":"color-gray")+'">'
								+statusObj[r.data[i].status]+'</span>)</li>'
					}
					$(".layui-tab-title").html(li)
				}
		    })
	  }
	  activityList();
	  getCapitalAccount();
	  $(".layui-tab-title li:eq("+tabActive+")").click()
	  if(tabActive==0){
    	getBuyfree();
		getGoodsList();
	  }else if(tabActive==1){
    	getCheckin();
    	getCheckGoodsList();
	  }else if(tabActive==2){
    	getScorelottery()
    	getScorelotteryGoodsList()
	  }else if(tabActive==3){
    	getFirstcharge()
    	getFirstchargeList()
	  }else if(tabActive==4){
    	getRecharge()
    	getRechargeList()
	  }else if(tabActive==5){
		getRecommend()
		getRecommendList()
	  }
	  element.on('tab(activity)', function(data){
		    if(data.index==0){
		    	getBuyfree();
				getGoodsList();
		    }else if(data.index==1){
		    	getCheckin();
		    	getCheckGoodsList();
		    }else if(data.index==2){
		    	getScorelottery()
		    	getScorelotteryGoodsList()
		    }else if(data.index==3){
		    	getFirstcharge()
		    	getFirstchargeList()
			}else if(data.index==4){
				getRecharge()
		    	getRechargeList()
			}else if(data.index==5){
				getRecommend()
				getRecommendList()
			}
	  });
		// 获取0元购基础信息
		function getBuyfree(){
			$.ajax({
		        url: '../back/activity/get',
		        type: 'get',
		        async:false,
		        data: {
		        	'uuid':'buyfree'
		        }
		    }).done(function(r) {
				if (r.status == "SUCCESS") {
					  buyfreeFlag = true
					  //日期
					  laydate.render({
						    elem: '.buyfree .activityTime'
						    ,value: r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
						    ,type: 'datetime'
						    ,range:'-'
							,theme: '#3D99FF'
					    	,done: function(value, date){
					     		selectDate = value;
					        }
					  });
					  $(".buyfree input[name='bannerUrl']").val(r.data.bannerUrl)
					  if(r.data.configuration){
						  $(".buyfree input[name='timesline']").val(JSON.parse(r.data.configuration).timesLine)
					  }
					  activityInfo = r.data.name
					  selectDate = r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
					  if(r.data.status=="disable"){
						  $(".buyfree .lay-startActivity").show()
						  $(".buyfree .lay-closeActivity").hide()
					  }else{
						  $(".buyfree .lay-startActivity").hide()
						  $(".buyfree .lay-closeActivity").show()
					  }
					  $(".buyfree .lay-startActivity").unbind().click(function(){
						  if(buyfreeFlag){
							  changeStatus('buyfree','normal')
						  }else{
							layer.msg("请先保存设置再开启活动")  
						  }  
					  })
					  $(".buyfree .lay-closeActivity").unbind().click(function(){
						  changeStatus('buyfree','disable')
					  })
				}
		    })
		}
	$(".buyfree .lay-submit").click(function(){
		for(var m=0;m<$(".buyfree .goods-item").length;m++){
			if($(".buyfree .goods-item:eq("+m+")").find("input[name='goodsId']").val()==""){
				layer.msg("有未选择商品的选项");
				return false;
			}else if($(".buyfree .goods-item:eq("+m+")").find("input[name='image']").val()==""){
				layer.msg("有未上传图片的选项");
				return false;
			}else if($(".buyfree .goods-item:eq("+m+")").find("input[name='goodsShares']").val()==""){
				layer.msg("有未设置总需人次的选项");
				return false;
			}
		}
		var data = {},details = [],detailItem = {};
		data['uuid'] = 'buyfree';
		data['starttime'] = selectDate.substring(0,19)
		data['endtime'] = selectDate.substring(22)
		data['configuration'] ='{"timesLine":'+$(".buyfree .timesline").val().replace(/(^\s*)|(\s*$)/g, "")+'}'
		data['bannerUrl'] = $(".buyfree .bannerUrl").val().replace(/(^\s*)|(\s*$)/g, "")
		for(var i=0;i<index;i++){
			detailItem = {}
			if($(".buyfree .goods-item"+i).find("input[name='uuid']").val()==""){
				detailItem['uuid']=''
				detailItem['activityInfo']=activityInfo
				detailItem['type']='add'
				detailItem['goodsId']=$(".buyfree .goods-item"+i).find("input[name='goodsId']").val()
				detailItem['goodsCover']=$(".buyfree .goods-item"+i).find("input[name='image']").val()
				detailItem['goodsPrice']=Number($(".buyfree .goods-item"+i).find("input[name='goodsPrice']").val())
				detailItem['goodsTitle']=$(".buyfree .goods-item"+i).find("input[name='goodsTitle']").val()
				detailItem['goodsShortTitle']=$(".buyfree .goods-item"+i).find("input[name='goodsShortTitle']").val()
				detailItem['shares']=Number($(".buyfree .goods-item"+i).find("input[name='goodsShares']").val())
				detailItem['sort']= i
				details.push(detailItem)
			}
		}
		for(var j=0;j<goodsList.length;j++){
			detailItem = {}
			var ishas = false;
			for(var i=0;i<index;i++){
				if(goodsList[j].uuid==$(".buyfree .goods-item"+i).find("input[name='uuid']").val()){
					ishas = true;
					detailItem['goodsCover']=$(".buyfree .goods-item"+i).find("input[name='image']").val()
					detailItem['shares']=Number($(".buyfree .goods-item"+i).find("input[name='goodsShares']").val())
				}
			}
			if(ishas){
				detailItem['type']="edit"
			}else{
				detailItem['type']="delete"
				detailItem['goodsCover']=goodsList[j].goodsCover
				detailItem['shares']=goodsList[j].shares
			}
			detailItem['uuid']=goodsList[j].uuid
			detailItem['activityInfo']=activityInfo
			detailItem['goodsId']=goodsList[j].goodsId
			detailItem['goodsPrice']=goodsList[j].goodsPrice
			detailItem['goodsTitle']=goodsList[j].goodsTitle
			detailItem['goodsShortTitle']=goodsList[j].goodsShortTitle
			detailItem['sort'] = j
			details.push(detailItem)
		}
		data['details']= JSON.stringify(details)
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../back/activity/edit',
	        type: 'post',
	        async:false,
	//        traditional: true,
	        data: data
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				buyfreeFlag = true
				layer.msg('设置成功')
				getBuyfree();
				getGoodsList();
			}else {
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
	    })
		return false;
	})
	//获取详细设置列表
	function getGoodsList(){
		$.ajax({
	        url: '../back/activityBuyfree/goodslist',
	        type: 'get',
	        async:false,
	        data: {
	        	'sort':'sort asc'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				var html = '';
				goodsList = r.data
				index = r.data.length
				for(var i=0;i<r.data.length;i++){
					html += '<div class="goods-item goods-item'+i+'">'+
								'<div class="layui-col-md11">'+
									'<div class="layui-col-md4">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">奖品名称</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must">'+
										        '<input type="text" name="goodsTitle" readonly class="layui-input" value="'+r.data[i].goodsTitle+'">'+
										        '<input type="hidden" name="goodsShortTitle" class="layui-input" value="'+r.data[i].goodsShortTitle+'">'+
										        '<input type="hidden" name="goodsId" class="layui-input" value="'+r.data[i].goodsId+'">'+
										        '<input type="hidden" name="uuid" class="layui-input" value="'+r.data[i].uuid+'">'+
										    '</div>'+
										'</div>'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">奖品封面</label>'+
										    '<div class="layui-input-block">'+
										        '<div class="layui-upload">'+
													  '<button type="button" class="layui-btn layui-btn-sm" id="goodsList">上传图片</button>'+
													  '<p class="layui-tips tips-must">建议尺寸：120*120px</p>'+
													  '<input type="hidden" name="image" value="'+r.data[i].goodsCover+'" >'+
												'</div>'+
										    '</div>'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md4">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">奖品原价</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must">'+
										        '<input type="text" name="goodsPrice" readonly class="layui-input" value="'+r.data[i].goodsPrice+'">'+
										    '</div>'+
										'</div>'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">总需人次</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must">'+
										        '<input type="number" name="goodsShares" class="layui-input" value="'+r.data[i].shares+'">'+
										    '</div>'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md4">'+
										'<img class="goodsCover" src="..'+r.data[i].goodsCoverUrl+'" />'+
									'</div>'+
								'</div>'+
								'<div class="layui-col-md1">'+
//									'<button class="layui-btn lay-edit layui-btn-sm" lay-submit lay-filter="*">编辑</button>'+
									'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
								'</div>'+
								'<div class="clear"></div>'+
							'</div>';
				}
				$(".buyfree .goodsList").html(html)
				addListenBuyfree()
				for(var i=0;i<r.data.length;i++){
					upload.render({
					    elem: '.buyfree .goods-item'+i+' #goodsList'
					    ,url: '../back/resource/add' //改成您自己的上传接口
					    ,multiple: true
					    ,before: function(obj){
					      //预读本地文件示例，不支持ie8
					    }
					    ,done: function(res,index){
					    	$('.buyfree .'+uploadClass+' input[name="image"]').val(res.data.uuid);
					    	$('.buyfree .'+uploadClass+' .goodsCover').attr('src','..' + res.data.url); 
					    }
					  });
				}
				$(".buyfree .layui-upload button").click(function(){
					uploadClass = $(this).parent().parent().parent().parent().parent().parent().attr('class')
					uploadClass = uploadClass.substring(uploadClass.indexOf(' ')+1)
				})
				$(".buyfree .lay-delete").click(function(){
					$(this).parent().parent().remove()
				})
			}
	    })
	}
	//新增
	$(".buyfree .lay-addNew").click(function(){
		var html = '<div class="goods-item goods-item'+index+'">'+
			'<div class="layui-col-md11">'+
				'<div class="layui-col-md4">'+
					'<div class="layui-col-md12 mb-15">'+
					    '<label class="layui-form-label">奖品名称</label>'+
					    '<div class="layui-input-block layui-pr layui-pr-must">'+
					        '<input type="text" name="goodsTitle" readonly class="layui-input">'+
					        '<input type="hidden" name="goodsShortTitle" class="layui-input">'+
					        '<input type="hidden" name="goodsId" class="layui-input">'+
					        '<input type="hidden" name="uuid" class="layui-input" value="">'+
					    '</div>'+
					'</div>'+
					'<div class="layui-col-md12 mb-15">'+
					    '<label class="layui-form-label">奖品封面</label>'+
					    '<div class="layui-input-block">'+
					        '<div class="layui-upload">'+
								  '<button type="button" class="layui-btn layui-btn-sm" id="goodsList">上传图片</button>'+
								  '<p class="layui-tips tips-must">建议尺寸：120*120px</p>'+
								  '<input type="hidden" name="image" >'+
							'</div>'+
					    '</div>'+
					'</div>'+
				'</div>'+
				'<div class="layui-col-md4">'+
					'<div class="layui-col-md12 mb-15">'+
					    '<label class="layui-form-label">奖品原价</label>'+
					    '<div class="layui-input-block layui-pr layui-pr-must">'+
					        '<input type="text" name="goodsPrice" readonly class="layui-input">'+
					    '</div>'+
					'</div>'+
					'<div class="layui-col-md12 mb-15">'+
					    '<label class="layui-form-label">总需人次</label>'+
					    '<div class="layui-input-block layui-pr layui-pr-must">'+
					        '<input type="number" name="goodsShares" class="layui-input">'+
					    '</div>'+
					'</div>'+
				'</div>'+
				'<div class="layui-col-md4">'+
					'<img class="goodsCover"/>'+
				'</div>'+
			'</div>'+
			'<div class="layui-col-md1">'+
		//		'<button class="layui-btn lay-edit layui-btn-sm" lay-submit lay-filter="*">编辑</button>'+
				'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
			'</div>'+
			'<div class="clear"></div>'+
		'</div>';
		  $(".buyfree .goodsList").append(html)
		  buyfreeFlag = false;
		  addUpload(index)
			$(".buyfree .goods-item"+index+" input[name='goodsTitle']").click(function(){
				var className = $(this).parent().parent().parent().parent().parent().attr('class')
				layer.open({
					type: 2, 
					title:false, 
					area: ['80%', '80%'],
					content: ['luckyGoodsChoose.html?classname='+className.substring(className.indexOf(' ')+1)] 
				});
			});
		  	$(".buyfree .layui-upload button").click(function(){
				uploadClass = $(this).parent().parent().parent().parent().parent().parent().attr('class')
				uploadClass = uploadClass.substring(uploadClass.indexOf(' ')+1)
			})
			$(".buyfree .lay-delete").click(function(){
				$(this).parent().parent().remove()
			})
			index = index + 1;
	});
	function addUpload(index){
		upload.render({
		    elem: '.buyfree .goods-item'+index+' #goodsList'
		    ,url: '../back/resource/add' //改成您自己的上传接口
		    ,multiple: true
		    ,before: function(obj){
		      //预读本地文件示例，不支持ie8
		    }
		    ,done: function(res){
		    	$('.buyfree .'+uploadClass+' input[name="image"]').val(res.data.uuid);
		    	$('.buyfree .'+uploadClass+' .goodsCover').attr('src','..' + res.data.url); 
		    }
		});
	}
	//监听内容是否发生变化
	function addListenBuyfree(){
		$(".buyfree input").on("change",function(){
			buyfreeFlag = false;
		})
	}
	//更改状态
	function changeStatus(uuid,status){
		$.ajax({
	        url: '../back/activity/changeStatus',
	        type: 'post',
	        async:false,
	//        traditional: true,
	        data: {
	        	'uuid':uuid,
	        	'status':status
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				activityList()
				if(uuid=='buyfree'){
			    	getBuyfree();
					getGoodsList();
				  }else if(uuid=='checkin'){
			    	getCheckin();
			    	getCheckGoodsList();
				  }else if(uuid=='scorelottery'){
			    	getScorelottery()
			    	getScorelotteryGoodsList()
				  }else if(uuid=='firstcharge'){
			    	getFirstcharge()
			    	getFirstchargeList()
				  }else if(uuid=='recharge'){
					getRecharge()
				    getRechargeList()
				  }else if(uuid=='recommend'){
					getRecommend()
				  }
			}else{
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
	    })
		return false;
	}

	/**
	 * 活动营销--签到活动
	 */
	// 获取签到活动基础信息
	function getCheckin(){
		$.ajax({
	        url: '../back/activity/get',
	        type: 'get',
	        async:false,
	        data: {
	        	'uuid':'checkin'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				  checkinFlag = true
				  //日期
				  laydate.render({
					    elem: '.checkIn .activityTime'
					    ,value: r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
					    ,type: 'datetime'
					    ,range:'-'
						,theme: '#3D99FF'
				    	,done: function(value, date){
				    		checkInselectDate = value;
				        }
				  });
				  $(".checkIn input[name='bannerUrl']").val(r.data.bannerUrl)
				  checkInactivityInfo = r.data.name
				  checkInselectDate = r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
				  if(r.data.status=="disable"){
					  $(".checkIn .lay-startActivity").show()
					  $(".checkIn .lay-closeActivity").hide()
				  }else{
					  $(".checkIn .lay-startActivity").hide()
					  $(".checkIn .lay-closeActivity").show()
				  }
				  $(".checkIn .lay-startActivity").unbind().click(function(){
					  if(checkinFlag){
						  changeStatus('checkin','normal')
					  }else{
						  layer.msg("请先保存设置再开启活动")  
					  }  
				  })
				  $(".checkIn .lay-closeActivity").unbind().click(function(){
					  changeStatus('checkin','disable')
				  })
			}
	    })
	}
	$(".checkIn .lay-submit").click(function(){
		for(var m=0;m<$(".checkIn .goods-item").length;m++){
			if($(".checkIn .goods-item:eq("+m+")").find("input[name='goodsTitle']").val()==""){
				layer.msg("有未填写奖品名称的选项");
				return false;
			}else if($(".checkIn .goods-item:eq("+m+")").find("input[name='prizeType']:checked").val()=="voucher"&&
					$(".checkIn .goods-item:eq("+m+")").find(".voucher-box input[name='goodsId']").val()==""){
				layer.msg("有勾选金券未选择金券的选项");
				return false;
			}else if($(".checkIn .goods-item:eq("+m+")").find("input[name='prizeType']:checked").val()=="gold"&&
					$(".checkIn .goods-item:eq("+m+")").find(".gold-box input[name='title']").val()==""){
				layer.msg("有勾选金币未选择金币的选项");
				return false;
			}else if($(".checkIn .goods-item:eq("+m+")").find("input[name='prizeType']:checked").val()=="entity"&&
					$(".checkIn .goods-item:eq("+m+")").find(".entity-box input[name='goodsId']").val()==""){
				layer.msg("有勾选实物未选择实物的选项");
				return false;
			}
		}
		var data = {},details = [],detailItem = {};
		data['uuid'] = 'checkin';
		data['starttime'] = checkInselectDate.substring(0,19)
		data['endtime'] = checkInselectDate.substring(22)
		data['configuration'] =''
		data['bannerUrl'] = $(".checkIn .bannerUrl").val().replace(/(^\s*)|(\s*$)/g, "")
		for(var i=0;i<checkInindex;i++){
			detailItem = {}
			if($(".checkIn .goods-item"+i).find("input[name='uuid']").val()==""){
				detailItem['uuid']=''
				detailItem['activityInfo']=checkInactivityInfo
				detailItem['type']='add'
				detailItem['prizeTitle']=$(".checkIn .goods-item"+i).find("input[name='goodsTitle']").val()
				detailItem['prizeType']=$(".checkIn .goods-item"+i).find("input[name='prizeType']:checked").val()
				detailItem['prizeCover']=$(".checkIn .goods-item"+i).find("input[name='image']").val()?
						$(".checkIn .goods-item"+i).find("input[name='image']").val():''
				if($(".checkIn .goods-item"+i).find("input[name='prizeType']:checked").val()=="voucher"){
				    detailItem['prize']=$(".checkIn .goods-item"+i+" .voucher-box input[name='goodsId']").val()
				}else if($(".checkIn .goods-item"+i).find("input[name='prizeType']:checked").val()=="entity"){
				    detailItem['prize']=$(".checkIn .goods-item"+i+" .entity-box input[name='goodsId']").val()
				}else{
					detailItem['prize']=$(".checkIn .goods-item"+i+" .layui-gold").val()
				}
				detailItem['dayNum']= i+1
				detailItem['sort']= i
				details.push(detailItem)
			}
		}
		for(var j=0;j<checkIngoodsList.length;j++){
			detailItem = {}
			var ishas = false;
			for(var i=0;i<checkInindex;i++){
				if(checkIngoodsList[j].uuid==$(".checkIn .goods-item:eq("+i+")").find("input[name='uuid']").val()){
					ishas = true;
					detailItem['prizeTitle']=$(".checkIn .goods-item:eq("+i+")").find("input[name='goodsTitle']").val()
					detailItem['prizeType']=$(".checkIn .goods-item:eq("+i+")").find("input[name='prizeType']:checked").val()
					detailItem['prizeCover']=$(".checkIn .goods-item:eq("+i+")").find("input[name='image']").val()?
							$(".checkIn .goods-item:eq("+i+")").find("input[name='image']").val():''
					if($(".checkIn .goods-item:eq("+i+")").find("input[name='prizeType']:checked").val()=="voucher"){
					    detailItem['prize']=$(".checkIn .goods-item:eq("+i+") .voucher-box input[name='goodsId']").val()
					}else if($(".checkIn .goods-item:eq("+i+")").find("input[name='prizeType']:checked").val()=="entity"){
					    detailItem['prize']=$(".checkIn .goods-item:eq("+i+") .entity-box input[name='goodsId']").val()
					}else{
						detailItem['prize']=$(".checkIn .goods-item:eq("+i+") .layui-gold").val()
					}
				}
			}
			if(ishas){
				detailItem['type']="edit"
			}else{
				detailItem['type']="delete"
				detailItem['prizeTitle']=checkIngoodsList[j].prizeTitle
				detailItem['prizeType']=checkIngoodsList[j].prizeType
				detailItem['prizeCover']=checkIngoodsList[j].prizeCover
				detailItem['prize']=checkIngoodsList[j].prize
			}
			detailItem['uuid']=checkIngoodsList[j].uuid
			detailItem['activityInfo']=checkInactivityInfo
			
			detailItem['dayNum']= j+1
			detailItem['sort'] = j
			details.push(detailItem)
		}
		data['details']= JSON.stringify(details)
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../back/activity/edit',
	        type: 'post',
	        async:false,
	//        traditional: true,
	        data: data
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				checkInFlag = true
				layer.msg('设置成功')
				getCheckin();
				getCheckGoodsList();
			}else {
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
	    })
		return false;
	})
	//获取签到活动详细设置
	function getCheckGoodsList(){
		$.ajax({
	        url: '../back/activityCheckin/prizelist',
	        type: 'get',
	        async:false,
	        data: {
	        	'sort':'sort asc'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				var html = '';
				checkIngoodsList = r.data
				checkInindex = r.data.length
				for(var i=0;i<r.data.length;i++){
					html += '<div class="goods-item goods-item'+i+'">'+
								'<div class="layui-col-md11">'+
									'<p class="layui-col-md12 day-number">第 '+(i+1)+' 天</p>'+
									'<div class="layui-col-md6">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">奖品名称</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must">'+
										        '<input type="text" name="goodsTitle" class="layui-input" value="'+r.data[i].prizeTitle+'">'+
										        '<input type="hidden" name="uuid" class="layui-input" value="'+r.data[i].uuid+'">'+
										    '</div>'+
										'</div>'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">奖品封面</label>'+
										    '<div class="layui-input-block">'+
										        '<div class="layui-upload">'+
													  '<button type="button" class="layui-btn layui-btn-sm" id="goodsList">上传图片</button>'+
													  '<p class="layui-tips">非必填，建议尺寸：32*32px</p>'+
													  '<input type="hidden" name="image" value="'+(r.data[i].prizeCover?r.data[i].prizeCover:'')+'" >'+
												'</div>'+
												'<img style="max-height:32px;'+(r.data[i].prizeCoverUrl?"display:block":"display:none")+'" class="goodsCover" src="..'+r.data[i].prizeCoverUrl+'" />'+
										    '</div>'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md6">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">奖品设置</label>'+
										    '<div class="layui-input-block">'+
										    '<form class="layui-form" action="" lay-filter="first'+i+'">'+
										    	'<div class="mb-15 voucher-box">'+
										    		'<input type="radio" name="prizeType" value="voucher" title="金券 送" checked lay-filter="jiedian">'+
										    		'<input type="text" name="title" readonly class="layui-input layui-voucher" placeholder="请选择金券" value="'
										    		+(r.data[i].prizeType=="voucher"?r.data[i].prizeDetail:"")+'" '+
										    		'style="display:inline-block;width:50%;">'+
										    		'<input type="hidden" name="goodsId" class="layui-input" value="'+r.data[i].prize+'">'+
										    	'</div>'+									        
										        '<div class="gold-box mb-15">'+
										        	'<input type="radio" name="prizeType" value="gold" title="金币 送" lay-filter="jiedian">'+
										    		'<input type="text" name="title" class="layui-input layui-gold" onkeyup="integer(this)" value="'
										        	+(r.data[i].prizeType=="gold"?r.data[i].prize:"")+'" '+
										    		'style="display:inline-block;width:50%;">'+
										    	'</div>'+								        
										        '<div class="entity-box">'+
										        	'<input type="radio" name="prizeType" value="entity" title="实物 送" lay-filter="jiedian">'+
										    		'<input type="text" name="title" readonly class="layui-input layui-entity" placeholder="请选择实物" value="'
										        	+(r.data[i].prizeType=="entity"?r.data[i].prizeDetail:"")+'" '+
										    		'style="display:inline-block;width:50%;">'+
										    		'<input type="hidden" name="goodsId" class="layui-input" value="'+r.data[i].prize+'">'+
										    	'</div>'+
									        '</form>'+
										    '</div>'+
										'</div>'+
									'</div>'+
//									'<div class="layui-col-md4">'+
//										'<img class="goodsCover" src="..'+r.data[i].goodsCoverUrl+'" />'+
//									'</div>'+
								'</div>'+
								'<div class="layui-col-md1">'+
//									'<button class="layui-btn lay-edit layui-btn-sm" lay-submit lay-filter="*">编辑</button>'+
									'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
								'</div>'+
								'<div class="clear"></div>'+
							'</div>';
				}
				$(".checkIn .goodsList").html(html)
				form.render();
				addListenCheckin()
				for(var i=0;i<r.data.length;i++){
					upload.render({
					    elem: '.checkIn .goods-item'+i+' #goodsList'
					    ,url: '../back/resource/add' //改成您自己的上传接口
					    ,multiple: true
					    ,before: function(obj){
					      //预读本地文件示例，不支持ie8
					    }
					    ,done: function(res,index){
					    	$('.checkIn .'+uploadClass+' input[name="image"]').val(res.data.uuid);
					    	$('.checkIn .'+uploadClass+' .goodsCover').attr('src','..' + res.data.url).show(); 
					    }
					  });
				}
				$(".checkIn .layui-upload button").click(function(){
					uploadClass = $(this).parent().parent().parent().parent().parent().parent().attr('class')
					uploadClass = uploadClass.substring(uploadClass.indexOf(' ')+1)
				})
				$(".checkIn .layui-entity").unbind().click(function(){
					var className = $(this).parent().parent().parent().parent().parent().parent().parent().attr('class')
					layer.open({
						type: 2, 
						title:false, 
						area: ['80%', '80%'],
						content: ['luckyGoodsChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=1'] 
					});
				})
				$(".checkIn .layui-voucher").unbind().click(function(){
					var className = $(this).parent().parent().parent().parent().parent().parent().parent().attr('class')
					layer.open({
						type: 2, 
						title:false, 
						area: ['80%', '80%'],
						content: ['voucherChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=1&type=voucher'] 
					});
				})
				$(".checkIn form").each(function(index){
					form.val('first'+index,checkIngoodsList[index]);
					form.on('radio(jiedian)', function(data){
						checkinFlag = false;
					});
				})
				$(".checkIn .lay-delete").click(function(){
					$(this).parent().parent().remove()
					for(var j=0;j<$(".checkIn .goods-item").length;j++){
					    $(".checkIn .goods-item:eq("+j+")").find(".day-number").html("第 "+(j+1)+" 天")
				    }
				})
			}
	    })
	}
	//新增
	$(".checkIn .lay-addNew").click(function(){
		var html = '<div class="goods-item goods-item'+checkInindex+'">'+
			'<div class="layui-col-md11">'+
				'<p class="layui-col-md12 day-number">第 '+($(".checkIn .goods-item").length+1)+' 天</p>'+
				'<div class="layui-col-md6">'+
					'<div class="layui-col-md12 mb-15">'+
					    '<label class="layui-form-label">奖品名称</label>'+
					    '<div class="layui-input-block layui-pr layui-pr-must">'+
					        '<input type="text" name="goodsTitle" class="layui-input">'+
					        '<input type="hidden" name="goodsId" class="layui-input">'+
					        '<input type="hidden" name="uuid" class="layui-input" value="">'+
					    '</div>'+
					'</div>'+
					'<div class="layui-col-md12 mb-15">'+
					    '<label class="layui-form-label">奖品封面</label>'+
					    '<div class="layui-input-block">'+
					        '<div class="layui-upload">'+
								  '<button type="button" class="layui-btn layui-btn-sm" id="goodsList">上传图片</button>'+
								  '<p class="layui-tips">非必填，建议尺寸：32*32px</p>'+
								  '<input type="hidden" name="image" >'+
							'</div>'+
							'<img style="max-height:32px;display:none" class="goodsCover" src="" />'+
					    '</div>'+
					'</div>'+
				'</div>'+
				'<div class="layui-col-md6">'+
					'<div class="layui-col-md12 mb-15">'+
					    '<label class="layui-form-label">奖品设置</label>'+
					    '<div class="layui-input-block">'+
					    	'<form class="layui-form" action="" lay-filter="first">'+
						    	'<div class="mb-15 voucher-box">'+
						    		'<input type="radio" name="prizeType" value="voucher" title="金券 送" checked lay-filter="jiedian">'+
						    		'<input type="text" name="title" readonly class="layui-input layui-voucher" placeholder="请选择金券" value="" '+
						    		'style="display:inline-block;width:50%;">'+
						    		'<input type="hidden" name="goodsId" class="layui-input" value="">'+
						    	'</div>'+									        
						        '<div class="gold-box mb-15">'+
						        	'<input type="radio" name="prizeType" value="gold" title="金币 送" lay-filter="jiedian">'+
						    		'<input type="text" name="title" class="layui-input layui-gold" onkeyup="integer(this)" value="" '+
						    		'style="display:inline-block;width:50%;">'+
						    	'</div>'+								        
						        '<div class="entity-box">'+
						        	'<input type="radio" name="prizeType" value="entity" title="实物 送" lay-filter="jiedian">'+
						    		'<input type="text" name="title" readonly class="layui-input layui-entity" placeholder="请选择实物" value="" '+
						    		'style="display:inline-block;width:50%;">'+
						    		'<input type="hidden" name="goodsId" class="layui-input" value="">'+
						    	'</div>'+
					        '</form>'+
					    '</div>'+
					'</div>'+
				'</div>'+
			'</div>'+
			'<div class="layui-col-md1">'+
		//		'<button class="layui-btn lay-edit layui-btn-sm" lay-submit lay-filter="*">编辑</button>'+
				'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
			'</div>'+
			'<div class="clear"></div>'+
		'</div>';
		  $(".checkIn .goodsList").append(html)
		  form.render();
		  checkinFlag = false;
		  checkAddUpload(checkInindex)
		  $(".checkIn .layui-entity").unbind().click(function(){
				var className = $(this).parent().parent().parent().parent().parent().parent().parent().attr('class')
				layer.open({
					type: 2, 
					title:false, 
					area: ['80%', '80%'],
					content: ['luckyGoodsChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=1'] 
				});
			})
			$(".checkIn .layui-voucher").unbind().click(function(){
				var className = $(this).parent().parent().parent().parent().parent().parent().parent().attr('class')
				layer.open({
					type: 2, 
					title:false, 
					area: ['80%', '80%'],
					content: ['voucherChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=1&type=voucher'] 
				});
			})	
			$(".checkIn .layui-upload button").click(function(){
				uploadClass = $(this).parent().parent().parent().parent().parent().parent().attr('class')
				uploadClass = uploadClass.substring(uploadClass.indexOf(' ')+1)
			})
	      $(".checkIn .lay-delete").click(function(){
			  $(this).parent().parent().remove()
			  for(var j=0;j<$(".checkIn .goods-item").length;j++){
				  $(".checkIn .goods-item:eq("+j+")").find(".day-number").html("第 "+(j+1)+" 天")
			  }
		  })
		  checkInindex = checkInindex + 1;
	});
	//监听内容是否发生变化
	function addListenCheckin(){
		$(".checkIn input").on("change",function(){
			checkinFlag = false;
		})
	}
	function checkAddUpload(index){
		upload.render({
		    elem: '.checkIn .goods-item'+index+' #goodsList'
		    ,url: '../back/resource/add' //改成您自己的上传接口
		    ,multiple: true
		    ,before: function(obj){
		      //预读本地文件示例，不支持ie8
		    }
		    ,done: function(res){
		    	$('.checkIn .'+uploadClass+' input[name="image"]').val(res.data.uuid);
		    	$('.checkIn .'+uploadClass+' .goodsCover').attr('src','..' + res.data.url).show(); 
		    }
		});
	}
	// 获取积分转盘活动基础信息
	function getScorelottery(){
		$.ajax({
	        url: '../back/activity/get',
	        type: 'get',
	        async:false,
	        data: {
	        	'uuid':'scorelottery'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				  scoreFlag = true
				  //日期
				  laydate.render({
					    elem: '.scorelottery .activityTime'
					    ,value: r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
					    ,type: 'datetime'
					    ,range:'-'
						,theme: '#3D99FF'
				    	,done: function(value, date){
				    		scorelotteryselectDate = value;
				        }
				  });
				  $(".scorelottery input[name='bannerUrl']").val(r.data.bannerUrl)
				  scorelotteryactivityInfo = r.data.name
				  scorelotteryselectDate = r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
				  if(r.data.configuration){
					  $(".scorelottery input[name='timesline']").val(JSON.parse(r.data.configuration).timesLine)
					  $(".scorelottery input[name='scoreline']").val(JSON.parse(r.data.configuration).scoreLine)
				  }
				  if(r.data.status=="disable"){
					  $(".scorelottery .lay-startActivity").show()
					  $(".scorelottery .lay-closeActivity").hide()
				  }else{
					  $(".scorelottery .lay-startActivity").hide()
					  $(".scorelottery .lay-closeActivity").show()
				  }
				  $(".scorelottery .lay-startActivity").unbind().click(function(){
					  if(scoreFlag){
						  changeStatus('scorelottery','normal')
					  }else{
						  layer.msg("请先保存设置再开启活动")  
					  }  
				  })
				  $(".scorelottery .lay-closeActivity").unbind().click(function(){
					  changeStatus('scorelottery','disable')
				  })
			}
	    })
	}
	$(".scorelottery .lay-submit").click(function(){
		var total = 0;
		for(var m=0;m<$(".scorelottery .goods-item").length;m++){
			console.log($(".scorelottery .goods-item:eq("+m+")"))
			if($(".scorelottery .goods-item:eq("+m+")").find("input[name='goodsId']").val()==""&&
			   $(".scorelottery .goods-item:eq("+m+")").find(".layui-gold").val()==""){
				layer.msg("有未选择商品的选项");
				return false;
			}else if($(".scorelottery .goods-item:eq("+m+")").find("input[name='winningRate']").val()==""){
				layer.msg("有未设置中奖概率的选项");
				return false;
			}else if($(".scorelottery .goods-item:eq("+m+")").find("input[name='goodsTitle']").val()==""){
				layer.msg("有未填写奖品名称的选项");
				return false;
			}else if($(".scorelottery .goods-item:eq("+m+")").find("input[name='prizeType']:checked").val()=="entity" && 
					$(".scorelottery .goods-item:eq("+m+")").find("input[name='image']").val()==""){
				layer.msg("实物选项有未上传图片的选项");
				return false;
			}
			total += Number($(".scorelottery .goods-item:eq("+m+")").find("input[name='winningRate']").val())
		}
		if(total!=100 && $(".scorelottery .goods-item").length!=0){
			layer.msg("中奖概率总和应为100");
			return false;
		}
		var data = {},details = [],detailItem = {};
		data['uuid'] = 'scorelottery';
		data['starttime'] = scorelotteryselectDate.substring(0,19)
		data['endtime'] = scorelotteryselectDate.substring(22)
		data['configuration'] ='{"timesLine":'+$(".scorelottery .timesline").val().replace(/(^\s*)|(\s*$)/g, "")+
								',"scoreLine":"'+($(".scorelottery .scoreline").val().replace(/(^\s*)|(\s*$)/g, "")==""
								?0:$(".scorelottery .scoreline").val().replace(/(^\s*)|(\s*$)/g, ""))+'"}'
		data['bannerUrl'] = $(".scorelottery .bannerUrl").val().replace(/(^\s*)|(\s*$)/g, "")
		for(var i=0;i<scorelotteryindex;i++){
			detailItem = {}
			if($(".scorelottery .goods-item"+i).find("input[name='uuid']").val()==""){				
				detailItem['uuid']=''
				detailItem['activityInfo']=scorelotteryactivityInfo
				detailItem['type']='add'
				detailItem['prizeTitle']=$(".scorelottery .goods-item"+i).find("input[name='goodsTitle']").val()
				detailItem['prizeType']=$(".scorelottery .goods-item"+i).find("input[name='prizeType']:checked").val()
				detailItem['prizeCover']=$(".scorelottery .goods-item"+i).find("input[name='image']").val()
				if($(".scorelottery .goods-item"+i).find("input[name='prizeType']:checked").val()=="entity"||
				   $(".scorelottery .goods-item"+i).find("input[name='prizeType']:checked").val()=="voucher"){
				   detailItem['prize']=$(".scorelottery .goods-item"+i+" input[name='goodsId']").val()
				}else{
					detailItem['prize']=$(".scorelottery .goods-item"+i+" .layui-gold").val()
				}
				detailItem['winningRate']= $(".scorelottery .goods-item"+i).find("input[name='winningRate']").val()
				detailItem['sort']= i
				details.push(detailItem)
			}
		}
		for(var j=0;j<scorelotterygoodsList.length;j++){
			detailItem = {}
			var ishas = false;
			for(var i=0;i<scorelotteryindex;i++){
				if(scorelotterygoodsList[j].uuid==$(".scorelottery .goods-item"+i).find("input[name='uuid']").val()){
					ishas = true;
					detailItem['prizeTitle']=$(".scorelottery .goods-item"+i).find("input[name='goodsTitle']").val()
					detailItem['prizeType']=$(".scorelottery .goods-item"+i).find("input[name='prizeType']:checked").val()
					detailItem['prizeCover']=$(".scorelottery .goods-item"+i).find("input[name='image']").val()
					if($(".scorelottery .goods-item"+i).find("input[name='prizeType']:checked").val()=="entity"||
					   $(".scorelottery .goods-item"+i).find("input[name='prizeType']:checked").val()=="voucher"){
					   detailItem['prize']=$(".scorelottery .goods-item"+i+" input[name='goodsId']").val()
					}else{
						detailItem['prize']=$(".scorelottery .goods-item"+i+" .layui-gold").val()
					}
					detailItem['winningRate']= $(".scorelottery .goods-item"+i).find("input[name='winningRate']").val()
				}
			}
			if(ishas){
				detailItem['type']="edit"
			}else{
				detailItem['type']="delete"
				detailItem['prizeTitle']=scorelotterygoodsList[j].prizeTitle
				detailItem['prizeType']=scorelotterygoodsList[j].prizeType
				detailItem['prizeCover']=scorelotterygoodsList[j].prizeCover
				detailItem['prize']=scorelotterygoodsList[j].prize
				detailItem['winningRate']= scorelotterygoodsList[j].winningRate
			}
			detailItem['uuid']=scorelotterygoodsList[j].uuid
			detailItem['activityInfo']=scorelotteryactivityInfo
			detailItem['sort'] = j
			details.push(detailItem)
		}

		data['details']= JSON.stringify(details)
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../back/activity/edit',
	        type: 'post',
	        async:false,
	//        traditional: true,
	        data: data
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				scoreFlag = true
				layer.msg('设置成功')
				getScorelottery();
				getScorelotteryGoodsList();
			}else {
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
	    })
		return false;
	})
	//获取积分转盘详细设置
	function getScorelotteryGoodsList(){
		$.ajax({
	        url: '../back/activityScorelottery/prizelist',
	        type: 'get',
	        async:false,
	        data: {
	        	'sort':'sort asc'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				var html = '';
				scorelotterygoodsList = r.data
				scorelotteryindex = r.data.length
				for(var i=0;i<r.data.length;i++){
					html += '<div class="goods-item goods-item'+i+'">'+
								'<div class="layui-col-md11">'+
									'<p class="layui-col-md12 day-number">奖项 '+(i+1)+'</p>'+
									'<div class="layui-col-md12 mb-15">'+
										'<label class="layui-form-label">奖品类型</label>'+
									    '<div class="layui-input-block">'+
											'<form class="layui-form" action="" lay-filter="first'+i+'">'+
										        '<input type="radio" name="prizeType" value="gold" title="金币" lay-filter="jiedian">'+
										        '<input type="radio" name="prizeType" value="voucher" title="金券" lay-filter="jiedian">'+
										        '<input type="radio" name="prizeType" value="entity" title="实物" lay-filter="jiedian">'+
										      '</form>'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md4">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">选择奖品</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must">'+
										        '<input type="text" name="prize" readonly class="layui-input layui-entity" placeholder="请选择奖品" value="'
										    			+r.data[i].prizeDetail+'" style="'+(r.data[i].prizeType=="gold"?"display:none":"display:block")+'">'+
										        '<input type="number" name="prize" class="layui-input layui-gold" value="'
										    			+r.data[i].prize+'" style="'+(r.data[i].prizeType=="gold"?"display:block":"display:none")+'">'+
										        '<input type="hidden" name="goodsId" class="layui-input" value="'+r.data[i].prize+'">'+
										        '<input type="hidden" name="uuid" class="layui-input" value="'+r.data[i].uuid+'">'+
										    '</div>'+
										'</div>'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">奖品名称</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must">'+
										        '<input type="text" name="goodsTitle" class="layui-input" value="'
										    			+r.data[i].prizeTitle+'">'+
										    '</div>'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md4">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">中奖概率</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must" style="position:relative">'+										    
									        	'<input type="number" onblur="winningRate(this)" class="layui-input" name="winningRate" value="'+r.data[i].winningRate+'">'+
									        	'<b style="position:absolute;right:15px;font-weight:normal;top:10px;color: gray;">%</b>'+
										    '</div>'+
										'</div>'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">奖品封面</label>'+
										    '<div class="layui-input-block">'+
										        '<div class="layui-upload">'+
													  '<button type="button" class="layui-btn layui-btn-sm" id="goodsList">上传图片</button>'+
													  '<p class="layui-tips">建议尺寸：36*36px</p>'+
													  '<input type="hidden" name="image" value="'+r.data[i].prizeCover+'" >'+
												'</div>'+
										    '</div>'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md4">'+
										'<img class="goodsCover" src="'+(r.data[i].prizeCoverUrl?'..'+r.data[i].prizeCoverUrl:'')+'" />'+
									'</div>'+
								'</div>'+
								'<div class="layui-col-md1">'+
//									'<button class="layui-btn lay-edit layui-btn-sm" lay-submit lay-filter="*">编辑</button>'+
									'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
								'</div>'+
								'<div class="clear"></div>'+
							'</div>';
				}
				$(".scorelottery .goodsList").html(html)
				form.render();
				addListenScore()
				$(".scorelottery form").each(function(index){
					form.val('first'+index,scorelotterygoodsList[index]);
					form.on('radio(jiedian)', function(data){
						scoreFlag = false;
						if(data.value=="gold"){
							$(this).parent().parent().parent().parent().find("input.layui-gold").val('').show();
							$(this).parent().parent().parent().parent().find("input.layui-entity").hide();
						}else{
							$(this).parent().parent().parent().parent().find("input.layui-gold").hide();
							$(this).parent().parent().parent().parent().find("input.layui-entity").val('').show();
						}
						$(this).parent().parent().parent().parent().find("input[name='goodsId']").val('')
					});
				})
				$(".scorelottery .layui-entity").click(function(){
					if($(this).parent().parent().parent().parent().find("input[name='prizeType']:checked").val()=="entity"){
						var className = $(this).parent().parent().parent().parent().parent().attr('class')
						layer.open({
							type: 2, 
							title:false, 
							area: ['80%', '80%'],
							content: ['luckyGoodsChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=2'] 
						});
					}else if($(this).parent().parent().parent().parent().find("input[name='prizeType']:checked").val()=="voucher"){
						var className = $(this).parent().parent().parent().parent().parent().attr('class')
						layer.open({
							type: 2, 
							title:false, 
							area: ['80%', '80%'],
							content: ['voucherChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=2'] 
						});
					}
					
				})
				for(var i=0;i<r.data.length;i++){
					upload.render({
					    elem: '.scorelottery .goods-item'+i+' #goodsList'
					    ,url: '../back/resource/add' //改成您自己的上传接口
					    ,multiple: true
					    ,before: function(obj){
					      //预读本地文件示例，不支持ie8
					    }
					    ,done: function(res,index){
					    	$('.scorelottery .'+uploadClass+' input[name="image"]').val(res.data.uuid);
					    	$('.scorelottery .'+uploadClass+' .goodsCover').attr('src','..' + res.data.url); 
					    }
					  });
				}
				$(".scorelottery .layui-upload button").click(function(){
					uploadClass = $(this).parent().parent().parent().parent().parent().parent().attr('class')
					uploadClass = uploadClass.substring(uploadClass.indexOf(' ')+1)
				})
				$(".scorelottery .lay-delete").click(function(){
					$(this).parent().parent().remove()
					for(var j=0;j<$(".scorelottery .goods-item").length;j++){
						$(".scorelottery .goods-item:eq("+j+")").find(".day-number").html("奖项 "+(j+1)+"")
					}
				})
			}
	    })
	}
	//新增
	$(".scorelottery .lay-addNew").click(function(){
		var html = '<div class="goods-item goods-item'+scorelotteryindex+'">'+
						'<div class="layui-col-md11">'+
							'<p class="layui-col-md12 day-number">奖项 '+($(".scorelottery .goods-item").length+1)+'</p>'+
							'<div class="layui-col-md12 mb-15">'+
								'<label class="layui-form-label">奖品类型</label>'+
							    '<div class="layui-input-block">'+
									'<form class="layui-form" action="" lay-filter="first'+scorelotteryindex+'">'+
								        '<input type="radio" name="prizeType" value="gold" title="金币" lay-filter="jiedian" checked>'+
								        '<input type="radio" name="prizeType" value="voucher" title="金券" lay-filter="jiedian">'+
								        '<input type="radio" name="prizeType" value="entity" title="实物" lay-filter="jiedian">'+
								      '</form>'+
								'</div>'+
							'</div>'+
							'<div class="layui-col-md4">'+
								'<div class="layui-col-md12 mb-15">'+
								    '<label class="layui-form-label">选择奖品</label>'+
								    '<div class="layui-input-block layui-pr layui-pr-must">'+
								        '<input type="text" name="prize" readonly class="layui-input layui-entity" placeholder="请选择奖品" value="'
								    			+'" style="display:none">'+
								        '<input type="number" name="prize" class="layui-input layui-gold" value="'
								    			+'" style="display:block">'+
								        '<input type="hidden" name="goodsId" class="layui-input" value="">'+
								        '<input type="hidden" name="uuid" class="layui-input" value="">'+
								    '</div>'+
								'</div>'+
								'<div class="layui-col-md12 mb-15">'+
								    '<label class="layui-form-label">奖品名称</label>'+
								    '<div class="layui-input-block layui-pr layui-pr-must">'+
								        '<input type="text" name="goodsTitle" class="layui-input" value="">'+
								    '</div>'+
								'</div>'+
							'</div>'+
							'<div class="layui-col-md4">'+
								'<div class="layui-col-md12 mb-15">'+
								    '<label class="layui-form-label">中奖概率</label>'+
								    '<div class="layui-input-block layui-pr layui-pr-must" style="position:relative">'+										    
							        	'<input type="number" class="layui-input" onblur="winningRate(this)" name="winningRate" value="">'+
							        	'<b style="position:absolute;right:15px;font-weight:normal;top:10px;color: gray;">%</b>'+
								    '</div>'+
								'</div>'+
								'<div class="layui-col-md12 mb-15">'+
								    '<label class="layui-form-label">奖品封面</label>'+
								    '<div class="layui-input-block">'+
								        '<div class="layui-upload">'+
											  '<button type="button" class="layui-btn layui-btn-sm" id="goodsList">上传图片</button>'+
											  '<p class="layui-tips">建议尺寸：36*36px</p>'+
											  '<input type="hidden" name="image" value="" >'+
										'</div>'+
								    '</div>'+
								'</div>'+
							'</div>'+
							'<div class="layui-col-md4">'+
								'<img class="goodsCover" src="" />'+
							'</div>'+
						'</div>'+
						'<div class="layui-col-md1">'+
					//		'<button class="layui-btn lay-edit layui-btn-sm" lay-submit lay-filter="*">编辑</button>'+
							'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
						'</div>'+
						'<div class="clear"></div>'+
					'</div>';
		  $(".scorelottery .goodsList").append(html)
		  form.render();
		  scoreFlag = false;
		  scorelotteryAddUpload(scorelotteryindex)
		  $(".scorelottery form").each(function(index){
				form.on('radio(jiedian)', function(data){
					if(data.value=="gold"){
						$(this).parent().parent().parent().parent().find("input.layui-gold").val('').show();
						$(this).parent().parent().parent().parent().find("input.layui-entity").hide();
					}else{
						$(this).parent().parent().parent().parent().find("input.layui-gold").hide();
						$(this).parent().parent().parent().parent().find("input.layui-entity").val('').show();
					}
					$(this).parent().parent().parent().parent().find("input[name='goodsId']").val('')
				});
			})
			$(".scorelottery .layui-entity").click(function(){
				if($(this).parent().parent().parent().parent().find("input[name='prizeType']:checked").val()=="entity"){
					var className = $(this).parent().parent().parent().parent().parent().attr('class')
					layer.open({
						type: 2, 
						title:false, 
						area: ['80%', '80%'],
						content: ['luckyGoodsChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=2'] 
					});
				}else if($(this).parent().parent().parent().parent().find("input[name='prizeType']:checked").val()=="voucher"){
					var className = $(this).parent().parent().parent().parent().parent().attr('class')
					layer.open({
						type: 2, 
						title:false, 
						area: ['80%', '80%'],
						content: ['voucherChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=2'] 
					});
				}
				
			})
			$(".scorelottery .layui-upload button").click(function(){
				uploadClass = $(this).parent().parent().parent().parent().parent().parent().attr('class')
				uploadClass = uploadClass.substring(uploadClass.indexOf(' ')+1)
			})
			$(".scorelottery .lay-delete").click(function(){
				$(this).parent().parent().remove()
				for(var j=0;j<$(".scorelottery .goods-item").length;j++){
					$(".scorelottery .goods-item:eq("+j+")").find(".day-number").html("奖项 "+(j+1)+"")
				}
			})
			scorelotteryindex = scorelotteryindex + 1;
	});
	//监听内容是否发生变化
	function addListenScore(){
		$(".scorelottery input").on("change",function(){
			scoreFlag = false;
		})
	}
	function scorelotteryAddUpload(index){
		upload.render({
		    elem: '.scorelottery .goods-item'+index+' #goodsList'
		    ,url: '../back/resource/add' //改成您自己的上传接口
		    ,multiple: true
		    ,before: function(obj){
		      //预读本地文件示例，不支持ie8
		    }
		    ,done: function(res){
		    	$('.scorelottery .'+uploadClass+' input[name="image"]').val(res.data.uuid);
		    	$('.scorelottery .'+uploadClass+' .goodsCover').attr('src','..' + res.data.url); 
		    }
		});
	}
	//首充活动基础信息
	function getFirstcharge(){
		$('.firstcharge select[name=capitalAccount]').html(capitalAccountHtml)
		form.render('select');
		$.ajax({
	        url: '../back/activity/get',
	        type: 'get',
	        async:false,
	        data: {
	        	'uuid':'firstcharge'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				  firstchargeFlag = true;
				  //日期
				  laydate.render({
					    elem: '.firstcharge .activityTime'
					    ,value: r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
					    ,type: 'datetime'
					    ,range:'-'
						,theme: '#3D99FF'
				    	,done: function(value, date){
				    		firstchargeselectDate = value;
				        }
				  });
				  $(".firstcharge input[name='bannerUrl']").val(r.data.bannerUrl)
				  firstchargeactivityInfo = r.data.name
				  firstchargeselectDate = r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
				  if(r.data.status=="disable"){
					  $(".firstcharge .lay-startActivity").show()
					  $(".firstcharge .lay-closeActivity").hide()
				  }else{
					  $(".firstcharge .lay-startActivity").hide()
					  $(".firstcharge .lay-closeActivity").show()
				  }
				  $(".firstcharge .lay-startActivity").unbind().click(function(){
					  if(firstchargeFlag){
						  changeStatus('firstcharge','normal')
					  }else{
						  layer.msg("请先保存设置再开启活动")  
					  }  
				  })
				  $(".firstcharge .lay-closeActivity").unbind().click(function(){
					  changeStatus('firstcharge','disable')
				  })
			}
	    })
	    $(".firstcharge .layui-voucher").unbind().click(function(){
			layer.open({
				type: 2, 
				title:false, 
				area: ['80%', '80%'],
				content: ['voucherChoose.html?classname=voucher-list&tab=3'] 
			});
		})
	}
	function getFirstchargeList(){
		$.ajax({
	        url: '../back/activityFirstcharge/prizelist',
	        type: 'get',
	        async:false,
	        data: {
	        	'sort':'sort asc'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				var html = '';
				firstchargegoodsList = r.data
				if(r.data.length>0){
					$(".firstcharge input[name='amount']").val(r.data[0].amount)
					$(".firstcharge select[name='capitalAccount']").val(r.data[0].capitalAccount)
					form.render('select');
					for(var i=0;i<r.data[0].listPrize.length;i++){
						html+='<li>'+
					    			'<p>'+r.data[0].listPrize[i].title+'</p>'+
									'<i class="layui-icon" style="font-size:24px;">&#xe640;</i>'+
									'<input type="hidden" name="prize" value="'+r.data[0].listPrize[i].uuid+'">'+
					    		'</li>'
					}
					$(".firstcharge .voucher-list").html(html)
					addListenFirstcharge()
					form.on('select(capitalPlatform)', function(data){
						firstchargeFlag = false;
					}); 
					$(".firstcharge .voucher-list .layui-icon").click(function(){
						$(this).parent().remove();
					})
				}
			}
	    })
	}
	//监听内容是否发生变化
	function addListenFirstcharge(){
		$(".firstcharge input").on("change",function(){
			firstchargeFlag = false;
		})
	}
	$(".firstcharge .lay-submit").click(function(){
		var prize = '';
		if($(".firstcharge .activityTime").val()==""){
			layer.msg("请设置活动有效期");
			return false;
		}else if($(".firstcharge .bannerUrl").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写banner图地址");
			return false;
		}else if($(".firstcharge input[name='amount']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写固定额度");
			return false;
		}
		$(".firstcharge input[name='prize']").each(function(){
			prize+=$(this).val()+','    
		});
		prize = prize.substring(0,prize.length-1)
		if(prize==""){
			layer.msg("请设置赠送的金券");
			return false;
		}
		var data = {},details = [],detailItem = {};
		data['uuid'] = 'firstcharge';
		data['starttime'] = firstchargeselectDate.substring(0,19)
		data['endtime'] = firstchargeselectDate.substring(22)
		data['configuration'] =''
		data['bannerUrl'] = $(".firstcharge .bannerUrl").val().replace(/(^\s*)|(\s*$)/g, "")
		if(firstchargegoodsList.length==0){
			detailItem['uuid']=''
			detailItem['type']='add'
		}else{
			detailItem['uuid']=firstchargegoodsList[0].uuid
			detailItem['type']='edit'
		}
		detailItem['activityInfo']=firstchargeactivityInfo
		detailItem['amount']=$(".firstcharge").find("input[name='amount']").val()
		detailItem['capitalAccount']=$(".firstcharge").find("select[name='capitalAccount']").val()
		detailItem['prizeType']='voucher'
		detailItem['prize']=prize
		detailItem['sort']=0
		details.push(detailItem)
		data['details']= JSON.stringify(details)
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../back/activity/edit',
	        type: 'post',
	        async:false,
	//        traditional: true,
	        data: data
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				firstchargeFlag = true
				layer.msg('设置成功')
				getFirstcharge();
				getFirstchargeList();
			}else {
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
	    })
		return false;
	})
	
	//充值返利基础信息
	function getRecharge(){
		$.ajax({
	        url: '../back/activity/get',
	        type: 'get',
	        async:false,
	        data: {
	        	'uuid':'recharge'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				  rechargeFlag = true;
				  //日期
				  laydate.render({
					    elem: '.recharge .activityTime'
					    ,value: r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
					    ,type: 'datetime'
					    ,range:'-'
						,theme: '#3D99FF'
				    	,done: function(value, date){
				    		rechargeselectDate = value;
				        }
				  });
				  $(".recharge input[name='bannerUrl']").val(r.data.bannerUrl)
				  rechargeactivityInfo = r.data.name
				  rechargeselectDate = r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
				  if(r.data.status=="disable"){
					  $(".recharge .lay-startActivity").show()
					  $(".recharge .lay-closeActivity").hide()
				  }else{
					  $(".recharge .lay-startActivity").hide()
					  $(".recharge .lay-closeActivity").show()
				  }
				  $(".recharge .lay-startActivity").unbind().click(function(){
					  if(rechargeFlag){
						  changeStatus('recharge','normal')
					  }else{
						  layer.msg("请先保存设置再开启活动")  
					  }  
				  })
				  $(".recharge .lay-closeActivity").unbind().click(function(){
					  changeStatus('recharge','disable')
				  })
			}
	    })
	}
	function getRechargeList(){
		$.ajax({
	        url: '../back/activityRecharge/configlist',
	        type: 'get',
	        async:false,
	        data: {
	        	'sort':'sort asc'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				var html = '';
				rechargegoodsList = r.data
				rechargeindex = r.data.length
				for(var i=0;i<r.data.length;i++){
					var voucherHtml = '';
					if(r.data[i].listPrize){
						for(var j=0;j<r.data[i].listPrize.length;j++){
							voucherHtml+='<li>'+
						    			'<p>'+r.data[i].listPrize[j].title+'</p>'+
										'<i class="layui-icon" style="font-size:24px;">&#xe640;</i>'+
										'<input type="hidden" name="prize" value="'+r.data[i].listPrize[j].uuid+'">'+
						    		'</li>'
						}
					}
					html += '<div class="goods-item goods-item'+i+'">'+
								'<div class="layui-col-md11">'+
									'<div class="layui-col-md5">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">充值送</label>'+
										    '<div class="layui-input-block">'+
										        '<form class="layui-form" action="" lay-filter="first'+i+'">'+
											    	'<div class="gold-box mb-15" style="position:relative">'+
											        	'<input type="radio" name="prizeType" value="gold" title="金币返利" lay-filter="jianting">'+
											    		'<input type="text" name="title" class="layui-input layui-gold" onkeyup="decimal(this)" value="'+
											    		(r.data[i].prizeType=="gold"?r.data[i].prize:"")+'"'+
											    		'style="display:inline-block;width:50%;">'+
											    		'<b style="font-weight:normal;margin-left:-30px;color: gray;">%</b>'+
											    		'<input type="hidden" name="uuid" class="layui-input" value="'+r.data[i].uuid+'">'+
											    	'</div>'+
											    	'<div class="mb-15 voucher-box">'+
											    		'<input type="radio" name="prizeType" value="voucher" title="金券返利" lay-filter="jianting">'+
											    		'<input type="text" name="title" readonly class="layui-input layui-voucher" placeholder="请选择金券" value=""'+ 
											    		'style="display:inline-block;width:50%;">'+
											    	'</div>	'+
										    	'</form>'+
										    	'<ul class="voucher-list">'+
										    		voucherHtml+
										    	'</ul>'+
										    '</div>'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md5 layui-col-md-offset1">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label" style="width:160px;">充值渠道</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must" style="margin-left:160px;">'+
										    	'<form class="layui-form" action="" lay-filter="first">'+
											        '<select name="capitalAccount" lay-filter="capitalPlatform">'+
														capitalAccountHtml+
													'</select>'+
												'</form>'+
										    '</div>'+
										'</div>'+
									'</div>'+
								'</div>'+
								'<div class="layui-col-md1">'+
									'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
								'</div>'+
								'<div class="clear"></div>'+
							'</div>';					
				}
				$(".recharge .goodsList").html(html)
				addListenRecharge()
				for(var i=0;i<r.data.length;i++){
					form.val('first'+i,rechargegoodsList[i]);
					$(".recharge .goods-item"+i+" select[name='capitalAccount']").val(r.data[i].capitalAccount)
					form.render();
				}
				$(".recharge .voucher-list .layui-icon").click(function(){
					$(this).parent().remove();
				})
				$(".recharge .lay-delete").click(function(){
					$(this).parent().parent().remove()
				})
				form.on('select(capitalPlatform)', function(data){
					rechargeFlag = false;
				}); 
				form.on('radio(jianting)', function(data){
					rechargeFlag = false;
				}); 
			    $(".recharge .layui-voucher").click(function(){
			    	var className = $(this).parent().parent().parent().parent().parent().parent().parent().attr('class')
					layer.open({
						type: 2, 
						title:false, 
						area: ['80%', '80%'],
						content: ['voucherChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=4'] 
					});
				})
			}
	    })
	}
	//新增
	$(".recharge .lay-addNew").click(function(){
		var html = '<div class="goods-item goods-item'+rechargeindex+'">'+
						'<div class="layui-col-md11">'+
							'<div class="layui-col-md5">'+
								'<div class="layui-col-md12 mb-15">'+
								    '<label class="layui-form-label">充值送</label>'+
								    '<div class="layui-input-block">'+
								        '<form class="layui-form" action="" lay-filter="first">'+
									    	'<div class="gold-box mb-15" style="position:relative">'+
									        	'<input type="radio" name="prizeType" value="gold" title="金币返利" checked lay-filter="jianting">'+
									    		'<input type="text" name="title" class="layui-input layui-gold" onkeyup="decimal(this)" value=""'+
									    		'style="display:inline-block;width:50%;">'+
									    		'<b style="font-weight:normal;margin-left:-30px;color: gray;">%</b>'+
									    		'<input type="hidden" name="uuid" class="layui-input" value="">'+
									    	'</div>'+
									    	'<div class="mb-15 voucher-box">'+
									    		'<input type="radio" name="prizeType" value="voucher" title="金券返利" lay-filter="jianting">'+
									    		'<input type="text" name="title" readonly class="layui-input layui-voucher" placeholder="请选择金券" value=""'+ 
									    		'style="display:inline-block;width:50%;">'+
									    	'</div>	'+
								    	'</form>'+
								    	'<ul class="voucher-list">'+
								    	'</ul>'+
								    '</div>'+
								'</div>'+
							'</div>'+
							'<div class="layui-col-md5 layui-col-md-offset1">'+
								'<div class="layui-col-md12 mb-15">'+
								    '<label class="layui-form-label" style="width:160px;">充值渠道</label>'+
								    '<div class="layui-input-block layui-pr layui-pr-must" style="margin-left:160px;">'+
								    	'<form class="layui-form" action="" lay-filter="first">'+
									        '<select name="capitalAccount" lay-filter="capitalPlatform">'+
												capitalAccountHtml+
											'</select>'+
										'</form>'+
								    '</div>'+
								'</div>'+
							'</div>'+
						'</div>'+
						'<div class="layui-col-md1">'+
							'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
						'</div>'+
						'<div class="clear"></div>'+
				'</div>';
		$(".recharge .goodsList").append(html)
		form.render();
		rechargeFlag = false;
		$(".recharge .layui-voucher").unbind().click(function(){
	    	var className = $(this).parent().parent().parent().parent().parent().parent().parent().attr('class')
			layer.open({
				type: 2, 
				title:false, 
				area: ['80%', '80%'],
				content: ['voucherChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=4'] 
			});
		})
		$(".recharge .lay-delete").click(function(){
			$(this).parent().parent().remove()
		})
		rechargeindex = rechargeindex + 1;
	})
	//监听内容是否发生变化
	function addListenRecharge(){
		$(".recharge input").on("change",function(){
			rechargeFlag = false;
		})
	}
	$(".recharge .lay-submit").click(function(){
		var prize = '';
		if($(".recharge .activityTime").val()==""){
			layer.msg("请设置活动有效期");
			return false;
		}else if($(".recharge .bannerUrl").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写banner图地址");
			return false;
		}
		for(var m=0;m<$(".recharge .goods-item").length;m++){
			prize = ''
			if($(".recharge .goods-item:eq("+m+")").find("input[name='prizeType']:checked").val()=="voucher"){
				$(".recharge .goods-item:eq("+m+") input[name='prize']").each(function(){
					prize+=$(this).val()+','    
				});
				prize = prize.substring(0,prize.length-1)
				if(prize==""){
					layer.msg("有勾选金券未选择金券的选项");
					return false;
				}
			}else if($(".recharge .goods-item:eq("+m+")").find("input[name='prizeType']:checked").val()=="gold"&&
					$(".recharge .goods-item:eq("+m+")").find(".gold-box input[name='title']").val()==""){
				layer.msg("有勾选金币未选择金币的选项");
				return false;
			}else if($(".recharge .goods-item:eq("+m+") select[name='capitalAccount']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
				layer.msg("请选择充值渠道");
				return false;
			}
		}
		
		var data = {},details = [],detailItem = {};
		data['uuid'] = 'recharge';
		data['starttime'] = rechargeselectDate.substring(0,19)
		data['endtime'] = rechargeselectDate.substring(22)
		data['configuration'] =''
		data['bannerUrl'] = $(".recharge .bannerUrl").val().replace(/(^\s*)|(\s*$)/g, "")
				
		for(var i=0;i<rechargeindex;i++){
			detailItem = {}
			if($(".recharge .goods-item"+i).find("input[name='uuid']").val()==""){
				detailItem['uuid']=''
				detailItem['activityInfo']=rechargeactivityInfo
				detailItem['type']='add'
				detailItem['capitalAccount']=$(".recharge .goods-item"+i).find("select[name='capitalAccount']").val()
				detailItem['prizeType']=$(".recharge .goods-item"+i).find("input[name='prizeType']:checked").val()
				if($(".recharge .goods-item"+i).find("input[name='prizeType']:checked").val()=="voucher"){
					prize = '';
					$(".recharge .goods-item"+i+" input[name='prize']").each(function(){
						prize+=$(this).val()+','    
					});
					prize = prize.substring(0,prize.length-1)
				    detailItem['prize']=prize
				}else{
					detailItem['prize']=$(".recharge .goods-item"+i+" .layui-gold").val()
				}
				detailItem['sort']=i
				details.push(detailItem)
			}
		}
		for(var j=0;j<rechargegoodsList.length;j++){
			detailItem = {}
			var ishas = false;
			for(var i=0;i<rechargeindex;i++){
				if(rechargegoodsList[j].uuid==$(".recharge .goods-item"+i).find("input[name='uuid']").val()){
					ishas = true;
					detailItem['capitalAccount']=$(".recharge .goods-item"+i).find("select[name='capitalAccount']").val()
					detailItem['prizeType']=$(".recharge .goods-item"+i).find("input[name='prizeType']:checked").val()
					if($(".recharge .goods-item"+i).find("input[name='prizeType']:checked").val()=="voucher"){
						prize = '';
						$(".recharge .goods-item"+i+" input[name='prize']").each(function(){
							prize+=$(this).val()+','    
						});
						prize = prize.substring(0,prize.length-1)
					    detailItem['prize']=prize
					}else{
						detailItem['prize']=$(".recharge .goods-item"+i+" .layui-gold").val()
					}
				}
			}
			if(ishas){
				detailItem['type']="edit"
			}else{
				detailItem['type']="delete"
				detailItem['capitalAccount']=rechargegoodsList[j].capitalAccount
				detailItem['prizeType']=rechargegoodsList[j].prizeType
				detailItem['prize']=rechargegoodsList[j].prize
			}
			detailItem['uuid']=rechargegoodsList[j].uuid
			detailItem['activityInfo']=rechargeactivityInfo
			detailItem['sort'] = j
			details.push(detailItem)
		}
		data['details']= JSON.stringify(details)
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../back/activity/edit',
	        type: 'post',
	        async:false,
	//        traditional: true,
	        data: data
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				rechargeFlag = true
				layer.msg('设置成功')
				getRecharge();
				getRechargeList();
			}else {
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
	    })
		return false;
	})
	//获取支付方式
	function getCapitalAccount(){
		$.ajax({
	        url: '../back/capitalAccount/list',
	        type: 'get',
	        async:false,
	        data: {
	        	'pageSize':1000,
	        	'pageNum':1,
	        	'sort':'sort',
	        	'status': 'normal'
	        }
	    }).done(function(r) {
	    	if (r.status == "SUCCESS") {
	    		capitalAccountHtml = '<option value="">请选择</option>'
	    		for(var i=0;i<r.data.length;i++){
	    			capitalAccountHtml = capitalAccountHtml + '<option value="'+r.data[i].uuid+'">'+r.data[i].name+'</option>';
	    		}
	    	}
	    })
	}
	// 获取邀新活动基础信息
	function getRecommend(){
		$.ajax({
	        url: '../back/activity/get',
	        type: 'get',
	        async:false,
	        data: {
	        	'uuid':'recommend'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				  var html = ''
				  recommendFlag = true
				  //日期
				  laydate.render({
					    elem: '.recommend .activityTime'
					    ,value: r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
					    ,type: 'datetime'
					    ,range:'-'
						,theme: '#3D99FF'
				    	,done: function(value, date){
				    		recommendselectDate = value;
				        }
				  });
				  $(".recommend input[name='bannerUrl']").val(r.data.bannerUrl)
				  if(r.data.configuration){
					  $(".recommend input[name='awardRate']").val(JSON.parse(r.data.configuration).awardRate)
					  $(".recommend input[name='registUrl']").val(JSON.parse(r.data.configuration).registUrl)
					  $(".recommend input[name='androidUrl']").val(JSON.parse(r.data.configuration).androidUrl)
					  $(".recommend input[name='iosUrl']").val(JSON.parse(r.data.configuration).iosUrl)
					  for(var i=0;i<r.data.configurationMap.voucher.length;i++){
						  html+='<li>'+
					    			'<p>'+r.data.configurationMap.voucher[i].title+'</p>'+
									'<i class="layui-icon" style="font-size:24px;">&#xe640;</i>'+
									'<input type="hidden" name="prize" value="'+r.data.configurationMap.voucher[i].uuid+'">'+
					    		'</li>'
					  }
					  $(".recommend .voucher-list").html(html)
					  $(".recommend .voucher-list .layui-icon").click(function(){
							$(this).parent().remove();
					  })
				  }
				  scorelotteryactivityInfo = r.data.name
				  recommendselectDate = r.data.starttime?(formatDate(r.data.starttime)+' - '+formatDate(r.data.endtime)):''
				  addListenRecommend()
				  if(r.data.status=="disable"){
					  $(".recommend .lay-startActivity").show()
					  $(".recommend .lay-closeActivity").hide()
				  }else{
					  $(".recommend .lay-startActivity").hide()
					  $(".recommend .lay-closeActivity").show()
				  }
				  $(".recommend .lay-startActivity").unbind().click(function(){
					  if(recommendFlag){
						  changeStatus('recommend','normal')
					  }else{
						  layer.msg("请先保存设置再开启活动")  
					  }  
				  })
				  $(".recommend .lay-closeActivity").unbind().click(function(){
					  changeStatus('recommend','disable')
				  })
				  $(".recommend .layui-voucher").unbind().click(function(){
					layer.open({
						type: 2, 
						title:false, 
						area: ['80%', '80%'],
						content: ['voucherChoose.html?classname=voucher-list&tab=5'] 
					});
				  })
				  $(".recommend .lay-delete").click(function(){
					$(this).parent().parent().remove()
				  })
			}
	    })
	}
	//监听内容是否发生变化
	function addListenRecommend(){
		$(".recommend input").on("change",function(){
			recommendFlag = false;
		})
	}
	function getRecommendList(){
		$.ajax({
	        url: '../back/recommendRanking/list',
	        type: 'get',
	        async:false,
	        data: {
	        	'type':'robot',
	        	'sort':'award desc'
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				var html = ''
				recommendgoodsList = r.data
				recommendindex = r.data.length
				for(var i=0;i<r.data.length;i++){
					html += '<div class="goods-item goods-item'+i+'">'+
								'<div class="layui-col-md10">'+
									'<div class="layui-col-md3">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">ID</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must">'+
										        '<input type="text" name="frontUserShowId" readonly class="layui-input" value="'+r.data[i].frontUserShowId+'">'+
										        '<input type="hidden" name="frontUser" class="layui-input" value="'+r.data[i].frontUser+'">'+
										        '<input type="hidden" name="uuid" class="layui-input" value="'+r.data[i].uuid+'">'+
										    '</div>'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md3">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">昵称</label>'+
										    '<div class="layui-input-block">'+
												'<input type="text" readonly class="layui-input" name="frontUserName" value="'+r.data[i].frontUserName+'" >'+
										    '</div>'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md3">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">获利金额</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must">'+
										        '<input type="number" name="award" class="layui-input" value="'+r.data[i].award+'">'+
										    '</div>'+
										'</div>'+
									'</div>'+
									'<div class="layui-col-md3">'+
										'<div class="layui-col-md12 mb-15">'+
										    '<label class="layui-form-label">推荐人数</label>'+
										    '<div class="layui-input-block layui-pr layui-pr-must">'+
										        '<input type="number" name="recommend" onkeyup="integer(this)" class="layui-input" value="'+r.data[i].recommend+'">'+
										    '</div>'+
										'</div>'+
									'</div>'+
								'</div>'+
								'<div class="layui-col-md1 layui-col-md-offset1">'+
//										'<button class="layui-btn lay-edit layui-btn-sm" lay-submit lay-filter="*">编辑</button>'+
									'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
								'</div>'+
								'<div class="clear"></div>'+
							'</div>';
				}
				$(".recommend .goodsList").html(html)
				$(".recommend .lay-delete").click(function(){
					$(this).parent().parent().remove()
				})
				$(".recommend input[name='frontUserShowId'],.recommend input[name='frontUserName']").unbind().click(function(){
					var className = $(this).parent().parent().parent().parent().parent().attr('class')
					layer.open({
						type: 2, 
						title:false, 
						area: ['500px', '80%'],
						content: ['robotChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=5'] 
					});
				  })
			}
	    })
	}
	//新增
	$(".recommend .lay-addNew").click(function(){
		var html = '<div class="goods-item goods-item'+recommendindex+'">'+
						'<div class="layui-col-md10">'+
						'<div class="layui-col-md3">'+
							'<div class="layui-col-md12 mb-15">'+
							    '<label class="layui-form-label">ID</label>'+
							    '<div class="layui-input-block layui-pr layui-pr-must">'+
							        '<input type="text" name="frontUserShowId" readonly class="layui-input" value="">'+
							        '<input type="hidden" name="frontUser" class="layui-input" value="">'+
							        '<input type="hidden" name="uuid" class="layui-input" value="">'+
							    '</div>'+
							'</div>'+
						'</div>'+
						'<div class="layui-col-md3">'+
							'<div class="layui-col-md12 mb-15">'+
							    '<label class="layui-form-label">昵称</label>'+
							    '<div class="layui-input-block">'+
									'<input type="text" readonly class="layui-input" name="frontUserName" value="" >'+
							    '</div>'+
							'</div>'+
						'</div>'+
						'<div class="layui-col-md3">'+
							'<div class="layui-col-md12 mb-15">'+
							    '<label class="layui-form-label">获利金额</label>'+
							    '<div class="layui-input-block layui-pr layui-pr-must">'+
							        '<input type="number" name="award" class="layui-input" value="">'+
							    '</div>'+
							'</div>'+
						'</div>'+
						'<div class="layui-col-md3">'+
							'<div class="layui-col-md12 mb-15">'+
							    '<label class="layui-form-label">推荐人数</label>'+
							    '<div class="layui-input-block layui-pr layui-pr-must">'+
							        '<input type="number" name="recommend" class="layui-input" value="" onkeyup="integer(this)">'+
							    '</div>'+
							'</div>'+
						'</div>'+
					'</div>'+
					'<div class="layui-col-md1 layui-col-md-offset1">'+
				//			'<button class="layui-btn lay-edit layui-btn-sm" lay-submit lay-filter="*">编辑</button>'+
						'<button class="layui-btn lay-delete layui-btn-sm" lay-submit lay-filter="*">删除</button>'+
					'</div>'+
					'<div class="clear"></div>'+
				'</div>';
		$(".recommend .goodsList").prepend(html)
		form.render();
		recommendFlag = false;
		$(".recommend .lay-delete").click(function(){
			$(this).parent().parent().remove()
		})
		$(".recommend input[name='frontUserShowId'],.recommend input[name='frontUserName']").unbind().click(function(){
			var className = $(this).parent().parent().parent().parent().parent().attr('class')
			layer.open({
				type: 2, 
				title:false, 
				area: ['500px', '80%'],
				content: ['robotChoose.html?classname='+className.substring(className.indexOf(' ')+1)+'&tab=5'] 
			});
		})
		recommendindex = recommendindex + 1;
	})
	$(".recommend .lay-datasubmit").click(function(){
		for(var m=0;m<$(".recommend .goods-item").length;m++){
			if($(".recommend .goods-item:eq("+m+")").find("input[name='frontUser']").val()==""){
				layer.msg("有未选择机器人的选项");
				return false;
			}else if($(".recommend .goods-item:eq("+m+")").find("input[name='award']").val()==""){
				layer.msg("有未设置获利金额的选项");
				return false;
			}else if($(".recommend .goods-item:eq("+m+")").find("input[name='recommend']").val()==""){
				layer.msg("有未设置推荐人数的选项");
				return false;
			}
		}
		var data = {},details = [],detailItem = {};
		for(var i=0;i<recommendindex;i++){
			detailItem = {}
			if($(".recommend .goods-item"+i).find("input[name='uuid']").val()==""){
				detailItem['uuid']=$(".recommend .goods-item"+i).find("input[name='uuid']").val()
				detailItem['frontUser']=$(".recommend .goods-item"+i).find("input[name='frontUser']").val()
				detailItem['type']='robot'
				detailItem['status']='normal'
				detailItem['award']=parseFloat($(".recommend .goods-item"+i).find("input[name='award']").val())
				detailItem['recommend']=Number($(".recommend .goods-item"+i).find("input[name='recommend']").val())
				details.push(detailItem)
			}
		}
		for(var j=0;j<recommendgoodsList.length;j++){
			detailItem = {}
			var ishas = false;
			for(var i=0;i<recommendindex;i++){
				if(recommendgoodsList[j].uuid==$(".recommend .goods-item"+i).find("input[name='uuid']").val()){
					ishas = true;
					detailItem['type']='robot'
					detailItem['frontUser']=$(".recommend .goods-item"+i).find("input[name='frontUser']").val()
					detailItem['award']=parseFloat($(".recommend .goods-item"+i).find("input[name='award']").val())
					detailItem['recommend']=Number($(".recommend .goods-item"+i).find("input[name='recommend']").val())
					detailItem['uuid']=$(".recommend .goods-item"+i).find("input[name='uuid']").val()
				}
			}
			if(ishas){
				detailItem['status']="normal"
			}else{
				detailItem['status']="delete"
				detailItem['type']='robot'
				detailItem['frontUser']=recommendgoodsList[j].frontUser
				detailItem['award']=recommendgoodsList[j].award
				detailItem['recommend']=recommendgoodsList[j].recommend
				detailItem['uuid']=recommendgoodsList[j].uuid
			}
			details.push(detailItem)
		}
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../back/recommendRanking/edit',
	        type: 'post',
	        async:false,
//	        traditional: true,
	        data: {
	        	'data':JSON.stringify(details)
	        }
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				recommendFlag = true
				layer.msg('设置成功')
				getRecommendList()
			}else {
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
	    })
		return false;
	})
	$(".recommend .lay-submit").click(function(){
		var prize = '';
		if($(".recommend .activityTime").val()==""){
			layer.msg("请设置活动有效期");
			return false;
		}else if($(".recommend .bannerUrl").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写banner图地址");
			return false;
		}else if($(".recommend input[name='awardRate']").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写返利比例");
			return false;
		}else if($(".recommend .registUrl").val().replace(/(^\s*)|(\s*$)/g, "")==""){
			layer.msg("请填写注册地址");
			return false;
		}
		$(".recommend input[name='prize']").each(function(){
			prize+=$(this).val()+','    
		});
		prize = prize.substring(0,prize.length-1)
		if(prize==""){
			layer.msg("请设置赠送的金券");
			return false;
		}
		var data = {},details = [],detailItem = {};
		data['uuid'] = 'recommend';
		data['starttime'] = recommendselectDate.substring(0,19)
		data['endtime'] = recommendselectDate.substring(22)
		data['configuration'] =''
		data['bannerUrl'] = $(".recommend .bannerUrl").val().replace(/(^\s*)|(\s*$)/g, "")
		detailItem['awardRate'] = $(".recommend input[name='awardRate']").val().replace(/(^\s*)|(\s*$)/g, "")
		detailItem['registUrl'] = $(".recommend .registUrl").val().replace(/(^\s*)|(\s*$)/g, "")
		detailItem['voucher'] = prize
		detailItem['androidUrl'] = $(".recommend input[name='androidUrl']").val().replace(/(^\s*)|(\s*$)/g, "")
		detailItem['iosUrl'] = $(".recommend input[name='iosUrl']").val().replace(/(^\s*)|(\s*$)/g, "")
		data['details']= JSON.stringify(detailItem)
		if(flagSubmit == false) {
			return false;
		}
		flagSubmit = false;
		setTimeout(function() {
			flagSubmit = true;
		}, 3000);
		$.ajax({
	        url: '../back/activity/edit',
	        type: 'post',
	        async:false,
	//        traditional: true,
	        data: data
	    }).done(function(r) {
			if (r.status == "SUCCESS") {
				recommendFlag = true
				layer.msg('设置成功')
				getRecommend()
			}else {
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
	    })
		return false;
	})
})

function winningRate(obj){
	obj.value = obj.value.replace(/[^\d.]/g,"");  
    obj.value = obj.value.replace(/\.{2,}/g,".");   
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d\d\d).*$/,'$1$2.$3');
    if(obj.value.indexOf(".")< 0 && obj.value !=""){
        obj.value= parseFloat(obj.value); 
    } 
}
function decimal(obj){
	obj.value = obj.value.replace(/[^\d.]/g,"");  
    obj.value = obj.value.replace(/\.{2,}/g,".");   
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
    if(obj.value.indexOf(".")< 0 && obj.value !=""){
        obj.value= parseFloat(obj.value); 
    } 
}
function integer(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");   
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$","."); 
    obj.value = obj.value.replace( /^d+$/,'$1$2.$3');
    if(obj.value.indexOf(".")< 0 && obj.value !=""){
        obj.value= parseFloat(obj.value); 
    } 
}
//给商品赋值
function goodsItem(classname,tab,type){
	if(tab&&tab=="2"){
		$(".scorelottery ."+classname+" input[name='goodsId']").val(window.localStorage.getItem("goodsId"));
		$(".scorelottery ."+classname+" input.layui-entity").val(window.localStorage.getItem("goodsName"));
	}else if(tab&&tab=="1"){
		if(type){
			$(".checkIn ."+classname+" .voucher-box input[name='goodsId']").val(window.localStorage.getItem("goodsId"));
			$(".checkIn ."+classname+" input.layui-voucher").val(window.localStorage.getItem("goodsName"));
		}else{
			$(".checkIn ."+classname+" .entity-box input[name='goodsId']").val(window.localStorage.getItem("goodsId"));
			$(".checkIn ."+classname+" input.layui-entity").val(window.localStorage.getItem("goodsName"));
		}
	}else if(tab=="3"){
		$(".firstcharge .voucher-list").append('<li>'+
    			'<p>'+window.localStorage.getItem("goodsName")+'</p>'+
				'<i class="layui-icon" style="font-size:24px;">&#xe640;</i>'+
				'<input type="hidden" name="prize" value="'+window.localStorage.getItem("goodsId")+'">'+
		'</li>')
		$(".firstcharge .voucher-list .layui-icon").click(function(){
			$(this).parent().remove();
		})
	}else if(tab=="4"){
		$(".recharge ."+classname+" .voucher-list").append('<li>'+
    			'<p>'+window.localStorage.getItem("goodsName")+'</p>'+
				'<i class="layui-icon" style="font-size:24px;">&#xe640;</i>'+
				'<input type="hidden" name="prize" value="'+window.localStorage.getItem("goodsId")+'">'+
		'</li>')
		$(".recharge ."+classname+" .voucher-list .layui-icon").click(function(){
			$(this).parent().remove();
		})
	}else if(tab=="5"){
		$(".recommend .voucher-list").append('<li>'+
    			'<p>'+window.localStorage.getItem("goodsName")+'</p>'+
				'<i class="layui-icon" style="font-size:24px;">&#xe640;</i>'+
				'<input type="hidden" name="prize" value="'+window.localStorage.getItem("goodsId")+'">'+
		'</li>')
		$(".recommend .voucher-list .layui-icon").click(function(){
			$(this).parent().remove();
		})
	}else{
		$(".buyfree ."+classname+" input[name='goodsId']").val(window.localStorage.getItem("goodsId"));
		$(".buyfree ."+classname+" input[name='goodsShortTitle']").val(window.localStorage.getItem("goodsTitle"));
		$(".buyfree ."+classname+" input[name='goodsTitle']").val(window.localStorage.getItem("goodsName"));
		$(".buyfree ."+classname+" input[name='goodsPrice']").val(window.localStorage.getItem("price"));
	}
	localStorage.clear();
}
// 邀新活动 新增机器人
function addRobot(classname,tab,type){
	if(tab=="5"){
		var ishas = false;
		for(var i=0;i<recommendindex;i++){
			if($(".recommend .goods-item"+i)){
				if(window.localStorage.getItem("goodsUuid")==$(".recommend .goods-item"+i).find("input[name='frontUser']").val())
				ishas = true
			}
		}
		if(ishas){
			layer.msg('已有该机器人数据')
		}else{
			$(".recommend ."+classname+" input[name='frontUserShowId']").val(window.localStorage.getItem("goodsId"));
			$(".recommend ."+classname+" input[name='frontUser']").val(window.localStorage.getItem("goodsUuid"));
			$(".recommend ."+classname+" input[name='frontUserName']").val(window.localStorage.getItem("goodsName"));
		}
	}
	localStorage.clear();
}



