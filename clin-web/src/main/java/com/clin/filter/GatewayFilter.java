package com.clin.filter;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-18
 **/
@WebFilter(filterName ="GatewayFilter",urlPatterns = {"/*"})
public class GatewayFilter  implements Filter {

    private Map<String,HashSet> CODEMAP=new HashMap<>();

    public  void init(FilterConfig filterConfig) throws  ServletException {
        ServletContext servletContext =filterConfig.getServletContext();
        WebApplicationContext cxt= WebApplicationContextUtils.getWebApplicationContext(servletContext);
        String managerRule=cxt.getEnvironment().getProperty("web.rule.managerRule");
        String agentRule=cxt.getEnvironment().getProperty("web.rule.agentRule");
        String volunteerRule=cxt.getEnvironment().getProperty("web.rule.volunteerRule");
        HashSet<String> ROLE_MANAGER = new HashSet<>(Arrays.asList(managerRule.split(",")));
        HashSet<String> ROLE_AGENT = new HashSet<>(Arrays.asList(agentRule.split(",")));
        HashSet<String> ROLE_VOLUNTEER = new HashSet<>(Arrays.asList(volunteerRule.split(",")));
        CODEMAP.put("ROLE_MANAGER",ROLE_MANAGER);
        CODEMAP.put("ROLE_AGENT",ROLE_AGENT);
        CODEMAP.put("ROLE_VOLUNTEER",ROLE_VOLUNTEER);
     }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;
        String roleCode = httpServletRequest.getHeader("RoleCode");
        String url =httpServletRequest.getRequestURI();
        if(StrUtil.isNotBlank(roleCode)&&url.indexOf("/captcha")==-1&&url.indexOf("/oauth")==-1&&url.indexOf("/file")==-1&&url.indexOf("/data")==-1){
            if(StrUtil.equals(roleCode,"ROLE_EAC_ADMIN")){
                filterChain.doFilter(servletRequest,servletResponse);
            }else if(StrUtil.equals(roleCode,"ROLE_MANAGER")&&getIf("ROLE_MANAGER",url)){
                filterChain.doFilter(servletRequest,servletResponse);
            }else if(StrUtil.equals(roleCode,"ROLE_AGENT")&&getIf("ROLE_AGENT",url)){
            filterChain.doFilter(servletRequest,servletResponse);
            }else if(StrUtil.equals(roleCode,"ROLE_VOLUNTEER")&&getIf("ROLE_VOLUNTEER",url)){
                filterChain.doFilter(servletRequest,servletResponse);
            }else{
                HttpServletResponse httpServletResponse=(HttpServletResponse)servletResponse;
                httpServletResponse.setStatus(401);
            }
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    public  boolean  getIf(String code,String url){
        HashSet<String> urlSet= new HashSet<>(Arrays.asList(url.split("/")));
        urlSet.retainAll(CODEMAP.get(code));
        return urlSet.size()>0;
    }
}
