package deanoffice.controllers;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

@Controller
public class TutorController {
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private MarkRepository markRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SubjectRepository subjectRepository;

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
        ModelAndView model = new ModelAndView("/tutor/addmark.html");
        String username = request.getRemoteUser();
        Tutor tutor = tutorRepository.findByUsername(username);
        System.out.print(tutor);
        model.addObject("tutorid", tutor.getId());
        return model;
    }
    @RequestMapping(value = "/tutor/marks/addconfirm", method = RequestMethod.POST)
    public ModelAndView addMarkConfirm(HttpServletRequest request){
        String tutorid = request.getParameter("tutorid");
        String studentid = request.getParameter("studentid");
        String value = request.getParameter("value");
        String subjectname = request.getParameter("subjectname");

        System.out.println("tutor's id: " + tutorid);

        Tutor tutor = tutorRepository.findById(Integer.valueOf(tutorid)).get();
        Student student = studentRepository.findByIndexNumber(Integer.valueOf(studentid));
        Subject subject = subjectRepository.findByName(subjectname);

        Mark mark = new Mark();
        mark.setValue(Double.valueOf(value));
        mark.setTutor(tutor);
        mark.setSubject(subject);
        mark.setStudent(student);
        mark.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        markRepository.save(mark);

        return this.marks(request);
    }

    @RequestMapping(value = "/tutor/marks/delete", method = RequestMethod.POST)
    public ModelAndView deletMark(HttpServletRequest request){
        String markid = request.getParameter("id");
        Mark mark = markRepository.findById(Integer.valueOf(markid)).get();
        markRepository.delete(mark);
        return this.marks(request);
    }
    @RequestMapping(value = "/tutor/marks/edit", method = RequestMethod.GET)
    public ModelAndView editMark(HttpServletRequest request){
        Mark mark = markRepository.findById(Integer.valueOf(request.getParameter("id"))).get();
        // TODO: please fix!!!
        ModelAndView model = new ModelAndView("/tutor/markform.html");
        String username = request.getRemoteUser();
        Tutor tutor = tutorRepository.findByUsername(username);
        System.out.print(tutor);
        model.addObject("tutorid", tutor.getId());
        model.addObject("mark", mark);
        return model;
    }
    @RequestMapping(value = "/tutor/marks/editconfirm", method = RequestMethod.POST)
    public ModelAndView editMarkConfirm(HttpServletRequest request){
        String markid = request.getParameter("markid");
        String tutorid = request.getParameter("tutorid");
        String studentid = request.getParameter("studentid");
        String value = request.getParameter("value");
        String subjectname = request.getParameter("subjectname");

        System.out.println("tutor's id: " + tutorid);

        Tutor tutor = tutorRepository.findById(Integer.valueOf(tutorid)).get();
        Student student = studentRepository.findByIndexNumber(Integer.valueOf(studentid));
        Subject subject = subjectRepository.findByName(subjectname);

        Mark mark = markRepository.findById(Integer.valueOf(markid)).get();
        mark.setValue(Double.valueOf(value));
        mark.setTutor(tutor);
        mark.setSubject(subject);
        mark.setStudent(student);
        mark.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        markRepository.save(mark);

        return this.marks(request);
    }
}
