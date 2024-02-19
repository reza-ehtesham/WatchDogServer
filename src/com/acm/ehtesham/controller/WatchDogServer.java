package com.acm.ehtesham.controller;

import com.acm.ehtesham.util.CommonUtils;
import com.sun.istack.internal.logging.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;

public class WatchDogServer implements Runnable {
    public static final Logger logger = Logger.getLogger(WatchDogServer.class);
    public ServerSocketChannel serverSocketChannel;
    public ServerSocket serverSocket;
    private Selector selector;
    public InetSocketAddress netAddress;

    /**
     * COnfig Server
     * @throws IOException
     */
    private synchronized void configurationNetwork() throws IOException {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            logger.log(Level.INFO, "Set Non Blocking Server");
            serverSocketChannel.configureBlocking(false);
            serverSocket = serverSocketChannel.socket();
            netAddress = new InetSocketAddress(Integer.parseInt(CommonUtils.getProperty("PORT")));
            serverSocket.bind(netAddress);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            serverSocketChannel.close();
            serverSocket.close();
        }
        try {
            connectionOperation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void run() {
        try {
            configurationNetwork();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read Key And Accpet, Read , Write Key
     * @throws Exception
     */
    private synchronized void connectionOperation() throws Exception {
        do {
            int numKeys = selector.select();
            if (numKeys > 0) {
                Set readyKeys = selector.selectedKeys();
                Iterator iterator = readyKeys.iterator();
                while (iterator.hasNext()) {
                    synchronized(this) {
                        SelectionKey key = (SelectionKey) iterator.next();
                        if (key.isAcceptable()) {
                            acceptConnection(key);
                            continue;
                        }
                        if (key.isReadable()) {
                            writeData(key);
                        }
                    }
                }
            }
        } while (true);
    }

    /**
     * Accept Connection Network
     * @param key
     * @throws IOException
     */
    private synchronized void acceptConnection(SelectionKey key) throws IOException {
        try {
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);
            Socket socket = client.socket();
            logger.log(Level.INFO, "Accepted connection from " + socket);
            client.register(selector, SelectionKey.OP_READ);
            selector.selectedKeys().remove(key);
        } catch (Exception ex) {
            logger.log(Level.INFO, "Interrupt Accept Connection");
            serverSocketChannel.close();
            serverSocket.close();
        }
    }

    /**
     * Write Data & Unmarshaling Data Client
     * @param key
     * @throws Exception
     */
    private synchronized void writeData(SelectionKey key) throws Exception {
        try {
            SocketChannel socketChannel;
            socketChannel = (SocketChannel) key.channel();
            Socket socket = socketChannel.socket();
            String clientIp = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
            key.cancel();
            socketChannel.configureBlocking(true);
            logger.log(Level.INFO, "Recieve HeartBeat File");
            InputStream inputStream = socket.getInputStream();
            logger.log(Level.INFO, "UnMarshal HeartBeat File");
            CommonUtils.unmarshalling(inputStream, clientIp);
            selector.selectedKeys().remove(key);
        } catch (Exception ioEx) {
            ioEx.printStackTrace();
            logger.log(Level.INFO, "Error In Create HeartBeat File");
            serverSocketChannel.close();
            serverSocket.close();
        }

    }
}
