/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITcustomersService;
import cn.zeppin.product.itic.backadmin.service.api.ITinNumberService;
import cn.zeppin.product.itic.backadmin.service.api.ITnumberRelationService;
import cn.zeppin.product.itic.backadmin.service.api.IToperatorMobileService;
import cn.zeppin.product.itic.backadmin.service.api.IToperatorService;
import cn.zeppin.product.itic.backadmin.vo.TnumberRelationVO;
import cn.zeppin.product.itic.backadmin.vo.ToperatorMobileVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.Tcustomers;
import cn.zeppin.product.itic.core.entity.TinNumber;
import cn.zeppin.product.itic.core.entity.TnumberRelation;
import cn.zeppin.product.itic.core.entity.TnumberRelation.TnumberRelationProcessStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelation.TnumberRelationStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog.TnumberRelationLogStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelationLog.TnumberRelationLogType;
import cn.zeppin.product.itic.core.entity.Toperator;
import cn.zeppin.product.itic.core.entity.ToperatorMobile;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.utility.CdrUtlity;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.Utlity;

/**
 * @author l
 *
 * 号码隐藏
 */

@Controller
@RequestMapping(value = "/backadmin/number")
public class TnumberRelationController extends BaseController {

	@Autowired
	private IToperatorService toperatorService;

	@Autowired
	private ITcustomersService tcustomersService;

	@Autowired
	private ITnumberRelationService tnumberRelationService;

	@Autowired
	private IToperatorMobileService toperatorMobileService;
	
	@Autowired
	private ITinNumberService tinNumberService;

