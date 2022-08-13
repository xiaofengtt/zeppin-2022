package com.whaty.platform.entity.fee.level;

import java.util.List;

import com.whaty.platform.util.Page;

public abstract class ChargeLevelList {
	
	public abstract List searchChargeLevelByType(Page page,List searchList,List orderList,String type);

	public abstract int searchChargeLevelByTypeNum(List searchList,String type);
	
	public abstract List searchChargeLevels(Page page,List searchList,List orderList);

	public abstract int searchChargeLevelsNum(List searchList);
	
}
