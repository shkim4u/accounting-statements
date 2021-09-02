package com.amazon.proserve.account.common.featuretoggle;

import com.amazon.proserve.account.common.featuretoggle.model.Flag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@ComponentScan(basePackages={"com.amazon.proserve.account.common.featuretoggle.config"})
public class Toggle {
    @Autowired
    private RestTemplate restTemplate;

    public boolean isEnabled(String toggleKey) {


        Map<String, Object> params = new HashMap<>();

        try {
            Flag flag = restTemplate.getForObject(toggleKey, Flag.class, params);

            boolean enabled = (flag != null ? flag.isEnabled() : false);
            log.info("[Feature Toggle Status] {} = {}", toggleKey, enabled);
            return enabled;
        } catch (Exception e) {
            log.warn("[Error while retrieving toggle: {}, {}", toggleKey, e.getMessage());
            return false;
        }
    }
}
