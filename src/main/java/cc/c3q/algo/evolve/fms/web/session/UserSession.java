package cc.c3q.algo.evolve.fms.web.session;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class UserSession
{
	protected String sessionCurrentUserId = this.getClass().getName()+".currentUserId";
	
	public Integer getUserId(HttpSession session)
	{
		return (Integer) session.getAttribute(sessionCurrentUserId);
	}
	public void setUserId(HttpSession session, Integer id)
	{
		session.setAttribute(sessionCurrentUserId, id);
	}
}
