package io.github.sliverkiss;

import cn.hutool.crypto.SecureUtil;
import io.github.sliverkiss.constants.UserContants;
import io.github.sliverkiss.domain.entity.RBAC.User;

/**
 * @author SliverKiss
 * @apiNote
 * @date 2023/9/5
 */

public class Main {
    public static void main(String[] args) {
        User user = new User ();
        user.setUsername ( "sliverkiss" );
        user.setPassword ( "123456" );
        String password = SecureUtil.md5 ( user.getPassword () + UserContants.MD5_SALT );
        System.out.println ( password );
    }
}
