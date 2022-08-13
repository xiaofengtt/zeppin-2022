<script language="javascript" type="text/javascript"><!--
	//objTree	= new treemenu("","课程超市－课程列表","/entity/manager/pub/tree/js/tree/usericon.gif");
	// add_item 的参数意义依次为：该项编号，不能重复；父id，所属的上一层的编号；该项显示的内容；折叠时的图标；展开时的图标；网址或命令；指向的窗口

	add_item(1,0,"平台操作","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
		add_item(1001,1,"通知","/entity/manager/pub/tree/js/tree/userpower.gif","/entity/manager/pub/tree/js/tree/powericon.gif","","menu","/entity/submanager/notice/notice_list.jsp");
		
		
		add_item(1002,1,"修改密码","/entity/manager/pub/tree/js/tree/userpower.gif","/entity/manager/pub/tree/js/tree/powericon.gif","","menu","/entity/submanager/pwd/change_pwd.jsp");
	
		//add_item(1003,1,"信息服务","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");	
			//add_item(100301,1003,"新闻管理M4","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/info/info_menu.jsp","menu","/entity/manager/info/info_news_list.jsp");
			//add_item(100302,1003,"通知管理M4","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/info/info_news_add.jsp?isNotice=1");
			//add_item(100305,1003,"短信管理M4","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/sms/menu/menu1.jsp","menu","/entity/manager/sms/sms_check.jsp");
			//add_item(100307,1003,"站内消息+","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/sms/menu/menu1.jsp","menu","/entity/manager/sms/sms_check.jsp");
			//add_item(100303,1003,"论坛管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/info/mainforum.jsp");
			//add_item(100304,1003,"流量管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/flux/menu/menu1.jsp","menu","/entity/manager/flux/flux_statistic.jsp");
		//add_item(1004,1,"投票管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/vote/menu/menu.jsp","menu","/entity/manager/vote/votepaper_add.jsp");
		//add_item(1005,1,"上传管理X","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/upload_set/menu/menu.jsp","menu","/entity/manager/upload_set/student_upload_set.jsp");
			
		//add_item(9002,9,"留言板管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/leaveword/menu/menu.jsp","menu","/entity/manager/leaveword/leavewordList.jsp");		
/*		
		add_item(2,0,"基本信息","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
	//add_item(3,0,"基础数据","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(2001,2,"学习中心管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/site.action");
			add_item(2002,2,"专业管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/major.action");
			add_item(2003,2,"层次管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/edutype.action");			
			add_item(2004,2,"年级管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/grade.action");
			add_item(2005,2,"学期管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/semester.action");
*/
		add_item(3,0,"招生管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(3001,3,"招生设置","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(300101,3001,"招生批次查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/peRecruitplan.action");
				add_item(300102,3001,"招生计划查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/planCSubManager.action");
//ZLB 20080807				add_item(300103,3001,"招生计划统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/planB_count.action");
			add_item(3002,3,"报名信息管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(300201,3002,"学生信息录入","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/baoming_enter_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(300202,3002,"学生信息处理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/info_proc_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(300203,3002,"免试资格查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/recStudentNoexam.action");
				add_item(300204,3002,"学生照片管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/photo_upload_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(4001,3,"入学考试管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(400101,4001,"计划科目时间查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/recruit_exam_set_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(400102,4001,"考场分配查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/recruit_examroom_set_menu.jsp","menu","/entity/manager/pub/loading.htm");
				//add_item(400103,4001,"试卷袋数统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/recruit/exambagstatistic.jsp");
				add_item(400104,4001,"打印管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/recruit_exam_print_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(3003,3,"考试成绩管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(300301,3003,"查询学生成绩","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/score.action");
				//add_item(300302,3003,"批量上传成绩","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/recruit/score_batch_select.jsp");
			add_item(3004,3,"录取管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				//add_item(300401,3004,"免试生录取","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/recruitmanage_noExamRecSearch.action");
				//add_item(300402,3004,"考试生录取","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/exam_recruit_menu.jsp","menu","/entity/recruit/recruitscoreline.action");
				//add_item(300403,3004,"进修生录取","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/recruitmanage_turnTojxSearch.action");
				add_item(300404,3004,"录取情况查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/baobiao_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
				//add_item(300405,3005,"为录取新生发布入学须知","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/baobiao_manage_menu.jsp","menu","/entity/recruit/recDetailStat.action");
				

		add_item(4,0,"考务管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			//add_item(4005,4,"考试管理M4?","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			//	add_item(400501,4005,"考试数据","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/exambatch_list.jsp","menu","/entity/exam/examNo.action");
			//	add_item(400502,4005,"考试安排","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/menu/menu2.jsp","menu","/entity/manager/exam/auto_examroom.jsp");
				
			//add_item(4002,4,"成绩管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(40020101,4,"平时成绩录入","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/score_input.jsp","menu","/entity/exam/scoreManualInput_search.action");
				add_item(40020102,4,"查看成绩表x","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/score_view.jsp","menu","/entity/manager/pub/loading.htm");
				//add_item(40020103,4002,"总评生成发布x","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/generalScore_issue.jsp","menu","/entity/exam/generalScoreIssue_courseSearch.action");
				add_item(400202,4,"成绩单","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/scoreReport_card_menu.jsp","menu","/entity/manager/pub/loading.htm");
			//	add_item(400203,4002,"学位英语成绩导入","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/exam/degreeEnglishScore_batch_input.jsp");
			//	add_item(400204,4002,"学位英语成绩查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/exam/degreeEnglishScore_search.jsp");
				add_item(400205,4,"成绩统计+","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/exam/scoreTotalStat_search.action");
		//	add_item(4003,4,"期末考试","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
		//		add_item(400301,4003,"考试计划","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/termend_plan_menu.jsp","menu","/entity/exam/examBatch.action");
		//		add_item(400302,4003,"考场分配","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/termend_roomallot_menu.jsp","menu","/entity/exam/prExamplanStu_form.action");
		//		add_item(400303,4003,"成绩管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/termend_scoremanage_menu.jsp","menu","/entity/manager/pub/loading.htm");
	/*		add_item(4004,4,"重考管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(400401,4004,"重考计划","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam1/retest_plan_menu.jsp","menu","/entity/manager/exam1/retest_pici_manage.jsp");
				add_item(400402,4004,"预约报名","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam1/retest_reserve_menu.jsp","menu","/entity/manager/exam1/retest_add_student.jsp");
				add_item(400403,4004,"考场分配","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam1/retest_roomallot_menu.jsp","menu","/entity/manager/exam1/retest_student_list.jsp");
				add_item(40040301,4004,"x+考场分配","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam1/retest_roomallot_menu2.jsp","menu","/entity/manager/exam1/retest_roomallot_auto.jsp");
				add_item(400404,4004,"成绩管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam1/retest_scoremanage_menu.jsp","menu","/entity/manager/exam1/retest_score_manage.jsp");
	*/	add_item(5,0,"教务管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(5001,5,"学生列表查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/student/student_manager_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(5002,5,"学籍异动查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/student/change_school_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(5005,5,"毕业管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/student/graduated_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(5006,5,"学位管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/student/degree_search.jsp");
			add_item(5007,5,"教学跟踪","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/student/study/study_info_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(5008,5,"毕业证学位证信息","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/student/graduatepaper_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
		add_item(6,0,"教学管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			//add_item(6007,6,"培养方案","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/program.action");
			//add_item(6007,6,"教学计划查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/teachplan.action");
			add_item(6007,6,"执行计划","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/executeplan.action");
			add_item(6007,6,"开课列表","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/opencourse_manager_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(6007,6,"选课管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/elective_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
			//add_item(6003,6,"课程管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/course.action");
			//add_item(6004,6,"课件管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/courseware_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
			//add_item(6005,6,"教师管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/peTeacher.action");
			//add_item(6006,6,"毕业论文管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/graduatedesign_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
		add_item(7,0,"教材管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(700101,7,"教材信息管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/textbook/book_info_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(7001,7,"光盘信息管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/textbook/disk_info_menu.jsp","menu","/entity/manager/pub/loading.htm");
			//	add_item(7002,7,"教材征订管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/book1/book_order_menu.jsp","menu","/entity/manager/book1/book_zhengding_search.jsp");
		add_item(8,0,"财务管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
		//add_item(8001,8,"入学考试资料费","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/fee1/enter_examfee_menu.jsp","menu","/entity/manager/pub/loading.htm");
				//add_item(800101,8001,"资料费统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/listGrade.action");
			//add_item(8002,8,"收费标准管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/fee1/tuition_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
				//add_item(800201,8002,"收费标准设置","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/feeStandardManager.action");
				//add_item(800202,8002,"个人标准设置","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/listStudentForFeeSet.action");
			add_item(8003,8,"交费明细管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/fee1/tuition_collect_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(800301,8003,"交费明细查看","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/viewFeeDetail.action");
				add_item(800302,8003,"批量导入明细","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/fee/feeDetail_batch_input.jsp");
				add_item(800303,8003,"上报交费批次","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/listFeeDetailForTakeIn.action");
				add_item(800304,8003,"查看交费批次","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/auditFeeBatch.action");
			//add_item(8004,8,"退费管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/listFeeReturnStudent.action");
			add_item(8005,8,"费用查询统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/fee1/quitfee_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(800501,8005,"学生账户费用查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/fee/studentFee_search.jsp");
				add_item(800502,8005,"学生交消费明细","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/viewStudentFeeDetail.action");
				//add_item(800503,8005,"学生账户费用统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/peRecruitplan.action");
				//add_item(800504,8005,"统计报表","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/peRecruitplan.action");
			//add_item(8006,8,"结算和分成XX","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/fee1/jiesuan_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
		add_item(9,0,"查询管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/query/query_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
		add_item(10,0,"统计信息","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/stat/stat_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
		add_item(11,0,"邮箱","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/pub/emailLogin.jsp");
		//add_item(12,0,"学生模拟登陆","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/student1/enter_student_search.jsp");
		//add_item(13,0,"教师模拟登陆","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/teacher1/enter_teacher_search.jsp");

		add_item(14,0,"本学习中心信息","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/site_siteInfo.action");

							
	document.write(menu(0));
--></script>
