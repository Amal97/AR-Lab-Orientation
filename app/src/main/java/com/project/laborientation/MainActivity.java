package com.project.laborientation;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import com.project.laborientation.Quiz.Category;
import com.project.laborientation.Quiz.QuizDbHelper;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadCategories();

        Button identify_equipment = findViewById(R.id.identify_equipment);
        Button interactive_equipment = findViewById(R.id.interactive_equipment);
        Button model_placement = findViewById(R.id.model_placement);


        identify_equipment.setOnClickListener((View view) -> {
                Intent cameraIntent = new Intent(getBaseContext(), CameraActivity.class);
                startActivityForResult(cameraIntent, 1);
            }
        );

        interactive_equipment.setOnClickListener((View view) -> {
                Intent interactiveEquipmentIntent = new Intent(getBaseContext(), ImageArActivity.class);
                startActivity(interactiveEquipmentIntent);
            }
        );
        model_placement.setOnClickListener((View view) -> {
                Intent modelPlacementIntent = new Intent(getBaseContext(), ARActivity.class);
                startActivity(modelPlacementIntent);
            }
        );
    }

    private void loadCategories(){
        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        List<Category> categories = dbHelper.getAllCategories();

        ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

}

