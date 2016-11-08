package com.groceryguru.jaegar.groceryguru;

/**
 * Created by Jaegar on 2016-10-23.
 */

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Grocery.db";
    public static final String PRODUCTS_TABLE_NAME = "products";
    public static final String PRODUCTS_COLUMN_ID = "id";
    public static final String PRODUCTS_COLUMN_NAME = "name";
    public static final String PRODUCTS_COLUMN_PRICE = "price";
    public static final String PRODUCTS_COLUMN_NOTES = "notes";

    public static final String LISTS_TABLE_NAME = "lists";
    public static final String LISTS_COLUMN_ID = "id";
    public static final String LISTS_COLUMN_NAME = "name";

    public static final String PRODLIST_TABLE_NAME = "productsLists";
    public static final String PRODLIST_COLUMN_PRIMARY = "primaryID";
    public static final String PRODLIST_LIST_ID = "listID";
    public static final String PRODLIST_PRODUCT_ID = "productID";
    public static final String PRODLIST_PRODUCT_CHECKED = "productChecked";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + PRODUCTS_TABLE_NAME +
                        "(" + PRODUCTS_COLUMN_ID + " integer primary key," +
                        PRODUCTS_COLUMN_NAME + " text," +
                        PRODUCTS_COLUMN_PRICE + " integer," +
                        PRODUCTS_COLUMN_NOTES + " text)"
        );

        db.execSQL(
                "create table " + LISTS_TABLE_NAME +
                        "(" + LISTS_COLUMN_ID + " integer primary key," +
                        LISTS_COLUMN_NAME + " text)"
        );

        db.execSQL(
                "create table " + PRODLIST_TABLE_NAME +
                        "(" + PRODLIST_LIST_ID + " integer," +
                        PRODLIST_PRODUCT_ID + " integer," +
                        PRODLIST_PRODUCT_CHECKED + " integer," +
                        "FOREIGN KEY (" + PRODLIST_LIST_ID + ") REFERENCES " + LISTS_TABLE_NAME + "(" + LISTS_COLUMN_ID + ")," +
                        "FOREIGN KEY (" + PRODLIST_PRODUCT_ID + ") REFERENCES " + PRODUCTS_TABLE_NAME + "(" + PRODUCTS_COLUMN_ID + ")," +
                        "CONSTRAINT " + PRODLIST_COLUMN_PRIMARY + " PRIMARY KEY (" + PRODLIST_LIST_ID + "," + PRODLIST_PRODUCT_ID + "))"
        );
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + PRODUCTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LISTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PRODLIST_TABLE_NAME);
        onCreate(db);
        db.close();
    }

    public boolean insertProductIntoList(int listID, int productID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODLIST_LIST_ID, listID);
        contentValues.put(PRODLIST_PRODUCT_ID, productID);
        contentValues.put(PRODLIST_PRODUCT_CHECKED, 0);
        db.insert(PRODLIST_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public boolean insertProductIntoList(int listID, int productID, boolean checked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODLIST_LIST_ID, listID);
        contentValues.put(PRODLIST_PRODUCT_ID, productID);
        contentValues.put(PRODLIST_PRODUCT_CHECKED, checked ? 1 : 0);
        db.insert(PRODLIST_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public boolean updateProductCheckInList(int listID, int prodID, boolean checked) {
        listID += 1;
        String strSQL = "UPDATE " + PRODLIST_TABLE_NAME + " SET " + PRODLIST_PRODUCT_CHECKED + " = " +
                (checked ? 1 : 0) + " WHERE " + PRODLIST_LIST_ID + " = " + listID + " AND " +
                PRODLIST_PRODUCT_ID + " = " + prodID;

        this.getWritableDatabase().execSQL(strSQL);
        return true;
    }

    public boolean removeAllProductsInList(int listID) {
        listID += 1;
        String str = "DELETE FROM " + PRODLIST_TABLE_NAME +
                "WHERE " + PRODLIST_LIST_ID + " = " + listID;

        this.getWritableDatabase().execSQL(str);
        return true;
    }

    public Product getProductFromList(int listID, int productID) {
        listID += 1; productID += 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PRODLIST_TABLE_NAME + " where " + PRODLIST_LIST_ID + " = "+listID+" AND " + PRODLIST_PRODUCT_ID + " = " + productID + "", null );
        res.moveToFirst();
        Log.d("COUNTS", "amount " + res.getCount());
        try {
            Product p = getProductByIDProduct(res.getInt(res.getColumnIndexOrThrow(DBHelper.PRODLIST_PRODUCT_ID)));
            if (p != null) {
                p.selected = res.getInt(res.getColumnIndexOrThrow(DBHelper.PRODLIST_PRODUCT_CHECKED)) == 1 ? true : false;
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
        return null;
    }

    public Product[] getAllProductsInList(int listID) {
        listID += 1;
        java.util.List<Product> prods = new java.util.ArrayList<Product>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PRODLIST_TABLE_NAME + " where " + PRODLIST_LIST_ID + "="+listID+"", null );
        res.moveToFirst();
        Log.d("WHY NOT????", "" + res.getCount());
        do {
            try {
                Product p = getProductByIDProduct(res.getInt(res.getColumnIndexOrThrow(DBHelper.PRODLIST_PRODUCT_ID)));
                if (p != null) {
                    p.selected = res.getInt(res.getColumnIndexOrThrow(DBHelper.PRODLIST_PRODUCT_CHECKED)) == 1 ? true : false;
                    prods.add(p);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            res.moveToNext();
        } while(!res.isAfterLast());
        return prods.toArray(new Product[prods.size()]);
    }

    public boolean insertProduct(String name, double price, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCTS_COLUMN_NAME, name);
        contentValues.put(PRODUCTS_COLUMN_PRICE, price);
        contentValues.put(PRODUCTS_COLUMN_NOTES, notes);
        db.insert(PRODUCTS_TABLE_NAME, null, contentValues);
        db.close();
        return true;
    }

    public long insertList(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LISTS_COLUMN_NAME, name);
        long rowID = db.insert(LISTS_TABLE_NAME, null, contentValues);
        db.close();
        return rowID;
    }

    public Cursor getListByID(int id) {
        id += 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + LISTS_TABLE_NAME + " where " + LISTS_COLUMN_ID + "="+id+"", null );
        return res;
    }

    public Cursor getListByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + LISTS_TABLE_NAME + " where " + LISTS_COLUMN_NAME + "="+name+"", null );
        return res;
    }


    public List getListByIDList(int id) {
        Cursor rs = getListByID(id);
        rs.moveToFirst();

        try {
            String name = rs.getString(rs.getColumnIndexOrThrow(DBHelper.LISTS_COLUMN_NAME));
            return new List(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            rs.close();
        }
    }

    public List[] getAllLists() {
        ArrayList<List> p = new ArrayList<List>();
        List tmp;
        int i = 0;
        tmp = getListByIDList(i++);
        while (tmp != null) {
            p.add(tmp);
            tmp = getListByIDList(i++);
        }
        return p.toArray(new List[p.size()]);
    }


    public String[] getAllListsString() {
        ArrayList<String> p = new ArrayList<String>();
        List tmp;
        int i = 0;
        tmp = getListByIDList(i++);
        while (tmp != null) {
            p.add(tmp.name);
            tmp = getListByIDList(i++);
        }
        return p.toArray(new String[p.size()]);
    }


    public Cursor getProductByID(int id){
        id += 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + PRODUCTS_TABLE_NAME + " where " + PRODUCTS_COLUMN_ID + "="+id+"", null );
        return res;
    }

    public Product getProductByIDProduct(int id) {
        Cursor rs = getProductByID(id);
        rs.moveToFirst();

        try {
            String name = rs.getString(rs.getColumnIndexOrThrow(DBHelper.PRODUCTS_COLUMN_NAME));
            int price = rs.getInt(rs.getColumnIndexOrThrow(DBHelper.PRODUCTS_COLUMN_PRICE));
            String notes = rs.getString(rs.getColumnIndexOrThrow(DBHelper.PRODUCTS_COLUMN_NOTES));
            return new Product(name, price, notes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            rs.close();
        }
    }

    public Product[] getAllProducts() {
        ArrayList<Product> p = new ArrayList<Product>();
        Product tmp;
        int i = 0;
        tmp = getProductByIDProduct(i++);
        while (tmp != null) {
            p.add(tmp);
            tmp = getProductByIDProduct(i++);
        }
        return p.toArray(new Product[p.size()]);
    }

    public Product[] getAllProductsWithListChecks(int listID) {
        ArrayList<Product> p = new ArrayList<Product>();
        Product tmp;
        int i = 0;
        do {
            tmp = getProductFromList(listID, i);
            if (tmp == null && 7 == 9) {
                Log.d("NO PRODUCT!!!!!!!!!!!!!", "none)");
                tmp = getProductByIDProduct(i);
            }
            if (tmp != null)
                p.add(tmp);
            i++;
        } while (tmp != null);
        return p.toArray(new Product[p.size()]);
    }

    public String[] getAllProductsString() {
        ArrayList<String> p = new ArrayList<String>();
        Product tmp;
        int i = 0;
        tmp = getProductByIDProduct(i++);
        while (tmp != null) {
            p.add(tmp.name);
            tmp = getProductByIDProduct(i++);
        }
        return p.toArray(new String[p.size()]);
    }

    /*
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
    */
}