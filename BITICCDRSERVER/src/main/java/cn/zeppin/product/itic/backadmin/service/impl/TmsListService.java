/**
 * 
 */
package cn.zeppin.product.itic.backadmin.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.zeppin.product.itic.backadmin.dao.api.ITmsListDAO;
import cn.zeppin.product.itic.backadmin.dao.api.ITnumberRelationDAO;
import cn.zeppin.product.itic.backadmin.service.api.ITmsListService;
import cn.zeppin.product.itic.backadmin.service.runable.RunablePushDownload;
import cn.zeppin.product.itic.core.entity.TmsList;
import cn.zeppin.product.itic.core.entity.TmsList.TmsListStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelation;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.service.base.BaseService;
import cn.zeppin.product.utility.CdrUtlity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author L
 *
 */
@Service
public class TmsListService extends BaseService implements ITmsListService {
	
	private static Logger logger = LoggerFactory.getLogger(TmsListService.class);
	
	@Autowired
	private ITmsListDAO  tmsListDAO;
	
	@Autowired
	private ITnumberRelationDAO tnumberRelationDAO;
	
	@Autowired
	private RunablePushDownload runablePushDownload;

	
	@Override
	public TmsList get(Integer uuid) {
		return tmsListDAO.get(uuid);
	}
	
	/**
	 * 向表中插入一条Controller数据
	 */
	@Override
	public TmsList insert(TmsList area) {
		return tmsListDAO.insert(area);
	}

	@Override
	public TmsList delete(TmsList area) {
		return tmsListDAO.delete(area);
	}

	@Override
	public TmsList update(TmsList area) {
		return tmsListDAO.update(area);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return tmsListDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return tmsListDAO.getCount(inputParams);
	}

