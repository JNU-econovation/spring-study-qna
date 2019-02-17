package codesquad.domain;

import codesquad.exception.AnswerFailureException;
import codesquad.util.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"), nullable = false)
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_question"), nullable = false)
    private Question question;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private boolean deleted = false;

    Answer(Long id, User writer, Question question, String content, Date date, boolean deleted) {
        this.id = id;
        this.writer = writer;
        this.question = question;
        this.content = content;
        this.date = date;
        this.deleted = deleted;
    }

    public Answer() {
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isSameId(Long id) {
        return this.id.equals(id);
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public boolean isWriter(User writer) {
        return this.writer.equals(writer);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getFormattedDate() {
        return DateUtil.getFormattedDate(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete(User user) {
        if (!isWriter(user))
            throw new AnswerFailureException(question.getId());
        deleted = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return Objects.equals(getId(), answer.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
