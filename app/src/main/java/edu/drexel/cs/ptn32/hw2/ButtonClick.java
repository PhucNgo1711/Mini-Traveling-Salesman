package edu.drexel.cs.ptn32.hw2;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by PhucNgo on 8/15/15.
 */
public class ButtonClick implements View.OnClickListener {
    private static MainWindowActivity mainWindowActivity;

    public ButtonClick(MainWindowActivity mainWindowActivity, Button doneBtn, Button addBtn) {
        ButtonClick.mainWindowActivity = mainWindowActivity;

        doneBtn.setEnabled(true);
        doneBtn.setOnClickListener(this);

        addBtn.setEnabled(true);
        addBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.done:
                String curLat = MyLocationListener.getLat();
                String curLong = MyLocationListener.getLon();

                if (Runner.getListAddr().isEmpty()) { // the list is empty means there are no multiple addresses
                    // get location from edit text fields
                    String[] array = new String[6];
                    array[0] = Runner.getAddressStr();
                    array[1] = Runner.getCityStr();
                    array[2] = Runner.getStateStr();
                    array[3] = Runner.getZipStr();
                    array[4] = curLat;
                    array[5] = curLong;

                    BackgroundDistanceSingleInput backgroundDistanceSingleInput = new BackgroundDistanceSingleInput(mainWindowActivity);
                    backgroundDistanceSingleInput.execute(array);
                }
                else { // multiple addresses
                    ArrayList<String> curLoc = new ArrayList<String>();
                    curLoc.add(curLat);
                    curLoc.add(curLong);

                    ArrayList<ArrayList<String>> listLoc = new ArrayList<ArrayList<String>>();
                    listLoc.add(Runner.getListAddr());
                    listLoc.add(Runner.getListCity());
                    listLoc.add(Runner.getListState());
                    listLoc.add(Runner.getListZip());
                    listLoc.add(curLoc);

                    BackgroundDistanceMultipleInputs backgroundDistanceMultipleInputs = new BackgroundDistanceMultipleInputs(mainWindowActivity);
                    backgroundDistanceMultipleInputs.execute(listLoc);
                }

                break;

            case R.id.add:
                // save entered address in lists and clear text for new inputs
                EditText address = (EditText) mainWindowActivity.findViewById(R.id.address);
                Runner.setListAddr(address.getText().toString());
                //address.setText("");
                address.requestFocus();

                EditText city = (EditText) mainWindowActivity.findViewById(R.id.city);
                Runner.setListCity(city.getText().toString());
                //city.setText("");

                EditText state = (EditText) mainWindowActivity.findViewById(R.id.state);
                Runner.setListState(state.getText().toString());
                //state.setText("");

                EditText zip = (EditText) mainWindowActivity.findViewById(R.id.zip);
                Runner.setListZip(zip.getText().toString());
                //zip.setText("");

                EditText openTime = (EditText) mainWindowActivity.findViewById(R.id.openTime);
                Runner.setListOpenTime(openTime.getText().toString());
                //openTime.setText("");

                EditText closeTime = (EditText) mainWindowActivity.findViewById(R.id.closeTime);
                Runner.setListCloseTime(closeTime.getText().toString());
                //closeTime.setText("");

                Toast.makeText(mainWindowActivity.getApplicationContext(), "Location saved", Toast.LENGTH_SHORT).show();

                break;
        }

    }

    public static MainWindowActivity getMainWindowActivity() {
        return mainWindowActivity;
    }
}