	/**
	 * 查询信息 (经理)
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/searchto", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result searchto(Integer pageNum, Integer pageSize, String name, String sorts) {
		Map<String, String> inputParams = new HashMap<String, String>();
		if (!Utlity.checkStringNull(name)) {
			inputParams.put("name", name);
		}

		List<Entity> arealist = toperatorService.getListForNumPage(inputParams, pageNum, pageSize, sorts, Toperator.class);

		return ResultManager.createDataResult(arealist, arealist.size());
	}

	/**
	 * 查询信息 (经理关联号码)
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/searchtono", method = RequestMethod.GET)
	@ActionParam(key = "opCode", message = "经理编号", required = true, type = DataType.NUMBER)
	@ResponseBody
	public Result searchtono(Integer opCode) {
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("fkToperator", opCode + "");

		List<Entity> arealist = toperatorMobileService.getListForPage(inputParams, -1, -1, null, ToperatorMobile.class);
		if (arealist != null && arealist.size() > 0) {
			ToperatorMobile tm = (ToperatorMobile) arealist.get(0);
			ToperatorMobileVO vo = new ToperatorMobileVO(tm);
			Toperator to = this.toperatorService.get(tm.getFkToperator());
			if(to != null) {
				vo.setOpName(to.getOpName());
			}
			return ResultManager.createDataResult(vo);
		}
		return ResultManager.createFailResult("未查询到相关数据");
	}

	/**
	 * 查询信息 (客户)
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sorts
	 * @param opCode
	 * @return
	 */
	@RequestMapping(value = "/searchtc", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ActionParam(key = "opCode", message = "经理编号", required = true, type = DataType.STRING)
	@ResponseBody
	public Result searchtc(Integer pageNum, Integer pageSize, String name, String sorts, String opCode) {
		// List<Entity> arealist = toperatorService.getAll(Toperator.class);
		Map<String, String> inputParams = new HashMap<String, String>();
		if (!Utlity.checkStringNull(name)) {
			inputParams.put("name", name);
		}
		inputParams.put("serviceMan", opCode);

		List<Entity> arealist = tcustomersService.getListForPage(inputParams, pageNum, pageSize, sorts,
				Tcustomers.class);

		return ResultManager.createDataResult(arealist, arealist.size());
	}

	/**
	 * 获取列表 查询信息 (号码绑定) 查询参数有 如下几个 客户姓名、呼出号、被叫号码 经理姓名、忽如好、主叫号码
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sorts
	 * @param opCode
	 * @param toMobile
	 * @param toTel
	 * @param tcPhone
	 * @param tcTel
	 * @return
	 */
	@RequestMapping(value = "/searchnr", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "name", message = "客户搜索参数", type = DataType.STRING)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ActionParam(key = "oname", message = "经理搜索参数", type = DataType.STRING)
	@ActionParam(key = "toMobile", message = "主叫号码（经理）", type = DataType.STRING)
	@ActionParam(key = "toTel", message = "呼入号码", type = DataType.STRING)
	@ActionParam(key = "tcPhone", message = "被叫号码（客户）", type = DataType.STRING)
	@ActionParam(key = "tcTel", message = "呼出号码", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ResponseBody
	public Result searchnr(Integer pageNum, Integer pageSize, String name, String sorts, String oname, String toMobile,
			String toTel, String tcPhone, String tcTel, String status) {
		Map<String, String> inputParams = new HashMap<String, String>();

		// 搜素参数
		if (!Utlity.checkStringNull(name)) {
			inputParams.put("name", name);
		}
		if (!Utlity.checkStringNull(oname)) {
			inputParams.put("oname", oname);
		}
		if (!Utlity.checkStringNull(toMobile)) {
			inputParams.put("toMobile", toMobile);
		}
		if (!Utlity.checkStringNull(toTel)) {
			inputParams.put("toTel", toTel);
		}
		if (!Utlity.checkStringNull(tcPhone)) {
			inputParams.put("tcPhone", tcPhone);
		}
		if (!Utlity.checkStringNull(tcTel)) {
			inputParams.put("tcTel", tcTel);
		}
		if (!Utlity.checkStringNull(status)) {
			inputParams.put("status", status);
		}
		inputParams.put("processStatus", TnumberRelationProcessStatus.NORMAL+"");
		Integer totalCount = this.tnumberRelationService.getCount(inputParams);
		List<Entity> arealist = tnumberRelationService.getListForPage(inputParams, pageNum, pageSize, sorts,
				TnumberRelation.class);
		List<TnumberRelationVO> arealistNew = new ArrayList<TnumberRelationVO>();
		for (Entity e : arealist) {
			TnumberRelation nr = (TnumberRelation) e;
			TnumberRelationVO trvo = new TnumberRelationVO(nr);
			Toperator to = this.toperatorService.get(nr.getFkToperator());
			Tcustomers tc = this.tcustomersService.get(nr.getFkTcustomers());
			if (to != null) {
				trvo.setOpName(to.getOpName());
			}
			if (tc != null) {
				trvo.setCustName(tc.getCustName());
			}
			arealistNew.add(trvo);
		}
		return ResultManager.createDataResult(arealistNew, totalCount);
	}

	/**
	 * 获取详情 查询信息 (号码绑定)
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sorts
	 * @param opCode
	 * @return
	 */
	@RequestMapping(value = "/getnr", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "唯一编号", required = true, type = DataType.NUMBER)
	@ResponseBody
	public Result getnr(Integer id) {

		TnumberRelation nr = this.tnumberRelationService.get(id);
		if (nr != null) {
			TnumberRelationVO trvo = new TnumberRelationVO(nr);
			Toperator to = this.toperatorService.get(nr.getFkToperator());
			Tcustomers tc = this.tcustomersService.get(nr.getFkTcustomers());
			if (to != null) {
				trvo.setOpName(to.getOpName());
			}
			if (tc != null) {
				trvo.setCustName(tc.getCustName());
			}

			return ResultManager.createDataResult(trvo);
		}

		return ResultManager.createFailResult("数据信息不存在！");
	}

	/**
	 * 获取列表 查询信息 (未分配的呼出号码)
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sorts
	 * @param opCode
	 * @return
	 */
	@RequestMapping(value = "/gettcTel", method = RequestMethod.GET)
	@ActionParam(key = "opCode", message = "经理编号", required = true, type = DataType.STRING)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ResponseBody
	public Result gettcTel(String opCode, Integer pageNum, Integer pageSize) {

		// 生成全部号码的Map集合
		// 查询已有的Map集合，并从全部集合里删除
		

		Map<String, String> numMap = new LinkedHashMap<String, String>();
//		for (int i = 0; i < Utlity.TCTEL_TOTAL_COUNT; i++) {
//			Integer number = Integer.parseInt(Utlity.TCTEL_FIR);
//			number += i;
////			numMap.put("010" + number + "", number + "");
//			numMap.put(number + "", number + "");
//		}

		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("status", "normal");
		List<Entity> liston = this.tinNumberService.getListForPage(inputParams, -1, -1, null, TinNumber.class);
		if(liston != null) {
			for(Entity e : liston) {
				TinNumber tn = (TinNumber)e;
				numMap.put(tn.getTcTel(), tn.getTcTel());
			}
		}

		inputParams.clear();
		// 搜素参数
		if (!Utlity.checkStringNull(opCode)) {
			inputParams.put("fkToperator", opCode);
		}

		List<Entity> arealist = tnumberRelationService.getListForPage(inputParams, -1, -1, null, TnumberRelation.class);
		for (Entity e : arealist) {
			TnumberRelation nr = (TnumberRelation) e;
			numMap.remove(nr.getTcTel());
		}

		List<String> numList = new ArrayList<>();
		for (String s : numMap.keySet()) {
			numList.add(s);
		}
		Integer start = (pageNum-1) * pageSize;
		Integer limit = pageNum * pageSize;
		List<String> limitList = new ArrayList<>();
		if(!numList.isEmpty()) {
			if(limit > numList.size()) {
				limit = numList.size();
			}
			if(start > numList.size()) {
				start = 0;
			}
			for(int i = start; i < limit; i++) {
				limitList.add(numList.get(i));
			}
		}

		return ResultManager.createDataResult(limitList, numList.size());
	}
	/**
	 * 添加号码关联关系
	 * 
	 * @param opCode
	 * @param toMobile
	 * @param toTel
	 * @param custId
	 * @param tcPhone
	 * @param tcTel
	 * @param tono
	 * @return
	 */
	@RequestMapping(value = "/addnr", method = RequestMethod.POST)
	@ActionParam(key = "opCode", message = "经理", required = true, type = DataType.NUMBER)
	@ActionParam(key = "toMobile", message = "主叫号码", required = true, type = DataType.STRING)
	@ActionParam(key = "toTel", message = "呼入号码", required = true, type = DataType.STRING)
	@ActionParam(key = "custId", message = "客户", required = true, type = DataType.NUMBER)
	@ActionParam(key = "tcPhone", message = "被叫号码", required = true, type = DataType.STRING)
	@ActionParam(key = "tcTel", message = "呼出号码", required = true, type = DataType.STRING)
	@ActionParam(key = "tono", message = "经理绑定号码编号", type = DataType.NUMBER)
	@ResponseBody
	public Result addnr(Integer opCode, String toMobile, String toTel, Integer custId, String tcPhone, String tcTel,
			Integer tono) {

		// 检验信息准确性
		Toperator to = this.toperatorService.get(opCode);
		if (to == null) {
			return ResultManager.createFailResult("经理信息不存在！");
		}

		Tcustomers tc = this.tcustomersService.get(custId);
		if (tc == null) {
			return ResultManager.createFailResult("客户信息不存在！");
		}
		
		if(tc.getServiceMan().compareTo(to.getOpCode()) != 0) {
			return ResultManager.createFailResult("客户信息错误！");
		}

		// 信息重复性校验
		Map<String, String> inputParams = new HashMap<>();
		inputParams.put("fkToperator", opCode + "");
		inputParams.put("fkTcustomers", custId + "");
		Integer count = this.tnumberRelationService.getCount(inputParams);
		if (count > 0) {
			return ResultManager.createFailResult("客户关联关系已存在！");
		}
		inputParams.clear();
		inputParams.put("toMobile", toMobile);
		inputParams.put("toTel", toTel);
		inputParams.put("tcPhone", tcPhone);
		inputParams.put("tcTel", tcTel);

		count = this.tnumberRelationService.getCount(inputParams);
		if (count > 0) {
			return ResultManager.createFailResult("号码关联关系已存在！");
		}

		try {
			// 封装数据对象，并调用联通接口进行数据同步传输
			TnumberRelation tr = new TnumberRelation();
			tr.setCreatetime(new Timestamp(System.currentTimeMillis()));
			tr.setExpiryDate(null);
			tr.setFkTcustomers(tc.getCustId());
			tr.setFkToperator(to.getOpCode());
			tr.setIsrecord(1);
			tr.setMaxduration(3600);
			tr.setProcessStatus(TnumberRelationProcessStatus.NORMAL);
			tr.setStatus(TnumberRelationStatus.NORMAL);
			tr.setTcPhone(tcPhone);
			tr.setTcTel(tcTel);
			tr.setToMobile(toMobile);
			tr.setToTel(toTel);
			tr.setWaybillnum("");

			Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_create, tr);
			Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
			String status = map.get("STATE_CODE").toString();
			String message = map.get("STATE_NAME").toString();
			String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();

			if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
				if (tono == 0) {// 不存在经理关联号码 需要创建新的关联信息并入库存储
					ToperatorMobile tm = new ToperatorMobile();
					tm.setFkToperator(to.getOpCode());
					tm.setToMobile(toMobile);
					tm.setToTel(toTel);
					this.tnumberRelationService.insert(tr, tm);

				} else {
					tr.setFkOpMobile(tono);
					this.tnumberRelationService.insert(tr);
				}
				return ResultManager.createSuccessResult("操作成功！");

			}
			return ResultManager.createFailResult("操作失败！" + message + "," + remark);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("操作异常！");
		}
	}

