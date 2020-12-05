package com.example.temario4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.text.Html;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ServicioMusica extends Service {
    private static final int ID_NOTIFICACION_CREAR = 1;
    public static final String NOTIFICATION_CHANNEL_ID = "1000";
    public static final String NOTIFICATION_CHANNEL_NAME = "MICANAL";
    public static MediaPlayer reproductor;
    @Override
    public void onCreate() {
        Toast.makeText(this,"Servicio creado", Toast.LENGTH_SHORT).show();
        reproductor = MediaPlayer.create(this, R.raw.musica);
    }
    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        Intent intencionLlamar = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:555123456"));
        PendingIntent intencionPendienteLlamar =
                PendingIntent.getActivity(this,0, intencionLlamar,0);
        NotificationCompat.Builder notific = new NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Creando Servicio de Música")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(Html.fromHtml("<b>Notificación</b> <u>Android<i>Curso</i></u>"))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        android.R.drawable.ic_media_play))
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Nueva Conferencia Los neutrinos")
                        .addLine("Nuevo curso Android Wear")
                        .setBigContentTitle("2 notificaciones AndroidCurso")
                        .setSummaryText("info@upt.pe"))
                .setNumber(4)
                .addAction(android.R.drawable.ic_menu_call, "llamar", intencionPendienteLlamar)
                .addAction(android.R.drawable.ic_menu_call, "contestar", intencionPendienteLlamar)
                .addAction(android.R.drawable.ic_menu_call, "bloquear", intencionPendienteLlamar)
                // .setAutoCancel(true)
                .setSubText("más info");
        // Para lanzar una actividad
        PendingIntent intencionPendiente = PendingIntent.getActivity(
                this, 0, new Intent(this, Servicio.class), 0);
        notific.setContentIntent(intencionPendiente);

        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel =
                    new NotificationChannel(
                            NOTIFICATION_CHANNEL_ID,
                            NOTIFICATION_CHANNEL_NAME,
                            NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(R.color.colorAccent);
            notificationManager.createNotificationChannel(notificationChannel);
            notific.setChannelId(NOTIFICATION_CHANNEL_ID);
        }
        //notificationManager.notify(ID_NOTIFICACION_CREAR, notific.build());

        Toast.makeText(this,"Servicio arrancado "+ idArranque,
                Toast.LENGTH_SHORT).show();
        reproductor.start();
        startForeground(101,notific.build());
        return START_STICKY;
    }
    @Override public void onDestroy() {
        Toast.makeText(this,"Servicio detenido",
                Toast.LENGTH_SHORT).show();
        reproductor.stop();
        //Eliminando la notificacion
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ID_NOTIFICACION_CREAR);

    }
    @Override public IBinder onBind(Intent intencion) {
        return null;
    }
}