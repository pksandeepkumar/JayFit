
package com.feathernet.jayfit.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.feathernet.jayfit.models.Category;

import java.util.ArrayList;







public class DatabasesHelper extends SQLiteOpenHelper {

	Context context;
	private static final String TAG = DatabasesHelper.class.getName();
	public static final String DATABASE_NAME = "Voncity.db";
	public static final int DATABASE_VERSION = 1;

	private static ArrayList<String> query_create_tables = null;
	private static ArrayList<String> tables = null;

	public DatabasesHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		loadQuery();
		createTables(database);
	}

	public void createTables( SQLiteDatabase database ) {
		for (String query : query_create_tables) {
			try {
				database.execSQL(query);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Create list of crate table queries. 
	 */
	private void loadQuery() {
		query_create_tables = new ArrayList<String>();
		query_create_tables.add(Category.CREATE_TABLE_QUERY);//1
	}

	private void loadTableNames() {
		tables.add(Category.TABLE_NAME);//1
	}

	public static void clearDB(Context context) {
		if( context ==  null) return;
		DatabasesHelper databasesHelper = new DatabasesHelper(context);
		clearDB(databasesHelper);
		databasesHelper.close();
	}

	public static void clearDB( DatabasesHelper databasesHelper) {
		if(databasesHelper == null) return;
		Category.deleteTable(databasesHelper);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
						  int newVersion) {
		onCreate(database);
	}

}
