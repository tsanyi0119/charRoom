package com.example.chatroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class LooginActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton button_SignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loogin);
        init();
    }
    private void init(){
        setSignInClient();
        setupUI();

        //訂閱 "news" 的主題，以接收與該主題相關的 FCM 通知。
        FirebaseMessaging.getInstance().subscribeToTopic("news");
        //獲取裝置的 FCM 註冊令牌 (token)
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful())return;
                String token = task.getResult();
            }
        });
    }
    private void setupUI(){
        SignInButton button_SignIn = findViewById(R.id.button_SignIn);
        button_SignIn.setOnClickListener(v->{
            startActivityForResult(mGoogleSignInClient.getSignInIntent(),200);
        });
    }
    private void setSignInClient(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("301125784448-fstgubsgvnh01v60c4bl2pnntc0qsm2t.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("LoginActivity","成功登出");

                    } else {
                        // 登出失败
                        Log.d("LoginActivity","登出失敗");
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String result = "登入成功\nEmail："+account.getEmail()+"\nGoogle名稱："
                        +account.getDisplayName();
                Log.d("LoginActivity", "Token: "+account.getIdToken());

                Intent intent = new Intent(LooginActivity.this,ChatRoomActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);

            } catch (ApiException e) {
                Log.w("LoginActivity", "Google sign in failed", e);
            }
        }
    }
}