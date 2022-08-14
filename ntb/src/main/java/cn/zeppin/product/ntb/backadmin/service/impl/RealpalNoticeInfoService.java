/**
 * 
 */
package cn.zeppin.product.ntb.backadmin.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IRealpalNoticeInfoDAO;
import cn.zeppin.product.ntb.backadmin.service.api.IRealpalNoticeInfoService;
import cn.zeppin.product.ntb.core.entity.RealpalNoticeInfo;
import cn.zeppin.product.ntb.core.entity.RealpalNoticeInfo.RealpalNoticeInfoPayType;
import cn.zeppin.product.ntb.core.entity.RealpalNoticeInfo.RealpalNoticeInfoStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */
@Service
public class RealpalNoticeInfoService extends BaseService implements IRealpalNoticeInfoService {

	@Autowired
	private IRealpalNoticeInfoDAO realpalNoticeInfoDAO;

	@Override
	public RealpalNoticeInfo insert(RealpalNoticeInfo realpalNoticeInfo) {
		return realpalNoticeInfoDAO.insert(realpalNoticeInfo);
	}

	@Override
	public RealpalNoticeInfo delete(RealpalNoticeInfo realpalNoticeInfo) {
		return realpalNoticeInfoDAO.delete(realpalNoticeInfo);
	}

	@Override
	public RealpalNoticeInfo update(RealpalNoticeInfo realpalNoticeInfo) {
		return realpalNoticeInfoDAO.update(realpalNoticeInfo);
	}

	@Override
	public RealpalNoticeInfo get(String uuid) {
		return realpalNoticeInfoDAO.get(uuid);
	}
	
