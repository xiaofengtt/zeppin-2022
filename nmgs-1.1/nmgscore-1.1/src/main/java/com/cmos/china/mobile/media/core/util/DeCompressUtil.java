package com.cmos.china.mobile.media.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
//import org.apache.tools.zip.ZipEntry;
import com.cmos.core.logger.Logger;
import com.cmos.core.logger.LoggerFactory;
  
public class DeCompressUtil {   
	private static Logger logger = LoggerFactory.getUtilLog(DeCompressUtil.class);
   /**  
    * 解压zip格式压缩包  
    * 对应的是ant.jar  
 * @throws IOException 
    */  
   private static List<File> unzip(String sourceZip,String destDir) throws IOException{
	   List<File> fileName = new ArrayList<File>();
	   ZipFile zip = null;
       try{
    	   File zipFile = new File(sourceZip);
    	   File pathFile = new File(destDir);
           if (!pathFile.exists()){
               pathFile.mkdirs();
           }
           zip = new ZipFile(zipFile);
           
           for (@SuppressWarnings("rawtypes")Enumeration entries = zip.entries(); entries.hasMoreElements();) {
               ZipEntry entry = (ZipEntry) entries.nextElement();
               String zipEntryName = entry.getName();
               String type = zipEntryName.substring(zipEntryName.indexOf(".") + 1).toLowerCase();
               
               if("jpg".equals(type)||"jpeg".equals(type)||"png".equals(type)
						||"bmp".equals(type)||"tiff".equals(type)||"gif".equals(type)
						||"psd".equals(type)){
				}else{
					continue;
				}
               
               InputStream in = zip.getInputStream(entry);
               String outPath = (destDir + zipEntryName).replaceAll("\\*", "/");
    
               //获取当前file的父路径,这才是文件夹
               File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                
               // 判断路径是否存在,不存在则创建文件路径
               if (!file.exists()){
                   file.mkdirs();
               }
               // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
               if (new File(outPath).isDirectory()){
                   continue;
               }
               OutputStream out = null;
               try{
            	   out = new FileOutputStream(outPath);
            	   byte[] buf1 = new byte[1024];
                   int len;
                   if(out!=null){
                	   while ((len = in.read(buf1)) > 0) {
                		   out.write(buf1, 0, len);
                	   }
                	   out.close();
                   }
               }catch(Exception e){
            	   logger.error("错误", e);
               }finally{
            	   if (out != null) {
					out.close();	
					}
               }
               in.close();
               out.close();
               fileName.add(new File(outPath));
           }
    	   
       }catch(Exception e){   
    	   logger.error("错误", e);
       }finally{
    	   if (zip!=null){
    		   zip.close();
    	   }
       }
       return fileName;
   }   
   /**  
    * 解压rar格式压缩包。  
    * 对应的是java-unrar-0.3.jar，但是java-unrar-0.3.jar又会用到commons-logging-1.1.1.jar  
 * @throws ExceptionUtil 
    */  
   private static List<File> unrar(String sourceRar,String destDir) throws ExceptionUtil{   
       Archive a = null;   
       FileOutputStream fos = null;
       List<File> fileName = new ArrayList<File>();
       try{   
           a = new Archive(new File(sourceRar));   
           FileHeader fh = a.nextFileHeader();   
           while(fh!=null){
               if(!fh.isDirectory()){
                   //1 根据不同的操作系统拿到相应的 destDirName 和 destFileName   
                   String compressFileName = fh.getFileNameString().trim();   
                   String destFileName = "";   
                   String destDirName = "";   
                   //非windows系统   
                   if("/".equals(File.separator)){   
                       destFileName = destDir + compressFileName.replaceAll("\\\\", "/");   
                       destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));   
                   //windows系统    
                   }else{   
                       destFileName = destDir + compressFileName.replaceAll("/", "\\\\");   
                       destDirName = destFileName.substring(0, destFileName.lastIndexOf("\\"));   
                   }   
                   String type = compressFileName.substring(compressFileName.indexOf(".") + 1).toLowerCase();
                   
                   if("jpg".equals(type)||"jpeg".equals(type)||"png".equals(type)
   						||"bmp".equals(type)||"tiff".equals(type)||"gif".equals(type)
   						||"psd".equals(type)){
   					}else{
    					continue;
    				}
                   //2创建文件夹   
                   File dir = new File(destDirName);   
                   if(!dir.exists()||!dir.isDirectory()){   
                       dir.mkdirs();   
                   }   
                   //3解压缩文件   
                   fos = new FileOutputStream(new File(destFileName));   
                   a.extractFile(fh, fos);   
                   fos.close();   
                   fos = null;   
                   fileName.add(new File(destFileName));
               }   
               fh = a.nextFileHeader();
           }   
           a.close();   
           a = null;   
       }catch(Exception e){
    	   {logger.error("错误", e);}  
           throw new ExceptionUtil(e.toString());   
       }finally{ 
           if(fos!=null){ 
               try{fos.close();fos=null;}catch(Exception e){logger.error("错误", e);}   
           }   
           if(a!=null){   
               try{a.close();a=null;}catch(Exception e){logger.error("错误", e);}   
           }   
       }
       return fileName;
   }   
   /**  
    * 解压缩  
 * @throws IOException 
    */  
   public static List<File> deCompress(String sourceFile,String destDir) throws ExceptionUtil, IOException{   
	   String sourceFileNew;
	   String destDirNew;
	   sourceFileNew= sourceFile;
	   destDirNew= destDir;
	   //保证文件夹路径最后是"/"或者"\"   
       char lastChar = destDirNew.charAt(destDirNew.length()-1);   
       if(lastChar!='/'&&lastChar!='\\'){   
    	   destDirNew += File.separator;   
       }   
       //根据类型，进行相应的解压缩   
       String type = sourceFileNew.substring(sourceFileNew.lastIndexOf(".")+1);   
       if("zip".equals(type)){     
          return DeCompressUtil.unzip(sourceFileNew, destDirNew);   
       }else if("rar".equals(type)){  
          return DeCompressUtil.unrar(sourceFileNew, destDirNew);   
        }else{   
        	throw new ExceptionUtil("只支持zip和rar格式的压缩包！");   
        }   
    }   
} 