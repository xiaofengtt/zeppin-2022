package cn.zeppin.project.chinamobile.media.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.project.chinamobile.media.core.entity.Category;
import cn.zeppin.project.chinamobile.media.core.entity.Commodity;
import cn.zeppin.project.chinamobile.media.core.entity.Resource;
import cn.zeppin.project.chinamobile.media.core.entity.VideoCommodityPoint;
import cn.zeppin.project.chinamobile.media.core.entity.VideoPublish;
import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo;
import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionResult;
import cn.zeppin.project.chinamobile.media.web.controller.base.BaseController;
import cn.zeppin.project.chinamobile.media.web.service.api.ICategoryService;
import cn.zeppin.project.chinamobile.media.web.service.api.ICommodityDisplayService;
import cn.zeppin.project.chinamobile.media.web.service.api.ICommodityService;
import cn.zeppin.project.chinamobile.media.web.service.api.IResourceService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoCommodityPointService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoPublishService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoinfoService;
import cn.zeppin.project.chinamobile.media.web.vo.iface.WebCategoryVO;
import cn.zeppin.project.chinamobile.media.web.vo.iface.WebCommodityDisplayVO;
import cn.zeppin.project.chinamobile.media.web.vo.iface.WebCommodityVO;
import cn.zeppin.project.chinamobile.media.web.vo.iface.WebVideoPointVO;
import cn.zeppin.project.chinamobile.media.web.vo.iface.WebVideoPublishVO;
import cn.zeppin.project.chinamobile.media.web.vo.iface.WebVideoVO;

@Controller
@RequestMapping("/webinterface")
public class WebInterfaceController extends BaseController {
	
	private static final long serialVersionUID = 6073435535477650100L;
	
	@Autowired
	private IVideoinfoService videoinfoService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private ICommodityService commodityService;
	
	@Autowired
	private ICommodityDisplayService commodityDisplayService;
	
	@Autowired
	private IVideoPublishService videoPublishService;
	
	@Autowired
	private IVideoCommodityPointService videoCommodityPointService;
	
