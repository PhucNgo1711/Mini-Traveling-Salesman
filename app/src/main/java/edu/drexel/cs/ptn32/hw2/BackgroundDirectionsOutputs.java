package edu.drexel.cs.ptn32.hw2;

import android.content.Intent;
import android.os.AsyncTask;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.temboo.Library.Google.Directions.GetDrivingDirections;
import com.temboo.Library.Google.Directions.GetDrivingDirections.GetDrivingDirectionsInputSet;
import com.temboo.Library.Google.Directions.GetDrivingDirections.GetDrivingDirectionsResultSet;
import com.temboo.core.TembooException;


/**
 * Created by PhucNgo on 8/23/15.
 */
public class BackgroundDirectionsOutputs extends AsyncTask<Void, Void, String[]> {
    public BackgroundDirectionsOutputs() {
    }

    @Override
    protected String[] doInBackground(Void... params) {
        GetDrivingDirections getDrivingDirectionsChoreo = BackgroundDirectionsInputs.getGetDrivingDirectionsChoreo();
        GetDrivingDirectionsInputSet getDrivingDirectionsInputs = BackgroundDirectionsInputs.getGetDrivingDirectionsInputs();

        String[] array = new String[5];
        try {
            // Execute Choreo
            GetDrivingDirectionsResultSet getDrivingDirectionsResults = getDrivingDirectionsChoreo.execute(getDrivingDirectionsInputs);

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(getDrivingDirectionsResults.get_Response());
            JsonObject rootObj = root.getAsJsonObject();
            JsonArray routes = rootObj.get("routes").getAsJsonArray();

            // get first route only which is usually the best choice
            JsonArray legs = routes.get(0).getAsJsonObject().get("legs").getAsJsonArray();

            String orig = legs.get(0).getAsJsonObject().get("start_address").getAsString();
            String origLat = legs.get(0).getAsJsonObject().get("start_location").getAsJsonObject().get("lat").getAsString();
            String origLon = legs.get(0).getAsJsonObject().get("start_location").getAsJsonObject().get("lng").getAsString();

            String dest = legs.get(0).getAsJsonObject().get("end_address").getAsString();
            String destLat = legs.get(0).getAsJsonObject().get("end_location").getAsJsonObject().get("lat").getAsString();
            String destLon = legs.get(0).getAsJsonObject().get("end_location").getAsJsonObject().get("lng").getAsString();

            JsonArray steps = legs.get(0).getAsJsonObject().get("steps").getAsJsonArray();

            String instruction = "";
            for (int i = 0; i < steps.size(); i++) {
                instruction += steps.get(i).getAsJsonObject().get("html_instructions").getAsString() + "\n";
            }

            instruction = instruction.replaceAll("\\<.*?>","");

            array[0] = orig;
            array[1] = dest;
            array[2] = origLat + "," + origLon + "," + orig;
            array[3] = destLat + "," + destLon + "," + dest;
            array[4] = instruction;

            return array;

        } catch (TembooException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (ProblemSolver.getListOptimalPath().size() > 1) { // there are more paths
            ProblemSolver.setListOptimalPath(); // remove first element which has been ran through Google Direction

            ProblemSolver.setInstruction(result); // add to instruction string
            ProblemSolver.setListCoordinates(result); // add to coordinate lists

            BackgroundDirectionsInputs backgroundDirectionsInputs = new BackgroundDirectionsInputs();
            backgroundDirectionsInputs.execute(ProblemSolver.getListOptimalPath().get(0)); // start next location from 0 because previous loc removed
        }
        else {
            ProblemSolver.setInstruction(result); // add to instruction string
            ProblemSolver.setListCoordinates(result); // add to coordinate lists

            // Open new child activity and pass data to child activity
            Intent intent = new Intent(ButtonClick.getMainWindowActivity(), MapsActivity.class);
            intent.putExtra("Instruction", ProblemSolver.getInstruction());
            intent.putExtra("Coordinates", ProblemSolver.getListCoordinates());
            ButtonClick.getMainWindowActivity().startActivity(intent);
        }

    }
}
