package com.makati.common.util;

import java.util.Iterator;
import java.util.List;

/**
 * 自己需要的工具类
 * @author eden
 * @create 2018-05-18 15:59
 **/

public class JobUtil {
    public static void removeList(List<String> list, String target){
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String item = iter.next();
            if (item.equals(target)) {
                iter.remove();
            }
        }
    }
}
