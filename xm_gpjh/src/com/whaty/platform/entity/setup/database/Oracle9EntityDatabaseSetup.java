/**
 * 
 */
package com.whaty.platform.entity.setup.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.config.DatabaseDefaultParams;
import com.whaty.platform.config.DatabaseTest;
import com.whaty.platform.config.SubSystemType;
import com.whaty.platform.entity.setup.EntityDatabaseSetup;
import com.whaty.platform.entity.setup.EntitySetupBean;
import com.whaty.platform.sso.setup.SupportDatabaseType;
import com.whaty.platform.util.log.EntityLog;

/**
 * @author chenjian
 * 
 */
public class Oracle9EntityDatabaseSetup extends EntityDatabaseSetup {

	public static String SQLFILENAME = "entityoracle9.sql";

	public Oracle9EntityDatabaseSetup() throws PlatformException {
		this.setDatabaseType(SupportDatabaseType.ORACLE9);
		Map defaultParams = DatabaseDefaultParams.getDbDefaultParams(this
				.getDatabaseType());
		this.setParams(defaultParams);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sso.setup.SsoDatabaseSetup#creatDb(java.util.List)
	 */
	public void creatDb(EntitySetupBean entitySetupBean)
			throws PlatformException {

		creatDBSystem(entitySetupBean);
		creatDBData(entitySetupBean);

	}

	public void creatDBSystem(EntitySetupBean entitySetupBean)
			throws PlatformException {
		Statement stmt = null;
		Connection conn = null;

		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			conn = DriverManager.getConnection((String) this.getParams().get(
					"dbConnStr"), (String) this.getParams().get("systemId"),
					(String) this.getParams().get("systemPwd"));
		} catch (SQLException e1) {
			throw new PlatformException(this.FAILEDTOCONNECT);
		}

		try {

			String sqluser = "CREATE USER "
					+ (String) this.getParams().get("dbUserId")
					+ " IDENTIFIED BY "
					+ (String) this.getParams().get("dbUserPwd")
					+ " DEFAULT TABLESPACE "
					+ (String) this.getParams().get("userTableSpace")
					+ " TEMPORARY TABLESPACE "
					+ (String) this.getParams().get("tempTableSpace")
					+ " QUOTA UNLIMITED ON "
					+ (String) this.getParams().get("userTableSpace")
					+ " QUOTA UNLIMITED ON "
					+ (String) this.getParams().get("tempTableSpace");
			stmt = conn.createStatement();
			EntityLog.setDebug(sqluser);
			stmt.execute(sqluser);
			String sql = "GRANT CONNECT, DBA TO "
					+ (String) this.getParams().get("dbUserId");
			EntityLog.setDebug(sql);
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new PlatformException(this.USERCREATERROR);
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					throw new PlatformException("stmt close error!");
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new PlatformException("conn close error!");
				}
		}
	}

	public void creatDBData(EntitySetupBean entitySetupBean)
			throws PlatformException {
		try {
			String fileName = entitySetupBean.getConfig()
					.getPlatformWebAppAbsPath()
					+ entitySetupBean.getConfig().getPlatformWebINFRefPath()
					+ "setupDB"
					+ File.separator
					+ SubSystemType.ENTITY
					+ File.separator + Oracle9EntityDatabaseSetup.SQLFILENAME;
			FileReader reader = new FileReader(fileName);
			BufferedReader br = new BufferedReader(reader);
			List sqlGroup = new ArrayList();
			String sql = br.readLine();
			if (sql != null)
				sqlGroup.add(sql.substring(0, sql.lastIndexOf(";")));
			while (sql != null) {
				sql = br.readLine();
				String sql1 = sql;
				if (sql1 != null && sql1.length() > 1) {
					sqlGroup.add(sql1.substring(0, sql1.lastIndexOf(";")));
				}

			}
			br.close();
			reader.close();
			Statement stmt = null;
			Connection conn = null;
			try {

				conn = DriverManager
						.getConnection((String) this.getParams().get(
								"dbConnStr"), (String) this.getParams().get(
								"dbUserId"), (String) this.getParams().get(
								"dbUserPwd"));
				stmt = conn.createStatement();
				for (int i = 0; i < sqlGroup.size(); i++) {
					EntityLog.setDebug((String) sqlGroup.get(i));
					stmt.execute((String) sqlGroup.get(i));
				}

			} catch (SQLException e) {

			} finally {
				if (stmt != null)
					try {
						stmt.close();
					} catch (SQLException e) {
						throw new PlatformException("stmt close error!");
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						throw new PlatformException("conn close error!");
					}
			}

		} catch (IOException e) {
			throw new PlatformException("error in creatDbTables!"
					+ e.toString());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sso.setup.SsoDatabaseSetup#testDatabase()
	 */
	public boolean testDatabase() throws PlatformException {
		return DatabaseTest.isSupported(this.getDatabaseType());
	}

}
