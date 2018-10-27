package tk.xdevcloud.medicalcore.servlets;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import java.io.IOException;
import tk.xdevcloud.medicalcore.models.Patient;
import tk.xdevcloud.medicalcore.services.PatientService;
import org.apache.log4j.Logger;
import com.google.gson.*;
import javax.persistence.EntityManager;
import java.util.UUID;

public class PatientServlet extends HttpServlet {

	private static final long serialVersionUID = -1500805539391979323L;
	private static Logger logger = Logger.getLogger(PatientServlet.class.getName());
	private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("medicaldb");
	EntityManager manager = emFactory.createEntityManager();
	private PatientService service = new PatientService(manager);

	public void init() {

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// if no parameter was provided return all results
		response.setContentType("application/json");
		String uuid = request.getParameter("uuid");
		Gson json = new Gson();
		try {
			if (uuid != null) {

				response.getWriter().println((json.toJson(service.getPatient(UUID.fromString(uuid)))));

			} else {

				response.getWriter().println(json.toJson(service.getPatients()));
			}
		} catch (Exception exception) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("{\"msg\":\"Exception thrown\"}");
			logger.info(exception.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Gson json
		response.setContentType("application/json");
		try {
			
			Gson json = new Gson();
			JsonObject jsonObject = json.fromJson(request.getReader(), JsonObject.class);
			
			String firstName = jsonObject.get("firstName").getAsString();
			String lastName = jsonObject.get("lastName").getAsString();
			String IdNumber = jsonObject.get("IdNumber").getAsString();

			Patient patient = new Patient();
			patient.setFirstName(firstName);
			patient.setLastName(lastName);
			patient.setIdNumber(IdNumber);
			String uuid = request.getParameter("uuid");
			// if requestType is update then modify the specific record
			if (uuid == null ) {

				if (service.add(patient)) {
					response.getWriter().println("{\"msg\":\"Captured\"}");

				} else {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().println("{\"msg\":\"Failed to Capture Record\"}");

				}
			} else {
				
				if (service.update(patient, UUID.fromString(uuid))) {

					response.getWriter().println("{\"msg\":\"Updated\"}");
				} else {
					
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					response.getWriter().println("{\"msg\":\"Failed to Update\"}");
				}

			}
			

		} catch (JsonSyntaxException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("{\"msg\" : \"Failed to Parse Json\"}");

		} catch (Exception e) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
			response.getWriter().println("{\"msg\" : \"System Error Try Again\"}");
			logger.warn(e.getMessage());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json");
		String uuid = request.getParameter("uuid");
		try {
			if (uuid != null) {

				if (service.delete(UUID.fromString(uuid))) {

					response.getWriter().println("{\"msg\":\"Deleted\"}");
				}

			}
			else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().println("{\"msg\":\"Parameter required\"}");
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().println("{\"msg\":\"System Error \"}");
			logger.info(e.getMessage());
		}

	}

}