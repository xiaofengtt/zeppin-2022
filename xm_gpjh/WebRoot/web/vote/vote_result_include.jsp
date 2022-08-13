<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>

<s:iterator value="prVoteAnswerList" id="class" status="num">
<s:set name="total" value="voteNumber"/>
    <tr>
		<td class="12content" ><span class="14title">问题<s:property value="#num.index+1"/>：</span><s:property value="#class.prVoteQuestion.questionNote" escape="false"/></td>
    </tr>
	<tr>
		<td style="padding-left:40px">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="12content"  >
            <s:if test="#class.prVoteQuestion.getItem1()!=null">
              <tr> 
                <td width="20%">（1）<s:property value="#class.prVoteQuestion.getItem1()" /></td>
                <td width="40%">
                	<table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.getAnswer1()>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getAnswer1() * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
                <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer1>0?#class.answer1:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer1>0?@com.whaty.platform.util.Const@div(#class.answer1*100,#total,2) : 0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.prVoteQuestion.getItem2()!=null">
              <tr> 
                <td width="20%">（2）<s:property value="#class.prVoteQuestion.getItem2()" /></td>
                <td width="40%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
                    <tr> 
                      <td>
                      	<s:if test="#class.answer2>0">
                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer2 * 300/#total" />" height="10" />
                      	</s:if>
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer2>0?#class.answer2:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer2>0?@com.whaty.platform.util.Const@div(#class.answer2*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
             <s:if test="#class.prVoteQuestion.item3!=null">
              <tr> 
                <td width="20%">（3）<s:property value="#class.prVoteQuestion.item3" /></td>
                <td width="40%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td>
                      	<s:if test="#class.answer3>0">
                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer3 * 300/#total" />" height="10" />
                      	</s:if>
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer3>0?#class.answer3:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer3>0?@com.whaty.platform.util.Const@div(#class.answer3*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.prVoteQuestion.item4!=null">
              <tr> 
                <td width="20%">（4）<s:property value="#class.prVoteQuestion.item4" /></td>
                <td width="40%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
                    <tr> 
                      <td>
                      	<s:if test="#class.answer4>0">
                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer4 * 300/#total" />" height="10" />
                      	</s:if>
                      </td>
                    </tr>
                  </table>
                </td>
                 <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer4>0?#class.answer4:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer4>0?@com.whaty.platform.util.Const@div(#class.answer4*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>    
             <s:if test="#class.prVoteQuestion.item5!=null">
              <tr> 
                <td width="20%">（5）<s:property value="#class.prVoteQuestion.item5" /></td>
                <td width="40%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
                      <td>
                      	<s:if test="#class.answer5>0">
                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer5*300/#total" />" height="10" />
                      	</s:if>
                      </td>
                    </tr>
                  </table>
                </td>
                 <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer5>0?#class.answer5:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer5>0?@com.whaty.platform.util.Const@div(#class.answer5*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.prVoteQuestion.item6!=null">
              <tr> 
                <td width="20%">（6）<s:property value="#class.prVoteQuestion.item6" /></td>
                <td width="40%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
                    <tr> 
                      <td>
                      	<s:if test="#class.answer6>0">
                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer6*300/#total" />" height="10" />
                      	</s:if>
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer6>0?#class.answer6:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer6>0?@com.whaty.platform.util.Const@div(#class.answer6*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.prVoteQuestion.item7!=null">
              <tr> 
                <td width="20%">（7）<s:property value="#class.prVoteQuestion.item7" /></td>
                <td width="40%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
                    <tr> 
						<td>
	                      	<s:if test="#class.getAnswer7()>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getAnswer7() * 300/#total" />" height="10" />
	                      	</s:if>
						</td>
                    </tr>
                  </table>
                </td>
                 <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer7>0?#class.answer7:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer7>0?@com.whaty.platform.util.Const@div(#class.answer7*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
             <s:if test="#class.prVoteQuestion.item8!=null">
              <tr> 
                <td width="20%">（8）<s:property value="#class.prVoteQuestion.item8" /></td>
                <td width="40%">    
                <table  height="12" border="0" cellpadding="0" cellspacing="0" >
                    <tr> 
						<td>
							<s:if test="#class.getAnswer8()>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getAnswer8() * 300/#total" />" height="10" />
	                      	</s:if>
	                    </td>
                    </tr>
                  </table>
                </td>
                <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer8>0?#class.answer8:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer8>0?@com.whaty.platform.util.Const@div(#class.answer8*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.prVoteQuestion.item9!=null">
              <tr> 
                <td width="20%">（9）<s:property value="#class.prVoteQuestion.item9" /></td>
                <td width="40%">    
               		<table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.getAnswer9()>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.getAnswer9() * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
                <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer9>0?#class.answer9:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer9>0?@com.whaty.platform.util.Const@div(#class.answer9*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
            <s:if test="#class.prVoteQuestion.item10!=null">
              <tr> 
                <td width="20%">（10）<s:property value="#class.prVoteQuestion.item10" /></td>
                <td width="40%">    
                	<table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer10>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer10 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
                <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer10>0?#class.answer10:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer10>0?@com.whaty.platform.util.Const@div(#class.answer10*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
            <s:if test="#class.prVoteQuestion.item11!=null">
              <tr> 
                <td width="20%">（11）<s:property value="#class.prVoteQuestion.item11" /></td>
                <td width="40%">    
                	<table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer11>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer11 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer11>0?#class.answer11:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer11>0?@com.whaty.platform.util.Const@div(#class.answer11*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>               
             <s:if test="#class.prVoteQuestion.item12!=null">
              <tr> 
                <td width="20%">（12）<s:property value="#class.prVoteQuestion.item12" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer12>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer12 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer12>0?#class.answer12:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer12>0?@com.whaty.platform.util.Const@div(#class.answer12*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>  
             <s:if test="#class.prVoteQuestion.item13!=null">
              <tr> 
                <td width="20%">（13）<s:property value="#class.prVoteQuestion.item13" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer13>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer13 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer13>0?#class.answer13:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer13>0?@com.whaty.platform.util.Const@div(#class.answer13*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if> 
             <s:if test="#class.prVoteQuestion.item14!=null">
              <tr> 
                <td width="20%">（14）<s:property value="#class.prVoteQuestion.item14" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer14>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer14 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer14>0?#class.answer14:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer14>0?@com.whaty.platform.util.Const@div(#class.answer14*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"> </td>
              </tr> 
              </s:if>    
             <s:if test="#class.prVoteQuestion.item15!=null">
              <tr> 
                <td width="20%">（15）<s:property value="#class.prVoteQuestion.item15" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer15>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer15 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer15>0?#class.answer15:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer15>0?@com.whaty.platform.util.Const@div(#class.answer15*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item16!=null">
              <tr> 
                <td width="20%">（16）<s:property value="#class.prVoteQuestion.item16" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer16>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer16 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer16>0?#class.answer16:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer16>0?@com.whaty.platform.util.Const@div(#class.answer16*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if>
             <s:if test="#class.prVoteQuestion.item17!=null">
              <tr> 
                <td width="20%">（17）<s:property value="#class.prVoteQuestion.item17" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer17>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer17 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer17>0?#class.answer17:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer17>0?@com.whaty.platform.util.Const@div(#class.answer17*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item18!=null">
              <tr> 
                <td width="20%">（18）<s:property value="#class.prVoteQuestion.item18" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer18>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer18 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer18>0?#class.answer18:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer18>0?@com.whaty.platform.util.Const@div(#class.answer18*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item19!=null">
              <tr> 
                <td width="20%">（19）<s:property value="#class.prVoteQuestion.item19" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer19>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer19 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer19>0?#class.answer19:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer19>0?@com.whaty.platform.util.Const@div(#class.answer19*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item20!=null">
              <tr> 
                <td width="20%">（20）<s:property value="#class.prVoteQuestion.item20" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer20>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer20 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer20>0?#class.answer20:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer20>0?@com.whaty.platform.util.Const@div(#class.answer20*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item21!=null">
              <tr> 
                <td width="20%">（21）<s:property value="#class.prVoteQuestion.item21" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer21>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer21 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer21>0?#class.answer21:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer21>0?@com.whaty.platform.util.Const@div(#class.answer21*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item22!=null">
              <tr> 
                <td width="20%">（22）<s:property value="#class.prVoteQuestion.item22" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer22>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer22 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer22>0?#class.answer22:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer22>0?@com.whaty.platform.util.Const@div(#class.answer22*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item23!=null">
              <tr> 
                <td width="20%">（23）<s:property value="#class.prVoteQuestion.item23" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer23>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer23 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer23>0?#class.answer23:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer23>0?@com.whaty.platform.util.Const@div(#class.answer23*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item24!=null">
              <tr> 
                <td width="20%">（24）<s:property value="#class.prVoteQuestion.item24" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer24>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer24 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer24>0?#class.answer24:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer24>0?@com.whaty.platform.util.Const@div(#class.answer24*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item25!=null">
              <tr> 
                <td width="20%">（25）<s:property value="#class.prVoteQuestion.item25" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer25>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer25 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer25>0?#class.answer25:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer25>0?@com.whaty.platform.util.Const@div(#class.answer25*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item26!=null">
              <tr> 
                <td width="20%">（26）<s:property value="#class.prVoteQuestion.item26" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer26>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer26 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer26>0?#class.answer26:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer26>0?@com.whaty.platform.util.Const@div(#class.answer26*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item27!=null">
              <tr>
                <td width="20%">（27）<s:property value="#class.prVoteQuestion.item27" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer27>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer27 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer27>0?#class.answer27:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer27>0?@com.whaty.platform.util.Const@div(#class.answer27*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item28!=null">
              <tr> 
                <td width="20%">（28）<s:property value="#class.prVoteQuestion.item28" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer28>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer28 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer28>0?#class.answer28:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer28>0?@com.whaty.platform.util.Const@div(#class.answer28*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item29!=null">
              <tr> 
                <td width="20%">（29）<s:property value="#class.prVoteQuestion.item29" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer29>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer29 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer29>0?#class.answer29:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer29>0?@com.whaty.platform.util.Const@div(#class.answer29*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item30!=null">
              <tr> 
                <td width="20%">（30）<s:property value="#class.prVoteQuestion.item30" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer30>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer30 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer30>0?#class.answer30:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer30>0?@com.whaty.platform.util.Const@div(#class.answer30*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item31!=null">
              <tr> 
                <td width="20%">（31）<s:property value="#class.prVoteQuestion.item31" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer31>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer31 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer31>0?#class.answer31:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer31>0?@com.whaty.platform.util.Const@div(#class.answer31*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item32!=null">
              <tr> 
                <td width="20%">（32）<s:property value="#class.prVoteQuestion.item32" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer32>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer32 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer32>0?#class.answer32:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer32>0?@com.whaty.platform.util.Const@div(#class.answer32*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item33!=null">
              <tr> 
                <td width="20%">（33）<s:property value="#class.prVoteQuestion.item33" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer33>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer33 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer33>0?#class.answer33:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer33>0?@com.whaty.platform.util.Const@div(#class.answer33*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if>
             <s:if test="#class.prVoteQuestion.item34!=null">
              <tr> 
                <td width="20%">（34）<s:property value="#class.prVoteQuestion.item34" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer34>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer34 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer34>0?#class.answer34:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer34>0?@com.whaty.platform.util.Const@div(#class.answer34*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
            <s:if test="#class.prVoteQuestion.item35!=null">
              <tr> 
                <td width="20%">（35）<s:property value="#class.prVoteQuestion.item35" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer35>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer35 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer35>0?#class.answer35:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer35>0?@com.whaty.platform.util.Const@div(#class.answer35*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if>
             <s:if test="#class.prVoteQuestion.item36!=null">
              <tr> 
                <td width="20%">（36）<s:property value="#class.prVoteQuestion.item36" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer36>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer36 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer36>0?#class.answer36:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer36>0?@com.whaty.platform.util.Const@div(#class.answer36*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
           <s:if test="#class.prVoteQuestion.item37!=null">
              <tr> 
                <td width="20%">（37）<s:property value="#class.prVoteQuestion.item37" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer37>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer37 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer37>0?#class.answer37:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer37>0?@com.whaty.platform.util.Const@div(#class.answer37*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if>
             <s:if test="#class.prVoteQuestion.item38!=null">
              <tr> 
                <td width="20%">（38）<s:property value="#class.prVoteQuestion.item38" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer38>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer38 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer38>0?#class.answer38:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer38>0?@com.whaty.platform.util.Const@div(#class.answer38*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if> 
             <s:if test="#class.prVoteQuestion.item39!=null">
              <tr> 
                <td width="20%">（39）<s:property value="#class.prVoteQuestion.item39" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer39>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer39 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer39>0?#class.answer39:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer39>0?@com.whaty.platform.util.Const@div(#class.answer39*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if>  
             <s:if test="#class.prVoteQuestion.item40!=null">
              <tr> 
                <td width="20%">（40）<s:property value="#class.prVoteQuestion.item40" /></td>
                <td width="40%">    
	                <table height="12" border="0" cellpadding="0" cellspacing="0" width="100%" >
	                    <tr> 
	                      <td>
	                      	<s:if test="#class.answer40>0">
	                      		<img src="/entity/manager/images/votetiao.gif" width="<s:property value="#class.answer40 * 300/#total" />" height="10" />
	                      	</s:if>
	                      </td>
	                    </tr>
                  	</table>
                </td>
               <td width="40%" style="padding-left:5px">共计
                	<s:property value="#class.answer40>0?#class.answer40:0" />票&nbsp;&nbsp;&nbsp;&nbsp;所占百分比为：
                	<s:property value="#class.answer40>0?@com.whaty.platform.util.Const@div(#class.answer40*100,#total,@com.whaty.platform.util.Const@percentScale):0" />%
                </td>
              </tr>
              <tr bgcolor="#efefef"> 
                <td height="1" colspan="3"></td>
              </tr> 
            </s:if>        
			</table>
		</td>
	</tr>     
</s:iterator>    