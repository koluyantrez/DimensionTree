package be.ac.umons.razanajao.sddproject;

import be.ac.umons.razanajao.sddproject.structure.*;
import be.ac.umons.razanajao.sddproject.backend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Amatest {
    Point p1,p2,p3;

    Table t;

    @BeforeEach
    void setUp() {
        p1 = new Point(24, 8.32223);
        p2 = new Point(104.478, 8.32229);
        p3 = new Point(0.25, 74.487);

        t = FileMaster.createTable("for_test.txt");
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

    @Test
    void checkGoodTableFromResource(){
        assertTrue(t.getY()==3);
        assertTrue(3==t.getHeader().length);
        assertTrue(t.getX()==19);
        assertTrue(t.getData().length==19);
    }

    @Test
    void allDataFromTableInAinaList(){
        AinaList al = t.giveDataset();
        assertTrue(al.size()==t.getX());
    }

    @Test
    void checkSizeAfterSplit(){
        AinaList al = t.giveDataset();
        AinaList[] recover = al.split(8);
        assertTrue(recover[0].size()==10);
    }

    @Test
    void checkAllPointInFirstPart(){
        AinaList al = t.giveDataset();
        AinaList firstPart = al.split(8)[0];

        boolean answer1 = true;
        for(Point p : firstPart.getXray()){
            if(!firstPart.getYankee().contains(p)){
                answer1 = false;
                break;
            }
        }
        boolean answer2 = true;
        for(Point p : firstPart.getYankee()){
            if(!firstPart.getXray().contains(p)){
                answer2 = false;
                break;
            }
        }
        assertTrue(answer1 && answer2);
    }

    @Test
    void checkAllPointInSecondPart() {
        AinaList al = t.giveDataset();
        AinaList secondPart = al.split(3)[1];

        boolean answer1 = true;
        for(Point p : secondPart.getXray()){
            if(!secondPart.getYankee().contains(p)){
                answer1 = false;
                break;
            }
        }
        boolean answer2 = true;
        for(Point p : secondPart.getYankee()){
            if(!secondPart.getXray().contains(p)){
                answer2 = false;
                break;
            }
        }
        al.display();
        assertTrue(answer1 && answer2);
    }

}
