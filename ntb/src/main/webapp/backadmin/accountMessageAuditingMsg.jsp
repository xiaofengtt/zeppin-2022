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
<link rel="stylesheet" href="./css/select2.min.css">
<style>
    .select2-container{
        padding:0 !important;
        width:50% !important;
    }
    .select2-selection__rendered{
        text-align: left;
        font-size:13px;
        color:#555555;
    }
    .select2-container--default{
        margin-bottom:10px;
        height:30px !important;
    }
    .select2-selection--single{
        border:1px solid #C1C1C1 !important;
        height:30px !important;
    }
</style>
<%--企业账户信息模板--%>
<script type="text/template" id="account_message_tpl">
    <div class="account_message text_box">
        {{if newData.type != "third"}}
            <div class="account_logo">
                <img src="..{{:newData.bankIconUrl}}">
            </div>
        {{/if}}
        {{if type == "add"}}
            <div class="message_item">
                <span><i>*</i> 账户类型：</span>
                <p>{{:newData.typeCN}}</p>
            </div>

            {{if newData.type != "third"}}
                <div class="message_item">
                    <span><i>*</i> 开户行：</span>
                    <p>{{:newData.bankName}}</p>
                </div>
                <div class="message_item">
                    <span><i>*</i> 支行：</span>
                    <p>{{:newData.branchBankName}}</p>
                </div>
            {{/if}}

            <div class="message_item">
                <span><i>*</i> 开户名：</span>
                <p>{{:newData.companyName}}</p>
            </div>

            <div class="message_item">
                <span><i>*</i> 开户别名：</span>
                <p>{{:newData.accountName}}</p>
            </div>

            <div class="message_item">
                <span><i>*</i> 账号：</span>
                <p>{{:newData.accountNum}}</p>
            </div>

            <div class="message_item">
                <span><i>*</i> 状态：</span>
                <p>{{:newData.statusCN}}</p>
            </div>
        {{else type == "edit"}}
            {{if status == "unchecked" || status == "checked"}}

                <div class="message_item">
                    <span><i>*</i> 账户类型：</span>
                    {{if oldData.typeCN == newData.typeCN}}
                        <p>{{:oldData.typeCN}}</p>
                    {{else}}
                        <p>{{:oldData.typeCN}}</p>
                        <p class="color_red">变更为：{{:newData.typeCN}}</p>
                    {{/if}}
                </div>

                {{if oldData.type != "third"}}
                    <div class="message_item">
                        <span><i>*</i> 开户行：</span>
                        {{if oldData.bankName == newData.bankName}}
                            <p>{{:oldData.bankName}}</p>
                        {{else}}
                            <p>{{:oldData.bankName}}</p>
                            <p class="color_red">变更为：{{:newData.bankName}}</p>
                        {{/if}}
                    </div>
                    <div class="message_item">
                        <span><i>*</i> 支行：</span>
                        {{if oldData.branchBankName == newData.branchBankName}}
                            <p>{{:oldData.branchBankName}}</p>
                        {{else}}
                            <p>{{:oldData.branchBankName}}</p>
                            <p class="color_red">变更为：{{:newData.branchBankName}}</p>
                        {{/if}}
                    </div>
                {{/if}}

                <div class="message_item">
                    <span><i>*</i> 开户名：</span>
                    {{if oldData.companyName == newData.companyName}}
                        <p>{{:oldData.companyName}}</p>
                    {{else}}
                        <p>{{:oldData.companyName}}</p>
                        <p class="color_red">变更为：{{:newData.companyName}}</p>
                    {{/if}}
                </div>

                <div class="message_item">
                    <span><i>*</i> 开户别名：</span>
                    {{if oldData.accountName == newData.accountName}}
                        <p>{{:oldData.accountName}}</p>
                    {{else}}
                        <p>{{:oldData.accountName}}</p>
                        <p class="color_red">变更为：{{:newData.accountName}}</p>
                    {{/if}}
                </div>

                <div class="message_item">
                    <span><i>*</i> 账号：</span>
                    {{if oldData.accountNum == newData.accountNum}}
                        <p>{{:oldData.accountNum}}</p>
                    {{else}}
                        <p>{{:oldData.accountNum}}</p>
                        <p class="color_red">变更为：{{:newData.accountNum}}</p>
                    {{/if}}
                </div>

                <div class="message_item">
                    <span><i>*</i> 状态：</span>
                    {{if oldData.statusCN == newData.statusCN}}
                        <p>{{:oldData.statusCN}}</p>
                    {{else}}
                        <p>{{:oldData.statusCN}}</p>
                        <p class="color_red">变更为：{{:newData.statusCN}}</p>
                    {{/if}}
                </div>

            {{else status == "unpassed"}}

                <div class="message_item">
                    <span><i>*</i> 账户类型：</span>
                    {{if oldData.typeCN == newData.typeCN}}
                        <p>{{:oldData.typeCN}}</p>
                    {{else}}
                        <p>{{:oldData.typeCN}}</p>
                        <p class="color_red">变更为：{{:newData.typeCN}}</p>
                    {{/if}}
                </div>
                {{if oldData.type != "third"}}
                    <div class="message_item">
                        <span><i>*</i> 开户行：</span>
                        {{if oldData.bankName == newData.bankName}}
                            <p>{{:oldData.bankName}}</p>
                        {{else}}
                            <p>{{:oldData.bankName}}</p>
                            <p class="color_red">变更为：{{:newData.bankName}}</p>
                        {{/if}}
                    </div>
                    <div class="message_item">
                        <span><i>*</i> 支行：</span>
                        {{if oldData.branchBankName == newData.branchBankName}}
                            <p>{{:oldData.branchBankName}}</p>
                        {{else}}
                            <p>{{:oldData.branchBankName}}</p>
                            <p class="color_red">变更为：{{:newData.branchBankName}}</p>
                        {{/if}}
                    </div>
                {{/if}}

                <div class="message_item">
                    <span><i>*</i> 开户名：</span>
                    {{if oldData.companyName == newData.companyName}}
                        <p>{{:oldData.companyName}}</p>
                    {{else}}
                        <p>{{:oldData.companyName}}</p>
                        <p class="color_red">变更为：{{:newData.companyName}}</p>
                    {{/if}}
                </div>

                <div class="message_item">
                    <span><i>*</i> 开户别名：</span>
                    {{if oldData.accountName == newData.accountName}}
                        <p>{{:oldData.accountName}}</p>
                    {{else}}
                        <p>{{:oldData.accountName}}</p>
                        <p class="color_red">变更为：{{:newData.accountName}}</p>
                    {{/if}}
                </div>

                <div class="message_item">
                    <span><i>*</i> 账号：</span>
                    {{if oldData.accountNum == newData.accountNum}}
                        <p>{{:oldData.accountNum}}</p>
                    {{else}}
                        <p>{{:oldData.accountNum}}</p>
                        <p class="color_red">变更为：{{:newData.accountNum}}</p>
                    {{/if}}
                </div>

                <div class="message_item">
                    <span><i>*</i> 状态：</span>
                    {{if oldData.statusCN == newData.statusCN}}
                        <p>{{:oldData.statusCN}}</p>
                    {{else}}
                        <p>{{:oldData.statusCN}}</p>
                        <p class="color_red">变更为：{{:newData.statusCN}}</p>
                    {{/if}}
                </div>
            {{else status == "draft"}}

                <div class="message_item">
                    <span><i>*</i> 账户类型：</span>
                    {{if oldData.typeCN == newData.typeCN}}
                        <p>{{:oldData.typeCN}}</p>
                    {{else}}
                        <p>{{:oldData.typeCN}}</p>
                        <p class="color_red">变更为：{{:newData.typeCN}}</p>
                    {{/if}}
                </div>

                {{if oldData.type != "third"}}
                    <div class="message_item">
                        <span><i>*</i> 开户行：</span>
                        {{if oldData.bankName == newData.bankName}}
                            <p>{{:oldData.bankName}}</p>
                        {{else}}
                            <p>{{:oldData.bankName}}</p>
                            <p class="color_red">变更为：{{:newData.bankName}}</p>
                        {{/if}}
                    </div>
                    <div class="message_item">
                        <span><i>*</i> 支行：</span>
                        {{if oldData.branchBankName == newData.branchBankName}}
                            <p>{{:oldData.branchBankName}}</p>
                        {{else}}
                            <p>{{:oldData.branchBankName}}</p>
                            <p class="color_red">变更为：{{:newData.branchBankName}}</p>
                        {{/if}}
                    </div>
                {{/if}}

                <div class="message_item">
                    <span><i>*</i> 开户名：</span>
                    {{if oldData.companyName == newData.companyName}}
                        <p>{{:oldData.companyName}}</p>
                    {{else}}
                        <p>{{:oldData.companyName}}</p>
                        <p class="color_red">变更为：{{:newData.companyName}}</p>
                    {{/if}}
                </div>

                <div class="message_item">
                    <span><i>*</i> 开户别名：</span>
                    {{if oldData.accountName == newData.accountName}}
                        <p>{{:oldData.accountName}}</p>
                    {{else}}
                        <p>{{:oldData.accountName}}</p>
                        <p class="color_red">变更为：{{:newData.accountName}}</p>
                    {{/if}}
                </div>

                <div class="message_item">
                    <span><i>*</i> 账号：</span>
                    {{if oldData.accountNum == newData.accountNum}}
                        <p>{{:oldData.accountNum}}</p>
                    {{else}}
                        <p>{{:oldData.accountNum}}</p>
                        <p class="color_red">变更为：{{:newData.accountNum}}</p>
                    {{/if}}
                </div>

                <div class="message_item">
                    <span><i>*</i> 状态：</span>
                    {{if oldData.statusCN == newData.statusCN}}
                        <p>{{:oldData.statusCN}}</p>
                    {{else}}
                        <p>{{:oldData.statusCN}}</p>
                        <p class="color_red">变更为：{{:newData.statusCN}}</p>
                    {{/if}}
                </div>
            {{/if}}
        {{/if}}
        <a href="javascript:;" id="edit" style="position:absolute;bottom:10%;right:8%;">修改</a>
    </div>
