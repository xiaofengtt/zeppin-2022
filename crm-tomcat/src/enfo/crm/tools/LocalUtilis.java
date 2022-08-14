package enfo.crm.tools;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalUtilis {
	//国际化
	public static String language(String key,Locale locale){
		String value = key;
		try{
			if(locale!=null){
				ResourceBundle rb = ResourceBundle.getBundle("InternationResouce",locale);   
				value = rb.getString(key);  
			}
		}
		catch(Exception e){
			value = "??" + key + "??";
		}
 
		return value;
	}
	/**
	 * 获得local
	 * @param languageType
	 * @return
	 */
	public static Locale getLocalByLanguageType(String languageType){
		String[] str = languageType.split("_");
		return new Locale(str[0],str[1]);
	}
}
