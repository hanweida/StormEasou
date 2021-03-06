package com.easou.let;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.AuthorizationException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.NimbusClient;
import backtype.storm.utils.Utils;
import com.easou.let.fun.CdpConvertFunction;
import com.easou.let.fun.CdpReducer;
import com.easou.let.fun.CusMapState;
import com.easou.let.fun.InValidLogFilter;
import com.easou.let.spout.CdpTridentSpout;
import com.easou.let.trident.scheme_cdp.CdpScheme;
import com.easou.let.utils.PropertiesUtils;
import org.apache.hadoop.conf.Configured;
import org.apache.log4j.Logger;
import org.apache.storm.shade.org.json.simple.JSONValue;
import org.apache.thrift7.TException;
import storm.kafka.*;
import storm.kafka.trident.TransactionalTridentKafkaSpout;
import storm.kafka.trident.TridentKafkaConfig;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import java.io.IOException;
import java.util.Map;

/**
 * storm运行主类
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 16-11-3
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
public class StormLetMain extends Configured {

    private static final Logger logger = Logger.getLogger("operation");
    //任务名称
    //private final static String [] TASK_NAMES = {"asp","cdp"};
    //获得 storm-let.properties 配置文件
    private static final PropertiesUtils propertiesUtils = PropertiesUtils.getInstance();
    /**
     * @param args the input arguments
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws IOException, InterruptedException, TException {
        logger.info("StormEasou Start ");
        normalTask(args);
        logger.info("StormEasou End ");
    }

    /**
     * Normal task.
     * @param args the args
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    //正常执行任务
    public static void normalTask(String[] args) throws IOException, InterruptedException, TException {
        //获得Zookeeper Server
        String zks = propertiesUtils.getProperties("zookeeper_server");
        String zRoot = propertiesUtils.getProperties("zookeeper_Root");

        String aspTopic = "asp_Log";
        String cdpTopic = "test1";
        String asp_id = "asp";
        String cdp_id = "cdp";

        BrokerHosts brokerHosts = new ZkHosts(zks);
        /**Kafka配置**/
        //Asp日志Kafka配置
        SpoutConfig asp_spoutConfig = new SpoutConfig(brokerHosts, aspTopic, zRoot, asp_id);
        asp_spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

        //Cdp日志Kafka配置
        SpoutConfig cdp_spoutConfig = new SpoutConfig(brokerHosts, cdpTopic, zRoot, cdp_id);
        cdp_spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

        //Transaction CdpKafkaSpout Kafka事务 START
        TridentKafkaConfig tridentKafkaConfig = new TridentKafkaConfig(brokerHosts, cdpTopic, cdp_id);
        tridentKafkaConfig.scheme = new SchemeAsMultiScheme(new CdpScheme());
        TransactionalTridentKafkaSpout transactionalTridentKafkaSpout = new TransactionalTridentKafkaSpout(tridentKafkaConfig);
        //Transaction CdpKafkaSpout Kafka事务 END

        /**文件配置**/
        //CdpSpout 文件事务 START
        CdpTridentSpout cdpTridentSpout = new CdpTridentSpout();
        //CdpSpout 文件事务 END

        //TridentState state = tridentTopology.newStaticState(new CdpStateFactory());
        //创建CDP数据流
        TridentTopology tridentTopology = new TridentTopology();
        Stream cdpStream = tridentTopology.newStream("cdpTridentStream", transactionalTridentKafkaSpout);
        cdpStream.each(new Fields("cdpTridentFields"), new InValidLogFilter())
                .each(new Fields("cdpTridentFields"), new CdpConvertFunction(), new Fields("keyValue" ,"cdpShowClick"))
                .groupBy(new Fields("keyValue"))
                //.aggregate(new Fields("keyValue", "cdpShowClick"), new CdpAgg(), new Fields("d")).parallelismHint(5)
                .persistentAggregate(new CusMapState.Factory(), new Fields("keyValue","cdpShowClick"), new CdpReducer(), new Fields("out"))
                //.stateQuery(state, new Fields("d"), new CdpQueryFunction(), new Fields("cdp"))
        ;
//                .persistentAggregate()
//                .stateQuery(state, new Fields("keyValue", "cdpShowClick"), new CdpQueryFunction(), new Fields("cdp"))
        ;

