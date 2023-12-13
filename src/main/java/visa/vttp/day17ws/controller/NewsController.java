package visa.vttp.day17ws.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import visa.vttp.day17ws.service.NewsService;

@Controller
@RequestMapping(path="/")
public class NewsController {

    @Autowired
    NewsService newsSvc;

    @GetMapping(path="/")
    public String getHome(Model model) {
        model.addAttribute("categories", newsSvc.getCategories());
        model.addAttribute("countries", newsSvc.getCountries());
        return "home";
    }

    @GetMapping("/news")
    public String getNews(@RequestParam String category, @RequestParam String country, Model model) {
        model.addAttribute("news", newsSvc.getNews(category, country));
        return "news";
    }
}
    

