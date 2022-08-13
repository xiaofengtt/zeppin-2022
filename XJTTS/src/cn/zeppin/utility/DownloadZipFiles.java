package cn.zeppin.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

public class DownloadZipFiles {
	public static void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
           File[] fl = f.listFiles();
           out.putNextEntry(new ZipEntry(base + "/"));
           base = base.length() == 0 ? "" : base + "/";
           for (int i = 0; i < fl.length; i++) {
           zip(out, fl[i], base + fl[i].getName());
         }
        }else {
           out.putNextEntry(new ZipEntry(base));
           FileInputStream in = new FileInputStream(f);
           int b;
           System.out.println(base);
           while ( (b = in.read()) != -1) {
            out.write(b);
         }
         in.close();
       }
    }
	
	
    public static HttpServletResponse downloadZip(File file,HttpServletResponse response) {
        try {
        // 以流的形式下载文件。
        InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        // 清空response
        response.reset();

        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");

        //如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
        response.setHeader("Content-Disposition", "attachment;filename=" +URLEncoder.encode(file.getName(), "UTF-8"));
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
        } catch (IOException ex) {
        ex.printStackTrace();
        }finally{
             try {
                    File f = new File(file.getPath());
                    f.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return response;
    }
    
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    public static String zip(String zipFile, String basePath){
    	try {
    		List<String> command=new ArrayList<String>();
    		command.add("zip");
    		command.add("-rj");
    		command.add(zipFile);
    		command.add(basePath);
    		String str = runCommand(command);
    		return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
    }
    
	/**
	 * 执行命令行返回处理结果
	 */
	public static String runCommand(List<String> command) throws Exception {
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(command);
		builder.redirectErrorStream(true);
		Process p = builder.start();
		BufferedReader buf = null;
		String line = null;
		buf = new BufferedReader(new InputStreamReader(p.getInputStream()));

		StringBuffer sb = new StringBuffer();
		while ((line = buf.readLine()) != null) {
			sb.append(line);
			continue;
		}
		p.waitFor();
		System.out.println(sb.toString());
		// logger.info(sb.toString());
		return sb.toString();
	}
}
