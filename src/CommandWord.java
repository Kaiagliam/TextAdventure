public enum CommandWord {
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), LOOK("look"), GRAB("grab"), DROP("drop"), READ("read"), CRAFT("craft"), EAT("eat");
    private String commandString;
    CommandWord(String commandString) {
        this.commandString = commandString;
    }
    public String toString() {
        return commandString;
    }
}
