package com.onlyjoon.test;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "test_table")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long testId;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "etc", nullable = false)
    private String etc;

}
