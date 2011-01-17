package com.neugent.touchcity.xmlparser;


import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.neugent.touchcity.R;

public class XMLDBHelper {
	private static final String DATABASE_NAME = "enjoydb";
	
	private static final String TABLE_ENJOY_DETAIL = "enjoyDetail";
	private static final String TABLE_ENJOY_MAIN = "enjoyMain";
	private static final String[] TABLE_ENJOY_DETAIL_COLUMNS = new String[]{"_id", "companyName", "inSection", "inCity",
																		"inAddress", "inPhone", "inHours",
																		"fileName", "latitude", "longitude"};
	
	public final static String[] TABLE_ENJOY_MAIN_COLUMNS = {"_id","companyName", "inSection", "inCategory", "inCity", "fileName",
													"holiday", "hasVisa", "hasDelivery","hasAmex", "hasVIP",
													"hasParking", "hasOnlineMenu", "hasChildseat","hasinternational",
													"hasJCB","hasMasterCard", "hasWifi", "hasDiners", "cardOffer",
													"voucherOffer","voucherValue", "Tagline1","TagLine2", "CoDescription",
													"addressKey"};
	
	private static final String CREATE_TABLE_ENJOY_DETAIL = "" +
	"create table if not exists "+TABLE_ENJOY_DETAIL+"("+TABLE_ENJOY_DETAIL_COLUMNS[0]+" integer primary key, " +
	TABLE_ENJOY_DETAIL_COLUMNS[1]+" text," +
	TABLE_ENJOY_DETAIL_COLUMNS[2]+" text," +
	TABLE_ENJOY_DETAIL_COLUMNS[3]+" text," +
	TABLE_ENJOY_DETAIL_COLUMNS[4]+" text," +
	TABLE_ENJOY_DETAIL_COLUMNS[5]+" text," +
	TABLE_ENJOY_DETAIL_COLUMNS[6]+" text," +
	TABLE_ENJOY_DETAIL_COLUMNS[7]+" text," +
	TABLE_ENJOY_DETAIL_COLUMNS[8]+" real," +
	TABLE_ENJOY_DETAIL_COLUMNS[9]+" real);";
	
	private static final String CREATE_TABLE_ENJOY_MAIN = "" +
	"create table if not exists "+TABLE_ENJOY_MAIN+"("+TABLE_ENJOY_MAIN_COLUMNS[0]+" integer primary key, " +
	TABLE_ENJOY_MAIN_COLUMNS[1]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[2]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[3]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[4]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[5]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[6]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[7]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[8]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[9]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[10]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[11]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[12]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[13]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[14]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[15]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[16]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[17]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[18]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[19]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[20]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[21]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[22]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[23]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[24]+" text," +
	TABLE_ENJOY_MAIN_COLUMNS[25]+" text);";
	
	private static Context context;
	private static SQLiteDatabase db;
	
	private Handler handler;
	
	public XMLDBHelper(Context context, Handler handler){
		this.handler = handler;
		this.context = context;
		db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
		
		db.execSQL(CREATE_TABLE_ENJOY_DETAIL);
		db.execSQL(CREATE_TABLE_ENJOY_MAIN);
		
		
		if(getTableCompanyRowCount() == 0){
			loadInitialCompanyValues();
			loadInitialMainCompanyValues();
		}
		else{
			Message msg = handler.obtainMessage();
			Bundle b = new Bundle();
			b.putString("message", "done");
			msg.setData(b);
			handler.sendMessage(msg);
		}
			
		
		db.close();
		
	}
	
	public static void open() throws SQLException{
		db = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
	}
	
	public static void close(){
		db.close();
	}
	
