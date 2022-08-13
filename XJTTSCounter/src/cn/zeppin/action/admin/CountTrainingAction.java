package cn.zeppin.action.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.action.baseAction;
import cn.zeppin.service.ICountTeacherYearService;

@Component
public class CountTrainingAction extends baseAction{
	
	private static final long serialVersionUID = 1L;
	static Logger logger = LogManager.getLogger(CountTrainingAction.class);
	
	@Autowired
	private ICountTeacherYearService countTeacherYearService;
	
	
	@Scheduled(cron="0 0 0 1 * ?")
	public void calculate() {
		this.countTeacherYearService.updateDate();
	}
}
