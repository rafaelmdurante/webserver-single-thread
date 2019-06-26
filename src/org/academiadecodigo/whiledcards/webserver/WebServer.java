package org.academiadecodigo.whiledcards.webserver;

import java.net.ServerSocket;

public class WebServer {

    // Attributes
    private static final int DEFAULT_PORT = 8082;
    private static final String DOCUMENT_ROOT = "www/";
    private ServerSocket serverSocket;

    // Main
    public static void main(String[] args) {

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
