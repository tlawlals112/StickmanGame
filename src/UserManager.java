import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, String> users; // 사용자 정보 저장 (username, password)

    public UserManager() {
        users = new HashMap<>();
        // 초기 사용자 등록
        users.put("user1", "password1");
        users.put("user2", "password2");
    }

    public boolean loginUser(String username, String password) {
        String storedPassword = users.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    public boolean addUser(String username, String password) {
        if (users.containsKey(username)) {
            return false; // 이미 존재하는 사용자
        }
        users.put(username, password);
        return true; // 사용자 등록 성공
    }
}
