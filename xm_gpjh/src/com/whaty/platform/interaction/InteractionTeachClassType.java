package com.whaty.platform.interaction;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.TypeErrorException;

public class InteractionTeachClassType {

	public static String XXDH = "XXDH";

	public static String FDDY = "FDDY";
	
	public static String ZYZC = "ZYZC";
	
	public static String SMZY = "SMZY";
	
	public static String ZFX = "ZFX";
	
	public static String DX = "DX";
	
	public static String KCZL = "KCZL";
	
	public static String SPFD = "SPFD";


	public static String typeShow(String type) throws TypeErrorException {
		if (type != null && type.equals(XXDH))
			return "教学大纲";
		else if (type != null && type.equals(FDDY))
			return "辅导与答疑";
		else if (type != null && type.equals(ZYZC))
			return "作业与自测";
		else if (type != null && type.equals(SMZY))
			return "纸质作业";
		else if (type != null && type.equals(ZFX))
			return "复习资料";	
		else if (type != null && type.equals(DX))
			return "导学与学习";
		else if (type != null && type.equals(SPFD))
			return "视频辅导";
		else if (type != null && type.equals(KCZL))
			return "参考资料";
		else
			throw new TypeErrorException("InteractionTeachClassType error!");
	}

	public static List types() {
		ArrayList list = new ArrayList();
		list.add(XXDH);
		list.add(FDDY);
		list.add(ZYZC);
		list.add(ZYZC);
		return list;
	}
}
