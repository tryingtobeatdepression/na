package netappspractical.demo.domain;


import javax.persistence.*;


@Entity
@Table(name = "parameters")
public class Parameters {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String pKey;
    private String pValue;

    public Parameters() {}

    public Parameters(int id, String pKey, String pValue) {
        this.id = id;
        this.pKey = pKey;
        this.pValue = pValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getpKey() {
        return pKey;
    }

    public void setpKey(String pKey) {
        this.pKey = pKey;
    }

    public String getpValue() {
        return pValue;
    }

    public void setpValue(String pValue) {
        this.pValue = pValue;
    }
}
