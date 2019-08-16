package exodia.web.controller;

import exodia.domain.model.binding.UserLoginBindingModel;
import exodia.domain.model.service.UserServiceModel;
import exodia.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public LoginController(ModelMapper modelMapper,
                           UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView login(HttpSession session, ModelAndView modelAndView) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("login");
        }

        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginConfirm(@ModelAttribute UserLoginBindingModel userLogin,
                                     ModelAndView modelAndView,
                                     HttpSession session) {
        UserServiceModel userServiceModel = userService.loginUser(modelMapper.map(userLogin, UserServiceModel.class));

        if (userServiceModel == null) {
            throw new IllegalArgumentException("Incorrect data!");
        }

        session.setAttribute("username", userServiceModel.getUsername());
        session.setAttribute("userId", userServiceModel.getId());

        modelAndView.setViewName("redirect:/home");

        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session,
                               ModelAndView modelAndView) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            session.invalidate();
            modelAndView.setViewName("redirect:/");
        }

        return modelAndView;
    }
}
