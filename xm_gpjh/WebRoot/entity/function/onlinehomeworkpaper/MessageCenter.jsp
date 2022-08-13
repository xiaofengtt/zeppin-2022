<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<script type="text/javascript">
		
  		 var messageinfo = '<s:property value='messageinfo'/>';
  		
  			if(messageinfo.length>0){
  				alert(messageinfo);
  			}
  			var url ='<s:property value='returnurl'/>';
  			var tar = url.substring(0,1);
  			var jstar = url.substring(0,10);
  			if(tar=="A"){
  				window.parent.location=url.substring(1,url.lenght);
  			}else{
  				window.location=url;
  			}
  			if(jstar.indexOf('javascript')!=-1){
  				var action =  url.substring(url.indexOf(":")+1,url.lastIndexOf(":"));
  				var backurl =  url.substring(url.lastIndexOf(":")+1);
  				if(action.indexOf('back')!=-1){
  					window.location=backurl;
  				}
  				if(action.indexOf('go')!=-1){
  					window.history.back();
  				}
  				if(action.indexOf('close')!=-1){
  					window.close();
  				}
  			}
  	  </script>
	</head>
	<body>
	</body>
</html>
