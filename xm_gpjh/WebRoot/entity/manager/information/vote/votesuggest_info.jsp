<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<html>
<head><title></title>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<link href="/entity/manager/css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">详细信息</div id="zlb_title_end"></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb"> 
<form name='user-form' action='' method='post' class='nomargin' onsubmit="">
        <div class="cntent_k" id="zlb_content_start">
          <table width="96%" border="0" cellpadding="0" cellspacing="0">
            <tr valign="bottom" class="postFormBox"> 
              <td width="82" valign="bottom"><span class="name">问卷标题：</span></td>
              <td><s:property value="bean.peVotePaper.title"/>  </td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">内容：</span></td>
              <td><s:property value="bean.note" escape="false"/></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">时间：</span></td>
              <td><s:date name="bean.foundDate" format="yyyy-MM-dd HH:mm:ss" /></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">IP：</span></td>
              <td><s:property value="bean.ip"/></td>
            </tr>
           
          </table>
      </div>
</form>
    </td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
          <td align="center" valign="middle"><span class="norm"  style="width:46px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'"><span class="text" onclick="window.close()">关闭</span></span></td>
        </tr>
      </table></td>
  </tr>
</table>

</body>
</html>
