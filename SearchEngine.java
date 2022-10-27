import java.util.*;
import java.io.IOException;
import java.net.URI;

class SearchEngine {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }

}

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> list = new ArrayList<String>();

    public String handleRequest(URI url) {
        // checks if path is "empty"
        if (url.getPath().equals("/")) {
            return String.format("Here is whats in the List!:\n" + String.join(", ", list));
        } else if (url.getPath().equals("/addFiller")) {
            list.add("filler");
            return String.format("it added the word \"filler\"!");
        } else {
            System.out.println("Path: " + url.getPath());
            String[] parameters = url.getQuery().split("=");
            // Code for Adding
            if (url.getPath().contains("/add")) {
                return add(parameters);
            }

            else if (url.getPath().contains("/query")) {
                // Code for Searching
                return search(parameters);
            }
        }

        return "bruh wtf error code 404 up in here\n you fucked up lol";
    }

    public String add(String[] parameters) {
        // if we in fact are adding.... check for the "a"
            list.add(parameters[1]);
            return String.format("Added " + parameters[1] + " to the List!");
    }

    public String search(String[] parameters) {
        // if we in fact are searching.... check for the "s"
        ArrayList<String> tempList = new ArrayList<String>();
        if (parameters[0].equals("s")) {
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                String temp = (String) iter.next();
                if (temp.contains(parameters[1])) {
                    tempList.add(temp);
                }
            }
        }
        return String.format("Result(s): " + String.join(", ", tempList));
    }

}