	private void loadInitialCompanyValues() {
		try {
        	SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        	SAXParser saxParser = saxParserFactory.newSAXParser();
        	XMLReader xmlReader = saxParser.getXMLReader();
        	
        	EnjoyDetailXMLHandler xmlHandler = new EnjoyDetailXMLHandler();
        	xmlReader.setContentHandler(xmlHandler);
        	
        	InputStream inputStream = context.getResources().openRawResource(R.raw.enjoy_detail);
        	xmlReader.parse(new InputSource(inputStream));
        	inputStream.close();

		} catch (Exception e) {
			Log.e("Error", e.getLocalizedMessage()+" "+e.getMessage());
		}
		
	}
	
	private void loadInitialMainCompanyValues() {
		try {
        	SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        	SAXParser saxParser = saxParserFactory.newSAXParser();
        	XMLReader xmlReader = saxParser.getXMLReader();
        	
        	EnjoyMainXMLHandler xmlHandler = new EnjoyMainXMLHandler(handler);
        	xmlReader.setContentHandler(xmlHandler);
        	
        	InputStream inputStream = context.getResources().openRawResource(R.raw.enjoy_main);
        	xmlReader.parse(new InputSource(inputStream));
        	inputStream.close();

		} catch (Exception e) {
			Log.e("Error", e.getLocalizedMessage()+" "+e.getMessage());
		}
	}
	
	public static void createEnjoyDetailRow(EnjoyDetailXMLDataSet e){
		ContentValues newValues = new ContentValues();
		
		newValues.put(TABLE_ENJOY_DETAIL_COLUMNS[1], e.getCompanyName());
		newValues.put(TABLE_ENJOY_DETAIL_COLUMNS[2], e.getInSection());
		newValues.put(TABLE_ENJOY_DETAIL_COLUMNS[3], e.getInCity());
		newValues.put(TABLE_ENJOY_DETAIL_COLUMNS[4], e.getInAddress());
		newValues.put(TABLE_ENJOY_DETAIL_COLUMNS[5], e.getInPhone());
		newValues.put(TABLE_ENJOY_DETAIL_COLUMNS[6], e.getInHours());
		newValues.put(TABLE_ENJOY_DETAIL_COLUMNS[7], e.getFileName());
		newValues.put(TABLE_ENJOY_DETAIL_COLUMNS[8], e.getLatitude());
		newValues.put(TABLE_ENJOY_DETAIL_COLUMNS[9], e.getLongitude());
		
        db.insert(TABLE_ENJOY_DETAIL, null, newValues);
	}
	