</script>
<script type="text/template" id="accountMessageEditTpl">

        <input type="hidden" value="{{:uuid}}" name="uuid"/>
        {{if newData.type != "third"}}
            <div class="submit_logo">
                <img src="..{{:newData.bankIconUrl}}" alt="">
            </div>
        {{/if}}
        <div class="form_item">
            <span><i>*</i> 账户类型</span>
            <select name="type" id="type">
                <option value="0">请选择账户类型...</option>
                <option value="third">第三方账户</option>
                <option value="invest">投资户口</option>
                <option value="collect">募集户</option>
                <option value="redem">结算户</option>
            </select>
        </div>

        {{if newData.type != "third"}}
            <div class="form_item">
                <span><i>*</i> 开户行</span>
                <input type="text" id="bankList" value="{{:newData.bankName}}" disabled />
            </div>
            <div class="form_item">
                <span><i>*</i> 支行</span>
                <select name="branchBank" id="branchBankList">

                </select>
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
    <input id="scode" type="hidden" value="00600066" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./auditingItem.jsp">待审批事项</a><span>></span><a href="./accountMessageAuditing.jsp">企业账户信息变动申请</a><span>></span><a class="current">查看</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div id="account_message_Cnt">

                </div>

                <div class="edit">

                    <form:form class="add_account_form" action="../rest/backadmin/companyAccount/operateEdit" method="post" id="add">
                        <div id="accountMessageEditCnt">

                        </div>
                    </form:form>
                </div>


            </div>
        </div>
    </div>


    <script type="text/javascript" src="./js/url.min.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/jquery.form.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/select2.min.js"></script>
    <script type="text/javascript" src="./js/accountMessageAuditingMsg.js"></script>
</body>
</html>
