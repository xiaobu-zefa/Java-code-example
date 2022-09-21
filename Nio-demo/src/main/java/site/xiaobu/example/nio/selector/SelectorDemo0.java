package site.xiaobu.example.nio.selector;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
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

                    if (selectionKey.isWritable()) {
                        handleWritable(selectionKey);
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
        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        System.out.println("[" + socketChannel.getRemoteAddress() + "] Connected ...");
    }

    @SneakyThrows
    public static void handleReadable(SelectionKey selectionKey) {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int len = socketChannel.read(buffer);

        StringBuilder builder = new StringBuilder();

        while (len > 0) {
            buffer.flip();

            builder.append(new String(buffer.array(), 0, buffer.limit(), StandardCharsets.UTF_8));

            buffer.clear();

            len = socketChannel.read(buffer);
        }

        String content = builder.toString();
        System.out.println(content);

        if (StrUtil.equals("quit", content)) {
            socketChannel.write(ByteBuffer.wrap("bye bye!".getBytes(StandardCharsets.UTF_8)));

            socketChannel.close();
        }
    }

    @SneakyThrows
    private static void handleWritable(SelectionKey selectionKey) {
        // System.out.println("有 Writeable 事件 ...");
    }
}
