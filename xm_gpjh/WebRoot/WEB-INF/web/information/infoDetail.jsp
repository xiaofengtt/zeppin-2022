<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page import="java.util.*,java.text.*;" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>学生报名信息查看</title>
    <link href="/web/css/css.css" rel="stylesheet" type="text/css" />
	<link href="/web/registration/registration.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="/js/extjs/css/ext-all.css" />
		<script type="text/javascript" src="/js/extjs/pub/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-all.js"></script>
		<script type="text/javascript" src="/js/extjs/pub/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/FCKeditor/fckeditor.js"></script> 
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body >
  <form name="print" method="POST" action="/entity/recruit/addPeRecStudent_editStudent.action" onsubmit='return userAddGuarding();'>
      <table width="1002" height="100%" border="0" align="center" cellpadding="0" cellspacing="0" valign="top">
  <tr>
  	  	<input type="hidden" name="student.id" value="<s:property value="student.getId()"/>"/> 
    <td width="1002"  height="100%" valign="top" align="center">
	<table width="980" border="0" cellspacing="0" cellpadding="0" style="margin:10px 0px 3px 0px;">
      <tr>
        <td><table width="980" border="0" cellpadding="0" cellspacing="0" background="/web/news/images/04.jpg">
          <tr>
            <td width="104"><img src="/web/registration/images/baomin1_03.jpg" width="104" height="29" /></td>
            <td width="798" align="center">
			<table width="240" border="0" cellpadding="0" cellspacing="0" style="margin-bottom:5px;">
              <tr>
                <td bgcolor="#FFFBE5"  class="redtitle">学  生  基  本  信  息</td>
              </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td valign="top" align="center" style="padding-bottom:5px;">
		<table width="96%" height="100%" border="0" cellspacing="0" cellpadding="0" style="margin:5px 0px; border:1px solid #E1E1E1;">
          <tr>
            <td valign="top">
			<table id="baseInfor1" width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px solid #E1E1E1; margin-bottom:5px;">
              
              <tr>
                <td height="198" align="center"> 
				<table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="redtitle1">基本信息</td>
                  </tr>
                  <tr>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 姓名：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.name"/></span></td>
                        <td width="34%" id="nameInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 民族：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.getFolk()"/></span></td>
                        <td width="34%"  id="folkInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 出生地：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.province"/></span><span class="grayfont1">省</span>
		<span class="grayfont1"><s:property value="student.city"/></span><span class="grayfont1">市</span></td>
                        <td width="34%"  id="provinceInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 政治面貌：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.zzmm"/></span></td>
                        <td width="34%" id="zzmmInfor">&nbsp;</td>
                      </tr>
  <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 证件类型：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.cardType"/></span></td>
                        <td width="34%" id="cardTypeInfor"  >&nbsp;</td>
                      </tr>
					  <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 证件号码：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.cardNo"/></span></td>
                        <td width="34%" id="cardNoInfor">&nbsp;</td>
                      </tr>
                    </table></td>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 性别：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.gender"/></span>
                        </td>
                        <td width="34%"  id="genderInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 户口：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.register"/></span></td>
                        <td width="34%" id="registerInfor" >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 出生日期：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:date name="student.birthday" format="yyyy-MM-dd"/></span></td>
                        <td width="34%" id="birthdayInfo">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 文化程度：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.xueli"/></span></td>
                        <td width="34%"  id="xueliInfor" >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 婚姻状况：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.marriage"/></span></td>
                        <td width="34%"  id="marriageInfor">&nbsp;</td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
				<!-------------- ----------------->
				
				<!--------- ------------------>				</td>
              </tr>
            </table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px solid #E1E1E1; margin-bottom:5px;" id="graduateInfor">
  <tr>
    <td align="center" >
	<table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="redtitle1">毕业信息</td>
                  </tr>
                  <tr>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 毕业院校：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.graduateSchool"/></span></td>
                        <td width="34%" id="graduateSchoolInfor" >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 就读专业：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.graduateMajor"/></span></td>
                        <td width="34%" id="graduateMajorInfor" >&nbsp;</td>
                      </tr>
                    </table></td>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 毕业时间：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="graduateYear"/></span>
                        <span class="grayfont1">年</span>
						<span class="grayfont1"><s:property value="graduateMonth"/></span> <span class="grayfont1">月</span>
                           <span  >&nbsp;</span></td>
                        <td width="34%" id="graduateDateInfor" >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 毕业证书编号：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.graduateCode"/></span></td>
                        <td width="34%" id="graduateCode" >&nbsp;</td>
                      </tr>
                    </table></td>
                  </tr>
                </table>	</td>
  </tr>
  <tr>
    <td align="center">&nbsp;</td>
  </tr>
