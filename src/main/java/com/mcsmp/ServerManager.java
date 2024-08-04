package com.mcsmp;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class ServerManager {
    private Map<String, ServerInfo> serverMap = new HashMap<>();


    public void createNewServer(String serverName) {
        String basePath = "/root/creator/";
        String newServerPath = "/root/newservers/" + serverName;

        // Get the base server files
        new File(newServerPath).mkdirs();
        copyDirectory(new File(basePath), new File(newServerPath));

        // Find an available port
        int port = getAvailablePort();

        // Start the server
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                "/usr/bin/java", "-jar", "spigot.jar", "--port", String.valueOf(port)
            );
            processBuilder.directory(new File(newServerPath));
            processBuilder.redirectErrorStream(true); // Read the error stream
            processBuilder.start();

               
                Process process = processBuilder.start();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("Error: " + line);
                }
            }

            ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(serverName, new InetSocketAddress("localhost", port), "SMP Server", false);
            serverMap.put(serverName, serverInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ServerInfo getServerInfo(String serverName) {
        return serverMap.get(serverName);
    }

    public void invitePlayer(String playerName, String serverName) {
        // TODO: Implement this
    }

    private int getAvailablePort() {
        int port = 25565;
        while (port <= 65535) {
            if (isPortAvailable(port)) {
                return port;
            }
            port++;
        }
        throw new RuntimeException("There are no available ports");
    }

    private boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            // If the port is available, socket will be created
            return true;
        } catch (IOException e) {
            // Port in use or some other error
            return false;
        }
    }

    private void copyDirectory(File source, File target) {
        if (!source.isDirectory()) {
            throw new IllegalArgumentException("Source must be a directory");
        }
        if (!target.exists()) {
            target.mkdirs();
        }
    
        File[] files = source.listFiles();
        if (files != null) {
            for (File file : files) {
                File newFile = new File(target, file.getName());
                if (file.isDirectory()) {
                    copyDirectory(file, newFile);
                } else {
                    copyFile(file, newFile);
                }
            }
        }
    }

    private void copyFile(File source, File target) {
        try (FileInputStream in = new FileInputStream(source);
             FileOutputStream out = new FileOutputStream(target)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}