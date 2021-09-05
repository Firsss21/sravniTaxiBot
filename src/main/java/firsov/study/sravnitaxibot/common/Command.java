package firsov.study.sravnitaxibot.common;

import javax.annotation.Resource;
import java.util.Locale;

public enum Command {
    START("начать"),
    HELP("список команд"),
    CANCEL("отмена"),
    SETCITY("поменять город"),
    FIND("сравнить цены"),
    CITY("узнать город");

    private String description;

    public String getDescription() {
        return "/" + this.name().toLowerCase(Locale.ROOT) + " - " + description;
    }

    Command(String description) {
        this.description = description;
    }
}
