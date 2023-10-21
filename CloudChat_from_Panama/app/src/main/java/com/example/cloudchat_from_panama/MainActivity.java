package com.example.cloudchat_from_panama;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    Button button;
    EditText editText;
    ArrayList<String> messages=new ArrayList<>();
    RecyclerView recyclerView;
    private  static int MAX_MESSAGE_LENGTH=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        button=findViewById(R.id.send_message_b);
        editText=findViewById(R.id.message_input);
        recyclerView=findViewById(R.id.message_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DateAdapter adapter=new DateAdapter(this,messages);
        recyclerView.setAdapter(adapter);

    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String msg=editText.getText().toString();
            if(msg.equals("")){
                Toast.makeText(MainActivity.this, "Введите сообщение", Toast.LENGTH_SHORT).show();
                return;
            }
            if(msg.length()>MAX_MESSAGE_LENGTH){
                Toast.makeText(MainActivity.this, "Максималное количество в сообщении 100 символов", Toast.LENGTH_SHORT).show();
                return;
            }
            myRef.push().setValue(msg);
            editText.setText("");
        }
    });

    myRef.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            String msg=snapshot.getValue(String.class);
            messages.add(msg);
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messages.size());
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }
}