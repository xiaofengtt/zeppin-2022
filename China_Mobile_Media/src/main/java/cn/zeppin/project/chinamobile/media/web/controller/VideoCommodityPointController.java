package cn.zeppin.project.chinamobile.media.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.project.chinamobile.media.core.entity.Commodity;
import cn.zeppin.project.chinamobile.media.core.entity.Resource;
import cn.zeppin.project.chinamobile.media.core.entity.User;
import cn.zeppin.project.chinamobile.media.core.entity.VideoCommodityPoint;
import cn.zeppin.project.chinamobile.media.core.entity.VideoIframe;
import cn.zeppin.project.chinamobile.media.core.entity.Videoinfo;
import cn.zeppin.project.chinamobile.media.core.entity.base.Entity;
import cn.zeppin.project.chinamobile.media.core.entity.base.BaseEntity.GerneralStatusType;
import cn.zeppin.project.chinamobile.media.web.controller.base.ActionResult;
import cn.zeppin.project.chinamobile.media.web.controller.base.BaseController;
import cn.zeppin.project.chinamobile.media.web.service.api.ICommodityService;
import cn.zeppin.project.chinamobile.media.web.service.api.IResourceService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoCommodityPointService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoIframeService;
import cn.zeppin.project.chinamobile.media.web.service.api.IVideoinfoService;
import cn.zeppin.project.chinamobile.media.web.vo.VideoPointVO;

@Controller
@RequestMapping("/videoPoint")
public class VideoCommodityPointController extends BaseController {
	
	private static final long serialVersionUID = 6073435535477650100L;
	
	@Autowired
	private IVideoCommodityPointService videoCommodityPointService;
	
	@Autowired
	private IVideoinfoService videoinfoService;
	
	@Autowired
	private IResourceService resourceService;
	
	@Autowired
	private IVideoIframeService videoIframeService;
	
	@Autowired
	private ICommodityService commodityService;
	
	@RequestMapping(value="/list", method = RequestMethod.GET) 
	@ResponseBody
	public ActionResult<List<Entity>> list(@Valid VideoCommodityPoint VideoCommodityPoint , String sorts) {
		ActionResult<List<Entity>> result = new ActionResult<List<Entity>>();

		List<Entity> list = this.videoCommodityPointService.getListForPage(VideoCommodityPoint, sorts, null, null, VideoPointVO.class);
		result.setData(list);
		result.setMessage("获取成功");
		result.setStatus("success");
		return result;
	}
	
