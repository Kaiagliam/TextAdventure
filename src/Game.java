import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private Parser parser;
    private Player player;
    Item Book  = new Item();
    Item Pizza = new Item();
    Item ArtifactOne = new Item();
    Item ArtifactTwo = new Item();
    Item Scroll = new Item();
    Room museum;
    Room pedestalRoom;
    boolean wantToQuit;
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
        museum = new Room("A place with many ancient things.", "You are now in the museum. In front of you, there are a bunch of different objects around you. Each from a different time in history. In the distance, you can see two large pillars that look like they are from Rome. Your answers lie here.");
        Room djsRestaurant = new Room(" A place to chill and get stuffed.", " D.J’s restaurant is top of the line. It is a very high dining place with a simple aesthetic. On top of that the food is affordable and delicious. The aroma that fills the restaurant is irresistible and just makes you so hungry. People claim once you start eating you can't stop which makes this place so successful. You have to keep eating until you find the missing item.");
        Room boothOne  = new Room("You are in booth one.", "Booth one is watching Black Panther. Too bad there is no one watching this movie. You have reached a dead end. Try looking for another booth since there is nothing to do at this one. ");
        Room boothTwo  = new Room("You are in booth two.", "Booth two has no movie or people. On the other hand, you see two objects that you might be looking for. You might want to take a look at the objects and see what they are. Be careful though. If you already got help, you know what the objects are and what to do with them.");
        pedestalRoom = new Room("You are now in the room that contains the pedestals.","");


        centerSquare.setExit("southwest", iceCreamParlor);
        centerSquare.setExit("northeast", movieTheater);
        centerSquare.setExit("southeast", museum);
        centerSquare.setExit("northwest", djsRestaurant);

        iceCreamParlor.setExit("northeast", centerSquare);
        iceCreamParlor.setExit("east", museum);
        iceCreamParlor.setExit("north", djsRestaurant );

        movieTheater.setExit("south", museum);
        movieTheater.setExit("southwest", centerSquare);
        movieTheater.setExit("west", djsRestaurant);
        movieTheater.setExit("boothOne", boothOne);
        movieTheater.setExit("boothTwo", boothTwo);

        boothOne.setExit("movieTheater", movieTheater);
        boothTwo.setExit("movieTheater", movieTheater);

        museum.setExit("northwest", centerSquare);
        museum.setExit("north", movieTheater);
        museum.setExit("west", iceCreamParlor);
        museum.setExit("PedestalRoom", pedestalRoom);

        pedestalRoom.setExit("Museum", museum);

        djsRestaurant.setExit("south", iceCreamParlor);
        djsRestaurant.setExit("east", movieTheater);
        djsRestaurant.setExit("southeast", centerSquare);


        djsRestaurant.setItem("Pizza", Pizza);
        iceCreamParlor.setItem("Book", Book);
        boothTwo.setItem("ArtifactOne", ArtifactOne);
        boothTwo.setItem("ArtifactTwo", ArtifactTwo);

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
                goRoom(command);
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
            case COMBINE:
                combine(command);
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
        }
        else {
            player.setItem(key, grabItem);
        }
        System.out.println("You have grabbed " + command.getSecondWord());
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
        System.out.println("You have dropped" + command.getSecondWord());
    }
    private void eat(Command command)
    {
        int hungerBar = 0;

        if(!command.hasSecondWord())
        {
            System.out.println("Eat what? ");
            return;
        }

        String eatCommand = command.getSecondWord();
        Item thingToEat = player.getItem(eatCommand);

        if(thingToEat.equals(Pizza)) {
            hungerBar += 1;
        }

        if(hungerBar == 1)
        {
            System.out.println("You are now full. You discover a hidden item in the food you ate. ");
            System.out.println("You gain a scroll. [try reading it] ");
            player.setItem("Scroll", Scroll);
        }
    }
    private void read(Command command)
    {
        if(!command.hasSecondWord())
        {
            System.out.println("Read what? ");
            return;
        }

        String readCommand = command.getSecondWord();
        Item thingToRead = player.getItem(readCommand);

        if(!thingToRead.equals(Book))
        {
            System.out.println("You can't read " + command.getSecondWord());
        }
        else {
            System.out.println("You have read the book.");
            System.out.println("Information in this book includes: ");
            System.out.println("You have to find the missing two artifacts and combine the two so that the artifact is complete.");
        }

        if(!thingToRead.equals(Scroll)) {
            System.out.println("You can't read " + command.getSecondWord());
        }
        if (thingToRead.equals(Scroll)) {
            System.out.println("You have read the scroll.");
            System.out.println("Information in this scroll includes: ");
            System.out.println("You have to go the museum with the complete artifact and place it on one of the pillars in order to escape.");
            System.out.println("The artifacts are in one of the booths in the movie theater.");
        }
    }
    public void combine(Command command) {
        if(!command.hasSecondWord())
        {
            System.out.println("Combine what? ");
            return;
        }

        String combinedCommand = command.getSecondWord();
        Item combinedItem = player.getItem(combinedCommand);


        if(!combinedItem.equals(ArtifactOne))
        {
            System.out.println("You can't apply " + command.getSecondWord());
        }

        String combineArtifact = "";

        if(combinedItem.equals(ArtifactOne))
        {
            Scanner kb = new Scanner(System.in);
            System.out.println("What would you like to apply it with? (an item IN your inventory)");
            combineArtifact = kb.nextLine();
        }
        if(combineArtifact.equals("ArtifactTwo")) {
            player.getItem("ArtifactOne");
            player.getItem("ArtifactTwo");
            player.setItem("CompleteArtifact", new Item ());
            System.out.println("The two artifacts have been combined.");
            System.out.println("They now have become one complete artifact.");
        }
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
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
        if(nextRoom.equals(pedestalRoom)) {
            if (!player.getInventory().containsKey("CompleteArtifact")) {
                System.out.println("The door is locked, until you get the complete artifact this door will not open. ");
                return;
            }
        }
        String LeftOrRight = "";
        if (nextRoom.equals(pedestalRoom)) {
            Scanner kb = new Scanner(System.in);
            System.out.println("You are now in the pedestal room.");
            System.out.println("There is a pedestal on the left and right. Place the COMPLETE artifact on the right pedestal. You will die if you place it on the wrong one.");
            System.out.println("Which pedestal would you like to place the COMPLETE artifact on ? [Left/Right] ");
            LeftOrRight = kb.nextLine();
        }
        if(LeftOrRight.equals("Left"))
        {
            if(player.getInventory().containsKey("CompleteArtifact"))
            {
                System.out.println("You lose because you have placed it on the wrong pedestal.");
                System.out.println("");
                wantToQuit = true;
            }
        }
        if(LeftOrRight.equals("Right"))
        {
            if(player.getInventory().containsKey("CompleteArtifact"))
            {
                System.out.println("Congrats, you have won the game and have escaped.");
                System.out.println("");
                wantToQuit =  true;
            }
        }
    }
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to my text adventure game!");
        System.out.println("You are in a make-shift world that you have to escape. You will go around the map and try to find different items and clues that can help you escape.");
        System.out.println("Type\"help\" if you need assistance");
        System.out.println();
        System.out.println("We will print a long room description here");
        System.out.println("In order to win you have to bring the complete artifact back to the museum and place it on the correct pedestal.");
        System.out.println("You lose if you place it on the wrong one.");
    }
}
