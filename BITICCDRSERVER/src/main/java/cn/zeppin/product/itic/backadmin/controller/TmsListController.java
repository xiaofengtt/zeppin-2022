/**
 * 
 */
package cn.zeppin.product.itic.backadmin.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zeppin.product.itic.backadmin.service.api.ITcustomersService;
import cn.zeppin.product.itic.backadmin.service.api.ITmsListService;
import cn.zeppin.product.itic.backadmin.service.api.IToperatorService;
import cn.zeppin.product.itic.backadmin.vo.TmsListVO;
import cn.zeppin.product.itic.core.controller.base.ActionParam;
import cn.zeppin.product.itic.core.controller.base.ActionParam.DataType;
import cn.zeppin.product.itic.core.controller.base.BaseController;
import cn.zeppin.product.itic.core.controller.base.Result;
import cn.zeppin.product.itic.core.controller.base.ResultManager;
import cn.zeppin.product.itic.core.entity.Tcustomers;
import cn.zeppin.product.itic.core.entity.TmsList;
import cn.zeppin.product.itic.core.entity.Toperator;
import cn.zeppin.product.itic.core.entity.base.Entity;
import cn.zeppin.product.utility.Utlity;

/**
 * @author l
 *
 * 话单
 */

@Controller
@RequestMapping(value = "/backadmin/mslist")
public class TmsListController extends BaseController {

	@Autowired
	private IToperatorService toperatorService;

	@Autowired
	private ITcustomersService tcustomersService;

	@Autowired
	private ITmsListService tmsListService;

	/**
	 * 获取列表 查询信息 (话单)
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param name
	 * @param sorts
	 * @param opCode
	 * @param toMobile
	 * @param toTel
	 * @param tcPhone
	 * @param tcTel
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ActionParam(key = "pageNum", message = "页码", required = true, type = DataType.NUMBER)
	@ActionParam(key = "pageSize", message = "每页数量", required = true, type = DataType.NUMBER)
	@ActionParam(key = "name", message = "客户搜索参数", type = DataType.STRING)
	@ActionParam(key = "sorts", message = "排序参数", type = DataType.STRING)
	@ActionParam(key = "oname", message = "经理搜索参数", type = DataType.STRING)
	@ActionParam(key = "toMobile", message = "主叫号码（经理）", type = DataType.STRING)
	@ActionParam(key = "toTel", message = "呼入号码", type = DataType.STRING)
	@ActionParam(key = "tcPhone", message = "被叫号码（客户）", type = DataType.STRING)
	@ActionParam(key = "tcTel", message = "呼出号码", type = DataType.STRING)
	@ActionParam(key = "status", message = "状态", type = DataType.STRING)
	@ResponseBody
	public Result search(Integer pageNum, Integer pageSize, String name, String sorts, String oname, String toMobile,
			String toTel, String tcPhone, String tcTel, String status) {
		Map<String, String> inputParams = new HashMap<String, String>();

		// 搜素参数
		if (!Utlity.checkStringNull(name)) {
			inputParams.put("name", name);
		}
		if (!Utlity.checkStringNull(oname)) {
			inputParams.put("oname", oname);
		}
		if (!Utlity.checkStringNull(toMobile)) {
			inputParams.put("callernum", toMobile);
		}
		if (!Utlity.checkStringNull(toTel)) {
			inputParams.put("calleddisplaynumber", toTel);
		}
		if (!Utlity.checkStringNull(tcPhone)) {
			inputParams.put("callednum", tcPhone);
		}
		if (!Utlity.checkStringNull(tcTel)) {
			inputParams.put("callerdisplaynumber", tcTel);
		}
		if (!Utlity.checkStringNull(status)) {
			inputParams.put("status", status);
		}
		Integer totalCount = this.tmsListService.getCountForSearchPage(inputParams);
		List<Entity> arealist = tmsListService.getListForSearchPage(inputParams, pageNum, pageSize, sorts,
				TmsList.class);
		List<TmsListVO> arealistNew = new ArrayList<TmsListVO>();
		for (Entity e : arealist) {
			TmsList nr = (TmsList) e;
			TmsListVO trvo = new TmsListVO(nr);
			if(nr.getFkToperator() != null && nr.getFkToperator() > 0) {
				Toperator to = this.toperatorService.get(nr.getFkToperator());
				if (to != null) {
					trvo.setOpName(to.getOpName());
				}
			}
			if(nr.getFkTcustomers() != null && nr.getFkTcustomers() > 0) {
				Tcustomers tc = this.tcustomersService.get(nr.getFkTcustomers());
				
				if (tc != null) {
					trvo.setCustName(tc.getCustName());
				}
			}
			arealistNew.add(trvo);
		}
		return ResultManager.createDataResult(arealistNew, totalCount);
	}
	
	/**
	 * 文件下载
	 * @param id
	 * @param response
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	@ActionParam(key = "id", message = "编号", required = true, type = DataType.NUMBER)
	@ResponseBody
	public void download(Integer id, HttpServletResponse response) {
		TmsList tm = this.tmsListService.get(id);
		if(tm != null) {
			String realpath = tm.getRealpath();
			if(!Utlity.checkStringNull(realpath)) {
				String dataType = Utlity.getMimeType(realpath);
				
				String type = realpath.substring(realpath.lastIndexOf(".") - 1, realpath.length());
				String filename = Utlity.getUUID()+type;
				response.setContentType(dataType);
				response.setHeader("Content-disposition", "attachment; filename=" + filename);
				
				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				try {
					bis = new BufferedInputStream(new FileInputStream(realpath));
					bos = new BufferedOutputStream(response.getOutputStream());
					
					byte[] buff = new byte[2048000];
					@SuppressWarnings("unused")
					int bytesRead = 0;

					while (-1 != (bytesRead = (bis.read(buff, 0, buff.length)))) {
						bos.write(buff, 0, buff.length);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (bos != null) {
						try {
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}
	}
}
