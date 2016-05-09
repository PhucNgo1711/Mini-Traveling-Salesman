package edu.drexel.cs.ptn32.hw2;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by PhucNgo on 8/15/15.
 */
public class Runner {
    private MainWindowActivity mainWindowActivity;
    private EditText address, city, state, zip, openTime, closeTime;
    private Button doneBtn, addBtn;

    private static String addressStr = "";
    private static String cityStr = "";
    private static String stateStr = "";
    private static String zipStr = "";
    private static String openTimeStr = "";
    private static String closeTimeStr = "";

    private static ArrayList<String> listAddr;
    private static ArrayList<String> listCity;
    private static ArrayList<String> listState;
    private static ArrayList<String> listZip;
    private static ArrayList<String> listOpenTime;
    private static ArrayList<String> listCloseTime;

    public Runner(MainWindowActivity mainWindowActivity, Button doneBtn, Button addBtn) {
        this.mainWindowActivity = mainWindowActivity;
        this.doneBtn = doneBtn;
        this.addBtn = addBtn;

        address = (EditText)this.mainWindowActivity.findViewById(R.id.address);
        city = (EditText)this.mainWindowActivity.findViewById(R.id.city);
        state = (EditText)this.mainWindowActivity.findViewById(R.id.state);
        zip = (EditText)this.mainWindowActivity.findViewById(R.id.zip);
        openTime = (EditText)this.mainWindowActivity.findViewById(R.id.openTime);
        closeTime = (EditText)this.mainWindowActivity.findViewById(R.id.closeTime);

        this.listAddr = new ArrayList<String>();
        this.listCity = new ArrayList<String>();
        this.listState = new ArrayList<String>();
        this.listZip = new ArrayList<String>();
        this.listOpenTime = new ArrayList<String>();
        this.listCloseTime = new ArrayList<String>();
    }

    public void Run() {
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                addressStr = s.toString();

                // keep button disabled until all fields have been filled
                if (!(addressStr.isEmpty()) && !(cityStr.isEmpty())  && !(stateStr.isEmpty())  && !(zipStr.isEmpty()) && !(openTimeStr.isEmpty()) && !(closeTimeStr.isEmpty())) {
                    ButtonClick buttonClick = new ButtonClick(mainWindowActivity, doneBtn, addBtn);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                cityStr = s.toString();

                // keep button disabled until all fields have been filled
                if (!(addressStr.isEmpty()) && !(cityStr.isEmpty())  && !(stateStr.isEmpty())  && !(zipStr.isEmpty()) && !(openTimeStr.isEmpty()) && !(closeTimeStr.isEmpty())) {
                    ButtonClick buttonClick = new ButtonClick(mainWindowActivity, doneBtn, addBtn);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        state.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                stateStr = s.toString();

                // keep button disabled until all fields have been filled
                if (!(addressStr.isEmpty()) && !(cityStr.isEmpty())  && !(stateStr.isEmpty())  && !(zipStr.isEmpty()) && !(openTimeStr.isEmpty()) && !(closeTimeStr.isEmpty())) {
                    ButtonClick buttonClick = new ButtonClick(mainWindowActivity, doneBtn, addBtn);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        zip.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                zipStr = s.toString();

                // keep button disabled until all fields have been filled
                if (!(addressStr.isEmpty()) && !(cityStr.isEmpty())  && !(stateStr.isEmpty())  && !(zipStr.isEmpty()) && !(openTimeStr.isEmpty()) && !(closeTimeStr.isEmpty())) {
                    ButtonClick buttonClick = new ButtonClick(mainWindowActivity, doneBtn, addBtn);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        openTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                openTimeStr = s.toString();

                // keep button disabled until all fields have been filled
                if (!(addressStr.isEmpty()) && !(cityStr.isEmpty())  && !(stateStr.isEmpty())  && !(zipStr.isEmpty()) && !(openTimeStr.isEmpty()) && !(closeTimeStr.isEmpty())) {
                    ButtonClick buttonClick = new ButtonClick(mainWindowActivity, doneBtn, addBtn);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

        closeTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                closeTimeStr = s.toString();

                // keep button disabled until all fields have been filled
                if (!(addressStr.isEmpty()) && !(cityStr.isEmpty())  && !(stateStr.isEmpty())  && !(zipStr.isEmpty()) && !(openTimeStr.isEmpty()) && !(closeTimeStr.isEmpty())) {
                    ButtonClick buttonClick = new ButtonClick(mainWindowActivity, doneBtn, addBtn);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });
    }


    public static String getZipStr() {
        return zipStr;
    }

    public static String getStateStr() {
        return stateStr;
    }

    public static String getCityStr() {
        return cityStr;
    }

    public static String getAddressStr() {
        return addressStr;
    }

    public static String getOpenTimeStr() {
        return openTimeStr;
    }

    public static String getCloseTimeStr() {
        return closeTimeStr;
    }

    public static ArrayList<String> getListState() {
        return listState;
    }

    public static ArrayList<String> getListAddr() {
        return listAddr;
    }

    public static ArrayList<String> getListCity() {
        return listCity;
    }

    public static ArrayList<String> getListZip() {
        return listZip;
    }

    public static ArrayList<String> getListOpenTime() {
        return listOpenTime;
    }

    public static ArrayList<String> getListCloseTime() {
        return listCloseTime;
    }

    public static void setListAddr(String aS) {
        Runner.listAddr.add(aS);
    }

    public static void setListCity(String cS) {
        Runner.listCity.add(cS);
    }

    public static void setListState(String sS) {
        Runner.listState.add(sS);
    }

    public static void setListZip(String zS) {
        Runner.listZip.add(zS);
    }

    public static void setListOpenTime(String otS) {
        Runner.listOpenTime.add(otS);
    }

    public static void setListCloseTime(String ctS) {
        Runner.listCloseTime.add(ctS);
    }
}
