package gdv.ucm.appnonogram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import com.example.libenginea.EngineA;
import com.example.libenginea.StatsA;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    private EngineA engine;          // Motor de Android
    private StatsA statsA;          // Motor de Android
    private SurfaceView renderView;  // Canvas
    private AssetManager mgr;        // Manager recursos
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mgr = getAssets();

        setContentView(R.layout.activity_main);

        this.mAdView = findViewById(R.id.adView);
        this.renderView = findViewById(R.id.surfaceView);

        if (savedInstanceState != null) {
            statsA = (StatsA) savedInstanceState.getSerializable("STATS_KEY");
        }

        // Creación del motor de Android y la escena inicial
        this.engine = new EngineA(this.renderView, statsA, this);
        TitleScene scene = new TitleScene(this.engine);
        engine.setCurrentScene(scene);
        engine.resume();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                // cargamos los anuncios
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        this.mAdView.loadAd(adRequest);


        // REWARDED
        //AdRequest adRequestReward = new AdRequest.Builder().build();


//        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//            @Override
//            public void onAdClicked() {
//                // Called when a click is recorded for an ad.
//                Log.d(TAG, "Ad was clicked.");
//            }
//
//            @Override
//            public void onAdDismissedFullScreenContent() {
//                // Called when ad is dismissed.
//                // Set the ad reference to null so you don't show the ad a second time.
//                Log.d(TAG, "Ad dismissed fullscreen content.");
//                mRewardedAd = null;
//            }
//
//            @Override
//            public void onAdFailedToShowFullScreenContent(AdError adError) {
//                // Called when ad fails to show.
//                Log.e(TAG, "Ad failed to show fullscreen content.");
//                mRewardedAd = null;
//            }
//
//            @Override
//            public void onAdImpression() {
//                // Called when an impression is recorded for an ad.
//                Log.d(TAG, "Ad recorded an impression.");
//            }
//
//            @Override
//            public void onAdShowedFullScreenContent() {
//                // Called when ad is shown.
//                Log.d(TAG, "Ad showed fullscreen content.");
//            }
//        });

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("STATS_KEY", engine.getStats());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {


        super.onRestoreInstanceState(savedInstanceState);
        statsA = (StatsA) savedInstanceState.getSerializable("STATS_KEY");
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.engine.resume();
    }

    @Override
    protected void onPause() {
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Poner la res del movil-------------------------
            this.engine.getGraphics().setLogicWidth(1920);
            this.engine.getGraphics().setLogicHeight(1080);
        }
        super.onPause();
        this.engine.pause();
    }
}