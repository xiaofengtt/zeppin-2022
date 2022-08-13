<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
function gotoUrl(url){
	window.location.href=url;
}
$(function() {
	var html = '';
	$.getJSON('../rest/left/list', function(r) {
		if(r.status == "success"){
			$.each(r.data,function(i,v){
				html += '<li><a class="" href="javascript:;" onclick="">'+v.name+'</a><ul class="sub-menu">'
				$.each(r.data[i].child,function(n,vv){
					html += '<li><a href="javascript:;" onclick="gotoUrl(\'../views/videoPublishList.jsp?parent='+vv.id+'\');">'+vv.name+'</a>'
				})
				html += '</ul></li>';
			})
			
		}
	}).done(function(){
		$('.categoryList').html("");
		$('.categoryList').html(html);
	})
	
})
</script>
            <!-- BEGIN SIDEBAR -->
            <div class="page-sidebar-wrapper">
                <div class="page-sidebar navbar-collapse collapse">
                    <!-- BEGIN SIDEBAR MENU -->
                    <ul class="page-sidebar-menu nav" id="page-sidebar-menu">
                        <li class="sidebar-toggler-wrapper">
                            <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                            <div class="sidebar-toggler hidden-phone"></div>
                            <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                        </li>

                        <li class="leftli">
                            <a href="javascript:;">
                                <i class="fa fa-gears icon-home icon-black"></i><span class="title"> 资源管理 </span>
                                <!-- <span class="arrow "> </span> -->
                            </a>
                            <ul class="sub-menu">
                                <li>
                                    <a href="javascript:" onclick="gotoUrl('../views/categoryList.jsp');">
                                        栏目管理
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:" onclick="gotoUrl('../views/videoList.jsp');">
                                        视频管理
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:;" onclick="gotoUrl('../views/commodityList.jsp');">
                                        商品管理
                                    </a>
                                </li>
                            </ul>
                        </li>

						<li class="leftli">
                            <a href="javascript:;">
                                <i class="fa fa-user icon-home icon-black"></i><span class="title"> 发布管理 </span>
                                <!-- <span class="arrow "> </span> -->
                            </a>
                            <ul class="sub-menu categoryList">
                                <li><a href="javascript:;" onclick="gotoUrl('../views/videoPublishList.jsp?parent=062b6a49-5332-430a-a91d-1faf040a1c07');">新品发布</a></li>
     
                            </ul>
                        </li>

                        <li class="leftli">
                            <a href="javascript:;">
                                <i class="fa fa-user icon-home icon-black"></i><span class="title"> 个人中心 </span>
                                <!-- <span class="arrow "> </span> -->
                            </a>
                            <ul class="sub-menu">
                                <li>
                                    <a href="javascript:;">
                                        信息修改
                                    </a>
                                </li>
                                <li>
                                    <a href="javascript:;">
                                        密码修改
                                    </a>
                                </li>
                                
     
                            </ul>
                        </li>

                    </ul>
                    <!-- END SIDEBAR MENU -->
                </div>
            </div>