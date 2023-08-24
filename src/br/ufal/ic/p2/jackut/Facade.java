package br.ufal.ic.p2.jackut;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Facade {
  ObjectOutputStream oos;

  ObjectInputStream ois;

  public ArrayList<Community> communities;
  public ArrayList<User> users;

  public void zerarSistema() {
    communities = new ArrayList<Community>();
    users = new ArrayList<User>();

  }

  public Facade() {
    try {
      // settando o arquivo que irá receber os dados de cadastro dos usuários e as
      // listas de comunidade
      // junto com seu fetcher
      File file = new File("cadastro.dat");
      ois = new ObjectInputStream(new FileInputStream(file));
      if (file.exists()) {
        // caso o sistema tenha dados guardados, inicialize a lista de usuários
        users = ((Data) ois.readObject()).getUsers();
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      // settando o setter do arquivo de cadastro
      oos = new ObjectOutputStream(new FileOutputStream(new File("cadastro.dat")));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // tenta criar o usuário, checa se as credenciais são invalidas, em seguida se
  // elas ja existem no sistema
  // se nenhuma dessas duas ocorrerem, cria o usuário
  public String criarUsuario(String login, String password, String name) {
    if (login == null || login.isBlank()) {
      Error e = new Error("Login inválido.");
      throw (e);
    }
    if (password == null || password.isBlank()) {
      Error e = new Error("Senha inválida.");
      throw (e);
    }
    for (User user : users) {
      if (user.getLogin().equals(login)) {
        Error e = new Error("Conta com esse nome já existe.");
        throw (e);
      }
    }
    users.add(
        new User(name, login, password));
    return login;
  }

  // checa se as credenciais são validas e se elas ja existem no sistema
  // se ambas ocorrerem, acessa o usuário
  public String abrirSessao(String login, String password) {
    if (login == null || login.isBlank()) {
      Error e = new Error("Login ou senha inválidos.");
      throw (e);
    }
    if (password == null || password.isBlank()) {
      Error e = new Error("Login ou senha inválidos.");
      throw (e);
    }
    for (User user : users) {
      if (user.getLogin().equals(login)) {
        if (user.loginValidate(password)) {
          return user.getLogin();
        }
      }
    }
    Error e = new Error("Login ou senha inv\u00E1lidos.");
    throw (e);
  }

  // caso a identificação seja bem sucedida
  // retorna os atributos do usuário apartir de uma busca pelo nome do atributo
  // se não houver atributo com esse nome, retorna um erro
  public String getAtributoUsuario(String login, String atributoNome) {
    for (User user : users) {
      if (user.getLogin().equals(login)) {
        if (atributoNome.equals("nome")) {
          return user.getName();
        } else {
          for (Atributo atributo : user.getAtributos()) {
            if (atributo.getNome().equals(atributoNome)) {
              return atributo.getValor();
            }
          }
          Error e = new Error("Atributo não preenchido.");
          throw (e);
        }
      }
    }
    Error e = new Error("Usuário não cadastrado.");
    throw (e);
  }

  // caso a identificação seja bem sucedida
  // busca nos atributos do usuário o nome informado
  // caso haja, reescreve o valor
  // caso não haja, cria um atributo novo com o nome fornecido
  public void editarPerfil(String id, String atributo, String valor) {
    Error e2 = new Error("Usuário não cadastrado.");
    if (id == null || id.isBlank()) {
      throw (e2);
    }
    for (User user : users) {
      if (user.getLogin().equals(id)) {
        if (atributo.equals("nome")) {
          user.setName(valor);
          break;
        } else {
          user.addAtributo(atributo, valor);
          break;
        }
      }
      throw (e2);
    }
  }

  // checa se amigoId está presente na lista de amigos do usuário
  public boolean ehAmigo(String id, String amigoId) {
    User amigo = new User();
    User user = new User();
    Error e = new Error("Usuário não cadastrado.");
    if(id == null || id.isBlank()){
      throw(e);
    }
    if(amigoId == null || amigoId.isBlank()){
      throw(e);
    }
    for (User i : users) {
      if (i.getLogin().equals(amigoId)) {
        amigo = i;
      }
    }
    if(amigo == new User()){
      throw(e);
    }
    for (User i : users) {
      if (i.getLogin().equals(id)) {
        user = i;
        if (user.getFriendList().contains(amigo)) {
          if (amigo.getFriendList().contains(user)) {
            return true;
          }
        }
      }
    }
    if(user == new User()){
      throw(e);
    }
    return false;
  }

  public void adicionarAmigo(String id, String amigoId) {
    Error e = new Error("Usuário não pode adicionar a si mesmo como amigo.");
    Error e2 = new Error("Usuário não cadastrado.");
    User amigo = new User();
    User user = new User();
    if(id == null || id.isBlank()){
      throw(e2);
    }
    if(amigoId == null || amigoId.isBlank()){
      throw(e2);
    }
    if (id.equals(amigoId)) {
      throw (e);
    }
    for (User i : users) {
      if (i.getLogin().equals(amigoId)) {
        amigo = i;
      }
    }
    if(amigo.getLogin() == null){
      throw(e2);
    }
    for (User i : users) {
      if (i.getLogin().equals(id)) {
        user = i;
          user.addFriend(amigo);
      }
    }
    if(user.getLogin() == null){
      throw(e2);
    }
  }

  public String getAmigos(String id) {
    Error e = new Error("Usuário não cadastrado.");
    for (User user : users) {
      if (user.getLogin().equals(id)) {
        return user.getFriendListString();
      }
    }
    throw (e);
  }

  public void enviarRecado(String id, String destinatarioId, String recado){
    User remetente = new User();
    User destinatario = new User();
    for(User user : users){
      if(user.getLogin().equals(id)){
        remetente = user;
      }
      if(user.getLogin().equals(destinatarioId)){
        destinatario = user;
      }
    }
    if(remetente.equals(destinatario)){
      Error e = new Error("Usuário não pode enviar recado para si mesmo.");
      throw(e);
    }
    if(destinatario.getName() == null){
      Error e = new Error("Usuário não cadastrado.");
      throw(e);
    }
    destinatario.receiveMessage(recado);
  }

  public String lerRecado(String id){
    for(User user : users){
      if(user.getLogin().equals(id)){
        return user.getLastMessage();
      }
    }
    Error e = new Error("Usuário não cadastrado.");
    throw(e);
  }

  // encerra o sistema, gravando novamente os dados dos usuários e das comunidades
  // no arquivo de cadastro
  public void encerrarSistema() throws IOException {
    Data data = new Data(communities, users);
    oos.writeObject(data);
    oos.close();
    ois.close();
  }
}
