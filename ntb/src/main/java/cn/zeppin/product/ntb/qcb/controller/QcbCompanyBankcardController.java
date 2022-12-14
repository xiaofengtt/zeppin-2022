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
 * ????????????????????????
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
	 * ??????????????????????????????????????? 
	 * @param bank
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "bank", message="??????", type = DataType.STRING)
	@ActionParam(key = "status", message="??????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="??????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "pageSize", message="????????????", type = DataType.NUMBER, required = true)
	@ActionParam(key = "sorts", message="????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(String bank, String status, Integer pageNum, Integer pageSize, String sorts) {
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
				
		//????????????
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("qcbCompany", qcbAdmin.getQcbCompany());
		searchMap.put("bank", bank);
		searchMap.put("status", status);
		
		//???????????????????????????????????????????????????
		Integer totalResultCount = qcbCompanyBankcardService.getCount(searchMap);
		//????????????????????????????????????????????????
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
	 * ?????????????????????????????????
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result get(String uuid) {		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//???????????????????????????
		QcbCompanyBankcard qcb = qcbCompanyBankcardService.get(uuid);
		if (qcb == null) {
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(!qcb.getQcbCompany().equals(qcbAdmin.getQcbCompany())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		//????????????????????????
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
	 * ?????????????????????????????????
	 * @param name
	 * @param logo
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "bank", message="??????", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "branchBank", message="??????", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "area", message="??????", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "bindingBankCard", message="????????????", type = DataType.STRING, required = true, maxLength = 64)
	@ActionParam(key = "bindingCardPhone", message="???????????????", type = DataType.STRING, required = true, maxLength = 20)
	@ActionParam(key = "bindingCardType", message="????????????", type = DataType.STRING, required = true, maxLength = 10)
	@ActionParam(key = "code", message="???????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result add(String bank, String branchBank, String area, String bindingBankCard, 
			String bindingCardPhone, String bindingCardType, String code) {
		
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
		
		//???????????????
		if(bindingBankCard.length() < 12){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
//		//???????????????
//		if(!BankCardUtlity.checkBankCard(bindingBankCard)){
//			return ResultManager.createFailResult("??????????????????????????????");
//		}
		if(!Utlity.isNumeric(bindingBankCard)){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		//??????????????????
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("bindingBankCard", bindingBankCard);
		inputParams.put("qcbCompany", qcbAdmin.getQcbCompany());
		int count = this.qcbCompanyBankcardService.getCount(inputParams);
		if(count > 0){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		//???????????????????????????
		if (!Utlity.isMobileNO(bindingCardPhone)) {
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		//???????????????????????????
		QcbCompanyBankcard qcb = new QcbCompanyBankcard();
		qcb.setUuid(UUID.randomUUID().toString());
		
		//????????????????????????
		QcbCompanyAccount qc = this.qcbCompanyAccountService.get(qcbAdmin.getQcbCompany());
		if(qc != null){
			qcb.setQcbCompany(qcbAdmin.getQcbCompany());
		}else{
			return ResultManager.createFailResult("??????????????????");
		}
		
		//??????????????????
		String bindingCardCardholder = "";
		if(QcbCompanyBankcardType.COMPANY.equals(bindingCardType)){
			bindingCardCardholder = qc.getName();
		}else if(QcbCompanyBankcardType.PERSONAL.equals(bindingCardType)){
			bindingCardCardholder = qc.getCorporation();
		}else{
			return ResultManager.createFailResult("????????????????????????");
		}
		
		//????????????????????????
		Bank b = this.bankService.get(bank);
		if(b != null){
			qcb.setBank(bank);
		}else{
			return ResultManager.createFailResult("??????????????????");
		}
		
		//????????????????????????????????????
//		String bankName = BankCardUtlity.getname(bindingBankCard);
//		if (bankName != null && !"".equals(bankName)) {
//			if((bankName.indexOf(b.getName()) == -1) && (b.getName().indexOf(bankName) == -1 )
//					&& (bankName.indexOf(b.getShortName()) == -1) && (b.getShortName().indexOf(bankName) == -1 )){
//				return ResultManager.createFailResult("????????????????????????????????????");
//			}
//		}
		
		//????????????????????????
		BranchBank bb = this.branchBankService.get(branchBank);
		if(bb != null){
			qcb.setBranchBank(branchBank);
		}else{
			return ResultManager.createFailResult("??????????????????");
		}
		
		//????????????????????????
		BkArea ba = this.bkAreaService.get(area);
		if(ba != null){
			if(ba.getLevel() < 2){
				return ResultManager.createFailResult("??????????????????");
			}else{
				qcb.setBkArea(area);
			}
		}else{
			return ResultManager.createFailResult("??????????????????");
		}
		
		qcb.setBindingCardCardholder(bindingCardCardholder);
		qcb.setBindingBankCard(bindingBankCard);
		qcb.setBindingCardPhone(bindingCardPhone);
		qcb.setBindingCardType(bindingCardType);
		qcb.setStatus(QcbCompanyBankcardStatus.NORMAL);
		qcb.setCreator(qcbAdmin.getUuid());
		qcb.setCreatetime(new Timestamp(System.currentTimeMillis()));
		
		//?????????????????????
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
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(!mc.getMobile().equals(qcbAdmin.getMobile())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(Utlity.checkCodeTime(mc.getCreatetime().getTime(), System.currentTimeMillis())){
			return ResultManager.createFailResult("??????????????????");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		mc.setStatus(MobileCodeStatus.DISABLE);
		this.mobileCodeService.update(mc);
		
		this.qcbCompanyBankcardService.insert(qcb);
		
		return ResultManager.createSuccessResult("???????????????");
	}
	
	/**
	 * ?????????????????????????????????
	 * @param uuid
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message="uuid", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ActionParam(key = "code", message="???????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid, String code) {
		//??????????????????
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		AdminVO qcbAdmin = (AdminVO) session.getAttribute("currentQcbOperator");
				
		//???????????????????????????
		QcbCompanyBankcard qcb = qcbCompanyBankcardService.get(uuid);
		if(qcb == null){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		//?????????????????????
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
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		if(!mc.getMobile().equals(qcbAdmin.getMobile())){
			return ResultManager.createFailResult("????????????????????????");
		}
		
		if(Utlity.checkCodeTime(mc.getCreatetime().getTime(), System.currentTimeMillis())){
			return ResultManager.createFailResult("??????????????????");
		}
		
		if(!code.equals(mc.getCode())){
			return ResultManager.createFailResult("??????????????????????????????");
		}
		
		mc.setStatus(MobileCodeStatus.DISABLE);
		this.mobileCodeService.update(mc);
		
		//???????????????????????????
		this.qcbCompanyBankcardService.delete(qcb);
		return ResultManager.createSuccessResult("???????????????");
	}
}
