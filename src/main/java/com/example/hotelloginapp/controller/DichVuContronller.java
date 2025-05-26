package com.example.hotelloginapp.controller;

import com.example.hotelloginapp.dao.DichVuDao;
import com.example.hotelloginapp.models.DichVu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.hotelloginapp.utils.util.showDialog;

public class DichVuContronller implements Initializable {
    @FXML private TableView<DichVu> tableDichVu;
    @FXML private TableColumn<DichVu, String> colMaDV, colTenDV, colGiaDV;
    @FXML private TextField addMaDV, addTenDV, addGiaDV;
    private ObservableList<DichVu> dichvulist = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMaDV.setCellValueFactory(new PropertyValueFactory<>("maDV"));
        colTenDV.setCellValueFactory(new PropertyValueFactory<>("tenDV"));
        colGiaDV.setCellValueFactory(new PropertyValueFactory<>("giaDV"));

        loadTable();
        tableDichVu.setRowFactory(tv -> {
            TableRow<DichVu> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    DichVu selected = row.getItem();
                    populateForm(selected);
                }
            });
            return row;
        });
        addListener();
    }

    public void loadTable() {
        dichvulist = FXCollections.observableArrayList(DichVuDao.getAllDichVu());
        tableDichVu.setItems(dichvulist);
    }
    public void addListener(){
        tableDichVu.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateLabels(newSelection);
            }
        });
    }

    private void updateLabels(DichVu dv) {
        colMaDV.setText(dv.getMaDV());
        colTenDV.setText(dv.getTenDV());
        colGiaDV.setText(String.valueOf(dv.getGiaDV()));

    }

    private void populateForm(DichVu dv) {
        addMaDV.setText(dv.getMaDV());
        addTenDV.setText(dv.getTenDV());
        addGiaDV.setText(String.valueOf(dv.getGiaDV()));
    }
    @FXML
    private void adddichvu() {

        if (addMaDV.getText().isEmpty() || addTenDV.getText().isEmpty() || addGiaDV.getText().isEmpty()) {
            showDialog("Vui lòng điền đầy đủ thông tin!");
            return;
        }
        try {
            String MaDV = addMaDV.getText();
            String TenDV = addTenDV.getText();
            String GiaDVStr = addGiaDV.getText();
            BigDecimal giaDV = new BigDecimal(GiaDVStr);

            DichVu dichvu = new DichVu(MaDV, TenDV, giaDV);
            boolean success = DichVuDao.insertDichVu(dichvu);

            if (success) {
                dichvulist.add(dichvu);
                showDialog("✅ Thêm Dịch vụ thành công!");
            } else {
                showDialog("❌ Lỗi khi thêm Dịch vụ!");
            }

        } catch (NumberFormatException e) {
            showDialog("Giá phải là số!");
        }
    }

    public void xoadichvu(ActionEvent e) {
        DichVu selected = tableDichVu.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showDialog("❗ Vui lòng chọn một nhân viên để xoá!");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xoá");
        confirm.setContentText("Bạn có chắc muốn xoá Dịch vụ: " + selected.getTenDV() + "?");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            if (DichVuDao.xoaDichVu(selected.getMaDV())) {
                dichvulist.remove(selected);
                showDialog("✅ Xoá Dịch vụ thành công!");
            } else {
                showDialog("❌ Lỗi khi xoá Dịch vụ!");
            }
        }
    }
}
