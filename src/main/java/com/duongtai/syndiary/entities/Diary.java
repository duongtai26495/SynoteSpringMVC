package com.duongtai.syndiary.entities;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name ="diary")
@Data
public class Diary {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 255)
	private String title;

	@Column(length = 5000)
	private String content;

	private boolean done;

	private boolean display;

	@Column(updatable = false)
	private String added_at;

	private String last_edited;

	public Diary() {
	}

	@ManyToOne
	@JoinColumn(name = "diaries", referencedColumnName = "user_id")
	private User author;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public User getAuthor() {
		return author;
	}

	public String getAdded_at() {
		return added_at;
	}

	public void setAdded_at(String added_at) {
		this.added_at = added_at;
	}

	public String getLast_edited() {
		return last_edited;
	}

	public void setLast_edited(String last_edited) {
		this.last_edited = last_edited;
	}

	public User getAuthor(User author) {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
