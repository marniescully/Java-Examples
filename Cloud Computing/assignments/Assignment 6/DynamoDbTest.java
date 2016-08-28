package cscie90.dynamodb;

import java.util.ArrayList;
import java.util.Iterator;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

// HW6 Problem 5 Marnie Scully

public class DynamoDbTest {

	private ProfileCredentialsProvider credentials;
	// specify your AWS user profile name - or 'default' if no other profile is
	// configured
	private String awsProfileName = "marina_dynamodb_useast";

	private DynamoDB dynamoDB;
	private String tableName = "MY_PETS";
	private Table table;

	public DynamoDbTest() {
		super();
		credentials = new ProfileCredentialsProvider(awsProfileName);
		dynamoDB = new DynamoDB(new AmazonDynamoDBClient(credentials));
		System.out.println("Connected to DynamoDB OK ");
	}

	private void createTable() {
		// check if the table is already created
		table = dynamoDB.getTable(tableName);
		if (table != null) {
			TableDescription tableDescription = table.getDescription();
			if (tableDescription != null){
				System.out.println("Table is already created: " + tableName);
				this.deleteTable();
			} 
		}
		ArrayList<AttributeDefinition> attributeDefinitions= new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("ID").withAttributeType("S"));

		ArrayList<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
		keySchema.add(new KeySchemaElement().withAttributeName("ID").withKeyType(KeyType.HASH));
		        
		CreateTableRequest request = new CreateTableRequest()
				.withTableName(tableName)
				.withKeySchema(keySchema)
				.withAttributeDefinitions(attributeDefinitions)
				.withProvisionedThroughput(new ProvisionedThroughput()
				    .withReadCapacityUnits(1L)
					.withWriteCapacityUnits(1L));

		table = dynamoDB.createTable(request);
		try {
			table.waitForActive();
		} catch (InterruptedException e) {
			System.out.println("Failed to create table: " + tableName);
			e.printStackTrace();
			return;
		}
		System.out.println("Table is created and is ACTIVE: " + tableName);
	}
	
	private void listTables(){
		TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		Iterator<Table> iterator = tables.iterator();
		System.out.println("My DynamoDB tables:");
		while (iterator.hasNext()) {
			Table table = iterator.next();
			System.out.println("\t" + table.getTableName());
		}
	}
	
	private void deleteTable() {
		table = dynamoDB.getTable(tableName);
		if (table == null) 
			return;		
		table.delete();
		try {
			table.waitForDelete();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Failed to delete table: " + tableName);
			e.printStackTrace();
		}
		System.out.println("Table is deleted: " + tableName);
	}

	private void loadData(){
		this.addPet("Sugar", "dog", "sweet and quiet", 5);
		this.addPet("Bandit", "racoon", "sneaky and fluffy", 2);
		this.addPet("Bagel", "cat", "lazy lazy", 10);
	}
	
	private void addPet(String name, String type, String notes, Integer age) {
		System.out.println("Adding pet: " + name + "/" + type + "/" + notes + "/" + age);
		Item item = new Item().withPrimaryKey("ID", name + "-" + type)
				.withString("petName", name)
				.withString("type", type)
				.withString("notes", notes)
				.withNumber("age", age);
		table.putItem(item);
	}

	private void testQuery() {
		String id = "Bagel-cat";
        Item item = table.getItem("ID", id, 
                "petName, notes, age", // projection expression - which attributes to return
                null); // name map - don't need this
        if (item == null) {
        	System.out.println("ERROR: could not get an Item for ID=" + id);
        } else {
        	System.out.println("Found pet: \n" + item.toJSONPretty());
        }
	}

	public static void main(String[] args) {
		System.out.println("Starting DynamoDbManager... ");
		DynamoDbTest ddb = new DynamoDbTest();
		System.out.println("Before table creation: ");
		ddb.listTables();
		
		ddb.createTable();
		System.out.println("After table creation: ");
		ddb.listTables();
		
		ddb.loadData();
		ddb.testQuery();
		
		ddb.deleteTable();
		System.out.println("After table deletion: ");
		ddb.listTables();
		/*  */
	}

}