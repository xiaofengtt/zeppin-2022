/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.jgroups.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITCheckInfoService;
import cn.zeppin.product.itic.backadmin.service.api.ITJyQjglxxzqService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsFileService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsLogService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorService;
import cn.zeppin.product.itic.backadmin.vo.TCheckInfoVO;
import cn.zeppin.product.itic.backadmin.vo.TSsLogVO;
import cn.zeppin.product.itic.backadmin.vo.TSsOperatorVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.controller.base.WarningData;
import cn.zeppin.product.itic.core.entity.TCheckInfo;
import cn.zeppin.product.itic.core.entity.TCheckInfo.TCheckInfoType;
import cn.zeppin.product.itic.core.entity.TJyQjglxxzq;
import cn.zeppin.product.itic.core.entity.TSsFile;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogStatus;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogType;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsRole.RoleId;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.TableColumn;
import cn.zeppin.product.utility.TableValues;
import cn.zeppin.product.utility.Utlity;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * TJyQjglxxzq
 */

@Controller
@RequestMapping(value = "/backadmin/TJyQjglxxzq")
public class TJyQjglxxzqController extends BaseController {

	@Autowired
	private ITJyQjglxxzqService TJyQjglxxzqService;
	
	@Autowired
	private ITCheckInfoService TCheckInfoService;

	@Autowired
	private ITSsOperatorService TSsOperatorService;
	
	@Autowired
	private ITSsLogService TSsLogService;
	
	@Autowired
	private ITSsFileService TSsFileService;
	
	private String datatype = "TJyQjglxxzq";
	
	/**
	 * 查询列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "xtxmbh", message = "信托项目编号", type = DataType.STRING)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String xtxmbh, Integer pageNum, Integer pageSize, String sorts) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		Map<String, String> productMap = (Map<String, String>) session.getAttribute("productMap");
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("xtxmbh", xtxmbh);
		if(RoleId.user.equals(operator.getRole())){inputParams.put("products", Utlity.getStringForSql(new ArrayList<String>(productMap.keySet())));}
		
		Integer count = TJyQjglxxzqService.getCount(inputParams);
		List<TJyQjglxxzq> list = TJyQjglxxzqService.getListForPage(inputParams, pageNum, pageSize, sorts);

		return ResultManager.createDataResult(list, count);
	}
	
	/**
	 * 获取记录
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result get(String id) {
		TJyQjglxxzq t = this.TJyQjglxxzqService.get(id);
		if(t != null){
			return ResultManager.createDataResult(t);
		}else{
			return ResultManager.createFailResult("数据不存在!");
		}
	}
	
	/**
	 * 获取记录
	 */
	@RequestMapping(value = "/editGet", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result editGet(String id) {
		TJyQjglxxzq t = this.TJyQjglxxzqService.get(id);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataid", id);
		List<TCheckInfo> list = this.TCheckInfoService.getListForPage(inputParams, -1, -1, null);
		if(list != null && !list.isEmpty()){
			TCheckInfo check = list.get(0);
			t = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), TJyQjglxxzq.class);
		}
		
		if(t == null){
			return ResultManager.createFailResult("数据不存在!");
		}
		
