package site.xiaobu.example.nio;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ServerSocketChannelDemo {
    @SneakyThrows
    public static void main(String[] args) {
        int port = 8889;

        java.nio.channels.ServerSocketChannel serverSocketChannel = java.nio.channels.ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.wrap("Hello world".getBytes(StandardCharsets.UTF_8));

        while (true) {
            System.out.println("等待连接...");
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                TimeUnit.SECONDS.sleep(2);
            }
            else {
                System.out.println("已连接: " + socketChannel.socket().getRemoteSocketAddress());
                socketChannel.write(buffer);
                socketChannel.close();
            }
        }
    }
}
