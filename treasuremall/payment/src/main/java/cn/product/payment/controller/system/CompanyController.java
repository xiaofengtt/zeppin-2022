/**
 * 
 */
package cn.product.payment.controller.system;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.product.payment.controller.base.ActionParam;
import cn.product.payment.controller.base.ActionParam.DataType;
import cn.product.payment.controller.base.BaseController;
import cn.product.payment.controller.base.Result;
import cn.product.payment.controller.base.ResultManager;
import cn.product.payment.entity.Admin;
import cn.product.payment.entity.Company;
import cn.product.payment.entity.Company.CompanyStatus;
import cn.product.payment.entity.CompanyAccount;
import cn.product.payment.service.AdminService;
import cn.product.payment.service.CompanyAccountService;
import cn.product.payment.service.CompanyService;
import cn.product.payment.util.PaymentUtil;
import cn.product.payment.util.Utlity;
import cn.product.payment.vo.system.CompanyVO;


/**
 * 用户充值
 */

@Controller
@RequestMapping(value = "/system/company")
public class CompanyController extends BaseController {

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private CompanyAccountService companyAccountService;
	
	@Autowired
	private AdminService adminService;
	
	/**
	 * 根据条件查询
	 * @param name
	 * @param code
	 * @param type
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "name", message="名称", type = DataType.STRING)
	@ActionParam(key = "code", message="编码", type = DataType.STRING)
	@ActionParam(key = "type", message="类型", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sort", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String name, String code, String status, String type, Integer pageNum, Integer pageSize, String sort) {
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("name", name);
		searchMap.put("code", code);
		searchMap.put("type", type);
		searchMap.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		Integer totalResultCount = companyService.getCountByParams(searchMap);
		List<Company> list = companyService.getListByParams(searchMap);
		
		List<CompanyVO> voList = new ArrayList<CompanyVO>();
		for(Company company : list){
			CompanyVO vo = new CompanyVO(company);
			
			CompanyAccount ca = this.companyAccountService.get(company.getUuid());
			vo.setBalance(ca.getBalance());
			vo.setBalanceLock(ca.getBalanceLock());
			
			voList.add(vo);
		}
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {
		Company company = companyService.get(uuid);
		if (company == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		CompanyVO vo = new CompanyVO(company);
		
		CompanyAccount ca = this.companyAccountService.get(company.getUuid());
		vo.setBalance(ca.getBalance());
		vo.setBalanceLock(ca.getBalanceLock());
		
		return ResultManager.createDataResult(vo);
	}
	
	/**
	 * 创建
	 * @param uuid
	 * @param name
	 * @param companyPrivate
	 * @param companyPublic
	 * @param whiteUrl
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "companyPrivate", message="商户私钥", type = DataType.STRING)
	@ActionParam(key = "companyPublic", message="商户公钥", type = DataType.STRING)
	@ActionParam(key = "whiteUrl", message="白名单", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(Company company) {
		Admin admin = getSystemAdmin();
		
		if(!CompanyStatus.NORMAL.equals(company.getStatus()) && !CompanyStatus.DISABLE.equals(company.getStatus())){
			return ResultManager.createFailResult("状态错误！");
		}
		
		if(!Utlity.checkStringNull(company.getCompanyPrivate()) && !Utlity.checkStringNull(company.getCompanyPublic())){
			if(!PaymentUtil.checkKeys(company.getCompanyPrivate(), company.getCompanyPublic())){
				return ResultManager.createFailResult("商户秘钥不匹配！");
			}
		}
		
		Map<String, String> result = companyService.getCompanyKeys(company);
		company.setSystemPrivate(result.get("privateKey"));
		company.setSystemPublic(result.get("publicKey"));
		
		if(!PaymentUtil.checkKeys(company.getSystemPrivate(), company.getSystemPublic())){
			return ResultManager.createFailResult("创建出错,请重新再试！");
		}
		
		company.setUuid(UUID.randomUUID().toString());
		company.setCreator(admin.getUuid());
		company.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		this.companyService.insertCompany(company);
		return ResultManager.createSuccessResult("创建成功！");
	}
	
	/**
	 * 修改
	 * @param uuid
	 * @param name
	 * @param whiteUrl
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "name", message="名称", type = DataType.STRING, required = true)
	@ActionParam(key = "whiteUrl", message="白名单", type = DataType.STRING, required = true)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result edit(Company company) {
		if(!CompanyStatus.NORMAL.equals(company.getStatus()) && !CompanyStatus.DISABLE.equals(company.getStatus())){
			return ResultManager.createFailResult("状态错误！");
		}
		
		Company c = companyService.get(company.getUuid());
		if (c == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		
		c.setName(company.getName());
		c.setWhiteUrl(company.getWhiteUrl());
		c.setStatus(company.getStatus());
		this.companyService.update(c);
		return ResultManager.createSuccessResult("修改成功！");
	}
	
	/**
	 * 变更状态
	 * @param uuid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, maxLength = 36)
	@ActionParam(key = "status", message="状态", type = DataType.STRING, required = true)
	@ResponseBody
	public Result changeStatus(String uuid, String status) {
		if(!CompanyStatus.NORMAL.equals(status) && !CompanyStatus.DISABLE.equals(status)){
			return ResultManager.createFailResult("状态错误！");
		}
		
		Company company = companyService.get(uuid);
		if (company == null) {
			return ResultManager.createFailResult("不存在该条记录！");
		}
		
		company.setStatus(status);
		this.companyService.update(company);
		return ResultManager.createSuccessResult("操作成功！");
	}
}
