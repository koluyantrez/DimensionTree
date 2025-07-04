package be.ac.umons.razanajao.sddproject.frontend;

import be.ac.umons.razanajao.sddproject.backend.Table;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * This class is for the frontend, it draws the table with the header and data. Furthmore, we put an additional
 * colums for the index of each line. This makes the table more readable.
 *
 */
public class FrontGrid {

    /**
     * It gives a GridPane with the content of the tabe given in parameter and the index column.
     *
     * @param t     The table that we want to display.
     * @return      GridPane with data and index.
     */
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

        for (int i = 0;i < t.getData().length; i++) {
            for (int j=0; j<t.getData()[i].length; j++) {
                if(j==0){
                    Label l = new Label(String.valueOf(i+1));
                    l.setAlignment(Pos.CENTER);
                    l.setMaxWidth(Double.MAX_VALUE);
                    l.setMaxHeight(Double.MAX_VALUE);
                    GridPane.setHalignment(l, HPos.CENTER);
                    GridPane.setValignment(l, VPos.CENTER);
                    gp.add(l, j, i+1);
                }
                Label l = new Label(t.getData()[i][j]);
                l.setAlignment(Pos.CENTER);
                l.setMaxWidth(Double.MAX_VALUE);
                l.setMaxHeight(Double.MAX_VALUE);
                GridPane.setHalignment(l, HPos.CENTER);
                GridPane.setValignment(l, VPos.CENTER);
                gp.add(l, j+1, i+1);
            }
        }
        return gp;
    }


}

