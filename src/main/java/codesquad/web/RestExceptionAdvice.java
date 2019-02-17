package codesquad.web;

import codesquad.exception.RedirectException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(RedirectException.class)
    public String handleException(RedirectException exception) {
        return "";
    }
}
