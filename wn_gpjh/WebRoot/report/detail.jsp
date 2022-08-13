<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">

        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
		<div class="wrapper">

			<div class="content">
				<div class="ctt_hd">
					调查结果
				</div>
				<div class="ctt_bd">
					
					<div class="que_meta">
						<table>
							<tbody>
								<tr>                         
									<td>所属项目：<s:property value="infoDic.get(submit.loginkey.id)[0]"/></td>
									<td>承训单位：<s:property value="infoDic.get(submit.loginkey.id)[2]"/></td>
									<td>学科：<s:property value="infoDic.get(submit.loginkey.id)[1]"/></td>
								</tr>
								<tr>
									<td>登录码：<s:property value="submit.loginkey.id"/></td>
									<td>提交时间：<s:date name="submit.creatTime" format="yyyy-MM-dd HH:mm:ss" /></td>
									<td>IP：<s:property value="submit.ip"/></td>
								</tr>
							</tbody>
						</table>
					</div>
					<h1 style="border:0;padding-top:20px;">满意度调查</h1>
					<ul class="que_results">
					<s:set name="flag" value="1" />
					<s:set name="questionId" value="-1" />
					<s:iterator value="results" id="result">
						
							<s:if test="#questionId==-1" >
								<li>
								第<s:property value="#flag"/>题：<s:property value="#result.question.name"/><br>
								<s:if test="#result.question.type == 6">
									<strong>调查结果：</strong> <s:property value="#result.answer.inx"/>: <s:property value="#result.content"/>
								</s:if>
								<s:elseif test="#result.question.type == 7"></s:elseif>
								<s:else>
									<strong>调查结果：</strong> <s:property value="#result.answer.inx"/>: <s:property value="#result.answer.name"/>
								</s:else>
								
							</s:if>
							<s:else>
								
								<s:if test="#questionId == #result.question.id">
									<s:property value="#result.answer.inx"/>: <s:property value="#result.answer.name"/>
								</s:if>
								<s:else>
									</li>
									<s:set name="flag" value="#flag+1"/>
									<li>
									第<s:property value="#flag"/>题：<s:property value="#result.question.name"/><br>
									<s:if test="#result.question.type == 6">
										<strong>调查结果：</strong>  <span style="color:red"><s:property value="#result.content"/></span>
									</s:if>
									<s:elseif test="#result.question.type == 7"></s:elseif>
									<s:else>
										<strong>调查结果：</strong> <s:property value="#result.answer.inx"/>: <s:property value="#result.answer.name"/>
									</s:else>
									
								</s:else>
								
							</s:else>							
							
						
						<s:set name="questionId" value="#result.question.id" />
						
					</s:iterator>
					</li>
					</ul>	
				</div>
			</div>
		</div>
        
    </body>
</html>