	/**
	 * 获取所有页面信息
	 * @param resultClass
	 * @return
	 */
	@Override
	public List<Entity> getAll(Class<? extends Entity> resultClass){
		return realpalNoticeInfoDAO.getAll(resultClass);
	}

	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams,
			Integer pageNum, Integer pageSize, String sorts,
			Class<? extends Entity> resultClass) {
		// TODO Auto-generated method stub
		return realpalNoticeInfoDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	@Override
	public Integer getCount(Map<String, String> inputParams) {
		// TODO Auto-generated method stub
		return realpalNoticeInfoDAO.getCount(inputParams);
	}

	@Override
	public boolean isExistRealpalNoticeInfo(String name, String uuid) {
		// TODO Auto-generated method stub
		return realpalNoticeInfoDAO.isExistRealpalNoticeInfo(name, uuid);
	}

	@Override
	public HashMap<String, Object> insertReapalNotice4PayInvestor(
			Map<String, Object> map) throws ParseException,
			TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String data = map.get("data") == null ? "" : map.get("data").toString();
		if(!Utlity.checkStringNull(data)){
			
			RealpalNoticeInfo rni = new RealpalNoticeInfo();
			rni.setUuid(UUID.randomUUID().toString());
			rni.setCreatetime(new Timestamp(System.currentTimeMillis()));
			rni.setPayType(RealpalNoticeInfoPayType.TAKEOUT);
			rni.setSource(data);
			rni.setStatus(RealpalNoticeInfoStatus.UNPRO);
			String resultArr[] = data.split(",");
			String orderNum = resultArr[12];
			rni.setOrderNum(orderNum);
			String batchNo = resultArr[1];
			rni.setBatchNo(batchNo);
			Boolean flag = this.realpalNoticeInfoDAO.isExistRealpalNoticeInfo(orderNum, null);
			if(!flag){
				this.realpalNoticeInfoDAO.insert(rni);
			}
			System.out.println("成功");
		} else {
			System.out.println("失败");
			resultFlag = false;
			message = "数据错误";
		}
		result.put("result", resultFlag);
		result.put("message", message);
		return result;
	}

	@Override
	public HashMap<String, Object> insertReapalNotice4PayQcb(
			Map<String, Object> map) throws ParseException,
			TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String data = map.get("data") == null ? "" : map.get("data").toString();
		if(!Utlity.checkStringNull(data)){
			RealpalNoticeInfo rni = new RealpalNoticeInfo();
			rni.setUuid(UUID.randomUUID().toString());
			rni.setCreatetime(new Timestamp(System.currentTimeMillis()));
			rni.setPayType(RealpalNoticeInfoPayType.QCB_TAKEOUT);
			rni.setSource(data);
			rni.setStatus(RealpalNoticeInfoStatus.UNPRO);
			String resultArr[] = data.split(",");
			String orderNum = resultArr[12];
			rni.setOrderNum(orderNum);
			String batchNo = resultArr[1];
			rni.setBatchNo(batchNo);
			Boolean flag = this.realpalNoticeInfoDAO.isExistRealpalNoticeInfo(orderNum, null);
			if(!flag){
				this.realpalNoticeInfoDAO.insert(rni);
			}
			System.out.println("成功");
		} else {
			System.out.println("失败");
			resultFlag = false;
			message = "数据错误";
		}
		result.put("result", resultFlag);
		result.put("message", message);
		return result;
	}

	@Override
	public HashMap<String, Object> insertReapalNotice4PayQcbEmp(
			Map<String, Object> map) throws ParseException,
			TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String data = map.get("data") == null ? "" : map.get("data").toString();
		if(!Utlity.checkStringNull(data)){
			RealpalNoticeInfo rni = new RealpalNoticeInfo();
			rni.setUuid(UUID.randomUUID().toString());
			rni.setCreatetime(new Timestamp(System.currentTimeMillis()));
			rni.setPayType(RealpalNoticeInfoPayType.EMP_TAKEOUT);
			rni.setSource(data);
			rni.setStatus(RealpalNoticeInfoStatus.UNPRO);
			String resultArr[] = data.split(",");
			String orderNum = resultArr[12];
			rni.setOrderNum(orderNum);
			String batchNo = resultArr[1];
			rni.setBatchNo(batchNo);
			Boolean flag = this.realpalNoticeInfoDAO.isExistRealpalNoticeInfo(orderNum, null);
			if(!flag){
				this.realpalNoticeInfoDAO.insert(rni);
			}
			System.out.println("成功");
		} else {
			System.out.println("失败");
			resultFlag = false;
			message = "数据错误";
		}
		result.put("result", resultFlag);
		result.put("message", message);
		return result;
	}

	@Override
	public HashMap<String, Object> insertReapalNotice4Pay(
			Map<String, Object> map, String type) throws ParseException,
			TransactionException, NumberFormatException, Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String data = map.get("data") == null ? "" : map.get("data").toString();
		if(!Utlity.checkStringNull(data)){
			RealpalNoticeInfo rni = new RealpalNoticeInfo();
			rni.setUuid(UUID.randomUUID().toString());
			rni.setCreatetime(new Timestamp(System.currentTimeMillis()));
			rni.setPayType(type);
			rni.setSource(data);
			rni.setStatus(RealpalNoticeInfoStatus.UNPRO);
			String resultArr[] = data.split(",");
			String orderNum = resultArr[12];
			rni.setOrderNum(orderNum);
			String batchNo = resultArr[1];
			rni.setBatchNo(batchNo);
			this.realpalNoticeInfoDAO.insert(rni);
			System.out.println("成功");
		} else {
			System.out.println("失败");
			resultFlag = false;
			message = "数据错误";
		}
		result.put("result", resultFlag);
		result.put("message", message);
		return result;
	}

	@Override
	public HashMap<String, Object> insertReapalNotice4Recharge(
			Map<String, Object> map, String type) throws ParseException,
			TransactionException, NumberFormatException, Exception {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String message = "OK";
		Boolean resultFlag = true;
		String out_trade_no = map.get("order_no") == null ? "" : map.get("order_no").toString();
		String transaction_id = map.get("trade_no") == null ? "" : map.get("trade_no").toString();
		String data = JSONUtils.obj2json(map);
		if(!Utlity.checkStringNull(data)){
			RealpalNoticeInfo rni = new RealpalNoticeInfo();
			rni.setUuid(UUID.randomUUID().toString());
			rni.setCreatetime(new Timestamp(System.currentTimeMillis()));
			rni.setPayType(type);
			rni.setSource(data);
			rni.setStatus(RealpalNoticeInfoStatus.UNPRO);
//			String resultArr[] = data.split(",");
//			String orderNum = resultArr[12];
			rni.setOrderNum(out_trade_no);
//			String batchNo = resultArr[1];
			rni.setBatchNo(transaction_id);
			Boolean flag = this.realpalNoticeInfoDAO.isExistRealpalNoticeInfo(out_trade_no, null);
			if(!flag){
				this.realpalNoticeInfoDAO.insert(rni);
			}
			System.out.println("成功");
		} else {
			System.out.println("失败");
			resultFlag = false;
			message = "数据错误";
		}
		result.put("result", resultFlag);
		result.put("message", message);
		return result;
	}

	@Override
	public void updateAll(List<RealpalNoticeInfo> listUpdate) {
		// TODO Auto-generated method stub
		if(listUpdate != null && listUpdate.size() > 0){
			for(RealpalNoticeInfo rni : listUpdate){
				this.realpalNoticeInfoDAO.update(rni);
			}
		}
	}
}
