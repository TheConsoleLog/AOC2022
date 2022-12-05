package input;

import java.io.Serializable;

public class Cookie implements Serializable {

    private String sessionCookie;

    public Cookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cookie cookie)) return false;

        return getSessionCookie() != null ? getSessionCookie().equals(cookie.getSessionCookie())
                : cookie.getSessionCookie() == null;
    }

    @Override
    public int hashCode() {
        return sessionCookie != null ? sessionCookie.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "SESSION_COOKIE='" + sessionCookie + '\'' +
                '}';
    }
}
