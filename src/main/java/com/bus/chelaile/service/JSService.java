package com.bus.chelaile.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;
import com.bus.chelaile.common.CacheUtil;
import com.bus.chelaile.common.Constants;
import com.bus.chelaile.model.PropertiesName;
import com.bus.chelaile.model.QueryParam;
import com.bus.chelaile.model.ShowType;
import com.bus.chelaile.model.ads.entity.BaseAdEntity;
import com.bus.chelaile.model.ads.entity.TaskEntity;
import com.bus.chelaile.model.ads.entity.TasksGroup;
import com.bus.chelaile.mvc.AdvParam;
import com.bus.chelaile.service.impl.DoubleAndSingleManager;
import com.bus.chelaile.service.impl.LineFeedAdsManager;
import com.bus.chelaile.service.impl.LineRightManager;
import com.bus.chelaile.service.impl.OpenManager;
import com.bus.chelaile.service.impl.OtherManager;
import com.bus.chelaile.service.impl.StationAdsManager;
import com.bus.chelaile.util.New;
import com.bus.chelaile.util.config.PropertiesUtils;

public class JSService {
    //    @Autowired
    //    private ServiceManager serviceManager;

    @Autowired
    private OpenManager openManager;
    @Autowired
    private StationAdsManager stationAdsManager;
    @Autowired
    private LineFeedAdsManager lineFeedAdsManager;
    @Autowired
    private DoubleAndSingleManager doubleAndSingleManager;
    @Autowired
    private LineRightManager lineRightManager;
    @Resource
    private ServiceManager serviceManager;
    @Autowired
    private OtherManager otherManager;

    protected static final Logger logger = LoggerFactory.getLogger(JSService.class);
    
    private static final ScheduledThreadPoolExecutor fixedThreadPool = new ScheduledThreadPoolExecutor(Integer.parseInt(PropertiesUtils.getValue(PropertiesName.PUBLIC.getValue(),
            "thread.count", "10")));
//    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
//    private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Integer.parseInt(PropertiesUtils.getValue(PropertiesName.PUBLIC.getValue(),
//            "thread.count", "10")));

    public TaskEntity getTask(final AdvParam param, String site) {
        long tBeginService = System.currentTimeMillis();
        TaskEntity taskEntity = new TaskEntity();
        //        ShowType showType;
        List<BaseAdEntity> entities = null;
        QueryParam queryParam = new QueryParam();
        queryParam.setJS(true);
        switch (site) {
            case "splash":
                //                showType = ShowType.OPEN_SCREEN;
                entities = openManager.doServiceList(param, ShowType.OPEN_SCREEN, queryParam);
                break;
            case "home":
                //                showType = ShowType.DOUBLE_COLUMN;
                entities = doubleAndSingleManager.doServiceList(param, ShowType.DOUBLE_COLUMN, queryParam);
                break;

            case "rightTop":
                //                showType = ShowType.LINE_RIGHT_ADV;
                entities = lineRightManager.doServiceList(param, ShowType.LINE_RIGHT_ADV, queryParam);
                break;
            case "station":
                //                showType = ShowType.STATION_ADV;
                entities = stationAdsManager.doServiceList(param, ShowType.STATION_ADV, queryParam);
                break;

            case "bottom":
                //                showType = ShowType.LINE_FEED_ADV;
                entities = lineFeedAdsManager.doServiceList(param, ShowType.LINE_FEED_ADV, queryParam);
                break;
                
            case "transfer":
                //                showType = ShowType.LINE_FEED_ADV;
                entities = otherManager.doServiceList(param, ShowType.TRANSFER_ADV, queryParam);
                break;
                
            case "stationDetail":
                //                showType = ShowType.LINE_FEED_ADV;
                entities = otherManager.doServiceList(param, ShowType.CAR_ALL_LINE_ADV, queryParam);
                break;

            case "allCars":
                //                showType = ShowType.LINE_FEED_ADV;
                entities = otherManager.doServiceList(param, ShowType.ALL_CAR_ADV, queryParam);
                break; 

            default:
                logger.error("未知类型的 site， udid={}, site={}", param);

        }
        //        List<BaseAdEntity> entities = openManager.doServiceList(param, ShowType.OPEN_SCREEN, new QueryParam());
        Map<String,String> map = New.hashMap();
        

       List<List<String>> tasks = New.arrayList();
        List<Long> times = New.arrayList();
        String closePic = "";
        if (entities != null && entities.size() > 0) {
            for (BaseAdEntity entity : entities) {
                if (entity.getTasksGroup() != null) {
                    tasks.addAll(entity.getTasksGroup().getTasks());
                    if(times.size() == 0)
                        times = entity.getTasksGroup().getTimeouts();
                    
                    if( entity.getTasksGroup() != null && entity.getTasksGroup().getMap() != null ) {
                    	for (Map.Entry<String, String> entry : entity.getTasksGroup().getMap().entrySet()) {
                        	map.put(entry.getKey(), entry.getValue());
                        }
                    }
                    
                    if(StringUtils.isNoneBlank(entity.getTasksGroup().getClosePic()))
                        closePic = entity.getTasksGroup().getClosePic();
                }
            }
            // 存储atraceInfo到redis中
            if(StringUtils.isBlank(param.getTraceid())) {
//            logger.info("traceid为空 ┭┮﹏┭┮");
                param.setTraceid(param.getUdid() + "_" + System.currentTimeMillis());
            }
            final String traceInfo = JSONObject.toJSONString(param);
            fixedThreadPool.execute(new Runnable() {
                
                @Override
                public void run() {
                    CacheUtil.setToAtrace(param.getTraceid(), traceInfo, Constants.ONE_HOUR_TIME * 8 );
                    
                }
            });
            
            
        }
        taskEntity.setTaskGroups(new TasksGroup(tasks, times,map, closePic));
        taskEntity.setTraceid(param.getTraceid());
        JSONObject resultMap = new JSONObject();
        resultMap.put("ads", entities);
        taskEntity.setAdDataString(JSONObject.toJSONString(serviceManager.getClienSucMap(resultMap, Constants.STATUS_REQUEST_SUCCESS)));
        logger.info("getTask cost time: udid={}, showType={}, cost={}", param.getUdid(), site, 
                System.currentTimeMillis() - tBeginService);
        return taskEntity;
    }

    public static void main(String[] args) {
        String a = "ddfs" + "_" + System.currentTimeMillis();
        System.out.println(a);
    }
}
