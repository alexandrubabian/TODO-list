package TODO.Model;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task
{
    private StringProperty title;
    private StringProperty description;
    private LocalDate dueDate;
    private StringProperty status;
    private StringProperty type;

    public Task()
    {
        this.title = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.status = new SimpleStringProperty("");;
        this.type = new SimpleStringProperty("");;
        this.dueDate = LocalDate.now();
    }
    public Task(String title, String description, LocalDate dueDate, String status,String type)
    {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.status = new SimpleStringProperty(status);;
        this.type = new SimpleStringProperty(type);;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
