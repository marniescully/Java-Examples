
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpledb.AmazonSimpleDB;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.DeleteAttributesRequest;
import com.amazonaws.services.simpledb.model.DeleteDomainRequest;
import com.amazonaws.services.simpledb.model.Item;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.amazonaws.services.simpledb.model.SelectRequest;

//This is the code for Problem 1 on HW 6 Marnie Scully

public class SimpleDBSample {

    public static void main(String[] args) throws Exception {

    	 /*
         * The ProfileCredentialsProvider will return your [marniescully]
         * credential profile by reading from the credentials file located at
         * (/Users/marnie/.aws/credentials).
         */
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("marniescully").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/marnie/.aws/credentials), and is in valid format.",
                    e);
        }
        AmazonSimpleDB sdb = new AmazonSimpleDBClient(credentials);
		Region usEast1 = Region.getRegion(Regions.US_EAST_1);
		sdb.setRegion(usEast1);

        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon SimpleDB");
        System.out.println("===========================================\n");

        try {
        	
        		// Delete a domain
        	 	String myDomain = "starsandnobels";
        	 	System.out.println("Deleting " + myDomain + " domain.\n");
            sdb.deleteDomain(new DeleteDomainRequest(myDomain));
            
            // Create a domain
            myDomain = "starsandnobels";
            System.out.println("Creating domain called " + myDomain + ".\n");
            sdb.createDomain(new CreateDomainRequest(myDomain));

            // List domains
            System.out.println("Listing all domains in your account:\n");
            for (String domainName : sdb.listDomains().getDomainNames()) {
                System.out.println("  " + domainName);
            }
            System.out.println();

            // Put data into a domain
            System.out.println("Putting data into " + myDomain + " domain.\n");
            sdb.batchPutAttributes(new BatchPutAttributesRequest(myDomain, createSampleData()));

            // Select data from a domain
            
            // Show all records in Domain
            String selectExpression = "select * from `" + myDomain + "` ";
            System.out.println("Selecting: " + selectExpression + "\n");
            SelectRequest selectRequest = new SelectRequest(selectExpression).withConsistentRead(true);
            for (Item item : sdb.select(selectRequest).getItems()) {
                System.out.println("  Item");
                System.out.println("    Name: " + item.getName());
                for (Attribute attribute : item.getAttributes()) {
                    System.out.println("      Attribute");
                    System.out.println("        Name:  " + attribute.getName());
                    System.out.println("        Value: " + attribute.getValue());
                }
            }
            System.out.println();

           // Demonstrate that you can change a year value of a laureate
            selectExpression = "select * from `" + myDomain + "` where itemName() = 'Marie-Curie'";
            System.out.println("Selecting: " + selectExpression + "\n");
            selectRequest = new SelectRequest(selectExpression).withConsistentRead(true);
            for (Item item : sdb.select(selectRequest).getItems()) {
                System.out.println("  Item");
                System.out.println("    Name: " + item.getName());
                for (Attribute attribute : item.getAttributes()) {
                    System.out.println("      Attribute");
                    System.out.println("        Name:  " + attribute.getName());
                    System.out.println("        Value: " + attribute.getValue());
                }
            }
            System.out.println();

            // Replace an attribute
            System.out.println("Replacing Year of Madame Curie's Prize in Physics with 1904.\n");
            List<ReplaceableAttribute> replaceableAttributes = new ArrayList<ReplaceableAttribute>();
            replaceableAttributes.add(new ReplaceableAttribute("Year", "1904", true));
            sdb.putAttributes(new PutAttributesRequest(myDomain, "Marie-Curie", replaceableAttributes));

            // See the Change to the Year
            selectExpression = "select * from `" + myDomain + "` where itemName() = 'Marie-Curie'";
            System.out.println("Selecting: " + selectExpression + "\n");
            selectRequest = new SelectRequest(selectExpression).withConsistentRead(true);
            for (Item item : sdb.select(selectRequest).getItems()) {
                System.out.println("  Item");
                System.out.println("    Name: " + item.getName());
                for (Attribute attribute : item.getAttributes()) {
                    System.out.println("      Attribute");
                    System.out.println("        Name:  " + attribute.getName());
                    System.out.println("        Value: " + attribute.getValue());
                }
            }
            System.out.println();
            
            
            
            //See all Stars
            selectExpression = "select * from `" + myDomain + "` where Type = 'star'";
            System.out.println("Selecting: " + selectExpression + "\n");
            selectRequest = new SelectRequest(selectExpression).withConsistentRead(true);
            for (Item item : sdb.select(selectRequest).getItems()) {
                System.out.println("  Item");
                System.out.println("    Name: " + item.getName());
                for (Attribute attribute : item.getAttributes()) {
                    System.out.println("      Attribute");
                    System.out.println("        Name:  " + attribute.getName());
                    System.out.println("        Value: " + attribute.getValue());
                }
            }
            System.out.println();
            
            // Delete an item and all of its attributes
            System.out.println("Deleting Dustin Hoffman.\n");
            sdb.deleteAttributes(new DeleteAttributesRequest(myDomain, "Dustin-Hoffman"));

            //See that Dustin is gone
            selectExpression = "select * from `" + myDomain + "` where Type = 'star'";
            System.out.println("Selecting: " + selectExpression + "\n");
            selectRequest = new SelectRequest(selectExpression).withConsistentRead(true);
            for (Item item : sdb.select(selectRequest).getItems()) {
                System.out.println("  Item");
                System.out.println("    Name: " + item.getName());
                for (Attribute attribute : item.getAttributes()) {
                    System.out.println("      Attribute");
                    System.out.println("        Name:  " + attribute.getName());
                    System.out.println("        Value: " + attribute.getValue());
                }
            }
            System.out.println();
        
           
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon SimpleDB, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with SimpleDB, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    /* Creates an array of SimpleDB ReplaceableItems populated with sample data. */
    private static List<ReplaceableItem> createSampleData() {
        List<ReplaceableItem> sampleData = new ArrayList<ReplaceableItem>();

        sampleData.add(new ReplaceableItem("Dustin-Hoffman").withAttributes(
                new ReplaceableAttribute("Full Name", "Dustin Hoffman", true),
                new ReplaceableAttribute("Type", "star", true),
                new ReplaceableAttribute("Most Popular Movie", "Tootsie", true),
                new ReplaceableAttribute("URLImage", "https://s3.amazonaws.com/starsandnobels/stars/images/dustin.jpeg", true),
                new ReplaceableAttribute("URLResume", "https://s3.amazonaws.com/starsandnobels/stars/resumes/dustin.docx", true)));

        sampleData.add(new ReplaceableItem("Marilyn-Monroe").withAttributes(
                new ReplaceableAttribute("Full Name", "Marilyn Monroe", true),
                new ReplaceableAttribute("Type", "star", true),
                new ReplaceableAttribute("Most Popular Movie", "Some Like it Hot", true),
                new ReplaceableAttribute("URLImage", "https://s3.amazonaws.com/starsandnobels/stars/images/marilyn.jpeg", true),
                new ReplaceableAttribute("URLResume", "https://s3.amazonaws.com/starsandnobels/stars/resumes/marilyn.docx", true)));

        sampleData.add(new ReplaceableItem("Meryl-Streep").withAttributes(
                new ReplaceableAttribute("Full Name", "Meryl Streep", true),
                new ReplaceableAttribute("Type", "star", true),
                new ReplaceableAttribute("Most Popular Movie", "Sophie's Choice", true),
                new ReplaceableAttribute("URLImage", "https://s3.amazonaws.com/starsandnobels/stars/images/meryl.jpeg", true),
                new ReplaceableAttribute("URLResume", "https://s3.amazonaws.com/starsandnobels/stars/resumes/meryl.docx", true)));

        sampleData.add(new ReplaceableItem("Marie-Curie").withAttributes(
                new ReplaceableAttribute("Full Name", "Marie Curie", true),
                new ReplaceableAttribute("Type", "nobel", true),
                new ReplaceableAttribute("Most Popular Movie", "", true), // the instructions said to include this for the nobels too
                new ReplaceableAttribute("Year", "1903", true),
                new ReplaceableAttribute("Field", "Physics", true),
                new ReplaceableAttribute("URLImage", "https://s3.amazonaws.com/starsandnobels/nobels/images/curie.jpg", true),
                new ReplaceableAttribute("URLResume", "https://s3.amazonaws.com/starsandnobels/nobels/resumes/curie.docx", true)));
        
        sampleData.add(new ReplaceableItem("Eugene-Oneil").withAttributes(
                new ReplaceableAttribute("Full Name", "Eugene O'Neil", true),
                new ReplaceableAttribute("Type", "nobel", true),
                new ReplaceableAttribute("Most Popular Movie", "", true),
                new ReplaceableAttribute("Year", "1936", true),
                new ReplaceableAttribute("Field", "Literature", true),
                new ReplaceableAttribute("URLImage", "https://s3.amazonaws.com/starsandnobels/nobels/images/oneil.jpg", true),
                new ReplaceableAttribute("URLResume", "https://s3.amazonaws.com/starsandnobels/nobels/resumes/oneil.docx", true)));
       
        sampleData.add(new ReplaceableItem("George-Bernard-Shaw").withAttributes(
                new ReplaceableAttribute("Full Name", "George Bernard Shaw", true),
                new ReplaceableAttribute("Type", "nobel", true),
                new ReplaceableAttribute("Most Popular Movie", "", true),
                new ReplaceableAttribute("Year", "1925", true),
                new ReplaceableAttribute("Field", "Literature", true),
                new ReplaceableAttribute("URLImage", "https://s3.amazonaws.com/starsandnobels/nobels/images/shaw.jpg", true),
                new ReplaceableAttribute("URLResume", "https://s3.amazonaws.com/starsandnobels/nobels/resumes/shaw.docx", true)));

        return sampleData;
    }
}