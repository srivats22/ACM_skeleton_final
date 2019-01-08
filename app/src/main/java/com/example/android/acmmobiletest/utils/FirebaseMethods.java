package com.example.android.acmmobiletest.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

//import com.example.android.acmmobiletest.profile.AccountSettingsActivity;
//import com.example.android.acmmobiletest.HomeScreen;
import com.example.android.acmmobiletest.R;
//import umbrella.com.muse.models.Photo;
import com.example.android.acmmobiletest.models.UserAccountSettings;
import com.example.android.acmmobiletest.models.User;
//import com.example.android.acmmobiletest.models.UserSettings;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;
    private String userID;

    //vars
    private Context mContext;
    private double mPhotoUploadProgress = 0;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    private void setProfilePhoto(String url){
        Log.d(TAG, "setProfilePhoto: setting new profile image: " + url);

        myRef.child(mContext.getString(R.string.skeleton_user_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.profile_photo))
                .setValue(url);
    }

    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.CANADA);
        sdf.setTimeZone(TimeZone.getTimeZone("Canada/Pacific"));
        return sdf.format(new Date());
    }

    /**
     * update username in the 'users' node and 'user_account_settings' node
     * @param username
     */
    public void updateUsername(String username){
        Log.d(TAG, "updateUsername: upadting username to: " + username);

        myRef.child(mContext.getString(R.string.skeleton_users))
                .child(userID)
                .child(mContext.getString(R.string.username))
                .setValue(username);

        myRef.child(mContext.getString(R.string.skeleton_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.username))
                .setValue(username);
    }

    /**
     * update the email in the 'user's' node
     * @param email
     */
    public void updateEmail(String email){
        Log.d(TAG, "updateEmail: upadting email to: " + email);

        myRef.child(mContext.getString(R.string.skeleton_users))
                .child(userID)
                .child(mContext.getString(R.string.email))
                .setValue(email);

    }

    public void registerNewEmail(final String email, String password, final String username) {
        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    userID = mAuth.getCurrentUser().getUid();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(mContext, R.string.auth_failed,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                            }else{
                                Toast.makeText(mContext, "couldn't send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void addNewUser(String email, String username, String description, String profile_picture){

        User user = new User(userID, 1, email, username);

        myRef.child(mContext.getString(R.string.skeleton_users))
                .child(userID)
                .setValue(user);


        UserAccountSettings settings = new UserAccountSettings(
                description,
                username,
                profile_picture,
                username,
                userID);

        myRef.child(mContext.getString(R.string.skeleton_user_account_settings))
                .child(userID)
                .setValue(settings);
    }

//
//    /**
//     * Retrieves the account settings for teh user currently logged in
//     * Database: user_acount_Settings node
//     * @param dataSnapshot
//     * @return
//     */
//    public UserSettings getUserSettings(DataSnapshot dataSnapshot){
//        Log.d(TAG, "getUserAccountSettings: retrieving user account settings from firebase.");
//
//
//        UserAccountSettings settings  = new UserAccountSettings();
//        User user = new User();
//
//        for(DataSnapshot ds: dataSnapshot.getChildren()){
//
//            // user_account_settings node
//            if(ds.getKey().equals(mContext.getString(R.string.dbname_user_account_settings))) {
//                Log.d(TAG, "getUserAccountSettings: user account settings node datasnapshot: " + ds);
//
//                try {
//
//                    settings.setDisplay_name(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getDisplay_name()
//                    );
//                    settings.setUsername(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getUsername()
//                    );
//                    settings.setWebsite(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getWebsite()
//                    );
//                    settings.setDescription(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getDescription()
//                    );
//                    settings.setProfile_photo(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getProfile_photo()
//                    );
//                    settings.setPosts(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getPosts()
//                    );
//                    settings.setFollowing(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getFollowing()
//                    );
//                    settings.setFollowers(
//                            ds.child(userID)
//                                    .getValue(UserAccountSettings.class)
//                                    .getFollowers()
//                    );
//
//                    Log.d(TAG, "getUserAccountSettings: retrieved user_account_settings information: " + settings.toString());
//                } catch (NullPointerException e) {
//                    Log.e(TAG, "getUserAccountSettings: NullPointerException: " + e.getMessage());
//                }
//            }
//
//
//                // users node
//                Log.d(TAG, "getUserSettings: snapshot key: " + ds.getKey());
//                if(ds.getKey().equals(mContext.getString(R.string.dbname_users))) {
//                    Log.d(TAG, "getUserAccountSettings: users node datasnapshot: " + ds);
//
//                    user.setUsername(
//                            ds.child(userID)
//                                    .getValue(User.class)
//                                    .getUsername()
//                    );
//                    user.setEmail(
//                            ds.child(userID)
//                                    .getValue(User.class)
//                                    .getEmail()
//                    );
//                    user.setPhone_number(
//                            ds.child(userID)
//                                    .getValue(User.class)
//                                    .getPhone_number()
//                    );
//                    user.setUser_id(
//                            ds.child(userID)
//                                    .getValue(User.class)
//                                    .getUser_id()
//                    );
//
//                    Log.d(TAG, "getUserAccountSettings: retrieved users information: " + user.toString());
//                }
//        }
//        return new UserSettings(user, settings);
//
//    }

}