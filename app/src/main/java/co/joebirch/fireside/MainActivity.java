package co.joebirch.fireside;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {

    private static final String PARAMETER_SHOULD_SHOW_CHAT = "should_show_chat";

    private FirebaseRemoteConfig firebaseRemoteConfig;
    private boolean shouldShowChatActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

        FirebaseRemoteConfigSettings configSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(BuildConfig.DEBUG)
                        .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);
        shouldShowChatActivity = firebaseRemoteConfig.getBoolean(PARAMETER_SHOULD_SHOW_CHAT);

        setupHelpButton();
        retrieveValues();
    }

    private void setupHelpButton() {
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shouldShowChatActivity) {
                    startChatActivity();
                } else {
                    startFaqActivity();
                }
            }
        });
    }

    private void retrieveValues() {
        firebaseRemoteConfig.fetch().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    firebaseRemoteConfig.activateFetched();
                }
                shouldShowChatActivity = firebaseRemoteConfig.getBoolean(
                        PARAMETER_SHOULD_SHOW_CHAT);
            }
        });
    }

    private void startChatActivity() {
        startActivity(new Intent(this, ChatActivity.class));
    }

    private void startFaqActivity() {
        startActivity(new Intent(this, FaqActivity.class));
    }

}
