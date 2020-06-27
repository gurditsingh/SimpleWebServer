package com.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.PathResource;
import org.eclipse.jetty.util.resource.Resource;
public class WebServer {

    public static Server createServer(int port, Resource baseResource) throws Exception
    {
        // Create a basic Jetty server object that will listen on port 8080.  Note that if you set this to port 0
        // then a randomly available port will be assigned that you can either look in the logs for the port,
        // or programmatically obtain it for use in test cases.
        Server server = new Server(port);

        // Create the ResourceHandler. It is the object that will actually handle the request for a given file. It is
        // a Jetty Handler object so it is suitable for chaining with other handlers as you will see in other examples.
        ResourceHandler resourceHandler = new ResourceHandler();

        // Configure the ResourceHandler. Setting the resource base indicates where the files should be served out of.
        // In this example it is the current directory but it can be configured to anything that the jvm has access to.
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});
        resourceHandler.setBaseResource(baseResource);

        // Add the ResourceHandler to the server.
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, new DefaultHandler()});
        server.setHandler(handlers);

        return server;
    }

    public static void main(String[] args) throws Exception
    {
        json();
        int port = 7000;
        Path userDir = Paths.get(new File("webapps/root").toURI());
        PathResource pathResource = new PathResource(userDir);

        Server server = createServer(port, pathResource);

        // Start things up! By using the server.join() the server thread will join with the current thread.
        // See "http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()" for more details.
        server.start();
        server.join();
    }


    public static void json(){

        JsonObject j1=new JsonObject();
        j1.addProperty("name","car");
        j1.addProperty("count","12");

        JsonObject j2=new JsonObject();
        j2.addProperty("name","jeep");
        j2.addProperty("count","3");


        JsonArray ja=new JsonArray();
        ja.add(j1);
        ja.add(j2);

        JsonObject r=new JsonObject();
        r.add("model",ja);

        r.toString();
    }
}
