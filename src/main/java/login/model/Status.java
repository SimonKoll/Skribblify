package login.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

public enum Status {
    Y,N;


    public static ObservableList<Status> valuesAsObservableList() {
        return FXCollections.observableList(Arrays.asList(values()));
    }
}
