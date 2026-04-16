package knr.yes24slc.integration.model;

import java.io.Serializable;

public interface IPose extends Serializable {
    double getX();
    double getY();
    double getTheta();
}
