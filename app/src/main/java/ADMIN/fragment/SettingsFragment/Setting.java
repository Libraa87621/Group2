package ADMIN.fragment.SettingsFragment;

public class Setting {
    private int id;
    private String name;
    private String date;
    private String components;
    private String combo;

    public Setting(int id, String name, String date, String components) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.components = components;
    }


    public int getId() {
        return id;
    }
    public String getCombo() {
        return combo;
    }

    public void setCombo(String combo) {
        this.combo = combo;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }
}
