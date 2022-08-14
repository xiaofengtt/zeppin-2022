<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/confirm.css">
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/card.css">
<link rel="stylesheet" href="./css/bank_form.css">
<link rel="stylesheet" href="./css/uploadfile.css">
<title>牛投帮-后台管理系统</title>
<script type="text/template" id="outTpl">

    <p class="text_title">{{:accountName}}</p>
    <span class="company_name">{{:companyName}}</span>
    <span class="card_number">{{:accountNumStar}}</span>
    <span class="card_balance">
        余额：
        <i id="aBalance">{{:accountBalanceCN}}</i>
    </span>
</script>

<script type="text/template" id="inTpl">

    <p class="text_title">{{:accountName}}</p>
    <span class="company_name">{{:companyName}}</span>
    <span class="card_number">{{:accountNumStar}}</span>
    <!-- <span class="card_bind">绑定</span> -->
    <span class="card_balance">
        余额：
        <i>{{:accountBalanceCN}}</i>
    </span>
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
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./accountControllerList.jsp">账户管理</a><span>></span><a class="current">转账</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="msg_card text_box">
                    <div id="outCnt"></div>
                </div>

                <span style="margin:0 10px;">>>>>>></span>

                <div class="msg_card text_box">
                    <div id="inCnt"></div>
                </div>




                <div class="recharge_msg text_box">
                    <p class="text_title">转账信息</p>
                    <form:form class="add_account_form" action="../rest/backadmin/companyAccountTransfer/transfer" method="post" id="submit">
                        <input type="hidden" name="companyAccountOut" id="companyAccountOut" value="">
                        <input type="hidden" name="companyAccountIn" id="companyAccountIn" value="">
                        <input type="hidden" name="receipt" value="">
                        <p class="cn_price" id="rechargeBigValue">人民币：</p>
                        <div class="form_item">
                            <span><i>* </i>转账金额</span>
                            <input type="number" name="totalAmount" value="" id="transferValue" step="0.01">
                            <small>元</small>
                        </div>

                        <div class="form_item">
                            <span><i>* </i>手续费</span>
                            <input type="number" name="poundage" value="" id="poundage" step="0.01">
                            <small>元</small>
                            <span class="form_balance"></span>
                        </div>

                        <div class="form_item">
                            <span><i>* </i>资金用途</span>
                            <input type="text" name="purpose" value="" id="useValue">

                        </div>

                        <div class="form_item">
                            <span><i>* </i>备注说明</span>
                            <textarea name="remark" id="psValue"></textarea>
                        </div>
                        <div id="upload">
                            <div class="clear">

                            </div>
                        </div>
                        <a href="javascript:void(0)" style="display:none" id="delete">删除</a>
                        <div class="btn_group">
                            <input type="button" name="" value="取消" onclick="history.go(-1)">
                            <input type="button" name="" value="确认" id="allRight">
                        </div>
                    </form:form>

                </div>

                <div class="recharge_confirm text-box">
                    <div class="confirm text_box">
                        <p class="text_title">转账明细</p>
                        <div class="confirm_item">
                            <span class="confirm_item_title">转账金额：</span>
                            <span class="confirm_item_price" id="transferConfirm">100000.00</span>
                            <span class="confirm_item_cn" id="rechargeBigConfirm">人民币：人民币壹拾万元整</span>
                        </div>


                        <div class="confirm_item">
                            <span class="confirm_item_title">手续费：</span>
                            <span id="poundageConfirm"></span>
                        </div>

                        <div class="confirm_item">
                            <span class="confirm_item_title">资金用途：</span>
                            <span  id="useConfirm">用于投资理财产品</span>
                        </div>

                        <div class="confirm_item">
                            <span class="confirm_item_title">备注说明：</span>
                            <span id="psConfirm">流水号：</span>
                            <%-- <span>123123123</span>
                            <span>&ensp;&ensp;交易时间：</span>
                            <span>2017.12.12</span> --%>
                        </div>
                        <div class="fileBox" style="margin-top:30px;margin-left:50px">
                            <div class="clear">

                            </div>
                        </div>
                    </div>

                    <%-- <div class="auditing text_box">
                        <span class="confirm_item_title">填报信息：</span>
                        <div class="auditing_msg">
                            <p>2017-9-12 15:00:00 秦龙填报</p>
                            <p>2017-9-12 15:00:00 荣景峰审批</p>
                        </div>
                    </div>
                    <div class="buy_password text_box">
                        <span class="confirm_item_title"><i>* </i>交易密码：</span>
                        <input type="password" name="" value="">
                    </div> --%>

                    <div class="btn_group">
                        <input type="button" name="" value="取消" id="back">
                        <input type="button" name="" value="确认" id="allRightConfirm">
                    </div>
                </div>

            </div>
        </div>
    </div>

    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/changeMoneyToChinese.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="js/jquery.uploadfile2.0.js"></script>
    <script type="text/javascript" src="js/uploadFile.js"></script>
    <script type="text/javascript" src="js/changePrice.js"></script>
    <script type="text/javascript" src="js/transfer.js"></script>
</body>
</html>
