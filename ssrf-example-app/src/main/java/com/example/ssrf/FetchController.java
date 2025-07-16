package com.example.ssrf;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/fetch")
public class FetchController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public ResponseEntity<String> fetchUrl(@RequestParam String url) {
        String result = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/safe-fetch")
    public ResponseEntity<String> safeFetchUrl(@RequestParam String url) {
        try {
            URI uri = new URI(url);

            // Only allow HTTP/S
            if (!uri.getScheme().startsWith("https")) {
                return ResponseEntity.badRequest().body("Only HTTP and HTTPS are allowed.");
            }

            // Resolve hostname to IP
            InetAddress address = InetAddress.getByName(uri.getHost());

            // Check if the address is on a private range
            if (isPrivateIP(address)) {
                return ResponseEntity.status(403).body("Access to internal IPs is blocked.");
            }

            String result = restTemplate.getForObject(uri, String.class);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid or blocked URL: " + e.getMessage());
        }
    }

    private boolean isPrivateIP(InetAddress address) {
        return address.isAnyLocalAddress() ||     // 0.0.0.0 or ::
                address.isLoopbackAddress() ||     // 127.0.0.1 or ::1
                address.isLinkLocalAddress() ||    // 169.254.x.x or similar
                address.isSiteLocalAddress() ||    // 10.x.x.x, 192.168.x.x, 172.16-31.x.x
                address.isMulticastAddress();      // For safety, block multicast too
    }


}