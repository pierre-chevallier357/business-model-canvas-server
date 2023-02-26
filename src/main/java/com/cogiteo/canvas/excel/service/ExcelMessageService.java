package com.cogiteo.canvas.excel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cogiteo.canvas.excel.helper.ExcelMessageHelper;
import com.cogiteo.canvas.excel.model.messageObject.Message;
import com.cogiteo.canvas.excel.repository.repositoryExcelMessage.RepositoryMessage;

@Service
public class ExcelMessageService extends ExcelService {

    @Autowired
    RepositoryMessage repositoryMessage;

    public void saveMessage(MultipartFile file) throws JSONException {
        try {
            long newVersion = repositoryMessage.max() + 1;
            List<Message> messageItems = ExcelMessageHelper.excelToMessage(file.getInputStream(), newVersion);
            repositoryMessage.saveAll(messageItems);

            // MESSAGE
            Message message = new Message();

            // recup last version
            List<Message> allMessage = getLastVersionMessage();

            // upload & construit json
            uploadFile("www/assets/i18n/popup-message/", "popup-message-fr.json", message.getJSON("FR", allMessage),
                    false);
            uploadFile("www/assets/i18n/popup-message/", "popup-message-en.json", message.getJSON("EN", allMessage),
                    false);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    private List<Message> getLastVersionMessage() {
        long version = repositoryMessage.max();
        return repositoryMessage.findByVersion(version);
    }

    public InputStream loadMessage() {
        List<Message> allMessage = getLastVersionMessage();

        ByteArrayInputStream in = ExcelMessageHelper.messageToExcel(allMessage);
        return in;
    }

}
