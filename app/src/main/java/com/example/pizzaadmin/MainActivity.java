package com.example.pizzaadmin;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText itemNameEditText, itemPriceEditText;
    private ImageView imageView;

    private Bitmap selectedImageBitmap;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonOrder = (Button)findViewById(R.id.buttonOrder);
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemPriceEditText = findViewById(R.id.itemPriceEditText);
        imageView = findViewById(R.id.imageView);
        Button chooseImageButton = findViewById(R.id.buttonChooseImage);
        Button uploadButton = findViewById(R.id.uploadButton);

        firestore = FirebaseFirestore.getInstance();

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OrderActivity.class);
                startActivity(intent);
            }
        });
        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to select an image
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadItem();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                try {
                    // Get the selected image bitmap
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    imageView.setImageBitmap(selectedImageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadItem() {
        String itemName = itemNameEditText.getText().toString().trim();
        String Price = itemPriceEditText.getText().toString().trim();

        if (itemName.isEmpty() || Price.isEmpty() || selectedImageBitmap == null) {
            Toast.makeText(MainActivity.this, "Please fill all the fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }
        int itemPrice = Integer.parseInt(Price);

        // Convert the selected image bitmap to a base64-encoded string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        // Create a new item object with the data
        Map<String, Object> item = new HashMap<>();
        item.put("name", itemName);
        item.put("price", itemPrice);
        item.put("image", encodedImage);

        // Add the item to Firestore
        firestore.collection("items")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Item uploaded successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error uploading item", e);
                        Toast.makeText(MainActivity.this, "Failed to upload item", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        itemNameEditText.setText("");
        itemPriceEditText.setText("");
        imageView.setImageBitmap(null);
        selectedImageBitmap = null;
    }
}
