package org.academiadecodigo.whiledcards.webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebServer {

    public static final int DEFAULT_PORT = 8082;
    public static final String DOCUMENT_ROOT = "www/";

    public static void main(String[] args) {
        try {
            //if it doesnt receive any argument, the port will be the default;
            int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

            WebServer webServer = new WebServer();

            webServer.listen(port);

        } catch (NumberFormatException e) {
            System.err.println("Usage Webserver [PORT]");
            System.exit(1);
        }
    }


    /**
     * Method to listen to the selected port, wait for requests from the client
     *
     * @param port
     */
    private void listen(int port) {

        try {

            ServerSocket serverSocket = new ServerSocket(port);
            serve(serverSocket);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * method to establish connection and dispatch, it creates a clientSocket from server request acceptance
     *
     * @param serverSocket
     */
    private void serve(ServerSocket serverSocket) {

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                dispatch(clientSocket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void dispatch(Socket clientSocket) {

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            String httpFirstLine = receiveHeader(input);

            if (httpFirstLine == null) {
                close(clientSocket);
                return;
            }

            String httpVerb = httpFirstLine.split(" ")[0];
            String httpResource = httpFirstLine.split(" ").length > 1 ? httpFirstLine.split(" ")[1] : null;

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
            if (file.exists() && !file.isDirectory()) {
                reply(output, HttpHelper.ok());
            } else {
                reply(output, HttpHelper.notFound());
                filepath = DOCUMENT_ROOT + "404.html";
                file = new File(filepath);
            }


            reply(output, HttpHelper.contentType(HttpMedia.getExtension(filepath)));
            System.out.println(HttpHelper.contentType(HttpMedia.getExtension(filepath)));
            reply(output, HttpHelper.contentSize(file.length()));

            streamFile(output, file); // send content

            close(clientSocket);

        } catch (IOException e) {
            e.printStackTrace();
            close(clientSocket);
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

    //TODO @rafa could you describe this method please?

    private String getResourcePath(String httpResource) {
        String path = httpResource;

        Pattern pattern = Pattern.compile("(\\.[^.]+)$"); // regex for file extension
        Matcher matcher = pattern.matcher(path);

        if (!matcher.find()) path += "/index.html";

        path = DOCUMENT_ROOT + path;
        return path;

    }

    /**
     * method to reply to client request(send http header)
     *
     * @param output
     * @param response
     * @throws IOException
     */
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