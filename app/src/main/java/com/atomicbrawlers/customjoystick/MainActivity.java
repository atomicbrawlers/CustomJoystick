package com.atomicbrawlers.customjoystick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private FirstCustomView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //myView = (FirstCustomView) findViewById(R.id.custView);
    }

    public void buttonPressed(View view){
        myView.setCircleColor(Color.GREEN);
        myView.setLabelColor(Color.MAGENTA);
        myView.setLabelText("Help");
    }
}
