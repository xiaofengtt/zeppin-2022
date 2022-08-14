<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理-用户提现手动处理</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/fundList.css">
<link rel="stylesheet" href="./css/userControl.css">
<link rel="stylesheet" href="./css/msg_table.css">
<style>
    #excel_import{
        position: relative;

    }
    #excel_import #excelSubmit{
        position: absolute;
        left:0;
        top:0;
        width:100%;
        height:100%;
    }
    #excel_import #excelSubmit #fileSubmit{
        position: absolute;
        left:0;
        top:0;
        display:inline-block;
        width:100%;
        height:100%;
        opacity: 0;
        cursor:pointer;
    }
    .controlling_box{
        margin-top:0;
    }
</style>
<!--未处理-->
<script type="text/template" id="unprocessTpl">
    <tr>
        <td>
            <input type="checkbox" name="" value="" class="checkbox" style="display:inline">
            <input type="hidden" name="" value="{{:uuid}}" class="uuid">
        </td>
        <td>
            {{if qcbEmployeeName}}
                {{:qcbEmployeeName}}
            {{else}}
                {{:qcbCompanyAccountName}}
            {{/if}}
        </td>
        <td class="color_orange">{{:price}}</td>
        <td>{{:orderTypeCN}}</td>
        <td>{{:application}}</td>
        <td class="color_red">{{:orderMessage}}</td>
        <td>{{:createtimeCN}}</td>
        <td>
            <a class="retry">重试</a>
            <a class="fail">提现失败</a>
        </td>
    </tr>
</script>



<!--处理中-->
<script type="text/template" id="processingTpl">
    <tr>
        <td>
            {{if qcbEmployeeName}}
                {{:qcbEmployeeName}}
            {{else}}
                {{:qcbCompanyAccountName}}
            {{/if}}
        </td>
        <td>{{:price}}</td>
        <td>{{:orderTypeCN}}</td>
        <td>{{:application}}</td>
        <td>{{:orderMessage}}</td>
        <td>{{:createtimeCN}}</td>
        <!-- <td>{{:processCompanyAccountName}}</td>
        <td>{{:processCreatorName}}</td> -->
    </tr>
</script>

<!--已处理-->
<script type="text/template" id="successTpl">
    <tr>
        <td>
            {{if qcbEmployeeName}}
                {{:qcbEmployeeName}}
            {{else}}
                {{:qcbCompanyAccountName}}
            {{/if}}
        </td>
        <td>{{:price}}</td>
        <td>{{:orderTypeCN}}</td>
        <td>{{:application}}</td>
        <td>{{:orderMessage}}</td>
        <td>{{:createtimeCN}}</td>
        <td>{{:processCompanyAccountName}}</td>
        <td>{{:processCreatorName}}</td>
        <td>{{:processCreatetimeCN}}</td>

    </tr>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00800088" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">企财宝网站管理</a><span>></span><a class="current">用户提现手动处理</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <form:form class="" action="index.html" method="post">

                </form:form>
                <ul class="select_page">
                    <li class="item">
                        <a class="highLight">
                            未处理
                            <span id="unprocessCount">(0)</span>
                        </a>
                    </li>
                    <li class="item">
                        <a>
                            处理中
                            <span id="processingCount">(0)</span>
                        </a>
                    </li>
                    <li class="item">
                        <a>
                            已处理
                            <span id="successCount">(0)</span>
                        </a>
                    </li>
                </ul>
                <div class="condition">
                    <div class="statusDiv shortStatusDiv filter">
                        <label>类型：</label>
                        <div>
                            <a class="statusLight" id="company">企业</a>
                            <a id="employee">员工</a>
                        </div>
                    </div>
                </div>
                <div class="searchDiv" style="top:73px;right:26px">
                    <div class="input-group">
                        <input id="search" class="form-control" type="text" value="" placeholder="搜索">
                        <label class="input-group-addon" onclick="searchBtn()"></label>
                    </div>
                </div>

                <div class="unControl_box">
                    <div class="unControl_box_item">
                        <a id="selectAll">全选</a>
                        <a id="retryAll">批量重试</a>
                        <a id="excel_import">
                            批量付款（Excel导入）
                            <form:form id="excelSubmit" action="../rest/backadmin/resource/add" method="post">
                                <input type="file" name="file" value="" id="fileSubmit" accept="application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
                            </form:form>
                        </a>
                        <a id="unpayList" href="../rest/backadmin/qcbWithdraw/companyExport?status=unprocess">导出未付款名单</a>
                        <a id="successAll">批量设置处理中</a>
                        <a id="failAll">批量设置处理中</a>
                    </div>



                    <table class="msg_table" cellspacing="0" cellpadding="0">
                        <tr class="first_tr">
                            <th>勾选</th>
                            <th>用户</th>
                            <th>提现金额</th>
                            <th>第三方通道</th>
                            <th>应用</th>
                            <th>原因</th>
                            <th>提现时间</th>
                            <th>操作</th>
                        </tr>
                        <tbody id="unprocessCnt">

                        </tbody>

                    </table>
                </div>

                <div class="controlling_box">

                    <table class="msg_table">
                        <tr class="first_tr">
                            <th>用户</th>
                            <th>提现金额</th>
                            <th>第三方通道</th>
                            <th>应用</th>
                            <th>原因</th>
                            <th>提现时间</th>
                            <!-- <th>转账账户</th>
                            <th>处理人</th> -->
                        </tr>
                        <tbody id="processingCnt">

                        </tbody>

                    </table>
                </div>

                <div class="control_box">

                    <table class="msg_table">
                        <tr class="first_tr">
                            <th>用户</th>
                            <th>提现金额</th>
                            <th>第三方通道</th>
                            <th>应用</th>
                            <th>原因</th>
                            <th>提现时间</th>
                            <th>转账账户</th>
                            <th>处理人</th>
                            <th>处理时间</th>
                        </tr>

                        <tbody id="successCnt">

                        </tbody>

                    </table>
                </div>



                <div id="pageTool"></div>

            </div>
        </div>
    </div>
    <script type="text/javascript" src="./js/url.min.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/jquery.form.js"></script>
    <script type="text/javascript" src="./js/QCBhandDrawcash.js"></script>


</body>
</html>
