package cn.product.treasuremall.service.front.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.GoodsCoverImageDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.GoodsTypeDao;
import cn.product.treasuremall.dao.LuckygameGoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.dao.MobileCodeDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.dao.WinningInfoDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUser.FrontUserType;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.FrontUserPaymentOrder.FrontUserPaymentOrderStatus;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.GoodsCoverImage;
import cn.product.treasuremall.entity.GoodsType;
import cn.product.treasuremall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.treasuremall.entity.LuckygameGoodsIssue.LuckygameGoodsIssueStatus;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.LuckygameGoods;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.service.front.FrontGoodsIssueService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.front.FrontUserPaymentOrderTopVO;
import cn.product.treasuremall.vo.front.FrontUserPaymentOrderVO;
import cn.product.treasuremall.vo.front.LuckygameGoodsIssueVO;
import cn.product.treasuremall.vo.front.WinningInfoVO;

@Service("frontGoodsIssueService")
public class FrontGoodsIssueServiceImpl implements FrontGoodsIssueService{
	
	@Autowired
	private FrontUserDao frontUserDao;

	@Autowired
	private MobileCodeDao mobileCodeDao;
	
	@Autowired
	private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
	private LuckygameGoodsDao luckygameGoodsDao;
	
	@Autowired
	private GoodsDao goodsDao;
	
	@Autowired
	private GoodsCoverImageDao goodsCoverImageDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
	private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
	private WinningInfoDao winningInfoDao;
	
	@Autowired
	private GoodsTypeDao goodsTypeDao;
	
	@Autowired
	private SystemParamDao systemParamDao;

	@Override
	public void get(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
    	String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
    	
    	LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(uuid);
    	if(lgi == null) {
    		result.setMessage("商品信息错误，请稍后重试！");
    		result.setStatus(ResultStatusType.FAILED);
    		return;
    	}
    	
    	LuckygameGoodsIssueVO lgivo = new LuckygameGoodsIssueVO(lgi);
    	
    	LuckygameGoods lg = this.luckygameGoodsDao.get(lgi.getLuckygameGoods());
    	if(lg != null) {//当前期数
    		lgivo.setCurrentIssueNum(lg.getCurrentIssueNum());
    		Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("luckygameGoods", lg.getUuid());
			searchMap.put("sort", "issue_num desc");
			List<LuckygameGoodsIssue> list = this.luckygameGoodsIssueDao.getListByParams(searchMap);
			if(list != null && list.size() > 0) {
				lgivo.setCurrentIssueUuid(list.get(0).getUuid());
			}
    	}
		
    	SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
    	
    	Goods good = this.goodsDao.get(lgi.getGoodsId());
    	lgivo.setCode(good.getCode());
    	lgivo.setCoverImg("");
		//奖品图片
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("belongs", lgi.getGoodsId());
//		searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
		List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
		if(gcis != null && gcis.size() > 0) {
			List<String> imgList = new ArrayList<String>();
			List<String> imgDetail = new ArrayList<String>();
			List<String> imgShow = new ArrayList<String>();
			for(GoodsCoverImage gci : gcis) {
				Resource re = this.resourceDao.get(gci.getImage());
				if(re != null) {
					if(GoodsCoverImageType.TYPE_LIST.equals(gci.getType())) {
						imgList.add(pathUrl + re.getUrl());
						lgivo.setCoverImg(pathUrl + re.getUrl());
					} else if(GoodsCoverImageType.TYPE_DETAIL.equals(gci.getType())) {
						imgDetail.add(pathUrl + re.getUrl());
					} else if(GoodsCoverImageType.TYPE_SHOW.equals(gci.getType())) {
						imgShow.add(pathUrl + re.getUrl());
					}
				}
			}
			lgivo.setImgDetail(imgDetail);
			lgivo.setImgList(imgList);
			lgivo.setImgShow(imgShow);
		}
		
		//封装中奖人信息
		searchMap.clear();
		searchMap.put("goodsIssue", lgi.getUuid());
		searchMap.put("isLuck", 1);
		
		List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
		if(listorder != null && listorder.size() > 0) {
			FrontUserPaymentOrder fupo = listorder.get(0);
			lgivo.setShowIdStr(fupo.getFrontUserShowId()+"");
			searchMap.clear();
			searchMap.put("goodsIssue", lgi.getUuid());
			searchMap.put("frontUser", fupo.getFrontUser());
			List<FrontUserPaymentOrder> listordersum = this.frontUserPaymentOrderDao.getGroupListByParams(searchMap);
			if(listordersum != null && listordersum.size() > 0) {
				lgivo.setBuyCount(listordersum.get(0).getBuyCount());
			}
			FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
			lgivo.setNickname(fu.getNickname());
			lgivo.setdAmount(fupo.getdAmount());
			lgivo.setActualDAmount(fupo.getActualDAmount());
			if(!Utlity.checkStringNull(fu.getImage())) {
				Resource re = this.resourceDao.get(fu.getImage());
				if(re != null) {
					lgivo.setImageUrl(pathUrl + re.getUrl());
				}
			}
		}
		result.setData(lgivo);
		result.setStatus(ResultStatusType.SUCCESS);
	}

	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String luckygameGoods = paramsMap.get("luckygameGoods") == null ? "" : paramsMap.get("luckygameGoods").toString();
		String goodsType = paramsMap.get("goodsType") == null ? "" : paramsMap.get("goodsType").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		String names = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String[] statuses = paramsMap.get("statuses") == null ? null : (String[])paramsMap.get("statuses");
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("luckygameGoods", luckygameGoods);
		searchMap.put("goodsType", goodsType);
		searchMap.put("status", status);
		searchMap.put("names", names);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		if(statuses != null) {
			searchMap.put("statuses", statuses);
		}
		Integer totalCount = luckygameGoodsIssueDao.getCountByParams(searchMap);
		List<LuckygameGoodsIssue> list = luckygameGoodsIssueDao.getListByParams(searchMap);
		
