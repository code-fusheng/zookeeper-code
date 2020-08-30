package xyz.fusheng;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.util.List;

/**
 * @FileName: TestZkSubData
 * @Author: code-fusheng
 * @Date: 2020/8/30 12:57
 * @version: 1.0
 * Description: 订阅 监控
 */

public class TestZkSubData {

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
        zkClient = new ZkClient(ZKCLUSTER);
    }

    public static void main(String[] args) throws IOException {
        // 创建 Java 节点
        zkClient.create("/java", "java", CreateMode.PERSISTENT);
        // 创建 java 下的子节点
        zkClient.create("/java/java1", "java1", CreateMode.PERSISTENT);
        zkClient.create("/java/java2", "java2", CreateMode.PERSISTENT);
        zkClient.create("/java/java3", "java3", CreateMode.PERSISTENT);
        zkClient.create("/java/java4", "java4", CreateMode.PERSISTENT);


        zkClient.subscribeDataChanges("/java", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("节点数据发生变更" + dataPath + " " + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("节点被删除" + dataPath);
            }
        });

        zkClient.subscribeChildChanges("/java", new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("父节点路径:" + parentPath);
                System.out.println("变更之后的子节点的信息");
                for (String currentChild : currentChilds) {
                    System.out.println(currentChild);
                }
            }
        });

        //不能让程序退出
        System.in.read();
        System.out.println("操作成功zkClient");
    }

}
