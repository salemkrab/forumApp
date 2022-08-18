package fr.m2i.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Entity implementation class for Entity: UserEnt
 *
 */
@Entity
@Table(name="users")
@NamedQueries({
@NamedQuery(name="findUserByUsername", query="select u from UserEnt u where u.username = :username"),
@NamedQuery(name="findUserByUsernamePassword", query="select u from UserEnt u where u.username = :username "
		+ "and u.password= :password"),
})
public class UserEnt implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Basic
    @Column(nullable = false, length = 20)
    private String username;
	
	@Basic
	@Column(name = "user_password", nullable=false, length = 40)
	private String password;
	
	@Column(nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
	private Calendar registerDate;
	
    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<TopicEnt> topics;

    @OneToMany(mappedBy = "user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<AnswerEnt> answers;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Calendar registerDate) {
		this.registerDate = registerDate;
	}

	public List<TopicEnt> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicEnt> topics) {
		this.topics = topics;
	}

	public List<AnswerEnt> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerEnt> answers) {
		this.answers = answers;
	}
	
	public UserEnt() {
	}
	
	public UserEnt(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.registerDate = Calendar.getInstance();
		this.topics = new LinkedList<>();
		this.answers = new LinkedList<>();
	}
    

	
}
