package viercimi.enpy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfile extends AppCompatActivity {

    ImageView back, url_photo_profile;
    EditText xfirst_name, xlast_name, xemail, xpassword, xphone_number, xaddress, xusername;
    Button btn_save, btn_add_new_photo;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        back = findViewById(R.id.back);
        xfirst_name = findViewById(R.id.xfirst_name);
        xlast_name = findViewById(R.id.xlast_name);
        xemail = findViewById(R.id.xemail);
        xpassword = findViewById(R.id.xpassword);
        xphone_number = findViewById(R.id.xphone_number);
        xaddress = findViewById(R.id.xaddress);
        url_photo_profile = findViewById(R.id.url_photo_profile);
        btn_save = findViewById(R.id.btn_save);
        xusername = findViewById(R.id.xusername);
        btn_add_new_photo = findViewById(R.id.btn_add_new_photo);


        getUsernameLocal();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                xfirst_name.setText(snapshot.child("first_name").getValue().toString());
                xlast_name.setText(snapshot.child("last_name").getValue().toString());
                xusername.setText(snapshot.child("username").getValue().toString());
                xemail.setText(snapshot.child("email").getValue().toString());
                xpassword.setText(snapshot.child("password").getValue().toString());
                xphone_number.setText(snapshot.child("phone_number").getValue().toString());
                xaddress.setText(snapshot.child("address").getValue().toString());
                Picasso.with(EditProfile.this).load(snapshot.child("url_photo_profile")
                        .getValue().toString()).centerCrop().fit().into(url_photo_profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("username").setValue(xusername.getText().toString());
                        snapshot.getRef().child("last_name").setValue(xlast_name.getText().toString());
                        snapshot.getRef().child("first_name").setValue(xfirst_name.getText().toString());
                        snapshot.getRef().child("email").setValue(xemail.getText().toString());
                        snapshot.getRef().child("password").setValue(xpassword.getText().toString());
                        snapshot.getRef().child("phone_number").setValue(xphone_number.getText().toString());
                        snapshot.getRef().child("address").setValue(xaddress.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                // validasi untuk file (apakah ada?)
                if (photo_location != null){
                    StorageReference storageReference1 =
                            storage.child(System.currentTimeMillis() + "." +
                                    getFileExtension(photo_location));

                    storageReference1.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String uri_photo = uri.toString();
                                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            //Toast.makeText(getApplicationContext(), "Pindah !", Toast.LENGTH_SHORT).show();
                                            // berpindah activity
                                            Intent gotobackprofile = new Intent(EditProfile.this,Profile.class);
                                            startActivity(gotobackprofile);
                                        }
                                    });

                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            // berpindah activity
                            Intent gotobackprofile = new Intent(EditProfile.this,Profile.class);
                             startActivity(gotobackprofile);
                        }
                    });
                }
                Intent gotobackprofile = new Intent(EditProfile.this,Profile.class);
                startActivity(gotobackprofile);
            }
        });
        btn_add_new_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });
    }
    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(url_photo_profile);
        }
    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}