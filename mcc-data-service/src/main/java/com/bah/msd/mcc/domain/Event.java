package com.bah.msd.mcc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EVENTS")
public class Event {
	//  Workshop:
	//
	//  Create an Event class, and implement methods to manage event data.
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	
	@Column(name="EVENT_CODE")
	String code;
	
	@Column(name="TITLE")
	String title;
	
	@Column(name="DESCRIPTION")
	String desc;
}