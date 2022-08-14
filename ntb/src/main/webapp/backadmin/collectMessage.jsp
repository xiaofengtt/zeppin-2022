<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="css/paging.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/base.css">
<style>
    .contariner{
        width:90%;
    }
    .msg_title{
        font-size:18px;
        color:#000000;
        margin:0 auto;
        margin-bottom:20px;
        margin-top:20px;
        text-align: center;
    }
</style>

<script type="text/template" id="queboxTpl">
    <tr  {{if #index%2 == 1}}style="background:#F7FBFF;"{{/if}}>
        <td>{{:userName}}</td>
        <td class="price">{{:price}}</td>
        <td>{{:application}}</td>
        <td>{{:createtimeCN}}</td>
    </tr>
</script>
</head>
<body>
    <div class="container">
        <p class="msg_title">募集金额明细</p>
        <table class="msg_table">
            <tr class="first_tr">
                <th>投资人</th>
                <th>金额</th>
                <th>客户端</th>
                <th>加入时间</th>
            </tr>
            <tbody id="queboxCnt">

            </tbody>
        </table>

        <div id="pageTool">

        </div>
    </div>


    <script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
    <script type="text/javascript" src="js/bootstrap.js" ></script>
    <script type="text/javascript" src="js/jquery.colorbox.js"></script>
    <script type="text/javascript" src="js/query.js"></script>
    <script type="text/javascript" src="js/paging.js"></script>
    <script type="text/javascript" src="js/url.min.js"></script>
    <script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/changePrice.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="js/collectMessage.js"></script>
</body>
</html>
