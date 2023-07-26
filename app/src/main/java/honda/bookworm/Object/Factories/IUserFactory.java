package honda.bookworm.Object.Factories;

import honda.bookworm.Object.User;

public interface IUserFactory {
    User make(String first, String last, String username, String password, String type);
}
