import java.util.HashMap;
import java.util.Map;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.util.Tables;

/**
 * Marnie Scully HW6 Problem 3
 */
public class DynamoDBSample {

    /*
     * Before running the code:
     *      Fill in your AWS access credentials in the provided credentials
     *      file template, and be sure to move the file to the default location
     *     (/Users/marnie/.aws/credentials) where the sample code will load the
     *      credentials from.
     *      https://console.aws.amazon.com/iam/home?#security_credential
     *
     * WARNING:
     *      To avoid accidental leakage of your credentials, DO NOT keep
     *      the credentials file in your source directory.
     */

    static AmazonDynamoDBClient dynamoDB;

    /**
     * The only information needed to create a client are security credentials
     * consisting of the AWS Access Key ID and Secret Access Key. All other
     * configuration, such as the service endpoints, are performed
     * automatically. Client parameters, such as proxies, can be specified in an
     * optional ClientConfiguration object when constructing a client.
     *
     * @see com.amazonaws.auth.BasicAWSCredentials
     * @see com.amazonaws.auth.ProfilesConfigFile
     * @see com.amazonaws.ClientConfiguration
     */
    private static void init() throws Exception {
        /*
         * The ProfileCredentialsProvider will return your [marniescully]
         * credential profile by reading from the credentials file located at
         * (/Users/marnie/.aws/credentials)..
         */
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("marniescully").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/marnie/.aws/credentials)., and is in valid format.",
                    e);
        }
        dynamoDB = new AmazonDynamoDBClient(credentials);
        Region usEast1 = Region.getRegion(Regions.US_EAST_1);
        dynamoDB.setRegion(usEast1);
    }

    public static void main(String[] args) throws Exception {
        init();

        try {
            String tableName = "CELEBRITIES_SDK";

            // Create table if it does not exist yet
            if (Tables.doesTableExist(dynamoDB, tableName)) {
                System.out.println("Table " + tableName + " is already ACTIVE");
            } else {
                // Create a table with a primary hash key named 'name', which holds a string
                CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
                    .withKeySchema(new KeySchemaElement().withAttributeName("name").withKeyType(KeyType.HASH))
                    .withAttributeDefinitions(new AttributeDefinition().withAttributeName("name").withAttributeType(ScalarAttributeType.S))
                    .withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));
                    TableDescription createdTableDescription = dynamoDB.createTable(createTableRequest).getTableDescription();
                System.out.println("Created Table: " + createdTableDescription);

                // Wait for it to become active
                System.out.println("Waiting for " + tableName + " to become ACTIVE...");
                Tables.awaitTableToBecomeActive(dynamoDB, tableName);
            }

            // Describe our new table
            DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
            TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
            System.out.println("Table Description: " + tableDescription);

            // Add an item
            Map<String, AttributeValue> item = newItem("Marilyn-Monroe", 0, "none", "https://s3.amazonaws.com/starsandnobels/stars/images/marilyn.jpeg", 
            		"https://s3.amazonaws.com/starsandnobels/stars/resumes/marilyn.docx", "Some Like it Hot");
            PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
            PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
            System.out.println("Result: " + putItemResult);

            // Add another item
            item = newItem("Dustin-Hoffman", 0, "none", "https://s3.amazonaws.com/starsandnobels/stars/images/dustin.jpeg", 
            		"https://s3.amazonaws.com/starsandnobels/stars/resumes/dustin.docx", "Tootsie");
            putItemRequest = new PutItemRequest(tableName, item);
            putItemResult = dynamoDB.putItem(putItemRequest);
            System.out.println("Result: " + putItemResult);
            
            // Add another item
            item = newItem("Meryl-Streep", 0, "none", "https://s3.amazonaws.com/starsandnobels/stars/images/meryl.jpeg", 
            		"https://s3.amazonaws.com/starsandnobels/stars/resumes/meryl.docx", "Out of Africa");
            putItemRequest = new PutItemRequest(tableName, item);
            putItemResult = dynamoDB.putItem(putItemRequest);
            System.out.println("Result: " + putItemResult);
            
            // Add another item
            item = newItem("Marie-Curie", 1903, "Physics", "https://s3.amazonaws.com/starsandnobels/nobels/images/curie.jpeg", 
            		"https://s3.amazonaws.com/starsandnobels/nobels/resumes/curie.docx", "none");
            putItemRequest = new PutItemRequest(tableName, item);
            putItemResult = dynamoDB.putItem(putItemRequest);
            System.out.println("Result: " + putItemResult);
            
            // Add another item
            item = newItem("George-Bernard-Shaw", 1925, "Literature", "https://s3.amazonaws.com/starsandnobels/nobels/images/shaw.jpeg", 
            		"https://s3.amazonaws.com/starsandnobels/nobels/resumes/shaw.docx", "none");
            putItemRequest = new PutItemRequest(tableName, item);
            putItemResult = dynamoDB.putItem(putItemRequest);
            System.out.println("Result: " + putItemResult);
            
            // Add another item
            item = newItem("Eugene-Oneil", 1936, "Literature", "https://s3.amazonaws.com/starsandnobels/nobels/images/oneil.jpeg", 
            		"https://s3.amazonaws.com/starsandnobels/nobels/resumes/oneil.docx", "none");
            putItemRequest = new PutItemRequest(tableName, item);
            putItemResult = dynamoDB.putItem(putItemRequest);
            System.out.println("Result: " + putItemResult);
            
            // Scan CELEBRITIES_SDK
            // Scan items for Laureates with a prize year attribute greater than -1 (which would be all)
            HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
            Condition condition = new Condition()
                .withComparisonOperator(ComparisonOperator.GT.toString())
                .withAttributeValueList(new AttributeValue().withN("-1"));
            scanFilter.put("year", condition);
            ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
            ScanResult scanResult = dynamoDB.scan(scanRequest);
            System.out.println("CELEBRITIES_SDK Result: " + scanResult);
            
            // Scan CELEBRITIES_CONSOLE Will only return Laureates
            // Scan items for Laureates with a prize year attribute greater than -1 (which would be all)
            tableName = "CELEBRITIES_CONSOLE";
            scanFilter = new HashMap<String, Condition>();
            condition = new Condition()
                .withComparisonOperator(ComparisonOperator.GT.toString())
                .withAttributeValueList(new AttributeValue().withN("1900"));
            scanFilter.put("Year", condition);
            scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
            scanResult = dynamoDB.scan(scanRequest);
            System.out.println("CELEBRITIES_CONSOLE Result: " + scanResult);
            
            // Changing the year of nobel prize for Eugene O'Neil
            tableName = "CELEBRITIES_SDK";
            item = newItem("Eugene-Oneil", 1946, "Literature", "https://s3.amazonaws.com/starsandnobels/nobels/images/oneil.jpeg", 
            		"https://s3.amazonaws.com/starsandnobels/nobels/resumes/oneil.docx", "none");
            putItemRequest = new PutItemRequest(tableName, item);
            putItemResult = dynamoDB.putItem(putItemRequest);
            System.out.println("Changing O'Neil Year: ");
            
            // View CELEBRITIES_SDK to see last update
            scanRequest = new ScanRequest(tableName);
            scanResult = dynamoDB.scan(scanRequest);
            System.out.println("CELEBRITIES_SDK Result: " + scanResult);
            

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    // I edited this function to have the fields of the CELEBRITIES_SDK table
    private static Map<String, AttributeValue> newItem(String name, int year, String field, String URLPic, String URLRes, String popularMovie) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("name", new AttributeValue(name));
        item.put("year", new AttributeValue().withN(Integer.toString(year)));
        item.put("field", new AttributeValue(field));
        item.put("URLPic", new AttributeValue(URLPic));
        item.put("URLRes", new AttributeValue(URLRes));
        item.put("popularMovie", new AttributeValue(popularMovie));
        return item;
    }

}