		return ResultManager.createDataResult(t);
	}

	/**
	 * 导入记录
	 */
	@SuppressWarnings({ "resource", "unchecked" })
	@RequestMapping(value = "/addInput", method = RequestMethod.POST)
	@ActionParam(key = "file", message = "上传文件", type = DataType.STRING, required = true)
	@ResponseBody
	public Result addInput(String file, HttpServletRequest request) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		Map<String,String> coloumTypeMap = new HashMap<String,String>();
		Field[] fields = TJyQjglxxzq.class.getDeclaredFields();
		for(Field field : fields){
			if(!"serialVersionUID".equals(field.getName()) && !"id".equals(field.getName()) && !"status".equals(field.getName()) && !"createtime".equals(field.getName()) && !"updatetime".equals(field.getName()) && !"ctltime".equals(field.getName())){coloumTypeMap.put(field.getName(), field.getGenericType().toString());}
		}
		
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
			Sheet s = wb.getSheetAt(0);
			
			int t = s.getLastRowNum();
			if(t < 3){
				return ResultManager.createFailResult("没有任何数据！");
			}
			
			Map<String, Object> resultMap = getInputResult(s, datatype, coloumTypeMap);
			
			if("different".equals(resultMap.get("result").toString())){
				return ResultManager.createFailResult("数据格式不匹配！");
			}
			
			Map<String, Object> totalMap = (HashMap<String, Object>)resultMap.get("totalMap");
			List<WarningData> errorList = (List<WarningData>)resultMap.get("errorList");
			List<Map<String, Object>> datasList = (List<Map<String, Object>>)resultMap.get("datasList");
			
			if(errorList.size() > 0){
				return ResultManager.createWarningResult(totalMap,errorList);
			}
			
			this.TJyQjglxxzqService.insertAll(datasList, operator);
			return ResultManager.createDataResult(totalMap);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("服务器繁忙，请稍后再试！");
		}
	}
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/addOutput", method = RequestMethod.GET)
	@ActionParam(key = "starttime", message="起始时间", type = DataType.DATE, required = true)
	@ActionParam(key = "endtime", message="截止时间", type = DataType.DATE, required = true)
	@ResponseBody
	public Result addOutput(String starttime, String endtime, HttpServletRequest request, HttpServletResponse response) {
		OutputStream ouputStream = null;
		XSSFWorkbook wb = null;
		try{
			String serverPath = request.getServletContext().getRealPath("/").replace("\\", "/");
			File file = new File(serverPath + "east3.0/excel/" + TableValues.tables.get(datatype) + ".xlsx");
			wb = new XSSFWorkbook(file);
			XSSFSheet s = wb.getSheetAt(0);
			String[] columnList = (String[]) ReflectUtlity.getConst(TableColumn.class, datatype);
			
			XSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setWrapText(true);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			
			XSSFRow row = s.getRow(0);
			row.createCell(columnList.length - 1, CellType.STRING).setCellValue("采集日期");
			row.getCell(columnList.length - 1).setCellStyle(cellStyle);
			row = s.getRow(1);
			row.createCell(columnList.length - 1, CellType.STRING).setCellValue("C8");
			row.getCell(columnList.length - 1).setCellStyle(cellStyle);
			row = s.getRow(2);
			row.createCell(columnList.length - 1, CellType.STRING).setCellValue("YYYYMMDD，默认值99991231");
			row.getCell(columnList.length - 1).setCellStyle(cellStyle);
			
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("starttime", starttime + " 00:00:00");
			inputParams.put("endtime", endtime + " 23:59:59");
			
			List<TJyQjglxxzq> dataList = this.TJyQjglxxzqService.getListForPage(inputParams, -1, -1, null);
			for(int i = 0; i<dataList.size(); i++){
				TJyQjglxxzq data = dataList.get(i);
				row = s.createRow(i+3);
				for(int x = 0; x<columnList.length; x++){
					String column = columnList[x];
					if(ReflectUtlity.invokeGet(data, column) != null){
						row.createCell(x, CellType.STRING).setCellValue(ReflectUtlity.invokeGet(data, column).toString());
					}else{
						row.createCell(x, CellType.STRING).setCellValue("");
					}
				}
			}
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(TableValues.tables.get(datatype), "UTF-8")+".xlsx");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 添加记录
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "bndxtljjzzzl", message = "本年度信托累计净值增长率", type = DataType.NUMBER)
	@ActionParam(key = "jrxkzh", message = "金融许可证号", type = DataType.STRING)
	@ActionParam(key = "ljqhzjje", message = "累计取回资金金额", type = DataType.NUMBER)
	@ActionParam(key = "ljzjzjje", message = "累计追加资金金额", type = DataType.NUMBER)
	@ActionParam(key = "xmclyljzzzl", message = "项目成立以来净值增长率", type = DataType.NUMBER)
	@ActionParam(key = "xtccdwjzjspd", message = "信托财产单位净值评估频度", type = DataType.STRING)
	@ActionParam(key = "xtccdwjzplpd", message = "信托财产单位净值披露频度", type = DataType.STRING)
	@ActionParam(key = "xtjgdm", message = "信托机构代码", type = DataType.STRING)
	@ActionParam(key = "xtjgmc", message = "信托机构名称", type = DataType.STRING)
	@ActionParam(key = "xtxmbh", message = "信托项目编号", type = DataType.STRING)
	@ActionParam(key = "xtzqzhzh", message = "信托证券专户账号", type = DataType.STRING)
	@ActionParam(key = "xtzxmbh", message = "信托子项目编号", type = DataType.STRING)
	@ActionParam(key = "zcglbgpd", message = "资产管理报告频度", type = DataType.STRING)
	@ActionParam(key = "zjqhzjje", message = "最近取回资金金额", type = DataType.NUMBER)
	@ActionParam(key = "zjqhzjrq", message = "最近取回资金日期", type = DataType.STRING)
	@ActionParam(key = "zjsdxtccdwjz", message = "最近时点信托财产单位净值", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmccbl", message = "最近时点信托项目持仓比例", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcydddccye", message = "最近时点信托项目持有多单的持仓余额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcydtbzjzy", message = "最近时点信托项目持有多头保证金占用", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcygpje", message = "最近时点信托项目持有股票市值金额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcyjjje", message = "最近时点信托项目持有基金金额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcyjkddccye", message = "最近时点信托项目持有净空单的持仓余额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcykddccye", message = "最近时点信托项目持有空单的持仓余额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcyktbzjzy", message = "最近时点信托项目持有空头保证金占用", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcyqtyjzqje", message = "最近时点信托项目持有其他有价证券金额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcyzqje", message = "最近时点信托项目持有债券金额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmqhzjzhjszbjye", message = "最近时点信托项目期货资金账号结算准备金余额", type = DataType.NUMBER)
	@ActionParam(key = "zjzcglbgrq", message = "最新资产管理报告日期", type = DataType.STRING)
	@ActionParam(key = "zjzjzjje", message = "最近追加资金金额", type = DataType.NUMBER)
	@ActionParam(key = "zjzjzjrq", message = "最近追加资金日期", type = DataType.STRING)
	@ResponseBody
	public Result add(BigDecimal bndxtljjzzzl,String jrxkzh,BigDecimal ljqhzjje,BigDecimal ljzjzjje,BigDecimal xmclyljzzzl,String xtccdwjzjspd,
			String xtccdwjzplpd,String xtjgdm,String xtjgmc,String xtxmbh,String xtzqzhzh,String xtzxmbh,String zcglbgpd,BigDecimal zjqhzjje,String zjqhzjrq,
			BigDecimal zjsdxtccdwjz,BigDecimal zjsdxtxmccbl,BigDecimal zjsdxtxmcydddccye,BigDecimal zjsdxtxmcydtbzjzy,BigDecimal zjsdxtxmcygpje,
			BigDecimal zjsdxtxmcyjjje,BigDecimal zjsdxtxmcyjkddccye,BigDecimal zjsdxtxmcykddccye,BigDecimal zjsdxtxmcyktbzjzy,BigDecimal zjsdxtxmcyqtyjzqje,
			BigDecimal zjsdxtxmcyzqje,BigDecimal zjsdxtxmqhzjzhjszbjye,String zjzcglbgrq,BigDecimal zjzjzjje,String zjzjzjrq) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TJyQjglxxzq t = new TJyQjglxxzq();
		t.setId(UUID.randomUUID().toString());
		t.setCreatetime(new Timestamp(System.currentTimeMillis()));
		t.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		t.setStatus("normal");
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("bndxtljjzzzl", bndxtljjzzzl);
		paraMap.put("jrxkzh", jrxkzh);
		paraMap.put("ljqhzjje", ljqhzjje);
		paraMap.put("ljzjzjje", ljzjzjje);
		paraMap.put("xmclyljzzzl", xmclyljzzzl);
		paraMap.put("xtccdwjzjspd", xtccdwjzjspd);
		paraMap.put("xtccdwjzplpd", xtccdwjzplpd);
		paraMap.put("xtjgdm", xtjgdm);
		paraMap.put("xtjgmc", xtjgmc);
		paraMap.put("xtxmbh", xtxmbh);
		paraMap.put("xtzqzhzh", xtzqzhzh);
		paraMap.put("xtzxmbh", xtzxmbh);
		paraMap.put("zcglbgpd", zcglbgpd);
		paraMap.put("zjqhzjje", zjqhzjje);
		paraMap.put("zjqhzjrq", zjqhzjrq);
		paraMap.put("zjsdxtccdwjz", zjsdxtccdwjz);
		paraMap.put("zjsdxtxmccbl", zjsdxtxmccbl);
		paraMap.put("zjsdxtxmcydddccye", zjsdxtxmcydddccye);
		paraMap.put("zjsdxtxmcydtbzjzy", zjsdxtxmcydtbzjzy);
		paraMap.put("zjsdxtxmcygpje", zjsdxtxmcygpje);
		paraMap.put("zjsdxtxmcyjjje", zjsdxtxmcyjjje);
		paraMap.put("zjsdxtxmcyjkddccye", zjsdxtxmcyjkddccye);
		paraMap.put("zjsdxtxmcykddccye", zjsdxtxmcykddccye);
		paraMap.put("zjsdxtxmcyktbzjzy", zjsdxtxmcyktbzjzy);
		paraMap.put("zjsdxtxmcyqtyjzqje", zjsdxtxmcyqtyjzqje);
		paraMap.put("zjsdxtxmcyzqje", zjsdxtxmcyzqje);
		paraMap.put("zjsdxtxmqhzjzhjszbjye", zjsdxtxmqhzjzhjszbjye);
		paraMap.put("zjzcglbgrq", zjzcglbgrq);
		paraMap.put("zjzjzjje", zjzjzjje);
		paraMap.put("zjzjzjrq", zjzjzjrq);
		
		paraMap = Utlity.removeEmpty(paraMap);
		if(paraMap.isEmpty()){
			return ResultManager.createFailResult("未填写任何数据!");
		}
		for(String key : paraMap.keySet()){
			ReflectUtlity.invokeSet(t, key, paraMap.get(key));
		}
		
		TSsLog log = new TSsLog();
		log.setId(UUID.randomUUID().toString());
		log.setDatatype(datatype);
		log.setDataid(t.getId());
		log.setNewdata(Utlity.StringToClob(JSONUtils.obj2json(t, SerializerFeature.WriteMapNullValue)));
		log.setType(TSsLogType.ADD);
		log.setStatus(TSsLogStatus.NORMAL);
		log.setCreatetime(new Timestamp(System.currentTimeMillis()));
		log.setCreator(operator.getId());
		this.TSsLogService.insert(log);
		
		TCheckInfo check = new TCheckInfo();
		check.setId(UUID.randomUUID().toString());
		check.setDataproduct(xtxmbh);
		check.setDatatype(datatype);
		check.setDataid(t.getId());
		check.setType(TCheckInfoType.ADD);
		check.setNewdata(log.getNewdata());
		check.setUpdatetime(new Timestamp(System.currentTimeMillis()));check.setCreator(operator.getId());
		this.TCheckInfoService.insert(check);
		
		return ResultManager.createSuccessResult("操作成功!");
	}
	
	/**
	 * 修改记录
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "id", message = "ID", required = true, type = DataType.STRING)
	@ActionParam(key = "bndxtljjzzzl", message = "本年度信托累计净值增长率", type = DataType.NUMBER)
	@ActionParam(key = "jrxkzh", message = "金融许可证号", type = DataType.STRING)
	@ActionParam(key = "ljqhzjje", message = "累计取回资金金额", type = DataType.NUMBER)
	@ActionParam(key = "ljzjzjje", message = "累计追加资金金额", type = DataType.NUMBER)
	@ActionParam(key = "xmclyljzzzl", message = "项目成立以来净值增长率", type = DataType.NUMBER)
	@ActionParam(key = "xtccdwjzjspd", message = "信托财产单位净值评估频度", type = DataType.STRING)
	@ActionParam(key = "xtccdwjzplpd", message = "信托财产单位净值披露频度", type = DataType.STRING)
	@ActionParam(key = "xtjgdm", message = "信托机构代码", type = DataType.STRING)
	@ActionParam(key = "xtjgmc", message = "信托机构名称", type = DataType.STRING)
	@ActionParam(key = "xtxmbh", message = "信托项目编号", type = DataType.STRING)
	@ActionParam(key = "xtzqzhzh", message = "信托证券专户账号", type = DataType.STRING)
	@ActionParam(key = "xtzxmbh", message = "信托子项目编号", type = DataType.STRING)
	@ActionParam(key = "zcglbgpd", message = "资产管理报告频度", type = DataType.STRING)
	@ActionParam(key = "zjqhzjje", message = "最近取回资金金额", type = DataType.NUMBER)
	@ActionParam(key = "zjqhzjrq", message = "最近取回资金日期", type = DataType.STRING)
	@ActionParam(key = "zjsdxtccdwjz", message = "最近时点信托财产单位净值", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmccbl", message = "最近时点信托项目持仓比例", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcydddccye", message = "最近时点信托项目持有多单的持仓余额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcydtbzjzy", message = "最近时点信托项目持有多头保证金占用", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcygpje", message = "最近时点信托项目持有股票市值金额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcyjjje", message = "最近时点信托项目持有基金金额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcyjkddccye", message = "最近时点信托项目持有净空单的持仓余额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcykddccye", message = "最近时点信托项目持有空单的持仓余额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcyktbzjzy", message = "最近时点信托项目持有空头保证金占用", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcyqtyjzqje", message = "最近时点信托项目持有其他有价证券金额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmcyzqje", message = "最近时点信托项目持有债券金额", type = DataType.NUMBER)
	@ActionParam(key = "zjsdxtxmqhzjzhjszbjye", message = "最近时点信托项目期货资金账号结算准备金余额", type = DataType.NUMBER)
	@ActionParam(key = "zjzcglbgrq", message = "最新资产管理报告日期", type = DataType.STRING)
	@ActionParam(key = "zjzjzjje", message = "最近追加资金金额", type = DataType.NUMBER)
	@ActionParam(key = "zjzjzjrq", message = "最近追加资金日期", type = DataType.STRING)
	@ResponseBody
	public Result edit(String id,BigDecimal bndxtljjzzzl,String jrxkzh,BigDecimal ljqhzjje,BigDecimal ljzjzjje,BigDecimal xmclyljzzzl,String xtccdwjzjspd,
			String xtccdwjzplpd,String xtjgdm,String xtjgmc,String xtxmbh,String xtzqzhzh,String xtzxmbh,String zcglbgpd,BigDecimal zjqhzjje,String zjqhzjrq,
			BigDecimal zjsdxtccdwjz,BigDecimal zjsdxtxmccbl,BigDecimal zjsdxtxmcydddccye,BigDecimal zjsdxtxmcydtbzjzy,BigDecimal zjsdxtxmcygpje,
			BigDecimal zjsdxtxmcyjjje,BigDecimal zjsdxtxmcyjkddccye,BigDecimal zjsdxtxmcykddccye,BigDecimal zjsdxtxmcyktbzjzy,BigDecimal zjsdxtxmcyqtyjzqje,
			BigDecimal zjsdxtxmcyzqje,BigDecimal zjsdxtxmqhzjzhjszbjye,String zjzcglbgrq,BigDecimal zjzjzjje,String zjzjzjrq) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TJyQjglxxzq t = this.TJyQjglxxzqService.get(id);
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("bndxtljjzzzl", bndxtljjzzzl);
		paraMap.put("jrxkzh", jrxkzh);
		paraMap.put("ljqhzjje", ljqhzjje);
		paraMap.put("ljzjzjje", ljzjzjje);
		paraMap.put("xmclyljzzzl", xmclyljzzzl);
		paraMap.put("xtccdwjzjspd", xtccdwjzjspd);
		paraMap.put("xtccdwjzplpd", xtccdwjzplpd);
		paraMap.put("xtjgdm", xtjgdm);
		paraMap.put("xtjgmc", xtjgmc);
		paraMap.put("xtxmbh", xtxmbh);
		paraMap.put("xtzqzhzh", xtzqzhzh);
		paraMap.put("xtzxmbh", xtzxmbh);
		paraMap.put("zcglbgpd", zcglbgpd);
		paraMap.put("zjqhzjje", zjqhzjje);
		paraMap.put("zjqhzjrq", zjqhzjrq);
		paraMap.put("zjsdxtccdwjz", zjsdxtccdwjz);
		paraMap.put("zjsdxtxmccbl", zjsdxtxmccbl);
		paraMap.put("zjsdxtxmcydddccye", zjsdxtxmcydddccye);
		paraMap.put("zjsdxtxmcydtbzjzy", zjsdxtxmcydtbzjzy);
		paraMap.put("zjsdxtxmcygpje", zjsdxtxmcygpje);
		paraMap.put("zjsdxtxmcyjjje", zjsdxtxmcyjjje);
		paraMap.put("zjsdxtxmcyjkddccye", zjsdxtxmcyjkddccye);
		paraMap.put("zjsdxtxmcykddccye", zjsdxtxmcykddccye);
		paraMap.put("zjsdxtxmcyktbzjzy", zjsdxtxmcyktbzjzy);
		paraMap.put("zjsdxtxmcyqtyjzqje", zjsdxtxmcyqtyjzqje);
		paraMap.put("zjsdxtxmcyzqje", zjsdxtxmcyzqje);
		paraMap.put("zjsdxtxmqhzjzhjszbjye", zjsdxtxmqhzjzhjszbjye);
		paraMap.put("zjzcglbgrq", zjzcglbgrq);
		paraMap.put("zjzjzjje", zjzjzjje);
		paraMap.put("zjzjzjrq", zjzjzjrq);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataid", id);
		List<TCheckInfo> list = this.TCheckInfoService.getListForPage(inputParams, -1, -1, null);
		if(list != null && !list.isEmpty()){
			TCheckInfo check = list.get(0);
			TJyQjglxxzq oldData = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), TJyQjglxxzq.class);

			if(t == null){
				t = oldData;
			}
			
			TJyQjglxxzq newData = (TJyQjglxxzq) t.clone();
			for(String key : paraMap.keySet()){
				ReflectUtlity.invokeSet(newData, key, paraMap.get(key));
			}
			
			TSsLog log = new TSsLog();
			log.setId(UUID.randomUUID().toString());
			log.setDatatype(datatype);
			log.setDataid(t.getId());
			log.setOlddata(Utlity.StringToClob(JSONUtils.obj2json(oldData)));
			log.setNewdata(Utlity.StringToClob(JSONUtils.obj2json(newData)));
			log.setType(TSsLogType.EDIT);
			log.setCreatetime(new Timestamp(System.currentTimeMillis()));
			log.setCreator(operator.getId());
			log.setStatus(TSsLogStatus.NORMAL);
			this.TSsLogService.insert(log);
			
			check.setNewdata(Utlity.StringToClob(JSONUtils.obj2json(newData)));
			check.setUpdatetime(new Timestamp(System.currentTimeMillis()));check.setCreator(operator.getId());
			this.TCheckInfoService.update(check);
		}else{
			if(t == null){
				return ResultManager.createFailResult("数据不存在");
			}
			
			TJyQjglxxzq newData = (TJyQjglxxzq) t.clone();
			for(String key : paraMap.keySet()){
				ReflectUtlity.invokeSet(newData, key, paraMap.get(key));
			}
			
			TSsLog log = new TSsLog();
			log.setId(UUID.randomUUID().toString());
			log.setDatatype(datatype);
			log.setDataid(t.getId());
			log.setOlddata(Utlity.StringToClob(JSONUtils.obj2json(t)));
			log.setNewdata(Utlity.StringToClob(JSONUtils.obj2json(newData)));
			log.setType(TSsLogType.EDIT);
			log.setCreatetime(new Timestamp(System.currentTimeMillis()));
			log.setCreator(operator.getId());
			log.setStatus(TSsLogStatus.NORMAL);
			this.TSsLogService.insert(log);
			
			TCheckInfo check = new TCheckInfo();
			check.setId(UUID.randomUUID().toString());
			check.setDataproduct(xtxmbh);
			check.setDatatype(datatype);
			check.setDataid(t.getId());
			check.setType(TCheckInfoType.EDIT);
			check.setNewdata(log.getNewdata());
			check.setUpdatetime(new Timestamp(System.currentTimeMillis()));check.setCreator(operator.getId());
			this.TCheckInfoService.insert(check);
		}
		
		return ResultManager.createSuccessResult("操作成功!");
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "ID", type = DataType.STRING)
	@ResponseBody
	public Result delete(String id) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TJyQjglxxzq t = this.TJyQjglxxzqService.get(id);
		if(t==null){
			return ResultManager.createFailResult("数据不存在");
		}
		
		this.TJyQjglxxzqService.deleteWholeData(t, operator);
		return ResultManager.createSuccessResult("操作成功!");
	}
	
	/**
	 * 查询列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkList", method = RequestMethod.GET)
	@ActionParam(key = "dataproduct", message = "信托项目编号", type = DataType.STRING)
	@ActionParam(key = "status", message = "审核状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result checkList(String dataproduct, String status,Integer pageNum, Integer pageSize, String sorts) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		Map<String, String> productMap = (Map<String, String>) session.getAttribute("productMap");
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("dataproduct", dataproduct);
		if(RoleId.user.equals(operator.getRole())){inputParams.put("products", Utlity.getStringForSql(new ArrayList<String>(productMap.keySet())));}
		inputParams.put("datatype", datatype);
		
		Integer count = TCheckInfoService.getCount(inputParams);
		List<TCheckInfo> list = TCheckInfoService.getListForPage(inputParams, pageNum, pageSize, sorts);
		
		List<TCheckInfoVO> voList = new ArrayList<TCheckInfoVO>();
		for(TCheckInfo check : list){
			TCheckInfoVO vo = new TCheckInfoVO(check);

			TSsOperator creator = this.TSsOperatorService.get(check.getCreator());
			if(creator != null){
				vo.setCreatorName(creator.getName());
			}
			
			TJyQjglxxzq t = this.TJyQjglxxzqService.get(check.getDataid());
			if(t != null){
				vo.setJrxkzh(t.getJrxkzh());
				vo.setCjrq(t.getCjrq());
			} else {
				String jrxkzh = "";
				String cjrq = "";
				if(vo.getNewdata() != null) {
					jrxkzh = vo.getNewdata().get("jrxkzh") == null ? "" : vo.getNewdata().get("jrxkzh").toString();
					cjrq = vo.getNewdata().get("cjrq") == null ? "" : vo.getNewdata().get("cjrq").toString();
				}
				vo.setJrxkzh(jrxkzh);
				vo.setCjrq(cjrq);
			}
			voList.add(vo);
		}
		return ResultManager.createDataResult(voList, count);
	}
	
	/**
	 * 获取记录
	 */
	@RequestMapping(value = "/checkGet", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result checkGet(String id) {
		TCheckInfo t = this.TCheckInfoService.get(id);
		if(t != null && datatype.equals(t.getDatatype())){
			TCheckInfoVO vo = new TCheckInfoVO(t);
			return ResultManager.createDataResult(vo);
		}else{
			return ResultManager.createFailResult("数据不存在!");
		}
	}
	
	/**
	 * 审核
	 */
	@RequestMapping(value = "/checkCheck", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING_ARRAY)
	@ActionParam(key = "status", message = "审核状态", required = true, type = DataType.STRING)
	@ResponseBody
	public Result checkCheck(String[] id, String status) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		if(!"checked".equals(status) && !"nopass".equals(status)){
			return ResultManager.createFailResult("审核状态错误");
		}
		List<TCheckInfo> checkList = new ArrayList<TCheckInfo>();
		for(String aid: id){
			TCheckInfo t = this.TCheckInfoService.get(aid);
			if(t == null || !datatype.equals(t.getDatatype())){
				return ResultManager.createFailResult("数据不存在!");
			}
			checkList.add(t);
		}
		this.TCheckInfoService.check(checkList, status, operator);
		return ResultManager.createSuccessResult("操作成功!");
	}

	/**
	 * 获取回滚记录
	 */
	@RequestMapping(value = "/checkRollbackGet", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result checkRollbackGet(String id) {
		TSsLog t = this.TSsLogService.get(id);
		if(t != null && datatype.equals(t.getDatatype())){
			TSsLogVO vo = new TSsLogVO(t);
			return ResultManager.createDataResult(vo);
		}else{
			return ResultManager.createFailResult("数据不存在");
		}
	}
	
	/**
	 * 取消审核（回滚）
	 */
	@RequestMapping(value = "/checkRollback", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result checkRollback(String id) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TSsLog t = this.TSsLogService.get(id);
		if(t == null || !datatype.equals(t.getDatatype())){
			return ResultManager.createFailResult("数据不存在!");
		}
		
		if(!TSsLogType.CHECK.equals(t.getType())){
			return ResultManager.createFailResult("该记录不是审核记录!");
		}
		this.TSsLogService.rollback(t, operator);
		
		return ResultManager.createSuccessResult("操作成功!");
	}
} 
