package kata;

import java.time.LocalDate;
import java.util.*;

public class Kata {


    public void follow(User user, User follower) {
        String userName = user.getUserName();
        Set<User> followers = KataRepo.userFollowerStore.get(user.getUserName());
        if (followers == null) {
            followers = new HashSet();
            KataRepo.userFollowerStore.put(user.getUserName(), followers);
        }
        followers.add(follower);
    }

    public void publish(User user, Message message) {
        String userName = user.getUserName();
        List<Message> messages = KataRepo.messagesStore.get(userName);
        if (messages == null) {
            messages = new ArrayList();
            KataRepo.messagesStore.put(userName, messages);
        }
        message.setPublisher(user);
        message.setCreatedAt(LocalDate.now());
        messages.add(0, message);
    }

    public List<Message> getTimeLine(User user) {
        return KataRepo.messagesStore.get(user.getUserName());
    }

    public List<Message> getWall(User user) {
        List<Message> wallMessages = new ArrayList();
        Set<User> followers = KataRepo.userFollowerStore.get(user.getUserName());
        wallMessages.addAll(KataRepo.messagesStore.get(user.getUserName()));
        followers.forEach(follower -> wallMessages.addAll(KataRepo.messagesStore.get(follower.getUserName())));
        Collections.sort(wallMessages, new Comparator<Message>() {
            @Override
            public int compare(Message message, Message message2) {
                return message.getCreatedAt().compareTo(message2.getCreatedAt());
            }
        });
        return wallMessages;
    }

}
