package com.testspace.amer.inventoryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button databaseDummyValuesInsertsButton;
    TextView databaseValuesHolderTextView;
    InventoryDbHelper dbHelper;
    SQLiteDatabase writableDatabase;
    SQLiteDatabase readableDatabase;
    ArrayList<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseDummyValuesInsertsButton = findViewById(R.id.databaseDummyValuesInsertsButton);
        databaseValuesHolderTextView = findViewById(R.id.databaseValuesHolderTextView);
        dbHelper = new InventoryDbHelper(this);
        writableDatabase = dbHelper.getWritableDatabase();
        readableDatabase = dbHelper.getReadableDatabase();
        books = getAllBooksFromDb();
        if (books != null && !books.isEmpty())
            displayAllBooks(books);
        databaseDummyValuesInsertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long rowId = insertDummyValues();
                if (rowId != -1)
                    displayNewInsertedVales(rowId);
                else
                    Toast.makeText(MainActivity.this, "Error While Inserting to DB", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ArrayList<Book> getAllBooksFromDb() {
        Cursor cursor = InventoryContract.BooksEntry.getAllBooks(readableDatabase);
        return Book.extractBooksFromCursor(cursor);
    }

    private void displayAllBooks(ArrayList<Book> books) {
        String toDisplayText = "";
        for (Book nextBook : books) {
            toDisplayText = toDisplayText
                    .concat(String.valueOf(nextBook.getId())).concat(" | ")
                    .concat(nextBook.getName()).concat(" | ")
                    .concat(String.valueOf(nextBook.getPrice())).concat("$ | ")
                    .concat(String.valueOf(nextBook.getQuantity())).concat(" | ")
                    .concat(nextBook.getSupplierName()).concat(" | ")
                    .concat(nextBook.getSupplierPhoneNumber()).concat("\n");
        }
        databaseValuesHolderTextView.setText(toDisplayText.trim());
    }

    private long insertDummyValues() {
        return InventoryContract.BooksEntry.insertBookToDB(writableDatabase, "Book1", 15.65, 37, "Name", "123456789");
    }

    private void displayNewInsertedVales(long rowId) {
        Cursor cursor = InventoryContract.BooksEntry.getBookById(rowId, readableDatabase);
        ArrayList<Book> tempBooks = Book.extractBooksFromCursor(cursor);
        if (tempBooks == null || tempBooks.isEmpty())
            return;
        Book nextBook = tempBooks.get(0);
        if (nextBook == null)
            return;
        books.add(nextBook);
        String toDisplayText = databaseValuesHolderTextView.getText().toString() + "\n";
        toDisplayText = toDisplayText
                .concat(String.valueOf(nextBook.getId())).concat(" | ")
                .concat(nextBook.getName()).concat(" | ")
                .concat(String.valueOf(nextBook.getPrice())).concat("$ | ")
                .concat(String.valueOf(nextBook.getQuantity())).concat(" | ")
                .concat(nextBook.getSupplierName()).concat(" | ")
                .concat(nextBook.getSupplierPhoneNumber());
        databaseValuesHolderTextView.setText(toDisplayText.trim());
    }
}