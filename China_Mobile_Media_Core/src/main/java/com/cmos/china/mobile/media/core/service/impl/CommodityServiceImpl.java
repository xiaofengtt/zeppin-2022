package com.cmos.china.mobile.media.core.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cmos.china.mobile.media.core.bean.Commodity;
import com.cmos.china.mobile.media.core.bean.CommodityDisplay;
import com.cmos.china.mobile.media.core.bean.Entity;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.service.ICommodityService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.china.mobile.media.core.vo.CommodityDisplayVO;
import com.cmos.china.mobile.media.core.vo.CommodityVO;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;

public class CommodityServiceImpl extends BaseServiceImpl implements ICommodityService {

	@Override
	public void list(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String name = inputObject.getValue("name");
		String price = inputObject.getValue("price");
		String originalPrice = inputObject.getValue("originalPrice");
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), 1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), 10);
		String sort = inputObject.getValue("sort");
		if(!Utlity.checkOrderBy(sort)){
			throw new Exception("参数异常");
		}
		
		Integer start = (pagenum - 1) * pagesize;
		
		if(sort == null || "".equals(sort)){
			sort = "createtime";
		}else{
			sort = sort.replaceAll("-", " ");
		}
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", id);
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
		if(list != null && list.size() > 0){
			for(CommodityVO cv: list){
				if(cv.getCover() != null && !"".equals(cv.getCover())){
					Resource re = this.getBaseDao().queryForObject("resource_get", cv.getCover(), Resource.class);
					if(re != null){
						cv.setCoverURL(re.getUrl());
					}else{
						cv.setCoverURL("/assets/img/default_productBig.jpg");
					}
				}else{
					cv.setCoverURL("/assets/img/default_productBig.jpg");
				}
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("pageNum", pagenum);
		resultMap.put("pageSize", pagesize);
		resultMap.put("totalPageCount", pageCount);
		resultMap.put("totalResultCount", count);
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

	@Override
	public void load(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		
		Commodity commodity = this.getBaseDao().queryForObject("commodity_get", id, Commodity.class);
		if(commodity==null){
			throw new Exception("不存在");
		}else{
			outputObject.convertBean2Map(commodity);
		}
	}

	@Override
	public void add(InputObject inputObject, OutputObject outputObject) throws Exception {
		String url = inputObject.getValue("url");
		String name = inputObject.getValue("name");
		String cover = inputObject.getValue("cover");
		String price = inputObject.getValue("price");
		String originalPrice = inputObject.getValue("originalPrice");
		String creator = inputObject.getValue("creator");
		String displays = inputObject.getValue("displays");
		
		if(name==null||name.equals("")){
			throw new Exception("名称不能为空");
		}
		if(price==null||price.equals("")){
			throw new Exception("销售价格不能为空");
		}
		if(url==null||url.equals("")){
			throw new Exception("商品链接为空");
		}
		
		Commodity commodity = new Commodity();
		String id = UUID.randomUUID().toString();
		commodity.setId(id);
		commodity.setName(name);
		commodity.setPrice(BigDecimal.valueOf(Double.valueOf(price)));
		commodity.setUrl(url);
		
		if(url!=null && !url.equals("")){
			commodity.setOriginalPrice(BigDecimal.valueOf(Double.valueOf(originalPrice)));
		}
		if(cover!=null && !cover.equals("")){
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

	@Override
	public void edit(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		String url = inputObject.getValue("url");
		String name = inputObject.getValue("name");
		String cover = inputObject.getValue("cover");
		String price = inputObject.getValue("price");
		String originalPrice = inputObject.getValue("originalPrice");
		String displays = inputObject.getValue("displays");
		String creator = inputObject.getValue("creator");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		if(name==null||name.equals("")){
			throw new Exception("名称不能为空");
		}
		if(price==null||price.equals("")){
			throw new Exception("销售价格不能为空");
		}
		if(url==null||url.equals("")){
			throw new Exception("商品链接为空");
		}
		
		Commodity commodity = this.getBaseDao().queryForObject("commodity_get", id, Commodity.class);
		if(commodity!=null){
			commodity.setName(name);
			commodity.setPrice(BigDecimal.valueOf(Double.valueOf(price)));
			commodity.setUrl(url);
			
			if(url!=null && !url.equals("")){
				commodity.setOriginalPrice(BigDecimal.valueOf(Double.valueOf(originalPrice)));
			}
			if(cover!=null && !cover.equals("")){
				commodity.setCover(cover);
			}
			this.getBaseDao().update("commodity_update", commodity);
			
			if(displays != null && !"".equals(displays)){
				Map<String, String> params = new HashMap<String, String>();
				params.put("commodity", commodity.getId());
				List<CommodityDisplayVO> list = this.getBaseDao().queryForList("commodity_getListByParams", params, CommodityDisplayVO.class);
				if(list != null && list.size() > 0){
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
			throw new Exception("商品不存在");
		}
	}

	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws Exception {
		String id = inputObject.getValue("id");
		
		if(id==null||id.equals("")){
			throw new Exception("ID不能为空");
		}
		
		Commodity commodity = this.getBaseDao().queryForObject("commodity_get", id, Commodity.class);
		if(commodity==null){
			throw new Exception("商品不存在");
		}else{
			this.getBaseDao().update("commodity_delete", commodity);
		}
	}

	@Override
	public void search(InputObject inputObject, OutputObject outputObject) throws Exception {
		String name = inputObject.getValue("name");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("name", name);
		paramMap.put("statis", Entity.GerneralStatusType.NORMAL);
		
		List<CommodityVO> list = this.getBaseDao().queryForList("commodity_getListByParams", paramMap, CommodityVO.class);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("totalResultCount", list.size());
		
		outputObject.convertBeans2List(list);
		outputObject.setObject(resultMap);
	}

}
