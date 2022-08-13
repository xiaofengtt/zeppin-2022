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
<link rel="stylesheet" href="../assets/css/columLists.css" type="text/css"/>
<link rel="stylesheet" href="../assets/css/main.css" type="text/css"/>
<link rel="stylesheet" href="../assets/css/connectionGoods.css" type="text/css"/>
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
				<a href="javascript:" class="linkAddress">&nbsp;首页&nbsp;</a>><a href="javascript:" class="linkAddress">&nbsp;资源管理&nbsp;</a>><a href="javascript:" class="linkAddress">&nbsp;资源列表&nbsp;</a>><a class="linkAddress">&nbsp;关联商品</a>
				<!--视频内容部分-->
				<div class="videoContent">
					<p class="videoTitle">标题：<label>最新苹果iphone 6c概念宣传片</label></p>
					<div class="ZeppinVideo" style="position:relative;">
		                <div class="ZeppinVideo_Top">
		                    <b onclick="ZeppinVideo_TopB(this)"></b>
		                    <video id="shuangkashuangdai">
		                        <source src="123.mp4">                     
		                        您的版本不支持该视频播放，请更换浏览器或者升级浏览器版本
		                    </video>
		                </div>
		                <div class="ZeppinVideo_Bottom">
		                    <div class="Progress"><div class='progressQQ'><i class="node1 node"><p class="nodeintro nodeintro1"><img src="../assets/img/intro.png" alt="视频节点"></p></i><i class="node2 node"><p class="nodeintro nodeintro2"><img src="../assets/img/intro.png" alt="视频节点"></p></i><i class="node3 node"><p class="nodeintro nodeintro3"><img src="../assets/img/intro.png" alt="视频节点"></p></i></div><div class='timeBar'></div></div>
		                    <div class="kongzhianniu"><b class="bgligh"></b><button></button><input min="0" max="1" step="0.1" type="range"><p></p></div>
		                </div>
		            </div>					
				</div>
				
				<!--视频内容部分结束-->

				<!--视频节点图片-->
				<div class="pic">
					<img src="../assets/img/left.png" alt="左箭头" class="leftImg">
					<div class="picbox">
						<ul class="picbox_inner">
							<li><img src="../assets/img/img6.jpg" alt="视频节点"><span>00:10</span></li>
							<li><img src="../assets/img/img6.jpg" alt="视频节点"><span>00:20</span></li>
							<li><img src="../assets/img/img6.jpg" alt="视频节点"><span>00:30</span></li>
							<li><img src="../assets/img/img6.jpg" alt="视频节点"><span>00:40</span></li>
							<li><img src="../assets/img/img6.jpg" alt="视频节点"><span>00:50</span></li>
							<li><img src="../assets/img/img6.jpg" alt="视频节点"><span>01:00</span></li>
							<li><img src="../assets/img/img6.jpg" alt="视频节点"><span>01:10</span></li>
							<li><img src="../assets/img/img6.jpg" alt="视频节点"><span>02:10</span></li>
							<li><img src="../assets/img/img6.jpg" alt="视频节点"><span>03:10</span></li>
							<li><img src="../assets/img/img6.jpg" alt="视频节点"><span>04:10</span></li>
						</ul>
					</div>
					<img src="../assets/img/right.png" alt="右箭头" class="rightImg">		
				</div>						
				<!--视频节点图片结束-->
				<a class="btn btn-default">添加视频点位</a>
				
				<!--商品关联信息列表-->
				<p class="listTitle">商品关联信息列表</p>
				<table class="table">
					<tr><th width="10%">序号</th><th width="15%">帧位图</th><th width="10%">时间点</th><th width="15%">关联商品信息</th><th width="15%">提示文字</th><th width="10%">创建人</th><th width="15%">创建时间</th><th width="10%">操作</th></tr>
					<tr><td>1231242</td><td><img src="../assets/img/img6.jpg" alt="帧位图"></td><td>00:12:12</td><td>标题：锤子t2</td><td>苹果2016年最新手机</td><td>秦龙</td><td>2016年4月28日<br>10:10</td><td class="operation"><a href="javascropt:">编辑</a><a href="javascropt:">删除</a></td></tr>
					<tr><td>1231242</td><td><img src="../assets/img/img6.jpg" alt="帧位图"></td><td>00:12:12</td><td>标题：锤子t2</td><td>苹果2016年最新手机</td><td>秦龙</td><td>2016年4月28日<br>10:10</td><td class="operation"><a href="javascropt:">编辑</a><a href="javascropt:">删除</a></td></tr>
					<tr><td>1231242</td><td><img src="../assets/img/img6.jpg" alt="帧位图"></td><td>00:12:12</td><td>标题：锤子t2</td><td>苹果2016年最新手机</td><td>秦龙</td><td>2016年4月28日<br>10:10</td><td class="operation"><a href="javascropt:">编辑</a><a href="javascropt:">删除</a></td></tr>
					<tr><td>1231242</td><td><img src="../assets/img/img6.jpg" alt="帧位图"></td><td>00:12:12</td><td>标题：锤子t2</td><td>苹果2016年最新手机</td><td>秦龙</td><td>2016年4月28日<br>10:10</td><td class="operation"><a href="javascropt:">编辑</a><a href="javascropt:">删除</a></td></tr>
				</table>
				<!--商品关联信息列表结束-->
			</div>			
		</div>
		</div>
		</div>
	</div>
            <!--left end-->
<jsp:include page="footer.jsp"></jsp:include>
<script src="../assets/plugins/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="../assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../assets/scripts/app.js" type="text/javascript"></script>
<script src="../assets/plugins/jquery.cokie.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../app/js/index.js"></script>
<script type="text/javascript" src="../assets/scripts/main.js"></script>
<script src="../assets/scripts/connectionGoods.js" type="text/javascript"></script>
</html>