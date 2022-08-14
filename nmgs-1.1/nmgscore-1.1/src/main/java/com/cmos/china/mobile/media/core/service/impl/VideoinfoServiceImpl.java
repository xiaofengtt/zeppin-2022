package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.base.VideoUtlity;
import com.cmos.china.mobile.media.core.bean.Province;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.bean.Videoinfo;
import com.cmos.china.mobile.media.core.bean.Videoinfo.VideoStatusType;
import com.cmos.china.mobile.media.core.service.IVideoinfoService;
import com.cmos.china.mobile.media.core.util.DataTimeConvert;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.VideoStatusMapVO;
import com.cmos.china.mobile.media.core.vo.VideoinfoVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public class VideoinfoServiceImpl extends BaseServiceImpl implements IVideoinfoService {
 
	private static Logger logger = LoggerFactory.getServiceLog(VideoinfoServiceImpl.class);
	/**
	 * 视频信息列表
	 */
	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		String province = inputObject.getValue("province");
		String title = inputObject.getValue("title");
		String status = inputObject.getValue("status");
		
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), -1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), -1);
		String sort = inputObject.getValue("sort");
		
		if(province==null||"".equals(province)){
			throw new ExceptionUtil("地区不能为空");
		}
		
		if(!Utlity.checkOrderBy(sort)){
			throw new ExceptionUtil("参数异常");
		}
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		paramMap.put("province", province);
		paramMap.put("title", title);
		paramMap.put("status", status);
		
		if(pagenum>=0 && pagesize>=0){
			Integer start = (pagenum - 1) * pagesize;
			paramMap.put("start", start+"");
			paramMap.put("limit", pagesize+"");
		}
		
		if (sort == null || "".equals(sort)) {
			sort = "v.sequence asc,v.createtime desc";
		} else {
			sort = sort.replaceAll("-", " ");
		}

		paramMap.put("sort", sort);
		Integer count = this.getBaseDao().getTotalCount("videoinfo_getCountByParams", paramMap);
		List<VideoinfoVO> list = this.getBaseDao().queryForList("videoinfo_getListByParams", paramMap, VideoinfoVO.class);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(pagenum>=0 && pagesize>=0){
			resultMap.put("pageNum", pagenum);
			resultMap.put("pageSize", pagesize);
			Integer pageCount = (int) Math.ceil((double) count / pagesize);
			resultMap.put("totalPageCount", pageCount);
		}else{
			resultMap.put("pageNum", 1);
			resultMap.put("pageSize", count);
			resultMap.put("totalPageCount", 1);
		}
		resultMap.put("totalResultCount", count);
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

	/**
	 * 加载视频信息
	 */
	@Override
	public void load(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", id, Videoinfo.class);
		if(videoinfo==null){
			throw new ExceptionUtil("视频不存在");
		}else{
			outputObject.convertBean2Map(videoinfo);
		}
	}

	/**
	 * 加载相关视频信息
	 */
	@Override
	public void loadVO(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		
		List<VideoinfoVO> list = this.getBaseDao().queryForList("videoinfo_getListByParams", paramMap, VideoinfoVO.class);
		
		if(!list.isEmpty()){
			outputObject.convertBean2Map(list.get(0));
		}else{
			throw new ExceptionUtil("不存在");
		}
	}

	/**
	 * 添加视频信息
	 */
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String title = inputObject.getValue("title");
		String province = inputObject.getValue("province");
		String originalVideo = inputObject.getValue("originalVideo");
		String author = inputObject.getValue("author");
		String tag = inputObject.getValue("tag");
		String context = inputObject.getValue("context");
		String source = inputObject.getValue("source");
		String copyright = inputObject.getValue("copyright");
		String creator = inputObject.getValue("creator");
		String sequenc = inputObject.getValue("sequence");
		Integer sequence = Integer.valueOf(sequenc);
		if(title==null||"".equals(title)){
			throw new ExceptionUtil("标题不能为空");
		}
		if(province==null||"".equals(province)){
			throw new ExceptionUtil("地区不能为空");
		}
		if(originalVideo==null||"".equals(originalVideo)){
			throw new ExceptionUtil("文件上传失败，请刷新页面");
		}
		
		Videoinfo videoinfo = new Videoinfo();
		String id = UUID.randomUUID().toString();
		videoinfo.setId(id);
		videoinfo.setTitle(title);
		videoinfo.setSequence(sequence);
		
		Province prov = this.getBaseDao().queryForObject("province_get", province, Province.class);
		if(prov!=null){
			videoinfo.setProvince(province);
		}else{
			throw new ExceptionUtil("地区不存在");
		}
		
		Resource resource = this.getBaseDao().queryForObject("resource_get", originalVideo, Resource.class);
		if(resource!=null){
//			String beanPath = Resource.class.getResource("").getPath();
//			String serverPath = beanPath.substring(0,beanPath.indexOf("WEB-INF"));
			String serverPath = Utlity.basePath;
			videoinfo.setOriginalVideo(originalVideo);
			try {
				videoinfo.setVideo(resource.getUrl().substring(0,resource.getUrl().lastIndexOf("/")+1));
				videoinfo.setTimeLength(VideoUtlity.getVideoLenth(serverPath + "/" + resource.getUrl()));
		        Boolean makeResult = VideoUtlity.makeThumbnail(resource, Double.valueOf(DataTimeConvert.getSecondTime(videoinfo.getTimeLength())),serverPath);
		        if(!makeResult){
		        	throw new ExceptionUtil("文件上传失败，请刷新页面");
		        }else{
		        	videoinfo.setThumbnail(videoinfo.getVideo()+"thumbnail.jpg");
		        }
			} catch (Exception e) {
				logger.warn("文件上传失败，请刷新页面", e);
				throw new ExceptionUtil("文件上传失败，请刷新页面");
			}
		}else{
			throw new ExceptionUtil("文件上传失败，请刷新页面");
		}
		
		videoinfo.setStatus(VideoStatusType.UPLOADED);
		videoinfo.setCreatetime(new Timestamp((new Date()).getTime()));
		videoinfo.setCreator(creator);
		videoinfo.setAuthor(author);
		videoinfo.setTag(tag);
		videoinfo.setContext(context);
		videoinfo.setSource(source);
		videoinfo.setCopyright(copyright);
		videoinfo.setTranscodingFlag(true);
		this.getBaseDao().insert("videoinfo_add", videoinfo);
	}

	/**
	 * 编辑视频信息
	 */
	@Override
	public void edit(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		String title = inputObject.getValue("title");
		String author = inputObject.getValue("author");
		String tag = inputObject.getValue("tag");
		String context = inputObject.getValue("context");
		String source = inputObject.getValue("source");
		String copyright = inputObject.getValue("copyright");
		String sequenc = inputObject.getValue("sequence");
		Integer sequence = Integer.valueOf(sequenc);
		System.out.println(context+"-------"+title);
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		if(title==null||"".equals(title)){
			throw new ExceptionUtil("标题不能为空");
		}
		
		Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", id, Videoinfo.class);
		if(videoinfo != null){
			videoinfo.setTitle(title);
			videoinfo.setAuthor(author);
			videoinfo.setTag(tag);
			videoinfo.setContext(context);
			videoinfo.setSource(source);
			videoinfo.setCopyright(copyright);
			videoinfo.setSequence(sequence);
			this.getBaseDao().update("videoinfo_update", videoinfo);
		}else{
			throw new ExceptionUtil("视频不存在");
		}
	}

	/**
	 * 删除视频信息
	 */
	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", id, Videoinfo.class);
		if(videoinfo==null){
			throw new ExceptionUtil("视频不存在");
		}else{
			this.getBaseDao().update("videoinfo_delete", videoinfo);
		}
	}

	/**
	 * 变更视频状态
	 */
	@Override
	public void changeStatus(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		String status = inputObject.getValue("status");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		if(status==null||"".equals(status)){
			throw new ExceptionUtil("状态不能为空");
		}
		
		Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", id, Videoinfo.class);
		if(videoinfo!=null){
			if("checked".equals(videoinfo.getStatus())){
				Map<String, String> params = new HashMap<String, String>();
				params.put("video", videoinfo.getId());
				params.put("statusNot", "deleted");
				Integer count = this.getBaseDao().getTotalCount("videoPublish_getCountByParams", params);
				if(count>0){
					throw new ExceptionUtil("视频已被发布，不可取消审核");
				}else{
					videoinfo.setStatus(status);
					this.getBaseDao().update("videoinfo_update", videoinfo);
				}
			}else{
				videoinfo.setStatus(status);
				this.getBaseDao().update("videoinfo_update", videoinfo);
			}
		}else{
			throw new ExceptionUtil("视频不存在");
		}
	}

	/**
	 * 获取分状态视频总数列表
	 */
	@Override
	public void statusList(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String title = inputObject.getValue("title");
		String province = inputObject.getValue("province");
		
		if(province==null||"".equals(province)){
			throw new ExceptionUtil("地区不能为空");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(title!=null){
			paramMap.put("title", title);
			paramMap.put("province", province);
		}
		List<Map<String,Object>> list = this.getBaseDao().queryForList("videoinfo_getStatusCount", paramMap);
		Map<String,Integer> countMap = new HashMap<String,Integer>();
		for(Map<String,Object> map : list){
			countMap.put(map.get("status").toString(), Integer.valueOf(map.get("count(status)").toString()));
		}
		VideoStatusMapVO statusVO = new VideoStatusMapVO();
		statusVO.setChecked(countMap.get("checked")==null? 0 : countMap.get("checked"));
		statusVO.setUnchecked(countMap.get("unchecked")==null? 0 : countMap.get("unchecked"));
		statusVO.setUploaded(countMap.get("uploaded")==null? 0 : countMap.get("uploaded"));
		statusVO.setDeleted(countMap.get("deleted")==null? 0 : countMap.get("deleted"));
		statusVO.setFailed(countMap.get("failed")==null? 0 : countMap.get("failed"));
		statusVO.setTranscoding(countMap.get("transcoding")==null? 0 : countMap.get("transcoding"));
		outputObject.convertBean2Map(statusVO);
	}
}
