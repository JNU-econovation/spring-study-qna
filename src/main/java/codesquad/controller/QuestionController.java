package codesquad.controller;

import codesquad.model.question.Question;
import codesquad.model.user.User;
import codesquad.service.QuestionService;
import codesquad.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/form")
    public String goToQnaForm(HttpSession session) {
        if (!SessionUtil.isLogin(session)) {
            return "redirect:/users/loginForm";
        }
        return "/qna/form";
    }

    @GetMapping("/{id}")
    public String goToQnaShow(Model model, @PathVariable Long id) {
        model.addAttribute("question", questionService.findById(id));
        return "/qna/show";
    }

    @PostMapping("/{userId}")
    public String createQuestion(Question question, @PathVariable String userId) {
        questionService.save(question, userId);
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String gotoUpdateForm(Model model, @PathVariable Long id, HttpSession session) {
        if (!SessionUtil.isLogin(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionService.findById(id);
        User writer = (User) session.getAttribute("user");
        question.isWriter(writer);
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @DeleteMapping("/{id}")
    public String deleteQuestion(@PathVariable Long id, HttpSession session) {
        if (!SessionUtil.isLogin(session)) {
            return "redirect:/users/loginForm";
        }
        questionService.delete(id, session);
        return "redirect:/";
    }

}