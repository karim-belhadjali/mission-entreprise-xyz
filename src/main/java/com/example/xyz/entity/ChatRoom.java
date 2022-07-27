package com.example.xyz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long chatId;

    @ManyToOne
    @JoinColumn(name="sender")
    private User sender;

    @ManyToOne
    @JoinColumn(name="recipient")
    private User recipient;

}