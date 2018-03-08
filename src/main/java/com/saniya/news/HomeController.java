package com.saniya.news;

import com.saniya.news.Security.RoleRepository;
import com.saniya.news.Security.User;
import com.saniya.news.Security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping("/")
    public String getHome(Model model){
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=bb79a951a7d246de9c757bdeb63d24c5";
        News news= restTemplate.getForObject(url, News.class);
        model.addAttribute("articles", news.getArticles());
        return "Home";
    }

    @RequestMapping("/login")
    public String login(){
        return "Login";
    }


    @GetMapping("/newuser")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        return "NewUser";
    }

    @PostMapping("/newuser")
    public String processUser(@Valid @ModelAttribute("user") User user, BindingResult result){
        if(result.hasErrors()){
            return "NewUser";
        }
        user.addRole(roleRepository.findRoleByRoleName("USER"));
        userRepository.save(user);
        return "redirect:/login";
    }

    @RequestMapping("/mynews")
    public String getUserNews(Model model, Authentication auth){
        User user = userRepository.findByUsername(auth.getName());
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=bb79a951a7d246de9c757bdeb63d24c5";
//        News news= restTemplate.getForObject(url, News.class);
//        model.addAttribute("articles", news.getArticles());

        return "Profile";
    }



}
