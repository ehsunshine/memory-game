package ir.jaryaan.matchmatch.utils.scheduler;

import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * Created by ehsun on 5/12/2017.
 */

public interface SchedulerProvider {
    @NonNull
    Scheduler getIoScheduler();

    @NonNull
    Scheduler getComputationScheduler();

    @NonNull
    Scheduler getMainScheduler();

}
