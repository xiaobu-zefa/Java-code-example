package site.xiaobu.example.nio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo3 {
    public static void main(String[] args) {
        int port = 8889;

        Selector selector = null;
        try {
            selector = Selector.open();
        } catch (IOException ignore) {
            System.exit(0);
        }

        ServerSocketChannel serverSocketChannel;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException ignore) {
        }


        while (true) {
            try {
                if (!(selector.select() > 0)) break;
            } catch (IOException e) {
                break;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                if (selectionKey.isAcceptable()) {
                    System.out.println("Accept Event ...");
                    ServerSocketChannel serverChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel;
                    try {
                        socketChannel = serverChannel.accept();
                        socketChannel.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        socketChannel.register(selector, SelectionKey.OP_READ, buffer);
                    } catch (IOException ignore) {

                    }
                } else if (selectionKey.isReadable()) {
                    try {
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        int len = socketChannel.read(buffer);
                        if (len == -1) {
                            selectionKey.cancel();
                        } else {
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                selectionKey.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        selectionKey.cancel();
                    }
                } else if (selectionKey.isWritable()) {
                }
            }
        }
    }


    private static void split(ByteBuffer source) {
        source.flip();

        for (int i = 0; i < source.limit(); i++) {
            byte b = source.get(i);
            if (b == '\n') {
                int len = i + 1 - source.position();
                ByteBuffer tBuffer = ByteBuffer.allocate(len);
                for (int j = 0; j < len; j++) {
                    tBuffer.put(source.get());
                }

                tBuffer.flip();
                System.out.println(StandardCharsets.UTF_8.decode(tBuffer));
            }
        }

        source.compact();
    }

}
