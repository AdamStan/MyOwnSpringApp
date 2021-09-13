package deanoffice.controllers.admin;

import deanoffice.entities.*;
import deanoffice.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private FacultyRepository facultyRepository;

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
    //region STUDENT FUNCTIONALITY
    @RequestMapping(value = "/admin/students/add", method = RequestMethod.GET)
    public ModelAndView addstudent(){
        ModelAndView model = new ModelAndView("/admin/adding/addstudent.html");
        return model;
    }

    @RequestMapping(value = "/admin/students/addconfirm", method = RequestMethod.POST)
    public ModelAndView confirmAddStudent(HttpServletRequest request){
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String numberOfBuilding = request.getParameter("numberofbuilding");
        String numberOfFlat = request.getParameter("numberofflat");
        String facultyid = request.getParameter("facultyid");

        Optional<Faculty> op_faculty = facultyRepository.findById(Integer.valueOf(facultyid));
        Student std = new Student(name, surname, city, street, numberOfBuilding, numberOfFlat);
        std.setFaculty(op_faculty.get());
        std.setUsername(username);
        std.setWhenStarted(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        studentRepository.save(std);

        return this.students();
    }

    @RequestMapping(value = "/admin/students/edit", method = RequestMethod.GET)
    public ModelAndView editstudent(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/editstudent.html");
        String id = request.getParameter("indexNumber");
        model.addObject( studentRepository.findByIndexNumber(Integer.valueOf(id)) );
        return model;
    }

    @RequestMapping(value = "/admin/students/editconfirm", method = RequestMethod.POST)
    public ModelAndView confirmEditStudent(HttpServletRequest request){
        String id = request.getParameter("indexNumber");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String numberOfBuilding = request.getParameter("numberofbuilding");
        String numberOfFlat = request.getParameter("numberofflat");
        String facultyid = request.getParameter("facultyid");
        String whenStarted = request.getParameter("whenStarted");
        String whenFinnished = request.getParameter("whenFinnished");

        Optional<Faculty> op_faculty = facultyRepository.findById(Integer.valueOf(facultyid));
        Student std = studentRepository.findByIndexNumber(Integer.valueOf(id));
        std.setName(name);
        std.setSurname(surname);
        std.setCity(city);
        std.setStreet(street);
        std.setNumberOfBuilding(numberOfBuilding);
        std.setNumberOfFlat(numberOfFlat);
        std.setFaculty(op_faculty.get());
        std.setUsername(username);
        std.setWhenStarted(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        try{
            System.out.println(whenStarted);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(whenStarted);
            java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
            std.setWhenStarted(sqlStartDate);

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date2 = sdf2.parse(whenFinnished);
            java.sql.Date sqlStartDate2 = new java.sql.Date(date2.getTime());
            std.setWhenFinnished(sqlStartDate2);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        studentRepository.save(std);

        return this.students();
    }

    @RequestMapping(value = "/admin/students/delete", method = RequestMethod.GET)
    public ModelAndView deleteStudent(HttpServletRequest request){
        String id = request.getParameter("indexNumber");
        Student student = studentRepository.findByIndexNumber(Integer.valueOf(id));
        studentRepository.delete(student);
        return this.students();
    }
    //endregion STUDENT FUNCTIONALITY
    //region SUBJECT FUNCTIONALITY
    @RequestMapping(value = "/admin/subjects/add", method = RequestMethod.GET)
    public ModelAndView addsubject(){
        ModelAndView model = new ModelAndView("/admin/adding/addsubject.html");
        return model;
    }

    @RequestMapping(value = "/admin/subjects/addconfirm", method = RequestMethod.POST)
    public ModelAndView confirmAddSubject(HttpServletRequest request){
        String name = request.getParameter("name");
        String facultyid = request.getParameter("facultyid");
        Subject subject = new Subject();

        Optional<Faculty> opt_faculty = facultyRepository.findById(Integer.valueOf(facultyid));
        subject.setName(name);
        subject.setFaculty(opt_faculty.get());

        subjectRepository.save(subject);
        return this.subjects();
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
    public ModelAndView confirmEditSubject(HttpServletRequest request){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String facultyid = request.getParameter("facultyid");

        Subject subject = subjectRepository.findById(Integer.valueOf(id)).get();
        Optional<Faculty> opt_faculty = facultyRepository.findById(Integer.valueOf(facultyid));
        subject.setName(name);
        subject.setFaculty(opt_faculty.get());

        subjectRepository.save(subject);
        return this.subjects();
    }
    @RequestMapping(value = "/admin/subjects/delete", method = RequestMethod.POST)
    public ModelAndView deletesubject(HttpServletRequest request){
        String id = request.getParameter("id");
        Subject subject = subjectRepository.findById(Integer.valueOf(id)).get();
        subjectRepository.delete(subject);
        return this.subjects();
    }
    //endregion SUBJECT FUNCTIONALITY
    //region TUTOR FUNCTIONALITY
    @RequestMapping(value = "/admin/tutors/add", method = RequestMethod.GET)
    public ModelAndView addtutor(){
        ModelAndView model = new ModelAndView("/admin/adding/addtutor.html");
        return model;
    }

    @RequestMapping(value = "/admin/tutors/addconfirm", method = RequestMethod.POST)
    public ModelAndView confirmAddTutor(HttpServletRequest request){
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String numberOfBuilding = request.getParameter("numberofbuilding");
        String numberOfFlat = request.getParameter("numberofflat");
        String facultyid = request.getParameter("facultyid");
        String whenStarted = request.getParameter("whenStarted");

        Optional<Faculty> op_faculty = facultyRepository.findById(Integer.valueOf(facultyid));
        Tutor tutor = new Tutor(name, surname, city, street, numberOfBuilding, numberOfFlat);
        tutor.setFaculty(op_faculty.get());
        tutor.setUsername(username);
        tutor.setWhenStarted(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        try{
            System.out.println(whenStarted);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(whenStarted);
            java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
            tutor.setWhenStarted(sqlStartDate);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        tutorRepository.save(tutor);

        return this.tutors();
    }

    @RequestMapping(value = "/admin/tutors/edit", method = RequestMethod.GET)
    public ModelAndView edittutor(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/edittutor.html");
        String id = request.getParameter("id");
        Optional<Tutor> tutor = tutorRepository.findById(Integer.valueOf(id));
        model.addObject("tutor", tutor.get());
        return model;
    }

    @RequestMapping(value = "/admin/tutors/editconfirm", method = RequestMethod.POST)
    public ModelAndView confirmEditTutor(HttpServletRequest request){
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String numberOfBuilding = request.getParameter("numberofbuilding");
        String numberOfFlat = request.getParameter("numberofflat");
        String facultyid = request.getParameter("facultyid");
        String whenStarted = request.getParameter("whenStarted");
        String whenFinnished = request.getParameter("whenFinnished");

        Optional<Faculty> op_faculty = facultyRepository.findById(Integer.valueOf(facultyid));
        Tutor tutor = tutorRepository.findById(Integer.valueOf(id)).get();
        tutor.setName(name);
        tutor.setSurname(surname);
        tutor.setCity(city);
        tutor.setStreet(street);
        tutor.setNumberOfBuilding(numberOfBuilding);
        tutor.setNumberOfFlat(numberOfFlat);
        tutor.setFaculty(op_faculty.get());
        tutor.setUsername(username);
        tutor.setWhenStarted(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

        try{
            System.out.println(whenStarted);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf1.parse(whenStarted);
            java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
            tutor.setWhenStarted(sqlStartDate);

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date2 = sdf2.parse(whenFinnished);
            java.sql.Date sqlStartDate2 = new java.sql.Date(date2.getTime());
            tutor.setWhenFinnished(sqlStartDate2);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        tutorRepository.save(tutor);

        return this.tutors();
    }

    @RequestMapping(value = "/admin/tutors/delete", method = RequestMethod.POST)
    public ModelAndView deleteTutor(HttpServletRequest request){
        String id = request.getParameter("id");
        Optional<Tutor> tutor = tutorRepository.findById(Integer.valueOf(id));
        tutorRepository.delete(tutor.get());
        return this.tutors();
    }
    //endregion TUTOR FUNCTIONALITY
}
