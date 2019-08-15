package exodia.domain.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Component
public class Security {
    private static HttpSession session;

    public static ModelAndView guest(String requestRoute, String redirectRoute, ModelAndView modelAndView) {
        if (session.getAttribute("username") != null) {
            modelAndView.setViewName("redirect:" + redirectRoute);
        } else {
            modelAndView.setViewName(requestRoute);
        }

        return modelAndView;
    }

    public static ModelAndView logged(String requestRoute, String redirectRoute, ModelAndView modelAndView) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:" + redirectRoute);
        } else {
            modelAndView.setViewName(requestRoute);
        }

        return modelAndView;
    }
}
