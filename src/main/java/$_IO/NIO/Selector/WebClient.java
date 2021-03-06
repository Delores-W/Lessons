package $_IO.NIO.Selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class WebClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8000));

        ByteBuffer writeBuffer = ByteBuffer.allocate(32);
        ByteBuffer readBuffer = ByteBuffer.allocate(32);

        writeBuffer.put("Hello".getBytes());
        writeBuffer.flip();

        while (true) {
            writeBuffer.rewind();
            socketChannel.write(writeBuffer);
            readBuffer.clear();
            socketChannel.read(readBuffer);
        }
    }
}
