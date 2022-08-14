package cn.product.worldmall.service.front.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.worldmall.api.base.BaseResult.ResultStatusType;
import cn.product.worldmall.api.base.DataResult;
import cn.product.worldmall.api.base.InputParams;
import cn.product.worldmall.dao.AreaDao;
import cn.product.worldmall.dao.FrontUserAddressDao;
import cn.product.worldmall.dao.FrontUserDao;
import cn.product.worldmall.dao.InternationalInfoDao;
import cn.product.worldmall.entity.Area;
import cn.product.worldmall.entity.FrontUserAddress;
import cn.product.worldmall.entity.FrontUserAddress.FrontUserAddressStatus;
import cn.product.worldmall.entity.InternationalInfo;
import cn.product.worldmall.service.front.FrontUserAddressService;
import cn.product.worldmall.util.Utlity;
import cn.product.worldmall.vo.front.FrontUserAddressVO;

@Service("frontUserAddressService")
public class FrontUserAddressServiceImpl implements FrontUserAddressService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserAddressDao frontUserAddressDao;
	
	@Autowired
	private AreaDao areaDao;
	
	@Autowired
	private InternationalInfoDao internationalInfoDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
    	FrontUserAddress fua = this.frontUserAddressDao.get(uuid);
    	if(!frontUser.equals(fua.getFrontUser())){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Query failed!");
			return;
    	}
    	
		FrontUserAddressVO vo = new FrontUserAddressVO(fua);
		if(!Utlity.checkStringNull(fua.getArea())) {
			Area area = this.areaDao.get(fua.getArea());
			List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
			vo.setAreaScode(area.getScode());
			vo.setAreaNameList(areaNameList);
		}

		if(!Utlity.checkStringNull(fua.getInternationalInfo())) {
			InternationalInfo ii = this.internationalInfoDao.get(fua.getInternationalInfo());
			if(ii != null) {
				vo.setCountry(ii.getNameEn());
			}
		}
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
    		if(!Utlity.checkStringNull(fua.getArea())) {
    			Area area = this.areaDao.get(fua.getArea());
    			List<String> areaNameList = this.areaDao.getFullName(fua.getArea());
    			vo.setAreaScode(area.getScode());
    			vo.setAreaNameList(areaNameList);
    		}

    		if(!Utlity.checkStringNull(fua.getInternationalInfo())) {
    			InternationalInfo ii = this.internationalInfoDao.get(fua.getInternationalInfo());
    			if(ii != null) {
    				vo.setCountry(ii.getNameEn());
    			}
    		}
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
    	String country = paramsMap.get("country") == null ? "" : paramsMap.get("country").toString();
    	String asub = paramsMap.get("asub") == null ? "" : paramsMap.get("asub").toString();
    	String city = paramsMap.get("city") == null ? "" : paramsMap.get("city").toString();
    	String state = paramsMap.get("state") == null ? "" : paramsMap.get("state").toString();
    	String zipcode = paramsMap.get("zipcode") == null ? "" : paramsMap.get("zipcode").toString();
    	
    	
    	FrontUserAddress fua = new FrontUserAddress();
    	fua.setUuid(UUID.randomUUID().toString());
    	fua.setFrontUser(frontUser);
    	fua.setReceiver(receiver);
    	fua.setPhone(phone);
    	fua.setZipcode(zipcode);
    	
    	if(!Utlity.checkStringNull(area)) {
    		//获取areaUUID
    		Area areaEntity = this.areaDao.getByScode(area);
    		if(areaEntity != null) {
        		fua.setArea(areaEntity.getUuid());
        	} else {
        		result.setStatus(ResultStatusType.FAILED);
        		result.setMessage("Area information load error!");
        		return;
        	}
    	}
    	
    	if(!Utlity.checkStringNull(country)) {
    		InternationalInfo ii = this.internationalInfoDao.get(country);
    		if(ii != null) {
    			fua.setInternationalInfo(ii.getUuid());
    		} else {
    			result.setStatus(ResultStatusType.FAILED);
        		result.setMessage("Country information load error!");
        		return;
    		}
    	}
    	fua.setAddress(address);
    	
    	fua.setAsub(asub);
    	fua.setCity(city);
    	fua.setState(state);
    	
    	fua.setIsDefault(true);
    	fua.setStatus(FrontUserAddressStatus.NORMAL);
    	fua.setCreatetime(new Timestamp(System.currentTimeMillis()));
    	this.frontUserAddressDao.addFrontUserAddress(fua);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
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
    	String country = paramsMap.get("country") == null ? "" : paramsMap.get("country").toString();
    	String asub = paramsMap.get("asub") == null ? "" : paramsMap.get("asub").toString();
    	String city = paramsMap.get("city") == null ? "" : paramsMap.get("city").toString();
    	String state = paramsMap.get("state") == null ? "" : paramsMap.get("state").toString();
    	String zipcode = paramsMap.get("zipcode") == null ? "" : paramsMap.get("zipcode").toString();
		
    	FrontUserAddress fua = this.frontUserAddressDao.get(uuid);
    	if(!frontUser.equals(fua.getFrontUser())){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Cannot operate!");
			return;
    	}
    	
    	fua.setReceiver(receiver);
    	fua.setPhone(phone);
    	fua.setZipcode(zipcode);
    	
    	if(!Utlity.checkStringNull(area)) {
    		//获取areaUUID
    		Area areaEntity = this.areaDao.getByScode(area);
    		if(areaEntity != null) {
        		fua.setArea(areaEntity.getUuid());
        	} else {
        		result.setStatus(ResultStatusType.FAILED);
        		result.setMessage("Area information load error!");
        		return;
        	}
    	}
    	
    	if(!Utlity.checkStringNull(country)) {
    		InternationalInfo ii = this.internationalInfoDao.get(country);
    		if(ii != null) {
    			fua.setInternationalInfo(ii.getUuid());
    		} else {
    			result.setStatus(ResultStatusType.FAILED);
        		result.setMessage("Country information load error!");
        		return;
    		}
    	}
    	fua.setAddress(address);

    	fua.setAsub(asub);
    	fua.setCity(city);
    	fua.setState(state);
    	this.frontUserAddressDao.update(fua);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	String frontUser = paramsMap.get("frontUser") == null ? "" : paramsMap.get("frontUser").toString();
		
    	FrontUserAddress fua = this.frontUserAddressDao.get(uuid);
    	if(!frontUser.equals(fua.getFrontUser())){
    		result.setStatus(ResultStatusType.FAILED);
			result.setMessage("Cannot operate!");
			return;
    	}
    	
    	fua.setStatus(FrontUserAddressStatus.DELETE);
    	this.frontUserAddressDao.update(fua);
    	
    	result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("Successful!");
	}
}
