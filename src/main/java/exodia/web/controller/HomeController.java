package exodia.web.controller;

import exodia.domain.model.view.DocumentHomeViewModel;
import exodia.service.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {


    private final ModelMapper modelMapper;
    private final DocumentService documentService;

    @Autowired
    public HomeController(ModelMapper modelMapper,
                          DocumentService documentService) {
        this.modelMapper = modelMapper;
        this.documentService = documentService;
    }

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

        List<DocumentHomeViewModel> usersDocuments = documentService
                .findAllDocumentsByUserId((String) session.getAttribute("userId"))
                .stream()
                .map(document -> modelMapper.map(document, DocumentHomeViewModel.class))
                .peek(document -> {
                    if (document.getTitle().length() > 12) {
                        document.setTitle(document.getTitle().substring(0, 12) + "...");
                    }
                })
                .collect(Collectors.toList());

        modelAndView.addObject("documents", usersDocuments);

        return modelAndView;
    }
}
