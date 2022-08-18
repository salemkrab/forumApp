package fr.m2i.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Topic
 *
 */
@Entity
@NamedQueries({ @NamedQuery(name = "findTopicById", query = "select t from TopicEnt t where t.id = :topicId"),
		@NamedQuery(name = "findTopicsByCategory", query = "select t from TopicEnt t where t.category = :category"),
		@NamedQuery(name = "findTopicsByUserOrderByDateDesc", query = "select t from TopicEnt t where t.user.id = :userId"
				+ " order by t.creationDate desc"),
		@NamedQuery(name = "findTopicsByUser", query = "select t from TopicEnt t where t.user.id = :userId"),
		@NamedQuery(name = "findAllTopics", query = "select t from TopicEnt t"), })
@Table(name = "topics")
public class TopicEnt implements Serializable {

	private static final long serialVersionUID = 1L;

	public TopicEnt() {
	}

	public TopicEnt(String title, String content, String category, UserEnt user) {
		this.title = title;
		this.content = content;
		this.category = category;
		this.creationDate = Calendar.getInstance();
		;
		this.user = user;
		this.answers = new LinkedList<>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Basic
	@Column(nullable = false, length = 100)
	private String title;

	@Basic
	@Column(length = 1024)
	private String content;

	@Basic
	@Column(nullable = false, length = 16)
	private String category;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar creationDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false)
	private UserEnt user;

	@OneToMany(mappedBy = "topic", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<AnswerEnt> answers;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public List<AnswerEnt> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerEnt> answers) {
		this.answers = answers;
	}

}
