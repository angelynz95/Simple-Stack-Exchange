/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserModel;

import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Devina
 */
@XmlRootElement(name = "User")
public class User {
  // Atribut
  @XmlElement(name="id_user", required=true)
  private int idUser;
  @XmlElement(name="name", required=true)
  private String name;
  @XmlElement(name="email", required=true)
  private String email;
  @XmlElement(name="token", required=true)
  private String token;
  
  // Konstruktor
  public User () {
    idUser = 0;
  }
  
  public User (int _idUser, String _name, String _email) {
    idUser = _idUser;
    name = _name;
    email = _email;
  }
  
  public int getIdUser() {
    return idUser;
  }
  
  public String getName() {
    return name;
  }
  
  public String getEmail() {
    return email;
  }
  
  public String token() {
    return token;
  }
}
