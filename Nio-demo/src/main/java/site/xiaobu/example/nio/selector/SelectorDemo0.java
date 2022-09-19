package site.xiaobu.example.nio.selector;

import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo0 {
    @SneakyThrows
    public static void main(String[] args) {
        int port = 8889;

        Selector selector = Selector.open();

        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (selector.select() > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();

                    if (selectionKey.isAcceptable()) {
                        handleAcceptable(serverSocketChannel, selector);
                    }

                    if (selectionKey.isReadable()) {
                        handleReadable(selectionKey);
                    }

                    iterator.remove();
                }
            }
        }
    }

    @SneakyThrows
    public static void handleAcceptable(ServerSocketChannel serverSocketChannel, Selector selector) {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        System.out.println("[" + socketChannel.getRemoteAddress() + "] Connected ...");
    }

    @SneakyThrows
    public static void handleReadable(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int len = socketChannel.read(buffer);

        while (len > 0) {
            buffer.flip();

            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }

            buffer.clear();

            len = socketChannel.read(buffer);
        }

        System.out.println("");
        socketChannel.close();
    }
}
