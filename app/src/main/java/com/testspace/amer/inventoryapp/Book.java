package com.testspace.amer.inventoryapp;

import android.database.Cursor;

import java.util.ArrayList;

public class Book {
    private long id;
    private String name;
    private double price;
    private int quantity;
    private String supplierName;
    private String supplierPhoneNumber;
    public static final int UNKNOWN_INT_VALUE = -1;
    public static final String UNKNOWN_STRING_VALUE = "unknown";
    public static final double UNKNOWN_FLOAT_VALUE = -1.0;

    public Book(long id, String name, double price, int quantity, String supplierName, String supplierPhoneNumber) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.supplierName = supplierName;
        this.supplierPhoneNumber = supplierPhoneNumber;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return (double) Math.round(price * 100) / 100;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSupplierPhoneNumber() {
        return supplierPhoneNumber;
    }

    public static ArrayList<Book> extractBooksFromCursor(Cursor cursor) {
        long id;
        String name;
        double price;
        int quantity;
        String supplierName;
        String supplierPhoneNumber;
        ArrayList<Book> books = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                int indexOfId = cursor.getColumnIndex(InventoryContract.BooksEntry._ID);
                int indexOfName = cursor.getColumnIndex(InventoryContract.BooksEntry.COLUMN_BOOK_NAME);
                int indexOfPrice = cursor.getColumnIndex(InventoryContract.BooksEntry.COLUMN_BOOK_PRICE);
                int indexOfQuantity = cursor.getColumnIndex(InventoryContract.BooksEntry.COLUMN_BOOK_QUANTITY);
                int indexOfSupplierName = cursor.getColumnIndex(InventoryContract.BooksEntry.COLUMN_BOOK_SUPPLIER_NAME);
                int indexOfSupplierNumber = cursor.getColumnIndex(InventoryContract.BooksEntry.COLUMN_BOOK_SUPPLIER_PHONE_NUMBER);
                if (indexOfId == -1)
                    id = UNKNOWN_INT_VALUE;
                else
                    id = cursor.getLong(indexOfId);
                if (indexOfName == -1)
                    name = UNKNOWN_STRING_VALUE;
                else
                    name = cursor.getString(indexOfName);
                if (indexOfPrice == -1)
                    price = UNKNOWN_FLOAT_VALUE;
                else
                    price = cursor.getFloat(indexOfPrice);
                if (indexOfQuantity == -1)
                    quantity = UNKNOWN_INT_VALUE;
                else
                    quantity = cursor.getInt(indexOfQuantity);
                if (indexOfSupplierName == -1)
                    supplierName = UNKNOWN_STRING_VALUE;
                else
                    supplierName = cursor.getString(indexOfSupplierName);
                if (indexOfSupplierNumber == -1)
                    supplierPhoneNumber = UNKNOWN_STRING_VALUE;
                else
                    supplierPhoneNumber = cursor.getString(indexOfSupplierNumber);
                books.add(new Book(id, name, price, quantity, supplierName, supplierPhoneNumber));
            }
        }finally {
            cursor.close();
        }
        return books;
    }
}