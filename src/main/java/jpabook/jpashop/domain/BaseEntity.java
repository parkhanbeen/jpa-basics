package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@MappedSuperclass
public abstract class BaseEntity {


        @Column(name = "create_user")
        private String createBy;
        private LocalDateTime createDate;
        @Column(name = "update_user")
        private String lastModifedBy;
        private LocalDateTime lastModifedDate;

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public LocalDateTime getCreateDate() {
            return createDate;
        }

        public void setCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
        }

        public String getLastModifedBy() {
            return lastModifedBy;
        }

        public void setLastModifedBy(String lastModifedBy) {
            this.lastModifedBy = lastModifedBy;
        }

        public LocalDateTime getLastModifedDate() {
            return lastModifedDate;
        }

        public void setLastModifedDate(LocalDateTime lastModifedDate) {
            this.lastModifedDate = lastModifedDate;
        }
}
