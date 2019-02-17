package codesquad.web;

import codesquad.domain.Answer;
import codesquad.dto.AnswerDeleteResponse;
import codesquad.exception.AnswerFailureException;
import codesquad.service.QuestionService;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, @RequestBody Answer answer, HttpSession session) {
        return questionService.addAnswer(SessionUtil.getSessionUser(session), questionId, answer);
    }

    @DeleteMapping("/{answerId}")
    public AnswerDeleteResponse delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        try {
            questionService.deleteAnswer(SessionUtil.getSessionUser(session), questionId, answerId);
            return AnswerDeleteResponse.ok(answerId);
        } catch (AnswerFailureException e) {
            return AnswerDeleteResponse.fail(answerId, "요청 오류");
        }
    }


}
