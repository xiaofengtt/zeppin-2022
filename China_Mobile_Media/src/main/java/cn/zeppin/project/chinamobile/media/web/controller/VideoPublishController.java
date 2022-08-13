package cn.zeppin.project.chinamobile.media.web.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.project.chinamobile.media.core.entity.Resource;
import cn.zeppin.project.chinamobile.media.core.entity.User;
import cn.zeppin.project.chinamobile.media.core.entity.VideoPublish;
import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionResult;
import cn.zeppin.project.chinamobile.media.web.controller.base.BaseController;
import cn.zeppin.project.chinamobile.media.web.service.api.IResourceService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoPublishService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoinfoService;
import cn.zeppin.project.chinamobile.media.web.vo.VideoPublishVO;
import cn.zeppin.project.chinamobile.media.web.vo.VideoStatusMapVO;
import cn.zeppin.project.chinamobile.media.web.vo.VideoinfoVO;

@Controller
@RequestMapping("/videopublish")
public class VideoPublishController extends BaseController {
	
	private static final long serialVersionUID = 6073435535477650100L;
	
	@Autowired
	private IVideoinfoService videoinfoService;
	
	@Autowired
	private IVideoPublishService videoPublishService;
	
	@Autowired
	private IResourceService resourceService;
	
	@RequestMapping(value="/list", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<List<VideoPublishVO>> list(@Valid VideoPublish videoPublish, @RequestParam Integer pagenum,  @RequestParam Integer pagesize, @RequestParam String sort) {
		ActionResult<List<VideoPublishVO>> result = new ActionResult<List<VideoPublishVO>>();
		if(pagenum == null || pagenum == 0){
			pagenum = 1;
		}
		if(pagesize == null || pagesize == 0){
			pagesize = 10;
		}
		int offset = (pagenum - 1) * pagesize;
		
		if(sort == null || "".equals(sort)){
			sort = "createtime";
		}
		
		int recordCount = this.videoPublishService.getCountByParams(videoPublish);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Entity> list = this.videoPublishService.getListForPage(videoPublish, sort, offset, pagesize, VideoPublishVO.class);
		List<VideoPublishVO> listData = new ArrayList<VideoPublishVO>();
		if(list != null && list.size() > 0){
			for(Entity entity:list){
				VideoPublishVO vpv = (VideoPublishVO)entity;
				if(vpv.getCover() != null && !"".equals(vpv.getCover())){
					Resource cover = this.resourceService.get(vpv.getCover());
					if(cover != null){
						vpv.setCoverURL(cover.getUrl());
					}else{
						vpv.setCoverURL("/assets/img/default_productBig.jpg");
					}
					
				}else{
					vpv.setCoverURL("/assets/img/default_productBig.jpg");
				}
				listData.add(vpv);
			}
		}
		result.setData(listData);
		result.setTotalPageCount(pageCount);
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalResultCount(recordCount);
		result.setMessage("获取成功");
		result.setStatus("success");
		return result;
	}
	
	@RequestMapping(value="/load", method = RequestMethod.GET)
	@ResponseBody
	public ActionResult<Map<String,Object>> load(@Valid VideoPublish videoinfo){
		ActionResult<Map<String,Object>> result = new ActionResult<Map<String,Object>>();
		Map<String,Object> resultData = new HashMap<String, Object>();
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			VideoPublish vp = this.videoPublishService.get(videoinfo.getId());
			if(vp!=null){
				resultData.put("category", vp.getCategory());
				if(vp.getVideo() != null && !"".equals(vp.getVideo())){
					Videoinfo video = this.videoinfoService.get(vp.getVideo());
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
						Resource cover = this.resourceService.get(vp.getCover());
						if(cover!=null){
							resultData.put("cover",cover.getUrl());
						}else{
							resultData.put("cover","/assets/img/default_productBig.jpg");
						}
					}else{
						resultData.put("cover","/assets/img/default_productBig.jpg");
					}
					
					String sort = "createtime desc";
					Videoinfo info = new Videoinfo();
					info.setStatus(Videoinfo.VideoStatusType.CHECKED);
					List<Entity> list = this.videoinfoService.getListForPage(info, sort, -1, -1, VideoinfoVO.class);
					List<VideoinfoVO> data = new ArrayList<VideoinfoVO>();
					if(list != null && list.size() > 0){
						for(Entity entity : list){
							VideoinfoVO vvo = (VideoinfoVO)entity;
							if(vvo.getId().equals(video.getId())){
								continue;
							}
							data.add(vvo);
						}
						resultData.put("videoList", data);
					}
				}
				
				result.setData(resultData);
				result.setMessage("加载成功");
				result.setStatus("success");
				return result;
			}else{
				result.setMessage("ID不正确");
				result.setStatus("error");
				return result;
			}
		}else{
			result.setMessage("ID不能为空");
			result.setStatus("error");
			return result;
		}
	}
	
	@RequestMapping(value="/add", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<String> add(@Valid VideoPublish videoinfo,HttpSession session){
		ActionResult<String> result = new ActionResult<String>();
		User user = (User)session.getAttribute("currentUser");
		
		VideoPublish vp = new VideoPublish();
		if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
			vp.setTitle(videoinfo.getTitle());
		}else{
			result.setMessage("标题不能为空");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}
		
		if(videoinfo.getVideo() != null && !"0".equals(videoinfo.getVideo())){
			vp.setVideo(videoinfo.getVideo());
			
		}else{
			result.setMessage("请选择视频");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}
		
		if(videoinfo.getCategory() != null && !"".equals(videoinfo.getCategory())){
			vp.setCategory(videoinfo.getCategory());
			
		}else{
			result.setMessage("请选择栏目");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}
		
		if(videoinfo.getCover() != null && !"".equals(videoinfo.getCover())){
			vp.setCover(videoinfo.getCover());
		}
		
		if(videoinfo.getShortTitle() != null && !"".equals(videoinfo.getShortTitle())){
			vp.setShortTitle(videoinfo.getShortTitle());
			
		}else{
			vp.setShortTitle("");
		}
		
		vp.setStatus(VideoPublish.VideoPublishStatusType.UNCHECKED);
		
		vp.setCreator(user.getId());
		
		vp.setCreatetime(new Timestamp((new Date()).getTime()));
		
		try {
			this.videoPublishService.insert(vp);
			result.setMessage("添加成功");
			result.setStatus("success");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			result.setMessage("添加过程异常");
			result.setStatus("fail");
			result.setSuccess(false);
			
			return result;
		}
		
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<String> edit(@Valid VideoPublish videoinfo,HttpSession session){
		ActionResult<String> result = new ActionResult<String>();
		
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			VideoPublish vp = this.videoPublishService.get(videoinfo.getId());
			if(vp != null){
				if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
					vp.setTitle(videoinfo.getTitle());
				}else{
					result.setMessage("标题不能为空");
					result.setStatus("fail");
					result.setSuccess(false);
					
					return result;
				}
				
				if(videoinfo.getVideo() != null && !"0".equals(videoinfo.getVideo())){
					vp.setVideo(videoinfo.getVideo());
					
				}else{
					result.setMessage("请选择视频");
					result.setStatus("fail");
					result.setSuccess(false);
					
					return result;
				}
				
				if(videoinfo.getCategory() != null && !"".equals(videoinfo.getCategory())){
					vp.setCategory(videoinfo.getCategory());
					
				}else{
					result.setMessage("请选择栏目");
					result.setStatus("fail");
					result.setSuccess(false);
					
					return result;
				}
				
				if(videoinfo.getCover() != null && !"".equals(videoinfo.getCover())){
					vp.setCover(videoinfo.getCover());
				}else{
					vp.setCover(null);
				}
				
				if(videoinfo.getShortTitle() != null && !"".equals(videoinfo.getShortTitle())){
					vp.setShortTitle(videoinfo.getShortTitle());
					
				}else{
					vp.setShortTitle("");
				}
				try {
					this.videoPublishService.update(vp);
					result.setMessage("编辑成功");
					result.setStatus("success");
					return result;
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
					result.setMessage("编辑过程异常");
					result.setStatus("fail");
					
					return result;
				}
			}else{
				result.setMessage("发布信息不存在");
				result.setStatus("error");
				return result;
			}
			
		}else{
			result.setMessage("ID不能为空");
			result.setStatus("error");
			return result;
		}
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<String> delete(@Valid VideoPublish videoinfo){
		ActionResult<String> result = new ActionResult<String>();
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			VideoPublish video = this.videoPublishService.get(videoinfo.getId());
			if(video != null){
				this.videoPublishService.delete(video);
				result.setMessage("删除成功");
				result.setStatus("success");
				return result;
			}else{
				result.setMessage("ID不正确");
				result.setStatus("error");
				return result;
			}
		}else {
			result.setMessage("ID不能为空");
			result.setStatus("error");
			return result;
		}
	}
	
	@RequestMapping(value="/changeStatus", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<String> changeStatus(@Valid VideoPublish videoinfo){
		ActionResult<String> result = new ActionResult<String>();
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId()) && videoinfo.getStatus() != null && !"".equals(videoinfo.getStatus())){
			VideoPublish vp = this.videoPublishService.get(videoinfo.getId());
			if(vp != null){
				vp.setStatus(videoinfo.getStatus());
				this.videoPublishService.update(vp);
				result.setMessage("修改成功");
				result.setStatus("success");
				return result;
			}else{
				result.setMessage("ID不正确");
				result.setStatus("error");
				return result;
			}
		}else {
			result.setMessage("ID不能为空");
			result.setStatus("error");
			return result;
		}
	}
	
	@RequestMapping(value="/statusList", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<VideoStatusMapVO> statusList(@Valid VideoPublish videoinfo){
		ActionResult<VideoStatusMapVO> result = new ActionResult<VideoStatusMapVO>();
		Map<String,Integer> dataMap = this.videoPublishService.getStatusCount(videoinfo);
		VideoStatusMapVO statusVO = new VideoStatusMapVO();
		statusVO.setChecked(dataMap.get("checked")==null? 0 : dataMap.get("checked"));
		statusVO.setUnchecked(dataMap.get("unchecked")==null? 0 : dataMap.get("unchecked"));
		result.setData(statusVO);
		result.setMessage("获取成功");
		result.setStatus("success");
		return result;
	}
}
