package xyz.fusheng;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * @FileName: TestZKServer
 * @Author: code-fusheng
 * @Date: 2020/8/30 12:34
 * @version: 1.0
 * Description: 测试 zkServer 连接
 */

public class TestZkServer {

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
        zkClient = new ZkClient(ZKSINGLE, 10000, 10000);
    }

    public static void main(String[] args) {

        /**
         * zkClient 方法参数说明
         * path : 路径
         * data : 数据
         * 节点类型 持久化
         */
        // 添加节点
        zkClient.create("/java", "java", CreateMode.PERSISTENT);
        // 修改节点内容
        zkClient.writeData("/java", "fusheng");
        // 查询节点内容
        Object data = zkClient.readData("/java");
        System.out.println(data);
        // 删除节点
        zkClient.delete("/java");
        System.out.println("操作成功 zkClient");

    }

}
