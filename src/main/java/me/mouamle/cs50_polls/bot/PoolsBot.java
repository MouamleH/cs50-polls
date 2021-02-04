package me.mouamle.cs50_polls.bot;

import me.mouamle.cs50_polls.util.KeyboardLoader;
import me.mouamle.cs50_polls.util.Projects;
import me.mouamle.cs50_polls.util.VotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class PoolsBot extends AbilityBot {

    private static final Logger logger = LoggerFactory.getLogger(PoolsBot.class);

    public PoolsBot() {
        super("", "");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()) {
            final CallbackQuery callbackQuery = update.getCallbackQuery();

            final User from = callbackQuery.getFrom();
            final String voteTarget = callbackQuery.getData();
            if (!Projects.isProject(voteTarget)) {
                logger.warn("User: {} id: {} tried to vote for ({}) which is not a project",
                        from.getFirstName(), from.getId(), voteTarget);
                final AnswerCallbackQuery answer = AnswerCallbackQuery.builder()
                        .callbackQueryId(callbackQuery.getId())
                        .showAlert(true)
                        .cacheTime(0)
                        .text("عذراً المشروع الذي صوتتَ عليه غير موجود")
                        .build();
                silent.execute(answer);
                return;
            }

            logger.info("Vote for ({}) from user {}, id:{}", voteTarget, from.getFirstName(), from.getId());
            VotesService.vote(from.getId(), voteTarget);

            final AnswerCallbackQuery answer = AnswerCallbackQuery.builder()
                    .callbackQueryId(callbackQuery.getId())
                    .showAlert(true)
                    .cacheTime(0)
                    .text(String.format("شكرا لك, لقد تم التصويت على مشروع “%s”", voteTarget))
                    .build();
            silent.execute(answer);

            final String message = String.format("شكرا لك, لقد تم التصويت على مشروع " +
                            "*\"%s\"*\n\n" +
                            "*ملاحظة*: يمكنك التصويت لمشروع واحد فقط, في حال اردت تحويل صوتك الى مشروع آخر, يرجى الضغط على المشروع الّذي تريد تحويل صوتك اليه.",
                    voteTarget);
            final SendMessage sendMessage = new SendMessage(String.valueOf(from.getId()), message);
            sendMessage.enableMarkdown(true);
            silent.execute(sendMessage);
            return;
        }
        super.onUpdateReceived(update);
    }

    public Ability start() {
        return Ability.builder()
                .name("start")
                .info("meh")
                .privacy(Privacy.PUBLIC)
                .locality(Locality.USER)
                .action(ctx -> {
                    SendMessage sendMessage = SendMessage.builder()
                            .chatId(String.valueOf(ctx.chatId()))
                            .replyMarkup(new InlineKeyboardMarkup(KeyboardLoader.getKeyboard()))
                            .text("مرحبا واهلا بيك ب CS50x Iraq voting system, صوت لمشروعك المفضل, الّذي تراه يستحق ان يكون من الفائزين\n" +
                                    "يمكنك التصويت للمشروع فقط بالضغط على اسم المشروع.")
                            .build();
                    silent.execute(sendMessage);
                })
                .build();
    }

    public int creatorId() {
        return 121414901;
    }

}
