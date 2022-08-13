package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Category;
import com.cmos.china.mobile.media.core.bean.Commodity;
import com.cmos.china.mobile.media.core.bean.Entity;
import com.cmos.china.mobile.media.core.bean.LeaveMessage;
import com.cmos.china.mobile.media.core.bean.Module;
import com.cmos.china.mobile.media.core.bean.Province;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.bean.SsoUser;
import com.cmos.china.mobile.media.core.bean.Template;
import com.cmos.china.mobile.media.core.bean.VideoPublish;
import com.cmos.china.mobile.media.core.bean.Videoinfo;
import com.cmos.china.mobile.media.core.service.IWebInterfaceService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.LeaveMessageVO;
import com.cmos.china.mobile.media.core.vo.ProvinceModuleVO;
import com.cmos.china.mobile.media.core.vo.iface.WebCategoryVO;
import com.cmos.china.mobile.media.core.vo.iface.WebCommodityDisplayVO;
import com.cmos.china.mobile.media.core.vo.iface.WebVideoPointVO;
import com.cmos.china.mobile.media.core.vo.iface.WebVideoPublishVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public class WebInterfaceServiceImpl extends BaseServiceImpl implements IWebInterfaceService {

	/**
	 * 前端获取分类列表
	 */
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
			childParamMap.put("parent", wcat.getId());
			childParamMap.put("status", Entity.GerneralStatusType.NORMAL);
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

	/**
	 * 前端获取发布信息列表
	 */
	@Override
	public void publishList(InputObject inputObject, OutputObject outputObject) throws Exception {
		String category = inputObject.getValue("category");
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), 1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), 10);
		String sort = inputObject.getValue("sort");
		if(!Utlity.checkOrderBy(sort)){
			throw new Exception("参数异常");
		}
		
		Integer start = (pagenum - 1) * pagesize;
		  
		if(sort == null || "".equals(sort)){
			sort = "vp.sequence asc,vp.createtime desc";
	//		sort = "createtime";
		}else{
			sort = sort.replaceAll("-", " ");
		}
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		int recordCount = 0;
		
		if(category != null && !"".equals(category)){
			Category cat = this.getBaseDao().queryForObject("category_get", category, Category.class);
			if(cat != null){
				String scode = cat.getScode();
				Map<String, String> params = new HashMap<String, String>();
				params.put("scode", scode);
				params.put("status", VideoPublish.VideoPublishStatusType.CHECKED);
				recordCount = this.getBaseDao().getTotalCount("videoPublish_getWebCountByParams", params);
				params.put("start", start+"");
				params.put("limit", pagesize+"");
				params.put("sort", sort);
				List<WebVideoPublishVO> wvplist = this.getBaseDao().queryForList("videoPublish_getWebListByParams", params, WebVideoPublishVO.class);
				
				for(WebVideoPublishVO wvp:wvplist){
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("id", wvp.getId());
					data.put("title", wvp.getTitle());
					data.put("coverURL", wvp.getCoverURL());
					data.put("videoURL", wvp.getVideoURL());
					data.put("videoPath", wvp.getVideoURL()+"transcode/video_500K.mp4");
					list.add(data);
				}
			}else{
				throw new Exception("栏目不存在");
			}
		}else{
			throw new Exception("参数异常");
		}
		int pageCount =  (int) Math.ceil((double) recordCount / pagesize);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalResultCount", recordCount);
		resultMap.put("pageNum", pagenum);
		resultMap.put("pageSize", pagesize);
		resultMap.put("totalPageCount", pageCount);
		
		outputObject.setBeans(list);
		outputObject.setObject(resultMap);
	}

	/**
	 * 前端加载视频相关信息
	 */
	@Override
	public void videoInfo(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		
		Videoinfo vi = this.getBaseDao().queryForObject("videoinfo_get", id, Videoinfo.class);
		if(vi==null){
			throw new Exception("视频不存在");
		}else{
			dataMap.put("videoTitle", vi.getTitle());
			dataMap.put("videoContext", vi.getContext());
			dataMap.put("videoThumbanil", vi.getThumbnail());
			dataMap.put("videoTimeLength", vi.getTimeLength());
			String time = vi.getTimeLength();
			String[] times = time.split(":");
			if(times.length==2){
				Double minute = Double.valueOf(times[0]);
				Double second = Double.valueOf(times[1]);
				Double doubleTime = minute * 60 + second;
				dataMap.put("timepointSecond", doubleTime.toString());
			}else if(times.length==3){
				Double hour = Double.valueOf(times[0]);
				Double minute = Double.valueOf(times[1]);
				Double second = Double.valueOf(times[2]);
				Double doubleTime = hour*3600 + minute * 60 + second;
				dataMap.put("timepointSecond", doubleTime.toString());
			}else{
				dataMap.put("timepointSecond", "0");
			}
			
			List<String> videoURLs = new ArrayList<String>();
			videoURLs.add(vi.getVideo()+"transcode/video_250K.mp4");
			videoURLs.add(vi.getVideo()+"transcode/video_500K.mp4");
			videoURLs.add(vi.getVideo()+"transcode/video_1000K.mp4");
			dataMap.put("videoURLs", videoURLs);
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("video", vi.getId());
			List<WebVideoPointVO> list = this.getBaseDao().queryForList("videoCommodityPoint_getWebListByParams", paramMap, WebVideoPointVO.class);
			List<Map<String, Object>> pointList = new ArrayList<Map<String, Object>>();
			if(list != null && list.size() > 0){
				for(WebVideoPointVO wvpv: list){
					Resource commodityCover = this.getBaseDao().queryForObject("resource_get", wvpv.getCommodityCover(), Resource.class);
					Map<String, Object> pointMap = new HashMap<String, Object>();
					pointMap.put("timepoint", wvpv.getTimepoint());
					pointMap.put("timepointSecond", wvpv.getTimepointSecond());
					pointMap.put("showMessage", wvpv.getShowMessage());
					pointMap.put("showBanner", wvpv.getShowBanner());
					pointMap.put("commodity", wvpv.getCommodity());
					pointMap.put("showTime", wvpv.getShowtime());
					if(commodityCover!=null){
						pointMap.put("commodityCover", commodityCover.getUrl());
					}else{
						pointMap.put("commodityCover", "/assets/img/default_productBig.jpg");
					}
					Resource showBanner = this.getBaseDao().queryForObject("resource_get", wvpv.getShowBanner(), Resource.class);
					pointMap.put("showBannerURL", showBanner.getUrl());
					pointList.add(pointMap);
				}
			}
			dataMap.put("webVideoPoints", pointList);
			outputObject.setBean(dataMap);
		}
	}
	
	/**
	 * 前端加载商品相关信息
	 */
	@Override
	public void commodityInfo(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> data = new HashMap<String,Object>();
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		Commodity com = this.getBaseDao().queryForObject("commodity_get", id, Commodity.class);
		if(com==null){
			throw new Exception("商品不存在");
		}else{
			data.put("commodityURL", com.getUrl());
			data.put("name", com.getName());
			data.put("originalPrice", com.getOriginalPrice());
			data.put("price", com.getPrice());
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("commodity", com.getId());
			paramMap.put("sort", "display_index");
			List<WebCommodityDisplayVO> list = this.getBaseDao().queryForList("commodityDisplay_getWebListByParams", paramMap, WebCommodityDisplayVO.class);
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

	@Override
	public void provinceTemplateInfo(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> templateData = new HashMap<String,Object>();
		Map<String,Object> moduleData = new HashMap<String,Object>();
		String id = inputObject.getValue("id");
		
		Province province = this.getBaseDao().queryForObject("province_get", id, Province.class);
		if(province!=null && !"".equals(province.getId())){
			Template template = this.getBaseDao().queryForObject("template_get", province.getTemplate(), Template.class);
			if(template!=null && !"".equals(template.getId())){
				Map<String,String> moduleParamsMap = new HashMap<String,String>();
				moduleParamsMap.put("template", template.getId());
				List<Module> moduleList = this.getBaseDao().queryForList("module_getListByParams", moduleParamsMap, Module.class);
				if(moduleList.size()>0){
					templateData.put("id", template.getId());
					templateData.put("name", template.getName());
					templateData.put("module", moduleData);
					for(Module module: moduleList){
						Map<String,Object> moduleMap = new HashMap<String,Object>();
						moduleMap.put("id", module.getId());
						moduleMap.put("name", module.getName());
						moduleMap.put("count", module.getCount());
						moduleMap.put("sequence", module.getSequence());
						moduleData.put(module.getId(), moduleMap);
						Map<String,String> paramsMap = new HashMap<String,String>();
						paramsMap.put("province", id);
						paramsMap.put("module", module.getId());
						paramsMap.put("status", "normal");
						paramsMap.put("start", "0");
						paramsMap.put("limit", module.getCount()+"");
						List<ProvinceModuleVO> pmList = this.getBaseDao().queryForList("provinceModule_getListByParams", paramsMap, ProvinceModuleVO.class);
						List<Map<String,Object>> moduleDataList = new ArrayList<Map<String,Object>>();
						for(ProvinceModuleVO pm: pmList){		
							Map<String,Object> data = new HashMap<String,Object>();
							data.put("title", pm.getTitle());
							data.put("content", pm.getContent());
							data.put("url", pm.getUrl());
							data.put("image", pm.getImage());
							data.put("imageUrl", pm.getImageUrl());
							data.put("index", moduleDataList.size() +"");
							moduleDataList.add(data);
						}
						moduleMap.put("datas", moduleDataList);
					}
					outputObject.setBean(templateData);
				}else{
					throw new Exception("接入省份模板配置错误");
				}
			}else{
				throw new Exception("接入省份未选择模板");
			}
		}else{
			throw new Exception("接入省份信息不存在");
		}
	}

	@Override
	public void leaveMessageInfo(InputObject inputObject,
			OutputObject outputObject) throws Exception {
	
		String video_publish = inputObject.getValue("video_publish");
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), 1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), 10);
		String status = " = 'checked'";
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

		Map<String, String> paramMap = new HashMap<String, String>();

		paramMap.put("video_publish", video_publish);
		paramMap.put("status", status);
		paramMap.put("start", start+"");
		paramMap.put("limit", pagesize+"");
		paramMap.put("sort", sort);
		
		Integer count = this.getBaseDao().getTotalCount("leaveMessage_getTotalCountByParams", paramMap);
		Integer pageCount = (int) Math.ceil((double) count / pagesize);

		List<LeaveMessageVO> list = this.getBaseDao().queryForList("leaveMessage_getListByParams", paramMap, LeaveMessageVO.class);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pageNum", pagenum);
		resultMap.put("pageSize", pagesize);
		resultMap.put("totalPageCount", pageCount);
		resultMap.put("totalResultCount", count);
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

	@Override
	public void addLeaveMessage(InputObject inputObject, OutputObject outputObject)
			throws Exception {
		String content = inputObject.getValue("content");
		String video_publish = inputObject.getValue("video_publish");
		String phone = inputObject.getValue("phone");
		
		LeaveMessage leavemessage = new LeaveMessage();
		String id = UUID.randomUUID().toString();
		leavemessage.setId(id);
		if (phone != null && !phone.equals("")) {
			SsoUser ssoUser = this.getBaseDao().queryForObject("ssoUser_getByPhone", phone, SsoUser.class);
			if(ssoUser!=null && !ssoUser.equals("")){
				leavemessage.setCreator(ssoUser.getId());
			}
			if (video_publish != null && !video_publish.equals("")) {
				leavemessage.setVideo_publish(video_publish);
				if (content != null && !content.equals("")) {
					leavemessage.setContent(content);
				} else {
					throw new Exception("发布内容异常");
				}
			} else {
				throw new Exception("发布视频异常");
			}
		} else {
			throw new Exception("发布人异常");
		}
		leavemessage.setStatus("unchecked");
		leavemessage.setCreatetime(new Timestamp((new Date()).getTime()));
		this.getBaseDao().insert("leaveMessage_add", leavemessage);
		
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("message", "发布成功");
		data.put("returnCode", "0");
		outputObject.setBean(data);
	}
}
