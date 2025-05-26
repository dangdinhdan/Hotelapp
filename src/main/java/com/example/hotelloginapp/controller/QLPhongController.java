package com.example.hotelloginapp.controller;

import com.example.hotelloginapp.dao.PhongDao;
import com.example.hotelloginapp.models.Phong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QLPhongController implements Initializable {
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;
    @FXML
    public Pane paneScreen;

    private ObservableList<Phong> phongList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPhong();
    }
    public void loadPhong() {
        phongList.clear();
        phongList.addAll(PhongDao.getAllphong());
        grid.getChildren().clear();

        int row = 1, col = 0;
        try {
            for (Phong phong : phongList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelloginapp/view/phong-view.fxml"));
                AnchorPane pane = loader.load();
                PhongController controller = loader.getController();

                controller.setData(phong);
                controller.setPhongChangeListener(this::loadPhong); // Gắn callback cập nhật phòng

                if (col == 4) {
                    col = 0;
                    row++;
                }
                grid.add(pane, col++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
                GridPane.setMargin(pane,new Insets(20,20,20,20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadThemPhong() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelloginapp/view/themphong-view.fxml"));
            Parent root = loader.load();
            ThemPhongController controller = loader.getController();
            controller.setPhongChangeListener(this::loadPhong);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Thêm phòng mới");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

