import java.util.*;

public class AssassinManager {
    private AssassinNode frontKillRing; // Linked list holds the people in the kill ring
    private AssassinNode currentAssassin; // Linked list holds the current people
    private AssassinNode prevAssassin; // Linked List that holds the previous people
    private AssassinNode frontGraveyard; // Linked list holds the people who are dead

    // This method is for constructing an assassin manager object.
    // Takes in a list of names to connect together to form our kill ring
    // If 'names' is empty then throw new IllegalArgumentException
    // There should be atleast one name
    // Builds a chain of targets in form of a kill Ring
    public AssassinManager(List<String> names) {
        if (names.isEmpty()) {
            throw new IllegalArgumentException();
        }
        for (int i = names.size() - 1; i >= 0; i--) {
            frontKillRing = new AssassinNode(names.get(i), frontKillRing);
        }

    }

    // This method prints the names of the people in the kill ring, one per
    // line, indented four spaces (not a tab), with output of the form “<name> is
    // stalking <name>”. If there is only one person in the ring, it should report
    // that the person is stalking themselves (e.g., x is stalking x”).
    public void printKillRing() {
        currentAssassin = frontKillRing;
        while (currentAssassin.next != null) {
            System.out.println("    " + currentAssassin.name + " is stalking " 
                                                            + currentAssassin.next.name);
            currentAssassin = currentAssassin.next;
        }
        System.out.println("    " + currentAssassin.name + " is stalking " + frontKillRing.name);
    }

    // This method prints the names of the people in the graveyard, one per
    // line, indented four spaces (not a tab), with output of the form “<name> was
    // killed by <name>”. It prints the names in reverse kill order (most
    // recently killed first, then next more recently killed, and so on). It 
    // produces no output if the graveyard is empty.
    public void printGraveyard() {
        currentAssassin = frontGraveyard;
        while (currentAssassin != null) {
            System.out.println("    " + currentAssassin.name + " was killed by " 
                                                                + currentAssassin.killer);
            currentAssassin = currentAssassin.next;
        }
    }

    // This method returns true if the given name is in the current kill ring and
    // returns false otherwise. It also ignores case in comparing names.
    public boolean killRingContains(String name) {
        return doesListContains(name, frontKillRing);
    }

    // This method returns true if the given name is in the current graveyard and
    // returns false otherwise. It also ignores case in comparing names.
    public boolean graveyardContains(String name) {
        return doesListContains(name, frontGraveyard);
    }

    // Private method to reduce Redundancy
    // Returns true if name is in the list also ignoring the casing
    // Returns false if condition not met
    private boolean doesListContains(String name, AssassinNode currentAssassin) {
        boolean doesNotContain = false;
        while (currentAssassin != null) {
            if (currentAssassin.name.equalsIgnoreCase(name)) {
                return !doesNotContain;
            }
            currentAssassin = currentAssassin.next;
        }
        return doesNotContain;
    }

    // This method returns true if the game is over (i.e., if the kill ring has just
    // one person in it) and returns false otherwise.
    public boolean gameOver() {
        return frontKillRing.next == null;
    }
    
    // This method returns the name of the winner of the game. It returns null
    // if the game is not over.
    public String winner() {
        if (!gameOver()) {
            return null;
        }
        return frontKillRing.name;
    }

    // This method records the killing of the person with the given name,
    // transferring the person from the kill ring to the graveyard. This operation
    // should not change the kill ring order of printKillRing (i.e., whoever used to
    // be printed first should still be printed first unless that’s the person who
    // was killed, in which case the person who used to be printed second should now
    // be printed first). It should throw an IllegalArgumentException if the given
    // name is not part of the current kill ring and it should throw an
    // IllegalStateException if the game is over (it doesn’t matter which it throws
    // if both are true). It should ignore case in comparing names.
    public void kill(String name) {
        currentAssassin = frontKillRing;
        prevAssassin = frontGraveyard;
        if (!killRingContains(name)) {
            throw new IllegalArgumentException();
        } else if (gameOver()) {
            throw new IllegalStateException();
        }
        if (currentAssassin.name.equalsIgnoreCase(name)) {
            prevAssassin = currentAssassin;
            while (currentAssassin.next != null) {
                currentAssassin = currentAssassin.next;
            }
            frontKillRing = frontKillRing.next;
        } else {
            while (!currentAssassin.next.name.equalsIgnoreCase(name)) {
                currentAssassin = currentAssassin.next;
            }
            prevAssassin = currentAssassin.next;
            currentAssassin.next = currentAssassin.next.next;
        }
        prevAssassin.killer = currentAssassin.name;
        prevAssassin.next = frontGraveyard;
        frontGraveyard = prevAssassin;
    }
}