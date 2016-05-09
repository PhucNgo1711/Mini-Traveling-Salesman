package edu.drexel.cs.ptn32.hw2;

import android.os.AsyncTask;

import com.temboo.Library.Google.DistanceMatrix.DrivingDistanceMatrix;
import com.temboo.Library.Google.DistanceMatrix.DrivingDistanceMatrix.DrivingDistanceMatrixInputSet;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import java.util.ArrayList;

/**
 * Created by PhucNgo on 8/23/15.
 */
public class BackgroundDistanceMultipleInputs extends AsyncTask<ArrayList<ArrayList<String>>, Void, Void> {
    private MainWindowActivity mainWindowActivity;
    private static DrivingDistanceMatrix drivingDistanceMatrixChoreo;
    private static DrivingDistanceMatrix.DrivingDistanceMatrixInputSet drivingDistanceMatrixInputs;

    public BackgroundDistanceMultipleInputs(MainWindowActivity mainWindowActivity) {
        this.mainWindowActivity = mainWindowActivity;
    }

    @Override
    protected Void doInBackground(ArrayList<ArrayList<String>>... params) {
        ArrayList<ArrayList<String>> listLoc = params[0];
        ArrayList<String> listAddr = listLoc.get(0);
        ArrayList<String> listCity = listLoc.get(1);
        ArrayList<String> listState = listLoc.get(2);
        ArrayList<String> listZip = listLoc.get(3);
        ArrayList<String> curLoc = listLoc.get(4);

        // create input strings based on Temboo format Address, City, State, Zip | Address, City, State, Zip | ...
        String destInput = "";
        String origInput = curLoc.get(0) + ", " + curLoc.get(1) + "|"; // lat and long

        // put all locations into one input string separated by |
        for (int i = 0; i < listAddr.size(); i++) {
            destInput += listAddr.get(i) + ", " + listCity.get(i) + ", " + listState.get(i) + ", " + listZip.get(i) + "|";
            origInput += listAddr.get(i) + ", " + listCity.get(i) + ", " + listState.get(i) + ", " + listZip.get(i) + "|";
        }
        destInput += curLoc.get(0) + ", " + curLoc.get(1);

        // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
        TembooSession session = null;
        try {
            session = new TembooSession("ptn32", "Project", "3a50a4154fb844b7afcedc1ce01a48f6");

            drivingDistanceMatrixChoreo = new DrivingDistanceMatrix(session);

            // Get an InputSet object for the choreo
            drivingDistanceMatrixInputs = drivingDistanceMatrixChoreo.newInputSet();

            // Set inputs
            drivingDistanceMatrixInputs.set_Destinations(destInput);
            drivingDistanceMatrixInputs.set_Origins(origInput);

            return null;

        } catch (TembooException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        BackgroundDistanceMultipleOutputs backgroundDistanceMultipleOutputs = new BackgroundDistanceMultipleOutputs(mainWindowActivity);
        backgroundDistanceMultipleOutputs.execute();
    }

    public static DrivingDistanceMatrix getDrivingDistanceMatrixChoreo() {
        return drivingDistanceMatrixChoreo;
    }

    public static DrivingDistanceMatrixInputSet getDrivingDistanceMatrixInputs() {
        return drivingDistanceMatrixInputs;
    }
}
