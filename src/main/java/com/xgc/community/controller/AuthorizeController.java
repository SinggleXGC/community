package com.xgc.community.controller;

import com.xgc.community.dto.AccessTokenDTO;
import com.xgc.community.dto.GithubUser;
import com.xgc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider githubProvider;

    @Value("${github.client.id}")
    String clientID;

    @Value("${github.client.secret}")
    String clientSecret;

    @Value("${github.redirect.uri}")
    String redirectUri;

    /**
     * 当用户单击index页面的登录按钮时，会向GitHub发送一个请求，
     * GitHub接收到请求后，会回调 /callback ,返回一个Code
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,@RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        System.out.println(user.getBio());
        System.out.println(user.getId());
        return "index";
    }
}
