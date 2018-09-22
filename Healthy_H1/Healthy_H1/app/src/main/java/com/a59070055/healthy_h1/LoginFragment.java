package com.a59070055.healthy_h1;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container,false);
    }
    FirebaseAuth _Auth;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _Auth = FirebaseAuth.getInstance();
        initRegisterTextView();
        checkCurrentUser();
        initLoginBtn();

    }
    void initRegisterTextView() {
        TextView _reg = getView().findViewById(R.id.login_register_new_account_text);
        _reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new RegisterFragment()).addToBackStack(null).commit();
                Log.d("USER", "GOTO REGISTER");
            }
        });
    }
    void checkCurrentUser(){
        if (_Auth.getCurrentUser() != null){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).commit();
                Log.d("LOGIN", "LOGIN CURRENT AND GO TO MENU");
        }
    }
    void initLoginBtn(){
        Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _userId = getView().findViewById(R.id.login_user_id);
                EditText _password = getView().findViewById(R.id.login_password);
                String _userIdStr = _userId.getText().toString();
                String _passwordStr = _password.getText().toString();
                if (_userIdStr.isEmpty() || _passwordStr.isEmpty()){
                    Toast.makeText(getActivity(), "กรุณาระบุ user หรือ password", Toast.LENGTH_LONG).show();
                    Log.d("USER", "USER OR PASSWORD IS EMPTY");
                }else{
                    _Auth.signInWithEmailAndPassword(_userIdStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            if(_Auth.getCurrentUser().isEmailVerified()){
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).addToBackStack(null).commit();
                                Log.d("LOGIN", "LOGIN SUCCESS AND GO TO MENU");
                            } else {
                                Toast.makeText(getActivity(), "กรุณายืนยัน email", Toast.LENGTH_LONG).show();
                                Log.d("LOGIN", "Login unsucess and unconfirm email");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "ERROR - " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

}
