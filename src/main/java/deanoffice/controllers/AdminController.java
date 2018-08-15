package deanoffice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {
    @RequestMapping(value = "/allmarks", method = RequestMethod.GET)
    public ModelAndView marks(){
        return new ModelAndView("/admin/marks.html");
    }
}
