package deanoffice.controllers;

import deanoffice.entities.*;
import deanoffice.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
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
    //region FACULTY FUNCTIONALITY
    @RequestMapping(value = "/admin/addnewfaculty", method = RequestMethod.GET)
    public ModelAndView addFaculty(){
        ModelAndView modelAndView = new ModelAndView("/admin/adding/addfaculty.html");
        return modelAndView;
    }

    @RequestMapping(value = "/admin/faculty/editfaculty", method = RequestMethod.GET)
    public ModelAndView editFaculty(HttpServletRequest request){
        String id = request.getParameter("id");
        ModelAndView modelAndView = new ModelAndView("/admin/adding/editfaculty.html");
        modelAndView.addObject("id", id);
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
    public ModelAndView confirmEditFaculty(HttpServletRequest request){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("desc");
        Optional<Faculty> opf = facultyRepository.findById(Integer.valueOf(id));
        Faculty facultyUpdate = opf.get();
        facultyUpdate.setName(name);
        facultyUpdate.setDescription(description);
        facultyRepository.save(facultyUpdate);
        return this.faculties();
    }
    //endregion FACULTY FUNCTIONALITY END
    //region MARK FUNCTIONALITY
    @RequestMapping(value = "/admin/marks/add", method = RequestMethod.GET)
    public ModelAndView addmark(){
        ModelAndView model = new ModelAndView("/admin/adding/addmark.html");
        return model;
    }

    @RequestMapping(value = "/admin/marks/addconfirm", method = RequestMethod.POST)
    public ModelAndView confirmAddMark(HttpServletRequest request){
        String value = request.getParameter("value");
        String studentid = request.getParameter("studentid");
        String tutorid = request.getParameter("tutorid");
        String subjectname = request.getParameter("subjectname");
        Mark newMark = new Mark();

        Student student = studentRepository.findByIndexNumber(Integer.valueOf(studentid));
        Optional<Tutor> t = tutorRepository.findById(Integer.valueOf(tutorid));
        Tutor tutor = t.get();
        Subject subject = subjectRepository.findByName(subjectname);

        newMark.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        newMark.setValue(Double.valueOf(value));
        newMark.setStudent(student);
        newMark.setSubject(subject);
        newMark.setTutor(tutor);
        markRepository.save(newMark);
        return this.marks();
    }

    @RequestMapping(value = "/admin/marks/edit", method = RequestMethod.GET)
    public ModelAndView editmark(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/editmark.html");
        String id = request.getParameter("id");
        Optional<Mark> mark = markRepository.findById(Integer.valueOf(id));
        model.addObject(mark.get());
        return model;
    }

    @RequestMapping(value = "/admin/marks/editconfirm", method = RequestMethod.POST)
    public ModelAndView confirmEditMark(HttpServletRequest request){
        String id = request.getParameter("id");
        String newValue = request.getParameter("value");
        String studentid = request.getParameter("studentid");
        String tutorid = request.getParameter("tutorid");
        String subjectname = request.getParameter("subjectname");

        Optional<Mark> m = markRepository.findById(Integer.valueOf(id));
        Mark mark = m.get();


        Student student = studentRepository.findByIndexNumber(Integer.valueOf(studentid));
        Optional<Tutor> t = tutorRepository.findById(Integer.valueOf(tutorid));
        Tutor tutor = t.get();
        Subject subject = subjectRepository.findByName(subjectname);

        mark.setValue(Double.valueOf(newValue));
        mark.setStudent(student);
        mark.setSubject(subject);
        mark.setTutor(tutor);

        markRepository.save(mark);
        return this.marks();
    }

    @RequestMapping(value = "/admin/marks/delete", method = RequestMethod.GET)
    public ModelAndView deleteMark(HttpServletRequest request){
        String id = request.getParameter("id");
        Optional<Mark> m = markRepository.findById(Integer.valueOf(id));
        Mark mark = m.get();
        markRepository.delete(mark);
        return this.marks();
    }
    /*
    //endregion MARK FUNCTIONALITY END
    //region STUDENT FUNCTIONALITY
    @RequestMapping(value = "/admin/students/add")
    public ModelAndView addstudent(){
        ModelAndView model = new ModelAndView("/admin/adding/addstudent.html");
        return model;
    }

    @RequestMapping(value = "/admin/students/addconfirm")

    @RequestMapping(value = "/admin/students/edit")
    public ModelAndView editmark(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/editmark.html");

        return model;
    }

    @RequestMapping(value = "/admin/students/editconfirm")

    @RequestMapping(value = "/admin/students/delete")
    //endregion STUDENT FUNCTIONALITY
    /*
    //region SUBJECT FUNCTIONALITY
    @RequestMapping(value = "/admin/marks/add")
    public ModelAndView addmark(){
        ModelAndView model = new ModelAndView("/admin/adding/addmark.html");
        return model;
    }

    @RequestMapping(value = "/admin/marks/addconfirm")

    @RequestMapping(value = "/admin/marks/edit")
    public ModelAndView editmark(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/editmark.html");

        return model;
    }

    @RequestMapping(value = "/admin/marks/editconfirm")

    @RequestMapping(value = "/admin/marks/delete")
    //endregion SUBJECT FUNCTIONALITY
    //region TUTOR FUNCTIONALITY
    @RequestMapping(value = "/admin/marks/add")
    public ModelAndView addmark(){
        ModelAndView model = new ModelAndView("/admin/adding/addmark.html");
        return model;
    }

    @RequestMapping(value = "/admin/marks/addconfirm")

    @RequestMapping(value = "/admin/marks/edit")
    public ModelAndView editmark(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/editmark.html");

        return model;
    }

    @RequestMapping(value = "/admin/marks/editconfirm")

    @RequestMapping(value = "/admin/marks/delete")
    //endregion TUTOR FUNCTIONALITY
    */
}
