package com.test.shoebox.entity;

import java.time.LocalDateTime;

import com.test.shoebox.dto.ProductPostQnaDTO;

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
@Table(name = "ProductPostQna")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPostQna {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productpostqna_seq_generator")
    @SequenceGenerator(name = "productpostqna_seq_generator", sequenceName = "productpostqna_seq", allocationSize = 1)
    @Column(name = "productpostqna_id")
    private Long productPostQnaId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "write_date", nullable = false)
    private LocalDateTime writeDate;

    @Column(name = "answer_title", length = 100)
    private String answerTitle;

    @Column(name = "answer_content")
    private String answerContent;

    @Column(name = "answer_date")
    private LocalDateTime answerDate;

    @ManyToOne
    @JoinColumn(name = "productpost_id", nullable = false)
    private ProductPost productPost;

    @ManyToOne
    @JoinColumn(name = "members_id", nullable = false)
    private Members members;

    public ProductPostQnaDTO toDTO() {
        return ProductPostQnaDTO.builder()
                .productPostQnaId(this.productPostQnaId)
                .title(this.title)
                .content(this.content)
                .writeDate(this.writeDate)
                .answerTitle(this.answerTitle)
                .answerContent(this.answerContent)
                .answerDate(this.answerDate)
                .productPostId(this.productPost.getProductPostId())
                .membersId(this.members.getMembersId())
                .build();
    }
    
    @PrePersist
    public void prePersist() {
        if (this.writeDate == null) {
            this.writeDate = LocalDateTime.now();
        }
    }
}

