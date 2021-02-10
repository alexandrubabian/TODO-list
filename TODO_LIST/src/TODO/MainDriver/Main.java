package TODO.MainDriver;

import TODO.Databases.Database;
import TODO.Model.Task;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main extends Application {
    /*Database connection*/
    Database db = new Database();

    /*Creating Task Object*/
    private Task task = new Task();

    /*Main Components*/
    Stage primaryStage = new Stage();
    AnchorPane rootPane = new AnchorPane();
    String fontFamily = "Arial";

    /*Components for MainScreen*/
    Label lblTodo = new Label();
    Button btn_addTasks = new Button();
    Button btn_seeSchedule = new Button();

    /*Components for AddTaskScreen*/
    Label lblTasks = new Label();
    Button btn_importantTask = new Button();
    Button btn_mightDoTask = new Button();
    Button btn_likeToDoTasks = new Button();
    Button btn_testTasks = new Button();
    Button btn_bckToMain = new Button("Back");

    /*Components for TaskScreen*/
    Label lblTaskDetail = new Label();
    Label lblTaskTitle = new Label();
    Label lblTaskDesc = new Label();
    Label lblTaskDate = new Label();
    TextField txtTitle = new TextField();
    TextArea txtDescription = new TextArea();
    DatePicker txtDate = new DatePicker();
    Button btn_taskSchedule = new Button();
    Button btn_bckToTaskType = new Button("Back");

    /*Components for SeeScheduleScreen*/
    TableView table;
    TableColumn col;
    ObservableList<ObservableList> data2;
    ObservableList<String> row;
    Label lblTasksSchedule = new Label();
    Button btn_viewDetails = new Button();

    /*Components for TaskDetailsScreen*/
    Label lbl_TaskDetail = new Label();
    Label lbl_TaskTitle = new Label();
    Label lbl_TaskDesc = new Label();
    Label lbl_TaskDate = new Label();
    Label lbl_TaskStatus = new Label();
    Label lbl_TaskType = new Label();

    TextField txt_Title = new TextField();
    TextArea txt_Description = new TextArea();
    TextField txt_Status = new TextField();
    TextField txt_Type = new TextField();
    TextField txt_Date = new TextField();
    Button btn_statusDoing =  new Button();
    Button btn_statusDone =  new Button();
    Button btn_bckToSchedule =  new Button("Back");

    /*Setting flags*/
    String currentTaskType = "Other";
    String currentTaskStatus = "New";

    /*Scene Components*/
    private Scene MainScreen,AddTasks,SeeSchedule,TaskDescription, TaskDetails;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        /*Setting Up Main Screen*/
        primaryStage.setScene(MainScreen());
        primaryStage.setTitle("TODO_LIST");
        primaryStage.show();

        /*Main Screen Button's Event Handlers*/
        btn_addTasks.setOnAction(e -> {
            primaryStage.setScene(AddTasksScreen());
            primaryStage.setTitle("Add Task");
        });
        btn_seeSchedule.setOnAction(e -> {
            primaryStage.setScene(SeeScheduleScreen());
            primaryStage.setTitle("Your Today's Schedule");
        });

        /*Add Task Screen Button's Event Handlers*/
        btn_importantTask.setOnAction(e -> {
            currentTaskType = btn_importantTask.getText();
            primaryStage.setScene(TaskDescScreen());
            primaryStage.setTitle("Add Important Task");
        });
        btn_mightDoTask.setOnAction(e -> {
            currentTaskType = btn_mightDoTask.getText();
            primaryStage.setScene(TaskDescScreen());
            primaryStage.setTitle("Add Might Do Task");
        });
        btn_likeToDoTasks.setOnAction(e -> {
            currentTaskType = btn_likeToDoTasks.getText();
            primaryStage.setScene(TaskDescScreen());
            primaryStage.setTitle("Add Like To Do Task");
        });
        btn_testTasks.setOnAction(e -> {
            currentTaskType = btn_testTasks.getText();
            primaryStage.setScene(TaskDescScreen());
            primaryStage.setTitle("Add Test Task");
        });
        btn_taskSchedule.setOnAction(e -> {
            addingTask(currentTaskType);
        });

        /*See Schedule Screen Button's Event Handlers*/
        btn_viewDetails.setOnAction(e -> {
            viewTaskDetails();
        });

        /* Back Button's Event Handlers*/
        btn_bckToTaskType.setOnAction(e -> {
            primaryStage.setScene(AddTasksScreen());
            primaryStage.setTitle("Add Task");
        });
        btn_bckToMain.setOnAction(e -> {
            primaryStage.setScene(MainScreen());
            primaryStage.setTitle("TODO_LIST");
        });
        btn_bckToSchedule.setOnAction(e -> {
            primaryStage.setScene(SeeScheduleScreen());
            primaryStage.setTitle("Your Today's Schedule");
        });
    }
    public static void main(String[] list) {
        launch(list);
    }

    private Scene MainScreen() {
        lblTodo.setText("My TODO List");
        lblTodo.setFont(Font.font(fontFamily,24));

        btn_addTasks.setText("Add Task For Today");
        btn_addTasks.setPadding(new Insets(30,20,30,20));
        btn_addTasks.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        btn_seeSchedule.setText("See Schedule For Today");
        btn_seeSchedule.setPadding(new Insets(30,20,30,20));
        btn_seeSchedule.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        HBox hb_btnOptions = new HBox(10);
        hb_btnOptions.getChildren().addAll(btn_seeSchedule,btn_addTasks);
        hb_btnOptions.setAlignment(Pos.CENTER);
        HBox.setMargin(btn_addTasks, new Insets(0,0,0,30));

        VBox vb_first_screen = new VBox(10);
        vb_first_screen.getChildren().addAll(lblTodo);
        vb_first_screen.setAlignment(Pos.CENTER);
        VBox.setMargin(lblTodo, new Insets(100,0,0,0));

        rootPane = new AnchorPane(vb_first_screen,hb_btnOptions);
        vb_first_screen.prefWidthProperty().bind(rootPane.widthProperty());
        hb_btnOptions.prefWidthProperty().bind(rootPane.widthProperty());
        hb_btnOptions.prefHeightProperty().bind(rootPane.heightProperty());

        MainScreen = new Scene(rootPane, 700,600);
        return MainScreen;
    }
    private Scene AddTasksScreen() {
        btn_bckToMain.setText("<-");

        lblTasks.setText("Choose Task Type");
        lblTasks.setFont(Font.font(fontFamily, 24));

        btn_importantTask.setText("Important Task");
        btn_importantTask.setPrefSize(150,100);
        btn_importantTask.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        btn_mightDoTask.setText("Might Do");
        btn_mightDoTask.setPrefSize(150,100);
        btn_mightDoTask.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        btn_likeToDoTasks.setText("Would Like To Do");
        btn_likeToDoTasks.setPrefSize(150,100);
        btn_likeToDoTasks.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        btn_testTasks.setText("Test Today");
        btn_testTasks.setPrefSize(150,100);
        btn_testTasks.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        HBox hb_impbtnOptions = new HBox(10);
        hb_impbtnOptions.getChildren().addAll(btn_importantTask,btn_testTasks);
        hb_impbtnOptions.setAlignment(Pos.CENTER);
        HBox.setMargin(btn_testTasks, new Insets(0,0,0,30));

        HBox hb_lessImpbtnOptions = new HBox(10);
        hb_lessImpbtnOptions.getChildren().addAll(btn_mightDoTask,btn_likeToDoTasks);
        hb_lessImpbtnOptions.setAlignment(Pos.CENTER);
        HBox.setMargin(btn_likeToDoTasks, new Insets(0,0,0,30));

        VBox vb_add_screen = new VBox(10);
        vb_add_screen.getChildren().addAll(lblTasks);
        vb_add_screen.setAlignment(Pos.CENTER);
        VBox.setMargin(lblTasks, new Insets(100,0,0,0));

        VBox vb_taskButtons_screen = new VBox(10);
        vb_taskButtons_screen.getChildren().addAll(hb_impbtnOptions,hb_lessImpbtnOptions);
        vb_taskButtons_screen.setAlignment(Pos.CENTER);

        rootPane = new AnchorPane(vb_add_screen,vb_taskButtons_screen,btn_bckToMain);
        vb_add_screen.prefWidthProperty().bind(rootPane.widthProperty());
        vb_taskButtons_screen.prefWidthProperty().bind(rootPane.widthProperty());
        vb_taskButtons_screen.prefHeightProperty().bind(rootPane.heightProperty());

        AddTasks = new Scene(rootPane, 700,600);
        return AddTasks;
    }

    private Scene SeeScheduleScreen() {
        btn_bckToMain.setText("<-");
        lblTasksSchedule.setText("Your Today's Schedule");
        lblTasksSchedule.setFont(Font.font(fontFamily, 24));

        table = new TableView();
        builddata();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.prefWidthProperty();

        btn_viewDetails.setText("View Details");
        btn_viewDetails.setPrefSize(100,40);
        btn_viewDetails.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        VBox vb_seeSchedule = new VBox(10);
        vb_seeSchedule.getChildren().addAll(lblTasksSchedule);
        vb_seeSchedule.setAlignment(Pos.CENTER);
        VBox.setMargin(lblTasksSchedule, new Insets(80,0,0,0));

        VBox vb_tableView = new VBox(10);
        vb_tableView.getChildren().addAll(table,btn_viewDetails);
        vb_tableView.setAlignment(Pos.CENTER);
        VBox.setMargin(table, new Insets(120,20,0,20));

        rootPane = new AnchorPane(vb_seeSchedule,vb_tableView,btn_bckToMain);
        vb_seeSchedule.prefWidthProperty().bind(rootPane.widthProperty());
        vb_tableView.prefWidthProperty().bind(rootPane.widthProperty());
        vb_tableView.prefHeightProperty().bind(rootPane.heightProperty());

        SeeSchedule = new Scene(rootPane, 700,600);
        return SeeSchedule;
    }

    private Scene TaskDescScreen() { btn_bckToTaskType.setText("<-");

        lblTaskDetail.setText("Add Task Details");
        lblTaskDetail.setFont(Font.font(fontFamily, 24));

        lblTaskTitle.setText("Task Title:");
        lblTaskTitle.setFont(Font.font(fontFamily,FontWeight.BOLD,12));
        lblTaskDesc.setText("Task Description:");
        lblTaskDesc.setFont(Font.font(fontFamily,FontWeight.BOLD,12));
        lblTaskDate.setText("Due Date:");
        lblTaskDate.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        txtTitle.setPromptText("Task Title");

        txtDescription.setPromptText("Task Description");
        txtDescription.setPrefSize(300,150);
        txtDescription.setWrapText(true);

        txtDate.setValue(LocalDate.now());
        txtDate.setPrefWidth(300);
        txtDate.setEditable(false);

        btn_taskSchedule.setText("Save");
        btn_taskSchedule.setPrefSize(100,40);
        btn_taskSchedule.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        VBox hb_taskLabels = new VBox();
        hb_taskLabels.getChildren().addAll(lblTaskTitle,lblTaskDesc,lblTaskDate);
        hb_taskLabels.setAlignment(Pos.CENTER);
        VBox.setMargin(lblTaskTitle, new Insets(0,0,20,0));
        VBox.setMargin(lblTaskDesc, new Insets(0,0,150,0));
        VBox.setMargin(lblTaskDate, new Insets(0,0,65,0));

        VBox hb_taskField = new VBox(10);
        hb_taskField.getChildren().addAll(txtTitle,txtDescription,txtDate,btn_taskSchedule);
        hb_taskField.setAlignment(Pos.CENTER);
        VBox.setMargin(btn_taskSchedule, new Insets(10,0,0,0));

        HBox vb_taskData = new HBox(10);
        vb_taskData.getChildren().addAll(hb_taskLabels,hb_taskField);
        vb_taskData.setAlignment(Pos.CENTER);

        VBox vb_taskdetail = new VBox(10);
        vb_taskdetail.getChildren().addAll(lblTaskDetail,vb_taskData);
        vb_taskdetail.setAlignment(Pos.CENTER);
        VBox.setMargin(lblTaskDetail, new Insets(100,0,0,0));

        rootPane = new AnchorPane(vb_taskdetail,vb_taskData,btn_bckToTaskType);
        vb_taskdetail.prefWidthProperty().bind(rootPane.widthProperty());
        vb_taskData.prefWidthProperty().bind(rootPane.widthProperty());
        vb_taskData.prefHeightProperty().bind(rootPane.heightProperty());

        TaskDescription = new Scene(rootPane, 700,600);
        return TaskDescription;
    }
    private Scene TaskDetailsScreen(char in){
        btn_bckToSchedule.setText("<-");

        lbl_TaskDetail.setText("Task Details");
        lbl_TaskDetail.setFont(Font.font(fontFamily, 24));

        lbl_TaskTitle.setText("Task Title:");
        lbl_TaskTitle.setFont(Font.font(fontFamily,FontWeight.BOLD,12));
        lbl_TaskDesc.setText("Task Description:");
        lbl_TaskDesc.setFont(Font.font(fontFamily,FontWeight.BOLD,12));
        lbl_TaskDate.setText("Due Date:");
        lbl_TaskDate.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        lbl_TaskStatus.setText("Status:");
        lbl_TaskStatus.setFont(Font.font(fontFamily,FontWeight.BOLD,12));
        lbl_TaskType.setText("Task Type:");
        lbl_TaskType.setFont(Font.font(fontFamily,FontWeight.BOLD,12));

        txt_Title.setPromptText("Task Title");
        txt_Title.setEditable(false);

        txt_Description.setPromptText("Task Description");
        txt_Description.setPrefSize(300,150);
        txt_Description.setWrapText(true);
        txt_Description.setEditable(false);

        txt_Date.setText("Task Date");
        txt_Date.setPrefWidth(300);
        txt_Date.setEditable(false);

        txt_Status.setPromptText("Status");
        txt_Status.setEditable(false);
        txt_Type.setPromptText("Type");
        txt_Type.setEditable(false);

        btn_statusDoing.setText("Done");
        btn_statusDone.setText("Doing");
        /*Add Task details Screen Button's Event Handlers*/
        btn_statusDoing.setOnAction(e -> {
            changeStatus(btn_statusDoing.getText(), Character.getNumericValue(in));
        });
        btn_statusDone.setOnAction(e -> {
            changeStatus(btn_statusDone.getText(), Character.getNumericValue(in));
        });

        HBox vb_status = new HBox(10);
        vb_status.getChildren().addAll(btn_statusDoing,btn_statusDone);
        vb_status.setAlignment(Pos.CENTER_RIGHT);

        VBox hb_taskLabels = new VBox();
        hb_taskLabels.getChildren().addAll(lbl_TaskTitle,lbl_TaskDesc,lbl_TaskDate,lbl_TaskType,lbl_TaskStatus);
        hb_taskLabels.setAlignment(Pos.CENTER);
        VBox.setMargin(lbl_TaskTitle, new Insets(0,0,20,0));
        VBox.setMargin(lbl_TaskDesc, new Insets(0,0,150,0));
        VBox.setMargin(lbl_TaskDate, new Insets(0,0,20,0));
        VBox.setMargin(lbl_TaskType, new Insets(0,0,20,0));
        VBox.setMargin(lbl_TaskStatus, new Insets(0,0,20,0));

        VBox hb_taskField = new VBox(10);
        hb_taskField.getChildren().addAll(txt_Title,txt_Description,txt_Date,txt_Type,txt_Status,vb_status);
        hb_taskField.setAlignment(Pos.CENTER);
        VBox.setMargin(txt_Title, new Insets(20,0,0,0));

        HBox vb_taskData = new HBox(10);
        vb_taskData.getChildren().addAll(hb_taskLabels,hb_taskField);
        vb_taskData.setAlignment(Pos.CENTER);

        VBox vb_taskdetail = new VBox(10);
        vb_taskdetail.getChildren().addAll(lbl_TaskDetail,vb_taskData);
        vb_taskdetail.setAlignment(Pos.CENTER);
        VBox.setMargin(lbl_TaskDetail, new Insets(100,0,0,0));
        getDetails(in);

        rootPane = new AnchorPane(vb_taskdetail,vb_taskData,btn_bckToSchedule);
        vb_taskdetail.prefWidthProperty().bind(rootPane.widthProperty());
        vb_taskData.prefWidthProperty().bind(rootPane.widthProperty());
        vb_taskData.prefHeightProperty().bind(rootPane.heightProperty());

        TaskDetails = new Scene(rootPane, 700,600);
        return TaskDetails;
    }
    private void getDetails(char in){
        try {
            db.prestatement = db.connection.prepareStatement("SELECT * FROM task where taskId = ?");
            db.prestatement.setInt(1, Character.getNumericValue(in));
            db.resultSet = db.prestatement.executeQuery();
            while (db.resultSet.next()) {
                txt_Title.setText(db.resultSet.getString("taskId"));
                txt_Description.setText(db.resultSet.getString("taskDescription"));
                txt_Date.setText(db.resultSet.getDate("taskDate").toString());
                txt_Status.setText(db.resultSet.getString("taskStatus"));
                txt_Type.setText(db.resultSet.getString("taskType"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addingTask(String currentTaskType) {
        if (txtTitle.getText().isEmpty() || txtDescription.getText().isEmpty()) {
            Alert msg = new Alert(Alert.AlertType.WARNING);
            msg.setTitle("Add Task");
            msg.setContentText("Please Enter Title And Description of Task");
            msg.show();
        }
        else if (txtTitle.getText().isEmpty()) {
            Alert msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("Add Task");
            msg.setContentText("Please Enter Title of Task");
            msg.show();
        }
        else if (txtDescription.getText().isEmpty()) {
            Alert msg = new Alert(Alert.AlertType.INFORMATION);
            msg.setTitle("Add Task");
            msg.setContentText("Please Description of Task");
            msg.show();
        } else {
            try {
                task.setTitle(txtTitle.getText());
                task.setDescription(txtDescription.getText());
                task.setDueDate(txtDate.getValue());
                task.setStatus(currentTaskStatus);
                task.setType(currentTaskType);

                db.prestatement = db.connection
                        .prepareStatement("INSERT INTO task(taskTitle, taskDescription, taskDate, taskStatus, taskType) VALUES (?,?,?,?,?)");
                db.prestatement.setString(1, task.getTitle());
                db.prestatement.setString(2, task.getDescription());
                db.prestatement.setDate(3, Date.valueOf(task.getDueDate()));
                db.prestatement.setString(4, task.getStatus());
                db.prestatement.setString(5, task.getType());

                int numberOfRowsChanged = db.prestatement.executeUpdate();
                if (numberOfRowsChanged > 0) {
                    Alert msg = new Alert(Alert.AlertType.INFORMATION);
                    msg.setTitle("Add Task");
                    msg.setContentText("Task added successfully.");
                    clearFields();
                    msg.show();
                }
            }// Handle any errors that may have occurred.
            catch (Exception e2) {
                //e2.printStackTrace();
                Alert msg = new Alert(Alert.AlertType.INFORMATION);
                msg.setTitle("Add Task");
                msg.setContentText("Error occurred. Please Try again.");
                clearFields();
                msg.show();

            }
        }
    }
    public void viewTaskDetails(){
        try {
            if (table.getSelectionModel().getSelectedIndex() >= 0) {
                TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                String item = (table.getItems().get(row).toString());

                primaryStage.setScene(TaskDetailsScreen(item.charAt(1)));
                primaryStage.setTitle("Task Detail");
            } else {
                Alert msg = new Alert(Alert.AlertType.INFORMATION);
                msg.setTitle("Error Alert");
                msg.setContentText("Please Select a Course");
                msg.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void builddata() {
        table.getColumns().clear();

        data2 = FXCollections.observableArrayList();

        try {
            db.prestatement = db.connection.prepareStatement("SELECT * FROM task");
            ResultSet rv = db.prestatement.executeQuery();

            for (int i = 0; i < rv.getMetaData().getColumnCount()-1; i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                col = new TableColumn(rv.getMetaData().getColumnName(i + 1));

                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                table.getColumns().addAll(col);
            }

            while (rv.next()) {
                row = FXCollections.observableArrayList();
                for (int i = 1; i <= rv.getMetaData().getColumnCount()-1; i++) {
                    row.add(rv.getString(i));
                }
                data2.add(row);
            }

            table.setItems(data2);
            table.autosize();

        } catch (SQLException ezz) {
            ezz.printStackTrace();
        }

    }

    private void changeStatus(String status, int in){
            try {
                this.task.setStatus(status);

                db.prestatement = db.connection
                        .prepareStatement("Update task SET taskStatus = '"+status+"' WHERE taskId= "+ in);

                int numberOfRowsChanged = db.prestatement.executeUpdate();
                if (numberOfRowsChanged > 0) {
                    Alert msg = new Alert(Alert.AlertType.INFORMATION);
                    msg.setTitle("Status Change");
                    msg.setContentText("Status changed successfully");
                    clearFields();
                    msg.show();
                    primaryStage.setScene(SeeScheduleScreen());
                    primaryStage.setTitle("Your's Today Schedule");
                }
            }// Handle any errors that may have occurred.
            catch (Exception e2) {
                e2.printStackTrace();
                Alert msg = new Alert(Alert.AlertType.INFORMATION);
                msg.setTitle("Add Task");
                msg.setContentText("Error occurred. Please Try again.");
                clearFields();
                msg.show();

            }
    }
    private void clearFields(){
        txtTitle.clear(); txtDescription.clear();
    }
}
