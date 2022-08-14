/*
 * 创建日期 2014-12-17
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package enfo.crm.tools;

import java.io.IOException;  
import java.net.DatagramPacket;  
import java.net.DatagramSocket;  
import java.net.InetAddress;  

/**
 * @author Administrator
 *
 * 获得客户端MAC地址
 */
public class UdpGetClientMacAddr {
	private String sRemoteAddr;
	private int iRemotePort=137;
	private byte[] buffer = new byte[1024];
	private DatagramSocket ds=null;

	public UdpGetClientMacAddr(String strAddr) throws Exception{
		sRemoteAddr = strAddr;
		ds = new DatagramSocket();
		ds.setSoTimeout(2000);
	}

	protected final DatagramPacket send(final byte[] bytes) throws IOException {
		DatagramPacket dp = new DatagramPacket(bytes,bytes.length,InetAddress.getByName(sRemoteAddr),iRemotePort);
		ds.send(dp);
		return dp;
	}

	protected final DatagramPacket receive() throws Exception {
		DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
		ds.setSoTimeout(2000);
		ds.receive(dp);
		return dp;
	}

	protected byte[] GetQueryCmd() throws Exception {
		byte[] t_ns = new byte[50];
		t_ns[0] = 0x00;
		t_ns[1] = 0x00;
		t_ns[2] = 0x00;
		t_ns[3] = 0x10;
		t_ns[4] = 0x00;
		t_ns[5] = 0x01;
		t_ns[6] = 0x00;
		t_ns[7] = 0x00;
		t_ns[8] = 0x00;
		t_ns[9] = 0x00;
		t_ns[10] = 0x00;
		t_ns[11] = 0x00;
		t_ns[12] = 0x20;
		t_ns[13] = 0x43;
		t_ns[14] = 0x4B;
		for(int i = 15; i < 45; i++){
			t_ns[i] = 0x41;
		}
		t_ns[45] = 0x00;
		t_ns[46] = 0x00;
		t_ns[47] = 0x21;
		t_ns[48] = 0x00;
		t_ns[49] = 0x01;
		return t_ns;
	}

	protected final String GetMacAddr(byte[] brevdata) throws Exception {
		// 获取计算机名
		int i = brevdata[56] * 18 + 56;
		String sAddr="";
		StringBuffer sb = new StringBuffer(17);
		// 先从第56字节位置，读出Number Of Names（NetBIOS名字的个数，其中每个NetBIOS Names Info部分占18个字节）
		// 然后可计算出“Unit ID”字段的位置＝56＋Number Of Names×18，最后从该位置起连续读取6个字节，就是目的主机的MAC地址。
		for(int j = 1; j < 7;j++){
			sAddr = Integer.toHexString(0xFF & brevdata[i+j]);
			if(sAddr.length() < 2){
				sb.append(0);
			}
			sb.append(sAddr.toUpperCase());
			if(j < 6) sb.append(':');
		}
		return sb.toString();
	}

	public final void close() {
		try{
			ds.close();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public final String GetRemoteMacAddr() throws Exception {
		byte[] bqcmd = GetQueryCmd();
		send(bqcmd);
		DatagramPacket dp = receive();
		String smac = GetMacAddr(dp.getData());
		close();
		return smac;
	}

}
