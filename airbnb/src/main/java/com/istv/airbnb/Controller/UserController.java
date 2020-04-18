package com.istv.airbnb.Controller;

import com.istv.airbnb.Model.User;
import com.istv.airbnb.Repository.LocationRepository;
import com.istv.airbnb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping(path="/registration")
    public String getRegistrationForm(){
        return "user/registration";
    }

    @RequestMapping(path="/registration", method = RequestMethod.POST)
    public String saveUserForm(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, @RequestParam int bankAccount, Model model){
        User userExist = userRepository.findByUsername(username);
        if(userExist!=null) {
            model.addAttribute("username", "already_taken");
            return "user/registration";
        }
        if(!password.equals(confirmPassword)){
            model.addAttribute("passwordConfirm", "password_not_equals");
            return "user/registration";
        }

        User newUser = new User(username,firstname,lastname,passwordEncoder.encode(password),email, bankAccount);
        userRepository.save(newUser);
        return "redirect:/";
    }

    @GetMapping(path = "/profile")
    public String show(HttpServletRequest request, Model model) {
        model.addAttribute("list_location", locationRepository.findAllByOwner((User) request.getSession().getAttribute("current_user")));
        return "user/show";
    }


}
