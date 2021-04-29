package rosegold.example.sudoku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button play, register, login, logout;
    String diff;
    TextView profileName;
    boolean loggedIn, connected = false;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    ImageView profilePic;
    boolean won = false;
    public String name = "", email = "";
    public int gamesWon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        play = (Button)findViewById(R.id.play);
        play.setOnClickListener(this);
        register = (Button)findViewById(R.id.register);
        register.setOnClickListener(this);
        login = (Button)findViewById(R.id.login2);
        login.setOnClickListener(this);
        profileName = (TextView)findViewById(R.id.profile);
        logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(this);
        profilePic = (ImageView)findViewById(R.id.profilepicView);
        profilePic.setOnClickListener(this);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            connected = false;
        }

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
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
                        String str = String.valueOf(task.getResult().getValue());
                        System.out.println(str);
                        str = str.substring(str.indexOf("userName") + 9, str.indexOf("userEmail")-2);
                        profileName.setText(str);
                    } else {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                }
            });

            if(getIntent().getExtras() != null) {
                won = getIntent().getExtras().getBoolean("wonKey");
            } else {
                won = false;
            }
            if(won) {
                //Toast.makeText(MainMenu.this, "Congrats! you win!", Toast.LENGTH_SHORT).show();
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(won) {
                            //Toast.makeText(MainMenu.this, "Congrats! you win!", Toast.LENGTH_SHORT).show();
                            UserProfile userProfile = snapshot.getValue(UserProfile.class);
                            updateProfile(userProfile.getUserEmail(), userProfile.getUserName(), userProfile.getGamesWon() + 1);
                            won = false;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainMenu.this, error.getCode(), Toast.LENGTH_SHORT).show();
                    }
                });
                /*UserProfile userProfile = new UserProfile(email, name);
                userProfile.setGamesWon(gamesWon+1);
                ref.setValue(userProfile);*/
            }
            loggedIn = true;
        } else {
            profileName.setText("Not logged in");
            loggedIn = false;
        }

    }

    private void updateProfile(String email, String name, int gamesWon) {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        UserProfile userProfile = new UserProfile(email, name);
        userProfile.setGamesWon(gamesWon);
        Toast.makeText(MainMenu.this, userProfile.getUserEmail()+" "+userProfile.getUserName()+" "+userProfile.getGamesWon(), Toast.LENGTH_SHORT).show();
        DatabaseReference ref = firebaseDatabase.getReference(firebaseAuth.getUid());
        ref.setValue(userProfile);
    }

    @Override
    public void onClick(View v) {
        if(v == play) {
            if(loggedIn) {
                String[] arr = {"Easy", "Medium", "Hard"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("Pick a difficulty:");
                builder.setItems(arr, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        diff = "Easy";
                    } else if (which == 1) {
                        diff = "Medium";
                    } else if (which == 2) {
                        diff = "Hard";
                    }
                    Intent i = new Intent(MainMenu.this, MainActivity.class);
                    i.putExtra("diff", diff);
                    startActivity(i);
                }
                }).show();
            } else {
                Toast.makeText(this, "Must be logged in to play.", Toast.LENGTH_SHORT).show();
            }
        } else if(v == register) {
            Intent i = new Intent(MainMenu.this, Register.class);
            startActivity(i);
        } else if(v == login) {
            startActivity(new Intent(MainMenu.this, Login.class));
        } else if(v == logout) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    profileName.setText("Not logged in");
                    loggedIn = false;
                }
            });
            profilePic.setImageResource(R.drawable.blankprofile);
        } else if(v == profilePic) {
            if(loggedIn) {
                startActivity(new Intent(MainMenu.this, Profile.class));
            }
        }
    }
}
