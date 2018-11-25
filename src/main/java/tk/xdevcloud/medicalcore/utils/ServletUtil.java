package tk.xdevcloud.medicalcore.utils;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.*;
public final class ServletUtil {
	
	/**
	 * Custom Http Error Response
	 * @param message 
	 * @param response 
	 * @param status
	 * @throws IOException
	 */
	public static void sendError(String message,HttpServletResponse response,Integer status) throws IOException {
		
		response.setStatus(status);
		response.getWriter().println(String.format("{\"msg\":\"%s\"}",message));
		
	}
	/**
	 * Send Response the user
	 * @param message
	 * 
	 * @param response
	 * @throws IOException
	 */
	public static void sendResponse(String message,HttpServletResponse response) throws IOException{
		
		response.getWriter().println(String.format("{\"msg\":\"%s\"}",message));
		
	}
	
	
	


}
