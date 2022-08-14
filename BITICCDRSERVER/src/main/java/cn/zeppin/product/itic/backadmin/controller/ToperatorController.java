/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITnumberRelationService;
import cn.zeppin.product.itic.backadmin.service.api.IToperatorMobileService;
import cn.zeppin.product.itic.backadmin.service.api.IToperatorService;
import cn.zeppin.product.itic.backadmin.service.api.IToutNumberService;
import cn.zeppin.product.itic.backadmin.vo.ToperatorMobileVO;
import cn.zeppin.product.itic.backadmin.vo.ToutNumberVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TnumberRelation;
import cn.zeppin.product.itic.core.entity.TnumberRelation.TnumberRelationProcessStatus;
import cn.zeppin.product.itic.core.entity.TnumberRelation.TnumberRelationStatus;
import cn.zeppin.product.itic.core.entity.Toperator;
import cn.zeppin.product.itic.core.entity.ToperatorMobile;
import cn.zeppin.product.itic.core.entity.ToutNumber;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.itic.core.exception.ProcessException;
import cn.zeppin.product.utility.SystemConfig;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 经理号码关联管理
 */

@Controller
@RequestMapping(value = "/backadmin/operator")
public class ToperatorController extends BaseController {
	
	@Autowired
	private IToperatorService  toperatorService;
	
	@Autowired
	private IToperatorMobileService toperatorMobileService;
	
	@Autowired
	private ITnumberRelationService tnumberRelationService;
	
	@Autowired
	private IToutNumberService toutNumberService;
	
	/**
	 * 用户登录
	 * 
	 * @param loginname
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ActionParam(key = "loginname", message="用户名", type = DataType.STRING, required = true)
	@ActionParam(key = "password", message="密码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result login(String loginname, String password) {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		String username = SystemConfig.getProperty("login.user.name");
		String userpwd = SystemConfig.getProperty("login.user.pwd");
			
		//简洁登录校验
		if(!username.equals(loginname)) {
			return ResultManager.createFailResult("用户名或密码不正确");
		}
		
		if(!userpwd.equals(password)) {
			return ResultManager.createFailResult("用户名或密码不正确");
		}
		
		session.setAttribute("username", username);
		return ResultManager.createSuccessResult("登录成功");
		
	}
	
	/**
	 * 用户登出
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public Result logout() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		
		session.removeAttribute("username");
		return ResultManager.createSuccessResult("登出成功");
		
	}

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
		inputParams.put("isnull", "is null");
		List<Entity> arealist = toperatorService.getListForSearchPage(inputParams, pageNum, pageSize, sorts, Toperator.class);

		return ResultManager.createDataResult(arealist, arealist.size());
	}
	
	/**
	 * 查询列表 (经理关联号码)
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/searchno", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "name", message = "搜索参数", type = DataType.STRING)
	@ActionParam(key = "toMobile", message = "主叫号码（经理）", type = DataType.STRING)
	@ActionParam(key = "toTel", message = "呼入号码", type = DataType.STRING)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result searchno(Integer pageNum, Integer pageSize, String name, String toMobile,
			String toTel, String sorts) {
		Map<String, String> inputParams = new HashMap<String, String>();
		if (!Utlity.checkStringNull(name)) {
			inputParams.put("name", name);
		}
		if (!Utlity.checkStringNull(toMobile)) {
			inputParams.put("toMobile", toMobile);
		}
		if (!Utlity.checkStringNull(toTel)) {
			inputParams.put("toTel", toTel);
		}

		Integer totalCount = this.toperatorMobileService.getCountForNumPage(inputParams);
		List<Entity> arealist = toperatorMobileService.getListForNumPage(inputParams, pageNum, pageSize, sorts, ToperatorMobile.class);
		List<ToperatorMobileVO> list = new ArrayList<>();
		if (arealist != null && arealist.size() > 0) {
			for(Entity e : arealist) {
				ToperatorMobile tm = (ToperatorMobile) e;
				ToperatorMobileVO vo = new ToperatorMobileVO(tm);
				Toperator to = this.toperatorService.get(tm.getFkToperator());
				if(to != null) {
					vo.setOpName(to.getOpName());
				}
				list.add(vo);
			}
		}
		return ResultManager.createDataResult(list, totalCount);
	}
	
	/**
	 * 获取列表 查询信息 (未分配的呼入号码)
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/gettoTel", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ResponseBody
	public Result gettoTel(Integer pageNum, Integer pageSize) {

		// 生成全部号码的Map集合
		// 查询已有的Map集合，并从全部集合里删除
		Map<String, Object> numMap = new LinkedHashMap<String, Object>();
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("status", "normal");
		List<Entity> liston = this.toutNumberService.getListForPage(inputParams, -1, -1, null, ToutNumber.class);
		if(liston != null) {
			for(Entity e : liston) {
				ToutNumber tn = (ToutNumber)e;
				ToutNumberVO tnvo = new ToutNumberVO(tn);
				numMap.put(tn.getToMobile()+"_"+tn.getToTel(), tnvo);
			}
		}

		inputParams.clear();
		// 搜素参数

		List<Entity> arealist = toperatorMobileService.getListForPage(inputParams, -1, -1, null, ToperatorMobile.class);
		for (Entity e : arealist) {
			ToperatorMobile nr = (ToperatorMobile) e;
			numMap.remove(nr.getToMobile()+"_"+nr.getToTel());
		}

		List<Object> numList = new ArrayList<>();
		for (String s : numMap.keySet()) {
			numList.add(numMap.get(s));
		}
		Integer start = (pageNum-1) * pageSize;
		Integer limit = pageNum * pageSize;
		List<Object> limitList = new ArrayList<>();
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
		
		return ResultManager.createDataResult(limitList);
	}
	
	/**
	 * 获取详情 查询信息
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sorts
	 * @param opCode
	 * @return
	 */
	@RequestMapping(value = "/getno", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "唯一编号", required = true, type = DataType.NUMBER)
	@ResponseBody
	public Result getno(Integer id) {

		ToperatorMobile tm = this.toperatorMobileService.get(id);
		if (tm != null) {
			ToperatorMobileVO vo = new ToperatorMobileVO(tm);
			Toperator to = this.toperatorService.get(tm.getFkToperator());
			if(to != null) {
				vo.setOpName(to.getOpName());
			}

			return ResultManager.createDataResult(vo);
		}

		return ResultManager.createFailResult("数据信息不存在！");
	}
	
