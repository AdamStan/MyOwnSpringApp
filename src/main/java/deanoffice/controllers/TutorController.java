package deanoffice.controllers;

import deanoffice.entities.Mark;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import deanoffice.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Set;

@Controller
public class TutorController {
    @Autowired
    private TutorRepository tutorRepository;

    @RequestMapping(value = "/tutor/marks", method = RequestMethod.GET)
    public ModelAndView marks(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/tutor/marks.html");
        String username = request.getRemoteUser();
        Tutor tutor = tutorRepository.findByUsername(username);
        Set<Subject> subjects = tutor.getSubjects();

        ArrayList<Mark> marks = new ArrayList<>();
        for(Subject subject : subjects){
            for(Mark mark : subject.getMarks()){
                marks.add(mark);
            }
        }

        model.addObject("marks", marks);
        return model;
    }

    @RequestMapping(value = "/tutor/subjects", method = RequestMethod.GET)
    public ModelAndView subjects(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/tutor/subjects.html");
        String username = request.getRemoteUser();
        Tutor tutor = tutorRepository.findByUsername(username);
        model.addObject("subjects", tutor.getSubjects());
        return model;
    }

    @RequestMapping(value = "/tutor/marks/add", method = RequestMethod.GET)
    public ModelAndView addMark(HttpServletRequest request){
        ModelAndView model = new ModelAndView();
        return model;
    }
    @RequestMapping(value = "/tutor/marks/addconfirm", method = RequestMethod.POST)
    public ModelAndView addMarkConfirm(HttpServletRequest request){
        ModelAndView model = new ModelAndView();
        return model;
    }

    @RequestMapping(value = "/tutor/marks/delete", method = RequestMethod.POST)
    public ModelAndView deletMark(HttpServletRequest request){
        String markid = request.getParameter("");
        ModelAndView model = new ModelAndView();
        return model;
    }
    @RequestMapping(value = "/tutor/marks/edit", method = RequestMethod.GET)
    public ModelAndView editMark(HttpServletRequest request){
        ModelAndView model = new ModelAndView();
        return model;
    }
    @RequestMapping(value = "/tutor/marks/editconfirm", method = RequestMethod.POST)
    public ModelAndView editMarkConfirm(HttpServletRequest request){
        ModelAndView model = new ModelAndView();
        return model;
    }
}
