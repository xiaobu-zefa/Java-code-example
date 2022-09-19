package site.xiaobu.example.nio;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
    @SneakyThrows
    public static void main(String[] args) {
        int port = 8889;

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(port));
        socketChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int size = socketChannel.read(buffer);
        while (size != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            buffer.clear();
            size = socketChannel.read(buffer);
        }
    }
}
