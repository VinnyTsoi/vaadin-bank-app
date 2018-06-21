package com.vinnytsoi;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Grid.SelectionMode;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    static String transactionString = "";
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {

        Bank bank = new Bank("Bank of Coders");
        
        final VerticalLayout layout = new VerticalLayout();
        final HorizontalLayout h1Layout = new HorizontalLayout();
        final HorizontalLayout h2Layout = new HorizontalLayout();
        final HorizontalLayout h3Layout = new HorizontalLayout();

        Label bankLabel = new Label ("<h2>Bank of Coders</h2>",ContentMode.HTML);

        TextField newBranchTextField = new TextField("Branch");

        Button branchButton = new Button("Add Branch");

        Label spaceLabel1 = new Label(" ");
        Label spaceLabel2 = new Label(" ");

        Label branchLabel = new Label("",ContentMode.HTML);

        Grid<Branch> branchGrid = new Grid<>();
        branchGrid.setItems(bank.getBranchArrayList());
        branchGrid.addColumn(Branch::getBranchName).setCaption("Branch List");
        //branchGrid.setHeightMode(HeightMode.ROW);
        //branchGrid.setHeightByRows(bank.getBranchArrayList().size());
        branchGrid.setSelectionMode(SelectionMode.SINGLE);
        SingleSelect<Branch> selection = branchGrid.asSingleSelect();

        branchGrid.addSelectionListener(event -> {
                Notification.show(selection.getValue().getBranchName() + " selected");
            }
        );
                    
        Button branchSelectionBtn = new Button("List customers");

        branchSelectionBtn.addClickListener(e -> {
            // Create a sub-window and set the content
            Window subWindow = new Window("Customer List Sub-window");
            VerticalLayout subContent = new VerticalLayout();
            subWindow.setContent(subContent);

            // Put some components in it
            Grid <Customer> customerGrid = new Grid<>();
            customerGrid.setItems(bank.listCustomers(selection.getValue().getBranchName()));
            customerGrid.addColumn(Customer::getName).setCaption(selection.getValue().getBranchName() + " customers");
            customerGrid.setSelectionMode(SelectionMode.SINGLE);
            SingleSelect<Customer> selection1 = customerGrid.asSingleSelect();
            customerGrid.addSelectionListener(event -> {
                Notification.show(selection1.getValue().getName() + " selected");
            }
        );

            Button transactionsButton = new Button("List Transactions");

            transactionsButton.addClickListener(event -> {
                    transactionString = "";
                    // Create a sub-window and set the content
                    Window subWindow2 = new Window("Transaction List Sub-window");
                    VerticalLayout subContent2 = new VerticalLayout();
                    subWindow2.setContent(subContent2);

                    // Put some components in it
                    Label transactionLabel = new Label();
                    
                    ArrayList<Double> currentTransactionLabelArrayList = bank.listCustomerTransactions(selection.getValue().getBranchName(), selection1.getValue().getName());
                    

                    for (int i = 0; i < currentTransactionLabelArrayList.size(); i++){
                        transactionString += "€" + (String.format( "%.2f",currentTransactionLabelArrayList.get(i))).toString() + ", \n";
                    }

                    transactionLabel.setValue(transactionString);
                    
                    
                    subContent2.addComponents(transactionLabel);
                    
                    // Center it in the browser window
                    subWindow2.center();

                    // Open it in the UI
                    addWindow(subWindow2);
                }
            );

            subContent.addComponents(customerGrid,transactionsButton);
            
            // Center it in the browser window
            subWindow.center();

            // Open it in the UI
            addWindow(subWindow);

            }
        );

        branchButton.addClickListener(e -> {
                if(newBranchTextField.isEmpty()){
                    branchLabel.setValue("<strong>Please enter a new branch</strong>");
                    return;
                }
                if(!bank.addBranch(newBranchTextField.getValue())){
                    branchLabel.setValue("<strong>Branch already exists!</strong>"); 
                }
                else {
                    Notification.show(newBranchTextField.getValue() + " added");
                    branchLabel.setValue("");
                    // branchLabel.setValue(newBranchTextField.getValue() + " added");
                    branchGrid.getDataProvider().refreshAll();  
                }
            }
        );

        TextField branchTextField = new TextField("Branch");
        TextField customerTextField = new TextField("Customer Name");
        TextField transactionTextField = new TextField("Transaction Amount");

        Button newCustomerButton = new Button("Add new customer");
        Button transactionButton = new Button("Add customer transaction");

        Label customerLabel = new Label("",ContentMode.HTML);

        newCustomerButton.addClickListener(e -> {
                if ((branchTextField.isEmpty()) || (customerTextField.isEmpty()) || (transactionTextField.isEmpty())) {
                    customerLabel.setValue("<strong>Please complete text fields</strong>");
                    return;
                }
                if(!bank.addCustomerToBranch(branchTextField.getValue(), customerTextField.getValue(), Double.parseDouble(transactionTextField.getValue()))){
                    customerLabel.setValue("<strong>Branch does not exist or customer already exists.</strong>");
                }
                else {
                    Notification.show("New customer added");
                    customerLabel.setValue("");
                    // customerLabel.setValue("New customer added");
                }
            }
        );

        transactionButton.addClickListener(e -> {
                if (!bank.addCustomerTransaction(branchTextField.getValue(), customerTextField.getValue(), Double.parseDouble(transactionTextField.getValue()))){
                    customerLabel.setValue("<strong>Error: Branch does not exist or customer already exists.</strong>");
                } else {
                    Notification.show("Transaction successful");
                    customerLabel.setValue("");
                    // customerLabel.setValue("Transaction successful");
                }
            }
        );


        h1Layout.addComponents(newBranchTextField, branchLabel);
        h2Layout.addComponents(branchTextField,customerTextField,transactionTextField,customerLabel);
        h3Layout.addComponents(newCustomerButton,transactionButton);

        layout.addComponents(bankLabel,h1Layout);
        layout.addComponents(branchButton,spaceLabel1);
        layout.addComponents(h2Layout,h3Layout);

        layout.addComponents(spaceLabel2, branchGrid, branchSelectionBtn);

        setContent(layout);
                
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
