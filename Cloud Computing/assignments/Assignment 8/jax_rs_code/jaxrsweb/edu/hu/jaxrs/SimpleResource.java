
package edu.hu.jaxrs;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Example REST Web Service
 */
@Path("example")
public class SimpleResource {

    /**
     * Creates a new instance of SimpleResource
     */
    public SimpleResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        StringBuilder sb = new StringBuilder();
        // sb.append("<?xml version='1.0'?><message>");
        // sb.append("This is the message").append("</message>");
        sb.append("{");
        sb.append("   \"message\":\"Server Message\",");
        sb.append("   \"type\":\"Official\"");
        sb.append("}");
        return sb.toString();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String putJson(String content) {
        System.out.println(content);
        return content;
    }
}
