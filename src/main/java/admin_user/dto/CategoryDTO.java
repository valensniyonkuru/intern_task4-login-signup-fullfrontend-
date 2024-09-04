package admin_user.dto;

public class CategoryDTO {

    private Long id;
    private String name;

    // Default constructor
    public CategoryDTO() {
    }

    // Parameterized constructor
    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
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
}
