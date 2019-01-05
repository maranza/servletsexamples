package tk.xdevcloud.medicalcore.servlets;

import javax.servlet.http.HttpServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import tk.xdevcloud.medicalcore.models.Patient;
import tk.xdevcloud.medicalcore.services.PatientService;
import org.apache.log4j.Logger;
import com.google.gson.*;
import java.util.UUID;
import tk.xdevcloud.medicalcore.exceptions.*;
import javax.validation.*;
import java.util.Set;
import tk.xdevcloud.medicalcore.utils.*;
import tk.xdevcloud.medicalcore.listeners.DBManagerListener;
public class PatientServlet extends HttpServlet {

	private static final long serialVersionUID = -1500805539391979323L;
	private static Logger logger = Logger.getLogger(PatientServlet.class.getName());

	private PatientService patientService = null;

	public void init() {

	}
 
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// if no parameter was provided return all results
		response.setContentType("application/json");
		String uuid = request.getParameter("uuid");
		Gson json = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		try {
			reEstablishConnection();
			if (uuid != null) {

				response.getWriter().println(json.toJson(patientService.getPatient(UUID.fromString(uuid))));
				return;

			} else {

				response.getWriter().println(json.toJson(patientService.getPatients()));
				return;
			}
		} catch (NotFoundException exception) {

			ServletUtil.sendError(exception.getMessage(), response, HttpServletResponse.SC_NOT_FOUND);

		} catch (Exception exception) {

			ServletUtil.sendError("System Error", response, HttpServletResponse.SC_BAD_REQUEST);
			exception.printStackTrace();
			logger.info(exception.getMessage());
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Gson json
	
		response.setContentType("application/json");
		try {
			reEstablishConnection();
			Gson json = new Gson();
			Patient patient = json.fromJson(request.getReader(), Patient.class);
			// validate patient data
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Patient>> constraintViolations = validator.validate(patient);
			logger.info("Constraint errors " + constraintViolations.size());
			if (constraintViolations.size() > 0) {

				String error = constraintViolations.iterator().next().getMessage();
				ServletUtil.sendError(error, response, HttpServletResponse.SC_BAD_REQUEST);
				return;
			}

			String uuid = request.getParameter("uuid");
			// if requestType is update then modify the specific record
			if (uuid == null) {

				if (patientService.add(patient)) {
					ServletUtil.sendResponse("Record Added", response);
					return;

				} else {

					ServletUtil.sendError("Failed to Add Record", response, HttpServletResponse.SC_NOT_FOUND);
					return;
				}
			} else {

				if (patientService.update(patient, UUID.fromString(uuid))) {

					ServletUtil.sendResponse("Record Updated", response);
					return;
				} else {

					ServletUtil.sendError("Failed to update", response, HttpServletResponse.SC_BAD_REQUEST);
					return;
				}

			}

		} catch (JsonSyntaxException exception) {

			ServletUtil.sendError("Failed to Parse Json", response, HttpServletResponse.SC_BAD_REQUEST);

		} catch (NotFoundException exception) {

			ServletUtil.sendError("Record does not exist", response, HttpServletResponse.SC_NOT_FOUND);

		} catch (Exception e) {

			ServletUtil.sendError("System Error", response, HttpServletResponse.SC_BAD_REQUEST);
			e.printStackTrace();
			logger.warn(e.getMessage());
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		String uuid = request.getParameter("uuid");
		try {
			reEstablishConnection();
			if (uuid != null) {

				if (patientService.delete(UUID.fromString(uuid))) {

					ServletUtil.sendResponse("Deleted", response);
					return;
				}

			} else {

				ServletUtil.sendError("Parameter Required", response, HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
		} catch (NotFoundException exception) {

			ServletUtil.sendError("Record Does not Exists", response, HttpServletResponse.SC_NOT_FOUND);
			logger.info(exception.getMessage());

		} catch (Exception exception) {

			ServletUtil.sendError("System Error", response, HttpServletResponse.SC_BAD_REQUEST);
			logger.info(exception.getMessage());
		}

	}
    /**
     * Reconnect the EntityManager connection if it was closed 
     * @throws Exception
     */
	private void reEstablishConnection() throws Exception {

		if (patientService == null) {
            patientService = new PatientService(DBManagerListener.getEntityManager());
		}
		else {
			    patientService.setEntityManager(DBManagerListener.getEntityManager());
			}

	}

}