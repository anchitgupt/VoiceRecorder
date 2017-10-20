package voicerecorder.ateam.com.voicerecorder.Record;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import voicerecorder.ateam.com.voicerecorder.Notification.NewMessageNotification;
import voicerecorder.ateam.com.voicerecorder.R;
import voicerecorder.ateam.com.voicerecorder.service.MyService;

/**
 * Created by anchit on 2/10/17.
 */

public class RecordVoice extends Fragment {

    private static final String ARG_POSITION = "position";
    boolean b = true;
    int s = 0;

    public RecordVoice() {
    }

    public static RecordVoice newInstance(int position) {
        RecordVoice r = new RecordVoice();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        r.setArguments(b);
        return r;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_record, container, false);
        final TextView textView = (TextView) v.findViewById(R.id.recording_text);
        final Chronometer mChronometer = (Chronometer) v.findViewById(R.id.chronometer);
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if (s == 0) {
                    textView.setText("Recording.");
                    s = 1;
                }
                if (s == 1) {
                    textView.setText("Recording..");
                    s = 2;
                }
                if (s == 2) {
                    textView.setText("Recording...");
                    s = 0;
                }
            }
        });
        FloatingActionButton mRecordButton = (FloatingActionButton) v.findViewById(R.id.floatingActionButton);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (b) {
                    b = false;
                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.start();

                    Intent intent = new Intent(getContext(), MyService.class);
                    intent.putExtra("pos", true);
                    getContext().startService(intent);
                } else {
                    b = true;
                    // mainActivity.setEnabledTabs();
                    mChronometer.setBase(SystemClock.elapsedRealtime());
                    mChronometer.stop();
                    Intent intent = new Intent(getContext(), MyService.class);
                    intent.putExtra("pos", false);
                    getContext().stopService(intent);
                }
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getContext(), "STopped", Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "Saving Recordings", Toast.LENGTH_SHORT).show();
    }


}
