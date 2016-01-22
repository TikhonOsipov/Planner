package com.tixon.planner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tixon.planner.Cost;
import com.tixon.planner.Income;
import com.tixon.planner.Saving;

import java.util.ArrayList;

/**
 * Created by tikhon on 18/01/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "planner_db_name";
    public static final int DB_VERSION = 2;

    public static final String TABLE_COSTS = "table_costs";
    public static final String TABLE_INCOMES = "table_incomes";
    public static final String TABLE_SAVINGS = "table_savings";

    public static final String UID = "_id";
    public static final String COST_NAME = "cost_name";
    public static final String COST_VALUE = "cost_value";
    public static final String COST_TIME = "cost_time";
    public static final String INCOME_NAME = "income_name";
    public static final String INCOME_VALUE = "income_value";
    public static final String INCOME_TIME = "income_time";
    public static final String SAVING_NAME = "saving_name";
    public static final String SAVING_VALUE = "saving_value";
    public static final String SAVING_TIME = "saving_time";

    public static final String CREATE_TABLE_COSTS = "create table " + TABLE_COSTS + " (" +
            UID + " integer primary key autoincrement, " + COST_NAME + " text, " +
            COST_VALUE + " real, " + COST_TIME + " integer" + ");";

    public static final String CREATE_TABLE_INCOMES = "create table " + TABLE_INCOMES + " (" +
            UID + " integer primary key autoincrement, " + INCOME_NAME + " text, " +
            INCOME_VALUE + " real, " + INCOME_TIME + " integer" + ");";

    public static final String CREATE_TABLE_SAVINGS = "create table " + TABLE_SAVINGS + " (" +
            UID + " integer primary key autoincrement, " + SAVING_NAME + " text, " +
            SAVING_VALUE + " real, " + SAVING_TIME + " integer" + ");";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COSTS);
        db.execSQL(CREATE_TABLE_INCOMES);
        db.execSQL(CREATE_TABLE_SAVINGS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ArrayList<Cost> costs = new ArrayList<>();
        ArrayList<Income> incomes = new ArrayList<>();
        getCosts(db, costs); //сохранить имеющиеся данные
        getIncomes(db, incomes);

        deleteTable(db, TABLE_COSTS);
        deleteTable(db, TABLE_INCOMES);
        deleteTable(db, TABLE_SAVINGS);

        onCreate(db); //обновиться
        for(Cost cost: costs) {
            addCost(db, cost); //добавить сохранённые данные в новую таблицу
        }
        for(Income income: incomes) {
            addIncome(db, income);
        }
    }

    private void deleteTable(SQLiteDatabase db, String tableName) {
        db.execSQL("drop table if exists " + tableName);
    }

    //costs
    public void getCosts(SQLiteDatabase db, ArrayList<Cost> costs) {
        costs.clear();
        Cursor c = db.query(TABLE_COSTS, null, null, null, null, null, null);
        int costNameCI = c.getColumnIndex(COST_NAME);
        int costValueCI = c.getColumnIndex(COST_VALUE);
        int costTimeCI = c.getColumnIndex(COST_TIME);
        if(c.moveToFirst()) {
            do {
                costs.add(new Cost(c.getString(costNameCI),
                        c.getDouble(costValueCI), c.getLong(costTimeCI)));
            } while(c.moveToNext());
        }
        c.close();
    }

    public void getLastCost(SQLiteDatabase db, ArrayList<Cost> costs) {
        Cursor c = db.query(TABLE_COSTS, null, null, null, null, null, null);
        int costNameCI = c.getColumnIndex(COST_NAME);
        int costValueCI = c.getColumnIndex(COST_VALUE);
        int costTimeCI = c.getColumnIndex(COST_TIME);
        if(c.moveToLast()) {
            costs.add(new Cost(c.getString(costNameCI),
                    c.getDouble(costValueCI), c.getLong(costTimeCI)));
        }
        c.close();
    }

    public void addCost(SQLiteDatabase db, Cost cost) {
        ContentValues cv = new ContentValues();
        cv.put(COST_NAME, cost.getName());
        cv.put(COST_VALUE, cost.getValue());
        cv.put(COST_TIME, cost.getTime());
        db.insert(TABLE_COSTS, null, cv);
    }

    //incomes
    public void getIncomes(SQLiteDatabase db, ArrayList<Income> incomes) {
        incomes.clear();
        Cursor c = db.query(TABLE_INCOMES, null, null, null, null, null, null);
        int incomeNameCI = c.getColumnIndex(INCOME_NAME);
        int incomeValueCI = c.getColumnIndex(INCOME_VALUE);
        int incomeTimeCI = c.getColumnIndex(INCOME_TIME);
        if(c.moveToFirst()) {
            do {
                incomes.add(new Income(c.getString(incomeNameCI),
                        c.getDouble(incomeValueCI), c.getLong(incomeTimeCI)));
            } while(c.moveToNext());
        }
        c.close();
    }

    public void getLastIncome(SQLiteDatabase db, ArrayList<Income> incomes) {
        Cursor c = db.query(TABLE_INCOMES, null, null, null, null, null, null);
        int incomeNameCI = c.getColumnIndex(INCOME_NAME);
        int incomeValueCI = c.getColumnIndex(INCOME_VALUE);
        int incomeTimeCI = c.getColumnIndex(INCOME_TIME);
        if(c.moveToLast()) {
            incomes.add(new Income(c.getString(incomeNameCI),
                    c.getDouble(incomeValueCI), c.getLong(incomeTimeCI)));
        }
        c.close();
    }

    public void addIncome(SQLiteDatabase db, Income income) {
        ContentValues cv = new ContentValues();
        cv.put(INCOME_NAME, income.getName());
        cv.put(INCOME_VALUE, income.getValue());
        cv.put(INCOME_TIME, income.getTime());
        db.insert(TABLE_INCOMES, null, cv);
    }
    
    //savings
    public void getSavings(SQLiteDatabase db, ArrayList<Saving> savings) {
        savings.clear();
        Cursor c = db.query(TABLE_SAVINGS, null, null, null, null, null, null);
        int savingNameCI = c.getColumnIndex(SAVING_NAME);
        int savingValueCI = c.getColumnIndex(SAVING_VALUE);
        int savingTimeCI = c.getColumnIndex(SAVING_TIME);
        if(c.moveToFirst()) {
            do {
                savings.add(new Saving(c.getString(savingNameCI),
                        c.getDouble(savingValueCI), c.getLong(savingTimeCI)));
            } while(c.moveToNext());
        }
        c.close();
    }

    public void getLastSaving(SQLiteDatabase db, ArrayList<Saving> savings) {
        Cursor c = db.query(TABLE_SAVINGS, null, null, null, null, null, null);
        int savingNameCI = c.getColumnIndex(SAVING_NAME);
        int savingValueCI = c.getColumnIndex(SAVING_VALUE);
        int savingTimeCI = c.getColumnIndex(SAVING_TIME);
        if(c.moveToLast()) {
            savings.add(new Saving(c.getString(savingNameCI),
                    c.getDouble(savingValueCI), c.getLong(savingTimeCI)));
        }
        c.close();
    }

    public void addSaving(SQLiteDatabase db, Saving saving) {
        ContentValues cv = new ContentValues();
        cv.put(SAVING_NAME, saving.getName());
        cv.put(SAVING_VALUE, saving.getValue());
        cv.put(SAVING_TIME, saving.getTime());
        db.insert(TABLE_SAVINGS, null, cv);
    }
    
    //total sums
    public double getCostsTotalValue(SQLiteDatabase db) {
        double sum = 0;
        Cursor c = db.query(TABLE_COSTS, null, null, null, null, null, null);
        int costValueCI = c.getColumnIndex(COST_VALUE);
        if(c.moveToFirst()) {
            do {
                sum += c.getDouble(costValueCI);
            } while(c.moveToNext());
        }
        c.close();
        return sum;
    }

    public double getIncomesTotalValue(SQLiteDatabase db) {
        double sum = 0;
        Cursor c = db.query(TABLE_INCOMES, null, null, null, null, null, null);
        int incomeValueCI = c.getColumnIndex(INCOME_VALUE);
        if(c.moveToFirst()) {
            do {
                sum += c.getDouble(incomeValueCI);
            } while(c.moveToNext());
        }
        c.close();
        return sum;
    }

    public double getSavingsTotalValue(SQLiteDatabase db) {
        double sum = 0;
        Cursor c = db.query(TABLE_SAVINGS, null, null, null, null, null, null);
        int savingValueCI = c.getColumnIndex(SAVING_VALUE);
        if(c.moveToFirst()) {
            do {
                sum += c.getDouble(savingValueCI);
            } while(c.moveToNext());
        }
        c.close();
        return sum;
    }

    //delete all
    public void deleteAllCosts(SQLiteDatabase db) {
        db.delete(TABLE_COSTS, null, null);
    }

    public void deleteAllIncomes(SQLiteDatabase db) {
        db.delete(TABLE_INCOMES, null, null);
    }

    public void deleteAllSavings(SQLiteDatabase db) {
        db.delete(TABLE_SAVINGS, null, null);
    }

}
