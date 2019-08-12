package com.xgc.community.controller;

import com.xgc.community.dto.AccessTokenDTO;
import com.xgc.community.dto.GithubUser;
import com.xgc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    /**
     * 当用户单击index页面的登录按钮时，会向GitHub发送一个请求，
     * GitHub接收到请求后，会回调 /callback ,返回一个Code
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,@RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("c330fe18210fca526135");
        accessTokenDTO.setClient_secret("d0bbaf2b071e9f0f047ea3a77867db3e7cf37fd9");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8080/callback");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        System.out.println(user.getBio());
        System.out.println(user.getId());
        return "index";
    }
}
