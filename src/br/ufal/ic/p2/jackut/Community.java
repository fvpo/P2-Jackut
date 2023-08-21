package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.ArrayList;

public class Community implements Serializable{
  private static final long serialVersionUID = 1L;
  User owner;
  String name;
  String description;
  ArrayList<User> subscribers;

  protected Community(User owner, String name, String descprition) {
    this.owner = owner;
    this.name = name;
    this.description = descprition;
  }
}
