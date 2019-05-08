package codesquad.controller;

import codesquad.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "/index";
    }

}
