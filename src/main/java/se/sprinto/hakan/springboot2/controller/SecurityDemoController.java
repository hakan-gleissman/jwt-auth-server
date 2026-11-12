package se.sprinto.hakan.springboot2.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/devops/demo")
public class SecurityDemoController {

    @PostMapping(
            value = "/lookup",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String lookup(@RequestParam String host)
            throws IOException, InterruptedException {

        // Simulerad injection: användarens text körs aldrig.
        Process process = new ProcessBuilder(
                "dig",
                "+tries=1",
                "+time=2",
                "guarddutyc2activityb.com"
        ).redirectErrorStream(true).start();

        if (!process.waitFor(5, TimeUnit.SECONDS)) {
            process.destroyForcibly();
            return "DNS-testet tog för lång tid.";
        }

        return new String(
                process.getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
    }
}