	@RequestMapping(value="/categoryList", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<List<WebCategoryVO>> categoryList(@Valid Category catt){
		ActionResult<List<WebCategoryVO>> result = new ActionResult<List<WebCategoryVO>>();
		Category category = new Category();
		if(catt.getParent() != null && !"".equals(catt.getParent())){
			category.setParent(catt.getParent());
		}else{
			category.setLevel(1);
			category.setStatus(BaseEntity.GerneralStatusType.NORMAL);
		}
		
		int recordCount = this.categoryService.getCountByParams(category);
		List<Entity> list = this.categoryService.getListByParams(category, WebCategoryVO.class);
		List<WebCategoryVO> resultData = new ArrayList<WebCategoryVO>();
		for(Entity entity : list){
			WebCategoryVO wcat = (WebCategoryVO) entity;
			Category cat = new Category();
			cat.setParent(wcat.getId());
			List<Entity> childlist = this.categoryService.getListByParams(cat, WebCategoryVO.class);
			List<WebCategoryVO>childLists = new ArrayList<WebCategoryVO>();
			for(Entity ent:childlist){
				WebCategoryVO wcats = (WebCategoryVO) ent;
				childLists.add(wcats);
			}
			wcat.setChild(childLists);
			resultData.add(wcat);
		}
		result.setData(resultData);
		result.setTotalResultCount(recordCount);
		result.setMessage("获取成功");
		result.setStatus("success");
		return result;
	}
	
	@RequestMapping(value="/publishList", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<List<WebVideoPublishVO>> publishList(@Valid VideoPublish category, @RequestParam Integer pagenum,  @RequestParam Integer pagesize, @RequestParam String sort){
		ActionResult<List<WebVideoPublishVO>> result = new ActionResult<List<WebVideoPublishVO>>();
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
		VideoPublish videoPublish = new VideoPublish();
		List<Entity> list = new ArrayList<Entity>();
		int recordCount = 0;
		
		if(category.getVideo() != null && !"".equals(category.getVideo())){
			List<VideoPublish> vps = this.videoPublishService.getListByParams(category.getVideo());
			if(vps != null && vps.size() > 0){
				category.setCategory(vps.get(0).getCategory());
			}else{
				List<Category> listAll = this.categoryService.getAll();
				category.setCategory(listAll.get(0).getId());
			}
		}
		
		if(category.getCategory() != null && !"".equals(category.getCategory())){
			Category cat = this.categoryService.get(category.getCategory());
			if(cat != null){
				String scode = cat.getScode();
				videoPublish.setStatus(VideoPublish.VideoPublishStatusType.CHECKED);
				recordCount = this.videoPublishService.getCountByParams(videoPublish, scode);
				List<Entity> listData = this.videoPublishService.getListByParams(videoPublish, scode, sort, offset, pagesize, WebVideoPublishVO.class);
				list.addAll(listData);
			}else{
				result.setMessage("栏目不存在");
				result.setStatus("fail");
				return result;
			}
		}else{
			result.setMessage("参数异常");
			result.setStatus("fail");
			return result;
		}
		int pageCount =  (int) Math.ceil((double) recordCount / pagesize);
		List<WebVideoPublishVO> lstData = new ArrayList<WebVideoPublishVO>();
		for(Entity entity:list){
			WebVideoPublishVO wvp = (WebVideoPublishVO)entity;
			wvp.setVideoPath(wvp.getVideoURL()+"transcode/video_500K.mp4");
			lstData.add(wvp);
		}
		result.setData(lstData);
		result.setTotalResultCount(recordCount);
		result.setMessage("获取成功");
		result.setStatus("success");
		result.setPageNum(pagenum);
		result.setPageSize(pagesize);
		result.setTotalPageCount(pageCount);
		return result;
	}
	
	@RequestMapping(value="/videoInfo", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<WebVideoVO> videoInfo(@Valid Videoinfo videoinfo){
		ActionResult<WebVideoVO> result = new ActionResult<WebVideoVO>();
		WebVideoVO wvv = new WebVideoVO();
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			Videoinfo vi = this.videoinfoService.get(videoinfo.getId());
			if(vi != null){
				wvv.setVideoTitle(vi.getTitle());
				wvv.setVideoContext(vi.getContext());
				wvv.setVideoThumbanil(vi.getThumbnail());
				wvv.setVideoTimeLength(vi.getTimeLength());
				List<String> videoURLs = new ArrayList<String>();
				videoURLs.add(vi.getVideo()+"transcode/video_250K.mp4");
				videoURLs.add(vi.getVideo()+"transcode/video_500K.mp4");
				videoURLs.add(vi.getVideo()+"transcode/video_1000K.mp4");
				wvv.setVideoURLs(videoURLs);
				VideoCommodityPoint videoCommodityPoint = new VideoCommodityPoint();
				videoCommodityPoint.setVideo(vi.getId());
				List<Entity> list = this.videoCommodityPointService.getListByParam(videoCommodityPoint, WebVideoPointVO.class);
				List<WebVideoPointVO> listData = new ArrayList<WebVideoPointVO>();
				if(list != null && list.size() > 0){
					for(Entity entity: list){
						WebVideoPointVO wvpv = (WebVideoPointVO)entity;
						Resource commodityCover = this.resourceService.get(wvpv.getCommodityCover());
						wvpv.setCommodityCover(commodityCover.getUrl());
						Resource showBanner = this.resourceService.get(wvpv.getShowBanner());
						wvpv.setShowBannerURL(showBanner.getUrl());
						listData.add(wvpv);
					}
				}
				wvv.setWebVideoPoints(listData);
				
				result.setData(wvv);
				result.setMessage("获取成功");
				result.setStatus("success");
				
			}else{
				result.setMessage("视频不存在");
				result.setStatus("fail");
				return result;
			}
			
		}else{
			result.setMessage("参数异常");
			result.setStatus("fail");
			return result;
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/commodityInfo", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<WebCommodityVO> commodityInfo(@Valid Commodity commodity){
		ActionResult<WebCommodityVO> result = new ActionResult<WebCommodityVO>();
		if(commodity.getId() != null &&!"".equals(commodity.getId())){
			WebCommodityVO wcv = new WebCommodityVO();
			Commodity com = this.commodityService.get(commodity.getId());
			if(com != null){
				wcv.setCommodityURL(com.getUrl());
				wcv.setName(com.getName());
				wcv.setOriginalPrice(com.getOriginalPrice());
				wcv.setPrice(com.getPrice());
				List<Entity> list = this.commodityDisplayService.getListByCommodity(com, "display_index", WebCommodityDisplayVO.class);
				if(list != null && list.size()>0){
					wcv.setWebCommodityDisplays(list);
				}
				
				result.setData(wcv);
				
				result.setMessage("获取成功");
				result.setStatus("success");
			}else{
				result.setMessage("商品不存在");
				result.setStatus("fail");
				return result;
			}
		}else{
			result.setMessage("参数异常");
			result.setStatus("fail");
			return result;
		}
		return result;
	}
//	@RequestMapping(value="/categoryList", method = RequestMethod.GET) 
//	@ResponseBody
//	public ActionResult<List<Entity>> categoryList(@Valid Videoinfo videoinfo, @RequestParam Integer pagenum,  @RequestParam Integer pagesize, @RequestParam String sort) {
//		ActionResult<List<Entity>> result = new ActionResult<List<Entity>>();
//		if(pagenum == null || pagenum == 0){
//			pagenum = 1;
//		}
//		if(pagesize == null || pagesize == 0){
//			pagesize = 10;
//		}
//		int offset = (pagenum - 1) * pagesize;
//		
//		if(sort == null || "".equals(sort)){
//			sort = "createtime";
//		}
//		
//		int recordCount = this.videoinfoService.getCountByParams(videoinfo);
//		int pageCount = (int) Math.ceil((double) recordCount / pagesize);
//
//		List<Entity> list = this.videoinfoService.getListForPage(videoinfo, sort, offset, pagesize, VideoinfoVO.class);
//		result.setData(list);
//		result.setTotalPageCount(pageCount);
//		result.setPageNum(pagenum);
//		result.setPageSize(pagesize);
//		result.setTotalResultCount(recordCount);
//		result.setMessage("获取成功");
//		result.setStatus("success");
//		return result;
//	}
	
//	@RequestMapping(value="/videoList", method = RequestMethod.GET) 
//	@ResponseBody
//	public ActionResult<List<Entity>> videoList(@Valid Videoinfo videoinfo, @RequestParam Integer pagenum,  @RequestParam Integer pagesize, @RequestParam String sort) {
//		ActionResult<List<Entity>> result = new ActionResult<List<Entity>>();
//		if(pagenum == null || pagenum == 0){
//			pagenum = 1;
//		}
//		if(pagesize == null || pagesize == 0){
//			pagesize = 10;
//		}
//		int offset = (pagenum - 1) * pagesize;
//		
//		if(sort == null || "".equals(sort)){
//			sort = "createtime";
//		}
//		
//		int recordCount = this.videoinfoService.getCountByParams(videoinfo);
//		int pageCount = (int) Math.ceil((double) recordCount / pagesize);
//
//		List<Entity> list = this.videoinfoService.getListForPage(videoinfo, sort, offset, pagesize, VideoinfoVO.class);
//		result.setData(list);
//		result.setTotalPageCount(pageCount);
//		result.setPageNum(pagenum);
//		result.setPageSize(pagesize);
//		result.setTotalResultCount(recordCount);
//		result.setMessage("获取成功");
//		result.setStatus("success");
//		return result;
//	}
//	
//	@RequestMapping(value="/load", method = RequestMethod.GET)
//	@ResponseBody
//	public ActionResult<Videoinfo> load(@Valid Videoinfo videoinfo){
//		ActionResult<Videoinfo> result = new ActionResult<Videoinfo>();
//		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
//			Videoinfo video = this.videoinfoService.get(videoinfo.getId());
//			if(video!=null){
//				result.setData(video);
//				result.setMessage("加载成功");
//				result.setStatus("success");
//				return result;
//			}else{
//				result.setMessage("ID不正确");
//				result.setStatus("error");
//				return result;
//			}
//		}else{
//			result.setMessage("ID不能为空");
//			result.setStatus("error");
//			return result;
//		}
//	}
//	
//	@RequestMapping(value="/add", method = RequestMethod.POST) 
//	@ResponseBody
//	public ActionResult<String> add(@Valid Videoinfo videoinfo,HttpSession session,HttpServletRequest request){
//		String serverPath = request.getSession().getServletContext().getRealPath("");
//		ActionResult<String> result = new ActionResult<String>();
//		User user = (User)session.getAttribute("currentUser");
//		Videoinfo video = new Videoinfo();
//		
//		if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
//			video.setTitle(videoinfo.getTitle());
//		}else{
//			result.setMessage("标题不能为空");
//			result.setStatus("error");
//			return result;
//		}
//		
//		if(videoinfo.getOriginalVideo() != null && !"".equals(videoinfo.getOriginalVideo())){
//			Resource resource = this.resourceService.get(videoinfo.getOriginalVideo());
//			if(resource!=null){
//				video.setOriginalVideo(videoinfo.getOriginalVideo());
//				try {
//					video.setVideo(resource.getUrl().substring(0,resource.getUrl().lastIndexOf("/")+1));
//			        video.setTimeLength(VideoUtlity.getVideoLenth(serverPath + "/" + resource.getUrl()));
//			        Boolean makeResult = VideoUtlity.makeThumbnail(resource, video.getTimeLength());
//			        if(!makeResult){
//			        	result.setMessage("文件上处理失败，请刷新页面");
//						result.setStatus("error");
//						return result;
//			        }
//			        video.setThumbnail(video.getVideo()+"thumbnail.jpg");
//				} catch (Exception e) {
//					e.printStackTrace();
//					result.setMessage("文件上传失败，请刷新页面");
//					result.setStatus("error");
//					return result;
//				}
//			}else{
//				result.setMessage("文件上传失败，请刷新页面");
//				result.setStatus("error");
//				return result;
//			}
//		}else{
//			result.setMessage("未上传视频");
//			result.setStatus("error");
//			return result;
//		}
//		
//		video.setStatus(VideoStatusType.UPLOADED);
//		video.setCreatetime(new Timestamp((new Date()).getTime()));
//		video.setCreator(user.getId());
//		video.setAuthor(videoinfo.getAuthor());
//		video.setTag(videoinfo.getTag());
//		video.setContext(videoinfo.getContext());
//		video.setSource(videoinfo.getSource());
//		video.setCopyright(videoinfo.getCopyright());
//		video.setTranscodingFlag(true);
//		this.videoinfoService.insert(video);
//		result.setMessage("添加成功");
//		result.setStatus("success");
//		return result;
//	}
//	
//	@RequestMapping(value="/edit", method = RequestMethod.POST) 
//	@ResponseBody
//	public ActionResult<String> edit(@Valid Videoinfo videoinfo,HttpSession session){
//		ActionResult<String> result = new ActionResult<String>();
//		
//		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
//			Videoinfo video = this.videoinfoService.get(videoinfo.getId());
//			if(video!=null){
//				//名称
//				if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
//					video.setTitle(videoinfo.getTitle());
//				}else{
//					result.setMessage("标题不能为空");
//					result.setStatus("error");
//					return result;
//				}
//				
//				video.setAuthor(videoinfo.getAuthor());
//				video.setTag(videoinfo.getTag());
//				video.setContext(videoinfo.getContext());
//				video.setSource(videoinfo.getSource());
//				video.setAuthor(videoinfo.getAuthor());
//				video.setCopyright(videoinfo.getCopyright());
//				this.videoinfoService.update(video);
//				result.setMessage("修改成功");
//				result.setStatus("success");
//				return result;
//			}else{
//				result.setMessage("ID不正确");
//				result.setStatus("error");
//				return result;
//			}
//		}else{
//			result.setMessage("ID不能为空");
//			result.setStatus("error");
//			return result;
//		}
//	}
//	
//	@RequestMapping(value="/delete", method = RequestMethod.GET) 
//	@ResponseBody
//	public ActionResult<String> delete(@Valid Videoinfo videoinfo){
//		ActionResult<String> result = new ActionResult<String>();
//		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
//			Videoinfo video = this.videoinfoService.get(videoinfo.getId());
//			if(video != null){
//				this.videoinfoService.delete(video);
//				result.setMessage("删除成功");
//				result.setStatus("success");
//				return result;
//			}else{
//				result.setMessage("ID不正确");
//				result.setStatus("error");
//				return result;
//			}
//		}else {
//			result.setMessage("ID不能为空");
//			result.setStatus("error");
//			return result;
//		}
//	}
//	
//	@RequestMapping(value="/changeStatus", method = RequestMethod.GET) 
//	@ResponseBody
//	public ActionResult<String> changeStatus(@Valid Videoinfo videoinfo){
//		ActionResult<String> result = new ActionResult<String>();
//		if(videoinfo.getId() != null && !"".equals(videoinfo.getId()) && videoinfo.getStatus() != null && !"".equals(videoinfo.getStatus())){
//			Videoinfo video = this.videoinfoService.get(videoinfo.getId());
//			if(video != null){
//				video.setStatus(videoinfo.getStatus());
//				this.videoinfoService.update(video);
//				result.setMessage("修改成功");
//				result.setStatus("success");
//				return result;
//			}else{
//				result.setMessage("ID不正确");
//				result.setStatus("error");
//				return result;
//			}
//		}else {
//			result.setMessage("ID不能为空");
//			result.setStatus("error");
//			return result;
//		}
//	}
//	
//	@RequestMapping(value="/statusList", method = RequestMethod.GET) 
//	@ResponseBody
//	public ActionResult<VideoStatusMapVO> statusList(){
//		ActionResult<VideoStatusMapVO> result = new ActionResult<VideoStatusMapVO>();
//		Map<String,Integer> dataMap = this.videoinfoService.getStatusCount();
//		VideoStatusMapVO statusVO = new VideoStatusMapVO();
//		statusVO.setChecked(dataMap.get("checked")==null? 0 : dataMap.get("checked"));
//		statusVO.setUnchecked(dataMap.get("unchecked")==null? 0 : dataMap.get("unchecked"));
//		statusVO.setUploaded(dataMap.get("uploaded")==null? 0 : dataMap.get("uploaded"));
//		statusVO.setDeleted(dataMap.get("deleted")==null? 0 : dataMap.get("deleted"));
//		statusVO.setFailed(dataMap.get("failed")==null? 0 : dataMap.get("failed"));
//		statusVO.setTranscoding(dataMap.get("transcoding")==null? 0 : dataMap.get("transcoding"));
//		result.setData(statusVO);
//		result.setMessage("获取成功");
//		result.setStatus("success");
//		return result;
//	}
}
