package cn.zeppin.project.chinamobile.media.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo;
import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo.VideoStatusType;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.utility.VideoUtlity;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionResult;
import cn.zeppin.project.chinamobile.media.web.controller.base.BaseController;
import cn.zeppin.project.chinamobile.media.web.service.api.IResourceService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoinfoService;
import cn.zeppin.project.chinamobile.media.web.vo.VideoStatusMapVO;
import cn.zeppin.project.chinamobile.media.web.vo.VideoinfoVO;

@Controller
@RequestMapping("/videoinfo")
public class VideoinfoController extends BaseController {
	
	private static final long serialVersionUID = 6073435535477650100L;
	
	@Autowired
	private IVideoinfoService videoinfoService;
	
	@Autowired
	private IResourceService resourceService;
	
	@RequestMapping(value="/list", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<List<Entity>> list(@Valid Videoinfo videoinfo, @RequestParam Integer pagenum,  @RequestParam Integer pagesize, @RequestParam String sort) {
		ActionResult<List<Entity>> result = new ActionResult<List<Entity>>();
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
		
		int recordCount = this.videoinfoService.getCountByParams(videoinfo);
		int pageCount = (int) Math.ceil((double) recordCount / pagesize);

		List<Entity> list = this.videoinfoService.getListForPage(videoinfo, sort, offset, pagesize, VideoinfoVO.class);
		result.setData(list);
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
	public ActionResult<Videoinfo> load(@Valid Videoinfo videoinfo){
		ActionResult<Videoinfo> result = new ActionResult<Videoinfo>();
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			Videoinfo video = this.videoinfoService.get(videoinfo.getId());
			if(video!=null){
				result.setData(video);
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
	
	@RequestMapping(value="/loadVO", method = RequestMethod.GET)
	@ResponseBody
	public ActionResult<Entity> loadVO(@Valid Videoinfo videoinfo){
		ActionResult<Entity> result = new ActionResult<Entity>();
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			List<Entity> videoList = this.videoinfoService.getListByParams(videoinfo, VideoinfoVO.class);
			if(videoList.size() > 0){
				result.setData(videoList.get(0));
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
	public ActionResult<String> add(@Valid Videoinfo videoinfo,HttpSession session,HttpServletRequest request){
		String serverPath = request.getSession().getServletContext().getRealPath("");
		ActionResult<String> result = new ActionResult<String>();
		User user = (User)session.getAttribute("currentUser");
		Videoinfo video = new Videoinfo();
		
		if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
			video.setTitle(videoinfo.getTitle());
		}else{
			result.setMessage("标题不能为空");
			result.setStatus("error");
			return result;
		}
		
		if(videoinfo.getOriginalVideo() != null && !"".equals(videoinfo.getOriginalVideo())){
			Resource resource = this.resourceService.get(videoinfo.getOriginalVideo());
			if(resource!=null){
				video.setOriginalVideo(videoinfo.getOriginalVideo());
				try {
					video.setVideo(resource.getUrl().substring(0,resource.getUrl().lastIndexOf("/")+1));
			        video.setTimeLength(VideoUtlity.getVideoLenth(serverPath + "/" + resource.getUrl()));
			        Boolean makeResult = VideoUtlity.makeThumbnail(resource, video.getTimeLength());
			        if(!makeResult){
			        	result.setMessage("文件上处理失败，请刷新页面");
						result.setStatus("error");
						return result;
			        }
			        video.setThumbnail(video.getVideo()+"thumbnail.jpg");
				} catch (Exception e) {
					e.printStackTrace();
					result.setMessage("文件上传失败，请刷新页面");
					result.setStatus("error");
					return result;
				}
			}else{
				result.setMessage("文件上传失败，请刷新页面");
				result.setStatus("error");
				return result;
			}
		}else{
			result.setMessage("未上传视频");
			result.setStatus("error");
			return result;
		}
		
		video.setStatus(VideoStatusType.UPLOADED);
		video.setCreatetime(new Timestamp((new Date()).getTime()));
		video.setCreator(user.getId());
		video.setAuthor(videoinfo.getAuthor());
		video.setTag(videoinfo.getTag());
		video.setContext(videoinfo.getContext());
		video.setSource(videoinfo.getSource());
		video.setCopyright(videoinfo.getCopyright());
		video.setTranscodingFlag(true);
		this.videoinfoService.insert(video);
		result.setMessage("添加成功");
		result.setStatus("success");
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<String> edit(@Valid Videoinfo videoinfo,HttpSession session){
		ActionResult<String> result = new ActionResult<String>();
		
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			Videoinfo video = this.videoinfoService.get(videoinfo.getId());
			if(video!=null){
				//名称
				if(videoinfo.getTitle() != null && !"".equals(videoinfo.getTitle())){
					video.setTitle(videoinfo.getTitle());
				}else{
					result.setMessage("标题不能为空");
					result.setStatus("error");
					return result;
				}
				
				video.setAuthor(videoinfo.getAuthor());
				video.setTag(videoinfo.getTag());
				video.setContext(videoinfo.getContext());
				video.setSource(videoinfo.getSource());
				video.setAuthor(videoinfo.getAuthor());
				video.setCopyright(videoinfo.getCopyright());
				this.videoinfoService.update(video);
				result.setMessage("修改成功");
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
	
	@RequestMapping(value="/delete", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<String> delete(@Valid Videoinfo videoinfo){
		ActionResult<String> result = new ActionResult<String>();
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId())){
			Videoinfo video = this.videoinfoService.get(videoinfo.getId());
			if(video != null){
				this.videoinfoService.delete(video);
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
	public ActionResult<String> changeStatus(@Valid Videoinfo videoinfo){
		ActionResult<String> result = new ActionResult<String>();
		if(videoinfo.getId() != null && !"".equals(videoinfo.getId()) && videoinfo.getStatus() != null && !"".equals(videoinfo.getStatus())){
			Videoinfo video = this.videoinfoService.get(videoinfo.getId());
			if(video != null){
				video.setStatus(videoinfo.getStatus());
				this.videoinfoService.update(video);
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
	public ActionResult<VideoStatusMapVO> statusList(){
		ActionResult<VideoStatusMapVO> result = new ActionResult<VideoStatusMapVO>();
		Map<String,Integer> dataMap = this.videoinfoService.getStatusCount();
		VideoStatusMapVO statusVO = new VideoStatusMapVO();
		statusVO.setChecked(dataMap.get("checked")==null? 0 : dataMap.get("checked"));
		statusVO.setUnchecked(dataMap.get("unchecked")==null? 0 : dataMap.get("unchecked"));
		statusVO.setUploaded(dataMap.get("uploaded")==null? 0 : dataMap.get("uploaded"));
		statusVO.setDeleted(dataMap.get("deleted")==null? 0 : dataMap.get("deleted"));
		statusVO.setFailed(dataMap.get("failed")==null? 0 : dataMap.get("failed"));
		statusVO.setTranscoding(dataMap.get("transcoding")==null? 0 : dataMap.get("transcoding"));
		result.setData(statusVO);
		result.setMessage("获取成功");
		result.setStatus("success");
		return result;
	}
}
