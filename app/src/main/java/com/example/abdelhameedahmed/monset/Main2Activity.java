package com.example.abdelhameedahmed.monset;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    TextView textView;
    ArrayAdapter<String> adapter;
    ArrayList<String> list;
    File file;
    FileReader reader;
    BufferedReader bufferedReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textView=(TextView)findViewById(R.id.textView);
        list=new ArrayList<String>();
        file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"history.txt");
        try {
            reader=new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bufferedReader=new BufferedReader(reader);
        String line="";
        try {
            while ((line=bufferedReader.readLine())!=null){
                list.add(line);
            }
        } catch (IOException e) {
            Toast.makeText(this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
        }
        if(list.isEmpty()){
            textView.setVisibility(View.VISIBLE);

        }else {
            textView.setVisibility(View.INVISIBLE);

        }
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        listView=(ListView)findViewById(R.id.list_history);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.bin:
                delete_content();
                textView.setVisibility(View.VISIBLE);


        }
        return super.onOptionsItemSelected(item);
    }
    public void delete_content(){
        adapter.clear();

        if(file.delete()){
            file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"history.txt");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Toast.makeText(this,file.getTotalSpace()+"",Toast.LENGTH_SHORT).show();
    }
}
