package com.kodoshinobi.braintrainermaths;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class fblogin extends AppCompatActivity {
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private TextView textviewUser;
    private ImageView mlogo;
    private LoginButton loginbutton;
    private String name;
    private String profile_img;
    private String Fb_username;
    DatabaseReference reference;
    int scored;


    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    private static final String TAG = "FacebookAuthentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reference = FirebaseDatabase.getInstance().getReference().child("Score");

        setContentView(R.layout.activity_fblogin);
        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        textviewUser = findViewById(R.id.text_user);
        mlogo = findViewById(R.id.image_logo);
        loginbutton = findViewById(R.id.login_button);
        loginbutton.setReadPermissions("email", "public_profile");
        mCallbackManager = CallbackManager.Factory.create();
        loginbutton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());


            /// hew=rw we edit
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError" + error);

            }
        });


        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    updateUI(user);
                    Intent intent = new Intent(fblogin.this, MainMenuActivity.class);
                    startActivity(intent);
                    finish();



                } else {
                    updateUI(null);
                }
            }
        };
        GraphRequest request;
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (accessTokenTracker == null) {
                    mFirebaseAuth.signOut();
                }
            }
        };

    }


    private void handleFacebookToken(AccessToken token) {
        Log.d(TAG, "handleFacebookToken" + token);


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Sign in with credential successful");
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    updateUI(user);

                } else {
                    Log.d(TAG, "Sign in with credential failed", task.getException());
                    Toast.makeText(fblogin.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }




    private void updateUI(FirebaseUser user) {
        HashMap map = new HashMap();
        if (user != null) {
            textviewUser.setText(user.getDisplayName());
            Profile profile = Profile.getCurrentProfile();

            if (profile != null) {
                Fb_username = profile.getFirstName() + " " + profile.getMiddleName() + " " + profile.getLastName();
                profile_img = profile.getProfilePictureUri(300, 300).toString();
                map.put("name", Fb_username);
                map.put("image", profile_img);

            }
            if (profile_img != null) { //user.getPhotoUrl()
                // String photoUrl=user.getPhotoUrl().toString();
                // photoUrl=photoUrl+"?type=large";
                Picasso.get().load(profile_img).into(mlogo);
            }
            //  name=user.getDisplayName(); //

            map.put("name", Fb_username);
            map.put("image", profile_img); //29:49
            map.put("score",0);



                reference.child(user.getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(fblogin.this,
                                "Data Inserted",
                                Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(fblogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

    else

    {
        textviewUser.setText("");
        mlogo.setImageResource(R.drawable.mathapk);
    }

}

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }
    }
}