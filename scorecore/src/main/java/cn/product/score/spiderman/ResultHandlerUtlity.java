package cn.product.score.spiderman;

import cn.product.score.entity.Category.CategoryMainUuid;

public class ResultHandlerUtlity {
	public static String getCategory(String type) {
		switch (type) {
		case "ucl":
			return CategoryMainUuid.UCL;
		case "premierleague":
			return CategoryMainUuid.PREMIERLEAGUE;
		case "bundesliga":
			return CategoryMainUuid.BUNDESLIGA;
		case "laliga":
			return CategoryMainUuid.LALIGA;
		case "seriea":
			return CategoryMainUuid.SERIEA;
		case "cft":
			return CategoryMainUuid.CHINA;
		case "csl":
			return CategoryMainUuid.CSL;
		default:
			return null;
		}
	}
}
