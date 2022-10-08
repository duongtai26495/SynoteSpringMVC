package com.duongtai.syndiary.controllers;

import com.duongtai.syndiary.configs.Snippets;
import com.duongtai.syndiary.configs.SortDiary;
import com.duongtai.syndiary.entities.Diary;
import com.duongtai.syndiary.entities.User;
import com.duongtai.syndiary.services.impl.StorageServiceImpl;
import com.duongtai.syndiary.services.impl.UserServiceImpl;
import static com.duongtai.syndiary.configs.MyUserDetail.getUsernameLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private StorageServiceImpl storageService;
    
    @Autowired
    private UserServiceImpl userService;



    @GetMapping("")
    public ModelAndView homePage(ModelMap model, @PathParam("sort") String sort){
        String[] sorts = { Snippets.LAST_EDITED, Snippets.CREATED_AT, Snippets.A_Z,Snippets.Z_A, Snippets.HIDDEN};
        if(sort == null) {
            sort = Snippets.LAST_EDITED;
        }
        List<Diary> sortedDiaries = SortDiary.sortByCondition(userService.findByUsername(getUsernameLogin()).getDiaries(),sort);
       
        model.addAttribute("sorts",sorts);
        model.addAttribute("sorted",sort);
        model.addAttribute(Snippets.TITLE, Snippets.APP_NAME+" "+Snippets.TITLE_HOME_PAGE);
        model.addAttribute("user", userService.getUserByUsername(Objects.requireNonNull(getUsernameLogin())));
        model.addAttribute("diaries", sortedDiaries);
        return new ModelAndView("home",model);
    }
    @GetMapping("authen")
    public ModelAndView createUser (ModelMap model,
                                    @ModelAttribute User user){
        model.addAttribute("user",user);
        model.addAttribute(Snippets.TITLE,Snippets.AUTHENTICATE+" "+Snippets.APP_NAME);
        return new ModelAndView("authen",model);
    }

    @PostMapping("save_user")
    public String save_user(@ModelAttribute User user, ModelMap model){
        if(user != null ){
            if(userService.saveUser(user)!= null){
                System.out.println("New user register: "+user.getUsername());
                return "redirect:/authen?register=true";
            }
        }
        model.addAttribute("user",model);
        return "redirect:/authen?register=false";
    }

    @GetMapping("images/{fileName:.+}")
    public ResponseEntity<byte[]> readFile (@PathVariable String fileName){
        return storageService.readFile(fileName);
    }


}
