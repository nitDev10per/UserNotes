package com.example.usernotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class logged extends Fragment {

    TextInputLayout T, P;
    Button googleBtn;
    View v;

    EditText email;
    EditText password;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;           //                                -NOTE-
    SharedPreferences pref;           //    SharedPreference Use here and main activity according to assignment
                                      //    Google signin also use here

    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getContext(), gso);

        pref= getActivity().getSharedPreferences("navSwitch", Context.MODE_PRIVATE);
        editor=pref.edit();

        // Inflate the layout for this fragment
        if (!pref.getBoolean("on",false)) {

            v = inflater.inflate(R.layout.fragment_logged, container, false);
            googleBtn = v.findViewById(R.id.Glog);
            T = v.findViewById(R.id.textInputLayout);
            email = T.getEditText();
            P = v.findViewById(R.id.textInputLayout2);
            password = P.getEditText();

            googleBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }

            });
        } else {
            v = inflater.inflate(R.layout.logout, container, false);
            Button b = v.findViewById(R.id.Logout);
            TextView hi=v.findViewById(R.id.textView2);
            GoogleSignInAccount acct=GoogleSignIn.getLastSignedInAccount(getContext());
            if(acct!=null){
                hi.setText("Hi "+acct.getDisplayName());
            }
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();
                }
            });
        }

        return v;
    }

    void signIn() {
        Intent inext = gsc.getSignInIntent();
        startActivityForResult(inext, 1000);
    }

    void signOut() {
        gsc.signOut().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // If sign out is successful, replace the fragment
                editor.putBoolean("on",false);
                editor.apply();

                getFragmentManager().beginTransaction().replace(R.id.container, new logged()).commit();

            } else {
                // Handle sign out failure
                Toast.makeText(getContext(), "Sign out failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                GoogleSignInAccount acct=GoogleSignIn.getLastSignedInAccount(getContext());
                if(acct!=null){
                    email.setText(acct.getEmail());
                }
                navigateToSecond();

            } catch (ApiException e) {
                Toast.makeText(getContext(), "Something wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToSecond() {
        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

        home Note = new home();
        editor.putBoolean("on",true);
        editor.apply();

        getFragmentManager().beginTransaction().replace(R.id.container, Note).commit();
    }
}


//public class logged extends Fragment {
//
//
//    TextInputLayout T,P;
//    Button googleBtn;
//    View v;
//    boolean login;
//
//    GoogleSignInOptions gso;
//    GoogleSignInClient gsc;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        gsc=GoogleSignIn.getClient(getContext(),gso);
//
//        // Inflate the layout for this fragment
//        if(!login) {
//
//            v = inflater.inflate(R.layout.fragment_logged, container, false);
//            googleBtn=v.findViewById(R.id.Glog);
//            T = v.findViewById(R.id.textInputLayout);
//            EditText email = T.getEditText();
//            P = v.findViewById(R.id.textInputLayout2);
//            EditText password = P.getEditText();
//
//            googleBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    signIn();
//                }
//
//            });
//        }else{
//            v = inflater.inflate(R.layout.logout, container, false);
//            Button b=v.findViewById(R.id.Logout);
//            b.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    signOut();
//                }
//            });
//        }
//
//        return v;
//    }
//    void signIn() {
//        Intent inext=gsc.getSignInIntent();
//        startActivityForResult(inext,1000);
//    }
//    void signOut(){
//        gsc.signOut().addOnCanceledListener((OnCanceledListener) task -> {
//            int commit = getFragmentManager().beginTransaction().replace(R.id.container, new logged()).commit());
//        }
//    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode,resultCode,data);
//        if(requestCode==1000){
//            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
//            try{
//                task.getResult(ApiException.class);
//                navigateToSecond();
//            }catch(ApiException e){
//                Toast.makeText(getContext(),"Something worng",Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//    void navigateToSecond(){
//        login=true;
//        home Note=new home();
//
//        getFragmentManager().beginTransaction().replace(R.id.container,Note).commit();
//    }
//}