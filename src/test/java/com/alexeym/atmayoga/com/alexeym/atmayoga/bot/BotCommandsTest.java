package com.alexeym.atmayoga.com.alexeym.atmayoga.bot;

import com.alexeym.atmayoga.bot.command.StartCommand;
import org.junit.Test;

public class BotCommandsTest {

    @Test
    public void testStartCommandText() throws Exception {
        StartCommand startCommand = new StartCommand();
        String text = startCommand.getStartCommandText("asdf");
        System.out.println(text);
    }

}
