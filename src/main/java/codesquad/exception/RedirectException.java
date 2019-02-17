package codesquad.exception;

public class RedirectException extends RuntimeException {

    private static final String BASE_REDIRECT = "redirect:";
    private String redirectUrl = "";

    public RedirectException(String url) {
        this(url, null);
    }

    public RedirectException(String url, String message) {
        super(message);
        redirectUrl = BASE_REDIRECT + url;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
