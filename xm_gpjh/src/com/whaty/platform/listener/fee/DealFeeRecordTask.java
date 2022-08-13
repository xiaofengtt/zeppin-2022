/**
 * 
 */
package com.whaty.platform.listener.fee;

import java.sql.SQLException;
import java.util.TimerTask;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;

public class DealFeeRecordTask extends TimerTask {

	public void run() {
		dbpool db = new dbpool();
		String sql = "select user_id,note,num from (select user_id,note,count(id) as num from entity_userfee_record where payout_type='CONSUME' group by user_id,note) where num>1 order by num desc";
		MyResultSet rs = db.executeQuery(sql);
		try 
		{
				while (rs != null && rs.next()) 
				{
					String sql1 = "select id from entity_userfee_record where user_id='"+rs.getString("user_id")+"' and note='"+rs.getString("note")+"' order by date_time desc";
					String sql2 = "delete from entity_userfee_record where id in (";
					MyResultSet rs1 = db.executeQuery(sql1);
					rs1.next();
					while (rs1 != null && rs1.next()) {
						sql2+="'"+rs1.getString("id")+"',";
					}
					db.close(rs1);
					sql2 = sql2.substring(0,sql2.length()-1)+")";
					int suc = db.executeUpdate(sql2);
					if (suc<1)
						System.out.print(sql2 + "<br>");
				}
		} catch (SQLException e) {
			System.out.println(e.getLocalizedMessage());
		}
		finally
		{
			db.close(rs);
		}
	}	
}
