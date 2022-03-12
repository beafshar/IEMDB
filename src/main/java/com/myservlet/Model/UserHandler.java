package com.myservlet.Model;

import Model.Error.UserNotFound;
import java.util.HashMap;
import java.util.Map;

public class UserHandler {

    public static Map<String, User> users = new HashMap<>();

    public void setUsers(User[] users) {
        for (User user : users)
            this.users.put(user.getEmail(), user);
    }

    public static User findUser(String email) throws UserNotFound {
        if (users.containsKey(email))
            return users.get(email);
        throw new UserNotFound();
    }

}
