package edu.neu.csye6220.models.pojos;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
public class SumByType {
    @Id
    @NotBlank
    private String type;
    @NotBlank
    private double sum;

    public SumByType() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
