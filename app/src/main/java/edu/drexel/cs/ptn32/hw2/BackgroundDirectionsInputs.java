package edu.drexel.cs.ptn32.hw2;

import android.os.AsyncTask;

import com.temboo.Library.Google.Directions.GetDrivingDirections;
import com.temboo.Library.Google.Directions.GetDrivingDirections.GetDrivingDirectionsInputSet;
import com.temboo.core.TembooException;
import com.temboo.core.TembooSession;

import java.util.ArrayList;

/**
 * Created by PhucNgo on 8/23/15.
 */
public class BackgroundDirectionsInputs extends AsyncTask<DurationBetween, Void, Void> {
    private static GetDrivingDirections getDrivingDirectionsChoreo;
    private static GetDrivingDirectionsInputSet getDrivingDirectionsInputs;

    public BackgroundDirectionsInputs() {
    }

    @Override
    protected Void doInBackground(DurationBetween... params) {
        DurationBetween durationBetween = params[0];
        String orig = durationBetween.getOrig();
        String dest = durationBetween.getDest();

        // Instantiate the Choreo, using a previously instantiated TembooSession object, eg:
        TembooSession session = null;
        try {
            session = new TembooSession("ptn32", "Project", "3a50a4154fb844b7afcedc1ce01a48f6");

            getDrivingDirectionsChoreo = new GetDrivingDirections(session);

            // Get an InputSet object for the choreo
            getDrivingDirectionsInputs = getDrivingDirectionsChoreo.newInputSet();

            // Set inputs
            getDrivingDirectionsInputs.set_Origin(orig);
            getDrivingDirectionsInputs.set_Destination(dest);

            return null;

        } catch (TembooException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        BackgroundDirectionsOutputs backgroundDirectionsOutputs = new BackgroundDirectionsOutputs();
        backgroundDirectionsOutputs.execute();
    }

    public static GetDrivingDirections getGetDrivingDirectionsChoreo() {
        return getDrivingDirectionsChoreo;
    }

    public static GetDrivingDirectionsInputSet getGetDrivingDirectionsInputs() {
        return getDrivingDirectionsInputs;
    }
}
