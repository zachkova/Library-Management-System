package model;
import exception.InvalidPrimaryKeyException;
import java.util.Properties;
import java.sql.SQLException;
import java.util.*;


public class Rental extends EntityBase {

    private static final String myTableName = "Rental";
    protected Properties dependencies;
    private String updateStatusMessage = "";
    private boolean exists = true;

    public Rental(String barcode) throws InvalidPrimaryKeyException {
        super(myTableName);
        this.setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE bookId = " + barcode + " and checkinDate IS NULL";
        Vector allDataFromDB = this.getSelectQueryResult(query);
        System.out.println("This didnt fail yet 1");
        if (allDataFromDB != null) {
            System.out.println("This didn't fail yet 2");
            int dataLen = allDataFromDB.size();
            if (dataLen != 1) {
                System.out.println("This failed 4: " + dataLen);
                throw new InvalidPrimaryKeyException("Multiple rentals matching id : " + barcode + " found.");
            } else {
                Properties bookData = (Properties) allDataFromDB.elementAt(0);
                this.persistentState = new Properties();
                Enumeration bookKeys = bookData.propertyNames();

                while (bookKeys.hasMoreElements()) {
                    String nextKey = (String) bookKeys.nextElement();
                    String nextValue = bookData.getProperty(nextKey);
                    if (nextValue != null) {
                        this.persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        else {
            System.out.println("This failed here 3");
            throw new InvalidPrimaryKeyException("No Books matching: " + barcode + " found.");
        }
        exists = true;
    }

    public Rental(Properties props) {
        super(myTableName);
        this.setDependencies();
        this.persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();

        while(allKeys.hasMoreElements()) {
            String one = (String)allKeys.nextElement();
            String two = props.getProperty(one);
            if (two != null) {
                this.persistentState.setProperty(one, two);
            }
        }
        exists = false;
    }

    public Rental() {

    }

    public void setExistsTrue()
    {
        exists = true;
    }

    private void setDependencies(){
        this.dependencies = new Properties();
        this.myRegistry.setDependencies(this.dependencies);
    }

    @Override
    public Object getState(String key) {
        return persistentState.getProperty(key);
    }

    @Override
    public void stateChangeRequest(String key, Object value) {

    }

    public String toString()
    {
        return "Book: barcode: " + getState("barcode") + " Title: " + getState("title");
    }

    public static int compare(Rental a, Rental b) {
        String ba = (String)a.getState("id");
        String bb = (String)b.getState("id");
        return ba.compareTo(bb);
    }


    //-----------------------------------------------------------------------------------
    public void update()
    {
        updateStateInDatabase();
    }

    //-----------------------------------------------------------------------------------
    private void updateStateInDatabase()
    {

        try
        {
            if (exists == true)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("id", persistentState.getProperty("id"));
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "Book data for book barcode number : " + persistentState.getProperty("barcode") + " updated successfully in database!";
            }
            else
            {
                insertPersistentState(mySchema, persistentState);
                updateStatusMessage = "Book data for new Book : " +  persistentState.getProperty("barcode")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error: " + ex.toString());
            updateStatusMessage = "Error in installing Patron data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }

    @Override
    //------------------------------------------------------------------------------------
    protected void initializeSchema(String tableName) {

        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public Vector<String> getEntryListView()
    {
        Vector<String> v = new Vector<String>();

        v.addElement(persistentState.getProperty("barcode"));
        v.addElement(persistentState.getProperty("title"));
        v.addElement(persistentState.getProperty("discipline"));
        v.addElement(persistentState.getProperty("author1"));
        v.addElement(persistentState.getProperty("author2"));
        v.addElement(persistentState.getProperty("author3"));
        v.addElement(persistentState.getProperty("author4"));
        v.addElement(persistentState.getProperty("publisher"));
        v.addElement(persistentState.getProperty("yearOfPublication"));
        v.addElement(persistentState.getProperty("isbn"));
        v.addElement(persistentState.getProperty("condition"));
        v.addElement(persistentState.getProperty("suggestedPrice"));
        v.addElement(persistentState.getProperty("notes"));
        v.addElement(persistentState.getProperty("status"));

        return v;
    }

}
