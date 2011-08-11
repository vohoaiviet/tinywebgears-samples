package com.tinywebgears.simplegae.webapp;

import java.io.Serializable;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.tinywebgears.simplegae.core.dao.UserRepository;
import com.tinywebgears.simplegae.core.service.DefaultUserServices;
import com.tinywebgears.simplegae.core.service.UserServicesIF;
import com.tinywebgears.simplegae.core.model.User;
import com.vaadin.Application;
import com.vaadin.service.ApplicationContext;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class WebApplication extends Application implements ApplicationContext.TransactionListener
{
    private static ThreadLocal<WebApplication> currentApplication = new ThreadLocal<WebApplication>();

    private final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    private final UserServicesIF userServices;
    private final UserSession session = new UserSession();
    private Boolean initialized = false;
    private Window mainWindow;

    public WebApplication()
    {
        userServices = new DefaultUserServices(DatabaseServices.userRepo);
    }

    /**
     * This method is called by Vaadin application manager once the application is accessed by user.
     */
    public void init()
    {
        setTheme("simplegae");
        getContext().addTransactionListener(this);
    }

    /**
     * See @ApplicationContext.TransactionListener
     */
    public void transactionStart(Application application, Object servletRequest)
    {
        if (application == WebApplication.this)
        {
            currentApplication.set(this);
            // Check the user's login status based on servlet request parameters.
            checkLoginLogoutRequest((HttpServletRequest) servletRequest);
        }
    }

    /**
     * See @ApplicationContext.TransactionListener
     */
    public void transactionEnd(Application application, Object o)
    {
    }

    /**
     * This method is called by Vaadin once the application is closed. This will be called on every request when
     * application is run on GAE.
     */
    @Override
    public void close()
    {
        logger.trace("Application is closing....");
        super.close();
    }

    public UserServicesIF getUserServices()
    {
        return userServices;
    }

    private void checkLoginLogoutRequest(HttpServletRequest request)
    {
        logger.trace("checkLoginLogoutRequest - initialized? " + initialized);
        Principal userPrincipal = request.getUserPrincipal();

        if (initialized
                && ((userPrincipal != null && session.getUsername() != null) || (userPrincipal == null && session
                        .getUsername() == null)))
        {
            return;
        }

        UserService userService = UserServiceFactory.getUserService();

        if (mainWindow == null)
        {
            logger.debug("Creating main window");
            Resource loginUrl = new ExternalResource(userService.createLoginURL(request.getRequestURI()));
            Resource logoutUrl = new ExternalResource(userService.createLogoutURL(request.getRequestURI()));
            mainWindow = new Window("Main Window");
            setMainWindow(mainWindow);
            // TODO: Implement
            // setLoggedIn(false);
            showMessage("Welcome!");
            initialized = true;
        }

        if (request.getUserPrincipal() != null)
        {
            // Login information is provided in the request.
            String userid = request.getUserPrincipal().getName();
            String nickname = userService.getCurrentUser().getNickname();
            logger.info("User logged in: " + userid + " nick name: " + nickname + " admin: "
                    + userService.isUserAdmin());
            session.setSession(userid, userid, userService.isUserAdmin(), nickname);
            try
            {
                User user = userServices.setUsername(userid, userid, nickname);
                // TODO: Implement
                // setLoggedIn(true);
            }
            catch (Exception e)
            {
                logger.error("Unable to log in: " + e);
                showMessage("Unable to log you in: " + e.getLocalizedMessage());
            }
        }
        else
        {
            // No user information is provided in the request.
            if (session.getUsername() != null)
            {
                // Logout is requested.
                logger.info("User logged out: " + session.getUsername());
                showMessage("You successfully logged out.");
                try
                {
                    userServices.clearUser();
                    session.clearSession();
                    // TODO: Implement
                    // setLoggedIn(false);
                }
                catch (Exception e)
                {
                    logger.warn("Error occured while logging out: " + e);
                    showMessage("Couldn't log you out: " + e.getLocalizedMessage());
                }
                finally
                {
                    ((WebApplicationContext) getContext()).getHttpSession().invalidate();
                    // close();
                }
            }
            else
            {
                // Application is initialized without login info.
                logger.debug("Application reloaded");
            }
        }
    }

    /**
     * This method is used by internal components to access global resources/services.
     * 
     * @return The current instance of web application
     */
    public static WebApplication getInstance()
    {
        return currentApplication.get();
    }

    public UserSession getUserSession()
    {
        return session;
    }

    public void showMessage(String description)
    {
        if (mainWindow != null)
            mainWindow.showNotification("", description, Notification.TYPE_HUMANIZED_MESSAGE);
    }

    private static class DatabaseServices implements Serializable
    {
        // Singleton services
        private final static UserRepository userRepo;

        static
        {
            userRepo = new UserRepository();
        }
    }
}
