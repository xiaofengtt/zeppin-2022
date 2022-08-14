package cn.zeppin.product.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

public class KettleExecu {
	
	public static Map<String, String> runJob(String jobname, Map<String, String> paraMap) {  
		Map<String, String> result = new HashMap<String, String>();
		String errorString = "";
		try {  
            KettleEnvironment.init();  
            // jobname 是Job脚本的路径及名称  
            JobMeta jobMeta = new JobMeta(jobname, null);  
            Job job = new Job(null, jobMeta);  
            // 向Job 脚本传递参数，脚本中获取参数值：${参数名}  
            Set<Entry<String, String>> set=paraMap.entrySet(); 
            for(Iterator<Entry<String, String>> it=set.iterator();it.hasNext();){ 
                Entry<String, String> ent=it.next(); 
                job.setVariable(ent.getKey(), ent.getValue());  
            } 
            job.start();  
            job.waitUntilFinished();  
            if (job.getErrors() > 0) {
            	errorString = job.getResult().getLogText();
                throw new Exception("There are errors during job exception!(执行job发生异常)");  
            }  
        } catch (Exception e) {
        	result.put("result", "false");
        	result.put("errorString", errorString);
            return result;
        }
		result.put("result", "true");
		result.put("errorString", errorString);
		return result;
    }  
  
    // 调用Transformation示例：  
    public static Boolean runTrans(String filename) {  
    	Trans trans = null;  
        try {  
            // // 初始化  
            // 转换元对象  
            KettleEnvironment.init();// 初始化             
            EnvUtil.environmentInit();  
            TransMeta transMeta = new TransMeta(filename);  
            // 转换  
            trans = new Trans(transMeta);  
              
            // 执行转换              
            trans.execute(null);  
            // 等待转换执行结束              
            trans.waitUntilFinished();  
            // 抛出异常  
            if (trans.getErrors() > 0) {  
                throw new Exception( "There are errors during transformation exception!(传输过程中发生异常)");
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;
        }  
        return true;
    }
}  