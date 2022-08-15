package fr.m2i.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Answer
 *
 */
@Entity
@NamedQueries({ 
	@NamedQuery(name = "findAnswerById", query = "select a from AnswerEnt a where a.id = :answerId"),
	@NamedQuery(name = "findAnswersByTopic", query = "select a from AnswerEnt a where a.topic.id = :topicId"),
	@NamedQuery(name = "findAnswersByUser", query = "select a from AnswerEnt a where a.user.id = :userId"),
})
@Table(name="answers")
public class AnswerEnt implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public AnswerEnt() {
	}
	
	public AnswerEnt(String content, UserEnt user, TopicEnt topic) {
		this.content = content;
		this.creationDate = Calendar.getInstance();
		this.user = user;
		this.topic = topic;
	}

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Basic
    @Column(nullable = false)
    private String content;
	
	@Column(nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
    private UserEnt user;

    @ManyToOne(fetch = FetchType.EAGER)
    private TopicEnt topic;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public UserEnt getUser() {
		return user;
	}

	public void setUser(UserEnt user) {
		this.user = user;
	}

	public TopicEnt getTopic() {
		return topic;
	}

	public void setTopic(TopicEnt topic) {
		this.topic = topic;
	}
}
