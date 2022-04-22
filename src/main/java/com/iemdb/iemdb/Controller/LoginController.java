package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.Error.UserNotFound;
import com.iemdb.iemdb.Model.IEMDBController;
import com.iemdb.iemdb.Model.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("")
    public User login(@RequestParam(value = "username", defaultValue = "") String username,
                      @RequestParam(value = "password", defaultValue = "") String password ) throws IOException, MovieNotFound, InterruptedException, UserNotFound {
        return IEMDBController.getInstance().setActive_user(username, password);
    }
}