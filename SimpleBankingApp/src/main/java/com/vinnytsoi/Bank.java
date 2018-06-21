package com.vinnytsoi;

import java.util.ArrayList;


public class Bank {
    
    private String bankName;
    private ArrayList<Branch> branchArrayList;

    public Bank(String bankName){
        this.bankName = bankName;
        this.branchArrayList = new ArrayList<Branch>();
    }

    public boolean addBranch(String branchName){
        if (branchExists(branchName) == null){
            this.branchArrayList.add(new Branch(branchName));
            return true;
        }
        return false;
    }

    public boolean addCustomerToBranch(String branchName, String customerName, double initialTransaction){
        Branch currentBranch = branchExists(branchName);
        if (currentBranch != null) {
            return currentBranch.newCustomer(customerName, initialTransaction);
        }
        return false;
    }

    public boolean addCustomerTransaction(String branchName, String customerName, double transaction){
        Branch currentBranch = branchExists(branchName);
        if (currentBranch != null) {
            return currentBranch.addCustomerTransaction(customerName, transaction);
        }
        return false;
    }
    
    public ArrayList<Customer> listCustomers(String branchName){
            Branch currentBranch = branchExists(branchName);
            if (currentBranch != null) {
            return currentBranch.getCustomerArrayList();
        }
            return null;
    }

    public ArrayList<Double> listCustomerTransactions (String branchName, String customerName){
        Branch currentBranch = branchExists(branchName);
        if (currentBranch != null) {
            Customer currentCustomer = currentBranch.customerExists(customerName);
            if (currentCustomer != null){
                return currentCustomer.getTransactionsList();
                // ArrayList<Double> currentCustomerTransactioList = currentCustomer.getTransactionsList();
                // for (int i = 0; i < currentCustomerTransactioList.size(); i++){
                //     System.out.println(currentCustomerTransactioList.get(i));
                }
            }
            return null;
    }

    private Branch branchExists (String branchName){   
        for (int i = 0; i < this.branchArrayList.size(); i++){
            Branch currentBranch = this.branchArrayList.get(i);
            if (currentBranch.getBranchName().equals(branchName))
                return currentBranch;
        }
        return null;
    }

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return this.bankName;
    }
    
    public ArrayList<Branch> getBranchArrayList() {
        return this.branchArrayList;
    }


    
}