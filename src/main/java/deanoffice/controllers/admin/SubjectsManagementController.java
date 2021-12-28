package deanoffice.controllers.admin;

import deanoffice.entities.Faculty;
import deanoffice.entities.Subject;
import deanoffice.repositories.FacultyRepository;
import deanoffice.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class SubjectsManagementController {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    @RequestMapping(value = "/admin/allsubjects", method = RequestMethod.GET)
    public ModelAndView subjects(){
        Iterable<Subject> subjects = subjectRepository.findAll();
        ModelAndView model = new ModelAndView("/admin/subjects.html");
        model.addObject("subjects", subjects);
        return model;
    }

    @RequestMapping(value = "/admin/subjects/add", method = RequestMethod.GET)
    public ModelAndView addsubject(){
        ModelAndView model = new ModelAndView("/admin/adding/addsubject.html");
        return model;
    }

    @RequestMapping(value = "/admin/subjects/addconfirm", method = RequestMethod.POST)
    public String confirmAddSubject(HttpServletRequest request){
        String name = request.getParameter("name");
        String facultyid = request.getParameter("facultyid");
        Subject subject = new Subject();

        Optional<Faculty> opt_faculty = facultyRepository.findById(Integer.valueOf(facultyid));
        subject.setName(name);
        subject.setFaculty(opt_faculty.get());

        subjectRepository.save(subject);
        return "redirect:/admin/allsubjects";
    }

    @RequestMapping(value = "/admin/subjects/edit", method = RequestMethod.GET)
    public ModelAndView editsubject(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/editsubject.html");
        String id = request.getParameter("id");
        Optional<Subject> subject = subjectRepository.findById(Integer.valueOf(id));
        model.addObject("subject",subject.get());
        return model;
    }

    @RequestMapping(value = "/admin/subjects/editconfirm", method = RequestMethod.POST)
    public String confirmEditSubject(HttpServletRequest request){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String facultyid = request.getParameter("facultyid");

        Subject subject = subjectRepository.findById(Integer.valueOf(id)).get();
        Optional<Faculty> opt_faculty = facultyRepository.findById(Integer.valueOf(facultyid));
        subject.setName(name);
        subject.setFaculty(opt_faculty.get());

        subjectRepository.save(subject);
        return "redirect:/admin/allsubjects";
    }
    @RequestMapping(value = "/admin/subjects/delete", method = RequestMethod.POST)
    public String deleteSubject(HttpServletRequest request){
        String id = request.getParameter("id");
        Subject subject = subjectRepository.findById(Integer.valueOf(id)).get();
        subjectRepository.delete(subject);
        return "redirect:/admin/allsubjects";
    }
}
