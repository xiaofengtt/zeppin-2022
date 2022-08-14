package cn.zeppin.product.itic.backadmin.service.runable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zeppin.product.itic.backadmin.service.api.ITmsListService;
import cn.zeppin.product.itic.core.entity.TmsList;
import cn.zeppin.product.utility.SystemConfig;
import cn.zeppin.product.utility.itic.DownloadUtils;
import cn.zeppin.product.utility.itic.MD5Utils;

@Component
public class RunablePushDownload implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(RunablePushDownload.class);
	private Thread t;
	private String Srfmsgid;
	private TmsList tl;
	
	@Autowired
	private ITmsListService tmsListService;

	private String vediosavepath = SystemConfig.getProperty("com.itic.cdr.vediosavepath");
	private String vccid = SystemConfig.getProperty("com.itic.cdr.vccid");
	private String key = SystemConfig.getProperty("com.itic.cdr.key");
	private String srfmsgidUrlA = SystemConfig.getProperty("com.itic.cdr.srfmsgidUrlA");
	private String srfmsgidUrlB = SystemConfig.getProperty("com.itic.cdr.srfmsgidUrlB");

	public RunablePushDownload() {
		super();
	}
	
	public RunablePushDownload(String srfmsgid, ITmsListService tmsListService, TmsList tl) {
		this.Srfmsgid = srfmsgid;
		this.tl = tl;
		this.tmsListService = tmsListService;
		logger.info("======Creating======");
		logger.info("======Srfmsgid: " +  Srfmsgid );
	}
	
	public void instance(String srfmsgid, TmsList tl) {
		Srfmsgid = srfmsgid;
		this.tl = tl;
		logger.info("======Creating======");
		logger.info("======Srfmsgid: " +  Srfmsgid );
	}

	public void run() {
		logger.info("======Running======");
		try {
			if (!Srfmsgid.equals("")) {
				logger.info("============Download prepare============");
				Thread.sleep(3000);
				int startIndex = Srfmsgid.indexOf("3268/");
				String time = Srfmsgid.substring(startIndex+5, startIndex+13);
				logger.info("time: "+time);
				String VTK = vccid + time + key;
				logger.info("VTK: "+VTK);
				String md5Str = MD5Utils.getMD5(VTK);
				logger.info("md5Str: "+md5Str);
				String urlStr = srfmsgidUrlA + Srfmsgid + srfmsgidUrlB + md5Str;
				logger.info("urlStr: "+urlStr);
				String fileName = Srfmsgid.substring(Srfmsgid.lastIndexOf("/"));
				logger.info("fileName: "+fileName);
				/*String filePath = "D:\\接口文档\\PNhide\\download\\redio\\ByUrl\\"+date+"\\";*/
				String filePath = vediosavepath + time + "/";
				int result = DownloadUtils.DownloadByUrl(urlStr, fileName, filePath,"");
				if (result == 1) {
					logger.info("============Download Success! ============");
					/*JsonReturn.put("header", SystemVar.Msg0000);*/
					tl.setRealpath(filePath+fileName);
					this.tmsListService.update(tl);
				}else {
					/*JsonReturn.put("header", SystemVar.Msg9997);*/
					logger.error(filePath+fileName);
					logger.error("============Download Error! ============");
				}
			}else {
				logger.info("============Download skip============");
				/*JsonReturn.put("header", SystemVar.Msg0000);*/
			}
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("======Thread  Exception.======");
		}
		logger.info("======Thread  exiting.======");
	}

	public void start () {
		logger.info("Starting ");
		if (t == null) {
			t = new Thread (this, Srfmsgid);
			t.start ();
		}
	}
}
