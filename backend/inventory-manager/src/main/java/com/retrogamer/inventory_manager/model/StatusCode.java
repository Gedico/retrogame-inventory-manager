package com.retrogamer.inventory_manager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "status_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Size(max = 100)
    @Column(length = 100)
    private String description;
}