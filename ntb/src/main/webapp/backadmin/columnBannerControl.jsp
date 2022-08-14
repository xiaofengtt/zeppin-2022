<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>牛头帮－后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/accountControllerList.css">
<link rel="stylesheet" href="./css/screening.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/column_box.css">
<style media="screen">
    .screening{
        margin-top:0;
    }
</style>
<script type="text/template" id="queboxTpl">

</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00600061" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">内容管理CMS</a><span>></span><a class="current">栏目内容管理</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">

                <div class="column_box">
                    <p class="column_title">栏目</p>

                    <div class="level_1">
                        app首页
                        <ul>
                            <li>
                                <a href="./columnControl.jsp">新手专享</a>
                            </li>
                            <li>
                                <a href="./columnControl.jsp">
                                    短期精选
                                </a>
                            </li>
                            <li>
                                <a href="./columnControl.jsp">
                                    中期精选
                                </a>
                            </li>
                            <li>
                                <a href="./columnControl.jsp">
                                    长期精选
                                </a>
                            </li>
                        </ul>
                    </div>
                    <div class="level_1">
                        Banner
                        <ul>
                            <li>
                                App首页幻灯片
                            </li>
                            <li>
                                个人中心广告
                            </li>
                            <li>
                                底部广告条
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="right_box">
                    <div class="screening">
                        <nav>
                            <span>栏目：</span>
                            <span>Banner</span>
                            >>
                            <span>APP首页幻灯片</span>
                        </nav>
                        <a href="./selectImgMsg.jsp" id="add" class="add_account">发布到栏目</a>
                        <!-- <a class="add_account" id="recharge1" style="right:120px;">模拟用户充值</a>
                        <a class="add_account" id="draw1" style="right:240px;">模拟用户提现</a> -->
                    </div>

                    <table cellspacing="0" cellpadding="0" class="msg_table">
                        <tr class="first_tr">
                            <th width="30%">图文内容标题</th>
                            <th width="30%">封面图片</th>
                            <th width="6%">顺序</th>
                            <th width="11%">截止时间</th>
                            <th width="20%">操作</th>
                        </tr>
                        <tbody id="queboxCnt">
                            <tr>
                                <td><a href="">牛头理财品牌介绍H5页面(app版)</a></td>
                                <td>有</td>
                                <td>1</td>
                                <td>2018-11-23 09:08:12</td>
                                <td>
                                    <a href="javascript:void(0);">上移</a>
                                    <a href="javascript:void(0);">下移</a>
                                    <a href="javascript:void(0);">移出</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>

                    <div id="pageTool"></div>
                </div>

                <%--//--%>
            </div>
        </div>
    </div>


</body>
</html>
<script src="./js/changePrice.js"></script>
<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
<script type="text/javascript" src="./js/jsrender.min.js"></script>
<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
<script src="./js/column_box.js"></script>
<script src="./js/columnBannerControl.js"></script>
