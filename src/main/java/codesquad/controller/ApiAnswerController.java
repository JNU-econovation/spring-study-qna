package codesquad.controller;

import codesquad.dto.AnswerDTO;
import codesquad.exception.UnAuthorizedException;
import codesquad.model.Answer;
import codesquad.service.AnswerService;
import codesquad.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private static final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerService answerService;

    @PostMapping("")
    public Answer uploadAnswer(@PathVariable Long questionId, @RequestBody AnswerDTO answerDTO, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            throw new UnAuthorizedException();
        }
        return answerService.saveAnswer(session, questionId, answerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Long>> delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isSessionedUser(session)) {
            throw new UnAuthorizedException();
        }

        log.info("id -> ", id);

        return answerService.deleteAnswer(HttpSessionUtils.getSessionedUser(session), id);
    }
}
