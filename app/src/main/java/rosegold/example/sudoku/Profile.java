package rosegold.example.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    TextView profileName, gamesWonView;
    boolean connected = false;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    ImageView profilePic;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = (TextView)findViewById(R.id.profile2);
        profilePic = (ImageView)findViewById(R.id.profilepicView2);
        gamesWonView = (TextView)findViewById(R.id.gamesWonView);
        back = (Button)findViewById(R.id.goback);
        back.setOnClickListener(this);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            connected = false;
        }

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        if(user != null && connected) {
            DatabaseReference ref = firebaseDatabase.getReference(firebaseAuth.getUid());

            storageReference = firebaseStorage.getReference();
            storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(profilePic); //custom library https://github.com/square/picasso
                }
            });

            ref.keepSynced(true);
            ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()) {
                        String str1 = String.valueOf(task.getResult().getValue());
                        str1 = str1.substring(str1.indexOf("userName") + 9, str1.indexOf("userEmail")-2);
                        profileName.setText(str1);
                        String str2 = String.valueOf(task.getResult().getValue());
                        str2 = str2.substring(10, 11);
                        gamesWonView.setText("Total Games Won: "+str2);
                    } else {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                }
            });
        } else {
            profileName.setText("Loading...");
        }

    }

    @Override
    public void onClick(View v) {
        if(v == back) {
            startActivity(new Intent(Profile.this, MainMenu.class));
        }
    }
}
