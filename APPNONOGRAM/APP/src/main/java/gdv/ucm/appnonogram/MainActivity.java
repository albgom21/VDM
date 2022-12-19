package gdv.ucm.appnonogram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private EngineA engine;          // Motor de Android
    private StatsA statsA;           // Motor de Android
    private SurfaceView renderView;  // Canvas
    private AssetManager mgr;        // Manager recursos
    private AdView mAdView;
    private String filenameStats;
    private NotificationManager notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mgr = getAssets();

        setContentView(R.layout.activity_main);

        this.mAdView = findViewById(R.id.adView);
        this.renderView = findViewById(R.id.surfaceView);
        this.statsA = new StatsA();
        this.filenameStats = "stats.ser";
        if (savedInstanceState != null)
         statsA = (StatsA) savedInstanceState.getSerializable("STATS_KEY");
        else{
            try {
                // Creamos un FileInputStream para leer desde el archivo en el almacenamiento interno de la aplicación
                FileInputStream fis = openFileInput(filenameStats);
                // Creamos un ObjectInputStream a partir del FileInputStream
                ObjectInputStream ois = new ObjectInputStream(fis);
                // Leemos el objeto serializado del archivo y lo asignamos a una variable de tipo Persona
                this.statsA = (StatsA) ois.readObject();

                // Cerramos los streams
                ois.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
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
        this.engine = new EngineA(this.renderView, statsA, this, adRequest, this.filenameStats);
        TitleScene scene = new TitleScene(this.engine);
        engine.setCurrentScene(scene);
        engine.resume();

        createChannel();
        createWorkRequest();

        int monedasExtras = this.getIntent().getIntExtra("Monedas",0);
        this.engine.getStats().addMoneda(monedasExtras);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("STATS_KEY", engine.getStats());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            this.statsA = (StatsA) savedInstanceState.getSerializable("STATS_KEY");
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

//    NOTIFICACIONES-------------------------------------------------------------------------------------
    private void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("0" , "Pruebas", NotificationManager.IMPORTANCE_HIGH) ;
            notificationManager = this.getSystemService(NotificationManager. class);
            notificationManager.createNotificationChannel(channel) ;
        }
    }

    private void createWorkRequest(){
        WorkRequest uploadWorkRequest =
                new OneTimeWorkRequest.Builder(NotificationWorker.class)
                        .setInitialDelay(15, TimeUnit.SECONDS)
                        .build();

//        PeriodicWorkRequest uploadWorkRequest =
//                new PeriodicWorkRequest.Builder(NotificationWorker.class,
//                        15, TimeUnit.MINUTES)
//                        .build();

        WorkManager.getInstance(this).enqueue(uploadWorkRequest);
    }

    public class NotificationWorker extends Worker{

        Context context;

        public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
            this.context = context;
        }

        @NonNull
        @Override
        public Result doWork() {

            Intent notifyIntent = new Intent(this.context, this.context.getClass());
            notifyIntent.putExtra("Monedas",10);
            // Set the Activity to start in a new, empty task
            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // Create the PendingIntent
            PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                    this.context, 0, notifyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, "0")
                    .setSmallIcon(com.example.libenginea.R.drawable.ic_stat_name)
                    .setColor(Color.RED)
                    .setContentTitle( "Entra ahora para conseguir 10 monedas gratis" )
                    .setContentText( "¡Llevas tiempo sin jugar, entra ahora!" )
                    .setStyle( new NotificationCompat.BigTextStyle()
                            .bigText( "¡Llevas tiempo sin jugar, entra ahora!" ))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(notifyPendingIntent)
                    .setAutoCancel(true);
            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            // Enviar la notificación
            notificationManager.notify(1, builder.build());

            return Result.success();
        }
    }
}