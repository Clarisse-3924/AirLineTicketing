package com.example.bookinginternationalcar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reff;
    EditText Name,Email,Initial,Destination,Date,Time,pass;
    Button Submit;
    ViewFlipper fli;
    International book;
    DrawerLayout drawerLayout;
    private int mHour, mMinute,mSecond,mYear, mMonth, mDay;
    String format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        Name = (EditText) findViewById(R.id.name);
        Email = (EditText) findViewById(R.id.email);
        Initial = (EditText) findViewById(R.id.hotel);
        Date = (EditText) findViewById(R.id.number);
        Destination = (EditText) findViewById(R.id.room);
        pass=findViewById(R.id.roo);
        Time = (EditText) findViewById(R.id.Number2);
        reff = FirebaseDatabase.getInstance().getReference().child("Book");
        Submit = (Button) findViewById(R.id.button6);
        book = new International();
        drawerLayout = findViewById(R.id.drawer_layout);


        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String sDate=year+ "-"+(monthOfYear + 1)+"-"+dayOfMonth;
                                Date.setText(sDate);
                                //entryDate.setText(year+ "-"+(monthOfYear + 1)+"-"+dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mSecond=c.get(Calendar.SECOND);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = "PM";

                                }
                                else {

                                    format = "AM";
                                }

                                if((hourOfDay <= (c.get(Calendar.HOUR_OF_DAY)))&&
                                        (minute <= (c.get(Calendar.MINUTE)))){
                                    Toast.makeText(MainActivity.this, "You can't pick a previous hour",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Time.setText(hourOfDay + ":" + minute + ":" + mSecond);
                                }
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book.setName(Name.getText().toString().trim());
                book.setEmail(Email.getText().toString().trim());
                book.setDate(Date.getText().toString().trim());
                book.setDistination(Destination.getText().toString().trim());
                book.setInitial(Initial.getText().toString().trim());
                book.setTime(Time.getText().toString().trim());
                book.setPassport(pass.getText().toString());


                String name = Name.getText().toString().trim();
                String email = Email.getText().toString().trim();
                String date = Date.getText().toString().trim();
                String destination = Destination.getText().toString().trim();
                String time = Time.getText().toString().trim();
                String initial = Initial.getText().toString().trim();
                String Pass = pass.getText().toString().trim();


                if (TextUtils.isEmpty(Pass)) {
                    pass.setError(" required");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    Name.setError("Name required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email required");
                    return;
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {
                    Email.setError("Enter Valid email");
                    return;
                }
                if (TextUtils.isEmpty(initial)) {
                    Initial.setError("required");
                    return;
                }

                if (TextUtils.isEmpty(date)) {
                    Date.setError(" required");
                    return;
                }
                if (TextUtils.isEmpty(time)) {
                    Time.setError("required");
                    return;
                }
                if (TextUtils.isEmpty(destination)) {
                    Destination.setError("required");
                    return;
                }

                String id = reff.push().getKey();
                reff.child(id).setValue(book);
                Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "THANK YOU FOR BOOKING", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void ClickMenu(View view){
        //open drawe
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        // open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        //close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //close drawer layout
        //check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //when drawer is open
            //close drawe
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
//    public void Clickcontact(View view){
//        //redirect about us
//        MainActivity.redirectActivity(this,contact.class);
//    }
    public void ClickSignUp(View view){
        //redirect about us
        MainActivity.redirectActivity(this,SignUp.class);
    }
    public void ClickMap(View view){
        //redirect about us
        MainActivity.redirectActivity(this,Map.class);
    }
    public void ClickLogin(View view){
        //redirect about us
        MainActivity.redirectActivity(this,Login.class);
    }
    public void ClickAdmin(View view){
        //redirect about us
        MainActivity.redirectActivity(this,AdminLogin.class);
    }

    public void ClickLogout(View view){
        //close app
        logout(this);
    }

    public static void logout(final Activity activity) {
        //initialize dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //set title
        builder.setTitle("Logout");
        //set meassage
        builder.setMessage("Are you sure you want to logout ?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Finish activity
                activity.finishAffinity();
                //exit app
                System.exit(0);
            }
        });
        //negative button
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dismiss dialog
                dialog.dismiss();
            }
        });
        //show dialog
        builder.show();
    }

    public static void redirectActivity(Activity activity,Class aclass) {
        //Initialize intent
        Intent intent = new Intent(activity,aclass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
    @Override
    protected void onPause(){
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }




}