package org.screenplay.context;

public class ScenarioContext {

    private static final ThreadLocal<ScenarioContext> INSTANCE =
            ThreadLocal.withInitial(ScenarioContext::new);

    private String email;
    private String password;
    private String username;
    private String ticketTitle;
    private String ticketDescription;

    private ScenarioContext() {
    }

    public static ScenarioContext get() {
        return INSTANCE.get();
    }

    public static void reset() {
        INSTANCE.remove();
    }

    public String getEmail()                        { return email; }
    public void   setEmail(String email)            { this.email = email; }

    public String getPassword()                     { return password; }
    public void   setPassword(String password)      { this.password = password; }

    public String getUsername()                     { return username; }
    public void   setUsername(String username)      { this.username = username; }

    public String getTicketTitle()                  { return ticketTitle; }
    public void   setTicketTitle(String t)          { this.ticketTitle = t; }

    public String getTicketDescription()            { return ticketDescription; }
    public void   setTicketDescription(String d)    { this.ticketDescription = d; }
}
