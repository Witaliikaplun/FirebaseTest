package com.example.firebasetest.realtimedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firebasetest.Model;
import com.example.firebasetest.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/*
* пример записи в базу данных реального времени Realtime Database
* */
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
                //myDBRef.setValue(model);//так происходит перезапись данных
                myDBRef.push().setValue(model);//так происходит добавление данных
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