	@Override
	public List<TmsList> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts) {
		// TODO Auto-generated method stub
		return tmsListDAO.getListForPage(inputParams, pageNum, pageSize, sorts);
	}

	@Override
	public void noticeProcess(Map<String, Object> resultMap, JSONObject Jsonmap, Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		String ipAdd = (String) map.get("IpAddress");
		String date = Utlity.getCurrentTimeStr("yyyyMMddHHmmss");// 当前时间戳
		String shortdate = Utlity.getCurrentTimeStr("yyyyMMdd");// 当前时间戳
		logger.info("time: "+date);
		Map<String, Object> headerMap = JSONObject.parseObject(resultMap.get("header").toString());
		Map<String, Object> bodyMap = JSONObject.parseObject(resultMap.get("body").toString());

		//处理信息入库
		//根据callId 查询数据库已存在的记录 进行后续处理
		TmsList tl = new TmsList();
		String callId = bodyMap.get("callId") == null ? "" : bodyMap.get("callId").toString();
		boolean flag = false;
		if(!Utlity.checkStringNull(callId)) {
			Map<String, String> inputParams = new HashMap<>();
			inputParams.put("callid", callId);
			
			List<Entity> list = this.tmsListDAO.getListForPage(inputParams, -1, -1, null, TmsList.class);
			if(list != null && !list.isEmpty()) {
				tl = (TmsList)list.get(0);
				flag = true;
			}
		}
		
		tl.setServicename(headerMap.get("serviceName") == null ? "" : headerMap.get("serviceName").toString());
		tl.setMessageid(bodyMap.get("messageId") == null ? "" : bodyMap.get("messageId").toString());
		tl.setServicekey(bodyMap.get("serviceKey") == null ? "" : bodyMap.get("serviceKey").toString());
		tl.setCallid(callId);
		tl.setCallernum(bodyMap.get("callerNum") == null ? "" : bodyMap.get("callerNum").toString());
		tl.setCallednum(bodyMap.get("calledNum") == null ? "" : bodyMap.get("calledNum").toString());
		tl.setMiddlenumber(bodyMap.get("middleNumber") == null ? "" : bodyMap.get("middleNumber").toString());
		tl.setCallerdisplaynumber(bodyMap.get("callerDisplayNumber") == null ? "" : bodyMap.get("callerDisplayNumber").toString());
		tl.setCalleddisplaynumber(bodyMap.get("calledDisplayNumber") == null ? "" : bodyMap.get("calledDisplayNumber").toString());
		tl.setCallerstreamno(bodyMap.get("callerStreamNo") == null ? "" : bodyMap.get("callerStreamNo").toString());
		tl.setStartcallertime(bodyMap.get("startCallerTime") == null ? "" : bodyMap.get("startCallerTime").toString());
		tl.setAbstartcalltime(bodyMap.get("abStartCallTime") == null ? "" : bodyMap.get("abStartCallTime").toString());
		tl.setAbstopcalltime(bodyMap.get("abStopCallTime") == null ? "" : bodyMap.get("abStopCallTime").toString());
		tl.setCallerduration(bodyMap.get("callerDuration") == null ? "" : bodyMap.get("callerDuration").toString());
		tl.setCallercost(bodyMap.get("callerCost") == null ? "" : bodyMap.get("callerCost").toString());
		tl.setCallerrelcause(bodyMap.get("callerRelCause") == null ? "" : bodyMap.get("callerRelCause").toString());
		tl.setCallerorirescode(bodyMap.get("callerOriRescode") == null ? "" : bodyMap.get("callerOriRescode").toString());
		tl.setCalledstreamno(bodyMap.get("calledStreamNo") == null ? "" : bodyMap.get("calledStreamNo").toString());
		tl.setStartcalledtime(bodyMap.get("startCalledTime") == null ? "" : bodyMap.get("startCalledTime").toString());
		tl.setCalledduration(bodyMap.get("calledDuration") == null ? "" : bodyMap.get("calledDuration").toString());
		tl.setCalledcost(bodyMap.get("calledCost") == null ? "" : bodyMap.get("calledCost").toString());
		tl.setCalledrelcause(bodyMap.get("calledRelCause") == null ? "" : bodyMap.get("calledRelCause").toString());
		tl.setCalledorirescode(bodyMap.get("calledOriRescode") == null ? "" : bodyMap.get("calledOriRescode").toString());
		tl.setSrfmsgid(bodyMap.get("srfmsgid") == null ? "" : bodyMap.get("srfmsgid").toString());
		tl.setChargenumber(bodyMap.get("chargeNumber") == null ? "" : bodyMap.get("chargeNumber").toString());
		tl.setCallerrelreason(bodyMap.get("callerRelReason") == null ? "" : bodyMap.get("callerRelReason").toString());
		tl.setCalledrelreason(bodyMap.get("calledRelReason") == null ? "" : bodyMap.get("calledRelReason").toString());
		tl.setMsserver(bodyMap.get("msServer") == null ? "" : bodyMap.get("msServer").toString());
		
		tl.setMiddlestarttime(bodyMap.get("middleStartTime") == null ? "" : bodyMap.get("middleStartTime").toString());
		tl.setMiddlecalltime(bodyMap.get("middleCallTime") == null ? "" : bodyMap.get("middleCallTime").toString());
		tl.setDuration(bodyMap.get("duration") == null ? "" : bodyMap.get("duration").toString());
		tl.setCostcount(bodyMap.get("costCount") == null ? "" : bodyMap.get("costCount").toString());
		
		tl.setStatus(TmsListStatus.NORMAL);
		tl.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		//查询对应关系表 找到客户和经理的对应关系 并存储在话单记录里
		Map<String, String> inputParams = new HashMap<String, String>();
		if (!Utlity.checkStringNull(tl.getCallernum())) {
			inputParams.put("toMobile", tl.getCallernum());
		}
		if (!Utlity.checkStringNull(tl.getCalleddisplaynumber())) {
			inputParams.put("toTel", tl.getCalleddisplaynumber());
		}
		if (!Utlity.checkStringNull(tl.getCallednum())) {
			inputParams.put("tcPhone", tl.getCallednum());
		}
		if (!Utlity.checkStringNull(tl.getCallerdisplaynumber())) {
			inputParams.put("tcTel", tl.getCallerdisplaynumber());
		}

		List<Entity> arealist = tnumberRelationDAO.getListForPage(inputParams, -1, -1, null, TnumberRelation.class);
		if(arealist != null && !arealist.isEmpty()) {
			TnumberRelation tr = (TnumberRelation)arealist.get(0);
			tl.setFkTcustomers(tr.getFkTcustomers());
			tl.setFkToperator(tr.getFkToperator());
		}
		if(!flag) {
			tl = this.tmsListDAO.insert(tl);
		} else {
			tl = this.tmsListDAO.update(tl);
		}
		
		// 处理接收参数 -- 并下载通话文件
		logger.info("header:" + headerMap.toString());
		logger.info("body:" + bodyMap.toString());
		String srfmsgid = bodyMap.get("srfmsgid").toString(); // 通话录音路径
		logger.info("srfmsgid:" + srfmsgid);
		String writeStr1 = "Tite: PushRecords\r\n";
		writeStr1 += "time: " + date + "\r\n";
		writeStr1 += "ipAddress: " + ipAdd + "\r\n";
		writeStr1 += "=======================\r\n";
		writeStr1 += "message:\r\n";
		writeStr1 += Jsonmap.toString() + "\r\n";

		// PushRecords-*.txt(每个通话产生一个文件)
		File saveDir1 = new File(CdrUtlity.ResolutionPushpath);// 文件保存位置
		if (!saveDir1.exists()) {
			saveDir1.mkdirs();
		}
		String filename1 = "PushRecords-" + date + ".txt";// 文件名
		String filepath1 = saveDir1 + File.separator + filename1;// 文件路径
		logger.info("============Start writing to PushRecords-*.txt!============");
		FileOutputStream out1 = new FileOutputStream(filepath1, true);// 输出文件路径
		out1.write(writeStr1.getBytes());
		out1.close();
		logger.info("============Document is written!============");
		logger.info("PushRecords: " + filename1);
		logger.info("FilePath: " + filepath1);

		// Srfmsgid-*.txt(每天一个文件,每次通话更新文件)
		if (!srfmsgid.equals("")) {
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
		
		
//		RunablePushDownload R = new RunablePushDownload(srfmsgid,tl);
//		R.start();
		this.runablePushDownload.instance(srfmsgid, tl);
		new Thread(this.runablePushDownload).start();
	}

	@Override
	public List<Entity> getListForSearchPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize,
			String sorts, Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return this.tmsListDAO.getListForSearchPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCountForSearchPage(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return this.tmsListDAO.getCountForSearchPage(inputParams);
	}

}