</table>
   <!------------------------------------>
<table width="100%" border="0" id="workInfor" cellspacing="0" cellpadding="0" style="border-bottom:1px solid #E1E1E1; margin-bottom:5px;">
  
  <tr>
    <td align="center">
	<table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="redtitle1">职业信息 </td>
                  </tr>
                  <tr>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 职业：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.occupation"/></span></td>
                        <td width="34%"  id="occupationInfor">&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24">工作单位：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.workplace"/></span></td>
                        <td width="34%" id="workspaceInfor" >&nbsp;</td>
                      </tr>
                     <tr>
                        <td width="23%" class="grayfont" height="24">单位电话：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.workplacePhone"/></span></td>
                        <td width="34%" id="workspacePhoneInfor" >&nbsp;</td>
                      </tr>
                    </table></td>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td >&nbsp;</td>
                      </tr>
                      
                      <tr>
                        <td width="23%" class="grayfont" height="24">何时参加工作：</td>

                        <td width="43%" align="left"><span class="grayfont1"><s:property value="workYear"/></span> <span class="grayfont1">年</span>
						<span class="grayfont1"><s:property value="workMonth"/></span> <span class="grayfont1">月</span></td>
                        <td width="34%"  id="workDateInfor">&nbsp;</td>
                      </tr>
					  <tr>
                        <td width="23%" class="grayfont" height="24">单位邮编：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.workplaceZip"/></span></td>
                        <td width="34%" id="workspaceZipInfor" >&nbsp;</td>
                      </tr>
                    </table></td>
                  </tr>
                </table>	</td>
  </tr>
</table>
<table width="100%" border="0" id="contactInfor" cellspacing="0" cellpadding="0" style="border-bottom:1px solid #E1E1E1; margin-bottom:5px;">
  
  <tr>
    <td align="center">
	<table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="redtitle1">联系方式 </td>
                  </tr>
                  <tr>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 联系地址：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.address"/></span></td>
                        <td width="34%" id="addressInfor" >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 手机：</td>
                        <td width="43%" align="left"><span class="grayfont1"><s:property value="student.mobilephone"/></span></td>
                        <td width="34%" id="mobilephoneInfor" >&nbsp;</td>
                      </tr>
                     <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> Email：</td>
                        <td width="42%" align="left"><span class="grayfont1"><s:property value="student.email"/></span></td>
                        <td width="35%" id="emailInfor">&nbsp;</td>
                      </tr>
                    </table></td>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                       <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 邮政编码：</td>
                        <td width="42%" align="left"><span class="grayfont1"><s:property value="student.zip"/></span></td>
                        <td width="35%" id="zipInfor" >&nbsp;</td>
                      </tr>
                      
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 联系电话：</td>

                        <td width="42%" align="left">
						<span class="grayfont1"><s:property value="student.phone"/></span></td>
                        <td width="35%" id="phoneInfor"  >&nbsp;</td>
                      </tr>
					  <tr>
                        <td width="23%" class="grayfont" height="24">&nbsp;</td>
                        <td width="42%" align="left">&nbsp;</td>
                        <td width="35%"  >&nbsp;</td>
                      </tr>
                    </table></td>
                  </tr>
                </table>	</td>
  </tr>
