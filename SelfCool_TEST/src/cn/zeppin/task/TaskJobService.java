package cn.zeppin.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("taskJob")
public class TaskJobService {
	private TaskJobDAO taskJobDAO;
	

	public TaskJobDAO getTaskJobDAO() {
		return taskJobDAO;
	}

	public void setTaskJobDAO(TaskJobDAO taskJobDAO) {
		this.taskJobDAO = taskJobDAO;
	}

	@Scheduled(initialDelay = 300000, fixedDelay = 900000)
	public void calculate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println(df.format(new Date()) + "开始执行定时任务");
		
//		/**
//		 * 计算用户做题的相关数据，更新到ssouser_item_count表中
//		 */
//		System.out.println("计算用户做题的相关数据，更新到ssouser_item_count表中");
//		this.getTaskJobDAO().calculateUserTestItemData();

		
		
		/**
		 * 按knowledge统计试题数，更新到knowledge_itemcount表中
		 */
		System.out.println("按knowledge统计试题数，更新到knowledge_itemcount表中");
		this.getTaskJobDAO().calculateAllKnowledgeItemData();
		
		/**
		 * 按itemType统计试题数，更新到subject_item_type表中
		 */
		System.out.println("按itemType统计试题数，更新到subject_item_type表中");
		this.getTaskJobDAO().calculateSubjectItemTypeData();
		
		/**
		 * 计算用户学科备考倒计时天数，更新到user_subject表中
		 */
		System.out.println("计算用户学科备考倒计时天数，更新到user_subject表中");
		this.getTaskJobDAO().calculateUserSubjectData();

		System.out.println(df.format(new Date()) + "定时任务执行结束");
	}

}
