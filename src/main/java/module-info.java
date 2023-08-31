module com.ben.tp_javafx_orm {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.ben.tp_javafx_orm to javafx.fxml;
    exports com.ben.tp_javafx_orm.controllers;
    opens com.ben.tp_javafx_orm.controllers to javafx.fxml;
    exports com.ben.tp_javafx_orm.dao.entities;
    opens com.ben.tp_javafx_orm.dao.entities to javafx.fxml;
    exports com.ben.tp_javafx_orm;
}