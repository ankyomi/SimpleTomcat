package com.simple.server.service.start;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer {
	
	private Selector selector;
	private int port = 8080;
	private BaseApps ba = null;
	/**
	 * 初始化
	 * @param port
	 * @throws IOException
	 */
	public void initServer() throws IOException {
		System.out.println("Socket Init at Port-"+ this.getPort());
		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		serverSocket.configureBlocking(false);
		serverSocket.socket().bind(new InetSocketAddress(this.port));
		this.selector = Selector.open();
		serverSocket.register(selector, SelectionKey.OP_ACCEPT);
	}
	
	/**
	 * 接收数据
	 * @throws IOException
	 */
	public void listen() throws IOException {
		System.out.println("Socket Start");
		while(true) {
			selector.select();
			Iterator<?> it = this.selector.selectedKeys().iterator();
			while(it.hasNext()) {
				SelectionKey key = (SelectionKey) it.next();
				it.remove();
				if(key.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) key.channel();
					SocketChannel channel = server.accept();
					channel.configureBlocking(false);
					//channel.write(ByteBuffer.wrap(new String("Hello World\n").getBytes()));
					channel.register(this.selector,SelectionKey.OP_READ);
				}else if(key.isReadable()) {
					read(key);
				}
				
			}
		}
	}
	
	public void read(SelectionKey key) throws IOException {
		// 服务器可读消息，得到事件发生的socket通道
        SocketChannel channel = (SocketChannel) key.channel();
        // 读取的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data).trim();
        //System.out.print( msg);
        String url = "";
        if(msg.contains("HTTP")) {
        	url = msg.substring(5, msg.indexOf("HTTP") - 1);
        }
        if(!url.equals("")) {
        	String file = this.ba.getFile(url);
            ByteBuffer outBuffer = ByteBuffer.wrap(file.getBytes());
            channel.write(outBuffer);
        }else {
            ByteBuffer outBuffer = ByteBuffer.wrap("ROOT".getBytes());
            channel.write(outBuffer);
        }
        channel.close();
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public BaseApps getBa() {
		return ba;
	}

	public void setBa(BaseApps ba) {
		this.ba = ba;
	}
}
