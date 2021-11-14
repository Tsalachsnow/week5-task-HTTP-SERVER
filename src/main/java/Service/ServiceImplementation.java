package Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServiceImplementation implements Services {


    public ServiceImplementation() {
    }

    @Override
    public void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException {
        OutputStream clientOutput = client.getOutputStream();
        clientOutput.write(("HTTP/1.1 \r\n" + status).getBytes());
        clientOutput.write(("ContentType: " + contentType + "\r\n").getBytes());
        clientOutput.write("\r\n".getBytes());
        clientOutput.write(content);
        clientOutput.write("\r\n\r\n".getBytes());
        clientOutput.flush();
        client.close();
    }

    @Override
    public Path getFilePath(String path) {
        if (path.equals("/")) {
            path = "/index.html";
        } else if(path.equals("/json")){
            path = "/javaindex1.json";
        }
        return Paths.get(path);
    }

    @Override
    public String guessContentType(Path filePath) throws IOException {
        return Files.probeContentType(filePath);
    }
}
