package codesquad.exception;

public class AnswerFailureException extends RedirectException {

    public AnswerFailureException(Long questionId) {
        this(questionId, null);
    }

    public AnswerFailureException(Long questionId, String message) {
        super("/questions/" + questionId);
    }
}
