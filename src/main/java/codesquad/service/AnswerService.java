package codesquad.service;

import codesquad.Result;
import codesquad.dto.AnswerDTO;
import codesquad.exception.AnswerNotFoundException;
import codesquad.exception.QuestionNotFoundException;
import codesquad.model.Answer;
import codesquad.model.Question;
import codesquad.model.User;
import codesquad.repository.AnswerRepository;
import codesquad.repository.QuestionRepository;
import codesquad.utils.HttpSessionUtils;
import codesquad.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public Answer saveAnswer(HttpSession session, Long questionId, AnswerDTO answerDTO) {
        Answer answer = new Answer(HttpSessionUtils.getSessionedUser(session), findQuestion(questionId), answerDTO.getContents());
        answer.setTime(TimeUtils.getCurrentTime());

        Question question = findQuestion(questionId);
        question.addAnswer(answer);
        questionRepository.save(question);

        return answerRepository.save(answer);
    }

    public ResponseEntity<Map<String, Long>> deleteAnswer(User sessionedUser, Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(AnswerNotFoundException::new);
        answer.delete(sessionedUser);

        if (answer.isDeleted()) {
            answerRepository.save(answer);
            return Result.ok(id);
        }

        return Result.fail(id);
    }

    private Question findQuestion(Long id) {
        return questionRepository.findById(id).orElseThrow(QuestionNotFoundException::new);
    }
}
