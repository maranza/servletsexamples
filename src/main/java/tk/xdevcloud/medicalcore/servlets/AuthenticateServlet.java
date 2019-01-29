package tk.xdevcloud.medicalcore.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import tk.xdevcloud.medicalcore.listeners.DBManagerListener;
import tk.xdevcloud.medicalcore.services.AuthenticateService;
import tk.xdevcloud.medicalcore.utils.SecurityUtil;
import tk.xdevcloud.medicalcore.utils.ServletUtil;

public class AuthenticateServlet extends HttpServlet {

	private static final long serialVersionUID = 4688675195513964816L;
	private static Logger logger = Logger.getLogger(AuthenticateServlet.class.getName());

	public void init() {

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// logout the session
		String logout = request.getParameter("logout");

		if (logout.equals("true")) {
			response.setHeader("token", "");
			response.getWriter().println("{\"success\":true}");
		} else {

			response.getWriter().println("{\"success\":false}");
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");

		Gson json = new Gson();
		String username = null;
		String password = null;
		try {
			AuthenticateService authService = new AuthenticateService(DBManagerListener.getEntityManager());
			JsonObject jsonObject = json.fromJson(request.getReader(), JsonObject.class);

			if (jsonObject.has("username")) {
				username = jsonObject.get("username").getAsString().trim();
			}
			if (jsonObject.has("password")) {
				password = jsonObject.get("password").getAsString().trim();
			}

			if ((username.equals("")) || (username == null)) {
				ServletUtil.sendError("Username is required", response, HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			if ((password.equals("")) || (password == null)) {
				ServletUtil.sendError("Password  is required", response, HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			if (authService.verify(username, password)) {

				String token = SecurityUtil.generateToken(username);
				if (!token.isEmpty()) {
					response.setHeader("token", token);
					response.getWriter().println("{\"success\":true}");
				}

			} else {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().println("{\"success\":false}");
				logger.info("Failed to authenticate ");
			}

		} catch (JWTCreationException exception) {
			ServletUtil.sendError("failed to generate access token", response, HttpServletResponse.SC_BAD_REQUEST);
			logger.info(exception.getMessage());
		} catch (JsonSyntaxException exception) {

			ServletUtil.sendError("Username and Password  required", response, HttpServletResponse.SC_BAD_REQUEST);
			logger.info(exception.getMessage());
		}

		catch (Exception exception) {
			exception.printStackTrace();
			ServletUtil.sendError("System Error", response, HttpServletResponse.SC_BAD_REQUEST);
			logger.info(exception.getMessage());
		}

	}

}
