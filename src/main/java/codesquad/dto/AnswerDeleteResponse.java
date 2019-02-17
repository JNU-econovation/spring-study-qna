package codesquad.dto;

public class AnswerDeleteResponse {

    ResponseHeader header;
    private Long answerId;


    public AnswerDeleteResponse(Long answerId, boolean response, String message) {
        this.answerId = answerId;
        header = new ResponseHeader(response, message);
    }

    public Long getAnswerId() {
        return answerId;

    }

    public ResponseHeader getHeader() {
        return header;
    }

    public static AnswerDeleteResponse ok(Long answerId) {
        return new AnswerDeleteResponse(answerId, ResponseHeader.RESPONSE_SUCCESS, ResponseHeader.RESPONSE_SUCCESS_DEFAULT_MESSAGE);
    }

    public static AnswerDeleteResponse fail(Long answerId, String message) {
        return new AnswerDeleteResponse(answerId, ResponseHeader.RESPONSE_FAIL, message);
    }
}