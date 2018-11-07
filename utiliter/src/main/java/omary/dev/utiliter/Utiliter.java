package omary.dev.utiliter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Utiliter {

    static ProgressDialog dialog;

    /*ToDo: To display an alert with text message*/
    public static void ShowToast(Context context,String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    /*ToDo: To display an error alert with text message*/
    public static void ShowErrorToast(Context context,String message) {
        Toast toast = Toast.makeText(context, "Error: " + " " + message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /*ToDo: To display a progress dialog with text message*/
    public static void ShowLoading(Context context,String message) {
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    /*ToDo: To hide an open progress dialog*/
    public static void HideLoading() {
        if (dialog != null)
            dialog.hide();
    }

    /*ToDo: To move to another activity*/
    public static void MoveToActivity(Context context,Class to) {
        Intent intent = new Intent(context, to);
        context.startActivity(intent);
    }

    /*ToDo: To update UI after language changed by user */
    public static void updateResources(Context context,String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        RefreshActivity(context);
    }

    /*ToDo: Check network state */
    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }

    /*ToDo: Show Dialog Box */
    public static void ShowDialogBox(Context ctx, String message, String title) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ctx);
        builderSingle.setCancelable(false);
        builderSingle.setTitle(title);

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                });

        builder.create();
        builder.show();
    }

    /*ToDo: Get Current Date with format */
    public static String getCurrentTimeStamp(String format) {
        return new SimpleDateFormat(format).format(new Date());
    }

    /*ToDo: Delete App Storage*/
    public static void deleteAppData(Context ctx) {
        try {
            Runtime.getRuntime().exec("pm clear " + ctx.getPackageName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*ToDo: Generate Notification */
    public static void Notify(Context c, String message, String title, boolean IsAutoCancel) {
        int notificationId = new Random().nextInt(60000);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(c, "M_CH_ID")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(IsAutoCancel)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
    }

    /*ToDo: Convert Cursor To JsonArray*/
    public static JSONArray cursorToJsonArray(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;

    }

    private static void RefreshActivity(Context context) {
        Intent intent = ((Activity) context).getIntent();
        ((Activity) context).finish();
        context.startActivity(intent);
    }

}
