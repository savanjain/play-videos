package com.example.user.playvideos;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

public class PlayVideosActivity extends AppCompatActivity {

    VideoView playvideo;
    Uri uri;
    ListVideoAdapter listVideoAdapter;
    LinearLayoutManager llm;
    RecyclerView rv;
    TextView title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_videos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playvideo=findViewById(R.id.videoplay);
        title=findViewById(R.id.ptitle);
        description=findViewById(R.id.pdescription);
        rv=findViewById(R.id.rv_listvideo);
        listVideoAdapter=new ListVideoAdapter();
        llm=new LinearLayoutManager(getApplicationContext());
        rv.setAdapter(listVideoAdapter);
        rv.setLayoutManager(llm);


        MediaController mc = new MediaController(PlayVideosActivity.this);
        mc.setAnchorView(playvideo);
        mc.setMediaPlayer(playvideo);
        uri=Uri.parse(Global.userData.getUrl());
        playvideo.setMediaController(mc);
        playvideo.setVideoURI(uri);
        playvideo.start();
       /* playvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/
        title.setText(Global.userData.getTitle());
        description.setText(Global.userData.getDescription());

    }

    private class ListVideoAdapter extends RecyclerView.Adapter<ListVideoAdapter.MemberViewHolder>{

        public class MemberViewHolder extends RecyclerView.ViewHolder
        {

            TextView description, id, url, title;
            ImageView thumb;
            CardView cv;
            LinearLayout l1;
            public MemberViewHolder(@NonNull View itemView) {

                super(itemView);

                title=itemView.findViewById(R.id.ltitle);
                description=itemView.findViewById(R.id.ldesc);
                thumb=itemView.findViewById(R.id.limage);
                l1=itemView.findViewById(R.id.l1);
            }
        }
        @NonNull
        @Override
        public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View v=getLayoutInflater().inflate(R.layout.listrow,null);
            MemberViewHolder mvh=new MemberViewHolder(v);
            return mvh;
        }

        @Override
        public void onBindViewHolder(@NonNull MemberViewHolder memberViewHolder, int i)
        {
            final UserData userData=Global.firebaseDataArrayList.get(i);
            memberViewHolder.title.setText(userData.getTitle());
            memberViewHolder.description.setText(userData.getDescription());
            uri=Uri.parse(userData.getThumb());
            // context=memberViewHolder.thumb.getContext();
            Picasso.get().load(uri).into(memberViewHolder.thumb);
            memberViewHolder.l1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MediaController mc = new MediaController(PlayVideosActivity.this);
                    mc.setAnchorView(playvideo);
                    mc.setMediaPlayer(playvideo);
                    uri=Uri.parse(userData.getUrl());
                    playvideo.setMediaController(mc);
                    playvideo.setVideoURI(uri);
                    playvideo.start();
                    title.setText(userData.getTitle());
                    description.setText(userData.getDescription());
                }
            });

        }

        @Override
        public int getItemCount() {
            return Global.firebaseDataArrayList.size();
        }


    }
    @Override
    public boolean onSupportNavigateUp()
    {
        Intent in=new Intent(getApplicationContext(),MainPageActivity.class);
        startActivity(in);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(getApplicationContext(),MainPageActivity.class);
        startActivity(in);
        finish();
    }
}
