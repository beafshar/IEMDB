package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.Error.UserNotFound;
import com.iemdb.iemdb.Model.IEMDBController;
import com.iemdb.iemdb.Model.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@RestController
@RequestMapping("/callback")
//@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class AuthController {

    public static String CLIENT_ID = "f6eb4e409157aef614cd";
    public static String CLIENT_SECRET = "6d746db62109cad853c5def2beacdd400737216c";
    public String ACCESS_TOKEN = "";

    @GetMapping("/{code}")
    public String getCode(@PathVariable("code") String code) throws IOException, InterruptedException, UserNotFound, MovieNotFound {
        String url = "https://github.com/login/oauth/access_token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + code;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ACCESS_TOKEN = response.body().split("&")[0].split("=")[1];
        User user = getUser();
        addUser(user);
        IEMDBController.getInstance().token = IEMDBController.getInstance().getToken(user);
        return IEMDBController.getInstance().token;
    }

    public User getUser() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("https://api.github.com/user");
        HttpRequest.Builder uri_builder = HttpRequest.newBuilder()
                .uri(uri);
        HttpRequest request2 = uri_builder.GET().header("Authorization", "token " + ACCESS_TOKEN).build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        return createUser(response2.body());
    }

    public User createUser(String body) {
        String nickname = body.split("\"login\":\"")[1].split("\"")[0];
        String email = body.split("\"email\":\"")[1].split("\"")[0];
        String name = body.split("\"name\":\"")[1].split("\"")[0];
        String created_at = body.split("\"created_at\":\"")[1].split("\"")[0].split("T")[0];

        LocalDate created = LocalDate.parse(created_at);
        String birthdate = created.getYear()-18 + "-" + created.getMonthValue() + "-" + created.getDayOfMonth();

        return new User(email, null, nickname, name, birthdate);
    }

    public void addUser(User user) throws UserNotFound, MovieNotFound, IOException, InterruptedException {
        if(IEMDBController.userHandler.isUser(user.getEmail())) {
            IEMDBController.userHandler.findUserFromGit(user.getEmail()).updateUser(user.getEmail(), user.getPassword(), user.getNickname(), user.getName(), user.getBirthdate());
        } else {
            IEMDBController.userHandler.addUser(user);
        }
        IEMDBController.getInstance().setActive_user_from_git(user.getEmail());
    }

}
