package com.designPattern.prototype;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.Data;
import lombok.SneakyThrows;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/9/15 9:36
 */
@Data
public class User implements Cloneable {

    String username;
    int    age;

    // 克隆该实例
    @Override
    protected Object clone() {

        User user = null;
        try {
            user  = (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
