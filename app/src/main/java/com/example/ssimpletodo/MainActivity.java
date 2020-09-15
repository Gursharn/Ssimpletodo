package com.example.ssimpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import org.apache.commons.io.FileUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;

    Button button;
    EditText edit;
    RecyclerView rvitems;
    ItemsAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        edit = findViewById(R.id.edit);
        rvitems = findViewById(R.id.rvitems);


        loadItems();


        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };



        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvitems.setAdapter(itemsAdapter);
        rvitems.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String todoItem= edit.getText().toString();
               // add item to model
                items.add(todoItem);
                itemsAdapter.notifyItemInserted(items.size() - 1);
                edit.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        });
        }

        private File getDataFile() {
            return new File(getFilesDir(), "data.text");
        }
        private void loadItems(){
            try {
                items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
            } catch (IOException e) {
                Log.e("MainActivity", "Error reading items", e);
                items = new ArrayList<>();
            }
        }
        private void saveItems(){
            try {
                FileUtils.writeLines(getDataFile(), items);
            } catch (IOException e) {
                Log.e("MainActivity", "Error reading items", e);
            }
        }

    }
