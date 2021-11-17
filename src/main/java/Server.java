import Service.ServiceImplementation;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) throws IOException {
        ServiceImplementation si = new ServiceImplementation();
        ServerSocket server = new ServerSocket(8082);
        ExecutorService foo = Executors.newFixedThreadPool(5);
        while (server != null) {
            Socket clientSocket = server.accept();


            foo.execute(() -> {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    StringBuilder requestBuilder = new StringBuilder();
                    String line;
                    while (!(line = br.readLine()).isBlank()) {
                        requestBuilder.append(line).append("\r\n");
                    }

                    String request = requestBuilder.toString();
                    String[] requestsLines = request.split("\r\n");
                    String[] requestLine = requestsLines[0].split(" ");
                    String method = requestLine[0];
                    String path = requestLine[1];
                    String version = requestLine[2];
                    String host = requestsLines[1].split(" ")[1];


                    Path filePath = si.getFilePath("src/main/java/MyFiles/index.html");
                    Path filePath1 = si.getFilePath("src/main/java/MyFiles/index1.json");
                    Path filePath2 = si.getFilePath("src/main/java/MyFiles/error.html");
                    if (path.equals("/") && Files.exists(filePath)) {
                        String contentType = si.guessContentType(filePath);
                        si.sendResponse(clientSocket, "200 OK", contentType, Files.readAllBytes(filePath));
                    } else if (path.equals("/json") && Files.exists(filePath1)) {
                        String contentType = si.guessContentType(filePath1);
                        si.sendResponse(clientSocket, "200 OK", contentType, Files.readAllBytes(filePath1));
                    } else {
                        String contentType = si.guessContentType(filePath2);
                        si.sendResponse(clientSocket, "200 OK", contentType, Files.readAllBytes(filePath2));
                    }
                    System.out.println(requestBuilder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }
}


