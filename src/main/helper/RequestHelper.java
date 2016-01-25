package main.helper;

import main.command.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Additional class uses in servlet.
 * Provide mapping for commands according to the business logic.
 * Class implements Singleton pattern.
 */
public class RequestHelper {
    /**
     * Instance of this class.
     */
    private static RequestHelper instance = null;
    /**
     * HashMap contains mapping for commands.
     */
    private Map<String, Command> commands = new HashMap<>();

    /**
     * Empty constructor. Create mapping.
     */
    private RequestHelper(){
        commands.put("logout", new NoCommand());
        commands.put("register", new RegisterCommand());
        commands.put("login", new LoginCommand());
        commands.put("addPublication", new AddPublicationCommand());
        commands.put("addSubscription", new AddToSubscriptionCommand());
        commands.put("confirmSubscription", new ConfirmSubscriptionCommand());
        commands.put("deleteSubscriptionPart", new DeleteSubscriptionPartCommand());
        commands.put("extendSubscription", new ExtendSubscriptionCommand());
        commands.put("interruptSubscription", new InterruptSubscriptionCommand());
    }

    /**
     * Determine command from client request.
     * @param request client request
     * @return corresponding command implementation.
     */
    public synchronized Command getCommand(HttpServletRequest request){
        String action = request.getParameter("command");
        Command command = commands.get(action);

        if (command == null) {
            command = new NoCommand();
        }
        return command;
    }

    /**
     * @param request client's request
     * @return resource bundle with correct locale.
     */
    public static synchronized ResourceBundle getResourceBundle(HttpServletRequest request){
        String locale = (String) request.getSession().getAttribute("language");
        ResourceBundle bundle = ResourceBundle.getBundle("i18n.text", new Locale(locale));
        return bundle;
    }

    /**
     * @return instance of this class.
     */
    public synchronized static RequestHelper getInstance(){
        if (instance == null) return new RequestHelper();
        return instance;
    }
}
