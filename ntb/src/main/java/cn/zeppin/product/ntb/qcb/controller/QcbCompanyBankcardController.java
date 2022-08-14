/**
 * 
 */
package cn.zeppin.product.ntb.qcb.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IBkAreaService;
import cn.zeppin.product.ntb.backadmin.service.api.IBranchBankService;
import cn.zeppin.product.ntb.backadmin.service.api.IMobileCodeService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Bank;
import cn.zeppin.product.ntb.core.entity.BkArea;
import cn.zeppin.product.ntb.core.entity.BranchBank;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbCompanyAccount;
import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard;
import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard.QcbCompanyBankcardStatus;
import cn.zeppin.product.ntb.core.entity.QcbCompanyBankcard.QcbCompanyBankcardType;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyAccountService;
import cn.zeppin.product.ntb.qcb.service.api.IQcbCompanyBankcardService;
import cn.zeppin.product.ntb.qcb.vo.AdminVO;
import cn.zeppin.product.ntb.qcb.vo.QcbCompanyBankcardVO;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 * 企财宝企业银行卡
 */

@Controller
@RequestMapping(value = "/qcb/companyBankcard")
public class QcbCompanyBankcardController extends BaseController {
	
	@Autowired
	private IQcbCompanyBankcardService qcbCompanyBankcardService;
	
	@Autowired
	private IQcbCompanyAccountService qcbCompanyAccountService;
	
	@Autowired
	private IBankService bankService;
	
	@Autowired
	private IBranchBankService branchBankService;
	
	@Autowired
	private IBkAreaService bkAreaService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IMobileCodeService mobileCodeService;
	
