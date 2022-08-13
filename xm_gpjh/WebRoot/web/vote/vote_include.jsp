<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<s:iterator value="questionList" status="aa">
	<tr>
		<td class="14title">
			<s:property value="#aa.index+1" />、（<s:property value="enumConstByFlagQuestionType.name" />）<s:property value="questionNote" escape="false" />
		</td>
	</tr>
		<input type="hidden" name="hidden<s:property value="#aa.index+1" />" id="hidden<s:property value="#aa.index+1" />" value="<s:property value="id"/>"/>
	<tr>
		<td bgcolor="#F9F9F9">
			<s:if test="enumConstByFlagQuestionType.code==1">
				<s:if test="item1 != null">
					<input type="radio" name="<s:property value="id"/>" value="@1">
					<s:property value="item1" />
					<br />
				</s:if>
				<s:if test="item2 != null">
					<input type="radio" name="<s:property value="id"/>" value="@2">
					<s:property value="item2" />
					<br />
				</s:if>
				<s:if test="item3 != null">
					<input type="radio" name="<s:property value="id"/>" value="@3">
					<s:property value="item3" />
					<br />
				</s:if>
				<s:if test="item4 != null">
					<input type="radio" name="<s:property value="id"/>" value="@4">
					<s:property value="item4" />
					<br />
				</s:if>
				<s:if test="item5 != null">
					<input type="radio" name="<s:property value="id"/>" value="@5">
					<s:property value="item5" />
					<br />
				</s:if>
				<s:if test="item6 != null">
					<input type="radio" name="<s:property value="id"/>" value="@6">
					<s:property value="item6" />
					<br />
				</s:if>
				<s:if test="item7 != null">
					<input type="radio" name="<s:property value="id"/>" value="@7">
					<s:property value="item7" />
					<br />
				</s:if>
				<s:if test="item8 != null">
					<input type="radio" name="<s:property value="id"/>" value="@8">
					<s:property value="item8" />
					<br />
				</s:if>
				<s:if test="item9 != null">
					<input type="radio" name="<s:property value="id"/>" value="@9">
					<s:property value="item9" />
					<br />
				</s:if>
				<s:if test="item10 != null">
					<input type="radio" name="<s:property value="id"/>" value="@10">
					<s:property value="item10" />
					<br />
				</s:if>
				<s:if test="item11 != null">
					<input type="radio" name="<s:property value="id"/>" value="@11">
					<s:property value="item11" />
					<br />
				</s:if>
				<s:if test="item12 != null">
					<input type="radio" name="<s:property value="id"/>" value="@12">
					<s:property value="item12" />
					<br />
				</s:if>
				<s:if test="item13 != null">
					<input type="radio" name="<s:property value="id"/>" value="@13">
					<s:property value="item13" />
					<br />
				</s:if>
				<s:if test="item14 != null">
					<input type="radio" name="<s:property value="id"/>" value="@14">
					<s:property value="item14" />
					<br />
				</s:if>
				<s:if test="item15 != null">
					<input type="radio" name="<s:property value="id"/>" value="@15">
					<s:property value="item15" />
					<br />
				</s:if>
				<s:if test="item16 != null">
					<input type="radio" name="<s:property value="id"/>" value="@16">
					<s:property value="item16" />
					<br />
				</s:if>
				<s:if test="item17 != null">
					<input type="radio" name="<s:property value="id"/>" value="@17">
					<s:property value="item17" />
					<br />
				</s:if>
				<s:if test="item18 != null">
					<input type="radio" name="<s:property value="id"/>" value="@18">
					<s:property value="item18" />
					<br />
				</s:if>
				<s:if test="item19 != null">
					<input type="radio" name="<s:property value="id"/>" value="@19">
					<s:property value="item19" />
					<br />
				</s:if>
				<s:if test="item20 != null">
					<input type="radio" name="<s:property value="id"/>" value="@20">
					<s:property value="item20" />
					<br />
				</s:if>
				<s:if test="item21 != null">
					<input type="radio" name="<s:property value="id"/>" value="@21">
					<s:property value="item21" />
					<br />
				</s:if>
				<s:if test="item22 != null">
					<input type="radio" name="<s:property value="id"/>" value="@22">
					<s:property value="item22" />
					<br />
				</s:if>
				<s:if test="item23 != null">
					<input type="radio" name="<s:property value="id"/>" value="@23">
					<s:property value="item23" />
					<br />
				</s:if>
				<s:if test="item24 != null">
					<input type="radio" name="<s:property value="id"/>" value="@24">
					<s:property value="item24" />
					<br />
				</s:if>
				<s:if test="item25 != null">
					<input type="radio" name="<s:property value="id"/>" value="@25">
					<s:property value="item25" />
					<br />
				</s:if>
				<s:if test="item26 != null">
					<input type="radio" name="<s:property value="id"/>" value="@26">
					<s:property value="item26" />
					<br />
				</s:if>
				<s:if test="item27 != null">
					<input type="radio" name="<s:property value="id"/>" value="@27">
					<s:property value="item27" />
					<br />
				</s:if>
				<s:if test="item28 != null">
					<input type="radio" name="<s:property value="id"/>" value="@28">
					<s:property value="item28" />
					<br />
				</s:if>
				<s:if test="item29 != null">
					<input type="radio" name="<s:property value="id"/>" value="@29">
					<s:property value="item29" />
					<br />
				</s:if>
				<s:if test="item30 != null">
					<input type="radio" name="<s:property value="id"/>" value="@30">
					<s:property value="item30" />
					<br />
				</s:if>
				<s:if test="item31 != null">
					<input type="radio" name="<s:property value="id"/>" value="@31">
					<s:property value="item31" />
					<br />
				</s:if>
				<s:if test="item32 != null">
					<input type="radio" name="<s:property value="id"/>" value="@32">
					<s:property value="item32" />
					<br />
				</s:if>
				<s:if test="item33 != null">
					<input type="radio" name="<s:property value="id"/>" value="@33">
					<s:property value="item33" />
					<br />
				</s:if>
				<s:if test="item34 != null">
					<input type="radio" name="<s:property value="id"/>" value="@34">
					<s:property value="item34" />
					<br />
				</s:if>
				<s:if test="item35 != null">
					<input type="radio" name="<s:property value="id"/>" value="@35">
					<s:property value="item35" />
					<br />
				</s:if>
				<s:if test="item36 != null">
					<input type="radio" name="<s:property value="id"/>" value="@36">
					<s:property value="item36" />
					<br />
				</s:if>
				<s:if test="item37 != null">
					<input type="radio" name="<s:property value="id"/>" value="@37">
					<s:property value="item37" />
					<br />
				</s:if>
				<s:if test="item38 != null">
					<input type="radio" name="<s:property value="id"/>" value="@38">
					<s:property value="item38" />
					<br />
				</s:if>
				<s:if test="item39 != null">
					<input type="radio" name="<s:property value="id"/>" value="@39">
					<s:property value="item39" />
					<br />
				</s:if>
				<s:if test="item40 != null">
					<input type="radio" name="<s:property value="id"/>" value="@40">
					<s:property value="item40" />
					<br />
				</s:if>
			</s:if>
			<s:else>
				<s:if test="item1 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@1">
					<s:property value="item1" />
					<br />
				</s:if>
				<s:if test="item2 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@2">
					<s:property value="item2" />
					<br />
				</s:if>
				<s:if test="item3 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@3">
					<s:property value="item3" />
					<br />
				</s:if>
				<s:if test="item4 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@4">
					<s:property value="item4" />
					<br />
				</s:if>
				<s:if test="item5 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@5">
					<s:property value="item5" />
					<br />
				</s:if>
				<s:if test="item6 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@6">
					<s:property value="item6" />
					<br />
				</s:if>
				<s:if test="item7 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@7">
					<s:property value="item7" />
					<br />
				</s:if>
				<s:if test="item8 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@8">
					<s:property value="item8" />
					<br />
				</s:if>
				<s:if test="item9 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@9">
					<s:property value="item9" />
					<br />
				</s:if>
				<s:if test="item10 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@10">
					<s:property value="item10" />
					<br />
				</s:if>
				<s:if test="item11 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@11">
					<s:property value="item11" />
					<br />
				</s:if>
				<s:if test="item12 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@12">
					<s:property value="item12" />
					<br />
				</s:if>
				<s:if test="item13 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@13">
					<s:property value="item13" />
					<br />
				</s:if>
				<s:if test="item14 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@14">
					<s:property value="item14" />
					<br />
				</s:if>
				<s:if test="item15 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@15">
					<s:property value="item15" />
					<br />
				</s:if>
				<s:if test="item16 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@16">
					<s:property value="item16" />
					<br />
				</s:if>
				<s:if test="item17 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@17">
					<s:property value="item17" />
					<br />
				</s:if>
				<s:if test="item18 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@18">
					<s:property value="item18" />
					<br />
				</s:if>
				<s:if test="item19 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@19">
					<s:property value="item19" />
					<br />
				</s:if>
				<s:if test="item20 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@20">
					<s:property value="item20" />
					<br />
				</s:if>
				<s:if test="item21 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@21">
					<s:property value="item21" />
					<br />
				</s:if>
				<s:if test="item21 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@21">
					<s:property value="item21" />
					<br />
				</s:if>
				<s:if test="item22 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@22">
					<s:property value="item22" />
					<br />
				</s:if>
				<s:if test="item23 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@23">
					<s:property value="item23" />
					<br />
				</s:if>
				<s:if test="item24 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@24">
					<s:property value="item24" />
					<br />
				</s:if>
				<s:if test="item25 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@25">
					<s:property value="item25" />
					<br />
				</s:if>
				<s:if test="item26 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@26">
					<s:property value="item26" />
					<br />
				</s:if>
				<s:if test="item27 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@27">
					<s:property value="item27" />
					<br />
				</s:if>
				<s:if test="item28 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@28">
					<s:property value="item28" />
					<br />
				</s:if>
				<s:if test="item29 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@29">
					<s:property value="item29" />
					<br />
				</s:if>
				<s:if test="item30 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@30">
					<s:property value="item30" />
					<br />
				</s:if>
				<s:if test="item31 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@31">
					<s:property value="item31" />
					<br />
				</s:if>
				<s:if test="item32 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@32">
					<s:property value="item32" />
					<br />
				</s:if>
				<s:if test="item33 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@33">
					<s:property value="item33" />
					<br />
				</s:if>
				<s:if test="item34 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@34">
					<s:property value="item34" />
					<br />
				</s:if>
				<s:if test="item35 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@35">
					<s:property value="item35" />
					<br />
				</s:if>
				<s:if test="item36 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@36">
					<s:property value="item36" />
					<br />
				</s:if>
				<s:if test="item37 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@37">
					<s:property value="item37" />
					<br />
				</s:if>
				<s:if test="item38 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@38">
					<s:property value="item38" />
					<br />
				</s:if>
				<s:if test="item39 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@39">
					<s:property value="item39" />
					<br />
				</s:if>
				<s:if test="item40 != null">
					<input type="checkbox" name="<s:property value="id"/>" value="@40">
					<s:property value="item40" />
					<br />
				</s:if>
			</s:else>
		</td>
	</tr>
</s:iterator>