package edu.drexel.cs.ptn32.hw2;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainWindowActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        Button doneBtn = (Button)findViewById(R.id.done);
        doneBtn.setEnabled(false);

        Button addBtn = (Button)findViewById(R.id.add);
        addBtn.setEnabled(false);



        Runner runner = new Runner(this, doneBtn, addBtn);
        runner.Run();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_window, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

/*---------- Listener class to get coordinates ------------- */
class MyLocationListener implements LocationListener {
    private MainWindowActivity mainWindowActivity;
    private static String lon;
    private static String lat;

    public MyLocationListener (MainWindowActivity mainWindowActivity) {
        this.mainWindowActivity = mainWindowActivity;
    }

    @Override
    public void onLocationChanged(Location loc) {
        lon = Double.toString(loc.getLongitude());
        lat = Double.toString(loc.getLatitude());
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    public static String getLon() {
        return lon;
    }

    public static String getLat() {
        return lat;
    }
}