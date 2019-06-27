package org.academiadecodigo.whiledcards.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private void listen(int port) {

        try {

            serverSocket = new ServerSocket(port);
            serve(serverSocket);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private void dispatch(Socket clientSocket) {
        //TODO
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            String httpFirstLine = receiveHeader(input);

            if (httpFirstLine == null) {
                close(clientSocket);
                return;
            }

            String httpVerb = httpFirstLine.split(" ")[0];
            String httpResource = httpFirstLine.split(" ")[1];

            if (!httpVerb.equals("GET")) {
                reply(output, HttpHelper.notAllowed());
                close(clientSocket);
                return;//get out from dispatch method
            }

            if (httpResource == null) {
                reply(output, HttpHelper.badRequest());
                close(clientSocket);
                return;//get out from dispatch method
            }

            String filepath = getResourcePath(httpResource);

            if (!HttpMedia.isSupported(filepath)) {
                reply(output, HttpHelper.unsupportedMediaType()); //reply the header with unsuported media type
                close(clientSocket);
                return; //get out from dispatch method
            }


            File file = new File(filepath);
            //TODO send header request
            reply(output, filepath);


            streamFile(output, file); // send content

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO @rafa could you describe this method please?
    private String getResourcePath(String httpResource) {
        String path = httpResource;

        Pattern pattern = Pattern.compile("(\\.[^.]+)$"); // regex for file extension
        Matcher matcher = pattern.matcher(path);

        if (!matcher.find()) path += "/index.html";

        path=DOCUMENT_ROOT+path;
        return path;

    }

    /**
     * @param output
     * @param response
     * @throws IOException
     */
    //TODO pass with the group
    private void reply(DataOutputStream output, String response) throws IOException {
        output.writeBytes(response);
    }


    /**
     * file content to byte and send it via the data output stream
     *
     * @param out
     * @param file
     * @throws IOException
     */
    private void streamFile(DataOutputStream out, File file) throws IOException {

        byte[] buffer = new byte[1024];
        FileInputStream in = new FileInputStream(file);

        //writes to the data output stream the buffered information
        int numBytes;
        while ((numBytes = in.read(buffer)) != -1) {
            out.write(buffer, 0, numBytes);
        }

        in.close();

    }


    private void close(Socket clientSocket) {

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
