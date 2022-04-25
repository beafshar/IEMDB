package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.*;
import com.iemdb.iemdb.Model.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {

    @PostMapping("")
    public User login(@RequestParam(value = "username", defaultValue = "") String username,
                      @RequestParam(value = "password", defaultValue = "") String password ) throws IOException, MovieNotFound, InterruptedException, UserNotFound {
        return IEMDBController.getInstance().setActive_user(username, password);
    }
}
