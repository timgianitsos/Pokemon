class Color {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[93m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_RED_HIGHLIGHT = "\u001B[0;30;41m";
    public static final String ANSI_BLUE_HIGHLIGHT = "\u001B[0;37;44m";

    public static String severityColor(int current, int capacity) {
        //https://gaming.stackexchange.com/a/331506
        return current <= 0 ? Color.ANSI_RED_HIGHLIGHT: current > capacity * 0.5 ? Color.ANSI_GREEN:
            current > capacity * 0.2 ? Color.ANSI_YELLOW: Color.ANSI_RED;
    }
}
