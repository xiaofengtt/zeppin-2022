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
import cn.zeppin.product.itic.backadmin.service.api.ITGyGyzzyyhtxxService;
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
import cn.zeppin.product.itic.core.entity.TGyGyzzyyhtxx;
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
 * TGyGyzzyyhtxx
 */

@Controller
@RequestMapping(value = "/backadmin/TGyGyzzyyhtxx")
public class TGyGyzzyyhtxxController extends BaseController {

	@Autowired
	private ITGyGyzzyyhtxxService TGyGyzzyyhtxxService;
	
	@Autowired
	private ITCheckInfoService TCheckInfoService;

	@Autowired
	private ITSsOperatorService TSsOperatorService;
	
	@Autowired
	private ITSsLogService TSsLogService;
	
	@Autowired
	private ITSsFileService TSsFileService;
	
	private String datatype = "TGyGyzzyyhtxx";
	
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
		
		Integer count = TGyGyzzyyhtxxService.getCount(inputParams);
		List<TGyGyzzyyhtxx> list = TGyGyzzyyhtxxService.getListForPage(inputParams, pageNum, pageSize, sorts);

		return ResultManager.createDataResult(list, count);
	}
	
	/**
	 * ????????????
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result get(String id) {
		TGyGyzzyyhtxx t = this.TGyGyzzyyhtxxService.get(id);
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
		TGyGyzzyyhtxx t = this.TGyGyzzyyhtxxService.get(id);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataid", id);
		List<TCheckInfo> list = this.TCheckInfoService.getListForPage(inputParams, -1, -1, null);
		if(list != null && !list.isEmpty()){
			TCheckInfo check = list.get(0);
			t = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), TGyGyzzyyhtxx.class);
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
		Field[] fields = TGyGyzzyyhtxx.class.getDeclaredFields();
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
			
			this.TGyGyzzyyhtxxService.insertAll(datasList, operator);
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
			
			List<TGyGyzzyyhtxx> dataList = this.TGyGyzzyyhtxxService.getListForPage(inputParams, -1, -1, null);
			for(int i = 0; i<dataList.size(); i++){
				TGyGyzzyyhtxx data = dataList.get(i);
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
	@ActionParam(key = "bdzclx", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "bdzcmc", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "bdzcrzjz", message = "????????????????????????", type = DataType.NUMBER)
	@ActionParam(key = "bmmc", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "btzcpdm", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "dsfbh", message = "???????????????", type = DataType.STRING)
	@ActionParam(key = "dsflx", message = "???????????????", type = DataType.STRING)
	@ActionParam(key = "gyywlb", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "hkfs", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "htdqrq", message = "?????????????????? ", type = DataType.STRING)
	@ActionParam(key = "htqdje", message = "??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "htqdrq", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "htqyll", message = "??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "hymx", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "jbrbh", message = "???????????????", type = DataType.STRING)
	@ActionParam(key = "jrxkzh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "ptzcpmc", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "rzhtdqrq", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "scfkrq", message = "?????????????????? ", type = DataType.STRING)
	@ActionParam(key = "sfbgscp", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "tcfs", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "tqzz", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "trzhtbh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "trzhtmc", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "txdq", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "txhy", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "wbhtbh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "xtjgdm", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "xtjgmc", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "yqsyl", message = "???????????????", type = DataType.NUMBER)
	@ActionParam(key = "ysdsbh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "ysdslx", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "ysfqjglx", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "yshtbh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "zcwjfl", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "zhrzcb", message = "??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "zjyyfs", message = "??????????????????", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result add(String bdzclx,String bdzcmc,BigDecimal bdzcrzjz,String bmmc,String btzcpdm,String dsfbh,String dsflx,String gyywlb,
			String hkfs,String htdqrq,BigDecimal htqdje,String htqdrq,BigDecimal htqyll,String hymx,String jbrbh,String jrxkzh,String ptzcpmc,
			String rzhtdqrq,String scfkrq,String sfbgscp,String tcfs,String tqzz,String trzhtbh,String trzhtmc,String txdq,String txhy,String wbhtbh,
			String xtjgdm,String xtjgmc,BigDecimal yqsyl,String ysdsbh,String ysdslx,String ysfqjglx,String yshtbh,String zcwjfl,BigDecimal zhrzcb,
			String[] zjyyfs) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TGyGyzzyyhtxx t = new TGyGyzzyyhtxx();
		t.setId(UUID.randomUUID().toString());
		t.setCreatetime(new Timestamp(System.currentTimeMillis()));
		t.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		t.setStatus("normal");
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("bdzclx",bdzclx);
		paraMap.put("bdzcmc",bdzcmc);
		paraMap.put("bdzcrzjz",bdzcrzjz);
		paraMap.put("bmmc",bmmc);
		paraMap.put("btzcpdm",btzcpdm);
		paraMap.put("dsfbh",dsfbh);
		paraMap.put("dsflx",dsflx);
		paraMap.put("gyywlb",gyywlb);
		paraMap.put("hkfs",hkfs);
		paraMap.put("htdqrq",htdqrq);
		paraMap.put("htqdje",htqdje);
		paraMap.put("htqdrq",htqdrq);
		paraMap.put("htqyll",htqyll);
		paraMap.put("hymx",hymx);
		paraMap.put("jbrbh",jbrbh);
		paraMap.put("jrxkzh",jrxkzh);
		paraMap.put("ptzcpmc",ptzcpmc);
		paraMap.put("rzhtdqrq",rzhtdqrq);
		paraMap.put("scfkrq",scfkrq);
		paraMap.put("sfbgscp",sfbgscp);
		paraMap.put("tcfs",tcfs);
		paraMap.put("tqzz",tqzz);
		paraMap.put("trzhtbh",trzhtbh);
		paraMap.put("trzhtmc",trzhtmc);
		paraMap.put("txdq",txdq);
		paraMap.put("txhy",txhy);
		paraMap.put("wbhtbh",wbhtbh);
		paraMap.put("xtjgdm",xtjgdm);
		paraMap.put("xtjgmc",xtjgmc);
		paraMap.put("yqsyl",yqsyl);
		paraMap.put("ysdsbh",ysdsbh);
		paraMap.put("ysdslx",ysdslx);
		paraMap.put("ysfqjglx",ysfqjglx);
		paraMap.put("yshtbh",yshtbh);
		paraMap.put("zcwjfl",zcwjfl);
		paraMap.put("zhrzcb",zhrzcb);
		String zjStr = "";
		if(zjyyfs != null && zjyyfs.length>0){
			for(String zjyy : zjyyfs){
				zjStr = zjStr + zjyy + ";";
			}
			zjStr = zjStr.substring(0, zjStr.length()-1);
		}
		paraMap.put("zjyyfs",zjStr);
		
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
	@ActionParam(key = "bdzclx", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "bdzcmc", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "bdzcrzjz", message = "????????????????????????", type = DataType.NUMBER)
	@ActionParam(key = "bmmc", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "btzcpdm", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "dsfbh", message = "???????????????", type = DataType.STRING)
	@ActionParam(key = "dsflx", message = "???????????????", type = DataType.STRING)
	@ActionParam(key = "gyywlb", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "hkfs", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "htdqrq", message = "?????????????????? ", type = DataType.STRING)
	@ActionParam(key = "htqdje", message = "??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "htqdrq", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "htqyll", message = "??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "hymx", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "jbrbh", message = "???????????????", type = DataType.STRING)
	@ActionParam(key = "jrxkzh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "ptzcpmc", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "rzhtdqrq", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "scfkrq", message = "?????????????????? ", type = DataType.STRING)
	@ActionParam(key = "sfbgscp", message = "?????????????????????", type = DataType.STRING)
	@ActionParam(key = "tcfs", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "tqzz", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "trzhtbh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "trzhtmc", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "txdq", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "txhy", message = "????????????", type = DataType.STRING)
	@ActionParam(key = "wbhtbh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "xtjgdm", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "xtjgmc", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "yqsyl", message = "???????????????", type = DataType.NUMBER)
	@ActionParam(key = "ysdsbh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "ysdslx", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "ysfqjglx", message = "????????????????????????", type = DataType.STRING)
	@ActionParam(key = "yshtbh", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "zcwjfl", message = "??????????????????", type = DataType.STRING)
	@ActionParam(key = "zhrzcb", message = "??????????????????", type = DataType.NUMBER)
	@ActionParam(key = "zjyyfs", message = "??????????????????", type = DataType.STRING_ARRAY)
	@ResponseBody
	public Result edit(String id,String bdzclx,String bdzcmc,BigDecimal bdzcrzjz,String bmmc,String btzcpdm,String dsfbh,String dsflx,
			String gyywlb,String hkfs,String htdqrq,BigDecimal htqdje,String htqdrq,BigDecimal htqyll,String hymx,String jbrbh,String jrxkzh,String ptzcpmc,
			String rzhtdqrq,String scfkrq,String sfbgscp,String tcfs,String tqzz,String trzhtbh,String trzhtmc,String txdq,String txhy,String wbhtbh,
			String xtjgdm,String xtjgmc,BigDecimal yqsyl,String ysdsbh,String ysdslx,String ysfqjglx,String yshtbh,String zcwjfl,BigDecimal zhrzcb,
			String[] zjyyfs) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TGyGyzzyyhtxx t = this.TGyGyzzyyhtxxService.get(id);
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("bdzclx",bdzclx);
		paraMap.put("bdzcmc",bdzcmc);
		paraMap.put("bdzcrzjz",bdzcrzjz);
		paraMap.put("bmmc",bmmc);
		paraMap.put("btzcpdm",btzcpdm);
		paraMap.put("dsfbh",dsfbh);
		paraMap.put("dsflx",dsflx);
		paraMap.put("gyywlb",gyywlb);
		paraMap.put("hkfs",hkfs);
		paraMap.put("htdqrq",htdqrq);
		paraMap.put("htqdje",htqdje);
		paraMap.put("htqdrq",htqdrq);
		paraMap.put("htqyll",htqyll);
		paraMap.put("hymx",hymx);
		paraMap.put("jbrbh",jbrbh);
		paraMap.put("jrxkzh",jrxkzh);
		paraMap.put("ptzcpmc",ptzcpmc);
		paraMap.put("rzhtdqrq",rzhtdqrq);
		paraMap.put("scfkrq",scfkrq);
		paraMap.put("sfbgscp",sfbgscp);
		paraMap.put("tcfs",tcfs);
		paraMap.put("tqzz",tqzz);
		paraMap.put("trzhtbh",trzhtbh);
		paraMap.put("trzhtmc",trzhtmc);
		paraMap.put("txdq",txdq);
		paraMap.put("txhy",txhy);
		paraMap.put("wbhtbh",wbhtbh);
		paraMap.put("xtjgdm",xtjgdm);
		paraMap.put("xtjgmc",xtjgmc);
		paraMap.put("yqsyl",yqsyl);
		paraMap.put("ysdsbh",ysdsbh);
		paraMap.put("ysdslx",ysdslx);
		paraMap.put("ysfqjglx",ysfqjglx);
		paraMap.put("yshtbh",yshtbh);
		paraMap.put("zcwjfl",zcwjfl);
		paraMap.put("zhrzcb",zhrzcb);
		String zjStr = "";
		if(zjyyfs != null && zjyyfs.length>0){
			for(String zjyy : zjyyfs){
				zjStr = zjStr + zjyy + ";";
			}
			zjStr = zjStr.substring(0, zjStr.length()-1);
		}
		paraMap.put("zjyyfs",zjStr);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataid", id);
		List<TCheckInfo> list = this.TCheckInfoService.getListForPage(inputParams, -1, -1, null);
		if(list != null && !list.isEmpty()){
			TCheckInfo check = list.get(0);
			TGyGyzzyyhtxx oldData = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), TGyGyzzyyhtxx.class);

			if(t == null){
				t = oldData;
			}
			
			TGyGyzzyyhtxx newData = (TGyGyzzyyhtxx) t.clone();
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
			
			TGyGyzzyyhtxx newData = (TGyGyzzyyhtxx) t.clone();
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
		
		TGyGyzzyyhtxx t = this.TGyGyzzyyhtxxService.get(id);
		if(t==null){
			return ResultManager.createFailResult("???????????????");
		}
		
		this.TGyGyzzyyhtxxService.deleteWholeData(t, operator);
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
			
			TGyGyzzyyhtxx t = this.TGyGyzzyyhtxxService.get(check.getDataid());
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
