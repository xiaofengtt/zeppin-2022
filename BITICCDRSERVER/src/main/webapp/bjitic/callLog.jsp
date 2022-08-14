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

                <td>{{:id}}</td>


                <!--经理名称-->
                <td>{{:opName}}</td>

                <!--客户名称-->
                <td>{{:custName}}</td>

                <td>{{:toMobile}}</td>
                <td>{{:tcPhone}}</td>
                <td>{{:toTel}}</td>
                <td>{{:tcTel}}</td>
                <td>{{:startcallertime}}</td>
                <td>{{:abstartcalltime}}</td>
                <td>{{:abstopcalltime}}</td>
                <td>{{:callerduration}}</td>
                <td>{{:calledduration}}</td>
                <td>{{:srfmsgid}}</td>
                <td>
                    {{if realpath != ""}}
                        <a href="../rest/backadmin/mslist/download?id={{:id}}">下载</a>
                    {{else}}
                        未下载
                    {{/if}}
                </td>
                <td>{{:callerrelcauseCN}}</td>
                <td>{{:calledrelcauseCN}}</td>
            </tr>
        </script>
        </head>

        <body>
        <div class="nav-bar">
            <span>主页</span> >>
            <span>信托号码关系设置</span>
            <a href="./setTo.jsp">设置经理</a>
            <a href="./default.jsp">查看绑定关系</a>
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
                <button id="clear">重置</button>
                <button id="search">查询</button>
            </div>
        </div>
        <div class="function-bar">
            <!-- <button id="add">新增</button> -->
            <!-- <button id="adds">批量添加</button> -->
            <%-- <button id="deletes">批量删除</button> --%>
        </div>

        <table cellspacing="1" cellpadding="2" class="table">
            <tr class="first-tr">
                <th width="3%">ID</th>
                <th width="5%">客户经理</th>
                <th width="5%">客户名称</th>
                <th width="7%">主叫号码</th>
                <th width="7%">客户联系方式</th>
                <th width="7%">呼出号码</th>
                <th width="7%">呼入号码</th>
                <th width="7%">主叫应答时间</th>
                <th width="7%">AB通话开始时间</th>
                <th width="7%">AB通话结束时间</th>
                <th width="5%">主叫通话时长</th>
                <th width="5%">被叫通话时长</th>
                <th width="7%">通话录音路径</th>
                <th width="7%">本地录音路径</th>
                <th width="5%">主叫结束原因</th>
                <th width="5%">被叫结束原因</th>
            </tr>
            <tbody id="queboxCnt">

            </tbody>


        </table>

        <div id="pageTool"></div>

        <script src="./js/jquery-1.11.1.js"></script>
        <script src="./js/jquery-ui.min.js"></script>
        <script src="./js/select2-debug.js"></script>
        <script src="./js/query.js"></script>
        <script src="./js/paging.js"></script>
        <script src="./laydate-v1.1/laydate/laydate.js"></script>
        <script src="./js/jsrender.min.js"></script>
        <script src="./js/select2.js"></script>
        <script src="./js/jquery.form.js"></script>
        <script src="./js/callLog.js"></script>
    </body>
</html>
