public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
    public Game() {
        player = new Player();
        parser = new Parser();
    }

    public static void main (String[] args) {
        Game game = new Game();
        game.createRooms();
        game.play();
    }
    private void createRooms() {
        Room centerSquare = new Room("The center square is a big space that is used for recreational use.", "The center square is split up into four different sections. Between those four sections is a large fountain in the center with a cross at the top . Each of the four sections is made up of grass and has a statue laying around. One section is where people go to walk their dog, talk, and relax. One holds basketball and tennis courts. One holds a football/soccer field. The last one has its own little park. ");
        Room iceCreamParlor= new Room("A cold destination with a delicious ending", " The Ice Cream Parlor has been there for over 50 years. It is usually always packed with customers. It has an old 90’s vibe to it. An item can be found here");
        Room movieTheater = new Room(" A place where multiple people gather to see different types of movies.", "The movie theater is made up of 2 booths. Each booth is watching a different movie. When you walk in you can smell and hear popcorn being made. You can hear laughter and screams around you as the walls of the theater are thin. There is someone named Simon that you must find in one of the booths that you must talk to for some information.");
        Room flowerShop = new Room("An old shop that smells as if you are in a forest.", "The Flower shop was an old shack that belonged to an old man that said crazy stories about many different things. It has one entrance that’s in the front. When you are inside you are greeted with plenty of different flowers around you. The aroma is very forest-like as there are different types of flowers. The owner claims “that his flowers are key to life”.");
        Room djsRestaurant = new Room(" A place to chill and get stuffed.", " D.J’s restaurant is top of the line. It is a very high dining place with a simple aesthetic. On top of that the food is affordable and delicious. The aroma that fills the restaurant is irresistible and just makes you so hungry. People claim once you start eating you can't stop which makes this place so successful. You have to keep eating until you find the missing item.");
        Room boothOne  = new Room("You are in booth one.", "Booth one is watching Black Panther. Too bad there is no one watching this movie. Try looking for another booth since there is nothing to do at this one. ");
        Room boothTwo  = new Room("You are in booth two.", "Booth two has no movie or people except one person named Simon. Simon is a little crazy, but you must talk to him if you want to continue to try to find the other items. Be aware of Simon though. We don't know what he could do. ");

        centerSquare.setExit("southwest", iceCreamParlor);
        centerSquare.setExit("northeast", movieTheater);
        centerSquare.setExit("southeast", flowerShop);
        centerSquare.setExit("northwest", djsRestaurant);

        iceCreamParlor.setExit("north", centerSquare);
        iceCreamParlor.setExit("east", flowerShop);
        iceCreamParlor.setExit("north", djsRestaurant );

        movieTheater.setExit("south", flowerShop);
        movieTheater.setExit("southwest", centerSquare);
        movieTheater.setExit("west", djsRestaurant);
        movieTheater.setExit("go to booth 1", boothOne );
        movieTheater.setExit("go to booth 2", boothTwo);


        flowerShop.setExit("north", movieTheater);
        flowerShop.setExit("east", iceCreamParlor);
        flowerShop.setExit("northeast", centerSquare);

        djsRestaurant.setExit("south", iceCreamParlor);
        djsRestaurant.setExit("west", movieTheater);
        djsRestaurant.setExit("southwest", centerSquare);


        Item book  = new Item();
        Item flower = new Item();
        Item food = new Item();
        Item artifact = new Item();
        Item obj2 = new Item();
        player.setItem("one", book);
        centerSquare.setItem("two", obj2);


        currentRoom = centerSquare;
    }
    public void play() {
        printWelcome();
        boolean finished = false;
        while(!finished) {
            Command command = parser.getCommand();
            finished = processedCommand(command);
        }
        System.out.println("Thanks for playing!");
    }
    private boolean processedCommand(Command command) {
        boolean wantToQuit = false;
        CommandWord commandWord = command.getCommandWord();
        switch(commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean");
            break;
            case HELP:
                printHelp();
            break;
            case GO:
            break;
            case QUIT:
            wantToQuit = quit(command);
            break;
            case LOOK:
                look(command);
                break;
            case DROP:
                drop(command);
                break;
            case GRAB:
                grab(command);
                break;
            case EAT:
                eat(command);
                break;
            case READ:
                read(command);
                break;
            case CRAFT:
                craft(command);
                break;
        }
        return wantToQuit;
    }
    private void grab(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Grab what?");
            return;
        }
        String key = command.getSecondWord();
        Item grabItem = currentRoom.getItem(key);

        if (grabItem == null) {
            System.out.println("You can't grab " + command.getSecondWord());
        } else {
            player.setItem(key, grabItem);
        }
    }
    private void drop(Command command) {
        if(!command.hasSecondWord()) {
            System.out.print("Drop what?");
            return;
        }
        String key = command.getSecondWord();
        Item dropItem = player.getItem(key);
        if(dropItem == null) {
            System.out.print("You cant drop" + command.getSecondWord());
        }
        else {
            currentRoom.setItem(key, dropItem);
        }
    }
    private void eat(Command command){

    }
    private void read(Command command) {

    }
    private void craft(Command command){

    }
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("You are in a garden maze");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    private void look (Command command) {
        if (command.hasSecondWord()) {
            System.out.println("You cant look at" + command.getSecondWord());
            return;
        }
        System.out.println(currentRoom.getLongDescription());
        System.out.println(player.getItemString());
    }
    private boolean quit (Command command) {
        if(command.hasSecondWord()) {
            System.out.println("You cant quit" + command.getSecondWord());
                    return false;
        }
        else {
            return true;
        }
    }
    private void goRoom (Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getsShortDescription());
        }
    }
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You are in a make-shift world that you have to escape. You will go around the map and try to find different items and clues that can help you escape.");
        System.out.println("Type\"help\" if you need assistance");
        System.out.println();
        System.out.println("We will print a long room description here");
    }
}
