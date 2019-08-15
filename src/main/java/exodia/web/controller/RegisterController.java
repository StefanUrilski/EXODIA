package exodia.web.controller;

import exodia.domain.config.Security;
import exodia.domain.model.binding.UserRegisterBindingModel;
import exodia.domain.model.service.UserServiceModel;
import exodia.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Validator;

@Controller
public class RegisterController {

    private final Validator validator;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public RegisterController(Validator validator,
                              ModelMapper modelMapper,
                              UserService userService) {
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel userRegister,
                                        ModelAndView modelAndView){

        if (! userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords doesn't match!");
        }

        if (validator.validate(userRegister).size() != 0) {
            throw new IllegalArgumentException("All fields have to be field!");
        }

        UserServiceModel userServiceModel = modelMapper.map(userRegister, UserServiceModel.class);

        if (! userService.saveUser(userServiceModel)) {
            throw new IllegalArgumentException("Oops... Something goes wrong!");
        }

        modelAndView.setViewName("redirect:/login");
        return modelAndView;
    }

}
