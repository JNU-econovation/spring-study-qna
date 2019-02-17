package codesquad.exception;

public class UserFailureException extends RedirectException {
    public UserFailureException(){
        super("/users/login_fail");
    }
}
