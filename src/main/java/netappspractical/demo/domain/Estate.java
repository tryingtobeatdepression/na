package netappspractical.demo.domain;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "estates")
public class Estate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private Double price;
    private Integer shareCount;
    private Date dateOfSelling;
    private String buyerName;

    @Version
    private Long version;

    @Column(nullable = true, length = 40)
    private String addedBy;

    @Column(nullable = true, length = 40)
    private String modifiedBy;

    @Column(nullable = true, length = 40)
    private String createdAt;

    @Column(nullable = true, length = 40)
    private String updatedAt;

    @Column(nullable = true, length = 40)
    private Boolean sold;

//    @ManyToOne()
//    @JoinColumn(name = "owner_id", nullable = true)
//    private _User owner;



    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getSold() {
        return sold;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Date getDateOfSelling() {
        return dateOfSelling;
    }

    public void setDateOfSelling(Date dateOfSelling) {
        this.dateOfSelling = dateOfSelling;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
