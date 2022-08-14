package cn.zeppin.product.itic.backadmin.service.autotask;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zeppin.product.itic.backadmin.dao.api.ITnumberRelationDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITnumberRelationLogDAO;
import cn.zeppin.product.itic.backadmin.dao.api.IToperatorMobileDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITmsListService;
import cn.zeppin.product.itic.backadmin.service.runable.RunablePushDownload;
import cn.zeppin.product.itic.core.entity.TmsList;
import cn.zeppin.product.itic.core.entity.TmsList.TmsListStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelation;
import cn.zeppin.product.itic.core.entity.TnumberRelation.TnumberRelationProcessStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelation.TnumberRelationStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog.TnumberRelationLogStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog.TnumberRelationLogType;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.utility.CdrUtlity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;


@Component
public class DailyTask {
	private static Logger logger = LoggerFactory.getLogger(DailyTask.class);
	
	@Autowired
	private ITnumberRelationDAO  tnumberRelationDAO;
	
	@Autowired
	private IToperatorMobileDAO  toperatorMobileDAO;
	
	@Autowired
	private ITmsListService tmsListService;
	
	@Autowired
	private ITnumberRelationLogDAO  tnumberRelationLogDAO;
	
//	@Autowired
//	private RunablePushDownload runablePushDownload;
	
	 public static void main(String args[]) { 
		 
		 	System.out.println("updateDailyRelation begin!"); 
		 	new DailyTask().updateDailyRelation();
	        System.out.println("updateDailyRelation end!"); 
	    } 
	
	@Scheduled(cron="10 0 6  * * ? ")
	public void updateDailyRelation() {
		System.out.println("updateDailyRelation begin!"); 
		Map<String, String> params = new HashMap<String, String>();
		params.put("status", "normal");
		
		List<Entity> trList = this.tnumberRelationLogDAO.getListForPage(params, -1, -1, "ID-ASC", TnumberRelationLog.class);
		if(trList == null || trList.isEmpty()) {
			//数据预处理
			logger.info("============始于"+ Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis())) +"============");
			this.tnumberRelationDAO.dailyDelete();
			this.tnumberRelationDAO.dailyDeleteOther();
			this.toperatorMobileDAO.dailyDelete();
			
//			this.toperatorMobileDAO.dailyInsert();
			this.tnumberRelationDAO.dailyInsert();
			
			syncRelation();
			
			logger.info("============终于"+ Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis())) +"============");
		}
	}
	
//	@Scheduled(cron="10 0/2 *  * * ? ")
	public void syncRelation(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("status", "normal");
		
		List<Entity> trList = this.tnumberRelationLogDAO.getListForPage(params, -1, -1, "ID-ASC", TnumberRelationLog.class);
		if(!trList.isEmpty()){
			for(Entity entity : trList){
				TnumberRelationLog trl = (TnumberRelationLog) entity;
				try {
					if(trl != null && trl.getFkTnumberRelation() != null) {
						TnumberRelation tr = this.tnumberRelationDAO.get(trl.getFkTnumberRelation());
						if(TnumberRelationLogType.DELETE.equals(trl.getType())) {//同步删除并清空tr数据
							Map<String, Object> result = CdrUtlity.setBindNum(TnumberRelationProcessStatus.DELETED, tr);
							Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
							String status = map.get("STATE_CODE").toString();
							String message = map.get("STATE_NAME").toString();
							String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();
							if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
								tr.setFkTcustomers(null);
								tr.setTcPhone(null);
								tr.setStatus(TnumberRelationStatus.EMPTY);
								trl.setStatus(TnumberRelationLogStatus.DISABLED);
							} else {
								tr.setFkTcustomers(null);
								tr.setTcPhone(null);
								tr.setStatus(TnumberRelationStatus.EMPTY);
								trl.setStatus(TnumberRelationLogStatus.DISABLED);
								logger.info("操作失败！" + message + "," + remark);
							}
							this.tnumberRelationDAO.update(tr);
						} else if (TnumberRelationLogType.INSERT.equals(trl.getType())) {
							tr.setFkTcustomers(trl.getFkTcustomers());
							tr.setFkToperator(trl.getFkToperator());
							tr.setTcTel(trl.getTcTel());
							tr.setTcPhone(trl.getTcPhone());
							tr.setStatus(TnumberRelationStatus.NORMAL);
							Map<String, Object> result = CdrUtlity.setBindNum(TnumberRelationProcessStatus.ADD, tr);
							Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
							String status = map.get("STATE_CODE").toString();
							String message = map.get("STATE_NAME").toString();
							String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();
							if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
								this.tnumberRelationDAO.update(tr);
								trl.setStatus(TnumberRelationLogStatus.DISABLED);
							} else {
								logger.info("操作失败！" + message + "," + remark);
							}
							trl.setStatus(TnumberRelationLogStatus.DISABLED);
						}
					}
					this.tnumberRelationLogDAO.update(trl);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 定时下载话单音频文件
	 */
//	@Scheduled(cron="0 0/5 *  * * ? ")
	public void updateDownloadFile() {
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("realpath", "1");
			params.put("status", TmsListStatus.NORMAL);
			String date = Utlity.getCurrentTimeStr("yyyyMMddHHmmss");// 当前时间戳
			String shortdate = Utlity.getCurrentTimeStr("yyyyMMdd");// 当前时间戳
			logger.info("time: "+date);
			List<Entity> msList = this.tmsListService.getListForPage(params, -1, -1, null, TmsList.class);
			if(msList != null && !msList.isEmpty()) {
				for(Entity e : msList) {
					TmsList ms = (TmsList)e;
					String srfmsgid = ms.getSrfmsgid(); // 通话录音路径
					logger.info("srfmsgid:" + srfmsgid);
					// Srfmsgid-*.txt(每天一个文件,每次通话更新文件)
					if (!Utlity.checkStringNull(srfmsgid)) {
						File saveDir2 = new File(CdrUtlity.Srfmsgidpath);// 文件保存位置
						if (!saveDir2.exists()) {
							saveDir2.mkdirs();
						}
						String writeStr2 = srfmsgid + ",\r\n";
						String filename2 = "Srfmsgid-" + shortdate + ".txt";
						String filepath2 = saveDir2 + File.separator + filename2;
						logger.info("============Start writing to Srfmsgid-*.txt!============");
						FileOutputStream out2 = new FileOutputStream(filepath2, true);
						out2.write(writeStr2.getBytes());
						out2.close();
						logger.info("============Document is written!============");
						logger.info("Srfmsgid: " + filename2);
						logger.info("FilePath: " + filepath2);
					}

//					this.runablePushDownload.instance(srfmsgid, ms);
//					new Thread(this.runablePushDownload).start();
					
					RunablePushDownload R = new RunablePushDownload(srfmsgid,this.tmsListService,ms);
					R.start();
					
				
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("文件下载异常！");
			e.printStackTrace();
		}

	}
}