//      本地或打包集群执行Topology
        excuteTopology(args, tridentTopology.build());
        //本地上传topology jar包到集群
        //deployTopology(tridentTopology.build());
    }

    /**
     * Asp日志Kafka配置Topology
     * @author:ES-BF-IT-126
     * @method:toBuildAspTologyBuilder
     * @date:Date 2017/6/14
     * @params:[topologyBuilder, brokerHosts, spoutConfig]
     * @returns:backtype.storm.topology.TopologyBuilder
     */
    public static TopologyBuilder toBuildAspTologyBuilder(TopologyBuilder topologyBuilder, BrokerHosts brokerHosts, SpoutConfig spoutConfig){
        //topologyBuilder.setSpout("aspSpout", new KafkaSpout(asp_spoutConfig), 2);
        //自定义Spout
        //topologyBuilder.setSpout("aspLogReader", new AspLogSpout(), 1);
        //asp日志normal bolt
        //topologyBuilder.setBolt("aspNormalBolt", new AspNormalBolt(), 1).localOrShuffleGrouping("aspSpout");
        //asp日志normal 合并bolt
        //topologyBuilder.setBolt("aspCombinerBolt", new AspCombinerBolt(), 10).fieldsGrouping("aspNormalBolt", new Fields("key"));
        //topologyBuilder.setBolt("aspHBaseBolt", new AspHbaseBolt(), 3).fieldsGrouping("aspNormalBolt", new Fields("key"));
        return topologyBuilder;
    }

    /**
     * 本地或打包集群执行Topology
     * @author:ES-BF-IT-126
     * @method:excuteTopology
     * @date:Date 2017/6/14
     * @params:[args, topologyBuilder]
     * @returns:void
     */
    public static void excuteTopology(String[] args, StormTopology topologyBuilder){
        //类名称
        String name = StormLetMain.class.getSimpleName();
        Config conf = new Config();
        if(args != null && args.length > 0){
            //Ni mbus host name passed from command line
            conf.put(Config.NIMBUS_HOST, args[0]);
            conf.setNumWorkers(3);
            logger.info("------------------------------------------submitTopologyWithProgressBar");
            try {
                StormSubmitter.submitTopologyWithProgressBar(name, conf, topologyBuilder);
            } catch (AlreadyAliveException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.r
            } catch (InvalidTopologyException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (AuthorizationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else {
            //conf.setMaxTaskParallelism(3);
            //asp.log.file 日志目录
            conf.put("asp.log.file", propertiesUtils.getProperties("asp.log.file"));
            //cdp.log.file日志目录
            conf.put("cdp.log.file", propertiesUtils.getProperties("cdp.log.file"));
            conf.setDebug(false);
            //提交Topology
            //conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
            //创建一个本地模式cluster
            LocalCluster cluster = new LocalCluster();
            //提交topology
            cluster.submitTopology(name, conf, topologyBuilder);
            //Utils.sleep(3000);
            //cluster.killTopology("Getting-Started-Toplogie");
            //cluster.shutdown();
        }
    }

    /**
     * 本地上传topology jar包到集群
     * @author:ES-BF-IT-126
     * @method:deployTopology
     * @date:Date 2017/6/14
     * @params:[topologyBuilder]
     * @returns:void
     */
    public static void deployTopology(StormTopology topologyBuilder) throws TException {
        Config conf = new Config();
        conf.setDebug(true);
        conf.setNumWorkers(4);
        System.setProperty("storm.conf.file", "storm_custom.yaml");
        Map stormConf = Utils.readStormConfig();
        stormConf.put("nimbus.host", "hadoop1");
        stormConf.putAll(conf);

        //打包后，本地jar包的位置
        String inputJar = "D:\\JerryCodeDemo\\StormEasou\\target\\StormEasou-1.0-SNAPSHOT.jar";
        NimbusClient nimbus = new NimbusClient(stormConf, "hadoop1", 6627);
        // 使用 StormSubmitter 提交 jar 包
        String uploadedJarLocation = StormSubmitter.submitJar(stormConf, inputJar);
        String jsonConf = JSONValue.toJSONString(stormConf);
        nimbus.getClient().submitTopology("remotetopology1", uploadedJarLocation, jsonConf, topologyBuilder);

    }
}
