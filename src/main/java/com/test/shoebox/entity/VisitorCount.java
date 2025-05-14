package com.test.shoebox.entity;

import java.time.LocalDate;

import com.test.shoebox.dto.VisitorCountDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "VisitorCount")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitorCount {

    @Id
    @Column(name = "visit_date")
    private LocalDate visitDate;

    public VisitorCountDTO toDTO() {
        return VisitorCountDTO.builder()
                .visitDate(this.visitDate)
                .build();
    }

    @PrePersist
    public void prePersist() {
        if (this.visitDate == null) {
            this.visitDate = LocalDate.now();
        }
    }
}

