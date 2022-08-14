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
import cn.zeppin.product.itic.backadmin.service.api.ITKhJydsjgService;
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
import cn.zeppin.product.itic.core.entity.TKhJydsjg;
import cn.zeppin.product.itic.core.entity.TSsFile;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogStatus;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogType;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.TableColumn;
import cn.zeppin.product.utility.TableValues;
import cn.zeppin.product.utility.Utlity;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * TKhJydsjg
 */

@Controller
@RequestMapping(value = "/backadmin/TKhJydsjg")
public class TKhJydsjgController extends BaseController {

	@Autowired
	private ITKhJydsjgService TKhJydsjgService;
	
	@Autowired
	private ITCheckInfoService TCheckInfoService;

	@Autowired
	private ITSsOperatorService TSsOperatorService;
	
	@Autowired
	private ITSsLogService TSsLogService;
	
	@Autowired
	private ITSsFileService TSsFileService;
	
	private String datatype = "TKhJydsjg";
	
	/**
	 * 查询列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(Integer pageNum, Integer pageSize, String sorts) {
		Map<String, String> inputParams = new HashMap<String, String>();
		
		Integer count = TKhJydsjgService.getCount(inputParams);
		List<TKhJydsjg> list = TKhJydsjgService.getListForPage(inputParams, pageNum, pageSize, sorts);

		return ResultManager.createDataResult(list, count);
	}
	
	/**
	 * 获取记录
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result get(String id) {
		TKhJydsjg t = this.TKhJydsjgService.get(id);
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
		TKhJydsjg t = this.TKhJydsjgService.get(id);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataid", id);
		List<TCheckInfo> list = this.TCheckInfoService.getListForPage(inputParams, -1, -1, null);
		if(list != null && !list.isEmpty()){
			TCheckInfo check = list.get(0);
			t = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), TKhJydsjg.class);
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
		Field[] fields = TKhJydsjg.class.getDeclaredFields();
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
			
			this.TKhJydsjgService.insertAll(datasList, operator);
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
			
			List<TKhJydsjg> dataList = this.TKhJydsjgService.getListForPage(inputParams, -1, -1, null);
			for(int i = 0; i<dataList.size(); i++){
				TKhJydsjg data = dataList.get(i);
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
	@ActionParam(key = "czdh", message = "传真电话", type = DataType.STRING)
	@ActionParam(key = "djdqrq", message = "登记到期日期", type = DataType.STRING)
	@ActionParam(key = "dsbh", message = "交易对手编号", type = DataType.STRING)
	@ActionParam(key = "fddbr", message = "法定代表人", type = DataType.STRING)
	@ActionParam(key = "fddbrzjhm", message = "法定代表人证件号码", type = DataType.STRING)
	@ActionParam(key = "fddbrzjlx", message = "法定代表人证件类型", type = DataType.STRING)
	@ActionParam(key = "gllx", message = "关联类型", type = DataType.STRING)
	@ActionParam(key = "hyfl", message = "行业分类", type = DataType.STRING)
	@ActionParam(key = "hymx", message = "行业明细", type = DataType.STRING)
	@ActionParam(key = "jjcf", message = "经济成份", type = DataType.STRING)
	@ActionParam(key = "jrxkzh", message = "金融许可证号", type = DataType.STRING)
	@ActionParam(key = "jtmc", message = "集团名称", type = DataType.STRING)
	@ActionParam(key = "jydsqc", message = "交易对手全称", type = DataType.STRING)
	@ActionParam(key = "ptbz", message = "平台标志", type = DataType.STRING)
	@ActionParam(key = "ptzfjb", message = "平台政府级别", type = DataType.STRING)
	@ActionParam(key = "ptzfmc", message = "平台政府名称", type = DataType.STRING)
	@ActionParam(key = "qyclrq", message = "企业成立日期", type = DataType.STRING)
	@ActionParam(key = "qygm", message = "企业规模", type = DataType.STRING)
	@ActionParam(key = "qyxz", message = "企业性质", type = DataType.STRING)
	@ActionParam(key = "qyzfz", message = "企业总负债", type = DataType.NUMBER)
	@ActionParam(key = "qyzzc", message = "企业总资产", type = DataType.NUMBER)
	@ActionParam(key = "sfjt", message = "是否集团", type = DataType.STRING)
	@ActionParam(key = "shxydm", message = "交易对手社会信用代码", type = DataType.STRING)
	@ActionParam(key = "snbz", message = "三农标志", type = DataType.STRING)
	@ActionParam(key = "ssbz", message = "上市标志", type = DataType.STRING)
	@ActionParam(key = "ssd", message = "上市地", type = DataType.STRING)
	@ActionParam(key = "ssdq", message = "所属地区", type = DataType.STRING)
	@ActionParam(key = "ssgb", message = "所属国家", type = DataType.STRING)
	@ActionParam(key = "txdz", message = "通讯地址", type = DataType.STRING)
	@ActionParam(key = "xhlb", message = "客户类别", type = DataType.STRING)
	@ActionParam(key = "xtjgdm", message = "信托机构代码", type = DataType.STRING)
	@ActionParam(key = "xtjgmc", message = "信托机构名称", type = DataType.STRING)
	@ActionParam(key = "yzbm", message = "邮政编码", type = DataType.STRING)
	@ActionParam(key = "zcdz", message = "注册地址", type = DataType.STRING)
	@ActionParam(key = "zclb", message = "注册类别", type = DataType.STRING)
	@ActionParam(key = "zczb", message = "注册资本", type = DataType.NUMBER)
	@ActionParam(key = "zjhm", message = "证件号码", type = DataType.STRING)
	@ActionParam(key = "zjlx", message = "证件类型", type = DataType.STRING)
	@ResponseBody
	public Result add(String czdh,String djdqrq,String dsbh,String fddbr,String fddbrzjhm,String fddbrzjlx,String gllx,String hyfl,
			String hymx,String jjcf,String jrxkzh,String jtmc,String jydsqc,String ptbz,String ptzfjb,String ptzfmc,String qyclrq,String qygm,
			String qyxz,BigDecimal qyzfz,BigDecimal qyzzc,String sfjt,String shxydm,String snbz,String ssbz,String ssd,String ssdq,String ssgb,
			String txdz,String xhlb,String xtjgdm,String xtjgmc,String yzbm,String zcdz,String zclb,BigDecimal zczb,String zjhm,String zjlx) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TKhJydsjg t = new TKhJydsjg();
		t.setId(UUID.randomUUID().toString());
		t.setCreatetime(new Timestamp(System.currentTimeMillis()));
		t.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		t.setStatus("normal");
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("czdh", czdh);
		paraMap.put("djdqrq", djdqrq);
		paraMap.put("dsbh", dsbh);
		paraMap.put("fddbr", fddbr);
		paraMap.put("fddbrzjhm", fddbrzjhm);
		paraMap.put("fddbrzjlx", fddbrzjlx);
		paraMap.put("gllx", gllx);
		paraMap.put("hyfl", hyfl);
		paraMap.put("hymx", hymx);
		paraMap.put("jjcf", jjcf);
		paraMap.put("jrxkzh", jrxkzh);
		paraMap.put("jtmc", jtmc);
		paraMap.put("jydsqc", jydsqc);
		paraMap.put("ptbz", ptbz);
		paraMap.put("ptzfjb", ptzfjb);
		paraMap.put("ptzfmc", ptzfmc);
		paraMap.put("qyclrq", qyclrq);
		paraMap.put("qygm", qygm);
		paraMap.put("qyxz", qyxz);
		paraMap.put("qyzfz", qyzfz);
		paraMap.put("qyzzc", qyzzc);
		paraMap.put("sfjt", sfjt);
		paraMap.put("shxydm", shxydm);
		paraMap.put("snbz", snbz);
		paraMap.put("ssbz", ssbz);
		paraMap.put("ssd", ssd);
		paraMap.put("ssdq", ssdq);
		paraMap.put("ssgb", ssgb);
		paraMap.put("txdz", txdz);
		paraMap.put("xhlb", xhlb);
		paraMap.put("xtjgdm", xtjgdm);
		paraMap.put("xtjgmc", xtjgmc);
		paraMap.put("yzbm", yzbm);
		paraMap.put("zcdz", zcdz);
		paraMap.put("zclb", zclb);
		paraMap.put("zczb", zczb);
		paraMap.put("zjhm", zjhm);
		paraMap.put("zjlx", zjlx);
		
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
	@ActionParam(key = "czdh", message = "传真电话", type = DataType.STRING)
	@ActionParam(key = "djdqrq", message = "登记到期日期", type = DataType.STRING)
	@ActionParam(key = "dsbh", message = "交易对手编号", type = DataType.STRING)
	@ActionParam(key = "fddbr", message = "法定代表人", type = DataType.STRING)
	@ActionParam(key = "fddbrzjhm", message = "法定代表人证件号码", type = DataType.STRING)
	@ActionParam(key = "fddbrzjlx", message = "法定代表人证件类型", type = DataType.STRING)
	@ActionParam(key = "gllx", message = "关联类型", type = DataType.STRING)
	@ActionParam(key = "hyfl", message = "行业分类", type = DataType.STRING)
	@ActionParam(key = "hymx", message = "行业明细", type = DataType.STRING)
	@ActionParam(key = "jjcf", message = "经济成份", type = DataType.STRING)
	@ActionParam(key = "jrxkzh", message = "金融许可证号", type = DataType.STRING)
	@ActionParam(key = "jtmc", message = "集团名称", type = DataType.STRING)
	@ActionParam(key = "jydsqc", message = "交易对手全称", type = DataType.STRING)
	@ActionParam(key = "ptbz", message = "平台标志", type = DataType.STRING)
	@ActionParam(key = "ptzfjb", message = "平台政府级别", type = DataType.STRING)
	@ActionParam(key = "ptzfmc", message = "平台政府名称", type = DataType.STRING)
	@ActionParam(key = "qyclrq", message = "企业成立日期", type = DataType.STRING)
	@ActionParam(key = "qygm", message = "企业规模", type = DataType.STRING)
	@ActionParam(key = "qyxz", message = "企业性质", type = DataType.STRING)
	@ActionParam(key = "qyzfz", message = "企业总负债", type = DataType.NUMBER)
	@ActionParam(key = "qyzzc", message = "企业总资产", type = DataType.NUMBER)
	@ActionParam(key = "sfjt", message = "是否集团", type = DataType.STRING)
	@ActionParam(key = "shxydm", message = "交易对手社会信用代码", type = DataType.STRING)
	@ActionParam(key = "snbz", message = "三农标志", type = DataType.STRING)
	@ActionParam(key = "ssbz", message = "上市标志", type = DataType.STRING)
	@ActionParam(key = "ssd", message = "上市地", type = DataType.STRING)
	@ActionParam(key = "ssdq", message = "所属地区", type = DataType.STRING)
	@ActionParam(key = "ssgb", message = "所属国家", type = DataType.STRING)
	@ActionParam(key = "txdz", message = "通讯地址", type = DataType.STRING)
	@ActionParam(key = "xhlb", message = "客户类别", type = DataType.STRING)
	@ActionParam(key = "xtjgdm", message = "信托机构代码", type = DataType.STRING)
	@ActionParam(key = "xtjgmc", message = "信托机构名称", type = DataType.STRING)
	@ActionParam(key = "yzbm", message = "邮政编码", type = DataType.STRING)
	@ActionParam(key = "zcdz", message = "注册地址", type = DataType.STRING)
	@ActionParam(key = "zclb", message = "注册类别", type = DataType.STRING)
	@ActionParam(key = "zczb", message = "注册资本", type = DataType.NUMBER)
	@ActionParam(key = "zjhm", message = "证件号码", type = DataType.STRING)
	@ActionParam(key = "zjlx", message = "证件类型", type = DataType.STRING)
	@ResponseBody
	public Result edit(String id,String czdh,String djdqrq,String dsbh,String fddbr,String fddbrzjhm,String fddbrzjlx,String gllx,
			String hyfl,String hymx,String jjcf,String jrxkzh,String jtmc,String jydsqc,String ptbz,String ptzfjb,String ptzfmc,String qyclrq,
			String qygm,String qyxz,BigDecimal qyzfz,BigDecimal qyzzc,String sfjt,String shxydm,String snbz,String ssbz,String ssd,String ssdq,
			String ssgb,String txdz,String xhlb,String xtjgdm,String xtjgmc,String yzbm,String zcdz,String zclb,BigDecimal zczb,String zjhm,
			String zjlx) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TKhJydsjg t = this.TKhJydsjgService.get(id);
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("czdh", czdh);
		paraMap.put("djdqrq", djdqrq);
		paraMap.put("dsbh", dsbh);
		paraMap.put("fddbr", fddbr);
		paraMap.put("fddbrzjhm", fddbrzjhm);
		paraMap.put("fddbrzjlx", fddbrzjlx);
		paraMap.put("gllx", gllx);
		paraMap.put("hyfl", hyfl);
		paraMap.put("hymx", hymx);
		paraMap.put("jjcf", jjcf);
		paraMap.put("jrxkzh", jrxkzh);
		paraMap.put("jtmc", jtmc);
		paraMap.put("jydsqc", jydsqc);
		paraMap.put("ptbz", ptbz);
		paraMap.put("ptzfjb", ptzfjb);
		paraMap.put("ptzfmc", ptzfmc);
		paraMap.put("qyclrq", qyclrq);
		paraMap.put("qygm", qygm);
		paraMap.put("qyxz", qyxz);
		paraMap.put("qyzfz", qyzfz);
		paraMap.put("qyzzc", qyzzc);
		paraMap.put("sfjt", sfjt);
		paraMap.put("shxydm", shxydm);
		paraMap.put("snbz", snbz);
		paraMap.put("ssbz", ssbz);
		paraMap.put("ssd", ssd);
		paraMap.put("ssdq", ssdq);
		paraMap.put("ssgb", ssgb);
		paraMap.put("txdz", txdz);
		paraMap.put("xhlb", xhlb);
		paraMap.put("xtjgdm", xtjgdm);
		paraMap.put("xtjgmc", xtjgmc);
		paraMap.put("yzbm", yzbm);
		paraMap.put("zcdz", zcdz);
		paraMap.put("zclb", zclb);
		paraMap.put("zczb", zczb);
		paraMap.put("zjhm", zjhm);
		paraMap.put("zjlx", zjlx);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataid", id);
		List<TCheckInfo> list = this.TCheckInfoService.getListForPage(inputParams, -1, -1, null);
		if(list != null && !list.isEmpty()){
			TCheckInfo check = list.get(0);
			TKhJydsjg oldData = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), TKhJydsjg.class);

			if(t == null){
				t = oldData;
			}
			
			TKhJydsjg newData = (TKhJydsjg) t.clone();
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
			
			TKhJydsjg newData = (TKhJydsjg) t.clone();
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
		
		TKhJydsjg t = this.TKhJydsjgService.get(id);
		if(t==null){
			return ResultManager.createFailResult("数据不存在");
		}
		
		this.TKhJydsjgService.deleteWholeData(t, operator);
		return ResultManager.createSuccessResult("操作成功!");
	}
	
	/**
	 * 查询列表
	 */
	@RequestMapping(value = "/checkList", method = RequestMethod.GET)
	@ActionParam(key = "status", message = "审核状态", type = DataType.STRING)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result checkList(String status,Integer pageNum, Integer pageSize, String sorts) {
		Map<String, String> inputParams = new HashMap<String, String>();
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
			
			TKhJydsjg t = this.TKhJydsjgService.get(check.getDataid());
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
