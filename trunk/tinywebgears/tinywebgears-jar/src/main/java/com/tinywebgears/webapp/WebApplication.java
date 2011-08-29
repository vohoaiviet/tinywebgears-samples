package com.tinywebgears.webapp;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.Application;
import com.vaadin.service.ApplicationContext;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class WebApplication extends Application implements ApplicationContext.TransactionListener
{
    private static ThreadLocal<WebApplication> currentApplication = new ThreadLocal<WebApplication>();

    private final Logger logger = LoggerFactory.getLogger(WebApplication.class);

    private Window mainWindow;
    private HorizontalLayout headerLayout;
    private VerticalLayout mainLayout;

    public WebApplication()
    {
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
            initUiComponents();
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

    /**
     * This method is used by internal components to access global resources/services.
     * 
     * @return The current instance of web application
     */
    public static WebApplication getInstance()
    {
        return currentApplication.get();
    }

    public void showMessage(String description)
    {
        if (mainWindow != null)
            mainWindow.showNotification("", description, Notification.TYPE_HUMANIZED_MESSAGE);
    }

    private void initUiComponents()
    {
        mainWindow = new Window("Simple GAE Application using Vaadin");
        mainWindow.setSizeFull();
        setMainWindow(mainWindow);

        mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();
        mainWindow.addComponent(mainLayout);

        headerLayout = new HorizontalLayout();
        headerLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
        headerLayout.addComponent(new Label("Tuatara Addressbook"));
        mainLayout.addComponent(headerLayout);

        try
        {
            loadTuatara();
        }
        catch (IOException e)
        {
            showMessage("An exception occurred while displaying Tuatara Addressbook: " + e.getLocalizedMessage());
        }
    }

    private void loadTuatara() throws IOException
    {
        URL url = new URL("http://tuatara-addressbook.appspot.com/");
        Embedded browser = new Embedded("", new ExternalResource(url));
        browser.setType(Embedded.TYPE_BROWSER);
        browser.setSizeFull();
        mainLayout.addComponent(browser);
    }
}
