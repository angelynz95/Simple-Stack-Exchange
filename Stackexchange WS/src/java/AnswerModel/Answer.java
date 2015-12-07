/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnswerModel;

import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Irene Wiliudarsan - 13513002
 * @author Angela Lynn - 13513032
 * @author Devina Ekawati - 13513088
 */
@XmlRootElement(name = "Answer")

public class Answer {
  @XmlElement(name="id_answer", required=true)
  private int idAnswer;
  @XmlElement(name="id_question", required=true)
  private int idQuestion;
  @XmlElement(name="id_user", required=true)
  private int idUser;
  @XmlElement(name="content", required=true)
  private String content;
  @XmlElement(name="datetime", required=true)
  private String datetime;
  @XmlElement(name="voteNum", required=true)
  private int voteNum;
  
  public Answer() { 
    idAnswer = 0; 
    idQuestion = 0; 
    idUser = 0; 
    voteNum = 0;
  } 
  
  public Answer(int idAnswer, String content, String datetime, int uid, int qid, int voteNum) {
    this.idAnswer = idAnswer; 
    idQuestion = qid; 
    idUser = uid; 
    this.content = content; 
    this.datetime = datetime; 
    this.voteNum = voteNum;
  }
  
  public String getContent() {
    return content;
  }
}
