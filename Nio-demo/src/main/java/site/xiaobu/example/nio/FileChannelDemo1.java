package site.xiaobu.example.nio;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelDemo1 {
    @SneakyThrows
    public static void main(String[] args) {
        RandomAccessFile randomAccessFile = new RandomAccessFile("01.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        String str = "new hello world";
        buffer.clear();
        buffer.put(str.getBytes(StandardCharsets.UTF_8));
        buffer.flip();

        while (buffer.hasRemaining()) {
            int b = fileChannel.write(buffer);
        }

        fileChannel.close();
    }
}
