package codesquad.model.result;

import codesquad.model.answer.Answer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class Result {

    @Column(nullable = false)
    private int status;

    private Answer answer;

    private String error;

    public Result ok(Answer answer){
        status = 200;
        this.answer = answer;
        return this;
    }

    public Result error(String errorMessage){
        status = 500;
        this.error = errorMessage;
        return this;
    }

}
