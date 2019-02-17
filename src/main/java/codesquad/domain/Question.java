package codesquad.domain;

import codesquad.exception.AnswerFailureException;
import codesquad.exception.QuestionFailureException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"), nullable = false)
    private User writer;

    @Size(min = 1)
    @Column(length = 100, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(length = 16, nullable = false)
    private Date date;

    @Column(nullable = false)
    private boolean deleted = false;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @JsonIgnore
    private List<Answer> answers;

    public Question() {
        date = new Date();
    }

    Question(Long id, User writer, @Size(min = 1) String title, String contents, Date date, boolean deleted) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = contents;
        this.date = date;
        this.deleted = deleted;
        this.answers = answers;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void addAnswer(User user, Answer answer) {
        answer.setQuestion(this);
        answer.setWriter(user);
        answers.add(answer);
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @JsonIgnore
    public Answer getLatestAnswer() {
        if (answers == null || answers.isEmpty())
            throw new QuestionFailureException();
        return answers.get(answers.size() - 1);
    }

    public Answer getAnswer(Long answerId) {
        Optional<Answer> maybeAnswer = answers.stream().filter(answer -> answer.isSameId(answerId)).findFirst();
        if (!maybeAnswer.isPresent())
            throw new AnswerFailureException(id);
        return maybeAnswer.get();
    }

    public void delete(User user) {
        if (!isDeletable(user))
            throw new QuestionFailureException();

        deleted = true;
        for (Answer answer : answers) {
            answer.delete(user);
        }
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Date getDate() {
        return date;
    }

//    public String getFormattedDate() {
//        return DateUtil.getFormattedDate(date);
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void update(Question updateQuestion, User user) throws QuestionFailureException {
        if (!isOwner(user))
            throw new QuestionFailureException("자신의 글만 수정할 수 있습니다.");
        title = updateQuestion.title;
        content = updateQuestion.content;
    }

    boolean isDeletable(User user) {
        if (!isOwner(user))
            return false;
        return answers.stream()
                .noneMatch(answer -> !answer.isDeleted() && !answer.isWriter(user));
    }

    public boolean isTitleEmpty() {
        return title.isEmpty();
    }

    boolean isOwner(User user) {
        return writer.equals(user);
    }
}
