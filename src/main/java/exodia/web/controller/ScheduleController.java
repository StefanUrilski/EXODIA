package exodia.web.controller;

import exodia.domain.model.binding.ScheduleCreateBindingModel;
import exodia.domain.model.service.DocumentServiceModel;
import exodia.domain.model.view.DocumentViewModel;
import exodia.service.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Validator;

@Controller
public class ScheduleController {

    private final Validator validator;
    private final ModelMapper modelMapper;
    private final DocumentService documentService;

    @Autowired
    public ScheduleController(Validator validator,
                              ModelMapper modelMapper,
                              DocumentService documentService) {
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.documentService = documentService;
    }

    @GetMapping("/schedule")
    public ModelAndView schedule(HttpSession session, ModelAndView modelAndView) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/");
        } else {
            modelAndView.setViewName("schedule");
        }


        return modelAndView;
    }

    @PostMapping("/schedule")
    public ModelAndView scheduleConfirm(HttpSession session,
                                        ModelAndView modelAndView,
                                        @ModelAttribute ScheduleCreateBindingModel bindingModel) {

        if (validator.validate(bindingModel).size() != 0) {
            throw new IllegalArgumentException("Fields must not be left empty!");
        }

        DocumentServiceModel document = modelMapper.map(bindingModel, DocumentServiceModel.class);

        document.setUserId((String) session.getAttribute("userId"));

        document = documentService.saveSchedule(document);

        if (document == null) {
            throw new IllegalArgumentException("Oops.. something goes wrong.");
        }

        modelAndView.setViewName("redirect:/details/" + document.getId());
        return modelAndView;
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable String id, ModelAndView modelAndView) {
        DocumentServiceModel document = documentService.findByDocumentId(id);

        if (document == null) {
            throw new IllegalArgumentException("Document not fount!");
        }

        modelAndView.setViewName("details");
        modelAndView.addObject("model", modelMapper.map(document, DocumentViewModel.class));

        return modelAndView;
    }
}
