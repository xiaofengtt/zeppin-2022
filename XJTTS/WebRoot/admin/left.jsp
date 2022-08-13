<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!--sidebar-menu-->
<div class="sidebar">
	<div class=" nav-sidebar">
		<ul class="nav ">
			<s:iterator value="leftHashNew" id="va">
				<s:if test="value.size()>0">
					<li class="active"><a href="#"><span> <s:property
									value="key.getName()" /></span> </a>
						<ul class="nav">
							<s:iterator value="value" id="v">
								<li <s:if test="leftName==#v.getName()">class="cur"</s:if> ><a href="<s:property value="#v.getFunCategory().getPath()" />"> <s:property
											value="#v.getName()" />
								</a></li>
							</s:iterator>
						</ul></li>
				</s:if>
				<s:else>

				</s:else>

			</s:iterator>

		</ul>
	</div>
	<div class="js-nav-shadow bottom"><img src="../img/round-shadow-bottom.png" style="top: auto; bottom: 0px;"></div>
</div>
<!--sidebar-menu-->