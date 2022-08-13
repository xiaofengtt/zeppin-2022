<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>调查问卷</title>
    
	<link href="/web/vote/css.css" rel="stylesheet" type="text/css">
		<script>
		function toresult(){
		var id = '<s:property value="peVotePaper.id"/>' ;
		window.location="/entity/first/firstPeVotePaper_voteResult.action?bean.id=" + id;
		
		}
		</SCRIPT>
  </head>
  
  <body topmargin="0" leftmargin="0" bgcolor="#EEEEEE">
  <form name="vote" method=post action="/entity/first/firstPeVotePaper_vote.action">
  <table width="780" border="1" align="center" cellpadding="0" cellspacing="0" bordercolordark="#FFD9AD" bordercolorlight="#FFFFFF">
  <tr>
    <td height="108" align="center" background="/entity/manager/images/votebg.gif" class="16title"><font size=6><s:property value="peVotePaper.pictitle"/></font></td>
  </tr>
  <tr>
    <td bgcolor="#FFFFFF"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
        
        <tr>
          <td align="center" valign="bottom" class="14title"><s:property value="peVotePaper.title"/></td>
        </tr>
        <tr>
          <td align="center" valign="bottom" class="12content"><s:date name="peVotePaper.startDate" format="yyyy-MM-dd" />~~<s:date name="peVotePaper.endDate" format="yyyy-MM-dd" />
          &nbsp;&nbsp;<s:if test="pastDue==1">还未开始</s:if><s:elseif test="pastDue==2">已过期</s:elseif> </td>
        </tr>
        <tr>
          <td align="center"><img src="/entity/manager/images/votebian.gif" width="417" height="4"></td>
        </tr>
        <tr>
          <td class="12texthei"><s:property value="peVotePaper.note" escape="false"/>
</td>
        </tr>
        
        <input type="hidden" name="peVotePaper.id" value="<s:property value="peVotePaper.id"/>" />
        <tr>
          <td><br>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="12content">
            	<%@ include file="/web/vote/vote_include.jsp" %>
              <tr> 
                <td bgcolor="#F9F9F9" height=10></td>
              </tr>
              
              <s:if test="peVotePaper.enumConstByFlagCanSuggest.code == 1">
             <tr> 
                <td bgcolor="#F9F9F9">请输入您的意见或建议！</td>
              </tr>
              <tr> 
                <td bgcolor="#F9F9F9"><textarea name="suggest" cols="65" rows="6"></textarea></td>
              </tr>              
              </s:if>
              <s:else>
              <tr> 
                <td bgcolor="#F9F9F9"></td>
              </tr>              
              </s:else>
 
            </table></td>
        </tr>
        
        <tr>
          <td height="40" align="center" valign="bottom" colspan=5> 
          <s:if test="canVote == 1">
          	<s:if test="pastDue == 0">
          	<input name="Submit1" type="submit" id="Submit1" value="提交结果">
          	<input name="Submit2" type="reset" id="Submit2" value="重  填" >
          	</s:if>
          </s:if>
          <s:else> 您已经做过本调查问卷！
          </s:else>

            <input  type="button" id="Submit2" value="查看结果"  onclick="toresult();">
          </td>
          
         </tr>          
    
              </table><br></td>
  </tr>
</table>
     </form>
  </body>
</html>
