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
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import omary.dev.utiliter.Interfaces.IUtiliter;

public class Utiliter {

    static ProgressDialog dialog;

    /*ToDo: To display an alert with text message*/
    public static void Toast(@NonNull Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    /*ToDo: To display an error alert with text message*/
    public static void ShowErrorToast(@NonNull Context context, String message) {
        Toast toast = Toast.makeText(context, "Error: " + " " + message, Toast.LENGTH_SHORT);
        toast.show();
    }

    /*ToDo: To display a progress dialog with text message*/
    public static void OpenLoadingDialog(@NonNull Context context, String message) {
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
    public static void StartActivity(Context context, Class activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    /*ToDo: To update UI after language changed by user */
    public static void UpdateLanguageResource(@NonNull Context context, String language) {
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
    public static boolean isNetworkAvailable(@NonNull Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
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

    /*ToDo: Get Current Date with format */
    public static String getCurrentTimeStamp(@NonNull String format) {
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
    public static void Notify(Context c, String message, String title, boolean isAutoCancel) {
        int notificationId = new Random().nextInt(60000);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(c, "M_CH_ID")
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(isAutoCancel)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notificationBuilder.build());
    }

    /*ToDo: Convert Cursor To JsonArray*/
    public static JSONArray cursorToJsonArray(@NonNull Cursor cursor) {

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

    private static void RefreshActivity(@NonNull Context context) {
        Intent intent = ((Activity) context).getIntent();
        ((Activity) context).finish();
        context.startActivity(intent);
    }

    /*ToDo: Show Dialog Box */
    public static void ShowDialogBox(@NonNull Context context, String title, String message, boolean IsCancelable) {
        ShowDialogBox(context, title, message, IsCancelable, null);
    }

    /*ToDo: Show Dialog Box With Positive and Negative Buttons*/
    public static void ShowDialogBox(@NonNull Context context, String title, String message, boolean IsCancelable, final IUtiliter.DialogButtonsClickListener dialogButtonsClickLitener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(IsCancelable);
        builder.setTitle(title);
        builder.setMessage(message);

        if (dialogButtonsClickLitener != null) {
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialogButtonsClickLitener.OnPositiveButtonClick();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialogButtonsClickLitener.OnNegativeButtonClick();
                }
            });
        } else {
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }


        builder.create();
        builder.show();
    }


}
