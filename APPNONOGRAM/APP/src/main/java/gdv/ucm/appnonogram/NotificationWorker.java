package gdv.ucm.appnonogram;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {

    private Context context;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        Intent notifyIntent = new Intent(this.context, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notifyIntent.putExtra("Monedas",10);
        // Set the Activity to start in a new, empty task

        // Create the PendingIntent
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this.context, 0, notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

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
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        // Enviar la notificación
        notificationManager.notify(1, builder.build());

        return Result.success();
    }
}