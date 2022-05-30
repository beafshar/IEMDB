package com.iemdb.iemdb.Controller;

import com.iemdb.iemdb.Model.Error.*;
import com.iemdb.iemdb.Model.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/logout")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@AllArgsConstructor
public class LogoutController {
    private IEMDBController iemdbController;
    @PostMapping("")
    public User logout() throws IOException, MovieNotFound, InterruptedException, UserNotFound {
        return iemdbController.setActive_user("", "");
    }
}
