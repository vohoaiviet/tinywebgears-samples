package com.tinywebgears.simplegae.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.appengine.api.datastore.Key;
import com.tinywebgears.simplegae.core.dao.DataPersistenceException;
import com.tinywebgears.simplegae.core.dao.UserRepository;
import com.tinywebgears.simplegae.core.model.User;

public class DefaultUserServices extends AbstractService implements UserServicesIF
{
    private final Logger logger = LoggerFactory.getLogger(DefaultUserServices.class);

    private final UserRepository userRepo;

    public DefaultUserServices(UserRepository userRepo)
    {
        super(userRepo);
        this.userRepo = userRepo;
    }

    @Override
    public User getUserByKey(Key userKey) throws DataPersistenceException
    {
        return userRepo.getByKey(userKey);
    }

    @Override
    public User setUsername(String username, String email, String nickname) throws DataPersistenceException
    {
        logger.info("setUsername - username: " + username + " email: " + email + " nickname: " + nickname);
        if (username == null)
            user = null;
        else
        {
            user = userRepo.getByName(username);
            if (user == null)
            {
                user = new User(username, email, nickname);
                userRepo.persist(user);
            }
        }
        return user;
    }
}
