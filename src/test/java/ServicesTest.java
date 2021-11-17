import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ServicesTest {

    @Test
    void testForRequestMethod() throws IOException {
        ServerSocket server = new ServerSocket(8082);
        Socket clientSocket = server.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while (!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line).append("\r\n");
        }
        String request = requestBuilder.toString();
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String Actual0utput = requestLine[0];
        assertEquals("GET", Actual0utput);
        server.close();
        clientSocket.close();
    }

    @Test
    void getFilePath() throws IOException {
        ServerSocket server = new ServerSocket(8082);
        Socket clientSocket = server.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while (!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line).append("\r\n");
        }
        String request = requestBuilder.toString();
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String path = requestLine[1];
        assertEquals("/", path);
        server.close();
        clientSocket.close();
    }

    @Test
    void shouldTestForRequestVersion() throws IOException {
        ServerSocket server = new ServerSocket(8082);
        Socket clientSocket = server.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while (!(line = br.readLine()).isBlank()) {
            requestBuilder.append(line).append("\r\n");
        }
        String request = requestBuilder.toString();
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String version = requestLine[2];
        assertEquals("HTTP/1.1", version);
        server.close();
        clientSocket.close();
    }

    @Test
    void ShouldTestTheContentType() throws IOException {
        String expected = Files.probeContentType(Path.of("src/main/java/MyFiles/index.html"));
        assertEquals("text/html", expected);
    }
}