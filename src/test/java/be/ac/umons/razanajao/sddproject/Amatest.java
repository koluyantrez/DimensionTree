package be.ac.umons.razanajao.sddproject;

import be.ac.umons.razanajao.sddproject.structure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Amatest {
    Point p1,p2,p3;

    @BeforeEach
    void SetUp(){
        p1 = new Point(24,8.32223);
        p2 = new Point(104.478,8.32229);
        p3 = new Point(0.25,74.487);
    }

    @Test
    void commonComparaison(){
        assertTrue(p1.compareToX(p2)==-1);
        assertTrue(p3.compareToY(p2)==1);
        assertTrue(p2.equalsY(p1));
    }

    @Test
    void specialComparaison(){
        assertTrue(p2.compareToY(p1)==1);
    }

}
