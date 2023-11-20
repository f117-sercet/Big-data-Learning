package com.designPattern.composition;

import com.sun.deploy.security.BadCertificateDialog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description： TODO
 *
 * @author: 段世超
 * @aate: Created in 2023/11/15 15:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class organizationComponent {

    private String name;
    private String des;

    protected void add(organizationComponent org) {

        // 默认实现
        throw new UnsupportedOperationException();
    }

    protected void remove(organizationComponent org) {

        // 默认实现
        throw new UnsupportedOperationException();
    }

    protected abstract void print();
}
