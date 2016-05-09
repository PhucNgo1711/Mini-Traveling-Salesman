package edu.drexel.cs.ptn32.hw2;

/**
 * Created by PhucNgo on 8/18/15.
 */
public class DurationBetween {
    private int dur;
    private String orig;
    private String dest;

    public DurationBetween(int dur, String orig, String dest) {
        this.dur = dur;
        this.orig = orig;
        this.dest = dest;
    }

    public int getDur() {
        return dur;
    }

    public String getOrig() {
        return orig;
    }

    public String getDest() {
        return dest;
    }
}
