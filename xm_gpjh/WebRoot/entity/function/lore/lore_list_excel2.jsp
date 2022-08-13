<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%
			response.setHeader("Content-Disposition",
			"attachment;filename=lore_list_duoxuan_excel.xls");
%>

<%@ page
	import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page
	import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page
	import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page
	import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.Exception.*"%>
<%@ page
	import="com.whaty.platform.database.oracle.standard.entity.recruit.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page
	import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ include file="../pub/priv.jsp"%>

<%@ page import="com.whaty.platform.test.TestManage,com.whaty.util.string.*" %>
<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office"
	xmlns:x="urn:schemas-microsoft-com:office:excel"
	xmlns="http://www.w3.org/TR/REC-html40">

	<head>
		<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
		<meta name=ProgId content=Excel.Sheet>
		<meta name=Generator content="Microsoft Excel 11">
		<link rel=File-List href="DANXUAN.files/filelist.xml">
		<link rel=Edit-Time-Data href="DANXUAN.files/editdata.mso">
		<link rel=OLE-Object-Data href="DANXUAN.files/oledata.mso">
		<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2008-03-06T04:57:41Z</o:LastSaved>
  <o:Version>11.8122</o:Version>
 </o:DocumentProperties>
 <o:OfficeDocumentSettings>
  <o:RemovePersonalInformation/>
 </o:OfficeDocumentSettings>
</xml><![endif]-->
		<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;}
tr
	{mso-height-source:auto;
	mso-ruby-visibility:none;}
col
	{mso-width-source:auto;
	mso-ruby-visibility:none;}
br
	{mso-data-placement:same-cell;}
.style0
	{mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	white-space:nowrap;
	mso-rotate:0;
	mso-background-source:auto;
	mso-pattern:auto;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:常规;
	mso-style-id:0;}
td
	{mso-style-parent:style0;
	padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:bottom;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	color:black;
	font-weight:700;
	text-align:center;}
.xl25
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;}
.xl26
	{mso-style-parent:style0;
	font-size:24.0pt;
	vertical-align:middle;}
.xl27
	{mso-style-parent:style0;
	font-size:24.0pt;
	font-weight:700;
	vertical-align:middle;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;
	display:none;}
-->
</style>
		<!--[if gte mso 9]><xml>
 <x:ExcelWorkbook>
  <x:ExcelWorksheets>
   <x:ExcelWorksheet>
    <x:Name>Sheet1</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:Print>
      <x:ValidPrinterInfo/>
      <x:PaperSizeIndex>9</x:PaperSizeIndex>
      <x:HorizontalResolution>180</x:HorizontalResolution>
      <x:VerticalResolution>180</x:VerticalResolution>
     </x:Print>
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>1</x:ActiveRow>
       <x:ActiveCol>4</x:ActiveCol>
      </x:Pane>
     </x:Panes>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet2</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:CodeName>Sheet2</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:CodeName>Sheet3</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>4530</x:WindowHeight>
  <x:WindowWidth>8505</x:WindowWidth>
  <x:WindowTopX>480</x:WindowTopX>
  <x:WindowTopY>120</x:WindowTopY>
  <x:AcceptLabelsInFormulas/>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
		<!--[if gte mso 9]><xml>
 <o:shapedefaults v:ext="edit" spidmax="2049"/>
</xml><![endif]-->
	</head>

	<body link=blue vlink=purple>

		<table x:str border=0 cellpadding=0 cellspacing=0 width=1844
			style='border-collapse:
 collapse;table-layout:fixed;width:1380pt'>
			<col class=xl25 width=111 span=4
				style='mso-width-source:userset;mso-width-alt:
 3552;width:83pt'>
			<col class=xl25 width=290
				style='mso-width-source:userset;mso-width-alt:9280;
 width:218pt'>
			<col class=xl25 width=111 span=10
				style='mso-width-source:userset;mso-width-alt:
 3552;width:83pt'>
			<tr class=xl26 height=42 style='height:31.5pt'>
				<td colspan=256 height=42 class=xl27 width=19196
					style='height:31.5pt;
  width:14394pt'>
					<span style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</span>多选题
				</td>
			</tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>难度</td>
  <td class=xl24>建议题目分值</td>
  <td class=xl24>建议作答时间</td>
  <td class=xl24>认知分类</td>
  <td class=xl24>题目用途</td>
  <td class=xl24>题目名称</td>
  <td class=xl24>题目内容</td>
  <td class=xl24>题目提示(对学)</td>
  <td class=xl24>题目要求(对老师)</td>
  <td class=xl24>选项Ａ</td>
  <td class=xl24>选项B</td>
  <td class=xl24>选项C</td>
  <td class=xl24>选项D</td>
  <td class=xl24>选项E</td>
  <td class=xl24>正确答案</td>
 </tr>
