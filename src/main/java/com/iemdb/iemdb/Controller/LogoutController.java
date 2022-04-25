package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.*;
import com.iemdb.iemdb.Model.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/logout")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LogoutController {

    @PostMapping("")
    public User logout() throws IOException, MovieNotFound, InterruptedException, UserNotFound {
        return IEMDBController.getInstance().setActive_user("", "");
    }
}