	@RequestMapping(value="/load", method = RequestMethod.GET)
	@ResponseBody
	public ActionResult<VideoCommodityPoint> load(@Valid VideoCommodityPoint videoCommodityPoint){
		ActionResult<VideoCommodityPoint> result = new ActionResult<VideoCommodityPoint>();
		if(videoCommodityPoint.getId() != null && !"".equals(videoCommodityPoint.getId())){
			VideoCommodityPoint videoPoint = this.videoCommodityPointService.get(videoCommodityPoint.getId());
			if(videoPoint!=null){
				result.setData(videoPoint);
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
	public ActionResult<Entity> loadVO(@Valid VideoCommodityPoint videoCommodityPoint){
		ActionResult<Entity> result = new ActionResult<Entity>();
		if(videoCommodityPoint.getId() != null && !"".equals(videoCommodityPoint.getId())){
			List<Entity> pointList = this.videoCommodityPointService.getListByParams(videoCommodityPoint, VideoPointVO.class);
			if(pointList.size()>0){
				result.setData(pointList.get(0));
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
	public ActionResult<String> add(@Valid VideoCommodityPoint videoCommodityPoint,HttpSession session,HttpServletRequest request){
		ActionResult<String> result = new ActionResult<String>();
		User user = (User)session.getAttribute("currentUser");
		VideoCommodityPoint point = new VideoCommodityPoint();
		
		if(videoCommodityPoint.getShowType() != null && !"".equals(videoCommodityPoint.getShowType())){
			point.setShowType(videoCommodityPoint.getShowType());
		}else{
			result.setMessage("未完选择显示类型");
			result.setStatus("error");
			return result;
		}
		
		if(videoCommodityPoint.getTimepoint() != null && !"".equals(videoCommodityPoint.getTimepoint())){
			point.setTimepoint(videoCommodityPoint.getTimepoint());
		}else{
			result.setMessage("未完成打点");
			result.setStatus("error");
			return result;
		}
		
		if(videoCommodityPoint.getVideo() != null && !"".equals(videoCommodityPoint.getVideo())){
			Videoinfo video = this.videoinfoService.get(videoCommodityPoint.getVideo());
			if(video!=null){
				point.setVideo(videoCommodityPoint.getVideo());
			}else{
				result.setMessage("打点失败，请重新打点");
				result.setStatus("error");
				return result;
			}
		}else{
			result.setMessage("未完成打点");
			result.setStatus("error");
			return result;
		}
		
		if(videoCommodityPoint.getIframe() != null && !"".equals(videoCommodityPoint.getIframe())){
			VideoIframe iframe = this.videoIframeService.get(videoCommodityPoint.getIframe());
			if(iframe!=null){
				point.setIframe(videoCommodityPoint.getIframe());
			}else{
				result.setMessage("打点失败，请重新打点");
				result.setStatus("error");
				return result;
			}
		}else{
			result.setMessage("未完成打点");
			result.setStatus("error");
			return result;
		}
		
		if(videoCommodityPoint.getCommodity() != null && !"".equals(videoCommodityPoint.getCommodity())){
			Commodity commodity = this.commodityService.get(videoCommodityPoint.getCommodity());
			if(commodity!=null){
				point.setCommodity(videoCommodityPoint.getCommodity());
			}else{
				result.setMessage("所选商品不存在");
				result.setStatus("error");
				return result;
			}
		}else{
			result.setMessage("未选择商品");
			result.setStatus("error");
			return result;
		}
		
		if(videoCommodityPoint.getShowBanner() != null && !"".equals(videoCommodityPoint.getShowBanner())){
			Resource resource = this.resourceService.get(videoCommodityPoint.getShowBanner());
			if(resource!=null){
				point.setShowBanner(videoCommodityPoint.getShowBanner());
			}else{
				result.setMessage("提示banner上传失败");
				result.setStatus("error");
				return result;
			}
		}else{
			result.setMessage("未上传提示banner");
			result.setStatus("error");
			return result;
		}
		
		point.setStatus(GerneralStatusType.NORMAL);
		point.setCreatetime(new Timestamp((new Date()).getTime()));
		point.setCreator(user.getId());
		point.setShowMessage(videoCommodityPoint.getShowMessage());
		this.videoCommodityPointService.insert(point);
		result.setMessage("添加成功");
		result.setStatus("success");
		return result;
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.POST) 
	@ResponseBody
	public ActionResult<String> edit(@Valid VideoCommodityPoint videoCommodityPoint,HttpSession session){
		ActionResult<String> result = new ActionResult<String>();
		
		if(videoCommodityPoint.getId() != null && !"".equals(videoCommodityPoint.getId())){
			VideoCommodityPoint point = this.videoCommodityPointService.get(videoCommodityPoint.getId());
			if(point!=null){
				if(videoCommodityPoint.getCommodity() != null && !"".equals(videoCommodityPoint.getCommodity())){
					Commodity commodity = this.commodityService.get(videoCommodityPoint.getCommodity());
					if(commodity!=null){
						point.setCommodity(videoCommodityPoint.getCommodity());
					}else{
						result.setMessage("所选商品不存在");
						result.setStatus("error");
						return result;
					}
				}else{
					result.setMessage("未选择商品");
					result.setStatus("error");
					return result;
				}
				
				if(videoCommodityPoint.getShowType() != null && !"".equals(videoCommodityPoint.getShowType())){
					point.setShowType(videoCommodityPoint.getShowType());
				}else{
					result.setMessage("未完选择显示类型");
					result.setStatus("error");
					return result;
				}
				
				if(videoCommodityPoint.getShowBanner() != null && !"".equals(videoCommodityPoint.getShowBanner())){
					Resource resource = this.resourceService.get(videoCommodityPoint.getShowBanner());
					if(resource!=null){
						point.setShowBanner(videoCommodityPoint.getShowBanner());
					}else{
						result.setMessage("提示banner上传失败");
						result.setStatus("error");
						return result;
					}
				}else{
					result.setMessage("未上传提示banner");
					result.setStatus("error");
					return result;
				}
				point.setShowMessage(videoCommodityPoint.getShowMessage());
				this.videoCommodityPointService.update(point);
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
	public ActionResult<String> delete(@Valid VideoCommodityPoint videoCommodityPoint){
		ActionResult<String> result = new ActionResult<String>();
		if(videoCommodityPoint.getId() != null && !"".equals(videoCommodityPoint.getId())){
			VideoCommodityPoint point = this.videoCommodityPointService.get(videoCommodityPoint.getId());
			if(point != null){
				this.videoCommodityPointService.delete(point);
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
}
