package edu.drexel.cs.ptn32.hw2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * Created by PhucNgo on 8/18/15.
 */
public class ProblemSolver {
    private ArrayList<DurationBetween> listDurBtwn;
    private ArrayList<String> listDur;
    private ArrayList<String> listOrig;
    private ArrayList<String> listDest;

    private static ArrayList<DurationBetween> listOptimalPath;
    private static String instruction = "";
    private static ArrayList<String> listCoordinates;

    public ProblemSolver(ArrayList<ArrayList<String>> list) {
        listDurBtwn = new ArrayList<DurationBetween>();
        listOptimalPath = new ArrayList<DurationBetween>();
        listCoordinates = new ArrayList<String>();

        listDur = list.get(0);
        listOrig = list.get(1);
        listDest = list.get(2);
    }

    public ProblemSolver(String[] array) {
        listOptimalPath = new ArrayList<DurationBetween>();
        listCoordinates = new ArrayList<String>();

        int dur = Integer.parseInt(array[0]);
        String orig = array[1];
        String dest = array[2];

        DurationBetween durationBetween = new DurationBetween(dur, orig, dest);
        listOptimalPath.add(durationBetween);
        // way back
        durationBetween = new DurationBetween(dur, dest, orig);
        listOptimalPath.add(durationBetween);

        draw(listOptimalPath);
    }

