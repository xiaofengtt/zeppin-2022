<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
        <meta http-equiv="Cache-Control" content="no-store" />
        <meta http-equiv="Pragma" content="no-cache" />
        <meta http-equiv="Expires" content="0" />
        <link rel="stylesheet" href="./css/base.css" />
        <link rel="stylesheet" href="./css/jquery-ui.min.css" />
        <link rel="stylesheet" type="text/css" href="css/jquery-ui.structure.min.css" />
        <link rel="stylesheet" href="./css/paging.css" />
        <link rel="stylesheet" href="./css/select2.min.css" />
        <link rel="stylesheet" href="./css/default.css" />
        <script type="text/javascript">
    	<%	
			Boolean flagLogin = true;
			if(session.getAttribute("username") == null){
				flagLogin = false;
			}
		%>
		var flagLogin = "<%=flagLogin%>";
		if(flagLogin == 'false'){
			window.location.href = "../views/login.jsp";
		}
        </script>
        <script id="queboxTpl" type="text/template">
            <tr {{if #index % 2 == 1}}class="bg-gray"{{/if}}>
                <td>
                    <input type="checkbox" class="check" data-id="{{:id}}" />
                </td>
                <td>{{:id}}</td>


                <!--经理名称-->
                <td>{{:opName}}</td>

                <!--主叫-->
                <td>{{:toMobile}}</td>

                <!--呼出号码-->
                <td>{{:toTel}}</td>

                <!--客户名称-->
                <td>{{:custName}}</td>

                <!--呼入号码-->
                <td>{{:tcTel}}</td>

                <!--被叫号码-->
                <td>{{:tcPhone}}</td>


                {{if status == "normal"}}
                    <td style="color:green">{{:statusCN}}</td>
                {{else status == "error"}}
                    <td style="color:red">{{:statusCN}}</td>
                {{else status == "unable"}}
                    <td style="color:blue">{{:statusCN}}</td>
                {{/if}}

                <td>{{:createtimeCN}}</td>
                <td>
                    <a class="edit" data-id="{{:id}}">编辑</a>
                    {{if status == "normal"}}
                        <a class="delete" data-id="{{:id}}">删除</a>
                    {{/if}}
                </td>
            </tr>
        </script>
        </head>

        <body>
        <div class="nav-bar">
            <span>主页</span> >>
            <span>信托号码关系设置</span>
            <a href="./setTo.jsp">设置经理</a>
            <a href="./callLog.jsp">查看通话记录</a>
        </div>
        <div class="top">
            <div class="top-item">
                <label for="">客户经理：</label>
                <input type="text" id="oname-name" placeholder="请输入客户经理" />
            </div>
            <div class="top-item">
                <label for="">客户名称：</label>
                <input type="text" id="name-name" placeholder="请输入客户名称" />
            </div>
            <div class="top-item">
                <label for="">主叫：</label>
                <input type="text" placeholder="请输入主叫" id="toMobile-name"/>
            </div>
            <div class="top-item">
                <label for="">呼出号码：</label>
                <input type="text" placeholder="请输入呼出号码" id="toTel-name" />
            </div>

            <div class="top-item">
                <label for="">呼入号码：</label>
                <input type="text" placeholder="请输入呼入号码" id="tcTel-name" />
            </div>
            <div class="top-item">
                <label for="">客户联系方式：</label>
                <input type="text" placeholder="请输入客户联系方式" id="tcPhone-name" />
            </div>
            <div class="top-item">
                <label for="">状态：</label>
                <select name="" id="status-name" class="select">
                    <option value="">请选择状态</option>
                    <option value="normal">正常</option>
                    <option value="error">异常</option>
                </select>
            </div>
            <div class="top-item">
                <button id="clear">重置</button>
                <button id="search">查询</button>
            </div>
        </div>
        <div class="function-bar">
            <%-- <button id="add">新增</button> --%>
            <!-- <button id="adds">批量添加</button> -->
            <button id="deletes">批量删除</button>
        </div>

        <table cellspacing="1" cellpadding="2" class="table">
            <tr class="first-tr">
                <th width="3%">
                    <input type="checkbox" id="allCheck" />
                </th>
                <th width="3%">ID</th>
                <th width="8%">客户经理</th>
                <th width="8%">主叫</th>
                <th width="8%">呼出号码</th>

                <th width="8%">客户姓名</th>
                <th width="8%">呼入号码</th>
                <th width="8%">客户联系方式</th>

                <th width="5%">状态</th>
                <th width="8%">更新时间</th>
                <th width="10%">操作</th>
            </tr>
            <tbody id="queboxCnt">

            </tbody>


        </table>

        <div id="pageTool"></div>

        <!--添加-->
        <div class="add-box" id="add-box" title="添加绑定关系">
            <form action="../rest/backadmin/number/addnr" method="post" id="add-form">
                <input type="hidden" class="tono" name="tono"/>
                <div class="add-box-content">
                    <div class="add-box-item" id="opCodePar">
                        <label for="">经理名称：</label>
                        <select name="opCode" class="selectManager opCode select" size="1" style="width:196px;">
                            <option value="">请选择经理名称</option>
                        </select>
                    </div>

                    <div class="add-box-item">
                        <label for="">主叫号码：</label>
                        <input type="text" name="toMobile" value="" class="text toMobile" placeholder="请输入主叫号码" readonly="true"/>
                    </div>

                    <div class="add-box-item">
                        <label for="">呼出号码：</label>
                        <input type="text" name="toTel" value="" class="text toTel" placeholder="请输入呼入号码" readonly="true" />
                    </div>

                    <div class="add-box-item" id="custIdPar">
                        <label for="">客户名称：</label>
                        <select name="custId" class="custId selectCustomer" size="1" style="width:196px;">
                            <option value="">请选择经理名称</option>
                        </select>
                    </div>

                    <div class="add-box-item">
                        <label for="">客户联系方式：</label>
                        <input type="text" name="tcPhone" value="" class="text tcPhone" placeholder="请输入客户联系方式" readonly="true"/>
                    </div>


                    <div class="add-box-item">
                        <label for="">呼入号码：</label>
                        <select name="tcTel" class="tcTel select" size="1" style="width:196px;">
                            <option value="0"></option>
                        </select>
                    </div>

                </div>
            </form>
        </div>


        <!--修改-->
        <div class="add-box" id="edit-box" title="修改绑定关系">
            <form action="../rest/backadmin/number/editnr" method="post" id="edit-form">
                <input type="hidden" class="tono" name="tono"/>
                <input type="hidden" id="id" name="id" value="" />
                <div class="add-box-content">
                    <div class="add-box-item">
                        <label for="">经理名称：</label>
                        <input name="" class="opCode text" size="1" readonly />
                        <input type="hidden" name="opCode">
                    </div>

                    <div class="add-box-item">
                        <label for="">主叫号码：</label>
                        <input type="text" name="toMobile" id="toMobile-edit" value="" class="text toMobile" placeholder="请输入主叫号码" readonly="true"/>
                    </div>

                    <div class="add-box-item">
                        <label for="">呼出号码：</label>
                        <input type="text" name="toTel" id="toTel-edit" value="" class="text toTel" placeholder="请输入呼入号码" readonly="true" />
                    </div>

                    <div class="add-box-item">
                        <label for="">客户名称：</label>
                        <input name="" class="custId text" size="1" readonly>
                        <input type="hidden" name="custId">
                    </div>

                    <div class="add-box-item">
                        <label for="">客户联系方式：</label>
                        <input type="text" name="tcPhone" id="tcPhone-edit" value="" class="text tcPhone" placeholder="请输入被叫号码"/>
                    </div>

                    <div class="add-box-item">
                        <label for="">呼入号码：</label>
                        <select name="tcTel" id="tcTel-edit" class="tcTel select" size="1" style="width:196px;">

                        </select>
                    </div>

                    <!--<div class="add-box-item">
                        <label for="">关系状态：</label>
                        <input type="radio" name="no" checked="true" />可用
                        <input type="radio" name="no" id="" value="" />不可用
                    </div>-->


                    <!--<div class="add-box-item">
                        <label for="">过期时间：</label>
                        <input type="text" name="" id="" value="" class="text date rq" readonly="true" />
                    </div>-->
                </div>
            </form>
        </div>

        <div class="delete-box" title="提示">
            <p class="delete-box-p">确认要删除吗?</p>
        </div>
        <div class="deletes-box" title="提示">
            <p class="delete-box-p">确认要删除已选项吗?</p>
        </div>
        <script src="./js/jquery-1.11.1.js"></script>
        <script src="./js/jquery-ui.min.js"></script>
        <script src="./js/select2-debug.js"></script>
        <script src="./js/query.js"></script>
        <script src="./js/paging.js"></script>
        <script src="./laydate-v1.1/laydate/laydate.js"></script>
        <script src="./js/jsrender.min.js"></script>
        <script src="./js/select2.js"></script>
        <script src="./js/jquery.form.js"></script>
        <script src="./js/default.js"></script>
    </body>
</html>
