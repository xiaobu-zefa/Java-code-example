package site.xiaobu.example.nio.chat;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class BioChatClient {
    @SneakyThrows
    public static void main(String[] args) {
        Socket socket = new Socket("127.0.0.1", 8889);

        new Thread(new SocketReader(socket)).start();


        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {

            String content = scanner.nextLine();

            OutputStream out = socket.getOutputStream();
            out.write(content.getBytes(StandardCharsets.UTF_8));
        }
    }


    static class SocketReader implements Runnable {

        private final Socket socket;

        public SocketReader(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream in = socket.getInputStream();
                while (true) {

                    byte[] buffer;

                    while (true) {
                        buffer = new byte[3];

                        int len = in.read(buffer);
                        if (len == -1) {
                            break;
                        }

                        String msg = new String(buffer, 0, len, StandardCharsets.UTF_8);
                        System.out.print(msg);
                    }

                    System.out.println("");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
