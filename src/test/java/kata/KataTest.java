package kata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class KataTest {
    Kata kataUnderTest;
    private String ALICE_MESSAGE1 = "I love the weather today.";
    private String BOB_MESSAGE1 = "Darn! We lost!";
    private String BOB_MESSAGE2 = "Good game though.";
    private String CHARLIE_MESSAGE1 = "I'm in New York today! Anyone wants to have a coffee?";

    @BeforeEach
    void setup() {
        KataRepo.messagesStore.clear();
        KataRepo.userFollowerStore.clear();
        kataUnderTest = new Kata();
    }

    @Test
    void testFollow() {
        User alice = new User("alice");
        User bob = new User("bob");
        kataUnderTest.follow(alice, bob);

        assertEquals(1, KataRepo.userFollowerStore.get("alice").size());
        assertTrue(KataRepo.userFollowerStore.get("alice").contains(bob));
    }

    @Test
    public void testPublishMessage() {
        User alice = new User("alice");
        kataUnderTest.publish(alice, new Message(ALICE_MESSAGE1));

        assertEquals(1, KataRepo.messagesStore.get("alice").size());
        assertEquals(ALICE_MESSAGE1, KataRepo.messagesStore.get("alice").get(0).getText());
    }

    @Test
    public void testGetTimeLine() {
        User bob = new User("bob");
        kataUnderTest.publish(bob, new Message(BOB_MESSAGE1));
        kataUnderTest.publish(bob, new Message(BOB_MESSAGE2));
        List<Message> bobsTimeLine = kataUnderTest.getTimeLine(bob);

        assertEquals(BOB_MESSAGE2, bobsTimeLine.get(0).getText());
        assertEquals(BOB_MESSAGE1, bobsTimeLine.get(1).getText());
    }

    @Test
    public void testGetWallMessages() {
        User charlie = new User("charlie");
        User alice = new User("alice");
        User bob = new User("bob");

        kataUnderTest.follow(charlie, alice);
        kataUnderTest.follow(charlie, bob);
        kataUnderTest.publish(alice, new Message(ALICE_MESSAGE1));
        kataUnderTest.publish(bob, new Message(BOB_MESSAGE1));
        kataUnderTest.publish(bob, new Message(BOB_MESSAGE2));
        kataUnderTest.publish(charlie, new Message(CHARLIE_MESSAGE1));

        List<Message> charliesWall = kataUnderTest.getWall(charlie);
        assertEquals(4, charliesWall.size());
        assertEquals(CHARLIE_MESSAGE1, charliesWall.get(0).getText());
        assertEquals(BOB_MESSAGE2, charliesWall.get(1).getText());
        assertEquals(BOB_MESSAGE1, charliesWall.get(2).getText());
        assertEquals(ALICE_MESSAGE1, charliesWall.get(3).getText());

    }

}
