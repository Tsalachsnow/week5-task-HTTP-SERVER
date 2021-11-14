package Service;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;

public interface Services {
    void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException;
    Path getFilePath(String path);
    String guessContentType(Path filePath) throws IOException;
}
