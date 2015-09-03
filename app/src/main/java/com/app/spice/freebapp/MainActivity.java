package com.app.spice.freebapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import in.freeb.sdk.interfaces.FreeBOffersListener;
import in.freeb.sdk.utils.FreeBCommonUtility;
import in.freebsdk.FreeBOffers;


public class MainActivity extends Activity implements FreeBOffersListener{

    private Button offers;
    private Context mContext;
    private FreeBOffers freeBOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        freeBOffers = new FreeBOffers(MainActivity.this);
        mContext = getApplicationContext();
        offers = (Button) findViewById(R.id.offers);

        offers.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    freeBOffers.setOnFreeBOffersListener(MainActivity.this);
                    freeBOffers.setTitle(
                            "Pick Any Offer to unlock your premium features",
                            "#FFFFFF", "#FF6D00");

                } catch (Exception e) {
                }
            }
        });

    }




    @Override
    public void onOffersLoaded(String freeBOffers, String code) {
        FreeBCommonUtility.showToast(getApplicationContext(), code);

    }

    @Override
    public void onShowOffers() {}

    @Override
    public void noOfferInstalled(String s, String s2) {}

    @Override
    public void onLeaveApplication(String s, String s2) {}

    @Override
    public void onDialogDismiss(String s) {}


    @Override
    public void onOffersFailed(String code, String errorMessage) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
        FreeBCommonUtility.showToast(getApplicationContext(), errorMessage);

    }

    @Override
    public void onOffersInstallSuccess(String code, String errorMessage) {}

    @Override
    public void onOffersInstallFailure(String code, String errorMessage) {}

}
