<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
        <s:iterator value="prVoteQuestionList" id="class" status="num">
       	 <s:set name="max" value="itemNum" />
       
        <tr>
          <td class="12content">问题<s:property value="#num.index+1"/>：<s:property value="#class.getQuestionNote()" escape="false"/></td>
          </tr>
        <tr>
          <td style="padding-left:40px"><table width="80%" border="0" cellspacing="0" cellpadding="0" class="12content"  >
             <s:if test="#class.getItem1()!=null">
              <tr> 
                <td width="20%">（1）<s:property value="#class.getItem1()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult1()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult1()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult1()>0"><s:property value="#class.getItemResult1()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem2()!=null">
              <tr> 
                <td width="20%">（2）<s:property value="#class.getItem2()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult2()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult2()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult2()>0"><s:property value="#class.getItemResult2()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
             <s:if test="#class.getItem3()!=null">
              <tr> 
                <td width="20%">（3）<s:property value="#class.getItem3()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td><s:if test="#class.getItemResult3()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult3()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult3()>0"><s:property value="#class.getItemResult3()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem4()!=null">
              <tr> 
                <td width="20%">（4）<s:property value="#class.getItem4()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult4()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult4()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult4()>0"><s:property value="#class.getItemResult4()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>    
             <s:if test="#class.getItem5()!=null">
              <tr> 
                <td width="20%">（5）<s:property value="#class.getItem5()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td><s:if test="#class.getItemResult5()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult5()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult5()>0"><s:property value="#class.getItemResult5()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem6()!=null">
              <tr> 
                <td width="20%">（6）<s:property value="#class.getItem6()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult6()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult6()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult6()>0"><s:property value="#class.getItemResult6()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem7()!=null">
              <tr> 
                <td width="20%">（7）<s:property value="#class.getItem2()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult7()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult7()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult7()>0"><s:property value="#class.getItemResult7()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
             <s:if test="#class.getItem8()!=null">
              <tr> 
                <td width="20%">（8）<s:property value="#class.getItem8()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td><s:if test="#class.getItemResult8()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult8()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult8()>0"><s:property value="#class.getItemResult8()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem9()!=null">
              <tr> 
                <td width="20%">（9）<s:property value="#class.getItem9()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult9()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult9()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult9()>0"><s:property value="#class.getItemResult9()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
            <s:if test="#class.getItem10()!=null">
              <tr> 
                <td width="20%">（10）<s:property value="#class.getItem10()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult10()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult10()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult10()>0"><s:property value="#class.getItemResult10()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
            <s:if test="#class.getItem11()!=null">
              <tr> 
                <td width="20%">（11）<s:property value="#class.getItem11()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult11()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult11()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult11()>0"><s:property value="#class.getItemResult11()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>               
             <s:if test="#class.getItem12()!=null">
              <tr> 
                <td width="20%">（12）<s:property value="#class.getItem12()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult12()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult12()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult12()>0"><s:property value="#class.getItemResult12()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
             <s:if test="#class.getItem13()!=null">
              <tr> 
                <td width="20%">（13）<s:property value="#class.getItem13()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td><s:if test="#class.getItemResult13()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult13()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult13()>0"><s:property value="#class.getItemResult13()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.getItem14()!=null">
              <tr> 
                <td width="20%">（14）<s:property value="#class.getItem14()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0"  >
                    <tr> 
                      <td><s:if test="#class.getItemResult14()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult14()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult14()>0"><s:property value="#class.getItemResult14()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>    
             <s:if test="#class.getItem15()!=null">
              <tr> 
                <td width="20%">（15）<s:property value="#class.getItem15()" /></td>
                <td width="60%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td><s:if test="#class.getItemResult15()>0"><img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getItemResult15()*300/#max" />" height=10></s:if></td>
                    </tr>
                  </table>
                </td>
                <td style="padding-left:5px">共计<s:if test="#class.getItemResult15()>0"><s:property value="#class.getItemResult15()" /></s:if><s:else>0</s:else>票</td>
              </tr>
              
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
                                                                  
                </table></td>
              </tr>     
        </s:iterator>    	

