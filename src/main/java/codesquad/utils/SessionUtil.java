package codesquad.utils;

import codesquad.exception.NullUserException;
import codesquad.model.user.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    public static boolean isLogin(HttpSession session){
        User user  = (User) session.getAttribute("user");
        if(user == null){
            return false;
        }
        return true;
    }

    public static User loginUser(HttpSession session){
        User user  = (User) session.getAttribute("user");
        if(user == null){
            throw new NullUserException("");
        }
        return user;
    }
}
