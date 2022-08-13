package cn.zeppin.project.chinamobile.media.web.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.project.chinamobile.media.core.entity.Resource;
import cn.zeppin.project.chinamobile.media.core.entity.VideoIframe;
import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.utility.DataTimeConvert;
import cn.zeppin.project.chinamobile.media.utility.VideoUtlity;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionResult;
import cn.zeppin.project.chinamobile.media.web.controller.base.BaseController;
import cn.zeppin.project.chinamobile.media.web.service.api.IResourceService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoIframeService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoinfoService;


@Controller
@RequestMapping("/videoIframe")
public class VideoIframeController extends BaseController {
	
	private static final long serialVersionUID = 2318623804948146943L;
	
	@Autowired
	private IVideoIframeService videoIframeService;
	
	@Autowired
	private IVideoinfoService videoinfoService;
	
	@Autowired
	private IResourceService resourceService;
	
	@RequestMapping(value="/add", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<Entity> add(@Valid VideoIframe videoIframe ,HttpServletRequest request){
		ActionResult<Entity> result = new ActionResult<Entity>();
		VideoIframe iframe = new VideoIframe();
		String timePoint = DataTimeConvert.getFormatTime(videoIframe.getTimepoint());
		iframe.setTimepoint(timePoint);
		iframe.setCreatetime(new Timestamp((new Date()).getTime()));
		if(videoIframe.getVideo() != null && !"".equals(videoIframe.getVideo())){
			Videoinfo video = this.videoinfoService.get(videoIframe.getVideo());
			if(video!=null){
				iframe.setVideo(videoIframe.getVideo());
				Resource resource = this.resourceService.get(video.getOriginalVideo());
				if(resource!=null){
					iframe.setPath("");
					iframe = this.videoIframeService.insert(iframe);
					System.out.println(iframe.getTimepoint());
					Boolean iframeResult = VideoUtlity.getIframe(resource,iframe.getId(),iframe.getTimepoint());
					if(iframeResult){
						iframe.setPath(resource.getUrl().substring(0,resource.getUrl().lastIndexOf("/")+1) + iframe.getId() + ".jpg");
						iframe = this.videoIframeService.update(iframe);
						result.setData(iframe);
						result.setMessage("打点成功");
						result.setStatus("success");
						return result;
					}else{
						result.setMessage("视频不存在");
						result.setStatus("error");
						return result;
					}
				}else{
					result.setMessage("视频不存在");
					result.setStatus("error");
					return result;
				}
			}else{
				result.setMessage("视频不存在");
				result.setStatus("error");
				return result;
			}
		}else{
			result.setMessage("视频不存在");
			result.setStatus("error");
			return result;
		}
		
	}
}
