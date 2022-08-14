package enfo.crm.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import sun.misc.BASE64Decoder;

/**
 * IO������
 * @author lzf
 */
public class IoUtils {

	/**
	 * �ر�������
	 * @param in ������
	 */
	public static void closeIn(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * �ر�������
	 * @param out ������
	 */
	public static void closeOut(OutputStream out) {
		if (out != null) {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * �ر�Reader
	 * @param in Reader
	 */
	public static void closeReader(Reader in) {
		if (in != null) {
			try {
				in.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * �ر�Writer
	 * @param writer Writer
	 */
	public static void closeWrite(Writer writer) {
		if (writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * �����л�
	 * @param b �ֽ�
	 * @return ����
	 */
	public static Object unserialize(byte[] b) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bin = null;
		ObjectInputStream in = null;
		try {
			bin = new ByteArrayInputStream(b);
			in = new ObjectInputStream(bin);
			return in.readObject();
		} catch (IOException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw e;
		} finally {
			closeIn(bin);
			closeIn(in);
		}
	}

	/**
	 * ���л�
	 * @param obj ����
	 * @return �ֽ�
	 * @throws IOException
	 */
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream bout = null;
		ObjectOutputStream out = null;
		try {
			bout = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bout);
			out.writeObject(obj);
			return bout.toByteArray();
		} catch (IOException e) {
			throw e;
		} finally {
			closeOut(bout);
			closeOut(out);
		}
	}

	/**
	 * ����������ȡΪ�ֽ�����
	 * @param in ������
	 * @return �ֽ�
	 * @throws IOException IO�쳣
	 */
	public static byte[] readStream(InputStream in) throws IOException {
		byte bytes[] = null;
		int i = 0;
		byte tempBytes1[] = new byte[1024];
		try {
			for (int j = in.read(tempBytes1); j != -1; j = in.read(tempBytes1)) {
				byte tempBytes2[] = new byte[i + j];
				if (i > 0) {
					System.arraycopy(bytes, 0, tempBytes2, 0, i);
				}
				System.arraycopy(tempBytes1, 0, tempBytes2, i, j);
				bytes = tempBytes2;
				i += j;
			}
		} catch (IOException e) {
			throw e;
		} finally {
			closeIn(in);
		}
		return bytes;
	}
	// ���ֽ������ַ�������Base64���벢����ͼƬ  
	public static boolean GenerateImage(String imgStr,OutputStream out) {  
		if (imgStr == null)  
			return false;  
		BASE64Decoder decoder = new BASE64Decoder();  
		try {  
			byte[] b = decoder.decodeBuffer(imgStr);  
			for (int i = 0; i < b.length; ++i) {  
				if (b[i] < 0) { 
					b[i] += 256;  
				}  
			}  
			out.write(b);  
			out.flush();  
			out.close();  
			return true;  
		} catch (Exception e) {  
			return false;  
		}  
	} 
}

