<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td>
                      	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="/entity/function/images/wt_02.gif" width="78" height="52"></td>
                            <td width="160" valign="top" class="text3" style="padding-top:25px">详细信息</td>
                            <td background="/entity/function/images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table>
                      </td>
                    </tr>
                    <tr> 
                      <td><img src="/entity/function/images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  	<tr>
                  		<td><img src="/entity/function/images/wt_04.gif" width="604" height="13"></td>
                    </tr>
                    <tr>
                    	<td background="/entity/function/images/wt_05.gif">
                    	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                        	<tr>
                            	<td><img src="/entity/function/images/wt_08.gif" width="572" height="11"></td>
                          	</tr>
                          	<tr>
                            	<td background="/entity/function/images/wt_10.gif">
                            	
                            	<table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">标题:</td>
		                                  <td width="400" valign="top" class="text1"  align="left" ><s:property value="onlineTest.title"/></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3">开始时间:</td>
		                                  <td width="400" class="text1"  align="left" ><s:property value="onlineTest.startDate"/></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3">结束时间:</td>
		                                  <td width="400" class="text1"  align="left" ><s:property value="onlineTest.endDate"/></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" align="left" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" align="left" class="text3" valign="top">说明:</td>
		                                  <td width="400" align="left" valign="top" class="text1" style="word-wrap: break-word; word-break: normal;"><s:property value="onlineTest.note" escape="true"/></td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<!-- tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">客观题是否自动判卷:</td>
		                                  <td valign="top">
		                                  	
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">答题完毕后是否显示答案:</td>
		                                  <td valign="top">
		                                  	
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr>
                          		
                          		<tr>
	                           		<td>
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
		                                <tr>
		                                  <td width="20" valign="top"><img src="/entity/function/images/ggzt.gif" width="20" height="32"></td>
				                          <td width="160" class="text3" valign="top">是否激活:</td>
		                                  <td valign="top">
		                                  	
		                                  </td>		                                  
		                                </tr>
	                            	</table>
									</td>
                          		</tr-->
                              	</table>
                              	</td>
                          	</tr>
                          <tr>
                            <td><img src="/entity/function/images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table>
                        </td>
                    </tr>
                    <tr>                            
                      <td><img src="/entity/function/images/wt_06.gif" width="604" height="11"></td>
                    </tr>
                    <tr>
                      <td align="center"><input type="button" value="关闭" style="font-size:12px;height:23;background-color:#ece9d8;border-width:1;" onclick="javascript:window.close()" /></td>
                    </tr>
                  </table></td>
                    </tr>
                  </table> <br>
                </td>
              </tr>

            </table></td>
        </tr>
      </table>
</body>
</html>
