package com.project.laborientation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class InteractiveActivity extends AppCompatActivity {
    ImageView mEquipment;
    private static final String TAG = "InteractiveActivity";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive);
        mEquipment = findViewById(R.id.initial_image);
        mEquipment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int rgb = getColour(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));
                handleColor(Integer.toHexString(rgb));
                return false;
            }
        });
    }

    private int getColour( int x, int y)
    {
        LinearLayout mInteractiveLayout = findViewById(R.id.interactive_layout);
        Drawable transparentEquipmentDrawable = ContextCompat.getDrawable(this, R.drawable.best_transparent);
        transparentEquipmentDrawable.setBounds(0, 0, mInteractiveLayout.getWidth(), mInteractiveLayout.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(mInteractiveLayout.getWidth(), mInteractiveLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        transparentEquipmentDrawable.draw(canvas);
        return bitmap.getPixel(x, y);
    }

    private void handleColor(String colorHex) {
        switch (colorHex) {
            case "ffffaec7":
                Toast.makeText(InteractiveActivity.this, "Power Button", Toast.LENGTH_LONG).show();
                break;
            case "ffb97b56":
                Toast.makeText(InteractiveActivity.this, "Voltage", Toast.LENGTH_LONG).show();
                break;
            case "ffffca18":
                Toast.makeText(InteractiveActivity.this, "Current", Toast.LENGTH_LONG).show();
                break;
            case "ff0ed145":
                Toast.makeText(InteractiveActivity.this, "Master Voltage", Toast.LENGTH_LONG).show();
                break;
            case "ffeb1c24":
                Toast.makeText(InteractiveActivity.this, "Knobs", Toast.LENGTH_LONG).show();
                break;
            case "ff3f47cc":
                Toast.makeText(InteractiveActivity.this, "Screen", Toast.LENGTH_LONG).show();
                break;
        }

    }

}
