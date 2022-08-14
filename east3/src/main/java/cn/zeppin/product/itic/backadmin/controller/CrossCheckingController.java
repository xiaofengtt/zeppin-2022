/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITGgGdxxbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGgJgxxbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGgYgxxbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGydbgxbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGydbhtbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGydzywbService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGyzhxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGyzjyyjylService;
import cn.zeppin.product.itic.backadmin.service.api.ITGyGyzzyyhtxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyQjglxxfzqService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyQjglxxzqService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyXtsypzService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyXtsyqzrxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyXtzjmjjfplService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyXtzjyyjylService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhDsfhzjgxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhJydsgrService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhJydsjgService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhJydsjggdxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhTzgwhtbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhXtkhgrService;
import cn.zeppin.product.itic.backadmin.service.api.ITKhXtkhjgService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjGynbkmdzbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjGyzcfzkmtjbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjGyzzkjqkmbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjXtnbkmdzbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjXtxmzcfztjbService;
import cn.zeppin.product.itic.backadmin.service.api.ITKjXtxmzzkjqkmbService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorProductService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmFdcjsxmxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmFfdcjsxmxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtdbgxbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtdbhtbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtdzywbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtxmqsxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtxmsyqbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtxmxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtxmyjhklypgbService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtzhxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtzjmjhtxxService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtzjyyhtxxService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.TGgGdxxb;
import cn.zeppin.product.itic.core.entity.TGgJgxxb;
import cn.zeppin.product.itic.core.entity.TGgYgxxb;
import cn.zeppin.product.itic.core.entity.TGyGydbgxb;
import cn.zeppin.product.itic.core.entity.TGyGydbhtb;
import cn.zeppin.product.itic.core.entity.TGyGydzywb;
import cn.zeppin.product.itic.core.entity.TGyGyzhxx;
import cn.zeppin.product.itic.core.entity.TGyGyzjyyjyl;
import cn.zeppin.product.itic.core.entity.TGyGyzzyyhtxx;
import cn.zeppin.product.itic.core.entity.TJyQjglxxfzq;
import cn.zeppin.product.itic.core.entity.TJyQjglxxzq;
import cn.zeppin.product.itic.core.entity.TJyXtsypz;
import cn.zeppin.product.itic.core.entity.TJyXtsyqzrxx;
import cn.zeppin.product.itic.core.entity.TJyXtzjmjjfpl;
import cn.zeppin.product.itic.core.entity.TJyXtzjyyjyl;
import cn.zeppin.product.itic.core.entity.TKhDsfhzjgxx;
import cn.zeppin.product.itic.core.entity.TKhJydsgr;
import cn.zeppin.product.itic.core.entity.TKhJydsjg;
import cn.zeppin.product.itic.core.entity.TKhJydsjggdxx;
import cn.zeppin.product.itic.core.entity.TKhTzgwhtb;
import cn.zeppin.product.itic.core.entity.TKhXtkhgr;
import cn.zeppin.product.itic.core.entity.TKhXtkhjg;
import cn.zeppin.product.itic.core.entity.TKjGynbkmdzb;
import cn.zeppin.product.itic.core.entity.TKjGyzcfzkmtjb;
import cn.zeppin.product.itic.core.entity.TKjGyzzkjqkmb;
import cn.zeppin.product.itic.core.entity.TKjXtnbkmdzb;
import cn.zeppin.product.itic.core.entity.TKjXtxmzcfztjb;
import cn.zeppin.product.itic.core.entity.TKjXtxmzzkjqkmb;
import cn.zeppin.product.itic.core.entity.TSsOperatorProduct;
import cn.zeppin.product.itic.core.entity.TSsRole.RoleId;
import cn.zeppin.product.itic.core.entity.TXmFdcjsxmxx;
import cn.zeppin.product.itic.core.entity.TXmFfdcjsxmxx;
import cn.zeppin.product.itic.core.entity.TXmXtdbgxb;
import cn.zeppin.product.itic.core.entity.TXmXtdbhtb;
import cn.zeppin.product.itic.core.entity.TXmXtdzywb;
import cn.zeppin.product.itic.core.entity.TXmXtxmqsxx;
import cn.zeppin.product.itic.core.entity.TXmXtxmsyqb;
import cn.zeppin.product.itic.core.entity.TXmXtxmxx;
import cn.zeppin.product.itic.core.entity.TXmXtxmyjhklypgb;
import cn.zeppin.product.itic.core.entity.TXmXtzhxx;
import cn.zeppin.product.itic.core.entity.TXmXtzjmjhtxx;
import cn.zeppin.product.itic.core.entity.TXmXtzjyyhtxx;
import cn.zeppin.product.utility.CheckBigDecimalValues;
import cn.zeppin.product.utility.CheckDateValues;
import cn.zeppin.product.utility.CheckUtil;
import cn.zeppin.product.utility.CheckValues;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.TableColumn;
import cn.zeppin.product.utility.TableColumnCN;
import cn.zeppin.product.utility.Utlity;

/**
 * 勾稽校验
 */

@Controller
@RequestMapping(value = "/backadmin/cross")
public class CrossCheckingController extends BaseController {
	
	@Autowired
	private ITSsOperatorProductService TSsOperatorProductService;
	@Autowired
	private ITGgGdxxbService TGgGdxxbService;
	@Autowired
	private ITGgJgxxbService TGgJgxxbService;
	@Autowired
	private ITGgYgxxbService TGgYgxxbService;
	@Autowired
	private ITGyGydbgxbService TGyGydbgxbService;
	@Autowired
	private ITGyGydbhtbService TGyGydbhtbService;
	@Autowired
	private ITGyGydzywbService TGyGydzywbService;
	@Autowired
	private ITGyGyzhxxService TGyGyzhxxService;
	@Autowired
	private ITGyGyzjyyjylService TGyGyzjyyjylService;
	@Autowired
	private ITGyGyzzyyhtxxService TGyGyzzyyhtxxService;
	@Autowired
	private ITJyQjglxxfzqService TJyQjglxxfzqService;
	@Autowired
	private ITJyQjglxxzqService TJyQjglxxzqService;
	@Autowired
	private ITJyXtsypzService TJyXtsypzService;
	@Autowired
	private ITJyXtsyqzrxxService TJyXtsyqzrxxService;
	@Autowired
	private ITJyXtzjmjjfplService TJyXtzjmjjfplService;
	@Autowired
	private ITJyXtzjyyjylService TJyXtzjyyjylService;
	@Autowired
	private ITKhDsfhzjgxxService TKhDsfhzjgxxService;
	@Autowired
	private ITKhJydsgrService TKhJydsgrService;
	@Autowired
	private ITKhJydsjgService TKhJydsjgService;
	@Autowired
	private ITKhJydsjggdxxService TKhJydsjggdxxService;
	@Autowired
	private ITKhTzgwhtbService TKhTzgwhtbService;
	@Autowired
	private ITKhXtkhgrService TKhXtkhgrService;
	@Autowired
	private ITKhXtkhjgService TKhXtkhjgService;
	@Autowired
	private ITKjGynbkmdzbService TKjGynbkmdzbService;
	@Autowired
	private ITKjGyzcfzkmtjbService TKjGyzcfzkmtjbService;
	@Autowired
	private ITKjGyzzkjqkmbService TKjGyzzkjqkmbService;
	@Autowired
	private ITKjXtnbkmdzbService TKjXtnbkmdzbService;
	@Autowired
	private ITKjXtxmzcfztjbService TKjXtxmzcfztjbService;
	@Autowired
	private ITKjXtxmzzkjqkmbService TKjXtxmzzkjqkmbService;
	@Autowired
	private ITXmFdcjsxmxxService TXmFdcjsxmxxService;
	@Autowired
	private ITXmFfdcjsxmxxService TXmFfdcjsxmxxService;
	@Autowired
	private ITXmXtdbgxbService TXmXtdbgxbService;
	@Autowired
	private ITXmXtdbhtbService TXmXtdbhtbService;
	@Autowired
	private ITXmXtdzywbService TXmXtdzywbService;
	@Autowired
	private ITXmXtxmqsxxService TXmXtxmqsxxService;
	@Autowired
	private ITXmXtxmsyqbService TXmXtxmsyqbService;
	@Autowired
	private ITXmXtxmxxService TXmXtxmxxService;
	@Autowired
	private ITXmXtxmyjhklypgbService TXmXtxmyjhklypgbService;
	@Autowired
	private ITXmXtzhxxService TXmXtzhxxService;
	@Autowired
	private ITXmXtzjmjhtxxService TXmXtzjmjhtxxService;
	@Autowired
	private ITXmXtzjyyhtxxService TXmXtzjyyhtxxService;
	
