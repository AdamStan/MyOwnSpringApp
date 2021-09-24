package deanoffice.controllers.admin;

import deanoffice.entities.Mark;
import deanoffice.services.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
public class MarksManagementController {
    private final static Logger log = Logger.getLogger(MarksManagementController.class.getName());

    private MarkService markService;

    @Autowired
    public void setMarkService(MarkService markService) {
        this.markService = markService;
    }

    @RequestMapping(value = "/allmarks", method = RequestMethod.GET)
    public ModelAndView marks(){
        List<Mark> marks = markService.getAll();
        ModelAndView model = new ModelAndView("/admin/marks.html");
        model.addObject("marks", marks);
        return model;
    }

    @RequestMapping(value = "/marks/add", method = RequestMethod.GET)
    public ModelAndView addMarkForm(){
        ModelAndView model = new ModelAndView("/admin/adding/markform.html");
        model.addObject(markService.getObjectToAdd());
        return model;
    }

    @RequestMapping(value = "/marks/edit", method = RequestMethod.GET)
    public ModelAndView editMarkForm(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/markform.html");
        String id = request.getParameter("id");
        model.addObject(markService.getObjectToEdit(id));
        return model;
    }

    @RequestMapping(value = "/marks/editconfirm", method = RequestMethod.POST)
    public String confirmEditMark(HttpServletRequest request){
        String id = request.getParameter("id");
        String newValue = request.getParameter("value");
        String studentId = request.getParameter("studentid");
        String tutorId = request.getParameter("tutorid");
        String subjectName = request.getParameter("subjectname");

        markService.update(id, newValue, studentId, tutorId, subjectName);
        return "redirect:/admin/allmarks";
    }

    @RequestMapping(value = "/marks/delete", method = RequestMethod.POST)
    public String deleteMark(HttpServletRequest request){
        String id = request.getParameter("id");
        markService.removeObject(id);
        return "redirect:/admin/allmarks";
    }
}
