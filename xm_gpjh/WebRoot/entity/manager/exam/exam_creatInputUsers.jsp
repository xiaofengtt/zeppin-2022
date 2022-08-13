<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>自动生成登分帐户</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css" />
		<script>
		<!--
		function sub(){
		   document.getElementById("info").innerHTML='正在生成登分帐户，请等待...';
		   return true;
		}
		//-->
		</script>
	</head>
	<body>
		<form name="final" method="get" action="/entity/exam/prexamscoreinputuser_creatInputUsersexe.action"
			onsubmit="return sub();">
			<div id="main_content">
				<div class="content_title">
					自动生成登分帐户
				</div>
				<div class="cntent_k" align="center">
					<div class="k_cc">
						<table width="80%">
							<tr>
								<td>
									<div align="center" class="postFormBox">
										自动生成登分帐户
									</div>
								</td>
							</tr>
							<tr>
								<td width="18%">
									<div align="left" class="postFormBox">
										注：1、如果已经生成登分帐户，执行将覆盖掉原来的数据。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、执行自动生成登分时间限制待定。<br/>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div align="center" id="info" class="postFormBox">
										<input type="submit" value="确定" id="submit1"/>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>