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
import cn.zeppin.product.itic.backadmin.service.api.ITGyGydzywbService;
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
import cn.zeppin.product.itic.core.entity.TGyGydzywb;
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
 * TGyGydzywb
 */

@Controller
@RequestMapping(value = "/backadmin/TGyGydzywb")
public class TGyGydzywbController extends BaseController {

	@Autowired
	private ITGyGydzywbService TGyGydzywbService;
	
	@Autowired
	private ITCheckInfoService TCheckInfoService;

	@Autowired
	private ITSsOperatorService TSsOperatorService;
	
	@Autowired
	private ITSsLogService TSsLogService;
	
	@Autowired
	private ITSsFileService TSsFileService;
	
	private String datatype = "TGyGydzywb";
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message = "??????", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "????????????", required = true, type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "????????????", type = DataType.STRING)
	@ResponseBody
	public Result list(Integer pageNum, Integer pageSize, String sorts) {
		Map<String, String> inputParams = new HashMap<String, String>();
		
		Integer count = TGyGydzywbService.getCount(inputParams);
		List<TGyGydzywb> list = TGyGydzywbService.getListForPage(inputParams, pageNum, pageSize, sorts);

		return ResultManager.createDataResult(list, count);
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result get(String id) {
		TGyGydzywb t = this.TGyGydzywbService.get(id);
		if(t != null){
			return ResultManager.createDataResult(t);
		}else{
			return ResultManager.createFailResult("???????????????!");
		}
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/editGet", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result editGet(String id) {
		TGyGydzywb t = this.TGyGydzywbService.get(id);
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataid", id);
		List<TCheckInfo> list = this.TCheckInfoService.getListForPage(inputParams, -1, -1, null);
		if(list != null && !list.isEmpty()){
			TCheckInfo check = list.get(0);
			t = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), TGyGydzywb.class);
		}
		
		if(t == null){
			return ResultManager.createFailResult("???????????????!");
		}
		
		return ResultManager.createDataResult(t);
	}

	/**
	 * ????????????
	 */
	@SuppressWarnings({ "resource", "unchecked" })
	@RequestMapping(value = "/addInput", method = RequestMethod.POST)
	@ActionParam(key = "file", message = "????????????", type = DataType.STRING, required = true)
	@ResponseBody
	public Result addInput(String file, HttpServletRequest request) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		Map<String,String> coloumTypeMap = new HashMap<String,String>();
		Field[] fields = TGyGydzywb.class.getDeclaredFields();
		for(Field field : fields){
			if(!"serialVersionUID".equals(field.getName()) && !"id".equals(field.getName()) && !"status".equals(field.getName()) && !"createtime".equals(field.getName()) && !"updatetime".equals(field.getName()) && !"ctltime".equals(field.getName())){coloumTypeMap.put(field.getName(), field.getGenericType().toString());}
		}
		
		TSsFile f = this.TSsFileService.get(file);
		if(f == null){
			return ResultManager.createFailResult("?????????????????????????????????");
		}
		try {
			String resourcePath = f.getUrl();
			String serverPath = request.getServletContext().getRealPath("/").replace("\\", "/");
			File realfile = new File(serverPath + resourcePath);
			InputStream is = null;
			boolean isE2007 = false; // ???????????????excel2007??????
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
				return ResultManager.createFailResult("?????????????????????????????????");
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
				return ResultManager.createFailResult("?????????????????????");
			}
			
			Map<String, Object> resultMap = getInputResult(s, datatype, coloumTypeMap);
			
			if("different".equals(resultMap.get("result").toString())){
				return ResultManager.createFailResult("????????????????????????");
			}
			
			Map<String, Object> totalMap = (HashMap<String, Object>)resultMap.get("totalMap");
			List<WarningData> errorList = (List<WarningData>)resultMap.get("errorList");
			List<Map<String, Object>> datasList = (List<Map<String, Object>>)resultMap.get("datasList");
			
			if(errorList.size() > 0){
				return ResultManager.createWarningResult(totalMap,errorList);
			}
			
			this.TGyGydzywbService.insertAll(datasList, operator);
			return ResultManager.createDataResult(totalMap);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultManager.createFailResult("????????????????????????????????????");
		}
	}
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/addOutput", method = RequestMethod.GET)
	@ActionParam(key = "starttime", message="????????????", type = DataType.DATE, required = true)
	@ActionParam(key = "endtime", message="????????????", type = DataType.DATE, required = true)
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
			row.createCell(columnList.length - 1, CellType.STRING).setCellValue("????????????");
			row.getCell(columnList.length - 1).setCellStyle(cellStyle);
			row = s.getRow(1);
			row.createCell(columnList.length - 1, CellType.STRING).setCellValue("C8");
			row.getCell(columnList.length - 1).setCellStyle(cellStyle);
			row = s.getRow(2);
			row.createCell(columnList.length - 1, CellType.STRING).setCellValue("YYYYMMDD????????????99991231");
			row.getCell(columnList.length - 1).setCellStyle(cellStyle);
			
			Map<String, String> inputParams = new HashMap<String, String>();
			inputParams.put("starttime", starttime + " 00:00:00");
			inputParams.put("endtime", endtime + " 23:59:59");
			
			List<TGyGydzywb> dataList = this.TGyGydzywbService.getListForPage(inputParams, -1, -1, null);
			for(int i = 0; i<dataList.size(); i++){
				TGyGydzywb data = dataList.get(i);
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
	 * ????????????
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ActionParam(key = "bwhsksrq", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "bxdh", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "bz", message = "??????", type = DataType.STRING)
	@ActionParam(key = "czjg", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "dbdqrq", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "dbhth", message = "???????????????", type = DataType.STRING)
	@ActionParam(key = "dbqsrq", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "djjg", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "djrq", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "djyxzzrq", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "dywsyqrmc", message = "???????????????????????????", type = DataType.STRING)
	@ActionParam(key = "hbrq", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "jrxkzh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "pgjgmc", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "pgjz", message = "????????????", type = DataType.NUMBER)
	@ActionParam(key = "pgrq", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "qzdjhm", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "qzmc", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "qzyxdqrq", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "sfnrbwhs", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "swsqrq", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "xtjgdm", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "xtjgmc", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "ydyjz", message = "???????????????", type = DataType.NUMBER)
	@ActionParam(key = "yxrdjz", message = "????????????????????????", type = DataType.NUMBER)
	@ActionParam(key = "zhdyl", message = "???????????????", type = DataType.NUMBER)
	@ActionParam(key = "zhdywbh", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "zhdywlx", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "zhdywmc", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "zhdywzmjz", message = "???????????????????????????", type = DataType.NUMBER)
	@ActionParam(key = "zypzhm", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "zypzje", message = "??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "zypzklrq", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "zypzlx", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "zypzqfjg", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "zypzzh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "zywbgfs", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "zywpgfs", message = "?????????????????????", type = DataType.STRING)
	@ResponseBody
	public Result add(String bwhsksrq,String bxdh,String bz,String czjg,String dbdqrq,String dbhth,String dbqsrq,String djjg,String djrq,
			String djyxzzrq,String dywsyqrmc,String hbrq,String jrxkzh,String pgjgmc,BigDecimal pgjz,String pgrq,String qzdjhm,String qzmc,String qzyxdqrq,
			String sfnrbwhs,String swsqrq,String xtjgdm,String xtjgmc,BigDecimal ydyjz,BigDecimal yxrdjz,BigDecimal zhdyl,String zhdywbh,String zhdywlx,
			String zhdywmc,BigDecimal zhdywzmjz,String zypzhm,BigDecimal zypzje,String zypzklrq,String zypzlx,String zypzqfjg,String zypzzh,String zywbgfs,
			String zywpgfs) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TGyGydzywb t = new TGyGydzywb();
		t.setId(UUID.randomUUID().toString());
		t.setCreatetime(new Timestamp(System.currentTimeMillis()));
		t.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		t.setStatus("normal");
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("bwhsksrq", bwhsksrq);
		paraMap.put("bxdh", bxdh);
		paraMap.put("bz", bz);
		paraMap.put("czjg", czjg);
		paraMap.put("dbdqrq", dbdqrq);
		paraMap.put("dbhth", dbhth);
		paraMap.put("dbqsrq", dbqsrq);
		paraMap.put("djjg", djjg);
		paraMap.put("djrq", djrq);
		paraMap.put("djyxzzrq", djyxzzrq);
		paraMap.put("dywsyqrmc", dywsyqrmc);
		paraMap.put("hbrq", hbrq);
		paraMap.put("jrxkzh", jrxkzh);
		paraMap.put("pgjgmc", pgjgmc);
		paraMap.put("pgjz", pgjz);
		paraMap.put("pgrq", pgrq);
		paraMap.put("qzdjhm", qzdjhm);
		paraMap.put("qzmc", qzmc);
		paraMap.put("qzyxdqrq", qzyxdqrq);
		paraMap.put("sfnrbwhs", sfnrbwhs);
		paraMap.put("swsqrq", swsqrq);
		paraMap.put("xtjgdm", xtjgdm);
		paraMap.put("xtjgmc", xtjgmc);
		paraMap.put("ydyjz", ydyjz);
		paraMap.put("yxrdjz", yxrdjz);
		paraMap.put("zhdyl", zhdyl);
		paraMap.put("zhdywbh", zhdywbh);
		paraMap.put("zhdywlx", zhdywlx);
		paraMap.put("zhdywmc", zhdywmc);
		paraMap.put("zhdywzmjz", zhdywzmjz);
		paraMap.put("zypzhm", zypzhm);
		paraMap.put("zypzje", zypzje);
		paraMap.put("zypzklrq", zypzklrq);
		paraMap.put("zypzlx", zypzlx);
		paraMap.put("zypzqfjg", zypzqfjg);
		paraMap.put("zypzzh", zypzzh);
		paraMap.put("zywbgfs", zywbgfs);
		paraMap.put("zywpgfs", zywpgfs);
	
		
		paraMap = Utlity.removeEmpty(paraMap);
		if(paraMap.isEmpty()){
			return ResultManager.createFailResult("?????????????????????!");
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
		
		return ResultManager.createSuccessResult("????????????!");
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ActionParam(key = "id", message = "ID", required = true, type = DataType.STRING)
	@ActionParam(key = "bwhsksrq", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "bxdh", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "bz", message = "??????", type = DataType.STRING)
	@ActionParam(key = "czjg", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "dbdqrq", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "dbhth", message = "???????????????", type = DataType.STRING)
	@ActionParam(key = "dbqsrq", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "djjg", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "djrq", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "djyxzzrq", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "dywsyqrmc", message = "???????????????????????????", type = DataType.STRING)
	@ActionParam(key = "hbrq", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "jrxkzh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "pgjgmc", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "pgjz", message = "????????????", type = DataType.NUMBER)
	@ActionParam(key = "pgrq", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "qzdjhm", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "qzmc", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "qzyxdqrq", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "sfnrbwhs", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "swsqrq", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "xtjgdm", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "xtjgmc", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "ydyjz", message = "???????????????", type = DataType.NUMBER)
	@ActionParam(key = "yxrdjz", message = "????????????????????????", type = DataType.NUMBER)
	@ActionParam(key = "zhdyl", message = "???????????????", type = DataType.NUMBER)
	@ActionParam(key = "zhdywbh", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "zhdywlx", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "zhdywmc", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "zhdywzmjz", message = "???????????????????????????", type = DataType.NUMBER)
	@ActionParam(key = "zypzhm", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "zypzje", message = "??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "zypzklrq", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "zypzlx", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "zypzqfjg", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "zypzzh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "zywbgfs", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "zywpgfs", message = "?????????????????????", type = DataType.STRING)
	@ResponseBody
	public Result edit(String id,String bwhsksrq,String bxdh,String bz,String czjg,String dbdqrq,String dbhth,String dbqsrq,String djjg,String djrq,
			String djyxzzrq,String dywsyqrmc,String hbrq,String jrxkzh,String pgjgmc,BigDecimal pgjz,String pgrq,String qzdjhm,String qzmc,String qzyxdqrq,
			String sfnrbwhs,String swsqrq,String xtjgdm,String xtjgmc,BigDecimal ydyjz,BigDecimal yxrdjz,BigDecimal zhdyl,String zhdywbh,String zhdywlx,
			String zhdywmc,BigDecimal zhdywzmjz,String zypzhm,BigDecimal zypzje,String zypzklrq,String zypzlx,String zypzqfjg,String zypzzh,String zywbgfs,
			String zywpgfs) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TGyGydzywb t = this.TGyGydzywbService.get(id);
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("bwhsksrq", bwhsksrq);
		paraMap.put("bxdh", bxdh);
		paraMap.put("bz", bz);
		paraMap.put("czjg", czjg);
		paraMap.put("dbdqrq", dbdqrq);
		paraMap.put("dbhth", dbhth);
		paraMap.put("dbqsrq", dbqsrq);
		paraMap.put("djjg", djjg);
		paraMap.put("djrq", djrq);
		paraMap.put("djyxzzrq", djyxzzrq);
		paraMap.put("dywsyqrmc", dywsyqrmc);
		paraMap.put("hbrq", hbrq);
		paraMap.put("jrxkzh", jrxkzh);
		paraMap.put("pgjgmc", pgjgmc);
		paraMap.put("pgjz", pgjz);
		paraMap.put("pgrq", pgrq);
		paraMap.put("qzdjhm", qzdjhm);
		paraMap.put("qzmc", qzmc);
		paraMap.put("qzyxdqrq", qzyxdqrq);
		paraMap.put("sfnrbwhs", sfnrbwhs);
		paraMap.put("swsqrq", swsqrq);
		paraMap.put("xtjgdm", xtjgdm);
		paraMap.put("xtjgmc", xtjgmc);
		paraMap.put("ydyjz", ydyjz);
		paraMap.put("yxrdjz", yxrdjz);
		paraMap.put("zhdyl", zhdyl);
		paraMap.put("zhdywbh", zhdywbh);
		paraMap.put("zhdywlx", zhdywlx);
		paraMap.put("zhdywmc", zhdywmc);
		paraMap.put("zhdywzmjz", zhdywzmjz);
		paraMap.put("zypzhm", zypzhm);
		paraMap.put("zypzje", zypzje);
		paraMap.put("zypzklrq", zypzklrq);
		paraMap.put("zypzlx", zypzlx);
		paraMap.put("zypzqfjg", zypzqfjg);
		paraMap.put("zypzzh", zypzzh);
		paraMap.put("zywbgfs", zywbgfs);
		paraMap.put("zywpgfs", zywpgfs);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataid", id);
		List<TCheckInfo> list = this.TCheckInfoService.getListForPage(inputParams, -1, -1, null);
		if(list != null && !list.isEmpty()){
			TCheckInfo check = list.get(0);
			TGyGydzywb oldData = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), TGyGydzywb.class);

			if(t == null){
				t = oldData;
			}
			
			TGyGydzywb newData = (TGyGydzywb) t.clone();
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
				return ResultManager.createFailResult("???????????????");
			}
			
			TGyGydzywb newData = (TGyGydzywb) t.clone();
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
		
		return ResultManager.createSuccessResult("????????????!");
	}
	
	/**
	 * ??????
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "ID", type = DataType.STRING)
	@ResponseBody
	public Result delete(String id) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TGyGydzywb t = this.TGyGydzywbService.get(id);
		if(t==null){
			return ResultManager.createFailResult("???????????????");
		}
		
		this.TGyGydzywbService.deleteWholeData(t, operator);
		return ResultManager.createSuccessResult("????????????!");
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/checkList", method = RequestMethod.GET)
	@ActionParam(key = "status", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "pageNum", message = "??????", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "????????????", required = true, type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "????????????", type = DataType.STRING)
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
			
			TGyGydzywb t = this.TGyGydzywbService.get(check.getDataid());
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
	 * ????????????
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
			return ResultManager.createFailResult("???????????????!");
		}
	}
	
	/**
	 * ??????
	 */
	@RequestMapping(value = "/checkCheck", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING_ARRAY)
	@ActionParam(key = "status", message = "????????????", required = true, type = DataType.STRING)
	@ResponseBody
	public Result checkCheck(String[] id, String status) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		if(!"checked".equals(status) && !"nopass".equals(status)){
			return ResultManager.createFailResult("??????????????????");
		}
		List<TCheckInfo> checkList = new ArrayList<TCheckInfo>();
		for(String aid: id){
			TCheckInfo t = this.TCheckInfoService.get(aid);
			if(t == null || !datatype.equals(t.getDatatype())){
				return ResultManager.createFailResult("???????????????!");
			}
			checkList.add(t);
		}
		this.TCheckInfoService.check(checkList, status, operator);
		return ResultManager.createSuccessResult("????????????!");
	}

	/**
	 * ??????????????????
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
			return ResultManager.createFailResult("???????????????");
		}
	}
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping(value = "/checkRollback", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result checkRollback(String id) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TSsLog t = this.TSsLogService.get(id);
		if(t == null || !datatype.equals(t.getDatatype())){
			return ResultManager.createFailResult("???????????????!");
		}
		
		if(!TSsLogType.CHECK.equals(t.getType())){
			return ResultManager.createFailResult("???????????????????????????!");
		}
		this.TSsLogService.rollback(t, operator);
		
		return ResultManager.createSuccessResult("????????????!");
	}
} 
