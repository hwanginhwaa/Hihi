package sec06.exam03_threadpool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ClientExample {
	public static void main(String[] args) {
		for(int i=1; i<=100; i++) {
			//SocketChannel 변수 선언
			SocketChannel socketChannel = null;
	
			try {
				//SocketChannel 열기
				socketChannel = SocketChannel.open();
	
				//로컬 PC의 50001에서 실행 중인 ServerSocketChannel로 연결 요청
				System.out.println("[연결 요청]");
				socketChannel.connect(new InetSocketAddress("localhost", 50001));
				System.out.println("[연결 성공]");
	
				ByteBuffer byteBuffer = null;
				Charset charset = Charset.forName("UTF-8");
	
				//서버로 데이터 보내기
				byteBuffer = charset.encode("Hello Server " + i);
				socketChannel.write(byteBuffer);
				System.out.println(i + "번째 데이터 보냄");
	
				//서버가 보낸 데이터 받기
				byteBuffer = ByteBuffer.allocate(100);
				int byteNum = socketChannel.read(byteBuffer);
				if(byteNum == -1) {
					throw new IOException(); 
				}
				byteBuffer.flip();
				String message = charset.decode(byteBuffer).toString();
				System.out.println(i + "번째 데이터 받기: " + message);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// SocketChannel 닫기
				try {
					System.out.println("[연결 끊기]");
					socketChannel.close();
				} catch (IOException e1) {
				}
			}
			System.out.println();
		}
	}
}

