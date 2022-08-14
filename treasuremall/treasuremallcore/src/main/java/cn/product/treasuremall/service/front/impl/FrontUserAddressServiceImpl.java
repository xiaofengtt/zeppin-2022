package cn.product.treasuremall.service.front.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.AreaDao;
import cn.product.treasuremall.dao.FrontUserAddressDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.entity.Area;
import cn.product.treasuremall.entity.FrontUserAddress;
import cn.product.treasuremall.entity.FrontUserAddress.FrontUserAddressStatus;
import cn.product.treasuremall.service.front.FrontUserAddressService;
import cn.product.treasuremall.vo.front.FrontUserAddressVO;

@Service("frontUserAddressService")
public class FrontUserAddressServiceImpl implements FrontUserAddressService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserAddressDao frontUserAddressDao;
	
	@Autowired
	private AreaDao areaDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
    	FrontUserAddress fua = this.frontUserAddressDao.get(uuid);
    	if(!frontUser.equals(fua.getFrontUser())){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("非本用户地址,无法查询！");
			return;
    	}
    	
		FrontUserAddressVO vo = new FrontUserAddressVO(fua);
		Area area = this.areaDao.get(fua.getArea());
		List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
		vo.setAreaScode(area.getScode());
		vo.setAreaNameList(areaNameList);
		
		result.setData(vo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
    	Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUser", frontUser);
		searchMap.put("sort", sort);
		List<FrontUserAddress> list = this.frontUserAddressDao.getListByParams(searchMap);
		
    	List<FrontUserAddressVO> voList = new ArrayList<FrontUserAddressVO>();
    	for(FrontUserAddress fua : list){
    		FrontUserAddressVO vo = new FrontUserAddressVO(fua);
    		Area area = this.areaDao.get(fua.getArea());
    		List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
    		vo.setAreaScode(area.getScode());
    		vo.setAreaNameList(areaNameList);
    		voList.add(vo);
    	}
		
    	result.setData(voList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String receiver = paramsMap.get("receiver") == null ? "" : paramsMap.get("receiver").toString();
    	String phone = paramsMap.get("phone") == null ? "" : paramsMap.get("phone").toString();
    	String area = paramsMap.get("area") == null ? "" : paramsMap.get("area").toString();
    	String address = paramsMap.get("address") == null ? "" : paramsMap.get("address").toString();
    	
    	
    	FrontUserAddress fua = new FrontUserAddress();
    	fua.setUuid(UUID.randomUUID().toString());
    	fua.setFrontUser(frontUser);
    	fua.setReceiver(receiver);
    	fua.setPhone(phone);
    	//获取areaUUID
		Area areaEntity = this.areaDao.getByScode(area);
		if(areaEntity != null) {
    		fua.setArea(areaEntity.getUuid());
    	} else {
    		result.setStatus(ResultStatusType.FAILED);
    		result.setMessage("地区加载错误！");
    		return;
    	}
    	
    	fua.setAddress(address);
    	fua.setIsDefault(true);
    	fua.setStatus(FrontUserAddressStatus.NORMAL);
    	fua.setCreatetime(new Timestamp(System.currentTimeMillis()));
    	this.frontUserAddressDao.addFrontUserAddress(fua);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
    	String receiver = paramsMap.get("receiver") == null ? "" : paramsMap.get("receiver").toString();
    	String phone = paramsMap.get("phone") == null ? "" : paramsMap.get("phone").toString();
    	String area = paramsMap.get("area") == null ? "" : paramsMap.get("area").toString();
    	String address = paramsMap.get("address") == null ? "" : paramsMap.get("address").toString();
		
    	FrontUserAddress fua = this.frontUserAddressDao.get(uuid);
    	if(!frontUser.equals(fua.getFrontUser())){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("非本用户地址,无法操作！");
			return;
    	}
    	
    	fua.setReceiver(receiver);
    	fua.setPhone(phone);
    	//获取areaUUID
		Area areaEntity = this.areaDao.getByScode(area);
		if(areaEntity != null) {
    		fua.setArea(areaEntity.getUuid());
    	} else {
    		result.setStatus(ResultStatusType.FAILED);
    		result.setMessage("地区加载错误！");
    		return;
    	}
    	fua.setAddress(address);
    	this.frontUserAddressDao.update(fua);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
    	FrontUserAddress fua = this.frontUserAddressDao.get(uuid);
    	if(!frontUser.equals(fua.getFrontUser())){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("非本用户地址,无法操作！");
			return;
    	}
    	
    	fua.setStatus(FrontUserAddressStatus.DELETE);
    	this.frontUserAddressDao.update(fua);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("操作成功！");
	}
}
