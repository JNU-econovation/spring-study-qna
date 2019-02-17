package codesquad.web;

import codesquad.domain.Question;
import codesquad.service.QuestionService;
import codesquad.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/form")
    public String questionForm(HttpSession session) {
        SessionUtil.getSessionUser(session);
        return "/qna/form";
    }

    @PostMapping("")
    public String create(Question question, HttpSession session) {
        questionService.createQuestion(question, SessionUtil.getSessionUser(session));
        return "redirect:/";
    }

    @GetMapping("")
    public String list() {
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("answers", questionService.findAnswersById(id));
        model.addAttribute("question", questionService.findQuestionById(id));
        return "/qna/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        questionService.deleteQuestionById(id, SessionUtil.getSessionUser(session));
        return "redirect:/";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.findQuestionById(id));
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, Question question, HttpSession session) {
        questionService.updateQuestionById(id, question, SessionUtil.getSessionUser(session));
        return "redirect:/questions/" + id;
    }
}
