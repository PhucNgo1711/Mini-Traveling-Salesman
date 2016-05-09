package edu.drexel.cs.ptn32.hw2;

import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.temboo.Library.Google.DistanceMatrix.DrivingDistanceMatrix;
import com.temboo.Library.Google.DistanceMatrix.DrivingDistanceMatrix.DrivingDistanceMatrixInputSet;
import com.temboo.Library.Google.DistanceMatrix.DrivingDistanceMatrix.DrivingDistanceMatrixResultSet;
import com.temboo.core.TembooException;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by PhucNgo on 8/23/15.
 */
public class BackgroundDistanceMultipleOutputs extends AsyncTask<Void, Void, ArrayList<ArrayList<String>>> {
    private MainWindowActivity mainWindowActivity;

    public BackgroundDistanceMultipleOutputs(MainWindowActivity mainWindowActivity) {
        this.mainWindowActivity = mainWindowActivity;
    }

    @Override
    protected ArrayList<ArrayList<String>> doInBackground(Void... params) {
        DrivingDistanceMatrix drivingDistanceMatrixChoreo = BackgroundDistanceMultipleInputs.getDrivingDistanceMatrixChoreo();
        DrivingDistanceMatrixInputSet drivingDistanceMatrixInputSet = BackgroundDistanceMultipleInputs.getDrivingDistanceMatrixInputs();

        try {
            // Execute Choreo
            DrivingDistanceMatrixResultSet drivingDistanceMatrixResults = drivingDistanceMatrixChoreo.execute(drivingDistanceMatrixInputSet);

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(drivingDistanceMatrixResults.get_Response());
            JsonObject rootObj = root.getAsJsonObject();

            // get orig and dest addresses
            ArrayList<String> listOrig = new ArrayList<String>();
            ArrayList<String> listDest = new ArrayList<String>();
            JsonArray arrayOrig = rootObj.get("origin_addresses").getAsJsonArray();
            JsonArray arrayDest = rootObj.get("destination_addresses").getAsJsonArray();

            for (int i = 0; i < arrayOrig.size(); i++) {
                String orig = arrayOrig.get(i).getAsString();
                listOrig.add(orig);
            }

            for (int i = 0; i < arrayDest.size(); i++) {
                String dest = arrayDest.get(i).getAsString();
                listDest.add(dest);
            }

            // get duration for addresses
            ArrayList<String> listDur = new ArrayList<String>();
            JsonArray rows = rootObj.get("rows").getAsJsonArray();
            for (int i = 0; i < rows.size(); i++) {
                JsonArray elements = rows.get(i).getAsJsonObject().get("elements").getAsJsonArray();
                for (int j = 0; j < elements.size(); j++) {
                    String dur = elements.get(j).getAsJsonObject().get("duration").getAsJsonObject().get("value").getAsString();

                    listDur.add(dur);
                }
            }

            ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
            list.add(listDur);
            list.add(listOrig);
            list.add(listDest);

            return list;

        } catch (TembooException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<String>> result) {
        ArrayList<ArrayList<String>> list = result;

        ProblemSolver problemSolver = new ProblemSolver(list);
        problemSolver.solve();
    }
}
