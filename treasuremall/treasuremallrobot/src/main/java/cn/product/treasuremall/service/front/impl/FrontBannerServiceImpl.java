package cn.product.treasuremall.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.BannerDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.entity.Banner;
import cn.product.treasuremall.entity.Banner.BannerStatus;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.service.front.FrontBannerService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.BannerVO;

@Service("frontBannerService")
public class FrontBannerServiceImpl implements FrontBannerService{
	
	
	@Autowired
	private BannerDao bannerDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private SystemParamDao systemParamDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", BannerStatus.NORMAL);
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的广告图信息的总数
		Integer totalResultCount = bannerDao.getCountByParams(searchMap);
		//查询符合条件的广告图信息列表
		List<Banner> list = bannerDao.getListByParams(searchMap);
		List<BannerVO> listvo = new ArrayList<BannerVO>();
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		for(Banner banner : list) {
			//界面返回封装对象
			BannerVO bannerVO = new BannerVO(banner);
			
			//资源信息
			Resource resource = resourceDao.get(banner.getImage());
			if (resource != null) {
				bannerVO.setImageUrl(pathUrl + resource.getUrl());
			}
			listvo.add(bannerVO);
		}
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}
	
}
