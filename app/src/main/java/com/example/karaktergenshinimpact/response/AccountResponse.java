/*
 * *
 *  * Created by zuhdi on 12/22/22, 8:35 AM
 *  * Copyright (c) 2022 . All rights reserved.
 *  * Last modified 12/16/22, 9:11 AM
 *
 */

package com.example.karaktergenshinimpact.response;

public class AccountResponse {
    private String status, messages;

    public AccountResponse(String status, String messages) {
        this.status = status;
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}