	/**
	 * 编辑号码绑定关系
	 * 分几种情况
	 * 修改客户
	 * @param id
	 * @param opCode
	 * @param toMobile
	 * @param toTel
	 * @param custId
	 * @param tcPhone
	 * @param tcTel
	 * @param tono
	 * @return
	 */
	@RequestMapping(value = "/editnr", method = RequestMethod.POST)
	@ActionParam(key = "id", message = "唯一编号", required = true, type = DataType.NUMBER)
	@ActionParam(key = "opCode", message = "经理", required = true, type = DataType.NUMBER)
	@ActionParam(key = "toMobile", message = "主叫号码", required = true, type = DataType.STRING)
	@ActionParam(key = "toTel", message = "呼入号码", required = true, type = DataType.STRING)
	@ActionParam(key = "custId", message = "客户", required = true, type = DataType.NUMBER)
	@ActionParam(key = "tcPhone", message = "被叫号码", required = true, type = DataType.STRING)
	@ActionParam(key = "tcTel", message = "呼出号码", type = DataType.STRING)
	@ActionParam(key = "tono", message = "经理绑定号码编号", type = DataType.NUMBER)
	@ResponseBody
	public Result editnr(Integer id, Integer opCode, String toMobile, String toTel, Integer custId, String tcPhone, String tcTel,
			Integer tono) {
		try {
			// 检验信息准确性
			Toperator to = this.toperatorService.get(opCode);
			if (to == null) {
				return ResultManager.createFailResult("经理信息不存在！");
			}

			Tcustomers tc = this.tcustomersService.get(custId);
			if (tc == null) {
				return ResultManager.createFailResult("客户信息不存在！");
			}
			
			if(tc.getServiceMan().compareTo(to.getOpCode()) != 0) {
				return ResultManager.createFailResult("客户信息错误！");
			}
			
			//参数格式校验
			if(!Utlity.isMobileNO(tcPhone)) {
				return ResultManager.createFailResult("客户联系方式格式有误！");
			}

			// 信息重复性校验
			Map<String, String> inputParams = new HashMap<>();
			inputParams.put("id", id + "");
			inputParams.put("fkToperator", opCode + "");
			inputParams.put("fkTcustomers", custId + "");
			Integer count = this.tnumberRelationService.getCount(inputParams);
			if (count > 0) {
				return ResultManager.createFailResult("客户关联关系已存在！");
			}
			
			TnumberRelation tr = this.tnumberRelationService.get(id);
			if (tr != null) {
				
				if(Utlity.checkStringNull(tcTel) && !Utlity.checkStringNull(tr.getTcTel())) {//释放
					Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_delete,tr);
					Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
					String status = map.get("STATE_CODE").toString();
					String message = map.get("STATE_NAME").toString();
					String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();
					
					//20180608 增加log记录
					TnumberRelationLog trl = new TnumberRelationLog();
					trl.setCreatetime(new Timestamp(System.currentTimeMillis()));
					trl.setStatus(TnumberRelationLogStatus.DISABLED);
					trl.setFkTcustomers(tr.getFkTcustomers());
					trl.setFkToperator(tr.getFkToperator());
					trl.setTcPhone(tr.getTcPhone());
					trl.setTcTel(tr.getTcTel());
					trl.setType(TnumberRelationLogType.DELETE);
					trl.setFkTnumberRelation(tr.getId());
					trl.setToMobile(tr.getToMobile());
					trl.setToTel(tr.getToTel());
					
					if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
						tr.setCreatetime(new Timestamp(System.currentTimeMillis()));
						tr.setExpiryDate(null);
						tr.setFkTcustomers(null);
						tr.setFkToperator(to.getOpCode());
						tr.setIsrecord(1);
						tr.setMaxduration(3600);
						tr.setProcessStatus(TnumberRelationProcessStatus.NORMAL);
						tr.setStatus(TnumberRelationStatus.EMPTY);
						tr.setTcPhone(null);
						//tr.setTcTel(null);
						tr.setToMobile(toMobile);
						tr.setToTel(toTel);
						tr.setWaybillnum("");
						tr.setFkOpMobile(tono);

						this.tnumberRelationService.update(tr, trl);
						return ResultManager.createSuccessResult("操作成功！");
					} else {
						return ResultManager.createFailResult("操作失败！" + message + "," + remark);
					}
				}
				if(Utlity.checkStringNull(tcTel) && Utlity.checkStringNull(tr.getTcTel())) {//无~无
					tr.setCreatetime(new Timestamp(System.currentTimeMillis()));
					tr.setExpiryDate(null);
					tr.setFkTcustomers(null);
					tr.setFkToperator(to.getOpCode());
					tr.setIsrecord(1);
					tr.setMaxduration(3600);
					tr.setProcessStatus(TnumberRelationProcessStatus.NORMAL);
					tr.setTcPhone(null);
//					tr.setTcTel(null);
					tr.setToMobile(toMobile);
					tr.setToTel(toTel);
					tr.setWaybillnum("");
					tr.setFkOpMobile(tono);
					this.tnumberRelationService.update(tr);
					return ResultManager.createSuccessResult("操作成功！");
				}
				
				//else
				inputParams.clear();
				inputParams.put("id", id + "");
				inputParams.put("toMobile", toMobile);
				inputParams.put("toTel", toTel);
				inputParams.put("tcPhone", tcPhone);
				inputParams.put("tcTel", tcTel);

				count = this.tnumberRelationService.getCount(inputParams);
				if (count > 0) {
					return ResultManager.createFailResult("号码关联关系已存在！");
				}
				
				if(!Utlity.checkStringNull(tcTel) && Utlity.checkStringNull(tr.getTcTel())) {//无~有
					tr.setCreatetime(new Timestamp(System.currentTimeMillis()));
					tr.setExpiryDate(null);
					tr.setFkTcustomers(tc.getCustId());
					tr.setFkToperator(to.getOpCode());
					tr.setIsrecord(1);
					tr.setMaxduration(3600);
					tr.setProcessStatus(TnumberRelationProcessStatus.NORMAL);
					tr.setStatus(TnumberRelationStatus.NORMAL);
					tr.setTcPhone(tcPhone);
					tr.setTcTel(tcTel);
					tr.setToMobile(toMobile);
					tr.setToTel(toTel);
					tr.setWaybillnum("");
					tr.setFkOpMobile(tono);
					
					//20180608 增加log记录
					TnumberRelationLog trl = new TnumberRelationLog();
					trl.setCreatetime(new Timestamp(System.currentTimeMillis()));
					trl.setStatus(TnumberRelationLogStatus.DISABLED);
					trl.setFkTcustomers(tr.getFkTcustomers());
					trl.setFkToperator(tr.getFkToperator());
					trl.setTcPhone(tr.getTcPhone());
					trl.setTcTel(tr.getTcTel());
					trl.setType(TnumberRelationLogType.INSERT);
					trl.setFkTnumberRelation(tr.getId());
					trl.setToMobile(tr.getToMobile());
					trl.setToTel(tr.getToTel());

					Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_create,tr);
					Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
					String status = map.get("STATE_CODE").toString();
					String message = map.get("STATE_NAME").toString();
					String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();

					if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
						this.tnumberRelationService.update(tr, trl);
						return ResultManager.createSuccessResult("操作成功！");
					} else {
						return ResultManager.createFailResult("操作失败！" + message + "," + remark);
					}
				}
				
				//20180608 增加log记录
				TnumberRelationLog trl = null;
				
				if(!tcTel.equals(tr.getTcTel()) || !tcPhone.equals(tr.getTcPhone())) {//要先把原关系释放掉，然后再重新绑定
					Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_delete,tr);
					Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
					String status = map.get("STATE_CODE").toString();
					String message = map.get("STATE_NAME").toString();
					String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();

					if (!CdrUtlity.request_status_0000.equals(status)) {// 请求成功
						return ResultManager.createFailResult("操作失败！" + message + "," + remark);
					}
					trl = new TnumberRelationLog();
					trl.setCreatetime(new Timestamp(System.currentTimeMillis()));
					trl.setStatus(TnumberRelationLogStatus.DISABLED);
					trl.setFkTcustomers(tr.getFkTcustomers());
					trl.setFkToperator(tr.getFkToperator());
					trl.setTcPhone(tr.getTcPhone());
					trl.setTcTel(tr.getTcTel());
					trl.setType(TnumberRelationLogType.DELETE);
					trl.setFkTnumberRelation(tr.getId());
					trl.setToMobile(tr.getToMobile());
					trl.setToTel(tr.getToTel());
				}
				tr.setCreatetime(new Timestamp(System.currentTimeMillis()));
				tr.setExpiryDate(null);
				tr.setFkTcustomers(tc.getCustId());
				tr.setFkToperator(to.getOpCode());
				tr.setIsrecord(1);
				tr.setMaxduration(3600);
				tr.setProcessStatus(TnumberRelationProcessStatus.NORMAL);
				tr.setStatus(TnumberRelationStatus.NORMAL);
				tr.setTcPhone(tcPhone);
				tr.setTcTel(tcTel);
				tr.setToMobile(toMobile);
				tr.setToTel(toTel);
				tr.setWaybillnum("");
				tr.setFkOpMobile(tono);
				
				//20180608 增加log记录
				TnumberRelationLog trl2 = new TnumberRelationLog();
				trl2.setCreatetime(new Timestamp(System.currentTimeMillis()));
				trl2.setStatus(TnumberRelationLogStatus.DISABLED);
				trl2.setFkTcustomers(tr.getFkTcustomers());
				trl2.setFkToperator(tr.getFkToperator());
				trl2.setTcPhone(tr.getTcPhone());
				trl2.setTcTel(tr.getTcTel());
				trl2.setType(TnumberRelationLogType.INSERT);
				trl2.setFkTnumberRelation(tr.getId());
				trl2.setToMobile(tr.getToMobile());
				trl2.setToTel(tr.getToTel());

				Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_create,tr);
				Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
				String status = map.get("STATE_CODE").toString();
				String message = map.get("STATE_NAME").toString();
				String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();

				if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
					this.tnumberRelationService.update(tr, trl, trl2);
					return ResultManager.createSuccessResult("操作成功！");
				}
				return ResultManager.createFailResult("操作失败！" + message + "," + remark);
			}
			return ResultManager.createFailResult("数据信息不存在！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("操作异常！");
		}
	}

	@RequestMapping(value = "/deletenr", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "唯一编号", required = true, type = DataType.STRING)
	@ResponseBody
	public Result deletenr(String id) {
		try {
			String[] ids = id.split(",");
			if(ids != null && ids.length > 0) {
				Integer countSuccess = 0;
				Integer countFail = 0;
				StringBuilder errorMessage = new StringBuilder();
				for(String idstr : ids) {
					Integer idnum = Integer.parseInt(idstr);
					TnumberRelation tr = this.tnumberRelationService.get(idnum);
					if (tr != null) {
						Map<String, Object> result = CdrUtlity.setBindNum(CdrUtlity.process_operate_delete, tr);
						Map<String, Object> map = JSONUtils.json2map(result.get("header").toString());
						String status = map.get("STATE_CODE").toString();
						String message = map.get("STATE_NAME").toString();
						String remark = map.get("REMARK") == null ? "" : map.get("REMARK").toString();

						//20180608 增加log记录
						TnumberRelationLog trl = new TnumberRelationLog();
						trl.setCreatetime(new Timestamp(System.currentTimeMillis()));
						trl.setStatus(TnumberRelationLogStatus.DISABLED);
						trl.setFkTcustomers(tr.getFkTcustomers());
						trl.setFkToperator(tr.getFkToperator());
						trl.setTcPhone(tr.getTcPhone());
						trl.setTcTel(tr.getTcTel());
						trl.setType(TnumberRelationLogType.DELETE);
						trl.setFkTnumberRelation(tr.getId());
						trl.setToMobile(tr.getToMobile());
						trl.setToTel(tr.getToTel());
						if (CdrUtlity.request_status_0000.equals(status)) {// 请求成功
							tr.setTcPhone(null);
							tr.setFkTcustomers(null);
							tr.setStatus(TnumberRelationStatus.EMPTY);
							this.tnumberRelationService.update(tr, trl);
							countSuccess++;
						} else {
							countFail ++;
							errorMessage.append("ID:"+idstr+"操作失败！" + message + "," + remark);
							errorMessage.append(";");
						}
						
					} else {
						countFail ++;
					}
				}
				String message = "处理完成，"+countSuccess+"条操作成功！";
				if(countFail > 0) {
					message += countFail + "条操作失败！" + errorMessage.toString();
				}
				return ResultManager.createSuccessResult(message);
			}
			return ResultManager.createFailResult("参数错误！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("操作异常！");
		}
	}

	/**
	 * 查询信息 (号码绑定-经理)
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sorts
	 * @param opCode
	 * @return
	 */
	@RequestMapping(value = "/searchom", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message = "页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", type = DataType.NUMBER)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ActionParam(key = "opCode", message = "经理编号", required = true, type = DataType.STRING)
	@ResponseBody
	public Result searchom(Integer pageNum, Integer pageSize, String name, String sorts, String opCode) {
		Map<String, String> inputParams = new HashMap<String, String>();
		if (!Utlity.checkStringNull(name)) {
			inputParams.put("name", name);
		}
		inputParams.put("serviceMan", opCode);

		List<ToperatorMobile> arealist = toperatorMobileService.getListForPage(inputParams, pageNum, pageSize, sorts);

		return ResultManager.createDataResult(arealist, arealist.size());
	}
}
