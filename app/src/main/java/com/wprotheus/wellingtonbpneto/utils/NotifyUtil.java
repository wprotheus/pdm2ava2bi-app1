package com.wprotheus.wellingtonbpneto.utils;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.wprotheus.wellingtonbpneto.R;
import com.wprotheus.wellingtonbpneto.view.MainActivity;

public class NotifyUtil {

    private static final String CHANNEL_ID = "8";
    private static final int NOTIFICATION_ID = 17;
    private static final int CODIGO_SOLICITACAO = 26;
    private static final String PERMISSAO = Manifest.permission.POST_NOTIFICATIONS;
    private static NotificationManagerCompat managerCompat;

    public static void criarNotificacao(Context context) {
        criarCanalNotificacao(context);
        solicitarPermissao(context);
    }

    private static void criarCanalNotificacao(Context context) {
        CharSequence nome = context.getString(R.string.channel);
        String descricao = context.getString(R.string.desc_channel);
        int importancia = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel canal = new NotificationChannel(CHANNEL_ID, nome, importancia);
        canal.setDescription(descricao);
        NotificationManager nm = context.getSystemService(NotificationManager.class);
        nm.createNotificationChannel(canal);
    }

    private static void solicitarPermissao(Context context) {
        int temPermissao = ContextCompat.checkSelfPermission(context, PERMISSAO);
        if (temPermissao != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{PERMISSAO}, CODIGO_SOLICITACAO);
        } else {
            gerarNotificacao(context);
        }
    }

    private static void gerarNotificacao(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.olympus);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.title_not))
                .setContentText(context.getString(R.string.desc_not))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pi)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.nome_classe)));

        managerCompat = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{PERMISSAO}, CODIGO_SOLICITACAO);
            return;
        }
        managerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, Context context) {
        if (requestCode != CODIGO_SOLICITACAO) {
            return;
        }
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            gerarNotificacao(context);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, PERMISSAO)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle(R.string.perm_title)
                        .setMessage(R.string.msg_perm)
                        .setCancelable(false)
                        .setPositiveButton(R.string.btn_sim, (dialog, which) ->
                                ActivityCompat.requestPermissions((Activity) context, new String[]{PERMISSAO}, CODIGO_SOLICITACAO))
                        .setNegativeButton(R.string.btn_nao, (dialog, which) ->
                                Toast.makeText(context, R.string.msg_sair, Toast.LENGTH_SHORT).show());
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        }
    }
}