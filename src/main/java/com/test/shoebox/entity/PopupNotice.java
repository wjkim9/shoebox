package com.test.shoebox.entity;

import com.test.shoebox.dto.PopupNoticeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PopupNotice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopupNotice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "popupnotice_seq_generator")
    @SequenceGenerator(name = "popupnotice_seq_generator", sequenceName = "popupnotice_seq", allocationSize = 1)
    @Column(name = "popupnotice_id")
    private Long popupNoticeId;

    @Column(name = "file_name", nullable = false, length = 200)
    private String fileName;

    public PopupNoticeDTO toDTO() {
        return PopupNoticeDTO.builder()
                .popupNoticeId(this.popupNoticeId)
                .fileName(this.fileName)
                .build();
    }
}