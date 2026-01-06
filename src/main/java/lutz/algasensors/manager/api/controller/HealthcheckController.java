package lutz.algasensors.manager.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class HealthcheckController {
    @RestController
    class HealthController {
        @GetMapping("/health")
        public String ok() {
            return "OK";
        }
    }

}
