import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String listOfStrings[] = new String[100];
    int index = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return ("Done!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                index ++;
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    listOfStrings[index] += parameters[1];
                    return ("Success! "+ parameters[1] + "added");
                }
            }
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                String searchFor = "";
                if (parameters[0].equals("s")) {
                    searchFor = parameters[1];
                }
                for(String n : listOfStrings){
                    if(n.contains(searchFor)){
                        System.out.println(n);
                    }
                }
        }
        return "404 Not Found!";
    }
}
class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

}