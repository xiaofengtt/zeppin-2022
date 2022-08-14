<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/bank_form.css">
<link rel="stylesheet" href="./css/accountMessage.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/base.css">
<%--企业账户信息模板--%>
<script type="text/template" id="account_message_tpl">
    <div class="account_message text_box">
        <p class="text_title" style="margin:0 0 20px 10px">支付方式</p>
        {{if type == "open" || type == "closed"}}
            <div class="message_item">
                <span><i>*</i> 名称：</span>
                <p>{{:oldData.payment}}</p>
            </div>

            <div class="message_item">
                <span><i>*</i> 描述：</span>
                <p>{{:oldData.paymentDes}}</p>
            </div>

            <div class="message_item">
                <span><i>*</i> 状态：</span>
                {{if oldData.flagSwitch == true}}
                    <p>开启</p>
                {{else}}
                    <p>关闭</p>
                {{/if}}
                {{if newData.flagSwitch == true}}
                    <p class="color_red">变更为：开启</p>
                {{else}}
                    <p class="color_red">变更为：关闭</p>
                {{/if}}
            </div>
        {{/if}}

        {{if type == "delete"}}
            <div class="message_item">
                <span><i>*</i> 名称：</span>
                <p>{{:oldData.payment}}</p>
            </div>

            <div class="message_item">
                <span><i>*</i> 描述：</span>
                <p>{{:oldData.paymentDes}}</p>
            </div>

            <div class="message_item">
                <span><i>*</i> 操作：</span>
                <p class="color_red">删除！</p>
            </div>
        {{/if}}
        {{if type == "add"}}


            <div class="message_item">
                <span><i>*</i> 名称：</span>
                <p>{{:newData.payment}}</p>
            </div>

            <div class="message_item">
                <span><i>*</i> 描述：</span>
                <p>{{:newData.paymentDes}}</p>
            </div>

        {{else type == "edit"}}
            {{if status == "unchecked" || status == "checked"}}

                <div class="message_item">
                    <span><i>*</i> 名称：</span>
                    {{if oldData.payment == newData.payment}}
                        <p>{{:oldData.payment}}</p>
                    {{else}}
                        <p>{{:oldData.payment}}</p>
                        <p class="color_red">变更为：{{:newData.payment}}</p>
                    {{/if}}
                </div>


                <div class="message_item">
                    <span><i>*</i> 描述：</span>
                    {{if oldData.paymentDes == newData.paymentDes}}
                        <p>{{:oldData.paymentDes}}</p>
                    {{else}}
                        <p>{{:oldData.paymentDes}}</p>
                        <p class="color_red">变更为：{{:newData.paymentDes}}</p>
                    {{/if}}
                </div>



            {{else status == "unpassed"}}

                <div class="message_item">
                    <span><i>*</i> 名称：</span>
                    {{if oldData.payment == newData.payment}}
                        <p>{{:oldData.payment}}</p>
                    {{else}}
                        <p>{{:oldData.payment}}</p>
                        <p class="color_red">变更为：{{:newData.payment}}</p>
                    {{/if}}
                </div>


                <div class="message_item">
                    <span><i>*</i> 描述：</span>
                    {{if oldData.paymentDes == newData.paymentDes}}
                        <p>{{:oldData.paymentDes}}</p>
                    {{else}}
                        <p>{{:oldData.paymentDes}}</p>
                        <p class="color_red">变更为：{{:newData.paymentDes}}</p>
                    {{/if}}
                </div>


            {{else status == "draft"}}

                <div class="message_item">
                    <span><i>*</i> 名称：</span>
                    {{if oldData.payment == newData.payment}}
                        <p>{{:oldData.payment}}</p>
                    {{else}}
                        <p>{{:oldData.payment}}</p>
                        <p class="color_red">变更为：{{:newData.payment}}</p>
                    {{/if}}
                </div>



                <div class="message_item">
                    <span><i>*</i> 描述：</span>
                    {{if oldData.paymentDes == newData.paymentDes}}
                        <p>{{:oldData.paymentDes}}</p>
                    {{else}}
                        <p>{{:oldData.paymentDes}}</p>
                        <p class="color_red">变更为：{{:newData.paymentDes}}</p>
                    {{/if}}
                </div>


            {{/if}}
        {{/if}}
        {{if type == "add" || type == "edit"}}
            <a href="javascript:;" id="edit" style="position:absolute;bottom:10%;right:22%;">修改</a>
        {{/if}}
        <a href="javascript:;" id="goBack" onclick="window.location.href=document.referrer;" style="position:absolute;bottom:10%;right:8%;">取消</a>
    </div>
</script>
<script type="text/template" id="accountMessageEditTpl">
        <p class="text_title" style="margin:0 0 20px 10px">支付方式</p>
        <input type="hidden" value="{{:uuid}}" name="uuid"/>

        <div class="form_item">
            <span><i>*</i> 开户名</span>
            <input type="text" name="paymentName" id="paymentName" value="{{:newData.payment}}" autocomplete="off" placeholder="请输入名称...">
        </div>

        <div class="form_item">
            <span><i>*</i> 开户别名</span>
            <input type="text" name="paymentDes" id="paymentDes" value="{{:newData.paymentDes}}" autocomplete="off" placeholder="请输入描述...">
        </div>
        <div class="btn_group">
            <input type="button" name="" value="取消" id="close">
            <input type="button" name="" value="保存" id="add_submit">
        </div>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00200027" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">用户权限管理</a><span>></span><a href="./alipayStatusListAuditing.jsp">支付方式变动申请</a><span>></span><a class="current">查看</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div id="account_message_Cnt">

                </div>

                <div class="edit">

                    <form:form class="add_account_form" action="../rest/backadmin/bkpayment/operateEdit" method="post" id="add">
                        <div id="accountMessageEditCnt">

                        </div>
                    </form:form>
                </div>


            </div>
        </div>
    </div>


    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/alipayStatusListAuditingMsg.js"></script>
</body>
</html>
