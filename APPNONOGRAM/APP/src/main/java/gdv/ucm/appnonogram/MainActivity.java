package gdv.ucm.appnonogram;

import androidx.appcompat.app.AppCompatActivity;

import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import com.example.libenginea.EngineA;
import com.example.libenginea.StatsA;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private EngineA engine;          // Motor de Android
    private StatsA statsA;           // Motor de Android
    private SurfaceView renderView;  // Canvas
    private AssetManager mgr;        // Manager recursos
    private AdView mAdView;
    private String filenameStats;
    private Board boardSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mgr = getAssets();
        this.boardSaved = null;
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

//            GUARDAR TABLERO
            try {
                // Creamos un FileInputStream para leer desde el archivo en el almacenamiento interno de la aplicación
                FileInputStream fis = openFileInput("t.ser");
                // Creamos un ObjectInputStream a partir del FileInputStream
                ObjectInputStream ois = new ObjectInputStream(fis);
                // Leemos el objeto serializado del archivo y lo asignamos a una variable de tipo Persona
                this.boardSaved = (Board) ois.readObject();

                // Cerramos los streams
                ois.close();
                fis.close();
                this.deleteFile("t.ser");
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

        if(this.boardSaved != null){
            if(this.boardSaved.getRandom()){
                MainSceneRandom scene = new MainSceneRandom(this.engine,this.boardSaved);
                engine.setCurrentScene(scene);
            }
            else{
                MainSceneRead scene = new MainSceneRead(this.engine,this.boardSaved);
                engine.setCurrentScene(scene);
            }
        }
        else{
            TitleScene scene = new TitleScene(this.engine);
            engine.setCurrentScene(scene);
        }
        engine.resume();

        createChannel();
        createWorkRequest();
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
        int monedasExtras = this.getIntent().getIntExtra("Monedas", 0);
        this.engine.getStats().addMoneda(monedasExtras);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.engine.getSaveBoard()){
            if(this.engine.getRandomBoard()){
                MainSceneRandom r = (MainSceneRandom) this.engine.getState();
                this.engine.serialize(r.getBoard(), "t.ser");
            }
            else{
                MainSceneRead r = (MainSceneRead) this.engine.getState();
                this.engine.serialize(r.getBoard(), "t.ser");
            }

        }
        this.engine.pause();

    }

//    NOTIFICACIONES-------------------------------------------------------------------------------------
    private void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("0" , "Pruebas", NotificationManager.IMPORTANCE_HIGH) ;
            NotificationManager notificationManager = this.getSystemService(NotificationManager. class);
            notificationManager.createNotificationChannel(channel) ;
        }
    }

    private void createWorkRequest(){
//        WorkRequest uploadWorkRequest =
//                new OneTimeWorkRequest.Builder(NotificationWorker.class)
//                        .setInitialDelay(5, TimeUnit.SECONDS)
//                        .build();

        PeriodicWorkRequest uploadWorkRequest =
                new PeriodicWorkRequest.Builder(NotificationWorker.class,
                        15, TimeUnit.MINUTES,
                        5, TimeUnit.MINUTES)
                        .build();

        WorkManager.getInstance(this).enqueue(uploadWorkRequest);
    }
}