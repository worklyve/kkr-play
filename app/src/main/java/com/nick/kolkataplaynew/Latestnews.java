package com.nick.kolkataplaynew;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Latestnews extends AppCompatActivity {
    private RecyclerView recyclerView;
    myadapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latestnews);



        Toast.makeText(this,"Loading! please Wait.", Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayoutManager layout_manager = new LinearLayoutManager(this);
        layout_manager.setReverseLayout(true);
        layout_manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layout_manager);

        FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("newsFeeds"), model.class)
                        .build();
        adapter = new myadapter(options);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
       
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}