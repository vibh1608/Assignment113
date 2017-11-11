package com.example.user.assignment113;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //creating reference of DataBaseHelper
    DataBaseHelper mDataBaseHelper;

    //creating reference of Textviews
    public TextView name;
    public TextView age;

    //creating refernce of Imageview
    public ImageView imageView;

    //onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting reference to it's ID's
        name=(TextView) findViewById(R.id.name1);
        age=(TextView)findViewById(R.id.age);
        imageView=(ImageView)findViewById(R.id.image1);


        //setting refernce of DataBaseHelper class
      mDataBaseHelper = new DataBaseHelper(MainActivity.this,Constants.TABLE_NAME,null,Constants.DATABASE_VERSION);


      //TODO:INSERT DATA TO THE TABLE
        insertRecordInTable();

        //Setting the Views According to the information in the object.
        ArrayList<Employee> employees = mDataBaseHelper.getAllEmployee();

        //initialising the employee details
        Employee employee= employees.get(0);
        name.setText("  Name : "+employee.employee_name);
        age.setText("  Age   : "+employee.employee_age);
        imageView.setImageBitmap(employee.employee_photo);

    }

    //method to insert employee record into the database
    private void insertRecordInTable() {

        //setting employee details
        Employee employee = new Employee();
        employee.employee_name="Venkat";
        employee.employee_age="25";

        //setting employee photo in bitmap format
        employee.employee_photo=BitmapFactory.decodeResource(getResources(),R.drawable.download);
        mDataBaseHelper.insertEmployeeRecords(employee);
    }//end of insertRecordInTable method

}//end of mainActivity
