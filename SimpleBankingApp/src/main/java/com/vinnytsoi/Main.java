package com.vinnytsoi;

public class Main {

    public static void main(String [] args){

    Bank kbcBank = new Bank("KBC Bank");

    kbcBank.addBranch("Dundrum");
    kbcBank.addBranch("Naas");

    if (!kbcBank.addCustomerToBranch("Dundrum", "Victor", 99.00))
        System.out.println("Error adding new customer");

    if (!kbcBank.addCustomerToBranch("Dundrum", "James", 9.00))  
        System.out.println("Error adding new customer");

    if (!kbcBank.addCustomerToBranch("undrum", "Franco", 909.00))
        System.out.println("Error adding new customer");

    kbcBank.addCustomerTransaction("Dundrum", "Victor", 200.00);

    kbcBank.listCustomers("Dundrum");

    kbcBank.listCustomers("Naas");

    kbcBank.listCustomerTransactions("Dundrum", "Victor");





    

    

    

        
    
        

        // dundrumBranch.newCustomer("Vincent & Lorraine", 205000.00);
        // dundrumBranch.newCustomer("Vincent", 205.00);
        // dundrumBranch.newCustomer("Lorraine", 5000.00);

    }
}