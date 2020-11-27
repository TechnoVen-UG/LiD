package com.dot.lid.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dot.lid.R;
import com.dot.lid.databinding.ActivityPolicyBinding;
import com.dot.lid.view.instruction.InstructionActivity;

public class PolicyActivity extends AppCompatActivity {

    private static final String termsUrl = "http://technoven.de/lid/terms_&_conditions.html";
    private static final String policyUrl = "http://technoven.de/lid/privacy_policy.html";
    private static final String text = "By using LiD app, you agree to our Terms & Conditions and that you have read our Privacy Policy.";
    private ActivityPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SpannableString spannableString = new SpannableString(text);

        final ClickableSpan termsSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(PolicyActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL_KEY, termsUrl);
                intent.putExtra(WebViewActivity.TITLE_KEY, "Terms & Conditions");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        };

        ClickableSpan policySpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(PolicyActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL_KEY, policyUrl);
                intent.putExtra(WebViewActivity.TITLE_KEY, "Privacy Policy");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        };

        spannableString.setSpan(termsSpan, 35, 53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(policySpan, 81, 95, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.policyTV.setText(spannableString);
        binding.policyTV.setMovementMethod(LinkMovementMethod.getInstance());

        binding.okBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PolicyActivity.this, InstructionActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}