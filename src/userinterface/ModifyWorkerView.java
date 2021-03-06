package userinterface;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.Worker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class ModifyWorkerView extends View{
    // GUI components
    protected TextField bannerId;
    protected TextField password;
    protected TextField first;
    protected TextField last;
    protected TextField phone;
    protected TextField email;
    protected ComboBox cred;
    protected TextField dOLC;
    protected TextField doh;
    protected ComboBox status;

    protected Button cancelButton;
    protected Button submitButton;

    // For showing error message
    protected MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public ModifyWorkerView(IModel worker)
    {
        super(worker, "ModifyWorkerView");

        // create a container for showing the contents
        VBox container = new VBox(10);
        container.setPadding(new Insets(15, 5, 5, 5));

        // Add a title for this panel
        container.getChildren().add(createTitle());

        // create our GUI components, add them to this Container
        container.getChildren().add(createFormContent());

        container.getChildren().add(createStatusLog("             "));

        getChildren().add(container);

        populateFields();

        // myModel.subscribe("ServiceCharge", this);
        //myModel.subscribe("UpdateStatusMessage", this);
    }


    // Create the title container
    //-------------------------------------------------------------
    private Node createTitle()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);

        Text titleText = new Text(" Modify Worker ");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleText.setWrappingWidth(300);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setFill(Color.DARKGREEN);
        container.getChildren().add(titleText);

        return container;
    }

    // Create the main form content
    //-------------------------------------------------------------
    private VBox createFormContent()
    {
        VBox vbox = new VBox(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text prompt = new Text("WORKER INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

        Text workerBannerId = new Text(" Worker's BannerId : ");
        Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
        workerBannerId.setFont(myFont);
        workerBannerId.setWrappingWidth(150);
        workerBannerId.setTextAlignment(TextAlignment.RIGHT);
        grid.add(workerBannerId, 0, 1);

        bannerId = new TextField();
        bannerId.setEditable(false);
        grid.add(bannerId, 1, 1);

        Text workerPass = new Text(" Worker's Password : ");
        workerPass.setFont(myFont);
        workerPass.setWrappingWidth(150);
        workerPass.setTextAlignment(TextAlignment.RIGHT);
        grid.add(workerPass, 0, 2);

        password = new PasswordField();
        password.setEditable(true);
        grid.add(password, 1, 2);

        Text wFirst = new Text(" Worker's First Name : ");
        wFirst.setFont(myFont);
        wFirst.setWrappingWidth(150);
        wFirst.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wFirst, 0, 3);

        first = new TextField();
        first.setEditable(true);
        grid.add(first, 1, 3);


        Text wLast = new Text(" Worker's Last Name : ");
        wLast.setFont(myFont);
        wLast.setWrappingWidth(150);
        wLast.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wLast, 0, 4);

        last = new TextField();
        last.setEditable(true);
        grid.add(last, 1, 4);

        Text wPhone = new Text(" Worker's Phone Number : ");
        wPhone.setFont(myFont);
        wPhone.setWrappingWidth(150);
        wPhone.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wPhone, 0, 5);

        phone = new TextField();
        phone.setEditable(true);
        grid.add(phone, 1, 5);

        Text wEmail = new Text(" Worker's Email : ");
        wEmail.setFont(myFont);
        wEmail.setWrappingWidth(150);
        wEmail.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wEmail, 0, 6);

        email = new TextField();
        email.setEditable(true);
        grid.add(email, 1, 6);

        Text wCred = new Text(" Worker's Credentials : ");
        wCred.setFont(myFont);
        wCred.setWrappingWidth(150);
        wCred.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wCred, 0, 7);

        cred = new ComboBox();
        cred.getItems().addAll(
                "Ordinary",
                "Administrator"
        );

        cred.setValue("Ordinary");
        grid.add(cred, 1, 7);

        Text wDOLC = new Text(" Worker's Date Of Latest Credentials : ");
        wDOLC.setFont(myFont);
        wCred.setWrappingWidth(150);
        wCred.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wDOLC, 0, 8);

        dOLC = new TextField();
        dOLC.setEditable(true);
        grid.add(dOLC, 1, 8);

        Text wDOH = new Text(" Worker's Date of Hire : ");
        wDOH.setFont(myFont);
        wDOH.setWrappingWidth(150);
        wDOH.setTextAlignment(TextAlignment.RIGHT);
        grid.add(wDOH, 0, 9);

        doh = new TextField();
        doh.setEditable(true);
        grid.add(doh, 1, 9);

        submitButton = new Button("Submit");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                processAction(e);
            }
        });

        cancelButton = new Button("Back");
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                myModel.stateChangeRequest("SelectWorkerView", null);
            }
        });

        HBox buttonCont = new HBox(10);
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(submitButton);
        Label space = new Label("               ");
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(space);
        buttonCont.setAlignment(Pos.CENTER);
        buttonCont.getChildren().add(cancelButton);
        vbox.getChildren().add(grid);
        vbox.getChildren().add(buttonCont);

        return vbox;
    }

    private void processAction(ActionEvent e) {

        clearErrorMessage();

        String ban = bannerId.getText();
        String pass = password.getText();
        String fName = first.getText();
        String lName = last.getText();
        String pho = phone.getText();
        String eml = email.getText();
        String credentials = (String)cred.getValue();
        String latestCred = dOLC.getText();
        String dateHire = doh.getText();
        Worker w = (Worker)myModel.getState("Worker");
        String stat = (String)w.getState("status");

        Properties p1 = new Properties();
        p1.setProperty("bannerId", ban);
        p1.setProperty("password", pass);
        p1.setProperty("firstName", fName);
        p1.setProperty("lastName", lName);
        p1.setProperty("contactPhone", pho);
        p1.setProperty("email", eml);
        p1.setProperty("credentials", credentials);
        p1.setProperty("dateOfLatestCredentialsStatus", latestCred);
        p1.setProperty("dateOfHire", dateHire);
        p1.setProperty("status", stat);

        databaseEdited();

        myModel.stateChangeRequest("insertWorkerModification", p1);
        myModel.stateChangeRequest("done", null);
/*
        bannerId.clear();
        password.clear();
        first.clear();
        last.clear();
        phone.clear();
        email.clear();
        dOLC.clear();
        doh.clear();

 */



    }


    // Create the status log field
    //-------------------------------------------------------------
    protected MessageView createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        Worker w = (Worker)myModel.getState("Worker");

        bannerId.setText((String)w.getState("bannerId"));
        password.setText((String)w.getState("password"));
        first.setText((String)w.getState("firstName"));
        last.setText((String)w.getState("lastName"));
        phone.setText((String)w.getState("contactPhone"));
        email.setText((String)w.getState("email"));
        cred.setValue((String)w.getState("credentials"));
        //dOLC.setText((String)w.getState("dateOfLatestCredentialsStatus"));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        dOLC.setEditable(false);
        dOLC.setText(dtf.format(now));

        doh.setText((String)w.getState("dateOfHire"));
        doh.setEditable(false);

        //status.setValue((String)w.getState("status"));
    }

    /**
     * Update method
     */
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        clearErrorMessage();

        if (key.equals("PopulatePatronMessage") == true)
        {
            displayMessage((String)value);
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Display info message
     */
    //----------------------------------------------------------
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

    public void databaseEdited(){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Database");
        alert.setHeaderText("Your request was complete!");
        alert.setContentText("Your changes were saved and sent to the database.");

        alert.showAndWait();
    }

}


//---------------------------------------------------------------
//	Revision History:
//
