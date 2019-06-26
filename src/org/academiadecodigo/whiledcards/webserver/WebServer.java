package org.academiadecodigo.whiledcards.webserver;

import java.net.ServerSocket;

public class WebServer {

    // Attributes
    private static final int DEFAULT_PORT = 8082;
    private static final String DOCUMENT_ROOT = "www/";
    private ServerSocket serverSocket;

    // Main
    public static void main(String[] args) {
        //if it doesnt receive any argument, the port will be the default;
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        WebServer webServer = new WebServer();

        webServer.listen(port);
    }



    // Flow
    /*
    - Listen
    - Serve
    - Dispatch
        > create in/out
        > fetchRequestHeaders
        > separate verb/request/resource
        ---- create header ---
        > !GET notAllowed
        > resource null BadRequest
        > resource supported?
        > resource exists? (ok / FileNotFound)
        > reply filePath
          reply length
        ---- header end ----
        > stream file
        > close connection
     */

    // Methods


}
