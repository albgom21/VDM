package gdv.ucm.appnonogram;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.SurfaceView;
import com.example.libenginea.EngineA;
import gdv.ucm.liblogica.TitleScene;

public class MainActivity extends AppCompatActivity {
    private EngineA engine;          // Motor de Android
    private SurfaceView renderView;  // Canvas
    private AssetManager mgr;        // Manager recursos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mgr = getAssets(); // ver si se puede quitar---------------------------------------------------------------------

        //Crear el SurfaceView que "contendrá" la escena
        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);

        // Creación del motor de Android y la escena inicial
        this.engine = new EngineA(this.renderView);
        TitleScene scene = new TitleScene(engine);
        engine.setCurrentScene(scene);
        engine.resume();
    }
//    public void PlayBackgroundSound() {
//        Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
//        startService(intent);
//    }

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
}