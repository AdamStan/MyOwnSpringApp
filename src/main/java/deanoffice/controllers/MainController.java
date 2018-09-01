package deanoffice.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import deanoffice.entities.Student;
import deanoffice.entities.Tutor;
import deanoffice.noentities.UsersTableData;
import deanoffice.repositories.StudentRepository;
import deanoffice.repositories.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.userdetails.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;

    @RequestMapping(value = {"/whodidit"}, method = RequestMethod.GET)
    public ModelAndView authors(){
        return new ModelAndView("aboutus.html");
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response){
        ModelAndView loginPage;
        String username = request.getRemoteUser();
        if(username != null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println("Nazwa uzytkownika: " + username);
            System.out.println("ROLA: " + user.getAuthorities());
        }
        loginPage = new ModelAndView("/home.html");
        return loginPage;
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("login.html");
    }
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView hello(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for(Object o : user.getAuthorities()){
            System.out.println("Who wants more options? " + o.toString());
            if(o.toString().equals("ROLE_ADMIN")){
                return new ModelAndView("admin/helloAdmin.html");
            }
            else if(o.toString().equals("ROLE_TUTOR")){
                return new ModelAndView("tutor/helloTutor.html");
            }
            else if(o.toString().equals("ROLE_STUDENT")){
                return new ModelAndView("student/helloStudent.html");
            }
        }
        return new ModelAndView("hello.html");
    }
    @RequestMapping(value = "/useroptions", method = RequestMethod.GET)
    public ModelAndView editProfile(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = user.getUsername();

        ModelAndView model = new ModelAndView("hello.html");
        for(Object o : user.getAuthorities()){
            System.out.println("Who wants more options? " + o.toString());
            if(o.toString().equals("ROLE_ADMIN")){
                model = this.adminsOptions(username);
            }
            else if(o.toString().equals("ROLE_TUTOR")){
                model = this.tutortsOptions(username);
            }
            else if(o.toString().equals("ROLE_STUDENT")){
                model = this.studentsOptions(username);
            }
        }
        return model;
    }
    @RequestMapping(value = "/useroptions/student", method = RequestMethod.POST)
    public ModelAndView confirmStudent(HttpServletRequest request) throws Exception{
        String id = request.getParameter("indexNumber");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String numberOfBuilding = request.getParameter("numberofbuilding");
        String numberOfFlat = request.getParameter("numberofflat");
        String password = request.getParameter("password");

        UsersTableData u = new UsersTableData();
        Student std = studentRepository.findByIndexNumber(Integer.valueOf(id));

        if(u.findUserByUsername(username).getUsername() != null && !username.equals(std.getName())){
            throw new Exception("Username is in use!");
        }

        std.setName(name);
        std.setSurname(surname);
        std.setCity(city);
        std.setStreet(street);
        std.setNumberOfBuilding(numberOfBuilding);
        std.setNumberOfFlat(numberOfFlat);

        u.deleteUserByUsernma(std.getUsername());
        u.insertUser(username, password,"ROLE_STUDENT", "on");
        std.setUsername(username);

        studentRepository.save(std);

        return new ModelAndView("/informlogout.html");
    }
    @RequestMapping(value = "/useroptions/tutor", method = RequestMethod.POST)
    public ModelAndView confirmTutor(HttpServletRequest request) throws Exception{
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String numberOfBuilding = request.getParameter("numberofbuilding");
        String numberOfFlat = request.getParameter("numberofflat");
        String password = request.getParameter("password");

        UsersTableData u = new UsersTableData();
        Tutor tutor = tutorRepository.findById(Integer.valueOf(id)).get();

        if(u.findUserByUsername(username).getUsername() != null && !username.equals(tutor.getName())){
            throw new Exception("Username is in use!");
        }

        tutor.setName(name);
        tutor.setSurname(surname);
        tutor.setCity(city);
        tutor.setStreet(street);
        tutor.setNumberOfBuilding(numberOfBuilding);
        tutor.setNumberOfFlat(numberOfFlat);


        u.deleteUserByUsernma(tutor.getUsername());
        u.insertUser(username, password,"ROLE_TUTOR", "on");

        tutor.setUsername(username);

        tutorRepository.save(tutor);

        return new ModelAndView("/informlogout.html");
    }
    @RequestMapping(value = "/useroptions/admin", method = RequestMethod.POST)
    public ModelAndView confirmAdmin(HttpServletRequest request) throws Exception{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String oldusername = request.getParameter("oldusername");
        String role = request.getParameter("role");
        String enabled = request.getParameter("enabled");
        System.out.println("Parameters: " + username + ", " +
                password + ", " + role + ", " + enabled);

        UsersTableData u = new UsersTableData();

        if(u.findUserByUsername(username).getUsername() != null && !username.equals(oldusername)){
            throw new Exception("Username is in use!");
        }

        u.deleteUserByUsernma(oldusername);
        u.insertUser(username, password, role, enabled);

        return new ModelAndView("/informlogout.html");
    }

    private ModelAndView studentsOptions(String username){ //student & password
        ModelAndView model = new ModelAndView("student/myAccount.html");
        Student student = studentRepository.findByUsername(username);
        deanoffice.noentities.User user = new UsersTableData().findUserByUsername(username);
        model.addObject("student", student);
        model.addObject("password", user.getPassword());
        return model;
    }
    private ModelAndView tutortsOptions(String username){ //tutor & password
        ModelAndView model = new ModelAndView("tutor/myAccount.html");
        Tutor tutor = tutorRepository.findByUsername(username);
        deanoffice.noentities.User user = new UsersTableData().findUserByUsername(username);
        model.addObject("tutor", tutor);
        model.addObject("password", user.getPassword());
        return model;
    }
    private ModelAndView adminsOptions(String username){ //all from users & autorithy table
        ModelAndView model = new ModelAndView("admin/myAccount.html");
        deanoffice.noentities.User user = new UsersTableData().findUserByUsername(username);
        model.addObject("user", user);
        return model;
    }
}
