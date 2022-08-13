<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>华南师范大学教学教务平台</title>
<script language="JavaScript" src="js/frame.js"></script>
<script type="text/javascript">
	function   window.onunload()   { 
		if(window.screenLeft>10000   ||   window.screenTop>10000){ 
			var url="/sso/login_close.action";
			if (window.XMLHttpRequest) {
		        req = new XMLHttpRequest( );
		    }
		    else if (window.ActiveXObject) {
		        req = new ActiveXObject("Microsoft.XMLHTTP");
		    }
		    req.open("Get",url,true);
		    req.onreadystatechange = function(){
		    	if(req.readyState == 4);
		    };
	  		req.send(null);
  		}
	}
	function logout(){
<s:set value="%{@com.whaty.platform.sso.web.action.SsoConstant@SSO_USER_SESSION_KEY_BAK}" name="sso"/>
<s:if test="#session[#sso]!=null&&#session[#sso].getId()!=null&&#session[#sso].getId().length()>0">
		window.top.close();
</s:if><s:else>
		var url="/sso/login_close.action";
		if (window.XMLHttpRequest) {
	        req = new XMLHttpRequest();
	    }
	    else if (window.ActiveXObject) {
	        req = new ActiveXObject("Microsoft.XMLHTTP");
	    }
	    req.open("Get",url,true);
	    req.onreadystatechange = function(){
	    	if(req.readyState == 4){
		    	window.top.navigate("/");
	    	}
	    };
  		req.send(null);
</s:else>
	}
</script>
</head>
<frameset rows="96,*,37" frameborder="no" border="0" framespacing="0" name="TCB">
   <frame src="work/topBar.htm" name="banner" scrolling="NO" noresize>
	<frameset cols="197,*" frameborder="no" border="0" framespacing="0" name="TC">
		<frame src="work/tree.htm" name="tree"scrolling="no" noresize>
		<frame src="work/content.htm" name="content"  scrolling="no" noresize>
	</frameset>
	<frame src="work/bottom.htm" name="bottom" scrolling="NO" noresize>
</frameset>
<noframes></noframes>
<body>
</body>
</html>
