package kr.co.skudeview.global.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
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

    // true  = 삭제된 상태 , false = 삭제되지 않은 상태
    @Column(name = "delete_at")
    private boolean deleteAt = false;

    public void changeDeleteAt() {
        this.deleteAt = true;
    }
}
