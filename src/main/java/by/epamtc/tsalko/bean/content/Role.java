package by.epamtc.tsalko.bean.content;

import java.io.Serializable;
import java.util.Objects;

public class Role implements Serializable {

    private static final long serialVersionUID = -5084441677970856985L;

    private int roleID;
    private String roleName;

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return roleID == role1.roleID &&
                Objects.equals(roleName, role1.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleID, roleName);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "roleID=" + roleID +
                ", role='" + roleName + '\'' +
                '}';
    }
}
