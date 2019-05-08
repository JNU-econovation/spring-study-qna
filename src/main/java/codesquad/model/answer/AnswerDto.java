package codesquad.model.answer;

import codesquad.model.question.Question;
import codesquad.model.user.User;
import lombok.Data;

@Data
public class AnswerDto {

    private Long id;

    private Question question;

    private User writer;

    private String content;

    private String date;

    private boolean deleted = false;

    public AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.question = answer.getQuestion();
        this.writer = answer.getWriter();
        this.content = answer.getContent();
        this.deleted = answer.isDeleted();
        this.date = answer.getDate();
    }

}
