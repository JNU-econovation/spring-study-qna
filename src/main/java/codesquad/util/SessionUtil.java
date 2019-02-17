package codesquad.util;

import codesquad.domain.User;
import codesquad.exception.UserLoginNeedException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionUtil {
    private static final String USER_SESSION = "sessionedUser";

    public static void setSessionUser(HttpSession session, User user) {
        session.setAttribute(USER_SESSION, user);
    }

    public static void removeSessionUser(HttpSession session) {
        session.removeAttribute(USER_SESSION);
    }

    public static User getSessionUser(HttpSession session) {
        Optional<User> maybeUser = Optional.ofNullable((User) session.getAttribute(USER_SESSION));
        if (!maybeUser.isPresent())
            throw new UserLoginNeedException();
        return maybeUser.get();
    }
}
