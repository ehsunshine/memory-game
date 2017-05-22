package ir.jaryaan.matchmatch.utils;

import android.os.Handler;

/**
 * Created by E.Mehranvari on 5/22/2017.
 */

public class CountDown {
    private final Handler handler;
    private long milliseconds;
    private long interval;
    private Runnable counter;

    public CountDown(int seconds, long interval) {
        this.milliseconds = seconds * 1000;
        this.interval = interval;
        handler = new Handler();
    }

    public void start(CountDownTimerListener countDownTimerListener) {
        counter = new Runnable() {

            public void run() {
                if (milliseconds < 0) {
                    countDownTimerListener.onFinished();
                } else {
                    countDownTimerListener.onTick(milliseconds);
                    milliseconds -= interval;
                    handler.postDelayed(this, interval);
                }
            }
        };

        handler.postDelayed(counter, interval);
    }

    public void stop() {
        handler.removeCallbacks(counter);
        counter = null;
    }

    public interface CountDownTimerListener {
        void onTick(long milliseconds);

        void onFinished();
    }
}
