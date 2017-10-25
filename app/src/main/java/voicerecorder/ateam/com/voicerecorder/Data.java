package voicerecorder.ateam.com.voicerecorder;

/**
 * Created by apple on 24/10/17.
 */

public class Data {

    String name,
    loc,
    date;

    int len;
    public Data(String name, int len, String loc, String date) {
        this.name = name;
        this.len = len;
        this.loc = loc;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getLen() {
        return len;
    }

    public String getLoc() {
        return loc;
    }

    public String getDate() {
        return date;
    }
}
