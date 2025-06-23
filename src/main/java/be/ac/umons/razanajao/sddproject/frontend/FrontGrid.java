package be.ac.umons.razanajao.sddproject.frontend;

import be.ac.umons.razanajao.sddproject.backend.Table;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class FrontGrid {


    public static GridPane onGridPane(Table t){
        GridPane gp = new GridPane(t.getX()+1,t.getY()+1);

        for(int o=0;o<=t.getHeader().length;o++) {
            if(o==0){
                Label l = new Label("#");
                l.setAlignment(Pos.CENTER);
                l.setMaxWidth(Double.MAX_VALUE);
                l.setMaxHeight(Double.MAX_VALUE);
                GridPane.setHalignment(l, HPos.CENTER);
                GridPane.setValignment(l, VPos.CENTER);
                gp.add(l, o, 0);
            }else {
                Label l = new Label(t.getHeader()[o-1]);
                l.setAlignment(Pos.CENTER);
                l.setMaxWidth(Double.MAX_VALUE);
                l.setMaxHeight(Double.MAX_VALUE);
                GridPane.setHalignment(l, HPos.CENTER);
                GridPane.setValignment(l, VPos.CENTER);
                gp.add(l, o, 0);
            }
        }

        for (int i = 1;i < t.getData().length; i++) {
            for (int j=0; j<t.getData()[i].length; j++) {
                if(j==0){
                    Label l = new Label(String.valueOf(i));
                    l.setAlignment(Pos.CENTER);
                    l.setMaxWidth(Double.MAX_VALUE);
                    l.setMaxHeight(Double.MAX_VALUE);
                    GridPane.setHalignment(l, HPos.CENTER);
                    GridPane.setValignment(l, VPos.CENTER);
                    gp.add(l, j, i);
                }
                Label l = new Label(t.getData()[i][j]);
                l.setAlignment(Pos.CENTER);
                l.setMaxWidth(Double.MAX_VALUE);
                l.setMaxHeight(Double.MAX_VALUE);
                GridPane.setHalignment(l, HPos.CENTER);
                GridPane.setValignment(l, VPos.CENTER);
                gp.add(l, j+1, i);
            }
        }
        return gp;
    }


}

