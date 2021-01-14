package com.video.glitcheffect;

import android.app.Activity;


public class Ad_class {

//    public static InterstitialAd interstitial;
//    public static AdRequest adRequest;
    public static int count = 1;
    public static int total = 5;


    public static void loadfullad(Activity activity) {
//        if (interstitial == null) {
//            interstitial = new InterstitialAd(activity);
//            interstitial.setAdUnitId(activity.getString(R.string.g_inr));
//        }
//
//        if (!interstitial.isLoaded()) {
//            adRequest = new AdRequest.Builder().build();
//            interstitial.loadAd(adRequest);
//        }

    }

    public static void showFullAd(Activity activity, onLisoner onLisoner) {


//        if (interstitial == null) {
//            onLisoner.click();
//            return;
//        }
//        if (!interstitial.isLoaded()) {
//            onLisoner.click();
//            return;
//        }
//        interstitial.setAdListener(new AdListener() {
//            @Override
//            public void onAdLoaded() {
//
//            }
//
//            @Override
//            public void onAdFailedToLoad(int errorCode) {
//                // Code to be executed when an ad request fails.
//            }
//
//            @Override
//            public void onAdOpened() {
//                // Code to be executed when the ad is displayed.
//            }
//
//            @Override
//            public void onAdClicked() {
//
//                // Code to be executed when the user clicks on an ad.
//            }
//
//            @Override
//            public void onAdLeftApplication() {
//                // Code to be executed when the user has left the app.
//            }
//
//            @Override
//            public void onAdClosed() {
//                onLisoner.click();
//
//                loadfullad(activity);
//
//            }
//        });
//        interstitial.show();
    }

    public interface onLisoner {
        void click();
    }

    public static void showBanner(/*AdView mAdView*/) {
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
    }
}



