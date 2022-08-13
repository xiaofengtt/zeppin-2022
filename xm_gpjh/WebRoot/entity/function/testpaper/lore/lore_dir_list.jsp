<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.text.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ include file="../../pub/priv.jsp"%>

<%
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		String loreDirId = request.getParameter("loreDirId");
		String courseId1 = openCourse.getBzzCourse().getId();
		String rootDirId = testManage.getLoreDirByGroupId(courseId).getId();		
		if(rootDirId == null) {		//如果目录不存在则自动创建一个LoreDir
			String courseName = openCourse.getBzzCourse().getName();
			String note = "课程" + courseName + "的知识点目录";
			String creatdate = DateFormat.getDateTimeInstance().format(new Date());
			testManage.addLoreDir(courseName,note,"0",creatdate,courseId);
			rootDirId = testManage.getLoreDirByGroupId(courseId).getId();
		}
		
		if(loreDirId == null) {
			//loreDirId = "0";			
			loreDirId = rootDirId;
		}
		
		LoreDir loreDir = testManage.getLoreDir(loreDirId);
		List subLoreDirList = loreDir.getSubLoreDirList();
		List loreList = loreDir.getLoreList();
		
		String loreDirName = loreDir.getName();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点目录</title>
<link href="css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function doAdd() {
		var para = "";
		var obj = document.getElementsByName("loreId");
		var j=0;
		for(i=0; i<obj.length; i++)
			if(obj[i].checked)
			{
				para = para + obj[i].value + "::";
				j++;
			}
		if(para.length > 0) {
			para = para.substring(0, para.length-2);
			window.opener.addRow(para);			
			alert("已选择"+j+"个知识点");
		} else {
			alert("请选择知识点");
		}
	}
</script>
</head>
<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="86" valign="top" background="images/top_01.gif"><img src="images/zsd.gif" width="217" height="86"></td>
              </tr>
              <tr>
                <td align="center" valign="top"><table width="608" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="78"><img src="images/wt_02.gif" width="78" height="52"></td>
                            <td width="100" valign="top" class="text3" style="padding-top:25px">知识点目录</td>
                            <td background="images/wt_03.gif">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td><img src="images/wt_01.gif" width="605" height="11"></td>
                    </tr>
                  <tr>
                            
                      <td><img src="images/wt_04.gif" width="604" height="13"></td>
                          </tr>
                          <tr>
                            <td background="images/wt_05.gif"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr>
                            <td>
                            	<table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="38" valign="bottom"><img src="images/wt_07.gif" width="38" height="25"></td>
                                  <td valign="bottom" class="text1">当前目录：<%=loreDirName%></td>
                                  <td valign="bottom" class="text1" align="right"><input type="button" value="添加" onclick="doAdd()"></td>
                                </tr>
                              	</table>
                            </td>
                          </tr>
                          <tr>
                            <td><img src="images/wt_08.gif" width="572" height="11"></td>
                          </tr>
                          <tr>
                            <td background="images/wt_10.gif"><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <%
                            	for(int i=0; i<subLoreDirList.size(); i++) {
                            		LoreDir dir = (LoreDir)subLoreDirList.get(i);
                            		String dirId = dir.getId();
                            		String dirName = dir.getName();
                            %>
                                <tr>
                                  <td width="10%" align="center"><img src="images/wtxb1.gif" width="25" height="35"></td>
                                  <td>
                                  	<a href="lore_dir_list.jsp?loreDirId=<%=dirId%>"><%=dirName%></a>
                                  </td>
                                </tr>
                            <%
                            	}
                            	
                            	for(int i=0; i<loreList.size(); i++) {
                            		Lore lore = (Lore)loreList.get(i);
                            		String loreId = lore.getId();
                            		String loreName = lore.getName();
                            %>
                                <tr>
                                  <td align="center"><img src="images/wtxb2.gif" width="25" height="35"></td>
                                  <td>
                                  	<input type="checkbox" name="loreId" value="<%=loreId + "|" + loreName%>"><a href="#" onclick="javascript:window.open('../../lore/lore_info.jsp?id=<%=loreId%>','newwindow', 'height=300, width=600, toolbar=no , menubar=no, scrollbars=yes, resizable=no, location=no, status=no')"><%=loreName%></a>
                                  </td>
                                </tr>
                            <%
                            	}
                            %>
                              </table></td>
                          </tr>
                          <tr>
                            <td><img src="images/wt_09.gif" width="572" height="11"></td>
                          </tr>
                        </table></td>
                          </tr>
                          <tr>
                            
                      <td><img src="images/wt_06.gif" width="604" height="11"></td>
                          </tr>
                    <tr>
                      <td align="center">
                      <%
                      	if(!loreDirId.equals(rootDirId)) {
                      %>
                      <a href="lore_dir_list.jsp?loreDirId=<%=loreDir.getFatherDir()%>" class="tj">[返回上层目录]</a>
                      <%
                      	}
                      %>
                      </td>
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
<%
	} catch (Exception e) {
		out.print(e.toString());
	}
%>
</html>
