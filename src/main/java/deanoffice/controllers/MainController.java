package deanoffice.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
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
}
