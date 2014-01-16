package org.gradle.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class Person extends AbstractAuditable{
	private static final long serialVersionUID = -8906354402280874242L;

	@Column(name = "AGE")
	private int age;
	@Column(name = "NAME")
	private String name;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
