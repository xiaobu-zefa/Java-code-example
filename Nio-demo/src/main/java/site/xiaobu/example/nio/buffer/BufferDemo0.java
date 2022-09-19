package site.xiaobu.example.nio.buffer;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo0 {
    @SneakyThrows
    public static void main(String[] args) {
        RandomAccessFile randomAccessFile = new RandomAccessFile("01.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int size = fileChannel.read(buffer);
        while (size != -1) {
            buffer.flip();

            while (buffer.hasRemaining()) {
                System.out.print((char)buffer.get());
            }

            buffer.clear();
            size = fileChannel.read(buffer);
        }

        fileChannel.close();
    }
}
