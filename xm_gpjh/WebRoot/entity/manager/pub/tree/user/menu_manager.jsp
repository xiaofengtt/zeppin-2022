<script language="javascript" type="text/javascript"><!--
	//objTree	= new treemenu("","课程超市－课程列表","/entity/manager/pub/tree/js/tree/usericon.gif");
	// add_item 的参数意义依次为：该项编号，不能重复；父id，所属的上一层的编号；该项显示的内容；折叠时的图标；展开时的图标；网址或命令；指向的窗口

	add_item(1,0,"平台及信息操作","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(1001,1,"通知公告","/entity/manager/pub/tree/js/tree/userpower.gif","/entity/manager/pub/tree/js/tree/powericon.gif","","menu","/entity/information/peBulletinView.action");
			add_item(1002,1,"我的公文","/entity/manager/pub/tree/js/tree/userpower.gif","/entity/manager/pub/tree/js/tree/powericon.gif","","menu","/entity/information/peDocumentView.action");
			add_item(1003,1,"个人资料","/entity/manager/pub/tree/js/tree/userpower.gif","/entity/manager/pub/tree/js/tree/powericon.gif","","menu","/entity/manager/information/owner_information.html");
			add_item(1004,1,"修改密码","/entity/manager/pub/tree/js/tree/userpower.gif","/entity/manager/pub/tree/js/tree/powericon.gif","","menu","/entity/manager/information/input_password.html");
		    add_item(1005,1,"新闻管理","/entity/manager/pub/tree/js/tree/userpower.gif","/entity/manager/pub/tree/js/tree/powericon.gif","","menu","/entity/information/peInfoNews.action");
		    add_item(1006,1,"公告管理","/entity/manager/pub/tree/js/tree/userpower.gif","/entity/manager/pub/tree/js/tree/powericon.gif","","menu","/entity/information/peBulletin.action");
		    add_item(1007,1,"公文管理","/entity/manager/pub/tree/js/tree/userpower.gif","/entity/manager/pub/tree/js/tree/powericon.gif","","menu","/entity/information/peDocumentOutbox.action");
		    add_item(1008,1,"短信管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");	
				add_item(100801,1008,"系统短信点管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/information/peSystemSmsPoint.action");
				add_item(100802,1008,"发送短信","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/information/sms/new_sms.jsp");
				add_item(100803,1008,"审核短信","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/information/peSmsCheck.action");
				add_item(100804,1008,"短信列表","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/information/peSms.action");
				add_item(100805,1008,"短信统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/information/peSmsStatistic_selectTime.action");
			add_item(1009,1,"论坛管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/sso/forum/forum_forumManage.action");
			
	add_item(2,0,"基础数据管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			//add_item(2001,2,"学习中心管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(2001,2,"学生中心管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/peSite.action");
			add_item(2002,2,"招生渠道管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/peRecChannel.action");
			add_item(2003,2,"专业管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/peMajor.action");
			add_item(2004,2,"层次管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/peEdutype.action");			
			add_item(2005,2,"年级管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/peGrade.action");
			add_item(2006,2,"学期管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/basic/peSemester.action");
			
	add_item(3,0,"招生管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(3001,3,"招生设置","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(300101,3001,"招生考试批次管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/peRecruitplan.action");
				add_item(300102,3001,"招生计划管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/prRecPlanMajorEdutype.action");
				add_item(300103,3001,"招生简章","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/recruitJianzhang.action");
				add_item(300104,3001,"学分标准设置","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/setCreditA.action");
			add_item(3002,3,"报名信息管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(300200,3002,"报名信息查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/recruitStu.action?search=true");
				add_item(300201,3002,"报名信息录入","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/baoming_enter_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(300202,3002,"报名信息审核","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/info_proc_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(300203,3002,"教师资格审查","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/info_stu_teacher_check_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(300204,3002,"免试资格审查","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/info_stu_noexam_check_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(300205,3002,"照片管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/photo_upload_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(3003,3,"入学考试管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(300301,3003,"入学考试设置","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/recruit_exam_set_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(300302,3003,"入学考试管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/recruit_examroom_set_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(300303,3003,"入学考试成绩管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/recruit_examresult_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(3004,3,"录取管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(300402,3004,"录取","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/exam_recruit_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(300403,3004,"录取状态修改","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/recruitManage.action?search=true");
				add_item(300404,3004,"录取通知书打印","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/recruitNotification.action");
		    add_item(3005,3,"统计查询","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(300501,3005,"报名信息查询统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/recruit/baomingstat.jsp");
				add_item(300502,3005,"录取状态考生人数查询统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/recruit/recruitStatus.jsp");
				add_item(300503,3005,"免试查询统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/recruit/recruitNoExamStat.action");
				add_item(300504,3005,"入学测试成绩统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/recruit/score_batch_select.jsp","menu","/entity/manager/pub/loading.htm");
			
	add_item(4,0,"学籍管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(4001,4,"学生注册管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			    add_item(400101,4001,"预交费管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/studentStatus/pri_pay_menu.jsp","menu","/entity/manager/pub/loading.htm");
			    add_item(400102,4001,"注册管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/studentStatus/register_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(4002,4,"学生信息管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			    add_item(4002001,4002,"学生信息查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/peStudentInfo.action");
			    add_item(4002002,4002,"学生信息打印","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/peStudentInfoPrint.action");
			    add_item(4002003,4002,"模拟登陆","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/simulateStudentLogin.action");
			    add_item(4002004,4002,"评优管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/prStudentGoodApply.action");
	            add_item(4002005,4002,"违纪管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/prStudentOffence.action?search=true");
	        add_item(4003,4,"学籍变动管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			    add_item(400301,4003,"学籍变动","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/studentStatus/student_change_search_menu.jsp","menu","/entity/manager/pub/loading.htm");
			    add_item(400302,4003,"添加学籍变动","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/studentStatus/student_change_menu.jsp","menu","/entity/manager/pub/loading.htm");
			    add_item(400303,4003,"退学，开除学籍","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/studentStatus/student_expel_menu.jsp","menu","/entity/manager/pub/loading.htm");
//			    add_item(400304,4003,"退学?","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/studentStatus/student_expel_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(4004,4,"毕业管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			    add_item(400401,4004,"毕业批次管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/peGraduate.action");
			    add_item(400402,4004,"毕业名单管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/studentStatus/graduated_list_menu.jsp","menu","/entity/manager/pub/loading.htm");
			    add_item(400403,4004,"报表管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/studentStatus/report_menu.jsp","menu","/entity/manager/pub/loading.htm");
//			    add_item(400405,4004,"毕业照片","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/studentStatus/report_menu.jsp","menu","/entity/manager/pub/loading.htm");
			    add_item(400404,4004,"电子图像校对","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/peStudentPhoto.action");
			add_item(4005,4,"学位管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			    add_item(400501,4005,"可以申请学位名单","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/peCanApplyDegree.action");
			    add_item(400502,4005,"学位审核","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/peApplyDegree.action");
			    add_item(400503,4005,"学位名单","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/peStudentDegree.action");
			add_item(4006,4,"查询统计","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");    
			    add_item(400601,4006,"学生综合信息查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/peStudentStudyCount.action?search=true");
			    add_item(400602,4006,"在籍学生统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/pub/pre_statistic.jsp");
			    add_item(400603,4006,"毕业学位统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/studentStatus/prStudentDegree.action?search=true");
			    
	add_item(5,0,"教学教务管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(5001,5,"教学基础数据","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");    
				add_item(500101,5001,"课程管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/course_menu.jsp","menu","/entity/manager/pub/loading.htm");
				//add_item(500102,5001,"培养方案管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/program_menu.jsp","menu","/entity/teaching/program.action");
				add_item(500103,5001,"教学计划管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/teachplan_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(500104,5001,"教师管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/teacher_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(500105,5001,"教材管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/teachingMaterials_menu.jsp","menu","/entity/teaching/teachingMaterialsManager.action");
				add_item(500106,5001,"课件管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/courseware_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(5002,5,"选课管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");    
				add_item(500201,5002,"选课时间设置","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/electiveTimeManage.action");
				add_item(500202,5002,"选课管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/elective_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(500203,5002,"开课管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/open_course_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(5003,5,"教学过程管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");    
				add_item(500301,5003,"课程表制作","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/syllabus_menu.jsp","menu","/entity/teaching/syllabus.action");
				add_item(500302,5003,"教师教学统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/manager/teaching/teaching_stat.jsp");
				add_item(500303,5003,"作业统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/homeWorkStat.action");
				add_item(500304,5003,"核算课酬","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/countcoursereward_menu.jsp","menu","/entity/teaching/countcoursereward_turnto.action");
				add_item(500305,5003,"核算批改作业费","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/countcheckofworkfee_turnto.action");
			add_item(5004,5,"成绩管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");    
				add_item(500401,5004,"课程成绩管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/courseresult_manager_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(500402,5004,"统考成绩导入","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/unitExamScore_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(500403,5004,"学位英语成绩导入","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/degreeEnglishScore_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(5005,5,"毕业论文管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");    
				add_item(500501,5005,"毕业论文时间设置","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/paperTime.action");
				add_item(500502,5005,"查看论文题目","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/teacherTopic.action");
				add_item(500503,5005,"查看学生选题","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/studentTopic.action");
				add_item(500504,5005,"答辩管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/reply_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(500505,5005,"成绩管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/result_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(500506,5005,"论文申请统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/teaching/paperShenqingStat.action");
				add_item(500507,5005,"论文成绩统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/teaching/paper_stat_menu.jsp","menu","/entity/manager/pub/loading.htm");
	
	add_item(6,0,"考务管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(6001,6,"监巡考人员管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/supervisor_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(6002,6,"期末考试管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(60020101,6002,"期末考试时间设置","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/exam/peFinalExam.action");
				add_item(60020102,6002,"期末考试预约管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/finalexam_apply_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(60020103,6002,"考场安排","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/exam_no_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(60020104,6002,"成绩管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/exam_score_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(60020105,6002,"成绩复查管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/score_recheck_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(6003,6,"主干课程考试","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(600301,6003,"考试设置","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/main_course_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(600302,6003,"考试安排","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/exam/maincourse_examroom_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(600303,6003,"成绩录入","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/exam/prMainCourseScore.action");
				add_item(600304,6003,"成绩复核","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/exam/peMainCourseScoreRecheck.action");
			
	add_item(7,0,"财务管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			add_item(7001,7,"交费操作","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(700101,7001,"录入交费明细","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/fee/fee_enter_menu.jsp","menu","/entity/manager/pub/loading.htm");
				add_item(700101,7001,"上报交费批次","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/prFeeDetailForTakeIn.action");
				add_item(700101,7001,"交费批次审核","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/peFeeBathCheck.action");
			add_item(7002,7,"减免费用操作","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/fee/fee_reduce_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(7003,7,"学生退费","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/fee/fee_return_menu.jsp","menu","/entity/manager/pub/loading.htm");
			add_item(7004,7,"人工特殊费用操作","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/prFeeDetailSpecial.action");
			
			add_item(7007,7,"收费标准管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(700701,7007,"收费标准设置","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/feeStandardManager.action");
				add_item(700702,7007,"个人标准设置","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/listStudentForFeeSet.action?search=true");
			
			add_item(7005,7,"财务管理查询统计","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
				add_item(700501,7005,"交费批次查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/peFeeBathQuery.action");
				add_item(700502,7005,"交费人数统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/feeTotalCount_turntoStat.action");
				add_item(700503,7005,"学生个人账户查询","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/studentAccount_turntoSearch.action");
				add_item(700504,7005,"学生所有费用明细","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/prFeeDetailByStudent.action?search=true");
			add_item(7006,7,"学费分成统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","","menu","/entity/fee/peFeeFencheng.action");
		
		
	//	add_item(9,0,"公选课管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/query/query_manage_menu.jsp","menu","/entity/manager/pub/loading.htm");
	//add_item(8,0,"公选课管理","/entity/manager/pub/tree/js/tree/close.gif","/entity/manager/pub/tree/js/tree/open.gif","#","");
			//add_item(8001,8,"学生管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/publiccourse/pcstudent_menu.jsp","menu","/entity/publiccourse/pcstudent.action?search=true");
			//add_item(8002,8,"教师管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/publiccourse/pcteacher_menu.jsp","menu","/entity/publiccourse/pcteacher.action");
			//add_item(8003,8,"课程管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/publiccourse/pccourse_menu.jsp","menu","/entity/publiccourse/pccourse.action");
			//add_item(8004,8,"考试管理","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/publiccourse/pcexam_menu.jsp","menu","/entity/publiccourse/pcexam.action");
			//add_item(8005,8,"查询统计","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/pub/tree/js/tree/parenticon.gif","/entity/manager/publiccourse/pcstatistics_menu.jsp","menu","/entity/publiccourse/pcstatistics.action");
	document.write(menu(0));
--></script>
