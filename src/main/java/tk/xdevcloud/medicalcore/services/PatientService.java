package tk.xdevcloud.medicalcore.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import tk.xdevcloud.medicalcore.models.Patient;
import java.util.List;
import java.util.UUID;
public class PatientService {

    
    private EntityManager entityManager;
    public PatientService(EntityManager entityManager) {
    	
    	   this.entityManager = entityManager;
    }
    /**
     * 
     * @param patient
     * @return
     */
    public boolean add(Patient patient) {
    	
        EntityTransaction tx = null;
        try {
            tx = entityManager.getTransaction();
            tx.begin();
            entityManager.persist(patient);
            tx.commit();
        } catch (Exception e) {

            tx.rollback();
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @param Patient p
     * @param UUID uuid
     * @return bool
     * @throws Exception 
     */

    public boolean update(Patient p,UUID uuid) throws Exception{
    	
    	Patient  patient = (Patient)entityManager.createQuery("SELECT p FROM Patient p WHERE p.uuid = :uuid").setParameter("uuid", uuid).getSingleResult();
    	 if(patient == null) {
             
         	throw new Exception("No Record found by that id");
         }
    	 EntityTransaction tx = null;
    	 try 
    	 {    
    	      tx = entityManager.getTransaction();
    	      tx.begin();
    	      patient.setFirstName(p.getFirstName());
    	      patient.setLastName(p.getLastName());
    	      patient.setIdNumber(p.getIdNumber());
    	      tx.commit();
    	 
    	 }
    	 catch(Exception e ) {
    		 
    		 tx.rollback();
    		 return false;
    	 }
    	 
    	return true;
    }
    /**
     * Gets a specific patient record
     * @param UUID uuid of the patient to view
     * @return
     * @throws Exception
     */
    public  Patient getPatient(UUID uuid) throws Exception {
    	
    	Patient patient  = (Patient)entityManager.createQuery("SELECT p FROM Patient p WHERE p.uuid = :uuid").setParameter("uuid", uuid).getSingleResult();
        if(patient == null) {
               
        	throw new Exception("No Record found by that id");
        }
        return patient;
    }
    
    
    @SuppressWarnings("unchecked")
	/**
	 * returns a list of patients
	 * @return List
	 */
    public  List<Patient> getPatients() {

        return (List<Patient>)entityManager.createQuery("SELECT p FROM Patient p").getResultList();

    }
  
    /**
     * delete a specific patient
     * @param uuid id of the specific patient
     * @return bool
     */
    public  boolean delete(UUID uuid) throws Exception {
    	
    	Patient  patient =  (Patient)entityManager.createQuery("SELECT p FROM Patient p WHERE p.uuid = :uuid").setParameter("uuid", uuid).getSingleResult();
    	 if(patient == null) {
             
         	throw new Exception("No Record found by that id");
         }
    	 EntityTransaction tx = entityManager.getTransaction();
    	 try 
    	 {    
    	      tx.begin();
    	      entityManager.remove(patient);
    	      tx.commit();
    	 
    	 }
    	 catch(Exception e ) {
    		 
    		 tx.rollback();
    	 }
    	 
    	return true;
    }

}
