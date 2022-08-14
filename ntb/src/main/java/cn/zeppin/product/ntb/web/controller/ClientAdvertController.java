package cn.zeppin.product.ntb.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IAdvertService;
import cn.zeppin.product.ntb.backadmin.vo.AdvertVO;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Advert;
import cn.zeppin.product.ntb.core.entity.base.Entity;

@Controller
@RequestMapping(value = "/web/advert")
public class ClientAdvertController extends BaseController {
	@Autowired
	private IAdvertService advertService;

	/**
	 * 根据条件查询广告信息
	 * 
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public Result get() {
		// 查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("status", Advert.AdvertStatus.NORMAL);

		// 查询符合条件的广告信息的总数
		Integer totalResultCount = advertService.getCount(searchMap);
		// 查询符合条件的广告列表
		List<Entity> list = advertService.getListForPage(searchMap, -1, -1, null, AdvertVO.class);
		if (list != null && list.size() > 0) {
			return ResultManager.createDataResult(list.get(0), totalResultCount);
		}
		return ResultManager.createDataResult(null, totalResultCount);
	}
}
