package codesquad.model.answer;

import codesquad.model.question.Question;
import codesquad.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    @ManyToOne
    @JsonBackReference
    private Question question;

    @ManyToOne
    private User writer;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Column(nullable = false)
    private boolean deleted = false;

    public Answer() {

    }

    public Answer(User writer, Question question, String content) {
        this.writer = writer;
        this.question = question;
        this.content = content;
    }

    public boolean isWriter(User user) {
        if (writer.getId().equals(user.getId())) {
            return true;
        }
        return false;
    }

}
