package netappspractical.demo.domain;


import javax.persistence.*;


@Entity
@Table(name = "parameters")
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String pKey;
    private Double pValue;

    public Parameter() {}

    public Parameter(String pKey, Double pValue) {
        this.pKey = pKey;
        this.pValue = pValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPKey() {
        return pKey;
    }

    public void setPKey(String pKey) {
        this.pKey = pKey;
    }

    public Double getPValue() {
        return pValue;
    }

    public void setPValue(Double pValue) {
        this.pValue = pValue;
    }
}
