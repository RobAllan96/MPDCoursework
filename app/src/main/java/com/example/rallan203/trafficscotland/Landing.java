package com.example.rallan203.trafficscotland;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


/**
 * Created by rallan203 on 3/23/2018.
 * Matriculation Number: S1427235
 */

public class Landing extends AppCompatActivity {

        private Button button;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_landing);
            button = (Button)findViewById(R.id.Button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity();
                }


            });

        }

        public void MainActivity() {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }



    }


