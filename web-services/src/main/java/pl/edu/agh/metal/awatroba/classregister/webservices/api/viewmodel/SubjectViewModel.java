package pl.edu.agh.metal.awatroba.classregister.webservices.api.viewmodel;

import javax.validation.constraints.NotNull;

public class SubjectViewModel {
    private String id;

    @NotNull
    private String name;

    private int nbMarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbMarks() {
        return nbMarks;
    }

    public void setNbMarks(int nbMarks) {
        this.nbMarks = nbMarks;
    }
}
