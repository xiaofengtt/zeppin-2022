package cn.zeppin.product.ntb.backadmin.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.ntb.backadmin.service.api.IAdvertService;
import cn.zeppin.product.ntb.backadmin.service.api.IResourceService;
import cn.zeppin.product.ntb.backadmin.vo.AdvertVO;
import cn.zeppin.product.ntb.core.controller.base.ActionParam;
import cn.zeppin.product.ntb.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.ntb.core.controller.base.BaseController;
import cn.zeppin.product.ntb.core.controller.base.Result;
import cn.zeppin.product.ntb.core.controller.base.ResultManager;
import cn.zeppin.product.ntb.core.entity.Advert;
import cn.zeppin.product.ntb.core.entity.BkOperator;
import cn.zeppin.product.ntb.core.entity.Resource;
import cn.zeppin.product.ntb.core.entity.base.Entity;

/**
 * App广告管理
 * 
 * @author geng
 * @date 2018年1月24日 上午10:59:01
 */
@Controller
@RequestMapping(value = "/backadmin/advert")
public class AdvertController extends BaseController {
	@Autowired
	private IAdvertService advertService;
	@Autowired
	private IResourceService resourceService;

	/**
	 * 根据条件查询广告列表信息
	 * 
	 * @param name
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @param sorts
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "title", message = "名称", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	// @ActionParam(key = "pageNum", message = "页码", type = DataType.NUMBER,
	// required = true)
	// @ActionParam(key = "pageSize", message = "每页数量", type = DataType.NUMBER,
	// required = true)
	// @ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String title, String status) {
		// 查询条件
		Map<String, String> searchMap = new HashMap<String, String>();
		searchMap.put("title", title);
		searchMap.put("status", status);

		// 查询符合条件的广告信息的总数
		Integer totalResultCount = advertService.getCount(searchMap);
		// 查询符合条件的广告列表
		List<Entity> list = advertService.getListForPage(searchMap, -1, -1, null, AdvertVO.class);

		return ResultManager.createDataResult(list, totalResultCount);
	}

	/**
	 * 获取一条广告信息
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ResponseBody
	public Result get(String uuid) {
		// 获取银行信息
		Advert advert = advertService.get(uuid);
		if (advert == null) {
			return ResultManager.createFailResult("该条数据不存在！");
		}

		// 界面返回封装对象
		AdvertVO advertVO = new AdvertVO(advert);

		// 资源信息
		Resource resource = resourceService.get(advert.getPicture());
		if (resource != null) {
			advertVO.setPictureUrl(resource.getUrl());
		}

		return ResultManager.createDataResult(advertVO);
	}

	/**
	 * 添加
	 * 
	 * @param title
	 * @param picture
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "title", message = "名称", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "picture", message = "广告", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result add(String title, String picture) {
		// 取管理员信息
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		BkOperator currentOperator = (BkOperator) session.getAttribute("currentOperator");
		// 广告信息
		Advert advert = new Advert();
		advert.setUuid(UUID.randomUUID().toString());
		advert.setTitle(title);
		advert.setPicture(picture);
		advert.setStatus(Advert.AdvertStatus.NORMAL);
		advert.setCreator(currentOperator.getUuid());
		advert.setCreatetime(new Timestamp(System.currentTimeMillis()));

		advert = advertService.insert(advert);

		return ResultManager.createSuccessResult("保存成功！");
	}

	/**
	 * 修改
	 * 
	 * @param id
	 * @param title
	 * @param picture
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "uuid", message = "uuid", type = DataType.STRING, required = true)
	@ActionParam(key = "title", message = "名称", type = DataType.STRING, required = true, minLength = 1, maxLength = 200)
	@ActionParam(key = "picture", message = "广告", type = DataType.STRING, required = true, minLength = 36, maxLength = 36)
	@ResponseBody
	public Result edit(String uuid, String title, String picture) {
		Advert advert = advertService.get(uuid);
		if (advert != null) {
			advert.setTitle(title);
			advert.setPicture(picture);
			advertService.update(advert);
			return ResultManager.createSuccessResult("保存成功！");
		} else {
			return ResultManager.createFailResult("该条数据不存在！");
		}
	}
}
