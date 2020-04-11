package com.yh.provider;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.yh.dto.AccessTokenDTO;
import com.yh.dto.GithubUser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GitHubProvider {
	public String getAccessToken(AccessTokenDTO accessTokenDTO) {
		MediaType midiaType = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();

		RequestBody body = RequestBody.create(midiaType, JSON.toJSONString(accessTokenDTO));
		Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
		try (Response response = client.newCall(request).execute()) {
			String string = response.body().string();
		    String token=string.split("&")[0].split("=")[1];
		    
			return token;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	public GithubUser getUser(String accessToken) {
		OkHttpClient client = new OkHttpClient();
	
		  Request request = new Request.Builder()
		      .url("https://api.github.com/user?access_token=" + accessToken)		    
		      .build();
		  try (Response response = client.newCall(request).execute()) {
				String string = response.body().string();
				GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
				
				return githubUser;
			} catch (Exception e) {
				// TODO: handle exception
			}
		  return null;
		
	}
}
