package exodia.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {


    @GetMapping("/")
    public ModelAndView index(HttpSession session, ModelAndView modelAndView) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:home");
        } else {
            modelAndView.setViewName("index");
        }
        return modelAndView;
    }

    @GetMapping("/home")
    public ModelAndView home(HttpSession session, ModelAndView modelAndView) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.setViewName("home");
        }

        return modelAndView;
    }
}
