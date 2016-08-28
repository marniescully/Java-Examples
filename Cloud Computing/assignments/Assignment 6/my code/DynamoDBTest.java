

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
	private String awsProfileName = "marniescully";

	private DynamoDB dynamoDB;
	private String tableName;
	private Table table;

	public DynamoDbTest() {
		super();
		credentials = new ProfileCredentialsProvider(awsProfileName);
		dynamoDB = new DynamoDB(new AmazonDynamoDBClient(credentials));
		System.out.println("Connected to DynamoDB OK ");
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
	
	private void deleteTables() {
		
		TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		Iterator<Table> iterator = tables.iterator();
		System.out.println("My DynamoDB tables:");
		while (iterator.hasNext()) {
			Table table = iterator.next();
			System.out.println("\t" + table.getTableName());	
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
		
	}


	public static void main(String[] args) {
		System.out.println("Starting DynamoDbManager... ");
		DynamoDbTest ddb = new DynamoDbTest();
		System.out.println("Before table deletion: ");
		ddb.listTables();
		ddb.deleteTables();
		System.out.println("After table deletion: ");
		ddb.listTables();
		/*  */
	}

}