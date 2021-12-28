package deanoffice.controllers;

import deanoffice.entities.Student;
import deanoffice.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @RequestMapping(value = "/student/marks", method = RequestMethod.GET)
    public ModelAndView marks(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/student/studentsMarks.html");
        String username = request.getRemoteUser();
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Remote user is not a student in database");
        }
        model.addObject("marks", student.getMarks());
        return model;
    }

    @RequestMapping(value = "/student/subjects", method = RequestMethod.GET)
    public ModelAndView subjects(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("/student/studentsSubjects.html");
        String username = request.getRemoteUser();
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Remote user is not a student in database");
        }
        model.addObject("subjects", student.getSubjects());
        return model;
    }
}
