package br.ufal.ic.p2.jackut;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;



public class Facade {
  ObjectOutputStream ps;

  ObjectInputStream fIO;
  
  public ArrayList<Community> communities;
  public ArrayList<User> users;

  public void zerarSistema() {
    communities = new ArrayList<Community>();
    users = new ArrayList<User>();
    
  }

  public Facade(){
    
    try {
      fIO = new ObjectInputStream(new FileInputStream(new File("cadastro.txt")));
      List<Object> users2 = Arrays.asList(fIO.readObject());
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e){
      e.printStackTrace();
    }

    try {
      ps = new ObjectOutputStream(new FileOutputStream(new File("cadastro.txt")));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  public String criarUsuario(String login, String password, String name) {
    if(login == null || login.isBlank()){
      Error e = new Error("Login inválido.");
      throw(e);
    }
    if(password == null || password.isBlank()){
      Error e = new Error("Senha inválida.");
      throw(e);
    }
    for (User user : users) {
      if (user.getLogin().equals(login)) {
        Error e = new Error("Conta com esse nome já existe.");
        throw (e);
      }
    }
    users.add(
        new User(name, login, password));
    return "user created successfully";
  }

  public String abrirSessao(String login, String password) {
    if(login == ""){
      Error e = new Error("Login inv\u00E1lido.");
      throw(e);
    }
    if(password == ""){
      Error e = new Error("senha inv\u00E1lida.");
      throw(e);
    }
    for (User user : users) {
      if (user.getLogin().equals(login)) {
        if (user.loginValidate(password)) {
          return "user created successfully";
        }
      }
    }
    Error e = new Error("Login ou senha inv\u00E1lidos.");
    throw (e);
  }

  public String getAtributoUsuario(String login, String atributo) {
    for (User user : users) {
      if (user.getLogin().equals(login)) {
        if (atributo.equals("nome")) {
          return user.getName();
        } else {
          return "atributo invalido";
        }
      }
    }
    Error e = new Error("Usuário não cadastrado.");
    throw (e);
  }
  public void encerrarSistema() throws IOException{
    ps.writeObject(communities.toString());
    ps.writeObject(users.toString());
    ps.close();
    fIO.close();
  }
}
