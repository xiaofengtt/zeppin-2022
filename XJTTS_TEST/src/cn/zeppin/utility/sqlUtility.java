/**
 * 
 */
package cn.zeppin.utility;

/**
 * @author sj
 * 
 */
public class sqlUtility
{
    public static String getSqlString(String key, String value, String opt)
    {
	String string = " and " + key + " " + opt + " " + value;
	return string;

    }

}
