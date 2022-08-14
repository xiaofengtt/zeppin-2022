<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-static-top navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="main.jsp" style="padding-top:6px;">
            	<img src="img/logo-east3.0.png" alt="logo" style="width: 200px;"/>
            </a>
        </div>
        <div class="navbar-right">
        	<select class="form-product" data-live-search="true" id="form-product" style="display:none;">
        		<option value="">请选择项目</option>
        		<c:forEach items="${sessionScope.productMap}" var="productMap">
					<option value="${productMap.key}">${productMap.key}-${productMap.value}</option>
				</c:forEach>
        	</select>
            <ul class="nav navbar-nav">
                <li><a>欢迎！${sessionScope.currentOperator.name}</a></li>
                <li class="text-centext modifyPassword"><a class="btn-reset" href="resetPwd.jsp?id=${sessionScope.currentOperator.id}">修改密码</a></li>
                <li class="layout"><a href="javascript:" onclick="logout();">退出登录</a></li>
            </ul>
        </div>
    </div>
</nav>


<script>
	<%	
		Boolean flagLogin = true;
		if(session.getAttribute("currentOperator") == null){
			flagLogin = false;
		}
	%>
	var flagLogin = "<%=flagLogin%>";
	if(flagLogin == 'false'){
		window.location.href = "../views/login.jsp";
	}
	
	
	$(document).ready(function(){
		$(".btn-reset").colorbox({
			iframe: true,
			width: "400px",
			height: "520px",
			opacity: "0.5",
			overlayClose: false,
			escKey: true
		});
		//select 模糊查询
	    $('.form-product').selectpicker({  
	        'selectedText': 'cat'  
	    }).change(function(){
	    	$.cookie('form-product',$("select.form-product").val());
	    });
		if($.cookie('form-product')){
			$('#form-product').selectpicker('val',$.cookie('form-product'));                        		 
    		$("#form-product").selectpicker('refresh');
		}
	    
	});
	//退出登录
	function logout(){
		$.ajax({
            url: '../rest/backadmin/operator/logout',
            type: 'get',
            data: {
               
            }
        })
        .done(function(r) {
        	window.location.href="../views/login.jsp";
        	$.cookie('form-product','');
        });
	}
</script>