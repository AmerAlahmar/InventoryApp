package com.testspace.amer.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class InventoryContract {
    private InventoryContract() {
    }

    public static final class BooksEntry implements BaseColumns {
        static final String TABLE_NAME = "books";
        static final String _ID = BaseColumns._ID;
        static final String _COUNT = BaseColumns._COUNT;
        static final String COLUMN_BOOK_NAME = "name";
        static final String COLUMN_BOOK_PRICE = "price";
        static final String COLUMN_BOOK_QUANTITY = "quantity";
        static final String COLUMN_BOOK_SUPPLIER_NAME = "supplier_name";
        static final String COLUMN_BOOK_SUPPLIER_PHONE_NUMBER = "supplier_phone_number";
        private static String[] columnsForProjection = new String[]{
                _ID,
                COLUMN_BOOK_NAME,
                COLUMN_BOOK_PRICE,
                COLUMN_BOOK_QUANTITY,
                COLUMN_BOOK_SUPPLIER_NAME,
                COLUMN_BOOK_SUPPLIER_PHONE_NUMBER
        };
        public static final String CREATE_BOOKS_TABLE_QUERY =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                        + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + COLUMN_BOOK_NAME + " TEXT NOT NULL, "
                        + COLUMN_BOOK_PRICE + " REAL NOT NULL DEFAULT " + String.valueOf(Book.UNKNOWN_FLOAT_VALUE) + ", "
                        + COLUMN_BOOK_QUANTITY + " INTEGER NOT NULL DEFAULT " + String.valueOf(Book.UNKNOWN_INT_VALUE) + ", "
                        + COLUMN_BOOK_SUPPLIER_NAME + " TEXT NOT NULL DEFAULT \"" + Book.UNKNOWN_STRING_VALUE + "\", "
                        + COLUMN_BOOK_SUPPLIER_PHONE_NUMBER + " TEXT NOT NULL DEFAULT \"" + Book.UNKNOWN_STRING_VALUE + "\" );";
        public static final String DROP_BOOKS_TABLE_QUERY =
                "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static Cursor getAllBooks(SQLiteDatabase readableDatabase) {
            return readableDatabase.query(TABLE_NAME, columnsForProjection, null, null, null, null, null);
        }

        public static long insertBookToDB(SQLiteDatabase writableDatabase, String name, double price, int quantity, String SName, String SNumber) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_BOOK_NAME, name);
            contentValues.put(COLUMN_BOOK_PRICE, price);
            contentValues.put(COLUMN_BOOK_QUANTITY, quantity);
            contentValues.put(COLUMN_BOOK_SUPPLIER_NAME, SName);
            contentValues.put(COLUMN_BOOK_SUPPLIER_PHONE_NUMBER, SNumber);
            return writableDatabase.insert(TABLE_NAME, null, contentValues);
        }

        public static Cursor getBookById(long rowId, SQLiteDatabase readableDatabase) {
            String selection = _ID + " = ?";
            String[] selectionArgs = new String[]{
                    String.valueOf(rowId)
            };
            return readableDatabase.query(TABLE_NAME, columnsForProjection, selection, selectionArgs, null, null, null);
        }
    }
}