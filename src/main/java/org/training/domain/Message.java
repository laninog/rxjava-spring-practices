package org.training.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGES")
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

}
