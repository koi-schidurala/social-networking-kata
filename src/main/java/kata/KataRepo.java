package kata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KataRepo {
    public static Map<String, List<Message>> messagesStore = new HashMap();
    public static Map<String, Set<User>> userFollowerStore = new HashMap();
}
