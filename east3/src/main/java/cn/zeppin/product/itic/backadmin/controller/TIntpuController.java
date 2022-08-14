/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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

import cn.zeppin.product.itic.backadmin.service.api.ITCheckInfoService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsFileService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsSyncService;
import cn.zeppin.product.itic.backadmin.service.api.ITSubmitService;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.controller.base.WarningData;
import cn.zeppin.product.itic.core.entity.TSsFile;
import cn.zeppin.product.itic.core.entity.TSsSync;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.TableColumnCN;
import cn.zeppin.product.utility.TableValues;

/**
 * 导入采集数据
 */

@Controller
@RequestMapping(value = "/backadmin/input")
public class TIntpuController extends BaseController {
	
	@Autowired
	private ITSubmitService TSubmitService;
	
	@Autowired
	private ITCheckInfoService TCheckInfoService;
	
	@Autowired
	private ITSsSyncService TSsSyncService;
	
	@Autowired
	private ITSsFileService TSsFileService;
	
	/**
	 * 导出数据模板
	 */
	@RequestMapping(value = "/output", method = RequestMethod.GET)
	@ActionParam(key = "type", message="数据类型", type = DataType.STRING, required = true)
	@ResponseBody
	public Result output(String type, HttpServletResponse response) {
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		try {
			wb = new XSSFWorkbook();
			XSSFSheet s = wb.createSheet();
			wb.setSheetName(0, TableValues.submitTables.get(type));
			XSSFRow row = s.createRow(0);
			
			String[] columnList = (String[]) ReflectUtlity.getConst(TableColumnCN.class, type); 
			for(int i = 0; i < columnList.length-1; i++){
				row.createCell(i).setCellValue(columnList[i]);
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(TableValues.tables.get(type), "UTF-8")+".xlsx");
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
	 * 批量导入数据
	 */
	@SuppressWarnings({ "resource", "unchecked" })
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	@ActionParam(key = "file", message = "上传文件", type = DataType.STRING, required = true)
	@ResponseBody
	public Result input(String file, HttpServletRequest request) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TSsFile f = this.TSsFileService.get(file);
		if(f == null){
			return ResultManager.createFailResult("文件不存在，请重新上传");
		}
		
		try {
			String resourcePath = f.getUrl();
			String serverPath = request.getServletContext().getRealPath("/").replace("\\", "/");
			File realfile = new File(serverPath + resourcePath);
			InputStream is = null;
			boolean isE2007 = false; // 判断是否是excel2007格式
			if (resourcePath.endsWith("xlsx")) {
				isE2007 = true;
			}
			if (realfile.exists()) {
				try {
					is = new FileInputStream(realfile);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			if (is == null) {
				return ResultManager.createFailResult("文件不存在，请重新上传");
			}
			
			Workbook wb = null;
			if (isE2007) {
				wb = new XSSFWorkbook(is);
			} else {
				wb = new HSSFWorkbook(is);
			}
			
			Map<String, List<Map<String, Object>>> datasMap = new HashMap<String, List<Map<String, Object>>>();
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				Sheet s = wb.getSheetAt(i);
				String type = TableValues.inputTables.get(s.getSheetName().trim().toUpperCase());
				if(type == null){
					return ResultManager.createFailResult("名为"+ s.getSheetName() + "的表，系统中不存在！");
				}
				
				int t = s.getLastRowNum();
				Class<?> cls = Class.forName("cn.zeppin.product.itic.core.entity."+type);
				Map<String,String> coloumTypeMap = new HashMap<String,String>();
				Field[] fields = cls.getDeclaredFields();
				for(Field field : fields){
					if(!"serialVersionUID".equals(field.getName()) && !"id".equals(field.getName()) && !"status".equals(field.getName()) && !"createtime".equals(field.getName()) && !"updatetime".equals(field.getName()) && !"ctltime".equals(field.getName())){coloumTypeMap.put(field.getName(), field.getGenericType().toString());}
				}
				Map<String, Object> resultMap = getInputResult(s, type, coloumTypeMap);
				
				if(t < 3){
					return ResultManager.createFailResult("名为"+ s.getSheetName() + "的表，没有任何数据！");
				}
				
				if("different".equals(resultMap.get("result").toString())){
					return ResultManager.createFailResult("名为"+ s.getSheetName() + "的表，数据格式不匹配！");
				}
				
				Map<String, Object> totalMap = (HashMap<String, Object>)resultMap.get("totalMap");
				List<WarningData> errorList = (List<WarningData>)resultMap.get("errorList");
				List<Map<String, Object>> datasList = (List<Map<String, Object>>)resultMap.get("datasList");
				
				if(errorList.size() > 0){
					return ResultManager.createWarningResult(totalMap,errorList);
				}
				datasMap.put(type, datasList);
			}
			this.TCheckInfoService.insertAll(datasMap, operator);
			return ResultManager.createSuccessResult("导入成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("数据异常，导入失败！");
		}
	}
	
	/**
	 * 数据同步
	 */
	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	@ActionParam(key = "types", message="数据类型", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result sync(String[] types) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		Map<String,Boolean> datatypeMap = new HashMap<String,Boolean>();
		if(types == null || types.length ==0){
			return ResultManager.createFailResult("未选择任何数据类型！");
		}
		
		for(String key: types){
			datatypeMap.put(key, true);
		}
		
		Map<String,Object> dataMap = this.TCheckInfoService.sync(operator, datatypeMap);
		return ResultManager.createDataResult(dataMap);
	}
	
	/**
	 * 获取sql
	 */
	@RequestMapping(value = "/getSql", method = RequestMethod.GET)
	@ActionParam(key = "datatype", message="数据类型", type = DataType.STRING, required = true)
	@ResponseBody
	public Result getSql(String datatype) {
		TSsSync sync = this.TSsSyncService.get(datatype);
		if(sync == null){
			return ResultManager.createFailResult("数据类型有误！");
		}
		
		return ResultManager.createDataResult(sync);
	}
	
	/**
	 * 修改sql
	 */
	@RequestMapping(value = "/editSql", method = RequestMethod.POST)
	@ActionParam(key = "datatype", message="数据类型", type = DataType.STRING, required = true)
	@ActionParam(key = "value", message="SQL值", type = DataType.STRING, required = true)
	@ResponseBody
	public Result editSql(String datatype, String value) {
		TSsSync sync = this.TSsSyncService.get(datatype);
		if(sync == null){
			return ResultManager.createFailResult("数据类型有误！");
		}
		sync.setValue(getSqlStr(value));
		this.TSsSyncService.update(sync);
		
		return ResultManager.createSuccessResult("修改成功");
	}
	
	/**
	 * 获取sql
	 */
	@RequestMapping(value = "/getMidSql", method = RequestMethod.GET)
	@ActionParam(key = "datatype", message="数据类型", type = DataType.STRING, required = true)
	@ResponseBody
	public Result getMidSql(String datatype) {
		TSsSync sync = this.TSsSyncService.get("Mid"+datatype);
		if(sync == null){
			return ResultManager.createFailResult("数据类型有误！");
		}
		
		return ResultManager.createDataResult(sync);
	}
	
	/**
	 * 修改sql
	 */
	@RequestMapping(value = "/editMidSql", method = RequestMethod.POST)
	@ActionParam(key = "datatype", message="数据类型", type = DataType.STRING, required = true)
	@ActionParam(key = "value", message="SQL值", type = DataType.STRING, required = true)
	@ResponseBody
	public Result editMidSql(String datatype, String value) {
		TSsSync sync = this.TSsSyncService.get("Mid"+datatype);
		if(sync == null){
			return ResultManager.createFailResult("数据类型有误！");
		}
		sync.setValue(getSqlStr(value));
		this.TSsSyncService.update(sync);
		
		return ResultManager.createSuccessResult("修改成功");
	}
	
	/**
	 * 中间库数据同步
	 */
	@RequestMapping(value = "/middleSync", method = RequestMethod.GET)
	@ActionParam(key = "types", message="数据类型", type = DataType.STRING_ARRAY, required = true)
	@ResponseBody
	public Result middleSync(String[] types) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		Map<String,Boolean> datatypeMap = new HashMap<String,Boolean>();
		if(types == null || types.length ==0){
			return ResultManager.createFailResult("未选择任何数据类型！");
		}
		
		for(String key: types){
			datatypeMap.put(key, true);
		}
		
		Map<String,Object> dataMap = this.TCheckInfoService.middleSync(operator, datatypeMap);
		return ResultManager.createDataResult(dataMap);
	}
	
	private String getSqlStr(String value){
		String sql = value;
		sql = sql.replace("&#39;", "'");
		sql = sql.replace("&#40;", "(");
		sql = sql.replace("&#41;", ")");
		sql = sql.replace("&gt;", ">");
		sql = sql.replace("&lt;", "<");
		return sql;
	}
}
