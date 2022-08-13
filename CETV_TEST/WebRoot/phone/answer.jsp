<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>答题页面</title>
<jsp:include page="head.jsp"></jsp:include>
<script src="../js/m/swipe.js"></script>
</head>
<body data-usertestid='<s:property value="usertId" />' data-clienttype='<s:property value="clientType" />' >

	<!-- Make sure all your bars are the first things in your <body> -->
	<header class="bar bar-nav bar-nav-gs">
		
		<span class="js-prev pull-left"><span class="icon icon-left-nav"></span></span>
		<span class="js-next pull-left"><span class="icon icon-right-nav"></span></span>
		<a class="pull-right btn btn-link iwantyou" id="iwantyou" href="javascript:void(0)">交卷</a>
		<h1 class="dataformate title"><span class="timespan" id="datafomate"></span></h1>
		
		
	</header>

	<div class="content">
		<div id="questionList" class="swipe">
		<div class="swipe-wrap">
		<s:set name="optionFlag" value="{'0','A','B','C','D','E','F','G','H','I','J','K','L','M','N'}"  />
		<s:set name="titleInx" value="1" />
		
		<s:iterator value='itemData' id='item1' status="step" >

			<s:if test='%{#item1.get("isgroup")}'>
				<!-- 组合题 -->
				<!-- 显示材料题干  -->
				<div class="slide">
					<div class="question">
						<div class="qtype">(<s:property value='#item1.get("itemType_name")' />)</div>
						<s:property value='#item1.get("material")' escape="false" />
					</div>
				</div>
				
				<s:iterator value='#item1.get("data")' id='group' status="gstep" >
					<div class="slide" data-itemid='<s:property value='#group.get("id")' />'>
						
						<div class="question">
							<span class="que-num pull-right"><s:property value='#titleInx' /> <em></em></span>
							
							<div class="qtype">(<s:property value='#group.get("itemType_name")' />)
							</div>
							
							<s:set name="titleInx" value="#titleInx+1" />
							<s:property value='#group.get("data").get("stem")' escape="false" />
							
						</div>
						
						<s:if test='%{#group.get("modelType")==1}'>
							
							<!-- 选择题 -->
							<div class="options" data-modeltype="1">
								<ul class="table-view">					
									<s:iterator value='#group.get("data").get("options")' id='goption'>
										<li data-inx='<s:property value="#goption.get('inx')" />' class="table-view-cell media">
											<a href="#"> 
													<span class="options-abc pull-left"><s:property value='#optionFlag[#goption.get("inx")]' /></span>
													<div class="media-body">
														<s:property value='#goption.get("content")' escape="false" />
													</div>
											</a>
										</li>
									</s:iterator>
			
								</ul>
		
							</div>
							
						</s:if>
						<s:elseif test='%{#group.get("modelType")==2}'>
							
							<!-- 填空 -->
							<div class="options" data-modeltype="2">
								<ul class="table-view">					
									<s:iterator value='#group.get("data").get("results")' id='goption'>
										<li data-inx='<s:property value="#goption.get('inx')" />' class="table-view-cell media">
											<a href="#"> 
												<span class="options-abc pull-left"><s:property value='#goption.get("inx")' /></span>
												<div class="media-body">
													<input type="text"  />
												</div>
											</a>
										</li>
									</s:iterator>
								</ul>
							</div>
							
						</s:elseif>
						<s:elseif test='%{#group.get("modelType")==3}'>
							
							<!-- 判断 -->
							<div class="options" data-modeltype="3">
								<ul class="table-view">					
									
									<li data-inx='1' class="table-view-cell media">
										<a href="#"> 
												<span class="options-abc pull-left"><i class="fa fa-check fa-lg"></i></span>
												<div class="media-body">
													正确
												</div>
										</a>
									</li>
									<li data-inx='2' class="table-view-cell media">
										<a href="#"> 
												<span class="options-abc pull-left"><i class="fa fa-times fa-lg"></i></span>
												<div class="media-body">
													错误
												</div>
										</a>
									</li>
								</ul>
							</div>
							
						</s:elseif>
					
					</div>	
				</s:iterator>
			</s:if>
			<s:else>
				<!-- 不是组合题 -->
				<div class="slide" data-itemid='<s:property value='#item1.get("id")' />'>
					<div class="question">
						<span class="que-num pull-right"><s:property value='#titleInx' /> <em></em></span>
						
						<div class="qtype">(<s:property value='#item1.get("itemType_name")' />)
						</div>
						
						<s:set name="titleInx" value="#titleInx+1" />
						<s:property value='#item1.get("data").get("stem")' escape="false" />
						
					</div>
					
					<s:if test='%{#item1.get("modelType")==1}'>
							
						<!-- 选择题 -->
						<div class="options" data-modeltype="1">
							<ul class="table-view">
								
								<s:iterator value='#item1.get("data").get("options")' id='option'>
									<li data-inx='<s:property value="#option.get('inx')" />' class="table-view-cell media">
										<a href="#"> 
											<span class="options-abc pull-left"><s:property value='#optionFlag[#option.get("inx")]' /></span>
											<div class="media-body">
												<s:property value='#option.get("content")' escape="false" />
											</div>
										</a>
									</li>
								</s:iterator>
		
							</ul>
		
						</div>
							
					</s:if>
					<s:elseif test='%{#item1.get("modelType")==2}'>
							
						<!-- 填空 -->
						<div class="options" data-modeltype="2">
							<ul class="table-view">					
								<s:iterator value='#item1.get("data").get("results")' id='goption'>
									<li data-inx='<s:property value="#goption.get('inx')" />' class="table-view-cell media">
										<a href="#"> 
											<span class="options-abc pull-left"><s:property value='#goption.get("inx")' /></span>
											<div class="media-body">
												<input type="text"  />
											</div>
										</a>
									</li>
								</s:iterator>
							</ul>
						</div>
						
					</s:elseif>
					<s:elseif test='%{#item1.get("modelType")==3}'>
							
							<!-- 判断 -->
							<div class="options" data-modeltype="3">
								<ul class="table-view">					
									
									<li data-inx='1' class="table-view-cell media">
										<a href="#"> 
												<span class="options-abc pull-left"><i class="fa fa-check fa-lg"></i></span>
												<div class="media-body">
													正确
												</div>
										</a>
									</li>
									<li data-inx='2' class="table-view-cell media">
										<a href="#"> 
												<span class="options-abc pull-left"><i class="fa fa-times fa-lg"></i></span>
												<div class="media-body">
													错误
												</div>
										</a>
									</li>
								</ul>
							</div>
							
					</s:elseif>
					
				</div>
			</s:else>


		</s:iterator>
		</div>
		</div>
		<footer>
			<div class="footer-copyright">&copy; 2014 中国教育电视台 京ICP证101020号</div>
		</footer>

	</div>
	
	<div id="comfirmModal" class="modal">
	  <header class="bar bar-nav bar-nav-gs">
	    <a class="icon icon-close pull-right" id="closeConfirm" href="#comfirmModal"></a>
	    <h1 class="title">答题情况</h1>
	  </header>

	  <div class="content">
	    <div class="padded">
			<div class="results-list">
				<s:set name="titleInx" value="1" />
				<s:iterator value='itemData' id='item1' status="step" >
					
					<s:if test='%{#item1.get("isgroup")}'>
						<s:iterator value='#item1.get("data")' id='group' status="gstep" >
							<a data-order="<s:property value='#titleInx' />" href="#"><span><s:property value='#titleInx' /></span></a>
							<s:set name="titleInx" value="#titleInx+1" />
						</s:iterator>
					</s:if>
					<s:else>
						<a data-order="<s:property value='#titleInx' />" href="#"><span><s:property value='#titleInx' /></span></a>
						<s:set name="titleInx" value="#titleInx+1" />
					</s:else>
					
				</s:iterator>
				
			
			</div>
    	</div>
	  </div>
	  <div class="bar bar-footer bar-results">
		<a id="continueTest" href="#"><span class="icon icon-edit"></span>继续答题</a>
		<a id="confirmTest" class="submit" href="#"><span class="icon icon-check"></span>确认交卷</a>
	  </div>
	  
	</div>
	<script>
		var elem = document.getElementById('questionList');
		window.mySwipe = Swipe(elem, {
		  // startSlide: 4,
		  // auto: 3000,
		  speed : 400,
		   continuous: false,
		   disableScroll: false,
		  // stopPropagation: true,
		  callback: function(index, element) {
		  	 //console.log(index,element);	
		  },
		  transitionEnd: function(index, element) {
		  	//$(".content").animate({opacity: 0}, 500)
		  	$('.content').scrollTop(0);
		  }
		});
		
		
		String.prototype.toHHMMSS = function () {
		    var sec_num = parseInt(this, 10); // don't forget the second param
		    var hours   = Math.floor(sec_num / 3600);
		    var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
		    var seconds = sec_num - (hours * 3600) - (minutes * 60);

		    if (hours   < 10) {hours   = "0"+hours;}
		    if (minutes < 10) {minutes = "0"+minutes;}
		    if (seconds < 10) {seconds = "0"+seconds;}
		    if(hours == 0) {
		    	var time    = minutes+':'+seconds;
		    }else {
		    	var time    = hours+':'+minutes+':'+seconds;
		    }
		    return time;
		}
		
		if (!Date.now) {
		    Date.now = function() { return new Date().getTime(); };
		}
		var initTime = Date.now();
		
		function timedUpdate () {
	      update();
	      setTimeout(timedUpdate, 1000);
	   	}
	    function update(){
	    	var curTime = Date.now(),
	    		dessc = Math.floor((curTime - initTime)/1000).toString();
	      $('#datafomate').html(dessc.toHHMMSS());
	    }  
		
		Zepto(function($){
			timedUpdate()
			var len = $('.que-num').length;
			$('.que-num').each(function(){
				$(this).find('em').html(' /'+len);
			})
			$('.options').each(function(i){
				var _this = $(this),
					cell  = _this.find('.table-view-cell'),
					modelType = _this.data('modeltype'),
					index = i;
				if(modelType == 1 || modelType == 3) {//选择 判断
					cell.on('tap',function(){
						cell.removeClass('selected');
						$(this).addClass('selected');
						$('.results-list span').eq(index).addClass('done');
						mySwipe.next();
					})
					cell.on('longTap',function(e){
						e.preventDefault();
						cell.removeClass('selected');
						$('.results-list span').eq(index).removeClass('done');
					})
				}else {//填空
					_this.find('input').on('blur',function(){
						var v = $.trim($(this).val());
						if(v.length > 0) {
							$('.results-list span').eq(index).addClass('done');
						}
					})
				}
				
				
			});
			
			$('.js-next').on('tap',function(){
				mySwipe.next();
			})
			$('.js-prev').on('tap',function(){
				mySwipe.prev();
			})
			
			$('.results-list a').on('tap',function(){
				$('#comfirmModal').removeClass('active');
				mySwipe.slide($(this).data('order')-1);
			})
			
			$('#iwantyou').on('tap',function(){
				$('#comfirmModal').addClass('active');
				return false;
			})
			
			$('#confirmTest').on('click',function(){
				var options = '';
				$('.options').each(function(i){
					var _this = $(this),
						modelType = _this.data('modeltype'),
						selected  = _this.find('.selected'),
						itemid = _this.parent().data('itemid'),
						com = (i == ($('.options').length-1)) ? '' : ',';
					
					if(modelType == 1 || modelType == 3) {//选择 判断
						if(selected.length) {//已经作答
							inx = selected.data('inx');
						}else {//未作答
							inx = 0;
						}
						options += '{'
							+ '"item.id":"'+ itemid +'",'
							+ '"inx":"'+ inx +'",'
							+ '"answertime":"1",'
							+ '"content":""'
							+ '}'+com
					}else {//填空
						var input = _this.find('input'),
							inputarr = [];
						input.each(function(){
							var v = $.trim($(this).val());
							if(v.length > 0) {
								inputarr.push('"'+v+'"');
							}
						});
						options += '{'
							+ '"item.id":"'+ itemid +'",'
							+ '"inx":"",'
							+ '"answertime":"1",'
							+ '"content":['+ inputarr.join(',') +']'
							+ '}'+com
						
					}
				});
				var str = '{'
						+  '"clienttype":"'+ $('body').data('clienttype') +'",'
						+  '"answertime":"'+ Math.floor((Date.now() - initTime)/1000) +'",'
						+  '"usertest.id":"'+ $('body').data('usertestid') + '",'
						+ '"answers":[' +options
						+ ']}';
				
				$.post('../phone/phPaperSubmit',{'json':str},function(ret) {
					var type = $('body').data('clienttype');
					var usertestid = $('body').data('usertestid');
					location.href = "../phone/phPaperParesePaper?usertest.id="+usertestid+"&type="+type;
				})
			});
			
			$('#continueTest,#closeConfirm').on('tap',function(){
				$('#comfirmModal').removeClass('active');
				return false;
			})
			
			
		})
	</script>
	

</body>
</html>
