package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.ArrayList;

class User implements Serializable{
  private static final long serialVersionUID = 1L;
  private String name;
  private String password;
  private String login;
  private ArrayList<User> friendList;

  protected User(String login, String password, String name){
    this.name = login;
    this.login = password;
    this.password = name;
    this.friendList = new ArrayList<User>();
  }

  protected String getName(){
    return name;
  }
  
  protected String getLogin() {
    return login;
  }

  protected String setName(String newName){
    this.name = newName;
    
    return "name has been altered";
  }

  
  protected String setPassword(String newPassword){
    this.password = newPassword;
    
    return "password has been altered";
  }

  
  protected String setLogin(String newLogin){
    this.login = newLogin;
    
    return "email has been altered";
  }

  protected String addFriend(User newFriend){
    this.friendList.add(newFriend);

    return newFriend.name+" added as friend successfully";
  }
  
  protected String rmFriend(User newFriend){
    this.friendList.remove(newFriend);

    return newFriend.name+" and you are no longer friends";
  }

  protected Community createCommunity(String name, String description){
    Community newCommunity = new Community(this, name, description);
    return newCommunity;
  }
  
  protected boolean loginValidate(String password){
    return this.password.equals(password);
  }
}
