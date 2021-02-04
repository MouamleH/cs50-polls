package me.mouamle.cs50_polls;

import me.mouamle.cs50_polls.bot.PoolsBot;
import me.mouamle.cs50_polls.util.KeyboardLoader;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws TelegramApiException {
        logger.info("Loading keyboard");
        KeyboardLoader.loadStaticKeyboard();
        logger.info("Keyboard loaded");

        final Server server = new Server(new InetSocketAddress(5400));
        EventBus.getDefault().register(server);
        new Thread(server, "sync-server").start();

        logger.info("Starting bot...");
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new PoolsBot());
        logger.info("Bot started!");
    }
}
