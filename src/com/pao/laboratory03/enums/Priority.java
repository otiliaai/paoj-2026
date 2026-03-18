package com.pao.laboratory03.enums;

public enum Priority {
    LOW(1, "green"){
        @Override String getEmoji(){
            return "🟢";
        }
    },
    MEDIUM(2, "yellow"){
        @Override String getEmoji(){
            return "🟡";
        }
    },
    HIGH(3, "orange"){
        @Override String getEmoji(){
            return "🟠";
        }
    },
    CRITICAL(4, "red"){
        @Override String getEmoji(){
            return "🔴";
        }
    };

    private int level;
    private String color;
    Priority(int level, String color) {
        this.level = level;
        this.color = color;
    }

    public int getLevel() {
        return this.level;
    }

    public String getColor() {
        return this.color;
    }
    abstract String getEmoji();
}
