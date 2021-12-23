package com.xiuxiuguang.niubi.factory;

import com.xiuxiuguang.niubi.service.office.FileTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*******************************************************************************
 *   @Author lixiuguang
 * - File Name: FileTransFactory
 * - Description:
 * - History:
 * Date         Author          Modification
 * 2021/3/15     lixiuguang    Create the current class
 *******************************************************************************/
@Component
public class FileTransFactory {
    @Autowired
    private Map<String, FileTransService> context = new ConcurrentHashMap<>();

    public FileTransService get(String suffix) {
        return this.context.get(suffix);
    }
}