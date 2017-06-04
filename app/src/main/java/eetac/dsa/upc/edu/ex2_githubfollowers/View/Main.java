package eetac.dsa.upc.edu.ex2_githubfollowers.View;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eetac.dsa.upc.edu.ex2_githubfollowers.Controller.ApiAdapter;
import eetac.dsa.upc.edu.ex2_githubfollowers.Model.Follower;
import eetac.dsa.upc.edu.ex2_githubfollowers.Model.User;
import eetac.dsa.upc.edu.ex2_githubfollowers.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main extends AppCompatActivity implements View.OnClickListener {

    private String username;
    protected static User user;
    protected static ArrayList<Follower> followerList;
    protected static EditText usernameText;
    private Button searchButton;
    protected Intent intent;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        usernameText = (EditText) findViewById(R.id.usernameText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        intent = new Intent(getBaseContext(), UserInfo.class);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchButton:
                if (usernameText.getText().length() != 0) {
                    username = usernameText.getText().toString();
                    getUserInfo(username);
                }
        }
    }
    private void getUserInfo(String username) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(10);
        Call<User> call = ApiAdapter.getApiService("http://api.github.com/").getUserInfoService(username);
        call.enqueue(new GetUserInfoCallback());
    }

    private class GetUserInfoCallback implements Callback<User> {

        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()) {
                progressBar.setProgress(20);
                Toast.makeText(getBaseContext(), "Tenim connexio", Toast.LENGTH_SHORT).show();
                user = response.body();
                progressBar.setProgress(30);
                intent.putExtra("data", user);
                getUserFollowersList(username);
            }
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
        }
    }
    public void getUserFollowersList(String username) {
        progressBar.setProgress(40);
        Call<List<Follower>> call = ApiAdapter.getApiService("http://api.github.com/").followerListService(username);
        call.enqueue(new getUserFollowersList());
    }
    private class getUserFollowersList implements  Callback<List<Follower>> {

        @Override
        public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
            progressBar.setProgress(70);
            followerList = (ArrayList)response.body();
            intent.putParcelableArrayListExtra("data2", (ArrayList) followerList);
            progressBar.setProgress(100);
            progressBar.setVisibility(View.GONE);
            startActivity(intent);
        }

        @Override
        public void onFailure(Call<List<Follower>> call, Throwable t) {
            Toast.makeText(getBaseContext(), "No hi ha connexio amb el servidor", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    }
}
