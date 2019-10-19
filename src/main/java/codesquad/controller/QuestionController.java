package codesquad.controller;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String question(Question question, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            Optional<User> loginUser = (Optional<User>) value;
            question.setCurrentTime();
            question.setUserInfo(loginUser.get());
            questionRepository.save(question);
            System.out.println("add 후 question : " + question);
            return "redirect:/";
        } else {
            return "redirect:/users/login";
        }
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String questionsShow(@PathVariable("id") Long id, Model model) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        model.addAttribute("question", question);
        return "qna/show";
    }

    @GetMapping("/questions/{id}/form")
    public String updateQuestionForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Object value = session.getAttribute("sessionedUser");
        Optional<User> user = (Optional<User>) value;
        if (value != null && question.getWriter().isSameUserId(user.get())) {
            model.addAttribute("question", question);
            return "qna/updateForm";
        } else {
            model.addAttribute("userMissMatchQuestion", question);
            return "qna/show";
        }
    }

    @PutMapping("/questions/{id}")
    public String editQuestion(@PathVariable("id") Long id, Question newQuestion, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Object value = session.getAttribute("sessionedUser");
        Optional<User> user = (Optional<User>) value;
        if (value != null && question.getWriter().isSameUserId(user.get())) {
            question.changeInfo(newQuestion);
            questionRepository.save(question);
            return "redirect:/";
        } else {
            model.addAttribute("userMissMatchQuestion", question);
            return "qna/show";
        }

    }

    @DeleteMapping("/questions/{id}")
    public String deleteQuestion(@PathVariable("id") Long id, HttpSession session) {
        Question question = questionRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Object value = session.getAttribute("sessionedUser");
        Optional<User> user = (Optional<User>) value;
        if (value != null && question.getWriter().isSameUserId(user.get())) {
            questionRepository.delete(question);
            return "redirect:/";
        } else {
            return "user/login_failed";
        }
    }

    @GetMapping("/questions/form")
    public String doQuestion(HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value != null) {
            return "qna/form";
        } else {
            return "/users/login";
        }
    }
}
