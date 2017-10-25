package voicerecorder.ateam.com.voicerecorder.Record;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import voicerecorder.ateam.com.voicerecorder.Adapter.MyAdapter;
import voicerecorder.ateam.com.voicerecorder.Database.SqlLite;
import voicerecorder.ateam.com.voicerecorder.Data;
import voicerecorder.ateam.com.voicerecorder.R;

/**
 * Created by anchit on 7/10/17.
 */

public class FileDisplay extends Fragment implements AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String ARG_POSITION = "position";
    final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    List<String> list2;
    List<Data> list3;
    MediaPlayer player;
    AlertDialog.Builder alert;
    ArrayAdapter<String> arrayAdapter;
    SeekBar seekbar;
    SqlLite sqlite;
    RecyclerView recycler;
    SwipeRefreshLayout swipe;
    public RecyclerView.Adapter adapter;
    public MyAdapter myAdapter;
    public boolean hai = false;
    ListView listView;


    public FileDisplay() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adapter.notifyDataSetChanged();
        //getActivity().setTheme(R.style.green);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.android_fragment_filedisplay, container, false);
        /*listView = v.findViewById(R.id.list_item);*/
        sqlite = new SqlLite(getContext());
        recycler = v.findViewById(R.id.recyclerView);
        swipe = v.findViewById(R.id.swipeRefreshLayout);

        File[] list = getFilesFromTheFile();

        list2 = new ArrayList<>();

        list3 = new ArrayList<>();
        Cursor cursor = sqlite.getAllData();
        Data data;
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_SHORT).show();
            Log.e("FileDisplay", "NO RECORD FOUND");
        } else
            while (cursor.moveToNext()) {
                data = new Data(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
                Log.e("Data Added", cursor.getString(1));
            /*list3.add(data);*/
                list2.add(cursor.getString(1));
            }
        cursor.close();

        LinearLayoutManager lllm = new LinearLayoutManager(getContext());
        lllm.setStackFromEnd(true);
        recycler.setLayoutManager(lllm);

        adapter = new MyAdapter(getContext(), list3);

        recycler.setAdapter(adapter);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                    }
                }, 3000);
                list3 = new ArrayList<>();
                Cursor cursor = sqlite.getAllData();
                Data data;
                if (cursor.getCount() == 0) {
                    Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_SHORT).show();
                    Log.e("FileDisplay", "NO RECORD FOUND");
                } else
                    while (cursor.moveToNext()) {
                        data = new Data(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
                        Log.e("Data Added", cursor.getString(1));
                        list3.add(data);
                    }
                cursor.close();
                LinearLayoutManager lllm = new LinearLayoutManager(getContext());
                lllm.setStackFromEnd(true);
                recycler.setLayoutManager(lllm);

                adapter = new MyAdapter(getContext(), list3);

                recycler.setAdapter(adapter);
            }
        });

        /////

        /*if(list != null) {
            for (File f :
                    list) {
                list2.add(f.getName());
            }
        }*/

        /*arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list2);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(this);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ///
                list2 = new ArrayList<String>();
                Cursor cursor= sqlite.getAllData();
                Data data;
                if(cursor.getCount() == 0){
                    Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_SHORT).show();
                    Log.e("FileDisplay","NO RECORD FOUND");
                }
                else
                    while(cursor.moveToNext()){
                        data = new Data(cursor.getString(1),cursor.getInt(2), cursor.getString(3), cursor.getString(4));
                        Log.e("Data Added", cursor.getString(1));
            *//*list3.add(data);*//*
                        list2.add(cursor.getString(1));
                    }
                cursor.close();
                ///
                arrayAdapter.notifyDataSetChanged();
                arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list2);
                listView.setAdapter(arrayAdapter);
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        swipe.setRefreshing(false);
                    }
                }, 2000);

            }
        });*/
        return v;
    }

    public static Fragment newInstance(int position) {
        FileDisplay r = new FileDisplay();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        r.setArguments(b);
        return r;
    }

    //for sqlite


    // for list view
    public File[] getFilesFromTheFile() {
        //String path = Environment.getExternalStorageDirectory().toString() + "/Pictures";
        Log.d("Files", "Path: " + PATH + "/Voice Recordings");
        File directory = new File(PATH + "/Voice Recordings");
        File[] files = new File[10000];
        try {

            files = directory.listFiles();
            if (files == null) {
                return null;
            }
            Log.d("Files", "Size: " + files.length);
            for (File file : files) {
                Log.d("Files", "FileName:" + file.getName());
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
        Button b = v.findViewById(R.id.start);
        Button b1 = v.findViewById(R.id.stop);
        seekbar = v.findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(this);
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    /*public void setDataChanged(String name, int len, String loc, String date) {
        //recycler.invalidate();
        Data data = new Data(name, len, loc, date);
        list3 = new ArrayList<>();
        list3.add(data);
        myAdapter.notifyData(list3);
    }*/
}
