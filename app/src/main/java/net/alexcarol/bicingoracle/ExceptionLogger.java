package net.alexcarol.bicingoracle;

import android.util.Log;

public class ExceptionLogger {
    private static final ExceptionLogger ourInstance = new ExceptionLogger();

    public static ExceptionLogger getInstance() {
        return ourInstance;
    }

    private ExceptionLogger() {
    }

    public void log(String tag, String message, Exception e) {
        Log.e(tag, message, e);
        // TODO implement some logging mecanism (log to server???)
    }
}
