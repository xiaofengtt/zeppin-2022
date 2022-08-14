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
<link rel="stylesheet" href="./css/rechargeDrawStatus.css">
<script type="text/template" id="queboxTpl">
    <tr>
        <td>
            {{:createtimeCN}}
        </td>
        <td>
            <p>{{:companyAccountNum}}<span style="color:orange">{{:accountNum}}</span></p>
            <p>{{:companyAccountName}}</p>
        </td>
        <td>{{:branchBankName}}</td>
        <td>
            <p>{{:qcbCompanyId}}</p>
            <p>{{:qcbCompanyName}}</p>
        </td>
        <td>
            <a href="javascript:void(0);" class="delete" data-uuid={{:uuid}}>删除</a>
        </td>
    </tr>
</script>
<style>
    .screening .add_account,.screening .delete_account{
        color: #FFFFFF;
        display: inline-block;
        font-size: 14px;
        height: 35px;
        width: 100px;
        line-height: 35px;
        background: #4D9CFF;
        text-align: center;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        -ms-border-radius: 5px;
        -o-border-radius: 5px;
        border-radius: 5px;
        position: absolute;
        top: 9px;
        right: 120px;
    }
    .screening .delete_account{
        right:10px;
    }
    .screening .add_account:hover,.screening .delete_account:hover{
        background: #3F8DF6;
    }
    .msg_table tr td p{
        margin:0;
    }
    .msg_table tr td{
        text-align: left;
        padding:10px 20px;
    }
    .msg_table tr th{
        text-align: left;
        padding:10px 20px;
    }
</style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00600068" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a>财务管理</a><span>></span><a class="current">银行虚拟账户管理</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">


                <div class="screening">
                    <div class="statusDiv shortStatusDiv filter">
                        <label>审核状态：</label>
                        <div>
                            <a id="all" class="statusLight">全部<span id="allCount">(0)</span></a>
                            <a id="normal">未绑定<span id="normalCount">(0)</span></a>
                            <a id="used">已绑定<span id="usedCount">(0)</span></a>
                        </div>
                    </div>
                    <%-- <div class="button_search">
                        <div class="search_div" style="width:200px;">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="搜索..." id="search">
                                <label class="input-group-addon" id="searchButton"></label>
                            </div>
                        </div>
                    </div> --%>
                    <a href="./bankFictitiousAccountAdd.jsp" class="add_account">批量添加</a>
                    <a href="./bankFictitiousAccountDelete.jsp" class="delete_account">批量删除</a>
                    <!-- <a class="add_account" id="recharge1" style="right:120px;">模拟用户充值</a>
                    <a class="add_account" id="draw1" style="right:240px;">模拟用户提现</a> -->
                </div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th width="20%">开通时间</th>
                        <th width="30%">账户信息</th>
                        <th width="20%">开户银行</th>
                        <th width="20%">绑定账号</th>
                        <th width="10%">操作</th>
                    </tr>
                    <tbody id="queboxCnt">

                    </tbody>
                </table>

                <div id="pageTool"></div>
                <%--//--%>
            </div>
        </div>
    </div>

</body>
</html>
<script type="text/javascript" src="./js/getHtmlDocName.js"></script>
<script src="./js/changePrice.js"></script>
<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
<script type="text/javascript" src="./js/jsrender.min.js"></script>
<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
<script type="text/javascript" src="./js/bankFictitiousAccountList.js"></script>