	/**
	 * 根据条件查询企业银行卡信息 
	 * @param bank
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "bank", message="银行", type = DataType.STRING)
	@ActionParam(key = "status", message="状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String bank, String status, Integer pageNum, Integer pageSize, String sorts) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
				
		//查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompany", qcbAdmin.getQcbCompany());
		searchMap.put("bank", bank);
		searchMap.put("status", status);
		
		//查询符合条件的企业银行卡信息的总数
		Integer totalResultCount = qcbCompanyBankcardService.getCount(searchMap);
		//查询符合条件的企业银行卡信息列表
		List<Entity> list = qcbCompanyBankcardService.getListForPage(searchMap, pageNum, pageSize, sorts, QcbCompanyBankcard.class);
		
		List<QcbCompanyBankcardVO> listvo = new ArrayList<QcbCompanyBankcardVO>();
		
		for(Entity e : list){
			QcbCompanyBankcard qcb = (QcbCompanyBankcard) e;
			QcbCompanyBankcardVO qcbVO = new QcbCompanyBankcardVO(qcb);
			
			if(!Utlity.checkStringNull(qcbVO.getQcbCompany())){
				QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcbVO.getQcbCompany());
				if(qca != null){
					qcbVO.setQcbCompanyName(qca.getName());
				}
			}
			
			if(!Utlity.checkStringNull(qcbVO.getBank())){
				Bank b = this.bankService.get(qcbVO.getBank());
				if(b != null){
					qcbVO.setBankName(b.getShortName());
					if(!Utlity.checkStringNull(b.getLogo())){
						Resource r = this.resourceService.get(b.getLogo());
						if(r!=null){
							qcbVO.setBankIconUrl(r.getUrl());
						}
					}
				}
			}
			
			if(!Utlity.checkStringNull(qcbVO.getBranchBank())){
				BranchBank bb = this.branchBankService.get(qcbVO.getBranchBank());
				if(bb != null){
					qcbVO.setBranchBankName(bb.getName());
				}
			}

			if(!Utlity.checkStringNull(qcbVO.getBkArea())){
				qcbVO.setBkAreaName(this.bkAreaService.getFullName(qcbVO.getBkArea()));
			}
			
			listvo.add(qcbVO);
		}
		
		return ResultManager.createDataResult(listvo, pageNum, pageSize, totalResultCount);
	}
	
	/**
	 * 获取一条企业银行卡信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//获取企业银行卡信息
		QcbCompanyBankcard qcb = qcbCompanyBankcardService.get(uuid);
		if (qcb == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		if(!qcb.getQcbCompany().equals(qcbAdmin.getQcbCompany())){
			return ResultManager.createFailResult("您无权查看此银行卡！");
		}
		
		//界面返回封装对象
		QcbCompanyBankcardVO qcbVO = new QcbCompanyBankcardVO(qcb);

		if(!Utlity.checkStringNull(qcbVO.getQcbCompany())){
			QcbCompanyAccount qca = this.qcbCompanyAccountService.get(qcbVO.getQcbCompany());
			if(qca != null){
				qcbVO.setQcbCompanyName(qca.getName());
			}
		}
		
		if(!Utlity.checkStringNull(qcbVO.getBank())){
			Bank b = this.bankService.get(qcbVO.getBank());
			if(b != null){
				qcbVO.setBankName(b.getShortName());
				qcbVO.setBankIconUrl(b.getLogo());
			}
		}
		
		if(!Utlity.checkStringNull(qcbVO.getBranchBank())){
			BranchBank bb = this.branchBankService.get(qcbVO.getBranchBank());
			if(bb != null){
				qcbVO.setBranchBankName(bb.getName());
			}
		}

		if(!Utlity.checkStringNull(qcbVO.getBkArea())){
			qcbVO.setBkAreaName(this.bkAreaService.getFullName(qcbVO.getBkArea()));
		}
		
		return ResultManager.createDataResult(qcbVO);
	}
	
	/**
	 * 添加一条企业银行卡信息
	 * @param name
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "bank", message="银行", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "branchBank", message="支行", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "area", message="地区", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "bindingBankCard", message="银行卡号", type = DataType.STRING, required = true, maxLength = 64)
	@ActionParam(key = "bindingCardPhone", message="预留手机号", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "bindingCardType", message="账户类型", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "code", message="短信验证码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String bank, String branchBank, String area, String bindingBankCard, 
			String bindingCardPhone, String bindingCardType, String code) {
		
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//银行卡长度
		if(bindingBankCard.length() < 12){
			return ResultManager.createFailResult("请填写正确的银行卡号");
		}
		
//		//银行卡规则
//		if(!BankCardUtlity.checkBankCard(bindingBankCard)){
//			return ResultManager.createFailResult("请填写正确的银行卡号");
//		}
		if(!Utlity.isNumeric(bindingBankCard)){
			return ResultManager.createFailResult("请填写正确的银行卡号");
		}
		
		//校验重复绑定
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("bindingBankCard", bindingBankCard);
		inputParams.put("qcbCompany", qcbAdmin.getQcbCompany());
		int count = this.qcbCompanyBankcardService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("该银行卡已经绑定过！");
		}
		
		//校验预留手机号规则
		if (!Utlity.isMobileNO(bindingCardPhone)) {
			return ResultManager.createFailResult("预留手机号输入错误！");
		}
		
		//创建企业银行卡信息
		QcbCompanyBankcard qcb = new QcbCompanyBankcard();
		qcb.setUuid(UUID.randomUUID().toString());
		
		//校验企业是否存在
		QcbCompanyAccount qc = this.qcbCompanyAccountService.get(qcbAdmin.getQcbCompany());
		if(qc != null){
			qcb.setQcbCompany(qcbAdmin.getQcbCompany());
		}else{
			return ResultManager.createFailResult("企业不正确！");
		}
		
		//校验账户类型
		String bindingCardCardholder = "";
		if(QcbCompanyBankcardType.COMPANY.equals(bindingCardType)){
			bindingCardCardholder = qc.getName();
		}else if(QcbCompanyBankcardType.PERSONAL.equals(bindingCardType)){
			bindingCardCardholder = qc.getCorporation();
		}else{
			return ResultManager.createFailResult("账户类型不正确！");
		}
		
		//校验银行是否存在
		Bank b = this.bankService.get(bank);
		if(b != null){
			qcb.setBank(bank);
		}else{
			return ResultManager.createFailResult("银行不正确！");
		}
		
		//校验银行卡与银行是否匹配
//		String bankName = BankCardUtlity.getname(bindingBankCard);
//		if (bankName != null && !"".equals(bankName)) {
//			if((bankName.indexOf(b.getName()) == -1) && (b.getName().indexOf(bankName) == -1 )
//					&& (bankName.indexOf(b.getShortName()) == -1) && (b.getShortName().indexOf(bankName) == -1 )){
//				return ResultManager.createFailResult("银行卡与所属银行不匹配！");
//			}
//		}
		
		//校验支行是否存在
		BranchBank bb = this.branchBankService.get(branchBank);
		if(bb != null){
			qcb.setBranchBank(branchBank);
		}else{
			return ResultManager.createFailResult("支行不正确！");
		}
		
		//校验地区是否存在
		BkArea ba = this.bkAreaService.get(area);
		if(ba != null){
			if(ba.getLevel() < 2){
				return ResultManager.createFailResult("地区不正确！");
			}else{
				qcb.setBkArea(area);
			}
		}else{
			return ResultManager.createFailResult("地区不正确！");
		}
		
		qcb.setBindingCardCardholder(bindingCardCardholder);
		qcb.setBindingBankCard(bindingBankCard);
		qcb.setBindingCardPhone(bindingCardPhone);
		qcb.setBindingCardType(bindingCardType);
		qcb.setStatus(QcbCompanyBankcardStatus.NORMAL);
		qcb.setCreator(qcbAdmin.getUuid());
		qcb.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		//验证短信验证码
		Map<String, String> codeParams = new HashMap<String, String>();
		codeParams.put("mobile", qcbAdmin.getMobile());
		codeParams.put("status", MobileCodeStatus.NORMAL);
		codeParams.put("type", MobileCodeTypes.QCB_BANKCARD_ADD);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(codeParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		if(!mc.getMobile().equals(qcbAdmin.getMobile())){
			return ResultManager.createFailResult("手机号输入错误！");
		}
		
		if(Utlity.checkCodeTime(mc.getCreatetime().getTime(), System.currentTimeMillis())){
			return ResultManager.createFailResult("验证码超时！");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		mc.setStatus(MobileCodeStatus.DISABLE);
		this.mobileCodeService.update(mc);
		
		this.qcbCompanyBankcardService.insert(qcb);
		
		return ResultManager.createSuccessResult("保存成功！");
	}
	
	/**
	 * 删除一条企业银行卡信息
	 * @param uuid
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "code", message="短信验证码", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid, String code) {
		//取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
				
		//获取企业银行卡信息
		QcbCompanyBankcard qcb = qcbCompanyBankcardService.get(uuid);
		if(qcb == null){
			return ResultManager.createFailResult("该条数据不存在！");
		}
		
		//验证短信验证码
		Map<String, String> codeParams = new HashMap<String, String>();
		codeParams.put("mobile", qcbAdmin.getMobile());
		codeParams.put("status", MobileCodeStatus.NORMAL);
		codeParams.put("type", MobileCodeTypes.QCB_BANKCARD_DELETE);
		List<Entity> lstMobileCode = this.mobileCodeService.getListForPage(codeParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		if(!mc.getMobile().equals(qcbAdmin.getMobile())){
			return ResultManager.createFailResult("手机号输入错误！");
		}
		
		if(Utlity.checkCodeTime(mc.getCreatetime().getTime(), System.currentTimeMillis())){
			return ResultManager.createFailResult("验证码超时！");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("短信验证码输入错误！");
		}
		
		mc.setStatus(MobileCodeStatus.DISABLE);
		this.mobileCodeService.update(mc);
		
		//删除企业银行卡信息
		this.qcbCompanyBankcardService.delete(qcb);
		return ResultManager.createSuccessResult("删除成功！");
	}
}
