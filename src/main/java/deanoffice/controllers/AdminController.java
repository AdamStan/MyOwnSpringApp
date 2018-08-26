package deanoffice.controllers;

import deanoffice.entities.*;
import deanoffice.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    @RequestMapping(value = "/admin/allmarks", method = RequestMethod.GET)
    public ModelAndView marks(){
        Iterable<Mark> marks = markRepository.findAll();
        ModelAndView model = new ModelAndView("/admin/marks.html");
        model.addObject("marks", marks);
        return model;
    }
    @RequestMapping(value = "/admin/allsubjects", method = RequestMethod.GET)
    public ModelAndView subjects(){
        Iterable<Subject> subjects = subjectRepository.findAll();
        ModelAndView model = new ModelAndView("/admin/subjects.html");
        model.addObject("subjects", subjects);
        return model;
    }

    @RequestMapping(value = "/admin/allstudents", method = RequestMethod.GET)
    public ModelAndView students(){
        Iterable<Student> students = studentRepository.findAll();
        ModelAndView model = new ModelAndView("/admin/students.html");
        model.addObject("students", students);
        return model;
    }

    @RequestMapping(value = "/admin/alltutors", method = RequestMethod.GET)
    public ModelAndView tutors(){
        Iterable<Tutor> tutors = tutorRepository.findAll();
        ModelAndView model = new ModelAndView("/admin/tutors.html");
        model.addObject("tutors", tutors);
        return model;
    }

    @RequestMapping(value = "/admin/allfaculties", method = RequestMethod.GET)
    public ModelAndView faculties(){
        Iterable<Faculty> faculties = facultyRepository.findAll();
        ModelAndView model = new ModelAndView("/admin/faculties.html");
        model.addObject("faculties", faculties);
        return model;
    }
    /** FACULTY FUNCTIONALITY START */
    @RequestMapping(value = "/admin/addnewfaculty", method = RequestMethod.GET)
    public ModelAndView addFaculty(){
        ModelAndView modelAndView = new ModelAndView("/admin/adding/addfaculty.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/faculty/editfaculty", method = RequestMethod.GET)
    public ModelAndView editFaculty(){
        ModelAndView modelAndView = new ModelAndView("/admin/adding/editfaculty.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/faculty/delete", method = RequestMethod.GET)
    public ModelAndView deleteFaculty(HttpServletRequest request){
        String id = request.getParameter("id");
        Optional<Faculty> faculty = facultyRepository.findById(Integer.valueOf(id));
        facultyRepository.delete(faculty.get());
        return this.faculties();
    }

    @RequestMapping(value = "/admin/faculty/confirm", method = RequestMethod.POST)
    public ModelAndView confirmAddFaculty(HttpServletRequest request){
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");
        Faculty newFaculty = new Faculty(name, desc);
        facultyRepository.save(newFaculty);
        return this.faculties();
    }

    @RequestMapping(value = "/admin/faculty/confirmedit", method = RequestMethod.POST)
    public ModelAndView confirmEditFaculty(){

        return this.faculties();
    }
    /** FACULTY FUNCTIONALITY END */
}
