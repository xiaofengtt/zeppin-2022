package cn.product.treasuremall.service.back.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.FrontUserPaidNumberDao;
import cn.product.treasuremall.dao.FrontUserPaymentOrderDao;
import cn.product.treasuremall.dao.FrontUserVoucherDao;
import cn.product.treasuremall.dao.GoodsCoverImageDao;
import cn.product.treasuremall.dao.GoodsDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserPaidNumber;
import cn.product.treasuremall.entity.FrontUserPaymentOrder;
import cn.product.treasuremall.entity.FrontUserVoucher;
import cn.product.treasuremall.entity.Goods;
import cn.product.treasuremall.entity.GoodsCoverImage;
import cn.product.treasuremall.entity.GoodsCoverImage.GoodsCoverImageType;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.service.back.UserPaymentService;
import cn.product.treasuremall.util.JSONUtils;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.back.FrontUserPaymentOrderVO;
import cn.product.treasuremall.vo.back.SharenumsVO;

@Service("userPaymentService")
public class UserPaymentServiceImpl implements UserPaymentService{
	
	@Autowired
    private FrontUserPaymentOrderDao frontUserPaymentOrderDao;
	
	@Autowired
	private ResourceDao resourceDao;
	
	@Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
    private GoodsDao goodsDao;
	
	@Autowired
    private GoodsCoverImageDao goodsCoverImageDao;
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
	private FrontUserVoucherDao frontUserVoucherDao;
	
	@Autowired
	private FrontUserPaidNumberDao frontUserPaidNumberDao;
	
	/**
	 * 根据条件查询用户投注列表
	 */
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sort = paramsMap.get("sort") == null ? "" : paramsMap.get("sort").toString();
		String showId = paramsMap.get("showId") == null ? "" : paramsMap.get("showId").toString();
		String goodsId = paramsMap.get("goodsId") == null ? "" : paramsMap.get("goodsId").toString();
		String goodsIssue = paramsMap.get("goodsIssue") == null ? "" : paramsMap.get("goodsIssue").toString();
		Boolean isLuck = paramsMap.get("isLuck") == null ? null : Boolean.valueOf(paramsMap.get("isLuck").toString());
		
		String bettime = paramsMap.get("bettime") == null ? "" : paramsMap.get("bettime").toString();
		
		String buyCount = paramsMap.get("buyCount") == null ? "" : paramsMap.get("buyCount").toString();
		
		String gameType = paramsMap.get("gameType") == null ? "" : paramsMap.get("gameType").toString();
		
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("frontUserShowId", showId);
		searchMap.put("goodsId", goodsId);
		searchMap.put("goodsIssue", goodsIssue);
		searchMap.put("gameType", gameType);
		if(isLuck != null) {
			searchMap.put("isLuck", isLuck);
		}
		
		if(!Utlity.checkStringNull(bettime)) {
			String[] times = bettime.split("_");
			if(times != null && times.length == 2) {
				searchMap.put("timestart", times[0]);
				searchMap.put("timeend", times[1]);
			}
		}
		
		if(!Utlity.checkStringNull(buyCount)) {
			String[] totalAmountArr = buyCount.split("_");
			if(totalAmountArr != null && totalAmountArr.length == 2) {
				searchMap.put("totalAmountmin", totalAmountArr[0]);
				searchMap.put("totalAmountmax", totalAmountArr[1]);
			}
		}
		
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		searchMap.put("sort", sort);
		
		List<FrontUserPaymentOrder> list = this.frontUserPaymentOrderDao.getListByParams(searchMap);
		Integer totalCount = this.frontUserPaymentOrderDao.getCountByParams(searchMap);
		
		List<FrontUserPaymentOrderVO> listvo = new ArrayList<FrontUserPaymentOrderVO>();
		if(list != null && list.size() > 0) {
			for(FrontUserPaymentOrder fupo : list) {
				FrontUserPaymentOrderVO fupovo = new FrontUserPaymentOrderVO(fupo);
				//封装用户信息
				FrontUser fu = this.frontUserDao.get(fupo.getFrontUser());
				if(fu != null) {
					fupovo.setIp(fu.getIp());
					fupovo.setArea(fu.getArea());
					fupovo.setNickname(fu.getNickname());
					//获取头像信息
					if(!Utlity.checkStringNull(fu.getImage())) {
						Resource res = resourceDao.get(fu.getImage());
						if(res != null) {
							fupovo.setImageURL(res.getUrl());
						}
					}
				}
				//封装号码信息
				FrontUserPaidNumber fupn = this.frontUserPaidNumberDao.get(fupo.getUuid());
				if(fupn != null) {
					String nums = fupn.getPaidSharenums();
					SharenumsVO svo = JSONUtils.json2obj(nums, SharenumsVO.class);
					List<Integer> currNum = svo.getCurrentNums();
					StringBuffer sb = new StringBuffer();
					if(currNum != null) {
						for(Integer num : currNum) {
							sb.append(num);
							sb.append(",");
						}
						sb.delete(sb.length() - 1, sb.length());
					}
					fupovo.setListNum(sb.toString());
				}
				//封装红包信息
				if(fupo.getIsVoucherUsed()) {
					if(!Utlity.checkStringNull(fupo.getVoucher())) {
						FrontUserVoucher fuv = this.frontUserVoucherDao.get(fupo.getVoucher());
						if(fuv != null) {
							fupovo.setVoucherDAmount(fuv.getdAmount());
							fupovo.setVoucherTitle(fuv.getTitle());
						}
						
					} else {
						fupovo.setVoucherDAmount(fupo.getdAmount().subtract(fupo.getActualDAmount()));
					}
				} else {
					fupovo.setVoucherDAmount(BigDecimal.ZERO);
				}
				
				//封装商品信息
				LuckygameGoodsIssue lgi = this.luckygameGoodsIssueDao.get(fupo.getGoodsIssue());
				if(lgi != null) {
					fupovo.setTitle(lgi.getTitle());
					fupovo.setShortTitle(lgi.getShortTitle());
					fupovo.setShares(lgi.getShares());
					
					Goods good = this.goodsDao.get(lgi.getGoodsId());
					fupovo.setPrice(good.getPrice());
					fupovo.setCode(good.getCode());
					fupovo.setCover("");
					//商品封面图
					searchMap.clear();
					searchMap.put("belongs", lgi.getGoodsId());
					searchMap.put("type", GoodsCoverImageType.TYPE_LIST);
					List<GoodsCoverImage> gcis = this.goodsCoverImageDao.getListByParams(searchMap);
					if(gcis != null && gcis.size() > 0) {
						Resource re = this.resourceDao.get(gcis.get(0).getImage());
						if(re != null) {
							fupovo.setCover(re.getUrl());
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
		return;	
	}
}
