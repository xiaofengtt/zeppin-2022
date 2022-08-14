package cn.product.treasuremall.service.back.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.GoodsCoverImageDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.Goods.GoodsStatus;
import cn.product.treasuremall.entity.GoodsCoverImage;
import cn.product.treasuremall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.service.back.GoodsService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.GoodsCoverImageVO;
import cn.product.treasuremall.vo.back.GoodsVO;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private GoodsCoverImageDao goodsCoverImageDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		Goods goods = goodsDao.get(uuid);
		if(goods == null || GoodsStatus.DELETE.equals(goods.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商品不存在");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("belongs", uuid);
		List<GoodsCoverImage> gciList = goodsCoverImageDao.getListByParams(searchMap);
		List<GoodsCoverImageVO> voList = new ArrayList<>();
		for(GoodsCoverImage gci : gciList){
			Resource resource = resourceDao.get(gci.getImage());
			if(resource != null){
				GoodsCoverImageVO vo = new GoodsCoverImageVO(gci);
				vo.setImageUrl(resource.getUrl());
				voList.add(vo);
			}
		}
		
		GoodsVO goodsVO = new GoodsVO(goods);
		goodsVO.setImageList(voList);
		
		if(goods.getVideo() != null){
			Resource resource = resourceDao.get(goods.getVideo());
			if(resource != null){
				goodsVO.setVideoUrl(resource.getUrl());
			}
		}
		
		result.setData(goodsVO);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("name", name);
		paramsls.put("type", type);
		paramsls.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			paramsls.put("offSet", (pageNum-1)*pageSize);
			paramsls.put("pageSize", pageSize);
		}
		paramsls.put("sort", sort);
		
		Integer totalCount = goodsDao.getCountByParams(paramsls);
		List<Goods> goodsList = goodsDao.getListByParams(paramsls);
		List<GoodsVO> volist = new ArrayList<GoodsVO>();
		if(goodsList != null) {
			for(Goods goods : goodsList) {
				Map<String, Object> searchMap = new HashMap<>();
				searchMap.put("belongs", goods.getUuid());
				List<GoodsCoverImage> gciList = goodsCoverImageDao.getListByParams(searchMap);
				List<GoodsCoverImageVO> voList = new ArrayList<>();
				for(GoodsCoverImage gci : gciList){
					Resource resource = resourceDao.get(gci.getImage());
					if(resource != null){
						GoodsCoverImageVO vo = new GoodsCoverImageVO(gci);
						vo.setImageUrl(resource.getUrl());
						voList.add(vo);
					}
				}
				
				GoodsVO goodsVO = new GoodsVO(goods);
				goodsVO.setImageList(voList);
				
				if(goods.getVideo() != null){
					Resource resource = resourceDao.get(goods.getVideo());
					if(resource != null){
						goodsVO.setVideoUrl(resource.getUrl());
					}
				}
				volist.add(goodsVO);
			}
		}
		
		result.setData(volist);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	@Override
	public void add(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Goods goods = (Goods) paramsMap.get("goods");
		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		String[] images = {};
		if(paramsMap.get("images") != null){
			images = (String[]) paramsMap.get("images");
		}
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("code", goods.getCode());
		Integer count = goodsDao.getCountByParams(searchMap);
		if(count > 0){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商品编码已存在！");
			return;
		}
		
		goods.setUuid(UUID.randomUUID().toString());
		
		List<GoodsCoverImage> gciList = new ArrayList<>();
		for(String image : images){
			String[] imageParams = image.split("@@");
			if(imageParams.length != 3){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商品图片有误，请重新上传！");
				return;
			}
			if(!GoodsCoverImageType.TYPE_LIST.equals(imageParams[0]) && !GoodsCoverImageType.TYPE_DETAIL.equals(imageParams[0])  
				&& !GoodsCoverImageType.TYPE_SHOW.equals(imageParams[0])){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商品图片有误，请重新上传！");
				return;
			}
			if(resourceDao.get(imageParams[1]) == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商品图片有误，请重新上传！");
				return;
			}
			if(!Utlity.isNumeric(imageParams[2])){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商品图片有误，请重新上传！");
				return;
			}
			
			GoodsCoverImage gci = new GoodsCoverImage();
			gci.setUuid(UUID.randomUUID().toString());
			gci.setBelongs(goods.getUuid());
			gci.setType(imageParams[0]);
			gci.setImage(imageParams[1]);
			gci.setSort(Integer.valueOf(imageParams[2]));
			gciList.add(gci);
		}
		
		goods.setCreator(admin);
		goods.setCreatetime(new Timestamp(System.currentTimeMillis()));
		goodsDao.insertGoods(goods, gciList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("添加成功！");
	}

	@Override
	public void edit(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Goods goods = (Goods) paramsMap.get("goods");
//		String admin = paramsMap.get("admin") == null ? "" : paramsMap.get("admin").toString();
		String[] images = {};
		if(paramsMap.get("images") != null){
			images = (String[]) paramsMap.get("images");
		}
		
		Goods g = goodsDao.get(goods.getUuid());
		if(g == null || GoodsStatus.DELETE.equals(g.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商品不存在");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("code", goods.getCode());
		List<Goods> lists = goodsDao.getListByParams(searchMap);
		if(lists.size() > 0 && !lists.get(0).getUuid().equals(goods.getUuid())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商品编码已存在！");
			return;
		}
		
		List<GoodsCoverImage> gciList = new ArrayList<>();
		for(String image : images){
			String[] imageParams = image.split("@@");
			if(imageParams.length != 3){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商品图片有误，请重新上传");
				return;
			}
			if(!GoodsCoverImageType.TYPE_LIST.equals(imageParams[0]) && !GoodsCoverImageType.TYPE_DETAIL.equals(imageParams[0])  
				&& !GoodsCoverImageType.TYPE_SHOW.equals(imageParams[0])){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商品图片有误，请重新上传！");
				return;
			}
			if(resourceDao.get(imageParams[1]) == null){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商品图片有误！请重新上传！");
				return;
			}
			if(!Utlity.isNumeric(imageParams[2])){
				result.setStatus(ResultStatusType.FAILED);
				result.setMessage("商品图片有误 请重新上传！");
				return;
			}
			
			GoodsCoverImage gci = new GoodsCoverImage();
			gci.setUuid(UUID.randomUUID().toString());
			gci.setBelongs(g.getUuid());
			gci.setType(imageParams[0]);
			gci.setImage(imageParams[1]);
			gci.setSort(Integer.valueOf(imageParams[2]));
			gciList.add(gci);
		}
		
		g.setName(goods.getName());
		g.setShortname(goods.getShortname());
		g.setCode(goods.getCode());
		g.setType(goods.getType());
		g.setPrice(goods.getPrice());
		g.setCosts(goods.getCosts());
		g.setSource(goods.getSource());
		g.setSourceUrl(goods.getSourceUrl());
		g.setDescription(goods.getDescription());
		g.setVideo(goods.getVideo());
		
		g.setStatus(goods.getStatus());
		goodsDao.updateGoods(g, gciList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}

	@Override
	public void changeStatus(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Goods g = goodsDao.get(uuid);
		if(g == null || GoodsStatus.DELETE.equals(g.getStatus())){
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商品不存在");
			return;
		}
		
		g.setStatus(status);
		
		goodsDao.update(g);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("修改成功！");
	}
	
}
