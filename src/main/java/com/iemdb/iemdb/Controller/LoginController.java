package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.*;
import com.iemdb.iemdb.Model.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    @PostMapping("")
    public User login(@RequestBody Map<String, String> data ) throws IOException, MovieNotFound, InterruptedException, UserNotFound {
        try {
            return IEMDBController.getInstance().setActive_user(data.get("username"), data.get("password"));
        } catch (IOException | MovieNotFound | InterruptedException | UserNotFound e) {
            return null;
        }
    }

    @GetMapping("/check")
    public Boolean check_login() throws IOException, MovieNotFound, InterruptedException {
        try {
            if (IEMDBController.getInstance().getActive_user() == null)
                return false;
            return true;
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return false;
        }
    }
}
