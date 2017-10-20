package voicerecorder.ateam.com.voicerecorder.Record;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import voicerecorder.ateam.com.voicerecorder.R;

/**
 * Created by anchit on 7/10/17.
 */

public class FileDisplay extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_POSITION = "position";
    final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    List<String> list2;
    MediaPlayer player;
    AlertDialog.Builder alert;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setTheme(R.style.green);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.android_fragment_filedisplay, container, false);
        ListView listView = v.findViewById(R.id.list_item);
        File[] list = getFilesFromTheFile();

        list2 = new ArrayList<>();
        if(list != null) {
            for (File f :
                    list) {
                list2.add(f.getName());
            }
        }
        listView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list2));
        listView.setOnItemClickListener(this);

        return v;
    }

    public static Fragment newInstance(int position) {
        FileDisplay r = new FileDisplay();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        r.setArguments(b);
        return r;
    }

    public File[] getFilesFromTheFile() {
        //String path = Environment.getExternalStorageDirectory().toString() + "/Pictures";
        Log.d("Files", "Path: " + PATH + "/Voice Recordings");
        File directory = new File(PATH + "/Voice Recordings");
        File[] files = new File[10000];
        try {

            files = directory.listFiles();
            if(files == null){
                return null;
            }
            Log.d("Files", "Size: " + files.length);
            for (int i = 0;
                 i < files.length; i++)

            {
                Log.d("Files", "FileName:" + files[i].getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return files;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, final long l) {
        View v = View.inflate(getContext(), R.layout.layout_player, null);
        alert = new AlertDialog.Builder(getContext());
        alert.setCustomTitle(v);
        Button b = (Button) v.findViewById(R.id.start);
        Button b1 = (Button) v.findViewById(R.id.stop);
        player = new MediaPlayer();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Start" + list2.get(i), Toast.LENGTH_SHORT).show();
                String s = PATH + "/Voice Recordings/" + list2.get(i);
                if (player == null) {
                    player = new MediaPlayer();
                }
                try {
                    player.setDataSource(s);
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player != null) {
                    player.stop();
                    player = null;
                }
            }
        });

        alert.setCancelable(false);
        alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (player != null) {
                    player.stop();
                    player = null;
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (player != null) {
                    player.stop();
                    player = null;
                }
                dialogInterface.dismiss();
            }
        });
        alert.show();

    }
}
