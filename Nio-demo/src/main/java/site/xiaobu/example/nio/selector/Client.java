package site.xiaobu.example.nio.selector;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    @SneakyThrows
    public static void main(String[] args) {

        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8889));
        socketChannel.configureBlocking(false);

        int count = 0;
        while(true){
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            count += socketChannel.read(buffer);
            System.out.println(count);
        }

    }
}