	/**
	 * 添加号码关联关系
	 * 
	 * @param opCode
	 * @param toMobile
	 * @param toTel
	 * @return
	 */
	@RequestMapping(value = "/addno", method = RequestMethod.POST)
	@ActionParam(key = "opCode", message = "经理", required = true, type = DataType.NUMBER)
	@ActionParam(key = "toMobile", message = "主叫号码", required = true, type = DataType.STRING)
	@ActionParam(key = "toTel", message = "呼入号码", required = true, type = DataType.STRING)
	@ResponseBody
	public Result addno(Integer opCode, String toMobile, String toTel) {

		// 检验信息准确性
		Toperator to = this.toperatorService.get(opCode);
		if (to == null) {
			return ResultManager.createFailResult("经理信息不存在！");
		}

		// 信息重复性校验
		Map<String, String> inputParams = new HashMap<>();
		inputParams.put("fkToperator", opCode + "");
		Integer count = this.toperatorMobileService.getCount(inputParams);
		if (count > 0) {
			return ResultManager.createFailResult("经理关联关系已存在！");
		}
		inputParams.clear();
		inputParams.put("toMobile", toMobile);
		inputParams.put("toTel", toTel);

		count = this.toperatorMobileService.getCount(inputParams);
		if (count > 0) {
			return ResultManager.createFailResult("号码关联关系已存在！");
		}

		try {
			// 封装数据对象
			ToperatorMobile tm = new ToperatorMobile();
			tm.setFkToperator(to.getOpCode());
			tm.setToMobile(toMobile);
			tm.setToTel(toTel);
			
			this.toperatorMobileService.insert(tm);
			return ResultManager.createSuccessResult("操作成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("操作异常！");
		}
	}

	/**
	 * 编辑号码绑定关系
	 * @param id
	 * @param opCode
	 * @param toMobile
	 * @param toTel
	 * @return
	 */
	@RequestMapping(value = "/editno", method = RequestMethod.POST)
	@ActionParam(key = "id", message = "唯一编号", required = true, type = DataType.NUMBER)
	@ActionParam(key = "toMobile", message = "主叫号码", type = DataType.STRING)
	@ActionParam(key = "toTel", message = "呼入号码", type = DataType.STRING)
	@ResponseBody
	public Result editno(Integer id, String toMobile, String toTel) {
		try {
			ToperatorMobile tm = this.toperatorMobileService.get(id);
			if (tm != null) {
				// 检验信息准确性
				Toperator to = this.toperatorService.get(tm.getFkToperator());
				if (to == null) {
					return ResultManager.createFailResult("经理信息不存在！");
				}
				
				String type = "";
				if(Utlity.checkStringNull(toMobile) && Utlity.checkStringNull(toTel)) {
					type = "1";//全为空
				} else if (!Utlity.checkStringNull(toMobile) && !Utlity.checkStringNull(toTel)) {
					type = "2";//全不为空
				} else {
					return ResultManager.createFailResult("参数信息错误！");
				}

				// 信息重复性校验
				Map<String, String> inputParams = new HashMap<>();
				
				if(!toMobile.equals(tm.getToMobile()) || !toTel.equals(tm.getToTel())) {
					
					String type2 = "";
					if(Utlity.checkStringNull(tm.getToMobile()) && Utlity.checkStringNull(tm.getToMobile())) {
						type2 = "1";
					} else if(!Utlity.checkStringNull(tm.getToMobile()) && !Utlity.checkStringNull(tm.getToMobile())) {
						type2 = "2";
					}
					
					if("1".equals(type) && "2".equals(type2)) {//有~无 释放号码
						inputParams.clear();
//						// 搜素参数
//						if (!Utlity.checkStringNull(tm.getToMobile())) {
//							inputParams.put("toMobile", tm.getToMobile());
//						}
//						if (!Utlity.checkStringNull(tm.getToTel())) {
//							inputParams.put("toTel", tm.getToTel());
//						}
						inputParams.put("fkToperator", tm.getFkToperator()+"");

						List<Entity> arealist = tnumberRelationService.getListForPage(inputParams, -1, -1, null, TnumberRelation.class);
						if(arealist != null && !arealist.isEmpty()) {
							List<TnumberRelation> list = new ArrayList<>();
							for(Entity e : arealist) {
								TnumberRelation tr = (TnumberRelation)e;
								list.add(tr);
							}
							tm.setToMobile(null);
							tm.setToTel(null);
							this.tnumberRelationService.deleteProcess(list, tm);
						} else {
							tm.setToMobile(null);
							tm.setToTel(null);
							this.toperatorMobileService.update(tm);
						}
					} else if ("2".equals(type) && "1".equals(type2)) {//无~有
						inputParams.clear();
//						inputParams.put("id", id + "");
						inputParams.put("toMobile", toMobile);
						inputParams.put("toTel", toTel);

						Integer count = this.toperatorMobileService.getCount(inputParams);
						if (count > 0) {
							return ResultManager.createFailResult("号码关联关系已存在！");
						}
						
						tm.setToMobile(toMobile);
						tm.setToTel(toTel);
						Map<String, String> input = new HashMap<String, String>();
						input.put("fkToperator", tm.getFkToperator()+"");
						
						List<Entity> list = this.tnumberRelationService.getListForPage(input, -1, -1, null, TnumberRelation.class);
						if(list != null && !list.isEmpty()) {
							List<TnumberRelation> listtr = new ArrayList<>();
							for(Entity e : list) {
								TnumberRelation tr = (TnumberRelation)e;
								tr.setToMobile(tm.getToMobile());
								tr.setToTel(tm.getToTel());
								tr.setStatus(TnumberRelationStatus.NORMAL);
								tr.setProcessStatus(TnumberRelationProcessStatus.NORMAL);
								listtr.add(tr);
							}
							this.tnumberRelationService.insertProcess(listtr,tm);
						} else {
							this.tnumberRelationService.insertProcess(tm);
							
//							Map<String, String> params = new HashMap<String, String>();
//							params.put("processing", "1");
//							params.put("fkToperator", tm.getFkToperator()+"");
//							
//							List<Entity> trList = this.tnumberRelationService.getListForPage(params, -1, -1, null, TnumberRelation.class);
//							List<TnumberRelation> listtr = new ArrayList<>();
//							for(Entity e : trList) {
//								TnumberRelation tr = (TnumberRelation)e;
//								listtr.add(tr);
//							}
//							this.tnumberRelationService.insertProcess(listtr);
						}
						
					} else if ("2".equals(type) && "2".equals(type2)) {//有~有
						inputParams.clear();
						inputParams.put("id", id + "");
						inputParams.put("toMobile", toMobile);
						inputParams.put("toTel", toTel);

						Integer count = this.toperatorMobileService.getCount(inputParams);
						if (count > 0) {
							return ResultManager.createFailResult("号码关联关系已存在！");
						}
						
						inputParams.clear();
						// 搜素参数
						if (!Utlity.checkStringNull(tm.getToMobile())) {
							inputParams.put("toMobile", tm.getToMobile());
						}
						if (!Utlity.checkStringNull(tm.getToTel())) {
							inputParams.put("toTel", tm.getToTel());
						}
						inputParams.put("fkToperator", tm.getFkToperator()+"");

						List<Entity> arealist = tnumberRelationService.getListForPage(inputParams, -1, -1, null, TnumberRelation.class);
						if(arealist != null && !arealist.isEmpty()) {
							List<TnumberRelation> list = new ArrayList<>();
							for(Entity e : arealist) {
								TnumberRelation tr = (TnumberRelation)e;
								list.add(tr);
							}

							tm.setToMobile(toMobile);
							tm.setToTel(toTel);
							this.tnumberRelationService.updateProcess(list, tm);
						} else {
							tm.setToMobile(toMobile);
							tm.setToTel(toTel);
							this.toperatorMobileService.update(tm);
						}
					}
					
				} else {
					tm.setToMobile(toMobile);
					tm.setToTel(toTel);
					this.toperatorMobileService.update(tm);
				}
				return ResultManager.createSuccessResult("操作成功！");
			}
			return ResultManager.createFailResult("数据信息不存在！");
		} catch (ProcessException e) {
			e.printStackTrace();
			return ResultManager.createFailResult(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("操作异常！");
		}
	}
}
