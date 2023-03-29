package com.example.botdigital.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.DateTimeUtils;


import java.time.LocalTime;


@Entity
@Data
@Table(name="bots")
@NoArgsConstructor
@AllArgsConstructor
public class BotSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;
    @Column(name="name")
    private String name;
    @Column(name="tokenbot",unique = true)
    private String tokenbot;
    @Column(name="active")
    private boolean active;
    @Column(name="chatId")
    private long chatId;
//    @Column(name="ftpServerName")
//    private String ftpServerName;
//    @Column(name ="ftpServerLogin")
//    private String ftpServerLogin;
//    @Column(name ="ftpServerPassword")
//    private String ftpServerPassword;
    @Column(name = "fileName")
    private String fileName;
    @Column(name ="timeTask")
    private LocalTime timeTask;

}
