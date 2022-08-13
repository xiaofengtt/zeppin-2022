/**
 * This class is used for 资源表操作
 * 
 * @author suijing
 * @version 1.0, 2014年7月24日 下午5:05:00
 */
package cn.zeppin.action.admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import cn.zeppin.action.base.ActionParam;
import cn.zeppin.action.base.ActionParam.ValueType;
import cn.zeppin.authority.AuthorityParas;
import cn.zeppin.action.base.ActionResult;
import cn.zeppin.action.base.BaseAction;
import cn.zeppin.utility.Utlity;

/**
 * @author sj
 * 
 */
public class CreateHTMLAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 369393031897616109L;

	/**
	 * 添加
	 */
	@AuthorityParas(userGroupName = "EDITOR_ADD_EDIT")
	@ActionParam(key = "content", type = ValueType.STRING, nullable = false, emptyable = false)
	public void Create() {
		
		ActionResult actionResult = new ActionResult();
		String serverPath = ServletActionContext.getServletContext().getRealPath("/").replace("\\", "/");
		
		String htmlStr = request.getParameter("content");
		
		System.out.println(htmlStr);
		
		if(htmlStr != null && !"".equals(htmlStr)){
			String filePath = "activity/";
			String[] dir = UUID.randomUUID().toString().split("-");
			
			for (String sPath : dir) {//创建存储路径
				filePath += sPath + "/";

				File tfFile = new File(serverPath + "/" + filePath);
				if (!tfFile.exists()) {
					tfFile.mkdirs();
				}
			}
			
			// 存储文件
			
			int avatarNumber = 1;
			// 取服务器时间+8位随机码作为部分文件名，确保文件名无重复。
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyyMMddHHmmssS");
			String fileName = simpleDateFormat.format(new Date());
			Random random = new Random();
			String randomCode = "";
			for (int i = 0; i < 8; i++) {
				randomCode += Integer.toString(random.nextInt(36), 36);
			}
			fileName = fileName + randomCode;
			
			//文件存储路径
			String virtualPath = "/" + filePath
					+ avatarNumber + "_" + fileName + ".html";
			
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(serverPath + virtualPath.replace("/", "\\")));
				
				bw.write(htmlStr);
				String path = ServletActionContext.getRequest().getScheme() + "://" + ServletActionContext.getRequest().getServerName() + ":" + ServletActionContext.getRequest().getServerPort() + ServletActionContext.getRequest().getContextPath();
				
				Map<String , Object> data = new HashMap<String, Object>();
				data.put("url", path + virtualPath.replace("/", "\\"));
				
				actionResult.init(SUCCESS, "html创建成功", data);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				actionResult.init(FAIL_STATUS, "html创建失败", null);
			}finally{
				if(bw != null){
					try {
						bw.close();
					} catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
			}
			
		}else{
			actionResult.init(FAIL_STATUS, "回传字符串为空", null);
		}
		Utlity.ResponseWrite(actionResult, dataType, response);
	}
}
