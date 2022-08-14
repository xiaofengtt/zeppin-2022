package cn.product.treasuremall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.dao.BannerDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.Banner;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.Banner.BannerStatus;
import cn.product.treasuremall.service.back.BannerService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.BannerVO;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;

@Service("bannerService")
public class BannerServiceImpl implements BannerService{
	
	@Autowired
	private BannerDao bannerDao;
	
	@Autowired
	private ResourceDao resourceDao;

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("title", title);
		searchMap.put("status", status);
		searchMap.put("type", type);
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
		for(Banner banner : list) {
			//界面返回封装对象
			BannerVO bannerVO = new BannerVO(banner);
			
			//资源信息
			Resource resource = resourceDao.get(banner.getImage());
			if (resource != null) {
				bannerVO.setImageUrl(resource.getUrl());
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

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取广告图信息
		Banner banner = bannerDao.get(uuid);
		if (banner == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
			return;
		}
		
		//界面返回封装对象
		BannerVO bannerVO = new BannerVO(banner);
		
		//资源信息
		Resource resource = resourceDao.get(banner.getImage());
		if (resource != null) {
			bannerVO.setImageUrl(resource.getUrl());
		}
		result.setData(bannerVO);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String code = paramsMap.get("code") == null ? "" : paramsMap.get("code").toString();
		String image = paramsMap.get("image") == null ? "" : paramsMap.get("image").toString();
		String url = paramsMap.get("url") == null ? "" : paramsMap.get("url").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		if(!BannerStatus.DISABLE.equals(status) && !BannerStatus.NORMAL.equals(status)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("广告图状态错误！");
			return;
		}
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
		String frontuserlevel = paramsMap.get("frontuserlevel") == null ? "" : paramsMap.get("frontuserlevel").toString();
		
		try {
			//创建广告图信息
			Banner banner = new Banner();
			banner.setUuid(UUID.randomUUID().toString());
			banner.setTitle(title);
			banner.setType(type);
			banner.setCode(code);
			banner.setImage(image);
			banner.setUrl(url);
			banner.setStatus(status);
			String[] times = endtime.split("_");
			banner.setStarttime(new Timestamp(Utlity.stringToDatetime(times[0]).getTime()));
			banner.setEndtime(new Timestamp(Utlity.stringToDatetime(times[1]).getTime()));
			banner.setSort(0);
			banner.setCreatetime(new Timestamp(System.currentTimeMillis()));
			banner.setFrontUserLevel(frontuserlevel);
			
			bannerDao.insert(banner);
			
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
		}
		
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String title = paramsMap.get("title") == null ? "" : paramsMap.get("title").toString();
		String code = paramsMap.get("code") == null ? "" : paramsMap.get("code").toString();
		String image = paramsMap.get("image") == null ? "" : paramsMap.get("image").toString();
		String url = paramsMap.get("url") == null ? "" : paramsMap.get("url").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		if(!BannerStatus.DISABLE.equals(status) && !BannerStatus.NORMAL.equals(status)) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("广告图状态错误！");
			return;
		}
		String endtime = paramsMap.get("endtime") == null ? "" : paramsMap.get("endtime").toString();
		String frontuserlevel = paramsMap.get("frontuserlevel") == null ? "" : paramsMap.get("frontuserlevel").toString();
		
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		try {
			//获取广告图信息
			Banner banner = bannerDao.get(uuid);
			if(banner != null && uuid.equals(banner.getUuid())){
				
				//修改广告图信息
				banner.setTitle(title);
				banner.setCode(code);
				banner.setImage(image);
				banner.setUrl(url);
				banner.setStatus(status);
				String[] times = endtime.split("_");
				banner.setStarttime(new Timestamp(Utlity.stringToDatetime(times[0]).getTime()));
				banner.setEndtime(new Timestamp(Utlity.stringToDatetime(times[1]).getTime()));
				banner.setFrontUserLevel(frontuserlevel);
				
				bannerDao.update(banner);
				
				result.setStatus(ResultStatusType.SUCCESS);
				result.setMessage("保存成功");
			} else {
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("该条数据不存在！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("保存异常");
		}
		
	}

	@Override
	public void delete(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取广告图信息
		Banner banner = bannerDao.get(uuid);
		if(banner != null && uuid.equals(banner.getUuid())){
			//删除广告图信息
			banner.setStatus(BannerStatus.DELETE);
			bannerDao.update(banner);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("删除成功");
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}

	/**
	 * 置顶广告
	 * 查询当前type下的所有广告sort最大值
	 * 将当前广告的sort 设置为最大值+1
	 */
	@Override
	public void top(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		//获取广告图信息
		Banner banner = bannerDao.get(uuid);
		if(banner != null && uuid.equals(banner.getUuid())){
			Integer sortNum = 0;
			//查询条件
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("sort", "sort desc");
			searchMap.put("type", banner.getType());
			List<Banner> list = bannerDao.getListByParams(searchMap);
			if(list != null) {
				sortNum = list.get(0).getSort() + 1;
			} else {
				sortNum += 1;
			}
			banner.setSort(sortNum);
			bannerDao.update(banner);
			result.setStatus(ResultStatusType.SUCCESS);
			result.setMessage("操作成功");
		}else{
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("该条数据不存在！");
		}
	}
	
}
