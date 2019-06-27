package org.academiadecodigo.whiledcards.webserver;

public class HttpMedia {

    public static boolean isImage(String file) {

        switch (getExtension(file)) {

            case "jpg":
            case "png":
            case "ico":
            case "gif":
            case "bmp":
            case "svg":
                return true;

            default:
               return false;
        }
    }

    public static boolean isHtml(String file) {
        return getExtension(file).equals("html");
    }

    public static boolean isCss(String file) {
        return getExtension(file).equals("css");
    }

    public static boolean isJavaScript(String file) {
        return getExtension(file).equals("js");
    }

    public static boolean isSupported (String file) {
        return isHtml(file) || isImage(file) || isCss(file) || isJavaScript(file);
    }

    public static String getExtension(String file) {
        return file.substring(file.lastIndexOf("." + 1));
    }
}
