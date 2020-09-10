package com.example.firebasetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText etName, etSecondName, etDescription;
    Button buttonSendDB;
    private final String MY_DATABASE = "mydatabase";
    FirebaseDatabase myDB;
    DatabaseReference myDBRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        buttonSendDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String secondName = etSecondName.getText().toString();
                String dicript = etDescription.getText().toString();

                Model model = new Model(name, secondName, dicript);
                myDBRef.push().setValue(model);
            }
        });


    }
    private void init(){
        etName = findViewById(R.id.etName);
        etSecondName = findViewById(R.id.etSecondName);
        etDescription = findViewById(R.id.etDiscription);
        buttonSendDB = findViewById(R.id.btnSendDB);
        myDB = FirebaseDatabase.getInstance();
        myDBRef = myDB.getReference(MY_DATABASE);
    }
}