package com.xgc.community.provider;

import com.alibaba.fastjson.JSON;
import com.xgc.community.dto.AccessTokenDTO;
import com.xgc.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

/**
 * 实现GitHub第三方登录功能的提供者
 */
@Component
public class GithubProvider {

    /**
     * 获取AccessToken
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            String accessToken = splitStringToAccessToken(string);
            return accessToken;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对返回的参数进行分割，获取AccessToken
     * @param string
     * @return
     */
    private String splitStringToAccessToken(String string){
        return string.split("&")[0].split("=")[1];
    }

    /**
     * 获取用户信息
     */
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try{
            Response response  = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
            return githubUser;
        }catch (Exception e){
        }
        return null;
    }
}
