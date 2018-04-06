package com.example.soulreaper.accidentalert5.auth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.AuthUI.IdpConfig;
import com.firebase.ui.auth.IdpResponse;
import com.example.soulreaper.accidentalert5.R;
import com.example.soulreaper.accidentalert5.app.Config;
import com.example.soulreaper.accidentalert5.fragment.Tab1;
import com.example.soulreaper.accidentalert5.fragment.Tab2;
import com.example.soulreaper.accidentalert5.fragment.Tab3;
import com.example.soulreaper.accidentalert5.util.NotificationUtils;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SignedInActivity extends AppCompatActivity {

    private static final String TAG = "SignedInActivity";

    private static final String EXTRA_IDP_RESPONSE = "extra_idp_response";
    private static final String EXTRA_SIGNED_IN_CONFIG = "extra_signed_in_config";

    String regId;

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mFcm = mRootRef.child("Fcm");
    DatabaseReference mAccident = mRootRef.child("Accident");

    @BindView(android.R.id.content)
    View mRootView;

    @BindView(R.id.materialViewPager)
    MaterialViewPager mViewPager;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;

    private IdpResponse mIdpResponse;

    private SignedInConfig mSignedInConfig;

    public static Intent createIntent(
            Context context,
            IdpResponse idpResponse,
            SignedInConfig signedInConfig) {

        Intent startIntent = new Intent();
        if (idpResponse != null) {
            startIntent.putExtra(EXTRA_IDP_RESPONSE, idpResponse);
        }

        return startIntent.setClass(context, SignedInActivity.class)
                .putExtra(EXTRA_SIGNED_IN_CONFIG, signedInConfig);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            startActivity(AuthUiActivity.createIntent(this));
            finish();
            return;
        }

        mIdpResponse = getIntent().getParcelableExtra(EXTRA_IDP_RESPONSE);
        mSignedInConfig = getIntent().getParcelableExtra(EXTRA_SIGNED_IN_CONFIG);

        setContentView(R.layout.signed_in_layout);
        ButterKnife.bind(this);

        txtRegId = findViewById(R.id.txt_reg_id);
        txtMessage = findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

        mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                switch (position % 3) {
                    case 0:
                        Tab1 tab1 = new Tab1();
                        return tab1;
                    case 1:
                        Tab2 tab2 = new Tab2();
                        return tab2;
                    case 2:
                        Tab3 tab3 = new Tab3();
                        return tab3;
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position % 3) {
                    case 0:
                        return "General Data";
                    case 1:
                        return "Location";
                    case 2:
                        return "Msg Alert";
                }
                return "Location";
            }
        });

        mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
            @Override
            public HeaderDesign getHeaderDesign(int page) {
                switch (page) {
                    case 0:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.red,
                                "https://images8.alphacoders.com/679/679478.jpg");
                    case 1:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.blue,
                                "https://images6.alphacoders.com/712/712437.jpg");
                    case 2:
                        return HeaderDesign.fromColorResAndUrl(
                                R.color.cyan,
                                "https://images5.alphacoders.com/699/699273.png");

                }

                //execute others actions if needed (ex : modify your header logo)

                return null;
            }
        });

        mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
        mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

        mAccident.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ACC = dataSnapshot.getValue(String.class);
                int text = Integer.parseInt(ACC);
                if (text == 1)
                {
                    String url = "https://demo.androidhive.info/firebase/notifications/?apikey=AIzaSyD__hcbhzKAZeUNEnoFfc_hd00F8eaWNVo&regId=" + regId + "&title=Accident+Alert&message=Your+friend%2Ffamily+has+been+in+accident%21%21%21&push_type=individual";

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                final String myResponse = response.body().string();

                                SignedInActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });
                            }
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @OnClick(R.id.sign_out)
    public void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(AuthUiActivity.createIntent(SignedInActivity.this));
                            finish();
                        } else {
                            Log.w(TAG, "signOut:failure", task.getException());
                            showSnackbar(R.string.sign_out_failed);
                        }
                    }
                });
    }

    private void showSnackbar(@StringRes int errorMessageRes) {
        Snackbar.make(mRootView, errorMessageRes, Snackbar.LENGTH_LONG).show();
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
        {
            txtRegId.setText("Firebase Reg Id: " + regId);
            //mFcm.setValue(regId);
        }
        else
        {
            txtRegId.setText("Firebase Reg Id is not received yet!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    static final class SignedInConfig implements Parcelable {
        int logo;
        int theme;
        List<IdpConfig> providerInfo;
        String tosUrl;
        boolean isCredentialSelectorEnabled;
        boolean isHintSelectorEnabled;

        SignedInConfig(int logo,
                       int theme,
                       List<IdpConfig> providerInfo,
                       String tosUrl,
                       boolean isCredentialSelectorEnabled,
                       boolean isHintSelectorEnabled) {
            this.logo = logo;
            this.theme = theme;
            this.providerInfo = providerInfo;
            this.tosUrl = tosUrl;
            this.isCredentialSelectorEnabled = isCredentialSelectorEnabled;
            this.isHintSelectorEnabled = isHintSelectorEnabled;
        }

        SignedInConfig(Parcel in) {
            logo = in.readInt();
            theme = in.readInt();
            providerInfo = new ArrayList<>();
            in.readList(providerInfo, IdpConfig.class.getClassLoader());
            tosUrl = in.readString();
            isCredentialSelectorEnabled = in.readInt() != 0;
            isHintSelectorEnabled = in.readInt() != 0;
        }

        public static final Creator<SignedInConfig> CREATOR = new Creator<SignedInConfig>() {
            @Override
            public SignedInConfig createFromParcel(Parcel in) {
                return new SignedInConfig(in);
            }

            @Override
            public SignedInConfig[] newArray(int size) {
                return new SignedInConfig[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(logo);
            dest.writeInt(theme);
            dest.writeList(providerInfo);
            dest.writeString(tosUrl);
            dest.writeInt(isCredentialSelectorEnabled ? 1 : 0);
            dest.writeInt(isHintSelectorEnabled ? 1 : 0);
        }
    }

}
