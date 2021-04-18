package com.example.typego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity {

    User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toast.makeText(this, Locale.getDefault().getDisplayLanguage(), Toast.LENGTH_LONG).show();





    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userInfo = database.getReference("Tet");

        //userInfo.setValue(new User(userInfo.hashCode(), "Callipso", "123"));
        userInfo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //for (DataSnapshot postsnapshot : snapshot.getChildren()) {
                    currentUser = snapshot.getValue(User.class);
                //    break;
                //}
                Log.d("Auth",  currentUser.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                currentUser = new User(userInfo.hashCode(), "Tet", "123");
                currentUser.setLastResult(30);
                currentUser.setBestResult(40);
                Log.d("Auth",  currentUser.getUserName());
            }
        });

    }

    public void startTesting_OnClick(View v) {
        Intent intent = new Intent (MainMenuActivity.this, TestSetupActivity.class);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }
}