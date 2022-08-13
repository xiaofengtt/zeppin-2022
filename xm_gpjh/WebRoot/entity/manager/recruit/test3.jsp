<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 9">
<link rel=File-List href="./Book1.files/filelist.xml">
<link rel=Edit-Time-Data href="./Book1.files/editdata.mso">
<link rel=OLE-Object-Data href="./Book1.files/oledata.mso">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生详细信息</title>

<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>whaty</o:Author>
  <o:LastAuthor>whaty</o:LastAuthor>
  <o:Created>2006-05-19T02:21:51Z</o:Created>
  <o:LastSaved>2006-05-19T02:38:32Z</o:LastSaved>
  <o:Company>a</o:Company>
  <o:Version>9.2812</o:Version>
 </o:DocumentProperties>
 <o:OfficeDocumentSettings>
  <o:DownloadComponents/>
  <o:LocationOfComponents HRef="msowc.cab"/>
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
.font0
	{color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font5
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font8
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
.font10
	{color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
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
	font-size:9.0pt;}
.xl25
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl26
	{mso-style-parent:style0;
	font-size:9.0pt;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;}
.xl27
	{mso-style-parent:style0;
	font-size:9.0pt;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	white-space:normal;}
.xl28
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl29
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl30
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl31
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl32
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl33
	{mso-style-parent:style0;
	font-size:9.0pt;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;}
.xl34
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;}
.xl35
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	background:#CCFFFF;
	mso-pattern:auto none;
	white-space:normal;}
.xl36
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFFF;
	mso-pattern:auto none;
	white-space:normal;}
.xl37
	{mso-style-parent:style0;
	font-size:9.0pt;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFFF;
	mso-pattern:auto none;}
.xl38
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFFF;
	mso-pattern:auto none;
	white-space:normal;}
.xl39
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFFF;
	mso-pattern:auto none;
	white-space:normal;}
.xl40
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFCC;
	mso-pattern:auto none;
	white-space:normal;}
.xl41
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	background:#CCFFCC;
	mso-pattern:auto none;
	white-space:normal;}
.xl42
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;

	background:#CCFFCC;
	mso-pattern:auto none;
	white-space:normal;}
.xl43
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;}
.xl44
	{mso-style-parent:style0;
	mso-number-format:"\@";
	vertical-align:middle;}
.xl45
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl46
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl47
	{mso-style-parent:style0;
	font-size:9.0pt;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl48
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl49
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl50
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl51
	{mso-style-parent:style0;
	font-size:9.0pt;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl52
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl53
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl54
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl55
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl56
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:none;
	border-left:.5pt solid black;
	white-space:normal;}
.xl57
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:none;
	border-left:none;
	white-space:normal;}
.xl58
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:none;
	border-left:.5pt solid black;
	white-space:normal;}
.xl59
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:none;

	border-left:none;
	white-space:normal;}
.xl60
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	white-space:normal;}
.xl61
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	white-space:normal;}
.xl62
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl63
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl64
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl65
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFFF;
	mso-pattern:auto none;
	white-space:normal;}
.xl66
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	background:#CCFFFF;
	mso-pattern:auto none;
	white-space:normal;}
.xl67
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFFF;
	mso-pattern:auto none;
	white-space:normal;}
.xl68
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFCC;
	mso-pattern:auto none;
	white-space:normal;}
.xl69
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	background:#CCFFCC;
	mso-pattern:auto none;
	white-space:normal;}
.xl70
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFCC;
	mso-pattern:auto none;
	white-space:normal;}
.xl71
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFCC;
	mso-pattern:auto none;
	white-space:normal;}
.xl72
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	background:#CCFFCC;
	mso-pattern:auto none;
	white-space:normal;}
.xl73
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:#CCFFCC;
	mso-pattern:auto none;
	white-space:normal;}
.xl74
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl75
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl76
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl77
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:none;
	border-left:.5pt solid black;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl78
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:none;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl79
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:left;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:none;
	border-left:.5pt solid black;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl80
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:left;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:none;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl81
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:none;
	border-left:.5pt solid black;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl82
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:left;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:none;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl83
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:right;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid black;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl84
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:right;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl85
	{mso-style-parent:style0;
	font-size:8.0pt;
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:none;
	border-left:none;
	white-space:normal;}
.xl86
	{mso-style-parent:style0;
	font-size:8.0pt;
	text-align:left;
	vertical-align:middle;
	white-space:normal;}