	public static void createEnjoyMainRow(EnjoyMainXMLDataSet e){
		ContentValues newValues = new ContentValues();
		
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[1], e.getCompanyName());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[2], e.getInSection());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[3], e.getInCategory());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[4], e.getInCity());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[5], e.getFileName());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[6], e.getHoliday());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[7], e.getHasVisa());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[8], e.getHasDelivery());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[9], e.getHasAmex());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[10], e.getHasVIP());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[11], e.getHasParking());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[12], e.getHasOnlineMenu());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[13], e.getHasChildSeat());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[14], e.getHasInternational());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[15], e.getHasJCB());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[16], e.getHasMasterCard());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[17], e.getHasWifi());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[18], e.getHasDiners());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[19], e.getCardOffer());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[20], e.getVoucherOffer());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[21], e.getVoucherValue());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[22], e.getTagLine1());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[23], e.getTagLine2());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[24], e.getCoDescription());
		newValues.put(TABLE_ENJOY_MAIN_COLUMNS[25], e.getAddressKey());
		

        db.insert(TABLE_ENJOY_MAIN, null, newValues);
	}

	private int getTableCompanyRowCount() {
		try {
			Cursor c = db.query(TABLE_ENJOY_DETAIL, TABLE_ENJOY_DETAIL_COLUMNS,
					null, null, null, null, null);
			int count = c.getCount();
			c.close();
			
			return count;
			
		} catch (SQLException e) {
			Log.e("Exception on query", e.toString());
			return 0;
		}
	}
	
	public static Cursor fetchAllCompanyRows() {
		Cursor cursor = db.query(TABLE_ENJOY_DETAIL, TABLE_ENJOY_DETAIL_COLUMNS,
				null, null, null, null, null);
		return cursor;
    }

	public static Cursor fetchCompanyByLocation(String location) {

		return db.query(TABLE_ENJOY_DETAIL, TABLE_ENJOY_DETAIL_COLUMNS,
				"inCity=\"" + location+"\"", null, null, null, null);
    }
	
	public static Cursor fetchCompanyByCategories(String location, String category) {

		return db.query(TABLE_ENJOY_DETAIL, TABLE_ENJOY_DETAIL_COLUMNS,
				"inCity=\"" + location+"\" and inSection like\"%" + category + "%\"", null, null, null, null);
    }
	
	public static Cursor detailQuery(String name){
        return db.query(TABLE_ENJOY_MAIN, TABLE_ENJOY_MAIN_COLUMNS, "companyName=\"" + name+"\"", null, null, null, null);
	}
	
	public static Cursor fetchAllCompany(String category){
		return db.query(TABLE_ENJOY_DETAIL, TABLE_ENJOY_DETAIL_COLUMNS,
				"inSection like \"%"+category+"%\"",
				 null, null, null, null);
	}
	
	

	
	/*dinagdag ni Leq*/
	public static Cursor fetchCompanyBySearchList(String searchView) {
		String selection = "SELECT * FROM "+TABLE_ENJOY_DETAIL+"   " +
		"WHERE "+TABLE_ENJOY_DETAIL+".companyName like \'%" + searchView +"%\' " +
		"or "+TABLE_ENJOY_DETAIL+".inSection LIKE \'%" + searchView +"%\' " +
		"or "+TABLE_ENJOY_DETAIL+".inCity LIKE \'%" + searchView +"%\'" +
		"or	"+TABLE_ENJOY_DETAIL+".inAddress LIKE \'%" + searchView+"%\'" +
		"or "+TABLE_ENJOY_DETAIL+".inPhone LIKE \'%" + searchView +"%\'" +
		"or "+TABLE_ENJOY_DETAIL+".inHours LIKE \'%" + searchView +"%\'";
		
		
	    return db.rawQuery(selection, null);
    }
	
	/*dinagdag ni Leq*/
	public static Cursor fetchCompanyByLocationSearch(String location) {

		return db.query(TABLE_ENJOY_DETAIL, TABLE_ENJOY_DETAIL_COLUMNS,
				"inCity like \"%" + location+"%\"", null, null, null, null);
    }
	
	public static Cursor mainDetailQuery(int id ){
		
		return db.query(TABLE_ENJOY_DETAIL, TABLE_ENJOY_DETAIL_COLUMNS, "_id=\"" + id +"\"" , null, null, null, null);
	}
	
	public static Cursor DetailQuery(String name){
		//System.out.print("QQQQQQQQQQQQQQWWWWWWWWWWWWWWWWW" + name);
		//return db.query("SELECT *" + "FROM" + TABLE_ENJOY_MAIN_COLUMNS + "WHERE companyName =" + name, null,null);
		return db.query(TABLE_ENJOY_MAIN, TABLE_ENJOY_MAIN_COLUMNS, "companyName=\"" + name+"\" ", null, null, null, null);
	}
	
	public static Cursor fetchCompanyBySearchListByLoc(String searchView, String location) {
		String selection = "SELECT * FROM "+TABLE_ENJOY_DETAIL+"   " +
		"WHERE "+TABLE_ENJOY_DETAIL+".companyName = \'" +  searchView +"\'" +
				"AND "+TABLE_ENJOY_DETAIL+".inCity = \'" +  location +"\'";
		
		
	    return db.rawQuery(selection, null);
    }
	
	public static Cursor fetchCompanyBySearchByLoc(CharSequence constraint, String location) {
		String selection = "SELECT * FROM "+TABLE_ENJOY_DETAIL+" " +
				"WHERE "+TABLE_ENJOY_DETAIL+".inCity = \'" +  location +"\'" +
				"AND ("+TABLE_ENJOY_DETAIL+".companyName like \'%" +  constraint.toString().toLowerCase() +"%\' " +
				"or "+TABLE_ENJOY_DETAIL+".inSection LIKE \'%" +  constraint.toString().toLowerCase() +"%\' " +
				"or "+TABLE_ENJOY_DETAIL+".inCity LIKE \'%" +  constraint.toString().toLowerCase() +"%\'" +
				"or	"+TABLE_ENJOY_DETAIL+".inAddress LIKE \'%" +  constraint.toString().toLowerCase()+"%\'" +
				"or "+TABLE_ENJOY_DETAIL+".inPhone LIKE \'%" +  constraint.toString().toLowerCase() +"%\'" +
				"or "+TABLE_ENJOY_DETAIL+".inHours LIKE \'%" +  constraint.toString().toLowerCase() +"%\')";
		
		
	    return db.rawQuery(selection, null);
		
		}
	
	
	
