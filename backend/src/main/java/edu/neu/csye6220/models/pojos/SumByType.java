package edu.neu.csye6220.models.pojos;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

public class SumByType {
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
