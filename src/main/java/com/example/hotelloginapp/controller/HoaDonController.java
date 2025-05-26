package com.example.hotelloginapp.controller;

import com.example.hotelloginapp.dao.HoaDonDao;
import com.example.hotelloginapp.models.HoaDonView;
import com.example.hotelloginapp.utils.util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class HoaDonController implements Initializable {

    @FXML private TableView<HoaDonView> HoaDonTable;
    @FXML private TableColumn<HoaDonView, Integer> colMaHD;
    @FXML private TableColumn<HoaDonView, Integer> colMaDP;
    @FXML private TableColumn<HoaDonView, String> colMaKH;
    @FXML private TableColumn<HoaDonView, String> colMaNV;
    @FXML private TableColumn<HoaDonView, LocalDate> colNgayThue;
    @FXML private TableColumn<HoaDonView, LocalDate> colNgayTra;
    @FXML private TableColumn<HoaDonView, BigDecimal> colThanhtien;
    @FXML private TableColumn<HoaDonView, LocalDateTime> colNgaylapHD;
    @FXML private TextField tfsearch;
    @FXML private Button btnSearch;
    private ObservableList<HoaDonView> list = FXCollections.observableArrayList();
    private FilteredList<HoaDonView> filteredData=new FilteredList<>(list, kh -> true);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMaHD.setCellValueFactory(new PropertyValueFactory<>("maHD"));
        colMaHD.setCellFactory(util.createPrefixCellFactory("HD"));
        colMaDP.setCellValueFactory(new PropertyValueFactory<>("maDP"));
        colMaDP.setCellFactory(util.createPrefixCellFactory("DP"));
        colMaKH.setCellValueFactory(new PropertyValueFactory<>("maKH"));
        colMaNV.setCellValueFactory(new PropertyValueFactory<>("maNV"));
        colMaNV.setCellFactory(util.createPrefixCellFactory("NV"));
        colNgayThue.setCellValueFactory(new PropertyValueFactory<>("ngayThue"));
        colNgayTra.setCellValueFactory(new PropertyValueFactory<>("ngayTra"));
        colThanhtien.setCellValueFactory(new PropertyValueFactory<>("tongTien"));
        colNgaylapHD.setCellValueFactory(new PropertyValueFactory<>("ngayLapHD"));

        loadData();
        tfsearch.setOnAction(e -> SearchHoaDon(null));
    }

    private void loadData() {
        list = FXCollections.observableArrayList(HoaDonDao.getHoaDon());
        filteredData = new FilteredList<>(list, hd -> true);
        HoaDonTable.setItems(filteredData);
    }


    public void SearchHoaDon(ActionEvent actionEvent) {
        String keyword = tfsearch.getText().toLowerCase().trim();

        filteredData.setPredicate(hd -> {
            if (keyword == null || keyword.isEmpty()) return true;

            String maHD = "hd" + hd.getMaHD();
            String maDP = "dp" + hd.getMaDP();
            String maKH = hd.getMaKH() != null ? hd.getMaKH().toLowerCase() : "";
            String maNV = hd.getMaNV() != null ? "nv" + hd.getMaNV().toLowerCase() : "";

            return maHD.contains(keyword) ||
                    maDP.contains(keyword) ||
                    maKH.contains(keyword) ||
                    maNV.contains(keyword);
        });
    }
}

