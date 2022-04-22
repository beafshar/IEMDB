package com.iemdb.iemdb.Model;

import com.iemdb.iemdb.Model.Error.UserNotFound;

import java.util.HashMap;
import java.util.Map;

public class UserHandler {

    public static Map<String, User> users = new HashMap<>();

    public void setUsers(User[] users) {
        for (User user : users)
            this.users.put(user.getEmail(), user);
    }

    public User findUser(String email, String password) throws UserNotFound {
        if(email.equals("")) return null;
        else if (users.containsKey(email)) {
            User user = users.get(email);
            if (user.getPassword().equals(password))
                return users.get(email);
        }
        throw new UserNotFound();
    }
}
