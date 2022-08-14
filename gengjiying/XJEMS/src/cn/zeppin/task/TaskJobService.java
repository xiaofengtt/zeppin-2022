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

	@Scheduled(initialDelay = 10000, fixedDelay = 30 * 60000)
	// @Scheduled(cron="0 5 0/24 * * ? ")//每天执行一次
	public void calculate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println(df.format(new Date()) + "开始执行定时任务");

		/**
		 * 计算当年内，教师监考次数
		 */
		System.out.println("计算当年内，教师监考次数");
		this.getTaskJobDAO().calculateUserInvigilationData();
		this.getTaskJobDAO().calculateUserInvigilationCount();

		/**
		 * 查看考试截止时间，超过后，自动结束考试
		 */
		System.out.println("查看考试截止时间，超过后，自动结束考试");
		this.getTaskJobDAO().calculateExamEndtimeData();

		/**
		 * 计算禁用时间是否到期，到期后自动解禁
		 */
		this.getTaskJobDAO().calculateTeacherReleaseTime();
	}

}
