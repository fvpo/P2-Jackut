package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.ArrayList;

class Atributo implements Serializable {
  protected String nome;
  protected String valor;

  protected Atributo(String nome, String valor) {
    this.nome = nome;
    this.valor = valor;
  }

  protected String getNome() {
    return nome;
  }

  protected void setNome(String nome) {
    this.nome = nome;
  }

  protected String getValor() {
    return valor;
  }

  protected void setValor(String valor) {
    this.valor = valor;
  }
}

class User implements Serializable {
  private static final long serialVersionUID = 1L;
  private String name;
  private ArrayList<Atributo> atributos;
  private String password;
  private String login;
  private ArrayList<User> friendList;
  private ArrayList<String> messages;

  protected User(String login, String password, String name) {
    this.name = login;
    this.login = password;
    this.password = name;
    this.friendList = new ArrayList<User>();
    this.atributos = new ArrayList<Atributo>();
    this.messages = new ArrayList<String>();
  }

  // User Temp
  protected User() {

  }

  protected ArrayList<User> getFriendList() {
    return friendList;
  }

  protected String getLastMessage(){
    if(messages.size() > 0){
      String message = messages.remove(0);
      return message;
    }
    else{
      Error e = new Error("Não há recados.");
      throw(e);
    }
  }

  protected void receiveMessage(String message){
    messages.add(message);
  }

  protected String getFriendListString() {
    String s = "{";
    for (User friend : this.friendList) {
      if (friend.friendList.contains(this)) {
        if (s.equals("{")) {
          s += friend.getLogin();
        } else {
          s += "," + friend.getLogin();
        }
      }
    }
    return s + "}";
  }

  protected ArrayList<Atributo> getAtributos() {
    return atributos;
  }

  protected ArrayList<String> getAtributosString() {
    ArrayList<String> s = new ArrayList<String>();
    for (Atributo atributo : atributos) {
      s.add(atributo.getNome() + ": " + atributo.getValor() + "\n");
    }
    return s;
  }

  protected void addAtributo(String nome, String valor) {
    for (Atributo atributo : atributos) {
      if (atributo.getNome().equals(nome)) {
        atributo.setValor(valor);
        break;
      }
    }
    atributos.add(new Atributo(nome, valor));
  }

  protected String getAtributo(String nome) {
    for (Atributo atributo : atributos) {
      if (atributo.getNome().equals(nome)) {
        return atributo.getValor();
      }
    }
    Error e = new Error("Atributo não preenchido.");
    throw (e);
  }

  protected String getName() {
    return name;
  }

  protected String getLogin() {
    return login;
  }

  protected String setName(String newName) {
    this.name = newName;

    return "name has been altered";
  }

  protected String setPassword(String newPassword) {
    this.password = newPassword;

    return "password has been altered";
  }

  protected String setLogin(String newLogin) {
    this.login = newLogin;

    return "email has been altered";
  }

  protected void addFriend(User newFriend) {
    Error e = new Error("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    Error e2 = new Error("Usuário já está adicionado como amigo.");
    for (User friend : this.friendList) {
      if (friend.equals(newFriend)) {
        for (User user : friend.getFriendList()) {
          if (user.equals(this)) {
            throw (e2);
          }
        }
        throw (e);
      }
    }
    this.friendList.add(newFriend);

  }

  protected String rmFriend(User newFriend) {
    this.friendList.remove(newFriend);

    return newFriend.name + " and you are no longer friends.";
  }

  protected Community createCommunity(String name, String description) {
    Community newCommunity = new Community(this, name, description);
    return newCommunity;
  }

  protected boolean loginValidate(String password) {
    return this.password.equals(password);
  }
}
