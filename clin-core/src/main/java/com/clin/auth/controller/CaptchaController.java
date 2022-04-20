package com.clin.auth.controller;


import com.clin.core.utils.CaptchaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * <pre>
 * Title :  验证码的控制层
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2021-11-18
 **/

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 生成图像验证码
	 *
	 * @param response 响应对象
	 * @return
	 */
	@GetMapping(path = "/generateImageCaptcha")
	public ResponseEntity<Resource> generateImageCaptcha(HttpServletRequest request) {
		ResponseEntity<Resource> responseEntity = null;
		try {
			String charCaptcha = CaptchaUtils.generateCharCaptcha();
			HttpSession session = request.getSession();
			// 将验证码存入SESSION
			session.setAttribute("verifyCode", charCaptcha);
			byte[] bytes = CaptchaUtils.generateImageCaptcha(charCaptcha);
			Resource resource = new ByteArrayResource(bytes);
			responseEntity = new ResponseEntity<>(resource, CaptchaUtils.getResponseHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			logger.warn(e.toString());
		}
		return responseEntity;
	}
}
