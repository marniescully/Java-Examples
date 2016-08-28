package edu.hu.jaxrs;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;

/** Simple  REST client. */

public class JSONClient {

    public static void main(String[] args) {
        SimpleResourceClient erc = new SimpleResourceClient();
        System.out.println(erc.getJson());
        StringBuilder sb = new StringBuilder();
        //sb.append("<?xml version='1.0'?><message>");
        //sb.append("My Personal message</message>");
        sb.append("{");
        sb.append("   \"message\":\"My Message\",");
        sb.append("   \"type\":\"Personal\"");
        sb.append("}");
        System.out.println(erc.putJson(sb.toString()));
        erc.close();
    }

    static class SimpleResourceClient {

        private WebResource webResource;
        private Client client;
        private static final String BASE_URI = 
        		   "http://localhost:8080/jaxrsweb/resources";

        public SimpleResourceClient() {
            ClientConfig config = new DefaultClientConfig();
            client = Client.create(config);
            webResource = 
               client.resource(BASE_URI).path("example");
        }

        public String getJson() throws UniformInterfaceException {
            WebResource resource = webResource;
            return 
            resource.accept (MediaType.APPLICATION_JSON).get(String.class);
        }

        public String putJson(Object requestEntity) throws UniformInterfaceException {
            return webResource.type(MediaType.APPLICATION_JSON).put(String.class, requestEntity);
        }

        public void close() {
            client.destroy();
        }
    }
}
