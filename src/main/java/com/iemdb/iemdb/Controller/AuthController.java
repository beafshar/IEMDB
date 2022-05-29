package com.iemdb.iemdb.Controller;

import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/callback")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    public static String CLIENT_ID = "f6eb4e409157aef614cd";
    public static String CLIENT_SECRET = "6b61bf341da6c84a4d718b6aece3a915db660d31";
    public String ACCESS_TOKEN = "";

    @GetMapping("/{code}")
    public void getCode(@PathVariable("code") String code) throws IOException, InterruptedException {
        String url = "https://github.com/login/oauth/access_token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + code;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
        ACCESS_TOKEN = response.body().split("&")[0].split("=")[1];
        System.out.println(ACCESS_TOKEN);

        URI uri = URI.create("https://api.github.com/user");
        HttpRequest.Builder uri_builder = HttpRequest.newBuilder()
                .uri(uri);
        HttpRequest request2 = uri_builder.GET().header("Authorization", "token " + ACCESS_TOKEN).build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        System.out.println(response2.body());
    }
}
