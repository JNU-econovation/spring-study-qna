package codesquad.exception;

public class QuestionFailureException extends RedirectException {

    public QuestionFailureException() {
        this(null);
    }

    public QuestionFailureException(String message) {
        super("/questions/error", message);
    }
}
