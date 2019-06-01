//package com.project.laborientation;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.provider.MediaStore;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.text.FirebaseVisionText;
//import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
//
//import java.util.List;
//
//public class SecondActivity extends AppCompatActivity {
//
//    private Button snapBtn;
//    private Button detectBtn;
//    private ImageView imageView;
//    private TextView txtView;
//    private Bitmap imageBitmap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second);
//        snapBtn = findViewById(R.id.snapBtn);
//        detectBtn = findViewById(R.id.detectBtn);
//        imageView = findViewById(R.id.imageView);
//        txtView = findViewById(R.id.txtView);
//        snapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dispatchTakePictureIntent();
//            }
//        });
//        detectBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                detectTxt();
//            }
//        });
//    }
//
//    static final int REQUEST_IMAGE_CAPTURE = 1;
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
//        }
//    }
//
//    private void detectTxt() {
//        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
//        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
//        detector.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
//            @Override
//            public void onSuccess(FirebaseVisionText firebaseVisionText) {
//                processTxt(firebaseVisionText);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });
//    }
//
//    private void processTxt(FirebaseVisionText text) {
//        List<FirebaseVisionText.TextBlock> blocks = text.getTextBlocks();
//    //    List<FirebaseVisionText.Element> words = text. text.getText();
//        String txt = "amal";
//
//        if (blocks.size() == 0) {
//            Toast.makeText(SecondActivity.this, "No Text :(", Toast.LENGTH_LONG).show();
//            return;
//        }
//        for (FirebaseVisionText.TextBlock block : text.getTextBlocks()) {
//            txt = block.getText();
//            blocks.add(block);
//            txtView.setTextSize(24);
//            txtView.setText(txt);
//        }
//
////        if (txt.contains("Lab")) {
////                openLabRules();
////            } else if (txt == "amal") {
////                txtView.setTextSize(24);
////                txtView.setText(txt);
////            } else {
////                txtView.setTextSize(24);
////                txtView.setText(txt);
////            }
////        }
//
////        val resultText = result.text
////        for (block in result.textBlocks) {
////            val blockText = block.text
////            val blockConfidence = block.confidence
////            val blockCornerPoints = block.cornerPoints
////            val blockFrame = block.boundingBox
////            for (line in block.lines) {
////                val lineText = line.text
////                val lineConfidence = line.confidence
////                val lineCornerPoints = line.cornerPoints
////                val lineFrame = line.boundingBox
////                for (element in line.elements) {
////                    val elementText = element.text
////                    val elementConfidence = element.confidence
////                    val elementCornerPoints = element.cornerPoints
////                    val elementFrame = element.boundingBox
////                }
////            }
////        }
////
////    }
//
////    private void openLabRules(){
////        Intent myIntent = new Intent(getBaseContext(), LabRules.class);
////        startActivity(myIntent);
////    }
//
//}