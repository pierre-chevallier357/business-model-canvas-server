package com.cogiteo.canvas.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.cogiteo.canvas.entitiescontroller.utilisateur.service.UtilisateurService;

public class ExcelService {

    @Autowired
    UtilisateurService userService;

    @Autowired
    private Environment env;

    public void uploadFile(String path, String fileName, String json, boolean delete) {
        String server = env.getProperty("spring.personal.ftp.server");
        int port = Integer.parseInt(env.getProperty("spring.personal.ftp.port"));
        String user = env.getProperty("spring.personal.ftp.user");
        String pass = env.getProperty("spring.personal.ftp.pass");

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            if (delete) {
                System.out.println("Start delete file : " + fileName);
                ftpClient.deleteFile(path + fileName);
                System.out.println("The file " + fileName + " is deleted successfully.");
            } else {

                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                // APPROACH #1: uploads first file using an InputStream
                String pathFTP = path + fileName;
                InputStream inputStream = new ByteArrayInputStream(json.getBytes());
                System.out.println("Start uploading file : " + fileName);

                boolean done = ftpClient.storeFile(pathFTP, inputStream);
                inputStream.close();
                if (done) {
                    System.out.println("The file " + fileName + " is uploaded successfully.");
                }

            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isAdmin(String adminToken) {
        return userService.IsAdmin(adminToken);
    }

}
