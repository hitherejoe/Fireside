package co.joebirch.fireside;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class MainActivity extends AppCompatActivity {

    private FirebaseRemoteConfig firebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
    }

}
