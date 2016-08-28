import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.MultiObjectDeleteException;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

// Gratefully adapted from the Sample for S3 from amazon
public class S3DeleteBucket_Arbitrary {
	  public static void main(String[] args) throws IOException {

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

	        AmazonS3 s3 = new AmazonS3Client(credentials);
	        Region usEast1 = Region.getRegion(Regions.US_EAST_1);
	        s3.setRegion(usEast1);

	        String bucketName = "my-first-s3-bucket-" + UUID.randomUUID();
	        String key1 = "MyObjectKey1";
	        String key2 = "MyObjectKey2";
	        String key3 = "MyObjectKey3";
	        String key4 = "MyObjectKey4";

	        System.out.println("===========================================");
	        System.out.println("Getting Started with Amazon S3");
	        System.out.println("===========================================\n");

	        try {
	            /*
	             * Create a new S3 bucket - Amazon S3 bucket names are globally unique,
	             * so once a bucket name has been taken by any user, you can't create
	             * another bucket with that same name.
	             *
	             * You can optionally specify a location for your bucket if you want to
	             * keep your data closer to your applications or users.
	             */
	            System.out.println("Creating bucket " + bucketName + "\n");
	            s3.createBucket(bucketName);

	            /*
	             * List the buckets in your account
	             */
	            System.out.println("Listing buckets");
	            for (Bucket bucket : s3.listBuckets()) {
	                System.out.println(" - " + bucket.getName());
	            }
	            System.out.println();

	            /*
	             * Upload an object to your bucket - You can easily upload a file to
	             * S3, or upload directly an InputStream if you know the length of
	             * the data in the stream. You can also specify your own metadata
	             * when uploading to S3, which allows you set a variety of options
	             * like content-type and content-encoding, plus additional metadata
	             * specific to your applications.
	             */
	            System.out.println("Uploading a new object to S3 from a file\n");
	            s3.putObject(new PutObjectRequest(bucketName, key1, createSampleFile()));

	            System.out.println("Uploading a new object to S3 from a file\n");
	            s3.putObject(new PutObjectRequest(bucketName, key2, createSampleFile()));
	           
	            System.out.println("Uploading a new object to S3 from a file\n");
	            s3.putObject(new PutObjectRequest(bucketName, key3, createSampleFile()));
	            
	            System.out.println("Uploading a new object to S3 from a file\n");
	            s3.putObject(new PutObjectRequest(bucketName, key4, createSampleFile()));

	            /*
	             * List objects in your bucket by prefix - There are many options for
	             * listing the objects in your bucket.  Keep in mind that buckets with
	             * many objects might truncate their results when listing their objects,
	             * so be sure to check if the returned object listing is truncated, and
	             * use the AmazonS3.listNextBatchOfObjects(...) operation to retrieve
	             * additional results.
	             */
	            System.out.println("Listing objects");
	            ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
	                    .withBucketName(bucketName)
	                    .withPrefix("My"));
	            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
	                System.out.println(" - " + objectSummary.getKey() + "  " +
	                                   "(size = " + objectSummary.getSize() + ")");
	            }
	            System.out.println();

	            /*
	             * This snippet was from the Amazon help file
	             * http://docs.aws.amazon.com/AmazonS3/latest/dev/DeletingMultipleObjectsUsingJava.html
	             */
	            DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName);

	            List<KeyVersion> keys = new ArrayList<KeyVersion>();
	            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
	            	 keys.add(new KeyVersion(objectSummary.getKey()));
	            }
	                     
	            multiObjectDeleteRequest.setKeys(keys);

	            try {
	                DeleteObjectsResult delObjRes = s3.deleteObjects(multiObjectDeleteRequest);
	                System.out.format("Successfully deleted all the %s items.\n", delObjRes.getDeletedObjects().size());
	                			
	            } catch (MultiObjectDeleteException e) {
	                // Process exception.
	            }
	            

	            /*
	             * Delete a bucket - A bucket must be completely empty before it can be
	             * deleted, so remember to delete any objects from your buckets before
	             * you try to delete them.
	             */
	            System.out.println("Deleting bucket " + bucketName + "\n");
	            s3.deleteBucket(bucketName);
	        } catch (AmazonServiceException ase) {
	            System.out.println("Caught an AmazonServiceException, which means your request made it "
	                    + "to Amazon S3, but was rejected with an error response for some reason.");
	            System.out.println("Error Message:    " + ase.getMessage());
	            System.out.println("HTTP Status Code: " + ase.getStatusCode());
	            System.out.println("AWS Error Code:   " + ase.getErrorCode());
	            System.out.println("Error Type:       " + ase.getErrorType());
	            System.out.println("Request ID:       " + ase.getRequestId());
	        } catch (AmazonClientException ace) {
	            System.out.println("Caught an AmazonClientException, which means the client encountered "
	                    + "a serious internal problem while trying to communicate with S3, "
	                    + "such as not being able to access the network.");
	            System.out.println("Error Message: " + ace.getMessage());
	        }
	    }
	  
	  private static File createSampleFile() throws IOException {
	        File file = File.createTempFile("aws-java-sdk-", ".txt");
	        file.deleteOnExit();

	        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
	        writer.write("abcdefghijklmnopqrstuvwxyz\n");
	        writer.write("01234567890112345678901234\n");
	        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
	        writer.write("01234567890112345678901234\n");
	        writer.write("abcdefghijklmnopqrstuvwxyz\n");
	        writer.close();

	        return file;
	    }

	    /**
	     * Displays the contents of the specified input stream as text.
	     *
	     * @param input
	     *            The input stream to display as text.
	     *
	     * @throws IOException
	     */
	    private static void displayTextInputStream(InputStream input) throws IOException {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
	        while (true) {
	            String line = reader.readLine();
	            if (line == null) break;

	            System.out.println("    " + line);
	        }
	        System.out.println();
	    }

}
