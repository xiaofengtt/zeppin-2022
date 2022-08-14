package enfo.crm.dao.supper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DbKit {
	
	private final static Log log = LogFactory.getLog(DbKit.class);
	
	private final static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
	
	/**
	 * 关闭连接池
	 */
	public final static void close() {
		try {
			Connection conn = conns.get();
			
			if (conn != null && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			}
		} catch (Exception e) {
			log.error("Unabled to destroy DataSource!!! ", e);
		}
	}
	
	/**
	 * 从连接池中取
	 * 
	 * 使用代理请使用如下方法
	 * 
	 * <pre>
	 * conn = new _HelperCellConnection(conn).getConnection();
	 * </pre>
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection get(DataSource ds) throws SQLException {
		Connection conn = conns.get();
		if (conn == null || conn.isClosed()) {
			conn = ds.getConnection();
			if(conn != null){
				conn = new _HelperCellConnection(conn).getConnection();
			}
			conns.set(conn);
		}
		return conn;
	}
	
	static class _HelperCellConnection implements InvocationHandler {

		private Connection conn = null;

		public _HelperCellConnection(Connection conn) {
			this.conn = conn;
		}

		public Connection getConnection() {
			return (Connection) Proxy.newProxyInstance(this.conn.getClass()
					.getClassLoader(), this.conn.getClass().getInterfaces(),
					this);
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {	
			
			//当为关闭的时候。设置对应的连接池关闭。释放资源
			if("close".equals(method.getName())){
				Connection conn = conns.get();
				if (conn != null && !conn.isClosed()) {
					conn.setAutoCommit(true);
				}
				conns.set(null);
			}
			
			return method.invoke(conn, args);
		}
	}


}
