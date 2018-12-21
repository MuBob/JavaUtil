package content;

import util.Log;
import work.*;
import work.inter.LogLineCallback;
import work.inter.LogReadCallback;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

public class Environment{
    public static void main(String[] args){
        Config.WORD_FILTER="PlaySerTAG";
        ReadThread.getInstance().readFileAll();
    }
}
