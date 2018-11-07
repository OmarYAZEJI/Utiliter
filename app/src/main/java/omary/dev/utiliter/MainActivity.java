package omary.dev.utiliter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void ShowDialog(View view) {
        Utiliter.ShowLoading(this, "test");
    }

    public void HideDialog(View view) {
        Utiliter.HideLoading();
    }

    public void ShowToast(View view) {
        Utiliter.ShowToast(this, "Test");
    }

    public void ShowErrorToast(View view) {
        Utiliter.ShowErrorToast(this, "test");
    }

    public void UpdateResource(View view) {
        Utiliter.updateResources(this, "tr");
    }

    public void DoNotify(View view) {
        Utiliter.Notify(this, "Hello", "Test", false);
    }

    public void IsNetWorkAvailable(View view) {
        if (Utiliter.isNetworkAvailable(this))
            Utiliter.ShowToast(this, "Network is available");
        else
            Utiliter.ShowToast(this, "Not available");
    }

    public void TimeStamp(View view) {
        Utiliter.ShowToast(this, Utiliter.getCurrentTimeStamp("MM//dd"));
    }
}
