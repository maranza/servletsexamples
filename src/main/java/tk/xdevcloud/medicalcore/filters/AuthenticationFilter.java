package tk.xdevcloud.medicalcore.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.exceptions.JWTVerificationException;

import javax.servlet.http.HttpServletRequest;
import tk.xdevcloud.medicalcore.utils.*;

public class AuthenticationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException, JWTVerificationException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String token = httpRequest.getHeader("token");

		if (token == null) {
			response.setContentType("application/json");
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			ServletUtil.sendError("Access Denied", httpResponse, HttpServletResponse.SC_FORBIDDEN);
			return;
		}else {
			if (!SecurityUtil.validateToken(token)) {
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				ServletUtil.sendError("Invalid token", httpResponse, HttpServletResponse.SC_FORBIDDEN);
				return;
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

}
