package deanoffice.controllers;

import deanoffice.entities.Student;
import deanoffice.entities.Tutor;
import deanoffice.noentities.UsersTableData;
import deanoffice.repositories.StudentRepository;
import deanoffice.repositories.TutorRepository;
import deanoffice.security.UserSecurityProvider;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UsersController {
    private static final Logger log = Logger.getLogger(UsersController.class);

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private UsersTableData userTable;
    @Autowired
    private UserSecurityProvider fromContext;

    @RequestMapping(value = {"/whodidit"}, method = RequestMethod.GET)
    public ModelAndView authors() {
        return new ModelAndView("about.html");
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getRemoteUser();
        if (username != null) {
            UserDetails user = fromContext.getCurrentSecurityUser().get();
            log.info("User's name: " + username);
            log.info("Authorities: " + user.getAuthorities());
        }
        return new ModelAndView("home.html");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("login.html");
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView hello() {
        Optional<UserDetails> user = fromContext.getCurrentSecurityUser();
        if (user.isEmpty()) {
            return new ModelAndView("hello.html");
        }

        for (GrantedAuthority auth : user.get().getAuthorities()) {
            log.info("Who wants more options? " + auth.toString());
            if (auth.getAuthority().equals("ROLE_ADMIN")) {
                return new ModelAndView("admin/helloAdmin.html");
            } else if (auth.getAuthority().equals("ROLE_TUTOR")) {
                return new ModelAndView("tutor/helloTutor.html");
            } else if (auth.getAuthority().equals("ROLE_STUDENT")) {
                return new ModelAndView("student/helloStudent.html");
            }
        }
        return new ModelAndView("hello.html");
    }

    @RequestMapping(value = "/useroptions", method = RequestMethod.GET)
    public ModelAndView editProfile() {
        ModelAndView model = new ModelAndView("hello.html");
        Optional<UserDetails> user = fromContext.getCurrentSecurityUser();

        if (user.isEmpty()) {
            return model;
        }
        String username = user.get().getUsername();

        for (Object o : user.get().getAuthorities()) {
            log.info("Who wants more options? " + o.toString());
            if (o.toString().equals("ROLE_ADMIN")) {
                model = this.adminsOptions(username);
            } else if (o.toString().equals("ROLE_TUTOR")) {
                model = this.tutortsOptions(username);
            } else if (o.toString().equals("ROLE_STUDENT")) {
                model = this.studentsOptions(username);
            }
        }
        return model;
    }

    @RequestMapping(value = "/useroptions/student", method = RequestMethod.POST)
    public ModelAndView confirmStudent(HttpServletRequest request) throws Exception {
        String id = request.getParameter("indexNumber");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String numberOfBuilding = request.getParameter("numberofbuilding");
        String numberOfFlat = request.getParameter("numberofflat");
        String password = request.getParameter("password");

        Student std = studentRepository.findByIndexNumber(Integer.valueOf(id));

        if (userTable.findUserByUsername(username).getUsername() != null && !username.equals(std.getName())) {
            throw new Exception("Username is in use!");
        }

        std.setName(name);
        std.setSurname(surname);
        std.setCity(city);
        std.setStreet(street);
        std.setNumberOfBuilding(numberOfBuilding);
        std.setNumberOfFlat(numberOfFlat);

        userTable.deleteUserByUsername(std.getUsername());
        userTable.insertUser(username, password, "ROLE_STUDENT", "on");
        std.setUsername(username);

        studentRepository.save(std);

        return new ModelAndView("/informlogout.html");
    }

    @RequestMapping(value = "/useroptions/tutor", method = RequestMethod.POST)
    public ModelAndView confirmTutor(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String username = request.getParameter("username");
        String city = request.getParameter("city");
        String street = request.getParameter("street");
        String numberOfBuilding = request.getParameter("numberofbuilding");
        String numberOfFlat = request.getParameter("numberofflat");
        String password = request.getParameter("password");

        Tutor tutor = tutorRepository.findById(Integer.valueOf(id)).get();

        if (userTable.findUserByUsername(username).getUsername() != null && !username.equals(tutor.getName())) {
            throw new Exception("Username is in use!");
        }

        tutor.setName(name);
        tutor.setSurname(surname);
        tutor.setCity(city);
        tutor.setStreet(street);
        tutor.setNumberOfBuilding(numberOfBuilding);
        tutor.setNumberOfFlat(numberOfFlat);

        userTable.deleteUserByUsername(tutor.getUsername());
        userTable.insertUser(username, password, "ROLE_TUTOR", "on");

        tutor.setUsername(username);

        tutorRepository.save(tutor);

        return new ModelAndView("/informlogout.html");
    }

    @RequestMapping(value = "/useroptions/admin", method = RequestMethod.POST)
    public ModelAndView confirmAdmin(HttpServletRequest request) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String oldusername = request.getParameter("oldusername");
        String role = request.getParameter("role");
        String enabled = request.getParameter("enabled");
        log.info("Parameters: " + username + ", " + password + ", " 
                    + role + ", " + enabled);

        if (userTable.findUserByUsername(username).getUsername() != null && !username.equals(oldusername)) {
            throw new Exception("Username is in use!");
        }

        userTable.deleteUserByUsername(oldusername);
        userTable.insertUser(username, password, role, enabled);

        return new ModelAndView("/informlogout.html");
    }

    private ModelAndView studentsOptions(String username) { //student & password
        ModelAndView model = new ModelAndView("student/myAccount.html");
        Optional<Student> student = studentRepository.findByUsername(username);
        deanoffice.noentities.User user = userTable.findUserByUsername(username);
        model.addObject("student", student.get());
        model.addObject("password", user.getPassword());
        return model;
    }

    private ModelAndView tutortsOptions(String username) { //tutor & password
        ModelAndView model = new ModelAndView("tutor/myAccount.html");
        Optional<Tutor> tutor = tutorRepository.findByUsername(username);
        if (tutor.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tutor doesn't exist");
        }
        deanoffice.noentities.User user = userTable.findUserByUsername(username);
        model.addObject("tutor", tutor);
        model.addObject("password", user.getPassword());
        return model;
    }

    private ModelAndView adminsOptions(String username) { //all from users & autorithy table
        ModelAndView model = new ModelAndView("admin/myAccount.html");
        deanoffice.noentities.User user = userTable.findUserByUsername(username);
        model.addObject("user", user);
        return model;
    }

}
