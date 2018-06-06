package a1klik.csp203.a1klik;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
    public static final String PREFS_NAME = "LoginPrefs";
    String InstructorName;
    String CourseName;
    String courseTitle;
    int CourseID;
    int id;
    int instructor_id;
    /* Api variables */
    String websiteURL  ;
    String apiURL  ;
    String apiPassword  ;

//    String websiteURL1   = getResources().getString(R.string.StoreFileLink).toString();
//    String apiURL1       = getResources().getString(R.string.UploadPHPFileLink).toString(); // Without ending slash
//    String apiPassword1  = getResources().getString(R.string.ImageUploadApiPass).toString();



    /* Current image */
    String currentImagePath = "";
    String currentImage = "";

    HttpRequestLongOperation StoredAfterActivityResult=null;
    private static int PHOTO_SELECTED=0;
    private Intent PHOTO_SELECTED_FROM_GALLERY=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        websiteURL   = getResources().getString(R.string.StoreFileLink).toString();
        apiURL       = getResources().getString(R.string.UploadPHPFileLink).toString(); // Without ending slash
        apiPassword  = getResources().getString(R.string.ImageUploadApiPass).toString();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);

        this.InstructorName= getIntent().getStringExtra(getResources().getString(R.string.SetDataUserName));
        this.CourseName= getIntent().getStringExtra(getResources().getString(R.string.SetDataCourseName));
        this.CourseID= getIntent().getIntExtra(getResources().getString(R.string.SetDataCourseId),-1);
        this.courseTitle= getIntent().getStringExtra(getResources().getString(R.string.SetDataCourseTitle));
        this.instructor_id=getIntent().getIntExtra(getResources().getString(R.string.SetDataInstructorID),-1);
        this.id= getIntent().getIntExtra(getResources().getString(R.string.SetDataId),-1);

//        this.InstructorName= getIntent().getStringExtra("UserName");
//        this.CourseName= getIntent().getStringExtra("courseName");
//        this.CourseID= getIntent().getIntExtra("course_id",-1);
//        this.courseTitle= getIntent().getStringExtra("courseTitle");
//        this.instructor_id=getIntent().getIntExtra("Instructor_id",-1);
//        this.id= getIntent().getIntExtra("id",-1);

        ((TextView)findViewById(R.id.loginUserName)).setText(InstructorName);
        ((TextView)findViewById(R.id.courseShowScreen)).setText(CourseName);
        StoredAfterActivityResult=null;
        //check permission
        checkPermissionRead();
        checkPermissionWrite();

        //listner
        //buttonListener();

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
                    System.out.println(result);
                    byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
                    ImageView imageViewImage = (ImageView)findViewById(R.id.imageViewImage);
                    TextView ShowScreen=(TextView)findViewById(R.id.textViewDynamicText);
                    ShowScreen.setText("Make sure to login to Site and Mark Attendance");
                    //Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    //imageViewImage.setImageBitmap(decodedByte);
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
            Toast.makeText(this, getResources().getString(R.string.SelectPhotoFirst), Toast.LENGTH_LONG).show();
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
