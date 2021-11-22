package ca.concordia.poll.core;

import java.util.HashMap;

public enum PollStatus {

    CREATED(1),
    RUNNING(2),
    RELEASED(3);

    private final int index;
    private final static HashMap<Integer, PollStatus> map = new HashMap<>();

    PollStatus(int i) {
        this.index = i;
    }

    static {
        for (PollStatus status : PollStatus.values()) {
            map.put(status.index, status);
        }
    }

    public static PollStatus valueOf(int index) {
        return (PollStatus) map.get(index);
    }

    public int getIndex() {
        return index;
    }
}
