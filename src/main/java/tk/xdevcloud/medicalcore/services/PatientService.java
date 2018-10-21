package tk.xdevcloud.medicalcore.services;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import tk.xdevcloud.medicalcore.models.Patient;
import java.util.List;

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
     * @param Integer id
     * @return bool
     * @throws Exception 
     */

    public boolean update(Patient p,Integer id) throws Exception{
    	
    	Patient  patient =  (Patient)entityManager.find(Patient.class,id);
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
     * @param id of the patient to view
     * @return
     * @throws Exception
     */
    public  Patient getPatient(Integer id) throws Exception {
    	
    	Patient patient  = (Patient)entityManager.find(Patient.class,id);
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
    public  boolean delete(Integer uuid) throws Exception {
    	
    	Patient  patient =  (Patient)entityManager.find(Patient.class,uuid);
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
