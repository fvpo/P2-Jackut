package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.ArrayList;

public class Community implements Serializable{
  private static final long serialVersionUID = 1L;
  User owner;
  String name;
  String description;
  ArrayList<User> subscribers;

  public Community(User owner, String name, String description) {
    this.owner = owner;
    this.name = name;
    this.description = description;
  }
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public User getOwner() {
    return owner;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public ArrayList<User> getSubscribers() {
    return subscribers;
  }

  @Override
  public String toString() {
    return "Community [owner=" + owner + ", name=" + name + ", description=" + description + ", subscribers="
        + subscribers + "]";
  }
}
