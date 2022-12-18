package gdv.ucm.appnonogram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import android.content.Context;
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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                // cargamos los anuncios
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        this.mAdView.loadAd(adRequest);

        // Creación del motor de Android y la escena inicial
        this.engine = new EngineA(this.renderView, statsA, this, adRequest);
        TitleScene scene = new TitleScene(this.engine);
        engine.setCurrentScene(scene);
        engine.resume();
        createWorkRequest();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Poner la res del movil-------------------------
//            this.engine.getGraphics().setLogicWidth(1920);
//            this.engine.getGraphics().setLogicHeight(1080);
        }
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

        super.onPause();
        this.engine.pause();
    }
    private void createWorkRequest(){
        HashMap<String, Object> dataValues = new HashMap<>();
        dataValues.put("chanel", "nonogram_prueba");
        dataValues.put("smallIcon", androidx.constraintlayout.widget.R.drawable.notification_template_icon_low_bg);
        Data inputData = new Data.Builder().putAll(dataValues).build();
        WorkRequest uploadWorkRequest =
                new OneTimeWorkRequest.Builder(IntentWork.class)
                        // Additional configuration
                        .addTag("String")
                        .setInitialDelay(30, TimeUnit.SECONDS)
                        .setInputData(inputData)
                        .build();

//        PeriodicWorkRequest uploadWorkRequest =
//                new PeriodicWorkRequest.Builder(IntentWork.class,
//                        15, TimeUnit.MINUTES,
//                        5, TimeUnit.MINUTES)
//                        // Constraints
//                        .addTag("String")
//                        .setInputData(inputData)
//                        .build();

        WorkManager.getInstance(this).enqueue(uploadWorkRequest);
        //WorkManager.getInstance(this).getWorkInfosByTag("String");
    }

    public class IntentWork extends Worker{

        Context context;

        public IntentWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
            this.context = context;
        }

        @NonNull
        @Override
        public Result doWork() {

            //Intent intent = new Intent(context , MainActivity.);
            //PendingIntent contentIntent = PendingIntent. getActivity(context, 0, intent, PendingIntent. FLAG_UPDATE_CURRENT);
            Data data = getInputData();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, data.getString("chanel"))
                    .setSmallIcon(data.getInt("smallIcon", androidx.constraintlayout.widget.R.drawable.notification_template_icon_low_bg))
                    .setContentTitle( "My notification" )
                    .setContentText( "Much longer text that cannot fit one line..." )
                    .setStyle( new NotificationCompat.BigTextStyle()
                            .bigText( "Much longer text that cannot fit one line..." ))
                    .setPriority(NotificationCompat. PRIORITY_DEFAULT)
                    //.setContentIntent(contentIntent)
                    .setAutoCancel(true);


            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            managerCompat.notify(1, builder.build());

            return Result.success();
        }
    }
}