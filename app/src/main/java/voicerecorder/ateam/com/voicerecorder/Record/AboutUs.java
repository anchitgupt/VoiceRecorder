package voicerecorder.ateam.com.voicerecorder.Record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import voicerecorder.ateam.com.voicerecorder.R;

/**
 * Created by anchit on 2/10/17.
 */

public class AboutUs extends Fragment {
    private static final String ARG_POSITION = "position";

    public AboutUs() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_aboutus, container, false);
        TextView head =(TextView)v.findViewById(R.id.text_about_us_head);
        TextView body =(TextView)v.findViewById(R.id.text_about_us_body);
        return v;
    }

    public static Fragment newInstance(int position) {
        AboutUs r = new AboutUs();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        r.setArguments(b);
        return r;
    }
}
