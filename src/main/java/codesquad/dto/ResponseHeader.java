package codesquad.dto;

public class ResponseHeader {

    public static final boolean RESPONSE_SUCCESS = true;
    public static final boolean RESPONSE_FAIL = true;
    public static final String RESPONSE_SUCCESS_DEFAULT_MESSAGE = "OK";

    private boolean response;
    private String message;

    public ResponseHeader() {

    }

    public ResponseHeader(boolean response, String message) {
        this.response = response;
        this.message = message;
    }


    public boolean isResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }

}
