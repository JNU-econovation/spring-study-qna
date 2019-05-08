package codesquad.controller;

import codesquad.model.answer.Answer;
import codesquad.model.answer.AnswerDto;
import codesquad.model.content.Content;
import codesquad.model.result.Result;
import codesquad.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/question/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("")
    public AnswerDto create(@PathVariable Long questionId, @RequestBody Content content, HttpSession session) {
        return new AnswerDto(questionService.saveAnswer(session, content, questionId));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id, HttpSession session) {
        Result result = new Result();
        try{
            Answer deleteAnswer = questionService.deleteAnswer(id, session);
            return result.ok(deleteAnswer);
        }catch(RuntimeException e){
            return result.error("NullAnswer");
        }
    }

}