</table>
<table width="100%" id="applyInfor" border="0" cellspacing="0" cellpadding="0" style=" margin-bottom:5px;">
  
  <tr>
    <td align="center">
	<table width="96%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td colspan="2" class="redtitle1">学习申请 </td>
                  </tr>
                   	  	<input type="hidden" id="canEdit" value="<s:property value="canEdit"/>"/> 
                  <tr>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 学习中心：</td>


                        <td width="70%" align="left">
                        <span class="grayfont1"><s:property value="student.getPrRecPlanMajorSite().getPeSite().getName()"/></span>        </td>
                        <td width="7%"  >&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 所报层次：</td>
                        <td width="70%" align="left">
                          <span class="grayfont1"><s:property value="student.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeEdutype().getName()"/></span></td>
                       <td width="7%"  >&nbsp;</td>
                      </tr>
                     <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 所报专业：</td>
                        <td width="70%" align="left">
                          <span class="grayfont1"><s:property value="student.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeMajor().getName()"/></span></td>
                        <td width="7%"  >&nbsp;</td>
                      </tr>

                      <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 是否为考试生：</td>
                        <td width="70%" align="left">
                         <s:if test="noexam == 1">
             					<span class="grayfont1">免试生</span>                 
                         </s:if>
                         <s:else>
             					<span class="grayfont1">考试生</span>
             			</s:else>
            			 </td>
                       <td width="7%"  >&nbsp;</td>
                      </tr>
                       <tr>
                        <td width="23%" class="grayfont" height="24"><font class="sign">*</font> 教师资格：</td>
                        <td width="70%" align="left"> 
                          
                         <s:if test="teacher == 0">                          
             					<span class="grayfont1">无教师资格</span>
             			</s:if>	
             			<s:elseif test="teacher == 1">
             					<span class="grayfont1">有教师资格</span>               			
             			</s:elseif>
             			<s:else>
             					<span class="grayfont1">其他专业</span>           			
             			</s:else>	             					
             			</td>
                       <td width="7%"  >&nbsp;</td>
                      </tr>                           
                    </table></td>
                    <td width="50%" align="center"  valign="top">
					<table width="98%" border="0" cellspacing="0" cellpadding="0" style="margin-top:8px;">
                       <tr>
                        <td width="50%" align="left" height="24" id="siteInfor">&nbsp;</td>
                        <td width="25%" align="left">&nbsp;</td>
                        <td width="25%"  >&nbsp;</td>
                      </tr>
                      
                       <tr>
                        <td width="50%" align="left" height="24"  id="edutypeInfor">&nbsp;</td>
                        <td width="25%" align="left">&nbsp;</td>
                        <td width="25%"  >&nbsp;</td>
                      </tr>
                       <tr>
                        <td width="50%" align="left" height="24" id="majorInfor">&nbsp;</td>
                        <td width="25%" align="left">&nbsp;</td>
                        <td width="25%"  >&nbsp;</td>
                      </tr>
                       <tr>
                        <td width="50%" align="left" height="24" id="recProvinceInfor">&nbsp;</td>
                        <td width="25%" align="left">&nbsp;</td>
                        <td width="25%"  >&nbsp;</td>
                      </tr>					  
                    </table></td>
                  </tr>
                </table>	</td>
  </tr>
</table></td>
          </tr>
        </table>

    </table></td>
  </tr>

</table>

      <map name="Map" id="Map">
  <area shape="rect" coords="272,94,391,127" href="#" />
  <area shape="rect" coords="267,16,386,49" href="#" />
<area shape="rect" coords="73,94,194,128" href="#" />
<area shape="rect" coords="72,15,191,48" href="#" /><area shape="rect" coords="176,55,272,91" href="#" /></map>
 </form>
  </body>
</html>
