package iwo.wintech.todoapp.persistence.dto;

import iwo.wintech.todoapp.persistence.model.User;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public record AuthenticateResponse(
        User user,
        boolean isAuthenticated
) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static AuthenticateResponse authenticated(final User user) {
        return new AuthenticateResponse(user, true);
    }

    public static AuthenticateResponse unAuthenticated() {
        return new AuthenticateResponse(null, false);
    }

    public List<String> roles() {
        return new ArrayList<>(user.getRoles());
    }

}
