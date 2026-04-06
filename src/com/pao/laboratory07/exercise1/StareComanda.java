package com.pao.laboratory07.exercise1;

public enum StareComanda {
    PLACED,
    PROCESSED,
    SHIPPED,
    DELIVERED,
    CANCELED;

    public StareComanda nextState() {
        if (this == PLACED)
            return PROCESSED;
        else if (this == PROCESSED)
            return SHIPPED;
        else if (this == SHIPPED)
            return DELIVERED;
        else if (this == DELIVERED)
            return CANCELED;
        return  null;
    }
}
