package Java.Insurance.dao;
import java.util.Collection;
import Java.Insurance.entity.*;
import Java.Insurance.exception.PolicyNotFoundException;

public interface IPolicyService {
    boolean createPolicy(Policy policy);
    Policy getPolicy(int policyId) throws PolicyNotFoundException; 
    Collection<Policy> getAllPolicies() throws PolicyNotFoundException; 
    boolean updatePolicy(Policy policy) throws PolicyNotFoundException;
    boolean deletePolicy(int policyId) throws PolicyNotFoundException;
	 
	
}
