package cn.zeppin.service;

import cn.zeppin.entity.ChineseLanguageLevel;

public interface IChineseLanguageLevelService extends
	IBaseService<ChineseLanguageLevel, Integer>
{
    public ChineseLanguageLevel getChineseLanguageLevelById(String id);

}
