package codesquad.service;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.exception.QuestionFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Iterable<Question> findQuestions() {
        return questionRepository.findByDeletedFalse();
    }

    public void createQuestion(Question question, User user) {
        question.setWriter(user);
        if (question.isTitleEmpty())
            throw new QuestionFailureException();
        questionRepository.save(question);
    }

    public Question findQuestionById(Long id) {
        Optional<Question> maybeQuestion = questionRepository.findById(id);
        if (!maybeQuestion.isPresent())
            throw new QuestionFailureException();

        return maybeQuestion.get();
    }

    public void deleteQuestionById(Long id, User user) {
        Question question = findQuestionById(id);
        question.delete(user);
        questionRepository.save(question);
    }

    public void updateQuestionById(Long id, Question updateQuestion, User user) {
        Question question = findQuestionById(id);
        question.update(updateQuestion, user);

        questionRepository.save(question);
    }

    public Answer addAnswer(User user, Long questionId, Answer answer) {
        Question question = findQuestionById(questionId);
        question.addAnswer(user, answer);
        return questionRepository.save(question).getLatestAnswer();
    }


    public List<Answer> findAnswersById(Long id) {
        return findQuestionById(id).getAnswers();
    }

    public void deleteAnswer(User user, long questionId, long answerId) {
        Question question = findQuestionById(questionId);

        Answer answer = question.getAnswer(answerId);
        answer.delete(user);
        questionRepository.save(question);
    }


}
