<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link href="assets/NewDefault.css" rel="stylesheet" type="text/css">
    <link id="linkBaseUrl" rel="stylesheet" type="text/css" href="assets/q_12px.css">
    <link href="assets/newdesign.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="assets/zhezhao.js"></script>
	
</head>
<body class="queedit-page">
	<div class="divMenu-cnt">
		 <div id="divMenu">
            <div class="divMenu-hd"></div>
			 <div class="inn">
			<ul>
				<li><a href="javascript:void(0);" onclick="save_paper(&#39;edit&#39;,true);" class="link-666 menu" title="保存并继续编辑，可以使用快捷键Ctrl+S">
					<span class="design-icon design-save"></span><span><strong>保存</strong></span></a>(<input id="chkAutoSave" type="checkbox" checked="checked" onclick="chkAutoSave_Click(this);" style="vertical-align: middle;" title="每5分钟定时保存一次">自动保存) </li>
				<li><a href='paper_view?id=<s:property value="#parameters.curid"/>&t=x123' onclick="return save_paper(&#39;preview&#39;,true,true);" target="_blank" class="link-666 menu">
					<span class="design-icon design-preview"></span><span><strong>预览问卷</strong></span></a></li>
				<li style="display:none;"><a href="/questionnairemng/designnew.aspx?version=7&openType=redesign&curid=2586349#" onclick="return save_paper(&#39;pub&#39;,true);" class="link-666 menu" title="保存本次编辑的内容" onmouseout="sb_setmenunav(&#39;divFinishRun&#39;, false,this)" onmouseover="sb_setmenunav(&#39;divFinishRun&#39;, true,this)">
					<span class="design-icon design-finish"></span><span><strong>完成编辑</strong></span></a>(<a href="javascript:abort();" title="放弃更改并返回" class="link-999">放弃更改</a>)</li>
				<li><a href="javascript:paper_attr('paper_attr_title');" class="link-666 menu" title="问卷属性"><span class="design-icon design-attr"></span><span><strong>问卷属性</strong></span></a></li>  
				 <li style="display:none"><a href="javascript:initPageQuestionRandom();" class="link-666 menu" title="问卷题目随机设置"><span></span><span><strong>题目随机设置</strong></span></a></li> 
				 <li style="display:none"><input type="checkbox" id="chkUseSelfTopic" style="vertical-align: middle;"><label for="chkUseSelfTopic">隐藏系统<b>题号</b></label></li> 
				 <li style="display:none" id="liSetCat"><a href="javascript:void(0);" onclick="PDF_launch(&#39;/wjx/design/categorysettings.aspx?activity=2586349&#39;,680,500);" class="link-666 menu" style="color:Red;">设置维度(分类)</a></li>             
				<li style="display:none; float:right;"><a href="http://www.sojump.com/help/help.aspx?catid=6" target="_blank" class="link-U00a6e6"><strong>帮助</strong></a>(<a href="http://www.sojump.com/help/help.aspx?catid=7" target="_blank" class="link-UF90">全部题型预览</a>)</li>
		   
			  
			</ul> 
			</div>
		</div> 
	</div>
   
    
    <!-- <input name="hfData" type="hidden" id="hfData" value="2013/7/21 11:29:48§天脉§关于天脉的员工§0§0§0§false§false§0¤page§1§§0§§§¤radio§1§请在此输入问题标题〒〒〒§0§1〒§true§false§0§true§ceping§§0§§§选项91〒false〒1〒0〒〒〒〒〒〒§选项92〒false〒2〒0〒〒〒〒〒〒¤radio§2§请在此输入问题标题〒〒〒§2§1〒§true§false§0§true§§§0§§§很不满意〒false〒1〒0〒〒〒〒〒〒§不满意〒false〒2〒0〒〒〒〒〒〒§一般〒false〒3〒0〒〒〒〒〒〒§满意〒false〒4〒0〒〒〒〒〒〒§很满意〒false〒5〒0〒〒〒〒〒〒?false§6cb0e5a6-49e3-446d-b85b-046a0e3eafdc§0§q_12px.css§False§null"> -->
	<!-- <input name="hfData" type="hidden" id="hfData" value="2013/07/26 17:02:06§天脉§关于天脉的员工§0§0§0§false§§§7§false§false§false§false§false¤page§1§§0§§§¤radio§1§请在此输入问题标题〒〒〒§0§1〒false§true§false§0§true§ceping§§0§§§选项91〒false〒1〒0〒false〒false〒〒false〒〒§选项92〒false〒2〒0〒false〒false〒〒false〒〒¤radio§2§请在此输入问题标题〒〒〒§2§1〒false§true§false§0§true§§§0§§§很不满意〒false〒1〒0〒false〒false〒〒false〒〒§不满意〒false〒2〒0〒false〒false〒〒false〒〒§一般〒false〒3〒0〒false〒false〒〒false〒〒§满意〒false〒4〒0〒false〒false〒〒false〒〒§很满意〒false〒5〒0〒false〒false〒〒false〒〒¤radio§3§请在此输入问题标题〒〒〒§1§1〒false§true§false§§true§§§§§§很不满意〒false〒1〒0〒false〒false〒〒false〒〒§不满意〒false〒2〒0〒false〒false〒〒false〒〒§一般〒false〒3〒0〒false〒false〒〒false〒〒§满意〒false〒4〒0〒false〒false〒〒false〒〒§很满意〒false〒5〒0〒false〒false〒〒false〒〒¤radio§4§请在此输入问题标题〒〒〒§0§1〒false§true§false§§true§ceping§§§§§选项14〒false〒1〒0〒false〒false〒〒false〒〒§选项15〒false〒2〒0〒false〒false〒〒false〒〒§选项16〒false〒3〒〒false〒false〒〒false〒〒?false§6cb0e5a6-49e3-446d-b85b-046a0e3eafdc§0§q_12px.css§False§null"/> -->
	
	<input name="hfData" type="hidden" id="hfData" value="<s:property value="surveydata" escape="true" />" />
	<div class="que-header">
		2013年 新疆国培计划问卷系统
	</div>
    <div style="width:870px; margin: 0 auto; text-align: left;border:4px solid #289fe5;">
		
        <div id="topnav" style="visibility: visible;">
            <div class="tabQTypet" style="background: 0px; border: 0px; height: 40px;">
                <ul id="tQType">
                    
                    <li><a href="javascript:createFreQ(&#39;ceping&#39;,1);" onmouseout="sb_setmenunav(&#39;divScore&#39;, false,this);" onmouseover="sb_setmenunav(&#39;divScore&#39;, true,this);" title="可以使用快捷键Alt+3" style="text-decoration: none; padding:0;"><span class="design-icon design-radio">
                        </span><span class="spanQTxt">单选题</span><span class="bordCss bordBottomCssN"></span></a></li>
					
					<li><a href="javascript:createFreQ(&#39;ceping&#39;,2);" onmouseout="sb_setmenunav('divCheckbox', false,this)" onmouseover="sb_setmenunav('divCheckbox', true,this)" title="可以使用快捷键Alt+2" style="text-decoration: none;
                        padding: 0;" class=""><span class="design-icon design-check"></span><span class="spanQTxt">多选题</span><span class="bordCss bordBottomCssN"></span></a></li>
					
					<li style="display:none;"><a href="javascript:createFreQ('page');" onmouseout="sb_setmenunav('divqqqPage', false,this)" onmouseover="sb_setmenunav('divqqqPage', true,this)" style="text-decoration: none;
                        padding: 0;"><span class="design-icon design-page"></span><span class="spanQTxt">分页栏</span><span class="bordCss bordBottomCssN"></span><span style="font-weight: normal;"></span></a></li>
                   
                   
                </ul> <div style="clear:both;"></div>
            </div>
        </div>
        <div class="survey" style="padding: 15px 24px 15px 25px; overflow: auto; background-color: rgb(255, 255, 255); width: 820px; height: 518px; background-position: initial initial; background-repeat: initial initial;" id="sur">
            <div style="width: 780px; border: 1px solid #e3e3e3; margin: 0 auto; padding-left: 10px;">
                <div id="divId" class="surveyhead" style="border: 2px solid rgb(255, 255, 255); margin-top: 3px; width: 730px;" title="双击鼠标编辑问卷属性">
                    <h1 id="pater_title" style="cursor: pointer;" title="双击鼠标编辑问卷属性"></h1>
                    <div id="pater_desc" class="surveydescription" style="cursor: pointer;" title="双击鼠标编辑问卷属性"></div>
                </div>
                <div id="question" class="surveycontent"></div>
                <div style="clear: both;">
                </div>
            </div>
        </div>
        <div id="status_tip" style="height: auto; left: 205px; display: none; top: 648px;">保存问卷成功！</div>
    </div>
    <div id="toolTipLayer" style="position: absolute; display: none; line-height: 20px; width: 150px; background-color: rgb(239, 247, 255); text-align: left; border: 1px solid rgb(127, 157, 185); z-index: 20000; overflow: auto; left: 603px; top: 429px; background-position: initial initial; background-repeat: initial initial;"><div style="padding:5px 10px;"><div style="cursor:pointer;margin-top:3px;"><a onclick="valChanged(2);" class="link-06f" href="javascript:void(0);">交换选项分数</a></div><div style="cursor:pointer;margin-top:3px;"><a onclick="valChanged(0);" class="link-06f" href="javascript:void(0);">分数<b>从1开始</b>顺序递增</a></div><div style="cursor:pointer;margin-top:3px;"><a onclick="valChanged(1);" class="link-06f" href="javascript:void(0);">选项分数全部<b>加1</b></a></div><div style="cursor:pointer;margin-top:3px;"><a onclick="valChanged(-1);" class="link-06f" href="javascript:void(0);">选项分数全部<b>减1</b></a></div></div></div>
    <div id="divQAttr" style="width: 500px; text-align: left; display: none;">
        <ul class="div_title_attr_question" style="padding: 10px 0 0 10px; margin: 0px;">
            <li style="list-style-type: none;"><strong>问卷标题：</strong><input id="paper_attr_title" title="在此输入问卷的标题，例如：关于大学生心理健康的调查" onblur="paper_attr_title_onblur(this);" style="font-size: 12px;
                width: 400px;" class="" type="text" value="请输入您的问卷的标题" size="54" maxlength="100">
            </li>
            <li style="margin-top: 15px;">
                <table>
                    <tbody><tr>
                        <td valign="middle">
                            <strong>问卷说明：</strong>
                        </td>
                        <td>
                            <textarea id="paper_attr_desc" onblur="paper_attr_desc_onblur(this);" rows="10" title="在此输入问卷说明，可以使用HTML格式插入图片或者音乐，Google地图等"></textarea><br>
                        </td>
                    </tr>
                </tbody></table>
            </li>
        </ul>
        <div style="text-align: center; margin-top: 10px;">
            <input type="button" value="确定" onclick="window.parent.PDF_close();" class="finish">
        </div>
    </div>
    <div id="divRandom" style="width: 500px; text-align: left; display: none;">
        <div style="margin:20px 0 0 10px;">
           
            <strong>随机调整题目顺序</strong><a href="http://www.sojump.com/help/help.aspx?helpid=15" title="显示帮助" class="link-00a6e6" target="_blank"><b>(?)</b></a> <span>
                    <input id="radioRandomDisplayNone" type="radio" onclick="radioRandomDisplay_Click()" name="randomDisplay" checked="checked">不调整&nbsp;&nbsp;<input id="radioRandomDisplaySubject" type="radio" onclick="radioRandomDisplay_Click()" name="randomDisplay">按题目随机&nbsp;&nbsp;<input id="radioRandomDisplayPageNum" onclick="radioRandomDisplay_Click()" type="radio" name="randomDisplay">按分页随机
                    &nbsp;&nbsp;<input id="radioRandomPageQuestion" onclick="radioRandomDisplay_Click()" type="radio" name="randomDisplay">分段随机</span>
            <div style="height:5px;" class="divclear">
            </div>
            <div id="divNoPart" style="display: none;">
            <span id="divRandomDisplay"><b style="color: #ff6600;">随机设置：</b>&nbsp;开始<span id="spanBegin">题</span>号：<input id="txtRandomBegin" onblur="txtRandom_onblur();" style="font-size: 12px; width: 30px;" type="text">&nbsp;结束<span id="spanEnd">题</span>号：<input id="txtRandomEnd" onblur="txtRandom_onblur();" style="font-size: 12px; width: 30px;" type="text"></span> <span id="divRandomDisplayNum" style="margin-left: 12px; display: none">
                            <input id="chkPart" type="checkbox">只显示部分<span id="spanPartTip1">题目</span><a href="javascript:void(0);" style="color: green;" title="可以用来实现类似题库的功能">(?)</a> <span id="spanPartNum" style="display: none;">
                                    <b><span id="spanPartTip2">题目</span>数：</b><input id="txtPartNum" value="" type="text" style="width: 30px;"></span> </span>
            </div>
            <div id="divPageQuestion" style="margin: 0px; padding: 0px; display: none;">
            
            </div>
            <div style="margin-top: 15px; color: rgb(255, 102, 0); display: none;" id="divRandomTip">
                提示：如果问卷中包含“跳题逻辑”，“关联逻辑”，或者“引用逻辑”，请不要使用随机功能，因为随机功能会导致题目顺序错乱！</div>
            <div style="text-align: center; margin-top: 10px;">
                <input type="button" value="确定" onclick="endSetRandom();" class="finish">
            </div>
        </div>
    </div>
    <div id="qType" style="display: none; left: 862px; top: 83px;">
        <iframe class="zindexDiv2" frameborder="0"></iframe>
        <table border="0" cellpadding="0" cellspacing="0" style="background: #cfe7ff; height: 240px;">
            <tbody><tr style="background: #cfe7ff; height: 110px;">
                <td class="tdT">
                    <div style="float: left; padding-top: 22px; padding-left: 6px; width: 30px;">
                        个人信息</div>
                    
                </td>
                <td align="center">
                    <ul>
                        <li style="margin-left: 0;"><a href="javascript:createFreQ('性别');">性别</a></li>
                        <li><a href="javascript:createFreQ('婚姻状况');">婚姻状况</a></li>
                        <li><a href="javascript:createFreQ('姓名');">姓名</a></li>
                        <li><a href="javascript:createFreQ('身份证号');">身份证号</a></li>
                        <li style="margin-left: 0;"><a href="javascript:createFreQ('年龄段');">年龄段</a></li>
                        <li><a href="javascript:createFreQ('生日');">生日</a></li>
                        <li><a href="javascript:createFreQ('电话');">电话</a></li>
                        <li><a href="javascript:createFreQ('手机');">手机</a></li>
                        <li style="margin-left: 0;"><a href="javascript:createFreQ('Email');">Email</a></li>
                        <li><a href="javascript:createFreQ('网址');">网址</a></li>
                        <li><a href="javascript:createFreQ('QQ');">QQ</a></li>
                        <li><a href="javascript:createFreQ('MSN');">MSN</a></li>
                        <li style="margin-left: 0;"><a href="javascript:createFreQ('省份');">省份</a></li>
                        <li><a href="javascript:createFreQ('城市');">省份城市</a></li>
                        <li><a href="javascript:createFreQ('城市单选');">省市区</a></li>
                        <li><a href="javascript:void(0);" onclick="createFreQ(&#39;邮寄地址&#39;);">联系信息</a></li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="height: 5px; padding-top: 5px;">
                    <hr class="fLine">
                </td>
            </tr>
            <tr style="background: #cfe7ff;">
                <td class="tdT">
                    <div style="float: left; padding-top: 14px; padding-left: 6px; width: 30px;">
                        教育信息</div>
                    
                </td>
                <td>
                    <ul>
                        <li style="margin-left: 0;"><a href="javascript:createFreQ('高校');">高校</a></li>
                        <li><a href="javascript:createFreQ('院系');">院系</a></li>
                        <li><a href="javascript:createFreQ('专业');">专业</a></li>
                        <li><a href="javascript:createFreQ('学位');">学位</a></li>
                        <li style="margin-left: 0;"><a href="javascript:createFreQ('年级');">年级</a></li>
                        <li><a href="javascript:createFreQ('班级');">班级</a></li>
                        <li><a href="javascript:createFreQ('学号');">学号</a></li>
                        <li><a href="javascript:createFreQ('入学时间');">入学时间</a></li>
                    </ul>
                </td>
            </tr>
            <tr>
                <td colspan="2" style="height: 5px; padding-top: 5px;">
                    <hr class="fLine">
                </td>
            </tr>
            <tr style="background: #cfe7ff;">
                <td class="tdT" valign="middle">
                    <div style="float: left; padding-top: 14px; padding-left: 6px; width: 30px;">
                        职业信息</div>
                    
                </td>
                <td>
                    <ul>
                        <li style="margin-left: 0;"><a href="javascript:createFreQ('行业');">行业</a></li>
                        <li><a href="javascript:createFreQ('职业');">职业</a></li>
                        <li><a href="javascript:createFreQ('工作年限');">工作年限</a></li>
                        <li><a href="javascript:createFreQ('公司名称');">公司名称</a></li>
                        <!--  <li><a href="javascript:createFreQ('公司网址');">公司网址</a></li>-->
                        <li style="margin-left: 0;"><a href="javascript:createFreQ('月收入');">月收入</a></li>
                        <li><a href="javascript:createFreQ('年收入');">年收入</a></li>
                        <li><a href="javascript:createFreQ('部门');">部门</a></li>
                        <li><a href="javascript:createFreQ('员工编号');">员工编号</a></li>
                    </ul>
                </td>
            </tr>
        </tbody></table>
    </div>
    <div id="divRadio" class="downMenu" style="display: none; z-index: 10999; left: 215px; position: absolute; top: 83px;">
        <iframe class="zindexDiv2" frameborder="0"></iframe>
        <ul>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;radio&#39;);">列表单选</a> </li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;radio&#39;,true);">组合单选</a></li>
             <li><a href="javascript:void(0);" onclick="createFreQ(&#39;radio_down&#39;);">下拉框</a></li>
             <li><a href="javascript:void(0);" onclick="createFreQ(&#39;ceping&#39;,1);">评分单选题</a> </li>
            <li><a style="color:#0000cc !important;" href="javascript:void(0);" onclick="createFreQ(&#39;toupiao&#39;,1);">投票单选</a></li>
        </ul>
    </div>
    <div id="divCheckbox" class="downMenu" style="display: none; z-index: 10999; left: 309px; position: absolute; top: 83px;">
        <iframe class="zindexDiv2" frameborder="0"></iframe>
        <ul>
			  <li><a href="javascript:void(0);" onclick="createFreQ(&#39;ceping&#39;,2);">评分多选题</a> </li>
           <!-- <li><a href="javascript:void(0);" onclick="createFreQ(&#39;check&#39;);">列表多选</a> </li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;check&#39;,2);">组合多选</a></li>
           
            <li><a href="javascript:void(0);" style="color:#0000cc !important;" onclick="createFreQ(&#39;toupiao&#39;,2);">投票多选</a></li>-->
            
        </ul>
    </div>
    <div id="divScore" class="downMenu" style="display: none; z-index: 10999; left: -5px;
        position: absolute; top: 36px;">
        <iframe class="zindexDiv2" frameborder="0"></iframe>
        <ul>
			<li><a href="javascript:void(0);" onclick="createFreQ(&#39;ceping&#39;,1);">评分单选题</a> </li>
			<!--li><a href="javascript:void(0);" onclick="createFreQ('radio');">列表单选</a> </li-->
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;likert&#39;);">量表题</a></li>
            
          
        </ul>
    </div>
    <div id="divqMatrix" class="downMenu" style="display: none; z-index: 10999; left: 497px; position: absolute; top: 83px;">
        <iframe class="zindexDiv2" frameborder="0"></iframe>
        <ul>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;matrix&#39;,101);" title="可以设置分数">矩阵量表题</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;matrix&#39;,103);">矩阵单选题</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;matrix&#39;,102);">矩阵多选题</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;matrix&#39;,201);">矩阵文本题</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;matrix&#39;,202);">矩阵滑动条</a></li>
            <li><a href="javascript:void(0);" onclick="if(checkPermission(2)) createFreQ(&#39;matrix&#39;,&#39;303&#39;); return false;">表格下拉框</a></li>
             <li><a href="javascript:void(0);" onclick="if(checkPermission(2)) createFreQ(&#39;matrix&#39;,&#39;301&#39;); return false;">表格数值题</a></li>
              <li><a href="javascript:void(0);" onclick="if(checkPermission(2)) createFreQ(&#39;matrix&#39;,&#39;302&#39;); return false;">表格文本题</a></li>
        </ul>
    </div>
    <div id="divText" class="downMenu" style="display: none; z-index: 10999; left: 591px; position: absolute; top: 83px;">
        <iframe class="zindexDiv2" frameborder="0"></iframe>
        <ul>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;question&#39;);">单行文本</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;question&#39;,5);">多行文本</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;gapfill&#39;);">填空题</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;数字&#39;);">数值</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;日期&#39;);">日期</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;城市单选&#39;);">省份城市</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;省市区&#39;);">省市区(县)</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;邮寄地址&#39;);">详细地址</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;高校&#39;);">大学/高校</a></li>
        </ul>
    </div>
    <div id="divqCut" class="downMenu" style="display: none; z-index: 10999; left: 779px; position: absolute; top: 83px;">
        <iframe class="zindexDiv2" frameborder="0"></iframe>
       <ul>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;cut&#39;);">段落说明</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;cut&#39;,&#39;&lt;hr/&gt;&#39;);">分隔栏</a></li>
        </ul>
    </div>
     <div id="divqqqPage" class="downMenu" style="display: none; z-index: 10999; left: 685px; position: absolute; top: 83px;">
        <iframe class="zindexDiv2" frameborder="0"></iframe>
         <ul>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;page&#39;);">分页栏</a> </li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;page&#39;,true);">甄别页</a> </li>
        </ul>
    </div>
    <div id="divAllQT" class="downMenu" style="display: none; z-index: 10999; left: 873px; position: absolute; top: 83px;">
        <iframe class="zindexDiv2" frameborder="0"></iframe>
        <ul>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;check&#39;,&#39;&#39;,&#39;1&#39;);">排序题</a></li>
            <li><a href="javascript:void(0);" onclick="if(checkPermission(2)) createFreQ(&#39;matrix&#39;,&#39;303&#39;); return false;">表格题</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;sum&#39;);">比重题</a></li>
            <li><a href="javascript:void(0);" onclick="createFreQ(&#39;slider&#39;);">滑动条</a></li>
            <li><a href="javascript:void(0);" onclick="if(checkPermission(0)) createFreQ(&#39;fileupload&#39;); return false;">
                上传文件</a></li>
        </ul>
    </div>
    <div style="display: none;" id="divTitleEditor">
        <div style="margin: 10px;">
            <textarea style="width: 694px; height: 340px;" id="textTitleId"></textarea>
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input style="font-size: 14px; font-weight: bold; color: #0066cc;" type="button" value=" 保 存 " onclick="clickOK();">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input style="font-size: 12px; color: #0066cc;" type="button" value=" 取 消 " onclick="clickCancel();"></div>
    </div>
   
    <script type="text/javascript">
        $ = function (element) {
            return (typeof (element) == 'object' ? element : document.getElementById(element));
        };
        
        var toolTipLayer = $("toolTipLayer");
        var bodyWidth = document.documentElement.clientWidth || document.body.clientWidth;
        var isNewActivity = "";
        var vipUser = "1";var proUser=0;
        var activityID = "<s:property value="#parameters.curid"/>";
        var isTiyan = "";
        var hfData = $("hfData");
        var serverVersion = '7';
        /* function checkPermission(type) {
            if (!vipUser) {
                if(type==0)
                  $("divUpTip").innerHTML='只有企业版用户才能使用上传文件题，<a href="#" class="link-U00a6e6" target="_blank">查看上传文件题示例</a>';
                else if(type==1)
                  $("divUpTip").innerHTML='只有企业版用户才能使用测试题型，<a href="#" class="link-U00a6e6" target="_blank">查看测试题问卷</a>&nbsp;&nbsp;<a href="#" class="link-U00a6e6" target="_blank">查看测评报告</a>';
                else if(type==2){
                  if(!proUser)
                     alert('只有专业版跟企业版用户才能使用表格题，请升级！\r\n您现在可以先添加好题目，升级后问卷才能发布！');
                  return true;
                }
                  //
                PDF_launch("divUpgradeVip",420,120);
                return false;
            }
            else
                return true;
        } */
        /* if(!vipUser){
          $("divUpgrade").style.display="";
        } */
        var isTiKu=0;
        var tikuId=0;
    </script>
   
	<script type="text/javascript" language="javascript" src="assets/hintinfo.js"></script>
	<script type="text/javascript" language="javascript" src="assets/design_new.js"></script>
    <div style="display:none;"></div>
</body></html>