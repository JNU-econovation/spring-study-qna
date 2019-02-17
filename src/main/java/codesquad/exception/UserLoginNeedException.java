package codesquad.exception;

public class UserLoginNeedException extends RedirectException {
    public UserLoginNeedException() {
        super("/users/login");
    }
}
