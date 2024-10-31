package cc.c3q.algo.evolve.fms.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AcrossAjaxFilter implements Filter
{
	public void init(FilterConfig filterConfig) throws ServletException {;}
	public void destroy() {;}
	
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain filterChain) throws IOException, ServletException
	{
		if(!(sRequest instanceof HttpServletRequest && sResponse instanceof HttpServletResponse)) {
			filterChain.doFilter(sRequest, sResponse);
			return;
		}
		HttpServletRequest request = (HttpServletRequest) sRequest;
		HttpServletResponse response = (HttpServletResponse) sResponse;
		
		String origin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", origin==null ? "*" : origin);
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "*");
		response.setHeader("Access-Control-Allow-Headers", "DNT,X-Requested-With,Content-Type");
		response.setHeader("Access-Control-Max-Age", "180");
		
		if("OPTIONS".equals(request.getMethod())) {
			response.setStatus(204);
		} else {
			filterChain.doFilter(sRequest, sResponse);
		}
	}
}
