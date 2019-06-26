package org.academiadecodigo.whiledcards.webserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

    /**
     * method to establish connection and dispatch, it creates a clientSocket from server request acceptance
     * @param serverSocket
     */
    void serve(ServerSocket serverSocket) {

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();

                dispatch(clientSocket);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

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
