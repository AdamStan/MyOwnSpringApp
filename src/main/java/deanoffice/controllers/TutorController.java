package deanoffice.controllers;

import deanoffice.entities.Mark;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import deanoffice.repositories.TutorRepository;
import deanoffice.services.MarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @RequestMapping(value = "/tutor/marks", method = RequestMethod.GET)
    public ModelAndView marks(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/tutor/marks.html");
        String username = request.getRemoteUser();
        Optional<Tutor> tutor = tutorRepository.findByUsername(username);
        if (tutor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tutor doesn't exist");
        }
        Set<Subject> subjects = tutor.get().getSubjects();

        List<Mark> marks = new ArrayList<>();
        for (Subject subject : subjects) {
            marks.addAll(subject.getMarks());
        }

        model.addObject("marks", marks);
        return model;
    }

    @RequestMapping(value = "/tutor/subjects", method = RequestMethod.GET)
    public ModelAndView subjects(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/tutor/subjects.html");
        String username = request.getRemoteUser();
        Optional<Tutor> tutor = tutorRepository.findByUsername(username);
        if (tutor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tutor doesn't exist");
        }
        model.addObject("subjects", tutor.get().getSubjects());
        return model;
    }

    @RequestMapping(value = "/tutor/marks/add", method = RequestMethod.GET)
    public ModelAndView addMark(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/tutor/addmark.html");
        String username = request.getRemoteUser();
        Optional<Tutor> tutor = tutorRepository.findByUsername(username);
        if (tutor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tutor doesn't exist");
        }
        Set<Subject> subjects = tutor.get().getSubjects();
        log.info(tutor.toString());
        model.addObject("tutorid", tutor.get().getId());
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
        Optional<Tutor> tutor = tutorRepository.findByUsername(username);
        if (tutor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tutor doesn't exist");
        }
        Set<Subject> subjects = tutor.get().getSubjects();
        subjects.remove(mark.getSubject());
        log.info(tutor.toString());
        model.addObject("subjects", subjects);
        model.addObject("tutorid", tutor.get().getId());
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
