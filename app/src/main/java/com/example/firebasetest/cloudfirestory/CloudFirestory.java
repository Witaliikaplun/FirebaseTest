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
import android.widget.Toast;

import com.example.firebasetest.Model;
import com.example.firebasetest.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CloudFirestory extends AppCompatActivity {
    public static final String TAG = "myLog";
    TextView tvDB;
    EditText editText;
    Button buttonSendDB2;
    FirebaseFirestore dbFirestore;
    DocumentReference docRef;
    Map<String, Object> myHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_firestory);

        // Создаем ссылку на Cloud Firestore
         dbFirestore = FirebaseFirestore.getInstance();
         init();
        //коллекция для отправки данных в Firebase
        myHashMap = new HashMap<>();
//        myHashMap.put("first", new Model("Петр", "Дроздов", "Машинист android"));
//        myHashMap.put("second", new Model("Василий", "Динозавров", "Таксист android"));
//        myHashMap.put("third", new Model("Иван", "Попов", "Тракторист android"));

        //получить данные один раз
        dbFirestore.collection("users1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
        readDataRealTime("users1", "alovelace");

        buttonSendDB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeDataOne("users1", "alovelace");
            }
        });
    }

    private void writeDataOne(String collectionPach, String documentPach){
        myHashMap.put("dritte", editText.getText().toString());
        dbFirestore.collection("users1").document("alovelace").update(myHashMap);
    }

    private void readDataRealTime(String collectionPach, String documentPach) {
        docRef = dbFirestore.collection(collectionPach).document(documentPach);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d(TAG, "Listen failed.", error);
                    return;
                }

                if (value != null && value.exists()) {
                    //пример чтения данных из Firestore. Данные прилетают в виде коллекции, которую
                    //можно преобразовать в массив
                    Log.d(TAG, "Current data: " + value.getData());
//                    tvDB.setText(value.getData().values().toArray()[0].toString());
//                    tvDB.append(value.getData().values().toArray()[1].toString());

                      tvDB.setText(value.getData().get("key").toString());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    private void init(){
        tvDB = findViewById(R.id.tvDB);
        buttonSendDB2 = findViewById(R.id.btnSendDB2);
        editText = findViewById(R.id.etDBwrite);
    }

//    //метод выборки данных по ключу
//    public String selectDataKey(DocumentSnapshot value, String key){
//        for (int i = 0; i < value.getData().size(); i++) {
//
//        }
//
//    }
}