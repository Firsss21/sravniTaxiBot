package firsov.study.sravnitaxibot.common;

import java.util.Locale;

public enum Command {
    START("начать"),
    HELP("список команд"),
    CANCEL("отмена"),
    FIND("сравнить цены");

    private String description;

    public String getDescription() {
        return "/" + this.name().toLowerCase(Locale.ROOT) + " - " + description;
    }

    Command(String description) {
        this.description = description;
    }
}
