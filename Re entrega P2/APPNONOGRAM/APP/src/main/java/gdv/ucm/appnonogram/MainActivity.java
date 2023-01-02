package gdv.ucm.appnonogram;

import androidx.appcompat.app.AppCompatActivity;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
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

import gdv.ucm.appnonogram.LOGIC.Board;
import gdv.ucm.appnonogram.NotificationWorker;
import gdv.ucm.appnonogram.R;
import gdv.ucm.appnonogram.SCENES.MainSceneRandom;
import gdv.ucm.appnonogram.SCENES.MainSceneRead;
import gdv.ucm.appnonogram.SCENES.TitleScene;

public class MainActivity extends AppCompatActivity {
    private EngineA engine;          // Motor de Android
    private StatsA statsA;           // Estadísticas del juego
    private SurfaceView renderView;  // Canvas
    private AdView mAdView;          // Adview para el banner
    private String filenameStats;    // Fichero serializado de estadísticas
    private String filenameBoard;    // Fichero serializado de tablero guardado
    private Board boardSaved;        // Tablero guardado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mAdView = findViewById(R.id.adView);
        this.renderView = findViewById(R.id.surfaceView);
        if(this.renderView == null)
            this.renderView = findViewById(R.id.surfaceView2);
        this.statsA = new StatsA();
        this.boardSaved = null;
        this.filenameStats = "stats.ser";
        this.filenameBoard = "t.ser";

//------------------------------------ANUNCIOS INICIALIZACIÓN------------------------------------
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                // cargamos los anuncios
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        if(this.mAdView != null)
            this.mAdView.loadAd(adRequest);

//      CREACION DEL MOTOR
        this.engine = new EngineA(this.renderView, 400,600,this, adRequest, this.filenameStats);
        this.engine.iniManagers();
        if (savedInstanceState != null)
            statsA = (StatsA) savedInstanceState.getSerializable("STATS_KEY");
        else{         //SI NO ES LA PRIMERA VEZ QUE SE USA PROBAR A CARGAR SERIALIZABLES
            // DESERIALIZACIÓN DE STATS
            try {
                // Creamos un FileInputStream para leer desde el archivo en el almacenamiento interno de la aplicación
                FileInputStream fis = openFileInput(filenameStats);
                // Creamos un ObjectInputStream a partir del FileInputStream
                ObjectInputStream ois = new ObjectInputStream(fis);
                // Leemos el objeto serializado del archivo y lo asignamos a una variable de tipo StatsA
                this.statsA = (StatsA) ois.readObject();

                // Cerramos los streams
                ois.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            // DESERIALIZACIÓN DE BOARD
            try {
                // Creamos un FileInputStream para leer desde el archivo en el almacenamiento interno de la aplicación
                FileInputStream fis = openFileInput(filenameBoard);
                // Creamos un ObjectInputStream a partir del FileInputStream
                ObjectInputStream ois = new ObjectInputStream(fis);
                // Leemos el objeto serializado del archivo y lo asignamos a una variable de tipo Board
                this.boardSaved = (Board) ois.readObject();

                // Cerramos los streams
                ois.close();
                fis.close();
                this.deleteFile(filenameBoard);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        this.engine.setStats(this.statsA);

        // Comenzar en la pantalla de titulo o en una partida
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
        if(this.engine.getSaveBoard()){ // GUARDAR TABLERO
            if(this.engine.getRandomBoard()){
                MainSceneRandom r = (MainSceneRandom) this.engine.getState();
                this.engine.getSerialization().serialize(r.getBoard(), filenameBoard);
            }
            else{
                MainSceneRead r = (MainSceneRead) this.engine.getState();
                this.engine.getSerialization().serialize(r.getBoard(), filenameBoard);
            }
        }
        this.engine.pause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        createWorkRequest();
    }


    //------------------------------------NOTIFICACIONES---------------------------
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("0" , "CANAL0", NotificationManager.IMPORTANCE_HIGH) ;
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