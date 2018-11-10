package omary.dev.utiliter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import omary.dev.utiliter.Interfaces.IUtiliter;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ShowDialog(View view) {
        Utiliter.OpenLoadingDialog(this, "test");
    }

    public void HideDialog(View view) {
        Utiliter.HideLoading();
    }

    public void ShowToast(View view) {
        Utiliter.Toast(this, "Test");
    }

    public void ShowErrorToast(View view) {
        Utiliter.ShowErrorToast(this, "test");
    }

    public void UpdateResource(View view) {
        Utiliter.UpdateLanguageResource(this, "tr");
    }

    public void DoNotify(View view) {
        Utiliter.Notify(this, "Hello", "Test", false);
    }

    public void IsNetWorkAvailable(View view) {
        if (Utiliter.isNetworkAvailable(this))
            Utiliter.Toast(this, "Network is available");
        else
            Utiliter.Toast(this, "Not available");
    }

    public void TimeStamp(View view) {
        Utiliter.Toast(this, Utiliter.getCurrentTimeStamp("MM//dd"));
    }

    public void ShowDialogBox(View view) {

        Utiliter.ShowDialogBox(this, "title", "Message", true);

    }
}
