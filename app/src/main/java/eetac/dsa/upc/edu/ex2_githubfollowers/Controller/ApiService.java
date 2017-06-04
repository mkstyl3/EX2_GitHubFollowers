package eetac.dsa.upc.edu.ex2_githubfollowers.Controller;

import java.util.List;

import eetac.dsa.upc.edu.ex2_githubfollowers.Model.Follower;
import eetac.dsa.upc.edu.ex2_githubfollowers.Model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by root on 4/06/17.
 */

public interface ApiService {
    @GET("users/{username}")
    Call<User> getUserInfoService (@Path("username") String username);
    @GET ("users/{username}/followers")
    Call<List<Follower>> followerListService (@Path("username") String username);
}
