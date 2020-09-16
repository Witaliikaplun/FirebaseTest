package com.example.firebasetest.cloudfirestory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.firebasetest.Model;
import com.example.firebasetest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;

public class CloudFirestory extends AppCompatActivity {
    public static final String TAG = "myLog";
    TextView tvDB;
    Button buttonSendDB2;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_firestory);

        // Создаем ссылку на Cloud Firestore
         db = FirebaseFirestore.getInstance();
         init();

        final HashMap<String, Model> myHashMap = new HashMap<>();
        myHashMap.put("first", new Model("Петр", "Дроздов", "Машинист android"));
        myHashMap.put("second", new Model("Василий", "Динозавров", "Таксист android"));
        myHashMap.put("third", new Model("Иван", "Попов", "Тракторист android"));


        //получить данные один раз
        db.collection("users1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents.", task.getException());
                }
            }
        });

        //получать данные в реальном времени
        final DocumentReference docFef = db.collection("users1").document("alovelace");
        docFef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d(TAG, "Listen failed.", error);
                    return;
                }

                if (value != null && value.exists()) {
                    Log.d(TAG, "Current data: " + value.getData());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });






        buttonSendDB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users1").document("alovelace").set(myHashMap);
            }
        });
    }

    private void init(){
        tvDB = findViewById(R.id.tvDB);
        buttonSendDB2 = findViewById(R.id.btnSendDB2);
    }
}