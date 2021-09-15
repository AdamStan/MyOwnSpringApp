package deanoffice.controllers.admin;

import deanoffice.entities.Faculty;
import deanoffice.entities.Student;
import deanoffice.repositories.FacultyRepository;
import deanoffice.repositories.StudentRepository;
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
public class StudentsManagementController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;


    @RequestMapping(value = "/admin/allstudents", method = RequestMethod.GET)
    public ModelAndView students(){
        Iterable<Student> students = studentRepository.findAll();
        ModelAndView model = new ModelAndView("/admin/students.html");
        model.addObject("students", students);
        return model;
    }

    @RequestMapping(value = "/admin/students/add", method = RequestMethod.GET)
    public ModelAndView addstudent(){
        ModelAndView model = new ModelAndView("/admin/adding/addstudent.html");
        return model;
    }

    @RequestMapping(value = "/admin/students/addconfirm", method = RequestMethod.POST)
    public String confirmAddStudent(HttpServletRequest request){
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

        return "redirect:/admin/allstudents";
    }

    @RequestMapping(value = "/admin/students/edit", method = RequestMethod.GET)
    public ModelAndView editstudent(HttpServletRequest request){
        ModelAndView model = new ModelAndView("/admin/adding/editstudent.html");
        String id = request.getParameter("indexNumber");
        model.addObject( studentRepository.findByIndexNumber(Integer.valueOf(id)) );
        return model;
    }

    @RequestMapping(value = "/admin/students/editconfirm", method = RequestMethod.POST)
    public String confirmEditStudent(HttpServletRequest request){
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

        return "redirect:/admin/allstudents";
    }

    @RequestMapping(value = "/admin/students/delete", method = RequestMethod.GET)
    public String deleteStudent(HttpServletRequest request){
        String id = request.getParameter("indexNumber");
        Student student = studentRepository.findByIndexNumber(Integer.valueOf(id));
        studentRepository.delete(student);
        return "redirect:/admin/allstudents";
    }
}
