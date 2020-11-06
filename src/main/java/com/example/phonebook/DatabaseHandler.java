package com.example.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 9;

    // Database Name
    private static final String DATABASE_NAME = "contact_details",

    // Contacts table name
    TABLE_CONTACTS = "contacts",
    // Contacts Table Columns names
    KEY_ID = "id",
            KEY_NAME = "name",
            KEY_PH_NO = "phone_number",
            KEY_EMAIL = "email",
            KEY_HOME_PAGE = "home_page_address";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_INFO_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME + " TEXT UNIQUE, "
                + KEY_PH_NO + " TEXT," + KEY_EMAIL + " TEXT," + KEY_HOME_PAGE + " TEXT)";
        db.execSQL(CREATE_CONTACTS_INFO_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addContact(contact_info contacts) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contacts.getName()); // Contact Name
        values.put(KEY_PH_NO, contacts.getPhoneNumber()); // Contact Phone
        values.put(KEY_EMAIL, contacts.getEmail()); //  Contact Email
        values.put(KEY_HOME_PAGE, contacts.getHome_page()); //  Contact Home Page Address
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Close database connection
    }


    // Get the count of contact
    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACTS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close(); // Close cursor
        return count;  // return count
    }


    // Get the info of all the contact
    public ArrayList<String> getAllContact() {
        ArrayList<String> array_list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from contacts", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            array_list.add(cursor.getString(cursor.getColumnIndex(KEY_NAME))); //get contact name
            array_list.add(cursor.getString(cursor.getColumnIndex(KEY_ID))); //get contact ID
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return array_list; //return array list

    }


    // Get the information of a particular contact using contact ID
    public contact_info getContactInformation(int id_to_retrieve) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name,phone_number,email,home_page_address FROM contacts WHERE id=?", new String[]{id_to_retrieve + ""});
        cursor.moveToFirst();
        contact_info contact = new contact_info((cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        return contact; //return contact_info object
    }


    // Delete the contact using contact ID
    public void deleteContact(int id_to_retrieve) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("DELETE FROM contacts WHERE id=?", new String[]{id_to_retrieve + ""});
        cursor.moveToFirst();
        cursor.close();
        db.close();
    }

    // Edit the contact
    public void editContact(int id_to_edit, String number, String email1, String home_page_address1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PH_NO, number); // Contact Phone
        values.put(KEY_EMAIL, email1); // Contact Email
        values.put(KEY_HOME_PAGE, home_page_address1); // Contact Home Page Address
        db.update(TABLE_CONTACTS, values, KEY_ID + "=" + id_to_edit, null);
        db.close();
    }
}


