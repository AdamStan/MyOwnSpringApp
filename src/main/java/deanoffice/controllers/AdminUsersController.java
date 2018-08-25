package deanoffice.controllers;

import deanoffice.noentities.User;
import deanoffice.noentities.UsersTableData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class AdminUsersController {
    @RequestMapping(value = "/admin/allusers", method = RequestMethod.GET)
    public ModelAndView users(){
        ArrayList<User> users = new UsersTableData().getAllUsers();
        ModelAndView model = new ModelAndView("/admin/users.html");
        model.addObject("users", users);
        return model;
    }
    @RequestMapping(value = "/admin/addnewuser", method = RequestMethod.GET)
    public ModelAndView addUser(){
        ModelAndView model = new ModelAndView("/admin/adding/adduser.html");
        return model;
    }

    @RequestMapping(value = "/admin/allusers/deleted", method = RequestMethod.GET)
    public ModelAndView deleteUser(HttpServletRequest request){
        //usun z bazy i wczytaj widok tabelki
        String username = request.getParameter("username");
        System.out.println("Username for delete: " + username);
        new UsersTableData().deleteUserByUsernma(username);
        return this.users();
    }

    @RequestMapping(value = "/admin/allusers/edituser", method = RequestMethod.GET)
    public ModelAndView editUser(HttpServletRequest request){
        String username = request.getParameter("username");
        System.out.println("User for edit: " + username);
        User user = new UsersTableData().findUserByUsername(username);
        ModelAndView model = new ModelAndView("/admin/users.html");
        if(username == null){
            model = new ModelAndView("/admin/adding/adduser.html");
            model.addObject(user);
        }
        return model;
    }

    @RequestMapping(value = "/admin/allusers/confirm", method = RequestMethod.POST)
    public ModelAndView confirmUser(HttpServletRequest request){
        //dodaj do bazy jeszcze
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String enabled = request.getParameter("enabled");
        System.out.println("Parameters: " + username + ", " +
                password + ", " + role + ", " + enabled);
        new UsersTableData().insertUser(username, password, role, enabled);
        return this.users();
    }
}
