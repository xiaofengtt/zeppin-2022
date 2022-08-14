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
import cn.zeppin.product.itic.backadmin.service.api.ITSsFileService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsLogService;
import cn.zeppin.product.itic.backadmin.service.api.ITSsOperatorService;
import cn.zeppin.product.itic.backadmin.service.api.ITXmXtxmxxService;
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
import cn.zeppin.product.itic.core.entity.TSsFile;
import cn.zeppin.product.itic.core.entity.TSsLog;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogStatus;
import cn.zeppin.product.itic.core.entity.TSsLog.TSsLogType;
import cn.zeppin.product.itic.core.entity.TSsOperator;
import cn.zeppin.product.itic.core.entity.TSsRole.RoleId;
import cn.zeppin.product.itic.core.entity.TXmXtxmxx;
import cn.zeppin.product.utility.JSONUtils;
import cn.zeppin.product.utility.ReflectUtlity;
import cn.zeppin.product.utility.TableColumn;
import cn.zeppin.product.utility.TableValues;
import cn.zeppin.product.utility.Utlity;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * TXmXtxmxx
 */

@Controller
@RequestMapping(value = "/backadmin/TXmXtxmxx")
public class TXmXtxmxxController extends BaseController {

	@Autowired
	private ITXmXtxmxxService TXmXtxmxxService;
	
	@Autowired
	private ITCheckInfoService TCheckInfoService;

	@Autowired
	private ITSsOperatorService TSsOperatorService;
	
	@Autowired
	private ITSsLogService TSsLogService;
	
	@Autowired
	private ITSsFileService TSsFileService;
	
	private String datatype = "TXmXtxmxx";
	
