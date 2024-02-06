package cc.langhai.es;

import cc.langhai.config.system.SystemConfig;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户相关操作测试
 *
 * @author 余微微
 * @date 2023-03-31 09:34
 */
@SpringBootTest
public class UserTest {

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 用于生成用户密码密文
     */
    @Test
    public void passwordGenerate(){
        // 构建AES加密工具
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, systemConfig.getSecret().getBytes());
        // 加密为16进制表示
        String encryptHex = aes.encryptHex("密码原文");
        System.out.println(encryptHex);
    }
}
