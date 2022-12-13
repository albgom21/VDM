package gdv.ucm.appnonogram;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.SurfaceView;
import com.example.libenginea.EngineA;
import com.example.libenginea.StatsA;

public class MainActivity extends AppCompatActivity {
    private EngineA engine;          // Motor de Android
    private StatsA statsA;          // Motor de Android
    private SurfaceView renderView;  // Canvas
    private AssetManager mgr;        // Manager recursos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mgr = getAssets();

        //Crear el SurfaceView que "contendrá" la escena
        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);

        if (savedInstanceState != null) {
            statsA = (StatsA) savedInstanceState.getSerializable("STATS_KEY");
        }

        // Creación del motor de Android y la escena inicial
        this.engine = new EngineA(this.renderView, statsA, this);
        TitleScene scene = new TitleScene(engine);
        engine.setCurrentScene(scene);
        engine.resume();
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
            System.out.println("XXXXXXXXXXXXXXXXXXXXXX\n");
            //Poner la res del movil-------------------------
            this.engine.getGraphics().setLogicWidth(1920);
            this.engine.getGraphics().setLogicHeight(1080);
        }
        super.onPause();
        this.engine.pause();
    }
}