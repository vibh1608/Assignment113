package com.example.user.assignment113;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by user on 09-11-2017.
 */

//DataBaseHelper class which is extending SQLiteOpenHelper class
public class DataBaseHelper extends SQLiteOpenHelper {


    //Constructor of class which is taking Context as a parameter.
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //onCreate method
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        //Creating String query.
        String createTable="CREATE TABLE " + Constants.TABLE_NAME + " (" + Constants.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + Constants.EMPLOYEE_NAME + " TEXT,"+Constants.EMPLOYEE_AGE + " TEXT," + Constants.EMPLOYEE_PIC + " BLOB)";

        //creating table to store data in database
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //method to insert employee record in database
    public long insertEmployeeRecords(Employee employee)
    {
        long count=0;

        //contentValues  used to insert data in database
        ContentValues contentValues = new ContentValues();

        //creating refernce of writable database
        SQLiteDatabase database = this.getWritableDatabase();
        try {

            //putting the values in the ContentValues object using key-value.
            contentValues.put(Constants.EMPLOYEE_NAME,employee.employee_name);
            contentValues.put(Constants.EMPLOYEE_AGE,employee.employee_age);


            //Converting Bitmap image to byte array.
            Bitmap bitmap=employee.employee_photo;
            ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
            byte[] img=byteArrayOutputStream.toByteArray();

            //putting image
            contentValues.put(Constants.EMPLOYEE_PIC,img);

            //count is counting the number of records in database
            count=database.insert(Constants.TABLE_NAME,null,contentValues);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return count;
    }//end of insertEmployeeRecords method


    //method to get the employee records
    public ArrayList<Employee> getAllEmployee()
    {
        ArrayList<Employee> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select * from "+Constants.TABLE_NAME;

        //Creating the cursor by using the rawQuery method of db.
        Cursor cursor = database.rawQuery(query,null);

        //creating employee object
        Employee employee = null;
        if (cursor!=null)
        {
            cursor.moveToFirst();
            do{
                employee = new Employee();

                //fetching the data into the employee object
                employee.employee_name= cursor.getString(1);
                employee.employee_age= cursor.getString(2);

                //Converting byte array into Bitmap Image.
                byte[] img=cursor.getBlob(3);
                employee.employee_photo= BitmapFactory.decodeByteArray(img,0,img.length);

                list.add(employee);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }//end of getAllEmployee method

}//end of DataBaseHelper class
