package codesquad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Result {
    private long answerId;
    private String status;


    public static ResponseEntity<Map<String, Long>> fail(long answerId) {
        Map<String, Long> map = new HashMap<>();
        map.put("answerId", answerId);
        return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<Map<String, Long>> ok(long answerId) {
        Map<String, Long> map = new HashMap<>();
        map.put("answerId", answerId);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
