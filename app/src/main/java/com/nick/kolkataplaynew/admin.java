package com.nick.kolkataplaynew;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class admin extends AppCompatActivity {

    private static final  int PICK_IMAGE_REQUEST =1;

    private EditText mFilename;
    private EditText mFileCategory;
    private ProgressBar mProgressBar;
    private ImageView mImageView;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    private RequestQueue mRequestQue;
    private  String URL = "https://fcm.googleapis.com/fcm/send";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mFilename = findViewById(R.id.admin_name);
        mFileCategory = findViewById(R.id.admin_category);
        mImageView = findViewById(R.id.admin_image);
        mProgressBar = findViewById(R.id.admin_progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("newsFeeds");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("newsFeeds");

        mRequestQue = Volley.newRequestQueue(this);
    }

    //great done!jhjhj
    private void openFileChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode ==RESULT_OK  && data != null && data.getData() != null)
        {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);



        }
    }
    public void galleryOpening(View view) {
        openFileChooser();
    }

    public void uploadingFeeds(View view) {
        if(mUploadTask != null && mUploadTask.isInProgress())
        {
            Toast.makeText(com.nick.kolkataplaynew.admin.this,"Uploading in progress",Toast.LENGTH_LONG).show();
        }
        else
        {
            Uploading();
           sendNotifcation();
        }
    }

    private  void Uploading()
    {
        if(mImageUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(mImageUri));
            mUploadTask =fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Handler handler =new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            },500);
                            Toast.makeText(com.nick.kolkataplaynew.admin.this,"upload Successfull",Toast.LENGTH_LONG).show();
                            Task<Uri> urlTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                            while (!urlTask.isSuccessful());

                            Uri downloadUrl = urlTask.getResult();
                            com.nick.kolkataplaynew.model upload = new com.nick.kolkataplaynew.model(mFilename.getText().toString().trim(),downloadUrl.toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);


                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(com.nick.kolkataplaynew.admin.this,e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = ((100.0* taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            mProgressBar.setProgress((int)progress);

                        }
                    });
        }
        else
        {
            Toast.makeText(this,"no file selected",Toast.LENGTH_LONG).show();
        }
    }
    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void sendNotifcation() {
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to","/topics/"+"news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title",mFileCategory.getText().toString());
            notificationObj.put("body",mFilename.getText().toString());
            mainObj.put("notification",notificationObj);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,
                    mainObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(com.nick.kolkataplaynew.admin.this,"notification send",Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(com.nick.kolkataplaynew.admin.this,error.toString(),Toast.LENGTH_LONG).show();
                }
            }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("Content-Type", "application/json");
                    header.put("Authorization", "key=AAAAKhiJSDY:APA91bFKsaUJnCbxZ847Llxt7FrrSfZr6W2pBSyPVrmJSb_flDR_GVE5pawlV3VMIkD49E0JguF3d7LPnBw_U8L7VuoeTtOR9kUnbygHOxp14J1kh6qpWlPYTXNR6CYHolYrbMEtSENS");
                    return  header;                }


            };
            mRequestQue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();

        }


    }

    public void restartt(View view) {
        setContentView(R.layout.activity_admin);
    }
}