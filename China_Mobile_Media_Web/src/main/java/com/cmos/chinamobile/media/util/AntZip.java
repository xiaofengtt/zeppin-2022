package com.cmos.chinamobile.media.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;


public class AntZip {

	/**
	 * @param sourcefiles 源文件或者目录
	 * @param compfilepath 压缩文件路径
	 * @throws IOException IO异常
	 */
	public boolean zip(String sourcefiles, String compfilepath) throws IOException {
		boolean succflag=false;
		if(sourcefiles.equals("") || compfilepath.equals(""))
		    return succflag;  //如果源文件或目标文件为空，则返回0
		ZipOutputStream out = null;
		try
		{
			File compfile =new File(compfilepath);
			out = new ZipOutputStream(compfile);
			File fileOrDirectory =new File(sourcefiles);
			if (fileOrDirectory.isFile())
				zipFileOrDirectory(out, fileOrDirectory, "");
			else {
				File[] entries = fileOrDirectory.listFiles();
				for (int i = 0; i < entries.length; i++)
					// 递归压缩
					{
					zipFileOrDirectory(out, entries[i], "");
				}
			}
			succflag=true;
			compfile=null;
			fileOrDirectory=null;
		} catch (IOException ex) {
			succflag=false;
			ex.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException ex) {

				}
			}
			out=null;
		}
		return succflag;
	}

	/**
	 * @param out 压缩输出流对象
	 * @param compresobject 要压缩的文件或目录对象
	 * @param curPath 当前压缩条目的路径，用于指定条目名称的前缀
	 * @throws IOException IO异常
	 */
	private boolean zipFileOrDirectory(ZipOutputStream out,File compresobject,String curPath) throws IOException {
		boolean succflag=false;
		FileInputStream in = null;
		try
		{
			if (!compresobject.isDirectory())
			{
				// 压缩文件
				byte[] buffer = new byte[4096];
				int bytes_read;
				in = new FileInputStream(compresobject);
				ZipEntry entry =new ZipEntry(curPath + compresobject.getName());
				out.putNextEntry(entry);
				while ((bytes_read = in.read(buffer)) != -1)
				{
					out.write(buffer, 0, bytes_read);
				}
				out.closeEntry();
			} else
			{
				// 压缩目录
				File[] entries = compresobject.listFiles();
				for (int i = 0; i < entries.length; i++) {
					// 递归压缩，更新curPaths
					zipFileOrDirectory(out,entries[i],curPath + compresobject.getName() + "/");
				}
			}
			succflag=true;
		} catch (IOException ex) {
			succflag=false;
			ex.printStackTrace();
			throw ex;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					succflag=false;
					ex.printStackTrace();
				}
			}
			in=null;
		}
		return succflag;
	}

	/**
	 * @param sourcefiles 源文件
	 * @param decompreDirectory 解压缩后文件存放的目录
	 * @throws IOException IO异常
	 */
	@SuppressWarnings("rawtypes")
	public static void unzip(String sourcefiles, String decompreDirectory) throws IOException {
		ZipFile readfile = null;
		try {
			readfile =new ZipFile(sourcefiles);
			
			Enumeration takeentrie = readfile.getEntries();
			ZipEntry zipEntry = null;
			File credirectory = new File(decompreDirectory);
			credirectory.mkdirs();
			while (takeentrie.hasMoreElements()) {
				zipEntry = (ZipEntry) takeentrie.nextElement();
				String entryName = zipEntry.getName();
				InputStream in = null;
				FileOutputStream out = null;
				try {
					if (zipEntry.isDirectory()) {
						String name = zipEntry.getName();
						name = name.substring(0, name.length() - 1);
						File  createDirectory = new File(decompreDirectory+ File.separator + name);
						createDirectory.mkdirs();
					} else {
						int index = entryName.lastIndexOf("\\");
						if (index != -1) {
							File createDirectory = new File(decompreDirectory+ File.separator+ entryName.substring(0, index));
							createDirectory.mkdirs();
						}
						index = entryName.lastIndexOf("/");
						if (index != -1) {
							File createDirectory = new File(decompreDirectory + File.separator + entryName.substring(0, index));
							createDirectory.mkdirs();
						}
						File unpackfile = new File(decompreDirectory + File.separator + zipEntry.getName());
						in = readfile.getInputStream(zipEntry);
						out = new FileOutputStream(unpackfile);
						int c;
						byte[] by = new byte[1024];
						while ((c = in.read(by)) != -1) {
							out.write(by, 0, c);
						}
						out.flush();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
					throw new IOException("解压失败：" + ex.toString());
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (IOException ex) {

						}
					}
					if (out != null) {
						try {
							out.close();
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
					in=null;
					out=null;
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new IOException("解压失败：" + ex.toString());
		} finally {
			if (readfile != null) {
				try {
					readfile.close();
				} catch (IOException ex) {
					ex.printStackTrace();
					throw new IOException("解压失败：" + ex.toString());
				}
			}
		}
	}

}
