//package com.project.laborientation.Util;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.DialogFragment;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.media.Image;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.Toast;
////import android.transition.Scene;
//
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.ar.core.Anchor;
//import com.google.ar.core.AugmentedImage;
//import com.google.ar.core.AugmentedImageDatabase;
//import com.google.ar.core.Config;
//import com.google.ar.core.Frame;
//import com.google.ar.core.Session;
//import com.google.ar.core.TrackingState;
//import com.google.ar.core.exceptions.NotYetAvailableException;
//import com.google.ar.sceneform.AnchorNode;
//import com.google.ar.sceneform.FrameTime;
//import com.google.ar.sceneform.Scene;
//import com.google.ar.sceneform.math.Quaternion;
//import com.google.ar.sceneform.math.Vector3;
//import com.google.ar.sceneform.rendering.ModelRenderable;
//import com.google.ar.sceneform.rendering.ViewRenderable;
//import com.google.ar.sceneform.rendering.ViewSizer;
//import com.google.ar.sceneform.ux.TransformableNode;
//import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
//import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
//import com.google.firebase.ml.vision.FirebaseVision;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
//import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
//import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;
//import com.project.laborientation.CustomArFragment;
//import com.project.laborientation.R;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//
//    public class ImageArActivity extends AppCompatActivity implements Scene.OnUpdateListener{
//
//        private CustomArFragment arFragment;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_image_ar);
//
//            arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
//            arFragment.getArSceneView().getScene().addOnUpdateListener(this);
//
//
//        }
//
//        public void setupDatabase(Config config, Session session){
//            Bitmap foxBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fox);
//            Bitmap waveformBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.waveform);
//            Bitmap socketBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.socket);
//
//            AugmentedImageDatabase aid = new AugmentedImageDatabase(session);
//            aid.addImage("fox", foxBitmap);
//            aid.addImage("waveform", waveformBitmap);
//            aid.addImage("socket", socketBitmap);
//
//            config.setAugmentedImageDatabase(aid);
//        }
//
//        @Override
//        public void onUpdate(FrameTime frameTime) {
//            Frame frame = arFragment.getArSceneView().getArFrame();
//            Collection<AugmentedImage> images = frame.getUpdatedTrackables(AugmentedImage.class);
//
//
//
//            //    try (Image i = frame.acquireCameraImage()) {
//            //    FirebaseVisionImage firebaseImage = FirebaseVisionImage.fromMediaImage(i, 0);
//            //   labelObject(firebaseImage);
//
//
////        } catch (NotYetAvailableException e) {
////            e.printStackTrace();
////        }
//
//
//            for(AugmentedImage image : images){
//                if(image.getTrackingState() == TrackingState.TRACKING){
//                    if(image.getName().equals("fox")){
//                        Anchor anchor = image.createAnchor(image.getCenterPose());
//                        createModel(anchor, "fox");
//                    }
//                    if(image.getName().equals("waveform")){
//                        Anchor anchor = image.createAnchor(image.getCenterPose());
//                        createModel(anchor, "waveform");
//                    }
//                    if(image.getName().equals("socket")){
//                        Anchor anchor = image.createAnchor(image.getCenterPose());
//                        createModel(anchor, "socket");
//                    }
//
//                }
//            }
//        }
//
//        String oldObject;
//
//        void labelObject(FirebaseVisionImage image) {
//            FirebaseLocalModel localModel = new FirebaseLocalModel.Builder("lab_model")
//                    .setAssetFilePath("data/manifest.json")
//                    .build();
//            FirebaseModelManager.getInstance().registerLocalModel(localModel);
//            FirebaseVisionOnDeviceAutoMLImageLabelerOptions labelerOptions =
//                    new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder()
//                            .setLocalModelName("lab_model")
//                            .setConfidenceThreshold(0)  // Evaluate your model in the Firebase console
//                            .build();
//            try {
//                FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(labelerOptions);
//                labeler.processImage(image)
//                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
//                            @Override
//                            public void onSuccess(List<FirebaseVisionImageLabel> labels) {
//                                String currentObject = labels.get(0).getText();
//                                if((oldObject == null) || (oldObject != currentObject)) {
//                                    oldObject = currentObject;
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString("OBJECT", currentObject);
//                                    DialogFragment optionFragment = new OptionsDialog();
//                                    optionFragment.setArguments(bundle);
//                                    optionFragment.show(getSupportFragmentManager(), "optionsDialog");
//
//                                    Toast.makeText(ImageArActivity.this, "FOUND", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(ImageArActivity.this, "Unable to detect object", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            } catch (Exception e) {
//            }
//        }
//
//        private void createModel(Anchor anchor, String object) {
//
//
//            if(object == "fox") {
//                ViewRenderable.builder()
//                        .setView(this, R.layout.ar_text)
//                        .build()
//                        .thenAccept(viewRenderable -> placeModel(viewRenderable, anchor)).exceptionally(throwable -> null);
//            }
//
//            if(object == "waveform") {
//                ViewRenderable.builder()
//                        .setView(this, R.layout.ar_text)
//                        .build()
//                        .thenAccept(viewRenderable -> placeModel(viewRenderable, anchor)).exceptionally(throwable -> null);
//            }
//            if(object == "socket") {
//                ViewRenderable.builder()
//                        .setView(this, R.layout.ar_text)
//                        .build()
//                        .thenAccept(viewRenderable -> placeModel(viewRenderable, anchor)).exceptionally(throwable -> null);
//            }
//
//        }
//
//        private void placeModel(ViewRenderable modelRenderable, Anchor anchor) {
//
//            AnchorNode anchorNode = new AnchorNode(anchor);
//            anchorNode.setParent(arFragment.getArSceneView().getScene());
//
//            TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
//
//            //set rotation in direction (x,y,z) in degrees 90
//            node.setLocalRotation(Quaternion.axisAngle(new Vector3(-1, 0, 0), 90f));
//
//
//            node.setParent(anchorNode);
//            node.setRenderable(modelRenderable);
//
//            node.select();
//
////        anchorNode.setLocalRotation(Quaternion.axisAngle(new Vector3 (1, 0, 0), 90f));
////
////        anchorNode.setRenderable(modelRenderable);
////        arFragment.getArSceneView().getScene().addChild(anchorNode);
//
//        }
//    }
//
//
//}
