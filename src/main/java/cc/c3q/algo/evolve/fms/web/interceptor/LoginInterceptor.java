package cc.c3q.algo.evolve.fms.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import cc.c3q.algo.evolve.fms.web.session.UserSession;

@Component
public class LoginInterceptor implements HandlerInterceptor
{
	@Autowired
	public UserSession userSession;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
	{
		Integer id_user = userSession.getUserId(request.getSession());
		if(id_user != null) return true;
		
		response.setStatus(401);
		return false;
	}
}