	/**
	 * 根据权限获取可操作表名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/tableList", method = RequestMethod.GET)
	@ResponseBody
	public Result tableList() {
		
		Session session = SecurityUtils.getSubject().getSession();
		List<String> methodList = (List<String>) session.getAttribute("methodList");
		List<String> controllerList = new ArrayList<String>();
		for(String method : methodList){
			if(method.indexOf("check") > -1 || method.indexOf("edit") > -1){
				controllerList.add(method.substring(0,method.indexOf(":")));
			}
		}
		List<Map<String, String>> listResult = new ArrayList<>();
		Map<String, String> info = new Hashtable<>();
		for(String con : controllerList) {//去除重复项
			info.put(con, Utlity.getTableName(con));
		}
		for(Map.Entry<String, String> entry : info.entrySet()){
			Map<String, String> tableInfo = new Hashtable<>();
			tableInfo.put("key", entry.getKey());
			tableInfo.put("value", entry.getValue());
			listResult.add(tableInfo);
        }
		return ResultManager.createDataResult(listResult);
	}
	
	
	/**
	 * 勾稽校验
	 * @param type -- key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	@ActionParam(key = "types", message="数据类型", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "dataproduct", message="信托项目", type = DataType.STRING)
	@ResponseBody
	public Result check(String[] types, String dataproduct) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		Map<String, String> productMap = (Map<String, String>) session.getAttribute("productMap");
		List<String> methodList = (List<String>) session.getAttribute("methodList");
		List<String> controllerList = new ArrayList<String>();
		for(String method : methodList){
			if(method.indexOf("check") > -1){
				controllerList.add(method.substring(0,method.indexOf(":")));
			}
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		if(RoleId.user.equals(operator.getRole())){
			if(RoleId.user.equals(operator.getRole())){inputParams.put("products", Utlity.getStringForSql(new ArrayList<String>(productMap.keySet())));}
			inputParams.put("datatypes", Utlity.getStringForSql(controllerList));
			inputParams.put("xtxmbh", dataproduct);
			inputParams.put("xtxmbm", dataproduct);
		}
		List<Map<String, Object>> listResult = new ArrayList<>();
		try {
			Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
			for(String type : types){
				if("TGgGdxxb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TGgGdxxb> datas = this.TGgGdxxbService.getListForPage(inputParams, -1, -1, null);
					for(TGgGdxxb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGgJgxxb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TGgJgxxb> datas = this.TGgJgxxbService.getListForPage(inputParams, -1, -1, null);
					for(TGgJgxxb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGgYgxxb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TGgYgxxb> datas = this.TGgYgxxbService.getListForPage(inputParams, -1, -1, null);
					for(TGgYgxxb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGydbgxb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TGyGydbgxb> datas = this.TGyGydbgxbService.getListForPage(inputParams, -1, -1, null);
					for(TGyGydbgxb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGydbhtb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TGyGydbhtb> datas = this.TGyGydbhtbService.getListForPage(inputParams, -1, -1, null);
					for(TGyGydbhtb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGydzywb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TGyGydzywb> datas = this.TGyGydzywbService.getListForPage(inputParams, -1, -1, null);
					for(TGyGydzywb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGyzhxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TGyGyzhxx> datas = this.TGyGyzhxxService.getListForPage(inputParams, -1, -1, null);
					for(TGyGyzhxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGyzjyyjyl".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TGyGyzjyyjyl> datas = this.TGyGyzjyyjylService.getListForPage(inputParams, -1, -1, null);
					for(TGyGyzjyyjyl data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGyzzyyhtxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TGyGyzzyyhtxx> datas = this.TGyGyzzyyhtxxService.getListForPage(inputParams, -1, -1, null);
					for(TGyGyzzyyhtxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyQjglxxfzq".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TJyQjglxxfzq> datas = this.TJyQjglxxfzqService.getListForPage(inputParams, -1, -1, null);
					for(TJyQjglxxfzq data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyQjglxxzq".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TJyQjglxxzq> datas = this.TJyQjglxxzqService.getListForPage(inputParams, -1, -1, null);
					for(TJyQjglxxzq data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyXtsypz".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TJyXtsypz> datas = this.TJyXtsypzService.getListForPage(inputParams, -1, -1, null);
					for(TJyXtsypz data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyXtsyqzrxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TJyXtsyqzrxx> datas = this.TJyXtsyqzrxxService.getListForPage(inputParams, -1, -1, null);
					for(TJyXtsyqzrxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyXtzjmjjfpl".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TJyXtzjmjjfpl> datas = this.TJyXtzjmjjfplService.getListForPage(inputParams, -1, -1, null);
					for(TJyXtzjmjjfpl data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyXtzjyyjyl".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TJyXtzjyyjyl> datas = this.TJyXtzjyyjylService.getListForPage(inputParams, -1, -1, null);
					for(TJyXtzjyyjyl data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhDsfhzjgxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKhDsfhzjgxx> datas = this.TKhDsfhzjgxxService.getListForPage(inputParams, -1, -1, null);
					for(TKhDsfhzjgxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhJydsgr".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKhJydsgr> datas = this.TKhJydsgrService.getListForPage(inputParams, -1, -1, null);
					for(TKhJydsgr data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhJydsjg".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKhJydsjg> datas = this.TKhJydsjgService.getListForPage(inputParams, -1, -1, null);
					for(TKhJydsjg data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhJydsjggdxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKhJydsjggdxx> datas = this.TKhJydsjggdxxService.getListForPage(inputParams, -1, -1, null);
					for(TKhJydsjggdxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhTzgwhtb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKhTzgwhtb> datas = this.TKhTzgwhtbService.getListForPage(inputParams, -1, -1, null);
					for(TKhTzgwhtb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhXtkhgr".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKhXtkhgr> datas = this.TKhXtkhgrService.getListForPage(inputParams, -1, -1, null);
					for(TKhXtkhgr data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhXtkhjg".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKhXtkhjg> datas = this.TKhXtkhjgService.getListForPage(inputParams, -1, -1, null);
					for(TKhXtkhjg data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjGynbkmdzb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKjGynbkmdzb> datas = this.TKjGynbkmdzbService.getListForPage(inputParams, -1, -1, null);
					for(TKjGynbkmdzb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjGyzcfzkmtjb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKjGyzcfzkmtjb> datas = this.TKjGyzcfzkmtjbService.getListForPage(inputParams, -1, -1, null);
					for(TKjGyzcfzkmtjb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjGyzzkjqkmb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKjGyzzkjqkmb> datas = this.TKjGyzzkjqkmbService.getListForPage(inputParams, -1, -1, null);
					for(TKjGyzzkjqkmb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjXtnbkmdzb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKjXtnbkmdzb> datas = this.TKjXtnbkmdzbService.getListForPage(inputParams, -1, -1, null);
					for(TKjXtnbkmdzb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjXtxmzcfztjb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKjXtxmzcfztjb> datas = this.TKjXtxmzcfztjbService.getListForPage(inputParams, -1, -1, null);
					for(TKjXtxmzcfztjb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjXtxmzzkjqkmb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TKjXtxmzzkjqkmb> datas = this.TKjXtxmzzkjqkmbService.getListForPage(inputParams, -1, -1, null);
					for(TKjXtxmzzkjqkmb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmFdcjsxmxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmFdcjsxmxx> datas = this.TXmFdcjsxmxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmFdcjsxmxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmFfdcjsxmxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmFfdcjsxmxx> datas = this.TXmFfdcjsxmxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmFfdcjsxmxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtdbgxb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmXtdbgxb> datas = this.TXmXtdbgxbService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtdbgxb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtdbhtb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmXtdbhtb> datas = this.TXmXtdbhtbService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtdbhtb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtdzywb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmXtdzywb> datas = this.TXmXtdzywbService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtdzywb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtxmqsxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmXtxmqsxx> datas = this.TXmXtxmqsxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtxmqsxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtxmsyqb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmXtxmsyqb> datas = this.TXmXtxmsyqbService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtxmsyqb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtxmxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmXtxmxx> datas = this.TXmXtxmxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtxmxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtxmyjhklypgb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmXtxmyjhklypgb> datas = this.TXmXtxmyjhklypgbService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtxmyjhklypgb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtzhxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmXtzhxx> datas = this.TXmXtzhxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtzhxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtzjmjhtxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmXtzjmjhtxx> datas = this.TXmXtzjmjhtxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtzjmjhtxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtzjyyhtxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());
					List<TXmXtzjyyhtxx> datas = this.TXmXtzjyyhtxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtzjyyhtxx data :datas){
						datasMap.get(type).add(data);
					}
				}
			}
			for(String type : datasMap.keySet()){
				List<Object> list = datasMap.get(type);
				Class<?> cls = Class.forName("cn.zeppin.product.itic.core.entity."+type);
				if(list != null && !list.isEmpty()) {
					for(Object t : list) {
						Field[] fields = cls.getDeclaredFields();
						for(Field f : fields) {
							if(!"serialVersionUID".equals(f.getName()) && !"id".equals(f.getName()) && !"status".equals(f.getName()) &&
									!"createtime".equals(f.getName()) && !"updatetime".equals(f.getName()) && !"ctltime".equals(f.getName())){
								
								Object value = ReflectUtlity.invokeGet(t, f.getName());
								
								boolean flag = false;
								Map<String, Object> result = new HashMap<>();
								result.put("id", ReflectUtlity.invokeGet(t, "id"));
								result.put("tableName", Utlity.getTableName(type));
								result.put("table", type);
								String product = "";
								if(ReflectUtlity.getGetMethod(cls, "xtxmbh") != null){
									product = ReflectUtlity.invokeGet(t, "xtxmbh") == null ? "" : ReflectUtlity.invokeGet(t, "xtxmbh").toString();
								}
								if(ReflectUtlity.getGetMethod(cls, "xtxmbm") != null){
									product = ReflectUtlity.invokeGet(t, "xtxmbm") == null ? "" : ReflectUtlity.invokeGet(t, "xtxmbm").toString();
								}
								result.put("product", product);
								result.put("field", f.getName().toUpperCase());
								Class<?> filedType = f.getType();
					            String typeName = filedType.getName();
								result.put("fieldType", typeName.substring(typeName.lastIndexOf(".")+1));
								result.put("value", value);
								if(value == null || "".equals(value)) {
									
									//20180625 修改非空校验 只校验制定列
									if(!CheckUtil.checkNullable(type+"_"+f.getName(), null)) {
										flag = true;
										result.put("crossType", "必填");
										
										Object obj = ReflectUtlity.getConst(CheckValues.class, f.getName());
										if(obj != null){
											result.put("crossType", "必填"+",取值范围："+obj.toString());
										}
										
										Object obj2 = ReflectUtlity.getConst(CheckDateValues.class, f.getName());
										if(obj2 != null){
											result.put("crossType", "必填"+",格式如："+obj2.toString());
										}
										
										//特殊情况 特殊校验 如货币等
							            if(typeName.indexOf("BigDecimal") > -1) {//筛选BigDecimal类型的过数字货币小数位数校验
							            	
							        		if(CheckBigDecimalValues.bigdecimal2p.containsKey(f.getName())) {//判断两位小数
						        				result.put("crossType", "必填"+",取值范围："+CheckBigDecimalValues.bigdecimal2p.get(f.getName()));
							        		}
							        		if(CheckBigDecimalValues.bigdecimal3p.containsKey(f.getName())) {//判断三位小数
						        				result.put("crossType", "必填"+",取值范围："+CheckBigDecimalValues.bigdecimal3p.get(f.getName()));
							        		}
							        		if(CheckBigDecimalValues.bigdecimal4p.containsKey(f.getName())) {//判断四位小数
						        				result.put("crossType", "必填"+",取值范围："+CheckBigDecimalValues.bigdecimal4p.get(f.getName()));
							        		}
							            }
									}
								} else {
									
									if(!CheckUtil.checkValue(f.getName(), value.toString())) {//校验select
										flag = true;
										Object obj = ReflectUtlity.getConst(CheckValues.class, f.getName());
										if(obj != null){
											result.put("crossType", "取值范围："+obj.toString());
										}
									}
									
									if(!CheckUtil.checkDateValue(f.getName(), value.toString())) {//校验日期
										flag = true;
										Object obj = ReflectUtlity.getConst(CheckDateValues.class, f.getName());
										if(obj != null){
											result.put("crossType", "格式如："+obj.toString());
										}
									}
									
									//特殊情况 特殊校验 如货币等
						            if(typeName.indexOf("BigDecimal") > -1) {//筛选BigDecimal类型的过数字校验
						        		if(CheckBigDecimalValues.bigdecimal2p.containsKey(f.getName())) {//判断两位小数
						        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 2)) {
						        				flag = true;
						        				result.put("crossType", "取值范围："+CheckBigDecimalValues.bigdecimal2p.get(f.getName()));
						        			}
						        		}
						        		if(CheckBigDecimalValues.bigdecimal3p.containsKey(f.getName())) {//判断三位小数
						        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 3)) {
						        				flag = true;
						        				result.put("crossType", "取值范围："+CheckBigDecimalValues.bigdecimal3p.get(f.getName()));
						        			}
						        		}
						        		if(CheckBigDecimalValues.bigdecimal4p.containsKey(f.getName())) {//判断四位小数
						        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 4)) {
						        				flag = true;
						        				result.put("crossType", "取值范围："+CheckBigDecimalValues.bigdecimal4p.get(f.getName()));
						        			}
						        		}
						            }
								}
								if(flag) {
									listResult.add(result);
								}
							}
						}
					}
				}
			}
			return ResultManager.createDataResult(listResult, listResult.size());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return ResultManager.createFailResult("勾稽校验失败，请刷新页面重试！");
		}
	}
	
	/**
	 * 超管勾稽校验
	 */
	@RequestMapping(value = "/adminCheck", method = RequestMethod.GET)
	@ActionParam(key = "types", message="数据类型", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "dataproduct", message="信托项目", type = DataType.STRING)
	@ResponseBody
	public Result adminCheck(String[] types, String dataproduct) {
		Session session = SecurityUtils.getSubject().getSession();
		
		Map<String, String> inputParams = new HashMap<String, String>();
		Map<String, List<Map<String, Object>>> dataResult = new HashMap<>();
		Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
		for(String type : types){
			if("TGgGdxxb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TGgGdxxb> datas = this.TGgGdxxbService.getListForPage(inputParams, -1, -1, null);
				for(TGgGdxxb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TGgJgxxb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TGgJgxxb> datas = this.TGgJgxxbService.getListForPage(inputParams, -1, -1, null);
				for(TGgJgxxb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TGgYgxxb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TGgYgxxb> datas = this.TGgYgxxbService.getListForPage(inputParams, -1, -1, null);
				for(TGgYgxxb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TGyGydbgxb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TGyGydbgxb> datas = this.TGyGydbgxbService.getListForPage(inputParams, -1, -1, null);
				for(TGyGydbgxb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TGyGydbhtb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TGyGydbhtb> datas = this.TGyGydbhtbService.getListForPage(inputParams, -1, -1, null);
				for(TGyGydbhtb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TGyGydzywb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TGyGydzywb> datas = this.TGyGydzywbService.getListForPage(inputParams, -1, -1, null);
				for(TGyGydzywb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TGyGyzhxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TGyGyzhxx> datas = this.TGyGyzhxxService.getListForPage(inputParams, -1, -1, null);
				for(TGyGyzhxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TGyGyzjyyjyl".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TGyGyzjyyjyl> datas = this.TGyGyzjyyjylService.getListForPage(inputParams, -1, -1, null);
				for(TGyGyzjyyjyl data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TGyGyzzyyhtxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TGyGyzzyyhtxx> datas = this.TGyGyzzyyhtxxService.getListForPage(inputParams, -1, -1, null);
				for(TGyGyzzyyhtxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyQjglxxfzq".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TJyQjglxxfzq> datas = this.TJyQjglxxfzqService.getListForPage(inputParams, -1, -1, null);
				for(TJyQjglxxfzq data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyQjglxxzq".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TJyQjglxxzq> datas = this.TJyQjglxxzqService.getListForPage(inputParams, -1, -1, null);
				for(TJyQjglxxzq data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyXtsypz".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TJyXtsypz> datas = this.TJyXtsypzService.getListForPage(inputParams, -1, -1, null);
				for(TJyXtsypz data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyXtsyqzrxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TJyXtsyqzrxx> datas = this.TJyXtsyqzrxxService.getListForPage(inputParams, -1, -1, null);
				for(TJyXtsyqzrxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyXtzjmjjfpl".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TJyXtzjmjjfpl> datas = this.TJyXtzjmjjfplService.getListForPage(inputParams, -1, -1, null);
				for(TJyXtzjmjjfpl data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyXtzjyyjyl".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TJyXtzjyyjyl> datas = this.TJyXtzjyyjylService.getListForPage(inputParams, -1, -1, null);
				for(TJyXtzjyyjyl data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKhDsfhzjgxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKhDsfhzjgxx> datas = this.TKhDsfhzjgxxService.getListForPage(inputParams, -1, -1, null);
				for(TKhDsfhzjgxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKhJydsgr".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKhJydsgr> datas = this.TKhJydsgrService.getListForPage(inputParams, -1, -1, null);
				for(TKhJydsgr data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKhJydsjg".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKhJydsjg> datas = this.TKhJydsjgService.getListForPage(inputParams, -1, -1, null);
				for(TKhJydsjg data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKhJydsjggdxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKhJydsjggdxx> datas = this.TKhJydsjggdxxService.getListForPage(inputParams, -1, -1, null);
				for(TKhJydsjggdxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKhTzgwhtb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKhTzgwhtb> datas = this.TKhTzgwhtbService.getListForPage(inputParams, -1, -1, null);
				for(TKhTzgwhtb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKhXtkhgr".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKhXtkhgr> datas = this.TKhXtkhgrService.getListForPage(inputParams, -1, -1, null);
				for(TKhXtkhgr data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKhXtkhjg".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKhXtkhjg> datas = this.TKhXtkhjgService.getListForPage(inputParams, -1, -1, null);
				for(TKhXtkhjg data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKjGynbkmdzb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKjGynbkmdzb> datas = this.TKjGynbkmdzbService.getListForPage(inputParams, -1, -1, null);
				for(TKjGynbkmdzb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKjGyzcfzkmtjb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKjGyzcfzkmtjb> datas = this.TKjGyzcfzkmtjbService.getListForPage(inputParams, -1, -1, null);
				for(TKjGyzcfzkmtjb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKjGyzzkjqkmb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKjGyzzkjqkmb> datas = this.TKjGyzzkjqkmbService.getListForPage(inputParams, -1, -1, null);
				for(TKjGyzzkjqkmb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKjXtnbkmdzb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKjXtnbkmdzb> datas = this.TKjXtnbkmdzbService.getListForPage(inputParams, -1, -1, null);
				for(TKjXtnbkmdzb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKjXtxmzcfztjb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKjXtxmzcfztjb> datas = this.TKjXtxmzcfztjbService.getListForPage(inputParams, -1, -1, null);
				for(TKjXtxmzcfztjb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKjXtxmzzkjqkmb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TKjXtxmzzkjqkmb> datas = this.TKjXtxmzzkjqkmbService.getListForPage(inputParams, -1, -1, null);
				for(TKjXtxmzzkjqkmb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmFdcjsxmxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmFdcjsxmxx> datas = this.TXmFdcjsxmxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmFdcjsxmxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmFfdcjsxmxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmFfdcjsxmxx> datas = this.TXmFfdcjsxmxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmFfdcjsxmxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtdbgxb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmXtdbgxb> datas = this.TXmXtdbgxbService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtdbgxb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtdbhtb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmXtdbhtb> datas = this.TXmXtdbhtbService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtdbhtb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtdzywb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmXtdzywb> datas = this.TXmXtdzywbService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtdzywb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtxmqsxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmXtxmqsxx> datas = this.TXmXtxmqsxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtxmqsxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtxmsyqb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmXtxmsyqb> datas = this.TXmXtxmsyqbService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtxmsyqb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtxmxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmXtxmxx> datas = this.TXmXtxmxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtxmxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtxmyjhklypgb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmXtxmyjhklypgb> datas = this.TXmXtxmyjhklypgbService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtxmyjhklypgb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtzhxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmXtzhxx> datas = this.TXmXtzhxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtzhxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtzjmjhtxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmXtzjmjhtxx> datas = this.TXmXtzjmjhtxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtzjmjhtxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtzjyyhtxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
				List<TXmXtzjyyhtxx> datas = this.TXmXtzjyyhtxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtzjyyhtxx data :datas){
					datasMap.get(type).add(data);
				}
			}
		}
		try {
			for(String type : datasMap.keySet()){
				List<Object> list = datasMap.get(type);
				Class<?> cls = Class.forName("cn.zeppin.product.itic.core.entity."+type);
				if(list != null && !list.isEmpty()) {
					for(Object t : list) {
						Field[] fields = cls.getDeclaredFields();
						for(Field f : fields) {
							if(!"serialVersionUID".equals(f.getName()) && !"id".equals(f.getName()) && !"status".equals(f.getName()) &&
									!"createtime".equals(f.getName()) && !"updatetime".equals(f.getName()) && !"ctltime".equals(f.getName())){
								
								Object value = ReflectUtlity.invokeGet(t, f.getName());
								
								boolean flag = false;
								Map<String, Object> result = new HashMap<>();
								result.put("id", ReflectUtlity.invokeGet(t, "id"));
								result.put("tableName", Utlity.getTableName(type));
								result.put("table", type);
								String product = "";
								if(ReflectUtlity.getGetMethod(cls, "xtxmbh") != null){
									product = ReflectUtlity.invokeGet(t, "xtxmbh") == null ? "" : ReflectUtlity.invokeGet(t, "xtxmbh").toString();
								}
								if(ReflectUtlity.getGetMethod(cls, "xtxmbm") != null){
									product = ReflectUtlity.invokeGet(t, "xtxmbm") == null ? "" : ReflectUtlity.invokeGet(t, "xtxmbm").toString();
								}
								result.put("product", product);
								result.put("field", f.getName().toUpperCase());
								Class<?> filedType = f.getType();
					            String typeName = filedType.getName();
								result.put("fieldType", typeName.substring(typeName.lastIndexOf(".")+1));
								result.put("value", value);
								if(value == null || "".equals(value)) {
									
									//20180625 修改非空校验 只校验制定列
									if(!CheckUtil.checkNullable(type+"_"+f.getName(), null)) {
										flag = true;
										result.put("crossType", "必填");
										
										Object obj = ReflectUtlity.getConst(CheckValues.class, f.getName());
										if(obj != null){
											result.put("crossType", "必填"+",取值范围："+obj.toString());
										}
										
										Object obj2 = ReflectUtlity.getConst(CheckDateValues.class, f.getName());
										if(obj2 != null){
											result.put("crossType", "必填"+",格式如："+obj2.toString());
										}
										
										//特殊情况 特殊校验 如货币等
							            if(typeName.indexOf("BigDecimal") > -1) {//筛选BigDecimal类型的过数字货币小数位数校验
							            	
							        		if(CheckBigDecimalValues.bigdecimal2p.containsKey(f.getName())) {//判断两位小数
						        				result.put("crossType", "必填"+",取值范围："+CheckBigDecimalValues.bigdecimal2p.get(f.getName()));
							        		}
							        		if(CheckBigDecimalValues.bigdecimal3p.containsKey(f.getName())) {//判断三位小数
						        				result.put("crossType", "必填"+",取值范围："+CheckBigDecimalValues.bigdecimal3p.get(f.getName()));
							        		}
							        		if(CheckBigDecimalValues.bigdecimal4p.containsKey(f.getName())) {//判断四位小数
						        				result.put("crossType", "必填"+",取值范围："+CheckBigDecimalValues.bigdecimal4p.get(f.getName()));
							        		}
							            }
									}
								} else {
									
									if(!CheckUtil.checkValue(f.getName(), value.toString())) {//校验select
										flag = true;
										Object obj = ReflectUtlity.getConst(CheckValues.class, f.getName());
										if(obj != null){
											result.put("crossType", "取值范围："+obj.toString());
										}
									}
									
									if(!CheckUtil.checkDateValue(f.getName(), value.toString())) {//校验日期
										flag = true;
										Object obj = ReflectUtlity.getConst(CheckDateValues.class, f.getName());
										if(obj != null){
											result.put("crossType", "格式如："+obj.toString());
										}
									}
									
									//特殊情况 特殊校验 如货币等
						            if(typeName.indexOf("BigDecimal") > -1) {//筛选BigDecimal类型的过数字校验
						        		if(CheckBigDecimalValues.bigdecimal2p.containsKey(f.getName())) {//判断两位小数
						        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 2)) {
						        				flag = true;
						        				result.put("crossType", "取值范围："+CheckBigDecimalValues.bigdecimal2p.get(f.getName()));
						        			}
						        		}
						        		if(CheckBigDecimalValues.bigdecimal3p.containsKey(f.getName())) {//判断三位小数
						        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 3)) {
						        				flag = true;
						        				result.put("crossType", "取值范围："+CheckBigDecimalValues.bigdecimal3p.get(f.getName()));
						        			}
						        		}
						        		if(CheckBigDecimalValues.bigdecimal4p.containsKey(f.getName())) {//判断四位小数
						        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 4)) {
						        				flag = true;
						        				result.put("crossType", "取值范围："+CheckBigDecimalValues.bigdecimal4p.get(f.getName()));
						        			}
						        		}
						            }
								}
								if(flag) {
									dataResult.get(type).add(result);
								}
							}
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return ResultManager.createFailResult("勾稽校验失败，请刷新页面重试！");
		}
		
		Map<String,TSsOperatorProduct> productDatas = new HashMap<>();
		List<TSsOperatorProduct> productList= this.TSsOperatorProductService.getList(new HashMap<String, String>());
		for(TSsOperatorProduct op : productList){
			productDatas.put(op.getProductcode(), op);
		}
		List<Map<String, Object>> resultList = new ArrayList<>();
		Map<String, Integer> rowIndex = new HashMap<String, Integer>();
		
		int r = 0;
		for(String type : dataResult.keySet()){
			List<Map<String, Object>> dataList = dataResult.get(type);
			if(dataList.isEmpty()){
				continue;
			}
			
			for(Map<String, Object> data : dataList){
				String[] columnList = (String[]) ReflectUtlity.getConst(TableColumn.class, type);
				String[] columnListCN = (String[]) ReflectUtlity.getConst(TableColumnCN.class, type);
				String field = data.get("field").toString().toLowerCase();
				for(int x = 0; x < columnList.length; x++){
					if(columnList[x].equals(field)){
						field = columnListCN[x];
						break;
					}
				}
				
				String id = data.get("id").toString();
				String product = data.get("product").toString();
				String productName = "";
				String manager = "";
				String department = "";
				if(!"".equals(product) && productDatas.get(product) != null){
					if(rowIndex.get(product) != null){
						Map<String, Object> dataMap = resultList.get(rowIndex.get(product));
						String oldField = dataMap.get("field").toString();
						if(oldField.indexOf(field) == -1){
							String oldCrossType= dataMap.get("crossType").toString();
							dataMap.put("field", oldField+";"+ field);
							dataMap.put("crossType", oldCrossType+";"+ data.get("crossType").toString());
						}
					}else{
						TSsOperatorProduct op = productDatas.get(product);
						productName = op.getProductname();
						manager = op.getOpname();
						department = op.getDepartname();
						
						rowIndex.put(product, r);
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("row", r+1);
						dataMap.put("tableName", data.get("tableName").toString());
						dataMap.put("field", field);
						dataMap.put("crossType", data.get("crossType").toString());
						dataMap.put("product", product);
						dataMap.put("productName", productName);
						dataMap.put("manager", manager);
						dataMap.put("department", department);
						resultList.add(dataMap);
						r++;
					}
				}else{
					if(rowIndex.get(id) != null){
						Map<String, Object> dataMap = resultList.get(rowIndex.get(id));
						String oldField = dataMap.get("field").toString();
						String oldCrossType= dataMap.get("crossType").toString();
						dataMap.put("field", oldField+";"+ field);
						dataMap.put("crossType", oldCrossType+";"+ data.get("crossType").toString());
					}else{
						rowIndex.put(id, r);
						Map<String, Object> dataMap = new HashMap<String, Object>();
						dataMap.put("row", r+1);
						dataMap.put("tableName", data.get("tableName").toString());
						dataMap.put("field", field);
						dataMap.put("crossType", data.get("crossType").toString());
						dataMap.put("product", product);
						dataMap.put("productName", productName);
						dataMap.put("manager", manager);
						dataMap.put("department", department);
						resultList.add(dataMap);
						r++;
					}
				}
			}
		}
		session.setAttribute("outputList", resultList);
		return ResultManager.createDataResult(resultList, resultList.size());
	}
	
	/**
	 * 导出勾稽结果
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/output", method = RequestMethod.GET)
	@ActionParam(key = "types", message="数据类型", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "dataproduct", message="信托项目", type = DataType.STRING)
	@ResponseBody
	public Result output(String[] types, String dataproduct, HttpServletResponse response) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		Map<String, String> productMap = (Map<String, String>) session.getAttribute("productMap");
		List<String> methodList = (List<String>) session.getAttribute("methodList");
		List<String> controllerList = new ArrayList<String>();
		for(String method : methodList){
			if(method.indexOf("check") > -1){
				controllerList.add(method.substring(0,method.indexOf(":")));
			}
		}
		
		Map<String, String> inputParams = new HashMap<String, String>();
		if(RoleId.user.equals(operator.getRole())){
			if(RoleId.user.equals(operator.getRole())){inputParams.put("products", Utlity.getStringForSql(new ArrayList<String>(productMap.keySet())));}
			inputParams.put("datatypes", Utlity.getStringForSql(controllerList));
			inputParams.put("xtxmbh", dataproduct);
			inputParams.put("xtxmbm", dataproduct);
		}
		
		Map<String, List<Map<String, Object>>> dataResult = new HashMap<>();
		try {
			Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
			for(String type : types){
				if("TGgGdxxb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TGgGdxxb> datas = this.TGgGdxxbService.getListForPage(inputParams, -1, -1, null);
					for(TGgGdxxb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGgJgxxb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TGgJgxxb> datas = this.TGgJgxxbService.getListForPage(inputParams, -1, -1, null);
					for(TGgJgxxb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGgYgxxb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TGgYgxxb> datas = this.TGgYgxxbService.getListForPage(inputParams, -1, -1, null);
					for(TGgYgxxb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGydbgxb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TGyGydbgxb> datas = this.TGyGydbgxbService.getListForPage(inputParams, -1, -1, null);
					for(TGyGydbgxb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGydbhtb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TGyGydbhtb> datas = this.TGyGydbhtbService.getListForPage(inputParams, -1, -1, null);
					for(TGyGydbhtb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGydzywb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TGyGydzywb> datas = this.TGyGydzywbService.getListForPage(inputParams, -1, -1, null);
					for(TGyGydzywb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGyzhxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TGyGyzhxx> datas = this.TGyGyzhxxService.getListForPage(inputParams, -1, -1, null);
					for(TGyGyzhxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGyzjyyjyl".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TGyGyzjyyjyl> datas = this.TGyGyzjyyjylService.getListForPage(inputParams, -1, -1, null);
					for(TGyGyzjyyjyl data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TGyGyzzyyhtxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TGyGyzzyyhtxx> datas = this.TGyGyzzyyhtxxService.getListForPage(inputParams, -1, -1, null);
					for(TGyGyzzyyhtxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyQjglxxfzq".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TJyQjglxxfzq> datas = this.TJyQjglxxfzqService.getListForPage(inputParams, -1, -1, null);
					for(TJyQjglxxfzq data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyQjglxxzq".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TJyQjglxxzq> datas = this.TJyQjglxxzqService.getListForPage(inputParams, -1, -1, null);
					for(TJyQjglxxzq data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyXtsypz".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TJyXtsypz> datas = this.TJyXtsypzService.getListForPage(inputParams, -1, -1, null);
					for(TJyXtsypz data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyXtsyqzrxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TJyXtsyqzrxx> datas = this.TJyXtsyqzrxxService.getListForPage(inputParams, -1, -1, null);
					for(TJyXtsyqzrxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyXtzjmjjfpl".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TJyXtzjmjjfpl> datas = this.TJyXtzjmjjfplService.getListForPage(inputParams, -1, -1, null);
					for(TJyXtzjmjjfpl data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TJyXtzjyyjyl".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TJyXtzjyyjyl> datas = this.TJyXtzjyyjylService.getListForPage(inputParams, -1, -1, null);
					for(TJyXtzjyyjyl data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhDsfhzjgxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKhDsfhzjgxx> datas = this.TKhDsfhzjgxxService.getListForPage(inputParams, -1, -1, null);
					for(TKhDsfhzjgxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhJydsgr".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKhJydsgr> datas = this.TKhJydsgrService.getListForPage(inputParams, -1, -1, null);
					for(TKhJydsgr data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhJydsjg".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKhJydsjg> datas = this.TKhJydsjgService.getListForPage(inputParams, -1, -1, null);
					for(TKhJydsjg data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhJydsjggdxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKhJydsjggdxx> datas = this.TKhJydsjggdxxService.getListForPage(inputParams, -1, -1, null);
					for(TKhJydsjggdxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhTzgwhtb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKhTzgwhtb> datas = this.TKhTzgwhtbService.getListForPage(inputParams, -1, -1, null);
					for(TKhTzgwhtb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhXtkhgr".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKhXtkhgr> datas = this.TKhXtkhgrService.getListForPage(inputParams, -1, -1, null);
					for(TKhXtkhgr data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKhXtkhjg".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKhXtkhjg> datas = this.TKhXtkhjgService.getListForPage(inputParams, -1, -1, null);
					for(TKhXtkhjg data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjGynbkmdzb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKjGynbkmdzb> datas = this.TKjGynbkmdzbService.getListForPage(inputParams, -1, -1, null);
					for(TKjGynbkmdzb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjGyzcfzkmtjb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKjGyzcfzkmtjb> datas = this.TKjGyzcfzkmtjbService.getListForPage(inputParams, -1, -1, null);
					for(TKjGyzcfzkmtjb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjGyzzkjqkmb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKjGyzzkjqkmb> datas = this.TKjGyzzkjqkmbService.getListForPage(inputParams, -1, -1, null);
					for(TKjGyzzkjqkmb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjXtnbkmdzb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKjXtnbkmdzb> datas = this.TKjXtnbkmdzbService.getListForPage(inputParams, -1, -1, null);
					for(TKjXtnbkmdzb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjXtxmzcfztjb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKjXtxmzcfztjb> datas = this.TKjXtxmzcfztjbService.getListForPage(inputParams, -1, -1, null);
					for(TKjXtxmzcfztjb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TKjXtxmzzkjqkmb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TKjXtxmzzkjqkmb> datas = this.TKjXtxmzzkjqkmbService.getListForPage(inputParams, -1, -1, null);
					for(TKjXtxmzzkjqkmb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmFdcjsxmxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmFdcjsxmxx> datas = this.TXmFdcjsxmxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmFdcjsxmxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmFfdcjsxmxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmFfdcjsxmxx> datas = this.TXmFfdcjsxmxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmFfdcjsxmxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtdbgxb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmXtdbgxb> datas = this.TXmXtdbgxbService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtdbgxb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtdbhtb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmXtdbhtb> datas = this.TXmXtdbhtbService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtdbhtb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtdzywb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmXtdzywb> datas = this.TXmXtdzywbService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtdzywb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtxmqsxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmXtxmqsxx> datas = this.TXmXtxmqsxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtxmqsxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtxmsyqb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmXtxmsyqb> datas = this.TXmXtxmsyqbService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtxmsyqb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtxmxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmXtxmxx> datas = this.TXmXtxmxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtxmxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtxmyjhklypgb".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmXtxmyjhklypgb> datas = this.TXmXtxmyjhklypgbService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtxmyjhklypgb data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtzhxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmXtzhxx> datas = this.TXmXtzhxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtzhxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtzjmjhtxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmXtzjmjhtxx> datas = this.TXmXtzjmjhtxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtzjmjhtxx data :datas){
						datasMap.get(type).add(data);
					}
				} else if("TXmXtzjyyhtxx".equals(type)){
					datasMap.put(type, new ArrayList<Object>());dataResult.put(type, new ArrayList<Map<String,Object>>());
					List<TXmXtzjyyhtxx> datas = this.TXmXtzjyyhtxxService.getListForPage(inputParams, -1, -1, null);
					for(TXmXtzjyyhtxx data :datas){
						datasMap.get(type).add(data);
					}
				}
			}
			for(String type : datasMap.keySet()){
				List<Object> list = datasMap.get(type);
				Class<?> cls = Class.forName("cn.zeppin.product.itic.core.entity."+type);
				if(list != null && !list.isEmpty()) {
					for(Object t : list) {
						Field[] fields = cls.getDeclaredFields();
						for(Field f : fields) {
							if(!"serialVersionUID".equals(f.getName()) && !"id".equals(f.getName()) && !"status".equals(f.getName()) &&
									!"createtime".equals(f.getName()) && !"updatetime".equals(f.getName()) && !"ctltime".equals(f.getName())){
								
								Object value = ReflectUtlity.invokeGet(t, f.getName());
								
								boolean flag = false;
								Map<String, Object> result = new HashMap<>();
								result.put("id", ReflectUtlity.invokeGet(t, "id"));
								result.put("tableName", Utlity.getTableName(type));
								result.put("table", type);
								String product = "";
								if(ReflectUtlity.getGetMethod(cls, "xtxmbh") != null){
									product = ReflectUtlity.invokeGet(t, "xtxmbh") == null ? "" : ReflectUtlity.invokeGet(t, "xtxmbh").toString();
								}
								if(ReflectUtlity.getGetMethod(cls, "xtxmbm") != null){
									product = ReflectUtlity.invokeGet(t, "xtxmbm") == null ? "" : ReflectUtlity.invokeGet(t, "xtxmbm").toString();
								}
								result.put("product", product);
								result.put("field", f.getName().toUpperCase());
								Class<?> filedType = f.getType();
					            String typeName = filedType.getName();
								result.put("fieldType", typeName.substring(typeName.lastIndexOf(".")+1));
								result.put("value", value);
								if(value == null || "".equals(value)) {
									
									//20180625 修改非空校验 只校验制定列
									if(!CheckUtil.checkNullable(type+"_"+f.getName(), null)) {
										flag = true;
										result.put("crossType", "必填");
										
										Object obj = ReflectUtlity.getConst(CheckValues.class, f.getName());
										if(obj != null){
											result.put("crossType", "必填"+",取值范围："+obj.toString());
										}
										
										Object obj2 = ReflectUtlity.getConst(CheckDateValues.class, f.getName());
										if(obj2 != null){
											result.put("crossType", "必填"+",格式如："+obj2.toString());
										}
										
										//特殊情况 特殊校验 如货币等
							            if(typeName.indexOf("BigDecimal") > -1) {//筛选BigDecimal类型的过数字货币小数位数校验
							            	
							        		if(CheckBigDecimalValues.bigdecimal2p.containsKey(f.getName())) {//判断两位小数
						        				result.put("crossType", "必填"+",取值范围："+CheckBigDecimalValues.bigdecimal2p.get(f.getName()));
							        		}
							        		if(CheckBigDecimalValues.bigdecimal3p.containsKey(f.getName())) {//判断三位小数
						        				result.put("crossType", "必填"+",取值范围："+CheckBigDecimalValues.bigdecimal3p.get(f.getName()));
							        		}
							        		if(CheckBigDecimalValues.bigdecimal4p.containsKey(f.getName())) {//判断四位小数
						        				result.put("crossType", "必填"+",取值范围："+CheckBigDecimalValues.bigdecimal4p.get(f.getName()));
							        		}
							            }
									}
								} else {
									
									if(!CheckUtil.checkValue(f.getName(), value.toString())) {//校验select
										flag = true;
										Object obj = ReflectUtlity.getConst(CheckValues.class, f.getName());
										if(obj != null){
											result.put("crossType", "取值范围："+obj.toString());
										}
									}
									
									if(!CheckUtil.checkDateValue(f.getName(), value.toString())) {//校验日期
										flag = true;
										Object obj = ReflectUtlity.getConst(CheckDateValues.class, f.getName());
										if(obj != null){
											result.put("crossType", "格式如："+obj.toString());
										}
									}
									
									//特殊情况 特殊校验 如货币等
						            if(typeName.indexOf("BigDecimal") > -1) {//筛选BigDecimal类型的过数字校验
						        		if(CheckBigDecimalValues.bigdecimal2p.containsKey(f.getName())) {//判断两位小数
						        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 2)) {
						        				flag = true;
						        				result.put("crossType", "取值范围："+CheckBigDecimalValues.bigdecimal2p.get(f.getName()));
						        			}
						        		}
						        		if(CheckBigDecimalValues.bigdecimal3p.containsKey(f.getName())) {//判断三位小数
						        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 3)) {
						        				flag = true;
						        				result.put("crossType", "取值范围："+CheckBigDecimalValues.bigdecimal3p.get(f.getName()));
						        			}
						        		}
						        		if(CheckBigDecimalValues.bigdecimal4p.containsKey(f.getName())) {//判断四位小数
						        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 4)) {
						        				flag = true;
						        				result.put("crossType", "取值范围："+CheckBigDecimalValues.bigdecimal4p.get(f.getName()));
						        			}
						        		}
						            }
								}
								if(flag) {
									dataResult.get(type).add(result);
								}
							}
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return ResultManager.createFailResult("勾稽校验失败，请刷新页面重试！");
		}
		Map<String,TSsOperatorProduct> productDatas = new HashMap<>();
		List<TSsOperatorProduct> productList= this.TSsOperatorProductService.getList(new HashMap<String, String>());
		for(TSsOperatorProduct op : productList){
			productDatas.put(op.getProductcode(), op);
		}
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "勾稽结果");
			XSSFRow row = s.createRow(0);
			
			s.setColumnWidth(0, 1000);
			s.setColumnWidth(1, 5000);
			s.setColumnWidth(2, 10000);
			s.setColumnWidth(3, 20000);
			s.setColumnWidth(4, 2000);
			s.setColumnWidth(5, 10000);
			s.setColumnWidth(6, 2000);
			s.setColumnWidth(7, 5000);
			
			String[] titleList = {"序号","表名","字段名","勾稽条件","所属信托项目编号","所属信托项目名称","项目管理员","所属部门"};
			Map<String, Integer> rowIndex = new HashMap<String, Integer>();
			for(int i = 0; i < titleList.length; i++){
				row.createCell(i).setCellValue(titleList[i]);
			}

			XSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setWrapText(true);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			
			int r = 0;
			for(String type : dataResult.keySet()){
				List<Map<String, Object>> dataList = dataResult.get(type);
				if(dataList.isEmpty()){
					continue;
				}
				
				for(Map<String, Object> data : dataList){
					row = s.createRow(r+1);
					String[] columnList = (String[]) ReflectUtlity.getConst(TableColumn.class, type);
					String[] columnListCN = (String[]) ReflectUtlity.getConst(TableColumnCN.class, type);
					String field = data.get("field").toString().toLowerCase();
					for(int x = 0; x < columnList.length; x++){
						if(columnList[x].equals(field)){
							field = columnListCN[x];
							break;
						}
					}
					
					String id = data.get("id").toString();
					String product = data.get("product").toString();
					String productName = "";
					String manager = "";
					String department = "";
					if(!"".equals(product) && productDatas.get(product) != null){
						if(rowIndex.get(product) != null){
							row = s.getRow(rowIndex.get(product)+1);
							String oldField = row.getCell(2).getStringCellValue();
							if(oldField.indexOf(field) == -1){
								String oldCrossType= row.getCell(3).getStringCellValue();
								row.getCell(2).setCellValue(oldField+";"+ field);
								row.getCell(3).setCellValue(oldCrossType+";"+ data.get("crossType").toString());
							}
						}else{
							TSsOperatorProduct op = productDatas.get(product);
							productName = op.getProductname();
							manager = op.getOpname();
							department = op.getDepartname();
							
							rowIndex.put(product, r);
							row = s.createRow(r+1);
							row.createCell(0, CellType.STRING).setCellValue((r+1)+"");
							row.createCell(1, CellType.STRING).setCellValue(data.get("tableName").toString());
							row.createCell(2, CellType.STRING).setCellValue(field);
							row.createCell(3, CellType.STRING).setCellValue(data.get("crossType").toString());
							row.createCell(4, CellType.STRING).setCellValue(product);
							row.createCell(5, CellType.STRING).setCellValue(productName);
							row.createCell(6, CellType.STRING).setCellValue(manager);
							row.createCell(7, CellType.STRING).setCellValue(department);
							
							row.getCell(0).setCellStyle(cellStyle);
							row.getCell(1).setCellStyle(cellStyle);
							row.getCell(2).setCellStyle(cellStyle);
							row.getCell(3).setCellStyle(cellStyle);
							row.getCell(4).setCellStyle(cellStyle);
							row.getCell(5).setCellStyle(cellStyle);
							row.getCell(6).setCellStyle(cellStyle);
							row.getCell(7).setCellStyle(cellStyle);
							r++;
						}
					}else{
						if(rowIndex.get(id) != null){
							row = s.getRow(rowIndex.get(id)+1);
							String oldField = row.getCell(2).getStringCellValue();
							String oldCrossType= row.getCell(3).getStringCellValue();
							row.getCell(2).setCellValue(oldField+";"+ field);
							row.getCell(3).setCellValue(oldCrossType+";"+ data.get("crossType").toString());
						}else{
							rowIndex.put(id, r);
							row = s.createRow(r+1);
							row.createCell(0, CellType.STRING).setCellValue((r+1)+"");
							row.createCell(1, CellType.STRING).setCellValue(data.get("tableName").toString());
							row.createCell(2, CellType.STRING).setCellValue(field);
							row.createCell(3, CellType.STRING).setCellValue(data.get("crossType").toString());
							row.createCell(4, CellType.STRING).setCellValue(product);
							row.createCell(5, CellType.STRING).setCellValue(productName);
							row.createCell(6, CellType.STRING).setCellValue(manager);
							row.createCell(7, CellType.STRING).setCellValue(department);
							
							row.getCell(0).setCellStyle(cellStyle);
							row.getCell(1).setCellStyle(cellStyle);
							row.getCell(2).setCellStyle(cellStyle);
							row.getCell(3).setCellStyle(cellStyle);
							row.getCell(4).setCellStyle(cellStyle);
							row.getCell(5).setCellStyle(cellStyle);
							row.getCell(6).setCellStyle(cellStyle);
							row.getCell(7).setCellStyle(cellStyle);
							r++;
						}
					}
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode("勾稽校验", "UTF-8")+".xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {  
			if(ouputStream != null){
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
			if(wb != null){
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
        } 
		
		return null;
	}
	
	/**
	 * 超管导出勾稽结果
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/adminOutput", method = RequestMethod.POST)
	@ResponseBody
	public Result adminOutput(HttpServletResponse response) {
		Session session = SecurityUtils.getSubject().getSession();
		
		if(session.getAttribute("outputList") == null){
			return ResultManager.createFailResult("页面已过期，请重新校验！");
		}
		
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) session.getAttribute("outputList");
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "勾稽结果");
			XSSFRow row = s.createRow(0);
			
			s.setColumnWidth(0, 1000);
			s.setColumnWidth(1, 5000);
			s.setColumnWidth(2, 10000);
			s.setColumnWidth(3, 20000);
			s.setColumnWidth(4, 2000);
			s.setColumnWidth(5, 10000);
			s.setColumnWidth(6, 2000);
			s.setColumnWidth(7, 5000);
			
			String[] titleList = {"序号","表名","字段名","勾稽条件","所属信托项目编号","所属信托项目名称","项目管理员","所属部门"};
			for(int i = 0; i < titleList.length; i++){
				row.createCell(i).setCellValue(titleList[i]);
			}

			XSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setWrapText(true);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			for(Map dataMap :dataList){
				Map<String, Object> data = (Map<String, Object>) dataMap;
				Integer rowNum = Integer.valueOf(data.get("row").toString());
				row = s.createRow(rowNum+2);
				
				row.createCell(0, CellType.STRING).setCellValue(rowNum+"");
				row.createCell(1, CellType.STRING).setCellValue(data.get("tableName").toString());
				row.createCell(2, CellType.STRING).setCellValue(data.get("field").toString());
				row.createCell(3, CellType.STRING).setCellValue(data.get("crossType").toString());
				row.createCell(4, CellType.STRING).setCellValue(data.get("product") == null ? "" : data.get("product").toString());
				row.createCell(5, CellType.STRING).setCellValue(data.get("productName") == null ? "" : data.get("productName").toString());
				row.createCell(6, CellType.STRING).setCellValue(data.get("manager") == null ? "" : data.get("manager").toString());
				row.createCell(7, CellType.STRING).setCellValue(data.get("department") == null ? "" : data.get("department").toString());
				
				row.getCell(0).setCellStyle(cellStyle);
				row.getCell(1).setCellStyle(cellStyle);
				row.getCell(2).setCellStyle(cellStyle);
				row.getCell(3).setCellStyle(cellStyle);
				row.getCell(4).setCellStyle(cellStyle);
				row.getCell(5).setCellStyle(cellStyle);
				row.getCell(6).setCellStyle(cellStyle);
				row.getCell(7).setCellStyle(cellStyle);
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode("勾稽校验", "UTF-8")+".xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch(Exception e){
			e.printStackTrace();
		} finally {  
			if(ouputStream != null){
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
			if(wb != null){
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
        } 
		return null;
	}
	
	/**
	 * 超管按部门统计勾稽结果
	 */
	@RequestMapping(value = "/adminDepartmentOutput", method = RequestMethod.GET)
	@ActionParam(key = "types", message="数据类型", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "dataproduct", message="信托项目", type = DataType.STRING)
	@ResponseBody
	public Result adminDepartmentOutput(String[] types, String dataproduct, HttpServletResponse response) {
		Map<String, String> inputParams = new HashMap<String, String>();
		Map<String, Integer> errorMap = new HashMap<String, Integer>();
		Map<String, Integer> totalMap = new HashMap<String, Integer>();
		Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
		
		Map<String,TSsOperatorProduct> productDatas = new HashMap<>();
		List<TSsOperatorProduct> productList= this.TSsOperatorProductService.getList(new HashMap<String, String>());
		for(TSsOperatorProduct op : productList){
			productDatas.put(op.getProductcode(), op);
		}
		
		for(String type : types){
			if("TJyQjglxxfzq".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TJyQjglxxfzq> datas = this.TJyQjglxxfzqService.getListForPage(inputParams, -1, -1, null);
				for(TJyQjglxxfzq data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyQjglxxzq".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TJyQjglxxzq> datas = this.TJyQjglxxzqService.getListForPage(inputParams, -1, -1, null);
				for(TJyQjglxxzq data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyXtsypz".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TJyXtsypz> datas = this.TJyXtsypzService.getListForPage(inputParams, -1, -1, null);
				for(TJyXtsypz data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyXtsyqzrxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TJyXtsyqzrxx> datas = this.TJyXtsyqzrxxService.getListForPage(inputParams, -1, -1, null);
				for(TJyXtsyqzrxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyXtzjmjjfpl".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TJyXtzjmjjfpl> datas = this.TJyXtzjmjjfplService.getListForPage(inputParams, -1, -1, null);
				for(TJyXtzjmjjfpl data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TJyXtzjyyjyl".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TJyXtzjyyjyl> datas = this.TJyXtzjyyjylService.getListForPage(inputParams, -1, -1, null);
				for(TJyXtzjyyjyl data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKhTzgwhtb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TKhTzgwhtb> datas = this.TKhTzgwhtbService.getListForPage(inputParams, -1, -1, null);
				for(TKhTzgwhtb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKjXtnbkmdzb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TKjXtnbkmdzb> datas = this.TKjXtnbkmdzbService.getListForPage(inputParams, -1, -1, null);
				for(TKjXtnbkmdzb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKjXtxmzcfztjb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TKjXtxmzcfztjb> datas = this.TKjXtxmzcfztjbService.getListForPage(inputParams, -1, -1, null);
				for(TKjXtxmzcfztjb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TKjXtxmzzkjqkmb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TKjXtxmzzkjqkmb> datas = this.TKjXtxmzzkjqkmbService.getListForPage(inputParams, -1, -1, null);
				for(TKjXtxmzzkjqkmb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmFdcjsxmxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmFdcjsxmxx> datas = this.TXmFdcjsxmxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmFdcjsxmxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmFfdcjsxmxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmFfdcjsxmxx> datas = this.TXmFfdcjsxmxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmFfdcjsxmxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtdbhtb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmXtdbhtb> datas = this.TXmXtdbhtbService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtdbhtb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtdzywb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmXtdzywb> datas = this.TXmXtdzywbService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtdzywb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtxmqsxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmXtxmqsxx> datas = this.TXmXtxmqsxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtxmqsxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtxmsyqb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmXtxmsyqb> datas = this.TXmXtxmsyqbService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtxmsyqb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtxmxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmXtxmxx> datas = this.TXmXtxmxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtxmxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtxmyjhklypgb".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmXtxmyjhklypgb> datas = this.TXmXtxmyjhklypgbService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtxmyjhklypgb data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtzhxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmXtzhxx> datas = this.TXmXtzhxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtzhxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtzjmjhtxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmXtzjmjhtxx> datas = this.TXmXtzjmjhtxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtzjmjhtxx data :datas){
					datasMap.get(type).add(data);
				}
			} else if("TXmXtzjyyhtxx".equals(type)){
				datasMap.put(type, new ArrayList<Object>());
				List<TXmXtzjyyhtxx> datas = this.TXmXtzjyyhtxxService.getListForPage(inputParams, -1, -1, null);
				for(TXmXtzjyyhtxx data :datas){
					datasMap.get(type).add(data);
				}
			}
		}
		try {
			for(String type : datasMap.keySet()){
				List<Object> list = datasMap.get(type);
				Class<?> cls = Class.forName("cn.zeppin.product.itic.core.entity."+type);
				if(list != null && !list.isEmpty()) {
					for(Object t : list) {
						Field[] fields = cls.getDeclaredFields();
						String product = "";
						String departname = "";
						if(ReflectUtlity.getGetMethod(cls, "xtxmbh") != null){
							product = ReflectUtlity.invokeGet(t, "xtxmbh") == null ? "" : ReflectUtlity.invokeGet(t, "xtxmbh").toString();
						}
						if(ReflectUtlity.getGetMethod(cls, "xtxmbm") != null){
							product = ReflectUtlity.invokeGet(t, "xtxmbm") == null ? "" : ReflectUtlity.invokeGet(t, "xtxmbm").toString();
						}
						if("".equals(product)){
							continue;
						}
						TSsOperatorProduct op = productDatas.get(product);
						
						if(op != null && op.getDepartname() != null){
							departname = op.getDepartname();
						}else{
							continue;
						}
						
						
						boolean flag = false;
						for(Field f : fields) {
							if(!"serialVersionUID".equals(f.getName()) && !"id".equals(f.getName()) && !"status".equals(f.getName()) &&
									!"createtime".equals(f.getName()) && !"updatetime".equals(f.getName()) && !"ctltime".equals(f.getName())){
								
								Object value = ReflectUtlity.invokeGet(t, f.getName());
								Class<?> filedType = f.getType();
								String typeName = filedType.getName();

								if(!"".equals(product)){
									if(value == null || "".equals(value)) {
										
										//20180625 修改非空校验 只校验制定列
										if(!CheckUtil.checkNullable(type+"_"+f.getName(), null)) {
											flag = true;
											break;
										}
									} else {
										
										if(!CheckUtil.checkValue(f.getName(), value.toString())) {//校验select
											flag = true;
											break;
										}
										
										if(!CheckUtil.checkDateValue(f.getName(), value.toString())) {//校验日期
											flag = true;
											break;
										}
										
										//特殊情况 特殊校验 如货币等
							            if(typeName.indexOf("BigDecimal") > -1) {//筛选BigDecimal类型的过数字校验
							        		if(CheckBigDecimalValues.bigdecimal2p.containsKey(f.getName())) {//判断两位小数
							        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 2)) {
							        				flag = true;
							        				break;
							        			}
							        		}
							        		if(CheckBigDecimalValues.bigdecimal3p.containsKey(f.getName())) {//判断三位小数
							        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 3)) {
							        				flag = true;
							        				break;
							        			}
							        		}
							        		if(CheckBigDecimalValues.bigdecimal4p.containsKey(f.getName())) {//判断四位小数
							        			if(!CheckUtil.checkDecimal(new BigDecimal(value.toString()), 4)) {
							        				flag = true;
							        				break;
							        			}
							        		}
							            }
									}
								}
							}
						}
						if(totalMap.get(departname) != null){
							totalMap.put(departname, totalMap.get(departname) + 1);
						}else{
							totalMap.put(departname, 1);
						}
						if(flag) {
							if(errorMap.get(departname) != null){
								errorMap.put(departname, errorMap.get(departname) + 1);
							}else{
								errorMap.put(departname, 1);
							}
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return ResultManager.createFailResult("勾稽校验失败，请刷新页面重试！");
		}
		
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "部门统计");
			XSSFRow row = s.createRow(0);
			
			XSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setWrapText(true);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			
			s.setColumnWidth(0, 1500);
			s.setColumnWidth(1, 10000);
			s.setColumnWidth(2, 5000);
			s.setColumnWidth(3, 5000);
			
			String[] titleList = {"序号","部门名称","上报信息总条数","勾稽未通过信息条数"};
			for(int i = 0; i < titleList.length; i++){
				row.createCell(i).setCellValue(titleList[i]);
				row.getCell(i).setCellStyle(cellStyle);
			}
			
			Integer rowNum = 1;
			for(String departname :totalMap.keySet()){
				row = s.createRow(rowNum);
				
				row.createCell(0, CellType.STRING).setCellValue(rowNum+"");
				row.createCell(1, CellType.STRING).setCellValue(departname);
				row.createCell(2, CellType.STRING).setCellValue(totalMap.get(departname).toString());
				row.createCell(3, CellType.STRING).setCellValue(errorMap.get(departname) == null? "0" : errorMap.get(departname).toString());
				
				row.getCell(0).setCellStyle(cellStyle);
				row.getCell(1).setCellStyle(cellStyle);
				row.getCell(2).setCellStyle(cellStyle);
				row.getCell(3).setCellStyle(cellStyle);
				rowNum++;
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode("部门统计", "UTF-8")+".xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch(Exception e){
			e.printStackTrace();
		} finally {  
			if(ouputStream != null){
				try {
					ouputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
			if(wb != null){
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
        } 
		return null;
	}
}
