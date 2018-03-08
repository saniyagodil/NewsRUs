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
import java.util.HashSet;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    InterestRepository interestRepository;

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
        Set<String> categories = getCategories(user);
        System.out.print(categories);
        return "redirect:/login";
    }

    @RequestMapping("/mynews")
    public String getUserNews(Model model, Authentication auth){
        User user = userRepository.findByUsername(auth.getName());
        String url = "https://newsapi.org/v2/top-headlines?country=us&category=";
        String key = "&apiKey=bb79a951a7d246de9c757bdeb63d24c5";
        Set<Article> articles = new HashSet<>();
        for(String category : getCategories(user)){
            RestTemplate restTemplate = new RestTemplate();
            String newUrl = url + category + key;
            News news1= restTemplate.getForObject(newUrl, News.class);
            category = category + "articles";
            model.addAttribute(category, news1.getArticles());
        }

        String interestUrl = "https://newsapi.org/v2/top-headlines?country=us&q=";

        for(Interest interest: user.getInterests()){
            RestTemplate restTemplate = new RestTemplate();
            String iUrl = interestUrl + interest.getName() + key;
            News news1= restTemplate.getForObject(iUrl, News.class);
            articles.addAll(news1.getArticles());

        }
        model.addAttribute("interests", articles);
        return "MyNews";
    }

    @GetMapping("/newinterest")
    public String newInterest(Model model){
        Interest interest = new Interest();
        model.addAttribute("interest", interest);
        return "AddInterest";
    }

    @PostMapping("/newinterest")
    public String processInterest(@Valid @ModelAttribute("interest") Interest interest, BindingResult result, Authentication auth){
        if(result.hasErrors()){
            return "AddInterest";
        }
        interestRepository.save(interest);
        User user = userRepository.findByUsername(auth.getName());
        user.addInterest(interest);
        userRepository.save(user);
        return "redirect:/mynews";
    }

    @RequestMapping("/profile")
    public String profile(Model model){

        return "Profile";
    }

    @GetMapping("/categories")
    public String modifyCategories(Model model, Authentication auth){
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("user", user);
        return "Categories";
    }

    @PostMapping("/categories")
    public String processCategories(@Valid @ModelAttribute("user") User user, BindingResult result){
        if(result.hasErrors()){
            return "Categories";
        }
        userRepository.save(user);
        return "redirect:/mynews";
    }

    @RequestMapping("/interests")
    public String viewInterests(Model model, Authentication auth){
        User user = userRepository.findByUsername(auth.getName());
        model.addAttribute("interests", user.getInterests());
        return "Interests";
    }



    public Set<String> getCategories(User user){
        Set<String> categories = new HashSet<>();
        if(user.getBusiness() != null){
            categories.add("business");
        }
//        if(user.getBusiness().equalsIgnoreCase("yes")){
//            categories.add("business");
//        }
        if(user.getTechnology() != null){
            categories.add("technology");
        }
        if(user.getScience() != null){
            categories.add("science");
        }
        if(user.getHealth() != null){
            categories.add("health");
        }
        if(user.getGeneral() != null){
            categories.add("general");
        }
        if(user.getEntertainment() != null){
            categories.add("entertainment");
        }
        if(user.getSports() != null){
            categories.add("sports");
        }
        return categories;
    }

}
