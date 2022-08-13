<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>

<%
PlatformFactory platformfactory = PlatformFactory.getInstance();
BasicRightManage rightManage = platformfactory.creatBasicRightManage();
String id=fixnull(request.getParameter("id"));





int count=0;


    		 	 
    		 	      
                    					 
                    					 
                    	
                    		
                    	
                    	String strsite="";
                    	String strgrade="";
                    	String strmajor="";
                    	String stredutype="";
                    			 
                        String site[] = request.getParameterValues("site");
                        String grade[] = request.getParameterValues("grade");
                        String major[] = request.getParameterValues("major");
                        String edutype[] = request.getParameterValues("edutype");
                        
                        if(site!=null){
                            for(int i=0;i<site.length;i++){
                                strsite=strsite+site[i]+"|";
                              
                            }
                            if(!strsite.equals("")){
                            strsite=strsite.substring(0,strsite.length()-1);
                          }    //out.println(strsite);
                        }
                        
                       
                        if(grade!=null){
                            for(int i=0;i<grade.length;i++){
                                strgrade=strgrade+grade[i]+"|";
                            }
                            if(!strgrade.equals("")){
                           strgrade=strgrade.substring(0,strgrade.length()-1);
                           }
                             //out.println(strgrade);
                        }
                       
                       
                       if(major!=null){
                            for(int i=0;i<major.length;i++){
                                strmajor=strmajor+major[i]+"|";
                            }
                            if(!strmajor.equals("")){
                            strmajor=strmajor.substring(0,strmajor.length()-1);
                             //out.println(strmajor);
                             }
                        }
                        
                        
                         if(edutype!=null){
                            for(int i=0;i<edutype.length;i++){
                                stredutype=stredutype+edutype[i]+"|";
                            }
                            if(!stredutype.equals("")){
                           stredutype=stredutype.substring(0,stredutype.length()-1);
                           }
                        }
                        
                     
                   //if(!strsite.equals("")||!strgrade.equals("")||!strmajor.equals("")||!stredutype.equals("")){
               
							
		 						 
		 						
		 						 count=rightManage.updateRangeAdminRights(id,strsite,strgrade,strmajor,stredutype);
		
							//out.println(count);
                    				 
                    	// }
   
			






if (count < 1)
	{
	%>
	<script language="javascript">
	alert("请先选择范围,再提交,修改失败！");
	window.history.back();
	</script>
	<%
	}
	else
	{
	%>
	<script language="javascript">
	alert("修改成功!");
	window.navigate("change_range_right.jsp?id=<%=id%>");
</script>

<%}%>