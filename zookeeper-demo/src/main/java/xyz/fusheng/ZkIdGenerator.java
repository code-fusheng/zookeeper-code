package xyz.fusheng;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @FileName: ZkIdGenerator
 * @Author: code-fusheng
 * @Date: 2020/8/30 13:23
 * @version: 1.0
 * Description: 全局自增 ID 生成器
 */

public class ZkIdGenerator {

    /**
     * zookeeper single 单机
     */
    private static final String ZKSINGLE = "175.24.45.179:2184";
    /**
     * zookeeper cluster 集群
     */
    private static final String ZKCLUSTER = "175.24.45.179:2181,175.24.45.179:2182,175.24.45.179:2183";

    private static ZkClient zkClient = null;

    static {
        zkClient = new ZkClient(ZKCLUSTER, 10000, 10000);
    }

    public static Integer next(String path) {
        if (!zkClient.exists(path)) {
            zkClient.create(path, new byte[0], CreateMode.PERSISTENT);
        }
        // 修改最小单位数据，减少 IO 压力
        Stat stat = zkClient.writeDataReturnStat(path, new byte[0], -1);
        int version = stat.getVersion();
        return version;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i ++) {
            System.out.println(next("/id-gen"));
        }
    }

}
