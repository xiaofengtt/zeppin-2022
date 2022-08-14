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
<link rel="stylesheet" href="./css/QCBRecharge.css">
<title>牛投帮-后台管理系统</title>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00800081" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">企业用户管理</a><span>></span><a href="javascript:void(0);" click="window.locatoin.href=document.referrer">企业用户详情</a><span>></span><a class="current">费用扣除</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">

                <div class="recharge_msg text_box">
                    <p class="text_title">扣除金额信息</p>
                    <form:form class="add_account_form" action="../rest/backadmin/qcbCompanyTransfer/expend" method="post" id="rechargeSubmit">
                        <input type="hidden" name="qcbCompany" value="" id="qcbCompany">
                        <%--如果需要上传凭证，请解除下面代码--%>
                        <%-- <input type="hidden" name="receipt" value=""> --%>
                        <div class="form_item">
                            <span>企业名称：</span> 
                            <span class="company-name"></span>
                        </div>
                                                
                         <div class="form_item form_item1">
                            <span><i>* </i>扣除方式：</span>
                            <input type="radio" name="flag" value="true" id="flagValue" checked="checked">固定费率
                            <input type="radio" name="flag" value="false" id="flagValue" class="flagtype">手动扣除
                        </div>
                        
                        <div class="form_item">
                            <span><i>* </i><span class="noticeFloat">开票</span>金额：</span>
                            <input type="number" name="totalAmount" value="" step="0.01" id="rechargeValue">
                            <small>元</small>
                            <span class="cn_price" id="rechargeBigValue"></span>
                        </div>

                        <div class="form_item">
                            <span>平台手续费率：</span>
                            <span class="rate" style="color:orange"></span>
                        </div>

                        <div class="form_item">
                            <span>扣除金额：</span>
                            <span class="expendValue">请输入固定费率</span>
                            <span class="cn_price expendBigValue" style="color:green"></span>
                        </div>
                        <div class="form_item">
                            <span>备注说明：</span>
                            <textarea name="remark" id="psValue"></textarea>
                        </div>
                        <%--如果需要上传凭证，请解除下面代码--%>
                        <%-- <div id="upload">
                            <div class="clear">

                            </div>
                        </div> --%>
                        <a href="javascript:void(0)" style="display:none" id="delete">删除</a>
                        <div class="btn_group">
                            <input type="button" name="" value="取消" onclick="history.go(-1)">
                            <input type="button" name="" value="确认" id="allRight">
                        </div>
                    </form:form>

                </div>

                <div class="recharge_confirm text-box">
                    <div class="confirm text_box">
                        <p class="text_title">扣除金额明细</p>
                        <div class="confirm_item">
                            <span class="confirm_item_title">企业名称：</span>
                            <span class="company-name"></span>
                        </div>

						<div class="confirm_item">
                            <span class="confirm_item_title">扣除方式：</span>
                            <span class="flagValueConfirm"></span>
                        </div>                        

                        <div class="confirm_item">
                            <span class="confirm_item_title"><span class="noticeFloat">开票</span>金额：</span>
                            <span id="rechargeConfirm"></span>
                            <span class="cn_price" id="rechargeBigConfirm" style="color:green"></span>
                        </div>
                        
                        <div class="confirm_item">
                            <span class="confirm_item_title">平台手续费率：</span>
                            <span class="rate" style="color:orange"></span>
                        </div>

                        <div class="confirm_item">
                            <span class="confirm_item_title">扣除金额：</span>
                            <span class="expendValue"></span>
                            <span class="cn_price expendBigValue" style="color:green"></span>
                        </div>
                        <div class="confirm_item">
                            <span class="confirm_item_title">备注说明：</span>
                            <span id="psConfirm"></span>
                        </div>

                        <%--如果需要上传凭证，请解除下面代码--%>
                        <%-- <div class="fileBox" style="margin-top:30px;margin-left:50px">
                            <div class="clear">

                            </div>
                        </div> --%>
                    </div>
                    <div class="btn_group">
                        <input type="button" name="" value="取消" id="back">
                        <input type="button" name="" value="确认" id="allRightConfirm">
                    </div>
                </div>

            </div>
        </div>
    </div>

    <script type="text/javascript" src="./js/url.min.js"></script>
    <script src="./js/laydate-v1.1/laydate/laydate.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/jquery.form.js"></script>
    <script type="text/javascript" src="./js/changeMoneyToChinese.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/jquery.uploadfile2.0.js"></script>
    <script type="text/javascript" src="./js/uploadFile.js"></script>
    <script type="text/javascript" src="./js/changePrice.js"></script>
    <script type="text/javascript" src="./js/QCBexpend.js"></script>


</body>
</html>
