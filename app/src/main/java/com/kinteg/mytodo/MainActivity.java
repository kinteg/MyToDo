package com.kinteg.mytodo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kinteg.mytodo.realm.Settings;

import java.util.Locale;
import java.util.Objects;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    final int NOTIFI_ID = 1002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        setTheme();
        setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    public void language(View view) {
        AlertDialog.Builder setLanguage = new AlertDialog.Builder(this);
        setLanguage.setTitle(getResources().getString(R.string.language_selection));
        String[] languages = getResources().getStringArray(R.array.languages);

        setLanguage.setItems(languages, (dialog, which) -> {
            realm.beginTransaction();
            Objects.requireNonNull(realm.where(Settings.class).findFirst()).setLocale(which == 1 ? "en" : "ru");
            realm.commitTransaction();
            setLanguage();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        setLanguage.show();
    }

    public void theme(View view) {
        AlertDialog.Builder setTheme = new AlertDialog.Builder(this);
        setTheme.setTitle(getResources().getString(R.string.language_selection));
        String[] themes = getResources().getStringArray(R.array.themes);
        setTheme.setItems(themes, (dialog, which) -> {
            realm.beginTransaction();
            Objects.requireNonNull(realm.where(Settings.class).findFirst()).setTheme(which == 1 ? R.style.Theme_AppCompat : R.style.AppTheme);
            realm.commitTransaction();
            setTheme();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        setTheme.show();
    }

    private void setLanguage() {
        realm.beginTransaction();
        if (realm.where(Settings.class).findAll().size() == 0) {
            Settings settings = realm.createObject(Settings.class);
            settings.setLocale(getResources().getConfiguration().locale.toString());
            settings.setTheme(R.style.AppTheme);
        }
        String string = Objects.requireNonNull(realm.where(Settings.class).findFirst()).getLocale();
        Locale locale = new Locale(string);
        Locale.setDefault(locale);
        Configuration configuration = getResources().getConfiguration();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        onConfigurationChanged(configuration);
        realm.commitTransaction();
    }

    private void setTheme() {
        realm.beginTransaction();
        if (realm.where(Settings.class).findAll().size() == 0) {
            Settings settings = realm.createObject(Settings.class);
            settings.setLocale(getResources().getConfiguration().locale.toString());
            settings.setTheme(R.style.AppTheme);
        }
        int i = Objects.requireNonNull(realm.where(Settings.class).findFirst()).getTheme();
        super.setTheme(i);
        realm.commitTransaction();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        createNotification(getResources().getString(R.string.app_name));
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        openQuitDialog();
    }

    private void openQuitDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle(getResources().getString(R.string.resources))
                .setPositiveButton(getResources().getString(R.string.yes), (dialog, id) -> finish())
                .setNegativeButton(getResources().getString(R.string.no), (dialog, which) -> dialog.cancel());
        builder.show();
    }

    public void createNotification(String aMessage) {
        String id = "my_package_channel_1";

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        builder.setContentTitle(aMessage)
                .setSmallIcon(android.R.drawable.ic_popup_reminder)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_dashboard_black_24dp))
                .setContentText(getResources().getString(R.string.exit))
                .setAutoCancel(true)
                .setTicker(aMessage)
                .setContentIntent(pendingIntent);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, "myChannel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("my_package_first_channel");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notifManager.createNotificationChannel(channel);
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent1 = new Intent(android.provider.Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(android.provider.Settings.EXTRA_CHANNEL_ID, channel.getId());
                intent.putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, getPackageName());
                startActivity(intent1);
            }
        } else {
            builder.setPriority(Notification.PRIORITY_HIGH)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        }

        Notification notification = builder.build();
        notifManager.notify(NOTIFI_ID, notification);
    }

}