<%!String fixnull(String str) {
		if (str == null || str.equals("") || str.trim().equalsIgnoreCase("null"))
			str = "";
		return str;
	}

	int fixnull0(String str) {
		if (str == null || str.trim().equals("") || str.trim().equalsIgnoreCase("null"))
			str = "0";
		return Integer.valueOf(str).intValue();
	}
	%>

			<%
 			try{
 			
 					String id = fixnull(request.getParameter("id"));
 					String type = fixnull(request.getParameter("type"));
 					InteractionFactory interactionFactory = InteractionFactory.getInstance();
					InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
					TestManage testManage = interactionManage.creatTestManage();
					List storeQuestionList = testManage.getStoreQuestions(null, null, null, null, null, null, null, null, id, null, null, null, null, null, null, type);

					for(int i = 0 ; i < storeQuestionList.size() ; i++) {
						StoreQuestion question = (StoreQuestion)storeQuestionList.get(i);
						String qid = question.getId();
						String diff = question.getDiff();
						String cognizetype = question.getCognizeType();
						String referencetime = question.getReferenceTime();
						String referencescore = question.getReferenceScore();
						String title = question.getTitle();
						String purpose = question.getPurpose();
						String qtype = question.getType();
						String studentnote = question.getStudentNote();
						String teachernote = question.getTeacherNote();
						String questionCoreXml = question.getQuestionCore();
						List coreList = XMLParserUtil.parserSingleMulti(questionCoreXml);
						String bodyString = (String)coreList.get(0);
						String answer = (String)coreList.get(coreList.size()-1);
						EntityConfig entityConfig = (EntityConfig)application.getAttribute("entityConfig");
						String tmp = "";
 						if(cognizetype.indexOf("LIAOJIE")>=0)
							tmp="了解";
						else if(cognizetype.indexOf("LIJIE")>=0)
							tmp="理解";
						else if(cognizetype.indexOf("YINGYONG")>=0)
							tmp="应用";
						else if(cognizetype.indexOf("FENXI")>=0)
							tmp="分析";
						else if(cognizetype.indexOf("ZONGHE")>=0)
							tmp="综合";
						else if(cognizetype.indexOf("PINGJIAN")>=0)
							tmp="评鉴";
						else
							tmp=cognizetype;
						cognizetype=tmp;
						tmp="";
						if(purpose.indexOf("KAOSHI")>=0)
						 	tmp+="在线自测|";
						if(purpose.indexOf("EXPERIMENT")>=0)
						 	tmp+="实验|";
						if(purpose.indexOf("ZUOYE")>=0)
						 	tmp+="在线作业|";
						if(purpose.indexOf("EXAM")>=0)
						 	tmp+="在线考试|";
						purpose=tmp.substring(0,tmp.length()>0?(tmp.length()-1):0);
						

 %><%--
			<![if supportMisalignedColumns]>
			--%><tr height=20>
				<td width=111 style='width:83pt'><%=fixnull(diff)%></td>
				<td width=111 style='width:83pt'><%=fixnull(referencescore)%></td>
				<td width=111 style='width:83pt'><%=fixnull(referencetime)%></td>
				<td width=111 style='width:83pt'><%=fixnull(cognizetype)%></td>
				<td width=290 style='width:218pt'><%=fixnull(purpose)%></td>
				<td width=111 style='width:83pt'><%=fixnull(title)%></td>
				<td width=111 style='width:83pt'><%=fixnull(bodyString)%></td>
				<td width=111 style='width:83pt'><%=fixnull(studentnote)%></td>
				<td width=111 style='width:83pt'><%=fixnull(teachernote)%></td>
				<%
					String answer1 = "";
					int count = 0;
					for(int j=1; j<coreList.size()-2; j=j+2) {
						count++;
						String index = (String)coreList.get(j);
						String content = (String)coreList.get(j+1);
						//out.print(index+"、"+content);
						WhatyStrManage strManage = new WhatyStrManage();
						strManage.setString(content);
						if(answer.indexOf(index)>=0){
							answer1 += strManage.htmlEncode();
						}
				%>
				<td width=111 style='width:83pt'><%=strManage.htmlEncode()%></td>

				<%
					}
					for(; count < 5; count++) {
					%>
						<td width=111 style='width:83pt'></td>
					<%}
				%>
<%--				<td width=111 style='width:83pt'><%=answer1%></td>				--%>
				<td width=111 style='width:83pt'><%=answer%></td>				
			</tr>

<%--<![endif]>--%>
			<%
				}
 			}catch(Exception e) {
 				out.println(e.getMessage());
 				return;
 			}
 %>
		</table>

	</body>

</html>

