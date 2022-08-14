package cn.zeppin.product.score.service;

import java.util.List;
import java.util.Map;

import cn.zeppin.product.score.entity.GuessingMatch;
import cn.zeppin.product.score.entity.GuessingMatchOdds;
import cn.zeppin.product.score.entity.GuessingMatchType;
import cn.zeppin.product.score.vo.front.CategoryCountVO;

public interface GuessingMatchTypeService extends IService<GuessingMatchType>{
	
	/**
	 * 根据参数获取总数
	 * @param params
	 * @return
	 */
	public Integer getCountByParams(Map<String, Object> params);
	
	/**
	 * 根据参数获取列表
	 * @param params
	 * @return
	 */
	public List<GuessingMatchType> getListByParams(Map<String, Object> params);
	
	/**
	 * 发布竞猜类型
	 * @param guessingMatchType
	 * @param guessingMatch
	 */
	public void publishGuessingMatchType(GuessingMatchType guessingMatchType, GuessingMatch guessingMatch);
	
	/**
	 * 添加竞猜类型
	 * @param guessingMatchType
	 * @param oddsList
	 */
	public void addGuessingMatchType(GuessingMatchType guessingMatchType, List<GuessingMatchOdds> oddsList);
	
	/**
	 * 删除竞猜类型
	 * @param guessingMatchType
	 */
	public void deleteGuessingMatchType(GuessingMatchType guessingMatchType);
	
	/**
	 * 更新竞猜类型
	 * @param guessingMatchType
	 * @param oddsList
	 */
	public void updateGuessingMatchType(GuessingMatchType guessingMatchType, List<GuessingMatchOdds> oddsList);
	
	/**
	 * 获取按赛事分类的发布竞猜列表
	 */
	public List<CategoryCountVO> getCategoryList(Map<String, Object> params);
	
	/**
	 * 获取结束投注的类型
	 */
	public List<GuessingMatchType> getWaitingMatchTypeList();
}
