<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>发送短信</title>
<link rel="stylesheet" type="text/css" media="screen" href="css/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css"/>
<script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>

<script src="js/jquery-ui-1.10.3.custom.min.js" type="text/javascript">
<script src="js/jquery-ui-i18n.js" type="application/x-javascript"></script>
<script src="js/jquery.ui.datepicker-zh-CN.js" type="application/x-javascript"></script>
</script>
<script src="js/Untitled-1.js" type="text/javascript"></script>
</head>

<body>
<div class="container">
  <div class="header"> 
  <!--<span>
    <input name="所属项目" type="checkbox" value="0" checked="checked" />
    <label>所属项目</label>
    </span> <span>
    <input name="所属项目" type="checkbox" value="0" checked="checked" />
    <label>所属项目</label>
    </span> -->
    <!-- end .header --></div>
  <div class="content">
    <div class="list">
      <table id="list2">
      </table>
      <div id="pager2"></div>
    </div>
    <!-- end .content --></div>
  <div class="send" id='sendSms' title="发送短信通知" style="display:none">
    <fieldset>
  
      <div class="form-row">
        <div class="field-label">
          <label for="field3">短信内容($PWD$代表登陆码所在位置)</label>
          :</div>
        <div class="field-widget">
          <textarea class="required" id="content"></textarea>
        </div>
           <div class="form-row">
            <div class="field-label"><label for="field1">签名：</label>:</div>
            <div class="field-widget"><input name="field1" id="sign" value="【国培办】" class="required" title="自动生成" /></div>
        </div>
       
      </div>
    </fieldset>
    
  </div>
  <!-- end .container --></div>
</body>
</html>