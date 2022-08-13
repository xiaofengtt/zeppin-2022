package com.zixueku.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 通过getReadableDatabase()和getWritableDatabase()可以获得数据库对象。
 * 提供onCreate()-创建数据库时,onUpgrade()-升级数据库时，两个回调函数。
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static String name = "ZIXUEKU.db"; // 表示数据库的名称
	private static int version = 1; // 表示数据库的版本号

	public DatabaseHelper(Context context) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	// 当数据库创建的时候，是第一次被执行，完成对数据库的表的创建
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// SQLite 数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型
		// 数据库这边有一个特点，就是SQLite数据库中文本类型没有过多的约束，也就是可以把布尔类型的数据存储到文本类型中，这样也是可以的
		String productSql = "create table collect_product(id integer primary key autoincrement,product_id varchar(64),create_time varchar(64),update_time varchar(64),use_flag varchar(1));";
		String shopSql = "create table collect_shop(id integer primary key autoincrement,shop_id varchar(64),create_time varchar(64),update_time varchar(64),use_flag varchar(1));";
		db.execSQL(shopSql);
		db.execSQL(productSql); // 完成数据库的创建
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}