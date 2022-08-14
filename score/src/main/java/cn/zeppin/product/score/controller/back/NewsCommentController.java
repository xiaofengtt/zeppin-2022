/**
 * 
 */
package cn.zeppin.product.score.controller.back;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.score.controller.base.ActionParam;
import cn.zeppin.product.score.controller.base.ActionParam.DataType;
import cn.zeppin.product.score.controller.base.BaseController;
import cn.zeppin.product.score.controller.base.Result;
import cn.zeppin.product.score.controller.base.ResultManager;
import cn.zeppin.product.score.entity.FrontUser;
import cn.zeppin.product.score.entity.NewsComment;
import cn.zeppin.product.score.entity.NewsComment.NewsCommentStatus;
import cn.zeppin.product.score.entity.NewsPublish;
import cn.zeppin.product.score.service.FrontUserService;
import cn.zeppin.product.score.service.NewsCommentService;
import cn.zeppin.product.score.service.NewsPublishService;
import cn.zeppin.product.score.vo.back.NewsCommentVO;

/**
 * 评论管理
 */

@Controller
@RequestMapping(value = "/back/newsComment")
public class NewsCommentController extends BaseController{

	@Autowired
	private NewsPublishService newsPublishService;
	
	@Autowired
	private NewsCommentService newsCommentService;
	
	@Autowired
	private FrontUserService frontUserService;
	
	/**
	 * 根据条件查询列表
	 * @param content
	 * @param newspublish
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "content", message = "名称", type = DataType.STRING)
	@ActionParam(key = "newspublish", message = "新闻", type = DataType.STRING)
	@ActionParam(key = "pageNum", message="页码", type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message="每页数量", type = DataType.NUMBER)
	@ResponseBody
	public Result list(String content, String newspublish, Integer pageNum, Integer pageSize) {
		//查询条件
		Map<String,Object> searchMap = new HashMap<String,Object>();
		searchMap.put("content", content);
		searchMap.put("newspublish", newspublish);
		searchMap.put("status", NewsCommentStatus.NORMAL);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", "createtime desc");
		
		Integer totalCount = newsCommentService.getCountByParams(searchMap);
		List<NewsComment> list = newsCommentService.getListByParams(searchMap);
		
		List<NewsCommentVO> voList = new LinkedList<NewsCommentVO>();
		for(NewsComment nc : list){
			NewsCommentVO vo = new NewsCommentVO(nc);
			
			NewsPublish np = this.newsPublishService.get(nc.getNewspublish());
			if(np != null){
				vo.setNewsTitle(np.getTitle());
			}
			
			FrontUser fu = this.frontUserService.get(nc.getCreator());
			if(fu != null){
				vo.setCreatorName(fu.getRealname());
				vo.setCreatorMobile(fu.getMobile());
			}
			
			voList.add(vo);
		}
		
		return ResultManager.createDataResult(voList, pageNum, pageSize, totalCount);
	}
	
	/**
	 * 获取信息
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		NewsComment nc = newsCommentService.get(uuid);
		if(nc == null){
			return ResultManager.createFailResult("评论不存在");
		}
		
		return ResultManager.createDataResult(nc);
	}
	
	/**
	 * 删除
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result delete(String uuid) {
		NewsComment nc = newsCommentService.get(uuid);
		if(nc == null){
			return ResultManager.createFailResult("评论不存在");
		}
		
		nc.setStatus(NewsCommentStatus.DELETE);
		newsCommentService.update(nc);
		return ResultManager.createSuccessResult("删除成功！");
	}
}
