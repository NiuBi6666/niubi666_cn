package com.xiuxiuguang.niubi.service;

import cn.hutool.core.collection.CollectionUtil;
import com.xiuxiuguang.niubi.dao.system.SysUserMapper;
import com.xiuxiuguang.niubi.pojo.domain.system.SysUser;
import com.xiuxiuguang.niubi.pojo.domain.system.SysUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: MyUserDetailsService
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/9/27     lixiuguang    Create the current class
 *******************************************************************************/
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserExample userExample = new SysUserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<SysUser> users = userMapper.selectByExample(userExample);
        if (CollectionUtil.isEmpty(users)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("rolez`");
        SysUser sysUser = users.get(0);
        return new User(sysUser.getUsername(), new BCryptPasswordEncoder().encode(sysUser.getPassword()), authorities);
    }
}