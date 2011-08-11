package com.tinywebgears.simplegae.core.service;

import com.google.appengine.api.datastore.Key;
import com.tinywebgears.simplegae.core.dao.DataPersistenceException;
import com.tinywebgears.simplegae.core.model.User;

public interface UserServicesIF extends ServiceIF
{
    User setUsername(String username, String email, String nickname) throws DataPersistenceException;

    User getUserByKey(Key userKey) throws DataPersistenceException;
}
