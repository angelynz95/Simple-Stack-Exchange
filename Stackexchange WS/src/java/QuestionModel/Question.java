/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuestionModel;

import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */

@XmlRootElement(name = "Question")
public class Question {
  // Atribut
  @XmlElement(name="id_question", required=true)
  private int idQuestion;
  @XmlElement(name="topic", required=true)
  private String topic;
  @XmlElement(name="content", required=true)
  private String content;
  @XmlElement(name="datetime", required=true)
  private String datetime;
  @XmlElement(name="id_user", required=true)
  private int idUser;
  @XmlElement(name="voteNum", required=true)
  private int voteNum;
  
  // Konstruktor
  public Question() {
    idQuestion = 0;
    idUser = 0;
    voteNum = 0;
  }
  
  public Question(int _idQuestion, String _topic, String _content, String _datetime, int _idUser, int _voteNum) {
    idQuestion = _idQuestion;
    topic = _topic;
    content = _content;
    datetime = _datetime;
    idUser = _idUser;
    voteNum = _voteNum;
  }
}
