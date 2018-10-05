package com.toplin.handlersample.thread_communication.domain;

public enum Job {
    ONE_SHOT(0),
    LONG_SHOT(1);

    private int value;

    private static Job ALL[] = values();

    Job(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Job getJobByPosition(int position) {
        for (Job job : ALL) {
            if (job.getValue() == position) {
                return job;
            }
        }
        return null;
    }
}
