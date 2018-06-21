package com.vinnytsoi;

import java.util.ArrayList;

public class Customer {

    private String name;
    private ArrayList<Double> transactionsList;

    public Customer (String name, double firstTransaction) {
        this.name = name;
        
        this.transactionsList = new ArrayList<Double>();
        customerTransaction(firstTransaction);
    }

    public void customerTransaction (double transactionAmount){
        this.transactionsList.add(transactionAmount);
    }

    public ArrayList<Double> getTransactionsList (){
        return this.transactionsList;
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

}
