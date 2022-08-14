package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.base.VideoUtlity;
import com.cmos.china.mobile.media.core.bean.OtherResource;
import com.cmos.china.mobile.media.core.bean.OtherVideoinfo;
import com.cmos.china.mobile.media.core.bean.OtherVideoinfo.VideoStatusType;
import com.cmos.china.mobile.media.core.service.IOtherVideoinfoService;
import com.cmos.china.mobile.media.core.util.DataTimeConvert;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.core.util.JsonUtil;
import com.cmos.core.util.StringUtil;
public class OtherVideoinfoServiceImpl extends BaseServiceImpl implements IOtherVideoinfoService {
 
	private static Logger logger = LoggerFactory.getServiceLog(OtherVideoinfoServiceImpl.class);
//	/**
//	 * 视频信息列表
//	 */ 
//	@Override
//	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
//		String id = inputObject.getValue("id");
//		String province = inputObject.getValue("province");
//		String title = inputObject.getValue("title");
//		String context = inputObject.getValue("context");
//		String status = inputObject.getValue("status");
//		String havecommodity = inputObject.getValue("havecommodity");
//		String aftertime = inputObject.getValue("aftertime");
//		String statusUpload = inputObject.getValue("statusUpload");
//		Integer pagenum = Utlity.getIntValue(inputObject.getValue("start"), 0);
//		Integer pagesize = Utlity.getIntValue(inputObject.getValue("limit"), 10);
//		String sort = inputObject.getValue("sort");
//		String creator = inputObject.getLogParams().get("userLoginName");
//		if(StringUtil.isEmpty(creator)){
//			logger.error("VideoinfoServiceImpl.list", "error_access_common"+"用户信息异常");
//			throw new ExceptionUtil("用户信息异常");
//		}
//		if(StringUtil.isEmpty(province)){
//			logger.error("VideoinfoServiceImpl.list", "error_access_common"+"参数信息异常");
//			throw new ExceptionUtil("地区不能为空");
//		}
//		
//		
//		if (StringUtil.isEmpty(sort)) {
//			sort = "v.createtime desc,v.sequence asc";
//		} else {
//			sort = sort.replaceAll("-", " ");
//		}
//		
//		if(!Utlity.checkStringNull(sort) && !Utlity.checkOrderBy(sort)){
//			logger.error("VideoinfoServiceImpl.list", "error_access_common"+"参数信息异常");
//			throw new ExceptionUtil("参数异常");
//		}
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		if(!"000".equals(province)){
//		String pro [] = province.split(",");
//		StringBuilder sb = new StringBuilder();
//		for(int i = 0 ; i < pro.length ; i++){
//			sb.append("'"+pro[i]+"',");
//		}
//		sb.deleteCharAt(sb.length()-1);
//		province = sb.toString();
//		paramMap.put("province", province);
//		}
//
//		if("yes".equals(havecommodity)){
//			paramMap.put("havecommodity", "yes");
//		}else if("no".equals(havecommodity)) {
//			paramMap.put("havecommodityNo", "no");
//		}
//		if(StringUtil.isNotEmpty(aftertime)){
//			paramMap.put("aftertime", Timestamp.valueOf(aftertime+" 00:00:00").toString());
//		}
//		
//		if(StringUtil.isNotEmpty(status)){
//			paramMap.put("status", status);
//		}
//		paramMap.put("id", id);
//		paramMap.put("title", title);
//		paramMap.put("context", context);
//
//		//新增栏目查询
//		paramMap.put("categoryFirst",inputObject.getValue("categoryFirst"));
//		paramMap.put("categorySecond",inputObject.getValue("categorySecond"));
//		paramMap.put("categorySecondList",inputObject.getValue("categorySecondList"));
//		if(pagenum>=0 && pagesize>=0){
//			paramMap.put("start", pagenum+"");
//			paramMap.put("limit", pagesize+"");
//		}
//		
//		paramMap.put("sort", sort);
//		paramMap.put("creator", creator);
//		if(!StringUtil.isEmpty(statusUpload)){
//			paramMap.put("statusUpload", statusUpload);
//		}
//		paramMap = Utlity.repalceNull(paramMap);
//		Integer count = this.getBaseDao().getTotalCount("videoinfo_getCountByParams", paramMap);
//		List<Map<String, Object>> list = this.getBaseDao().queryForList("videoinfo_getListByParams", paramMap);
//		
//		//获取绝对路径
//		for(Map<String, Object> map : list){
//			//拼写绝对路径
//			String thumbnail =  Utlity.getFullUrl(map.get("thumbnail"));
//			map.put("thumbnail", thumbnail);
//			String video =  Utlity.getFullUrl(map.get("video"));
//			map.put("video", video);
//			String originalVideoUrl =  Utlity.getFullUrl(map.get("originalVideoUrl"));
//			map.put("originalVideoUrl", originalVideoUrl);
//			String originalVideoPath = Utlity.getFullUrl(map.get("originalVideoPath"));
//			map.put("originalVideoPath", originalVideoPath);
//		}
//		
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		resultMap.put("total", count);
//		resultMap.put("start", pagenum);
//		resultMap.put("limit", pagesize);
//		
//		outputObject.setBean(resultMap);
//		outputObject.setBeans(list);
//	}
//
//	/**
//	 * 加载视频信息
//	 */
//	@Override
//	public void load(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
//		String id = inputObject.getValue("id");
//		
//		if(StringUtil.isEmpty(id)){
//			throw new ExceptionUtil("ID不能为空");
//		}
//		
//		Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", id, Videoinfo.class);
//		if(videoinfo==null){
//			logger.error("VideoinfoServiceImpl.load", "error_access_common"+"视频信息为空");
//			throw new ExceptionUtil("视频不存在");
//		}else{
//			//获取绝对路径
//			String thumbnail =  Utlity.getFullUrl(videoinfo.getThumbnail());
//			videoinfo.setThumbnail(thumbnail);
//			String video =  Utlity.getFullUrl(videoinfo.getVideo());
//			videoinfo.setVideo(video);
//			outputObject.convertBean2Map(videoinfo);
//		}
//	}
//
//	/**
//	 * 加载相关视频信息
//	 */
//	@Override
//	public void loadVO(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
//		String id = inputObject.getValue("id");
//		
//		if(StringUtil.isEmpty(id)){
//			throw new ExceptionUtil("ID不能为空");
//		}
//		
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("id", id);
//		
//		List<Map<String, Object>> list = this.getBaseDao().queryForList("videoinfo_getListByParams", paramMap);
//		
//		if(!list.isEmpty()){
//			//获取绝对路径
//			for(Map<String, Object> map : list){
//				//拼写绝对路径
//				String thumbnail =  Utlity.getFullUrl(map.get("thumbnail"));
//				map.put("thumbnail", thumbnail);
//				String video =  Utlity.getFullUrl(map.get("video"));
//				map.put("video", video);
//				String originalVideoUrl =  Utlity.getFullUrl(map.get("originalVideoUrl"));
//				map.put("originalVideoUrl", originalVideoUrl);
//				String originalVideoPath = Utlity.getFullUrl(map.get("originalVideoPath"));
//				map.put("originalVideoPath", originalVideoPath);
//			}
//			outputObject.setBean(list.get(0));
//		}else{
//			logger.error("VideoinfoServiceImpl.list", "error_access_common"+"视频信息为空");
//			throw new ExceptionUtil("不存在");
//		}
//	}
//
//	/**
//	 * 添加视频信息
//	 */
//	@Override
//	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
//		String title = inputObject.getValue("title");
//		String province = inputObject.getValue("province");
//		String originalVideo = inputObject.getValue("originalVideo");
//		String author = inputObject.getValue("authors");
//		String tag = inputObject.getValue("tag");
//		String context = inputObject.getValue("context");
//		String source = inputObject.getValue("source");
//		String copyright = inputObject.getValue("copyright");
//		String creator = inputObject.getLogParams().get("userLoginName");
//		if(StringUtil.isEmpty(creator)){
//			logger.error("VideoinfoServiceImpl.add", "error_access_common"+"用户信息异常");
//			throw new ExceptionUtil("用户信息异常");
//		}
//		String sequenc = inputObject.getValue("sequence");
//		String unfinished = inputObject.getValue("unfinished");
//		Integer sequence = null;
//		if(StringUtil.isNotEmpty(sequenc)){
//			sequence = Integer.valueOf(sequenc);
//		}
//
//		String cover = inputObject.getValue("cover");
//		if(StringUtil.isEmpty(title)){
//			logger.error("VideoinfoServiceImpl.add", "error_access_common"+"传入参数信息错误");
//			throw new ExceptionUtil("标题不能为空");
//		}
//		if(StringUtil.isEmpty(province)){
//			logger.error("VideoinfoServiceImpl.add", "error_access_common"+"传入参数信息错误");
//			throw new ExceptionUtil("地区不能为空");
//		}
//		if(StringUtil.isEmpty(originalVideo)){
//			logger.error("VideoinfoServiceImpl.add", "error_access_common"+"传入参数信息错误");
//			throw new ExceptionUtil("文件上传失败，请刷新页面");
//		}
//		
//		OtherVideoinfo videoinfo = new OtherVideoinfo();
//		String id = UUID.randomUUID().toString();
//		videoinfo.setId(id);
//		videoinfo.setTitle(title);
//		videoinfo.setSequence(sequence);
//		
//		OtherResource resource = this.getBaseDao().queryForObject("resource_get", originalVideo, OtherResource.class);
//		if(resource!=null){
//
//			String serverPath = Utlity.basePath;
//			videoinfo.setOriginalVideo(originalVideo);
//			try {
//				videoinfo.setVideo(resource.getUrl().substring(0,resource.getUrl().lastIndexOf("/")+1));
//				videoinfo.setTimeLength(VideoUtlity.getVideoLenth(serverPath + "/" + resource.getUrl()));
//		        Boolean makeResult = VideoUtlity.makeThumbnail(resource, Double.valueOf(DataTimeConvert.getSecondTime(videoinfo.getTimeLength())),serverPath);
//		        if(!makeResult){
//		        	logger.error("VideoinfoServiceImpl.add", "error_access_common"+"截取视频封面错误");
//		        	throw new ExceptionUtil("文件上传失败，请刷新页面");
//		        }else{
//		        	videoinfo.setThumbnail(videoinfo.getVideo()+"thumbnail.jpg");
//		        }
//			} catch (Exception e) {
//				logger.error("VideoinfoServiceImpl.add", "error_access_common"+"截图失败",e);
////				logger.warn("文件上传失败，请刷新页面", e);
//				throw new ExceptionUtil("文件上传失败，请刷新页面");
//			}
//		}else{
//			logger.error("VideoinfoServiceImpl.add", "error_access_common"+"资源文件不存在");
//			throw new ExceptionUtil("文件上传失败，请刷新页面");
//		}
//		if(StringUtil.isNotEmpty(cover)){
//			Resource res = (Resource) this.getBaseDao().queryForObject("resource_get", cover);
//			if(res!=null){
//			videoinfo.setThumbnail(res.getUrl());
//			}
//		}
//		if("unfinished".equals(unfinished)){
//			videoinfo.setStatus("unfinished");
//		}else{
//			videoinfo.setStatus(VideoStatusType.UPLOADED);
//		}
//		videoinfo.setCreatetime(new Timestamp((new Date()).getTime()));
//		videoinfo.setCreator(creator);
//		videoinfo.setAuthor(author);
//		videoinfo.setTag(tag);
//		videoinfo.setContext(context);
//		videoinfo.setSource(source);
//		videoinfo.setCopyright(copyright);
//		videoinfo.setTranscodingFlag(true);
//		videoinfo.setClickCount("0");
//
//		//新增一级栏目 二级栏目
//		videoinfo.setCategoryFirst(inputObject.getValue("categoryFirst"));
//		videoinfo.setCategorySecond(inputObject.getValue("categorySecond"));
//		this.getBaseDao().insert("videoinfo_add", videoinfo);
//
//		//此处添加视频省份关联表
//		//发布信息 与关联表信息和视频信息一致才能得到列表
//		if(!"000".equals(province)){
//			String pro [] = province.split(",");
//			StringBuilder sb = new StringBuilder();
//			for(int i = 0 ; i < pro.length ; i++){
//				Province prov = this.getBaseDao().queryForObject("province_getbycode", pro[i], Province.class);
//				if(prov!=null){
//				sb.append("'"+pro[i]+"',");
//				videoProvince vp = new videoProvince();
//				vp.setId(UUID.randomUUID().toString());
//				vp.setCreatetime(new Timestamp((new Date()).getTime()));
//				vp.setProvinceid(pro[i]);
//				vp.setVideoid(id);
//				vp.setCreator(creator);
//				this.getBaseDao().insert("videoinfoprovince_add", vp);
//				}
//			}
//			sb.deleteCharAt(sb.length()-1);
//			province = sb.toString();
//			if(province.length()<4){
//				logger.error("VideoinfoServiceImpl.add", "error_access_common"+"传入参数信息错误");
//				throw new ExceptionUtil("地区不存在");
//			}
//		}else{
//			Map<String, Object> paramMap = new HashMap<>();
//			List<Map<String, Object>> listpro = this.getBaseDao().queryForList("province_getListByParams", paramMap);
//			for(Map<String, Object> pv : listpro){
//			videoProvince vp = new videoProvince();
//			vp.setId(UUID.randomUUID().toString());
//			vp.setCreatetime(new Timestamp((new Date()).getTime()));
//			vp.setProvinceid(pv.get("code").toString());
//			vp.setVideoid(id);
//			vp.setCreator(creator);
//			this.getBaseDao().insert("videoinfoprovince_add", vp);
//			}
//		}
//		
//	}
//
//	/**
//	 * 编辑视频信息
//	 */
//	@Override
//	public void edit(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
//		String id = inputObject.getValue("id");
//		String title = inputObject.getValue("title");
//		String author = inputObject.getValue("authors");
//		String tag = inputObject.getValue("tag");
//		String context = inputObject.getValue("context");
//		String source = inputObject.getValue("source");
//		String copyright = inputObject.getValue("copyright");
//		String sequenc = inputObject.getValue("sequence");
//		String cover = inputObject.getValue("cover");
//		Integer sequence = Integer.valueOf(sequenc);
//		if(StringUtil.isEmpty(id)){
//			logger.error("VideoinfoServiceImpl.edit", "error_access_common"+"传入参数信息错误");
//			throw new ExceptionUtil("ID不能为空");
//		}
//		if(StringUtil.isEmpty(title)){
//			logger.error("VideoinfoServiceImpl.edit", "error_access_common"+"传入参数信息错误");
//			throw new ExceptionUtil("标题不能为空");
//		}
//		
//		OtherVideoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", id, Videoinfo.class);
//		if(videoinfo != null){
//			videoinfo.setTitle(title);
//			videoinfo.setAuthor(author);
//			videoinfo.setTag(tag);
//			videoinfo.setContext(context);
//			videoinfo.setSource(source);
//			videoinfo.setCopyright(copyright);
//			videoinfo.setSequence(sequence);
//
//			//视频编辑新增俩个字段
//			videoinfo.setCategoryFirst(inputObject.getValue("categoryFirst"));
//			videoinfo.setCategorySecond(inputObject.getValue("categorySecond"));
//
//			if(!StringUtil.isEmpty(cover)){
//				Resource res = this.getBaseDao().queryForObject("resource_get", cover, Resource.class);
//				videoinfo.setThumbnail(res.getPath());
//			}
//			logger.info("videoinfo_update","params", JsonUtil.convertObject2Json(videoinfo));
//			this.getBaseDao().update("videoinfo_update", videoinfo);
//		}else{
//			logger.error("VideoinfoServiceImpl.edit", "error_access_common"+"文件不存在");
//			throw new ExceptionUtil("视频不存在");
//		}
//	}
//
//	/**
//	 * 删除视频信息
//	 */
//	@Override
//	public void delete(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
//		String id = inputObject.getValue("id");
//		String type = inputObject.getValue("type");
//		if(StringUtil.isEmpty(id)){
//			throw new ExceptionUtil("ID不能为空");
//		}
//		if(StringUtil.isEmpty(type)){
//			throw new ExceptionUtil("修改类型不能为空");
//		}
//		if("1".equals(type)){//省份关联id
//			videoProvince videoinfo = this.getBaseDao().queryForObject("videoinfo_getPro", id, videoProvince.class);
//			if(videoinfo==null){
//				throw new ExceptionUtil("视频不存在");
//			}
//			Boolean marke = false;
//			Map<String, Object> paramMap = new HashMap<String, Object>();
//			paramMap.put("video", id);
//			List<Map<String,Object>> list = this.getBaseDao().queryForList("videoPublish_getListByParams", paramMap);
//			if(!list.isEmpty()){
//				for(Map<String,Object> vp : list){
//				if("checked".equals(vp.get("status"))){
//					marke = true;
//					}
//				}
//			}
//			if(marke){
//				throw new ExceptionUtil("视频已上架，请先下架");
//			}else{
//				this.getBaseDao().update("videoinfo_delete", videoinfo);
//			}
//		}else if("2".equals(type)){//视频id
//			Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", id, Videoinfo.class);
//			if(videoinfo==null){
//				throw new ExceptionUtil("视频不存在");
//			}
//			if(Videoinfo.VideoStatusType.PUBLISH.equals(videoinfo.getStatus())){
//				throw new ExceptionUtil("视频已发布，请先取消发布");
//			}else{
//				this.getBaseDao().update("videoinfo_delete", videoinfo);
//			}
//		} else {
//			throw new ExceptionUtil("type参数类型错误");
//		}
//	}

	@Override
	public void list(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadVO(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		// TODO Auto-generated method stub
		
	}

}