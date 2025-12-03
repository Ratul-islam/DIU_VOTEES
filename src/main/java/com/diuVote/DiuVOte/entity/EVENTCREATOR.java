package com.diuVote.DiuVOte.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "eventcreator")
public class EVENTCREATOR {

    @Id
    private String id;

    private String email;
    private String passwordHash;

    private String role = "EVENTCREATOR";
}