.xl87
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid black;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl88
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl89
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid black;
	border-right:.5pt solid black;
	border-bottom:.5pt solid windowtext;
	border-left:none;

	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl90
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid windowtext;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl91
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl92
	{mso-style-parent:style0;
	font-size:9.0pt;
	text-align:center;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl93
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl94
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl95
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid black;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl96
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl97
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid black;
	border-left:.5pt solid windowtext;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl98
	{mso-style-parent:style0;
	font-size:9.0pt;
	mso-number-format:"\@";
	text-align:left;
	vertical-align:middle;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid black;
	border-bottom:.5pt solid black;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl99
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid black;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl100
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl101
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-weight:700;
	text-align:center;
	vertical-align:middle;
	border-top:none;
	border-right:.5pt solid black;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl102
	{mso-style-parent:style0;
	font-size:9.0pt;
	border:.5pt solid black;}
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

</head>
  
  <body link=blue vlink=purple>
<table x:str border=0 cellpadding=0 cellspacing=0 width=638 style='border-collapse:
 collapse;table-layout:fixed;width:480pt'>
 <col width=50 style='mso-width-source:userset;mso-width-alt:3360;width:50pt'>
 <col width=90 style='mso-width-source:userset;mso-width-alt:2880;width:100pt'>
 <col width=106 style='mso-width-source:userset;mso-width-alt:3392;width:80pt'>
 <col width=97 style='mso-width-source:userset;mso-width-alt:3104;width:73pt'>
 <col width=50 style='mso-width-source:userset;mso-width-alt:2720;width:50pt'>
 <col width=150 style='mso-width-source:userset;mso-width-alt:2656;width:80pt'>
 <col width=72 style='width:54pt'>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl25 width=566 style='height:14.25pt;width:50pt'>招生批次：</td>
  <td class=xl26 width=566 style='width:100pt'>
  <s:property value="student.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeRecruitplan().getName()"/></td>
  <td class=xl27 width=566 style='width:80pt'>准考证号:</td>
  <td class=xl27 width=566 style='width:73pt' align='left'><s:property value="student.getExamCardNum()"/></td>
  <td class=xl25 width=566 style='width:50pt'>学习中心:</td>
  <td class=xl28 width=566 style='width:150pt'>
	<s:property value="student.getPrRecPlanMajorSite().getPeSite().getName()"/></td>
  <td width=72 style='width:120pt'>&nbsp;</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=6 height=19 class=xl53 width=566 style='border-right:.5pt solid black;
  height:14.25pt;width:426pt'>自然信息部分</td>
  <td></td>
 </tr>
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td height=30 class=xl29 width=566 style='height:22.5pt;width:79pt'>姓名</td>
  <td class=xl34 width=566 style='width:68pt'><s:property value="student.getName()"/></td>
  <td class=xl31 width=566 style='width:80pt'>性别</td>
  <td class=xl30 width=566 style='width:68pt'><s:property value="student.getGender()"/></td>
  <td colspan=2 rowspan=6 class=xl56 style='border-right:.5pt solid black;
  border-bottom:.5pt solid black;width:126pt'>
  <img height="100" width="100" src='<s:property value="student.getPhotoLink()"/>' border=0 onload='javascript:DrawImage(this);'></td>
  <td></td>
 </tr>
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td height=30 class=xl29 style='height:22.5pt;width:79pt'>出生日期</td>
  <td height=30 class=xl29 style='height:22.5pt;width:79pt'><s:date name="student.birthday" format="yyyy-MM-dd" /></td>
  <td height=30 class=xl29 style='height:22.5pt;width:79pt'>政治面貌</td>
  <td height=30 class=xl29 style='height:22.5pt;width:79pt'><s:property value="student.getZzmm()"/></td>
 </tr>
 
 <tr height=30 style='height:22.5pt'>
  <td height=30 class=xl29 width=566 style='height:22.5pt;width:79pt'>户口所在地</td>
  <td class=xl30 width=566 style='width:68pt'><s:property value="student.getRegister()"/></td>
  <td class=xl31 width=566 style='width:80pt'>文化程度</td>
  <td class=xl30 width=566 style='width:73pt'><s:property value="student.getXueli()"/>
  <td></td>
 </tr>
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td height=30 class=xl29 width=566 style='height:22.5pt;width:79pt'>民族</td>
  <td class=xl30 width=566 style='width:68pt'><s:property value="student.getFolk()"/></td>
  <td class=xl31 width=566 style='width:80pt'>毕业学校</td>
  <td class=xl33><s:property value="student.getGraduateSchool()"/></td>
  <td></td>
 </tr>
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td height=30 class=xl29 width=566 style='height:22.5pt;width:79pt'>证件类型</td>
  <td class=xl30 width=566 style='width:68pt'><s:property value="student.getCardType()"/></td>
  <td class=xl31 width=566 style='width:80pt'>毕业时间</td>
  <td class=xl33><s:property value="student.getGraduateDate()"/></td>
  <td></td>
 </tr>
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td height=30 class=xl34 style='height:22.5pt'>证件号码</td>
  <td class=xl29 ><s:property value="student.getCardNo()"/></td>
  <td colspan="2" class=xl29 style='width:80pt'>&nbsp;</td>
  <td></td>
 </tr>
 
 <tr height=30 style='height:22.5pt'>
  <td height=30 class=xl34 width=566 style='height:22.5pt;width:79pt'>职业</td>
  <td class=xl29 width=566 style='border-right:.5pt solid black;
  border-left:none;width:221pt'><s:property value="student.getOccupation()"/></td>
  <td class=xl31 width=566 style='width:80pt'>工作单位
  </td>
  <td class=xl30 width=566 style='width:73pt'>
  <s:property value="student.getWorkplace()"/></td>
  
  <td class=xl34 width=566 style='border-top:none;width:64pt'>移动电话</td>
  <td class=xl29 style='border-top:none'><s:property value="student.getMobilephone()"/></td>
  <td></td>
 </tr>
 <tr height=30 style='height:22.5pt'>
  <td height=30 class=xl34 width=566 style='height:22.5pt;width:79pt'>录取通知书及在学期间联系地址</td>
  <td colspan=3 class=xl29 width=566 style='border-right:.5pt solid black;
  border-left:none;width:221pt'><s:property value="student.getAddress()"/></td>
  <td class=xl34 width=566 style='height:22.5pt'>邮政编码</td>
  <td class=xl29 width=566 style='width:62pt'><s:property value="student.getZip()"/></td>
  <td></td>
 </tr>
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td colspan=6 height=30 class=xl53 width=566 style='border-right:.5pt solid black;
  height:22.5pt;width:426pt'>报考信息部分</td>
  <td></td>
 </tr>
 <tr height=30 style='height:22.5pt'>
  <td height=30 class=xl29 width=566 style='height:22.5pt;width:79pt'>报考层次</td>
  <td class=xl30 width=566 style='width:68pt'>
	<s:property value="student.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeEdutype().getName()"/></td>
  <td class=xl31 width=566 style='width:80pt'>入学形式</td>
  <td colspan="3" class=xl29 style='width:73pt'><s:property value="examStudent"/></td>
  <td></td>
 </tr>
 
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td height=30 class=xl29 width=566 style='height:22.5pt;width:79pt'>学习中心</td>
  <td class=xl30 width=566 style='width:68pt'>
  <s:property value="student.getPrRecPlanMajorSite().getPeSite().getName()"/></td>
  
  <td class=xl31 width=566 style='width:64pt'>&nbsp;</td>
  <td colspan="3" class=xl29 style='width:62pt'>&nbsp;</td>
  </tr>
 <tr height=60 style='mso-height-source:userset;height:45.0pt'>
  <td height=60 class=xl29 width=566 style='height:45.0pt;width:79pt'>报考专业</td>
  <td class=xl30 width=566 style='width:68pt'>
	<s:property value="student.getPrRecPlanMajorSite().getPrRecPlanMajorEdutype().getPeMajor().getName()"/></td>

  <td class=xl31 width=566 style='width:64pt'>是否具有教师资格<span
  style="mso-spacerun: yes"></span></td>
  <td colspan="3" class=xl29 style='width:62pt'><s:property value="student.getEnumConstByFlagTeacher().getName()"/></td>
  </tr>
 <tr height=45 style='mso-height-source:userset;height:33.75pt'>
  <td colspan=6 height=45 class=xl74 width=566 style='border-right:.5pt solid black;
  height:33.75pt;width:426pt'>学院对考生提供的以上证书及成绩任何时间审核不通过者取消一切资格,所有费用不予退还</td>
  <td></td>
 </tr>
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td colspan=2 height=30 class=xl77 width=566 style='border-right:.5pt solid black;
  height:22.5pt;width:147pt'>签字前请认真核查报考内容</td>
  <td colspan=2 class=xl79 width=566 style='border-right:.5pt solid black;
  border-left:none;width:153pt'>学习中心（公章）</td>
  <td colspan=2 class=xl79 width=566 style='border-right:.5pt solid black;
  border-left:none;width:126pt'>网络教育学院（公章）</td>
  <td></td>
 </tr>
 <tr height=40 style='mso-height-source:userset;height:30.0pt'>
  <td colspan=2 height=40 class=xl81 width=566 style='border-right:.5pt solid black;
  height:30.0pt;width:147pt'>我保证所提交的以上信息真实准确,并愿意承担由于虚假信息所带来的一切后果</td>
  <td colspan=2 class=xl81 width=566 style='border-right:.5pt solid black;
  border-left:none;width:153pt'>　</td>
  <td colspan=2 class=xl81 width=566 style='border-right:.5pt solid black;
  border-left:none;width:126pt'>　</td>
  <td></td>
 </tr>
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td colspan=2 height=30 class=xl81 width=566 style='border-right:.5pt solid black;
  height:22.5pt;width:147pt'>考生签名: <font class=font8><span style="mso-spacerun:
  yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></font><font
  class=font5><span style="mso-spacerun:
  yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></font></td>
  <td colspan=2 class=xl81 width=566 style='border-right:.5pt solid black;
  border-left:none;width:153pt'>审核人签字:</td>
  <td colspan=2 class=xl81 width=566 style='border-right:.5pt solid black;
  border-left:none;width:126pt'>审核人签字:</td>
  <td></td>
 </tr>
 <tr height=35 style='mso-height-source:userset;height:26.25pt'>
  <td colspan=2 height=35 class=xl83 width=566 style='border-right:.5pt solid black;
  height:26.25pt;width:147pt'><span style="mso-spacerun: yes">&nbsp;</span><font
  class=font8><span style="mso-spacerun:
  yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font
  class=font5>年</font><font class=font8><span style="mso-spacerun:
  yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font class=font5>月</font><font
  class=font8><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span></font><font class=font5>日</font></td>
  <td colspan=2 class=xl83 width=566 style='border-right:.5pt solid black;
  border-left:none;width:153pt'><span style="mso-spacerun: yes">&nbsp;</span><font
  class=font8><span style="mso-spacerun:
  yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font
  class=font5>年</font><font class=font8><span style="mso-spacerun:
  yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font class=font5>月</font><font
  class=font8><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span></font><font class=font5>日</font></td>
  <td colspan=2 class=xl83 width=566 style='border-right:.5pt solid black;
  border-left:none;width:126pt'><span style="mso-spacerun: yes">&nbsp;</span><font
  class=font8><span style="mso-spacerun:
  yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font
  class=font5>年</font><font class=font8><span style="mso-spacerun:
  yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font class=font5>月</font><font
  class=font8><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span></font><font class=font5>日</font></td>
  <td></td>
 </tr>
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td colspan=6 height=30 class=xl85 width=566 style='height:22.5pt;width:426pt'>注：带*号项目为必填项。其中免试信息部分由免试入学学生填写,自然信息中灰色部分学生在学期间都可修改,其它不能由修改，</td>
  <td></td>
 </tr>
 <tr height=45 style='mso-height-source:userset;height:33.75pt'>
  <td colspan=6 height=45 class=xl86 width=566 style='height:33.75pt;
  width:426pt'><span style="mso-spacerun: yes">&nbsp;&nbsp;
  </span></td>
  <td></td>
 </tr>
 <tr height=30 style='mso-height-source:userset;height:22.5pt'>
  <td height=30 style='height:22.5pt'></td>
  <td class=xl43></td>
  <td class=xl24></td>
  <td class=xl44 colspan=4 style='mso-ignore:colspan'>填表日期*：<font class=font10><span
  style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font
  class=font0>年</font><font class=font10><span style="mso-spacerun:
  yes">&nbsp;&nbsp;&nbsp;&nbsp; </span></font><font class=font0>月</font><font
  class=font10><span style="mso-spacerun: yes">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  </span></font><font class=font0>日</font></td>
 </tr>
 <tr height=60 style='mso-height-source:userset;height:45.0pt'>
  <td height=60 style='height:45.0pt'></td>
  <td class=xl43></td>
  <td class=xl24></td>
  <td colspan=3 class=xl44 style='mso-ignore:colspan'></td>
  <td></td>
 </tr>
 <tr height=40 style='mso-height-source:userset;height:30.0pt'>
  <td height=40 style='height:30.0pt'></td>
  <td class=xl43></td>
  <td class=xl24></td>
  <td colspan=3 class=xl44 style='mso-ignore:colspan'></td>
  <td></td>
 </tr>
 
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=566 style='width:79pt'></td>
  <td width=566 style='width:68pt'></td>
  <td width=566 style='width:80pt'></td>
  <td width=566 style='width:73pt'></td>
  <td width=566 style='width:64pt'></td>
  <td width=566 style='width:62pt'></td>
  <td width=72 style='width:54pt'></td>
 </tr>
 <![endif]>
</table>

  </body>
</html>
