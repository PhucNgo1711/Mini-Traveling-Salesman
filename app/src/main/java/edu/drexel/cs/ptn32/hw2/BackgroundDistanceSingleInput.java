package edu.drexel.cs.ptn32.hw2;

import android.os.AsyncTask;

import com.temboo.Library.Google.DistanceMatrix.DrivingDistanceMatrix;
import com.temboo.Library.Google.DistanceMatrix.DrivingDistanceMatrix.DrivingDistanceMatrixInputSet;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;


/**
 * Created by PhucNgo on 8/15/15.
 */
public class BackgroundDistanceSingleInput extends AsyncTask<String[], Void, Void> {
    private MainWindowActivity mainWindowActivity;
    private static DrivingDistanceMatrix drivingDistanceMatrixChoreo;
    private static DrivingDistanceMatrixInputSet drivingDistanceMatrixInputs;

    public BackgroundDistanceSingleInput(MainWindowActivity mainWindowActivity) {
        this.mainWindowActivity = mainWindowActivity;
    }

    @Override
    protected Void doInBackground(String[]... params) {
        String[] array = params[0];

        // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
        TembooSession session = null;
        try {
            session = new TembooSession("ptn32", "Project", "3a50a4154fb844b7afcedc1ce01a48f6");

            drivingDistanceMatrixChoreo = new DrivingDistanceMatrix(session);

            // Get an InputSet object for the choreo
            drivingDistanceMatrixInputs = drivingDistanceMatrixChoreo.newInputSet();

            // Set inputs
            drivingDistanceMatrixInputs.set_Destinations(array[0] + ", " + array[1] + ", " + array[2] + " " + array[3]); // address, city, state zip
            drivingDistanceMatrixInputs.set_Origins(array[4] + ", " + array[5]); // current location in lat long

            return null;

        } catch (TembooException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        BackgroundDistanceSingleOutput backgroundDistanceSingleOutput = new BackgroundDistanceSingleOutput(mainWindowActivity);
        backgroundDistanceSingleOutput.execute();
    }

    public static DrivingDistanceMatrix getDrivingDistanceMatrixChoreo() {
        return drivingDistanceMatrixChoreo;
    }

    public static DrivingDistanceMatrixInputSet getDrivingDistanceMatrixInputs() {
        return drivingDistanceMatrixInputs;
    }
}