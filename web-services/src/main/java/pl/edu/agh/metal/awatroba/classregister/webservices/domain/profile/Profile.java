package pl.edu.agh.metal.awatroba.classregister.webservices.domain.profile;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer level;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Allocation> allocations;

    public void addAllocation(Allocation allocation) {
        allocation.setProfile(this);
        getAllocations().add(allocation);
    }

    public void removeAllocation(Allocation allocation) {
        allocation.setProfile(null);
        getAllocations().remove(allocation);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Collection<Allocation> getAllocations() {
        return allocations;
    }

    public void setAllocations(Collection<Allocation> allocations) {
        this.allocations = allocations;
    }
}
