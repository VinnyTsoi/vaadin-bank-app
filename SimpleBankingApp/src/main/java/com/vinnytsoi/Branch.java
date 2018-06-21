package com.vinnytsoi;

import java.util.ArrayList;

public class Branch {
    
    private String branchName;
    private ArrayList<Customer> customerArrayList;

    public Branch (String branchName){
        this.branchName = branchName;
        this.customerArrayList = new ArrayList<Customer>();
    }

    public boolean newCustomer (String customerName, double firstTransaction){
        if (customerExists(customerName) == null){
            Customer newCustomer = new Customer(customerName,firstTransaction);
            this.customerArrayList.add(newCustomer);
            return true;
        }
        else 
            return false;
    }

    public boolean addCustomerTransaction (String customerName, double transaction){
        Customer selectCustomer = customerExists(customerName);
        if (selectCustomer != null){
            selectCustomer.customerTransaction(transaction);
            return true;
        }
        return false;
    }

    public Customer customerExists (String customerName){
        for (int i = 0; i < this.customerArrayList.size(); i++) {
            Customer customer = this.customerArrayList.get(i);
            if (customer.getName().equals(customerName))
                return customer;
            }
                return null;
    }

	public String getBranchName() {
		return branchName;
    }

    public ArrayList<Customer> getCustomerArrayList (){
        return customerArrayList;
    }
    



    

}