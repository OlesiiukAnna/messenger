package com.epam.ld.module2.testing;


import com.epam.ld.module2.testing.template.TemplateEngine;

import java.util.List;

/**
 * The type Messenger.
 */
public class Messenger {

    private MailServer mailServer;
    private TemplateEngine templateEngine;

    public Messenger(MailServer mailServer,
                     TemplateEngine templateEngine) {
        this.mailServer = mailServer;
        this.templateEngine = templateEngine;
    }

    public String receiveMessage(String inputFileName) {
        return mailServer.receive(inputFileName);
    }

    public void sendMessage(Client client, String tag, List<String> values, String outputFileName) {
        String messageContent = templateEngine.generateMessage(tag, values);
        mailServer.send(client.getAddresses(), messageContent, outputFileName);
    }

}
