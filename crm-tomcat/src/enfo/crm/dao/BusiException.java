package enfo.crm.dao;

import java.sql.*;

public class BusiException extends Exception {
	private int errorCode;
	private Exception next;

	public static String getErrorMsg(int errorCode){
		return BusiException.getErrorMsg(errorCode, 1);
	}
	/**
	 * 获得错误信息
	 * @param errorCode
	 * @param languageType
	 * @return
	 */
	public static String getErrorMsg(int errorCode,int languageType){
		String sSQL = "{call SP_QUERY_TERRORINFO(?,?,?)}";
		
		try{		
			Connection conn = null;
			CallableStatement stmt = null;
			
			try {
				conn = CrmDBManager.getConnection();
				stmt = conn.prepareCall(sSQL);
				stmt.setInt(1, errorCode);
				stmt.setInt(2, languageType);
				stmt.registerOutParameter(3, Types.VARCHAR);
				stmt.execute();
				
				String errorstr = stmt.getString(3);
				return errorstr;
			} 		
			finally{
				try{	
					if(stmt!=null)stmt.close();
				}
				catch(SQLException e){		
				}
				try{
					if(conn!=null&&!conn.isClosed())conn.close();
				}
				catch(SQLException e){		
				}			
			}
		}
		catch(Exception e){
			return "读取系统错误信息失败，错误代码："+errorCode;
		}
	}

	public BusiException(){
		super();
	}

	public BusiException(int errorCode){
		super(getErrorMsg(errorCode));
		this.setErrorCode(errorCode);
	}

	public BusiException(String string){
		super(string);
	}
	
	public BusiException(String string,Exception e){
		super(string+" - "+e.getMessage());
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}	
}

