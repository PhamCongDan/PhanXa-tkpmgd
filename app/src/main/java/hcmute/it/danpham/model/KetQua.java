package hcmute.it.danpham.model;

import java.io.Serializable;

/**
 * Created by Dan Pham on 05/10/2018.
 */

public class KetQua implements Serializable {

    String deBai;
    boolean dapAnDung;

    public KetQua(String deBai, boolean dapAnDung) {
        this.deBai = deBai;
        this.dapAnDung = dapAnDung;
    }

    public KetQua() {
    }

    public String getDeBai() {
        return deBai;
    }

    public void setDeBai(String deBai) {
        this.deBai = deBai;
    }

    public boolean isDapAnDung() {
        return dapAnDung;
    }

    public void setDapAnDung(boolean dapAnDung) {
        this.dapAnDung = dapAnDung;
    }
}
