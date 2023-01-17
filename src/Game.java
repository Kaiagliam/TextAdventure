public class Game {
    private Room currentRoom;
    public Game() {
    }
    public static void main (String[] args) {
        Game game = new Game();
        game.createRooms();
        game.play();
    }
    private void createRooms() {
        Room centerGarden = new Room("Short description")
        Room northGarden = new Room("Short description")
        Room southGarden = new Room("Short description")
        Room eastGarden = new Room("Short description")
        Room westGarden = new Room("Short description")
    }
    public void play() {
        printWelcome();
        boolean finished = false;
        while(!finished) {

        }
        System.out.println("Thanks for playing!");
    }
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You will find yourself in a garden maze, desperate to escape!");
        System.out.println("Type\"help\" if you need assistance");
        System.out.println();
        System.out.println("We will print a long room description here");
    }
}
