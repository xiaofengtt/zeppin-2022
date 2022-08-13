<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>关闭打开登分帐户登录权限</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css" />
<script>
	 function sub()
  {
   info2.innerHTML='正在同步考试成绩，请等待...';
   return true;
  }
</script>				
	</head>
	<body>
		<form name="final" method="get" action="/entity/exam/prexamscoreinputuser_setinputstatus.action" >
			<div id="main_content">
				<div class="content_title">
					关闭或者打开登分帐户登录登分限制
				</div>
				<div class="cntent_k" align="center">
					<div class="k_cc">
						<table width="80%">
							<tr>
								<td>
									<div align="center" class="postFormBox">
										关闭或者打开登分帐户登录登分限制
									</div>
								</td>
							</tr>
							<tr>
								<td width="18%">
									<div align="left" class="postFormBox">
										注：1、关闭之后登分帐户不能被自动分配。但可以手动调整。<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、打开之后登分帐户才能登陆登分。<br/>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div align="center" id="info" class="postFormBox">
									<s:if test='#session.inputpri==null||#session.inputpri.equals("0")'>
										当前登分帐户不能登录登分<input type="submit" value="打开" id="submit"/>
									</s:if><s:else>
										当前登分帐户可以登录登分<input type="submit" value="关闭" id="submit"/>
									</s:else>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</form>
		<form name="final" method="get" action="/entity/exam/prexamscoreinputuser_setElectiveScore.action" onsubmit ="return sub();">
			<div id="main_content">
				<div class="content_title">
					将考试成绩同步到学生选课表
				</div>
				<div class="cntent_k" align="center">
					<div class="k_cc">
						<table width="80%">
							<tr>
								<td>
									<div align="center" class="postFormBox">
										将登入的考试成绩同步到学生选课表
									</div>
								</td>
							</tr>
							<tr>
								<td width="18%">
									<div align="left" class="postFormBox">
										注：1.只有关闭登分账户的时候才可以同步成绩。<br/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.如新登入的成绩高于学生原有成绩，则会覆盖学生的原有成绩。<br/>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<div align="center" id="info2" class="postFormBox">
									<s:if test='#session.inputpri==null||#session.inputpri.equals("0")'>
										同步学生成绩<input type="submit" value="确定" id="submit"/>
									</s:if><s:else>
										当前登分帐户可以登录登分，不可以同步学生成绩。
									</s:else>
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