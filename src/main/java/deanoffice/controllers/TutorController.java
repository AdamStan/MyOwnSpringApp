package deanoffice.controllers;

import deanoffice.entities.Mark;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import deanoffice.repositories.SubjectRepository;
import deanoffice.repositories.TutorRepository;
import deanoffice.services.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

@Controller
public class TutorController {
    private static final Logger log = Logger.getLogger(TutorController.class.getName());

    private MarkService markService;

    @Autowired
    public void setMarkService(MarkService markService) {
        this.markService = markService;
    }

    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping(value = "/tutor/marks", method = RequestMethod.GET)
    public ModelAndView marks(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/tutor/marks.html");
        String username = request.getRemoteUser();
        Tutor tutor = tutorRepository.findByUsername(username);
        Set<Subject> subjects = tutor.getSubjects();

        ArrayList<Mark> marks = new ArrayList<>();
        for (Subject subject : subjects) {
            for (Mark mark : subject.getMarks()) {
                marks.add(mark);
            }
        }

        model.addObject("marks", marks);
        return model;
    }

    @RequestMapping(value = "/tutor/subjects", method = RequestMethod.GET)
    public ModelAndView subjects(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/tutor/subjects.html");
        String username = request.getRemoteUser();
        Tutor tutor = tutorRepository.findByUsername(username);
        model.addObject("subjects", tutor.getSubjects());
        return model;
    }

    @RequestMapping(value = "/tutor/marks/add", method = RequestMethod.GET)
    public ModelAndView addMark(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/tutor/addmark.html");
        String username = request.getRemoteUser();
        Tutor tutor = tutorRepository.findByUsername(username);
        Set<Subject> subjects = tutor.getSubjects();
        log.info(tutor.toString());
        model.addObject("tutorid", tutor.getId());
        model.addObject("subjects", subjects);
        return model;
    }

    @RequestMapping(value = "/tutor/marks/addconfirm", method = RequestMethod.POST)
    public ModelAndView addMarkConfirm(HttpServletRequest request) {
        String markId = "-1";
        String tutorId = request.getParameter("tutorid");
        String studentId = request.getParameter("studentid");
        String value = request.getParameter("value");
        String subjectName = request.getParameter("subjectname");

        markService.update(markId, value, studentId, tutorId, subjectName);
        return this.marks(request);
    }

    @RequestMapping(value = "/tutor/marks/delete", method = RequestMethod.POST)
    public ModelAndView deletMark(HttpServletRequest request) {
        String markId = request.getParameter("id");
        markService.removeObject(markId);
        return this.marks(request);
    }

    @RequestMapping(value = "/tutor/marks/edit", method = RequestMethod.GET)
    public ModelAndView editMark(HttpServletRequest request) {
        Mark mark = markService.getObjectToEdit(request.getParameter("id"));
        ModelAndView model = new ModelAndView("/tutor/editmark.html");
        String username = request.getRemoteUser();
        Tutor tutor = tutorRepository.findByUsername(username);
        Set<Subject> subjects = tutor.getSubjects();
        subjects.remove(mark.getSubject());
        log.info(tutor.toString());
        model.addObject("subjects", subjects);
        model.addObject("tutorid", tutor.getId());
        model.addObject("mark", mark);
        return model;
    }

    @RequestMapping(value = "/tutor/marks/editconfirm", method = RequestMethod.POST)
    public ModelAndView editMarkConfirm(HttpServletRequest request) {
        String markId = request.getParameter("markid");
        String tutorId = request.getParameter("tutorid");
        String studentId = request.getParameter("studentid");
        String value = request.getParameter("value");
        String subjectName = request.getParameter("subjectname");

        markService.update(markId, value, studentId, tutorId, subjectName);
        return this.marks(request);
    }
}
