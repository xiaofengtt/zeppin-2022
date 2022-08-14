package cn.product.treasuremall.service.front.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.product.treasuremall.api.base.BaseResult.ResultStatusType;
import cn.product.treasuremall.api.base.DataResult;
import cn.product.treasuremall.api.base.InputParams;
import cn.product.treasuremall.dao.FrontUserCommentDao;
import cn.product.treasuremall.dao.FrontUserDao;
import cn.product.treasuremall.dao.LuckygameGoodsIssueDao;
import cn.product.treasuremall.dao.ResourceDao;
import cn.product.treasuremall.dao.SystemParamDao;
import cn.product.treasuremall.dao.WinningInfoDao;
import cn.product.treasuremall.entity.FrontUser;
import cn.product.treasuremall.entity.FrontUserComment;
import cn.product.treasuremall.entity.FrontUserComment.FrontUserCommentStatus;
import cn.product.treasuremall.entity.SystemParam.SystemParamKey;
import cn.product.treasuremall.entity.LuckygameGoodsIssue;
import cn.product.treasuremall.entity.Resource;
import cn.product.treasuremall.entity.SystemParam;
import cn.product.treasuremall.entity.WinningInfo;
import cn.product.treasuremall.service.front.FrontUserCommentService;
import cn.product.treasuremall.util.Utlity;
import cn.product.treasuremall.vo.front.FrontUserCommentVO;

@Service("frontUserCommentService")
public class FrontUserCommentServiceImpl implements FrontUserCommentService{
	
	@Autowired
	private FrontUserDao frontUserDao;
	
	@Autowired
    private WinningInfoDao winningInfoDao;
	
	@Autowired
    private FrontUserCommentDao frontUserCommentDao;
	
	@Autowired
    private ResourceDao resourceDao;
	
	@Autowired
    private LuckygameGoodsIssueDao luckygameGoodsIssueDao;
	
	@Autowired
	private SystemParamDao systemParamDao;
	
	@Override
	public void list(InputParams params, DataResult<Object> result) {
		Map<String, Object> paramsMap = params.getParams();
		Integer pageNum = Integer.valueOf(paramsMap.get("pageNum") == null ? "0" : paramsMap.get("pageNum").toString());
		Integer pageSize = Integer.valueOf(paramsMap.get("pageSize") == null ? "0" : paramsMap.get("pageSize").toString());
		String sorts = paramsMap.get("sorts") == null ? "" : paramsMap.get("sorts").toString();
		
		//查询条件
		Map<String, Object> searchMap = new HashMap<String, Object>();
		searchMap.put("status", FrontUserCommentStatus.CHECKED);
		searchMap.put("sort", sorts);
		if(pageNum != null && pageNum > 0 && pageSize != null && pageSize > 0){
			searchMap.put("offSet", (pageNum-1)*pageSize);
			searchMap.put("pageSize", pageSize);
		}
		
		//查询符合条件的用户晒单信息的总数
		Integer totalResultCount = frontUserCommentDao.getCountByParams(searchMap);
		//查询符合条件的用户晒单信息列表
		List<FrontUserComment> list = frontUserCommentDao.getListByParams(searchMap);
		List<FrontUserCommentVO> listvo = new ArrayList<FrontUserCommentVO>();
		SystemParam sp = this.systemParamDao.get(SystemParamKey.IMAGE_PATH_URL);//链接地址
		String pathUrl = "";
		if(sp != null) {
			pathUrl = sp.getParamValue();
		} else {
			pathUrl = Utlity.IMAGE_PATH_URL;
		}
		for(FrontUserComment frontUserComment : list) {
			//界面返回封装对象
			FrontUserCommentVO frontUserCommentVO = new FrontUserCommentVO(frontUserComment);
			
			//资源信息
			//图片
			List<Map<String, Object>> listImage = new ArrayList<Map<String,Object>>();
			if(!Utlity.checkStringNull(frontUserComment.getImage())) {
				String[] images = frontUserComment.getImage().split(",");
				for(String image : images) {
					if(!Utlity.checkStringNull(image)) {
						Resource re = this.resourceDao.get(image);
						if(re != null) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("image", re.getUuid());
							map.put("url", pathUrl + re.getUrl());
							listImage.add(map);
						}
					}
				}
			}
			frontUserCommentVO.setImageList(listImage);
			//视频
			List<Map<String, Object>> listVideo = new ArrayList<Map<String,Object>>();
			if(!Utlity.checkStringNull(frontUserComment.getVideo())) {
				String[] videos = frontUserComment.getVideo().split(",");
				for(String video : videos) {
					if(!Utlity.checkStringNull(video)) {
						Resource re = this.resourceDao.get(video);
						if(re != null) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("video", re.getUuid());
							map.put("url", pathUrl + re.getUrl());
							listVideo.add(map);
						}
					}
				}
			}
			frontUserCommentVO.setVideoList(listVideo);
			//商品信息
			LuckygameGoodsIssue lgi = luckygameGoodsIssueDao.get(frontUserComment.getGoodsIssue());
			if(lgi != null) {
				frontUserCommentVO.setTitle(lgi.getTitle());
				frontUserCommentVO.setIssueNum(lgi.getIssueNum());
			}
			//用户信息
			FrontUser fu = this.frontUserDao.get(frontUserComment.getFrontUser());
			if(fu != null) {
				frontUserCommentVO.setNickName(fu.getNickname());
				if(!Utlity.checkStringNull(fu.getImage())) {
					Resource re = this.resourceDao.get(fu.getImage());
					if(re != null) {
						frontUserCommentVO.setImageUrl(pathUrl + re.getUrl());
					}
				}
			}
			//中奖信息
			WinningInfo wi = this.winningInfoDao.get(frontUserComment.getWinningInfo());
			if(wi != null) {
				frontUserCommentVO.setBuyCount(wi.getBuyCount());
				frontUserCommentVO.setLuckynum(wi.getLuckynum());
				frontUserCommentVO.setWinningTime(wi.getWinningTime());
			}
			
			listvo.add(frontUserCommentVO);
		}
		result.setData(listvo);
		result.setStatus(ResultStatusType.SUCCESS);
		result.setMessage("success");
		result.setPageNum(pageNum);
		result.setPageSize(pageSize);
		result.setTotalResultCount(totalResultCount);
	}
}
