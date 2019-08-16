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
import java.util.List;

@Controller
public class DocumentController {
    private static final String DOCUMENT_NOT_FOUND = "Document not fount!";

    private final Validator validator;
    private final ModelMapper modelMapper;
    private final DocumentService documentService;

    @Autowired
    public DocumentController(Validator validator,
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
    public ModelAndView details(@PathVariable String id,
                                HttpSession session,
                                ModelAndView modelAndView) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        } else {
            DocumentViewModel document = modelMapper
                    .map(documentService.findByDocumentId(id), DocumentViewModel.class);

            if (document == null) {
                throw new IllegalArgumentException(DOCUMENT_NOT_FOUND);
            }

            modelAndView.setViewName("details");
            modelAndView.addObject("model", document);
        }
        return modelAndView;
    }

    @GetMapping("/print/{id}")
    public ModelAndView print(HttpSession session,
                              @PathVariable String id,
                              ModelAndView modelAndView) {

        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/login");
        } else {
           DocumentViewModel documentViewModel = modelMapper
                    .map(documentService.findByDocumentId(id), DocumentViewModel.class);

            if (documentViewModel == null) {
                throw new IllegalArgumentException(DOCUMENT_NOT_FOUND);
            }

            modelAndView.addObject("document", documentViewModel);
            modelAndView.setViewName("print");
        }
        return modelAndView;
    }

    @PostMapping("/print/{id}")
    public ModelAndView printConfirm(@PathVariable String id,
                                     ModelAndView modelAndView) {

        if (! documentService.deleteByDocumentId(id)) {
            throw new IllegalArgumentException(DOCUMENT_NOT_FOUND);
        }

        modelAndView.setViewName("redirect:/home");

        return modelAndView;
    }
}
