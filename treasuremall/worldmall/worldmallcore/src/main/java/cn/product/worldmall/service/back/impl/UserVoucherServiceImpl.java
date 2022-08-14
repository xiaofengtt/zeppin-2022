package cn.product.worldmall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.dao.AdminDao;
import cn.product.worldmall.dao.FrontUserAccountDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.FrontUserHistoryDao;
import cn.product.worldmall.dao.FrontUserVoucherDao;
import cn.product.worldmall.dao.ResourceDao;
import cn.product.worldmall.dao.VoucherDao;
import cn.product.worldmall.entity.FrontUser;
import cn.product.worldmall.entity.FrontUserVoucher;
import cn.product.worldmall.entity.Resource;
import cn.product.worldmall.entity.Voucher;
import cn.product.worldmall.entity.FrontUser.FrontUserStatus;
import cn.product.worldmall.entity.FrontUser.FrontUserType;
import cn.product.worldmall.entity.FrontUserVoucher.FrontUserVoucherStatus;
import cn.product.worldmall.entity.Voucher.VoucherStatus;
import cn.product.worldmall.service.back.UserVoucherService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.back.FrontUserVoucherVO;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;

@Service("userVoucherService")
public class UserVoucherServiceImpl implements UserVoucherService{
	
	@Autowired
	private FrontUserVoucherDao frontUserVoucherDao;
	
	@Autowired
    private FrontUserDao frontUserDao;
	
	@Autowired
    private FrontUserAccountDao frontUserAccountDao;
	
	@Autowired
    private FrontUserHistoryDao frontUserHistoryDao;
	
	@Autowired
    private AdminDao adminDao;
	
	@Autowired
    private ResourceDao resourceDao;

	@Autowired
    private VoucherDao voucherDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		FrontUserVoucher fuv = frontUserVoucherDao.get(uuid);
		
		FrontUserVoucherVO vo = new FrontUserVoucherVO(fuv);
		FrontUser fu = frontUserDao.get(fuv.getFrontUser());
		if(fu != null){
			vo.setFrontUserShowId(fu.getShowId());
			vo.setFrontUserNickname(fu.getNickname());
			vo.setFrontUserShowId(fu.getShowId());
			
			if(fu.getImage() != null){
				Resource image = resourceDao.get(fu.getImage());
				if(image != null){
					vo.setFrontUserImageURL(image.getUrl());
				}
			}
		}
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String userType = paramsMap.get("userType") == null ? "" : paramsMap.get("userType").toString();
		String showid = paramsMap.get("showid") == null ? "" : paramsMap.get("showid").toString();
		String mobile = paramsMap.get("mobile") == null ? "" : paramsMap.get("mobile").toString();
		String createtime = paramsMap.get("createtime") == null ? "" : paramsMap.get("createtime").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("status", status);
		if(!Utlity.checkStringNull(userType)) {
			if(!FrontUserType.NORMAL.equals(userType) && !FrontUserType.ROBOT.equals(userType)) {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("用户类型错误！");
				return;
			}
			searchMap.put("userType", userType);
		}
		searchMap.put("showid", showid);
		searchMap.put("mobile", mobile);
		if(!Utlity.checkStringNull(createtime)) {
			String[] times = createtime.split("_");
			if(times != null && times.length == 2) {
				searchMap.put("timestart", times[0].trim());
				searchMap.put("timeend", times[1].trim());
			}
		}
		searchMap.put("backlist", "1");
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<FrontUserVoucher> list = this.frontUserVoucherDao.getLeftListByParams(searchMap);
		Integer totalCount = this.frontUserVoucherDao.getLeftCountByParams(searchMap);
		
		List<FrontUserVoucherVO> listvo = new ArrayList<FrontUserVoucherVO>();
		if(list != null && list.size() > 0) {
			for(FrontUserVoucher fuv : list) {
				FrontUserVoucherVO fuvvo = new FrontUserVoucherVO(fuv);
				FrontUser fu = frontUserDao.get(fuv.getFrontUser());
				if(fu != null){
					fuvvo.setFrontUserShowId(fu.getShowId());
					fuvvo.setFrontUserNickname(fu.getNickname());
					fuvvo.setFrontUserShowId(fu.getShowId());
					
					if(fu.getImage() != null){
						Resource image = resourceDao.get(fu.getImage());
						if(image != null){
							fuvvo.setFrontUserImageURL(image.getUrl());
						}
					}
				}
				listvo.add(fuvvo);
			}
		}
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		String[] vouchers = paramsMap.get("vouchers") == null ? null : (String[])paramsMap.get("vouchers");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		
		FrontUser fu = this.frontUserDao.get(frontUser);
		if(fu == null || FrontUserStatus.DELETE.equals(fu.getStatus())) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("用户不存在！");
			return;
		}
		try {
			if(vouchers != null) {
				List<FrontUserVoucher> fuvList = new ArrayList<>();
				for(String voucher : vouchers) {
					Voucher v = this.voucherDao.get(voucher);
					if(v == null || VoucherStatus.DELETE.equals(v.getStatus())) {
						result.setStatus(ResultStatusType.FAILED);
						result.setMessage("金券不存在！");
						return;
					}
					FrontUserVoucher fuv = new FrontUserVoucher();
					fuv.setUuid(UUID.randomUUID().toString());
					fuv.setFrontUser(frontUser);
					fuv.setVoucher(voucher);
					fuv.setTitle(v.getTitle());
					fuv.setDiscription(v.getDiscription());
					fuv.setdAmount(v.getdAmount());
					fuv.setPayMin(v.getPayMin());
					fuv.setGoodsType(v.getGoodsType());
					fuv.setGoods(v.getGoods());
					
					//按照voucher设定的起止时间，计算初始时间和结束时间
					fuv.setStarttime(Utlity.getTime(v.getStarttime()) == null ? new Timestamp(System.currentTimeMillis()) : Utlity.getTime(v.getStarttime()));//未设置代表当前时间生效
					fuv.setEndtime(Utlity.getTime(v.getEndtime()));
					fuv.setCreatetime(new Timestamp(System.currentTimeMillis()));
					fuv.setCreator(admin);
					fuv.setStatus(FrontUserVoucherStatus.UNSTART);
					fuv.setOperattime(fuv.getCreatetime());
					
					fuvList.add(fuv);
				}
				this.frontUserVoucherDao.insert(fuvList);
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("金券信息错误！");
				return;
			}
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("操作异常！");
			return;
		}
	}
	
//	private static Timestamp getTime(String timeStr) throws ParseException {
//		Timestamp time = null;
//		if(!Utlity.checkStringNull(timeStr)) {
//			if(timeStr.startsWith("+")) {//动态起始时间
//				int dayNum = Integer.valueOf(timeStr.substring(1));
//				Calendar ca = Calendar.getInstance();
//				ca.add(Calendar.DATE, dayNum);
//				time = new Timestamp(ca.getTimeInMillis());
//			} else {
//				time = new Timestamp(Utlity.stringToDatetime(timeStr).getTime());
//			}
//		}
//		return time;
//	}
}
