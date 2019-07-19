package com.project.laborientation;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.laborientation.Quiz.Category;
import com.project.laborientation.Quiz.QuizDbHelper;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_QUIZ = 1;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "sharedPrefs";

    private TextView textViewHighscore;
    private Spinner spinnerCategory;

    private int highscore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHighscore = findViewById(R.id.text_view_highscore);
        loadCategories();
        loadHighscore();

        Button identify_equipment = findViewById(R.id.identify_equipment);
        Button health_safety = findViewById(R.id.health_safety);
        Button interactive_equipment = findViewById(R.id.interactive_equipment);
        Button model_placement = findViewById(R.id.model_placement);


        identify_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(getBaseContext(), CameraActivity.class);
                startActivityForResult(cameraIntent, 1);
              //  startActivity(cameraIntent);
            }
        });
        // HEALTH AND SAFETY NEEDS IMPLEMENTATION
//        health_safety.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent healthSafetyIntent = new Intent(getBaseContext(), ImageArActivity.class);
//                //  startActivityForResult(cameraIntent, 1);
//                startActivity(healthSafetyIntent);
//            }
//        });
        interactive_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent interactiveEquipmentIntent = new Intent(getBaseContext(), ImageArActivity.class);
                //  startActivityForResult(cameraIntent, 1);
                startActivity(interactiveEquipmentIntent);
            }
        });
        model_placement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent modelPlacementIntent = new Intent(getBaseContext(), ARActivity.class);
                //  startActivityForResult(cameraIntent, 1);
                startActivity(modelPlacementIntent);
            }
        });
    }

    private void loadCategories(){
        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        List<Category> categories = dbHelper.getAllCategories();

        ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerCategory.setAdapter(adapterCategories);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_QUIZ){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(CameraActivity.PASS_EXTRA_SCORE, 0);
                textViewHighscore.setText(Integer.toString(score));
                if(score > highscore){
                    updateHighscore(score);
                }
            }
        }
    }

    private void loadHighscore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("HighScore: " + highscore);
    }

    private void updateHighscore(int highscoreNew){
        highscore = highscoreNew;
        textViewHighscore.setText("HighScore: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();

    }


}

