package be.ac.umons.razanajao.sddproject;

import be.ac.umons.razanajao.sddproject.structure.*;
import be.ac.umons.razanajao.sddproject.backend.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class Amatest {
    Point p1,p2,p3;
    String someInfo = "";
    Table t;
    KdTree<CoupleList> kdt = new KdTree<>();
    CoupleList cl;

    @BeforeEach
    void setUp() {
        p1 = new Point(24, 8.32223,someInfo);
        p2 = new Point(104.478, 8.32229,someInfo);
        p3 = new Point(0.25, 74.487,someInfo);

        t = FileMaster.createTable("for_test.txt");
        cl=t.giveDataset();
        kdt = kdt.buildKdTree(cl,0);
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
        CoupleList cl= t.giveDataset();
        assertTrue(cl.size()==t.getX());
    }

    @Test
    void checkOrderData(){
        CoupleList cl= t.giveDataset();

        boolean okX = true;
        boolean okY = true;


        for (int i=0;i<cl.size()-1;i++) {
            if(cl.getXray().get(i).compareToX(cl.getXray().get(i+1))>0) {
                okX = false;
                break;
            }
            if(cl.getYankee().get(i).compareToY(cl.getYankee().get(i+1))>0) {
                okY = false;
                break;
            }
        }

        assertTrue(okX && okY);

    }

    @Test
    void checkSizeAfterSplit(){
        CoupleList cl= t.giveDataset();
       cl.split(420);
        assertTrue(cl.getFirstHalfY().size()==10);
    }

    @Test
    void checkAllPointInFirstPart(){
        CoupleList cl= t.giveDataset();
       cl.split(98);
        CoupleList firstPart =cl.getFirstPart();


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
        CoupleList cl= t.giveDataset();
       cl.split(141);
        CoupleList secondPart =cl.getSecondPart();

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
    
    /*@Test
    void checkSearchZero(){
        String input = "height >= 1.71 AND weight >= 83.4";
        ArrayList<Point> zero = InputMaster.selectTwo(input,t,kdt);
        assertTrue(zero.size()==0);
    }*/

   /* @Test
    void checkSearchResult(){
        String input1 = "weight >= 100";
        ArrayList<Point> twee = InputMaster.selectTwo(input1,t,kdt);
        assertTrue(twee.size()==2);

        String input2 = "weight >= 100 AND height in [1.55,1.7]";
        ArrayList<Point> een = InputMaster.selectTwo(input2,t,kdt);
        assertTrue(een.size()==1);
    }*/

}
