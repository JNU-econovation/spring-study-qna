package codesquad.controller;

import codesquad.QuestionRepository;
import codesquad.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/questions")
    public String question(Question question) {
        question.checkCurrentTime();
        questionRepository.save(question);
        System.out.println("add 후 question : " + question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String questionList(Model model) {
        model.addAttribute("questionList", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String questionsShow(@PathVariable("id") Long id, Model model) {
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            model.addAttribute("question", findQuestion.get());
            return "qna/show";
        } else {
            return "/";
        }
    }

    @GetMapping("/questions/{id}/form")
    public String updateQuestionForm(@PathVariable("id") Long id, Model model) {
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            model.addAttribute("question", findQuestion.get());
            return "qna/updateForm";
        } else {
            return "/questions/{id}";
        }
    }

    @PostMapping("/questions/{id}/update")
    public String editQuestion(@PathVariable("id") Long id, Question question) {
        Question changedQuestion = questionRepository.findById(id).get();
        changedQuestion.setTitle(question.getTitle());
        changedQuestion.setContents(question.getContents());
        questionRepository.save(changedQuestion);
        return "redirect:/";
    }

    @DeleteMapping("/questions/{id}/delete")
    public String deleteQuestion(@PathVariable("id") Long id) {
        Optional<Question> findQuestion = questionRepository.findById(id);
        if (findQuestion.isPresent()) {
            questionRepository.delete(findQuestion.get());
            return "redirect:/";
        } else {
            return "/";
        }
    }
}
