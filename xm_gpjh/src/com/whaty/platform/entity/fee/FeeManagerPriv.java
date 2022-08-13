/**
 * 
 */
package com.whaty.platform.entity.fee;

/**
 * @author wangqiang
 * 
 */
public abstract class FeeManagerPriv {
	/***************************************************************************
	 * 费用管理权限 *
	 **************************************************************************/

	public int addFeeStandard = 1; // 是否可以添加学费标准

	public int getFeeStandard = 1; // 是否可以查看学费标准

	public int addFee = 1; // 是否可以添加学费标准

	public int getFee = 1; // 是否可以查看学费标准

	public int getFeeByTime = 1; // 是否可以按照时间断查询学费

	public int getStuOtherFee = 1; // 是否可以查看学生杂费

	public int getStuOtherFeeByTime = 1; // 是否可以按照时间段查询学生杂费

	public int getStuFeeReturnApply = 1; // 是否可以查看学生退费申请

	public int getConfirmOrder = 1; // 是否可以确认订单

	public int getReConfirmOrder = 1; // 是否可以重新确认订单

	public int getSiteFeeStat = 1; // 是否可以查看教学站学费统计

	/***************************************************************************
	 * 杂费管理权限 *
	 **************************************************************************/

	public int addOtherFeeType = 1; // 是否可以添加杂费类型

	public int deleteOtherFeeType = 1; // 是否可以删除杂费类型

	public int getOtherFeeType = 1; // 是否可以查看杂费类型

	public int updateOtherFeeType = 1; // 是否可以修改杂费类型

	public int addOtherFeeStandard = 1; // 是否可以添加杂费标准

	public int deleteOtherFeeStandard = 1; // 是否可以删除杂费标准

	public int getOtherFeeStandard = 1; // 是否可以查看杂费标准

	public int updateOtherFeeStandard = 1; // 是否可以修改杂费标准

}
