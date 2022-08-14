package com.cmos.china.mobile.media.core.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Commodity;
import com.cmos.china.mobile.media.core.bean.Entity;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.bean.VideoCommodityPoint;
import com.cmos.china.mobile.media.core.bean.VideoIframe;
import com.cmos.china.mobile.media.core.bean.Videoinfo;
import com.cmos.china.mobile.media.core.service.IVideoCommodityPointService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.VideoPointVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public class VideoCommodityPointServiceImpl extends BaseServiceImpl implements IVideoCommodityPointService {
 
	/**
	 * 打点信息列表
	 */
	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String video = inputObject.getValue("video");
		String sort = inputObject.getValue("sort");
		
		if(!Utlity.checkOrderBy(sort)){
			throw new ExceptionUtil("参数异常");
		}
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("video", video);
		paramMap.put("sort", sort);
		
		List<VideoPointVO> list = this.getBaseDao().queryForList("videoCommodityPoint_getListByParams", paramMap, VideoPointVO.class);
		outputObject.convertBeans2List(list);
	}

	/**
	 * 加载详细打点信息
	 */
	@Override
	public void load(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		VideoCommodityPoint videoCommodityPoint = this.getBaseDao().queryForObject("videoCommodityPoint_get", id, VideoCommodityPoint.class);
		if(videoCommodityPoint==null){
			throw new ExceptionUtil("不存在");
		}else{
			outputObject.convertBean2Map(videoCommodityPoint);
		}
	}

	/**
	 * 加载详细打点关联信息
	 */
	@Override
	public void loadVO(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
		
		List<VideoPointVO> list = this.getBaseDao().queryForList("videoCommodityPoint_getListByParams", paramMap, VideoPointVO.class);
		
		if(!list.isEmpty()){
			outputObject.convertBean2Map(list.get(0));
		}else{
			throw new ExceptionUtil("不存在");
		}
	}

	/**
	 * 添加打点信息
	 */
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String video = inputObject.getValue("video");
		String showType = inputObject.getValue("showType");
		String timepoint = inputObject.getValue("timepoint");
		String iframe = inputObject.getValue("iframe");
		String commodity = inputObject.getValue("commodity");
		String showMessage = inputObject.getValue("showMessage");
		String showBanner = inputObject.getValue("showBanner");
		String creator = inputObject.getValue("creator");
		String showtime = inputObject.getValue("showTime");
		
		if(showType==null||"".equals(showType)){
			throw new ExceptionUtil("未选择显示类型");
		}
		if(timepoint==null||"".equals(timepoint)){
			throw new ExceptionUtil("未完成打点");
		}
		if(video==null||"".equals(video)){
			throw new ExceptionUtil("未完成打点");
		}
		if(iframe==null||"".equals(iframe)){
			throw new ExceptionUtil("未完成打点");
		}
		if(commodity==null||"".equals(commodity)){
			throw new ExceptionUtil("未选择商品");
		}
		if(showBanner==null||"".equals(showBanner)){
			throw new ExceptionUtil("未上传提示banner");
		}
		
		VideoCommodityPoint point = new VideoCommodityPoint();
		String id = UUID.randomUUID().toString();
		point.setId(id);
		point.setShowType(showType);
		point.setTimepoint(timepoint);
		point.setShowtime(Integer.valueOf(showtime));
		Videoinfo videoinfo = this.getBaseDao().queryForObject("videoinfo_get", video, Videoinfo.class);
		if(videoinfo!=null){
			point.setVideo(video);
		}else{
			throw new ExceptionUtil("打点失败，请重新打点");
		}
		VideoIframe videoiframe = this.getBaseDao().queryForObject("videoIframe_get", iframe, VideoIframe.class);
		if(videoiframe!=null){
			point.setIframe(iframe);
		}else{
			throw new ExceptionUtil("打点失败，请重新打点");
		}
		Commodity com = this.getBaseDao().queryForObject("commodity_get", commodity, Commodity.class);
		if(com!=null){
			point.setCommodity(commodity);
		}else{
			throw new ExceptionUtil("所选商品不存在");
		}
		Resource resource = this.getBaseDao().queryForObject("resource_get", showBanner, Resource.class);
		if(resource!=null){
			point.setShowBanner(showBanner);
		}else{
			throw new ExceptionUtil("banner上传失败");
		}
		point.setStatus(Entity.GerneralStatusType.NORMAL);
		point.setCreatetime(new Timestamp((new Date()).getTime()));
		point.setCreator(creator);
		point.setShowMessage(showMessage);
		this.getBaseDao().insert("videoCommodityPoint_add", point);
	}

	/**
	 * 编辑打点信息
	 */
	@Override
	public void edit(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		String commodity = inputObject.getValue("commodity");
		String showType = inputObject.getValue("showType");
		String showMessage = inputObject.getValue("showMessage");
		String showBanner = inputObject.getValue("showBanner");
		String showtime = inputObject.getValue("showTime");
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		if(showType==null||"".equals(showType)){
			throw new ExceptionUtil("未选择显示类型");
		}
		if(commodity==null||"".equals(commodity)){
			throw new ExceptionUtil("未选择商品");
		}
		if(showBanner==null||"".equals(showBanner)){
			throw new ExceptionUtil("未上传提示banner");
		}
		
		VideoCommodityPoint point = this.getBaseDao().queryForObject("videoCommodityPoint_get", id, VideoCommodityPoint.class);
		if(point!=null){
			point.setShowType(showType);
			point.setShowtime(Integer.valueOf(showtime));
			Commodity com = this.getBaseDao().queryForObject("commodity_get", commodity, Commodity.class);
			if(com!=null){
				point.setCommodity(commodity);
			}else{
				throw new ExceptionUtil("所选商品不存在");
			}
			
			Resource resource = this.getBaseDao().queryForObject("resource_get", showBanner, Resource.class);
			if(resource!=null){
				point.setShowBanner(showBanner);
			}else{
				throw new ExceptionUtil("banner上传失败");
			}
			point.setShowMessage(showMessage);
			this.getBaseDao().update("videoCommodityPoint_update", point);
		}else{
			throw new ExceptionUtil("节点不存在");
		}
	}

	/**
	 * 删除打点信息
	 */
	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		VideoCommodityPoint videoCommodityPoint = this.getBaseDao().queryForObject("videoCommodityPoint_get", id, VideoCommodityPoint.class);
		if(videoCommodityPoint==null){
			throw new ExceptionUtil("节点不存在");
		}else{
			this.getBaseDao().update("videoCommodityPoint_delete", videoCommodityPoint);
		}
	}

}