	/**
	 * 查询列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ActionParam(key = "xtxmbm", message = "信托项目编号", type = DataType.STRING)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ResponseBody
	public Result list(String xtxmbm, Integer pageNum, Integer pageSize, String sorts) {
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		Map<String, String> productMap = (Map<String, String>) session.getAttribute("productMap");
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("xtxmbm", xtxmbm);
		if(RoleId.user.equals(operator.getRole())){inputParams.put("products", Utlity.getStringForSql(new ArrayList<String>(productMap.keySet())));}
		
		Integer count = TXmXtxmxxService.getCount(inputParams);
		List<TXmXtxmxx> list = TXmXtxmxxService.getListForPage(inputParams, pageNum, pageSize, sorts);

		return ResultManager.createDataResult(list, count);
	}
	
	/**
	 * 获取记录
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "id", required = true, type = DataType.STRING)
	@ResponseBody
	public Result get(String id) {
		TXmXtxmxx t = this.TXmXtxmxxService.get(id);
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
		TXmXtxmxx t = this.TXmXtxmxxService.get(id);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataid", id);
		List<TCheckInfo> list = this.TCheckInfoService.getListForPage(inputParams, -1, -1, null);
		if(list != null && !list.isEmpty()){
			TCheckInfo check = list.get(0);
			t = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), TXmXtxmxx.class);
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
		Field[] fields = TXmXtxmxx.class.getDeclaredFields();
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
			
			this.TXmXtxmxxService.insertAll(datasList, operator);
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
			
			List<TXmXtxmxx> dataList = this.TXmXtxmxxService.getListForPage(inputParams, -1, -1, null);
			for(int i = 0; i<dataList.size(); i++){
				TXmXtxmxx data = dataList.get(i);
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
	@ActionParam(key = "bsjtfs", message = "报酬计提方式", type = DataType.STRING)
	@ActionParam(key = "cpzxbz", message = "产品增信标识", type = DataType.STRING)
	@ActionParam(key = "csrgjg", message = "初始认购价格", type = DataType.NUMBER)
	@ActionParam(key = "dyjhbz", message = "单一集合标志", type = DataType.STRING)
	@ActionParam(key = "fgldbh", message = "分管领导编号", type = DataType.STRING)
	@ActionParam(key = "glfs", message = "管理方式", type = DataType.STRING)
	@ActionParam(key = "glmxm", message = "关联母项目", type = DataType.STRING)
	@ActionParam(key = "glyyhcffs", message = "管理运用和处分方式", type = DataType.STRING)
	@ActionParam(key = "hzjgbh", message = "合作机构编号", type = DataType.STRING)
	@ActionParam(key = "hzjgmc", message = "合作机构名称", type = DataType.STRING)
	@ActionParam(key = "hzms", message = "合作模式", type = DataType.STRING)
	@ActionParam(key = "jfjzx", message = "是否净值型", type = DataType.STRING)
	@ActionParam(key = "jgsx", message = "项目结构属性", type = DataType.STRING)
	@ActionParam(key = "jrxkzh", message = "金融许可证号", type = DataType.STRING)
	@ActionParam(key = "jsbz", message = "结算币种", type = DataType.STRING)
	@ActionParam(key = "jtnbjgmc", message = "集团内部机构名称", type = DataType.STRING)
	@ActionParam(key = "jzpgpd", message = "净值评估频度", type = DataType.STRING)
	@ActionParam(key = "jzplpd", message = "净值披露频度", type = DataType.STRING)
	@ActionParam(key = "kfpd", message = "开放频度", type = DataType.STRING)
	@ActionParam(key = "kshbz", message = "可赎回标志", type = DataType.STRING)
	@ActionParam(key = "ktqzz", message = "可提前终止", type = DataType.STRING)
	@ActionParam(key = "qxsm", message = "期限说明", type = DataType.STRING)
	@ActionParam(key = "sfjttj", message = "是否集团推荐", type = DataType.STRING)
	@ActionParam(key = "sfsx", message = "是否伞形", type = DataType.STRING)
	@ActionParam(key = "sfxjl", message = "是否现金流", type = DataType.STRING)
	@ActionParam(key = "slfs", message = "设立方式", type = DataType.STRING)
	@ActionParam(key = "ssbm", message = "所属部门", type = DataType.STRING)
	@ActionParam(key = "strzz", message = "受托人主要职责", type = DataType.STRING)
	@ActionParam(key = "syfs", message = "受益方式", type = DataType.STRING)
	@ActionParam(key = "tzfw", message = "投资范围", type = DataType.STRING)
	@ActionParam(key = "xmclr", message = "项目成立日", type = DataType.STRING)
	@ActionParam(key = "xmhzly", message = "项目合作来源", type = DataType.STRING)
	@ActionParam(key = "xmjlbh", message = "信托经理编号", type = DataType.STRING)
	@ActionParam(key = "xmtotbs", message = "项目TOT标识", type = DataType.STRING)
	@ActionParam(key = "xmzgbh", message = "项目主管编号", type = DataType.STRING)
	@ActionParam(key = "xmzzr", message = "项目终止日", type = DataType.STRING)
	@ActionParam(key = "xtbcl", message = "信托报酬率", type = DataType.NUMBER)
	@ActionParam(key = "xtcsclfe", message = "信托初始成立份额", type = DataType.NUMBER)
	@ActionParam(key = "xtgn", message = "信托功能", type = DataType.STRING)
	@ActionParam(key = "xtjgdm", message = "信托机构代码", type = DataType.STRING)
	@ActionParam(key = "xtjgmc", message = "信托机构名称", type = DataType.STRING)
	@ActionParam(key = "xtxcgm", message = "信托续存规模", type = DataType.NUMBER)
	@ActionParam(key = "xtxmbm", message = "信托项目编号", type = DataType.STRING)
	@ActionParam(key = "xtxmqc", message = "信托项目全称", type = DataType.STRING)
	@ActionParam(key = "xtywfl", message = "信托业务分类", type = DataType.STRING)
	@ActionParam(key = "xtzxmbh", message = "信托子项目编号", type = DataType.STRING)
	@ActionParam(key = "yjdqr", message = "预计到期日", type = DataType.STRING)
	@ActionParam(key = "ywtz", message = "业务特征", type = DataType.STRING)
	@ActionParam(key = "yxfs", message = "运行方式", type = DataType.STRING)
	@ActionParam(key = "yxlhsyqbl", message = "优先劣后受益权比率", type = DataType.NUMBER)
	@ActionParam(key = "zcglbgpd", message = "资产管理报告频度", type = DataType.STRING)
	@ActionParam(key = "zdyqsyl", message = "最低预期收益率", type = DataType.NUMBER)
	@ActionParam(key = "zgyqsyl", message = "最高预期收益率", type = DataType.NUMBER)
	@ActionParam(key = "ztjd", message = "主推介地", type = DataType.STRING)
	@ActionParam(key = "zxjglx", message = "增信机构类型", type = DataType.STRING)
	@ActionParam(key = "zxxs", message = "增信形式", type = DataType.STRING)
	@ResponseBody
	public Result add(String bsjtfs, String cpzxbz, BigDecimal csrgjg, String dyjhbz, String fgldbh,String glfs, String glmxm,
			 String glyyhcffs, String hzjgbh, String hzjgmc, String hzms, String jfjzx, String jgsx, String jrxkzh, String jsbz, String jtnbjgmc,
			 String jzpgpd, String jzplpd, String kfpd, String kshbz, String ktqzz, String qxsm, String sfjttj, String sfsx, String sfxjl,
			 String slfs, String ssbm, String strzz, String syfs, String tzfw, String xmclr, String xmhzly, String xmjlbh, String xmtotbs,
			 String xmzgbh, String xmzzr, BigDecimal xtbcl, BigDecimal xtcsclfe, String xtgn, String xtjgdm, String xtjgmc, BigDecimal xtxcgm,
			 String xtxmbm, String xtxmqc, String xtywfl, String xtzxmbh, String yjdqr, String ywtz, String yxfs, BigDecimal yxlhsyqbl,
			 String zcglbgpd, BigDecimal zdyqsyl, BigDecimal zgyqsyl,String ztjd, String zxjglx, String zxxs) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TXmXtxmxx t = new TXmXtxmxx();
		t.setId(UUID.randomUUID().toString());
		t.setCreatetime(new Timestamp(System.currentTimeMillis()));
		t.setUpdatetime(new Timestamp(System.currentTimeMillis()));
		t.setStatus("normal");
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("bsjtfs", bsjtfs);
		paraMap.put("cpzxbz", cpzxbz);
		paraMap.put("csrgjg", csrgjg);
		paraMap.put("dyjhbz", dyjhbz);
		paraMap.put("fgldbh", fgldbh);
		paraMap.put("glfs", glfs);
		paraMap.put("glmxm", glmxm);
		paraMap.put("glyyhcffs", glyyhcffs);
		paraMap.put("hzjgbh", hzjgbh);
		paraMap.put("hzjgmc", hzjgmc);
		paraMap.put("hzms", hzms);
		paraMap.put("jfjzx", jfjzx);
		paraMap.put("jgsx", jgsx);
		paraMap.put("jrxkzh", jrxkzh);
		paraMap.put("jsbz", jsbz);
		paraMap.put("jtnbjgmc", jtnbjgmc);
		paraMap.put("jzpgpd", jzpgpd);
		paraMap.put("jzplpd", jzplpd);
		paraMap.put("kfpd", kfpd);
		paraMap.put("kshbz", kshbz);
		paraMap.put("ktqzz", ktqzz);
		paraMap.put("qxsm", qxsm);
		paraMap.put("sfjttj", sfjttj);
		
		paraMap.put("sfsx", sfsx);
		paraMap.put("sfxjl", sfxjl);
		paraMap.put("slfs", slfs);
		paraMap.put("ssbm", ssbm);
		paraMap.put("strzz", strzz);
		paraMap.put("syfs", syfs);
		paraMap.put("tzfw", tzfw);
		paraMap.put("xmclr", xmclr);
		paraMap.put("xmhzly", xmhzly);
		paraMap.put("xmjlbh", xmjlbh);
		paraMap.put("xmtotbs", xmtotbs);
		paraMap.put("xmzgbh", xmzgbh);
		paraMap.put("xmzzr", xmzzr);
		paraMap.put("xtbcl", xtbcl);
		paraMap.put("xtcsclfe", xtcsclfe);
		paraMap.put("xtgn", xtgn);
		paraMap.put("xtjgdm", xtjgdm);
		paraMap.put("xtjgmc", xtjgmc);
		paraMap.put("xtxcgm", xtxcgm);
		paraMap.put("xtxmbm", xtxmbm);
		paraMap.put("xtxmqc", xtxmqc);
		paraMap.put("xtywfl", xtywfl);
		paraMap.put("xtzxmbh", xtzxmbh);
		paraMap.put("yjdqr", yjdqr);
		paraMap.put("ywtz", ywtz);
		paraMap.put("yxfs", yxfs);
		paraMap.put("yxlhsyqbl", yxlhsyqbl);
		paraMap.put("zcglbgpd", zcglbgpd);
		paraMap.put("zdyqsyl", zdyqsyl);
		paraMap.put("zgyqsyl", zgyqsyl);
		paraMap.put("ztjd", ztjd);
		paraMap.put("zxjglx", zxjglx);
		paraMap.put("zxxs", zxxs);
		
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
		check.setDataproduct(xtxmbm);
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
	@ActionParam(key = "bsjtfs", message = "报酬计提方式", type = DataType.STRING)
	@ActionParam(key = "cpzxbz", message = "产品增信标识", type = DataType.STRING)
	@ActionParam(key = "csrgjg", message = "初始认购价格", type = DataType.NUMBER)
	@ActionParam(key = "dyjhbz", message = "单一集合标志", type = DataType.STRING)
	@ActionParam(key = "fgldbh", message = "分管领导编号", type = DataType.STRING)
	@ActionParam(key = "glfs", message = "管理方式", type = DataType.STRING)
	@ActionParam(key = "glmxm", message = "关联母项目", type = DataType.STRING)
	@ActionParam(key = "glyyhcffs", message = "管理运用和处分方式", type = DataType.STRING)
	@ActionParam(key = "hzjgbh", message = "合作机构编号", type = DataType.STRING)
	@ActionParam(key = "hzjgmc", message = "合作机构名称", type = DataType.STRING)
	@ActionParam(key = "hzms", message = "合作模式", type = DataType.STRING)
	@ActionParam(key = "jfjzx", message = "是否净值型", type = DataType.STRING)
	@ActionParam(key = "jgsx", message = "项目结构属性", type = DataType.STRING)
	@ActionParam(key = "jrxkzh", message = "金融许可证号", type = DataType.STRING)
	@ActionParam(key = "jsbz", message = "结算币种", type = DataType.STRING)
	@ActionParam(key = "jtnbjgmc", message = "集团内部机构名称", type = DataType.STRING)
	@ActionParam(key = "jzpgpd", message = "净值评估频度", type = DataType.STRING)
	@ActionParam(key = "jzplpd", message = "净值披露频度", type = DataType.STRING)
	@ActionParam(key = "kfpd", message = "开放频度", type = DataType.STRING)
	@ActionParam(key = "kshbz", message = "可赎回标志", type = DataType.STRING)
	@ActionParam(key = "ktqzz", message = "可提前终止", type = DataType.STRING)
	@ActionParam(key = "qxsm", message = "期限说明", type = DataType.STRING)
	@ActionParam(key = "sfjttj", message = "是否集团推荐", type = DataType.STRING)
	@ActionParam(key = "sfsx", message = "是否伞形", type = DataType.STRING)
	@ActionParam(key = "sfxjl", message = "是否现金流", type = DataType.STRING)
	@ActionParam(key = "slfs", message = "设立方式", type = DataType.STRING)
	@ActionParam(key = "ssbm", message = "所属部门", type = DataType.STRING)
	@ActionParam(key = "strzz", message = "受托人主要职责", type = DataType.STRING)
	@ActionParam(key = "syfs", message = "受益方式", type = DataType.STRING)
	@ActionParam(key = "tzfw", message = "投资范围", type = DataType.STRING)
	@ActionParam(key = "xmclr", message = "项目成立日", type = DataType.STRING)
	@ActionParam(key = "xmhzly", message = "项目合作来源", type = DataType.STRING)
	@ActionParam(key = "xmjlbh", message = "信托经理编号", type = DataType.STRING)
	@ActionParam(key = "xmtotbs", message = "项目TOT标识", type = DataType.STRING)
	@ActionParam(key = "xmzgbh", message = "项目主管编号", type = DataType.STRING)
	@ActionParam(key = "xmzzr", message = "项目终止日", type = DataType.STRING)
	@ActionParam(key = "xtbcl", message = "信托报酬率", type = DataType.NUMBER)
	@ActionParam(key = "xtcsclfe", message = "信托初始成立份额", type = DataType.NUMBER)
	@ActionParam(key = "xtgn", message = "信托功能", type = DataType.STRING)
	@ActionParam(key = "xtjgdm", message = "信托机构代码", type = DataType.STRING)
	@ActionParam(key = "xtjgmc", message = "信托机构名称", type = DataType.STRING)
	@ActionParam(key = "xtxcgm", message = "信托续存规模", type = DataType.NUMBER)
	@ActionParam(key = "xtxmbm", message = "信托项目编号", type = DataType.STRING)
	@ActionParam(key = "xtxmqc", message = "信托项目全称", type = DataType.STRING)
	@ActionParam(key = "xtywfl", message = "信托业务分类", type = DataType.STRING)
	@ActionParam(key = "xtzxmbh", message = "信托子项目编号", type = DataType.STRING)
	@ActionParam(key = "yjdqr", message = "预计到期日", type = DataType.STRING)
	@ActionParam(key = "ywtz", message = "业务特征", type = DataType.STRING)
	@ActionParam(key = "yxfs", message = "运行方式", type = DataType.STRING)
	@ActionParam(key = "yxlhsyqbl", message = "优先劣后受益权比率", type = DataType.NUMBER)
	@ActionParam(key = "zcglbgpd", message = "资产管理报告频度", type = DataType.STRING)
	@ActionParam(key = "zdyqsyl", message = "最低预期收益率", type = DataType.NUMBER)
	@ActionParam(key = "zgyqsyl", message = "最高预期收益率", type = DataType.NUMBER)
	@ActionParam(key = "ztjd", message = "主推介地", type = DataType.STRING)
	@ActionParam(key = "zxjglx", message = "增信机构类型", type = DataType.STRING)
	@ActionParam(key = "zxxs", message = "增信形式", type = DataType.STRING)
	@ResponseBody
	public Result edit(String id, String bsjtfs, String cpzxbz, BigDecimal csrgjg, String dyjhbz, String fgldbh,String glfs, String glmxm,
			 String glyyhcffs, String hzjgbh, String hzjgmc, String hzms, String jfjzx, String jgsx, String jrxkzh, String jsbz, String jtnbjgmc,
			 String jzpgpd, String jzplpd, String kfpd, String kshbz, String ktqzz, String qxsm, String sfjttj, String sfsx, String sfxjl,
			 String slfs, String ssbm, String strzz, String syfs, String tzfw, String xmclr, String xmhzly, String xmjlbh, String xmtotbs,
			 String xmzgbh, String xmzzr, BigDecimal xtbcl, BigDecimal xtcsclfe, String xtgn, String xtjgdm, String xtjgmc, BigDecimal xtxcgm,
			 String xtxmbm, String xtxmqc, String xtywfl, String xtzxmbh, String yjdqr, String ywtz, String yxfs, BigDecimal yxlhsyqbl,
			 String zcglbgpd, BigDecimal zdyqsyl, BigDecimal zgyqsyl,String ztjd, String zxjglx, String zxxs) {
		
		Session session = SecurityUtils.getSubject().getSession();
		TSsOperatorVO operator = (TSsOperatorVO) session.getAttribute("currentOperator");
		
		TXmXtxmxx t = this.TXmXtxmxxService.get(id);
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("bsjtfs", bsjtfs);
		paraMap.put("cpzxbz", cpzxbz);
		paraMap.put("csrgjg", csrgjg);
		paraMap.put("dyjhbz", dyjhbz);
		paraMap.put("fgldbh", fgldbh);
		paraMap.put("glfs", glfs);
		paraMap.put("glmxm", glmxm);
		paraMap.put("glyyhcffs", glyyhcffs);
		paraMap.put("hzjgbh", hzjgbh);
		paraMap.put("hzjgmc", hzjgmc);
		paraMap.put("hzms", hzms);
		paraMap.put("jfjzx", jfjzx);
		paraMap.put("jgsx", jgsx);
		paraMap.put("jrxkzh", jrxkzh);
		paraMap.put("jsbz", jsbz);
		paraMap.put("jtnbjgmc", jtnbjgmc);
		paraMap.put("jzpgpd", jzpgpd);
		paraMap.put("jzplpd", jzplpd);
		paraMap.put("kfpd", kfpd);
		paraMap.put("kshbz", kshbz);
		paraMap.put("ktqzz", ktqzz);
		paraMap.put("qxsm", qxsm);
		paraMap.put("sfjttj", sfjttj);
		
		paraMap.put("sfsx", sfsx);
		paraMap.put("sfxjl", sfxjl);
		paraMap.put("slfs", slfs);
		paraMap.put("ssbm", ssbm);
		paraMap.put("strzz", strzz);
		paraMap.put("syfs", syfs);
		paraMap.put("tzfw", tzfw);
		paraMap.put("xmclr", xmclr);
		paraMap.put("xmhzly", xmhzly);
		paraMap.put("xmjlbh", xmjlbh);
		paraMap.put("xmtotbs", xmtotbs);
		paraMap.put("xmzgbh", xmzgbh);
		paraMap.put("xmzzr", xmzzr);
		paraMap.put("xtbcl", xtbcl);
		paraMap.put("xtcsclfe", xtcsclfe);
		paraMap.put("xtgn", xtgn);
		paraMap.put("xtjgdm", xtjgdm);
		paraMap.put("xtjgmc", xtjgmc);
		paraMap.put("xtxcgm", xtxcgm);
		paraMap.put("xtxmbm", xtxmbm);
		paraMap.put("xtxmqc", xtxmqc);
		paraMap.put("xtywfl", xtywfl);
		paraMap.put("xtzxmbh", xtzxmbh);
		paraMap.put("yjdqr", yjdqr);
		paraMap.put("ywtz", ywtz);
		paraMap.put("yxfs", yxfs);
		paraMap.put("yxlhsyqbl", yxlhsyqbl);
		paraMap.put("zcglbgpd", zcglbgpd);
		paraMap.put("zdyqsyl", zdyqsyl);
		paraMap.put("zgyqsyl", zgyqsyl);
		paraMap.put("ztjd", ztjd);
		paraMap.put("zxjglx", zxjglx);
		paraMap.put("zxxs", zxxs);
		
		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("datatype", datatype);
		inputParams.put("dataid", id);
		List<TCheckInfo> list = this.TCheckInfoService.getListForPage(inputParams, -1, -1, null);
		if(list != null && !list.isEmpty()){
			TCheckInfo check = list.get(0);
			TXmXtxmxx oldData = JSONUtils.json2obj(Utlity.ClobToString(check.getNewdata()), TXmXtxmxx.class);

			if(t == null){
				t = oldData;
			}
			
			TXmXtxmxx newData = (TXmXtxmxx) t.clone();
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
			
			TXmXtxmxx newData = (TXmXtxmxx) t.clone();
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
			check.setDataproduct(xtxmbm);
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
		
		TXmXtxmxx t = this.TXmXtxmxxService.get(id);
		if(t==null){
			return ResultManager.createFailResult("数据不存在");
		}
		
		this.TXmXtxmxxService.deleteWholeData(t, operator);
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
			
			TXmXtxmxx t = this.TXmXtxmxxService.get(check.getDataid());
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
