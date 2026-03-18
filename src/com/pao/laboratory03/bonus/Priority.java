package com.pao.laboratory03.bonus;
// * 2. Priority — cu câmpuri (int level, double multiplier):
//        *    - LOW(1, 1.0), MEDIUM(2, 1.5), HIGH(3, 2.0), CRITICAL(4, 3.0)
//        *    - Metodă: double calculateScore(int baseDays)
// *      → returnează baseDays * multiplier (scor de urgență)
enum Priority {
    LOW(1, 1.0),
    MEDIUM(2, 1.5),
    HIGH(3, 2.0),
    CRITICAL(4, 3.0);

    private final int level;
    private final double multiplier;

    Priority(int level, double multiplier){
        this.level=level;
        this.multiplier = multiplier;
    }

    double calculateScore(int baseDays){
        return baseDays * multiplier;
    }

}
