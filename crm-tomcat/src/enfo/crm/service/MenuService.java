/*
 * 创建日期 2009-12-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.service;

import java.util.List;

import enfo.crm.system.MenuInfoLocal;
import enfo.crm.tools.EJBFactory;
import enfo.crm.util.JsonUtil;

/**
 * @author lzhd
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class MenuService {

	public String getMenuList(Integer op_code,String menu_id,String parent_id,Integer languageType) throws Exception {
		
		MenuInfoLocal menu = EJBFactory.getMenuInfo();
		return JsonUtil.object2json(menu.listMenuRight(op_code,menu_id,parent_id,languageType));
	}
	
}
