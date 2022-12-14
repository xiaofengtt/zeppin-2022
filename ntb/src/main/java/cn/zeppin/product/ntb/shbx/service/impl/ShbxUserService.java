/**
 * 
 */
package cn.zeppin.product.ntb.shbx.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zeppin.product.ntb.backadmin.dao.api.IAliCertificationDAO;
import cn.zeppin.product.ntb.backadmin.dao.api.IMobileCodeDAO;
import cn.zeppin.product.ntb.core.entity.AliCertification;
import cn.zeppin.product.ntb.core.entity.MobileCode;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeCreatorType;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeStatus;
import cn.zeppin.product.ntb.core.entity.MobileCode.MobileCodeTypes;
import cn.zeppin.product.ntb.core.entity.QcbEmployee.QcbEmployeeStatus;
import cn.zeppin.product.ntb.core.entity.ShbxUser;
import cn.zeppin.product.ntb.core.entity.ShbxUser.ShbxUserStatus;
import cn.zeppin.product.ntb.core.entity.base.Entity;
import cn.zeppin.product.ntb.core.exception.TransactionException;
import cn.zeppin.product.ntb.core.service.base.BaseService;
import cn.zeppin.product.ntb.shbx.dao.api.IShbxUserDAO;
import cn.zeppin.product.ntb.shbx.service.api.IShbxUserService;
import cn.zeppin.product.utility.IDCardUtil;
import cn.zeppin.product.utility.JuHeUtility;
import cn.zeppin.product.utility.MD5;
import cn.zeppin.product.utility.SendSmsNew;
import cn.zeppin.product.utility.Utlity;

/**
 * @author hehe
 *
 */
@Service
public class ShbxUserService extends BaseService implements IShbxUserService {

	@Autowired
	private IShbxUserDAO shbxUserDAO;
	
	@Autowired
	private IMobileCodeDAO mobileCodeDAO;
	
	@Autowired
	private IAliCertificationDAO aliCertificationDao;
	
	@Override
	public ShbxUser insert(ShbxUser shbxUser) {
		return shbxUserDAO.insert(shbxUser);
	}

	@Override
	public ShbxUser delete(ShbxUser shbxUser) {
		shbxUser.setStatus(ShbxUserStatus.DELETED);
		return shbxUserDAO.update(shbxUser);
	}

	@Override
	public ShbxUser update(ShbxUser shbxUser) {
		return shbxUserDAO.update(shbxUser);
	}

	@Override
	public ShbxUser get(String uuid) {
		return shbxUserDAO.get(uuid);
	}
	
	/**
	 * ??????????????????????????????(??????????????????),
	 * @param inputParams
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @param resultClass
	 * @return  List<Entity>
	 */
	@Override
	public List<Entity> getListForPage(Map<String, String> inputParams, Integer pageNum, Integer pageSize, String sorts, Class<? extends Entity> resultClass) {
		return shbxUserDAO.getListForPage(inputParams, pageNum, pageSize, sorts, resultClass);
	}

	/**
	 * ??????????????????????????????
	 * @param inputParams
	 * @return 
	 */
	@Override
	public Integer getCount(Map<String, String> inputParams) {
		return shbxUserDAO.getCount(inputParams);
	}

	@Override
	public ShbxUser getByMobile(String mobile) {
		return shbxUserDAO.getByMobile(mobile);
	}

	@Override
	public ShbxUser getByOpenId(String openId) {
		return shbxUserDAO.getByOpenId(openId);
	}
	
	@Override
	public boolean isExistShbxUserByMobile(String mobile, String uuid) {
		return shbxUserDAO.isExistShbxUserByMobile(mobile, uuid);
	}

	@Override
	public boolean isExistShbxUserByIdcard(String idcard, String uuid) {
		return shbxUserDAO.isExistShbxUserByIdcard(idcard, uuid);
	}
	
	public HashMap<String, Object> register(String token,String mobile) throws TransactionException{
		HashMap<String,Object> result = new HashMap<String,Object>();
		String message = "";
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			message = "?????????????????????????????????";
			throw new TransactionException(message);
		}
		
		if (!Utlity.isMobileNO(mobile)) {
			message = "??????????????????";
			throw new TransactionException(message);
		}
		
		String timestamp = token.substring(2,15);
		if(timestamp == null || "".equals(timestamp)){
			throw new TransactionException(message);
		}
		long time = Long.parseLong(timestamp);
		long nowTime = System.currentTimeMillis();
		if(time <= nowTime){
			if(Utlity.checkTime(time, nowTime)){
				message = "?????????????????????????????????";
				throw new TransactionException(message);
			}
		} else {
			message = "?????????????????????????????????";
			throw new TransactionException(message);
		}
		
