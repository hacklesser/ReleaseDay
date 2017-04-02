package com.releaseday.provider.service.appInfoPush;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.releaseday.api.service.appInfoPush.AppInfoPushService;
import com.releaseday.provider.config.AppInfoPushConf;

@Service(value = "appInfoPushService")
@EnableConfigurationProperties(AppInfoPushConf.class)
public class AppInfoPushServiceImpl implements AppInfoPushService{
	
	@Autowired
	private AppInfoPushConf appInfoPushConf;

	@Override
	public void generalPush() {
		
		IGtPush push = new IGtPush(appInfoPushConf.getUrl(), appInfoPushConf.getAppKey(), appInfoPushConf.getMasterSecret());

        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        LinkTemplate template = new LinkTemplate();
        template.setAppId(appInfoPushConf.getAppId());
        template.setAppkey(appInfoPushConf.getAppKey());
        template.setTitle("欢迎使用个推!");
        template.setText("这是一条推送消息~");
        template.setUrl("http://getui.com");

        List<String> appIds = new ArrayList<String>();
        appIds.add(appInfoPushConf.getAppId());

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);
        System.out.println(ret.getResponse().toString());
		
	}

}
