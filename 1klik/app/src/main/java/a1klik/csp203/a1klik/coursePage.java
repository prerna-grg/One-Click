package a1klik.csp203.a1klik;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import a1klik.csp203.a1klik.UploadImageLib.FilePath;
import a1klik.csp203.a1klik.UploadImageLib.HttpRequestImageLoadTask;
import a1klik.csp203.a1klik.UploadImageLib.HttpRequestLongOperation;

public class coursePage extends AppCompatActivity {
    String InstructorName;
    String CourseName;
    String courseTitle;
    int CourseID;
    int id;
    int instructor_id;
    /* Api variables */
    String websiteURL   = "https://1klik.000webhostapp.com/ImageUpload/images";
    String apiURL       = "https://1klik.000webhostapp.com/ImageUpload/api"; // Without ending slash
    String apiPassword  = "qw2e3erty6uiop";

    /* Current image */
    String currentImagePath = "";
    String currentImage = "";

    HttpRequestLongOperation StoredAfterActivityResult=null;
    private static int PHOTO_SELECTED=0;
    private Intent PHOTO_SELECTED_FROM_GALLERY=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);
        InstructorName= getIntent().getStringExtra("UserName");
        CourseName= getIntent().getStringExtra("courseName");
        CourseID= getIntent().getIntExtra("course_id",-1);
        courseTitle= getIntent().getStringExtra("courseTitle");
        instructor_id=getIntent().getIntExtra("Instructor_id",-1);
        id= getIntent().getIntExtra("id",-1);
        ((TextView)findViewById(R.id.loginUserName)).setText(InstructorName);
        ((TextView)findViewById(R.id.courseShowScreen)).setText(CourseName);
        StoredAfterActivityResult=null;
        //check permission
        checkPermissionRead();
        checkPermissionWrite();

        //listner
        //buttonListener();

    }
    public void onLogout(View view)
    {

    }

    /*- Check permission Read ---------------------------------------------------------- */
// Pops up message to user for reading
    private void checkPermissionRead(){
        int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
    } // checkPermissionRead

    /*- Check permission Write ---------------------------------------------------------- */
// Pops up message to user for writing
    private void checkPermissionWrite(){
        int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }
    } // checkPermissionWrite

    public void loadFromGallery(View view)
    {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto,1); //one can be replaced with any action code

    }
    /*- Button Listener ------------------------------------------------------------- */
//    public void buttonListener() {
//        // Load image button listener
//        Button buttonGallery = (Button) findViewById(R.id.buttonGallery);
//        buttonGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                PHOTO_SELECTED_FROM_GALLERY=pickPhoto;
//                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
//            }
//        });
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            // Set image
            ImageView imageViewImage = (ImageView)findViewById(R.id.imageViewImage);
            imageViewImage.setImageURI(selectedImageUri);

            // Save image
            String destinationFilename = FilePath.getPath(this, selectedImageUri);

            // Dynamic text
            TextView textViewDynamicText = (TextView) findViewById(R.id.textViewDynamicText); // Dynamic text

            // URL
            String urlToApi = apiURL + "/image_upload.php";


            // Toast
            //Toast.makeText(this, "ID:"  + currentRecipeId, Toast.LENGTH_LONG).show();

            // Data
            Map mapData = new HashMap();
            mapData.put("inp_api_password", apiPassword);
            mapData.put("i_name",instructor_id);
            mapData.put("c_name",CourseName);
            HttpRequestLongOperation task = new HttpRequestLongOperation(this, urlToApi, "post_image", mapData, destinationFilename, textViewDynamicText, new HttpRequestLongOperation.TaskListener() {
                @Override
                public void onFinished(String result) {
                    // Do Something after the task has finished
                    //imageUploadResult();
                }
            });
            StoredAfterActivityResult=task;
            //task.execute();

        }
    }


    public void imageUploadResult() {
        // Dynamic text
        TextView textViewDynamicText = (TextView)findViewById(R.id.textViewDynamicText);
        String dynamicText = textViewDynamicText.getText().toString();

        // Split
        int index = dynamicText.lastIndexOf('/');
        try {
            currentImagePath = dynamicText.substring(0, index);
        }
        catch (Exception e){
            TextView Error_screen=findViewById(R.id.courseShowScreen);
            System.out.println("Error1 found 1");
            Error_screen.setText(Error_screen.getText().toString()+" "+e.getMessage().toString());
            Toast.makeText(this, "path: " + e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        try {
            currentImage = dynamicText.substring(index,dynamicText.length());
        }
        catch (Exception e){
            TextView Error_screen=findViewById(R.id.courseShowScreen);
            System.out.println("Error1 found 2");
            Error_screen.setText(Error_screen.getText().toString()+" "+e.getMessage().toString());
            Toast.makeText(this, "image: " + e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        //Load new image
         loadImage();

    } // imageUploadResult


    /*- Load image ------------------------------------------------------------------ */
    public void loadImage(){

        // Load image
        ImageView imageViewImage = (ImageView)findViewById(R.id.imageViewImage);

        if(!(currentImagePath.equals("")) && !(currentImage.equals(""))){

            String loadImage = websiteURL + "/" + currentImagePath + "/" + currentImage;
            new HttpRequestImageLoadTask(this, loadImage, imageViewImage).execute();

        }
    }

    public void uploadPhotoSelected(View view)
    {
        if (StoredAfterActivityResult==null)
        {
            Toast.makeText(this, "Please select the photo first", Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            StoredAfterActivityResult.execute();
            ImageView imageViewImage = (ImageView)findViewById(R.id.imageViewImage);
            imageViewImage.setImageURI(null);
            StoredAfterActivityResult=null;
        }


    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }
    //below function isn't correct, check again while using
    String get_first_name(String input)
    {
        String output="";
        int n=input.length();
        int i=0;
        while (i<n && input.charAt(i)!=' ')
        {
            output=output+input.charAt(i);
        }
        //System.out.println("First Name is "+output);
        return output;
    }
}
