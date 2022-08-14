/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITSubmitService;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.utility.TableValues;
import cn.zeppin.product.utility.Utlity;
import cn.zeppin.product.utility.ZipUtils;

/**
 * 上报文本
 */

@Controller
@RequestMapping(value = "/backadmin/submit")
public class TSubmitController extends BaseController {
	
	@Autowired
	private ITSubmitService TSubmitService;
	
	/**
	 * 导出上报数据
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	@ActionParam(key = "types", message="数据类型", type = DataType.STRING_ARRAY, required = true)
	@ActionParam(key = "starttime", message="起始时间", type = DataType.DATE, required = true)
	@ActionParam(key = "endtime", message="截止时间", type = DataType.DATE, required = true)
	@ActionParam(key = "time", message="上报时间", type = DataType.DATE, required = true)
	@ResponseBody
	public Result submit(String[] types, String starttime, String endtime, String time, HttpServletRequest request, HttpServletResponse response) {
		if(types.length == 0){
			return ResultManager.createFailResult("未选择需导出的数据类型！");
		}
		
		String serverPath = request.getServletContext().getRealPath("/").replace("\\", "/");
        FileOutputStream outSTr = null;
        BufferedOutputStream buff = null;
        
		Map<String, String> dataMap = TSubmitService.submitDatas(types, starttime, endtime, time);
		String count = dataMap.get("count");

		if("0".equals(count)){
			return ResultManager.createFailResult("没有可提交的内容！");
		}
		
		response.setContentType("APPLICATION/OCTET-STREAM");  
		if(types.length > 1){
			response.setHeader("Content-Disposition","attachment; filename="+"K0066H211000001-"+ time.replace("-", "")+".zip");
		}else{
			response.setHeader("Content-Disposition","attachment; filename="+"K0066H211000001-"+TableValues.submitTables.get(types[0])+"-"+ time.replace("-", "")+".zip");
		}
        ZipOutputStream out = null;
        
		try {
			out = new ZipOutputStream(response.getOutputStream());
			for(String type : types){
				String dataStr = dataMap.get(type);
				String dataCount = dataMap.get(type+"Count");
				if(dataStr != null && !"".equals(dataStr)){
					String filename = "K0066H211000001-" + TableValues.submitTables.get(type) + "-"+ time.replace("-", "");
					
					String logStr = filename + ".txt" + "\r\n" 
							+ dataStr.getBytes("UTF-8").length + "\r\n"
							+ Utlity.timeSpanToString(new Timestamp(System.currentTimeMillis())) + "\r\n"
							+ "Y" + "\r\n"
							+ dataCount;
					
					File file=new File(serverPath + "/submit/" +filename);
					if(!file.isDirectory() && !file.exists()){
						file.mkdir();
					}
					
					//写txt
					outSTr = new FileOutputStream(new File(serverPath + "/submit/" + filename + "/" + filename+".txt"));
					buff = new BufferedOutputStream(outSTr);
					buff.write(dataStr.getBytes("UTF-8"));
					buff.flush();
					
					//写log
					outSTr = new FileOutputStream(new File(serverPath + "/submit/" + filename + "/" + filename+".log"));
					buff = new BufferedOutputStream(outSTr);
					buff.write(logStr.getBytes("UTF-8"));
					buff.flush();
					
		            ZipUtils.doCompress(serverPath + "/submit/" + filename + "/" + filename+".txt", out);
		            response.flushBuffer();
		            ZipUtils.doCompress(serverPath + "/submit/" + filename + "/" + filename+".log", out);
		            response.flushBuffer();
				}
			}
			buff.close();
            out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}	finally {
            try {
                buff.close();
                outSTr.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		return null;
	}
}
