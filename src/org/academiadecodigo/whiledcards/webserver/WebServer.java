package org.academiadecodigo.whiledcards.webserver;

public class WebServer {

    // Attributes
    

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
