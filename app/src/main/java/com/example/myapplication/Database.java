package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Database";
    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_APPOINTEMENT = "appointments";
    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création des tables
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL, " +
                "email TEXT NOT NULL UNIQUE)";
        db.execSQL(createUsersTable);
        Log.d(TAG, "Database tables created.");

        String createCartTable = "CREATE TABLE " + TABLE_CART + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "product TEXT NOT NULL, " +
                "price FLOAT NOT NULL, " +
                "otype TEXT NOT NULL, " +
                "FOREIGN KEY(user_id) REFERENCES " + TABLE_USERS + "(id) ON DELETE CASCADE)";
        db.execSQL(createCartTable);

        Log.d(TAG, "Database tables created.");

        String createAppointmentsTable = "CREATE TABLE appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "appointment_date TEXT NOT NULL, " + // Correspond au champ "date"
                "address TEXT NOT NULL, " +
                "phone TEXT NOT NULL, " +
                "title TEXT NOT NULL, " +
                "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)";
        db.execSQL(createAppointmentsTable);
        Log.d(TAG, "Appointments table created.");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
        Log.d(TAG, "Database upgraded from version " + oldVersion + " to " + newVersion);
    }


    // Login utilisateur
    public void register(String username, String email, String password) {
        if (username == null || password == null) {
            Log.e(TAG, "Username or password is null. Registration failed.");
            return;
        }

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);

        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_USERS, null, values);
        db.close();

        if (result == -1) {
            Log.e(TAG, "Failed to register user: " + username);
        } else {
            Log.d(TAG, "User registered successfully: " + username);
        }
    }
    // Login user with hashed password
    public int Login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        Log.d("Database", "Executing query: " + query);
        Log.d("Database", "Parameters: username = " + username + ", password = " + password);

        if (cursor != null && cursor.moveToFirst()) {
            // Si un utilisateur correspondant est trouvé
            Log.d("Database", "User found: " + username);
            cursor.close();
            return 1; // Connexion réussie
        } else {
            Log.d("Database", "User not found: " + username);
            cursor.close();
            return 0; // Si aucun utilisateur n'est trouvé
        }
    }


    public List<String> getUserInfoByName(String username) {
        List<String> userInfo = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int usernameIndex = cursor.getColumnIndex("username");
            int emailIndex = cursor.getColumnIndex("email");
            int passwordIndex = cursor.getColumnIndex("password");

            userInfo.add("ID: " + cursor.getInt(idIndex));
            userInfo.add("Username: " + cursor.getString(usernameIndex));
            userInfo.add("Email: " + cursor.getString(emailIndex));
            userInfo.add("Password: " + cursor.getString(passwordIndex)); // À éviter d'afficher en production
        } else {
            userInfo.add("No user found with username: " + username);
        }

        cursor.close();
        db.close();

        return userInfo;
    }


    // Ajouter un article au panier
    public void addCart(int userId, String product, float price, String otype) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("product", product);
        values.put("price", price);
        values.put("otype", otype);

        long result = db.insert(TABLE_CART, null, values);
        db.close();

        if (result == -1) {
            Log.e(TAG, "Failed to add item to cart: " + product);
        } else {
            Log.d(TAG, "Item added to cart successfully: " + product);
        }
    }

    public int CheckCart(int userId, String product) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE user_id = ? AND product = ?",
                new String[]{String.valueOf(userId), product});
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM " + TABLE_USERS + " WHERE username = ?", new String[]{username});

        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return userId;
    }
    // Vérifier si un article est dans le panier
    public boolean isProductInCart(int userId, String product) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM " + TABLE_CART + " WHERE user_id = ? AND product = ?",
                new String[]{String.valueOf(userId), product}
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return exists;
    }

    // Supprimer un article du panier
    public void removeCartItem(int userId, String product) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(TABLE_CART, "user_id = ? AND product = ?", new String[]{String.valueOf(userId), product});
        db.close();

        Log.d(TAG, "Removed " + rows + " items from cart for user ID: " + userId);
    }

    // Obtenir les articles du panier d'un utilisateur
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT product, price, otype FROM " + TABLE_CART + " WHERE user_id = ?",
                new String[]{String.valueOf(userId)}
        );

        while (cursor.moveToNext()) {
            String product = cursor.getString(0);
            float price = cursor.getFloat(1);
            String orderType = cursor.getString(2);
            cartItems.add(new CartItem(product, price, orderType));
            Log.d("CartItems", "Product: " + product + ", Price: " + price + ", OrderType: " + orderType);
        }

        cursor.close();
        db.close();
        return cartItems;
    }

    public float getCartTotal(int userId) {
        float total = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT price FROM " + TABLE_CART + " WHERE user_id = ?", new String[]{String.valueOf(userId)});

        while (cursor.moveToNext()) {
            total += cursor.getFloat(0);
        }
        cursor.close();
        db.close();

        return total;
    }

    public List<String> getOrdersByUser(String username) {
        List<String> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT product FROM Cart WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            do {
                orders.add(cursor.getString(0)); // Ajouter le nom du produit
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }
    public void deleteOrder(String username, String product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Cart", "username = ? AND product = ?", new String[]{username, product});
        db.close();
    }
    public void bookAppointment(Appointment appointment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("appointment_date", appointment.getDate() +" Time "+appointment.getTime() );
        values.put("address", appointment.getAddress());
        values.put("phone", appointment.getPhone());
        values.put("title", "Consultation");
        values.put("user_id", appointment.getUserId());


        long result = db.insert("appointments", null, values);
        if (result == -1) {
            Log.e(TAG, "Failed to book appointment: " + result);
        } else {
            Log.d(TAG, "Appointment booked successfully: " + result);
        }
        db.close();
    }

    public List<Appointment> getAppointmentsByUserId(int userId) {
        List<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        // Requête SQL pour récupérer les rendez-vous
        String query = "SELECT appointment_date, address, phone FROM appointments WHERE user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        Log.e(TAG, "Executing query: " + query);

        // Vérifier si des résultats ont été trouvés
        if (cursor.moveToFirst()) {
            do {
                // Récupérer les valeurs des colonnes
                String date = cursor.getString(0);  // appointment_date
                String address = cursor.getString(1);  // address
                String phone = cursor.getString(2);  // phone

                // Créer un objet Appointment et l'ajouter à la liste
                Appointment appointment = new Appointment("Date: "+date,"Address: " + address, "Phone:"+phone);
                appointments.add(appointment);
                Log.d(TAG, "Fetched Appointment:" + appointment);

                //Log.d(TAG, "Fetched Appointment: Date: " + date + ", Address: " + address + ", Phone: " + phone);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "No appointments found for user ID: " + userId);
        }

        cursor.close();
        db.close();

        return appointments;
    }


}

