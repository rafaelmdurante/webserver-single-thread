package org.academiadecodigo.whiledcards.webserver;

public class HttpHelper {

    /*
    HTTP/1.0 200 Document Follows\r\n
    Content-Type: text/html; charset=UTF-8\r\n
    Content-Length: <file_byte_size> \r\n
    \r\n

    HTTP/1.0 200 Document Follows\r\n
    Content-Type: image/<image_file_extension> \r\n
    Content-Length: <file_byte_size> \r\n
    \r\n

    HTTP/1.0 404 Not Found\r\n
    Content-Type: text/html; charset=UTF-8\r\n
    Content-Length: <file_byte_size> \r\n
    \r\n

     */

    //Header First Line
    /**
     * Verifies if resource is null
     * @return
     */
    public static String badRequest() {

        return "HTTP/1.0 400 Bad Request\r\n";

    }

    /**
     * Verifies if verb (GET, PUT, DELETE or POST) is allowed. For this server so far, only GET is allowed.
     * @return
     */
    public static String notAllowed() {

        return "HTTP/1.0 405 Not Allowed\r\n" + "Allow: GET\r\n";

    }

    /**
     * Verifies if media type is supported by the server.
     * @return
     */
    public static String unsupportedMediaType() {

        return "HTTP/1.0 415 Unsupported Media Type\r\n";

    }

    /**
     * Return success
     * @return
     */
    public static String ok() {

        return "HTTP/1.0 200 Document Follow\r\n";

    }

    //Header Second Line
    /**
     * Method to return the second line of the header. Parameter fileExtension must be passed as string without anything but the extension itself.
     * E.g: "jpg", "html", "svg" etc.
     * @param fileExtension
     * @return
     */
    public static String contentType(String fileExtension) {

        String type;

        switch (fileExtension) {
            // We decided to keep HTML and CSS even though it's unnecessary due to default values.
            case "html":
            case "css":
                type = "text";
                break;
            case "js":
                type = "text";
                fileExtension = "javascript";
                break;
            case "png":
            case "ico":
            case "gif":
            case "bmp":
                type = "image";
                break;
            case "jpg":
            case "jpeg":
                type = "image";
                fileExtension = "jpeg";
                break;
            case "svg":
                type = "image";
                fileExtension = "svg+xml";
                break;
            default:
                type = "text";
                break;
        }

        // Line basic model
        StringBuilder line = new StringBuilder("Content-Type: ")
                .append(type).append("/")
                .append(fileExtension)
                .append("\r\n");

        return line.toString();
    }

    // Header Third Line
    public static String contentSize(long fileLength) {

        return "Content-Length: " + fileLength + "\r\n\n\r\n";

    }

}
