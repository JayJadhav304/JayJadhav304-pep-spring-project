package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Integer postedBy, String messageText, long timePostedEpoch) {

        if (messageText == null || messageText.isBlank() || messageText.length() > 255) {
            throw new IllegalArgumentException("Invalid message text");
        }
        try{    
        Message message = new Message(postedBy, messageText, timePostedEpoch );
        return messageRepository.save(message);
        }catch(DataIntegrityViolationException e){
            throw new IllegalArgumentException("Invalid data provided", e);
        }catch(Exception e){
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(Integer messageId) {
        return messageRepository.findById(messageId);
    }

    public List<Message> getMessagesByUserId(Integer postedBy) {
        return messageRepository.findAllByPostedBy(postedBy);
    }

    public void deleteMessageById(Integer messageId) {
        messageRepository.deleteById(messageId);
    }

    public Message updateMessage(Integer messageId, String messageText) {
        if(messageText == null || messageText.isBlank() || messageText.length() > 255){
            throw new IllegalArgumentException("Invalid message text");
        }
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isPresent()) {
            Message message = existingMessage.get();
            message.setMessageText(messageText);
            return messageRepository.save(message);
        } else {
            throw new IllegalArgumentException("Invalid message text or message ID");
        }
    }

}
