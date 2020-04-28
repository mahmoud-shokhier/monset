package com.example.abdelhameedahmed.monset;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity {
    Intent intent,intent1;
    ToggleButton toggle;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                intent1=new Intent(this,Main2Activity.class);
                startActivity(intent1);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent=new Intent(this,jop.class);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        toggle = (ToggleButton) findViewById(R.id.toggleButton2);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    Drawable drawable=getDrawable(R.drawable.aar_ic_stop);
                    toggle.setBackground(drawable);
                    star();
                } else {
                    // The toggle is disabled

                    Drawable drawable=getDrawable(R.drawable.aar_ic_play);
                    toggle.setBackground(drawable);
                    stop();
                }
            }
        });


    }






    public void star() {

        startService(intent);
    }
    public void stop() {

        intent.putExtra("tag",0);
        stopService(intent);
    }



}

