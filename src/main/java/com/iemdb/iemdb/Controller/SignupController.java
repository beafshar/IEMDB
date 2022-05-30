package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.MovieNotFound;
import com.iemdb.iemdb.Model.Error.UserNotFound;
import com.iemdb.iemdb.Model.IEMDBController;
import com.iemdb.iemdb.Model.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/signup")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SignupController {
    private IEMDBController iemdbController;
    @PostMapping("")
    public User signup(@RequestBody Map<String, String> data ) throws IOException, MovieNotFound, InterruptedException, UserNotFound {
        return iemdbController.setActive_user(data.get("username"), data.get("password"));
    }
}
