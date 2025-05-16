package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.CustomerServiceDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CustomerService")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerService {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerservice_seq_generator")
    @SequenceGenerator(name = "customerservice_seq_generator", sequenceName = "customerservice_seq", allocationSize = 1)
    @Column(name = "customerservice_id")
    private Long customerServiceId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "write_date", nullable = false)
    private LocalDateTime writeDate;

    @Column(name = "answer_content")
    private String answerContent;

    @Column(name = "answer_date")
    private LocalDateTime answerDate;

    @Column(name = "category", nullable = false, length = 100)
    private String category;

    @ManyToOne
    @JoinColumn(name = "members_id", nullable = false)
    private Members members;

    @ManyToOne
    @JoinColumn(name = "parents_customerservice_id")
    private CustomerService parent;

    public CustomerServiceDTO toDTO() {
        return CustomerServiceDTO.builder()
                .customerServiceId(this.customerServiceId)
                .title(this.title)
                .content(this.content)
                .writeDate(this.writeDate)
                .answerContent(this.answerContent)
                .answerDate(this.answerDate)
                .category(this.category)
                .membersId(this.members.getMembersId())
                .parentId(this.parent != null ? this.parent.getCustomerServiceId() : null)
                .build();
    }

    @PrePersist
    public void prePersist() {
        if (this.writeDate == null) {
            this.writeDate = LocalDateTime.now();
        }
    }
}