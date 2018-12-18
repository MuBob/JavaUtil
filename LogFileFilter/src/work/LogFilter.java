package work;

import content.Config;

public class LogFilter {
    private String matchWord;

    public LogFilter(String matchWord) {
        this.matchWord = matchWord;
    }

    public String getMatchWord(){
        return matchWord;
    }

}
