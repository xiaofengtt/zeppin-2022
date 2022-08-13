package com.cmos.china.mobile.media.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmos.china.mobile.media.core.bean.Category;
import com.cmos.china.mobile.media.core.bean.Commodity;
import com.cmos.china.mobile.media.core.bean.Entity;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.bean.VideoPublish;
import com.cmos.china.mobile.media.core.bean.Videoinfo;
import com.cmos.china.mobile.media.core.service.IWebInterfaceService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.VideoPublishVO;
import com.cmos.china.mobile.media.core.vo.iface.WebCategoryVO;
import com.cmos.china.mobile.media.core.vo.iface.WebCommodityDisplayVO;
import com.cmos.china.mobile.media.core.vo.iface.WebVideoPointVO;
import com.cmos.china.mobile.media.core.vo.iface.WebVideoPublishVO;
import com.cmos.china.mobile.media.core.vo.iface.WebVideoVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public class WebInterfaceServiceImpl extends BaseServiceImpl implements IWebInterfaceService {

	@Override
	public void categoryList(InputObject inputObject, OutputObject outputObject) throws Exception {
		String parent = inputObject.getValue("parent");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		if(parent!=null && !parent.equals("")){
			paramMap.put("parent", parent);
			paramMap.put("status", Entity.GerneralStatusType.NORMAL);
		}else{
			paramMap.put("level", "1");
			paramMap.put("status", Entity.GerneralStatusType.NORMAL);
		}
		
		List<WebCategoryVO> list = this.getBaseDao().queryForList("category_getWebListByParams", paramMap, WebCategoryVO.class);
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		for(WebCategoryVO wcat : list){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("id", wcat.getId());
			dataMap.put("name", wcat.getName());
			Map<String, String> childParamMap = new HashMap<String, String>();
			paramMap.put("parent", wcat.getId());
			paramMap.put("status", Entity.GerneralStatusType.NORMAL);
			List<WebCategoryVO> childlist = this.getBaseDao().queryForList("category_getWebListByParams", childParamMap, WebCategoryVO.class);
			List<Map<String,Object>> childDatas = new ArrayList<Map<String,Object>>();
			for(WebCategoryVO wcvo : childlist){
				Map<String,Object> childMap = new HashMap<String,Object>();
				childMap.put("id", wcvo.getId());
				childMap.put("name", wcvo.getName());
				childDatas.add(childMap);
			}
			dataMap.put("child", childDatas);
			dataList.add(dataMap);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalResultCount", list.size());
		
		outputObject.setBeans(dataList);
		outputObject.setObject(resultMap);
	}

	@Override
	public void publishList(InputObject inputObject, OutputObject outputObject) throws Exception {
		String video = inputObject.getValue("video");
		String category = inputObject.getValue("category");
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), 1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), 10);
		String sort = inputObject.getValue("sort");
		if(!Utlity.checkOrderBy(sort)){
			throw new Exception("参数异常");
		}
		
		Integer start = (pagenum - 1) * pagesize;
		
		if(sort == null || "".equals(sort)){
			sort = "createtime";
		}else{
			sort = sort.replaceAll("-", " ");
		}
		
		List<WebVideoPublishVO> list = new ArrayList<WebVideoPublishVO>();
		int recordCount = 0;
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("video", video);
		if(video==null||video.equals("")){
			List<VideoPublishVO> vps = this.getBaseDao().queryForList("videoPublish_getListByParams", paramMap, VideoPublishVO.class);
			if(vps != null && vps.size() > 0){
				category = vps.get(0).getCategory();
			}else{
				throw new Exception("参数异常");
			}
		}
		
		if(category != null && !"".equals(category)){
			Category cat = this.getBaseDao().queryForObject("category_get", category, Category.class);
			if(cat != null){
				String scode = cat.getScode();
				Map<String, String> params = new HashMap<String, String>();
				params.put("scode", scode);
				params.put("status", VideoPublish.VideoPublishStatusType.CHECKED);
				params.put("start", start+"");
				params.put("limit", pagesize+"");
				params.put("sort", sort);
				List<WebVideoPublishVO> listData = this.getBaseDao().queryForList("videoPublish_getWebCountByParams", paramMap, WebVideoPublishVO.class);
				recordCount = listData.size();
				list.addAll(listData);
			}else{
				throw new Exception("栏目不存在");
			}
		}else{
			throw new Exception("参数异常");
		}
		int pageCount =  (int) Math.ceil((double) recordCount / pagesize);
		for(WebVideoPublishVO wvp:list){
			wvp.setVideoPath(wvp.getVideoURL()+"transcode/video_500K.mp4");
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalResultCount", recordCount);
		resultMap.put("pageNum", pagenum);
		resultMap.put("pageSize", pagesize);
		resultMap.put("totalPageCount", pageCount);
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

	@Override
	public void videoInfo(InputObject inputObject, OutputObject outputObject) throws Exception {
		WebVideoVO wvv = new WebVideoVO();
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		
		Videoinfo vi = this.getBaseDao().queryForObject("videoinfo_get", id, Videoinfo.class);
		if(vi==null){
			throw new Exception("视频不存在");
		}else{
			wvv.setVideoTitle(vi.getTitle());
			wvv.setVideoContext(vi.getContext());
			wvv.setVideoThumbanil(vi.getThumbnail());
			wvv.setVideoTimeLength(vi.getTimeLength());
			List<String> videoURLs = new ArrayList<String>();
			videoURLs.add(vi.getVideo()+"transcode/video_250K.mp4");
			videoURLs.add(vi.getVideo()+"transcode/video_500K.mp4");
			videoURLs.add(vi.getVideo()+"transcode/video_1000K.mp4");
			wvv.setVideoURLs(videoURLs);
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("video", vi.getId());
			List<WebVideoPointVO> list = this.getBaseDao().queryForList("videoPoint_getWebListByParams", paramMap, WebVideoPointVO.class);
			if(list != null && list.size() > 0){
				for(WebVideoPointVO wvpv: list){
					Resource commodityCover = this.getBaseDao().queryForObject("resource_get", wvpv.getCommodityCover(), Resource.class);
					if(commodityCover!=null){
						wvpv.setCommodityCover(commodityCover.getUrl());
					}else{
						wvpv.setCommodityCover("/assets/img/default_productBig.jpg");
					}
					Resource showBanner = this.getBaseDao().queryForObject("resource_get", wvpv.getShowBanner(), Resource.class);
					wvpv.setShowBannerURL(showBanner.getUrl());
				}
			}
			wvv.setWebVideoPoints(list);
			outputObject.convertBean2Map(wvv);
		}
	}

	@Override
	public void commodityInfo(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> data = new HashMap<String,Object>();
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		Commodity com = this.getBaseDao().queryForObject("commodity_get", id, Commodity.class);
		if(com==null){
			throw new Exception("视频不存在");
		}else{
			data.put("commodityURL", com.getUrl());
			data.put("name", com.getName());
			data.put("originalPrice", com.getOriginalPrice());
			data.put("price", com.getPrice());
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("commodity", com.getId());
			paramMap.put("sort", "display_index");
			List<WebCommodityDisplayVO> list = this.getBaseDao().queryForList("commodity_getWebListByParams", paramMap, WebCommodityDisplayVO.class);
			List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
			for(WebCommodityDisplayVO wcdvo : list){
				Map<String,Object> comData = new HashMap<String,Object>();
				comData.put("displayIndex", wcdvo.getDisplayIndex());
				comData.put("displayImageURL", wcdvo.getDisplayImageURL());
				dataList.add(comData);
			}
			
			data.put("webCommodityDisplays", dataList);
			outputObject.setBean(data);
		}
	}

}
