<%
if(isInited && (input_bookCode != null)){
if (main_menu_id.equals("2")){%>
<td>
                                  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="120">
                                      <tr align="left" valign="middle">
                                        <td width="76"><img src="/images/center/index-contant-bt-01.jpg" width="68" height="68"></td>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td height="20" align="left" valign="middle"><a target="_top" title="进入前台业务系统" href="/transaction/xtwebdefault.jsp?parent_id=2" class="index-menu-3"  onclick="javascript:return gotoPage(this,2);">前台业务系统</a></td>
                                            </tr>
                                            <tr> 
                                              <td height="20" align="left" valign="middle" class="index-txt-727272">项目、产品、销售、文档管理...</td>
                                            </tr>
                                          </table>
                                          </td>
                                      </tr>
                                    </table>
</td>                                    <%}
else if(main_menu_id.equals("9")){%>
<td>
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="120">
                                      <tr align="left" valign="middle"> 
                                        <td width="76"><img src="/images/center/index-contant-bt-02.jpg" width="68" height="68"></td>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td height="20" align="left" valign="middle"><a target="_top" title="进入报表管理系统" <a href="/webreport/xtwebdefault.jsp" class="index-menu-3"  onclick="javascript:return gotoPage(this,6);">报表管理系统</a></td>
                                            </tr>
                                            <tr> 
                                              <td height="20" align="left" valign="middle" class="index-txt-727272">客户报表、财务报表、信托报表...</td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                    </table>
</td>                                    <%}
else if(main_menu_id.equals("3")){%>
<td><table width="100%" border="0" cellspacing="0" cellpadding="0" height="120">
                                      <tr align="left" valign="middle"> 
                                        <td width="76"><img src="/images/center/index-contant-bt-03.jpg" width="68" height="68"></td>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td height="20" align="left" valign="middle"><a target="_top" title="进入项目投资系统" href="/investment/xtwebdefault1.jsp?parent_id=3" class="index-menu-3"  onclick="javascript:return gotoPage(this,3);">项目投资系统</a></td>
                                            </tr>
                                            <tr> 
                                              <td height="20" align="left" valign="middle" class="index-txt-727272">贷款、租赁、担保、证券...</td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                    </table></td>
<%}else if (main_menu_id.equals("1")){%>
 <td><table width="100%" border="0" cellspacing="0" cellpadding="0" height="120">
                                      <tr align="left" valign="middle"> 
                                        <td width="76"><img src="/images/center/index-contant-bt-04.jpg" width="68" height="68"></td>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td height="20" align="left" valign="middle"><a target="_top" title="进入配置管理系统" href="/system/xtwebdefault1.jsp?parent_id=1" class="index-menu-3"  onclick="javascript:return gotoPage(this,7);">配置管理系统</a></td>
                                            </tr>
                                            <tr> 
                                              <td height="20" align="left" valign="middle" class="index-txt-727272">员工、部门、权限、币种、参数...</td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                    </table></td>
<%}else if (main_menu_id.equals("4")){%>
<td><table width="100%" border="0" cellspacing="0" cellpadding="0" height="120">
                                      <tr align="left" valign="middle"> 
                                        <td width="76"><img src="/images/center/index-contant-bt-05.jpg" width="68" height="68"></td>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td height="20" align="left" valign="middle"><a target="_top" title="进入总账财务系统" href="/financing_total/xtwebdefault1.jsp?parent_id=5" class="index-menu-3"  onclick="javascript:return gotoPage(this,5);">总账</a></td>
                                            </tr>
                                            <tr> 
                                              <td height="20" align="left" valign="middle" class="index-txt-727272">基本财务处理、集团财务处理...</td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                    </table></td>
<%}else if (main_menu_id.equals("5")){%>
<td><table width="100%" border="0" cellspacing="0" cellpadding="0"  height="120">
                                      <tr align="left" valign="middle"> 
                                        <td width="76"><img src="/images/center/index-contant-bt-06.jpg" width="68" height="68"></td>
                                        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td height="20" align="left" valign="middle"><a title="进入业务财务系统" href="/financing/xtwebdefault1.jsp?parent_id=4" class="index-menu-3"  onclick="javascript:return gotoPage(this,4);">业务财务系统</a></td>
                                            </tr>
                                            <tr> 
                                              <td height="20" align="left" valign="middle" class="index-txt-727272">输入说明</td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                    </table></td>

<%}
}%>