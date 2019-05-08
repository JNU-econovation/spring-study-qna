package codesquad.model.question;

import codesquad.exception.WrongUserException;
import codesquad.model.answer.Answer;
import codesquad.model.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Question {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @OneToMany(mappedBy = "question")
    @JsonManagedReference
    private List<Answer> answers;

    public void update(Question question) {
        this.contents = question.contents;
        this.title = question.title;
    }

    public boolean isWriter(User user) {
        if (this.writer.getId().equals(user.getId())) {
            return true;
        }
        throw new WrongUserException(user.getId());
    }

    public long getAnswerSize() {
        return answers.stream().filter(answer -> (!answer.isDeleted())).count();
    }

    public List<Answer> getTrueAnswers() {
        return answers.stream().filter(answer -> (!answer.isDeleted())).collect(Collectors.toList());
    }

}


