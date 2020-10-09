package com.example.firebasetest.cloudfirestory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentFirestore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFirestore extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "myLog";
    View view;
    private TextView tvDB;
    private EditText editText;
    private Button buttonSendDB2;
    private FirebaseFirestore dbFirestore;
    private DocumentReference docRef;
    private Map<String, Object> myHashMap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentFirestore() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentFirestore.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentFirestore newInstance(String param1, String param2) {
        FragmentFirestore fragment = new FragmentFirestore();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_firestore, container, false);

        // Создаем ссылку на Cloud Firestore
        dbFirestore = FirebaseFirestore.getInstance();
        init();
        //коллекция для отправки данных в Firebase
        myHashMap = new HashMap<>();


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

        return view;
    }
    private void init(){
        tvDB = view.findViewById(R.id.tvDB);
        buttonSendDB2 = view.findViewById(R.id.btnSendDB2);
        editText = view.findViewById(R.id.etDBwrite);
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
                    /*пример чтения данных из Firestore. Данные прилетают в виде коллекции, которую
                    можно преобразовать в массив
                    tvDB.setText(value.getData().values().toArray()[0].toString());
                    tvDB.append(value.getData().values().toArray()[1].toString());*/
                    Log.d(TAG, "Current data: " + value.getData());
                    tvDB.setText(value.getData().get("key").toString());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }
}