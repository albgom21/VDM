package com.example.libenginea;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdManager {
    private Activity context;
    private EngineA engineA;
    private RewardedAd mRewardedAd;
    private AdRequest adRequest;
    private final String TAG = "MainActivity";

    public AdManager(EngineA engineA,Activity context, AdRequest adRequest){
        this.engineA = engineA;
        this.context = context;
        this.adRequest = adRequest;
    }

    public void preloadReward()
    {
        RewardedAd.load(this.context, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        System.out.println("Error adLoad");
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        System.out.println("Reward added");
                    }
                });

    }
    public void showReward() {
        Activity act = this.context;
        this.context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // LOAD ----------------------------------------------------------------------------------
                if (mRewardedAd != null) {
                    // SET_CALLBACK -----------------------------------------------------------------------------
                    mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            // Called when ad is dismissed.
                            // Set the ad reference to null so you don't show the ad a second time.
                            Log.d(TAG, "Ad dismissed fullscreen content.");
                            //mRewardedAd = null;
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            // Called when ad fails to show.
                            Log.e(TAG, "Ad failed to show fullscreen content.");
                            //mRewardedAd = null;
                        }

                        @Override
                        public void onAdImpression() {
                            // Called when an impression is recorded for an ad.
                            Log.d(TAG, "Ad recorded an impression.");
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
//                            preloadReward();
                            // Called when ad is shown.
                            Log.d(TAG, "Ad showed fullscreen content.");
                        }
                    });

                    // SHOW ----------------------------------------------------------------------
                    mRewardedAd.show(act, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            engineA.setRewardObtain(true);
                        }
                    });

                    //mRewardedAd = null;
                    preloadReward();
                } else {
                    System.out.println("The rewarded ad wasn't ready yet.");
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }
            }
        });
    }
}
