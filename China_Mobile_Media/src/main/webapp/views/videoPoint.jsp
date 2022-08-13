<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关联商品</title>
<link href="../assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/style.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/themes/light.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="../assets/css/colorbox.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="../assets/css/columLists.css" type="text/css"/>
<link rel="stylesheet" href="../assets/css/main.css" type="text/css"/>
<link rel="stylesheet" href="../assets/css/connectionGoods.css" type="text/css"/>

<script src="../assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="../assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/scripts/app.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../app/js/index.js"></script>
<script type="text/javascript" src="../assets/scripts/main.js"></script>
<script src="../assets/scripts/connectionGoods.js" type="text/javascript"></script>
<script src="../assets/plugins/url.min.js"></script>
<script src="../assets/plugins/jquery.colorbox.js"></script>
</head>
<body>

</body>
<jsp:include page="header.jsp"></jsp:include>
		<!--head end-->
		<div class="clearfix"></div>
		<!--left-->
			<div class="page-container">
            <!-- BEGIN SIDEBAR -->
            <jsp:include page="left_admin.jsp"></jsp:include>
            <!-- END SIDEBAR -->
            <div class="page-content-wrapper">
                <div class="page-content">
            <div id="contents">
			<div class="content_inners">
				<a  href="../views/videoList.jsp" class="linkAddress">&nbsp;资源列表&nbsp;</a>><a class="linkAddress">&nbsp;关联商品</a>
				<!--视频内容部分-->
				<div class="videoContent">
					<p class="videoTitle">标题：<label id="videoTitle"></label></p>
					<div class="ZeppinVideo" style="position:relative;">
		                <div class="ZeppinVideo_Top" id="ZeppinVideo_Top">
		                
		                </div>
		                <div class="ZeppinVideo_Bottom">
		                    <div class="Progress">
		                   		<div class='progressQQ' id="progressPoint"></div>
		                   		<div class='timeBar'></div>
		                   	</div>
		                    <div class="kongzhianniu">
		                    	<b class="bgligh"></b>
		                    	<button></button>
		                    	<input min="0" max="1" step="0.1" type="range">
		                    	<p></p>
		                    </div>
		                </div>
		            </div>					
				</div>
				
				<!--视频内容部分结束-->

				<!--视频节点图片-->
				<div class="picbox">
					<ul class="picbox_inner">
						<li><img id="iframeImg_1" src="" name="" alt="视频节点"><span id="iframeSpan_1" name=""></span></li>
						<li><img id="iframeImg_2" src="" name="" alt="视频节点"><span id="iframeSpan_2" name=""></span></li>
						<li><img id="iframeImg_3" src="" name="" alt="视频节点"><span id="iframeSpan_3" name=""></span></li>
						<li><img id="iframeImg_4" src="" name="" alt="视频节点"><span id="iframeSpan_4" name=""></span></li>
						<li><img id="iframeImg_5" src="" name="" alt="视频节点"><span id="iframeSpan_5" name=""></span></li>
						<li><img id="iframeImg_6" src="" name="" alt="视频节点"><span id="iframeSpan_6" name=""></span></li>
					</ul>
				</div>			
				<!--视频节点图片结束-->
				<a class="btn btn-default btn-create"  id="addButton" onclick="videoPointAdd()" href="">添加视频点位</a>
				<!--商品关联信息列表-->
				<p class="listTitle">商品关联信息列表</p>
				<table class="table" id="pointTable">

				</table>
				<!--商品关联信息列表结束-->
				<script type="text/javascript">
					var video = (url('?id') != null) ? url('?id') : '';
					var totalLength;
					var maxImg;
					var videoPath;
					function videoPointAdd(){
						var timepoint = document.getElementById("videoTag").currentTime;
						$("#addButton").attr("href","../views/videoPointAdd.jsp?video="+video+"&timepoint="+timepoint);
					}
				
					$(".btn-create").colorbox({
						iframe : true,
						width : "860px",
						height : "700px",
						opacity : '0.5',
						overlayClose : false,
						escKey : true
					});
					$(document).ready(
						function() {
							$.get('../rest/videoinfo/loadVO?id='+video,function(r) {
								if(r.status =='success') {
									var videoHtml = '';
									videoHtml += '<b onclick="ZeppinVideo_TopB(this)"></b>';
									videoHtml += '<video id="videoTag"><source id="videoSource" src="..'+r.data.originalVideoUrl+'">';
									videoHtml += '您的版本不支持该视频播放，请更换浏览器或者升级浏览器版本</video>';
									$('#ZeppinVideo_Top').html(videoHtml);
									$('#videoTitle').html(r.data.title);
									Video=document.getElementById("videoTag");
									ClickVideo(document.getElementById("videoTag"));
									totalLength=r.data.timeLengthSecond;
									maxImg = (totalLength - totalLength%5) / 5 + 1;
									videoPath = r.data.video;
									if (maxImg>=6){
										for(var i=1;i<7;i++){
											$('#iframeImg_'+i).attr("src",".."+r.data.video+'frames/'+(i+1)+".jpg");
											$('#iframeImg_'+i).attr("name",(i+1));
											var second=0,minute=0;
											var iframeSecond = (i-1)*5;
											if(iframeSecond>=60){
												second = iframeSecond%60;
												minute = (iframeSecond-second)/60;
											}else{
												second = iframeSecond;
												minute = 0;
											}
											if(second<10){
												$('#iframeSpan_'+i).html(minute+":0"+second);
											}else{
												$('#iframeSpan_'+i).html(minute+":"+second);
											}
											$('#iframeSpan_'+i).attr("name",iframeSecond);
										}
									}else{
										for(var i=1;i<7;i++){
											if(i<=maxImg){
												$('#iframeImg_'+i).attr("src",".."+r.data.video+'frames/'+(i+1)+".jpg");
												$('#iframeImg_'+i).attr("name",(i+1)+"");
												var second=0,minute=0;
												var iframeSecond = (i-1)*5;
												if(iframeSecond>=60){
													second = iframeSecond%60;
													minute = (iframeSecond-second)/60;
												}else{
													second = iframeSecond;
													minute = 0;
												}
												if(second<10){
													$('#iframeSpan_'+i).html(minute+":0"+second);
												}else{
													$('#iframeSpan_'+i).html(minute+":"+second);
												}
												$('#iframeSpan_'+i).attr("name",iframeSecond);
											}else{
												
											}
										}
									}
								}				
							}).done(function(){
								getList();
								if(maxImg>=6){
									setInterval("imageProgress()",500);
								}
							});
					})
					
					function getList(){
						$.get('../rest/videoPoint/list?sorts=createtime desc&video='+ video,function(r) {
							if(r.status =='success' && r.data.length > 0) {
								var tableHtml = '<tr><th width="15%">帧位图</th><th width="10%">时间点</th><th width="15%">关联商品信息</th><th width="15%">提示文字</th><th width="10%">创建人</th><th width="15%">创建时间</th><th width="8%">操作</th></tr>';
								var pointHtml = ''
								$.each(r.data,function(i,v){
									tableHtml += '<td><img src="..'+ v.iframePath +'" alt="帧位图"></td>';
									tableHtml += '<td>'+v.timepoint+'</td><td>'+v.commodityName+'</td>';
									tableHtml += '<td>'+v.showMessage+'</td><td>'+v.creatorName+'</td>';
									tableHtml += '<td>'+v.createtimeCN+'</td>';
									tableHtml += '<td class="operation"><a class="btn-edit" href="../views/videoPointEdit.jsp?id='+v.id+'">编辑</a>';
									tableHtml += '<a onclick="changeStatus(this)" data-url="../rest/videoPoint/delete?id='+v.id+'">删除</a></td></tr>';
									
									var rate = v.timepointSecond/totalLength*100;
									pointHtml += '<i class="node node1" style="left:'+rate+'%"><p class="nodeintro"><img src="../'+ v.iframePath +'" alt="'+v.showMessage+'"></p></i>'
								})
								$('#pointTable').html(tableHtml);
								$('#progressPoint').html(pointHtml);
								$(".ZeppinVideo .ZeppinVideo_Bottom .Progress .progressQQ i.node").hover(function(){
									$(this).find("p").stop().fadeIn(500);
								},function(){
									$(this).find("p").stop().fadeOut(500);
								});
								$(".btn-edit").colorbox({
									iframe : true,
									width : "860px",
									height : "700px",
									opacity : '0.5',
									overlayClose : false,
									escKey : true
								});
							}else{
								$('#pointTable').html("没有商品关联信息");	
								$('#progressPoint').html("");
							}
						})
					};
					function imageProgress(){
						var timepoint = document.getElementById("videoTag").currentTime;
						var lImg = $('#iframeImg_1').attr("name");
						var fistImg = (timepoint-timepoint%5)/5 + 2;
						if(fistImg != lImg){
							var imgNum =((totalLength - timepoint)-(totalLength - timepoint)%5) / 5 + 1;
							if (imgNum>=5){
								for(var i=1;i<7;i++){
									$('#iframeImg_'+i).attr("src",".."+videoPath+'frames/'+(fistImg+i-1)+".jpg");
									$('#iframeImg_'+i).attr("name",(fistImg+i-1)+"");
									var second=0,minute=0;
									var iframeSecond = (fistImg+i-3)*5;
									if(iframeSecond>=60){
										second = iframeSecond%60;
										minute = (iframeSecond-second)/60;
									}else{
										second = iframeSecond;
										minute = 0;
									}
									if(second<10){
										$('#iframeSpan_'+i).html(minute+":0"+second);
									}else{
										$('#iframeSpan_'+i).html(minute+":"+second);
									}
									$('#iframeSpan_'+i).attr("name",iframeSecond);
								}
							}
						}
					}
					
					function changeStatus(t) {
						var obj = $(t),cUrl = obj.attr('data-url');
						$.getJSON(cUrl, function(ret) {
							if (ret.status == 'success') {
								getList();
							} else {
								alert('失败,' + ret.message);
							}
						})
						return false;
					}
				</script>
			</div>			
		</div>
		</div>
		</div>
	</div>
            <!--left end-->
<jsp:include page="footer.jsp"></jsp:include>
</html>