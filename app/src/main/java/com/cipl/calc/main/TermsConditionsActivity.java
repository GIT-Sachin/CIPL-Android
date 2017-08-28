package com.cipl.calc.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class TermsConditionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        configureTermsAndConditions();
        configureAccept();
        configureDecline();
        configureWebView();
    }

    private void configureTermsAndConditions() {
        setFontGothic(R.id.termsAndConditions);
    }

    private void configureWebView() {
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/html/t&c.html");
    }

    private void configureDecline() {
        Button decline = (Button) findViewById(R.id.declineButton);
        setFontGothic(R.id.declineButton);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeApplication();
            }
        });
    }

    private void configureAccept() {
        Button accept = (Button) findViewById(R.id.acceptButton);
        setFontGothic(R.id.acceptButton);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeStateInDatabase();
                sendMessageToChaplot();
                launchMainActivity();
            }

            private void sendMessageToChaplot() {
            }

            private void storeStateInDatabase() {
                SharedPreferences settings = getSharedPreferences("TERMS_ACCEPTED", MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean("T&C_ACCEPTED", true);
                editor.commit();
            }
        });
    }

    private void closeApplication() {
        finish();
        System.exit(0);
    }

    private void setFontGothic(int id) {
        TextView cb = (TextView) findViewById(id);
        Typeface bodoni = Typeface.createFromAsset(getAssets(), "fonts/centurygothic.TTF");
        cb.setTypeface(bodoni);
    }

    private void launchMainActivity() {
        Intent intent = new Intent(TermsConditionsActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }


}