		if(this.shbxUserDAO.isExistShbxUserByMobile(mobile, null)){
			message = "??????????????????";
			throw new TransactionException(message);
		}
		
		String md5Str = token.substring(15);
		if(md5Str == null || "".equals(md5Str)){
			throw new TransactionException(message);
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", mobile);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<Entity> lstMobileCode = this.mobileCodeDAO.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			message = "????????????????????????";
			throw new TransactionException(message);
		}
		
		if(!mc.getMobile().equals(mobile)){
			message = "????????????????????????";
			throw new TransactionException(message);
		}
		
		if(!MobileCodeTypes.REGISTER.equals(mc.getType())){
			message = "????????????????????????";
			throw new TransactionException(message);
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			message = "??????????????????";
			throw new TransactionException(message);
		}
		
		String realMD5Str = MD5.getMD5(Utlity.KEY_QCB+time+mc.getMobile()+mc.getCode());
		if(realMD5Str.equals(md5Str)){//??????
			
			ShbxUser su = new ShbxUser();
			su.setCreatetime(new Timestamp(System.currentTimeMillis()));
			su.setMobile(mobile);
			su.setStatus(QcbEmployeeStatus.NORMAL);
			su.setUuid(UUID.randomUUID().toString());
			su.setStatus(QcbEmployeeStatus.NORMAL);
			su.setCurrentAccount(BigDecimal.ZERO);
			su.setCurrentAccountYesterday(BigDecimal.ZERO);
			su.setFlagCurrent(false);
			su.setSecretPasswordFlag(false);
			su.setRealnameAuthFlag(false);
			su.setTotalInvest(BigDecimal.valueOf(0));
			su.setTotalReturn(BigDecimal.valueOf(0));
			su.setAccountBalance(BigDecimal.valueOf(0));
			this.shbxUserDAO.insert(su);
			
			mc.setStatus(MobileCodeStatus.DISABLE);
			this.mobileCodeDAO.update(mc);
//			InvestorInformation iii = new InvestorInformation();
//			iii.setCreator(Investor.getUuid());
//			iii.setStatus(InvestorInformationStatus.UNREAD);
//			iii.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			iii.setUuid(UUID.randomUUID().toString());
//			iii.setInfoText(InvestorInformationModel.INFO_MODEL_REGISTER);
//			iii.setInfoTitle(InvestorInformationTitle.REGISTER);
//			iii.setInvestor(Investor.getUuid());
//			this.investorInformationDAO.insert(iii);
			message = "????????????!";
			result.put("result", true);
			result.put("shbxUser", su);
			result.put("message", message);
			return result;
		} else {
			message = "???????????????????????????????????????";
			throw new TransactionException(message);
		}
	}
	
	@Override
	public HashMap<String, Object> loginBycode(String token, String phone) throws TransactionException {
		HashMap<String,Object> result = new HashMap<String,Object>();
		String message = "????????????????????????";
		String deviceNumber = token.substring(0,2);
		if(deviceNumber == null || "".equals(deviceNumber)){
			message = "????????????????????????????????????";
			throw new TransactionException(message);
		}
		
		String timestamp = token.substring(2,15);
		if(timestamp == null || "".equals(timestamp)){
			throw new TransactionException(message);
		}
		long time = Long.parseLong(timestamp);
		long nowTime = System.currentTimeMillis();
		if(time <= nowTime){
			if(Utlity.checkLessTime(time, nowTime)){
				throw new TransactionException(message);
			}
		} else {
			throw new TransactionException(message);
		}
		
		ShbxUser su = this.shbxUserDAO.getByMobile(phone);
		if(su == null){
			message = "???????????????";
			throw new TransactionException(message);
		}
		String md5Str = token.substring(15);
		if(md5Str == null || "".equals(md5Str)){
			throw new TransactionException(message);
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("mobile", phone);
		inputParams.put("status", MobileCodeStatus.NORMAL);
		List<Entity> lstMobileCode = this.mobileCodeDAO.getListForPage(inputParams, -1, -1, null, MobileCode.class);
		MobileCode mc = null;
		if(lstMobileCode != null && lstMobileCode.size() > 0){
			mc = (MobileCode) lstMobileCode.get(0);
		}
		if(mc == null){
			message = "????????????????????????";
			throw new TransactionException(message);
		}
		
		if(!mc.getMobile().equals(phone)){
			message = "????????????????????????";
			throw new TransactionException(message);
		}
		
		if(!MobileCodeTypes.REGISTER.equals(mc.getType())){
			message = "????????????????????????";
			throw new TransactionException(message);
		}
		
		if((System.currentTimeMillis()-mc.getCreatetime().getTime()) > 300000){
			message = "??????????????????";
			throw new TransactionException(message);
		}
		
		String realMD5Str = MD5.getMD5(Utlity.KEY_QCB+time+mc.getMobile()+mc.getCode());
		if(md5Str.equals(realMD5Str)){//????????????
//			su = this.shbxUserDAO.get(su.getUuid());
//			su.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
//			this.shbxUserDAO.update(su);
			message = "????????????";
			result.put("result", true);
			result.put("message", message);
			result.put("shbxUser", su);
			return result;
		} else {
			throw new TransactionException(message);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void logincertification(String idcard, String username, ShbxUser shbxUser) throws TransactionException {
		HashMap<String, Object> result = new HashMap<String, Object>();
		if(!shbxUser.getRealnameAuthFlag()){
			result = JuHeUtility.certification(idcard, username);
			if(result.get("request") != null && (Boolean)result.get("request")){
				//????????????
				Map<String, Object> response = (Map<String, Object>) result.get("response");
				if(response != null && !response.isEmpty()){
					AliCertification ac = new AliCertification();
					ac.setUuid(UUID.randomUUID().toString());
					ac.setCreatetime(new Timestamp(System.currentTimeMillis()));
					ac.setCreator(shbxUser.getUuid());
					
					ac.setBinNo(response.get("bizNo")==null ? "???":response.get("bizNo").toString());
					ac.setVerifyCode(response.get("verifyCode")==null ? "???":response.get("verifyCode").toString());
					ac.setProductCode(response.get("product_code")==null ? "???":response.get("product_code").toString());
					ac.setTranscationid(response.get("transAutoIncIdx")==null ? "???":response.get("transAutoIncIdx").toString());
					ac.setInscription("??????????????????????????????????????????");
					ac.setCode(response.get("code")==null ? "???":response.get("code").toString());
					ac.setMsg(response.get("msg")==null ? "???":response.get("msg").toString());
					ac.setSubCode(response.get("sub_code")==null ? "???":response.get("sub_code").toString());
					ac.setSubMsg(response.get("sub_msg")==null ? "???":response.get("sub_msg").toString());
					this.aliCertificationDao.insert(ac);
				}
			} else {
				throw new TransactionException(result.get("message").toString());
			}
			if((Boolean)result.get("result")){
				//????????????+??????
				shbxUser.setIdcard(idcard);
				shbxUser.setRealname(username);
				shbxUser.setRealnameAuthFlag(true);
				//????????????
				if(IDCardUtil.getSex(idcard) == 1){
					shbxUser.setSex(Utlity.SEX_MAN);
				} else {
					shbxUser.setSex(Utlity.SEX_WOMAN);
				}
				this.shbxUserDAO.update(shbxUser);
			} else {
				throw new TransactionException(result.get("message").toString());
			}
		}
	}

	@Override
	public HashMap<String, Object> updateBindingBankcard(String phone,
			String bankcard, String idcard, String username, ShbxUser shbxUser,
			String billDevice) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		//??????????????????????????????????????????????????????????????????????????????
//		result = AliUtlity.certification(investor.getIdcard(), username, phone, bankcard);
//		if(result.get("request") != null && (Boolean)result.get("request")){
//			//????????????
//			Map<String, Object> response = (Map<String, Object>) result.get("response");
//			if(response != null && !response.isEmpty()){
//				AliCertification ac = new AliCertification();
//				ac.setUuid(UUID.randomUUID().toString());
//				ac.setCreatetime(new Timestamp(System.currentTimeMillis()));
//				ac.setCreator(investor.getUuid());
//				
//				ac.setBinNo(response.get("bizNo")==null ? "???":response.get("bizNo").toString());
//				ac.setVerifyCode(response.get("verifyCode")==null ? "???":response.get("verifyCode").toString());
//				ac.setProductCode(response.get("product_code")==null ? "???":response.get("product_code").toString());
//				ac.setTranscationid(response.get("transAutoIncIdx")==null ? "???":response.get("transAutoIncIdx").toString());
//				ac.setInscription("??????????????????????????????????????????");
//				ac.setCode(response.get("code")==null ? "???":response.get("code").toString());
//				ac.setMsg(response.get("msg")==null ? "???":response.get("msg").toString());
//				ac.setSubCode(response.get("sub_code")==null ? "???":response.get("sub_code").toString());
//				ac.setSubMsg(response.get("sub_msg")==null ? "???":response.get("sub_msg").toString());
//				this.aliCertificationDao.insert(ac);
//			}
//		}
//		if((Boolean)result.get("result")){
//			//?????????????????????????????????????????????????????????
//			Map<String, Object> returnMap = ChanPayUtil.nmg_biz_api_auth_req(investor.getUuid(), investor.getIdcard(), username, phone, bankcard, billDevice);
//			OrderinfoByThirdparty obt = new OrderinfoByThirdparty();
//			obt.setUuid(UUID.randomUUID().toString());
//			obt.setType(OrderinfoByThirdpartyType.CHANPAY);
//			obt.setInvestor(investor.getUuid());
//			obt.setOrderNum(returnMap.get("TrxId").toString());
//			obt.setBody("??????????????????????????????");
//			obt.setTotalFee(BigDecimal.ZERO);
//			obt.setPaySource(MD5.getMD5(investor.getUuid()));
//			obt.setStatus(returnMap.get("Status").toString());
//			obt.setCreatetime(new Timestamp(System.currentTimeMillis()));
//			if(ChanPayUtil.RETSTATUS_SUCCESS.equals(returnMap.get("Status").toString()) || ChanPayUtil.RETSTATUS_RROSESS.equals(returnMap.get("Status").toString())){
//				obt.setPrepayId(returnMap.get("OrderTrxid").toString());
//				result.put("result", true);
//				result.put("message", returnMap.get("RetMsg") == null ? "????????????" : returnMap.get("RetMsg").toString());
//			} else {
//				String prepayId = returnMap.get("OrderTrxid") == null ? "" : returnMap.get("OrderTrxid").toString();
//				obt.setPrepayId(prepayId);
//				result.put("result", false);
//				String message = returnMap.get("RetMsg") == null ? "????????????" : returnMap.get("RetMsg").toString();
//				if(message.indexOf("???????????????????????????") > -1){
//					message = "???????????????????????????";
//				}
//				result.put("message", message);
//			}
//			
//			obt.setErrCode(returnMap.get("RetCode") == null ? "" : returnMap.get("RetCode").toString());
////			if("CARD_IS_BIND".equals(obt.getErrCode())){
////				result.put("result", true);
////				result.put("message", "????????????");
////			}
//			obt.setErrCodeDes(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
//			obt.setMessage(returnMap.get("RetMsg") == null ? "" : returnMap.get("RetMsg").toString());
//			result.put("orderNum", obt.getUuid());
//			this.orderinfoByThirdpartyDAO.insert(obt);
			
			String codestr = Utlity.getCaptcha();
			String content = "????????????"+codestr+"???????????????????????????????????????????????????????????????????????????5???????????????????????????????????????????????????????????????";
			
			
			String results = SendSmsNew.sendSms4Shbx(content, phone);
			if ("0".equals(results.split("_")[0])) {
				
				MobileCode mc = new MobileCode();
				mc.setUuid(UUID.randomUUID().toString());
				mc.setCode(codestr);
				mc.setContent(content);
				mc.setCreatetime(new Timestamp(System.currentTimeMillis()));
				mc.setCreatorType(MobileCodeCreatorType.INVESTOR);
				mc.setMobile(phone);
				mc.setStatus(MobileCodeStatus.NORMAL);
				mc.setType(MobileCodeTypes.FUNDCODE);
				
				Map<String, String> inputParams = new HashMap<String, String>();
				inputParams.put("mobile", mc.getMobile());
				inputParams.put("status", MobileCodeStatus.NORMAL);
				List<Entity> lstMobileCode = this.mobileCodeDAO.getListForPage(inputParams, -1, -1, null, MobileCode.class);

				// ???????????????????????????????????????????????????
				if (lstMobileCode != null && lstMobileCode.size() > 0) {
					for(Entity entity: lstMobileCode){
						MobileCode code = (MobileCode)entity;
						code.setStatus(MobileCodeStatus.DISABLE);
						this.mobileCodeDAO.update(code);
					}
				}
				this.mobileCodeDAO.insert(mc);
				result.put("orderNum", mc.getUuid());
				result.put("result", true);
				result.put("message", "?????????????????????");
			} else {
				result.put("result", false);
				result.put("message", "?????????????????????");
			}
//		}
		return result;
	}

	@Override
	public void updateBatch(List<ShbxUser> listUpdate) {
		for(ShbxUser su : listUpdate){
			this.shbxUserDAO.update(su);
		}
	}
}
