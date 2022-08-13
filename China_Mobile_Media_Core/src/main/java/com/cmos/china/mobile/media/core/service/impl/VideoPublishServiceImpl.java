package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Category;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.bean.VideoPublish;
import com.cmos.china.mobile.media.core.bean.Videoinfo;
import com.cmos.china.mobile.media.core.service.IVideoPublishService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.VideoPublishVO;
import com.cmos.china.mobile.media.core.vo.VideoStatusMapVO;
import com.cmos.china.mobile.media.core.vo.VideoinfoVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public class VideoPublishServiceImpl extends BaseServiceImpl implements IVideoPublishService {

	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String title = inputObject.getValue("title");
		String shortTitle = inputObject.getValue("shortTitle");
		String category = inputObject.getValue("category");
		String status = inputObject.getValue("status");
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
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("title", title);
		paramMap.put("shortTitle", shortTitle);
		paramMap.put("category", category);
		paramMap.put("status", status);
		paramMap.put("start", start+"");
		paramMap.put("limit", pagesize+"");
		paramMap.put("sort", sort);
		
		Integer count = this.getBaseDao().getTotalCount("videoPublish_getCountByParams", paramMap);
		Integer pageCount = (int) Math.ceil((double) count / pagesize);
		List<VideoPublishVO> list = this.getBaseDao().queryForList("videoPublish_getListByParams", paramMap, VideoPublishVO.class);
		
		if(list != null && list.size() > 0){
			for(VideoPublishVO vpv:list){
				if(vpv.getCover() != null && !"".equals(vpv.getCover())){
					Resource cover = this.getBaseDao().queryForObject("resource_get", vpv.getCover(), Resource.class);
					if(cover != null){
						vpv.setCoverURL(cover.getUrl());
					}else{
						vpv.setCoverURL("/assets/img/default_productBig.jpg");
					}
					
				}else{
					vpv.setCoverURL("/assets/img/default_productBig.jpg");
				}
			}
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pageNum", pagenum);
		resultMap.put("pageSize", pagesize);
		resultMap.put("totalPageCount", pageCount);
		resultMap.put("totalResultCount", count);
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

	@Override
	public void load(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> resultData = new HashMap<String, Object>();
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		
		VideoPublish vp = this.getBaseDao().queryForObject("videoPublish_get", id, VideoPublish.class);
		if(vp==null){
			throw new Exception("发布不存在");
		}else{
			resultData.put("category", vp.getCategory());
			Videoinfo video = this.getBaseDao().queryForObject("videoinfo_get", vp.getVideo(), Videoinfo.class);
			if(video != null){
				resultData.put("videoID", video.getId());
				resultData.put("videoTitle", video.getTitle());
			}else{
				resultData.put("videoID", "0");
				resultData.put("videoTitle", "未选择");
			}
			resultData.put("title", vp.getTitle());
			resultData.put("shortTitle", vp.getShortTitle());
			if(vp.getCover() != null && !"".equals(vp.getCover())){
				Resource cover = this.getBaseDao().queryForObject("resource_get", vp.getCover(), Resource.class);
				if(cover!=null){
					resultData.put("cover",cover.getUrl());
				}else{
					resultData.put("cover","/assets/img/default_productBig.jpg");
				}
			}else{
				resultData.put("cover","/assets/img/default_productBig.jpg");
			}
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("status", Videoinfo.VideoStatusType.CHECKED);
			paramMap.put("sort", "createtime desc");
			List<VideoinfoVO> list = this.getBaseDao().queryForList("videoinfo_getListByParams", paramMap, VideoinfoVO.class);
			List<VideoinfoVO> data = new ArrayList<VideoinfoVO>();
			if(list != null && list.size() > 0){
				for(VideoinfoVO vvo : list){
					if(vvo.getId().equals(video.getId())){
						continue;
					}
					data.add(vvo);
				}
				resultData.put("videoList", data);
			}
			outputObject.setBean(resultData);
		}
	}

	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws Exception {
		String title = inputObject.getValue("title");
		String video = inputObject.getValue("video");
		String category = inputObject.getValue("category");
		String cover = inputObject.getValue("cover");
		String shortTitle = inputObject.getValue("shortTitle");
		String creator = inputObject.getValue("creator");
		
		if(title==null||title.equals("")){
			throw new Exception("标题不能为空");
		}
		if(video==null||video.equals("")){
			throw new Exception("未选择发布视频");
		}
		if(category==null||category.equals("")){
			throw new Exception("未选择发布栏目");
		}
		
		VideoPublish videoPublish = new VideoPublish();
		String id = UUID.randomUUID().toString();
		videoPublish.setId(id);
		videoPublish.setTitle(title);
		
		Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", video, Videoinfo.class);
		if(videoinfo!=null){
			videoPublish.setVideo(video);
		}else{
			throw new Exception("视频不存在");
		}
		Category cate = this.getBaseDao().queryForObject("category_get", category, Category.class);
		if(cate!=null){
			videoPublish.setCategory(category);
		}else{
			throw new Exception("栏目不存在");
		}
		if(cover!=null && !cover.equals("")){
			Resource resource = this.getBaseDao().queryForObject("resource_get", cover, Resource.class);
			if(resource!=null){
				videoPublish.setCover(cover);
			}else{
				throw new Exception("封面不存在");
			}
		}
		videoPublish.setShortTitle(shortTitle);
		videoPublish.setStatus(VideoPublish.VideoPublishStatusType.UNCHECKED);
		videoPublish.setCreator(creator);
		videoPublish.setCreatetime(new Timestamp((new Date()).getTime()));
		this.getBaseDao().insert("videoPublish_add", videoPublish);
	}

	@Override
	public void edit(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String title = inputObject.getValue("title");
		String video = inputObject.getValue("video");
		String cover = inputObject.getValue("cover");
		String shortTitle = inputObject.getValue("shortTitle");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		if(title==null||title.equals("")){
			throw new Exception("标题不能为空");
		}
		if(video==null||video.equals("")){
			throw new Exception("未选择发布视频");
		}
		
		VideoPublish videoPublish = this.getBaseDao().queryForObject("videoPublish_get", id, VideoPublish.class);
		if(videoPublish!=null){
			videoPublish.setTitle(title);
			
			Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", video, Videoinfo.class);
			if(videoinfo!=null){
				videoPublish.setVideo(video);
			}else{
				throw new Exception("视频不存在");
			}
			if(cover!=null && !cover.equals("")){
				Resource resource = this.getBaseDao().queryForObject("resource_get", cover, Resource.class);
				if(resource!=null){
					videoPublish.setCover(cover);
				}else{
					throw new Exception("封面不存在");
				}
			}
			videoPublish.setShortTitle(shortTitle);	
			this.getBaseDao().update("videoPublish_update", videoPublish);
		}else{
			throw new Exception("发布信息不存在");
		}
	}

	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		
		VideoPublish videoPublish = this.getBaseDao().queryForObject("videoPublish_get", id, VideoPublish.class);
		if(videoPublish==null){
			throw new Exception("发布信息不存在");
		}else{
			this.getBaseDao().update("videoPublish_delete", videoPublish);
		}
	}

	@Override
	public void changeStatus(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String status = inputObject.getValue("status");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		if(status==null||status.equals("")){
			throw new Exception("状态不能为空");
		}
		
		VideoPublish videoPublish = this.getBaseDao().queryForObject("videoPublish_get", id, VideoPublish.class);
		if(videoPublish!=null){
			videoPublish.setStatus(status);
			this.getBaseDao().update("videoPublish_update", videoPublish);
		}else{
			throw new Exception("发布信息不存在");
		}
	}

	@Override
	public void statusList(InputObject inputObject, OutputObject outputObject) throws Exception {
		String category = inputObject.getValue("category");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("category", category);
		List<Map<String,Object>> list = this.getBaseDao().queryForList("videoPublish_getStatusCount", paramMap);
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		for(Map<String,Object> map : list){
			countMap.put(map.get("status").toString(), Integer.valueOf(map.get("count(status)").toString()));
		}
		VideoStatusMapVO statusVO = new VideoStatusMapVO();
		statusVO.setChecked(countMap.get("checked")==null? 0 : countMap.get("checked"));
		statusVO.setUnchecked(countMap.get("unchecked")==null? 0 : countMap.get("unchecked"));
		outputObject.convertBean2Map(statusVO);
	}
}
