package com.yh.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yh.dto.AccessTokenDTO;
import com.yh.dto.GithubUser;
import com.yh.provider.GitHubProvider;

@Controller
public class AuthorizeController {
	@Autowired
	private GitHubProvider gitHubProvider;
	
	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String clientSecret;
	@Value("${github.redirect.uri}")
	private String redirectUri;
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state, HttpServletRequest request) {
		AccessTokenDTO accessTokenDTO =  new AccessTokenDTO();
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(clientSecret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setState(state);
		String accessToken =gitHubProvider.getAccessToken(accessTokenDTO);
	    GithubUser user = gitHubProvider.getUser(accessToken);
	    System.out.println(user.getName());
	    
	    if(user!=null) {
	    	//登录成功，写cookie和session
	    	request.getSession().setAttribute("user", user);
	    	return "redirect:/";
	    }else {
	    	return "redirect:/";
	    }
	  
	
	}
}
