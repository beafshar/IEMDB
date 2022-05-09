//package com.iemdb.iemdb.Controller;
//
//import com.iemdb.iemdb.Model.Error.*;
//import com.iemdb.iemdb.Model.*;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/login")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//public class LoginController {
//
//    @PostMapping("")
//    public User login(@RequestBody Map<String, String> data ) throws IOException, MovieNotFound, InterruptedException, UserNotFound {
//        return IEMDBController.getInstance().setActive_user(data.get("username"), data.get("password"));
//    }
//}
