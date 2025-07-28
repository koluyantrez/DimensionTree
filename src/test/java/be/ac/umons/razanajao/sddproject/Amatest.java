package be.ac.umons.razanajao.sddproject;

import be.ac.umons.razanajao.sddproject.structure.*;
import be.ac.umons.razanajao.sddproject.backend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Amatest {
    Point p1,p2,p3;
    String someInfo = "";
    Table t;

    @BeforeEach
    void setUp() {
        p1 = new Point(24, 8.32223,someInfo);
        p2 = new Point(104.478, 8.32229,someInfo);
        p3 = new Point(0.25, 74.487,someInfo);

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
        CoupleList al = t.giveDataset();
        assertTrue(al.size()==t.getX());
    }

    @Test
    void checkOrderData(){
        CoupleList al = t.giveDataset();

        boolean okX = true;
        boolean okY = true;


        for (int i=0;i<al.size()-1;i++) {
            if(al.getXray().get(i).compareToX(al.getXray().get(i+1))>0) {
                okX = false;
                break;
            }
            if(al.getYankee().get(i).compareToY(al.getYankee().get(i+1))>0) {
                okY = false;
                break;
            }
        }

        assertTrue(okX && okY);

    }

    @Test
    void checkSizeAfterSplit(){
        CoupleList al = t.giveDataset();
        al.split(420);
        assertTrue(al.getFirstHalfY().size()==10);
    }

    @Test
    void checkAllPointInFirstPart(){
        CoupleList al = t.giveDataset();
        al.split(98);
        CoupleList firstPart = al.getFirstPart();


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
        CoupleList al = t.giveDataset();
        al.split(141);
        CoupleList secondPart = al.getSecondPart();

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

    @Test
    void checkSelectSyntax(){
        String NAME = "[a-zA-Z0-9_\\.]+";
        String NUMBER = "-?\\d+(\\.\\d+)?";
        String CONDITION = "("+NAME+"\\s+(>=|<=)\\s+"+NUMBER+"|"+NAME+"\\s+in\\s+\\["+NUMBER+",\\s*"+NUMBER+"\\])";
        String REGEX_SELECTOR = "^SELECT\\s+"+NAME+
                "(\\s*,\\s*"+NAME+")?"+
                "\\s+FROM\\s+"+NAME+
                "\\s+WHERE\\s+"+CONDITION+
                "(\\s+AND\\s+"+CONDITION+")?$";

        String t1 = "SELECT num FROM wrc_estonia WHERE num <= 40";
        String f1 = "SELECT pro day FROM fifa WHERE pro>=4 AND day>=33";
        String t2 = "SELECT time FROM war WHERE end in [1453,1789] AND time >= 20";
        String f2 = "SELECT hertz, teta FROM sinusoid.txt WHERE hertz in [163,282];";
        String f3 = "SELECT girl,rank FROM perfo.txt WHERE rank<=4.2";
        String t3 = "    SELECT girl, rank FROM perfo.txt WHERE rank <= 4.2";
        assertTrue(t1.trim().matches(REGEX_SELECTOR));
        assertTrue(t2.trim().matches(REGEX_SELECTOR));
        assertFalse(f1.trim().matches(REGEX_SELECTOR));
        assertFalse(f2.trim().matches(REGEX_SELECTOR));
        assertFalse(f3.trim().matches(REGEX_SELECTOR));
        assertTrue(t3.trim().matches(REGEX_SELECTOR));

    }

}
