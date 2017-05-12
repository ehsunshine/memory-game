package ir.jaryaan.matchmatch.utils.scheduler;

import android.support.annotation.NonNull;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ehsun on 5/12/2017.
 */

public class DefaultSchedulerProvider implements SchedulerProvider {

    @NonNull
    @Override
    public Scheduler getIoScheduler() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler getMainScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