    public void solve() {
        // create classes of dur, orig, dest
        // one orig will go to multiple dest and have multiple dur
        for (int i = 0; i < listOrig.size(); i++) {
            for (int j = 0; j < listDest.size(); j++) {
                String orig = listOrig.get(i);
                String dest = listDest.get(j);

                int k = (i * listDest.size()) + j;
                int dur = Integer.parseInt(listDur.get(k));

                if (dur != 0) { // skip instances where orig and dest are the same
                    DurationBetween durationBetween = new DurationBetween(dur, orig, dest);
                    listDurBtwn.add(durationBetween);
                }
            }
        }

        ArrayList<String> listOpenTime = Runner.getListOpenTime();
        ArrayList<String> listCloseTime = Runner.getListCloseTime();

        boolean isSolved = false;
        String orig = listOrig.get(0); // start at current location

        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        inputFormat.setTimeZone(TimeZone.getTimeZone("EDT"));

        // get current time
        Date currentTime = new Date();
        String formatCurrentTime = currentTime.toString();
        try {
            currentTime = inputFormat.parse(formatCurrentTime); // get EDT time
        } catch (ParseException e) {
            e.printStackTrace();
        }

        while(!isSolved) {
            Date minTime;
            DurationBetween optimalPath = null;
            long stayTime = 0;
            int pointer = 0; // keep track of the chosen elements in the lists

            try {
                // find open time closest to current time
                minTime = currentTime;
                Calendar cal = Calendar.getInstance();
                cal.setTime(minTime);
                cal.add(Calendar.DATE, 1); // set min to be one day after current time
                minTime = cal.getTime();

                // go through each open and close time to find "optimal" time
                for (int i = 0; i < listOpenTime.size(); i++) {
                    Date openTime = outputFormat.parse(listOpenTime.get(i));
                    Date closeTime = outputFormat.parse(listCloseTime.get(i));

                    // check if close time has already passed current time
                    if (closeTime.before(currentTime)) {
                        // remove unnecessary location from list along with its open and close time
                        Iterator<String> it = listOpenTime.iterator();
                        while (it.hasNext()) {
                            String time = (String)it.next();
                            if (time.equals(listOpenTime.get(i))) {
                                it.remove();
                                break;
                            }
                        }

                        it = listCloseTime.iterator();
                        while (it.hasNext()) {
                            String time = (String)it.next();
                            if (time.equals(listCloseTime.get(i))) {
                                it.remove();
                                break;
                            }
                        }

                        it = listDest.iterator();
                        while (it.hasNext()) {
                            String dest = (String)it.next();
                            if (dest.equals(listDest.get(i))) {
                                it.remove();
                                break;
                            }
                        }

                        continue; // skip to next
                    }

                    // get closest time
                    if (openTime.before(minTime)) {
                        // search for pairs of orig and dest that are the optimal path with closest open time
                        DurationBetween tmpPath = searchPairs(orig, listDest.get(i));
                        int dur = tmpPath.getDur();

                        // with closest open time, it is possible that the user cannot make it from current location
                        // check by adding duration to current time = arrival time
                        Calendar c = Calendar.getInstance();
                        c.setTime(currentTime);
                        c.add(Calendar.SECOND, dur);

                        // the user can make it if arrival time is before close time
                        if (c.getTime().before(closeTime)) {
                            pointer = i;
                            minTime = openTime;
                            optimalPath = tmpPath;
                            stayTime = closeTime.getTime() - openTime.getTime();
                        }
                    }
                }

                if (optimalPath == null) {
                    if (listDest.size() > 1) {
                        continue;
                    }
                    else {
                        break;
                    }
                }

                // remove the the destination of optimal path along with its time and open time
                // the next locations can only compare with the remaining paths
                Iterator<String> it = listOpenTime.iterator();
                while (it.hasNext()) {
                    String time = (String)it.next();
                    if (time.equals(listOpenTime.get(pointer))) {
                        it.remove();
                        break;
                    }
                }

                it = listCloseTime.iterator();
                while (it.hasNext()) {
                    String time = (String)it.next();
                    if (time.equals(listCloseTime.get(pointer))) {
                        it.remove();
                        break;
                    }
                }

                it = listDest.iterator();
                while (it.hasNext()) {
                    String dest = (String)it.next();
                    if (dest.equals(listDest.get(pointer))) {
                        it.remove();
                        break;
                    }
                }

                // add stay time to current time
                // now current time will be the time after the visit
                Calendar c = Calendar.getInstance();
                c.setTime(currentTime);
                c.add(Calendar.MILLISECOND, (int)stayTime);
                currentTime = c.getTime();

                orig = optimalPath.getDest();
                listOptimalPath.add(optimalPath);

                // when destination is current location
                // means it's the final route to go back to starting point
                if (listDest.get(0).equals(listOrig.get(0))) {
                    listOptimalPath.add(searchPairs(orig, listDest.get(0))); // add the last pair
                    isSolved = true;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        draw(listOptimalPath);
    }

    public void draw(ArrayList<DurationBetween> listOptimalPath) {
        BackgroundDirectionsInputs backgroundDirectionsInputs = new BackgroundDirectionsInputs();
        backgroundDirectionsInputs.execute(listOptimalPath.get(0)); // start from the first location
    }


    public DurationBetween searchPairs(String orig, String dest) {
        Iterator it = listDurBtwn.iterator();
        while (it.hasNext()) {
            DurationBetween durationBetween = (DurationBetween)it.next();
            if (durationBetween.getOrig().equals(orig) && durationBetween.getDest().equals(dest)) {
                return durationBetween;
            }
        }

        return null;
    }

    public static ArrayList<DurationBetween> getListOptimalPath() {
        return listOptimalPath;
    }

    public static void setListOptimalPath() {
        Iterator<DurationBetween> it = listOptimalPath.iterator();
        if (it.hasNext()) {
            it.next();
            it.remove(); // remove first element of the list
        }
    }

    public static String getInstruction() {
        return instruction;
    }

    public static void setInstruction(String[] result) {
        ProblemSolver.instruction += "From:  " + result[0] + "\nTo:  " + result[1] + "\n\nNavigation:  " + result[4] + "\n\n\n";

    }

    public static ArrayList<String> getListCoordinates() {
        return listCoordinates;
    }

    public static void setListCoordinates(String[] result) {
        ProblemSolver.listCoordinates.add(result[2]); // origin lat and long
        ProblemSolver.listCoordinates.add(result[3]); // destination lat and long
    }
}
