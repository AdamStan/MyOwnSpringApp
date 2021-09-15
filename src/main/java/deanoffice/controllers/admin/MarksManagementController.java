package deanoffice.controllers.admin;

import deanoffice.entities.Mark;
import deanoffice.entities.Student;
import deanoffice.entities.Subject;
import deanoffice.entities.Tutor;
import deanoffice.repositories.MarkRepository;
import deanoffice.repositories.StudentRepository;
import deanoffice.repositories.SubjectRepository;
import deanoffice.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class MarksManagementController {

    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;

    @RequestMapping(value = "/allmarks", method = RequestMethod.GET)
    public ModelAndView marks(){
        Iterable<Mark> marks = markRepository.findAll();
        ModelAndView model = new ModelAndView("/admin/marks.html");
        model.addObject("marks", marks);
        return model;
    }

    @RequestMapping(value = "/marks/add", method = RequestMethod.GET)
    public ModelAndView addMarkForm(){
        ModelAndView model = new ModelAndView("/admin/adding/addmark.html");
        return model;
    }

    @RequestMapping(value = "/marks/addconfirm", method = RequestMethod.POST)
    public String confirmAddMark(HttpServletRequest request){
        String value = request.getParameter("value");
        String studentid = request.getParameter("studentid");
        String tutorid = request.getParameter("tutorid");
        String subjectname = request.getParameter("subjectname");
        Mark newMark = new Mark();

        Student student = studentRepository.findByIndexNumber(Integer.valueOf(studentid));
        Optional<Tutor> t = tutorRepository.findById(Integer.valueOf(tutorid));
        Subject subject = subjectRepository.findByName(subjectname);

        newMark.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        newMark.setValue(Double.valueOf(value));
        newMark.setStudent(student);
        newMark.setSubject(subject);
        t.ifPresent(newMark::setTutor);
        markRepository.save(newMark);
        return "redirect:/admin/allmarks";
    }

    @RequestMapping(value = "/marks/edit", method = RequestMethod.GET)
    public ModelAndView editMarkForm(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/editmark.html");
        String id = request.getParameter("id");
        Optional<Mark> mark = markRepository.findById(Integer.valueOf(id));
        model.addObject(mark.get());
        return model;
    }

    @RequestMapping(value = "/marks/editconfirm", method = RequestMethod.POST)
    public String confirmEditMark(HttpServletRequest request){
        String id = request.getParameter("id");
        String newValue = request.getParameter("value");
        String studentid = request.getParameter("studentid");
        String tutorid = request.getParameter("tutorid");
        String subjectname = request.getParameter("subjectname");

        Optional<Mark> m = markRepository.findById(Integer.parseInt(id));
        Mark mark = m.get();

        Student student = studentRepository.findByIndexNumber(Integer.valueOf(studentid));
        Optional<Tutor> t = tutorRepository.findById(Integer.valueOf(tutorid));

        Subject subject = subjectRepository.findByName(subjectname);

        mark.setValue(Double.valueOf(newValue));
        mark.setStudent(student);
        mark.setSubject(subject);
        t.ifPresent(mark::setTutor);

        markRepository.save(mark);
        return "redirect:/admin/allmarks";
    }

    @RequestMapping(value = "/marks/delete", method = RequestMethod.POST)
    public String deleteMark(HttpServletRequest request){
        String id = request.getParameter("id");
        Optional<Mark> m = markRepository.findById(Integer.valueOf(id));
        Mark mark = m.get();
        markRepository.delete(mark);
        return "redirect:/admin/allmarks";
    }
}
