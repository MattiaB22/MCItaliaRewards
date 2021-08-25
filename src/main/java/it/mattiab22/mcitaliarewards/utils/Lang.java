package it.mattiab22.mcitaliarewards.utils;

import lombok.Getter;
import lombok.Setter;

public enum Lang {

    PRIZE_REDEEMED("prize-redeemed"),
    NEVER_VOTED("never-voted"),
    PRIZES_REMAINING("prizes-remaining"),
    VOTE_LINK("vote-link"),
    HELP_MESSAGE("help-message"),
    GAME_ONLY_COMMAND("game-only-command"),
    INSUFFICIENT_PERMISSION("insufficient-permission"),
    NO_PRIZES_TO_REDEEM("no-prizes-to-redeem");

    private final @Getter String path;
    private @Setter String[] message;

    Lang(String path) {
        this.path = path;
    }

    public String[] get() {
        return message.clone();
    }

    public String[] get(Object... objects) {
        String[] msg = get();
        for (int i = 0; i < msg.length; i++) {
            for (int j = 0; j < objects.length; j++) {
                msg[i] = msg[i].replace("{" + j + "}", objects[j].toString());
            }
        }
        return msg;
    }

}
