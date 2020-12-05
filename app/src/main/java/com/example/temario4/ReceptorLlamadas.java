package com.example.temario4;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;

public class ReceptorLlamadas extends BroadcastReceiver {
    public static final String NOTIFICATION_CHANNEL_ID = "2000";
    public static final String NOTIFICATION_CHANNEL_NAME = "MICANAL2";
    @Override public void onReceive(Context context, Intent intent) {
// Sacamos información de la intención
        String estado = "", numero = "";
        Bundle extras = intent.getExtras();
        if (extras != null) {
            estado = extras.getString(TelephonyManager.EXTRA_STATE);
            if (estado.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                numero = extras.getString(
                        TelephonyManager.EXTRA_INCOMING_NUMBER);
                String info = estado + " " + numero;
                Log.d("ReceptorAnuncio", info + " intent=" + intent);
// Creamos Notificación
                NotificationCompat.Builder notificacion = new
                        NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                        .setContentTitle("Llamada entrante ")
                        .setContentText(info)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(PendingIntent.getActivity(context, 0,
                                new Intent(context, Servicio.class), 0));
                NotificationManager notificationManager = (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel =
                            new NotificationChannel(
                                    NOTIFICATION_CHANNEL_ID,
                                    NOTIFICATION_CHANNEL_NAME,
                                    NotificationManager.IMPORTANCE_LOW);
                    notificationManager.createNotificationChannel(notificationChannel);
                    notificacion.setChannelId(NOTIFICATION_CHANNEL_ID);
                }
                notificationManager.notify(1, notificacion.build());
            }
        }
    }
}