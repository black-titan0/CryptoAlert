package rule_utilities.stat_utilities.oprators;

public enum OperatorType {
    MOVING_AVERAGE("mean"),
    EXPONENTIAL_MOVING_AVERAGE("ema"),
    VARIANCE("var");
    private String text;

    OperatorType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static OperatorType fromString(String text) {
        for (OperatorType type : OperatorType.values()) {
            if (type.text.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
