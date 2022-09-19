package site.xiaobu.example.nio;

import lombok.SneakyThrows;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo0 {
    @SneakyThrows
    public static void main(String[] args) {

        RandomAccessFile randomAccessFile = new RandomAccessFile("01.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int b = fileChannel.read(buffer);
        while (b != -1){
            System.out.println("Read:" + b);
            buffer.flip();

            while (buffer.hasRemaining()){
                System.out.print((char)buffer.get());
            }

            buffer.clear();
            b = fileChannel.read(buffer);
        }

        randomAccessFile.close();
    }
}
