<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
// || request.getParameter("year") == null || request.getParameter("mmdd") == null
	if(request.getParameter("serial_no") == null)
		throw new BusiException("非法的参数设置");
 
	Integer serial_no = Integer.valueOf(request.getParameter("serial_no"));
	Integer year = Utility.parseInt(request.getParameter("year"), new Integer(1900));
	Integer MMDD = Utility.parseInt(request.getParameter("mmdd"), new Integer(101));
	String date = Utility.trimNull(request.getParameter("wholeDate"));
	Integer del_flag = Utility.parseInt(request.getParameter("del"), new Integer(1));
	//add
	if(serial_no.intValue() == -1){
		List resultSet = (List)session.getAttribute("HolidayCache");
		if(resultSet == null)
			resultSet = new ArrayList();
		Map line = new HashMap();
		line.put("my_serial_no", new Integer(resultSet.size()));
		line.put("yearInt", year);
		line.put("MMDD", MMDD);
		line.put("date", date);
		resultSet.add(line);
		session.setAttribute("HolidayCache", resultSet);
	}else if(del_flag.intValue() == 1){
	//modi
		List resultSet = (List)session.getAttribute("HolidayCache");
		Map line = (Map)resultSet.get(serial_no.intValue());
		line.put("yearInt", year);
		line.put("MMDD", MMDD);
		line.put("date", date);
		session.setAttribute("HolidayCache", resultSet);
	}else{
	//del
		List resultSet = (List)session.getAttribute("HolidayCache");
		resultSet.remove(serial_no.intValue());
		session.setAttribute("HolidayCache", resultSet);
	}

%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=GBK"/>
<title>holiday_ff.jsp</title>
<link href="<%=request.getContextPath()%>/includes/jQuery/msdropdown/dd.css", type="text/css" rel="stylesheet"/>
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet />
<link href="<%=request.getContextPath()%>/includes/jQuery/css/redmond/jquery-ui-1.8.6.custom.css", type="text/css" rel="stylesheet" />

<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/msdropdown/jquery.dd.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.8.6.custom.min.js"></script>

</head>
<body>
<table>

<tr>
	<td align="right"></td>
	<td></td>
	<td align="right"></td>
	<td>
					<select id="mouth">
					</select>	
					<select id="day">
					</select>	
	</td>
</tr>
</table>
	<script language="javascript" type="text/javascript">
    $(document).ready(function() {
		 $(".ff").msDropDown();
		$("#mouth").msDropDown()
		$("#day").msDropDown();
	}); 
	</script>
</body>
</html>
