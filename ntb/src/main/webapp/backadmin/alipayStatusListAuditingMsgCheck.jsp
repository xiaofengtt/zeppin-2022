<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
        <div class="message_item">
            <span><i>*</i> 提交人：</span>
            <p>{{:creatorName}}</p>
        </div>
        <div class="message_item">
            <span><i>*</i> 提交时间：</span>
            <p>{{:submittimeCN}}</p>
        </div>

        {{if status=='unchecked'}}
            <div class="message_item">
                <span><i>*</i> 审核信息：</span>
                <p><input type="text" name="" value="" style="margin:0;" id="reason"></p>
            </div>
        {{else status=='checked'}}
            <div class="message_item">
                <span><i>*</i> 审核信息：</span>
                <p class="color_green">{{:reason}}</p>
            </div>
        {{else status == "unpassed"}}
            <div class="message_item">
                <span><i>*</i> 审核信息：</span>
                <p class="color_red">{{:reason}}</p>
            </div>
        {{/if}}
        <div class="btn_group">
            {{if status == "unchecked"}}
                <a href="javascript:;" class="auditing_check" data-uuid="{{:uuid}}" data-status="checked">通过</a>
                <a href="javascript:;" class="auditing_check" data-uuid="{{:uuid}}" data-status="unpassed">不通过</a>
            {{/if}}
            <a href="javascript:;" id="edit" onclick="window.location.href = document.referrer">取消</a>
        </div>
    </div>
</script>

<script type="text/template" id="accountMessageEditTpl">

        <input type="hidden" value="{{:uuid}}" name="uuid"/>
        <div class="form_item">
            <span><i>*</i> 账户类型</span>
            <select name="type" id="type">
                <option value="0">请选择账户类型...</option>
                <option value="third">第三方账户</option>
                <option value="invest">投资化</option>
                <option value="collect">募集户</option>
                <option value="redem">结算户</option>
            </select>
        </div>
        {{if newData.type == "third"}}
            <div class="form_item">
                <span><i>*</i> 开户行</span>
                <input type="text" id="bankList" value="{{:newData.bankName}}" disabled />
            </div>
        {{/if}}

        <div class="form_item">
            <span><i>*</i> 开户名</span>
            <input type="text" name="companyName" id="companyName" value="{{:newData.companyName}}" autocomplete="off" placeholder="请输入开户名...">
        </div>

        <div class="form_item">
            <span><i>*</i> 开户别名</span>
            <input type="text" name="accountName" id="accountName" value="{{:newData.accountName}}" autocomplete="off" placeholder="请输入开户别名...">
        </div>

        <div class="form_item">
            <span><i>*</i> 账号</span>
            <input type="text" name="accountNum" id="accountNum" value="{{:newData.accountNum}}" autocomplete="off" placeholder="请输入账号...">
        </div>

        <div class="form_item">
            <span><i>*</i> 状态</span>
            <select name="status" id="status">
                <option value="0">请选择状态...</option>
                <option value="normal">正常</option>
                <option value="disable">禁用</option>
            </select>
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
    <input id="scode" type="hidden" value="00200028" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">用户权限管理</a><span>></span><a href="./alipayStatusListAuditingCheck.jsp">支付方式变动待审核事项</a><span>></span><a class="current">查看</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div id="account_message_Cnt">

                </div>

                <div class="edit">
                    <form class="submit_logo" action="" method="post">
                        <input type="file" name="logo" value="">
                        <input type="hidden" name="type" value="1">
                        <img src="" alt="">
                        <p>上传LOGO</p>
                    </form>
                    <form:form class="add_account_form" action="../rest/backadmin/companyAccount/operateEdit" method="post" id="add">
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
    <script type="text/javascript" src="./js/alipayStatusListAuditingMsgCheck.js"></script>
</body>
</html>
