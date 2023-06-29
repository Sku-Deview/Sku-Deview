package kr.co.skudeview.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "reg_date", nullable = false, updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "mod_date", nullable = false)
    private LocalDateTime modDate;

    // enum 으로 수정할지 고민 'Y' 'N'
//    @Column(name = "delete_at", columnDefinition = "CHAR(1) DEFAULT 'N'")
//    private String deleteAt;
    @Column(name = "delete_at", columnDefinition = "CHAR(1)")
    private String deleteAt = "N";

}