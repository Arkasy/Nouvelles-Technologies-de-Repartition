package com.istv.airbnb.Controller;

import com.istv.airbnb.Model.User;
import com.istv.airbnb.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String home(HttpServletRequest request, Model model) {
        return "index";
    }
}
