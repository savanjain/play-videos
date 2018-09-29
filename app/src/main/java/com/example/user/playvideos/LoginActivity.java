package com.example.user.playvideos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button login;
  //  private ProgressDialog pDialog;
  //  private static String[] PERMISSIONS_CONTACT = {android.Manifest.permission.GET_ACCOUNTS, android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.WRITE_CONTACTS};
    Context context;

    SignInButton googlelogin;
    private GoogleSignInOptions gso;
    private int RC_SIGN_IN = 100;
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_CONTACTS = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
       // login=findViewById(R.id.login);
        googlelogin = (SignInButton) findViewById(R.id.googlelogin);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                    // Implement this feature without material design
                    signIn();

            }
        });
       /* login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                ApiInterface retrofitApi=ApiClient.getClient().create(ApiInterface.class);
                Call<List<UserData>> call=retrofitApi.getAllData();
                call.enqueue(new Callback<List<UserData>>() {
                    @Override
                    public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response)
                    {

                        Global.firebaseDataArrayList= (ArrayList<UserData>) response.body();

                        Intent in=new Intent(LoginActivity.this,MainPageActivity.class);
                        startActivity(in);
                        finish();


                    }

                    @Override
                    public void onFailure(Call<List<UserData>> call, Throwable t)
                    {
                        Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
                    }
                });

               *//* Intent in=new Intent(LoginActivity.this,MainPageActivity.class);
                startActivity(in);
                finish()*//*;
            }
        });*/
    }

    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onConnectionFailed(@NonNull com.google.android.gms.common.ConnectionResult connectionResult)
    {

    }
    @Override
    protected void onStart() {
        super.onStart();
        // make sure to initiate connection
        mGoogleApiClient.connect();
    }
    @Override
    protected void onStop() {
        super.onStop();
        // disconnect api if it is connected
        if (mGoogleApiClient.isConnected())
            mGoogleApiClient.disconnect();
    }


   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CONTACTS) {
            //    Log.i(TAG, "Received response for contact permissions request.");

            // We have requested multiple permissions for contacts, so all of them need to be
            // checked.
            if (PermissionUtil.verifyPermissions(grantResults))
            {
                // All required permissions have been granted, display contacts fragment.
                signIn();
            } else
            {
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin with google
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            //Calling a new function to handle signin
            handleSignInResult(result);
        }

    }

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess())
        {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            ApiInterface retrofitApi=ApiClient.getClient().create(ApiInterface.class);
            Call<List<UserData>> call=retrofitApi.getAllData();
            call.enqueue(new Callback<List<UserData>>() {
                @Override
                public void onResponse(Call<List<UserData>> call, Response<List<UserData>> response)
                {

                    Global.firebaseDataArrayList= (ArrayList<UserData>) response.body();

                    Intent in=new Intent(LoginActivity.this,MainPageActivity.class);
                    startActivity(in);
                    finish();


                }

                @Override
                public void onFailure(Call<List<UserData>> call, Throwable t)
                {
                    Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
                }
            });


            //seting profile information into global variables to access
           /* Global.addPrefs(context.getApplicationContext(),"name",acct.getDisplayName());

            Global.addPrefs(context.getApplicationContext(),"UEmail",acct.getEmail());
            Global.addPrefs(getApplicationContext(), "login_flag", "Y");*/

          /*  pDialog = new ProgressDialog(context);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.style));
            pDialog.setCancelable(true);
            //  pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

            Intent in=new Intent(MainActivity.this,HomePage.class);
            startActivity(in);
            finish();*/


        } else
            {
            //If login fails

            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }
}
