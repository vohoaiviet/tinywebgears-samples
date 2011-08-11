package com.tinywebgears.simplegae.core.model;

import java.io.Serializable;

import javax.jdo.annotations.Cacheable;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@Cacheable(value = "false")
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class User implements Serializable
{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String username;

    @Persistent
    private String email;

    @Persistent
    private String nickname;

    public User()
    {
        this(null, null, null);
    }

    public User(String username, String email, String nickname)
    {
        this.username = username;
        this.email = email;
        this.nickname = nickname;
    }

    public Key getKey()
    {
        return key;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("[");
        sb.append("username=").append(username);
        sb.append(",").append("email=").append(email);
        sb.append(",").append("nickname=").append(nickname);
        sb.append("]");
        return sb.toString();
    }
}
