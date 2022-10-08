package com.duongtai.syndiary.controllers;

import com.duongtai.syndiary.configs.Snippets;
import com.duongtai.syndiary.configs.SortDiary;
import com.duongtai.syndiary.entities.Diary;
import com.duongtai.syndiary.services.impl.DiaryServiceImpl;
import com.duongtai.syndiary.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;

import static com.duongtai.syndiary.configs.MyUserDetail.getUsernameLogin;

import java.util.List;

@Controller
@RequestMapping("/diary/")
public class DiaryController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private DiaryServiceImpl diaryService;


    @PostMapping("save")
    public ModelAndView save_new (ModelMap model, @ModelAttribute Diary diary){
        if(diary != null){
            diaryService.save_diary(diary);
            return new ModelAndView("redirect:/",model);
        }
        model.addAttribute(diary);
        return new ModelAndView("redirect:/diary/new",model);

    }

    @GetMapping("new")
    public ModelAndView new_diary (ModelMap model, Diary diary){
        model.addAttribute("title", Snippets.NEW_DIARY);
        model.addAttribute("diary",diary);
        return new ModelAndView("/diary/new",model);
    }

    @GetMapping("update/{id}")
    public ModelAndView update_diary(ModelMap model, Diary diary, @PathVariable String id){
        model.addAttribute("diary",diaryService.getById(Long.parseLong(id)));
        model.addAttribute("title","Update diary - "+ diaryService.getById(diary.getId()).getTitle());
        return new ModelAndView("/diary/update");
    }

    @PostMapping("save_update")
    public ModelAndView save_update(ModelMap model, @ModelAttribute Diary diary){
        if(diary!=null){
            diaryService.update_diary(diary);
            model.addAttribute(diary);
            return new ModelAndView("redirect:/diary/update/"+diary.getId()+"?updated=true",model);
        }
        return new ModelAndView("redirect:/diary/update/"+diary.getId()+"?updated=false",model);
    }

    @GetMapping("update_hidden/{id}")
    public ModelAndView update_hidden(ModelMap model, Diary diary, @PathVariable String id){
        diary.setId(Long.parseLong(id));
        diary.setDisplay(false);
        diaryService.update_display(diary);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("update_display/{id}")
    public ModelAndView update_display(ModelMap model, Diary diary, @PathVariable String id){
        diary.setId(Long.parseLong(id));
        diary.setDisplay(true);
        diaryService.update_display(diary);
        return new ModelAndView("redirect:/?sort="+Snippets.HIDDEN);
    }
    
    @GetMapping("update_done")
    public ModelAndView update_done(ModelMap model, Diary diary, @PathParam("id") String id, @PathParam("done") String done) {
    	
    	diary.setDone(Boolean.parseBoolean(done));
    	diary.setId(Long.parseLong(id));
    	diaryService.update_done(diary);
    	return new ModelAndView("redirect:/");
    }
    
    @GetMapping("searching")
    public ModelAndView search(ModelMap model, @PathParam("keyword") String keyword) {
    	List<Diary> sortedDiaries = SortDiary.sortByCondition(diaryService.searchWithKeyword(keyword),Snippets.LAST_EDITED);
        model.addAttribute("diaries", sortedDiaries);
    	return new ModelAndView("/diary/search",model); 
    }
}
