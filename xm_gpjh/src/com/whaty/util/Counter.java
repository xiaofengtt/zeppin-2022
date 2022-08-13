package com.whaty.util;

import java.io.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Counter {
	// �ĳ�ͬ��
	private synchronized static int count(String countFilePath) {
		int record;
		int nrecord;
		int step = 10;// ����Ƶ��

		try {
			record = readCount(countFilePath);
			File f = new File(countFilePath);
			File bakFile = new File(f.getParent(), "counter_bak.txt");
			if (record == 0 || record < readCount(bakFile)) {
				// ���������ݵ��뵽������������
				record = readCount(bakFile) + step;
				writeInt(f, record);
				return record;
			}

			nrecord = record + 1;
			writeInt(f, nrecord);
			// ÿ��n�η��ʣ�����һ��
			if (nrecord % step == 0) {
				int bak = 100;
				try {
					bak = readCount(bakFile);
				} catch (Exception e) {

				}
				// ����¼����һ�����ֵ�ʱ��������ֵ
				if (nrecord > bak) {
					writeInt(bakFile, nrecord);
					return nrecord;
				} else {
					// ���򽫱������ݵ��뵽������������
					writeInt(f, bak);
					return bak;
				}
			} else {
				return nrecord;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}

	}

	public static String showCount(ServletRequest req, String countFilePath,
			String imgUriPath, int defaultNumLen) {
		String record;
		String htmCode = "";
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		// if(session.isNew())
		// {
		count(countFilePath);
		// }
		try {
			BufferedReader br = new BufferedReader(
					new FileReader(countFilePath));
			record = br.readLine();
			br.close();
			if (record == null)
				record = "0";
			int i = 0;
			int k = defaultNumLen - record.length();
			if (k > 0) {
				for (i = 0; i < k; i++) {
					htmCode += "<img src='" + imgUriPath + "/0.gif'>";
				}
				for (i = 0; i < record.length(); i++) {
					htmCode += "<img src='" + imgUriPath + "/"
							+ record.charAt(i) + ".gif'>";
				}
			} else {
				for (i = 0; i < record.length(); i++) {
					htmCode += "<img src='" + imgUriPath + "/"
							+ record.charAt(i) + ".gif'>";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return htmCode;
	}

	public static String showCount(ServletRequest req, String countFilePath,
			int defaultNumLen) {

		String record = "";
		String s = "";
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		// if(session.isNew())
		// {
		int count = count(countFilePath);
		// }
		try {
			record = String.valueOf(count);
			if (record.length() < defaultNumLen) {
				for (int i = 0; i < defaultNumLen - record.length(); i++) {
					s += "0";
				}
				record = s + record;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return record;
	}

	private static void writeInt(File file, int record) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			pw.print(record);
			pw.close();
		} catch (Exception e) {
			System.out.println("File write error: " + file.getAbsolutePath());
		}
	}

	private static void writeInt(File file, String record) {
		try {
			int num = Integer.parseInt(record);
			writeInt(file, num);
		} catch (Exception e) {
			System.out.println("File write error, illegal number: "
					+ file.getAbsolutePath());
		}
	}

	private static int readCount(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String record = br.readLine();
			br.close();
			return Integer.parseInt(record.trim());
		} catch (FileNotFoundException e1) {
			System.out.println("FileNotFoundException: "
					+ file.getAbsolutePath());
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	private static int readCount(String filePath) {
		return readCount(new File(filePath));
	}
}
