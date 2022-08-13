<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
            	<s:iterator value="prVoteQuestionList" status="aa">
            	<tr> 
            		<td class="14title"><s:property value="#aa.index+1"/> 、<s:property value="questionNote" escape="false"/></td>
            	 </tr>
            	 <tr>
            	  <td bgcolor="#F9F9F9">
            	 	<s:if test="enumConstByFlagQuestionType.code==1">          	 	
            	 	 <s:if test="item1 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"   value="<s:property value="id"/>@1"><s:property value="item1"/>
            	 	 </s:if>
            	 	 <s:if test="item2 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"   value="<s:property value="id"/>@2"><s:property value="item2"/>
            	 	 </s:if>
            	 	 <s:if test="item3 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"   value="<s:property value="id"/>@3"><s:property value="item3"/>
            	 	 </s:if>
            	 	 <s:if test="item4 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@4"><s:property value="item4"/>
            	 	 </s:if>  
            	 	 <s:if test="item5 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@5"><s:property value="item5"/>
            	 	 </s:if>
            	 	 <s:if test="item6 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@6"><s:property value="item6"/>
            	 	 </s:if>
            	 	 <s:if test="item7 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@7"><s:property value="item7"/>
            	 	 </s:if>
            	 	 <s:if test="item8 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@8"><s:property value="item8"/>
            	 	 </s:if>      
            	 	 <s:if test="item9 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@9"><s:property value="item9"/>
            	 	 </s:if>
            	 	 <s:if test="item10 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@10"><s:property value="item10"/>
            	 	 </s:if>
            	 	 <s:if test="item11 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@11"><s:property value="item11"/>
            	 	 </s:if>
            	 	 <s:if test="item12 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@12"><s:property value="item12"/>
            	 	 </s:if>  
            	 	 <s:if test="item13 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@13"><s:property value="item13"/>
            	 	 </s:if>
            	 	 <s:if test="item14 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@14"><s:property value="item14"/>
            	 	 </s:if>
            	 	 <s:if test="item15 != null">
            	 	 <input type="radio" name="radio<s:property value="#aa.index+1"/>"    value="<s:property value="id"/>@15"><s:property value="item15"/>
            	 	 </s:if>        	 	         	 	           	 	              	 	 
            	 	</s:if>
            	 	<s:else>
            	 	 <s:if test="item1 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@1"><s:property value="item1"/>
            	 	 </s:if>
            	 	 <s:if test="item2 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@2"><s:property value="item2"/>
            	 	 </s:if>
            	 	 <s:if test="item3 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@3"><s:property value="item3"/>
            	 	 </s:if>
            	 	 <s:if test="item4 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@4"><s:property value="item4"/>
            	 	 </s:if>  
            	 	 <s:if test="item5 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@5"><s:property value="item5"/>
            	 	 </s:if>
            	 	 <s:if test="item6 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@6"><s:property value="item6"/>
            	 	 </s:if>
            	 	 <s:if test="item7 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@7"><s:property value="item7"/>
            	 	 </s:if>
            	 	 <s:if test="item8 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@8"><s:property value="item8"/>
            	 	 </s:if>      
            	 	 <s:if test="item9 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@9"><s:property value="item9"/>
            	 	 </s:if>
            	 	 <s:if test="item10 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@10"><s:property value="item10"/>
            	 	 </s:if>
            	 	 <s:if test="item11 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@11"><s:property value="item11"/>
            	 	 </s:if>
            	 	 <s:if test="item12 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@12"><s:property value="item12"/>
            	 	 </s:if>  
            	 	 <s:if test="item13 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@13"><s:property value="item13"/>
            	 	 </s:if>
            	 	 <s:if test="item14 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@14"><s:property value="item14"/>
            	 	 </s:if>
            	 	 <s:if test="item15 != null">
            	 	 <input type="checkbox" name="checkboxQuestion" value="<s:property value="id"/>@15"><s:property value="item15"/>
            	 	 </s:if>  
            	 	</s:else>
            	 </td> 
            	 </tr>       	 
            	</s:iterator>

