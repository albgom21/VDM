package gdv.ucm.appnonogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.SurfaceView;

import com.example.libenginea.EngineA;
import gdv.ucm.liblogica.MainScene;
import gdv.ucm.liblogica.TitleScene;

public class MainActivity extends AppCompatActivity {
    private EngineA engine;

    private SurfaceView renderView;

    private AssetManager mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mgr = getAssets();

        //Creamos el SurfaceView que "contendrá" nuestra escena
        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);
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