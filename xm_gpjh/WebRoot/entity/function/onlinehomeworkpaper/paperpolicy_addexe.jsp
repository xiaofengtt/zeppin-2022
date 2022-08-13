<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "com.whaty.platform.*"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.paper.*,com.whaty.platform.test.question.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ page import="com.whaty.platform.interaction.*,com.whaty.platform.util.*"%>
<jsp:directive.page import="java.sql.Connection"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<%@ include file="../pub/priv.jsp"%>
<%
	try {
		InteractionFactory interactionFactory = InteractionFactory.getInstance();
		InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);	
		TestManage testManage = interactionManage.creatTestManage();
		int paperId = 0;
		String title = (String)session.getAttribute("paper_title");
		String creatUser = user.getName();
		String status = (String)session.getAttribute("paper_status");
		String note = (String)session.getAttribute("paper_note");
		String startDate = (String)session.getAttribute("paper_startDate");
		String endDate = (String)session.getAttribute("paper_endDate");
		int id = 0;
		boolean flag = true;
		boolean flagDanxuan = true;
		boolean flagDuoxuan = true;
		boolean flagPanduan = true;
		boolean flagWenda = true;
		try {
			paperId = testManage.addHomeworkPaper(title,creatUser,status,note,null,startDate,endDate,"1",teachclass_id);
		} catch (Exception e1) {
			flag = false;
		}
		if (flag) {
			session.setAttribute("paper_id",paperId+"");
			try {
				id = testManage.addPaperPolicy(request,session);

				Map paperQuestions = new HashMap();
				paperQuestions = testManage.getQuestionsByPaperPolicy(id+"");
				List danxuanList = (List)paperQuestions.get("DANXUAN");
				if(request.getParameter("DANXUAN_num")!=null && danxuanList!=null) {
					int danxuannum = Integer.parseInt(request.getParameter("DANXUAN_num"));
					if(danxuanList!=null && danxuannum > danxuanList.size()) {
						flagDanxuan = false;
						throw new Exception();
					}
				}
				
				List duoxuanList = (List)paperQuestions.get("DUOXUAN");
				if(request.getParameter("DUOXUAN_num")!=null && duoxuanList!=null) {
					int duoxuannum = Integer.parseInt(request.getParameter("DUOXUAN_num"));
					if(duoxuanList!=null && duoxuannum > duoxuanList.size()) {
						flagDuoxuan = false;
						throw new Exception();
					}
				}
				
				List panduanList = (List)paperQuestions.get("PANDUAN");
				if(request.getParameter("PANDUAN_num")!=null && panduanList!=null) {
					int panduannum = Integer.parseInt(request.getParameter("PANDUAN_num"));
					if(panduanList != null && panduannum > panduanList.size()) {
						flagPanduan = false;
						throw new Exception();
					}
				}
				
				List wendaList = (List)paperQuestions.get("WENDA");
				if(request.getParameter("WENDA_num")!=null && wendaList!=null) {
					int wendanum = Integer.parseInt(request.getParameter("WENDA_num"));
					if(wendaList != null && wendanum > wendaList.size()) {
						flagWenda = false;
						throw new Exception();
					}
				}
			} catch(Exception e1) {
				testManage.deleteHomeworkPaper(paperId+"");
				flag = false;
			}
		}
		if(flag) {
%>
<script type="text/javascript">
	alert("添加成功!")
	location.href = "homeworkpaper_list.jsp";
</script>
<%
		} else if(!flagDanxuan){
%>
<script type="text/javascript">
	alert("题库中没有足够符合条件单选题,添加失败!");
	window.history.back();
</script>
<%		
		} else if(!flagDuoxuan) {
		%>
<script type="text/javascript">
	alert("题库中没有足够符合条件多选题,添加失败!");
	window.history.back();
</script>
<%
		} else if(!flagPanduan) {
		%>
<script type="text/javascript">
	alert("题库中没有足够符合条件判断题,添加失败!");
	window.history.back();
</script>
<%		} else if (!flagWenda) {
%>
<script type="text/javascript">
	alert("题库中没有足够符合条件问答题,添加失败!");
	window.history.back();
</script>
<%		}
	} catch (Exception e) {
		out.print(e.toString());
		e.printStackTrace();
	}
%>