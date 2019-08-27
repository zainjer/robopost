package com.techolon.robopost;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Calendar;

public class Post_activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    //VARIABLES
    EditText post_title;
    EditText post_caption;
    TextView tvSetTime;
    Button btnSave;
    ImageView imgFb,imgTwt,imgInsta,ivAddImage;
    Posts_Created postObject;
    String scheduledDateString,scheduledTimeString;
    Bitmap image;
    private static final int PICK_IMAGE = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        findviews();
        onClicks();
        postObject = new Posts_Created();
    }

    private void onClicks() {

        //SAVE BUTTON LISTENER
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Post_activity.this, "Trying", Toast.LENGTH_SHORT).show();
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                postObject.title = post_title.getText().toString();
            }
        });


        tvSetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
                DialogFragment datepicker = new DatePickerFragment();
                datepicker.show(getSupportFragmentManager(),"date picker");

            }
        });


        ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(openGallery,PICK_IMAGE);

            }
        });
    }

    private void findviews(){
        post_title = findViewById(R.id.etPostTitle);
        post_caption = findViewById(R.id.etPostCaption);
        ivAddImage = findViewById(R.id.ivAddimage);
        imgFb = findViewById(R.id.imgFb);
        imgInsta = findViewById(R.id.imginsta);
        imgTwt = findViewById(R.id.imgtwt);
        btnSave = findViewById(R.id.btnSave);
        tvSetTime = findViewById(R.id.tvScheduleTime);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE && resultCode == RESULT_OK){
            assert data != null;
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image = selectedImage;
                ivAddImage.setImageBitmap(selectedImage);
                postObject.image_uri = imageUri;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Post_activity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
//            post_image_uri = data.getData();
//            ivAddImage.setImageURI(post_image_uri);
//            postObject.image_uri = post_image_uri;
        }else{
            Toast.makeText(this, "You haven't Picked an image", Toast.LENGTH_SHORT).show();
        }
    }

    public void logoutAction(View view) {
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(Post_activity.this,login.class));
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        c.set(Calendar.MONDAY,month);
        c.set(Calendar.YEAR, year);
        scheduledDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        scheduledTimeString = ""+hourOfDay+":"+minute+"";
        String temp = "[ "+scheduledTimeString+" - "+ scheduledDateString +" ]";
        tvSetTime.setText(temp);
    }

}
