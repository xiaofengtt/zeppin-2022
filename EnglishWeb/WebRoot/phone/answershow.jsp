<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>错题回顾页面</title>
<jsp:include page="head.jsp"></jsp:include>
<script src="../js/m/swipe.js"></script>
</head>
<body data-usertestid='<s:property value="usertId" />' data-clienttype='<s:property value="clientType" />' >

	<!-- Make sure all your bars are the first things in your <body> -->
	<header class="bar bar-nav bar-nav-gs">
		
		<a class="btn btn-link btn-nav purple pull-right" data-transition="slide-out" href="../phone/chart.jsp"> 
			    返回首页	
		</a>
		<h1 class="title purple">错题解析</h1>
		<span class="js-prev pull-left"><span class="icon icon-left-nav"></span></span>
		<span class="js-next pull-left"><span class="icon icon-right-nav"></span></span>
		
	</header>

	<div class="content">
		<div id="questionList" class="swipe">
		<div class="swipe-wrap">
		<s:set name="optionFlag" value="{'放弃','A','B','C','D','E','F','G','H','I','J','K','L','M','N'}"  />
		<s:set name="judgeFlag" value="{'放弃','√','×'}"  />
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
							
							<s:set name="okinx" value='#group.get("data").get("results")[0].get("inx")' />
							<s:set name="ansinx" value='currentAnswer.get(#group.get("id"))' />
							
							<div class="options" data-modeltype="1">
								<ul class="table-view">					
									<s:iterator value='#group.get("data").get("options")' id='goption'>
										<li data-inx='<s:property value="#goption.get('inx')" />' class="table-view-cell media">
											<a href="#">
												<s:set name="clstyle" value="" />
											
												<!-- 这道题是正确选项 -->
												<s:if test='%{#okinx==#goption.get("inx")}'>
													<s:set name="clstyle" value="'right'" />
												</s:if>
												
												<!-- 如果答错 -->
												<s:if test="%{#okinx!=#ansinx}">
													<s:if test='%{#ansinx==#goption.get("inx")}'>
														<s:set name="clstyle" value="'error'" />
													</s:if>
												</s:if>
												
												<span class="options-abc pull-left <s:property value='#clstyle' />"><s:property value='#optionFlag[#goption.get("inx")]' /></span>
												<div class="media-body">
													<s:property value='#goption.get("content")' escape="false" />
												</div>
											</a>
										</li>
									</s:iterator>
			
								</ul>
								
								
		
							</div>
							
							<div class="padded">
								<span> 正确答案是 <span class="tright"><s:property value='#optionFlag.get(#okinx)' /></span></span> , 
								<span> 你的答案是 <span class="terror"><s:property value='#optionFlag.get(#ansinx)' /></span></span>
							</div>
							
						</s:if>
						<s:elseif test='%{#group.get("modelType")==2}'>
							
							<!-- 填空 -->
							
							<s:set name="ansStr" value='currentAnswer.get(#group.get("id"))' />
							
							<div class="options" data-modeltype="2">
								<ul class="table-view">					
									<s:iterator value='#group.get("data").get("results")' id='goption'>
										<li data-inx='<s:property value="#goption.get('inx')" />' class="table-view-cell media">
											<a href="#"> 
												<span class="options-abc pull-left"><s:property value='#goption.get("inx")' /></span>
												<div class="media-body">
													<s:iterator value="#ansStr" >
														<s:if test='%{key==#goption.get("inx")}'>
															<input type="text"  value="<s:property value='#ansStr.get(key)' />" />
														</s:if>
													</s:iterator>
												</div>
											</a>
										</li>
									</s:iterator>
								</ul>
								
								
								
							</div>
							
							<div class="padded">
								<span> 正确答案是 <span class="tright">
									<s:iterator value='#group.get("data").get("results")' id='goption'>
										[<s:property value='#goption.get("content")' />]
									</s:iterator>
								</span></span>
								<span> 你的答案是 <span class="terror">
									<s:iterator value="#ansStr" >
										[<s:property value='value' />]
									</s:iterator>
								</span></span>
							</div>
							
						</s:elseif>
						<s:elseif test='%{#group.get("modelType")==3}'>
							
							<!-- 判断 -->
							
							<s:set name="okinx" value='#group.get("data").get("results")[0].get("inx")' />
							<s:set name="ansinx" value='currentAnswer.get(#group.get("id"))' />
							
							<div class="options" data-modeltype="3">
								<ul class="table-view">					
									
									<li data-inx='1' class="table-view-cell media">
										<a href="#">
											
											<s:set name="clstyle" value="" />
											<!-- 这道题是正确选项 -->
											<s:if test='%{#okinx==1}'>
												<s:set name="clstyle" value="'right'" />
											</s:if>
											
											<!-- 如果答错 -->
											<s:if test="%{#okinx!=#ansinx}">
												<s:if test='%{#ansinx==1}'>
													<s:set name="clstyle" value="'error'" />
												</s:if>
											</s:if>
											
											<span class="options-abc pull-left <s:property value='#clstyle' />"><i class="fa fa-check fa-lg"></i></span>
											<div class="media-body">
												正确
											</div>
										</a>
									</li>
									<li data-inx='2' class="table-view-cell media">
										<a href="#">
											
											<s:set name="clstyle" value="" />
											
											<!-- 这道题是正确选项 -->
											<s:if test='%{#okinx==2}'>
												<s:set name="clstyle" value="'right'" />
											</s:if>
											
											<!-- 如果答错 -->
											<s:if test="%{#okinx!=#ansinx}">
												<s:if test='%{#ansinx==2}'>
													<s:set name="clstyle" value="'error'" />
												</s:if>
											</s:if>
											
											<span class="options-abc pull-left <s:property value='#clstyle' />"><i class="fa fa-times fa-lg"></i></span>
											<div class="media-body">
												错误
											</div>
										</a>
									</li>
								</ul>
								
								
								
							</div>
							
							<div class="padded">
								<span> 正确答案是 <span class="tright"><s:property value='#judgeFlag.get(#okinx)' /></span></span>
								<span> 你的答案是 <span class="terror"><s:property value='#judgeFlag.get(#ansinx)' /></span></span>
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
								
								<s:set name="okinx" value='#item1.get("data").get("results")[0].get("inx")' />
								<s:set name="ansinx" value='currentAnswer.get(#item1.get("id"))' />
								
								<s:iterator value='#item1.get("data").get("options")' id='option'>
									<li data-inx='<s:property value="#option.get('inx')" />' class="table-view-cell media">
										<a href="#">
											
											<s:set name="clstyle" value="" />
											
											<!-- 这道题是正确选项 -->
											<s:if test='%{#okinx==#option.get("inx")}'>
												<s:set name="clstyle" value="'right'" />
											</s:if>
											
											<!-- 如果答错 -->
											<s:if test="%{#okinx!=#ansinx}">
												
												<s:if test='%{#ansinx==#option.get("inx")}'>
													<s:set name="clstyle" value="'error'" />
												</s:if>
											</s:if>
											
											<span class="options-abc pull-left <s:property value='#clstyle' />"><s:property value='#optionFlag.get(#option.get("inx"))' /></span>
											<div class="media-body">
												<s:property value='#option.get("content")' escape="false" />
											</div>
										</a>
									</li>
								</s:iterator>
		
							</ul>
							
							
							
						</div>
						<div class="padded">
							<span> 正确答案是 <span class="tright"><s:property value='#optionFlag.get(#okinx)' /></span></span>
							<span> 你的答案是 <span class="terror"><s:property value='#optionFlag.get(#ansinx)' /></span></span>
						</div>
							
					</s:if>
					<s:elseif test='%{#item1.get("modelType")==2}'>
						
						<!-- 填空 -->
						<s:set name="ansStr" value='currentAnswer.get(#item1.get("id"))' />
						
						<div class="options" data-modeltype="2">
							<ul class="table-view">					
								<s:iterator value='#item1.get("data").get("results")' id='goption'>
									
									<li data-inx='<s:property value="#goption.get('inx')" />' class="table-view-cell media">
										<a href="#"> 
											<span class="options-abc pull-left"><s:property value='#goption.get("inx")' /></span>
											<div class="media-body">
												<s:iterator value="#ansStr" >
													<s:if test='%{key==#goption.get("inx")}'>
														<input type="text"  value="<s:property value='#ansStr.get(key)' />" />
													</s:if>
												</s:iterator>
											</div>
										</a>
									</li>
									
								</s:iterator>
							</ul>
							
							
							
						</div>
						<div class="padded">
							<span> 正确答案是 <span class="tright">
								<s:iterator value='#item1.get("data").get("results")' id='goption'>
									<s:property value='#goption.get("content")' />
								</s:iterator>
							</span></span>
							<span> 你的答案是 <span class="terror">
								<s:iterator value="#ansStr" >
									<s:property value='value' />
								</s:iterator>
							</span></span>
						</div>
						
					</s:elseif>
					<s:elseif test='%{#item1.get("modelType")==3}'>
							
							<!-- 判断 -->
							
							<s:set name="okinx" value='#item1.get("data").get("results")[0].get("inx")' />
							<s:set name="ansinx" value='currentAnswer.get(#item1.get("id"))' />
							
							<div class="options" data-modeltype="3">
								<ul class="table-view">					
									
									<li data-inx='1' class="table-view-cell media">
										<a href="#"> 
											
											<s:set name="clstyle" value="" />
											<!-- 这道题是正确选项 -->
											<s:if test='%{#okinx==1}'>
												<s:set name="clstyle" value="'right'" />
											</s:if>
											
											<!-- 如果答错 -->
											<s:if test="%{#okinx!=#ansinx}">
												<s:if test='%{#ansinx==1}'>
													<s:set name="clstyle" value="'error'" />
												</s:if>
											</s:if>
											
											<span class="options-abc pull-left <s:property value='#clstyle' />"><i class="fa fa-check fa-lg"></i></span>
											<div class="media-body">
												正确
											</div>
										</a>
									</li>
									<li data-inx='2' class="table-view-cell media">
										<a href="#"> 
										
											<s:set name="clstyle" value="" />
											
											<!-- 这道题是正确选项 -->
											<s:if test='%{#okinx==2}'>
												<s:set name="clstyle" value="'right'" />
											</s:if>
											
											<!-- 如果答错 -->
											<s:if test="%{#okinx!=#ansinx}">
												<s:if test='%{#ansinx==2}'>
													<s:set name="clstyle" value="'error'" />
												</s:if>
											</s:if>
											
											<span class="options-abc pull-left <s:property value='#clstyle' />"><i class="fa fa-times fa-lg"></i></span>
											<div class="media-body">
												错误
											</div>
										</a>
									</li>
								</ul>
								
								
								
							</div>
							
							<div class="padded">
								<span> 正确答案是 <span class="tright"><s:property value='#judgeFlag.get(#okinx)' /></span></span>
								<span> 你的答案是 <span class="terror"><s:property value='#judgeFlag.get(#ansinx)' /></span></span>
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
		  	$('.content').scrollTop(0);
		  }
		});
		
		Zepto(function($){
			
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
					cell.on('click',function(){
						cell.removeClass('selected');
						$(this).addClass('selected');
						
						mySwipe.next();
					})
					cell.on('longTap',function(e){
						e.preventDefault();
						cell.removeClass('selected');
						
					})
				}
				
				
			});
			
			$('.js-next').on('click',function(){
				mySwipe.next();
			})
			$('.js-prev').on('click',function(){
				mySwipe.prev();
			})
			
		})
	</script>
	

</body>
</html>
