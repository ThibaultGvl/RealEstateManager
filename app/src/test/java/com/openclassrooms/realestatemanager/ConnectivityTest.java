package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNetworkInfo;

import java.util.Objects;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

@Config(sdk = {Build.VERSION_CODES.O_MR1})
@RunWith(AndroidJUnit4.class)
public class ConnectivityTest {
    private ConnectivityManager mConnectivityManager;
    private ShadowNetworkInfo mShadowOfActiveNetworkInfo;

    @Before
    public void setUp() {
        mConnectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        mShadowOfActiveNetworkInfo = shadowOf(Objects.requireNonNull(mConnectivityManager).getActiveNetworkInfo());
    }

    @Test
    public void isConnected() {
        mShadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTED);
        assert(Utils.isInternetAvailable(getApplicationContext()));
    }

    @Test
    public void isConnecting() {
        mShadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.CONNECTING);
        assert(!Utils.isInternetAvailable(getApplicationContext()));
    }

    @Test
    public void isDisconnected() {
        mShadowOfActiveNetworkInfo.setConnectionStatus(NetworkInfo.State.DISCONNECTED);
        assert(!Utils.isInternetAvailable(getApplicationContext()));
    }
}
