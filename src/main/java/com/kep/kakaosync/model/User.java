package com.kep.kakaosync.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nickname;
	
	@Column
	private String profileImage;
	
	@Column
	private String thumbnailImage;
	
	@Column
	private String profile;
	
	@Column
	private String email;
	
	@Column
	private String ageRange;
	
	@Column
	private String birthday;
	
	@Column
	private String gender;
	
}
