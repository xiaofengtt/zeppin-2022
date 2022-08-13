<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<s:if test="scormStuList != null and scormStuList.size > 0">
<head>
<link type="text/css" href="/entity/bzz-students/css/css2.css" rel="stylesheet" />
<script language="javascript" src="<%=request.getContextPath()%>/entity/bzz-students/js/pro.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学习记录</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/training/student/course/courseware/scorm12/js/ext-2.2/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/training/student/course/courseware/scorm12/js/ext-2.2/examples/tree/column-tree.css" />
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/examples/tree/ColumnNodeUI.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/examples/tree/column-tree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/json.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/scoDataModel.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/training/student/course/courseware/scorm12/js/ext-2.2/scoManager.js"></script>
<style type="text/css">
<!--
body {
	margin: 0px;
	padding: 0px;
	text-align:center;
	background-color:#E0E0E0;
}
-->
</style>
<style type="text/css">
<!--
.w1 {
	font-family: "宋体";
	font-size: 12px;
	line-height: 24px;
	color: #333333;
	text-decoration: none;
}
.w2 {
	font-family: "宋体";
	font-size: 14px;
	line-height: 24px;
	font-weight: bold;
	color: #265A8E;
	text-decoration: none;
}
-->
</style>
</head>
<body leftmargin="0" topmargin="0" style="background-color:#E0E0E0;">
      <table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61" >
         <tr> 
          <td>
			  <div  style="width:917px; margin:0px auto" class="studytop">
			    <div id="topimg" style="float:right"><img src="/web/bzz_index/images/top_02.jpg" width="917" height="126" /></div>
			    <div class="clear" style="clear:both;"></div>
			  </div>
		</td>
        </tr>
        </table>
       <!--  <tr bgcolor="#FFFFFF"> 
          <td colspan="3" height="11"></td>
        </tr>  -->
   	<s:iterator value='scormStuList' id='scormStu' status='stas'>
      <table width=60%  border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61" >
        <tr bgcolor="#FFFFFF"> 
          <td width="77%" bgColor=#E6EFF9  colspan="3" align="center" class="w2"><s:property value='course_name'/>课程-<s:property value='courseWareNameList[#stas.index]'/>课件详细学习记录</td>
        </tr>
        <tr align="center"> 
          <td colspan="3" class="t12_14_bgE3EAE9">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr bgcolor="#FFFFFF"> 
                <td valign="top"> <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;课程完成情况：</td>
                            <td width="171" bgColor=#ffffff class="w1"><script language="javascript">var last_<s:property value='#stas.index'/> =new CreatPro("last_<s:property value='#stas.index'/>", <s:property value='#scormStu.completePercent'/>,"2.5",5000,1,10,"/entity/bzz-students/");last_<s:property value='#stas.index'/>.stepPro()</script> 
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                     <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;总学习时间：</td>
                            <td width="171" bgColor=#ffffff class="w1"><s:property value='#scormStu.totalTime'/></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;总学习次数：</td>
                            <td width="171" bgColor=#ffffff class="w1"><s:property value='#scormStu.attemptNum'/></td>
                          </tr>
                        </table></td>
                    </tr>
                  <!--   <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;课程成绩：</td>
                            <td width="171" bgColor=#ffffff class="w1"></td>
                          </tr>
                        </table></td>
                    </tr>  -->
                   <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;首次进入课程时间：</td>
                            <td width="171" bgColor=#ffffff class="w1"><s:date name='#scormStu.firstDate' format='yyyy-MM-dd HH:mm:ss'/></td>
                          </tr>
                        </table></td>
                    </tr>
                     <tr bgcolor="#FFFFFF"> 
                      <td valign="middle" class="t12_14_bgE3EAE9"> <table width="532" border="0" cellpadding="0" cellspacing="0">
                          <tr bgcolor="#FFFFFF"> 
                            <td width="340" bgColor=#ffffff class="w1">&nbsp;&nbsp;&nbsp;&nbsp;最后离开课程时间：</td>
                            <td width="171" bgColor=#ffffff class="w1"><s:date name='#scormStu.lastDate' format='yyyy-MM-dd HH:mm:ss'/></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
  <!--   </table>
   <table>--> 
        <tr bgcolor="#FFFFFF"> 
          <td width="70%" bgColor=#E6EFF9 colspan="3" align="center" class="w2">课程学习状态</td>
        </tr>
        <tr align="center"> 
          <td colspan="3" class="t12_14_bgE3EAE9"><table width=100%  border="0" align="center" bgcolor="3F6C61" cellpadding="2" cellspacing="1">
              <tr bgcolor="#FFFFFF"> 
              <td><div id="tabletree_<s:property value='#stas.index'/>" ></div></td>
              </tr>
            </table></td>
        </tr>
      </table>
<script type="text/javascript">
	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/training/student/course/courseware/scorm12/js/ext-2.2/resources/images/default/s.gif';
	var studyStatusObj = null;
	var studyStatusObjStr = "";
	function refreshSSObj()
	{
		try{
			studyStatusObjStr = '<s:property value='jsonList[#stas.index]' escape='false'/>';
			var tt = JSON.parse(studyStatusObjStr, function (key, value)	{
																		 	 return value;
																		  	}
								)
			studyStatusObj = scoManager.creatSSObj(tt);
			//studyStatusObj = eval('['+studyStatusObjStr+']');
			//studyStatusObj = studyStatusObj[0];
		}
		catch(err){}
	}
	refreshSSObj();
	

	//构建tree
	
	function creatTree()
	{
		var tempObj = new Ext.tree.ColumnTree({
			width		: 915,
			height		: 350,
			rootVisible	: false,
			autoScroll	: true,
			//title		: '跟踪记录',
			renderTo	: 'tabletree_<s:property value='#stas.index'/>',
			
			//{
			//	header:'学习单元名称',
			//	width:200,
			//	dataIndex:'sid'
			//},
			columns:[
			{
				header:'学习单元标题',
				width:400,
				dataIndex:'title'
			}
			,{
				header:'学习状态',
				width:200,
				dataIndex:'status'
				,
				renderer:selectStatus
			},{
				header:'学习时长',
				width:400,
				dataIndex:'duration'
			}],
			//,{
			//	header:'获得成绩',
			//	width:200,
			//	dataIndex:'score',
			//	renderer:showScore
			//}
	
			loader: new Ext.tree.TreeLoader({
				//dataUrl:'column-data.json',
				uiProviders:{
					'col': Ext.tree.ColumnNodeUI
				}
			}),
			
			//指定显示的根位置
			root: new Ext.tree.AsyncTreeNode({ 
				text:'课程学习模块学习记录', 
				expanded:true,
				children: (studyStatusObj && studyStatusObj.children)?studyStatusObj.children:json
			})
    });
		return tempObj;
	}
	
	var tree = creatTree();
</script>
</s:iterator>
</body>
</s:if>
<s:else>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>没有学习记录</title>
</head>

<body>
<table width="100%" height="100%" border="0">
<tr><td width="100%" height="100%" valign="middle" align="center">
<table width="300" border="0">
  <tr>
    <td width="100%" align="center">该学员的此课程没有学习记录!</td>
  </tr>
  <tr>
    <td width="100%" align="center">【<span><a href="#" onClick="javascript:window.close()" style="color:#FE7E01; text-decoration:none;">关闭</a></span>】</td>
  </tr>
</table>
</td></tr>
</table>
</body>
</s:else>
</html>