/*dinagdag ni Leq*/
	
	public static Cursor fetchCompanyBySearchAll(CharSequence constraint) {
		String selection = "SELECT * FROM "+TABLE_ENJOY_DETAIL+"   " +
		"WHERE "+TABLE_ENJOY_DETAIL+".companyName like \'%" +  constraint.toString().toLowerCase() +"%\' " +
		"or "+TABLE_ENJOY_DETAIL+".inSection LIKE \'%" +  constraint.toString().toLowerCase() +"%\' " +
		"or "+TABLE_ENJOY_DETAIL+".inCity LIKE \'%" +  constraint.toString().toLowerCase() +"%\'" +
		"or	"+TABLE_ENJOY_DETAIL+".inAddress LIKE \'%" +  constraint.toString().toLowerCase()+"%\'" +
		"or "+TABLE_ENJOY_DETAIL+".inPhone LIKE \'%" +  constraint.toString().toLowerCase() +"%\'" +
		"or "+TABLE_ENJOY_DETAIL+".inHours LIKE \'%" +  constraint.toString().toLowerCase() +"%\'";
		
		
	    return db.rawQuery(selection, null);
		
		}
	
	public static Cursor fetchCompanyBySearchByLocMain(CharSequence constraint, String location) {
		String selection = "SELECT * FROM "+TABLE_ENJOY_MAIN+" " +
				"WHERE "+TABLE_ENJOY_MAIN+".inCity like \'%" +  location +"%\' " +
				"AND ("+TABLE_ENJOY_MAIN+".companyName like \'%" +  constraint.toString().toLowerCase() +"%\' " +
				"or "+TABLE_ENJOY_MAIN+".inSection LIKE \'%" +  constraint.toString().toLowerCase() +"%\' " +
				"or "+TABLE_ENJOY_MAIN+".inCity LIKE \'%" +  constraint.toString().toLowerCase() +"%\'" +
				"or	"+TABLE_ENJOY_MAIN+".addressKey LIKE \'%" +  constraint.toString().toLowerCase()+"%\'";
		
		
	    return db.rawQuery(selection, null);
		
		}
	
	public static Cursor fetchCompanyBySearchAllMain(CharSequence constraint) {
		String selection = "SELECT * FROM "+TABLE_ENJOY_MAIN+"   " +
		"WHERE "+TABLE_ENJOY_MAIN+".companyName like \'%" +  constraint.toString().toLowerCase() +"%\' " +
		"or "+TABLE_ENJOY_MAIN+".inSection LIKE \'%" +  constraint.toString().toLowerCase() +"%\' " +
		"or "+TABLE_ENJOY_MAIN+".inCity LIKE \'%" +  constraint.toString().toLowerCase() +"%\'" +
		"or	"+TABLE_ENJOY_MAIN+".addressKey LIKE \'%" +  constraint.toString().toLowerCase()+"%\'";
		
		
	    return db.rawQuery(selection, null);
		
		}
}
