package com.xgc.community.controller;

import com.xgc.community.dto.QuestionDTO;
import com.xgc.community.mapper.QuestionMapper;
import com.xgc.community.mapper.UserMapper;
import com.xgc.community.model.User;
import com.xgc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return "index";
        }

        for (Cookie cookie : cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user != null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        List<QuestionDTO> questionDTOList = questionService.list();
        model.addAttribute("questions",questionDTOList);

        return "index";
    }
}
