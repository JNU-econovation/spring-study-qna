package codesquad.service;

import codesquad.exception.NullQuestionException;
import codesquad.exception.NullUserException;
import codesquad.exception.WrongUserException;
import codesquad.model.answer.Answer;
import codesquad.model.answer.AnswerRepository;
import codesquad.model.content.Content;
import codesquad.model.question.Question;
import codesquad.model.question.QuestionRepository;
import codesquad.model.user.User;
import codesquad.model.user.UserRepository;
import codesquad.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Question findById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new NullQuestionException(id));
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public void update(Long id, Question newQuestion) {
        Question question = findById(id);
        question.update(newQuestion);
        questionRepository.save(question);
    }

    public void delete(Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        isAuthority(id, user);
        questionRepository.deleteById(id);
    }

    public void save(Question question, String userId) {
        User writer = userRepository.findByUserId(userId).orElseThrow(() -> new NullUserException(userId));
        question.setWriter(writer);
        questionRepository.save(question);
    }

    public boolean isAuthority(Long questionId, User writer) {
        Question question = findById(questionId);
        if (!question.isWriter(writer)) {
            throw new WrongUserException(writer.getId());
        }
        return true;
    }

    public Answer saveAnswer(HttpSession session, Content content, Long questionId) {
        User loginUser = SessionUtil.loginUser(session);
        Question question = findById(questionId);
        return answerRepository.save(new Answer(loginUser, question, content.getContent()));
    }

    public Answer deleteAnswer(Long id, HttpSession session) {
        User loginUser = SessionUtil.loginUser(session);
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new RuntimeException());
        if (!answer.isWriter(loginUser)) {
            throw new WrongUserException(loginUser.getId());
        }
        answer.setDeleted(true);
        return answerRepository.save(answer);
    }

}
