package eetac.dsa.upc.edu.ex2_githubfollowers.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import eetac.dsa.upc.edu.ex2_githubfollowers.Model.Follower;
import eetac.dsa.upc.edu.ex2_githubfollowers.Model.User;
import eetac.dsa.upc.edu.ex2_githubfollowers.R;

/**
 * Created by root on 4/06/17.
 */

public class UserInfo extends AppCompatActivity {

    private TextView usernameView;
    private TextView reposView;
    private TextView followersView;
    private TextView followingsView;
    private ListView lv;
    private User user;
    private ArrayList<Follower> followerList;
    private ImageView userAvatarView;
    private ImageView followerAvatarView;
    private TextView followerNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        //activity_this
        usernameView = (TextView) findViewById(R.id.usernameView);
        reposView = (TextView) findViewById(R.id.reposView);
        followersView = (TextView) findViewById(R.id.followersView);
        followingsView = (TextView) findViewById(R.id.followingView);
        userAvatarView = (ImageView) findViewById(R.id.userAvatarView);
        lv = (ListView) findViewById(R.id.followerListView);
        //activity_customlist
        followerAvatarView = (ImageView) findViewById(R.id.followerAvatarView);
        followerNameView = (TextView) findViewById(R.id.followerNameView);

        //Pick up the intent's data and load variables

        Intent intent = getIntent();
        followerList = intent.getParcelableArrayListExtra("data2");
        user = intent.getParcelableExtra("data");
        //Fill data
        usernameView.setText(user.getName());
        reposView.setText(Integer.toString(user.getPublicRepos()));
        followersView.setText(Integer.toString(user.getFollowers()));
        followingsView.setText(Integer.toString(user.getFollowing()));
        //Picasso.with(getBaseContext()).load(user.getAvatarUrl()).into(userAvatarView);
        Picasso.with(getBaseContext()).load(user.getAvatarUrl())
                .into(userAvatarView);
        ListAdapter adapter = new ListAdapter(
                getApplicationContext(), R.layout.activity_userinfo, followerList
        );
        lv.setAdapter(adapter);
    }
}
