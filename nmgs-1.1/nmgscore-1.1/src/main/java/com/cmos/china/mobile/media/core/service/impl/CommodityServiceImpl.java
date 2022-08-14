package com.cmos.china.mobile.media.core.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Commodity;
import com.cmos.china.mobile.media.core.bean.CommodityDisplay;
import com.cmos.china.mobile.media.core.bean.Entity;
import com.cmos.china.mobile.media.core.bean.Province;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.service.ICommodityService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.CommodityDisplayVO;
import com.cmos.china.mobile.media.core.vo.CommodityVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public class CommodityServiceImpl extends BaseServiceImpl implements ICommodityService {
 
	/**
	 * 商品列表
	 */
	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		String province = inputObject.getValue("province");
		String name = inputObject.getValue("name");
		String price = inputObject.getValue("price");
		String originalPrice = inputObject.getValue("originalPrice");
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), 1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), 10);
		String sort = inputObject.getValue("sort");
		
		if(province==null||"".equals(province)){
			throw new ExceptionUtil("地区不能为空");
		}
		
		if(!Utlity.checkOrderBy(sort)){
			throw new ExceptionUtil("参数异常");
		}
		
		Integer start = (pagenum - 1) * pagesize;
		
		if(sort == null || "".equals(sort)){
			sort = "createtime";
		}else{
			sort = sort.replaceAll("-", " ");
		}
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("id", id);
		paramMap.put("province", province);
		paramMap.put("name", name);
		paramMap.put("price", price);
		paramMap.put("originalPrice", originalPrice);
		paramMap.put("status", Entity.GerneralStatusType.NORMAL);
		paramMap.put("start", start+"");
		paramMap.put("limit", pagesize+"");
		paramMap.put("sort", sort);
		
		Integer count = this.getBaseDao().getTotalCount("commodity_getCountByParams", paramMap);
		Integer pageCount = (int) Math.ceil((double) count / pagesize);
		List<CommodityVO> list = this.getBaseDao().queryForList("commodity_getListByParams", paramMap, CommodityVO.class);
		if(list != null && !list.isEmpty()){
			for(CommodityVO cv: list){
				if(cv.getCover() != null && !"".equals(cv.getCover())){
					Resource re = this.getBaseDao().queryForObject("resource_get", cv.getCover(), Resource.class);
					if(re != null && !"".equals(re)){
						cv.setCoverURL(re.getUrl());
					}else{
						cv.setCoverURL("/assets/img/default_productBig.jpg");
					}
				}else{
					cv.setCoverURL("/assets/img/default_productBig.jpg");
				}
			}
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("pageNum", pagenum);
		resultMap.put("pageSize", pagesize);
		resultMap.put("totalPageCount", pageCount);
		resultMap.put("totalResultCount", count);
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

	/**
	 * 加载详细商品信息
	 */
	@Override
	public void load(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Commodity commodity = this.getBaseDao().queryForObject("commodity_get", id, Commodity.class);
		if(commodity==null || "".equals(commodity)){
			throw new ExceptionUtil("不存在");
		}else{
			Map<String,Object> comMap = new HashMap<>();
			comMap.put("id", commodity.getId());
			comMap.put("name", commodity.getName());
			comMap.put("price", commodity.getPrice());
			comMap.put("originalPrice", commodity.getOriginalPrice());
			comMap.put("url", commodity.getUrl());
			
			if(commodity.getCover() != null && !"".equals(commodity.getCover())){
				Resource cover = this.getBaseDao().queryForObject("resource_get", commodity.getCover(), Resource.class);
				if(cover != null){
					comMap.put("cover", cover.getId());
					comMap.put("coverURL", cover.getUrl());
				}else{
					comMap.put("cover", "");
					comMap.put("coverURL", "/assets/img/default_productBig.jpg");
				}
			}else{
				comMap.put("cover", "");
				comMap.put("coverURL", "/assets/img/default_productBig.jpg");
			}
			
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("commodity", commodity.getId());
			List<CommodityDisplayVO> cdList = this.getBaseDao().queryForList("commodityDisplay_getListByParams", paramMap, CommodityDisplayVO.class);
			if(cdList != null && !cdList.isEmpty()){
				List<String> dispalyList = new ArrayList<>();
				for(CommodityDisplayVO cdvo : cdList){
					dispalyList.add(cdvo.getId());
				}
				comMap.put("displays",dispalyList);
			}
			outputObject.setBean(comMap);
		}
	}

	/**
	 * 添加商品
	 */
	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String url = inputObject.getValue("url");
		String name = inputObject.getValue("name");
		String province = inputObject.getValue("province");
		String cover = inputObject.getValue("cover");
		String price = inputObject.getValue("price");
		String originalPrice = inputObject.getValue("originalPrice");
		String creator = inputObject.getValue("creator");
		String displays = inputObject.getValue("displays");
		
		if(name==null||"".equals(name)){
			throw new ExceptionUtil("名称不能为空");
		}
		if(province==null||"".equals(province)){
			throw new ExceptionUtil("地区不能为空");
		}
		if(price==null||"".equals(price)){
			throw new ExceptionUtil("销售价格不能为空");
		}
		if(url==null||"".equals(url)){
			throw new ExceptionUtil("商品链接为空");
		}
		
		Commodity commodity = new Commodity();
		String id = UUID.randomUUID().toString();
		commodity.setId(id);
		commodity.setName(name);
		commodity.setPrice(BigDecimal.valueOf(Double.valueOf(price)));
		commodity.setUrl(url);
		
		Province prov = this.getBaseDao().queryForObject("province_get", province, Province.class);
		if(prov!=null && !"".equals(prov)){
			commodity.setProvince(province);
		}else{
			throw new ExceptionUtil("地区不存在");
		}
		

			commodity.setOriginalPrice(BigDecimal.valueOf(Double.valueOf(originalPrice)));

		if(cover!=null && !"".equals(cover)){
			commodity.setCover(cover);
		}
		
		commodity.setStatus(Entity.GerneralStatusType.NORMAL);
		commodity.setCreatetime(new Timestamp((new Date()).getTime()));
		commodity.setCreator(creator);
		
		if(displays != null && !"".equals(displays)){
			String[] display = displays.split(",");
			String[] uuid = UUID.randomUUID().toString().split("-");
			commodity.setStatus(uuid[0]);
			this.getBaseDao().insert("commodity_add", commodity);
			for(String dis : display){
				CommodityDisplay cd = new CommodityDisplay();
				String cid = UUID.randomUUID().toString();
				cd.setId(cid);
				cd.setCommodity(commodity.getId());
				cd.setCreator(creator);
				cd.setCreatetime(new Timestamp((new Date()).getTime()));
				String[] strs = dis.split("_");
				cd.setDisplayImage(strs[0]);
				cd.setDisplayIndex(Integer.parseInt(strs[1]));
				this.getBaseDao().insert("commodityDisplay_add", cd);
			}
			commodity = this.getBaseDao().queryForObject("commodity_get", commodity.getId(), Commodity.class);
			commodity.setStatus(Entity.GerneralStatusType.NORMAL);
			this.getBaseDao().update("commodity_update", commodity);
		}else{
			this.getBaseDao().insert("commodity_add", commodity);
		}
	}

	/**
	 * 编辑商品
	 */
	@Override
	public void edit(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		String url = inputObject.getValue("url");
		String name = inputObject.getValue("name");
		String cover = inputObject.getValue("cover");
		String price = inputObject.getValue("price");
		String originalPrice = inputObject.getValue("originalPrice");
		String displays = inputObject.getValue("displays");
		String creator = inputObject.getValue("creator");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		if(name==null||"".equals(name)){
			throw new ExceptionUtil("名称不能为空");
		}
		if(price==null||"".equals(price)){
			throw new ExceptionUtil("销售价格不能为空");
		}
		if(url==null||"".equals(url)){
			throw new ExceptionUtil("商品链接为空");
		}
		
		Commodity commodity = this.getBaseDao().queryForObject("commodity_get", id, Commodity.class);
		if(commodity!=null && !"".equals(commodity)){
			commodity.setName(name);
			commodity.setPrice(BigDecimal.valueOf(Double.valueOf(price)));
			commodity.setUrl(url);
			commodity.setOriginalPrice(BigDecimal.valueOf(Double.valueOf(originalPrice)));
				
			if(cover!=null && !"".equals(cover)){
				commodity.setCover(cover);
			}
			this.getBaseDao().update("commodity_update", commodity);
			
			if(displays != null && !"".equals(displays)){
				Map<String, String> params = new HashMap<>();
				params.put("commodity", commodity.getId());
				List<CommodityDisplayVO> list = this.getBaseDao().queryForList("commodityDisplay_getListByParams", params, CommodityDisplayVO.class);
				if(list != null && !list.isEmpty()){
					for(CommodityDisplayVO cdvo : list){
						this.getBaseDao().delete("commodityDisplay_delete", cdvo.getId());
					}
				}
				
				String[] display = displays.split(",");
				for(String dis : display){
					CommodityDisplay cd = new CommodityDisplay();
					String cid = UUID.randomUUID().toString();
					cd.setId(cid);
					cd.setCommodity(commodity.getId());
					cd.setCreator(creator);
					cd.setCreatetime(new Timestamp((new Date()).getTime()));
					String[] strs = dis.split("_");
					cd.setDisplayImage(strs[0]);
					cd.setDisplayIndex(Integer.parseInt(strs[1]));
					this.getBaseDao().insert("commodityDisplay_add", cd);
				}
			}
		}else{
			throw new ExceptionUtil("商品不存在");
		}
	}

	/**
	 * 删除商品
	 */
	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String id = inputObject.getValue("id");
		
		if(id==null||"".equals(id)){
			throw new ExceptionUtil("ID不能为空");
		}
		
		Commodity commodity = this.getBaseDao().queryForObject("commodity_get", id, Commodity.class);
		if(commodity==null){
			throw new ExceptionUtil("商品不存在");
		}else{
			Map<String, String> params = new HashMap<>();
			params.put("commodity", commodity.getId());
			params.put("statusNot", "destroy");
			Integer count = this.getBaseDao().getTotalCount("videoCommodityPoint_getCountByParams", params);
			if(count>0){
				throw new ExceptionUtil("商品已被关联，不可删除");
			}else{
				this.getBaseDao().update("commodity_delete", commodity);
			}
		}
	}

	/**
	 * 检索商品
	 */
	@Override
	public void search(InputObject inputObject, OutputObject outputObject) throws ExceptionUtil {
		String province = inputObject.getValue("province");
		String name = inputObject.getValue("name");
		
		if(province==null||"".equals(province)){
			throw new ExceptionUtil("地区不能为空");
		}
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("name", name);
		paramMap.put("province", province);
		paramMap.put("status", Entity.GerneralStatusType.NORMAL);
		
		List<CommodityVO> list = this.getBaseDao().queryForList("commodity_getListByParams", paramMap, CommodityVO.class);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("totalResultCount", list.size());
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

}
