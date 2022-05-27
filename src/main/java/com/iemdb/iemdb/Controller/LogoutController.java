package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.*;
import com.iemdb.iemdb.Model.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/logout")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LogoutController {

    @GetMapping("")
    public void logout() throws IOException, MovieNotFound, InterruptedException {
        try {
            IEMDBController.getInstance().setActive_user_null();
        } catch (IOException | MovieNotFound | InterruptedException e) {
            return;
        }
    }
}
