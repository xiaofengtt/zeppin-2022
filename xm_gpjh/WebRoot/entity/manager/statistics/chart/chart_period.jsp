<%@ page language="java" import="java.util.*"
	contentType="text/xml;charset=gbk"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page" />
<jsp:directive.page import="java.io.PrintWriter" />
<jsp:directive.page
	import="com.whaty.platform.sso.web.action.SsoConstant" />
<%@page
	import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<%@page import="java.text.DateFormat"%>

<%!

//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
    	if(str == null || str.equals("null") || str.equals(""))
		str = "";
		return str;
	}


	private int checkdate(String date){
	int num =0;
	StringTokenizer stokenizer = new StringTokenizer(date,":");
	StringBuffer stringBuffer = new StringBuffer();
	while(stokenizer!=null&&stokenizer.hasMoreTokens()){
		stringBuffer.append(stokenizer.nextToken());
	}
	String tem = "1"+stringBuffer.toString();
	int temp = Integer.parseInt(tem);
	if(temp>=1000000&&temp<=1020000){
		num = 1;
	}
	if(temp>1020000&&temp<=1040000){
		num = 2;
	}
	if(temp>1040000&&temp<=1060000){
		num = 3;
	}
	if(temp>1060000&&temp<=1080000){
		num = 4;
	}
	if(temp>1080000&&temp<=1100000){
		num = 5;
	}
	if(temp>1100000&&temp<=1120000){
		num = 6;
	}
	if(temp>1120000&&temp<=1140000){
		num = 7;
	}
	if(temp>1140000&&temp<=1160000){
		num = 8;
	}
	if(temp>1160000&&temp<=1180000){
		num = 9;
	}
	if(temp>1180000&&temp<=1200000){
		num = 10;
	}
	if(temp>1200000&&temp<=1220000){
		num = 11;
	}
	if(temp>1220000&&temp<=1240000){
		num = 12;
	}
	return num;
}
%>

<%
		String temp = request.getParameter("d");
		StringTokenizer stringTokenizer = new StringTokenizer(temp,"|");		
		String [] sten = new String [stringTokenizer.countTokens()];
		int k =0;
		while(stringTokenizer.hasMoreTokens()){
			sten[k] =stringTokenizer.nextToken();
			k++;
		}
		String batch_id = fixnull(sten[0]);
		String sldate = fixnull(sten[1]);
		String eldate = fixnull(sten[2]);
		if(sldate.equals(eldate))
		{	
			sldate+=" 00:00:00";
			eldate+=" 23:59:59";
		}
		
		String sql ="  select st.start_date as sdate , st.end_date as edate "
				+	"  from stuttime st ,pe_bzz_student ps "
				+   "  where ps.fk_sso_user_id = st.fk_ssouser_id and ps.fk_batch_id ='"+batch_id+"' and (st.start_date between to_date('"+sldate+"','yyyy-MM-dd hh24:mi:ss') and to_date('"+eldate+"','yyyy-MM-dd hh24:mi:ss')+1" 
				+   "  or st.end_date between to_date('"+sldate+"','yyyy-MM-dd hh24:mi:ss') and to_date('"+eldate+"','yyyy-MM-dd hh24:mi:ss')+1)";
			//System.out.println(sql);
			dbpool db = new dbpool();
			MyResultSet rs = db.executeQuery(sql);
			int one = 0;
			int two = 0;
			int three = 0;
			int four = 0;
			int five = 0;
			int six = 0;
			int seven = 0;
			int eight = 0;
			int night = 0;
			int ten = 0;
			int eleven = 0;
			int zero = 0;
			
			
		while(rs!=null&&rs.next()){
			String sdate = rs.getString("sdate").substring(11,19);
			String edate = rs.getString("edate").substring(11,19);
			switch (this.checkdate(edate)){
				case 1:
					zero  = zero+1;
					break;
				case 2:
					one  = one+1;
					break;
				case 3:
					two  = two+1;
					break;
				case 4:
					three  = three+1;
					break;
				case 5:
					four  = four+1;
					break;
				case 6:
					five  = five+1;
					break;
				case 7:
					six  = six+1;
					break;
				case 8:
					seven  = seven+1;
					break;
				case 9:
					eight  = eight+1;
					break;
				case 10:
					night  = night+1;
					break;
				case 11:
					ten  = ten+1;
					break;
				case 12:
					eleven  = eleven+1;
					break;
			}
			switch (this.checkdate(sdate)){
			case 1:
				zero  = zero+1;
				break;
			case 2:
				one  = one+1;
				break;
			case 3:
				two  = two+1;
				break;
			case 4:
				three  = three+1;
				break;
			case 5:
				four  = four+1;
				break;
			case 6:
				five  = five+1;
				break;
			case 7:
				six  = six+1;
				break;
			case 8:
				seven  = seven+1;
				break;
			case 9:
				eight  = eight+1;
				break;
			case 10:
				night  = night+1;
				break;
			case 11:
				ten  = ten+1;
				break;
			case 12:
				eleven  = eleven+1;
				break;
		}
		}
			db.close(rs);
	   StringBuffer sqltemp=new StringBuffer();
       String str =" <chart  xAxisName='时间（时）' yAxisName='Number'   showValues='0' alternateHGridColor='FCB541' alternateHGridAlpha='20' divLineColor='FCB541' divLineAlpha='50' canvasBorderColor='666666' baseFontColor='666666' lineColor='FCB541'>"+
		 
       	  "<set label='0~2' value='"+zero+"' />"+ 
		  "<set label='2~4' value='"+one+"' /> "+
		  "<set label='4~6' value='"+two+"' /> "+
		  "<set label='6~8' value='"+three+"' /> "+
		  "<set label='8~10' value='"+four+"' /> "+
		  "<set label='10~12' value='"+five+"' /> "+
		  "<set label='12~14' value='"+six+"' /> "+
		  "<set label='14~16' value='"+seven+"' /> "+
		  "<set label='16~18' value='"+eight+"' /> "+
		  "<set label='18~20' value='"+night+"' /> "+
		  "<set label='20~22' value='"+ten+"' /> "+
		  "<set label='22~24' value='"+eleven+"' /> "+

		  "</chart>";
  
              
		sqltemp.append(str);
		response.setContentType("text/xml");
		response.setHeader("Cache-Control","no-cache");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader("Expires",0);
		PrintWriter w = response.getWriter();
		w.write(sqltemp.toString());
		w.close();
%>
