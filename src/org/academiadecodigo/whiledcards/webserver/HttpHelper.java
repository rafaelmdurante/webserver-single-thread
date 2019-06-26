package org.academiadecodigo.whiledcards.webserver;

public class HttpHelper {

    //Header First Line
    public static String badRequest() {

        return "HTTP/1.0 400 Bad Request\r\n";


    }

    public static String notAllowed() {

        return "HTTP/1.0 405 Not Allowed\r\n" + "Allow: GET\r\n";

    }

    public static String unsupportedMediaType() {

        return "HTTP/1.0 415 Unsupported Media Type\r\n";

    }

    public static String ok() {

        return "HTTP/1.0 200 Document Follow\r\n";

    }

    //Header Second Line

    public static String contentType(String fileExtension) {

        if (fileExtension.equals(""))

            return


    }


}
