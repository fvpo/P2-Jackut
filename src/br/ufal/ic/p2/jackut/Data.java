package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.ArrayList;

//Datatype que existe para salvar as listas de usuários e comunidades escritas durante a execução do sistema


public class  Data implements Serializable {
  private static final long serialVersionUID = 1L;
  private ArrayList<Community> communities;
  private ArrayList<User> users;
  
  public ArrayList<Community> getCommunities() {
    return communities;
  }

  public void setCommunities(ArrayList<Community> communities) {
    this.communities = communities;
  }

  public ArrayList<User> getUsers() {
    return users;
  }

  public void setUsers(ArrayList<User> users) {
    this.users = users;
  }

  public Data(ArrayList<Community> communities, ArrayList<User> users) {
    this.communities = communities;
    this.users = users;
  }
}
