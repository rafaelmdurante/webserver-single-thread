package org.academiadecodigo.whiledcards.webserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
     *
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

    /**
     * Method to listen to the selected port, wait for requests from the client
     *
     * @param port
     */
    public void listen(int port) {

        try {

            serverSocket = new ServerSocket(port);
            serve(serverSocket);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void dispatch(Socket clientSocket) {
        //TODO
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            String httpFirstLine = receiveHeader(input);
            String httpVerb = httpFirstLine.split(" ")[0];
            String httpResource = httpFirstLine.split(" ")[1];

            if (!httpVerb.equals("GET")) {
                //TODO something


                close(clientSocket);
                return;
            }

            if (httpResource == null) {
            //TODO something

                close(clientSocket);
                return;
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void close(Socket clientSocket){

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method that returns the first line of the http request
     *
     * @param in
     * @return
     */
    private String receiveHeader(BufferedReader in) {

        String line = null;

        try {
            line = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;

    }


}