		List<LuckygameGoodsIssueVO> listvo = new ArrayList<LuckygameGoodsIssueVO>();
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			
			for(LuckygameGoodsIssue lgi : list) {
				LuckygameGoodsIssueVO lgivo = new LuckygameGoodsIssueVO(lgi);
				
				Goods good = this.goodsDao.get(lgi.getGoodsId());
				lgivo.setCode(good.getCode());
				lgivo.setCoverImg("");
				//奖品封面图
				searchMap.clear();
				searchMap.put("belongs", lgi.getGoodsId());
				searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
				List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
				if(gcis != null && gcis.size() > 0) {
					Resource re = this.resourceDao.get(gcis.get(0).getImage());
					if(re != null) {
						lgivo.setCoverImg(pathUrl + re.getUrl());
					}
				}
				
				if(LuckygameGoodsIssueStatus.LOTTERY.equals(lgi.getStatus())) {//倒计时
					lgivo.setTimeLine(lgi.getLotterytime().getTime()+30000L-System.currentTimeMillis());
				}
				//封装中奖人信息
				searchMap.clear();
				searchMap.put("goodsIssue", lgi.getUuid());
				searchMap.put("isLuck", 1);
				
				List<FrontUserPaymentOrder> listorder = this.frontUserPaymentOrderDao.getListByParams(searchMap);
				if(listorder != null && listorder.size() > 0) {
					FrontUserPaymentOrder fupo = listorder.get(0);
					lgivo.setShowIdStr(fupo.getFrontUserShowId()+"");
					searchMap.clear();
					searchMap.put("goodsIssue", lgi.getUuid());
					searchMap.put("frontUser", fupo.getFrontUser());
					List<FrontUserPaymentOrder> listordersum = this.frontUserPaymentOrderDao.getGroupListByParams(searchMap);
					if(listordersum != null && listordersum.size() > 0) {
						lgivo.setBuyCount(listordersum.get(0).getBuyCount());
					}
					FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
					lgivo.setNickname(fu.getNickname());
					lgivo.setdAmount(fupo.getdAmount());
					lgivo.setActualDAmount(fupo.getActualDAmount());
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource re = this.resourceDao.get(fu.getImage());
						if(re != null) {
							lgivo.setImageUrl(pathUrl + re.getUrl());
						}
					}
				}
				listvo.add(lgivo);
			}
		}
		
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}

	/**
	 * 本期参与记录
	 */
	@Override
	public void paymentList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String goodsIssue = paramsMap.get("goodsIssue") == null ? "" : paramsMap.get("goodsIssue").toString();
		Boolean isLuck = paramsMap.get("isLuck") == null ? null : Boolean.valueOf(paramsMap.get("isLuck").toString());
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("showId", showId);
		searchMap.put("goodsIssue", goodsIssue);
		if(isLuck != null) {
			searchMap.put("isLuck", isLuck);
		}
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<FrontUserPaymentOrder> list = this.frontUserPaymentOrderDao.getGroupListByParams(searchMap);
		Integer totalCount = this.frontUserPaymentOrderDao.getGroupCountByParams(searchMap);
		
		List<FrontUserPaymentOrderVO> listvo = new ArrayList<FrontUserPaymentOrderVO>();
		if(list != null && list.size() > 0) {
			for(FrontUserPaymentOrder fupo : list) {
				FrontUserPaymentOrderVO fupovo = new FrontUserPaymentOrderVO(fupo);
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
				if(fu != null) {
					fupovo.setNickname(fu.getNickname());
					//获取头像信息
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fupovo.setImageURL(res.getUrl());
						}
					}
				}
				listvo.add(fupovo);
			}
		}
		
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
	}

	/**
	 * 往期揭晓结果
	 */
	@Override
	public void winningInfoList(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String goodsIssue = paramsMap.get("goodsIssue") == null ? "" : paramsMap.get("goodsIssue").toString();
		String type = paramsMap.get("type") == null ? "" : paramsMap.get("type").toString();
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("showId", showId);
		searchMap.put("goodsIssue", goodsIssue);
		searchMap.put("type", type);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<WinningInfo> list = this.winningInfoDao.getListByParams(searchMap);
		Integer totalCount = this.winningInfoDao.getCountByParams(searchMap);
		
		List<WinningInfoVO> listvo = new ArrayList<WinningInfoVO>();
		if(list != null && list.size() > 0) {
			SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
			String pathUrl = "";
			if(sp != null) {
				pathUrl = sp.getParamValue();
			} else {
				pathUrl = Utlity.IMAGE_PATH_URL;
			}
			for(WinningInfo wi : list) {
				WinningInfoVO wivo = null;
				wivo = new WinningInfoVO(wi);
				
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(wi.getFrontUser());
				if(fu != null) {
					wivo.setNickname(fu.getNickname());
					wivo.setShowId(fu.getShowId());
					wivo.setIsRobot(fu.getType().equals(FrontUserType.ROBOT));
					
					Resource re = this.resourceDao.get(fu.getImage());
					if(re != null) {
						wivo.setImageUrl(pathUrl + re.getUrl());
					}
				}
				
				
				
				//封装商品信息
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(wi.getGoodsIssue());
				if(lgi != null) {
					wivo.setTitle(lgi.getTitle());
					wivo.setShortTitle(lgi.getShortTitle());
					wivo.setShares(lgi.getShares());
					wivo.setIssueNum(lgi.getIssueNum());
					wivo.setStarttime(lgi.getCreatetime());
					wivo.setFinishedtime(lgi.getFinishedtime());
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					wivo.setCode(good.getCode());
					wivo.setPrice(good.getPrice());
					wivo.setCover("");
					//商品封面图
					searchMap.clear();
					searchMap.put("belongs", lgi.getGoodsId());
					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
					if(gcis != null && gcis.size() > 0) {
						Resource re = this.resourceDao.get(gcis.get(0).getImage());
						if(re != null) {
							wivo.setCover(pathUrl + re.getUrl());
						}
					}
				}
				listvo.add(wivo);
			}
		}
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
		result.setMessage("查询成功！");
		return;	
	}

	/**
	 * 获取沙发、土豪、包尾数据
	 */
	@Override
	public void paymentTop(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		String uuid = paramsMap.get("uuid") == null ? "" : paramsMap.get("uuid").toString();
		
		LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(uuid);
		if(lgi == null) {
			result.setStatus(ResultStatusType.FAILED);
			result.setMessage("商品信息有误！");
			return;
		}
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("goodsIssue", uuid);
		searchMap.put("status", FrontUserPaymentOrderStatus.SUCCESS);
		//获取数据集
		List<FrontUserPaymentOrder> list = this.frontUserPaymentOrderDao.getListByParams(searchMap);
		
		Map<String, Object> shafa = new HashMap<String, Object>();
		shafa.put("nickname", "");
		shafa.put("imageUrl", "");
		Map<String, Object> tuhao = new HashMap<String, Object>();
		tuhao.put("nickname", "");
		tuhao.put("imageUrl", "");
		Map<String, Object> baowei = new HashMap<String, Object>();
		baowei.put("nickname", "");
		baowei.put("imageUrl", "");

		FrontUserPaymentOrderTopVO topvo = new FrontUserPaymentOrderTopVO();
		
		if(list != null && list.size() > 0) {
			FrontUserPaymentOrder first = list.get(list.size() - 1);//沙发
			FrontUser fu = this.frontUserDao.get(first.getFrontUser());
			if(fu != null) {
				shafa.put("nickname", fu.getNickname());
				if(!Utlity.checkStringNull(fu.getImage())) {
					Resource r = this.resourceDao.get(fu.getImage());
					if(r != null) {
						shafa.put("imageUrl", Utlity.IMAGE_PATH_URL + r.getUrl());
					} else {
						shafa.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
					}
				} else {
					shafa.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
				}
			}
			FrontUserPaymentOrder top1 = null;//土豪
			Map<String, FrontUserPaymentOrder> countMap = new HashMap<String, FrontUserPaymentOrder>();
			for(FrontUserPaymentOrder fupo : list) {
				if(countMap.containsKey(fupo.getFrontUser())) {
					Integer buycount = fupo.getBuyCount()+countMap.get(fupo.getFrontUser()).getBuyCount();
					fupo.setBuyCount(buycount);
				}
				countMap.put(fupo.getFrontUser(), fupo);
			}
			List<Map.Entry<String,FrontUserPaymentOrder>> listsort = new ArrayList<Map.Entry<String,FrontUserPaymentOrder>>(countMap.entrySet());
			Collections.sort(listsort, new Comparator<Map.Entry<String,FrontUserPaymentOrder>>() {
				@Override
				public int compare(Map.Entry<String,FrontUserPaymentOrder> u1, Map.Entry<String,FrontUserPaymentOrder> u2) {//buyCount升序
					return u1.getValue().getBuyCount() - u2.getValue().getBuyCount(); //相等为0
				}
			});
			top1 = listsort.get(listsort.size() - 1).getValue();
			FrontUser futop = this.frontUserDao.get(top1.getFrontUser());
			if(futop != null) {
				tuhao.put("nickname", futop.getNickname());
				if(!Utlity.checkStringNull(futop.getImage())) {
					Resource r = this.resourceDao.get(futop.getImage());
					if(r != null) {
						tuhao.put("imageUrl", Utlity.IMAGE_PATH_URL + r.getUrl());
					} else {
						tuhao.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
					}
				} else {
					tuhao.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
				}
			}
			
			if(!LuckygameGoodsIssueStatus.BETTING.equals(lgi.getStatus())) {
				FrontUserPaymentOrder last = list.get(0);//包尾
				FrontUser fue = this.frontUserDao.get(last.getFrontUser());
				if(fue != null) {
					baowei.put("nickname", fue.getNickname());
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource r = this.resourceDao.get(fue.getImage());
						if(r != null) {
							baowei.put("imageUrl", Utlity.IMAGE_PATH_URL + r.getUrl());
						} else {
							baowei.put("imageUrl",Utlity.IMAGE_PATH_URL + "/image/img-defaultAvatar.png");
						}
					} else {
						baowei.put("imageUrl","/image/img-defaultAvatar.png");
					}
				}
			}
		}
		
		topvo.setBaowei(baowei);
		topvo.setShafa(shafa);
		topvo.setTuhao(tuhao);
		
		result.setData(topvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
	}

	/**
	 * 商品类型列表
	 */
	@Override
	public void goodsType(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		
		String name = paramsMap.get("name") == null ? "" : paramsMap.get("name").toString();
		String level = paramsMap.get("level") == null ? "" : paramsMap.get("level").toString();
		String parent = paramsMap.get("parent") == null ? "" : paramsMap.get("parent").toString();
		String status = paramsMap.get("status") == null ? "" : paramsMap.get("status").toString();
		
		Map<String, Object> paramsls = new HashMap<String, Object>();
		paramsls.put("name", name);
		paramsls.put("level", level);
		paramsls.put("parent", parent);
		paramsls.put("status", status);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			paramsls.put("offSet", (pageNum-1)*pageSize);
			paramsls.put("pageSize", pageSize);
		}
		paramsls.put("sort", sort);
		
		Integer totalCount = goodsTypeDao.getCountByParams(paramsls);
		List<GoodsType> goodsTypeList = goodsTypeDao.getListByParams(paramsls);
		
		result.setData(goodsTypeList);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalCount);
	}
}
