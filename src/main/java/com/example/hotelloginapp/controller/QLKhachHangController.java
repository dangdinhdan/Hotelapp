package com.example.hotelloginapp.controller;

import com.example.hotelloginapp.models.KhachHang;
import com.example.hotelloginapp.dao.QLKhachHangDao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

import java.util.ResourceBundle;


public class QLKhachHangController implements Initializable {
    @FXML private TableView<KhachHang> khachhangTableView;
    @FXML private TableColumn<KhachHang, String> colTenKH;
    @FXML private TableColumn<KhachHang, Integer> colMaKH;
    @FXML private TableColumn<KhachHang, String> colCccd;
    @FXML private TableColumn<KhachHang, String> colSdt;
    @FXML private TextField tfsearch;

    public static ObservableList<KhachHang> khachhangList= FXCollections.observableArrayList();
    private FilteredList<KhachHang> filteredData=new FilteredList<>(khachhangList, kh -> true);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        colTenKH.setCellValueFactory(new PropertyValueFactory<>("tenKH"));
        colCccd.setCellValueFactory(new PropertyValueFactory<>("cccd"));
        colSdt.setCellValueFactory(new PropertyValueFactory<>("sdt"));
        loadTable();

    }

    private void loadTable() {
        khachhangList = QLKhachHangDao.getAllKhachhang();
        SortedList<KhachHang> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(khachhangTableView.comparatorProperty());
        khachhangTableView.setItems(sortedData);
    }

    public void SearchKhachhang(ActionEvent actionEvent) {
        String keyword = tfsearch.getText().toLowerCase();

        filteredData.setPredicate(kh -> {
            if (keyword == null || keyword.isEmpty()) return true;

            return kh.getTenKH().toLowerCase().contains(keyword)
                    || kh.getCccd().toLowerCase().contains(keyword)
                    || kh.getSdt().toLowerCase().contains(keyword);
        });
    }
}
