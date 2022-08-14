/*
 * �������� 2009-12-8
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package enfo.crm.service;

import java.util.List;

import enfo.crm.system.MenuInfoLocal;
import enfo.crm.tools.EJBFactory;
import enfo.crm.util.JsonUtil;

/**
 * @author lzhd
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class MenuService {

	public String getMenuList(Integer op_code,String menu_id,String parent_id,Integer languageType) throws Exception {
		
		MenuInfoLocal menu = EJBFactory.getMenuInfo();
		return JsonUtil.object2json(menu.listMenuRight(op_code,menu_id,parent_id,languageType));
	}
	
}
