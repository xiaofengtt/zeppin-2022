package com.cmos.china.mobile.media.core.service.impl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.cmos.china.mobile.media.core.bean.AppVersion;
import com.cmos.china.mobile.media.core.bean.Resource;
import com.cmos.china.mobile.media.core.service.IAppVersionService;
import com.cmos.china.mobile.media.core.util.Utlity;
import com.cmos.core.bean.InputObject;
import com.cmos.core.bean.OutputObject;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
import com.cmos.china.mobile.media.core.util.ExceptionUtil;
public class AppVersionServiceImpl extends BaseServiceImpl implements IAppVersionService {
	private static Logger logger = LoggerFactory.getServiceLog(AppVersionServiceImpl.class);
	@Override
	public void add(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
			String apk = inputObject.getValue("apk");
			String component = inputObject.getValue("component");
			String province = inputObject.getValue("province");
			String explain = inputObject.getValue("explain");
			String creator = inputObject.getValue("creator");
			if(apk==null || "".equals(apk))
			{
				throw new ExceptionUtil("文件为空");
			}
			if(component==null ||"".equals(component)){
				throw new ExceptionUtil("组件为空");
			}
			if(province==null || "".equals(province)){
				throw new ExceptionUtil("省份为空");
			}
			if(explain==null || "".equals(explain)){
				throw new ExceptionUtil("版本说明为空");
			}
			if(creator==null || "".equals(creator)){
				throw new ExceptionUtil("用户为空");
			}
								Resource res = (Resource) this.getBaseDao().queryForObject("resource_get", apk);
								if(res!=null  && !"".equals(res)){
								String name = res.getFilename();
								String versionName = null;
								String versionNumber = null;
								try{
								versionName = name.substring(name.indexOf("-")+1,name.lastIndexOf("-"));
								versionNumber = name.substring(name.lastIndexOf("-")+1,name.lastIndexOf("."));
								}catch(Exception e){
									logger.info("app内部命名格式错误", e);
									throw new ExceptionUtil("app内部命名格式错误");
								}
								Map<String,String> param = new HashMap<>();
								param.put("status","in ('normal','stopped')");
								param.put("sort","createtime desc");
								List<AppVersion> list = this.getBaseDao().queryForList("appversion_getListByParams", param,AppVersion.class);
								if(!list.isEmpty()){
									if(!(Integer.valueOf(list.get(0).getVersionnu())<Integer.valueOf(versionNumber))){
									throw new ExceptionUtil("app版本号过低！");
									}
								}
								AppVersion appVersion = new AppVersion();
								appVersion.setComponent(component);
								appVersion.setCreatetime(new Timestamp(new Date().getTime()));
								appVersion.setCreator(creator);
								appVersion.setAppexplain(explain);
								String id = UUID.randomUUID().toString();
								appVersion.setId(id);
								appVersion.setProvince(province);
								appVersion.setPath(res.getPath());
								appVersion.setSize(res.getSize());
								appVersion.setStatus("normal");
								appVersion.setVersionna(versionName);
								appVersion.setVersionnu(versionNumber);
								this.getBaseDao().insert("appversion_add", appVersion);
								}	else{
									throw new ExceptionUtil("app上传失败");
									}			
		 }

	@Override
	public void getApps(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		String component = inputObject.getValue("component");
		String province = inputObject.getValue("province");
		String sort = inputObject.getValue("sort");
		String status = inputObject.getValue("status");
		Integer pagenum = Utlity.getIntValue(inputObject.getValue("pagenum"), 1);
		Integer pagesize = Utlity.getIntValue(inputObject.getValue("pagesize"), 10);
		Integer start = (pagenum - 1) * pagesize;
		if(sort == null || "".equals(sort)){
			sort = "createtime desc";
		}else{
			sort = sort.replaceAll("-", " ");
		}
		Map<String,String> param = new HashMap<>();
		param.put("limit",pagesize+"");
		param.put("start",start+"");
		param.put("province",province);
		param.put("component",component);
		param.put("sort",sort);
		if(status==null){
			param.put("status","in ('normal','stopped')");
		}else{
			param.put("status","='"+status+"'");
		}
		if(component!=null  && !"".equals(component)){
			if(province!=null && !"".equals(province)){
				List<AppVersion> list = this.getBaseDao().queryForList("appversion_getListByParams", param,AppVersion.class);
				Integer count = this.getBaseDao().getTotalCount("appversion_getCount", param);
				Integer pageCount = (int) Math.ceil((double) count / pagesize);
				
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("pageNum", pagenum);
				resultMap.put("pageSize", pagesize);
				resultMap.put("totalPageCount", pageCount);
				resultMap.put("totalResultCount", count);
				outputObject.setObject(resultMap);
				outputObject.convertBeans2List(list);
				}else{
					throw new ExceptionUtil("省份信息错误");
				}
			}else{
				throw new ExceptionUtil("组件信息为空");
			}
		}

	@Override
	public void editApp(InputObject inputObject, OutputObject outputObject)
			throws ExceptionUtil {
		String status = inputObject.getValue("status");
		String auditor = inputObject.getValue("auditor");
		String id = inputObject.getValue("id");
		
		if(!"normal".equals(status) && !"stopped".equals(status)){
			throw new ExceptionUtil("参数异常");
		}
		
		if (id != null && !"".equals(id)) {
				AppVersion appVersion = new AppVersion();
				appVersion.setId(id);
				appVersion.setCreator(auditor);
				appVersion.setStatus(status);
				this.getBaseDao().update("appversion_update",appVersion);
		} else {
			throw new ExceptionUtil("参数异常");
		}
	}
		
	}
	

