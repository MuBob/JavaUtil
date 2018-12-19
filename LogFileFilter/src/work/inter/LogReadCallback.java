package work.inter;

import work.LogRead;

public interface LogReadCallback {
    void onComplete(LogRead readRunnable, String readFileName);
}
