package com.epam.ld.module2.testing;

import com.epam.ld.module2.testing.template.Template;
import com.epam.ld.module2.testing.template.TemplateEngine;
import com.epam.ld.module2.testing.util.CustomReader;
import com.epam.ld.module2.testing.util.CustomWriter;
import com.epam.ld.module2.testing.util.MessageParser;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        //init()
        Template template = new Template();
        template.setTemplates(getTemplates());

        MailServer mailServer = new MailServer(new CustomReader(), new CustomWriter());

        TemplateEngine templateEngine = new TemplateEngine(template);
        templateEngine.setTemplate(template);

        Messenger messenger = new Messenger(mailServer, templateEngine);

        //get message
        String incomeMessage = messenger.receiveMessage(getInputFileName(args));

        //parse
        MessageParser messageParser = new MessageParser(template);
        String tag = messageParser.extractTag(incomeMessage);
        List<String> values = messageParser.extractValues(incomeMessage);

        //send message
        messenger.sendMessage(createclient(), tag, values, getOutputFileName(args));
    }

    private static String getInputFileName(String[] args) {
        return getFileName(args, 0);
    }

    private static String getOutputFileName(String[] args) {
        return getFileName(args, 1);
    }

    private static String getFileName(String[] args, int fileArg) {
        String fileName = null;
        int minArgLength = 2;
        if (args.length >= minArgLength) {
            fileName = args[fileArg];
        }
        return fileName;
    }

    private static Client createclient() {
        Client client = new Client();
        client.setAddresses("some@address");
        return client;
    }

    private static Map<String, String> getTemplates() {
        return Map.of("Greeting", "Welcome to the world of tomorrow, %1$s %2$s",
                "Say hello", "Hey %1$s, I'm your far away relative and now I'm awake");
    }

}
