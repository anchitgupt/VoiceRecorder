package voicerecorder.ateam.com.voicerecorder.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import voicerecorder.ateam.com.voicerecorder.Data;
import voicerecorder.ateam.com.voicerecorder.R;

/**
 * Created by apple on 24/10/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    List<Data> list;
    AlertDialog.Builder alert;
    SeekBar seekbar;
    MediaPlayer player;
    private final static int FADE_DURATION = 1000; // in milliseconds


    public MyAdapter(Context context, List<Data> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        final Data data = list.get(position);
        holder.title.setText(data.getName());
        int i = data.getLen();
        String s = String.valueOf(i);
        holder.length.setText(s);
        holder.loc.setText(data.getLoc());
        holder.date.setText(data.getDate());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = View.inflate(context, R.layout.layout_player, null);
                alert = new AlertDialog.Builder(context);
                alert.setCustomTitle(v);
                Button b = v.findViewById(R.id.start);
                Button b1 = v.findViewById(R.id.stop);
                seekbar = v.findViewById(R.id.seekbar);
                player = new MediaPlayer();
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s = data.getLoc()+"/"+data.getName();
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
        });

        setFadeAnimation(holder.itemView);
    }

    private void setFadeAnimation(View itemView) {
        /*AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        itemView.startAnimation(anim);
*/
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    anim.setDuration(FADE_DURATION);
    itemView.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*public void notifyData(List<Data> myList) {
        Log.d("notifyData ", myList.size() + "");
        this.list = myList;
        notifyDataSetChanged();
    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,
                        length,
                        loc,
                        date;
        public LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_title);
            length = itemView.findViewById(R.id.list_dur);
            loc = itemView.findViewById(R.id.list_dur);
            date = itemView.findViewById(R.id.list_date);
            linearLayout = itemView.findViewById(R.id.list);
        }
    }
}
