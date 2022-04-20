package com.clin.auth.utils;

import com.clin.core.provider.CurrentUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * Title :  当前用户信息工具类
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-01-13
 **/
public class CurrentUserUtils {

	private CurrentUserUtils() {

	}

	public static Map<String, String> getOAuth2AuthenticationDetailsInfo() {
		Map<String, String> oauth2AuthenticationMap = new HashMap<>();
		OAuth2Authentication oauth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) oauth2Authentication
				.getUserAuthentication();
		CurrentUser currentUser=(CurrentUser) usernamePasswordAuthenticationToken.getPrincipal();//(CurrentUser) principal.get("currentUser");
		oauth2AuthenticationMap=currentUser.getCurrentUser();
		oauth2AuthenticationMap.put("name",currentUser.getUsername());
//		oauth2AuthenticationMap.put("role_code",currentUser.getUsername());
		return oauth2AuthenticationMap;
	}


}
