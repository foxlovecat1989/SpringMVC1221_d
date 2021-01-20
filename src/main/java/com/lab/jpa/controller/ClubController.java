package com.lab.jpa.controller;

import com.lab.jpa.entities.Club;
import com.lab.jpa.repository.CompanyDao;
import com.lab.jpa.validation.ClubValidation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/club")
public class ClubController {
    
    @Autowired
    private CompanyDao dao;
    
    @Autowired
    private ClubValidation clubValidation;
    
    @RequestMapping(value = {"/"} ,method = {RequestMethod.GET})
    public String read(Model model) {
        List club_list = dao.queryAllClubs();
        Club club = new Club();
        model.addAttribute("club_list", club_list);
        model.addAttribute("club", club);
        return "club_page";
    }
    
    @RequestMapping(value = {"/"} ,method = {RequestMethod.POST})
    public String create(@ModelAttribute("club") Club club, 
                         BindingResult result, 
                         Model model) {
        // 數據驗證 
        clubValidation.validate(club, result); // result 存放驗證後的結果
        if(result.hasErrors()) {
            model.addAttribute("_method", "POST");
            model.addAttribute("club_list", dao.queryAllClubs());
            model.addAttribute("club", club);
            return "club_page"; 
        }
        dao.saveClub(club);
        return "redirect: ./";
    }
    
}
