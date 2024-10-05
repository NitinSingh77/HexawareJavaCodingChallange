package Java.Insurance.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import Java.Insurance.entity.*;
import Java.Insurance.exception.PolicyNotFoundException;

public class InsuranceServiceImpl implements IPolicyService {
    private Connection conn;

    public InsuranceServiceImpl(Connection conn) {
        this.conn = conn;
    }

    // Create policy
    @Override
    public boolean createPolicy(Policy policy) {
        String query = "INSERT INTO Policy (policyId, policyName, policyType) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, policy.getPolicyId());
            ps.setString(2, policy.getPolicyName());
            ps.setString(3, policy.getPolicyType()); // Assuming you are using policyType
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get policy by ID
    @Override
    public Policy getPolicy(int policyId) throws PolicyNotFoundException {
        try {
            String query = "SELECT * FROM Policy WHERE policyId = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, policyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Policy(
                    rs.getInt("policyId"),
                    rs.getString("policyName"),
                    rs.getString("policyType")
                );
            } else {
                throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all policies
    @Override
    public Collection<Policy> getAllPolicies() {
        Collection<Policy> policies = new ArrayList<>();
        try {
            String query = "SELECT * FROM Policy";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Policy policy = new Policy(
                    rs.getInt("policyId"),
                    rs.getString("policyName"),
                    rs.getString("policyType")
                );
                policies.add(policy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return policies;
    }

    // Update policy
    @Override
    public boolean updatePolicy(Policy policy) throws PolicyNotFoundException {
        try {
            // Check if the policy exists
            Policy existingPolicy = getPolicy(policy.getPolicyId());
            if (existingPolicy == null) {
                throw new PolicyNotFoundException("Policy with ID " + policy.getPolicyId() + " not found.");
            }
            String query = "UPDATE Policy SET policyName = ?, policyType = ? WHERE policyId = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, policy.getPolicyName());
            ps.setString(2, policy.getPolicyType());
            ps.setInt(3, policy.getPolicyId());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (PolicyNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete policy
    @Override
    public boolean deletePolicy(int policyId) throws PolicyNotFoundException {
        try {
            // Check if the policy exists
            Policy existingPolicy = getPolicy(policyId);
            if (existingPolicy == null) {
                throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
            }
            String query = "DELETE FROM Policy WHERE policyId = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, policyId);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (PolicyNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
