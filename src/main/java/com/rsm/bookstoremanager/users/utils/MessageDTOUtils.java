package com.rsm.bookstoremanager.users.utils;

import com.rsm.bookstoremanager.users.dto.MessageDTO;
import com.rsm.bookstoremanager.users.entity.User;

public class MessageDTOUtils {
    public static MessageDTO creationMessage(User updateduser, String action) {
        return returnMessage(updateduser, "created");
    }

    public static MessageDTO updatedMessage(User updateduser, String action) {
        return returnMessage(updateduser, "updated");
    }

    public static MessageDTO returnMessage(User updateduser, String action) {
        String createdUserName = updateduser.getUsername();
        Long createdId = updateduser.getId();
        String createduserMessage = String.format("User %s with ID %s was successfully %s", createdUserName, createdId, action);
        return MessageDTO.builder().message(createduserMessage).build();
    }
}
